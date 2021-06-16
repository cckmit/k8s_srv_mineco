package com.egoveris.edt.base.service.vista;

import com.egoveris.edt.base.model.TareasPorSistemaBean;
import com.egoveris.edt.base.model.eu.usuario.UsuarioFrecuenciaDTO;

import java.util.List;
import java.util.Map;

/**
 * The Interface IVistaBuzonGrupalService.
 */
public interface IVistaBuzonGrupalService {

  /**
   * Obtener vista tareas.
   *
   * @param listaIdsAplicacionesMisTareas
   *          the lista ids aplicaciones mis tareas
   * @param userName
   *          the user name
   * @param usuarioFrecuencia
   *          the usuario frecuencia
   * @return the list
   */
  List<TareasPorSistemaBean> obtenerVistaTareas(
      Map<Integer, List<String>> listaIdsAplicacionesMisTareas, String userName,
      UsuarioFrecuenciaDTO usuarioFrecuencia);

}