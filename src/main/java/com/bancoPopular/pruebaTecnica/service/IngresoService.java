package com.bancoPopular.pruebaTecnica.service;

import com.bancoPopular.pruebaTecnica.entity.Ingreso;

import java.util.List;

public interface IngresoService {

    List<Ingreso> getIngresosByCedula(String cedula);

}
