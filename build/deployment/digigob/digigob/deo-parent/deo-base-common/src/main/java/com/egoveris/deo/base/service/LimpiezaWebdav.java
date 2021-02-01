package com.egoveris.deo.base.service;

import org.terasoluna.plus.common.exception.ApplicationException;

public interface LimpiezaWebdav {

	static final String SLASH = "/";
	static final String PREFIX_DOCUMENTOS_ADJUNTOS = "Proyectos_temporales/GEDO/Documentos_Adjuntos";
	static final String PREFIX_DOCUMENTOS_DE_TRABAJO = "Proyectos_temporales/GEDO/Documentos_De_Trabajo";
	static final String PREFIX_DOCUMENTOS_SADE = "Proyectos_temporales/GEDO/SADE";
	
	public void limpiezaSADE(String filename) throws ApplicationException;
	
	public void limpiezaDocumentoAdjunto(String filename) throws ApplicationException;
	
	public void limpiezaDocumentoDeTrabajo(String filename) throws ApplicationException;
	
	public void limpiezaCarpetasVacias() throws ApplicationException;
	
}
