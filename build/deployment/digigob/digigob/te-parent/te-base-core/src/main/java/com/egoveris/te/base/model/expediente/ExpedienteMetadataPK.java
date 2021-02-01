package com.egoveris.te.base.model.expediente;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ExpedienteMetadataPK implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 6070902739261760862L;

  @Column(name = "ID_EXPEDIENTE")
  private Long idExpediente;

  @Column(name = "ORDEN")
  private Integer orden;

  public Long getIdExpediente() {
    return idExpediente;
  }

  public void setIdExpediente(Long idExpediente) {
    this.idExpediente = idExpediente;
  }

  public Integer getOrden() {
    return orden;
  }

  public void setOrden(Integer orden) {
    this.orden = orden;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((idExpediente == null) ? 0 : idExpediente.hashCode());
    result = prime * result + ((orden == null) ? 0 : orden.hashCode());
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
    ExpedienteMetadataPK other = (ExpedienteMetadataPK) obj;
    if (idExpediente == null) {
      if (other.idExpediente != null)
        return false;
    } else if (!idExpediente.equals(other.idExpediente))
      return false;
    if (orden == null) {
      if (other.orden != null)
        return false;
    } else if (!orden.equals(other.orden))
      return false;
    return true;
  }

}