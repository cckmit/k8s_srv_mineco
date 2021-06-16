package com.egoveris.te.base.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Estados en los que puede estar un expediente electronico.
 * 
 * @author Juan Pablo Norverto
 * @author Rocco Gallo Citera
 *
 */
@Deprecated
public class Estado implements Serializable{

	private static final long serialVersionUID = 751453890704715254L;
	private Long id;
	private Date fechaModificacion;
	private Date fechaCreacion;
	private String usuarioModificacion;
	private String nombreEstado;
	enum estadosPosibles {
		INICIAR_EXPEDIENTE, 
		ANULAR_MODIFICAR_SOLICITUD,
		INICIACION,
		SUBSANACION,
		TRAMITACION,
		COMUNICACION,
		EJECUCION,
		ARCHIVO
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getFechaModificacion() {
		return fechaModificacion;
	}
	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public String getUsuarioModificacion() {
		return usuarioModificacion;
	}
	public void setUsuarioModificacion(String usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}
	public String getNombreEstado() {
		return nombreEstado;
	}
	public void setNombreEstado(String nombreEstado) {
		this.nombreEstado = nombreEstado;
	}
	
	
	
}
