package com.egoveris.deo.base.service;

import com.egoveris.deo.model.exception.NegocioException;
import com.egoveris.deo.model.model.TareasUsuarioDTO;
import com.egoveris.sharedsecurity.base.model.Usuario;

import java.util.List;

import org.jbpm.api.task.Task;
import org.terasoluna.plus.common.exception.ApplicationException;


public interface ISupervisadosService {
  /**
   * Obtiene la lista de los resúmenes de tareas correpondientes a los
   * supervisados por el usuario pasado como argumento.
   * 
   * @param username
   *          Usuario del cual buscar los resúmenes de tareas de sus
   *          supervisados.
   * @return Una lista con el resultado o una lista vacía si el usuario no tiene
   *         supervisados
   * @throws NegocioException
   *           On SQL error
   */
  public List<TareasUsuarioDTO> obtenerResumenTareasSupervisados(String username)
      throws NegocioException;

  /**
   * Obtiene la lista de los resúmenes de tareas correpondientes a los
   * supervisados por el usuario pasado como argumento.
   * 
   * @param username
   * @return
   */
  public List<Task> obtenerTareasSupervisadosCCOO(String username);

  /**
   * Obtiene la lista de los resúmenes de tareas correpondientes a los
   * supervisados por el usuario pasado como argumento. Con tipos de documento
   * comunicables
   * 
   * @param username
   *          Usuario del cual buscar los resúmenes de tareas de sus
   *          supervisados.
   * @return Una lista con el resultado o una lista vacía si el usuario no tiene
   *         supervisados
   * @throws NegocioException
   */
  public List<TareasUsuarioDTO> obtenerResumenTareasSupervisadosCCOO(String username)
      throws NegocioException;

  /**
   * Cancela (termina el workflow) de la tarea pasada como argumento.
   * 
   * @param tarea
   *          Instancia de la tarea de la cual la instancia del proceso asociada
   *          se cancelará.
   * @throws Exception
   */
  public void cancelarTarea(Task tarea, String usuarioCancelacion) throws ApplicationException;

  /**
   * Asigna la tarea enviada como argumento al usuario pasado como parámetro. Si
   * el usuario es null o vacío deja la tarea en el mismo.
   * 
   * @param task
   *          Tarea a reasignar
   * @param username
   *          Usuario al que se le reasignará la tarea
   */
  public void reasignarTarea(Task task, String username);

  /**
   * Obtiene la lista de supervisados por el usuario <b>username</b>
   * 
   * @param username
   * @return La lista de supervisados del usuario o una lista vacía si el mismo
   *         no tiene supervisados
   * @throws NegocioException
   */
  public List<Usuario> obtenerListaSupervisados(String username) throws NegocioException;

  /**
   * Obtiene la lista de usuarios jefe del usuario <b>username</b>
   * 
   * @param username
   * @return La lista de jefes del usuario o una lista vacía si el mismo no
   *         tiene jefes
   * @throws NegocioException
   */
  public List<Usuario> obtenerUsuariosJefe(String username) throws NegocioException;

  /**
   * Obtiene los datos de un supervisado específico.
   * 
   * @param username
   *          Username del superior
   * @param usernameSupervisado
   *          Username del supevisado a obtener los datos
   * @return El objeto que representa al supervisado o <b>null</b> si no existe
   *         como supervisado de username
   * @throws NegocioException
   */
  public Usuario getSupervisado(String username, String usernameSupervisado)
      throws NegocioException;
}
