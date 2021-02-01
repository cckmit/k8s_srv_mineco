package com.egoveris.te.base.model;

import java.io.Serializable;
import java.util.Date;

public class DatosVariablesComboGruposDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8251894973911651469L;
	
	private Long id;
	private String nombreGrupo;
	private Date fechaBaja;
	private String tipo;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNombreGrupo() {
		return nombreGrupo;
	}
	
	public void setNombreGrupo(String nombreGrupo) {
		this.nombreGrupo = nombreGrupo;
	}
	
	public Date getFechaBaja() {
		return fechaBaja;
	}
	
	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
