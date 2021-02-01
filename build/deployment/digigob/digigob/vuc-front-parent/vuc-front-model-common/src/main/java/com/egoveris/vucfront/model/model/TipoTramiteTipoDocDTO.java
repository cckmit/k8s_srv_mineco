package com.egoveris.vucfront.model.model;

import java.io.Serializable;
import java.util.Date;

public class TipoTramiteTipoDocDTO implements Serializable {

  private static final long serialVersionUID = 6066183786548640550L;

  private Long id;
  private Long cantidad;
  private Date fechaCreacion;
  private Date fechaModificacion;
  private TipoDocumentoDTO tipoDoc;
  private Boolean obligatorio;
  private Long orden;
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

  public TipoDocumentoDTO getTipoDoc() {
    return tipoDoc;
  }

  public void setTipoDoc(TipoDocumentoDTO tipoDoc) {
    this.tipoDoc = tipoDoc;
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

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("TipoTramiteTipoDocDTO [id=").append(id).append(", cantidad=").append(cantidad)
        .append(", fechaCreacion=").append(fechaCreacion).append(", fechaModificacion=")
        .append(fechaModificacion).append(", tipoDoc=").append(tipoDoc).append(", obligatorio=")
        .append(obligatorio).append(", orden=").append(orden).append(", version=").append(version)
        .append("]");
    return builder.toString();
  }

}