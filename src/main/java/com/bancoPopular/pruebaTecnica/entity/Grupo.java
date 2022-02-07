package com.bancoPopular.pruebaTecnica.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@ToString
@Entity
@Table(name = "grupos")
public class Grupo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "total_integrantes")
    private int total_integrantes;

    /*@OneToMany(mappedBy = "grupo", cascade = {CascadeType.ALL})
    List<Ingreso> ingresos;*/

    public Grupo(long id, int total_integrantes) {
        this.id = id;
        this.total_integrantes = total_integrantes;
    }

    public Grupo() {
    }
}
