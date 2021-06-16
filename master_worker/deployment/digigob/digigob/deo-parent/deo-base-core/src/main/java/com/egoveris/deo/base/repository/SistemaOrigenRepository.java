package com.egoveris.deo.base.repository;

import com.egoveris.deo.base.model.SistemaOrigen;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SistemaOrigenRepository extends JpaRepository<SistemaOrigen, Integer> {

  SistemaOrigen findByNombreLike(String nombre);
}
