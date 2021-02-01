package com.egoveris.te.base.service;

import java.util.Map;

import com.egoveris.te.base.exception.GenerarPdfException;

/**
 * The Interface PDFService.
 */
@FunctionalInterface
public interface PDFService {
	
	
	/**
	 * Generar PDF.
	 *
	 * @param parametros the parametros
	 * @return the byte[]
	 * @throws Exception the exception
	 */
	public byte[] generarPDF(Map<String,Object>parametros) throws GenerarPdfException;

}
