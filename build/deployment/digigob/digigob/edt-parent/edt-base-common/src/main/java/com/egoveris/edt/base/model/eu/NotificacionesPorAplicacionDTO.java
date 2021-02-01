package com.egoveris.edt.base.model.eu;

import java.io.Serializable;

public class NotificacionesPorAplicacionDTO
    implements Comparable<NotificacionesPorAplicacionDTO>, Serializable {

	
  private static final long serialVersionUID = 2653958378971466336L;
  private AplicacionDTO aplicacion;
  private Long cantidadDeNotificaciones;

  public AplicacionDTO getAplicacion() {
    return aplicacion;
  }

  public void setAplicacion(AplicacionDTO aplicacion) {
    this.aplicacion = aplicacion;
  }

  public Long getCantidadDeNotificaciones() {
    return cantidadDeNotificaciones;
  }

  public void setCantidadDeNotificaciones(Long cantidadDeNotificaciones) {
    this.cantidadDeNotificaciones = cantidadDeNotificaciones;
  }

  @Override
  public int compareTo(NotificacionesPorAplicacionDTO notPorAp) {
    return this.getAplicacion().getNombreAplicacion()
        .compareTo(notPorAp.getAplicacion().getNombreAplicacion());
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((aplicacion == null) ? 0 : aplicacion.hashCode());
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
    NotificacionesPorAplicacionDTO other = (NotificacionesPorAplicacionDTO) obj;
    if (aplicacion == null) {
      if (other.aplicacion != null) {
        return false;
      }
    } else if (!aplicacion.getNombreAplicacion().equals(other.aplicacion.getNombreAplicacion())) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("NotificacionesPorAplicacionDTO [aplicacion=").append(aplicacion)
        .append(", cantidadDeNotificaciones=").append(cantidadDeNotificaciones).append("]");
    return builder.toString();
  }

}