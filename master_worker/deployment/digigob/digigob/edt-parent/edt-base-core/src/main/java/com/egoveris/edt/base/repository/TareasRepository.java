package com.egoveris.edt.base.repository;

import java.util.Date;
import java.util.List;

/**
 * The Interface ITareasDAO.
 *
 * @author pfolgar
 */
public interface TareasRepository {

  /**
   * Este metodo busca todas las tareas pendientes para el usuario pasado como
   * parametro.
   *
   * @param username
   *          the username
   * @return devuelve una lista de fechas de las tareas pendientes, o una lista
   *         vacia si no tiene tareas pendientes
   */
  public List<Date> buscarTareasPorUsuarioSistemas(String username);

  /**
   * Buscar tareas buzon grupal.
   *
   * @param userName
   *          the user name
   * @param object
   *          the object
   * @return the list
   */
  public List<Date> buscarTareasBuzonGrupal(String userName, List<String> object);

}
