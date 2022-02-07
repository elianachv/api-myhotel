package com.bancoPopular.pruebaTecnica.repository;

import com.bancoPopular.pruebaTecnica.entity.Registro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistroRepository extends JpaRepository<Registro, Long> {

    @Query("select registro from Registro registro where registro.cliente.cedula = ?1")
    List<Registro> findtAllByCedula(String cedula);

    @Query("select registro from Registro registro where registro.cliente.cedula = ?1 and registro.id_ingreso = ?2")
    List<Registro> findAllByCedulaIdIngreso(String cedula, long id_ingreso);

    @Query("select registro from Registro registro where registro.servicio.identificador = ?1")
    List<Registro> findAllByServicio(String servicio);
}
