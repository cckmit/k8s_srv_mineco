package com.egoveris.te.model.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TrataEE implements Serializable {

	private static final long serialVersionUID = -980776937896230653L;

	/**
	 * Identificador de la trata
	 */
	private Long id;
	/**
	 * Código que identifica a la trata.
	 */
	private String codigoTrata;
	/**
	 * Descripción de la trata.
	 */
	private String descripcionTrata;
	/**
	 * Identificador de la trata según SADE.
	 */
	private Integer idTrataSade;
	/**
	 * Código que recibe la trata en SADE.
	 */
	private String trataSade;
	/**
	 * Alta: La trata se encuentra disponible para seguir creando instancias con ella.
	 * Baja: trata no disponible.
	 */
	private String estado;
	/**
	 *  
	 */
	private TipoReservaDTO tipoReserva; 
	/**
	 * 
	 */
	private Boolean esAutomatica;
	/**
	 * 
	 */
	private Boolean esManual;
	
	/**
	 * 
	 */
	private String acronimoGedo;

	/**
	 * 
	 */
	private List<ExpedienteMetadataExternal> datoVariable = new ArrayList<ExpedienteMetadataExternal>();//Lista de los metadata pertenecientes a la trata.

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigoTrata() {
		return codigoTrata;
	}

	public void setCodigoTrata(String codigoTrata) {
		this.codigoTrata = codigoTrata;
	}

	public String getDescripcionTrata() {
		return descripcionTrata;
	}

	public void setDescripcionTrata(String descripcionTrata) {
		this.descripcionTrata = descripcionTrata;
	}

	public Integer getIdTrataSade() {
		return idTrataSade;
	}

	public void setIdTrataSade(Integer idTrataSade) {
		this.idTrataSade = idTrataSade;
	}

	public String getTrataSade() {
		return trataSade;
	}

	public void setTrataSade(String trataSade) {
		this.trataSade = trataSade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public TipoReservaDTO getTipoReserva() {
		return tipoReserva;
	}

	public void setTipoReserva(TipoReservaDTO tipoReserva) {
		this.tipoReserva = tipoReserva;
	}

	public Boolean getEsAutomatica() {
		return esAutomatica;
	}

	public void setEsAutomatica(Boolean esAutomatica) {
		this.esAutomatica = esAutomatica;
	}

	public Boolean getEsManual() {
		return esManual;
	}

	public void setEsManual(Boolean esManual) {
		this.esManual = esManual;
	}

	public List<ExpedienteMetadataExternal> getDatoVariable() {
		return datoVariable;
	}

	public void setDatoVariable(List<ExpedienteMetadataExternal> datoVariable) {
		this.datoVariable = datoVariable;
	}
	
	/*JIRA: https://quark.everis.com/jira/browse/BISADE-3743*/
	@Override
	public String toString() { 
		return this.codigoTrata; 
	}

	public void setAcronimoGedo(String acronimoGedo) {
		this.acronimoGedo = acronimoGedo;
	}

	public String getAcronimoGedo() {
		return acronimoGedo;
	} 
}
