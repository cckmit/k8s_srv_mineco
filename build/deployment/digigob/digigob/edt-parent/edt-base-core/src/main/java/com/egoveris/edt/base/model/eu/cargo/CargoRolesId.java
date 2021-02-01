package com.egoveris.edt.base.model.eu.cargo;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CargoRolesId {

  @Column(name = "CARGO_ID")
  private Integer idCargo;

  @Column(name = "EDTRL_ID")
  private Integer idRol;

  public Integer getIdCargo() {
    return idCargo;
  }

  public void setIdCargo(Integer idCargo) {
    this.idCargo = idCargo;
  }

  public Integer getIdRol() {
    return idRol;
  }

  public void setIdRol(Integer idRol) {
    this.idRol = idRol;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((idCargo == null) ? 0 : idCargo.hashCode());
    result = prime * result + ((idRol == null) ? 0 : idRol.hashCode());
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
    CargoRolesId other = (CargoRolesId) obj;
    if (idCargo == null) {
      if (other.idCargo != null) {
        return false;
      }
    } else if (!idCargo.equals(other.idCargo)) {
      return false;
    }
    if (idRol == null) {
      if (other.idRol != null) {
        return false;
      }
    } else if (!idRol.equals(other.idRol)) {
      return false;
    }
    return true;
  }

}