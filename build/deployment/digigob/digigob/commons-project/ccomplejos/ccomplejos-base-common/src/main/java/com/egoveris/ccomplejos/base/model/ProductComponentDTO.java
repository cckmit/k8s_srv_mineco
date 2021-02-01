package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;

public class ProductComponentDTO extends AbstractCComplejoDTO implements Serializable{
	
	private static final long serialVersionUID = 8489923196191864749L;

	 
	
	protected Long productComponentid; 
	
	protected String nombreProductComponent;
	
	protected String codigo;
	
	protected String nombre;
	
	protected String descripcion;
	
	protected String principioActivo;
	
	protected String porcentajeConsituyente;
	
	protected String constituyentesUom;

	 

	public Long getProductComponentid() {
		return productComponentid;
	}

	public void setProductComponentid(Long productComponentid) {
		this.productComponentid = productComponentid;
	} 

	public String getNombreProductComponent() {
		return nombreProductComponent;
	}

	public void setNombreProductComponent(String nombreProductComponent) {
		this.nombreProductComponent = nombreProductComponent;
	}




	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getPrincipioActivo() {
		return principioActivo;
	}

	public void setPrincipioActivo(String principioActivo) {
		this.principioActivo = principioActivo;
	}

	public String getPorcentajeConsituyente() {
		return porcentajeConsituyente;
	}

	public void setPorcentajeConsituyente(String porcentajeConsituyente) {
		this.porcentajeConsituyente = porcentajeConsituyente;
	}

	public String getConstituyentesUom() {
		return constituyentesUom;
	}

	public void setConstituyentesUom(String constituyentesUom) {
		this.constituyentesUom = constituyentesUom;
	}

	
}
