package com.egoveris.edt.base.service.vista;

import com.egoveris.edt.base.model.eu.AplicacionDTO;

import java.util.List;

public interface IVistaMisSistemasService {

  /**
   * Este metodo devuelve la vista de mis sistemas para un usuario.
   *
   * @param listaIdsAplicaciones
   *          the lista ids aplicaciones
   * @param userName
   *          the user name
   * @return una lista de aplicaciones que conforman la vista de tareas para el
   *         usuario o una lista vacia si no tiene aplicaciones configuradas
   */
  List<AplicacionDTO> obtenerVistaMisSistemas(List<Integer> listaIdsAplicaciones, String userName);

}
