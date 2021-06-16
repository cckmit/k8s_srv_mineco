package com.egoveris.edt.base.model.eu.usuario;

import java.io.Serializable;

public class UsuarioFrecuenciaDTO implements Serializable {

  private static final long serialVersionUID = 3842036940899323466L;
  private int id;
  private String usuario;
  private int frecuenciaMayor;
  private int frecuenciaMedia;
  private int frecuenciaMenor;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  public int getFrecuenciaMayor() {
    return frecuenciaMayor;
  }

  public void setFrecuenciaMayor(int frecuenciaMayor) {
    this.frecuenciaMayor = frecuenciaMayor;
  }

  public int getFrecuenciaMedia() {
    return frecuenciaMedia;
  }

  public void setFrecuenciaMedia(int frecuenciaMedia) {
    this.frecuenciaMedia = frecuenciaMedia;
  }

  public int getFrecuenciaMenor() {
    return frecuenciaMenor;
  }

  public void setFrecuenciaMenor(int frecuenciaMenor) {
    this.frecuenciaMenor = frecuenciaMenor;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("UsuarioFrecuencia [id=").append(id).append(", usuario=").append(usuario)
        .append(", frecuenciaMayor=").append(frecuenciaMayor).append(", frecuenciaMedia=")
        .append(frecuenciaMedia).append(", frecuenciaMenor=").append(frecuenciaMenor).append("]");
    return builder.toString();
  }

}