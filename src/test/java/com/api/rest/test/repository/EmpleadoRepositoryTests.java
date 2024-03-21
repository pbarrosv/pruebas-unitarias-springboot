package com.api.rest.test.repository;

import static org.assertj.core.api.Assertions.assertThat;
import com.api.rest.test.models.Empleado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class EmpleadoRepositoryTests {

    @Autowired
    private EmpleadosRepository empleadosRepository;

    Empleado empleado;

    /*Se ejecuta antes de cada metodo "@BeforeEach" */
    @BeforeEach
    void setup(){
        empleado = Empleado.builder()
                .nombre("Katherine")
                .apellido("Morales")
                .email("Katy@gmail.com")
                .build();
    }

    @DisplayName("Test para guardar un empleado")/*Sirve cuando se tiene muchos metodos*/
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
        Empleado empleadoGuardado = empleadosRepository.save(empleado1);

        /* then - verificar la salida */
        assertThat(empleadoGuardado).isNotNull();
        assertThat(empleadoGuardado.getId()).isGreaterThan(0);
    }

    @DisplayName("Test para listar empleados")/*Sirve cuando se tiene muchos metodos*/
    @Test
    void testListarEmpleados(){
        //given
        Empleado empleado1 = Empleado.builder()
                .nombre("Luis")
                .apellido("Valle")
                .email("luis@gmail.com")
                .build();
        empleadosRepository.save(empleado1);
        empleadosRepository.save(empleado);

        //when (es lo que se va probar)
        List<Empleado> listarEmpleados = empleadosRepository.findAll();

        //then
        assertThat(listarEmpleados).isNotNull();
        assertThat(listarEmpleados.size()).isEqualTo(2);
    }

    @DisplayName("Test para obtener un empleado por ID")
    @Test
    void testObtenerEmpleadoPorId(){
        empleadosRepository.save(empleado);

        //when
        Empleado empleadoBD = empleadosRepository.findById(empleado.getId()).get();

        //then
        assertThat(empleadoBD).isNotNull();

    }
    @DisplayName("Test para actualizar un empleado")
    @Test
    void testActualizarEmpleado(){
        empleadosRepository.save(empleado);

        //when
        Empleado empleadoGuardado = empleadosRepository.findById(empleado.getId()).get();
        empleadoGuardado.setEmail("zxc@gmail.com");
        empleadoGuardado.setNombre("Micho");
        empleadoGuardado.setApellido("Ramirez");

        Empleado empleadoActtualizado = empleadosRepository.save(empleadoGuardado);

        //then
        assertThat(empleadoActtualizado.getEmail()).isEqualTo("zxc@gmail.com");
        assertThat(empleadoActtualizado.getNombre()).isEqualTo("Micho");

    }
    @DisplayName("Test para eliminar un empleado")
    @Test
    void testEliminarEmpleado(){
        empleadosRepository.save(empleado);

        //when
        empleadosRepository.deleteById(empleado.getId());
        Optional<Empleado> empleadorOpctional = empleadosRepository.findById(empleado.getId());

        //then
        assertThat(empleadorOpctional).isEmpty();
    }
}
