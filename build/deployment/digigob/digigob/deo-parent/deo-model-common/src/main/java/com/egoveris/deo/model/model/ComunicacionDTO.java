package com.egoveris.deo.model.model;

import java.io.Serializable;
import java.util.Date;

public class ComunicacionDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2160344301518309129L;

	private Integer id;
	
	private DocumentoDTO documento;					
	
	private String mensaje;
	
	private Date fechaCreacion;			

	private Integer idComunicacion;
	
	private String usuarioCreador;
	
	private String nombreApellidoUsuario;
	
	private Date fechaEliminacion;
	
	private String nroComunicacionRespondida;
	
	private Boolean tieneAdjuntos;
	
	private Boolean isSelected = false;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}	
	
	public DocumentoDTO getDocumento() {
		return documento;
	}

	public void setDocumento(DocumentoDTO documento) {
		this.documento = documento;
	}		

	public Integer getIdComunicacion() {
		return idComunicacion;
	}

	public void setIdComunicacion(Integer idComunicacion) {
		this.idComunicacion = idComunicacion;
	}

	public String getUsuarioCreador() {
		return usuarioCreador;
	}

	public void setUsuarioCreador(String usuarioCreador) {
		this.usuarioCreador = usuarioCreador;
	}

	public String getNombreApellidoUsuario() {
		return nombreApellidoUsuario;
	}

	public void setNombreApellidoUsuario(String nombreApellidoUsuario) {
		this.nombreApellidoUsuario = nombreApellidoUsuario;
	}

	public Date getFechaEliminacion() {
		return fechaEliminacion;
	}

	public void setFechaEliminacion(Date fechaEliminacion) {
		this.fechaEliminacion = fechaEliminacion;
	}

	public String getNroComunicacionRespondida() {
		return nroComunicacionRespondida;
	}

	public void setNroComunicacionRespondida(String nroComunicacionRespondida) {
		this.nroComunicacionRespondida = nroComunicacionRespondida;
	}

	public Boolean getTieneAdjuntos() {
		return tieneAdjuntos;
	}

	public void setTieneAdjuntos(Boolean tieneAdjuntos) {
		this.tieneAdjuntos = tieneAdjuntos;
	}

	public Boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}
		
}
