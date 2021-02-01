package com.egoveris.ccomplejos.base.model;

import java.util.Date;

public class VistaOperacionRespuestaDinDTO extends AbstractCComplejoDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5170166788082573082L;

	String codigoOperacion;
	String creadoPor;
	Date fechaCreacion;
	Date fechaEstado;
	
	/**
	 * @return the codigoOperacion
	 */
	public String getCodigoOperacion() {
		return codigoOperacion;
	}
	
	/**
	 * @param codigoOperacion
	 *            the codigoOperacion to set
	 */
	public void setCodigoOperacion(String codigoOperacion) {
		this.codigoOperacion = codigoOperacion;
	}
	
	/**
	 * @return the creadoPor
	 */
	public String getCreadoPor() {
		return creadoPor;
	}
	
	/**
	 * @param creadoPor
	 *            the creadoPor to set
	 */
	public void setCreadoPor(String creadoPor) {
		this.creadoPor = creadoPor;
	}
	
	/**
	 * @return the fechaCreacion
	 */
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	
	/**
	 * @param fechaCreacion
	 *            the fechaCreacion to set
	 */
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	
	/**
	 * @return the fechaEstado
	 */
	public Date getFechaEstado() {
		return fechaEstado;
	}
	
	/**
	 * @param fechaEstado
	 *            the fechaEstado to set
	 */
	public void setFechaEstado(Date fechaEstado) {
		this.fechaEstado = fechaEstado;
	}

}