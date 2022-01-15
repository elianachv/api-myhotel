package com.bancoPopular.pruebaTecnica.repository;

import com.bancoPopular.pruebaTecnica.entity.Registro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RegistroRepository extends JpaRepository<Registro, Long> {

    @Query("select registro from Registro registro where registro.cedula = ?1")
    List<Registro> getAllByCedula(String cedula);

    @Query("select registro from Registro registro where registro.cedula = ?1 and registro.id_ingreso = ?2")
    List<Registro> getAllByCedulaIdIngreso(String cedula, long id_ingreso);

    @Query("select registro from Registro registro where registro.servicio = ?1")
    List<Registro> getAllByServicio(String servicio);
}
