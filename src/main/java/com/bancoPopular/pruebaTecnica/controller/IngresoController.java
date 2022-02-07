package com.bancoPopular.pruebaTecnica.controller;

import com.bancoPopular.pruebaTecnica.entity.Ingreso;
import com.bancoPopular.pruebaTecnica.exception.NotFoundException;
import com.bancoPopular.pruebaTecnica.repository.IngresoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingresos")
public class IngresoController {

    @Autowired
    private IngresoRepository ingresoRepository;

    @GetMapping
    public List<Ingreso> obtenerIngresos() {
        return ingresoRepository.findAll();
    }

    @GetMapping("/grupo/{grupo}")
    public List<Ingreso> obtenerIngresosPorGrupo(@PathVariable(value = "grupo") long grupo) {
        return ingresoRepository.getAllByGrupo(grupo);
    }

    @GetMapping("/cc/{cc}")
    public List<Ingreso> obtenerIngresosPorCedula(@PathVariable(value = "cc") String cedula) {
        return ingresoRepository.getAllByCedula(cedula);
    }

    @GetMapping("/id/{id}")
    public Ingreso obtenerIngresoPorId(@PathVariable(value = "id") long id) {
        return ingresoRepository.findById(id).orElseThrow(() -> new NotFoundException("Ingreso con id " + id + " no está registrado en la base de datos"));
    }

    @PostMapping
    public Ingreso crearIngreso(@RequestBody Ingreso ingreso) {
        return ingresoRepository.save(ingreso);
    }

    @PutMapping("/id/{id}")
    public Ingreso modificarIngresoPorId(@PathVariable(value = "id") long id, @RequestBody Ingreso ingresoEditado) {
        Ingreso ingresoExistente = ingresoRepository.findById(id).orElseThrow(() -> new NotFoundException("Ingreso con id " + id + " no está registrado en la base de datos"));
        ingresoExistente.setFecha_ingreso(ingresoEditado.getFecha_ingreso());
        ingresoExistente.setFecha_salida(ingresoEditado.getFecha_salida());
        //ingresoExistente.setCedula(ingresoEditado.getCedula());
        //ingresoExistente.setId_grupo(ingresoEditado.getId_grupo());
        ingresoExistente.setTotal_consumo(ingresoEditado.getTotal_consumo());
        ingresoRepository.save(ingresoExistente);
        return ingresoExistente;
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Ingreso> eliminarIngresoPorId(@PathVariable(value = "id") long id) {
        Ingreso ingresoExistente = ingresoRepository.findById(id).orElseThrow(() -> new NotFoundException("Ingreso con id " + id + " no está registrado en la base de datos"));
        ingresoRepository.delete(ingresoExistente);
        return ResponseEntity.ok().build();

    }

    @DeleteMapping("/cc/{cc}")
    public void eliminarIngresosPorCedula(@PathVariable(value = "cedula") String cedula) {
        ingresoRepository.deleteByCedula(cedula);
    }

}
