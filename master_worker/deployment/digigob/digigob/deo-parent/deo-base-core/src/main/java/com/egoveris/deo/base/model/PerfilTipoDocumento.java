package com.egoveris.deo.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "GEDO_TIPODOC_PERFIL")
public class PerfilTipoDocumento {

  @Id
  @Column(name = "idTipoDocumento")
  private Integer idTipoDocumento;

  @Column(name = "idAdobePdfSetting")
  private Integer idAdobePdfSetting;

  @Column(name = "idFileTypeSetting")
  private Integer idFileTypeSetting;

  public Integer getIdTipoDocumento() {
    return idTipoDocumento;
  }

  public void setIdTipoDocumento(Integer idTipoDocumento) {
    this.idTipoDocumento = idTipoDocumento;
  }

  public Integer getIdAdobePdfSetting() {
    return idAdobePdfSetting;
  }

  public void setIdAdobePdfSetting(Integer idAdobePdfSetting) {
    this.idAdobePdfSetting = idAdobePdfSetting;
  }

  public Integer getIdFileTypeSetting() {
    return idFileTypeSetting;
  }

  public void setIdFileTypeSetting(Integer idFileTypeSetting) {
    this.idFileTypeSetting = idFileTypeSetting;
  }

}
