package com.egoveris.vucfront.model.model;

import java.io.Serializable;
import java.util.Date;

public class TerminosCondicionesDTO implements Serializable {

  private static final long serialVersionUID = -1492207900832036978L;

  private Long id;
  private String codigoContenido;
  private Boolean estado;
  private Date fecha;
  private TipoDocumentoDTO tipoDoc;
  private Long version;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCodigoContenido() {
    return codigoContenido;
  }

  public void setCodigoContenido(String codigoContenido) {
    this.codigoContenido = codigoContenido;
  }

  public Boolean getEstado() {
    return estado;
  }

  public void setEstado(Boolean estado) {
    this.estado = estado;
  }

  public Date getFecha() {
    return fecha;
  }

  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }

  public TipoDocumentoDTO getTipoDoc() {
    return tipoDoc;
  }

  public void setTipoDoc(TipoDocumentoDTO tipoDoc) {
    this.tipoDoc = tipoDoc;
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
    builder.append("TerminosCondicionesDTO [id=").append(id).append(", codigoContenido=")
        .append(codigoContenido).append(", estado=").append(estado).append(", fecha=")
        .append(fecha).append(", tipoDoc=").append(tipoDoc).append(", version=").append(version)
        .append("]");
    return builder.toString();
  }

}