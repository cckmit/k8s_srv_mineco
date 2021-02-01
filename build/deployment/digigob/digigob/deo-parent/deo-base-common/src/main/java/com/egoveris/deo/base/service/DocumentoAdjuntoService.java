package com.egoveris.deo.base.service;

import com.egoveris.deo.model.model.DocumentoAdjuntoDTO;

import java.util.List;

import org.terasoluna.plus.common.exception.ApplicationException;

public interface DocumentoAdjuntoService {

	public Integer grabarDocumentoAdjuntoBD (DocumentoAdjuntoDTO documento);
	
	public void eliminarDocumentoAdjuntoBD (DocumentoAdjuntoDTO documento);
	
	public List<DocumentoAdjuntoDTO> buscarArchivosDeTrabajoPorProceso(String workflowId);
	
	public void subirDocumentoAdjuntoWebDav (DocumentoAdjuntoDTO documento);
	
	public byte[] obtenerDocumentoAdjuntoTemporalWebDav(String pathRelativo, String nombreDocumentoAdjunto) throws ApplicationException;;
	
	public void borrarDocumentoAdjuntoTemporalWebDav (String pathRelativo, String nombreDocumentoAdjunto);
	
	public void regularizacionDocumentoAdjuntoNuevoRepositorio (DocumentoAdjuntoDTO documento) throws ApplicationException;
	
}
