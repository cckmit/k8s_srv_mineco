package com.egoveris.ffdd.model.model;

import java.io.Serializable;

public class ItemDTO implements Serializable, Comparable<ItemDTO> {

	private static final long serialVersionUID = -549962551175339258L;

	private Integer id;
	private String valor;
	private String descripcion;
	private Integer orden;
	private ComponenteDTO componente;
	private String multivalor;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public ComponenteDTO getComponente() {
		return componente;
	}

	public void setComponente(ComponenteDTO componente) {
		this.componente = componente;
	}

	@Override
	public int compareTo(ItemDTO o) {
		return orden.compareTo(o.getOrden());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof ItemDTO) {
			ItemDTO itemDTO = (ItemDTO) obj;
			if (itemDTO.getOrden().equals(this.orden)
					&& itemDTO.getValor().equals(this.valor)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((orden == null) ? 0 : orden.hashCode());
		return result;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ItemDTO [valor=").append(valor).append(", descripcion=").append(descripcion).append(", orden=")
				.append(orden).append("]");
		return builder.toString();
	}

	public String getMultivalor() {
		return multivalor;
	}

	public void setMultivalor(String multivalor) {
		this.multivalor = multivalor;
	}
}
