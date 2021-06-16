package com.egoveris.deo.model.model;

import java.io.Serializable;
import java.util.List;

public class RequestExternalDepuracionDocumento implements Serializable {
  private static final long serialVersionUID = -9105669278907347808L;

  private List<String> listaNroSADE;
  private String motivoDepuracion;

  public String getMotivoDepuracion() {
    return motivoDepuracion;
  }

  public void setMotivoDepuracion(String motivoDepuracion) {
    this.motivoDepuracion = motivoDepuracion;
  }

  public List<String> getListaNroSADE() {
    return listaNroSADE;
  }

  public void setListaNroSADE(List<String> listaNroSADE) {
    this.listaNroSADE = listaNroSADE;
  }

}
