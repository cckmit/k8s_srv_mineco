package com.egoveris.edt.base.model.eu.usuario;

import java.io.Serializable;

public class AltaUsuarioResponse implements Serializable {

  private static final long serialVersionUID = 3661304283072793013L;
  private String respuesta;
  private String username;
  private String codigoSectorinterno;
  private String codigoReparticion;
  private String cargo;
  private String eMail;

  public void setRespuesta(String respuesta) {
    this.respuesta = respuesta;
  }

  public String getRespuesta() {
    return respuesta;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getUsername() {
    return username;
  }

  public void setCodigoSectorinterno(String codigoSectorinterno) {
    this.codigoSectorinterno = codigoSectorinterno;
  }

  public String getCodigoSectorinterno() {
    return codigoSectorinterno;
  }

  public void setCargo(String cargo) {
    this.cargo = cargo;
  }

  public String getCargo() {
    return cargo;
  }

  public void seteMail(String eMail) {
    this.eMail = eMail;
  }

  public String geteMail() {
    return eMail;
  }

  public void setCodigoReparticion(String codigoReparticion) {
    this.codigoReparticion = codigoReparticion;
  }

  public String getCodigoReparticion() {
    return codigoReparticion;
  }

}
