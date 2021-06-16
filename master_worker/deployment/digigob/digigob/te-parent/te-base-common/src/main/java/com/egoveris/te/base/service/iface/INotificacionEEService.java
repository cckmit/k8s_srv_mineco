package com.egoveris.te.base.service.iface;

import java.util.List;

import com.egoveris.te.base.exception.AsignacionException;
import com.egoveris.te.base.exception.NegocioException;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
/**
 * The Interface INotificacionEEService.
 */
public interface INotificacionEEService {
	
	/**
	 * Obtener documentos notificables.
	 *
	 * @param expedienteElectronico the expediente electronico
	 * @return the list
	 */
	public List<DocumentoDTO> obtenerDocumentosNotificables(ExpedienteElectronicoDTO expedienteElectronico);
	
	/**
	 * Alta notificacion TAD.
	 *
	 * @param usuario the usuario
	 * @param ee the ee
	 * @param listaDocumento the lista documento
	 * @param motivoDeNotificacion the motivo de notificacion
	 * @throws Exception the exception
	 */
	public void altaNotificacionVUC(String usuario,ExpedienteElectronicoDTO ee, List<DocumentoDTO> listaDocumento,String motivoDeNotificacion) throws AsignacionException;

	/**
	 * Notificar EE toma vista con suspencion.
	 *
	 * @param usuario the usuario
	 * @param ee the ee
	 * @param docs the docs
	 * @param motivo the motivo
	 * @throws Exception the exception
	 */
	// SACAR EL COMENTARIO CUANDO TAD HAGA LA RELEASE 2.5.0 Y NECESITE ESTE SERVICIO
	//public void notificarEETomaVistaConSuspencion(String usuario,ExpedienteElectronicoDTO ee,List<DocumentoRequest>docs, String motivo ) throws Exception;
	
	/**
	 * Notificar buzon judicial.
	 *
	 * @param usuario the usuario
	 * @param ee the ee
	 * @param d the d
	 * @param motivoNotificacion the motivo notificacion
	 * @param cuit the cuit
	 * @throws NegocioException the negocio exception
	 */
	public void notificarBuzonJudicial(String usuario,ExpedienteElectronicoDTO ee,DocumentoDTO d,String motivoNotificacion,String cuit) throws NegocioException;



}
