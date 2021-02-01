package com.egoveris.deo.model.model;

import java.io.Serializable;
import java.util.Date;

public class TareaCambioDeSiglaErrorDTO implements Serializable {

	private static final long serialVersionUID = -4938111146021532822L;
	
	private Integer id;
	
	private Integer idTarea;
	
	private String error;
	
	private Date fecha;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdTarea() {
		return idTarea;
	}

	public void setIdTarea(Integer idTarea) {
		this.idTarea = idTarea;
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
