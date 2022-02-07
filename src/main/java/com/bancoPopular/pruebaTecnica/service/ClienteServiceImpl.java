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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    ClienteRepository clienteDao;

    @Autowired
    IngresoRepository ingresoDao;

    @Autowired
    RegistroRepository registroDao;

    @Override
    @Transactional(readOnly = true)
    public List<?> findAll() {
        return clienteDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cliente> findByCedula(String cedula) {

        Optional<Cliente> cliente = clienteDao.findByCedula(cedula);
        if (cliente.isEmpty()) {
            throw new NotFoundException("El cliente con cedula " + cedula + " no se encuentra registrado en la base de datos");
        }

        return cliente;

    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cliente> findById(long id) {

        Optional<Cliente> cliente = clienteDao.findById(id);
        if (cliente.isEmpty()) {
            throw new NotFoundException("El cliente con id " + id + " no se encuentra registrado en la base de datos");
        }

        return cliente;

    }

    @Override
    @Transactional
    public Cliente save(Cliente cliente) {
        Optional<Cliente> cliente1 = clienteDao.findById(cliente.getId());
        Optional<Cliente> cliente2 = clienteDao.findByCedula(cliente.getCedula());


        if (cliente1.isEmpty() && cliente2.isEmpty()) {
            return clienteDao.save(cliente);
        }

        throw new DuplicatedException("El cliente que intenta crear ya existe");
    }

    @Override
    @Transactional
    public Cliente update(long id, Cliente clienteEditado) {
        Optional<Cliente> clienteExistente = clienteDao.findById(id);

        if (clienteExistente.isEmpty()) {
            throw new NotFoundException("No existe ningún cliente registrado con el id " + id);
        }

        //BeanUtils.copyProperties(servicioEditado,servicioExistente.get());
        clienteExistente.get().setCedula(clienteEditado.getCedula());
        clienteExistente.get().setNombre(clienteEditado.getNombre());
        clienteExistente.get().setTelefono(clienteEditado.getTelefono());
        clienteExistente.get().setCorreo(clienteEditado.getCorreo());

        return clienteDao.save(clienteExistente.get());
    }

    @Override
    @Transactional
    public Cliente update(String cedula, Cliente clienteEditado) {
        Optional<Cliente> clienteExistente = clienteDao.findByCedula(cedula);

        if (clienteExistente.isEmpty()) {
            throw new NotFoundException("No existe ningún cliente registrado con cedula " + cedula);
        }

        //BeanUtils.copyProperties(servicioEditado,servicioExistente.get());
        clienteExistente.get().setCedula(clienteEditado.getCedula());
        clienteExistente.get().setNombre(clienteEditado.getNombre());
        clienteExistente.get().setTelefono(clienteEditado.getTelefono());
        clienteExistente.get().setCorreo(clienteEditado.getCorreo());

        return clienteDao.save(clienteExistente.get());
    }

    @Override
    @Transactional
    public Cliente delete(long id) {
        Optional<Cliente> clienteExistente = clienteDao.findById(id);

        if (clienteExistente.isEmpty()) {
            throw new NotFoundException("No existe ningún cliente registrado con id " + id);
        }

        clienteDao.deleteById(id);
        return clienteExistente.get();

    }

    @Override
    @Transactional
    public Cliente delete(String cedula) {
        Optional<Cliente> clienteExistente = clienteDao.findByCedula(cedula);

        if (clienteExistente.isEmpty()) {
            throw new NotFoundException("No existe ningún cliente registrado con cedula " + cedula);
        }

        clienteDao.delete(clienteExistente.get());
        return clienteExistente.get();
    }

    @Override
    @Transactional
    public Map<String, Object> checkoutById(long idCliente) {
        Optional<Cliente> cliente = clienteDao.findById(idCliente);
        if (cliente.isEmpty()) {
            throw new NotFoundException("No existe ningun cliente registrado con id " + idCliente);
        }
        return calcularCuenta(cliente.get());
    }

    @Override
    @Transactional
    public Map<String, Object> checkoutByCedula(String cedula) {
        Optional<Cliente> cliente = clienteDao.findByCedula(cedula);
        if (cliente.isEmpty()) {
            throw new NotFoundException("No existe ningun cliente registrado con cedula " + cedula);
        }
        return calcularCuenta(cliente.get());
    }

    public Map<String, Object> calcularCuenta(Cliente cliente) {

        Ingreso ingreso = ingresoDao.getUltimoIngreso(cliente.getCedula());
        if (ingreso == null) {
            throw new NotFoundException("El cliente identificado con cedula " + cliente.getCedula() + " no ha ingresado");
        }


        //List<Registro> registros = cliente.getRegistros(ingreso.getId());
        List<Registro> registros = registroDao.getAllByCedulaIdIngreso(cliente.getCedula(), ingreso.getId());
        long total = 0;
        for (Registro resgistro : registros) {
            total += resgistro.getInfoServicio().getPrecio();
        }

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("mensaje", "El cliente identificado con CC " + cliente.getCedula() + " debe pagar " + total);
        resultado.put("total", total);
        ingreso.setTotal_consumo(total);
        ingresoDao.save(ingreso);
        return resultado;

    }


}
