package com.egoveris.edt.base.model;

import java.util.List;

public class SupervisadosBean {

  private String nombreSupervisado;
  private List<TareasPorSistemaBean> listaTareasPorSistema;

  public List<TareasPorSistemaBean> getListaTareasPorSistema() {
    return listaTareasPorSistema;
  }

  public void setListaTareasPorSistema(List<TareasPorSistemaBean> listaTareasPorSistema) {
    this.listaTareasPorSistema = listaTareasPorSistema;
  }

  public String getNombreSupervisado() {
    return nombreSupervisado;
  }

  public void setNombreSupervisado(String nombreSupervisado) {
    this.nombreSupervisado = nombreSupervisado;
  }

}
