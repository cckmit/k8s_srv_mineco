/**
 * 
 */
package com.egoveris.te.base.service;

import java.util.List;
import java.util.Map;

import org.jbpm.api.task.Task;

import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.te.base.model.BuzonBean;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.Grupo;
import com.egoveris.te.base.util.WorkFlow;

/**
 * Servicio de asignacion para la funcionalidad de poder asignar 
 * tareas desde el buzón de tareas grupales.
 *
 * @author jnorvert
 */
public interface AsignacionService {
	
	/**
	 * Asignar tarea.
	 *
	 * @param task the task
	 * @param username the username
	 */
	public void asignarTarea(Task task, String username);
	
	/**
	 * Asignar expediente bandeja.
	 *
	 * @param expedienteElectronico the expediente electronico
	 * @param workFlow the work flow
	 */
	public void asignarExpedienteBandeja(ExpedienteElectronicoDTO expedienteElectronico, WorkFlow workFlow);
	
	/**
	 * Asignar tarea.
	 *
	 * @param task the task
	 * @param usernameOrigen the username origen
	 * @param motivo the motivo
	 * @param usuarioDestino the usuario destino
	 */
	public void asignarTarea(Task task, String usernameOrigen, String motivo, String usuarioDestino);

	/**
	 * Asignar expediente bandeja desde batch inbox grupal.
	 *
	 * @param buzonBean the buzon bean
	 * @param asignacionRRobin the asignacion R robin
	 * @param usuariosAsignadores the usuarios asignadores
	 * @return the usuario
	 */
	public Usuario asignarExpedienteBandejaDesdeBatchInboxGrupal(BuzonBean buzonBean,Map<String,Grupo> asignacionRRobin, List<Usuario> usuariosAsignadores);
	
	/**
	 * Obtener usuario O grupo A partir de batch.
	 *
	 * @param workflowID the workflow ID
	 * @return the map
	 */
	public Map<String,String> obtenerUsuarioOGrupoAPartirDeBatch(String workflowID);
}
