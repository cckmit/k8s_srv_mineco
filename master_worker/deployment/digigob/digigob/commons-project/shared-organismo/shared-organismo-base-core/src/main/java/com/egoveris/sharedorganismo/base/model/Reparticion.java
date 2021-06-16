package com.egoveris.sharedorganismo.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "edt_sade_reparticion")
public class Reparticion {

	@Id
	@Column(name = "ID_REPARTICION")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "CODIGO_REPARTICION")
	private String codigo;

	@Column(name = "NOMBRE_REPARTICION")
	private String nombre;

	@Column(name = "VIGENCIA_DESDE")
	private Date vigenciaDesde;

	@Column(name = "VIGENCIA_HASTA")
	private Date vigenciaHasta;

	@Column(name = "ID_ESTRUCTURA")
	private Integer idEstructura;

	@Column(name = "ESTADO_REGISTRO")
	private String estadoRegistro;

	@Column(name = "ES_DGTAL")
	private Integer esDigital;

	@Column(name = "REP_PADRE")
	private Integer idPadre;

	@Column(name = "COD_DGTAL")
	private Integer codDgtal;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getVigenciaDesde() {
		return vigenciaDesde;
	}

	public void setVigenciaDesde(Date vigenciaDesde) {
		this.vigenciaDesde = vigenciaDesde;
	}

	public Date getVigenciaHasta() {
		return vigenciaHasta;
	}

	public void setVigenciaHasta(Date vigenciaHasta) {
		this.vigenciaHasta = vigenciaHasta;
	}

	public Integer getIdEstructura() {
		return idEstructura;
	}

	public void setIdEstructura(Integer idEstructura) {
		this.idEstructura = idEstructura;
	}

	public String getEstadoRegistro() {
		return estadoRegistro;
	}

	public void setEstadoRegistro(String estadoRegistro) {
		this.estadoRegistro = estadoRegistro;
	}

	public Integer getEsDigital() {
		return esDigital;
	}

	public void setEsDigital(Integer esDigital) {
		this.esDigital = esDigital;
	}

	public Integer getIdPadre() {
		return idPadre;
	}

	public void setIdPadre(Integer idPadre) {
		this.idPadre = idPadre;
	}

	public Integer getCodDgtal() {
		return codDgtal;
	}

	public void setCodDgtal(Integer codDgtal) {
		this.codDgtal = codDgtal;
	}

}
