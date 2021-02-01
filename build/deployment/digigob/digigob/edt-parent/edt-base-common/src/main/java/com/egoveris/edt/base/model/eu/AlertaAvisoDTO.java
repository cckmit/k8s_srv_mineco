package com.egoveris.edt.base.model.eu;

import java.io.Serializable;
import java.util.Date;

public class AlertaAvisoDTO implements Serializable {

  private static final long serialVersionUID = -1836903086417985471L;

  private Integer id;
  private AplicacionDTO aplicacion;
  private String referencia;
  private String motivo;
  private String userName;
  private Date fechaCreacion;
  private String estado;
  private String nroGedo;
  private Boolean redirigible;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public AplicacionDTO getAplicacion() {
    return aplicacion;
  }

  public void setAplicacion(AplicacionDTO aplicacion) {
    this.aplicacion = aplicacion;
  }

  public String getReferencia() {
    return referencia;
  }

  public void setReferencia(String referencia) {
    this.referencia = referencia;
  }

  public String getMotivo() {
    return motivo;
  }

  public void setMotivo(String motivo) {
    this.motivo = motivo;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
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

  public String getNroGedo() {
    return nroGedo;
  }

  public void setNroGedo(String nroGedo) {
    this.nroGedo = nroGedo;
  }

  public Boolean getRedirigible() {
    return redirigible;
  }

  public void setRedirigible(Boolean redirigible) {
    this.redirigible = redirigible;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("AlertaAvisoDTO [id=").append(id).append(", aplicacion=").append(aplicacion)
        .append(", referencia=").append(referencia).append(", motivo=").append(motivo)
        .append(", userName=").append(userName).append(", fechaCreacion=").append(fechaCreacion)
        .append(", estado=").append(estado).append(", nroGedo=").append(nroGedo)
        .append(", redirigible=").append(redirigible).append("]");
    return builder.toString();
  }

}