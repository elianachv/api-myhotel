package com.bancoPopular.pruebaTecnica.controller;

import com.bancoPopular.pruebaTecnica.entity.Servicio;
import com.bancoPopular.pruebaTecnica.exception.InvalidDataException;
import com.bancoPopular.pruebaTecnica.repository.ServicioRepository;
import com.bancoPopular.pruebaTecnica.service.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/v2/servicios")
public class ServicioController {

    @Autowired
    private ServicioService servicioService;

    @GetMapping
    public ResponseEntity<?> obtenerServicios() {
        List<Servicio> servicios = StreamSupport.stream(servicioService.findAll().spliterator(), false).collect(Collectors.toList());
        return ResponseEntity.ok().body(servicios);
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<?> obtenerServiciosPorTipo(@PathVariable(value = "tipo") String tipo) {
        List<Servicio> servicios = StreamSupport.stream(servicioService.findAllByTipo(tipo).spliterator(), false).collect(Collectors.toList());
        return ResponseEntity.ok().body(servicios);
    }

    @GetMapping("/identificador/{identificador}")
    public ResponseEntity<?> obtenerServicioPorIdentificador(@PathVariable(value = "identificador") String identificador) {
        return ResponseEntity.ok().body(servicioService.findByIdentificador(identificador));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> obtenerServicioPorId(@PathVariable(value = "id") long id) {
        return ResponseEntity.ok().body(servicioService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> crearServicio(@Valid @RequestBody Servicio servicio, BindingResult result) {
        if (result.hasErrors()) {
            throw new InvalidDataException(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(servicioService.save(servicio));
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> modificarServicioPorId(@PathVariable(value = "id") long id, @Valid @RequestBody Servicio servicioEditado, BindingResult result) {
        if (result.hasErrors()) {
            throw new InvalidDataException(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(servicioService.update(id, servicioEditado));
    }

    @PutMapping("/identificador/{identificador}")
    public ResponseEntity<?> modificarServicioPorIdentificador(@PathVariable(value = "identificador") String identificador, @Valid @RequestBody Servicio servicioEditado, BindingResult result) {
        if (result.hasErrors()) {
            throw new InvalidDataException(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(servicioService.update(identificador, servicioEditado));

    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Servicio> eliminarServicioPorId(@PathVariable(value = "id") long id) {
        return ResponseEntity.ok().body(servicioService.delete(id));
    }

    @DeleteMapping("/identificador/{identificador}")
    public ResponseEntity<Servicio> eliminarServicioPorIdentificador(@PathVariable(value = "identificador") String identificador) {
        return ResponseEntity.ok().body(servicioService.delete(identificador));
    }


}
