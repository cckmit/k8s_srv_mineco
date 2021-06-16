package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;

public class VistaTotalesDeclaracionDTO extends AbstractCComplejoDTO implements Serializable{
	
	
	private static final long serialVersionUID = 2333846370454280034L;
	
	String pesoBrutoEmbarque;
	String totalBulto;
	String pesoNetoEmbarque;
	String totalItem;
	
	public String getPesoBrutoEmbarque() {
		return pesoBrutoEmbarque;
	}
	public void setPesoBrutoEmbarque(String pesoBrutoEmbarque) {
		this.pesoBrutoEmbarque = pesoBrutoEmbarque;
	}
	public String getTotalBulto() {
		return totalBulto;
	}
	public void setTotalBulto(String totalBulto) {
		this.totalBulto = totalBulto;
	}
	public String getPesoNetoEmbarque() {
		return pesoNetoEmbarque;
	}
	public void setPesoNetoEmbarque(String pesoNetoEmbarque) {
		this.pesoNetoEmbarque = pesoNetoEmbarque;
	}
	public String getTotalItem() {
		return totalItem;
	}
	public void setTotalItem(String totalItem) {
		this.totalItem = totalItem;
	}
	
	
	
}
