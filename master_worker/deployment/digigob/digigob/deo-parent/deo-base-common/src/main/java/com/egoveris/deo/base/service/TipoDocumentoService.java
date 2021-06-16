package com.egoveris.deo.base.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.egoveris.deo.base.exception.ExistenDocumentosDelTipoException;
import com.egoveris.deo.base.exception.ExistenDocumentosDelTipoJbpmException;
import com.egoveris.deo.base.exception.GEDOServicesExceptions;
import com.egoveris.deo.model.model.FamiliaTipoDocumentoDTO;
import com.egoveris.deo.model.model.FormatoTamanoArchivoDTO;
import com.egoveris.deo.model.model.ProductionEnum;
import com.egoveris.deo.model.model.ReparticionHabilitadaDTO;
import com.egoveris.deo.model.model.TipoDocumentoAuditoriaDTO;
import com.egoveris.deo.model.model.TipoDocumentoDTO;
import com.egoveris.deo.model.model.TipoDocumentoEmbebidosDTO;
import com.egoveris.deo.model.model.TipoDocumentoReducidoDTO;

public interface TipoDocumentoService {

  public TipoDocumentoAuditoriaDTO crearTipoDocumento(TipoDocumentoDTO tipoDocumento, String userName)
      throws GEDOServicesExceptions;

  /**
   * Eliminar el tipo de documento pasado como parámetro. Lanza una excepción si
   * hay documentos ya creados con ese tipo.
   * 
   * @param tipoDocumento
   *          Tipo de documento a eliminar
   * @throws ExistenDocumentosDelTipoException
   *           Si existen documentos ya creados con ese tipo
   */
  public void eliminarTipoDocumento(TipoDocumentoDTO tipoDocumento, String userName)
      throws ExistenDocumentosDelTipoException, ExistenDocumentosDelTipoJbpmException;

  public void actualizarTareasExistentesConNuevaVersion(TipoDocumentoDTO tipoDocumento,
      TipoDocumentoDTO tipoDocumentoAnterior);

  /**
   * Actualiza la información del tipo de documento en la base de datos,
   * incluyendo información relacionada a reparticiones habilitadas.
   */
  public void modificarTipoDocumentoReparticiones(TipoDocumentoDTO tipoDocumento, String userName);

  /**
   * Actualiza la información del tipo de documento en la base de datos,
   * incluyendo información relacionada a reparticiones habilitadas.
   */
  public void modificarTipoDocumento(TipoDocumentoDTO tipoDocumento, String userName);

  public List<TipoDocumentoDTO> buscarTipoDocumento();

  public List<TipoDocumentoDTO> buscarTipoDocumentoOrdenadoPorFamiliaYTipo();

  public List<TipoDocumentoDTO> buscarTipoDocumentoByEstadoFiltradosManual(String estado,
      boolean esManual);

  public List<TipoDocumentoDTO> buscarTipoDocumentoByFamilia(Map<String, Object> parametros);

  public List<TipoDocumentoDTO> buscarTipoDocumentoSinFiltroByFamilia(
      Map<String, Object> parametros);

  public List<TipoDocumentoDTO> buscarTipoDocumentoByFamiliayComunicable(
      Map<String, Object> parametros);

  public List<TipoDocumentoDTO> buscarTipoDocumentoSinFiltroByFamiliayComunicable(
      Map<String, Object> parametros);

  public List<TipoDocumentoDTO> buscarTipoDocumentoByFamiliaYAcronimo(
      Map<String, Object> parametros);

  public List<TipoDocumentoDTO> buscarTipoDocumentoByFamiliaAcronimoYComunicable(
      Map<String, Object> parametros);

  public List<FamiliaTipoDocumentoDTO> buscarFamilias();

  public List<FamiliaTipoDocumentoDTO> buscarFamiliasByComunicable(
      Map<String, Object> parametros);

  public List<FamiliaTipoDocumentoDTO> buscarFamiliaPorTipoDocumento(
      Map<String, Object> parametros);

  public List<FamiliaTipoDocumentoDTO> buscarFamiliaPorTipoDocumentoYComunicable(
      Map<String, Object> parametros);

  public List<FamiliaTipoDocumentoDTO> buscarTodasLasFamiliasByComunicable(
      Map<String, Object> parametros);

  public List<FamiliaTipoDocumentoDTO> buscarTodasLasFamilias();

  public List<TipoDocumentoDTO> buscarTipoDocumentoByFiltradoComunicable(String estado,
      boolean esManual);

  public boolean existeTipoDocumentoVariables(int idTipoDocumento)
      throws ExistenDocumentosDelTipoException;

  public Set<ReparticionHabilitadaDTO> cargarReparticionesHabilitadas(
      TipoDocumentoDTO tipoDocumento);

  public List<FormatoTamanoArchivoDTO> buscarFormatosArchivos();

  public void borrarTipoDocEmbebidosByTipoDoc(
      List<TipoDocumentoEmbebidosDTO> listaExtensionesPermitidas);

  public void borrarTipoDocEmbebido(TipoDocumentoEmbebidosDTO tipoDocumentoEmbebidos);

  public TipoDocumentoDTO buscarTipoDocumentoPorId(int id);

  public TipoDocumentoDTO buscarTipoDocumentoByAcronimo(String acronimo);

  public TipoDocumentoDTO buscarTipoDocumentoPorAcronimoYVersion(String acronimo, String version);

  public TipoDocumentoDTO buscarTipoDocumentoPorAcronimoConFamilia(String acronimo);

  public TipoDocumentoDTO buscarTipoDocumentoManualByAcronimo(String acronimo);

  public List<TipoDocumentoDTO> buscarDocumentosPorVersiones(String acronimo);

  public List<TipoDocumentoReducidoDTO> buscarTipoDocumentoReducido();

  public List<TipoDocumentoDTO> getDocumentTypeByProduction(ProductionEnum productionType);

  public void saveTipoDocumento(TipoDocumentoAuditoriaDTO tipoDocumen);
  
  public List<TipoDocumentoDTO> getTipoDocumentoByFamiliaNombre(String famNombre);
  
  public List<TipoDocumentoDTO> getTiposDocumentoHabilitados();
  
  public List<TipoDocumentoDTO> getTipoDocumentoEspecial();
  
  public List<TipoDocumentoDTO> getTiposDocumento();
  
  public boolean existsAcronymDocumentType(String acronym);
  
  public List<TipoDocumentoDTO> getTiposDocumentoResultado();

  public List<TipoDocumentoDTO> buscarDocumentosPorIdSade(Integer id);
  
}
