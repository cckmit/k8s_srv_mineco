package com.egoveris.deo.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "GEDO_ERROR_REINTENTO")
public class ErrorReintento {

  @Id
  @Column(name = "ID")
  private Integer id;

  @Column(name = "NOMBRE")
  private String nombre;

  @Column(name = "DESCRIPCION")
  private String descripcion;

  @Column(name = "ES_REINTENTO")
  private Boolean esReintento;

  @Column(name = "FECHA_CREACION")
  private Date fechaCreacion;

  @Column(name = "FECHA_MODIFICACION")
  private Date fechaModificacion;

  @Column(name = "USUARIO_ALTA")
  private String usuarioAlta;

  @Column(name = "USUARIO_MODIFICACION")
  private String usuarioModificacion;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public Boolean getEsReintento() {
    return esReintento;
  }

  public void setEsReintento(Boolean esReintento) {
    this.esReintento = esReintento;
  }

  public Date getFechaCreacion() {
    return fechaCreacion;
  }

  public void setFechaCreacion(Date fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }

  public Date getFechaModificacion() {
    return fechaModificacion;
  }

  public void setFechaModificacion(Date fechaModificacion) {
    this.fechaModificacion = fechaModificacion;
  }

  public String getUsuarioAlta() {
    return usuarioAlta;
  }

  public void setUsuarioAlta(String usuarioAlta) {
    this.usuarioAlta = usuarioAlta;
  }

  public String getUsuarioModificacion() {
    return usuarioModificacion;
  }

  public void setUsuarioModificacion(String usuarioModificacion) {
    this.usuarioModificacion = usuarioModificacion;
  }

}
