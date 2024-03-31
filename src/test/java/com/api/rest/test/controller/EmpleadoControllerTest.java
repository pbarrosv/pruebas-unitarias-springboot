package com.api.rest.test.controller;

import com.api.rest.test.models.Empleado;
import com.api.rest.test.service.EmpleadoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/*@WebMvcTest permite probar los controladores
* Autoconfigura MockMvc para realizar peticiones
* HTTP a nuestro controller*/
@WebMvcTest
public class EmpleadoControllerTest {

    /*Nos permite crear peticiones HTTP*/
    @Autowired
    private MockMvc mockMvc;

    /*Nos permite agregar objetos simulados al contexto de la aplicacion
    * El simulacro reemplazara cualquier bien existente del mismo tipo y
    * contexto de la aplicacion*/
    @MockBean
    private EmpleadoService empleadoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGuardarEmpleado() throws Exception{
        //given
        Empleado empl = Empleado.builder()
                .id(1L)
                .nombre("Katherine")
                .apellido("Morales")
                .email("Katy@gmail.com")
                .build();

        given(empleadoService.saveEmpleado(any(Empleado.class)))/*Guardo algun empleado de alguna clase Empleado*/
                .willAnswer( (invocation) -> invocation.getArgument(0));/*willAnswer responde con el argumento cero*/
        //when
        ResultActions response = mockMvc.perform(post("/api/empleados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(empl)));

        //then
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre",is(empl.getNombre())))
                .andExpect(jsonPath("$.apellido",is(empl.getApellido())))
                .andExpect(jsonPath("$.email",is(empl.getEmail())));
    }

    @Test
    void testListarEmpleados() throws Exception {
        //GIVEN
        List<Empleado> ListaEmpleados = new ArrayList<>();
        ListaEmpleados.add(Empleado.builder().nombre("Christian").apellido("Ramirez").email("c1@gmail.com").build());
        ListaEmpleados.add(Empleado.builder().nombre("Gabriel"). apellido("Ramirez").email("g1@gmail.com").build());
        ListaEmpleados.add(Empleado.builder().nombre("Julen").apellido("Ramirez").email("cj@gmail.com").build());
        ListaEmpleados.add(Empleado.builder().nombre("Biaggio").apellido("Ramirez").email("b1@gmail.com").build());
        ListaEmpleados.add(Empleado.builder().nombre("Adrian").apellido("Ramirez").email("a@gmail.com").build());
        given(empleadoService.getAllEmpleados()).willReturn(ListaEmpleados);

        //WHEN
        /*cuando haga una peticion GET va retornar el listado
        * given(empleadoService.getAllEmpleados()).willReturn(ListaEmpleados);*/
        ResultActions response = mockMvc.perform(get("/api/empleados/listaEm"));

        //THEN
        /*Y los resultados que quiero esperar son*/
        response.andExpect(status().isOk())  /*Es tado sea OK*/
                .andDo(print()) /*que me imprima*/
                .andExpect(jsonPath("$.size()",is(ListaEmpleados.size()))); /*Que el tamaño sea igual al retorno*/
    }

    @Test
    void testObtenerEmpleadoPorId() throws Exception {
        //given
        long empleadoId = 1L;
        Empleado empl = Empleado.builder()
                .nombre("Katherine")
                .apellido("Morales")
                .email("Katy@gmail.com")
                .build();
        given(empleadoService.getEmpleadoById(empleadoId)).willReturn(Optional.of(empl));

        //when
        ResultActions response = mockMvc.perform(get("/api/empleados/{id}",empleadoId));

        //Then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.nombre",is(empl.getNombre())))
                .andExpect(jsonPath("$.apellido",is(empl.getNombre())))
                .andExpect(jsonPath("$.email",is(empl.getNombre())));
    }

    @Test
    void testObtenerEmpleadoPorIdNoEncontrado() throws Exception {
        //given
        long empleadoId = 1L;
        Empleado empl = Empleado.builder()
                .nombre("Katherine")
                .apellido("Morales")
                .email("Katy@gmail.com")
                .build();
        given(empleadoService.getEmpleadoById(empleadoId)).willReturn(Optional.empty());

        //when
        ResultActions response = mockMvc.perform(get("/api/empleados/{id}",empleadoId));

        //Then
        response.andExpect(status().isNotFound())
                .andDo(print());
    }
}
