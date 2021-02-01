package com.egoveris.numerador.base.service;

import java.util.Date;
import java.util.List;

import com.egoveris.numerador.model.exception.SistemaInvalidoException;
import com.egoveris.numerador.model.exception.ValidacionDatosException;
import com.egoveris.numerador.model.model.NumeroDTO;

/**
 * The Interface NumeroTrabajoService.
 */
public interface NumeroService {
	
	/**
	 * crate new registers from list of numbers. 
	 * @param numero the number
	 */
	void crearNumero(List<NumeroDTO> numero) ;
	
	/**
	 * create a new register from number.
	 * @param numero the number
	 */
	void crearNumero(NumeroDTO numero) ;
	
	/**
	 * find a list of numbers by year, number and status, 
	 * to return the DTOs mapped list.
	 *
	 * @param anio the year
	 * @param numero the number
	 * @param estado the status
	 * @return the list
	 */
	public List<NumeroDTO> buscarNumeroByEstado(int anio, int numero, String estado) ;
	
	/**
	 * find a number by year, number and sequence 
	 * 
	 * @param anio the year
	 * @param numero the number
	 * @param secuencia the sequence
	 * @return the number
	 */
	public NumeroDTO buscarNumero(int anio, int numero, String secuencia) ;

	/**
	 * find the next number by year, if it doesn't exists
	 * then create a new one with his default number 1.
	 * 
	 * @param anio the year
	 * @return the number
	 */
	public NumeroDTO obtenerProximoNumero(int anio);

	/**
	 * find a number by the following parameters. 
	 * 
	 * @param usuario the user
	 * @param sistema the system
	 * @param codigoActuacion acting code
	 * @param reparticionActuacion 
	 * @param reparticionUsuario
	 * @return the number
	 */
	public NumeroDTO obtenerNumero(String usuario, String sistema, String codigoActuacion, String reparticionActuacion,
			String reparticionUsuario);
	
	/**
	 * change the number's status to "estado de baja" (not used).
	 * 
	 * @param anio year
	 * @param numero number
	 * @throws ValidacionDatosException
	 */
	public void anularNumero(int anio, int numero) throws ValidacionDatosException;

	/**
	 * change the number's status to "estado usado" (used).
	 * 
	 * @param anio year
	 * @param numero number 
	 * @throws ValidacionDatosException
	 */
	public void confimarNumero(int anio, int numero) throws ValidacionDatosException;
	
	/**
	 * find a number by year, updating his work number. 
	 * 
	 * @param sistema
	 * @param usuario
	 * @param anio
	 * @param codigoActuacion
	 * @param reparticionUsuario
	 * @param reparticionActuacion
	 * @param fechaCreacion
	 * @return
	 */
	public NumeroDTO getNumeroCaratulacion(String sistema, String usuario, int anio, String codigoActuacion,
			String reparticionUsuario, String reparticionActuacion, Date fechaCreacion) throws SistemaInvalidoException;

	/**
	 * find a number by year, updating the sequence on work numbers.
	 * 
	 * @param usuario
	 * @param sistema
	 * @param codigoActuacion
	 * @param reparticionActuacion
	 * @param reparticionUsuario
	 * @param listaSecuencia
	 * @return
	 */
	public NumeroDTO obtenerNumeroConSecuencia(String usuario, String sistema, String codigoActuacion,
			String reparticionActuacion, String reparticionUsuario, List<String> listaSecuencia);
	
	/**
	 * create caratula number according  to an existing number.
	 * 
	 * @param usuario
	 * @param sistema
	 * @param codigoActuacion
	 * @param numero
	 * @param anio
	 * @param reparticionUsuario
	 * @param reparticionActuacion
	 * @param secuencia
	 */
	public void crearCaratulaConSecuencia(String usuario, String sistema,
			String codigoActuacion, int numero, int anio,
			String reparticionUsuario, String reparticionActuacion,
			String secuencia);

	/**
	 * find a number by year and increment it on 1,
	 * if the number doesn't exists the create a new one with this year	 *  
	 * 
	 * @param anio
	 * @return
	 * @
	 */
	public NumeroDTO incrementNumeroByAnio(int anio) ;

}
