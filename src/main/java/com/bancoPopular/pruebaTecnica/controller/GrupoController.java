package com.bancoPopular.pruebaTecnica.controller;

import com.bancoPopular.pruebaTecnica.entity.Grupo;
import com.bancoPopular.pruebaTecnica.exception.InvalidDataException;
import com.bancoPopular.pruebaTecnica.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v2/grupos")
public class GrupoController {

    @Autowired
    private GrupoService grupoService;

    @GetMapping
    public ResponseEntity<?> obtenerGrupos() {
        return ResponseEntity.ok().body(grupoService.findAll());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> obtenerGrupoPorId(@PathVariable(value = "id") long id) {
        return ResponseEntity.ok().body(grupoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> crearGrupo(@Valid @RequestBody Grupo grupo, BindingResult result) {
        if (result.hasErrors()) {
            throw new InvalidDataException(result);
        }
        return ResponseEntity.ok().body(grupoService.save(grupo));
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> modificarGrupoPorId(@PathVariable(value = "id") long id, @Valid @RequestBody Grupo grupoEditado, BindingResult result) {
        if (result.hasErrors()) {
            throw new InvalidDataException(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(grupoService.update(id, grupoEditado));
    }


    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> eliminarGrupoPorId(@PathVariable(value = "id") long id) {
        return ResponseEntity.ok().body(grupoService.delete(id));
    }


}
