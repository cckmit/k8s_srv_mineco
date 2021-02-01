package com.egoveris.te.base.model;

import java.io.Serializable;

public class SectorInternoSADEBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2838833538442669493L;
	/**
	 * 
	 */

	private String nombre;
	private String codigo;
	private Integer id;
	private boolean sectorMesa;
	private boolean mesaVirtual;
	
	public String toString(){
		return this.codigo;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setSectorMesa(boolean sectorMesa) {
		this.sectorMesa = sectorMesa;
	}

	public boolean isSectorMesa() {
		return sectorMesa;
	}

	public boolean isMesaVirtual() {
		return mesaVirtual;
	}

	public void setMesaVirtual(boolean mesaVirtual) {
		this.mesaVirtual = mesaVirtual;
	}
}
