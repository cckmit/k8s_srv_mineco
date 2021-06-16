package com.egoveris.sharedsecurity.base.model.cargo;

import java.io.Serializable;
import java.util.Date;

public class CargoDTO implements Serializable {

  private static final long serialVersionUID = -3303415253003264934L;

  private Integer id;
  private String cargoNombre;
  private boolean esTipoBaja;
  private String usuarioCreacion;
  private Date fechaModificacion;
  private String usuarioModificacion;
  private Date fechaCreacion;
  private Boolean vigente;
  private Integer idReparticion;
  // private List<RolDTO> listaRoles;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getCargoNombre() {
    return cargoNombre;
  }

  public void setCargoNombre(String cargoNombre) {
    this.cargoNombre = cargoNombre;
  }

  public boolean isEsTipoBaja() {
    return esTipoBaja;
  }

  public void setEsTipoBaja(boolean esTipoBaja) {
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

  public Integer getIdReparticion() {
    return idReparticion;
  }

  public void setIdReparticion(Integer idReparticion) {
    this.idReparticion = idReparticion;
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
    CargoDTO other = (CargoDTO) obj;
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
    builder.append("CargoDTO [id=").append(id).append(", cargoNombre=").append(cargoNombre)
        .append(", esTipoBaja=").append(esTipoBaja).append(", usuarioCreacion=")
        .append(usuarioCreacion).append(", fechaModificacion=").append(fechaModificacion)
        .append(", usuarioModificacion=").append(usuarioModificacion).append(", fechaCreacion=")
        .append(fechaCreacion).append(", vigente=").append(vigente).append(", idReparticion=")
        .append(idReparticion);
        //.append(", listaRoles=").append(listaRoles).append("]");
    return builder.toString();
  }

}