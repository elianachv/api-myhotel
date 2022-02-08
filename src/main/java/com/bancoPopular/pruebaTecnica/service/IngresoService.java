package com.bancoPopular.pruebaTecnica.service;

import com.bancoPopular.pruebaTecnica.entity.Ingreso;

import java.util.List;
import java.util.Optional;

public interface IngresoService {


    Iterable<Ingreso> findAll();

    Iterable<Ingreso> findAllByGroup(long grupo);

    Iterable<Ingreso> getIngresosByCedula(String cedula);

    Optional<Ingreso> findById(long id);

    Ingreso save(Ingreso ingreso);

    Ingreso update(long id, Ingreso ingreso);

    Ingreso delete(long id);
    void deleteByCedula(String cedula);

}


