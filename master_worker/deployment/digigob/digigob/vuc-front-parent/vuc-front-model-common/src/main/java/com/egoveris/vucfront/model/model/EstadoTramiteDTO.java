package com.egoveris.vucfront.model.model;

import com.egoveris.vucfront.model.util.EstadoTramiteEnum;

import java.io.Serializable;

public class EstadoTramiteDTO implements Serializable {

  private static final long serialVersionUID = 8870773537792163630L;
  private Long id;
  private String descripcion;

  public EstadoTramiteDTO(EstadoTramiteEnum estadoTramite) {
    this.id = estadoTramite.getId();
  }

  public EstadoTramiteDTO() {
    // Default constructor needed for Mapper
  }

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

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("EstadoTramiteDTO [id=").append(id).append(", descripcion=").append(descripcion)
        .append("]");
    return builder.toString();
  }

}