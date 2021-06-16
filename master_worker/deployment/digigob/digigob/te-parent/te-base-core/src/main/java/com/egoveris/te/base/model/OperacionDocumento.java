package com.egoveris.te.base.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TE_OPERACION_DOC")
public class OperacionDocumento {

	@EmbeddedId
	private OperacionDocumentoPK pk;
	
	@OneToOne
	@JoinColumn(name = "ID_OPERACION", insertable = false, updatable = false)
	private Operacion operacion;
	
	@OneToOne
	@JoinColumn(name = "ID_GEDO_TIPODOCUMENTO", insertable = false, updatable = false)
	private TipoDocumentoGedo tipoDocumentoGedo;
	
	@OneToOne
  @JoinColumn(name = "NUMERO_DOCUMENTO", referencedColumnName="numero", insertable = false, updatable = false)
	private DocumentoGedo documentoGedo;
	
	@Column(name = "NUMERO_DOCUMENTO")
	private String numeroDocumento;
	
	@Column(name = "NOMBRE_ARCHIVO")
	private String nombreArchivo;

	public OperacionDocumentoPK getPk() {
		return pk;
	}

	public void setPk(OperacionDocumentoPK pk) {
		this.pk = pk;
	}

	public Operacion getOperacion() {
		return operacion;
	}

	public void setOperacion(Operacion operacion) {
		this.operacion = operacion;
	}

	public TipoDocumentoGedo getTipoDocumentoGedo() {
		return tipoDocumentoGedo;
	}

	public void setTipoDocumentoGedo(TipoDocumentoGedo tipoDocumentoGedo) {
		this.tipoDocumentoGedo = tipoDocumentoGedo;
	}

	public DocumentoGedo getDocumentoGedo() {
		return documentoGedo;
	}

	public void setDocumentoGedo(DocumentoGedo documentoGedo) {
		this.documentoGedo = documentoGedo;
	}
	
	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}
	
}
