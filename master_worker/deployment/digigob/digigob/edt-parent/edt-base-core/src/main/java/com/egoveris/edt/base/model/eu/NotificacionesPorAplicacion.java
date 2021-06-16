package com.egoveris.edt.base.model.eu;

public class NotificacionesPorAplicacion {

  private Aplicacion aplicacion;
  private Long cantidadDeNotificaciones;

  public NotificacionesPorAplicacion(Aplicacion aplicacion, Long cantidadDeNotificaciones) {
    this.aplicacion = aplicacion;
    this.cantidadDeNotificaciones = cantidadDeNotificaciones;
  }

  public Aplicacion getAplicacion() {
    return aplicacion;
  }

  public void setAplicacion(Aplicacion aplicacion) {
    this.aplicacion = aplicacion;
  }

  public Long getCantidadDeNotificaciones() {
    return cantidadDeNotificaciones;
  }

  public void setCantidadDeNotificaciones(Long cantidadDeNotificaciones) {
    this.cantidadDeNotificaciones = cantidadDeNotificaciones;
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
    NotificacionesPorAplicacion other = (NotificacionesPorAplicacion) obj;
    if (aplicacion == null) {
      if (other.aplicacion != null) {
        return false;
      }
    } else if (!aplicacion.getNombreAplicacion().equals(other.aplicacion.getNombreAplicacion())) {
      return false;
    }
    return true;
  }

}