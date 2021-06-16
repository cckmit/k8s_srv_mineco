package com.egoveris.vucfront.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.egoveris.vucfront.base.model.Estilos;

public interface EstilosRepository  extends JpaRepository<Estilos, Long> {

	public Estilos findByCodigo(String codigo);
	
}
