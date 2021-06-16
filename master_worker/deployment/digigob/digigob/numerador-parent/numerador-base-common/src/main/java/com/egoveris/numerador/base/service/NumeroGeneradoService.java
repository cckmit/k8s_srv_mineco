package com.egoveris.numerador.base.service;

import com.egoveris.numerador.model.model.NumeroGeneradoDTO;

public interface NumeroGeneradoService {

	/**
	 * Find a number by year, number and sequence, to return it as a mapped generated number.
	 * @param anio
	 * @param numero
	 * @param secuencia
	 * @return NumeroGeneradoDTO
	 */
	public NumeroGeneradoDTO consultarNumero(int anio, int numero, String secuencia);

}
