package com.egoveris.vucfront.model.model;

import java.util.Date;

public class ApoderadoDTO {

  private Long id;
  private Boolean estado;
  private Date fechaVencimiento;
  private Long idApoderado;
  private Long idTitular;
  private String tipoRepresentacion;
  private Long version;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Boolean getEstado() {
    return estado;
  }

  public void setEstado(Boolean estado) {
    this.estado = estado;
  }

  public Date getFechaVencimiento() {
    return fechaVencimiento;
  }

  public void setFechaVencimiento(Date fechaVencimiento) {
    this.fechaVencimiento = fechaVencimiento;
  }

  public Long getIdApoderado() {
    return idApoderado;
  }

  public void setIdApoderado(Long idApoderado) {
    this.idApoderado = idApoderado;
  }

  public Long getIdTitular() {
    return idTitular;
  }

  public void setIdTitular(Long idTitular) {
    this.idTitular = idTitular;
  }

  public String getTipoRepresentacion() {
    return tipoRepresentacion;
  }

  public void setTipoRepresentacion(String tipoRepresentacion) {
    this.tipoRepresentacion = tipoRepresentacion;
  }

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ApoderadoDTO [id=").append(id).append(", estado=").append(estado)
        .append(", fechaVencimiento=").append(fechaVencimiento).append(", idApoderado=")
        .append(idApoderado).append(", idTitular=").append(idTitular)
        .append(", tipoRepresentacion=").append(tipoRepresentacion).append(", version=")
        .append(version).append("]");
    return builder.toString();
  }

}