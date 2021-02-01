package com.egoveris.edt.base.service;

import java.util.Date;
import java.util.List;

/**
 * Interface que agrupa operaciones relacionadas con la obtención de tareas y de
 * otros atributos propios de cada aplicación requeridas para la presentación de
 * las distintas vistas.
 * 
 * Cada aplicación debe implementar esta interface, o usar las implementaciones
 * básicas ya existentes.
 * 
 * @author pfolgar
 * 
 */
public interface ITareasService {

  /**
   * Este metodo busca todas las tareas pendientes para el sistema y usuario
   * pasado como parametro.
   *
   * @param username
   *          the username
   * @return devuelve una lista de fechas de las tareas pendientes, o una lista
   *         vacia si no tiene tareas pendientes
   */
  List<Date> buscarTareasPorUsuario(String username);

  /**
   * Busca todas las tareas del usuario para los grupos que dicho usuario posee
   * en la aplicación Los grupos son enviados por parámetro, para permitir
   * tenerlos en caché en vez de calcularlos cada vez, que es una operación
   * posiblemente intensiva.
   *
   * @param userName
   *          the user name
   * @param grupos
   *          the grupos
   * @return the list
   */
  List<Date> buscarTareasBuzonGrupal(String userName, List<String> grupos);

  /**
   * Calcula los grupos a los que pertenece un usuario en una aplicación. No son
   * los permisos ni los roles generales del usuario, sino específicamente
   * aquellos grupos que son tenidos en cuenta para calcular sus tareas de
   * buzones grupales.
   *
   * @param user
   *          the user
   * @return the list
   */
  List<String> buscarGruposUsuarioAplicacion(String user);

}
