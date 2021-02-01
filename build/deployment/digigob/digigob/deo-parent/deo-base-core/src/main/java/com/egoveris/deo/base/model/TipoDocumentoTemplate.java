package com.egoveris.deo.base.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "GEDO_TIPODOCUMENTO_TEMPLATE")
public class TipoDocumentoTemplate {

	@EmbeddedId
	private TipoDocumentoTemplatePK tipoDocumentoTemplatePK;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "TEMPLATE")
	private String template;

	@Column(name = "DESCRIPCION")
	private String descripcion;

	@Column(name = "FECHA_CREACION")
	private Date fechaCreacion;

	@Column(name = "USUARIO_ALTA")
	private String usuarioAlta;

	@Column(name = "ID_FORMULARIO")
	private String idFormulario;

	public TipoDocumentoTemplatePK getTipoDocumentoTemplatePK() {
		return tipoDocumentoTemplatePK;
	}

	public void setTipoDocumentoTemplatePK(TipoDocumentoTemplatePK tipoDocumentoTemplatePK) {
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

	public String getIdFormulario() {
		return idFormulario;
	}

	public void setIdFormulario(String idFormulario) {
		this.idFormulario = idFormulario;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

}
