package com.egoveris.deo.base.repository;

import com.egoveris.deo.base.model.TipoProduccion;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoProduccionRepository extends JpaRepository<TipoProduccion, Integer> {

  TipoProduccion findById(Integer id);
}
