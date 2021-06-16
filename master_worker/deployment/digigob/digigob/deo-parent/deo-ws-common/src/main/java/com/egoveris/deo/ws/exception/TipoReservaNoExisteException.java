package com.egoveris.deo.ws.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class TipoReservaNoExisteException extends ApplicationException {

  private static final long serialVersionUID = 1026115114460243018L;
  private static final String mensajeError = "Error no se ha generado en GEDO un tipo de reserva con el nombre: ";

  public TipoReservaNoExisteException(final String reserva) {
    super(mensajeError + reserva);
  }

  public TipoReservaNoExisteException(final String reserva, Throwable cause) {
    super(mensajeError + reserva, cause);
  }

}
