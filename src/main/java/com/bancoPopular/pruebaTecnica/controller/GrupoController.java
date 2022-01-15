package com.bancoPopular.pruebaTecnica.controller;

import com.bancoPopular.pruebaTecnica.entity.Grupo;
import com.bancoPopular.pruebaTecnica.exception.RecursoNoEncontradoException;
import com.bancoPopular.pruebaTecnica.repository.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("ap/grupos")
public class GrupoController {
    @Autowired
    private GrupoRepository grupoRepository;

    @GetMapping
    public List<Grupo> obtenerGrupos() {
        return grupoRepository.findAll();
    }


    @GetMapping("/id/{id}")
    public Grupo obtenerGrupoPorId(@PathVariable(value = "id") long id) {
        return grupoRepository.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("Grupo con id " + id + " no está registrado en la base de datos"));
    }

    @PostMapping
    public Grupo crearGrupo(@RequestBody Grupo grupo) {
        return grupoRepository.save(grupo);
    }

    @PutMapping("/id/{id}")
    public Grupo modificarGrupoPorId(@PathVariable(value = "id") long id, @RequestBody Grupo grupoEditado) {
        Grupo grupoExistente = grupoRepository.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("Grupo con id " + id + " no está registrado en la base de datos"));
        grupoExistente.setTotal_integrantes(grupoEditado.getTotal_integrantes());
        grupoRepository.save(grupoExistente);
        return grupoExistente;
    }


    @DeleteMapping("/id/{id}")
    public ResponseEntity<Grupo> eliminarGrupoPorId(@PathVariable(value = "id") long id) {
        Grupo grupoExistente = grupoRepository.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("Grupo con id " + id + " no está registrado en la base de datos"));
        grupoRepository.delete(grupoExistente);
        return ResponseEntity.ok().build();

    }


}
