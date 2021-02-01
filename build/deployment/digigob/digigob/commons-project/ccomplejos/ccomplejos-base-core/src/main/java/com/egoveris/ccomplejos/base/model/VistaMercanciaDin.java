package com.egoveris.ccomplejos.base.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "view_mercanciadin")
public class VistaMercanciaDin extends AbstractViewCComplejoJPA {

	@Column(name = "CANTIDAD_PAQUETES")
	protected Integer totalBultos;
	@Column(name = "IDENTIFICADOR_BULTO")
	protected String identificacionDeBultos;
	@Column(name = "PESO_BRUTO")
	protected BigDecimal totalPesoBruto;
	@Column(name = "VOLUMEN_TOTAL")
	protected Integer numeroTotalDeItems;
	@OneToMany
	@JoinColumn(name = "ID_PRODUCTO", referencedColumnName = "id", insertable = false, updatable = false)
	protected List<VistaItemsDocIngresoEnvio> item;

	/**
	 * @return the totalBultos
	 */
	public Integer getTotalBultos() {
		return totalBultos;
	}

	/**
	 * @param totalBultos
	 *            the totalBultos to set
	 */
	public void setTotalBultos(Integer totalBultos) {
		this.totalBultos = totalBultos;
	}

	/**
	 * @return the identificacionDeBultos
	 */
	public String getIdentificacionDeBultos() {
		return identificacionDeBultos;
	}

	/**
	 * @param identificacionDeBultos
	 *            the identificacionDeBultos to set
	 */
	public void setIdentificacionDeBultos(String identificacionDeBultos) {
		this.identificacionDeBultos = identificacionDeBultos;
	}

	/**
	 * @return the totalPesoBruto
	 */
	public BigDecimal getTotalPesoBruto() {
		return totalPesoBruto;
	}

	/**
	 * @param totalPesoBruto
	 *            the totalPesoBruto to set
	 */
	public void setTotalPesoBruto(BigDecimal totalPesoBruto) {
		this.totalPesoBruto = totalPesoBruto;
	}

	/**
	 * @return the numeroTotalDeItems
	 */
	public Integer getNumeroTotalDeItems() {
		return numeroTotalDeItems;
	}

	/**
	 * @param numeroTotalDeItems
	 *            the numeroTotalDeItems to set
	 */
	public void setNumeroTotalDeItems(Integer numeroTotalDeItems) {
		this.numeroTotalDeItems = numeroTotalDeItems;
	}

	/**
	 * @return the item
	 */
	public List<VistaItemsDocIngresoEnvio> getItem() {
		return item;
	}

	/**
	 * @param item
	 *            the item to set
	 */
	public void setItem(List<VistaItemsDocIngresoEnvio> item) {
		this.item = item;
	}

}