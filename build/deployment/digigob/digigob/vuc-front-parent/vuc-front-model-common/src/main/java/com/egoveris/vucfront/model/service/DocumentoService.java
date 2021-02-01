package com.egoveris.vucfront.model.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.egoveris.vucfront.model.model.DocumentoDTO;
import com.egoveris.vucfront.model.model.ExpedienteFamiliaSolicitudDTO;
import com.egoveris.vucfront.model.model.PersonaDTO;
import com.egoveris.vucfront.model.model.TipoDocumentoDTO;

public interface DocumentoService {

	/**
	 * Descarga un documento de notificación desde DEO.
	 * 
	 * @param encrytpedNotificacionId
	 */
	void downloadNotificacionDocument(String encrytpedNotificacionId);
	
	/**
	 * Descarga un documento que ha sido subido desde VUC. Si el trámite no se ha
	 * finalizado, el documento se encuentra almacenado en el WebDav; en caso
	 * contrario, se encuentra en DEO.
	 * 
	 * @param document
	 * @return
	 */
	InputStream downloadDocument(DocumentoDTO document);

	/**
	 * Saved a document.
	 * 
	 * @param document
	 */
	DocumentoDTO saveDocument(DocumentoDTO document);

	/**
	 * Get a Document by it's ID.
	 * 
	 * @param id
	 * @return
	 */
	DocumentoDTO getDocumentById(Long id);

	/**
	 * Deletes one Document
	 * 
	 * @param document
	 */
	void deleteDocument(DocumentoDTO document);

	/**
	 * Generates a GEDO Document.
	 * 
	 * @param document
	 * @param usuarioIniciador
	 * @param ownerExpediente
	 * @return
	 */
	DocumentoDTO generateDocumentoGedo(DocumentoDTO document, String usuarioIniciador, PersonaDTO ownerExpediente);

	/**
	 * Get an array bytes of a GEDO Document.
	 * 
	 * @param codigoSade
	 * @param usuarioCreador
	 * @return
	 */
	byte[] getDocumentoGedoArrayBytes(String codigoSade, String usuarioCreador);

	/**
	 * Get all the Documentos that are stored in DEO from a Persona.
	 * 
	 * @param expediente
	 * @return
	 */
	List<DocumentoDTO> getDocumentosDeoByPersona(PersonaDTO persona);

	/**
	 * Get all the Documentos that are stored in DEO from a Persona and by it's
	 * TipoDocumento.
	 * 
	 * @param persona
	 * @param tipoDoc
	 * @return
	 */
	List<DocumentoDTO> getDocumentosDeoByPersonaAndTipodocumento(PersonaDTO persona, TipoDocumentoDTO tipoDoc);

	/**
	 * Get a DEO Document by it's codigo.
	 * 
	 * @param codigoSade
	 * @return a DEO Document mapped into a VUC Documento.
	 */
	DocumentoDTO getDocumentoDeoByCodigo(String codigoSade, String usuarioConsulta);

	/**
	 * Get all Documentos from an Expediente.
	 * 
	 * @param codigoExpediente
	 * @return
	 */
	List<DocumentoDTO> getDocumentosByExpedienteCodigo(String codigoExpediente);

	/**
	 * Get a TipoDocumento by it's ID.
	 * 
	 * @param id
	 * @return
	 */
	TipoDocumentoDTO getTipoDocumentoById(Long id);

	/**
	 * Get a TipoDocumento by it's acronimo DEO.
	 * 
	 * @param acronimoGedo
	 * @return
	 */
	TipoDocumentoDTO getTipoDocumentoByAcronimoGedo(String acronimoGedo);

	/**
	 * Get TipoDocumento by it's acronimo VUC.
	 * 
	 * @param acronimoVuc
	 * @return
	 */
	TipoDocumentoDTO getTipoDocumentoByAcronimoVuc(String acronimoVuc);

	/**
	 * Get all TipoDocumento from a Tipo Tramite
	 * 
	 * @param trata
	 * @return
	 */
	List<TipoDocumentoDTO> getTipoDocumentoByCodigoTramite(String trata);

	/**
	 * Get the Documento FFDD that is discreetly associated to an Expediente.
	 * 
	 * @param idExpediente
	 * @return
	 */
	List<DocumentoDTO> getDocumentoDiscretoByIdexpediente(Long idExpediente);

	/**
	 * Get the documento by urlTemporal order by idTRansaccion. Only one Documento or null if not exists.
	 * @param idTemporal
	 * @return
	 */
	DocumentoDTO getDocumentoByIdTemporalOrderByIdTransaccion(String idTransaccion);

	/**
	 * Get the docuemnto by idTransaccion
	 * @param idTransaccion
	 * @return
	 */
	DocumentoDTO getDocumentoByIdTransaccion(Long idTransaccion);

	DocumentoDTO generateDocumentoGedoLibre(ExpedienteFamiliaSolicitudDTO expediente, String usuarioIniciador,
			PersonaDTO ownerExpediente, Map<String, String> infoPago) throws Exception;

	List<TipoDocumentoDTO> getAllTipoDocumento();

	DocumentoDTO getDocumentoByNumero(String numeroDocumento);
}