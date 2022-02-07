package com.bancoPopular.pruebaTecnica.service;

import com.bancoPopular.pruebaTecnica.entity.Registro;

public interface RegistrosService {

    Iterable<Registro> findAll();

    Iterable<Registro> findAllByCedula(String cedula);

    Iterable<Registro> findAllByServicio(String servicio);

    Registro findById(long id);

    Registro save(Registro registro);

    Registro save(Registro registro, String cedula, String idServicio);

    Registro save( String cedula, String servicio);

    Registro update(long id, Registro registro);

    Registro delete(long id);


}
