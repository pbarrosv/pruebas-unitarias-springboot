package com.api.rest.test.repository;

import com.api.rest.test.models.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmpleadosRepository extends JpaRepository {

    Optional<Empleado> findByEmail(String email);

}
