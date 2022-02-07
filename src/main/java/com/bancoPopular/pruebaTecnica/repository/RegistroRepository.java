package com.bancoPopular.pruebaTecnica.repository;

import com.bancoPopular.pruebaTecnica.entity.Registro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistroRepository extends JpaRepository<Registro, Long> {

    @Query("select registro from Registro registro where registro.cliente.cedula = ?1")
    List<Registro> getAllByCedula(String cedula);

    @Query("select registro from Registro registro where registro.cliente.cedula = ?1 and registro.ingreso.id = ?2")
    List<Registro> getAllByCedulaIdIngreso(String cedula, long id_ingreso);

    @Query("select registro from Registro registro where registro.infoServicio.identificador = ?1")
    List<Registro> getAllByServicio(String servicio);
}
