package com.egoveris.deo.ws.service;

import com.egoveris.deo.model.model.RequestExternalDepuracionDocumento;
import com.egoveris.deo.model.model.ResponseExternalDepuracionDocumento;
import com.egoveris.deo.ws.exception.ErrorDepuracionDocumentoException;

public interface IExternalDepuracionDocumentoService {

  public ResponseExternalDepuracionDocumento depuracionDocumento(
      RequestExternalDepuracionDocumento request) throws ErrorDepuracionDocumentoException;

}