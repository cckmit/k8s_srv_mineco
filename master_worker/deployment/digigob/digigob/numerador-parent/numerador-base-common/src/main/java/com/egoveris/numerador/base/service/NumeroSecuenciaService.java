package com.egoveris.numerador.base.service;

import java.util.List;

import com.egoveris.numerador.model.model.NumeroSecuenciaDTO;

/**
 * The Interface NumeroSecuenciaService.
 */
public interface NumeroSecuenciaService {
	
	/**
	 * find a list of numbers by year and number to return a mapped list of sequence numbers. 
	 * @param anio the year
	 * @param numero the number
	 * @return list of NumeroSecuenciaDTO
	 */
	public List<NumeroSecuenciaDTO> buscarCaratulas(int anio, int numero);
	
	/**
	 * find a list of sequence numbers by year and number to return it .
	 * @param anio the year
	 * @param numero the number
	 * @return list of NumeroSecuenciaDTO
	 */
	public List<NumeroSecuenciaDTO> obtenerCaratulas(int anio, int numero);
}
