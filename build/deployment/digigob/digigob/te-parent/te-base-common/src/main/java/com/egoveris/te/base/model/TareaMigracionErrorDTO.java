package com.egoveris.te.base.model;

import java.io.Serializable;
import java.util.Date;

public class TareaMigracionErrorDTO implements Serializable {

	private static final long serialVersionUID = -4938111146021532822L;
	
	private Long id;
	
	private String error;
	
	private Date fecha;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	
	

}
