package com.egoveris.edt.base.model;

import java.io.Serializable;
import java.util.Date;

public class EscritorioUnicoIndexDTO implements Serializable {

  private static final long serialVersionUID = -6926889660223482624L;

  private String sistema;
  private Long numero;
  private String usuario;
  private Integer anio;
  private String reparticionUsuario;
  private String reparticionActuacion;
  private String secuencia;
  private Date fechaCreacion;
  private String estado;
  private String tipoActuacion;
  private Date fechaCreacionCaratula;
  private String sectorInterno;

  public String getSistema() {
    return sistema;
  }

  public void setSistema(String sistema) {
    this.sistema = sistema;
  }

  public Long getNumero() {
    return numero;
  }

  public void setNumero(Long numero) {
    this.numero = numero;
  }

  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  public Integer getAnio() {
    return anio;
  }

  public void setAnio(Integer anio) {
    this.anio = anio;
  }

  public String getReparticionUsuario() {
    return reparticionUsuario;
  }

  public void setReparticionUsuario(String reparticionUsuario) {
    this.reparticionUsuario = reparticionUsuario;
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

  public Date getFechaCreacion() {
    return fechaCreacion;
  }

  public void setFechaCreacion(Date fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public String getTipoActuacion() {
    return tipoActuacion;
  }

  public void setTipoActuacion(String tipoActuacion) {
    this.tipoActuacion = tipoActuacion;
  }

  public Date getFechaCreacionCaratula() {
    return fechaCreacionCaratula;
  }

  public void setFechaCreacionCaratula(Date fechaCreacionCaratula) {
    this.fechaCreacionCaratula = fechaCreacionCaratula;
  }

  public String getSectorInterno() {
    return sectorInterno;
  }

  public void setSectorInterno(String sectorInterno) {
    this.sectorInterno = sectorInterno;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("EscritorioUnicoIndexDTO [sistema=").append(sistema).append(", numero=")
        .append(numero).append(", usuario=").append(usuario).append(", anio=").append(anio)
        .append(", reparticionUsuario=").append(reparticionUsuario)
        .append(", reparticionActuacion=").append(reparticionActuacion).append(", secuencia=")
        .append(secuencia).append(", fechaCreacion=").append(fechaCreacion).append(", estado=")
        .append(estado).append(", tipoActuacion=").append(tipoActuacion)
        .append(", fechaCreacionCaratula=").append(fechaCreacionCaratula)
        .append(", sectorInterno=").append(sectorInterno).append("]");
    return builder.toString();
  }

}