package com.api.rest.test.controller;


import com.api.rest.test.models.Empleado;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

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
    }
}
