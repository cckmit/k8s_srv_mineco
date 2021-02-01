package com.egoveris.te.base.model;

import java.util.Date;
             
public class AuditoriaNotificacionDTO {

	private Long idAuditoria;
	private String usuario;
	private String actuacion;
	private Integer anio;
	private Integer numero;
	private String reparticion;
	private Date fechaOperacion;
	private Long idDocumento;
	private String tipoDocumento;
	
	
	public AuditoriaNotificacionDTO() {

	}

	public AuditoriaNotificacionDTO(String usuario, String actuacion,
			Integer anio, Integer numero,String reparticion,
			Date fechaOperacion, Long idDocumento,String tipoDocumento) {
		super();
		this.usuario = usuario;
		this.setActuacion(actuacion);
		this.setAnio(anio);
		this.setNumero(numero);
		this.setReparticion(reparticion);
		this.fechaOperacion = fechaOperacion;
		this.idDocumento = idDocumento;
		this.tipoDocumento = tipoDocumento;
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

	public Date getFechaOperacion() {
		return fechaOperacion;
	}

	public void setFechaOperacion(Date fechaOperacion) {
		this.fechaOperacion = fechaOperacion;
	}
	
	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Long getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(Long idDocumento) {
		this.idDocumento = idDocumento;
	}

	public void setActuacion(String actuacion) {
		this.actuacion = actuacion;
	}

	public String getActuacion() {
		return actuacion;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	public Integer getAnio() {
		return anio;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setReparticion(String reparticion) {
		this.reparticion = reparticion;
	}

	public String getReparticion() {
		return reparticion;
	}
}
