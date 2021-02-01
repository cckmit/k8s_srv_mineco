package com.egoveris.te.base.model;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SubProcesoOperacionPk implements Serializable {

  private static final long  serialVersionUID = -1497417148769745457L;
  @Column(name = "ID_OPERACION")
  private Long idOperacion;

  @Column(name = "ID_EXPEDIENTE_ELECTRONICO")
  private Long idExpediente;

  @Column(name = "ID_SUBPROCESO")
  private Long idSubproceso;

  public Long getIdOperacion() {
    return idOperacion;
  }

  public void setIdOperacion(final Long idOperacion) {
    this.idOperacion = idOperacion;
  }

  public Long getIdExpediente() {
    return idExpediente;
  }

  public void setIdExpediente(final Long idExpediente) {
    this.idExpediente = idExpediente;
  }

  public Long getIdSubproceso() {
    return idSubproceso;
  }

  public void setIdSubproceso(Long idSubproceso) {
    this.idSubproceso = idSubproceso;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((idExpediente == null) ? 0 : idExpediente.hashCode());
    result = prime * result + ((idOperacion == null) ? 0 : idOperacion.hashCode());
    result = prime * result + ((idSubproceso == null) ? 0 : idSubproceso.hashCode());
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
    SubProcesoOperacionPk other = (SubProcesoOperacionPk) obj;
    if (idExpediente == null) {
      if (other.idExpediente != null)
        return false;
    } else if (!idExpediente.equals(other.idExpediente))
      return false;
    if (idOperacion == null) {
      if (other.idOperacion != null)
        return false;
    } else if (!idOperacion.equals(other.idOperacion))
      return false;
    if (idSubproceso == null) {
      if (other.idSubproceso != null)
        return false;
    } else if (!idSubproceso.equals(other.idSubproceso))
      return false;
    return true;
  }

}