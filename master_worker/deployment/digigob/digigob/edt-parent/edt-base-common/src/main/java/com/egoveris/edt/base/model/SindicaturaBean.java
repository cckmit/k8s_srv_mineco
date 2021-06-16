package com.egoveris.edt.base.model;

import java.util.Date;

public class SindicaturaBean {

  private String sistema;
  private String usuario;
  private String codigo;
  private int anio;
  private int numero;
  private String reparticionCodigo;
  private String reparticionActuacion;
  private String secuencia;
  private Date fecha;
  private String estado;
  private String sectorUsuario;

  public String getSistema() {
    return sistema;
  }

  public void setSistema(String sistema) {
    this.sistema = sistema;
  }

  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  public String getCodigo() {
    return codigo;
  }

  public void setCodigo(String codigo) {
    this.codigo = codigo;
  }

  public int getAnio() {
    return anio;
  }

  public void setAnio(int anio) {
    this.anio = anio;
  }

  public int getNumero() {
    return numero;
  }

  public void setNumero(int numero) {
    this.numero = numero;
  }

  public String getReparticionCodigo() {
    return reparticionCodigo;
  }

  public void setReparticionCodigo(String reparticionCodigo) {
    this.reparticionCodigo = reparticionCodigo;
  }

  public String getReparticionActuacion() {
    return reparticionActuacion;
  }

  public void setReparticionActuacion(String reparticionActuacion) {
    this.reparticionActuacion = reparticionActuacion;
  }

  public String getSecuencia() {
    return secuencia;
  }

  public void setSecuencia(String secuencia) {
    this.secuencia = secuencia;
  }

  public Date getFecha() {
    return fecha;
  }

  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public void setSectorUsuario(String sectorUsuario) {
    this.sectorUsuario = sectorUsuario;
  }

  public String getSectorUsuario() {
    return sectorUsuario;
  }

}
