package com.egoveris.tica.base.service;

import java.util.Map;

import com.egoveris.tica.base.exception.TicaServiceException;
import com.egoveris.tica.base.model.ResponseDocumento;

/**
 * Interfaz que mediante spring remoting accede
 * a los procesos de transformacion y firmado de PDF en la app ticapdf-core-api
 * @author lmancild
 *
 */
public interface ExternalTicaPdfService {
	
	/**
	 * Retornará el documento firmado
	 * @param responseDocumento
	 * @return PDF firmado digitalmente 
	 * @since 1.0.0
	 * @throws TicaServiceException
	 */
	public byte[] firmarDocumento(ResponseDocumento responseDocumento, boolean importado, Map<String, String> labels)
			throws TicaServiceException;

	/**
	 * Transforma un template HTML a PDF, posteriormente retorna
	 * el array de bytes.
	 * @param data
	 * @return PDF generado en un arreglo de bytes
	 * @since 1.0.0
	 * @throws TicaServiceException
	 */
	public byte[] htmlToPdf(byte[] data) throws TicaServiceException;

}
