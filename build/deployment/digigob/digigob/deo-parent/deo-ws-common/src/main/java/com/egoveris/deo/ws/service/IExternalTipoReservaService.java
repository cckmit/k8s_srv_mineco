package com.egoveris.deo.ws.service;

import com.egoveris.deo.model.model.RequestExternalActualizarReservaReparticionDocumento;
import com.egoveris.deo.ws.exception.DocumentoNoExisteException;
import com.egoveris.deo.ws.exception.ErrorConsultaDocumentoException;
import com.egoveris.deo.ws.exception.ParametroInvalidoConsultaException;
import com.egoveris.deo.ws.exception.TipoReservaNoExisteException;

public interface IExternalTipoReservaService {

  public static String RESPUESTA_OK = "OK";
  public static String RESPUESTA_ERROR = "ERROR";

  public String actualizarReservaReparticionDocumento(
      RequestExternalActualizarReservaReparticionDocumento request)
      throws ParametroInvalidoConsultaException, DocumentoNoExisteException,
      ErrorConsultaDocumentoException, TipoReservaNoExisteException;
}
