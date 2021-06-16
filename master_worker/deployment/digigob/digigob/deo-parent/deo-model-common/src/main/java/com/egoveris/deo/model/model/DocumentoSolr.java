package com.egoveris.deo.model.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.solr.client.solrj.beans.Field;

public class DocumentoSolr implements Serializable {

	private static final long serialVersionUID = 7270760696514290867L;

	private Long id;
	@Field(value = "fecha_creacion")
	private Date fechaCreacion;
	@Field(value = "actuacion_acr")
	private String actuacionAcr;
	@Field(value = "nro_sade")
	private String nroSade;
	@Field(value = "nro_sade_papel")
	private String nroSadePapel;
	@Field(value = "nro_especial_sade")
	private String nroEspecialSade;
	@Field(value = "usuario_generador")
	private String usuarioGenerador;
	@Field(value = "reparticion_generador")
	private String reparticion;
	@Field(value = "referencia")
	private String referencia;
	@Field(value = "tipo_doc_acr")
	private String tipoDocAcr;
	@Field(value = "tipo_doc_nombre")
	private String tipoDocNombre;
	@Field(value = "anio_doc")
	private Integer anioDoc;
	@Field(value = "nro_doc")
	private Long nroDoc;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getActuacionAcr() {
		return actuacionAcr;
	}

	public void setActuacionAcr(String actuacionAcr) {
		this.actuacionAcr = actuacionAcr;
	}

	public String getNroSade() {
		return nroSade;
	}

	public void setNroSade(String nroSade) {
		this.nroSade = nroSade;
	}

	public String getNroSadePapel() {
		return nroSadePapel;
	}

	public void setNroSadePapel(String nroSadePapel) {
		this.nroSadePapel = nroSadePapel;
	}

	public String getNroEspecialSade() {
		return nroEspecialSade;
	}

	public void setNroEspecialSade(String nroEspecialSade) {
		this.nroEspecialSade = nroEspecialSade;
	}

	public String getUsuarioGenerador() {
		return usuarioGenerador;
	}

	public void setUsuarioGenerador(String usuarioGenerador) {
		this.usuarioGenerador = usuarioGenerador;
	}

	public String getReparticion() {
		return reparticion;
	}

	public void setReparticion(String reparticion) {
		this.reparticion = reparticion;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getTipoDocAcr() {
		return tipoDocAcr;
	}

	public void setTipoDocAcr(String tipoDocAcr) {
		this.tipoDocAcr = tipoDocAcr;
	}

	public Integer getAnioDoc() {
		return anioDoc;
	}

	public void setAnioDoc(Integer anioDoc) {
		this.anioDoc = anioDoc;
	}

	public Long getNroDoc() {
		return nroDoc;
	}

	public void setNroDoc(Long nroDoc) {
		this.nroDoc = nroDoc;
	}

	public String getTipoDocNombre() {
		return tipoDocNombre;
	}

	public void setTipoDocNombre(String tipoDocNombre) {
		this.tipoDocNombre = tipoDocNombre;
	}
}
