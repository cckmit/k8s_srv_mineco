package com.egoveris.deo.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "GEDO_AUD_ERROR_CAMBIO_SIGLA")
public class TareaCambioDeSiglaError {

  @Id
  @Column(name = "ID")
  private Integer id;

  @Column(name = "ID_TAREA")
  private Integer idTarea;

  @Column(name = "ERROR")
  private String error;

  @Column(name = "FECHA")
  private Date fecha;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getIdTarea() {
    return idTarea;
  }

  public void setIdTarea(Integer idTarea) {
    this.idTarea = idTarea;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  public Date getFecha() {
    return fecha;
  }

  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }

}
