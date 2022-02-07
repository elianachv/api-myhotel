package com.bancoPopular.pruebaTecnica.service;

import com.bancoPopular.pruebaTecnica.entity.Servicio;
import com.bancoPopular.pruebaTecnica.exception.DuplicatedException;
import com.bancoPopular.pruebaTecnica.exception.NotFoundException;
import com.bancoPopular.pruebaTecnica.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ServicioServiceImpl implements ServicioService {

    @Autowired
    ServicioRepository servicioDao;

    @Override
    @Transactional(readOnly = true)
    public Iterable<Servicio> findAll() {
        return servicioDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Servicio> findAllByTipo(String tipo) {
        return servicioDao.findAllByTipo(tipo);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Servicio> findByIdentificador(String identificador) {
        Optional<Servicio> servicio = servicioDao.findByIdentificador(identificador);
        if (servicio.isEmpty()) {
            throw new NotFoundException("No existe ningún servicio registrado con el identificador " + identificador);
        }
        return servicio;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Servicio> findById(long id) {
        Optional<Servicio> servicio = servicioDao.findById(id);

        if (servicio.isEmpty()) {
            throw new NotFoundException("No existe ningún servicio registrado con el id " + id);

        }
        return servicio;
    }
    
    @Override
    @Transactional
    public Servicio save(Servicio servicio) {

        Optional<Servicio> servicioExistente1 = servicioDao.findById(servicio.getId());
        Optional<Servicio> servicioExistente2 = servicioDao.findByIdentificador(servicio.getIdentificador());


        if (servicioExistente1.isEmpty() && servicioExistente2.isEmpty()) {
            return servicioDao.save(servicio);
        }

        throw new DuplicatedException("El servicio que intenta crear ya existe");

    }

    @Override
    @Transactional
    public Servicio update(long id, Servicio servicioEditado) {
        Optional<Servicio> servicioExistente = servicioDao.findById(id);

        if (servicioExistente.isEmpty()) {
            throw new NotFoundException("No existe ningún servicio registrado con el id " + id);
        }

        //BeanUtils.copyProperties(servicioEditado,servicioExistente.get());
        servicioExistente.get().setTipo(servicioEditado.getTipo());
        servicioExistente.get().setIdentificador(servicioEditado.getIdentificador());
        servicioExistente.get().setDescripcion(servicioEditado.getDescripcion());
        servicioExistente.get().setPrecio(servicioEditado.getPrecio());

        return servicioDao.save(servicioExistente.get());
    }

    @Override
    @Transactional
    public Servicio update(String identificador, Servicio servicioEditado) {
        Optional<Servicio> servicioExistente = servicioDao.findByIdentificador(identificador);

        if (servicioExistente.isEmpty()) {
            throw new NotFoundException("No existe ningún servicio registrado con el identificador " + identificador);
        }

        //BeanUtils.copyProperties(servicioEditado,servicioExistente.get());
        servicioExistente.get().setTipo(servicioEditado.getTipo());
        servicioExistente.get().setIdentificador(servicioEditado.getIdentificador());
        servicioExistente.get().setDescripcion(servicioEditado.getDescripcion());
        servicioExistente.get().setPrecio(servicioEditado.getPrecio());

        return servicioDao.save(servicioExistente.get());
    }

    @Override
    @Transactional
    public Servicio delete(long id) {

        Optional<Servicio> servicioExistente = servicioDao.findById(id);

        if (servicioExistente.isEmpty()) {
            throw new NotFoundException("No existe ningún servicio registrado con el id " + id);
        }

        servicioDao.deleteById(id);
        return servicioExistente.get();
    }

    @Override
    @Transactional
    public Servicio delete(String identificador) {

        Optional<Servicio> servicioExistente = servicioDao.findByIdentificador(identificador);

        if (servicioExistente.isEmpty()) {
            throw new NotFoundException("No existe ningún servicio registrado con el identificador " + identificador);
        }

        servicioDao.delete(servicioExistente.get());
        return servicioExistente.get();
    }
}
