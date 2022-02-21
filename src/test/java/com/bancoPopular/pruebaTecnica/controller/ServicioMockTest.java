package com.bancoPopular.pruebaTecnica.controller;

import com.bancoPopular.pruebaTecnica.entity.Servicio;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static java.util.Collections.singletonList;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ServicioController.class)
public class ServicioMockTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ServicioController servicioController;

    @Test
    public void getServicios() throws Exception {
        Servicio servicio = new Servicio(1, "TEST", "servicio de prueba", 1000, "TEST");

        Iterable<Servicio> servicios = singletonList(servicio);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<?> r = new ResponseEntity<>(
                servicios,
                header,
                HttpStatus.OK
        );
        when(servicioController.obtenerServicios()).thenAnswer(invocation -> r);

        //doReturn(ResponseEntity.ok().body(servicios)).when(servicioController.obtenerServicios());
        //given(servicioController.obtenerServicios()).willReturn(ResponseEntity.ok().body(servicios));

        mvc.perform(get("http://localhost:8080/api/v2/servicios")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].identificador", is(servicio.getIdentificador())));
    }

    @Test
    public void getServicioById() throws Exception {
        Servicio servicio = new Servicio(1, "TEST", "servicio de prueba", 1000, "TEST");

        when(servicioController.obtenerServicios()).thenAnswer(invocation -> ResponseEntity.ok().body(servicio));


        mvc.perform(get("http://localhost:8080/api/v2/servicios/id/1")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.identificador", is(servicio.getIdentificador())));
    }
}