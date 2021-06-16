package com.egoveris.te.base.model;

import java.io.Serializable;
import java.util.Date;

public class DocumentoSubsanableDTO implements Serializable {

	private static final long serialVersionUID = 6777548518187488642L;

	private boolean subsanado;
	private boolean subsanadoLimitado;
	private String usuarioSubsanador;
	private Date fechaSubsanacion;

	public DocumentoSubsanableDTO() {
	}
	public DocumentoSubsanableDTO(boolean subsanado, boolean subsanadoLimitado) {
		this.subsanado = subsanado;
		this.subsanadoLimitado = subsanadoLimitado;
	}
	
	public boolean isSubsanado() {
		return subsanado;
	}

	public void setSubsanado(boolean subsanado) {
		this.subsanado = subsanado;
	}

	public boolean isSubsanadoLimitado() {
		return subsanadoLimitado;
	}

	public void setSubsanadoLimitado(boolean subsanadoLimitado) {
		this.subsanadoLimitado = subsanadoLimitado;
	}
	
	public String getUsuarioSubsanador() {
		return usuarioSubsanador;
	}

	public void setUsuarioSubsanador(String usuarioSubsanador) {
		this.usuarioSubsanador = usuarioSubsanador;
	}

	public Date getFechaSubsanacion() {
		return fechaSubsanacion;
	}

	public void setFechaSubsanacion(Date fechaSubsanacion) {
		this.fechaSubsanacion = fechaSubsanacion;
	}
}
