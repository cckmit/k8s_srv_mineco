package com.egoveris.edt.base.model.eu;

import java.io.Serializable;
import java.util.Date;

public class PeriodoLicenciaDTO implements Serializable {

  private static final long serialVersionUID = -1980155715722219082L;

  public static final String TERMINADO = "terminado";
  public static final String PENDIENTE = "pendiente";
  public static final String ACTIVO = "activo";

  private Integer id;
  private Date fechaHoraDesde;
  private Date fechaHoraHasta;
  private String usuario;
  private String apoderado;
  private String condicionPeriodo;
  private Date fechaCancelacion;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Date getFechaHoraDesde() {
    return fechaHoraDesde;
  }

  public void setFechaHoraDesde(Date fechaHoraDesde) {
    this.fechaHoraDesde = fechaHoraDesde;
  }

  public Date getFechaHoraHasta() {
    return fechaHoraHasta;
  }

  public void setFechaHoraHasta(Date fechaHoraHasta) {
    this.fechaHoraHasta = fechaHoraHasta;
  }

  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  public String getApoderado() {
    return apoderado;
  }

  public void setApoderado(String apoderado) {
    this.apoderado = apoderado;
  }

  public String getCondicionPeriodo() {
    return condicionPeriodo;
  }

  public void setCondicionPeriodo(String condicionPeriodo) {
    this.condicionPeriodo = condicionPeriodo;
  }

  public Date getFechaCancelacion() {
    return fechaCancelacion;
  }

  public void setFechaCancelacion(Date fechaCancelacion) {
    this.fechaCancelacion = fechaCancelacion;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("PeriodoLicenciaDTO [id=").append(id).append(", fechaHoraDesde=")
        .append(fechaHoraDesde).append(", fechaHoraHasta=").append(fechaHoraHasta)
        .append(", usuario=").append(usuario).append(", apoderado=").append(apoderado)
        .append(", condicionPeriodo=").append(condicionPeriodo).append(", fechaCancelacion=")
        .append(fechaCancelacion).append("]");
    return builder.toString();
  }

}