package com.bancoPopular.pruebaTecnica.repository;

import com.bancoPopular.pruebaTecnica.entity.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ServicioRepository extends JpaRepository<Servicio, Long> {

    @Query("select servicio from Servicio servicio where servicio.identificador like %?1")
    Servicio getByIdentificador(String identificador);

    @Query("select servicio from Servicio servicio where servicio.tipo like %?1")
    List<Servicio> getAllByTipo(String tipo);

}
