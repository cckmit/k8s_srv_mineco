package com.egoveris.deo.base.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DocumentoTemplatePK implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -6890076337995234732L;

  @Column(name = "ID_TIPODOCUMENTO")
  private Integer idTipoDocumento;

  @Column(name = "VERSION")
  private double version;

  @Column(name = "WORKFLOWID")
  private String workflowId;

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

  public String getWorkflowId() {
    return workflowId;
  }

  public void setWorkflowId(String workflowId) {
    this.workflowId = workflowId;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((idTipoDocumento == null) ? 0 : idTipoDocumento.hashCode());
    result = prime * result + ((Double.valueOf(version) == null) ? 0 : Double.valueOf(version).hashCode());
    result = prime * result + ((workflowId == null) ? 0 : workflowId.hashCode());
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
		DocumentoTemplatePK other = (DocumentoTemplatePK) obj;
		if (idTipoDocumento == null) {
			if (other.getIdTipoDocumento() != null)
				return false;
		} else if (!idTipoDocumento.equals(other.getIdTipoDocumento()))
			return false;
		if (Double.valueOf(version) == null) {
			if (Double.valueOf(other.getVersion()) != null)
				return false;
		} else if (!Double.valueOf(version).equals(Double.valueOf(other.getVersion())))
			return false;
		if (workflowId == null) {
			if (other.getWorkflowId() != null)
				return false;
		} else if (!workflowId.equals(other.getWorkflowId()))
			return false;
		return true;
	}

}
