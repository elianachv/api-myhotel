package com.bancoPopular.pruebaTecnica.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@ToString
@Entity
@Table(name = "servicios")
public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "identificador")
    @NotEmpty
    @NotNull(message = "No puede ser nulo")
    private String identificador;

    @Column(name = "tipo")
    @NotEmpty
    @NotNull(message = "No puede ser nulo")
    private String tipo;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "precio")
    @Min(value = 1000, message = "El valor mínimo debe ser 1000")
    @NotNull(message = "No puede ser nulo")
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
