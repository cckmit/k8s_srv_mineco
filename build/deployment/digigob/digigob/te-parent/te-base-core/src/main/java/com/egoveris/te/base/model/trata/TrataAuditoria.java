package com.egoveris.te.base.model.trata;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TRATA_AUDITORIA")
public class TrataAuditoria {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  private Long id;

  @Column(name = "TIPOOPERACION")
  private String tipoOperacion;

  @Column(name = "FECHAOPERACION")
  private Date fechaModificacion;

  @Column(name = "USERNAME")
  private String userName;

  @Column(name = "CODIGO_TRATA")
  private String codigoTrata;

  @Column(name = "DESCRIPCION")
  private String descripcionTrata;

  @Column(name = "ESTADO")
  private String estado;

  @Column(name = "ID_RESERVA")
  private Integer tipoReserva;

  @Column(name = "ES_AUTOMATICA")
  private Boolean esAutomatica;

  @Column(name = "ES_MANUAL")
  private Boolean esManual;

  @Column(name = "TIPO_DOCUMENTO")
  private String tipoDocumento;

  @Column(name = "WORKFLOW")
  private String workflow;

  @Column(name = "TIPO_ACTUACION")
  private String tipoActuacion;

  @Column(name = "TIEMPO_ESTIMADO")
  private Integer tiempoEstimado;

  @Column(name = "ES_INTERNO")
  private Boolean esInterno;

  @Column(name = "ES_EXTERNO")
  private Boolean esExterno;

  @Column(name = "NOTIFICABLE_JMS")
  private boolean notificableJMS;

  @Column(name = "ES_NOTIFICABLE_TAD")
  private Boolean esNotificableTad;

  @Column(name = "ENVIO_AUTOMATICO_GT")
  private Boolean esEnvioAutomaticoGT;

  @Column(name = "CLAVE_TAD")
  private String claveTad;

  @Column(name = "INTEGRA_SIS_EXT")
  private Boolean integracionSisExt;

  @Column(name = "INTEGRACION_AFJG")
  private Boolean integracionAFJG;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public String getTipoDocumento() {
    return tipoDocumento;
  }

  public void setTipoDocumento(String tipoDocumento) {
    this.tipoDocumento = tipoDocumento;
  }

  public String getWorkflow() {
    return workflow;
  }

  public void setWorkflow(String workflow) {
    this.workflow = workflow;
  }

  public String getTipoActuacion() {
    return tipoActuacion;
  }

  public void setTipoActuacion(String tipoActuacion) {
    this.tipoActuacion = tipoActuacion;
  }

  public Integer getTiempoEstimado() {
    return tiempoEstimado;
  }

  public void setTiempoEstimado(Integer tiempoEstimado) {
    this.tiempoEstimado = tiempoEstimado;
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

  public boolean isNotificableJMS() {
    return notificableJMS;
  }

  public void setNotificableJMS(boolean notificableJMS) {
    this.notificableJMS = notificableJMS;
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

}
