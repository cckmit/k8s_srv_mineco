package com.egoveris.te.base.model;

public class OperacionPKDTO {
	
	private Long id;
	
	private Long tipoOperacion;
	
	private String jbpmExecutionId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(Long tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	public String getJbpmExecutionId() {
		return jbpmExecutionId;
	}

	public void setJbpmExecutionId(String jbpmExecutionId) {
		this.jbpmExecutionId = jbpmExecutionId;
	}
	
	
	

}
