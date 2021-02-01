package com.egoveris.deo.base.service;

import com.egoveris.deo.base.exception.VisualizacionDocumentoException;
import com.egoveris.deo.base.exception.obtenerImagenesException;
import com.egoveris.deo.model.model.ArchivoDeTrabajoDTO;
import com.egoveris.deo.model.model.VisualizarDocumentoDTO;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.List;

public interface IVisualizarDocumentosService {
	
	/**
	 * Se pasan los parámetros número de documento sade y el usuario loggeado.
	 * Se completan los atributos del DocumentoDTO, tanto los atributos del
	 * documento, como si tiene permisos para visualizar el documento.
	 * 
	 * @param numeroSade
	 *            Numero sade del documento a completar
	 * @param loggedUsername
	 *            Usuario loggeado
	 * @param assignee
	 *            En el caso que sea invocado desde EE, existen casos que necesita saber si tiene permisos de visualizacion mediante assignee           
	 * @return DocumentoDTO con sus atributos completos
	 * @throws VisualizacionDocumentoException 
	 */
	public VisualizarDocumentoDTO completarDocumentoDTO(String numeroSade,
			String loggedUsername, boolean esAssignee) throws VisualizacionDocumentoException;
	/**
	 * Se pasan los parámetros número de documento sade y el usuario loggeado.
	 * Se completan los atributos del DocumentoDTO, tanto los atributos del
	 * documento, como si tiene permisos para visualizar el documento.
	 * 
	 * @param numeroSade
	 *            Numero sade del documento a completar
	 * @param loggedUsername
	 *            Usuario loggeado
	 * @return DocumentoDTO con sus atributos completos
	 * @throws VisualizacionDocumentoException 
	 */
	public VisualizarDocumentoDTO completarDocumentoDTO(String numeroSade,
			String loggedUsername) throws VisualizacionDocumentoException;

	/**
	 * Devuelve un BufferedInputStream que contiene el documento para que pueda ser descargado.
	 * 
	 * @param numeroSade Número sade del documento.
	 * @throws Exception
	 */
	public BufferedInputStream descargaDocumento(VisualizarDocumentoDTO documentoDTO) throws IOException;
		
	/**
	 * Devuelve un BufferedInputStream que contiene el archivo de trabajo para que pueda ser descargado.
	 * 
	 * @param archivoDeTrabajoDTO
	 * @return
	 * @throws Exception
	 */
	public BufferedInputStream descargaArchivoDeTrabajo(ArchivoDeTrabajoDTO archivoDeTrabajoDTO)
			throws IOException;

	/**
	 * Genera las imagenes de previsualizacion del documento.
	 * 
	 * @param numeroSade Número sade del documento.
	 * @return
	 * @throws Exception
	 */
	public List<String> obtenerImagenesPrevisualizacion(String numeroSade)
			throws obtenerImagenesException;
	
	
	public byte[] obtenerPrimerasHojasPrevisualizacionDocumento(String numeroSade)throws IOException;
	
	public int obtenerMaxCantPrevisualizar();
	
}
