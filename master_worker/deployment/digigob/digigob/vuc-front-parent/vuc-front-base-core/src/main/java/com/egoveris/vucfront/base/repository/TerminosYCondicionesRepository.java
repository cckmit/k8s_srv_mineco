package com.egoveris.vucfront.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.egoveris.vucfront.base.model.TerminosCondiciones;

public interface TerminosYCondicionesRepository extends JpaRepository<TerminosCondiciones, Long>{

	public TerminosCondiciones findTopByOrderByIdDesc();
	
}
