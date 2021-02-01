package com.egoveris.te.base.model;

import java.io.Serializable;

//import ar.gob.gcaba.sade.services.beans.ReparticionBeanSade;

@Deprecated
/**
 * Informacion relevante de una reparticion.
 *
 */
public class ReparticionSADEBean implements Serializable/*extends ReparticionBeanSade*/{
	
/**
	 * 
	 */
	private static final long serialVersionUID = -528665397204039343L;
/**
	 * 
	 */
	
	
	//	private static final long serialVersionUID = -7842694538729405709L;
	@Deprecated
	private String nombre;
	@Deprecated
	private String codigo;
	@Deprecated
	private Integer id;
	
	@Deprecated
	public String toString(){
		return this.codigo;
	}
	
	@Deprecated
	public String getNombre() {
		return nombre;
	}

	@Deprecated
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Deprecated
	public String getCodigo() {
		return codigo;
	}

	@Deprecated
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@Deprecated
	public Integer getId() {
		return id;
	}

	@Deprecated
	public void setId(Integer id) {
		this.id = id;
	}
}
