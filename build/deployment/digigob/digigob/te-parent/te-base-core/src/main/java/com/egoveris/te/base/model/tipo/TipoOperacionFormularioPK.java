package com.egoveris.te.base.model.tipo;

import java.io.Serializable;

public class TipoOperacionFormularioPK implements Serializable {

  private static final long serialVersionUID = 5983684031998297131L;

  private Long idFormulario;
  private Long idTipoOperacion;

  public Long getIdFormulario() {
    return idFormulario;
  }

  public void setIdFormulario(Long idFormulario) {
    this.idFormulario = idFormulario;
  }

  public Long getIdTipoOperacion() {
    return idTipoOperacion;
  }

  public void setIdTipoOperacion(Long idTipoOperacion) {
    this.idTipoOperacion = idTipoOperacion;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((idFormulario == null) ? 0 : idFormulario.hashCode());
    result = prime * result + ((idTipoOperacion == null) ? 0 : idTipoOperacion.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    TipoOperacionFormularioPK other = (TipoOperacionFormularioPK) obj;
    if (idFormulario == null) {
      if (other.idFormulario != null)
        return false;
    } else if (!idFormulario.equals(other.idFormulario))
      return false;
    if (idTipoOperacion == null) {
      if (other.idTipoOperacion != null)
        return false;
    } else if (!idTipoOperacion.equals(other.idTipoOperacion))
      return false;
    return true;
  }

}