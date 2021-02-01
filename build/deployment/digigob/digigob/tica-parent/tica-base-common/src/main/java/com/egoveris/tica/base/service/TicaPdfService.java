package com.egoveris.tica.base.service;

import com.egoveris.tica.base.exception.TicaHtmlToPdfException;
import com.egoveris.tica.base.exception.TicaSignPdfException;
import com.egoveris.tica.base.model.FirmaInput;


/**
 * Interfaz que define los metodos para el firmado y conversion de archivos PDF
 * @author lmancild
 *
 */
public interface TicaPdfService {
	/**
	 * Firmará un archivo PDF digitalmente
	 * @param firmaInput
	 * @return Documento firmado digitalmente en un arreglo de bytes
	 * @since 1.0.0
	 * @throws TicaServiceException
	 * @throws TicaSignPdfException 
	 * 
	 */
	
	public byte[] firmarDocumento(FirmaInput firmaInput, boolean importado) throws TicaSignPdfException;
	/**
	 * Generará un PDF a partir de un template HTML
	 * @param data
	 * @return PDF generado en un arreglo de bytes
	 * @since 1.0.0
	 * @throws TicaHtmlToPdfException 
	 */
	public byte[] htmlToPdf(byte[] data) throws TicaHtmlToPdfException;
}
