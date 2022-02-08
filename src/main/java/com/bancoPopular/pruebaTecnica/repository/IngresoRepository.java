package com.bancoPopular.pruebaTecnica.repository;

import com.bancoPopular.pruebaTecnica.entity.Ingreso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface IngresoRepository extends JpaRepository<Ingreso, Long> {

    @Query("select ingreso from Ingreso ingreso where ingreso.cliente.cedula = ?1")
    List<Ingreso> findAllByCedula(String cedula);

    @Query("select ingreso from Ingreso ingreso where ingreso.id_grupo = ?1")
    List<Ingreso> findAllByGrupo(long grupo);

    @Query(value = "SELECT * FROM ingresos  WHERE cedula = ?1 ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Ingreso getUltimoIngreso(String cedula);

    @Modifying
    @Transactional
    @Query("delete from Ingreso ingreso where ingreso.cliente.cedula = ?1")
    void deleteByCedula(String cedula);
}
