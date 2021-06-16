package com.egoveris.deo.model.model;


public class SuscripcionPKDTO {

	private static final long serialVersionUID = 1L;

	  private String workflowId;

	  private Integer sistemaOrigen;

	  public SuscripcionPKDTO(String workflowId, Integer sistemaOrigen) {
		  this.workflowId = workflowId;
		  this.sistemaOrigen = sistemaOrigen;
		 
	  }
	  
	  public SuscripcionPKDTO() {
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

	}
