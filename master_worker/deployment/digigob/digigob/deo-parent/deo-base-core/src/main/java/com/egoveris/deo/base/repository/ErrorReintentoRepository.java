package com.egoveris.deo.base.repository;

import com.egoveris.deo.base.model.ErrorReintento;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ErrorReintentoRepository extends JpaRepository<ErrorReintento, Integer> {

  ErrorReintento findByNombre(String nombre);
}
