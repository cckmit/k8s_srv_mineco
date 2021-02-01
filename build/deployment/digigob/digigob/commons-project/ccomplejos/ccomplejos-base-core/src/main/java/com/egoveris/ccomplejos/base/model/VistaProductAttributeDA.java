package com.egoveris.ccomplejos.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CC_VISTA_PRODUCT_ATTRIBUTE_DA")
public class VistaProductAttributeDA extends AbstractCComplejoJPA{
	
	@Column(name = "SECUENCIA_ATRIBUTO")
	protected Integer secuenciaAtributo;
	@Column(name = "NOMBRE_ATRIBUTO")
	protected String nombreAtributo;
	@Column(name = "ES_FIJO")
	protected String esFijo;
	@Column(name = "VALOR_ATRIBUTO")
	protected String valorAtributo;
	@ManyToOne
	@JoinColumn(name = "SECUENCIA_ATRIBUTO", referencedColumnName = "id", insertable = false, updatable = false)
	VistaProductDA vistaProductDA;



	/**
	 * @return the secuenciaAtributo
	 */
	public Integer getSecuenciaAtributo() {
		return secuenciaAtributo;
	}

	/**
	 * @param secuenciaAtributo
	 *            the secuenciaAtributo to set
	 */
	public void setSecuenciaAtributo(Integer secuenciaAtributo) {
		this.secuenciaAtributo = secuenciaAtributo;
	}

	/**
	 * @return the nombreAtributo
	 */
	public String getNombreAtributo() {
		return nombreAtributo;
	}

	/**
	 * @param nombreAtributo
	 *            the nombreAtributo to set
	 */
	public void setNombreAtributo(String nombreAtributo) {
		this.nombreAtributo = nombreAtributo;
	}

	/**
	 * @return the esFijo
	 */
	public String getEsFijo() {
		return esFijo;
	}

	/**
	 * @param esFijo
	 *            the esFijo to set
	 */
	public void setEsFijo(String esFijo) {
		this.esFijo = esFijo;
	}

	/**
	 * @return the valorAtributo
	 */
	public String getValorAtributo() {
		return valorAtributo;
	}

	/**
	 * @param valorAtributo
	 *            the valorAtributo to set
	 */
	public void setValorAtributo(String valorAtributo) {
		this.valorAtributo = valorAtributo;
	}

	/**
	 * @return the vistaProductDA
	 */
	public VistaProductDA getVistaProductDA() {
		return vistaProductDA;
	}

	/**
	 * @param vistaProductDA
	 *            the vistaProductDA to set
	 */
	public void setVistaProductDA(VistaProductDA vistaProductDA) {
		this.vistaProductDA = vistaProductDA;
	}
	
	

}