package com.api.rest.test.controller;

import com.api.rest.test.models.Empleado;
import com.api.rest.test.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Empleado guardarEmpleado(@RequestBody Empleado emp){
        return empleadoService.saveEmpleado(emp);
    }

    @GetMapping("/listaEm")
    public List<Empleado> listarEmpleados(){
        return empleadoService.getAllEmpleados();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empleado> obtenerEmpleadoPorId(@PathVariable("id") long empleadoId){
        return empleadoService.getEmpleadoById(empleadoId)
                .map(ResponseEntity::ok)
                .orElseGet(()-> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Empleado> actualizarEmpleado(@PathVariable("id") long empleadoId,@RequestBody Empleado emp){
        return empleadoService.getEmpleadoById(empleadoId)
                .map( empleadoGuardado -> {
                    empleadoGuardado.setNombre(emp.getNombre());
                    empleadoGuardado.setApellido(emp.getApellido());
                    empleadoGuardado.setEmail(emp.getEmail());

                    Empleado empleadoActualizado = empleadoService.updateEmpleado(empleadoGuardado);
                    return new ResponseEntity<>(empleadoActualizado,HttpStatus.OK);
                }).orElseGet(()-> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarEmpleado(@PathVariable("id") long empleadoId){
        empleadoService.deleteEmpleado(empleadoId);
        return new ResponseEntity<String>("Empleado eliminado Exitosamente",HttpStatus.OK);
    }
}
