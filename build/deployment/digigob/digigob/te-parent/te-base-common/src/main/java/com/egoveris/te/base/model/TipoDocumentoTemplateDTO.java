package com.egoveris.te.base.model;

import java.io.Serializable;
import java.sql.Blob;
import java.util.Date;

public class TipoDocumentoTemplateDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private TipoDocumentoTemplatePKDTO tipoDocumentoTemplatePK;
	private transient Blob template;
	private String descripcion;
	private Date fechaCreacion;
	private String usuarioAlta;
	private String idFormulario;
	
	private TipoDocumentoDTO tipoDocumento;
	
	public TipoDocumentoTemplatePKDTO getTipoDocumentoTemplatePK() {
		return tipoDocumentoTemplatePK;
	}
	public void setTipoDocumentoTemplatePK(TipoDocumentoTemplatePKDTO tipoDocumentoTemplatePK) {
		this.tipoDocumentoTemplatePK = tipoDocumentoTemplatePK;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public String getUsuarioAlta() {
		return usuarioAlta;
	}
	public void setUsuarioAlta(String usuarioAlta) {
		this.usuarioAlta = usuarioAlta;
	}
	public Blob getTemplate() {
		return template;
	}
	public void setTemplate(Blob template) {
		this.template = template;
	}
	public String getIdFormulario() {
		return idFormulario;
	}
	public void setIdFormulario(String idFormulario) {
		this.idFormulario = idFormulario;
	}
	
	public TipoDocumentoDTO getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(TipoDocumentoDTO tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
}

