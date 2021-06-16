package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;

public class HeaderDTO extends AbstractCComplejoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	protected Long idHeader;
	protected String destinacionAduanera;
	protected String tipoOperacion;
	protected String tipoTramite;
	protected String aduanaTramitacion;
	protected String numeroInternoDespacho;
	protected String comentario;
	protected String tipoIngreso;
	
	
	public Long getIdHeader() {
		return idHeader;
	}
	public void setIdHeader(Long idHeader) {
		this.idHeader = idHeader;
	}
	public String getDestinacionAduanera() {
		return destinacionAduanera;
	}
	public void setDestinacionAduanera(String destinacionAduanera) {
		this.destinacionAduanera = destinacionAduanera;
	}
	public String getTipoOperacion() {
		return tipoOperacion;
	}
	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}
	public String getTipoTramite() {
		return tipoTramite;
	}
	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}
	public String getAduanaTramitacion() {
		return aduanaTramitacion;
	}
	public void setAduanaTramitacion(String aduanaTramitacion) {
		this.aduanaTramitacion = aduanaTramitacion;
	}
	public String getNumeroInternoDespacho() {
		return numeroInternoDespacho;
	}
	public void setNumeroInternoDespacho(String numeroInternoDespacho) {
		this.numeroInternoDespacho = numeroInternoDespacho;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public String getTipoIngreso() {
		return tipoIngreso;
	}
	public void setTipoIngreso(String tipoIngreso) {
		this.tipoIngreso = tipoIngreso;
	}
	
}
