package com.api.rest.test.service;

import com.api.rest.test.exception.ResourceNotFoundException;
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
        Optional<Empleado> empleadoGuardado =  empleadosRepository.findByEmail(empleado.getEmail());
        if (empleadoGuardado.isPresent()){
            throw new ResourceNotFoundException("El empleado con ese email ya existe : " + empleado.getEmail());
        }
        return (Empleado)empleadosRepository.save(empleado);
    }

    @Override
    public List<Empleado> getAllEmpleados() {
        return empleadosRepository.findAll();
    }

    @Override
    public Optional<Empleado> getEmpleadoById(Long id) {
        return empleadosRepository.findById(id);
    }

    @Override
    public Empleado updateEmpleado(Empleado empleadoActualizado) {
        return (Empleado) empleadosRepository.save(empleadoActualizado);
    }

    @Override
    public void deleteEmpleado(Long id) {
        empleadosRepository.deleteById(id);
    }
}
