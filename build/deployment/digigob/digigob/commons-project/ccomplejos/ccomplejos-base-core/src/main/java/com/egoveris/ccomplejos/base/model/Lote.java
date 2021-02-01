package com.egoveris.ccomplejos.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CC_LOTE")
public class Lote extends AbstractCComplejoJPA {


	@Column(name = "NUM_LOTE")
	Long numeroLote;

	@Column(name = "VALOR_LOTE")
	String valorLote;

	@ManyToOne
	@JoinColumn(name = "ID_ITEM", referencedColumnName = "id")
	ItemJPA item;


	/**
	 * @return the numeroLote
	 */
	public Long getNumeroLote() {
		return numeroLote;
	}

	/**
	 * @param numeroLote
	 *            the numeroLote to set
	 */
	public void setNumeroLote(Long numeroLote) {
		this.numeroLote = numeroLote;
	}

	/**
	 * @return the valorLote
	 */
	public String getValorLote() {
		return valorLote;
	}

	/**
	 * @param valorLote
	 *            the valorLote to set
	 */
	public void setValorLote(String valorLote) {
		this.valorLote = valorLote;
	}

	/**
	 * @return the item
	 */
	public ItemJPA getItem() {
		return item;
	}

	/**
	 * @param item
	 *            the item to set
	 */
	public void setItem(ItemJPA item) {
		this.item = item;
	}

	/**
	 * @return the idOperacion
	 */
	public Integer getIdOperacion() {
		return idOperacion;
	}

	/**
	 * @param idOperacion
	 *            the idOperacion to set
	 */
	public void setIdOperacion(Integer idOperacion) {
		this.idOperacion = idOperacion;
	}



}
