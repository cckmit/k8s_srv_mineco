package com.egoveris.vucfront.model.util;

public enum EstadoTramiteEnum {
  BORRADOR(1l), APROBADO(2l), ENTRAMITE(3l), RECHAZADO(4l);

  private final Long id;

  private EstadoTramiteEnum(final Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }
}