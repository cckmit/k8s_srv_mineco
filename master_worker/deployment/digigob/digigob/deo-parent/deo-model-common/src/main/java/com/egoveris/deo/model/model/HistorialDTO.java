package com.egoveris.deo.model.model;

import java.io.Serializable;
import java.util.Date;

public class HistorialDTO implements Serializable {
  private static final long serialVersionUID = 9066910749498778259L;
  private Integer id;
  private String usuario;
  private String actividad;
  private Date fechaFin;
  private String mensaje;
  private String workflowOrigen;
  private Date fechaInicio;

  public HistorialDTO(){}
  
  public HistorialDTO(String usuario, String actividad, String workflowOrigen) {
    super();
    this.usuario = usuario;
    this.actividad = actividad;
    this.workflowOrigen = workflowOrigen;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  public String getActividad() {
    return actividad;
  }

  public void setActividad(String actividad) {
    this.actividad = actividad;
  }

  public Date getFechaFin() {
    return fechaFin;
  }

  public void setFechaFin(Date fechaFin) {
    this.fechaFin = fechaFin;
  }

  public String getMensaje() {
    return mensaje;
  }

  public void setMensaje(String mensaje) {
    this.mensaje = mensaje;
  }

  public String getWorkflowOrigen() {
    return workflowOrigen;
  }

  public void setWorkflowOrigen(String workflowOrigen) {
    this.workflowOrigen = workflowOrigen;
  }

  public Date getFechaInicio() {
    return fechaInicio;
  }

  public void setFechaInicio(Date fechaInicio) {
    this.fechaInicio = fechaInicio;
  }

}
