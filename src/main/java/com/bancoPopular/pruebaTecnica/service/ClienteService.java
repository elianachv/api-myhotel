package com.bancoPopular.pruebaTecnica.service;

import com.bancoPopular.pruebaTecnica.entity.Cliente;

import java.util.Optional;

public interface ClienteService {

    Iterable<Cliente> findAll();

    Optional<Cliente> findByCedula(String cedula);

    Optional<Cliente> findById(long id);

}
