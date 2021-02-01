package com.egoveris.edt.base.model.eu;

import java.io.Serializable;

public class CategoriaDTO implements Serializable {

  private static final long serialVersionUID = 3580910688400834476L;

  private Integer id;
  private String categoriaNombre;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getCategoriaNombre() {
    return categoriaNombre;
  }

  public void setCategoriaNombre(String categoriaNombre) {
    this.categoriaNombre = categoriaNombre;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((categoriaNombre == null) ? 0 : categoriaNombre.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    CategoriaDTO other = (CategoriaDTO) obj;
    if (categoriaNombre == null) {
      if (other.categoriaNombre != null) {
        return false;
      }
    } else if (!categoriaNombre.equals(other.categoriaNombre)) {
      return false;
    }
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("CategoriaDTO [id=").append(id).append(", categoriaNombre=")
        .append(categoriaNombre).append("]");
    return builder.toString();
  }

}