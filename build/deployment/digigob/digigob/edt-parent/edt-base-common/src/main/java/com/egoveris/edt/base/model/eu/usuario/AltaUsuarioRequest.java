package com.egoveris.edt.base.model.eu.usuario;

import java.io.Serializable;
import java.util.List;

public class AltaUsuarioRequest implements Serializable {

  private static final long serialVersionUID = 5201820957256410979L;

  private String username;
  private String codigoSectorinterno;
  private String codigoReparticion;
  private String cargo;
  private String eMail;
  private String cuil;
  private String legajo;
  private String nombre;
  private List<String> permisos;
  private String uuid;

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

  public void setCuil(String cuil) {
    this.cuil = cuil;
  }

  public String getCuil() {
    return cuil;
  }

  public void setLegajo(String legajo) {
    this.legajo = legajo;
  }

  public String getLegajo() {
    return legajo;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getNombre() {
    return nombre;
  }

  public void setPermisos(List<String> permisos) {
    this.permisos = permisos;
  }

  public List<String> getPermisos() {
    return permisos;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public String getUuid() {
    return uuid;
  }

}
