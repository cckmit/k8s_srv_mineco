package com.egoveris.deo.base.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class ExtensionMimetypePK implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 5683973173354925851L;

  @ManyToOne
	@JoinColumn(name =  "ID_FORMATO_TAMANO_ARCHIVO")
	private FormatoTamanoArchivo formatoTamanoId;
  
  @Column(name = "MIMETYPE")
	private String mimetype;

  public FormatoTamanoArchivo getFormatoTamanoId() {
		return formatoTamanoId;
	}

	public void setFormatoTamanoId(FormatoTamanoArchivo formatoTamanoId) {
		this.formatoTamanoId = formatoTamanoId;
	}

	public String getMimetype() {
    return mimetype;
  }

  public void setMimetype(String mimetype) {
    this.mimetype = mimetype;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result
        + ((formatoTamanoId.getId() == null) ? 0 : formatoTamanoId.getId().hashCode());
    result = prime * result + ((mimetype == null) ? 0 : mimetype.hashCode());
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
    ExtensionMimetypePK other = (ExtensionMimetypePK) obj;
    if (formatoTamanoId.getId() == null) {
      if (other.formatoTamanoId.getId() != null)
        return false;
    } else if (!formatoTamanoId.getId().equals(other.formatoTamanoId.getId()))
      return false;
    if (mimetype == null) {
      if (other.mimetype != null)
        return false;
    } else if (!mimetype.equals(other.mimetype))
      return false;
    return true;
  }

}
