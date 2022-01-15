package com.bancoPopular.pruebaTecnica.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "grupos")
public class Grupo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "total_integrantes")
    private int total_integrantes;

    public Grupo(long id, int total_integrantes) {
        this.id = id;
        this.total_integrantes = total_integrantes;
    }

    public Grupo() {
    }
}
