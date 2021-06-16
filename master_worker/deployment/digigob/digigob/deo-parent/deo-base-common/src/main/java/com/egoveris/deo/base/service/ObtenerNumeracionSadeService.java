package com.egoveris.deo.base.service;

import com.egoveris.deo.model.model.NroSADEProcesoDTO;
import com.egoveris.numerador.model.model.NumeroDTO;

import java.util.List;

import org.terasoluna.plus.common.exception.ApplicationException;

public interface ObtenerNumeracionSadeService {

	/**
	 * @param userName
	 * @param acronimo
	 * Solicita un número al numerador para luego ser empleado en el documento
	 * @return 
	 * @throws Exception
	 */
	public NumeroDTO buscarNumeroSecuenciaSade(String userName,String tipoActuacionSade) throws ApplicationException;
	

	/**
	 * @param anio
	 * @param numero
	 * Anula el número solicitado en caso de ocurrir algún tipo de excepción
	 * @throws Exception
	 */
	public void anularNumeroSADE(int anio, int numero)throws ApplicationException;
	
	/**
	 * @param anio
	 * @param numero
	 * Confirma el número pedido luego de que se haya guardado correctamento el documento
	 * @throws Exception
	 */
	public void confirmarNumeroSADE(int anio, int numero) throws ApplicationException;
	
	
	/**
	 * @param anio
	 * @param numerosTransitorios
	 * Busca si los números que han quedado en estado transitorio en el numerador han sido empleados en algún documento
	 * @return
	 * @throws Exception
	 */
	public List<NroSADEProcesoDTO> buscarNumeros(int anio, List<Integer> numerosTransitorios);
}