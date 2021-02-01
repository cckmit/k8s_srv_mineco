package com.egoveris.ccomplejos.base.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "CC_BULTO")
public class Bulto extends AbstractCComplejoJPA {

	@Column(name = "TIPO_BULTO")
	protected String tipoBulto;
	@Column(name = "SUB_CONTINENTE")
	protected String subContinente;
	@Column(name = "SECUENCIA_BULTO")
	protected Long secuencialBulto;
	@OneToMany(mappedBy = "bulto")
	protected List<ItemJPA> listaItems;
	@Column(name = "IDENTIFICADOR_BULTO")
	protected String identificadorBulto;
	@Column(name = "ID_BULTO")
	protected Long idBulto;
	@Column(name = "CANTIDAD_PAQUETES")
	protected Long cantidadPaquetes;
	
	@ManyToOne
	@JoinColumn(name = "OPERATION_ID", referencedColumnName = "id")
	Operation operation;

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

	public List<ItemJPA> getListaItems() {
		return listaItems;
	}

	public void setListaItems(List<ItemJPA> listaItems) {
		this.listaItems = listaItems;
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
