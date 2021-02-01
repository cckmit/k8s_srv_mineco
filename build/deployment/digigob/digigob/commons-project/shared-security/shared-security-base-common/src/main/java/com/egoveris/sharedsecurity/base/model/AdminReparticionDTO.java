package com.egoveris.sharedsecurity.base.model;

import java.io.Serializable;
import java.util.Date;

import com.egoveris.sharedsecurity.base.model.reparticion.ReparticionDTO;

public class AdminReparticionDTO implements Serializable {

  private static final long serialVersionUID = 8257841767854063247L;

  private Integer id;
  private ReparticionDTO reparticion;
  private String nombreUsuario;
  private Date fechaRevision;
  private String tipoRevision;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public ReparticionDTO getReparticion() {
    return reparticion;
  }

  public void setReparticion(ReparticionDTO reparticion) {
    this.reparticion = reparticion;
  }

  public String getNombreUsuario() {
    return nombreUsuario;
  }

  public void setNombreUsuario(String nombreUsuario) {
    this.nombreUsuario = nombreUsuario;
  }

  public Date getFechaRevision() {
    return fechaRevision;
  }

  public void setFechaRevision(Date fechaRevision) {
    this.fechaRevision = fechaRevision;
  }

  /**
   * Gets the tipo revision.
   *
   * @return the tipo revision
   */
  public String getTipoRevision() {
    if ("ADD".equals(tipoRevision)) {
      this.tipoRevision = TipoRevisionEnum.AGREGADO.name();
    }
    if ("MOD".equals(tipoRevision)) {
      this.tipoRevision = TipoRevisionEnum.ELIMINADO.name();
    }
    return tipoRevision;
  }

  public void setTipoRevision(String tipoRevision) {
    this.tipoRevision = tipoRevision;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof AdminReparticionDTO)) {
      return false;
    }
    if (getId() != null && ((AdminReparticionDTO) obj).getId() != null
        && getId().equals(((AdminReparticionDTO) obj).getId())) {
      return true;
    } else if ((getNombreUsuario().equals(((AdminReparticionDTO) obj).getNombreUsuario()))
        && (getReparticion().equals(((AdminReparticionDTO) obj).getReparticion()))) {
      return true;
    } else {
      return super.equals(obj);
    }
  }

  @Override
  public int hashCode() {
    if ((getReparticion() != null) && (getNombreUsuario() != null)) {
      return getReparticion().hashCode() + getNombreUsuario().hashCode();
    }
    return super.hashCode();
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("AdminReparticionDTO [id=").append(id).append(", reparticion=")
        .append(reparticion).append(", nombreUsuario=").append(nombreUsuario)
        .append(", fechaRevision=").append(fechaRevision).append(", tipoRevision=")
        .append(tipoRevision).append("]");
    return builder.toString();
  }

}