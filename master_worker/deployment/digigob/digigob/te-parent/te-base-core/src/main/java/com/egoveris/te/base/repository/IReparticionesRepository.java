package com.egoveris.te.base.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.egoveris.te.base.model.Reparticion;

public interface IReparticionesRepository extends JpaRepository<Reparticion, Integer> {

  List<Reparticion> findById(Integer idEstructura);

}
