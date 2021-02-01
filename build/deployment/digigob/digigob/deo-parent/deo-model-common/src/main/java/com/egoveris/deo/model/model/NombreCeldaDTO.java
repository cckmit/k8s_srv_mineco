package com.egoveris.deo.model.model;

import java.io.Serializable;


public class NombreCeldaDTO implements Serializable{


	private static final long serialVersionUID = -5339749651179928328L;

	private String nombre;
	private String nombreDoc;

	public NombreCeldaDTO(String data,String nombreDoc) {
		super();
		this.nombre=data;
		this.nombreDoc=nombreDoc;
	}
	

	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getNombreDoc() {
		return nombreDoc;
	}


	public void setNombreDoc(String nombreDoc) {
		this.nombreDoc = nombreDoc;
	}





}
