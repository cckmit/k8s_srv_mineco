package com.egoveris.te.base.model;

import java.util.Date;

public class AuditoriaDeConsultaDTO {

	private Long idAuditoria;
	private String usuario;
	private String tipoActuacion;
	private Integer anioActuacion;
	private Integer numeroActuacion;
	private String reparticionActuacion;
	private String reparticionUsuario;
	private Date fechaConsulta;
	private Date fechaDesde;
	private Date fechaHasta;
	private String trata;
	private String metadato1;
	private String metadato2;
	private String metadato3;
	private String valorMetadato1;
	private String valorMetadato2;
	private String valorMetadato3;
	private String tipoDocumento;
	private String numeroDocumento;
	private String cuitCuil;
	private String domicilio;
	private String piso;
	private String departamento;
	private String codigoPostal;
	
	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public String getPiso() {
		return piso;
	}

	public void setPiso(String piso) {
		this.piso = piso;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public AuditoriaDeConsultaDTO() {

	}

	public AuditoriaDeConsultaDTO(String usuario, String tipoActuacion,
			Integer anioActuacion, Integer numeroActuacion,
			String reparticionActuacion, String reparticionUsuario,
			Date fechaConsulta) {
		super();
		this.usuario = usuario;
		this.tipoActuacion = tipoActuacion;
		this.anioActuacion = anioActuacion;
		this.numeroActuacion = numeroActuacion;
		this.reparticionActuacion = reparticionActuacion;
		this.reparticionUsuario = reparticionUsuario;
		this.fechaConsulta = fechaConsulta;
	}

	public Long getIdAuditoria() {
		return idAuditoria;
	}

	public void setIdAuditoria(Long idAuditoria) {
		this.idAuditoria = idAuditoria;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getTipoActuacion() {
		return tipoActuacion;
	}

	public void setTipoActuacion(String tipoActuacion) {
		this.tipoActuacion = tipoActuacion;
	}


	public String getReparticionActuacion() {
		return reparticionActuacion;
	}

	public void setReparticionActuacion(String reparticionActuacion) {
		this.reparticionActuacion = reparticionActuacion;
	}

	public String getReparticionUsuario() {
		return reparticionUsuario;
	}

	
	public Integer getAnioActuacion() {
		return anioActuacion;
	}

	public void setAnioActuacion(Integer anioActuacion) {
		this.anioActuacion = anioActuacion;
	}

	public Integer getNumeroActuacion() {
		return numeroActuacion;
	}

	public void setNumeroActuacion(Integer numeroActuacion) {
		this.numeroActuacion = numeroActuacion;
	}

	public void setReparticionUsuario(String reparticionUsuario) {
		this.reparticionUsuario = reparticionUsuario;
	}

	public Date getFechaConsulta() {
		return fechaConsulta;
	}

	public void setFechaConsulta(Date fechaConsulta) {
		this.fechaConsulta = fechaConsulta;
	}

	public Date getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public Date getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public String getTrata() {
		return trata;
	}

	public void setTrata(String trata) {
		this.trata = trata;
	}

	public String getMetadato1() {
		return metadato1;
	}

	public void setMetadato1(String metadato1) {
		this.metadato1 = metadato1;
	}

	public String getMetadato2() {
		return metadato2;
	}

	public void setMetadato2(String metadato2) {
		this.metadato2 = metadato2;
	}

	public String getMetadato3() {
		return metadato3;
	}

	public void setMetadato3(String metadato3) {
		this.metadato3 = metadato3;
	}

	public String getValorMetadato1() {
		return valorMetadato1;
	}

	public void setValorMetadato1(String valorMetadato1) {
		this.valorMetadato1 = valorMetadato1;
	}

	public String getValorMetadato2() {
		return valorMetadato2;
	}

	public void setValorMetadato2(String valorMetadato2) {
		this.valorMetadato2 = valorMetadato2;
	}

	public String getValorMetadato3() {
		return valorMetadato3;
	}

	public void setValorMetadato3(String valorMetadato3) {
		this.valorMetadato3 = valorMetadato3;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public String getCuitCuil() {
		return cuitCuil;
	}

	public void setCuitCuil(String cuitCuil) {
		this.cuitCuil = cuitCuil;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
}
