package com.egoveris.edt.base.model.eu.usuario;

import java.io.Serializable;

public class UsuarioGenericoDTO implements Serializable {

  private static final long serialVersionUID = -6560154408736445086L;
  private int id;
  private int aplicacionID;
  private String usuario;

  public int getAplicacionID() {
    return aplicacionID;
  }

  public void setAplicacionID(int aplicacionID) {
    this.aplicacionID = aplicacionID;
  }

  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

}