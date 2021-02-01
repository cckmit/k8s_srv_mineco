package com.egoveris.deo.base.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SuscripcionPK implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -9043218824519382636L;

  @Column(name = "WORKFLOWID")
  private String workflowId;

  @Column(name = "SISTEMA_ORIGEN")
  private Integer sistemaOrigen;

  public SuscripcionPK() {
	    super();
	    
	  }

  public SuscripcionPK(String workflowId, Integer sistemaOrigen) {
    super();
    this.workflowId = workflowId;
    this.sistemaOrigen = sistemaOrigen;
  }

  public Integer getSistemaOrigen() {
    return sistemaOrigen;
  }

  public void setSistemaOrigen(Integer sistemaOrigen) {
    this.sistemaOrigen = sistemaOrigen;
  }

  public String getWorkflowId() {
    return workflowId;
  }

  public void setWorkflowId(String workflowId) {
    this.workflowId = workflowId;
  }

//  @Override
//  public int hashCode() {
//    final int prime = 31;
//    int result = 1;
//    result = prime * result + ((sistemaOrigen == null) ? 0 : sistemaOrigen.hashCode());
//    result = prime * result + ((workflowId == null) ? 0 : workflowId.hashCode());
//    return result;
//  }

//  @Override
//  public boolean equals(Object obj) {
//    if (this == obj)
//      return true;
//    if (obj == null)
//      return false;
//    if (getClass() != obj.getClass())
//      return false;
//    SuscripcionPK other = (SuscripcionPK) obj;
//    if (sistemaOrigen == null) {
//      if (other.sistemaOrigen != null)
//        return false;
//    } else if (!sistemaOrigen.equals(other.sistemaOrigen))
//      return false;
//    if (workflowId == null) {
//      if (other.workflowId != null)
//        return false;
//    } else if (!workflowId.equals(other.workflowId))
//      return false;
//    return true;
//  }

}
