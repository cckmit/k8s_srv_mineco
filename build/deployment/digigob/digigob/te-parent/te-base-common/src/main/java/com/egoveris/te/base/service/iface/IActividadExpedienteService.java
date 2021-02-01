package com.egoveris.te.base.service.iface;

import java.util.List;

import com.egoveris.te.base.exception.NegocioException;
import com.egoveris.te.base.model.ActividadDTO;
import com.egoveris.te.base.model.ActividadInbox;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.model.exception.ParametroIncorrectoException;
import com.egoveris.vucfront.ws.model.ExternalDocumentoVucDTO;
import com.egoveris.vucfront.ws.model.NuevaTareaRequest;

/**
 * The Interface IActividadExpedienteService.
 */
public interface IActividadExpedienteService {

	/**
	 * Buscar actividades vigentes.
	 *
	 * @param workFlowIds the work flow ids
	 * @return the list
	 */
	public List<ActividadInbox> buscarActividadesVigentes(List<String> workFlowIds);

	/**
	 * Buscar historico actividades.
	 *
	 * @param workFlowId the work flow id
	 * @return the list
	 */
	public List<ActividadInbox> buscarHistoricoActividades(String workFlowId);
	
	/**
	 * Eliminar actividad gedo.
	 *
	 * @param numeroExpediente the numero expediente
	 */
	public void eliminarActividadGedo(String numeroExpediente);
	
	
	/**
	 * Metodo que elimina la actividad de subsanacion del EE, envia la notificacion a TAD y adjunta el documento que 
	 * informa la cancelacion al EE, lo unico que se deberia hacer despues de invocar este metodo es arrojar 
	 * el/los evento/s necesario/s para informar el cambio del expediente a la ventana asociada...
	 *
	 * @param ee the ee
	 * @param userName the user name
	 */
	public void eliminarActividadSubsanacion(ExpedienteElectronicoDTO ee,String userName);
	
	
	/**
	 * Metodo que envia la notificacion a TAD y adjunta el documento que 
	 * informa la cancelacion al EE, lo unico que se deberia hacer despues de invocar este metodo es arrojar 
	 * el/los evento/s necesario/s para informar el cambio del expediente a la ventana asociada...
	 * 
	 * @param ee the ee
	 * @param userName the user name
	 */
	public void eliminacionActividadVUC(ExpedienteElectronicoDTO ee, ActividadDTO actividad, String userName);
	
	/**
	 * Elimina una actividad dependiendo del tipo, numero exp y id actividad.
	 *
	 * @param idActividad the id actividad
	 * @param numeroExpediente the numero expediente
	 * @param tipoActividad the tipo actividad
	 */
	public void eliminarActividad(Long idActividad, String numeroExpediente, String tipoActividad);
	
	
	
	/**
	 * Tiene actividades.
	 *
	 * @param e the e
	 * @return true, if successful
	 */
	public boolean tieneActividades(ExpedienteElectronicoDTO e);
	
	/**
	 * Rechaza una actividad dependiendo del tipo, numero exp y id actividad.
	 *
	 * @param idActividad the id actividad
	 * @param numeroExpediente the numero expediente
	 * @param tipoActividad the tipo actividad
	 * @param nombreUsuario the nombre usuario
	 */
	public void rechazarActividad(Integer idActividad, String numeroExpediente, String tipoActividad, String nombreUsuario);
	
	/**
	 * Rechazar actividad.
	 *
	 * @param ee the ee
	 * @param userName the user name
	 * @param tipoActividad the tipo actividad
	 */
	/* Rechaza una actividad dependiendo del tipo, numero exp y id actividad
	 * Crea un documento GEDO y lo agrega al expediente.
	 * @param idActividad
	 * @param numeroExpediente
	 * @param tipoActividad
	 */
	public void rechazarActividad(ExpedienteElectronicoDTO ee,String userName, String tipoActividad);
	
	/**
	 * Generar actividad crear paquete arch.
	 *
	 * @param numeroExpediente the numero expediente
	 * @param nombreUsuario the nombre usuario
	 * @return idObjetivo
	 * @throws ParametroIncorrectoException the parametro incorrecto exception
	 * @throws NegocioException the negocio exception
	 */
	public int generarActividadCrearPaqueteArch(String numeroExpediente, String nombreUsuario) throws ParametroIncorrectoException, NegocioException;
	
	/**
	 * Cierra actividad.
	 *
	 * @param codigoExpediente the codigo expediente
	 * @param tipoActividad the tipo actividad
	 * @param idObjetivo the id objetivo
	 * @param nombreUsuario the nombre usuario
	 * @param estado the estado
	 * @throws ParametroIncorrectoException the parametro incorrecto exception
	 */
	public void cerrarActividad(String codigoExpediente, String tipoActividad ,Long idObjetivo, String nombreUsuario, String estado) throws ParametroIncorrectoException;
	
	public List<ExternalDocumentoVucDTO> getDocumentosByCodigoExpediente(String codigoExpediente, String codigoTramite);
	
	void nuevaTareaSubsanacionRequest(NuevaTareaRequest request);
}
