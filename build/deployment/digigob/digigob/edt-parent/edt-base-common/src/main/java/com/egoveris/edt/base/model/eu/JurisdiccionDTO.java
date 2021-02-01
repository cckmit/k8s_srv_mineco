package com.egoveris.edt.base.model.eu;

import java.io.Serializable;

public class JurisdiccionDTO implements Serializable {

  private static final long serialVersionUID = 7721504210103596788L;

  private Integer id;
  private String descripcion;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
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
    builder.append("JurisdiccionDTO [id=").append(id).append(", descripcion=").append(descripcion)
        .append("]");
    return builder.toString();
  }

}
