package com.egoveris.numerador.ws.service;

import java.util.List;

import com.egoveris.numerador.model.exception.SistemaInvalidoException;
import com.egoveris.numerador.model.exception.ValidacionDatosException;
import com.egoveris.numerador.model.model.NumeroDTO;
import com.egoveris.numerador.model.model.NumeroGeneradoDTO;
import com.egoveris.numerador.model.model.NumeroSecuenciaDTO;


public interface ExternalNumeroService{

	/**
	 * @param usuario
	 * @param sistema
	 *            Obtiene o crea un nuevo nro-sade para el año en curso en caso
	 *            de que ya existan nros-sade para el año actual actualiza el
	 *            último nro sumándole uno y guarda ese nuevo nro generado en la
	 *            tabla numero_sade_trabajo
	 * @throws SistemaInvalidoException
	 */
	public NumeroDTO obtenerNumeroSade(String usuario, String sistema,String codigoActuacion,
			String reparticionActuacion,String reparticionUsuario)
			throws SistemaInvalidoException;

	/**
	 * @param numero
	 *            Elimina el nro-sade generado cuando se lo confirma o se lo
	 *            anula
	 * @throws com.egoveris.numerador.base.exception.ValidacionDatosException 
	 * @throws ValidacionDatosException
	 */
	public void anularNumeroSade(int anio, int numero) throws ValidacionDatosException;

	/**
	 * 
	 * @param numero
	 *            Crea un numero_sade_usado y elimina ese número de la tabla
	 *            numero_sade_trabajo
	 * @throws ValidacionDatosException
	 */
	public void confimarNumeroSade(int anio, int numero)
			throws ValidacionDatosException;
	
	
	/**
	 * @param usuario
	 * @param sistema
	 * @param codigoActuacion
	 * @param numero
	 * @param anio
	 * @param reparticionUsuario
	 * @param reparticionActuacion
	 * @param secuencia
	 * @throws AccesoDatosException
	 * @throws ValidacionDatosException
	 * Guarda actuaciones como CD,PD,PA,CM,ER,RR,KR,OR, OJ copias, con el número de la actuación a la cual
	 * se asocian, estas no piden un nuevo número 
	 * 
	 *  
	 */
	public void registrarCaratulaConSecuencia(String usuario, String sistema,
			String codigoActuacion, int numero, int anio,
			String reparticionUsuario, String reparticionActuacion,
			String secuencia) throws
			ValidacionDatosException;
	
	
	/**
	 * @param anio
	 * @param numero
	 * @return
	 * @throws AccesoDatosException
	 * Busca si existe una o más carátulas según un año y número determinado para realizar una validación
	 * en Track
	 */
	public List<NumeroSecuenciaDTO> obtenerCaratulas(int anio,int numero);
	
	
	
	/**
	 * @param sistema
	 * @param usuario
	 * @param anio
	 * @param codigoActuacion
	 * @param reparticionUsuario
	 * @param reparticionActuacion
	 * @param fechaCreacion
	 * @return
	 * @throws AccesoDatosException
	 * @throws SistemaInvalidoException
	 * Obtiene un numero_sade para expedientes que pertenecen al año anterior 
	 */
	public NumeroDTO obtenerNroSadeCaratulacionII(String sistema, String usuario,
			int anio, String codigoActuacion,String reparticionUsuario,
			String reparticionActuacion,java.util.Date fechaCreacion)throws SistemaInvalidoException;
	
	
	/**
	 * @param usuario
	 * @param sistema
	 * @param codigoActuacion
	 * @param reparticionActuacion
	 * @param reparticionUsuario
	 * @param listaSecuencia
	 * @return
	 * @throws AccesoDatosException
	 * @throws SistemaInvalidoException
	 * Obtiene un numero_sade y se le pasa la lista de secuencias, cada secuencia indica que es una copia y recibirá el mismo
	 * número que el original
	 * este caso se da en CCOO cuando se envía un comunicación oficial a usuarios no electrónicos.
	 */
	public NumeroDTO obtenerNumeroSadeConSecuencia(String usuario,
			String sistema, String codigoActuacion,
			String reparticionActuacion, String reparticionUsuario,
			List<String> listaSecuencia) throws
			SistemaInvalidoException;
	
	
	/**
	 * Permite buscar un número otorgado por el numerador de actuaciones
	 * 
	 * @param anio
	 * @param numero
	 * @param secuencia
	 * @return Un sólo registro que contiene toda la información
	 * sobre quien fue el solicitante del mismo
	 * @throws NumeroSadeException 
	 * @throws Exception 
	 */
	public NumeroGeneradoDTO consultarNumero(int anio, int numero,String secuencia );
	
}
