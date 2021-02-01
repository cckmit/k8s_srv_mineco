package com.egoveris.vucfront.model.model;

import java.io.Serializable;
import java.util.Date;

public class DocumentoVucDTO implements Serializable {

	private static final long serialVersionUID = 6867007178837858673L;

	private String nombreOriginal;
	private Date fechaCreacion;
	private TipoDocumentoVucDTO tipoDocumento;
	private boolean seleccionado;
	private String numeroSade;
	private String motivo;

	public String getNombreOriginal() {
		return nombreOriginal;
	}

	public void setNombreOriginal(String nombreOriginal) {
		this.nombreOriginal = nombreOriginal;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public TipoDocumentoVucDTO getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumentoVucDTO tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public boolean isSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}

	public String getNumeroSade() {
		return numeroSade;
	}

	public void setNumeroSade(String numeroSade) {
		this.numeroSade = numeroSade;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
}