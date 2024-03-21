package com.api.rest.test.repository;

import static org.assertj.core.api.Assertions.assertThat;
import com.api.rest.test.models.Empleado;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class EmpleadoRepositoryTests {

    @Autowired
    private EmpleadosRepository empleadosRepository;

    @Test
    void testGuardarEmpleado(){
        /* BDD Behavior Driven Development */
        /* given - dado o condicion previa a configuraciòn */
        Empleado empleado1 = Empleado.builder()
                .nombre("Pedro")
                .apellido("Barros")
                .email("pedro@gmail.com")
                .build();
        /* when - accion o el comportamiento que vamos a probar */
        Empleado empleadoGuardado = (Empleado) empleadosRepository.save(empleado1);

        /* then - verificar la salida */
        assertThat(empleadoGuardado).isNotNull();
        assertThat(empleadoGuardado.getId()).isGreaterThan(0);
    }

}
