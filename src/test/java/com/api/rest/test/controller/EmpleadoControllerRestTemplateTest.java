package com.api.rest.test.controller;


import com.api.rest.test.models.Empleado;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class) /*Vamos a indicar el orden de los metodos*/
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)/*Le decimos que usaremos un puerto aleatorio*/
public class EmpleadoControllerRestTemplateTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @Order(1)
    void testGuardarEmpleado(){
        Empleado empl = Empleado.builder()
                .id(1L)
                .nombre("Pedro")
                .apellido("Barros")
                .email("pbarros@gmail.com")
                .build();
        /*Vamos poner al empl y le vamos a poner de que tipo es Empleado.class*/
        ResponseEntity<Empleado> respuesta = testRestTemplate.postForEntity("http://localhost:8080/api/empleados",empl,Empleado.class);
        assertEquals(HttpStatus.CREATED,respuesta.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON,respuesta.getHeaders().getContentType());

        Empleado empleadoCreado = respuesta.getBody();

        assertEquals(1L,empleadoCreado.getId());
        assertEquals("Pedro",empleadoCreado.getNombre());
        assertEquals("Barros",empleadoCreado.getApellido());
        assertEquals("pbarros@gmail.com",empleadoCreado.getEmail());
    }

    @Test
    @Order(2)
    void testListarEmpleado(){
        ResponseEntity<Empleado[]> respuesta = testRestTemplate.getForEntity("http://localhost:8080/api/empleados/listaEm",Empleado[].class);
        List<Empleado> empleados = Arrays.asList(respuesta.getBody());

        assertEquals(HttpStatus.OK,respuesta.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON,respuesta.getHeaders().getContentType());

        assertEquals(1,empleados.size());
        assertEquals(1L,empleados.get(0).getId());
        assertEquals("Pedro",empleados.get(0).getNombre());
        assertEquals("Barros",empleados.get(0).getApellido());
        assertEquals("pbarros@gmail.com",empleados.get(0).getEmail());
    }

    @Test
    @Order(3)
    void testObtenerEmpleado(){
        ResponseEntity<Empleado> respuesta = testRestTemplate.getForEntity( "http://localhost:8080/api/empleados/1", Empleado.class);
        Empleado empleado = respuesta.getBody();

        assertEquals(HttpStatus.OK,respuesta.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, respuesta.getHeaders().getContentType());

        assertNotNull(empleado);
        assertEquals( 1L, empleado.getId());
        assertEquals( "Pedro", empleado.getNombre());
        assertEquals( "Barros", empleado.getApellido());
        assertEquals( "pbarros@gmail.com", empleado.getEmail());
    }
}
