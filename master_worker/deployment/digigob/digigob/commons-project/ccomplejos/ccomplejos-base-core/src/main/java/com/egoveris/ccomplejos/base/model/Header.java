package com.egoveris.ccomplejos.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CC_HEADER")
public class Header extends AbstractCComplejoJPA {

	@Column(name = "ID_HEADER")
	protected Long idHeader;

	@Column(name = "DESTINACION_ADUANERA")
	protected String destinacionAduanera;

	@Column(name = "TIPO_OPERACION")
	protected String tipoOperacion;

	@Column(name = "TIPO_TRAMITE")
	protected String tipoTramite;

	@Column(name = "ADUANA_TRAMITACION")
	protected String aduanaTramitacion;

	@Column(name = "NUMERO_INTERNO_DESPACHO")
	protected String numeroInternoDespacho;

	@Column(name = "COMENTARIO")
	protected String comentario;

	@Column(name = "TIPO_INGRESO")
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
