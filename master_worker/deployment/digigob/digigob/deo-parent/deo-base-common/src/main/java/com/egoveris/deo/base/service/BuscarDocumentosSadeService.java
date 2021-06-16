package com.egoveris.deo.base.service;

import com.egoveris.deo.base.exception.CodigoDocumentoNuloOVacioExcepcion;
import com.egoveris.deo.base.exception.DocumentoDigitalExistenteException;
import com.egoveris.deo.base.exception.DocumentoExistenteGEDOCaratulaExcepcion;
import com.egoveris.deo.base.exception.NoEsDocumentoSadePapelException;
import com.egoveris.deo.base.exception.NoExisteDocumentoSadeExcepcion;

public interface BuscarDocumentosSadeService {


  /**
   * Consulta si existe la carátula identificada por el codigoSADE, valida si no
   * se ha generado ya en GEDO, un documento asociado a dicha carátula.
   * 
   * @param numeroDocumento:
   *          Número documento en GEDO.
   * @param codigoSADE:
   *          Número carátula SADE.
   * @return String descripción de la trata de la caratula.
   * @throws NoExisteDocumentoSadeExcepcion,DocumentoExistenteGEDOCaratulaExcepcion,DocumentoDigitalExistenteException,
   *           CodigoDocumentoNuloOVacioExcepcion,
   *           NoEsDocumentoSadePapelException
   */
  public String consultaCaratulaSadeExiste(String numeroDocumento, String codigoSADE)
      throws NoExisteDocumentoSadeExcepcion, DocumentoExistenteGEDOCaratulaExcepcion,
      DocumentoDigitalExistenteException, CodigoDocumentoNuloOVacioExcepcion,
      NoEsDocumentoSadePapelException;

}
