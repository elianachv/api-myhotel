package com.bancoPopular.pruebaTecnica.service;

import com.bancoPopular.pruebaTecnica.entity.Cliente;
import com.bancoPopular.pruebaTecnica.entity.Ingreso;
import com.bancoPopular.pruebaTecnica.exception.NotFoundException;
import com.bancoPopular.pruebaTecnica.repository.ClienteRepository;
import com.bancoPopular.pruebaTecnica.repository.IngresoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngresoServiceImpl implements IngresoService {

    @Autowired
    IngresoRepository ingresoDao;
    @Autowired
    ClienteRepository clienteDao;

    @Override
    public List<Ingreso> getIngresosByCedula(String cedula) {
        Optional<Cliente> cliente = clienteDao.findByCedula(cedula);

        if (cliente.isEmpty()) {
            throw new NotFoundException("cliente paila");
        }

        return ingresoDao.getAllByCedula(cedula);
    }
}
