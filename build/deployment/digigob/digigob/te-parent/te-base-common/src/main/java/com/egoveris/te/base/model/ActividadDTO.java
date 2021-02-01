package com.egoveris.te.base.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class ActividadDTO implements Serializable {

  private static final long serialVersionUID = -5136557421951116190L;

  private Long id;
  private String estado;
  private String usuarioAlta;
  private String usuarioCierre;
  private String idObjetivo;
  private Long parentId;
  private Date fechaAlta;
  private Date fechaCierre;
  private TipoActividadDTO tipoActividad;
  private Map<String, ParametroActividadDTO> parametros;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public String getUsuarioAlta() {
    return usuarioAlta;
  }

  public void setUsuarioAlta(String usuarioAlta) {
    this.usuarioAlta = usuarioAlta;
  }

  public String getIdObjetivo() {
    return idObjetivo;
  }

  public void setIdObjetivo(String idObjetivo) {
    this.idObjetivo = idObjetivo;
  }

  public Date getFechaAlta() {
    return fechaAlta;
  }

  public void setFechaAlta(Date fechaAlta) {
    this.fechaAlta = fechaAlta;
  }

  public TipoActividadDTO getTipoActividad() {
    return tipoActividad;
  }

  public void setTipoActividad(TipoActividadDTO tipoActividad) {
    this.tipoActividad = tipoActividad;
  }

  public Long getParentId() {
    return parentId;
  }

  public void setParentId(Long parentId) {
    this.parentId = parentId;
  }

  public Map<String, ParametroActividadDTO> getParametros() {
    return parametros;
  }

  public void setParametros(Map<String, ParametroActividadDTO> parametros) {
    this.parametros = parametros;
  }

  public Date getFechaCierre() {
    return fechaCierre;
  }

  public void setFechaCierre(Date fechaCierre) {
    this.fechaCierre = fechaCierre;
  }

  public String getUsuarioCierre() {
    return usuarioCierre;
  }

  public void setUsuarioCierre(String usuarioCierre) {
    this.usuarioCierre = usuarioCierre;
  }
}