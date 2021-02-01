package com.egoveris.deo.base.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TipoDocumentoTemplatePK implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -892251172398602061L;

  @Column(name = "ID_TIPODOCUMENTO")
  private Integer idTipoDocumento;

  @Column(name = "VERSION")
  private double version;

  public Integer getIdTipoDocumento() {
    return idTipoDocumento;
  }

  public void setIdTipoDocumento(Integer idTipoDocumento) {
    this.idTipoDocumento = idTipoDocumento;
  }

  public double getVersion() {
    return version;
  }

  public void setVersion(double version) {
    this.version = version;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((idTipoDocumento == null) ? 0 : idTipoDocumento.hashCode());
    result = prime * result + ((Double.valueOf(version) == null) ? 0 : Double.valueOf(version).hashCode());
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
    TipoDocumentoTemplatePK other = (TipoDocumentoTemplatePK) obj;
    if (idTipoDocumento == null) {
      if (other.idTipoDocumento != null)
        return false;
    } else if (!idTipoDocumento.equals(other.idTipoDocumento))
      return false;
    if (Double.valueOf(version) == null) {
      if (Double.valueOf(other.version) != null)
        return false;
    } else if (!Double.valueOf(version).equals(Double.valueOf(other.version)))
      return false;
    return true;
  }

}