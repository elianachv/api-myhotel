package com.bancoPopular.pruebaTecnica.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@ToString
@Entity
@Table(name = "ingresos")
public class Ingreso {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /*@Column(name = "id_grupo")
    private long id_grupo;*/

    /*@Column(name = "cedula")
    private String cedula;*/

    @Column(name = "fecha_ingreso")
    private Date fecha_ingreso;

    @Column(name = "fecha_salida")
    private Date fecha_salida;

    @Column(name = "total_consumo")
    private long total_consumo;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "cedula", referencedColumnName = "cedula")
    private Cliente cliente;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_grupo", referencedColumnName = "id")
    private Grupo grupo;

    @OneToMany(mappedBy = "ingreso", cascade = {CascadeType.ALL})
    List<Registro> registros;


    public Ingreso(long id, long id_grupo, String cedula, Date fecha_ingreso, Date fecha_salida, long total_consumo) {
        this.id = id;
        //this.id_grupo = id_grupo;
        //this.cedula = cedula;
        this.fecha_ingreso = fecha_ingreso;
        this.fecha_salida = fecha_salida;
        this.total_consumo = total_consumo;
    }

    public Ingreso() {
    }
}
