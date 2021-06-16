package com.egoveris.deo.ws.service;

import com.egoveris.deo.model.model.RequestExternalConsultaDocumento;
import com.egoveris.deo.model.model.RequestExternalConsultarNumeroSade;
import com.egoveris.deo.model.model.ResponseExternalConsultaDocumento;
import com.egoveris.deo.model.model.ResponseExternalConsultaDocumentoResponse;
import com.egoveris.deo.model.model.ResponseExternalConsultaNumeroSade;
import com.egoveris.deo.ws.exception.DocumentoNoExisteException;
import com.egoveris.deo.ws.exception.ErrorConsultaDocumentoException;
import com.egoveris.deo.ws.exception.ErrorConsultaNumeroSadeException;
import com.egoveris.deo.ws.exception.ParametroInvalidoConsultaException;
import com.egoveris.deo.ws.exception.SinPrivilegiosException;

public interface IExternalConsultaDocumentoService {

  /**
   * Consulta un documento en GEDO, validando existencia, y tipos reservados.
   * 
   * @param request
   * @return un objeto ResponseExternalConsultaDocumento, con los datos básicos
   *         del documento a saber. - numeroDocumento; - usuarioGenerador; -
   *         fechaCreacion;
   * @throws ParametroInvalidoConsultaException
   * @throws DocumentoNoExisteException
   * @throws SinPrivilegiosException
   */
  public ResponseExternalConsultaDocumento consultarDocumentoPorNumero(
      RequestExternalConsultaDocumento request) throws ParametroInvalidoConsultaException,
      DocumentoNoExisteException, SinPrivilegiosException, ErrorConsultaDocumentoException;

  /**
   * Consulta un documento en GEDO
   * 
   * @param request
   * @return Un ResponseExternalConsultaDocumentoDetalle el cual contiene un
   *         DocumentoDetalle
   */
  public ResponseExternalConsultaDocumentoResponse consultarDocumentoDetalle(
      RequestExternalConsultaDocumento request) throws ParametroInvalidoConsultaException;

  /**
   * Consulta un documento en GEDO
   * 
   * @param request
   * @return Un byte[] con el contenido del PDF
   */
  public byte[] consultarDocumentoPdf(RequestExternalConsultaDocumento request)
      throws ParametroInvalidoConsultaException, DocumentoNoExisteException,
      SinPrivilegiosException, ErrorConsultaDocumentoException;

  /**
   * Consulta el numero SADE de un documento por workflowId o por messageId
   * dependiendo del sistema origen
   * 
   * @param request
   * @return
   * @throws ErrorConsultaNumeroSadeException
   */
  public ResponseExternalConsultaNumeroSade consultarNumeroSade(
      RequestExternalConsultarNumeroSade request) throws ErrorConsultaNumeroSadeException;

}
