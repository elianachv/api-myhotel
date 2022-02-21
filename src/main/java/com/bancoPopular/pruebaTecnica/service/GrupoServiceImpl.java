package com.bancoPopular.pruebaTecnica.service;

import com.bancoPopular.pruebaTecnica.entity.Cliente;
import com.bancoPopular.pruebaTecnica.entity.Grupo;
import com.bancoPopular.pruebaTecnica.entity.Ingreso;
import com.bancoPopular.pruebaTecnica.exception.DuplicatedException;
import com.bancoPopular.pruebaTecnica.exception.NotFoundException;
import com.bancoPopular.pruebaTecnica.repository.ClienteRepository;
import com.bancoPopular.pruebaTecnica.repository.GrupoRepository;
import com.bancoPopular.pruebaTecnica.repository.IngresoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class GrupoServiceImpl implements GrupoService {

    @Autowired
    GrupoRepository grupoDao;

    @Autowired
    IngresoRepository ingresoDao;

    @Override
    @Transactional(readOnly = true)
    public List<?> findAll() {

        List<Grupo> grupos = grupoDao.findAll();
        List<Map<String, Object>> gruposFinal = new ArrayList<>();

        for (Grupo grupo : grupos) {

            gruposFinal.add(getInfoGrupo(grupo));
        }
        return gruposFinal;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> findById(long id) {

        Optional<Grupo> grupo = grupoDao.findById(id);

        if (grupo.isEmpty()) {
            throw new NotFoundException("El grupo con id " + id + " no existe");
        }
        return getInfoGrupo(grupo.get());
    }

    @Override
    @Transactional
    public Grupo save(Grupo grupo) {
        Optional<Grupo> grupo1 = grupoDao.findById(grupo.getId());

        if (grupo1.isEmpty()) {
            return grupoDao.save(grupo);
        }

        throw new DuplicatedException("El grupo que intenta crear ya existe");
    }

    @Override
    @Transactional
    public Grupo update(long id, Grupo grupoEditado) {
        Optional<Grupo> grupoExistente = grupoDao.findById(id);

        if (grupoExistente.isEmpty()) {
            throw new NotFoundException("No existe ningún grupo registrado con id " + id);
        }
        //BeanUtils.copyProperties(servicioEditado,servicioExistente.get());
        grupoExistente.get().setTotal_integrantes(grupoEditado.getTotal_integrantes());

        return grupoDao.save(grupoExistente.get());
    }

    @Override
    @Transactional
    public Grupo delete(long id) {
        Optional<Grupo> grupoExistente = grupoDao.findById(id);

        if (grupoExistente.isEmpty()) {
            throw new NotFoundException("No existe ningún grupo registrado con id " + id);
        }
        //BeanUtils.copyProperties(servicioEditado,servicioExistente.get());
        grupoDao.deleteById(id);
        return grupoExistente.get();
    }

    public Map<String, Object> getInfoGrupo(Grupo grupo) {

        Map<String, Object> infoGrupo = new HashMap<>();

        List<Ingreso> ingresos = ingresoDao.findAllByGrupo(grupo.getId());
        List<Cliente> clientes = new ArrayList<>();

        for (Ingreso ingreso : ingresos) {
            clientes.add(ingreso.getCliente());
        }

        infoGrupo.put("id", grupo.getId());
        infoGrupo.put("total_integrantes", grupo.getTotal_integrantes());
        infoGrupo.put("integrantes", clientes);

        return infoGrupo;
    }

}
