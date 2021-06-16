package com.egoveris.te.base.service.iface;

import java.sql.SQLDataException;
import java.util.List;
import java.util.Map;

import org.jbpm.api.task.Task;

import com.egoveris.te.base.exception.BuscarTareaException;
import com.egoveris.te.base.model.DocumentUnqtAppDTO;
import com.egoveris.te.base.model.Tarea;


/**
 * The Interface ITaskViewService.
 */
public interface ITaskViewService {
	
	/**
	 * Buscar tareas por usuario.
	 *
	 * @param parametrosConsulta the parametros consulta
	 * @param parametrosOrden the parametros orden
	 * @return the list
	 */
	public List<?> buscarTareasPorUsuario(Map <String, Object> parametrosConsulta, Map<String, String> parametrosOrden);

	/**
	 * Numero total tareas por usuario.
	 *
	 * @param username the username
	 * @return the int
	 */
	public int numeroTotalTareasPorUsuario(String username);
	
	/**
	 * Numero total tareas grupo por usuario.
	 *
	 * @param username the username
	 * @param activityName the activity name
	 * @return the int
	 */
	public int numeroTotalTareasGrupoPorUsuario(String username,String activityName);

    /**
     * Esté método busca por algun criterio <code>org.jbpm.api.TaskQuery</code> en aquellas <code>org.jbpm.api.task.Task</code> asignadas.
     * Transformando la <code>org.jbpm.api.task.Task</code> en objecto <code>Tarea</code>
     *
     * @param parametrosConsulta the parametros consulta
     * @param parametrosOrden the parametros orden
     * @return <code>List<?></code> Lista de Tareas.
     */
	public List<?> buscarTask2TareaPorGrupo(Map<String, Object> parametrosConsulta, Map<String, String> parametrosOrden);

	/**
	 * Buscar solicitudes paralelas por usuario.
	 *
	 * @param user the user
	 * @return the list
	 */
	public List<Task> buscarSolicitudesParalelasPorUsuario(String user);
	
	/**
	 * Buscar tarea dist por trata.
	 *
	 * @param expedienteEstado the expediente estado
	 * @param expedienteUsuarioAsignado the expediente usuario asignado
	 * @param codigosDeTrataList the codigos de trata list
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<Tarea> buscarTareaDistPorTrata(final String expedienteEstado, final String expedienteUsuarioAsignado, final List<String> codigosDeTrataList) throws BuscarTareaException;
	
	/**
	 * Verificar no existe tarea.
	 *
	 * @param idWorkflow the id workflow
	 * @return the boolean
	 * @throws Exception the exception
	 */
	public Boolean verificarNoExisteTarea(final String idWorkflow) throws SQLDataException;
	
	 /**
 	 * Buscar tareas por usuario.
 	 *
 	 * @param user the user
 	 * @return the list
 	 */
 	public List<Task> buscarTareasPorUsuario(String user);
	 
	 /**
 	 * Obtener task by id.
 	 *
 	 * @param idTask the id task
 	 * @return the task
 	 */
 	public Task obtenerTaskById(String idTask);
	 
	 /**
 	 * Buscar solicitudes de anular modificar.
 	 *
 	 * @param user the user
 	 * @return the list
 	 */
 	public List<Task>buscarSolicitudesDeAnularModificar(String user);
	 
	 /**
 	 * Buscar solicitudes de caratulacion por usuario.
 	 *
 	 * @param user the user
 	 * @return the list
 	 */
 	public List<Task> buscarSolicitudesDeCaratulacionPorUsuario(String user);
 	
 	/**
	 * Buscar tareas por usuario.
	 *
	 * @param parametrosConsulta the parametros consulta 
	 * @return the list
	 */
	public List<?> buscarAllTareasPorUsuario(Map <String, Object> parametrosConsulta, Integer pageSize);
	
	/**
	 * Buscar los documentos asociados al numeroSade.
	 *
	 * @param user user
	 * @param numeroSade numeroSade
	 * @return the DocumentListAppDTO
	 */
	public DocumentUnqtAppDTO buscarDocument(String user, String numeroSade);
	
	
}