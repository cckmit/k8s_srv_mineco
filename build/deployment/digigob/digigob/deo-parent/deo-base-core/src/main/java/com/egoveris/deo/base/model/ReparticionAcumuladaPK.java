package com.egoveris.deo.base.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ReparticionAcumuladaPK implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -6415333628116024650L;

  @Column(name = "IDDOCUMENTO", nullable = false)
  private Integer idDocumento;

  @Column(name = "reparticion", nullable = false)
  private String reparticion;

  public Integer getIdDocumento() {
    return idDocumento;
  }

  public void setIdDocumento(Integer idDocumento) {
    this.idDocumento = idDocumento;
  }

  public String getReparticion() {
    return reparticion;
  }

  public void setReparticion(String reparticion) {
    this.reparticion = reparticion;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((idDocumento == null) ? 0 : idDocumento.hashCode());
    result = prime * result + ((reparticion == null) ? 0 : reparticion.hashCode());
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
    ReparticionAcumuladaPK other = (ReparticionAcumuladaPK) obj;
    if (idDocumento == null) {
      if (other.idDocumento != null)
        return false;
    } else if (!idDocumento.equals(other.idDocumento))
      return false;
    if (reparticion == null) {
      if (other.reparticion != null)
        return false;
    } else if (!reparticion.equals(other.reparticion))
      return false;
    return true;
  }

}
