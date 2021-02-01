package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class VistaMercanciaDinDTO extends AbstractCComplejoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2122435726306886817L;
	

	protected Integer totalBultos;
	protected String identificacionDeBultos;
	protected BigDecimal totalPesoBruto;
	protected Integer numeroTotalDeItems;
	protected List<VistaItemsDocIngresoEnvioDTO> item;
	
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
	public List<VistaItemsDocIngresoEnvioDTO> getItem() {
		return item;
	}
	
	/**
	 * @param item
	 *            the item to set
	 */
	public void setItem(List<VistaItemsDocIngresoEnvioDTO> item) {
		this.item = item;
	}


}