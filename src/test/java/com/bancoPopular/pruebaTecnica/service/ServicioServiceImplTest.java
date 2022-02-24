package com.bancoPopular.pruebaTecnica.service;

import com.bancoPopular.pruebaTecnica.entity.Servicio;
import com.bancoPopular.pruebaTecnica.repository.ServicioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ServicioServiceImplTest {

    @Mock
    private ServicioRepository servicioDao;

    @InjectMocks
    private ServicioServiceImpl servicioService;

    private Servicio servicio;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        servicio = new Servicio(1,"TEST001","Testing servicio", 2000,"TEST");
    }

    @Test
    void findAll() {
        System.out.println("\nTEST findAll()\n");
        when(servicioDao.findAll()).thenReturn(List.of(servicio));
        System.out.println("Listado de servicios:\n"+servicioService.findAll());
        assertNotNull(servicioService.findAll());
    }


    @Test
    void findAllByTipo() {
        System.out.println("\nTEST findAllByTipo()\n");
        when(servicioDao.findAllByTipo("TEST")).thenReturn(List.of(servicio));
        System.out.println("Listado de servicios TEST:\n"+servicioService.findAllByTipo("TEST"));
        assertEquals(1,servicioService.findAllByTipo("TEST").spliterator().getExactSizeIfKnown());
        assertNotNull(servicioService.findAllByTipo("TEST"));
    }

    @Test
    void save() {
        System.out.println("\nTEST save()\n");
        Servicio servicio = new Servicio(2,"TEST002","Testing servicio", 2000,"TEST");
        System.out.println("Servicio guardado:\n"+servicio.toString());
        when(servicioDao.save(any(Servicio.class))).thenReturn(servicio);
        assertNotNull(servicioService.save(servicio));

    }
}