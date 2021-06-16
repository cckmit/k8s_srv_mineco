package com.egoveris.deo.base.service;

import com.egoveris.deo.base.exception.PDFConversionException;

public interface ConversorPdf {
	
	/**
	 * Crea un contenido pdf a partir de un arreglo de bytes con tipo de contenido html.
	 * @param data
	 * @return Un arreglo de bytes con contenido de tipo pdf.
	 * @throws PDFConversionException
	 */
	public byte[] convertirHTMLaPDF(byte[] data) throws PDFConversionException;
	
	/**
	 * Crea un archivo pdf, a partir del arreglo de bytes dado.
	 * @param data
	 * @param nameFile
	 * @return File pdf.
	 * @throws PDFConversionException
	 */
	public byte[] crearPDF(byte[] data, String tipoArchivo) throws PDFConversionException;
	
	/**
	 * Convierte un archivo pdf en archivos png.
	 * @param contenidoPdf archivo pdf.
	 * @return Lista de imágenes, tantas como páginas tenga el archivo pdf.
	 * @throws Exception
	 */
//	@SuppressWarnings("rawtypes")
//	public List convertirPDFaPNG(byte[] contenidoPdf) throws Exception;
}