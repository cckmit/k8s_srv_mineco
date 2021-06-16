package com.egoveris.edt.base.model.eu;

import java.io.Serializable;
import java.util.Date;

public class CalleDTO implements Serializable {

  private static final long serialVersionUID = -5127350058795947559L;

  private Integer idCalle;
  private String codigoCalle;
  private String codigoPostal;
  private String nombreCalle;
  private Float alturaDesde;
  private Float alturaHasta;
  private CalleDTO idCalleAnterior;
  private Date vigenciaDesde;
  private Date vigenciaHasta;
  private Integer version;
  private Date fechaCreacion;
  private String usuarioCreacion;
  private Date fechaModificacion;
  private String usuarioModificacion;
  private Boolean estadoRegistro;

  public Integer getIdCalle() {
    return idCalle;
  }

  public void setIdCalle(Integer idCalle) {
    this.idCalle = idCalle;
  }

  public String getCodigoCalle() {
    return codigoCalle;
  }

  public void setCodigoCalle(String codigoCalle) {
    this.codigoCalle = codigoCalle;
  }

  public String getCodigoPostal() {
    return codigoPostal;
  }

  public void setCodigoPostal(String codigoPostal) {
    this.codigoPostal = codigoPostal;
  }

  public String getNombreCalle() {
    return nombreCalle;
  }

  public void setNombreCalle(String nombreCalle) {
    this.nombreCalle = nombreCalle;
  }

  public Float getAlturaDesde() {
    return alturaDesde;
  }

  public void setAlturaDesde(Float alturaDesde) {
    this.alturaDesde = alturaDesde;
  }

  public Float getAlturaHasta() {
    return alturaHasta;
  }

  public void setAlturaHasta(Float alturaHasta) {
    this.alturaHasta = alturaHasta;
  }

  public CalleDTO getIdCalleAnterior() {
    return idCalleAnterior;
  }

  public void setIdCalleAnterior(CalleDTO idCalleAnterior) {
    this.idCalleAnterior = idCalleAnterior;
  }

  public Date getVigenciaDesde() {
    return vigenciaDesde;
  }

  public void setVigenciaDesde(Date vigenciaDesde) {
    this.vigenciaDesde = vigenciaDesde;
  }

  public Date getVigenciaHasta() {
    return vigenciaHasta;
  }

  public void setVigenciaHasta(Date vigenciaHasta) {
    this.vigenciaHasta = vigenciaHasta;
  }

  public Integer getVersion() {
    return version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  public Date getFechaCreacion() {
    return fechaCreacion;
  }

  public void setFechaCreacion(Date fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }

  public String getUsuarioCreacion() {
    return usuarioCreacion;
  }

  public void setUsuarioCreacion(String usuarioCreacion) {
    this.usuarioCreacion = usuarioCreacion;
  }

  public Date getFechaModificacion() {
    return fechaModificacion;
  }

  public void setFechaModificacion(Date fechaModificacion) {
    this.fechaModificacion = fechaModificacion;
  }

  public String getUsuarioModificacion() {
    return usuarioModificacion;
  }

  public void setUsuarioModificacion(String usuarioModificacion) {
    this.usuarioModificacion = usuarioModificacion;
  }

  public Boolean getEstadoRegistro() {
    return estadoRegistro;
  }

  public void setEstadoRegistro(Boolean estadoRegistro) {
    this.estadoRegistro = estadoRegistro;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("CalleDTO [idCalle=").append(idCalle).append(", codigoCalle=")
        .append(codigoCalle).append(", codigoPostal=").append(codigoPostal)
        .append(", nombreCalle=").append(nombreCalle).append(", alturaDesde=").append(alturaDesde)
        .append(", alturaHasta=").append(alturaHasta).append(", idCalleAnterior=")
        .append(idCalleAnterior).append(", vigenciaDesde=").append(vigenciaDesde)
        .append(", vigenciaHasta=").append(vigenciaHasta).append(", version=").append(version)
        .append(", fechaCreacion=").append(fechaCreacion).append(", usuarioCreacion=")
        .append(usuarioCreacion).append(", fechaModificacion=").append(fechaModificacion)
        .append(", usuarioModificacion=").append(usuarioModificacion).append(", estadoRegistro=")
        .append(estadoRegistro).append("]");
    return builder.toString();
  }

}