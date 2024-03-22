package com.api.rest.test.service;

import static javax.management.Query.times;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.verify;

import com.api.rest.test.exception.ResourceNotFoundException;
import com.api.rest.test.models.Empleado;
import com.api.rest.test.repository.EmpleadosRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EmpleadoServiceTests {

    @Mock
    private EmpleadosRepository empleadosRepository;

    @InjectMocks
    private EmpleadoServiceImpl empleadoService;

    Empleado empleado = new Empleado();
    /*Se ejecuta antes de cada metodo "@BeforeEach" */
    @BeforeEach
    void setup(){
        empleado = Empleado.builder()
                .id(1L)
                .nombre("Katherine")
                .apellido("Morales")
                .email("Katy@gmail.com")
                .build();
    }

    @DisplayName("Test para guardar un empleado")
    @Test
    void testGuardarEmpleado(){
        //given
        /*Xomprueba que el empleado no tenga ese email, si el empleado ya tiene
          ese email va retornar empty*/
        given(empleadosRepository.findByEmail(empleado.getEmail()))
                .willReturn(Optional.empty());
        /*Si no existe ese email va retornar el empleado*/
        given(empleadosRepository.save(empleado)).willReturn(empleado);

        //when
        Empleado empleadoGuardado = empleadoService.saveEmpleado(empleado);

        //then
        assertThat(empleadoGuardado).isNotNull();
    }

    @DisplayName("Test para guardar un empleado con Throw Exception")
    @Test
    void testGuardarEmpleadoConThrowException(){
        //given
        /*Si el empleado ya existe me tiene que dar vacio ya que existe y no deberia duplicarse*/
        given(empleadosRepository.findByEmail(empleado.getEmail()))
                .willReturn(Optional.of(empleado));

        //when
        assertThrows(ResourceNotFoundException.class,()-> {
            empleadoService.saveEmpleado(empleado);
        });

        //then
        verify(empleadosRepository,never()).save(any(Empleado.class));
    }

    @DisplayName("Test para listar los empleados")
    @Test
    void testListarEmpleado(){
        //given
        Empleado emp = Empleado.builder()
                    .id(1L)
                    .nombre("Pedro")
                    .apellido("Lopez")
                    .email("lopes@gmail.com")
                    .build();
        given(empleadosRepository.findAll()).willReturn(List.of(emp,empleado));

        //when
        List<Empleado> empleados = empleadoService.getAllEmpleados();

        //then
        assertThat(empleados).isNotNull();
        assertThat(empleados.size()).isEqualTo(2);/*El tamaño de la lista de empledos sea igual a 2*/
    }

    @DisplayName("Test para listar los empleados vacios")
    @Test
    void testListarCollectionEmpleadosVacio(){
        //given
        Empleado emp = Empleado.builder()
                .id(1L)
                .nombre("Pedro")
                .apellido("Lopez")
                .email("lopes@gmail.com")
                .build();
        given(empleadosRepository.findAll()).willReturn(Collections.emptyList());

        //when
        List<Empleado> listaEmpleados = empleadoService.getAllEmpleados();

        //then
        assertThat(listaEmpleados).isNotNull();
        assertThat(listaEmpleados.size()).isEqualTo(0);
    }

    @DisplayName("Test para obtener empleado por ID")
    @Test
    void testObtenerPorId(){
        given(empleadosRepository.findById(1L)).willReturn(Optional.of(empleado));

        Empleado empleadoGuardado = empleadoService.getEmpleadoById(empleado.getId()).get();

        assertThat(empleadoGuardado).isNotNull();
    }

    @DisplayName("Test para actualizar empleado")
    @Test
    void testActualizarEmpleado(){
        given(empleadosRepository.save(empleado)).willReturn(empleado);
        empleado.setNombre("Carlos");
        empleado.setApellido("coacc");

        Empleado empleadoActualizado = empleadoService.updateEmpleado(empleado);

        assertThat(empleadoActualizado.getNombre()).isEqualTo("Carlos");
        assertThat(empleadoActualizado.getApellido()).isEqualTo("coacc");
    }

    @DisplayName("Test para eliminar un empleado")
    @Test
    void testEliminarEmpleado(){

        long empleadoId = 1L;
        willDoNothing().given(empleadosRepository).deleteById(empleadoId);

        empleadoService.deleteEmpleado(empleadoId);

        /*times es para que se aya ejecutado 1 vez; eliminacion del empleado*/
        verify(empleadosRepository, Mockito.times(1)).deleteById(empleadoId);
    }
}
