package com.egoveris.ccomplejos.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CC_PRODUCT_COMPONENT")
public class ProductComponent extends AbstractCComplejoJPA{
	
	
	@Column(name = "PRODUCT_COMPONENT_ID")
	protected Long productComponentid;
	
	@Column(name = "NOMBRE_PRODUCT_COMPONENT")
	protected String nombreProductComponent;
	 
	@Column(name = "CODIGO")
	protected String codigo;
 
	@Column(name = "DESCRIPCION")
	protected String descripcion;
	
	@Column(name = "PRINCIPIO_ACTIVO")
	protected String principioActivo;
	
	@Column(name = "PORCENTAJE_CONSITUYENTE")
	protected String porcentajeConsituyente;
	
	@Column(name = "CONSTITUYENTE_SUOM")
	protected String constituyentesUom;
	 
	@ManyToOne
	@JoinColumn(name = "PRODUCT_OPERATION", referencedColumnName = "id")
	protected ProductOperation productOperation;
	 

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

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
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

	/**
	 * @return the productOperation
	 */
	public ProductOperation getProductOperation() {
		return productOperation;
	}

	/**
	 * @param productOperation the productOperation to set
	 */
	public void setProductOperation(ProductOperation productOperation) {
		this.productOperation = productOperation;
	}

	  
}
