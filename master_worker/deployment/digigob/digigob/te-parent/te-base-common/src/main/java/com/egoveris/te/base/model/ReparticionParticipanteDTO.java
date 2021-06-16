package com.egoveris.te.base.model;

import java.io.Serializable;
import java.util.Date;
/**
 * @author dpadua
 */
public class ReparticionParticipanteDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long idReparticion;
	private String reparticion;
	private String tipoOperacion;
	private Date fechaModificacion;
	private String usuario;

	public Long getIdReparticion() {
		return idReparticion;
	}

	public void setIdReparticion(Long idReparticion) {
		this.idReparticion = idReparticion;
	}

	public String getReparticion() {
		return reparticion;
	}

	public void setReparticion(String reparticion) {
		this.reparticion = reparticion;
	}

	public String getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	
	
public ReparticionParticipanteDTO(){
		
	}
	
	public ReparticionParticipanteDTO(ReparticionParticipanteDTO r) {
		this.idReparticion = r.getIdReparticion();
		this.reparticion = r.getReparticion();
		this.tipoOperacion = r.getTipoOperacion();
		this.fechaModificacion = r.getFechaModificacion();
		this.usuario = r.getUsuario();
	}
}
