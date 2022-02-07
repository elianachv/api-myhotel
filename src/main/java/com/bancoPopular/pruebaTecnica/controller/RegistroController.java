package com.bancoPopular.pruebaTecnica.controller;

import com.bancoPopular.pruebaTecnica.entity.Registro;
import com.bancoPopular.pruebaTecnica.exception.NotFoundException;
import com.bancoPopular.pruebaTecnica.repository.RegistroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/registros")
public class RegistroController {
    @Autowired
    private RegistroRepository registroRepository;

    @GetMapping
    public List<Registro> obtenerRegistros() {
        return registroRepository.findAll();
    }

    @GetMapping("/cliente/{cc_cliente}")
    public List<Registro> obtenerRegistrosPorCliente(@PathVariable(value = "cc_cliente") String cedula) {
        return registroRepository.getAllByCedula(cedula);
    }

    @GetMapping("/servicio/{id_servicio}")
    public List<Registro> obtenerRegistrosPorServicio(@PathVariable(value = "id_servicio") String servicio) {
        return registroRepository.getAllByServicio(servicio);
    }


    @GetMapping("/id/{id}")
    public Registro obtenerRegistroPorId(@PathVariable(value = "id") long id) {
        return registroRepository.findById(id).orElseThrow(() -> new NotFoundException("Registro con id " + id + " no está registrado en la base de datos"));
    }

    @PostMapping
    public Registro crearRegistro(@RequestBody Registro registro) {
        return registroRepository.save(registro);
    }

    @PutMapping("/id/{id}")
    public Registro modificarRegistroPorId(@PathVariable(value = "id") long id, @RequestBody Registro registroEditado) {
        Registro registroExistente = registroRepository.findById(id).orElseThrow(() -> new NotFoundException("Registro con id " + id + " no está registrado en la base de datos"));
        //registroExistente.setCedula(registroEditado.getCedula());
        registroExistente.setFecha(registroEditado.getFecha());
        //registroExistente.setServicio(registroEditado.getServicio());
        registroRepository.save(registroExistente);
        return registroExistente;
    }


    @DeleteMapping("/id/{id}")
    public ResponseEntity<Registro> eliminarRegistroPorId(@PathVariable(value = "id") long id) {
        Registro registroExistente = registroRepository.findById(id).orElseThrow(() -> new NotFoundException("Registro con id " + id + " no está registrado en la base de datos"));
        registroRepository.delete(registroExistente);
        return ResponseEntity.ok().build();

    }


}

