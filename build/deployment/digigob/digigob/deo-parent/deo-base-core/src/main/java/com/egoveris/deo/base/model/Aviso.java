package com.egoveris.deo.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "GEDO_AVISO")
public class Aviso {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  private Integer id;

  @Column(name = "USUARIORECEPTOR")
  private String usuarioReceptor;

  @Column(name = "USUARIOACCION")
  private String usuarioAccion;

  @Column(name = "REDIRIGIDOPOR")
  private String redirigidoPor;

  @Column(name = "MOTIVO")
  private String motivo;

  @Column(name = "MOTIVORECHAZO")
  private String motivoRechazo;

  @Column(name = "FECHAACCION")
  private Date fechaAccion;

  @Column(name = "FECHAENVIO")
  private Date fechaEnvio;

  @Column(name = "REFERENCIADOCUMENTO")
  private String referenciaDocumento;

  @Column(name = "NUMEROSADEPAPEL")
  private String numeroSadePapel;

  @Column(name = "NUMEROESPECIAL")
  private String numeroEspecial;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "DOCUMENTO")
  private Documento documento;

  public String getUsuarioReceptor() {
    return usuarioReceptor;
  }

  public void setUsuarioReceptor(String usuarioReceptor) {
    this.usuarioReceptor = usuarioReceptor;
  }

  public String getRedirigidoPor() {
    return redirigidoPor;
  }

  public void setRedirigidoPor(String redirigidoPor) {
    this.redirigidoPor = redirigidoPor;
  }

  public Date getFechaEnvio() {
    return fechaEnvio;
  }

  public void setFechaEnvio(Date fechaEnvio) {
    this.fechaEnvio = fechaEnvio;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUsuarioAccion() {
    return usuarioAccion;
  }

  public void setUsuarioAccion(String usuarioAccion) {
    this.usuarioAccion = usuarioAccion;
  }

  public String getMotivo() {
    return motivo;
  }

  public void setMotivo(String motivo) {
    this.motivo = motivo;
  }

  public String getMotivoRechazo() {
    return motivoRechazo;
  }

  public void setMotivoRechazo(String motivoRechazo) {
    this.motivoRechazo = motivoRechazo;
  }

  public Date getFechaAccion() {
    return fechaAccion;
  }

  public void setFechaAccion(Date fechaAccion) {
    this.fechaAccion = fechaAccion;
  }

  public String getReferenciaDocumento() {
    return referenciaDocumento;
  }

  public void setReferenciaDocumento(String referenciaDocumento) {
    this.referenciaDocumento = referenciaDocumento;
  }

  public String getNumeroSadePapel() {
    return numeroSadePapel;
  }

  public void setNumeroSadePapel(String numeroSadePapel) {
    this.numeroSadePapel = numeroSadePapel;
  }

  public String getNumeroEspecial() {
    return numeroEspecial;
  }

  public void setNumeroEspecial(String numeroEspecial) {
    this.numeroEspecial = numeroEspecial;
  }

  public void setDocumento(Documento documento) {
    this.documento = documento;
  }

  public Documento getDocumento() {
    return documento;
  }

}
