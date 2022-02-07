package com.bancoPopular.pruebaTecnica.controller;

import com.bancoPopular.pruebaTecnica.entity.*;
import com.bancoPopular.pruebaTecnica.entity.Cliente;
import com.bancoPopular.pruebaTecnica.exception.InvalidDataException;
import com.bancoPopular.pruebaTecnica.exception.NotFoundException;
import com.bancoPopular.pruebaTecnica.repository.*;
import com.bancoPopular.pruebaTecnica.repository.ClienteRepository;
import com.bancoPopular.pruebaTecnica.service.ClienteService;
import com.bancoPopular.pruebaTecnica.service.IngresoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("api/v2/clientes")
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    @Autowired
    IngresoService ingresoService;

    @GetMapping
    public ResponseEntity<?> listarClientes() {
        System.out.println("LISTAR TODOS LOS CLIENTES REGISTRADOS");
        //List<Cliente> clienteList = StreamSupport.stream(clienteService.findAll().spliterator(), false).collect(Collectors.toList());
        return ResponseEntity.ok().body(clienteService.findAll());
        //return ResponseEntity.status(HttpStatus.ACCEPTED).body(clienteService.findAll());
    }

    @GetMapping("/cc/{cc}")
    public ResponseEntity<?> obtenerClientePorCedula(@PathVariable(value = "cc") String cedula) {

        Optional<Cliente> cliente = clienteService.findByCedula(cedula);

        if (cliente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(cliente.get());
    }

    @GetMapping("/cc/{cc}/ingresos")
    public ResponseEntity<?> obtenerIngresos(@PathVariable(value = "cc") String cedula) {
        return ResponseEntity.ok().body(ingresoService.getIngresosByCedula(cedula));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> obtenerClientePorId(@PathVariable(value = "id") long id) {
        Optional<Cliente> cliente = clienteService.findById(id);

        if (cliente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(cliente.get());
    }

    @GetMapping("/checkout/id/{id}")
    public ResponseEntity<?> checkout(@PathVariable(value = "id") long id) {
        return ResponseEntity.ok().body(clienteService.checkoutById(id));
    }

    @GetMapping("/checkout/cc/{cedula}")
    public ResponseEntity<?> checkout(@PathVariable(value = "cedula") String cedula) {
        return ResponseEntity.ok().body(clienteService.checkoutByCedula(cedula));
    }

    @PostMapping
    public ResponseEntity<?> crearCliente(@Valid @RequestBody Cliente cliente, BindingResult result) {
        if (result.hasErrors()) {
            throw new InvalidDataException(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.save(cliente));
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> modificarClientePorId(@PathVariable(value = "id") long id, @Valid @RequestBody Cliente clienteEditado, BindingResult result) {
        if (result.hasErrors()) {
            throw new InvalidDataException(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.update(id, clienteEditado));
    }

    @PutMapping("/cc/{cedula}")
    public ResponseEntity<?> modificarClientePorCedula(@PathVariable(value = "cedula") String cedula, @Valid @RequestBody Cliente clienteEditado, BindingResult result) {
        if (result.hasErrors()) {
            throw new InvalidDataException(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.update(cedula, clienteEditado));

    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> eliminarClientePorId(@PathVariable(value = "id") long id) {
        return ResponseEntity.ok().body(clienteService.delete(id));
    }

    @DeleteMapping("/cc/{cedula}")
    public ResponseEntity<Cliente> eliminarClientePorIdentificador(@PathVariable(value = "cedula") String cedula) {
        return ResponseEntity.ok().body(clienteService.delete(cedula));
    }


}
