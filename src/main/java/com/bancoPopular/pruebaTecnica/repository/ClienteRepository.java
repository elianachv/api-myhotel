package com.bancoPopular.pruebaTecnica.repository;

import com.bancoPopular.pruebaTecnica.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("select cliente from Cliente cliente where cliente.cedula like %?1")
    Cliente getByCedula(String cedula);



}
