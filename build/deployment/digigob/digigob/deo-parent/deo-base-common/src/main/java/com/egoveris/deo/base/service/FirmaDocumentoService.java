package com.egoveris.deo.base.service;

import java.util.Map;

import com.egoveris.deo.base.exception.FirmaDocumentoException;
import com.egoveris.deo.model.model.FirmaResponse;
import com.egoveris.deo.model.model.RequestGenerarDocumento;

public interface FirmaDocumentoService {


  /**
   * Firma el documento con certificado del servidor. Actualiza los historiales
   * de estado de workflow y de firmante. Avanza el workflow al proximo estado.
   * 
   * @param currentUser
   * @param executionId
   * @return En caso de generar documento devuelve el nro sade, caso contrario
   *         el estado de workflow.
   * @throws FirmaDocumentoException
   *           ver javadoc para los codigos de error
   */
  public FirmaResponse firmFaDocumentoConServ(String currentUser, String executionId)
      throws FirmaDocumentoException;

  public FirmaResponse documentoFirmadoConAutoFirma(byte[] docFirmado, String currentUser, String executionId);

  
  public Map<String, Object> obtenerDocumentoParaFirmarConAutoFirma(String currentUser, String executionId);
//
 	public byte[] firmaDocumentoConServExternal(RequestGenerarDocumento request);

}
