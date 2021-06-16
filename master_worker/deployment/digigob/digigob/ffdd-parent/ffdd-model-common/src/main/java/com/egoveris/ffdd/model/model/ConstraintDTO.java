package com.egoveris.ffdd.model.model;

import java.io.Serializable;
import java.util.List;

public class ConstraintDTO implements Serializable{

	private static final long serialVersionUID = 7539389044183958778L;
	
	private String nombreComponente;
	private String mensaje;
	private List<CondicionDTO> condiciones;
	private Integer id;
	
	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public List<CondicionDTO> getCondiciones() {
		return condiciones;
	}

	public void setCondiciones(List<CondicionDTO> condiciones) {
		this.condiciones = condiciones;
	}

	public String getNombreComponente() {
		return nombreComponente;
	}

	public void setNombreComponente(String nombreComponente) {
		this.nombreComponente = nombreComponente;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ConstraintDTO [nombreComponente=").append(nombreComponente).append(", mensaje=").append(mensaje)
				.append(", condiciones=").append(condiciones).append(", id=").append(id).append("]");
		return builder.toString();
	}

}
