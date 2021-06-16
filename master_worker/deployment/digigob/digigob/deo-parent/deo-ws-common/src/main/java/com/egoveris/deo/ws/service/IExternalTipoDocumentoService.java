package com.egoveris.deo.ws.service;

import java.util.List;

import com.egoveris.deo.model.model.ProductionEnum;
import com.egoveris.deo.model.model.ResponseTipoDocumento;
import com.egoveris.deo.ws.exception.ErrorConsultaTipoDocumentoException;
import com.egoveris.deo.ws.exception.TipoDocumentoNoExisteException;

public interface IExternalTipoDocumentoService {

  /**
   * Obtiene todos los tipos de documentos disponibles con sus metadatos
   * asociados. No incluye las reparticiones habilitadas
   *
   * @return Lista de documentos existentes
   * @throws ErrorConsultaTipoDocumentoException the error consulta tipo documento exception
   */
  List<ResponseTipoDocumento> consultarTiposDocumento() throws ErrorConsultaTipoDocumentoException;

  /**
   * Obtiene un tipo de documento a partir de su acrónimo, con sus metadatos
   * asociados. No incluye las reparticiones habilitadas
   *
   * @param acronimo          Sigla identificadora del tipo de documento
   * @return Lista de documentos existentes
   * @throws ErrorConsultaTipoDocumentoException the error consulta tipo documento exception
   * @throws TipoDocumentoNoExisteException the tipo documento no existe exception
   */
  ResponseTipoDocumento consultarTipoDocumentoPorAcronimo(String acronimo)
      throws ErrorConsultaTipoDocumentoException, TipoDocumentoNoExisteException;

  /**
   * Buscar tipo documento by estado filtrados manual.
   *
   * @param estado the estado
   * @param esManual the es manual
   * @return the list
   * @throws ErrorConsultaTipoDocumentoException the error consulta tipo documento exception
   */
  List<ResponseTipoDocumento> buscarTipoDocumentoByEstadoFiltradosManual(String estado,
      boolean esManual) throws ErrorConsultaTipoDocumentoException;

  /**
   * Buscar tipo documento by acronimo.
   *
   * @param acronimo the acronimo
   * @return the response tipo documento
   * @throws ErrorConsultaTipoDocumentoException the error consulta tipo documento exception
   * @throws TipoDocumentoNoExisteException the tipo documento no existe exception
   */
  ResponseTipoDocumento buscarTipoDocumentoByAcronimo(String acronimo)
      throws ErrorConsultaTipoDocumentoException, TipoDocumentoNoExisteException;

  /**
   * Return all active documents that are produced by the type indicated.
   *
   * @param productionType the production type
   * @return the document type by production
   * @throws ErrorConsultaTipoDocumentoException the error consulta tipo documento exception
   */
  List<ResponseTipoDocumento> getDocumentTypeByProduction(ProductionEnum productionType)
      throws ErrorConsultaTipoDocumentoException;


  /**
   * Get's the last version of all TipoDocumento of some FamiliatipoDocumento ordered by codigoTipoDocumentoSade.
   *
   * @param famNombre the familia nombre
   * @return List of TipoDocumento of some FamiliaTipoDocumento
   */
  List<ResponseTipoDocumento> getTipoDocumentoByFamilia(String famNombre);

  /**
   * Get's all the TipoDocumento.
   *
   * @return List of all TipoDocumento
   */
  List<ResponseTipoDocumento> getTiposDocumentoHabilitados();
  
  
  /**
   * Get's all the TipoDocumento especial.
   *
   * @return the tipo documento especial
   */
  List<ResponseTipoDocumento> getTipoDocumentoEspecial();
  
  
  /**
   * Gets all the tipos documento.
   *
   * @return the tipos documento
   */
  List<ResponseTipoDocumento> getTiposDocumento();
  
  
  /**
   * Buscar tipo documento por id.
   *
   * @param TipoDocumento ID
   * @return the TipoDocumento DTO
   */
  ResponseTipoDocumento buscarTipoDocumentoPorId(Integer id);
  
  /**
   * Get all TipoDocumento with resultado = true
   * 
   * @return List<ResponseTipoDocumento>
   */
  List<ResponseTipoDocumento> getTiposDocumentoResultado();
  
  /**
   * Gets the tipo documento by actuacion.
   *
   * @param id the id
   * @return the tipo documento by actuacion
   */
  List<ResponseTipoDocumento> getTipoDocumentoByIdSade(Integer id);

  /**
   * Existe documentos asociados.
   *
   * @param idActuacion the id actuacion
   * @return true, if successful
   */
  boolean existeDocumentosAsociados(Integer idActuacion);
  
}
