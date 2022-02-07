package com.bancoPopular.pruebaTecnica.service;

import com.bancoPopular.pruebaTecnica.entity.Cliente;
import com.bancoPopular.pruebaTecnica.entity.Ingreso;
import com.bancoPopular.pruebaTecnica.entity.Registro;
import com.bancoPopular.pruebaTecnica.entity.Servicio;
import com.bancoPopular.pruebaTecnica.exception.DuplicatedException;
import com.bancoPopular.pruebaTecnica.exception.NotFoundException;
import com.bancoPopular.pruebaTecnica.repository.ClienteRepository;
import com.bancoPopular.pruebaTecnica.repository.IngresoRepository;
import com.bancoPopular.pruebaTecnica.repository.RegistroRepository;
import com.bancoPopular.pruebaTecnica.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Optional;

@Service
public class RegistroServiceImpl implements RegistrosService {

    @Autowired
    RegistroRepository registroDao;

    @Autowired
    ClienteRepository clienteDao;

    @Autowired
    ServicioRepository servicioDao;

    @Autowired
    IngresoRepository ingresoDao;

    @Override
    @Transactional(readOnly = true)
    public Iterable<Registro> findAll() {
        return registroDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Registro> findAllByCedula(String cedula) {
        return registroDao.findtAllByCedula(cedula);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Registro> findAllByServicio(String servicio) {
        return registroDao.findAllByServicio(servicio);
    }

    @Override
    @Transactional(readOnly = true)
    public Registro findById(long id) {
        Optional<Registro> registro = registroDao.findById(id);

        if (registro.isEmpty()) {
            throw new NotFoundException("No hay ningun registro con id " + id);
        }

        return registro.get();
    }

    @Override
    @Transactional
    public Registro save(Registro registro) {
        Optional<Registro> registro1 = registroDao.findById(registro.getId());
        System.out.println("REGISTRO A GUARDAR = " + registro.toString());
        Registro r = registroDao.save(registro);


        System.out.println(r.toString());
        return r;
        /*if (registro1.isEmpty()) {
            return registroDao.save(registro);
        }*/

        //throw new DuplicatedException("El registro que intenta crear ya existe");
    }

    @Override
    @Transactional
    public Registro save(Registro registro, String cedula, String idServicio) {
        Optional<Registro> registro1 = registroDao.findById(registro.getId());

        if (registro1.isEmpty()) {
            Optional<Cliente> cliente = clienteDao.findByCedula(cedula);
            Optional<Servicio> servicio = servicioDao.findByIdentificador(idServicio);

            if (cliente.isEmpty() || servicio.isEmpty()) {
                throw new NotFoundException("f");
            }

            registro.setCliente(cliente.get());
            registro.setServicio(servicio.get());
            return registroDao.save(registro);
        }

        throw new DuplicatedException("El registro que intenta crear ya existe");
    }

    @Override
    @Transactional
    public Registro update(long id, Registro registroEditado) {

        Optional<Registro> registro = registroDao.findById(id);

        if (registro.isEmpty()) {
            throw new NotFoundException("No hay ningun registro con id " + id);
        }

        registro.get().setFecha(registroEditado.getFecha());
        registro.get().setId_ingreso(registroEditado.getId_ingreso());
        registro.get().setServicio(registroEditado.getServicio());
        registro.get().setCliente(registroEditado.getCliente());

        return registroDao.save(registro.get());
    }

    @Override
    @Transactional
    public Registro save(String cedula, String idServicio) {

        Ingreso ingreso = ingresoDao.getUltimoIngreso(cedula);
        if (ingreso != null) {

            if (ingreso.getFecha_salida() == null) {
                Optional<Cliente> cliente = clienteDao.findByCedula(cedula);
                Optional<Servicio> servicio = servicioDao.findByIdentificador(idServicio);

                if (servicio.isEmpty()) {
                    throw new NotFoundException("El servicio " + idServicio + " no existe");
                }

                if (cliente.isEmpty()) {
                    throw new NotFoundException("El cliente  con cedula " + cedula + " no esta registrado en la base de datos");
                }

                Registro registro = new Registro();
                registro.setId_ingreso(ingreso.getId());
                registro.setFecha(new Date(System.currentTimeMillis()));
                registro.setCliente(cliente.get());
                registro.setServicio(servicio.get());
                return registroDao.save(registro);
            }

            throw new NotFoundException("El cliente con cedula " + cedula + " no ha ingresado");

        }

        throw new NotFoundException("El cliente con cedula " + cedula + " no ha ingresado");
    }

    @Override
    @Transactional
    public Registro delete(long id) {
        Optional<Registro> registro = registroDao.findById(id);

        if (registro.isEmpty()) {
            throw new NotFoundException("No hay ningun registro con id " + id);
        }

        registroDao.deleteById(id);
        return registro.get();
    }


}
