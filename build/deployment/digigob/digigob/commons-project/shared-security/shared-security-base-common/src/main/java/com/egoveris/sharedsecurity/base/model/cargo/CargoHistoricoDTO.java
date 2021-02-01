package com.egoveris.sharedsecurity.base.model.cargo;

import java.io.Serializable;
import java.util.Date;

public class CargoHistoricoDTO implements Serializable {

  private static final long serialVersionUID = -2055708303940365946L;

  private Integer id;
  private String cargo;
  private Boolean esTipoBaja;
  private String usuarioCreacion;
  private Date fechaModificacion;
  private String usuarioModificacion;
  private Date fechaCreacion;
  private Boolean vigente;
  private Integer revision;
  private Integer tipoRevision;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getCargo() {
    return cargo;
  }

  public void setCargo(String cargo) {
    this.cargo = cargo;
  }

  public Boolean getEsTipoBaja() {
    return esTipoBaja;
  }

  public void setEsTipoBaja(Boolean esTipoBaja) {
    this.esTipoBaja = esTipoBaja;
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

  public Date getFechaCreacion() {
    return fechaCreacion;
  }

  public void setFechaCreacion(Date fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }

  public Boolean getVigente() {
    return vigente;
  }

  public void setVigente(Boolean vigente) {
    this.vigente = vigente;
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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    CargoHistoricoDTO other = (CargoHistoricoDTO) obj;
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("CargoHistoricoDTO [id=").append(id).append(", cargo=").append(cargo)
        .append(", esTipoBaja=").append(esTipoBaja).append(", usuarioCreacion=")
        .append(usuarioCreacion).append(", fechaModificacion=").append(fechaModificacion)
        .append(", usuarioModificacion=").append(usuarioModificacion).append(", fechaCreacion=")
        .append(fechaCreacion).append(", vigente=").append(vigente).append(", revision=")
        .append(revision).append(", tipoRevision=").append(tipoRevision).append("]");
    return builder.toString();
  }

}