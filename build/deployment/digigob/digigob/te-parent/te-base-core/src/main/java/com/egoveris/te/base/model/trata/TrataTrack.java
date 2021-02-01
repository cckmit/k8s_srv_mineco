package com.egoveris.te.base.model.trata;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "EE_SADE_EXTRACTO")
public class TrataTrack {

  @Id
  @Column(name = "ID_EXTRACTO")
  private Long idExtracto;

  @Column(name = "CODIGO_EXTRACTO")
  private String codigoExtracto;

  @Column(name = "DESCRIPCION_EXTRAC")
  private String descripcionExtracto;

  @Column(name = "VIGENCIA_HASTA")
  private Date vigenciaHasta;

  @Column(name = "VIGENCIA_DESDE")
  private Date vigenciaDesde;

  @Column(name = "ESTADO_REGISTRO")
  private Boolean estadoRegistro;

  public Long getIdExtracto() {
    return idExtracto;
  }

  public void setIdExtracto(Long idExtracto) {
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

}
