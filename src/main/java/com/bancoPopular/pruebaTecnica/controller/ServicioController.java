package com.bancoPopular.pruebaTecnica.controller;

import com.bancoPopular.pruebaTecnica.entity.Servicio;
import com.bancoPopular.pruebaTecnica.exception.RecursoNoEncontradoException;
import com.bancoPopular.pruebaTecnica.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicios")
public class ServicioController {

    @Autowired
    private ServicioRepository servicioRepository;

    @GetMapping
    public List<Servicio> obtenerServicios() {
        return servicioRepository.findAll();
    }

    @GetMapping("/tipo/{tipo}")
    public List<Servicio> obtenerServiciosPorTipo(@PathVariable(value = "tipo") String tipo) {
        return servicioRepository.getAllByTipo(tipo);
    }

    @GetMapping("/identificador/{identificador}")
    public Servicio obtenerServicioPorIdentificador(@PathVariable(value = "identificador") String identificador) {

        Servicio servicio = servicioRepository.getByIdentificador(identificador);

        if (servicio != null) {
            return servicio;
        } else {
            throw new RecursoNoEncontradoException("Servicio con identificador " + identificador + " no está registrado en la base de datos");
        }
    }

    @GetMapping("/id/{id}")
    public Servicio obtenerServicioPorId(@PathVariable(value = "id") long id) {
        return servicioRepository.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("Servicio con id " + id + " no está registrado en la base de datos"));
    }

    @PostMapping
    public Servicio crearServicio(@RequestBody Servicio servicio) {
        return servicioRepository.save(servicio);
    }

    @PutMapping("/id/{id}")
    public Servicio modificarServicioPorId(@PathVariable(value = "id") long id, @RequestBody Servicio servicioEditado) {
        Servicio servicioExistente = servicioRepository.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("Servicio con id " + id + " no está registrado en la base de datos"));
        servicioExistente.setTipo(servicioEditado.getTipo());
        servicioExistente.setIdentificador(servicioEditado.getIdentificador());
        servicioExistente.setDescripcion(servicioEditado.getDescripcion());
        servicioExistente.setPrecio(servicioEditado.getPrecio());
        servicioRepository.save(servicioExistente);
        return servicioExistente;
    }

    @PutMapping("/identificador/{identificador}")
    public Servicio modificarServicioPorIdentificador(@PathVariable(value = "identificador") String identificador, @RequestBody Servicio servicioEditado) {
        Servicio servicioExistente = servicioRepository.getByIdentificador(identificador);

        if (servicioExistente != null) {
            servicioExistente.setTipo(servicioEditado.getTipo());
            servicioExistente.setIdentificador(servicioEditado.getIdentificador());
            servicioExistente.setDescripcion(servicioEditado.getDescripcion());
            servicioExistente.setPrecio(servicioEditado.getPrecio());
            servicioRepository.save(servicioExistente);
            return servicioRepository.save(servicioExistente);

        } else {
            throw new RecursoNoEncontradoException("Servicio con identificador " + identificador + " no está registrado en la base de datos");
        }

    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Servicio> eliminarServicioPorId(@PathVariable(value = "id") long id) {
        Servicio servicioExistente = servicioRepository.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("Servicio con id " + id + " no está registrado en la base de datos"));
        servicioRepository.delete(servicioExistente);
        return ResponseEntity.ok().build();

    }

    @DeleteMapping("/identificador/{identificador}")
    public ResponseEntity<Servicio> eliminarServicioPorIdentificador(@PathVariable(value = "identificador") String identificador) {

        Servicio servicioExistente = servicioRepository.getByIdentificador(identificador);

        if (servicioExistente != null) {
            servicioRepository.delete(servicioExistente);
            return ResponseEntity.ok().build();
        } else {
            throw new RecursoNoEncontradoException("Servicio con identificador " + identificador + " no está registrado en la base de datos");
        }


    }


}
