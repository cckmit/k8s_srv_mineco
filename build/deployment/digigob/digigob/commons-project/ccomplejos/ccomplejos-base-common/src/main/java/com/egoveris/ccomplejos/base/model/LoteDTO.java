package com.egoveris.ccomplejos.base.model;

public class LoteDTO extends AbstractCComplejoDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5170166788082573082L;

	Long numeroLote;
	String valorLote;

	/**
	 * @return the numeroLote
	 */
	public Long getNumeroLote() {
		return numeroLote;
	}
	/**
	 * @param numeroLote the numeroLote to set
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
	 * @param valorLote the valorLote to set
	 */
	public void setValorLote(String valorLote) {
		this.valorLote = valorLote;
	}

}
