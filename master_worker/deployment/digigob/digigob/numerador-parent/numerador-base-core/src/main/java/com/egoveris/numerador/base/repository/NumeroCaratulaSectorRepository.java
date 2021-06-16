package com.egoveris.numerador.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.egoveris.numerador.base.model.NumeroCaratulaSector;

public interface NumeroCaratulaSectorRepository extends JpaRepository<NumeroCaratulaSector, Integer> {
	
	 NumeroCaratulaSector findByUsuario(String usuario);
}
