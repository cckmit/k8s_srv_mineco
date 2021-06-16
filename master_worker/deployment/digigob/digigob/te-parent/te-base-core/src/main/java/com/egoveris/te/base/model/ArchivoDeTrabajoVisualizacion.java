package com.egoveris.te.base.model;

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
@Table(name = "EE_ARCH_TRABAJO_VISUALIZACION")
public class ArchivoDeTrabajoVisualizacion {

  public ArchivoDeTrabajoVisualizacion(ArchivoDeTrabajoVisualizacionDTO at, ArchivoDeTrabajo ar) {
    this.archivoDeTrabajo = ar;
    this.setCodigoReparticion(at.getCodigoReparticion());
    this.setCodigoReparticionRectora(at.getCodigoReparticionRectora());
    this.setCodigoSector(at.getCodigoSector());
    this.setCodigoUsuario(at.getCodigoUsuario());
    this.setFechaAlta(this.getFechaAlta());
  }

  public ArchivoDeTrabajoVisualizacion() {
  }

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "usuario")
  private String codigoUsuario;

  @Column(name = "reparticion")
  private String codigoReparticion;

  @Column(name = "sector")
  private String codigoSector;

  @Column(name = "rectora")
  private String codigoReparticionRectora;

  @Column(name = "fecha_alta")
  private Date fechaAlta;

  @ManyToOne
  @JoinColumn(name = "ID_ARCHIVO_TRABAJO")
  private ArchivoDeTrabajo archivoDeTrabajo;

  @Column(name = "id_archivo_trabajo")
  private Long idArchivoDeTrabajo;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCodigoUsuario() {
    return codigoUsuario;
  }

  public void setCodigoUsuario(String codigoUsuario) {
    this.codigoUsuario = codigoUsuario;
  }

  public String getCodigoReparticion() {
    return codigoReparticion;
  }

  public void setCodigoReparticion(String codigoReparticion) {
    this.codigoReparticion = codigoReparticion;
  }

  public String getCodigoSector() {
    return codigoSector;
  }

  public void setCodigoSector(String codigoSector) {
    this.codigoSector = codigoSector;
  }

  public String getCodigoReparticionRectora() {
    return codigoReparticionRectora;
  }

  public void setCodigoReparticionRectora(String codigoReparticionRectora) {
    this.codigoReparticionRectora = codigoReparticionRectora;
  }

  public Date getFechaAlta() {
    return fechaAlta;
  }

  public void setFechaAlta(Date fechaAlta) {
    this.fechaAlta = fechaAlta;
  }

  public ArchivoDeTrabajo getArchivoDeTrabajo() {
    return archivoDeTrabajo;
  }

  public void setArchivoDeTrabajo(ArchivoDeTrabajo archivoDeTrabajo) {
    this.archivoDeTrabajo = archivoDeTrabajo;
  }

  public Long getIdArchivoDeTrabajo() {
    return idArchivoDeTrabajo;
  }

  public void setIdArchivoDeTrabajo(Long idArchivoDeTrabajo) {
    this.idArchivoDeTrabajo = idArchivoDeTrabajo;
  }

}
