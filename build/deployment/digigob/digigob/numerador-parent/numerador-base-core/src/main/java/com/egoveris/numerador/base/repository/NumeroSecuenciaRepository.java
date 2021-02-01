package com.egoveris.numerador.base.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.egoveris.numerador.base.model.NumeroSecuencia;


public interface NumeroSecuenciaRepository extends JpaRepository<NumeroSecuencia, Integer > {
		
	List<NumeroSecuencia> findByAnioAndNumero(Integer anio, Integer numero);
	
	NumeroSecuencia findByAnio(Integer anio);
	
}
