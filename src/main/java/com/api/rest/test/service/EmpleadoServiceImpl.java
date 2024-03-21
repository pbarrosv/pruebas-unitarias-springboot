package com.api.rest.test.service;

import com.api.rest.test.models.Empleado;
import com.api.rest.test.repository.EmpleadosRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class EmpleadoServiceImpl implements EmpleadoService{

    @Autowired
    private EmpleadosRepository empleadosRepository;

    @Override
    public Empleado saveEmpleado(Empleado empleado) {
        return (Empleado)empleadosRepository.save(empleado);
    }

    @Override
    public List<Empleado> getAllEmpleados() {
        return null;
    }

    @Override
    public Optional<Empleado> getEmpleadoById(Long id) {
        return Optional.empty();
    }

    @Override
    public Empleado updateEmpleado(Empleado empleadoActualizado) {
        return null;
    }

    @Override
    public void deleteEmpleado(Long id) {

    }
}
