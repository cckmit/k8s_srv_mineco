package com.egoveris.vucfront.model.model;

import com.egoveris.vucfront.model.util.EstadoTareaEnum;

import java.io.Serializable;

public class EstadoTareaDTO implements Serializable {

  private static final long serialVersionUID = 5007925834460246246L;

  private Long id;
  private String descripcion;

  public EstadoTareaDTO(EstadoTareaEnum estadoTarea) {
    this.id = estadoTarea.getId();
  }

  public EstadoTareaDTO() {
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
    builder.append("EstadoTareaDTO [id=").append(id).append(", descripcion=").append(descripcion)
        .append("]");
    return builder.toString();
  }

}