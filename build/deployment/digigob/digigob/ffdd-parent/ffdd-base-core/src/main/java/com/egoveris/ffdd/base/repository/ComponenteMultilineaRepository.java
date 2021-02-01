package com.egoveris.ffdd.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.egoveris.ffdd.base.model.ComponenteMultilinea;

public interface ComponenteMultilineaRepository extends JpaRepository<ComponenteMultilinea, Integer>{

	ComponenteMultilinea findOneByIdFormComp(Integer idComponente);
	
	void deleteByIdFormComp(Integer idComponente);
}
