package com.egoveris.deo.base.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class TipoDocumentoEmbebidosPK implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 2188187597447540575L;

	@ManyToOne
	@JoinColumn(name = "ID_TIPODOCUMENTO")
	private TipoDocumento tipoDocumentoId;
	
	@ManyToOne
	@JoinColumn(name = "ID_FORMATO")
	private FormatoTamanoArchivo formatoTamanoId;
	
	public TipoDocumentoEmbebidosPK() {
		super();
	}
	
	public TipoDocumentoEmbebidosPK(TipoDocumento tipoDocumentoId, FormatoTamanoArchivo idFormato) {
	  super();
	  this.tipoDocumentoId = tipoDocumentoId;
	  this.formatoTamanoId = idFormato;
	}
	
	public TipoDocumentoEmbebidosPK(Integer idTipoDocumento, Integer idFormato) {
		super();
		this.tipoDocumentoId.setId(idTipoDocumento);
		this.formatoTamanoId.setId(idFormato);
	}
	
	public TipoDocumento getTipoDocumentoId() {
		return tipoDocumentoId;
	}
	
	public void setTipoDocumentoId(TipoDocumento tipoDocumentoId) {
		this.tipoDocumentoId = tipoDocumentoId;
	}
	
	public FormatoTamanoArchivo getFormatoTamanoId() {
		return formatoTamanoId;
	}
	
	public void setFormatoTamanoId(FormatoTamanoArchivo formatoTamanoId) {
		this.formatoTamanoId = formatoTamanoId;
	}
	
	@Override
	public boolean equals(Object o) {
	  return ((o instanceof TipoDocumentoEmbebidosPK)
	      && tipoDocumentoId.equals(((TipoDocumentoEmbebidosPK) o).getTipoDocumentoId()))
	      && formatoTamanoId == ((TipoDocumentoEmbebidosPK) o).getFormatoTamanoId();
	}
	
	@Override
	public int hashCode() {
	  return tipoDocumentoId.getId().hashCode() + formatoTamanoId.getId().intValue();
	}
  

}
