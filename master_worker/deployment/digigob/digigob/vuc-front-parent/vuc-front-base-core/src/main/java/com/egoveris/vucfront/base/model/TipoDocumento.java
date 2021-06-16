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
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TAD_TIPO_DOCUMENTO")
public class TipoDocumento {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "ACRONIMO_GEDO")
  private String acronimoGedo;

  @Column(name = "ACRONIMO_TAD")
  private String acronimoTad;

  @Column(name = "DESCRIPCION")
  private String descripcion;

  @Column(name = "DETALLE_FC")
  private String detalleFc;

  @Column(name = "ESTADO")
  private Boolean estado;

  @Column(name = "FECHA_CREACION")
  private Date fechaCreacion;

  @Column(name = "FECHA_MODIFICACION")
  private Date fechaModificacion;

  @Column(name = "FIRMA_CON_TOKEN")
  private Boolean firmaConToken;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "ID_TIPADO_DOCUMENTO")
  private TipadoDocumento tipadoDcto;

  @Column(name = "INCLUIDO_EN_SUPERTRATA")
  private Boolean incluidoEnSupertrata;

  @Column(name = "NOMBRE")
  private String nombre;

  @Column(name = "NOMBRE_FORMULARIO_CONTROLADO")
  private String nombreFormularioControlado;

  @Column(name = "USUARIO_INICIADOR")
  private String usuarioIniciador;

  @Column(name = "VERSION")
  private Long version;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getAcronimoGedo() {
    return acronimoGedo;
  }

  public void setAcronimoGedo(String acronimoGedo) {
    this.acronimoGedo = acronimoGedo;
  }

  public String getAcronimoTad() {
    return acronimoTad;
  }

  public void setAcronimoTad(String acronimoTad) {
    this.acronimoTad = acronimoTad;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getDetalleFc() {
    return detalleFc;
  }

  public void setDetalleFc(String detalleFc) {
    this.detalleFc = detalleFc;
  }

  public Boolean getEstado() {
    return estado;
  }

  public void setEstado(Boolean estado) {
    this.estado = estado;
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

  public Boolean getFirmaConToken() {
    return firmaConToken;
  }

  public void setFirmaConToken(Boolean firmaConToken) {
    this.firmaConToken = firmaConToken;
  }

  public TipadoDocumento getTipadoDcto() {
    return tipadoDcto;
  }

  public void setTipadoDcto(TipadoDocumento tipadoDcto) {
    this.tipadoDcto = tipadoDcto;
  }

  public Boolean getIncluidoEnSupertrata() {
    return incluidoEnSupertrata;
  }

  public void setIncluidoEnSupertrata(Boolean incluidoEnSupertrata) {
    this.incluidoEnSupertrata = incluidoEnSupertrata;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getNombreFormularioControlado() {
    return nombreFormularioControlado;
  }

  public void setNombreFormularioControlado(String nombreFormularioControlado) {
    this.nombreFormularioControlado = nombreFormularioControlado;
  }

  public String getUsuarioIniciador() {
    return usuarioIniciador;
  }

  public void setUsuarioIniciador(String usuarioIniciador) {
    this.usuarioIniciador = usuarioIniciador;
  }

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

}