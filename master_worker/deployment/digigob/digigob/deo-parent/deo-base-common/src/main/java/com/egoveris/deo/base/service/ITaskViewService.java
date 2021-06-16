/**
 * 
 */
package com.egoveris.deo.base.service;

import com.egoveris.deo.model.model.TaskViewDTO;

import java.util.List;
import java.util.Map;
import org.jbpm.api.task.Task;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author pfolgar
 *
 */
public interface ITaskViewService {

  /**
   * Este metodo crea una lista de TaskView a partir de una lista de Tareas de
   * JBPM
   * 
   * @param jbpmTaskList
   * @return una lista de TaskView
   */
  public List<TaskViewDTO> getTaskViewList(List<Task> jbpmTaskList);

  /**
   * Carga Tasks de JBPM, filtrando por usuario.
   * 
   * @param usuario:
   *          Usuario.
   * @param parametrosOrden:
   *          Es un map que contiene los valores correspondientes a: inicioCarga
   *          tamanioPaginacion criterio: Campo a ordenar orden:
   *          Ascendente/Descendente, para efectos de paginación y ordenamiento
   *          desde base de datos.
   * @return
   */
  public List<Task> buscarTasksPorUsuario(Map<String, Object> parametrosConsulta,
      Map<String, String> parametrosOrden);

  /**
   * Retorna la cantidad Tasks para este usuario
   * 
   * @param usuario:
   *          Usuario.
   * @return El número de Tasks para este usuario.
   */
  public int countTasks(String usuario);

  /**
   * Retorna la cantidad Tasks con documentos comunicables para este usuario
   * 
   * @param usuario:
   *          Usuario.
   * @return El número de Tasks con docs comunicables para este usuario.
   */
  @Transactional
  public int countTasksComunicables(String usuarioActual);

  /**
   * Retorna uan lista de tasks por un determinado EXECUTION_ID_.
   * 
   * @param workflowId
   * @return
   */
  public List<Task> buscarTask(String workflowId);

  @Transactional
  public List<Task> buscarTasksPorUsuarioYComunicable(Map<String, Object> parametrosConsulta,
      Map<String, String> parametrosOrden);

}
