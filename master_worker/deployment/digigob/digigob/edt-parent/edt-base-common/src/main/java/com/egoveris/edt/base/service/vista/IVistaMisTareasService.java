package com.egoveris.edt.base.service.vista;

import com.egoveris.edt.base.model.TareasPorSistemaBean;
import com.egoveris.edt.base.model.eu.usuario.UsuarioFrecuenciaDTO;

import java.util.List;

public interface IVistaMisTareasService {

  /**
   * Este metodo devuelve la vista de mis tareas para un usuario.
   *
   * @param listaIdsAplicaciones
   *          the lista ids aplicaciones
   * @param userName
   *          the user name
   * @param usuarioFrecuencia
   *          the usuario frecuencia
   * @return una lista de tareas que conforman la vista de tareas para el
   *         usuario o una lista vacia si no tiene tareas configuradas
   */
  public List<TareasPorSistemaBean> obtenerVistaMisTareas(List<Integer> listaIdsAplicaciones,
      String userName, UsuarioFrecuenciaDTO usuarioFrecuencia);

}
