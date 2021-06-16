package com.egoveris.edt.base.service;

import java.util.List;

public interface ISupervisadosService {

  /**
   * Este metddo busca los supervisados para un usuario.
   *
   * @param userName
   *          the user name
   * @return una lista con los usuarios subordinados al usuario logueado o una
   *         lista vacia
   */
  List<String> obtenerSupervisadosParaUsuario(String userName);

}