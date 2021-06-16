package com.egoveris.te.base.model;

import java.io.Serializable;
import java.util.Date;

/**
 * PermisoVisualizacion que se asocia a un archivo de trabajo.
 * 
 * 
 * @author esroveda
 * 
 */

public class ArchivoDeTrabajoVisualizacionDTO implements Serializable {

  /**
  * 
  */
  private static final long serialVersionUID = -3136132030411802469L;

  private Long id;
  private String codigoUsuario;
  private String codigoReparticion;
  private String codigoSector;
  private String codigoReparticionRectora;
  private Date fechaAlta;
  private ArchivoDeTrabajoDTO archivoDeTrabajo;
  private Long idArchivoDeTrabajo;

  public ArchivoDeTrabajoVisualizacionDTO(ArchivoDeTrabajoVisualizacionDTO at,
      ArchivoDeTrabajoDTO archivoDeTrabajo2) {
    this.archivoDeTrabajo = archivoDeTrabajo2;
    this.setCodigoReparticion(at.getCodigoReparticion());
    this.setCodigoReparticionRectora(at.getCodigoReparticionRectora());
    this.setCodigoSector(at.getCodigoSector());
    this.setCodigoUsuario(at.getCodigoUsuario());
    this.setFechaAlta(this.getFechaAlta());
  }

  public ArchivoDeTrabajoVisualizacionDTO() {
  }

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

  public ArchivoDeTrabajoDTO getArchivoDeTrabajo() {
    return archivoDeTrabajo;
  }

  public void setArchivoDeTrabajo(ArchivoDeTrabajoDTO archivoDeTrabajo) {
    this.archivoDeTrabajo = archivoDeTrabajo;
  }

  public Long getIdArchivoDeTrabajo() {
    return idArchivoDeTrabajo;
  }

  public void setIdArchivoDeTrabajo(Long idArchivoDeTrabajo) {
    this.idArchivoDeTrabajo = idArchivoDeTrabajo;
  }

}
