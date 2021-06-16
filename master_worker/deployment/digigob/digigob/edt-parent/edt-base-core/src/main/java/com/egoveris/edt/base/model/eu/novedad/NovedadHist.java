package com.egoveris.edt.base.model.eu.novedad;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.egoveris.edt.base.model.eu.Categoria;

@Entity
@Table(name = "EU_NOVEDAD_HIST")
public class NovedadHist {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "novedad", nullable = false, length = 550)
  private String novedad;

  @JoinColumn(name = "APLICACIONES")
  private String aplicaciones;

  @ManyToOne(cascade = CascadeType.REFRESH)
  @JoinColumn(name = "CATEGORIA_ID")
  private Categoria categoria;

  @Column(name = "FECHA_INICIO")
  private Date fechaInicio;

  @Column(name = "FECHA_FIN")
  private Date fechaFin;

  @Column(name = "FECHA_MODIFICACION")
  private Date fechaModificacion;

  @Column(name = "USUARIO", nullable = false)
  private String usuario;

  @Column(name = "ESTADO")
  private String estado;

  @Column(name = "REVISION")
  private Integer revision;

  @Column(name = "TIPO_REVISION")
  private Integer tipoRevision;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getNovedad() {
    return novedad;
  }

  public void setNovedad(String novedad) {
    this.novedad = novedad;
  }

  public String getAplicaciones() {
    return aplicaciones;
  }

  public void setAplicaciones(String aplicaciones) {
    this.aplicaciones = aplicaciones;
  }

  public Categoria getCategoria() {
    return categoria;
  }

  public void setCategoria(Categoria categoria) {
    this.categoria = categoria;
  }

  public Date getFechaInicio() {
    return fechaInicio;
  }

  public void setFechaInicio(Date fechaInicio) {
    this.fechaInicio = fechaInicio;
  }

  public Date getFechaFin() {
    return fechaFin;
  }

  public void setFechaFin(Date fechaFin) {
    this.fechaFin = fechaFin;
  }

  public Date getFechaModificacion() {
    return fechaModificacion;
  }

  public void setFechaModificacion(Date fechaModificacion) {
    this.fechaModificacion = fechaModificacion;
  }

  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public Integer getRevision() {
    return revision;
  }

  public void setRevision(Integer revision) {
    this.revision = revision;
  }

  public Integer getTipoRevision() {
    return tipoRevision;
  }

  public void setTipoRevision(Integer tipoRevision) {
    this.tipoRevision = tipoRevision;
  }

}