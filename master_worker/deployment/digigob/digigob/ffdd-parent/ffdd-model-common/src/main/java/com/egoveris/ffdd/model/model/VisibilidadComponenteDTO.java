package com.egoveris.ffdd.model.model;

import java.io.Serializable;
import java.util.List;

public class VisibilidadComponenteDTO implements Serializable{

	private static final long serialVersionUID = -2289424944098724613L;

	private List<CondicionDTO> condiciones;
	private List<String> componentesOcultos;
	private Integer id;

	public List<CondicionDTO> getCondiciones() {
		return condiciones;
	}

	public void setCondiciones(List<CondicionDTO> condiciones) {
		this.condiciones = condiciones;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<String> getComponentesOcultos() {
		return componentesOcultos;
	}

	public void setComponentesOcultos(List<String> componentesOcultos) {
		this.componentesOcultos = componentesOcultos;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VisibilidadComponenteDTO [condiciones=").append(condiciones).append(", componentesOcultos=")
				.append(componentesOcultos).append(", id=").append(id).append("]");
		return builder.toString();
	}
}