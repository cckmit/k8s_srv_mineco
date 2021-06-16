package com.egoveris.deo.base.service;

import java.util.Date;
import java.util.List;

import org.jbpm.api.ProcessInstance;

import com.egoveris.deo.base.exception.EjecucionSiglaException;
import com.egoveris.deo.model.model.HistorialDTO;

public interface HistorialService {
  /**
   * Guarda el historial que se envia como parametro
   * 
   * @param historial
   */
  public void guardarHistorial(HistorialDTO historial);

  /**
   * Permitir actualizar un registro del historial
   * 
   * @param historial
   */
  public void actualizarHistorial(String workflowOrigen);

  /**
   * Permitir actualizar un registro del historial
   * 
   * @param historial
   */
  public void actualizarHistorial(String workflowOrigen, String mnjsEnvRevision);
  /**
   * Busca el ultimo historial
   * 
   * @param workflowOrigen
   * @return ultimo historial
   */
  public HistorialDTO buscarUltimaHistorial(String workflowOrigen, String actividad);

  /**
   * Genera un nuevo historial de la tarea realizada.
   * 
   * @param workflowOrigen
   * @param usuario
   */
  public void nuevoHistorial(String usuario, ProcessInstance pIns);

	/**
	 * Busca el historial de una tarea, ordenadas de forma descendente por su fecha
	 * de finalización. Los registros que tienen este dato en null, se dejan al
	 * final de la lista.
	 * 
	 * @param workflowOrigen
	 * @return
	 */
	public List<HistorialDTO> buscarTodosHistoriales(String workflowOrigen);

  public String buscarUsuarioEnHistorial(String workflowOrigen) throws EjecucionSiglaException;

  public void actualizarHistorialUsuarioBaja(Date fechaActual, String executionID,
      String usuarioBaja, String activityName, String mensaje) throws EjecucionSiglaException;

  public void actualizarHistorialUsuarioBajaFirmante(String mensaje, String executionID,
      String usuarioBaja, String activityName) throws EjecucionSiglaException;

  public void actualizarActividadHistorialUsuarioBajaFirmante(String executionID,
      String activityName) throws EjecucionSiglaException;

  public void actualizarHistorialUsuarioRevisorBaja(String mensaje, String executionID,
      String usuarioBaja) throws EjecucionSiglaException;
}
