package com.bancoPopular.pruebaTecnica.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Getter
@Setter
@ToString
@Entity
@Table(name = "registros")
public class Registro {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

   /* @Column(name = "id_ingreso")
    private long id_ingreso;*/

    @Column(name = "fecha")
    @NotNull(message = "No puede ser nulo")
    private Date fecha;

    @Column(name = "id_ingreso")
    @NotNull(message = "No puede ser nulo")
    private long id_ingreso;

    /*@Column(name = "cedula")
    private String cedula;*/

    /*@Column(name = "servicio")
    private String servicio;*/

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "cedula", referencedColumnName = "cedula")
    private Cliente cliente;

    /*@ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_ingreso", referencedColumnName = "id")
    private Ingreso ingreso;*/

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "servicio", referencedColumnName = "identificador")
    private Servicio servicio;


    public Registro(Date fecha, long id_ingreso) {
        this.fecha = fecha;
        this.id_ingreso = id_ingreso;
    }


    public Registro() {
    }


}
