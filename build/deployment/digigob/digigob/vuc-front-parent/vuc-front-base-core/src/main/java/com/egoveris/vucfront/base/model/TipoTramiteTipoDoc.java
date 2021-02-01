package com.egoveris.vucfront.base.model;

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
@Table(name = "TAD_T_TRAMITE_T_DOCUMENTO")
public class TipoTramiteTipoDoc {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "CANTIDAD")
  private Long cantidad;

  @Column(name = "FECHA_CREACION")
  private Date fechaCreacion;

  @Column(name = "FECHA_MODIFICACION")
  private Date fechaModificacion;

  @ManyToOne
  @JoinColumn(name = "ID_TIPO_DOCUMENTO")
  private TipoDocumento tipoDoc;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ID_TIPO_TRAMITE")
  private TipoTramite tipoTramite;

  @Column(name = "OBLIGATORIO")
  private Boolean obligatorio;

  @Column(name = "ORDEN")
  private Long orden;

  @Column(name = "VERSION")
  private Long version;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getCantidad() {
    return cantidad;
  }

  public void setCantidad(Long cantidad) {
    this.cantidad = cantidad;
  }

  public Date getFechaCreacion() {
    return fechaCreacion;
  }

  public void setFechaCreacion(Date fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }

  public Date getFechaModificacion() {
    return fechaModificacion;
  }

  public void setFechaModificacion(Date fechaModificacion) {
    this.fechaModificacion = fechaModificacion;
  }

  public TipoDocumento getTipoDoc() {
    return tipoDoc;
  }

  public void setTipoDoc(TipoDocumento tipoDoc) {
    this.tipoDoc = tipoDoc;
  }

  public TipoTramite getTipoTramite() {
    return tipoTramite;
  }

  public void setTipoTramite(TipoTramite tipoTramite) {
    this.tipoTramite = tipoTramite;
  }

  public Boolean getObligatorio() {
    return obligatorio;
  }

  public void setObligatorio(Boolean obligatorio) {
    this.obligatorio = obligatorio;
  }

  public Long getOrden() {
    return orden;
  }

  public void setOrden(Long orden) {
    this.orden = orden;
  }

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

}