package com.bancoPopular.pruebaTecnica.repository;

import com.bancoPopular.pruebaTecnica.entity.Cliente;
import com.bancoPopular.pruebaTecnica.entity.Ingreso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IngresoRepository extends JpaRepository<Ingreso, Long> {

    @Query("select ingreso from Ingreso ingreso where ingreso.cedula = ?1")
    List<Ingreso> getAllByCedula(String cedula);

    @Query("select ingreso from Ingreso ingreso where ingreso.id_grupo = ?1")
    List<Ingreso> getAllByGrupo(long grupo);

    @Modifying
    @Transactional
    @Query("delete from Ingreso ingreso where ingreso.cedula = ?1")
    void deleteByCedula(String cedula);
}
