package com.egoveris.te.base.model.tipo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.egoveris.te.base.model.TipoDocumentoGedo;

@Entity
@Table(name = "TE_TIPO_OPER_DOCUMENTO")
public class TipoOperacionDocumento implements Serializable {

	private static final long serialVersionUID = 5983624031998297331L;
	
	@EmbeddedId
	private TipoOperacionDocumentoPK pk;
	
	@ManyToOne
	@JoinColumn(name = "ID_TIPO_OPERACION", insertable = false, updatable = false)
	private TipoOperacion tipoOperacion;
	
	@ManyToOne
	@JoinColumn(name = "ID_GEDO_TIPODOCUMENTO", insertable = false, updatable = false)
	private TipoDocumentoGedo tipoDocumentoGedo;
	
	@Column(name="OPCIONAL")
	private boolean opcional;
	
	@Column(name="OBLIGATORIO")
	private boolean obligatorio;
	
	public TipoOperacionDocumentoPK getPk() {
		return pk;
	}
	
	public void setPk(TipoOperacionDocumentoPK pk) {
		this.pk = pk;
	}
	
	public TipoOperacion getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(TipoOperacion tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	public TipoDocumentoGedo getTipoDocumentoGedo() {
		return tipoDocumentoGedo;
	}

	public void setTipoDocumentoGedo(TipoDocumentoGedo tipoDocumentoGedo) {
		this.tipoDocumentoGedo = tipoDocumentoGedo;
	}

	public boolean isOpcional() {
		return opcional;
	}

	public void setOpcional(boolean opcional) {
		this.opcional = opcional;
	}

	public boolean isObligatorio() {
		return obligatorio;
	}

	public void setObligatorio(boolean obligatorio) {
		this.obligatorio = obligatorio;
	}
	
}
