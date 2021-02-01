package com.egoveris.te.base.service.iface;

import java.util.List;

import com.egoveris.te.base.exception.ServiceException;
import com.egoveris.te.base.model.SolicitudSubs;
import com.egoveris.vucfront.ws.model.ExternalDocumentoVucDTO;
import com.egoveris.vucfront.ws.model.ExternalTipoDocumentoVucDTO;

/**
 * The Interface IActivSubsanacionService.
 */
public interface IActivSubsanacionService {

	/**
	 * Retorna los documentos tad pertenecientes a la trata del expediente.
	 *
	 * @param nroExpediente nro de expediente sade del cual se obtendra la trata para luego buscar en tad los tipos de doc
	 * @return the tipo documentos tad para exp
	 * @throws ServiceException the service exception
	 * @throws NoResultException the no result exception
	 */
	public List<ExternalDocumentoVucDTO> getTipoDocumentosVucParaExp(String nroExpediente) throws ServiceException;
	
	/**
	 * Retorna los documentos tad pertenecientes a la trata del expediente.
	 *
	 * @param codigoTrata the codigo trata
	 * @return the tipo documentos tad por codigo trata
	 * @throws ServiceException the service exception
	 * @throws NoResultException the no result exception
	 */	
	public List<ExternalDocumentoVucDTO> getDocumentosVucPorCodigoTrata(String codigoTrata) throws ServiceException;

	/**
	 * Devuelve todos los tipos de documento tad
	 * @return
	 */
	public List<ExternalDocumentoVucDTO> getAllTipoDocumentosTad() throws ServiceException;
	
	/**
	 * Buscar actividad solicitud subs.
	 *
	 * @param idActiv the id activ
	 * @return the solicitud subs
	 */
	public SolicitudSubs buscarActividadSolicitudSubs(Long idActiv);
	
	/**
	 * Genera una actividad de solicitud de subsanacion, una nueva tarea de subsanacion en TAD, 
	 * genera documento de subsanacion en gedo y lo agrega al expediente.
	 *
	 * @param solicitudSubs dto con los datos de solicitud de subsanacion
	 * @throws ServiceException the service exception
	 */
	public void enviarSubsanacionExp(SolicitudSubs solicitudSubs) throws ServiceException;

	/**
	 * Obtiene el documento tad en base al acronimo tad.
	 *
	 * @param acronimoVuc
	 * @return the tipo doc tad by cod tad
	 * @throws ServiceException the service exception
	 */
	public ExternalTipoDocumentoVucDTO getTipoDocVucByCodVuc(String acronimoVuc) throws ServiceException;


	
	public List<ExternalDocumentoVucDTO> getTipoDocumentosTadSoportados() throws ServiceException ;

	
	/**
	 * Enviar subsanacion docs con nombre exp.
	 *
	 * @param solicitudSubs the solicitud subs
	 * @throws ServiceException the service exception
	 */
	public void enviarSubsanacionDocsConNombreExp(SolicitudSubs solicitudSubs) throws ServiceException;
		
}
