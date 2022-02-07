package com.bancoPopular.pruebaTecnica.repository;

import com.bancoPopular.pruebaTecnica.entity.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Long> {

    @Query("select servicio from Servicio servicio where servicio.identificador like %?1")
    Optional<Servicio> findByIdentificador(String identificador);

    @Query("select servicio from Servicio servicio where servicio.tipo like %?1")
    Iterable<Servicio> findAllByTipo(String tipo);

}
