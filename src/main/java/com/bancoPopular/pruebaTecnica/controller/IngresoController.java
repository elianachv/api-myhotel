package com.bancoPopular.pruebaTecnica.controller;

import com.bancoPopular.pruebaTecnica.entity.Ingreso;
import com.bancoPopular.pruebaTecnica.exception.InvalidDataException;
import com.bancoPopular.pruebaTecnica.service.IngresoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v2/ingresos")
public class IngresoController {


    @Autowired
    private IngresoService ingresoService;

    @GetMapping
    public ResponseEntity<?> obtenerIngresos() {

        return ResponseEntity.ok().body(ingresoService.findAll());
    }

    @GetMapping("/grupo/{grupo}")
    public ResponseEntity<?> obtenerIngresosPorGrupo(@PathVariable(value = "grupo") long grupo) {
        return ResponseEntity.ok().body(ingresoService.findAllByGroup(grupo));
    }

    @GetMapping("/cc/{cc}")
    public ResponseEntity<?> obtenerIngresosPorCedula(@PathVariable(value = "cc") String cedula) {
        return ResponseEntity.ok().body(ingresoService.getIngresosByCedula(cedula));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> obtenerIngresoPorId(@PathVariable(value = "id") long id) {
        return ResponseEntity.ok().body(ingresoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> crearIngreso(@Valid @RequestBody Ingreso ingreso, BindingResult result) {

        if (result.hasErrors()) {
            throw new InvalidDataException(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(ingresoService.save(ingreso));
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> modificarIngresoPorId(@PathVariable(value = "id") long id, @Valid @RequestBody Ingreso ingresoEditado, BindingResult result) {
        if (result.hasErrors()) {
            throw new InvalidDataException(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(ingresoService.update(id, ingresoEditado));
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Ingreso> eliminarIngresoPorId(@PathVariable(value = "id") long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ingresoService.delete(id));

    }

    @DeleteMapping("/cc/{cc}")
    public ResponseEntity<Ingreso> eliminarIngresosPorCedula(@PathVariable(value = "cedula") String cedula) {
        ingresoService.deleteByCedula(cedula);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
