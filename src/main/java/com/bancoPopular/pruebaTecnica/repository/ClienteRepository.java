package com.bancoPopular.pruebaTecnica.repository;

import com.bancoPopular.pruebaTecnica.entity.Cliente;
import com.bancoPopular.pruebaTecnica.entity.Ingreso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("select cliente from Cliente cliente where cliente.cedula like %?1")
    Optional<Cliente> findByCedula(String cedula);




}
