package com.egoveris.deo.model.model;


public class MedicionMetodosConfigDTO {

	private String acronimoTipoDocumento;

	private String usuarioActual;
	
	private String formatoResultado;
	
	private String contenido;
	
	private String tipoContenido;
	
	private String referencia;
	
	public String getAcronimoTipoDocumento() {
		return acronimoTipoDocumento;
	}
	
	public void setAcronimoTipoDocumento(String acronimoTipoDocumento) {
		this.acronimoTipoDocumento = acronimoTipoDocumento;
	}
	
	public String getUsuarioActual() {
		return usuarioActual;
	}
	
	public void setUsuarioActual(String usuarioActual) {
		this.usuarioActual = usuarioActual;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
	
	public String getContenido() {
		return contenido;
	}

	public void setFormatoResultado(String formatoResultado) {
		this.formatoResultado = formatoResultado;
	}

	public String getFormatoResultado() {
		return formatoResultado;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getReferencia() {
		return referencia;
	}

	public String getTipoContenido() {
		return tipoContenido;
	}

	public void setTipoContenido(String tipoContenido) {
		this.tipoContenido = tipoContenido;
	}
}
