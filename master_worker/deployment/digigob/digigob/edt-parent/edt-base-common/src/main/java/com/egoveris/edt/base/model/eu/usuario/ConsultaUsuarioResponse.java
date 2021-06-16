package com.egoveris.edt.base.model.eu.usuario;

import java.io.Serializable;
import java.util.Date;

public class ConsultaUsuarioResponse implements Serializable {

  private static final long serialVersionUID = 2887905961271629986L;

  private String apellidoNombre;
  private String usuario;
  private String reparticion;
  private String sector;
  private String jurisdiccion;
  private String cuitCuil;
  private String cargo;
  private Date fechaAlta;
  private Date fechaBaja;
  private String email;

  public String getApellidoNombre() {
    return apellidoNombre;
  }

  public void setApellidoNombre(String apellidoNombre) {
    this.apellidoNombre = apellidoNombre;
  }

  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  public String getReparticion() {
    return reparticion;
  }

  public void setReparticion(String reparticion) {
    this.reparticion = reparticion;
  }

  public String getSector() {
    return sector;
  }

  public void setSector(String sector) {
    this.sector = sector;
  }

  public String getJurisdiccion() {
    return jurisdiccion;
  }

  public void setJurisdiccion(String jurisdiccion) {
    this.jurisdiccion = jurisdiccion;
  }

  public String getCuitCuil() {
    return cuitCuil;
  }

  public void setCuitCuil(String cuitCuil) {
    this.cuitCuil = cuitCuil;
  }

  public String getCargo() {
    return cargo;
  }

  public void setCargo(String cargo) {
    this.cargo = cargo;
  }

  public Date getFechaAlta() {
    return fechaAlta;
  }

  public void setFechaAlta(Date fechaAlta) {
    this.fechaAlta = fechaAlta;
  }

  public Date getFechaBaja() {
    return fechaBaja;
  }

  public void setFechaBaja(Date fechaBaja) {
    this.fechaBaja = fechaBaja;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

}