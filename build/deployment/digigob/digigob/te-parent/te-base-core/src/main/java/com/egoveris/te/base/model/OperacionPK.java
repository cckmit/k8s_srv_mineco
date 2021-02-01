package com.egoveris.te.base.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;

@Embeddable
public class OperacionPK implements Serializable {

  private static final long serialVersionUID = 5413096519629080131L;
 
	@GeneratedValue
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "TIPO_OPERACION_ID")
	private Long tipoOperacion;
	
	@Column(name = "EXECUTION_DBID_")
	private String jbpmExecutionId;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getJbpmExecutionId() {
		return jbpmExecutionId;
	}

	public void setJbpmExecutionId(String jbpmExecutionId) {
		this.jbpmExecutionId = jbpmExecutionId;
	}

	/**
	 * @return the tipoOperacion
	 */
	public Long getTipoOperacion() {
		return tipoOperacion;
	}

	/**
	 * @param tipoOperacion the tipoOperacion to set
	 */
	public void setTipoOperacion(Long tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}
}
