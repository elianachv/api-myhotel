package com.bancoPopular.pruebaTecnica.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "servicios")
public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "identificador")
    private String identificador;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "precio")
    private long precio;

    public Servicio() {
    }

    public Servicio(long id, String identificador, String descripcion, long precio, String tipo) {
        this.id = id;
        this.identificador = identificador;
        this.descripcion = descripcion;
        this.precio = precio;
        this.tipo = tipo;
    }
}
