/**
 * 
 */
package com.egoveris.te.base.model;

import java.io.Serializable;


/**
 * @author difarias
 *
 */
@SuppressWarnings("serial")
public class TramitacionLibre implements Serializable{
	private ExpedienteElectronicoDTO ee;
	private String fechaPase;
	private String usuarioOrigen;
	private String destino;
	private String motivo;
	private boolean adquirible;

	/**
	 * Default constructor
	 * @param ee
	 */
	public TramitacionLibre() {
	}
	
	/**
	 * Constructor with parameters
	 * @param ee
	 * @param usuarioOrigen
	 * @param destino
	 */
	public TramitacionLibre(ExpedienteElectronicoDTO ee, String fechaPase, String usuarioOrigen,	String destino, String motivo, boolean adquirible) {
		this.ee = ee;
		this.fechaPase = fechaPase;
		this.usuarioOrigen = usuarioOrigen;
		this.destino = destino;
		this.motivo = motivo;
		this.adquirible = adquirible;
	}
	
	
	/**
	 * @return the fechaPase
	 */
	public String getFechaPase() {
		return fechaPase;
	}

	/**
	 * @param fechaPase the fechaPase to set
	 */
	public void setFechaPase(String fechaPase) {
		this.fechaPase = fechaPase;
	}

	/**
	 * @return the ee
	 */
	public ExpedienteElectronicoDTO getEe() {
		return ee;
	}
	/**
	 * @param ee the ee to set
	 */
	public void setEe(ExpedienteElectronicoDTO ee) {
		this.ee = ee;
	}
	/**
	 * @return the usuarioOrigen
	 */
	public String getUsuarioOrigen() {
		return usuarioOrigen;
	}
	/**
	 * @param usuarioOrigen the usuarioOrigen to set
	 */
	public void setUsuarioOrigen(String usuarioOrigen) {
		this.usuarioOrigen = usuarioOrigen;
	}
	/**
	 * @return the destino
	 */
	public String getDestino() {
		return destino;
	}
	/**
	 * @param destino the destino to set
	 */
	public void setDestino(String destino) {
		this.destino = destino;
	}

	/**
	 * @return the motivo
	 */
	public String getMotivo() {
		return motivo;
	}

	/**
	 * @param motivo the motivo to set
	 */
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	/**
	 * @return the adquirible
	 */
	public boolean isAdquirible() {
		return adquirible;
	}

	/**
	 * @param adquirible the adquirible to set
	 */
	public void setAdquirible(boolean adquirible) {
		this.adquirible = adquirible;
	}

	
}
