package com.egoveris.edt.base.service;

/**
 * Interface correspondiente a las clases encargadas de crear o mantener un
 * registro de beans ITareasService que la aplicación requiere para obtener
 * tareas y buzones grupales con lógica puntual de cada sistema integrado en EU.
 *
 * @author alelarre
 */
public interface ITareasServiceFactory {

  /**
   * Obtiene el bean correspondiente al sistema pasado por parámetro.
   *
   * @param sistemName
   *          the sistem name
   * @return El bean correspondiente al sistema o nulo si no existe
   */
  public ITareasService get(String sistemName);
}
