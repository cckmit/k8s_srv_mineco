package com.egoveris.ccomplejos.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cc_observacion")
public class Observacion extends AbstractCComplejoJPA {

	@Column(name = "OBSERVACION_CODE")
	String observacionCode;

	@Column(name = "OBSERVACION_DESC")
	String observacionDesc;

	@ManyToOne
	@JoinColumn(name = "ID_ITEM", referencedColumnName = "id")
	ItemJPA item;

	@ManyToOne
	@JoinColumn(name = "ID_AUTORIZACION", referencedColumnName = "id")
	Autorizacion autorizacion;
	
	@ManyToOne
	@JoinColumn(name = "ID_DECLARACION", referencedColumnName = "id")
	Declaracion declaracion;

	public String getObservacionCode() {
		return observacionCode;
	}

	public void setObservacionCode(String observacionCode) {
		this.observacionCode = observacionCode;
	}

	public String getObservacionDesc() {
		return observacionDesc;
	}

	public void setObservacionDesc(String observacionDesc) {
		this.observacionDesc = observacionDesc;
	}

	/**
	 * @return the item
	 */
	public ItemJPA getItem() {
		return item;
	}

	/**
	 * @param item
	 *            the item to set
	 */
	public void setItem(ItemJPA item) {
		this.item = item;
	}

}
