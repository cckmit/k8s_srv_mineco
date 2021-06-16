package com.egoveris.edt.base.service;

import com.egoveris.edt.base.model.SupervisadosBean;
import com.egoveris.edt.base.model.eu.AplicacionDTO;

import java.util.HashMap;
import java.util.List;

public interface IAplicacionMisSubordinadosService extends IAplicacionService {

  /**
   * Este metodo devuelve un hashmap con las aplicaciones asociadas a cada
   * subordinado de un usuario
   * 
   * @param userName
   * @return un hashmap de usuarios con sus aplicaciones o null
   */
  public HashMap<String, List<AplicacionDTO>> buscarAplicacionesSubordinados(String userName);

  /**
   * Este metodo busca las tareas pendientes de los subordinados de un usuario
   *
   * @param userName
   * @return una lista con las tareas de los subordinados de un usuario o una
   *         lista vacia
   */
  public List<SupervisadosBean> buscarTareasPendientesSubordinados(String userName);

}
