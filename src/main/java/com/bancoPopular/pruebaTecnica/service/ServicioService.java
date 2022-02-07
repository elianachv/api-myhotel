package com.bancoPopular.pruebaTecnica.service;

import com.bancoPopular.pruebaTecnica.entity.Servicio;

import java.util.Optional;

public interface ServicioService {

    Iterable<Servicio> findAll();

    Iterable<Servicio> findAllByTipo(String tipo);

    Optional<Servicio> findByIdentificador(String identificador);

    Optional<Servicio> findById(long id);

    Servicio save(Servicio servicio);

    Servicio update(long id, Servicio servicio);

    Servicio update(String identificador, Servicio servicio);

    Servicio delete(long id);

    Servicio delete(String identificador);

}
