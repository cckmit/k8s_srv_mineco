package com.egoveris.te.base.service;

import java.util.List;
import java.util.Map;

import com.egoveris.te.base.exception.GenerarExpedienteException;
import com.egoveris.te.base.model.ExpedienteMetadataDTO;
import com.egoveris.te.base.model.HistorialOperacionDTO;
import com.egoveris.te.base.model.SolicitudExpedienteDTO;
import com.egoveris.te.base.model.TrataDTO;
public interface SolicitudExpedienteService {
	public void grabarSolicitud(SolicitudExpedienteDTO solicitudExpediente, HistorialOperacionDTO historialOperacion, Boolean grabaHistorial);
	public void modificarSolicitud(SolicitudExpedienteDTO solicitudExpediente);
	public SolicitudExpedienteDTO obtenerSolitudByIdSolicitud(Long idSolicitud);
	
	public void grabarSolicitudPorCaratulacionInternaoExterna(SolicitudExpedienteDTO solicitudExpediente);

    /**
     * Construye un <code>IngresoSolicitudExpediente</code> con algunos parámetros más.
     * Generacion de Expediente para verificar que se pueda crear el Expediente Electronico, antes de terminar la generacion de la solicitud.
     * Se inicializa el <code>WorkFlow</code> para alguna <code>Tarea</code> - <code>SolicitudExpediente</code>
     * @param <code>ProcessEngine</code> processEngine,
     * @param La estructura que devuelve es <code>Map<String, Object></code>inputMap 
	 * <ul>
	 * 	<li>ATTR_USER_NAME</li>
	 * 	<li>ATTR_APELLIDO_SOLICITANTE</li>
	 * 	<li>ATTR_NOMBRE_SOLICITANTE</li>
	 * 	<li>ATTR_RAZON_SOCIAL</li>
	 * 	<li>ATTR_EMAIL</li>
	 * 	<li>ATTR_TELEFONO</li>
	 * 	<li>ATTR_TIPO_DOC</li>
	 * 	<li>ATTR_NUM_DOC</li>
	 * 	<li>ATTR_MOTIVO_INTERNO</li>
	 * 	<li>ATTR_MOTIVO_EXTERNO</li>
	 * </ul>
     * @param <code>java.lang.Boolean</code> isSolicitudExterna,
     * @return <code>java.lang.Object</code> solicitudExpediente,
     * @exception <code>Exception</code> exception
     */
	public Object generarSolicitudExpediente(final Map<String, Object> inputMap, final org.jbpm.api.ProcessEngine processEngine, final TrataDTO norma, final String descripcion, 
			final List<ExpedienteMetadataDTO> expedienteMetadata, final String username, final String motivoExpediente, final Boolean isSolicitudExterna) throws GenerarExpedienteException;
		
}
