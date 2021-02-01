package com.egoveris.vucfront.base.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Clase de dominio que representa una tarea subido por la persona y asignada a
 * un expediente, junto con la lista de documentos a subsanar
 * 
 */
@Entity
@Table(name = "TAD_TAREA")
public class Tarea {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "FECHA")
  private Date fecha;

  @OneToOne
  @JoinColumn(name = "ID_ESTADO")
  private EstadoTarea estado;

  @OneToOne
  @JoinColumn(name = "ID_EXPEDIENTE")
  private ExpedienteBase expedienteBase;

  @Column(name = "MOTIVO")
  private String motivo;

  @Column(name = "ENVIADO_POR")
  private String enviadoPor;

  @Column(name = "BAJA_LOGICA")
  private boolean bajaLogica;

  @Column(name = "ID_INTERVINIENTE")
  private Long idInterviniente;

  @Column(name = "TIPO")
  private String tipo;

  @Column(name = "CUIT_DESTINO")
  private String cuitDestino;

  @ManyToMany(cascade = CascadeType.REFRESH)
  @JoinTable(name = "TAD_TAREA_TIPO_DOCUMENTO", joinColumns = {
      @JoinColumn(name = "id_tarea") }, inverseJoinColumns = {
          @JoinColumn(name = "id_tipo_documento") })
  private List<TipoDocumento> tipoDocumentos;

  @Column(name = "NOTIFICADO")
  private boolean notificado;

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

  public EstadoTarea getEstado() {
    return estado;
  }

  public void setEstado(EstadoTarea estado) {
    this.estado = estado;
  }

  public ExpedienteBase getExpedienteBase() {
    return expedienteBase;
  }

  public void setExpedienteBase(ExpedienteBase expedienteBase) {
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

  public List<TipoDocumento> getTipoDocumentos() {
    return tipoDocumentos;
  }

  public void setTipoDocumentos(List<TipoDocumento> tipoDocumentos) {
    this.tipoDocumentos = tipoDocumentos;
  }

  public boolean getNotificado() {
    return notificado;
  }

  public void setNotificado(boolean notificado) {
    this.notificado = notificado;
  }

}