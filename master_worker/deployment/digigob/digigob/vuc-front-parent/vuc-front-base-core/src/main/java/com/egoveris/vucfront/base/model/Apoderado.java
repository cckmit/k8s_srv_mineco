package com.egoveris.vucfront.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TAD_APODERADO_PERSONA")
public class Apoderado {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "ESTADO")
  private Boolean estado;

  @Column(name = "FECHA_VENCIMIENTO")
  private Date fechaVencimiento;

  @Column(name = "ID_APODERADO")
  private Long idApoderado;

  @Column(name = "ID_TITULAR")
  private Long idTitular;

  @Column(name = "TIPO_REPRESENTACION")
  private String tipoRepresentacion;

  @Column(name = "VERSION")
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

}