package com.egoveris.te.base.model;

import java.io.Serializable;
import java.util.Date;

public class TrataTrackDTO implements Serializable {

  private static final long serialVersionUID = 1L;
  
  private Integer idExtracto;

  private String codigoExtracto;
 
  private String descripcionExtracto;

  private Date vigenciaHasta;

  private Date vigenciaDesde;

  private Boolean estadoRegistro;

  public Integer getIdExtracto() {
    return idExtracto;
  }

  public void setIdExtracto(Integer idExtracto) {
    this.idExtracto = idExtracto;
  }

  public String getCodigoExtracto() {
    return codigoExtracto;
  }

  public void setCodigoExtracto(String codigoExtracto) {
    this.codigoExtracto = codigoExtracto;
  }

  public String getDescripcionExtracto() {
    return descripcionExtracto;
  }

  public void setDescripcionExtracto(String descripcionExtracto) {
    this.descripcionExtracto = descripcionExtracto;
  }

  public Date getVigenciaHasta() {
    return vigenciaHasta;
  }

  public void setVigenciaHasta(Date vigenciaHasta) {
    this.vigenciaHasta = vigenciaHasta;
  }

  public Date getVigenciaDesde() {
    return vigenciaDesde;
  }

  public void setVigenciaDesde(Date vigenciaDesde) {
    this.vigenciaDesde = vigenciaDesde;
  }

  public Boolean getEstadoRegistro() {
    return estadoRegistro;
  }

  public void setEstadoRegistro(Boolean estadoRegistro) {
    this.estadoRegistro = estadoRegistro;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

}
