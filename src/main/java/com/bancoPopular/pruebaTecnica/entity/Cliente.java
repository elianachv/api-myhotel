package com.bancoPopular.pruebaTecnica.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "clientes")
public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "cedula")
    @NotEmpty
    @NotNull(message = "No puede ser nula")
    private String cedula;

    @Column(name = "nombre")
    @NotEmpty
    @NotNull(message = "No puede ser nulo")
    private String nombre;

    @Column(name = "correo")
    @NotEmpty
    @NotNull(message = "No puede ser nulo")
    @Size(min = 10, message = "No es lo suficientemente largo")
    private String correo;

    @Column(name = "telefono")
    @NotEmpty
    @NotNull(message = "No puede ser nulo")
    @Size(min = 10, message = "No es lo suficientemente largo")
    private String telefono;

    /*@OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente", cascade = {CascadeType.ALL})
    @ToString.Exclude
    List<Ingreso> ingresos;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente", cascade = {CascadeType.ALL})
    @ToString.Exclude
    List<Registro> registros;

    public List<Registro> getRegistros(long idIngreso) {
        List<Registro> registros = new ArrayList<>();
        for (Registro registro : this.registros) {
            if (registro.getIngreso().getId() == idIngreso) {
                registros.add(registro);
            }
        }
        return registros;
    }*/

    public Cliente(long id, String cedula, String nombre, String correo, String telefono, List<Ingreso> ingresos, List<Registro> registros) {
        this.id = id;
        this.cedula = cedula;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;

    }

    public Cliente(long id, String cedula, String nombre, String correo, String telefono) {
        this.id = id;
        this.cedula = cedula;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
    }

    public Cliente() {
    }
}
