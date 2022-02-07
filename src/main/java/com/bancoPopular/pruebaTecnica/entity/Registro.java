package com.bancoPopular.pruebaTecnica.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table(name = "registros")
public class Registro {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "id_ingreso")
    private long id_ingreso;

    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "cedula")
    private String cedula;

    @Column(name = "servicio")
    private String servicio;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "cedula", referencedColumnName = "cedula")
    private Cliente cliente;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_ingreso", referencedColumnName = "id")
    private Ingreso ingreso;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "servicio", referencedColumnName = "nombre")
    private Servicio infoServicio;



    public Registro(long id, long id_ingreso, Date fecha, String cedula, String servicio) {
        this.id = id;
        this.id_ingreso = id_ingreso;
        this.fecha = fecha;
        this.cedula = cedula;
        this.servicio = servicio;
    }

    public Registro() {
    }

    //Cuando se registre la fecha de salida de un cliente se recalcula los costos totales
    /*
    * for each registro que coincide con el cliente
    *   consultar el servivio que tiene
    *   total += servivio.getPrecio()
    *
    * modificar cliente total_consumo = total
    * */
}
