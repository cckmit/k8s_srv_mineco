package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;
import java.util.List;

public class VistaMercanciaDADTO extends AbstractCComplejoDTO implements Serializable {

	private List<ItemDTO> items;

	/**
	 * @return the items
	 */
	public List<ItemDTO> getItems() {
		return items;
	}

	/**
	 * @param items
	 *            the items to set
	 */
	public void setItems(List<ItemDTO> items) {
		this.items = items;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}