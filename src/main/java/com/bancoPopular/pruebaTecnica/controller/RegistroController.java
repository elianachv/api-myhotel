package com.bancoPopular.pruebaTecnica.controller;

import com.bancoPopular.pruebaTecnica.entity.Registro;
import com.bancoPopular.pruebaTecnica.exception.InvalidDataException;
import com.bancoPopular.pruebaTecnica.exception.NotFoundException;
import com.bancoPopular.pruebaTecnica.repository.RegistroRepository;
import com.bancoPopular.pruebaTecnica.service.RegistrosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v2/registros")
public class RegistroController {

    @Autowired
    private RegistrosService registrosService;

    @GetMapping
    public ResponseEntity<?> obtenerRegistros() {
        return ResponseEntity.ok().body(registrosService.findAll());
    }

    @GetMapping("/cliente/{cc_cliente}")
    public ResponseEntity<?> obtenerRegistrosPorCliente(@PathVariable(value = "cc_cliente") String cedula) {
        return ResponseEntity.ok().body(registrosService.findAllByCedula(cedula));
    }

    @GetMapping("/servicio/{id_servicio}")
    public ResponseEntity<?> obtenerRegistrosPorServicio(@PathVariable(value = "id_servicio") String servicio) {
        return ResponseEntity.ok().body(registrosService.findAllByServicio(servicio));
    }


    @GetMapping("/id/{id}")
    public ResponseEntity<?> obtenerRegistroPorId(@PathVariable(value = "id") long id) {
        return ResponseEntity.ok().body(registrosService.findById(id));
    }


    @PostMapping
    public ResponseEntity<?> crearRegistro( @RequestParam String cedula, @RequestParam String servicio) {
        return ResponseEntity.status(HttpStatus.CREATED).body(registrosService.save(cedula,servicio));
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> modificarRegistroPorId(@PathVariable(value = "id") long id, @Valid @RequestBody Registro registroEditado, BindingResult result) {

        if(result.hasErrors()){
            throw new InvalidDataException(result);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(registrosService.update(id,registroEditado));

    }


    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> eliminarRegistroPorId(@PathVariable(value = "id") long id) {
        return ResponseEntity.ok().body(registrosService.delete(id));
    }


}

