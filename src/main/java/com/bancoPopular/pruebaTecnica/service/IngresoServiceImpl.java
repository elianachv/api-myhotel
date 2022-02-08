package com.bancoPopular.pruebaTecnica.service;

import com.bancoPopular.pruebaTecnica.entity.Cliente;
import com.bancoPopular.pruebaTecnica.entity.Ingreso;
import com.bancoPopular.pruebaTecnica.exception.DuplicatedException;
import com.bancoPopular.pruebaTecnica.exception.NotFoundException;
import com.bancoPopular.pruebaTecnica.repository.ClienteRepository;
import com.bancoPopular.pruebaTecnica.repository.IngresoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class IngresoServiceImpl implements IngresoService {

    @Autowired
    IngresoRepository ingresoDao;
    @Autowired
    ClienteRepository clienteDao;

    @Override
    @Transactional(readOnly = true)
    public Iterable<Ingreso> findAll() {
        return ingresoDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Ingreso> findAllByGroup(long grupo) {
        return ingresoDao.findAllByGrupo(grupo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ingreso> getIngresosByCedula(String cedula) {
        Optional<Cliente> cliente = clienteDao.findByCedula(cedula);

        if (cliente.isEmpty()) {
            throw new NotFoundException("El cliente con cédula " + cedula + " no está registrado");
        }

        return ingresoDao.findAllByCedula(cedula);
    }

    @Override
    @Transactional
    public Optional<Ingreso> findById(long id) {

        Optional<Ingreso> ingreso = ingresoDao.findById(id);

        if (ingreso.isEmpty()) {
            throw new NotFoundException("El ingreso con id " + id + " no existe");
        }
        return ingreso;
    }

    @Override
    @Transactional
    public Ingreso save(Ingreso ingreso) {
        Optional<Ingreso> ingreso1 = ingresoDao.findById(ingreso.getId());
        if (ingreso1.isEmpty()) {
            ingresoDao.save(ingreso);
        }

        throw new DuplicatedException("El ingreso con id " + ingreso.getId() + " ya existe");
    }

    @Override
    @Transactional
    public Ingreso update(long id, Ingreso ingreso) {
        Optional<Ingreso> ingreso1 = ingresoDao.findById(id);
        if (ingreso1.isEmpty()) {
            throw new NotFoundException("El ingreso con id " + id + " no existe");

        }

        ingreso1.get().setFecha_ingreso(ingreso.getFecha_ingreso());
        ingreso1.get().setFecha_salida(ingreso.getFecha_salida());
        ingreso1.get().setId_grupo(ingreso.getId_grupo());
        ingreso1.get().setTotal_consumo(ingreso.getTotal_consumo());
        ingreso1.get().setCliente(ingreso.getCliente());

        return ingresoDao.save(ingreso1.get());
    }


    @Override
    @Transactional
    public Ingreso delete(long id) {
        if (!ingresoDao.existsById(id)) {
            throw new NotFoundException("El ingreso con id " + id + " no existe");

        }
        Optional<Ingreso> ingreso = ingresoDao.findById(id);

        ingresoDao.deleteById(id);

        return ingreso.get();
    }

    @Override
    @Transactional
    public void deleteByCedula(String cedula) {


        ingresoDao.deleteByCedula(cedula);

    }
}