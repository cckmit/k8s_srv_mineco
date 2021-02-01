package com.egoveris.vucfront.model.model;

import java.io.Serializable;
import java.util.Date;

public class DocumentoEstadoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5700974503712504363L;
	
	
	private Long id;
	private DocumentoDTO documento;
	private Date fecha;
	private String estado;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public DocumentoDTO getDocumento() {
		return documento;
	}
	public void setDocumento(DocumentoDTO documento) {
		this.documento = documento;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}

	
}
