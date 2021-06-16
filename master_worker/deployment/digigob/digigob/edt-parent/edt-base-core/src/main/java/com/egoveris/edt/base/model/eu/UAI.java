package com.egoveris.edt.base.model.eu;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

// It's being used?
@Entity
@Table(name = "edt_sade_uai")
public class UAI {

  @Id
  @Column(name = "id_reparticion")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "codigo", nullable = false, unique = true)
  private String codigo;

  @Column(name = "descripcion", nullable = false)
  private String descripcion;

  @Column(name = "fecha_creacion", nullable = false)
  private Date fechaCreacion;

  @Column(name = "usuario_creacion", nullable = false)
  private String usuarioCreacion;

  @Column(name = "fecha_modificacion", nullable = false)
  private Date fechaModificacion;

  @Column(name = "usuario_modificacion", nullable = false)
  private String usuarioModificacion;

  @Column(name = "estado_registro", nullable = false)
  private String estadoRegistro;

  @Column(name = "version")
  private Integer version;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getCodigo() {
    return codigo;
  }

  public void setCodigo(String codigo) {
    this.codigo = codigo;
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

  public String getUsuarioCreacion() {
    return usuarioCreacion;
  }

  public void setUsuarioCreacion(String usuarioCreacion) {
    this.usuarioCreacion = usuarioCreacion;
  }

  public Date getFechaModificacion() {
    return fechaModificacion;
  }

  public void setFechaModificacion(Date fechaModificacion) {
    this.fechaModificacion = fechaModificacion;
  }

  public String getUsuarioModificacion() {
    return usuarioModificacion;
  }

  public void setUsuarioModificacion(String usuarioModificacion) {
    this.usuarioModificacion = usuarioModificacion;
  }

  public String getEstadoRegistro() {
    return estadoRegistro;
  }

  public void setEstadoRegistro(String estadoRegistro) {
    this.estadoRegistro = estadoRegistro;
  }

  public Integer getVersion() {
    return version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

}