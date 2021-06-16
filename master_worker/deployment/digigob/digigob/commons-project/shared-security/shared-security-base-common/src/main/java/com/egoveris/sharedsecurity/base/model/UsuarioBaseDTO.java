package com.egoveris.sharedsecurity.base.model;

import java.io.Serializable;
import java.util.List;

public class UsuarioBaseDTO implements Serializable {

  private static final long serialVersionUID = 2940630608303604984L;

  private String nombre;
  private String mail;
  private String uid;
  private String legajo;
  private String password;
  private List<String> permisos;

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getMail() {
    return mail;
  }

  public void setMail(String mail) {
    this.mail = mail;
  }

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public String getLegajo() {
    return legajo;
  }

  public void setLegajo(String legajo) {
    this.legajo = legajo;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public List<String> getPermisos() {
    return permisos;
  }

  public void setPermisos(List<String> permisos) {
    this.permisos = permisos;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("UsuarioSadeDTO [nombre=").append(nombre).append(", mail=").append(mail)
        .append(", uid=").append(uid).append(", legajo=").append(legajo).append(", password=")
        .append(password).append(", permisos=").append(permisos).append("]");
    return builder.toString();
  }

}