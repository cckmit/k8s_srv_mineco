package com.egoveris.edt.base.service.vista;

import com.egoveris.edt.base.model.SupervisadosBean;
import com.egoveris.edt.base.model.eu.usuario.UsuarioFrecuenciaDTO;

import java.util.List;

public interface IVistaMisSupervisadosService {

  /**
   * Este metodo devuelve la vista de mis supervisados para un usuario.
   *
   * @param listaIdsAplicaciones
   *          the lista ids aplicaciones
   * @param userName
   *          the user name
   * @param usuarioFrecuencia
   *          the usuario frecuencia
   * @param usuariosSubordinados
   *          the usuarios subordinados
   * @return una lista de aplicaciones que conforman la vista de tareas para el
   *         supervisado del usuario o una lista vacia si los subordinados del
   *         usuario no tienen tareas configuradas o si el usuario no tiene
   *         subordinados
   */
  List<SupervisadosBean> obtenerVistaMisSistemas(List<Integer> listaIdsAplicaciones,
      String userName, UsuarioFrecuenciaDTO usuarioFrecuencia, List<String> usuariosSubordinados);

}
