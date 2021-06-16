package com.egoveris.vucfront.base.util;

public enum MessageType {
  ERROR("Error"), INFO("Información"), WARNING("Atención"), FALTAN_DATOS("Faltan datos");

  private final String title;

  private MessageType(final String title) {
    this.title = title;
  }

  public String getTitle() {
    return title;
  }
}
