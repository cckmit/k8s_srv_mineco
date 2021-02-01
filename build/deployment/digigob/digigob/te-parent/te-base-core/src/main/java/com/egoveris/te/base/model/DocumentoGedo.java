package com.egoveris.te.base.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "GEDO_DOCUMENTO")
public class DocumentoGedo implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -1137683831555425572L;

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "numero")
  private String numero;

  @Column(name = "numeroEspecial")
  private String numeroEspecial;

  @Column(name = "reparticion")
  private String reparticion;

  @Column(name = "anio")
  private String anio;

  @Column(name = "motivo")
  private String motivo;

  @Column(name = "usuarioGenerador")
  private String usuarioGenerador;

  @Column(name = "fechaCreacion")
  private Date fechaCreacion;

  @Column(name = "workflowOrigen")
  private String workflowOrigen;

  @Column(name = "sistemaOrigen")
  private String sistemaOrigen;

  @Column(name = "sistemaIniciador")
  private String sistemaIniciador;

  @Column(name = "usuarioIniciador")
  private String usuarioIniciador;

  @Column(name = "numero_sade_papel")
  private String numeroSadePapel;

  @Column(name = "tiporeserva")
  private Integer tipoReserva;

  @Column(name = "motivo_depuracion")
  private String motivoDepuracion;

  @ManyToOne
  @JoinColumn(name = "tipo")
  private TipoDocumentoGedo tipo;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNumero() {
    return numero;
  }

  public void setNumero(String numero) {
    this.numero = numero;
  }

  public String getNumeroEspecial() {
    return numeroEspecial;
  }

  public void setNumeroEspecial(String numeroEspecial) {
    this.numeroEspecial = numeroEspecial;
  }

  public String getReparticion() {
    return reparticion;
  }

  public void setReparticion(String reparticion) {
    this.reparticion = reparticion;
  }

  public String getAnio() {
    return anio;
  }

  public void setAnio(String anio) {
    this.anio = anio;
  }

  public String getMotivo() {
    return motivo;
  }

  public void setMotivo(String motivo) {
    this.motivo = motivo;
  }

  public String getUsuarioGenerador() {
    return usuarioGenerador;
  }

  public void setUsuarioGenerador(String usuarioGenerador) {
    this.usuarioGenerador = usuarioGenerador;
  }

  public Date getFechaCreacion() {
    return fechaCreacion;
  }

  public void setFechaCreacion(Date fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }

  public String getWorkflowOrigen() {
    return workflowOrigen;
  }

  public void setWorkflowOrigen(String workflowOrigen) {
    this.workflowOrigen = workflowOrigen;
  }

  public String getSistemaOrigen() {
    return sistemaOrigen;
  }

  public void setSistemaOrigen(String sistemaOrigen) {
    this.sistemaOrigen = sistemaOrigen;
  }

  public String getSistemaIniciador() {
    return sistemaIniciador;
  }

  public void setSistemaIniciador(String sistemaIniciador) {
    this.sistemaIniciador = sistemaIniciador;
  }

  public String getUsuarioIniciador() {
    return usuarioIniciador;
  }

  public void setUsuarioIniciador(String usuarioIniciador) {
    this.usuarioIniciador = usuarioIniciador;
  }

  public String getNumeroSadePapel() {
    return numeroSadePapel;
  }

  public void setNumeroSadePapel(String numeroSadePapel) {
    this.numeroSadePapel = numeroSadePapel;
  }

  public Integer getTipoReserva() {
    return tipoReserva;
  }

  public void setTipoReserva(Integer tipoReserva) {
    this.tipoReserva = tipoReserva;
  }

  public String getMotivoDepuracion() {
    return motivoDepuracion;
  }

  public void setMotivoDepuracion(String motivoDepuracion) {
    this.motivoDepuracion = motivoDepuracion;
  }

  public TipoDocumentoGedo getTipo() {
    return tipo;
  }

  public void setTipo(TipoDocumentoGedo tipo) {
    this.tipo = tipo;
  }

}