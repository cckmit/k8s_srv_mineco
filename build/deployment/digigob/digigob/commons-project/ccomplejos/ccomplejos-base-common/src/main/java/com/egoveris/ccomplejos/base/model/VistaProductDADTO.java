package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;
import java.util.List;

public class VistaProductDADTO extends AbstractCComplejoDTO implements Serializable {

	String codigoProducto;

	private List<VistaProductAttributeDADTO> atributos;

	/**
	 * @return the codigoProducto
	 */
	public String getCodigoProducto() {
		return codigoProducto;
	}

	/**
	 * @param codigoProducto
	 *            the codigoProducto to set
	 */
	public void setCodigoProducto(String codigoProducto) {
		this.codigoProducto = codigoProducto;
	}

	/**
	 * @return the atributos
	 */
	public List<VistaProductAttributeDADTO> getAtributos() {
		return atributos;
	}

	/**
	 * @param atributos
	 *            the atributos to set
	 */
	public void setAtributos(List<VistaProductAttributeDADTO> atributos) {
		this.atributos = atributos;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}