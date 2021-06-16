package com.egoveris.vucfront.model.model;

import java.io.Serializable;
import java.util.List;

public class GrupoDTO implements Serializable {

  private static final long serialVersionUID = -2085550932796149552L;

  private Long id;
  private String descripcion;
  private String nombre;
  private List<TipoTramiteDTO> tipoTramiteList;
  private Long version;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public List<TipoTramiteDTO> getTipoTramiteList() {
    return tipoTramiteList;
  }

  public void setTipoTramiteList(List<TipoTramiteDTO> tipoTramiteList) {
    this.tipoTramiteList = tipoTramiteList;
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
    builder.append("GrupoDTO [id=").append(id).append(", descripcion=").append(descripcion)
        .append(", nombre=").append(nombre).append(", version=").append(version).append("]");
    return builder.toString();
  }

}