package com.egoveris.ccomplejos.base.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "view_productda")
public class VistaProductDA extends AbstractViewCComplejoJPA {
	

	@Column(name = "SECUENCIA_ATRIBUTO")
	Integer secuencia;

	@Column(name = "PRODUCT_ID")
	String codigoProducto;

	@OneToMany(mappedBy = "vistaProductDA")
	private List<VistaProductAttributeDA> atributos;

	@OneToOne
	@JoinColumn(name = "ID_PRODUCTO", referencedColumnName = "id", insertable = false, updatable = false)
	VistaItemDA producto;

	/**
	 * @return the secuencia
	 */
	public Integer getSecuencia() {
		return secuencia;
	}

	/**
	 * @param secuencia
	 *            the secuencia to set
	 */
	public void setSecuencia(Integer secuencia) {
		this.secuencia = secuencia;
	}

	/**
	 * @return the codigoProducto
	 */
	public String getCodigoProducto() {
		return codigoProducto;
	}

	/**
	 * @param codigoProducto
	 *            the codigoProducto to set
	 */
	public void setCodigoProducto(String codigoProducto) {
		this.codigoProducto = codigoProducto;
	}

	/**
	 * @return the atributos
	 */
	public List<VistaProductAttributeDA> getAtributos() {
		return atributos;
	}

	/**
	 * @param atributos
	 *            the atributos to set
	 */
	public void setAtributos(List<VistaProductAttributeDA> atributos) {
		this.atributos = atributos;
	}

	/**
	 * @return the producto
	 */
	public VistaItemDA getProducto() {
		return producto;
	}

	/**
	 * @param producto
	 *            the producto to set
	 */
	public void setProducto(VistaItemDA producto) {
		this.producto = producto;
	}

}