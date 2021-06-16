package com.egoveris.tica.base.model;

import java.io.Serializable;

/**
 * The Class FirmaParam.
 */
public class FirmaParam implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7068765183767084213L;

	/** The datos. */
	private byte[] datos;

	/** The datos firmados. */
	private String datosFirmados;

	/** The alias cert. */
	private String aliasCert;

	/** The algoritmo hash. */
	private String algoritmoHash;

	/** The formato firma. */
	private String formatoFirma;               

	/** The id aplicacion. */
	private String idAplicacion;

	/** The nombre fichero. */
	private String nombreFichero;

	/** The id transaccion. */
	private String idTransaccion;

	/** The firmante. */
	private String firmante;

	/** The id referencia. */
	private String idReferencia;

	/** The ticket. */
	private String ticket;

	/** The firmante objetivo. */
	private String firmanteObjetivo;

	/** The tipo documento. */
	private String tipoDocumento;


	/**
	 * Gets the tipo documento.
	 *
	 * @return the tipoDocumento
	 */
	public String getTipoDocumento() {
		return tipoDocumento;
	}

	/**
	 * Sets the tipo documento.
	 *
	 * @param tipoDocumento the tipoDocumento to set
	 */
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	/**
	 * Gets the firmante.
	 *
	 * @return the firmante
	 */
	public String getFirmante() {
		return firmante;
	}

	/**
	 * Sets the firmante.
	 *
	 * @param firmante the firmante to set
	 */
	public void setFirmante(String firmante) {
		this.firmante = firmante;
	}

	/**
	 * Gets the algoritmo hash.
	 *
	 * @return the algoritmoHash
	 */
	public String getAlgoritmoHash() {
		return algoritmoHash;
	}

	/**
	 * Sets the algoritmo hash.
	 *
	 * @param algoritmoHash the algoritmoHash to set
	 */
	public void setAlgoritmoHash(String algoritmoHash) {
		this.algoritmoHash = algoritmoHash;
	}

	/**
	 * Gets the alias cert.
	 *
	 * @return the aliasCert
	 */
	public String getAliasCert() {
		return aliasCert;
	}

	/**
	 * Sets the alias cert.
	 *
	 * @param aliasCert the aliasCert to set
	 */
	public void setAliasCert(String aliasCert) {
		this.aliasCert = aliasCert;
	}

	/**
	 * Gets the datos.
	 *
	 * @return the datos
	 */
	public byte[] getDatos() {
		return datos;
	}

	/**
	 * Sets the datos.
	 *
	 * @param datos the datos to set
	 */
	public void setDatos(byte[] datos) {
		this.datos = datos;
	}

	/**
	 * Gets the datos firmados.
	 *
	 * @return the datosFirmados
	 */
	public String getDatosFirmados() {
		return datosFirmados;
	}

	/**
	 * Sets the datos firmados.
	 *
	 * @param datosFirmados the datosFirmados to set
	 */
	public void setDatosFirmados(String datosFirmados) {
		this.datosFirmados = datosFirmados;
	}

	/**
	 * Gets the formato firma.
	 *
	 * @return the formatoFirma
	 */
	public String getFormatoFirma() {
		return formatoFirma;
	}

	/**
	 * Sets the formato firma.
	 *
	 * @param formatoFirma the formatoFirma to set
	 */
	public void setFormatoFirma(String formatoFirma) {
		this.formatoFirma = formatoFirma;
	}

	/**
	 * Gets the id aplicacion.
	 *
	 * @return the idAplicacion
	 */
	public String getIdAplicacion() {
		return idAplicacion;
	}

	/**
	 * Sets the id aplicacion.
	 *
	 * @param idAplicacion the idAplicacion to set
	 */
	public void setIdAplicacion(String idAplicacion) {
		this.idAplicacion = idAplicacion;
	}

	/**
	 * Gets the id transaccion.
	 *
	 * @return the idTransaccion
	 */
	public String getIdTransaccion() {
		return idTransaccion;
	}

	/**
	 * Sets the id transaccion.
	 *
	 * @param idTransaccion the idTransaccion to set
	 */
	public void setIdTransaccion(String idTransaccion) {
		this.idTransaccion = idTransaccion;
	}

	/**
	 * Gets the nombre fichero.
	 *
	 * @return the nombreFichero
	 */
	public String getNombreFichero() {
		return nombreFichero;
	}

	/**
	 * Sets the nombre fichero.
	 *
	 * @param nombreFichero the nombreFichero to set
	 */
	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}

	/**
	 * Gets the ticket.
	 *
	 * @return the ticket
	 */
	public String getTicket() {
		return ticket;
	}

	/**
	 * Sets the ticket.
	 *
	 * @param ticket the ticket to set
	 */
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	/**
	 * Gets the id referencia.
	 *
	 * @return the idReferencia
	 */
	public String getIdReferencia() {
		return idReferencia;
	}

	/**
	 * Sets the id referencia.
	 *
	 * @param idReferencia the idReferencia to set
	 */
	public void setIdReferencia(String idReferencia) {
		this.idReferencia = idReferencia;
	}

	/**
	 * To string data.
	 *
	 * @return the string
	 */
	public String toStringData(){
		StringBuilder buffer = new StringBuilder();
		buffer.append("FirmaParam");
		buffer.append("AlgoritmoHash ").append(getAlgoritmoHash());
		buffer.append("FormatoFirma ").append(getFormatoFirma());
		buffer.append("AliasCert ").append(getAliasCert());
		buffer.append("IdAplicacion ").append(getIdAplicacion());	
		buffer.append("Ticket ").append(getTicket());

		return buffer.toString();
	}

	/**
	 * Gets the firmante objetivo.
	 *
	 * @return the firmanteObjetivo
	 */
	public String getFirmanteObjetivo() {
		return firmanteObjetivo;
	}

	/**
	 * Sets the firmante objetivo.
	 *
	 * @param firmanteObjetivo the firmanteObjetivo to set
	 */
	public void setFirmanteObjetivo(String firmanteObjetivo) {
		this.firmanteObjetivo = firmanteObjetivo;
	}


}
