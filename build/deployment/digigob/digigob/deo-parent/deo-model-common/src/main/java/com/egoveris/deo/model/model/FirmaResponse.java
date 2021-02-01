package com.egoveris.deo.model.model;

import java.io.Serializable;

public class FirmaResponse implements Serializable {

  private static final long serialVersionUID = 1L;

  private String nroSade;
  private String nroEspecial;
  private String nroSadeRectif;
  private String estado;

  public String getNroSade() {
    return nroSade;
  }

  public String getNroSadeRectif() {
    return nroSadeRectif;
  }

  public String getEstado() {
    return estado;
  }

  public void setNroSade(String nroSade) {
    this.nroSade = nroSade;
  }

  public void setNroSadeRectif(String nroSadeRectif) {
    this.nroSadeRectif = nroSadeRectif;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public String getNroEspecial() {
    return nroEspecial;
  }

  public void setNroEspecial(String nroEspecial) {
    this.nroEspecial = nroEspecial;
  }
}
