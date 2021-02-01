package com.egoveris.numerador.base.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.egoveris.numerador.base.model.NumeroSistema;

public interface NumeroSistemaRepository extends JpaRepository<NumeroSistema, Integer> {

	/**
	 * 
	 * @param activo
	 * @return
	 */
	List<NumeroSistema> findByActivo(boolean activo);
	
	/**
	 * 
	 * @param nombreSistema
	 * @return
	 */
	NumeroSistema findByNombreSistema(String nombreSistema);
}
