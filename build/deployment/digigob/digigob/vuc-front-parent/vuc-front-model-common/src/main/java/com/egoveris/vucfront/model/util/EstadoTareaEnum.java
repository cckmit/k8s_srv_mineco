package com.egoveris.vucfront.model.util;

public enum EstadoTareaEnum {
  PENDIENTE(1l), FINALIZADO(2l);

  private final Long id;

  private EstadoTareaEnum(final Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }
}