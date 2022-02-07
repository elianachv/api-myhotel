package com.bancoPopular.pruebaTecnica.service;

import com.bancoPopular.pruebaTecnica.entity.Grupo;

import java.util.List;
import java.util.Map;

public interface GrupoService {

    List<?> findAll();

    Map<String, Object> findById(long id);

    Grupo save(Grupo grupo);

    Grupo update(long id, Grupo grupoEditado);

    Grupo delete(long id);
}
