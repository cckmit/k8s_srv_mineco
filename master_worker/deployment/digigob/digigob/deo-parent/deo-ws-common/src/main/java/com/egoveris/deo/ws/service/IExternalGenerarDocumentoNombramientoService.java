package com.egoveris.deo.ws.service;

import com.egoveris.deo.model.model.RequestExternalGenerarDocumento;
import com.egoveris.deo.model.model.ResponseExternalGenerarDocumento;
import com.egoveris.deo.ws.exception.CantidadDatosNoSoportadaException;
import com.egoveris.deo.ws.exception.ClavesFaltantesException;
import com.egoveris.deo.ws.exception.ErrorGeneracionDocumentoException;
import com.egoveris.deo.ws.exception.ParametroInvalidoException;

public interface IExternalGenerarDocumentoNombramientoService {

  /**
   * Genera un documento en GEDO.
   * 
   * @param request
   * @return
   * @throws ParametroInvalidoException
   * @throws CantidadDatosNoSoportadaException
   * @throws ErrorGeneracionDocumentoException
   * @throws ClavesFaltantesException
   */
  public ResponseExternalGenerarDocumento generarDocumentoGEDO(
      RequestExternalGenerarDocumento request)
      throws ParametroInvalidoException, CantidadDatosNoSoportadaException,
      ErrorGeneracionDocumentoException, ClavesFaltantesException;
}
