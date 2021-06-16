package com.egoveris.deo.model.model;

import java.io.Serializable;

public class TipoDocumentoEmbebidosPKDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private TipoDocumentoDTO tipoDocumentoId;
	
	private FormatoTamanoArchivoDTO formatoTamanoId;

	public TipoDocumentoDTO getTipoDocumentoId() {
		return tipoDocumentoId;
	}

	public void setTipoDocumentoId(TipoDocumentoDTO tipoDocumentoId) {
		this.tipoDocumentoId = tipoDocumentoId;
	}

	public FormatoTamanoArchivoDTO getFormatoTamanoId() {
		return formatoTamanoId;
	}

	public void setFormatoTamanoId(FormatoTamanoArchivoDTO formatoTamanoId) {
		this.formatoTamanoId = formatoTamanoId;
	}
	
	
//	private Integer idTipoDocumento;
//	private Integer idFormato;
//	
//	public Integer getIdTipoDocumento() {
//		return idTipoDocumento;
//	}
//	public void setIdTipoDocumento(Integer idTipoDocumento) {
//		this.idTipoDocumento = idTipoDocumento;
//	}
//	public Integer getIdFormato() {
//		return idFormato;
//	}
//	public void setIdFormato(Integer idFormato) {
//		this.idFormato = idFormato;
//	}


	
	
}