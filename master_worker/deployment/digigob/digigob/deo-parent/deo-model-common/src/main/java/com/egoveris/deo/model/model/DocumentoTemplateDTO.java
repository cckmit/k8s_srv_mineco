package com.egoveris.deo.model.model;

import java.io.Serializable;

public class DocumentoTemplateDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private DocumentoTemplatePKDTO documentoTemplatePK;
	private Integer idTransaccion;
	private TipoDocumentoTemplateDTO tipoDocumentoTemplate;
	
	public DocumentoTemplatePKDTO getDocumentoTemplatePK() {
		return documentoTemplatePK;
	}
	public void setDocumentoTemplatePK(DocumentoTemplatePKDTO documentoTemplatePK) {
		this.documentoTemplatePK = documentoTemplatePK;
	}
	public Integer getIdTransaccion() {
		return idTransaccion;
	}
	public void setIdTransaccion(Integer idTransaccion) {
		this.idTransaccion = idTransaccion;
	}
	public TipoDocumentoTemplateDTO getTipoDocumentoTemplate() {
		return tipoDocumentoTemplate;
	}
	public void setTipoDocumentoTemplate(TipoDocumentoTemplateDTO tipoDocumentoTemplate) {
		this.tipoDocumentoTemplate = tipoDocumentoTemplate;
	}
}
