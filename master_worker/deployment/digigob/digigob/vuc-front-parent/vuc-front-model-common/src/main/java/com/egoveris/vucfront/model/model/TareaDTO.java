package com.egoveris.vucfront.model.model;

import com.egoveris.shared.date.DateUtil;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TareaDTO implements Serializable {

  private static final long serialVersionUID = -1231547883669890927L;

  private Long id;
  private Date fecha;
  private EstadoTareaDTO estado;
  private ExpedienteBaseDTO expedienteBase;
  private String motivo;
  private String enviadoPor;
  private boolean bajaLogica;
  private Long idInterviniente;
  private String tipo;
  private String cuitDestino;
  private List<TipoDocumentoDTO> tipoDocumentos;
  private boolean notificado;

  public String getFormattedDate() {
    return DateUtil.getFormattedDate(fecha);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getFecha() {
    return fecha;
  }

  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }

  public EstadoTareaDTO getEstado() {
    return estado;
  }

  public void setEstado(EstadoTareaDTO estado) {
    this.estado = estado;
  }

  public ExpedienteBaseDTO getExpedienteBase() {
    return expedienteBase;
  }

  public void setExpedienteBase(ExpedienteBaseDTO expedienteBase) {
    this.expedienteBase = expedienteBase;
  }

  public String getMotivo() {
    return motivo;
  }

  public void setMotivo(String motivo) {
    this.motivo = motivo;
  }

  public String getEnviadoPor() {
    return enviadoPor;
  }

  public void setEnviadoPor(String enviadoPor) {
    this.enviadoPor = enviadoPor;
  }

  public boolean isBajaLogica() {
    return bajaLogica;
  }

  public void setBajaLogica(boolean bajaLogica) {
    this.bajaLogica = bajaLogica;
  }

  public Long getIdInterviniente() {
    return idInterviniente;
  }

  public void setIdInterviniente(Long idInterviniente) {
    this.idInterviniente = idInterviniente;
  }

  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  public String getCuitDestino() {
    return cuitDestino;
  }

  public void setCuitDestino(String cuitDestino) {
    this.cuitDestino = cuitDestino;
  }

  public List<TipoDocumentoDTO> getTipoDocumentos() {
    return tipoDocumentos;
  }

  public void setTipoDocumentos(List<TipoDocumentoDTO> tipoDocumentos) {
    this.tipoDocumentos = tipoDocumentos;
  }

  public boolean getNotificado() {
    return notificado;
  }

  public void setNotificado(boolean notificado) {
    this.notificado = notificado;
  }

}