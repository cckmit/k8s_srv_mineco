package com.egoveris.te.base.model;

import java.io.Serializable;
import java.util.Date;

public class TrataAuditoriaDTO implements Serializable {
	private static final long serialVersionUID = -311502891248393567L;
	
	private String tipoOperacion;
	private Date fechaModificacion;
	private String userName;
	private Long id;
	private String codigoTrata;
	private String descripcionTrata;
	private String estado;
	private Integer tipoReserva;
	private Boolean esAutomatica;
	private Boolean esManual;
	private String tipoDocumento;
	private String workflow;
	private String tipoActuacion;
	private Integer tiempoEstimado;
	private Boolean esInterno;
	private Boolean esExterno;
	private Boolean notificableJMS;
	private Boolean esNotificableTad;
	private Boolean esEnvioAutomaticoGT;
	private String claveTad;
	private Boolean integracionSisExt;
	private Boolean integracionAFJG;
	
	public static final String MOD = "MODIFICACION";
	public static final String ALTA = "ALTA";
	public static final String BAJA = "BAJA";
	
	public TrataAuditoriaDTO() {
	}

	public TrataAuditoriaDTO(TrataDTO trata, String userName) {
		this.setFechaModificacion(new Date());
		this.setUserName(userName);
		this.setCodigoTrata(trata.getCodigoTrata());
		this.setEstado(trata.getEstado());
		this.setTipoReserva(trata.getTipoReserva().getId());
		this.setEsAutomatica(trata.getEsAutomatica());
		this.setEsManual(trata.getEsManual());
		this.setWorkflow(trata.getWorkflow());
		this.setTiempoEstimado(trata.getTiempoResolucion());
		this.setTipoActuacion(trata.getTipoActuacion());
		this.setTipoDocumento(trata.getAcronimoDocumento());
		this.setDescripcionTrata(trata.getDescripcion());
		this.setEsInterno(trata.getEsInterno());
		this.setEsExterno(trata.getEsExterno());
		this.setEsNotificableTad(trata.getEsNotificableTad());
		this.setEsEnvioAutomaticoGT(trata.getEsEnvioAutomaticoGT());
		this.setClaveTad(trata.getClaveTad());
		this.setIntegracionSisExt(trata.getIntegracionSisExt());
		this.setIntegracionAFJG(trata.getIntegracionAFJG());
		this.setNotificableJMS(trata.isNotificableJMS());
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigoTrata() {
		return codigoTrata;
	}

	public void setCodigoTrata(String codigoTrata) {
		this.codigoTrata = codigoTrata;
	}

	public String getDescripcionTrata() {
		return descripcionTrata;
	}

	public void setDescripcionTrata(String descripcionTrata) {
		this.descripcionTrata = descripcionTrata;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Integer getTipoReserva() {
		return tipoReserva;
	}

	public void setTipoReserva(Integer tipoReserva) {
		this.tipoReserva = tipoReserva;
	}

	public Boolean getEsAutomatica() {
		return esAutomatica;
	}

	public void setEsAutomatica(Boolean esAutomatica) {
		this.esAutomatica = esAutomatica;
	}

	public Boolean getEsManual() {
		return esManual;
	}

	public void setEsManual(Boolean esManual) {
		this.esManual = esManual;
	}

	public String getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setWorkflow(String workflow) {
		this.workflow = workflow;
	}

	public String getWorkflow() {
		return workflow;
	}

	public void setTipoActuacion(String tipoActuacion) {
		this.tipoActuacion = tipoActuacion;
	}

	public String getTipoActuacion() {
		return tipoActuacion;
	}

	public void setTiempoEstimado(Integer tiempoEstimado) {
		this.tiempoEstimado = tiempoEstimado;
	}

	public Integer getTiempoEstimado() {
		return tiempoEstimado;
	}

	public Boolean getEsInterno() {
		return esInterno;
	}

	public void setEsInterno(Boolean esInterno) {
		this.esInterno = esInterno;
	}

	public Boolean getEsExterno() {
		return esExterno;
	}

	public void setEsExterno(Boolean esExterno) {
		this.esExterno = esExterno;
	}

	public Boolean getEsNotificableTad() {
		return esNotificableTad;
	}

	public void setEsNotificableTad(Boolean esNotificableTad) {
		this.esNotificableTad = esNotificableTad;
	}

	public Boolean getEsEnvioAutomaticoGT() {
		return esEnvioAutomaticoGT;
	}

	public void setEsEnvioAutomaticoGT(Boolean esEnvioAutomaticoGT) {
		this.esEnvioAutomaticoGT = esEnvioAutomaticoGT;
	}

	public String getClaveTad() {
		return claveTad;
	}

	public void setClaveTad(String claveTad) {
		this.claveTad = claveTad;
	}

	public Boolean getIntegracionSisExt() {
		return integracionSisExt;
	}

	public void setIntegracionSisExt(Boolean integracionSisExt) {
		this.integracionSisExt = integracionSisExt;
	}

	public Boolean getIntegracionAFJG() {
		return integracionAFJG;
	}

	public void setIntegracionAFJG(Boolean integracionAFJG) {
		this.integracionAFJG = integracionAFJG;
	}

	public Boolean getNotificableJMS() {
		return notificableJMS;
	}

	public void setNotificableJMS(Boolean notificableJMS) {
		this.notificableJMS = notificableJMS;
	}
	
}
