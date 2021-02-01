package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;
import java.util.List;


public class BultoDTO extends AbstractCComplejoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	protected String tipoBulto;
	protected String subContinente;
	protected Long secuencialBulto;
	protected List<ItemDTO> listaItemsDTO;
	protected String identificadorBulto;
	protected Long idBulto;
	protected Long cantidadPaquetes;
	
	
	public String getTipoBulto() {
		return tipoBulto;
	}
	public void setTipoBulto(String tipoBulto) {
		this.tipoBulto = tipoBulto;
	}
	public String getSubContinente() {
		return subContinente;
	}
	public void setSubContinente(String subContinente) {
		this.subContinente = subContinente;
	}
	public Long getSecuencialBulto() {
		return secuencialBulto;
	}
	public void setSecuencialBulto(Long secuencialBulto) {
		this.secuencialBulto = secuencialBulto;
	}
	public List<ItemDTO> getListaItems() {
		return listaItemsDTO;
	}
	public void setListaItems(List<ItemDTO> listaItems) {
		this.listaItemsDTO = listaItems;
	}
	public String getIdentificadorBulto() {
		return identificadorBulto;
	}
	public void setIdentificadorBulto(String identificadorBulto) {
		this.identificadorBulto = identificadorBulto;
	}
	public Long getIdBulto() {
		return idBulto;
	}
	public void setIdBulto(Long idBulto) {
		this.idBulto = idBulto;
	}
	public Long getCantidadPaquetes() {
		return cantidadPaquetes;
	}
	public void setCantidadPaquetes(Long cantidadPaquetes) {
		this.cantidadPaquetes = cantidadPaquetes;
	}
	
}
