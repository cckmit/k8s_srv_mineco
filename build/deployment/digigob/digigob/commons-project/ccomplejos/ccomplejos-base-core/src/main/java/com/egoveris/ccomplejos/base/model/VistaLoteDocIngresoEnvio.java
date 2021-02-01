package com.egoveris.ccomplejos.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "view_lotedocingresoenvio")
public class VistaLoteDocIngresoEnvio extends AbstractViewCComplejoJPA {



	@Column(name = "VALOR_LOTE")
	String valorLote;


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

}
