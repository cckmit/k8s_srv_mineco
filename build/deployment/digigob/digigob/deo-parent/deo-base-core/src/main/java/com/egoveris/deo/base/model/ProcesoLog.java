package com.egoveris.deo.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "GEDO_PROCESO_LOG")
public class ProcesoLog {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "PROCESO")
  private String proceso;

  @Column(name = "WORKFLOWID")
  private String workflowId;

  @Column(name = "SISTEMA_ORIGEN")
  private String sistemaOrigen;

  @Column(name = "ESTADO")
  private String estado;

  @Column(name = "DESCRIPCION")
  private String descripcion;

  @Column(name = "FECHA_CREACION")
  private Date fechaCreacion;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getProceso() {
    return proceso;
  }

  public void setProceso(String proceso) {
    this.proceso = proceso;
  }

  public String getWorkflowId() {
    return workflowId;
  }

  public void setWorkflowId(String workflowId) {
    this.workflowId = workflowId;
  }

  public String getSistemaOrigen() {
    return sistemaOrigen;
  }

  public void setSistemaOrigen(String sistemaOrigen) {
    this.sistemaOrigen = sistemaOrigen;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public Date getFechaCreacion() {
    return fechaCreacion;
  }

  public void setFechaCreacion(Date fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }

}
