package com.bancoPopular.pruebaTecnica.controller;

import com.bancoPopular.pruebaTecnica.entity.*;
import com.bancoPopular.pruebaTecnica.entity.Cliente;
import com.bancoPopular.pruebaTecnica.exception.RecursoNoEncontradoException;
import com.bancoPopular.pruebaTecnica.repository.*;
import com.bancoPopular.pruebaTecnica.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private RegistroRepository registroRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private IngresoRepository ingresoRepository;

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

    @GetMapping("/checkout/id/{id}")
    public Map<String, Object>  checkout(@PathVariable(value = "id") long id) {
        return this.calcularTotal(id);
    }

    @GetMapping("/checkout/cc/{cedula}")
    public Map<String, Object>  checkout(@PathVariable(value = "cedula") String cedula) {
        return this.calcularTotal(cedula);
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

    @DeleteMapping("/cc/{cedula}")
    public ResponseEntity<Cliente> eliminarClientePorIdentificador(@PathVariable(value = "cedula") String cedula) {

        Cliente clienteExistente = clienteRepository.getByCedula(cedula);

        if (clienteExistente != null) {
            clienteRepository.delete(clienteExistente);
            return ResponseEntity.ok().build();
        } else {
            throw new RecursoNoEncontradoException("Cliente con identificador " + cedula + " no está registrado en la base de datos");
        }


    }

    public Map<String, Object> calcularTotal(long id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("Cliente con id " + id + " no está registrado en la base de datos"));
        Ingreso ingreso = ingresoRepository.getUltimoIngreso(cliente.getCedula());
        if (ingreso == null) {
            throw new RecursoNoEncontradoException("El cliente identificado con " + cliente.getCedula() + " no ha ingresado");
        } else {
            List<Registro> registros = registroRepository.getAllByCedulaIdIngreso(cliente.getCedula(), ingreso.getId());
            long total = 0;
            for (Registro resgistro : registros) {
                Servicio s = servicioRepository.getByIdentificador(resgistro.getServicio());
                total += s.getPrecio();
            }

            Map<String, Object> resultado = new HashMap<>();
            resultado.put("mensaje", "El cliente identificado con CC " + cliente.getCedula() + " debe pagar " + total);
            resultado.put("total", total);
            ingreso.setTotal_consumo(total);
            ingresoRepository.save(ingreso);
            return resultado;
        }

    }

    public Map<String, Object> calcularTotal(String cedula) {
        Cliente cliente = clienteRepository.getByCedula(cedula);

        if (cliente != null) {
            Ingreso ingreso = ingresoRepository.getUltimoIngreso(cedula);
            if (ingreso == null) {
                throw new RecursoNoEncontradoException("El cliente identificado con " + cedula + " no ha ingresado");
            } else {
                List<Registro> registros = registroRepository.getAllByCedulaIdIngreso(cliente.getCedula(), ingreso.getId());
                long total = 0;
                for (Registro resgistro : registros) {
                    Servicio s = servicioRepository.getByIdentificador(resgistro.getServicio());
                    total += s.getPrecio();
                }
                Map<String, Object> resultado = new HashMap<>();
                resultado.put("mensaje", "El cliente identificado con CC " + cliente.getCedula() + " debe pagar " + total);
                resultado.put("total", total);
                ingreso.setTotal_consumo(total);
                ingresoRepository.save(ingreso);
                return resultado;
            }


        } else {
            throw new RecursoNoEncontradoException("Cliente con cedula " + cedula + " no está registrado en la base de datos");

        }
    }

}
