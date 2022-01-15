package com.bancoPopular.pruebaTecnica.controller;

import com.bancoPopular.pruebaTecnica.entity.Cliente;
import com.bancoPopular.pruebaTecnica.entity.Cliente;
import com.bancoPopular.pruebaTecnica.entity.Servicio;
import com.bancoPopular.pruebaTecnica.exception.RecursoNoEncontradoException;
import com.bancoPopular.pruebaTecnica.repository.ClienteRepository;
import com.bancoPopular.pruebaTecnica.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping
    public List<Cliente> obtenerClientes() {
        return clienteRepository.findAll();
    }



    @GetMapping("/cc/{cc}")
    public Cliente obtenerClientePorCedula(@PathVariable(value = "cc") String cedula) {

        Cliente cliente = clienteRepository.getByCedula(cedula);

        if (cliente != null) {
            return cliente;
        } else {
            throw new RecursoNoEncontradoException("Cliente con cedula " + cedula + " no está registrado en la base de datos");
        }
    }

    @GetMapping("/id/{id}")
    public Cliente obtenerClientePorId(@PathVariable(value = "id") long id) {
        return clienteRepository.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("Cliente con id " + id + " no está registrado en la base de datos"));
    }

    @PostMapping
    public Cliente crearCliente(@RequestBody Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @PutMapping("/id/{id}")
    public Cliente modificarClientePorId(@PathVariable(value = "id") long id, @RequestBody Cliente clienteEditado) {
        Cliente clienteExistente = clienteRepository.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("Cliente con id " + id + " no está registrado en la base de datos"));
        clienteExistente.setNombre(clienteEditado.getNombre());
        clienteExistente.setCedula(clienteEditado.getCedula());
        clienteExistente.setCorreo(clienteEditado.getCorreo());
        clienteExistente.setTelefono(clienteEditado.getTelefono());
        clienteRepository.save(clienteExistente);
        return clienteExistente;
    }

    @PutMapping("/cc/{cedula}")
    public Cliente modificarClientePorCedula(@PathVariable(value = "cedula") String cedula, @RequestBody Cliente clienteEditado) {
        Cliente clienteExistente = clienteRepository.getByCedula(cedula);

        if (clienteExistente != null) {
            clienteExistente.setNombre(clienteEditado.getNombre());
            clienteExistente.setCedula(clienteEditado.getCedula());
            clienteExistente.setCorreo(clienteEditado.getCorreo());
            clienteExistente.setTelefono(clienteEditado.getTelefono());
            clienteRepository.save(clienteExistente);
            return clienteExistente;
        } else {
            throw new RecursoNoEncontradoException("Cliente con cedula " + cedula + " no está registrado en la base de datos");
        }

    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Cliente> eliminarClientePorId(@PathVariable(value = "id") long id) {
        Cliente clienteExistente = clienteRepository.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("Cliente con id " + id + " no está registrado en la base de datos"));
        clienteRepository.delete(clienteExistente);
        return ResponseEntity.ok().build();

    }

    @DeleteMapping("/identificador/{identificador}")
    public ResponseEntity<Cliente> eliminarClientePorIdentificador(@PathVariable(value = "cedula") String cedula) {

        Cliente clienteExistente = clienteRepository.getByCedula(cedula);

        if (clienteExistente != null) {
            clienteRepository.delete(clienteExistente);
            return ResponseEntity.ok().build();
        } else {
            throw new RecursoNoEncontradoException("Cliente con identificador " + cedula + " no está registrado en la base de datos");
        }


    }

}
