package com.bancoPopular.pruebaTecnica.service;

import com.bancoPopular.pruebaTecnica.entity.Cliente;
import com.bancoPopular.pruebaTecnica.entity.Ingreso;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ClienteService {

    List<?> findAll();

    Optional<Cliente> findByCedula(String cedula);

    Optional<Cliente> findById(long id);

    Cliente save(Cliente cliente);

    Cliente update(long id, Cliente cliente);

    Cliente update(String cedula, Cliente cliente);

    Cliente delete(long id);

    Cliente delete(String cedula);

    Map<String,Object> checkoutById(long idCliente);

    Map<String,Object> checkoutByCedula(String cedula);

}
