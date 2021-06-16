package com.egoveris.deo.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "GEDO_TIPODOCUMENTO_EMBEBIDO")
public class TipoDocumentoEmbebidos {

  @EmbeddedId
  private TipoDocumentoEmbebidosPK tipoDocumentoEmbebidosPK;

  @Column(name = "DESCRIPCION")
  private String descripcion;

  @Column(name = "OBLIGATORIO")
  private boolean obligatorio;

  @Column(name = "FECHACREACION")
  private Date fechaCreacion;

  @Column(name = "USERNAME")
  private String userName;

  @Column(name = "TAMANO")
  private Integer sizeArchivoEmb;

  public TipoDocumentoEmbebidosPK getTipoDocumentoEmbebidosPK() {
    return tipoDocumentoEmbebidosPK;
  }

  public void setTipoDocumentoEmbebidosPK(TipoDocumentoEmbebidosPK tipoDocumentoEmbebidosPK) {
    this.tipoDocumentoEmbebidosPK = tipoDocumentoEmbebidosPK;
  }

  public Integer getSizeArchivoEmb() {
    return sizeArchivoEmb;
  }

  public void setSizeArchivoEmb(Integer sizeArchivoEmb) {
    this.sizeArchivoEmb = sizeArchivoEmb;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public boolean getObligatorio() {
    return obligatorio;
  }

  public void setObligatorio(boolean obligatorio) {
    this.obligatorio = obligatorio;
  }

  public Date getFechaCreacion() {
    return fechaCreacion;
  }

  public void setFechaCreacion(Date fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }
}
