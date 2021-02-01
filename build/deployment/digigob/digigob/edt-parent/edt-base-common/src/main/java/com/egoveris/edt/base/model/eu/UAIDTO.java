package com.egoveris.edt.base.model.eu;

import java.io.Serializable;
import java.util.Date;

public class UAIDTO implements Serializable {

  private static final long serialVersionUID = 3927750038034730154L;

  private Integer id;
  private String codigo;
  private String descripcion;
  private Date fechaCreacion;
  private String usuarioCreacion;
  private Date fechaModificacion;
  private String usuarioModificacion;
  private String estadoRegistro;
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

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("UAIDTO [id=").append(id).append(", codigo=").append(codigo)
        .append(", descripcion=").append(descripcion).append(", fechaCreacion=")
        .append(fechaCreacion).append(", usuarioCreacion=").append(usuarioCreacion)
        .append(", fechaModificacion=").append(fechaModificacion).append(", usuarioModificacion=")
        .append(usuarioModificacion).append(", estadoRegistro=").append(estadoRegistro)
        .append(", version=").append(version).append("]");
    return builder.toString();
  }

}