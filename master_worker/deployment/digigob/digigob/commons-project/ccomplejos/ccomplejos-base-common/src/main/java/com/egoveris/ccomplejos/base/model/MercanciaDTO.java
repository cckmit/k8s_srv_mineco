package com.egoveris.ccomplejos.base.model;

public class MercanciaDTO extends AbstractCComplejoDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9036965657458182924L;

	Long idMercancia;
	Long mercanciaNacionalizada;
	String regionOrigen;
	Long propiosMedios;
	Long mercanciaZonaPrimaria;
	Long adquiridasCIF;
	Long origenCIF;
	String paisOrigen;
	String paisAdquisicion;

	/**
	 * @return the idMercancia
	 */
	public Long getIdMercancia() {
		return idMercancia;
	}

	/**
	 * @param idMercancia
	 *            the idMercancia to set
	 */
	public void setIdMercancia(Long idMercancia) {
		this.idMercancia = idMercancia;
	}

	/**
	 * @return the mercanciaNacionalizada
	 */
	public Long getMercanciaNacionalizada() {
		return mercanciaNacionalizada;
	}

	/**
	 * @param mercanciaNacionalizada
	 *            the mercanciaNacionalizada to set
	 */
	public void setMercanciaNacionalizada(Long mercanciaNacionalizada) {
		this.mercanciaNacionalizada = mercanciaNacionalizada;
	}

	/**
	 * @return the regionOrigen
	 */
	public String getRegionOrigen() {
		return regionOrigen;
	}

	/**
	 * @param regionOrigen
	 *            the regionOrigen to set
	 */
	public void setRegionOrigen(String regionOrigen) {
		this.regionOrigen = regionOrigen;
	}

	/**
	 * @return the propiosMedios
	 */
	public Long getPropiosMedios() {
		return propiosMedios;
	}

	/**
	 * @param propiosMedios
	 *            the propiosMedios to set
	 */
	public void setPropiosMedios(Long propiosMedios) {
		this.propiosMedios = propiosMedios;
	}

	/**
	 * @return the mercanciaZonaPrimaria
	 */
	public Long getMercanciaZonaPrimaria() {
		return mercanciaZonaPrimaria;
	}

	/**
	 * @param mercanciaZonaPrimaria
	 *            the mercanciaZonaPrimaria to set
	 */
	public void setMercanciaZonaPrimaria(Long mercanciaZonaPrimaria) {
		this.mercanciaZonaPrimaria = mercanciaZonaPrimaria;
	}

	/**
	 * @return the adquiridasCIF
	 */
	public Long getAdquiridasCIF() {
		return adquiridasCIF;
	}

	/**
	 * @param adquiridasCIF
	 *            the adquiridasCIF to set
	 */
	public void setAdquiridasCIF(Long adquiridasCIF) {
		this.adquiridasCIF = adquiridasCIF;
	}

	/**
	 * @return the origenCIF
	 */
	public Long getOrigenCIF() {
		return origenCIF;
	}

	/**
	 * @param origenCIF
	 *            the origenCIF to set
	 */
	public void setOrigenCIF(Long origenCIF) {
		this.origenCIF = origenCIF;
	}

	/**
	 * @return the paisOrigen
	 */
	public String getPaisOrigen() {
		return paisOrigen;
	}

	/**
	 * @param paisOrigen
	 *            the paisOrigen to set
	 */
	public void setPaisOrigen(String paisOrigen) {
		this.paisOrigen = paisOrigen;
	}

	/**
	 * @return the paisAdquisicion
	 */
	public String getPaisAdquisicion() {
		return paisAdquisicion;
	}

	/**
	 * @param paisAdquisicion
	 *            the paisAdquisicion to set
	 */
	public void setPaisAdquisicion(String paisAdquisicion) {
		this.paisAdquisicion = paisAdquisicion;
	}

	
	
}