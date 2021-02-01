package com.egoveris.te.model.model;

import com.egoveris.te.model.model.CampoFFCCDTO;

import java.io.Serializable;
import java.util.List;

public class CaratulaVariableResponse implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8248866248138301848L;
	private String nombre;
	private List<CampoFFCCDTO> campos;
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNombre() {
		return nombre;
	}
	public void setCampos(List<CampoFFCCDTO> campos) {
		this.campos = campos;
	}
	public List<CampoFFCCDTO> getCampos() {
		return campos;
	}

}
