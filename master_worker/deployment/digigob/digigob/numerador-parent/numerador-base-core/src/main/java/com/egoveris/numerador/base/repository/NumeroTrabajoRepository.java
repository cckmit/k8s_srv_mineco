package com.egoveris.numerador.base.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;

import com.egoveris.numerador.base.model.NumeroTrabajo;

/**
 * The Interface NumeroTrabajoRepository.
 */
public interface NumeroTrabajoRepository extends JpaRepository<NumeroTrabajo, Integer> {

	/*
	 * SAVE() LISTO
	 * public void crearNumeroSadeTrabajo(List<NumeroSadeTrabajo> numeroSadeTrabajo)
	 * 
	 * DELETE()
	 * public void eliminarNumeroSade(List<NumeroSadeTrabajo> numeroSadeTrabajo)
	 * 
	 * 
	 */
	
	/**
	 * Buscar por anio y numero en entidad NumeroTrabajo.
	 *
	 * @param anio 
	 * @param numero 
	 * @return List<NumeroTrabajo>
	 */
	List<NumeroTrabajo> findByAnioAndNumero(Integer anio, Integer numero);

	/**
	 * Buscar por anio, sistema y secuencia en entidad NumeroTrabajo.
	 *
	 * @param anio 
	 * @param sistema 
	 * @param secuencia 
	 * @param cantidadInvocados cantidad de registros solicitados
	 * @return List<NumeroTrabajo>
	 */
	List<NumeroTrabajo> findByAnioAndSistemaAndSecuencia(@RequestParam("anio") Integer anio,
			@RequestParam("sistema") String sistema, @RequestParam("secuencia") String secuencia,
			Pageable cantidadInvocados);

}
