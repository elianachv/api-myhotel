package com.bancoPopular.pruebaTecnica.service;

import com.bancoPopular.pruebaTecnica.entity.Cliente;
import com.bancoPopular.pruebaTecnica.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    ClienteRepository clienteDao;

    @Override
    public Iterable<Cliente> findAll() {
        return clienteDao.findAll();
    }


    @Override
    public Optional<Cliente> findByCedula(String cedula) {

        Optional<Cliente> cliente = clienteDao.findByCedula(cedula);
        if (cliente.isEmpty()) {
            //Desencadenar exception
        }

        return cliente;

    }

    @Override
    public Optional<Cliente> findById(long id) {

        Optional<Cliente> cliente = clienteDao.findById(id);
        if (cliente.isEmpty()) {
            //Desencadenar exception
        }

        return cliente;

    }
}
