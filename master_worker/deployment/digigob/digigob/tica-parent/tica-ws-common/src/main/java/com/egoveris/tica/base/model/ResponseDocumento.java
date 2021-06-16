package com.egoveris.tica.base.model;

import java.io.Serializable;


/**
 * The Class ResponseDocumento.
 */
public class ResponseDocumento implements Serializable  {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1163899880601470569L;
	
	/** The data. */
	private byte[] data;
	
	/** The campo firma. */
	private String campoFirma;
	
	/** The location. */
	private String location;
	
	/** The usuario. */
	private String usuario;
	
	/** The cargo. */
	private String cargo;
	
	/** The organismo. */
	private String organismo;
	
	/** The sector. */
	private String sector;
	
	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public byte[] getData() {
		return data;
	}
	
	/**
	 * Sets the data.
	 *
	 * @param data the new data
	 */
	public void setData(byte[] data) {
		this.data = data;
	}
	
	/**
	 * Gets the campo firma.
	 *
	 * @return the campo firma
	 */
	public String getCampoFirma() {
		return campoFirma;
	}
	
	/**
	 * Sets the campo firma.
	 *
	 * @param campoFirma the new campo firma
	 */
	public void setCampoFirma(String campoFirma) {
		this.campoFirma = campoFirma;
	}
	
	/**
	 * Gets the location.
	 *
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}
	
	/**
	 * Sets the location.
	 *
	 * @param location the new location
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	
	/**
	 * Gets the usuario.
	 *
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}
	
	/**
	 * Sets the usuario.
	 *
	 * @param usuario the new usuario
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	/**
	 * Gets the cargo.
	 *
	 * @return the cargo
	 */
	public String getCargo() {
		return cargo;
	}
	
	/**
	 * Sets the cargo.
	 *
	 * @param cargo the new cargo
	 */
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	
	/**
	 * Gets the organismo.
	 *
	 * @return the organismo
	 */
	public String getOrganismo() {
		return organismo;
	}
	
	/**
	 * Sets the organismo.
	 *
	 * @param organismo the new organismo
	 */
	public void setOrganismo(String organismo) {
		this.organismo = organismo;
	}
	
	/**	
	 * Gets the sector.
	 * 
	 * @return the sector
	 */
	public String getSector() {
		return sector;
	}

	/**
	 * Sets the sector.
	 * 
	 * @param sector the new sector
	 */
	public void setSector(String sector) {
		this.sector = sector;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return "[data: " +  data.length + "] "
				+ " [campoFirma: " + campoFirma + "]"
				+ " [location: " + location + "]";
	}
}
