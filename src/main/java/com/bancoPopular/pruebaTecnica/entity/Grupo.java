package com.bancoPopular.pruebaTecnica.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
