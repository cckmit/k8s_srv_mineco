package com.egoveris.deo.base.service;

import com.egoveris.deo.base.exception.EjecucionSiglaException;
import com.egoveris.deo.base.exception.ErrorBloquearTareaException;
import com.egoveris.deo.model.model.TareaBusquedaDTO;
import com.egoveris.deo.model.model.TareaJBPMDTO;

import java.util.List;
import java.util.Map;



public interface TareaJBPMService {
	
	/**
	 * Permite obtener una lista de las tareas del usuario a darse de baja.
	 * 
	 * @param usuarioBaja
	 * @return
	 */
	public List<TareaJBPMDTO> buscarTareasDelUsuarioPorActividad(String usuarioBaja,String actividad);
	
	public List<TareaJBPMDTO> buscarTareasDelUsuarioPorActividadRevisionRechazo(String usuarioBaja);
	
	public List<TareaJBPMDTO> buscarTareasPorUsuarioFirmante(String usuario)throws EjecucionSiglaException;
	
	public List<TareaJBPMDTO> buscarTareasPorUsuarioRevisor(String usuario,String actividad) throws EjecucionSiglaException;
	
	public List<TareaBusquedaDTO> buscarTareasPorUsuarioInvolucrado(Map<String,Object> parametrosConsulta);
	
	public List<TareaBusquedaDTO> buscarTareasPorUsuarioInvolucradoSinFiltrar(Map<String,Object> parametrosConsulta);
	
	public Integer cantidadTotalTareasPorUsuarioInvolucrado(Map<String,Object> parametrosConsulta);
	
	/**
	 * Permite eliminar todas las variables de la tabla JBPM4_VARIABLE de una tarea.
	 * 
	 * @param execution
	 * @throws EjecucionSiglaException
	 */
	public void eliminarVariablesJBPM(String execution) throws EjecucionSiglaException;
	
	/**
	 * Permite eliminar todas las tareas del usuario por nombre el de actividad.
	 * 
	 * @param usuarioBaja
	 * @param actividad
	 * @throws EjecucionSiglaException
	 */
	public void eliminarLasTareasDelUsuarioPorActividad(String usuarioBaja,String actividad) throws EjecucionSiglaException;
	
	/**
	 * Permite actualizar la tarea, modificando la actividad a rechazado, se modifica el usuario assignee.
	 * @param usuario
	 * @param execution
	 * @throws EjecucionSiglaException
	 */
	public void generarTareaDeRechazo(String usuario,String execution) throws EjecucionSiglaException;
	
	public void generarTareaDeFirma(String usuario, String execution) throws EjecucionSiglaException; 
	
	public void bloquearTarea(String operacion, String execution) throws ErrorBloquearTareaException;
	/**
	 * Elimina una tarea.
	 * Se utiliza en el caso que no haya usuario receptor de la tarea de rechazo en
	 * caso de una baja de usuario - Cambio de Sigla.
	 * @param execution
	 * @param usuario
	 * @throws EjecucionSiglaException
	 */
	public void eliminarTarea(String execution) throws EjecucionSiglaException;
		
}
