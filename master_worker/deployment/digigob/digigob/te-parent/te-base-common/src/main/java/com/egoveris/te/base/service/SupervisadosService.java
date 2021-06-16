package com.egoveris.te.base.service;

import java.util.List;

import org.jbpm.api.task.Task;

import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.te.base.model.TareasUsuario;

 

/**
 * The Interface SupervisadosService.
 */
public interface SupervisadosService {
	/**
	 * Obtiene la lista de los resúmenes de tareas correpondientes a los
	 * supervisados por el usuario pasado como argumento.
	 * 
	 * @param username Usuario del cual buscar los resúmenes de tareas de sus supervisados.
	 * @return Una lista con el resultado o una lista vacía si el usuario no tiene supervisados
	 */
	public List<TareasUsuario> obtenerResumenTareasSupervisados(String username);
	
	/**
	 * Cancela (termina el workflow) de la tarea pasada como argumento.
	 * @param tarea Instancia de la tarea de la cual la instancia del proceso asociada se cancelará.
	 */
	public void cancelarTarea(Task tarea);

	/**
	 * Asigna la tarea enviada como argumento al usuario pasado como parámetro. Si el usuario es null o vacío deja la tarea en el mismo.
	 *
	 * @param task Tarea a reasignar
	 * @param username Usuario al que se le reasignará la tarea
	 * @param usuarioSupervisado the usuario supervisado
	 * @param motivo the motivo
	 * @param usuarioLogin the usuario login
	 */
	public void reasignarTarea(Task task, String username, String usuarioSupervisado, String motivo, String usuarioLogin);
	
	/**
	 * Obtiene los datos de un supervisado específico.
	 * @param username Username del superior
	 * @param usernameSupervisado Username del supevisado a obtener los datos
	 * @return El objeto que representa al supervisado o <b>null</b> si no existe como supervisado de username
	 */
	public Usuario getSupervisado(String username, String usernameSupervisado);
	
	/**
	 * Obtiene la lista usuarios por sector.
	 * 
	 * @param sector sector del expiente.
	 * @return Una lista con el resultado o una lista vacía si el usuario no tiene supervisados
	 */
	public List<TareasUsuario> obtenerUsuarioPorSector(String sector);
}
