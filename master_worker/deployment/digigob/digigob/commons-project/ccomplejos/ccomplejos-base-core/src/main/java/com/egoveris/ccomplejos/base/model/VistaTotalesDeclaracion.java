package com.egoveris.ccomplejos.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "view_totalesdeclaracion")
public class VistaTotalesDeclaracion extends AbstractViewCComplejoJPA {
	
	@Column(name = "PESO_BRUTO")
	protected String pesoBrutoEmbarque;
	@Column(name = "TOTAL_BULTO")
	protected String totalBulto;
	@Column(name = "PESO_NETO")
	protected String pesoNetoEmbarque;
	@Column(name = "TOTAL_ITEM")
	protected String totalItem;
	
	
	/**
	 * @return the pesoBrutoEmbarque
	 */
	public String getPesoBrutoEmbarque() {
		return pesoBrutoEmbarque;
	}

	/**
	 * @param pesoBrutoEmbarque
	 *            the pesoBrutoEmbarque to set
	 */
	public void setPesoBrutoEmbarque(String pesoBrutoEmbarque) {
		this.pesoBrutoEmbarque = pesoBrutoEmbarque;
	}

	/**
	 * @return the totalBulto
	 */
	public String getTotalBulto() {
		return totalBulto;
	}

	/**
	 * @param totalBulto
	 *            the totalBulto to set
	 */
	public void setTotalBulto(String totalBulto) {
		this.totalBulto = totalBulto;
	}

	/**
	 * @return the pesoNetoEmbarque
	 */
	public String getPesoNetoEmbarque() {
		return pesoNetoEmbarque;
	}

	/**
	 * @param pesoNetoEmbarque
	 *            the pesoNetoEmbarque to set
	 */
	public void setPesoNetoEmbarque(String pesoNetoEmbarque) {
		this.pesoNetoEmbarque = pesoNetoEmbarque;
	}

	/**
	 * @return the totalItem
	 */
	public String getTotalItem() {
		return totalItem;
	}

	/**
	 * @param totalItem
	 *            the totalItem to set
	 */
	public void setTotalItem(String totalItem) {
		this.totalItem = totalItem;
	}


}