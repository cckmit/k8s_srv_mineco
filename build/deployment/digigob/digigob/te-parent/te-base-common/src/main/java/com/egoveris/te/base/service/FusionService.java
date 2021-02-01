package com.egoveris.te.base.service;


import java.util.List;
import java.util.Map;

import org.jbpm.api.task.Task;

import com.egoveris.te.base.exception.ArchivoUploadException;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.ExpedienteAsociadoEntDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;



/**
 * The Interface FusionService.
 */
public interface FusionService {

  /**
   * Realiza validaciones sobre los expedientes y crea el Expediente Fusion
   * Asociado.
   *
   * @param expParaAsociar
   *          the exp para asociar
   * @param loggedUsername
   *          the logged username
   * @param estado
   *          the estado
   * @return the expediente asociado
   */
  public ExpedienteAsociadoEntDTO obtenerExpedienteFusion(ExpedienteElectronicoDTO expParaAsociar,
      String loggedUsername, String estado);

  /**
   * Obtener expedientes de fusion.
   *
   * @param idExpedienteAsociado
   *          the id expediente asociado
   * @return the expediente asociado
   */
  public ExpedienteAsociadoEntDTO obtenerExpedientesDeFusion(int idExpedienteAsociado);

  /**
   * Guardar expediente fusion.
   *
   * @param expedienteAsociado
   *          the expediente asociado
   */
  public void guardarExpedienteFusion(ExpedienteAsociadoEntDTO expedienteAsociado);

  /**
   * Eliminar expediente fusion.
   *
   * @param expedienteAsociado
   *          the expediente asociado
   */
  public void eliminarExpedienteFusion(ExpedienteAsociadoEntDTO expedienteAsociado);

  /**
   * Movimiento fusion.
   *
   * @param expedienteElectronico
   *          the expediente electronico
   * @param loggedUsername
   *          the logged username
   * @param detalles
   *          the detalles
   * @param estadoAnterior
   *          the estado anterior
   * @param estadoSeleccionado
   *          the estado seleccionado
   * @param motivoExpediente
   *          the motivo expediente
   * @param destino
   *          the destino
   */
  public void movimientoFusion(ExpedienteElectronicoDTO expedienteElectronico,
      String loggedUsername, Map<String, String> detalles, String estadoAnterior,
      String estadoSeleccionado, String motivoExpediente, String destino);

  /**
   * Actulizar documentos en fusion.
   *
   * @param expedienteElectronico
   *          the expediente electronico
   */
  public void actulizarDocumentosEnFusion(ExpedienteElectronicoDTO expedienteElectronico);

  /**
   * Actualizar archivos de trabajo en fusion.
   *
   * @param expedienteElectronico
   *          the expediente electronico
   */
  public void actualizarArchivosDeTrabajoEnFusion(ExpedienteElectronicoDTO expedienteElectronico);

  /**
   * Actualizar expedientes asociados en fusion.
   *
   * @param expedienteElectronico
   *          the expediente electronico
   */
  public void actualizarExpedientesAsociadosEnFusion(
      ExpedienteElectronicoDTO expedienteElectronico);

  /**
   * Desvincular expedientes fusion.
   *
   * @param expedienteElectronico
   *          the expediente electronico
   * @param loggedUsername
   *          the logged username
   * @param documentoTC
   *          the documento TC
   * @return the expediente electronico
   */
  public ExpedienteElectronicoDTO desvincularExpedientesFusion(
      ExpedienteElectronicoDTO expedienteElectronico, String loggedUsername,
      DocumentoDTO documentoTC);

  /**
   * Se fija si el expediente se encuentra en proceso de fusión Valida si el
   * mismo esta como Expediente Asociado, en tramitacionConjunta y en fusión =
   * true.
   *
   * @param codigoExpediente
   *          (p.e.: EX-2011-00000025- -MGEYA-MGEYA)
   * @return boolean
   */
  public boolean esExpedienteEnProcesoDeFusion(String codigoExpediente);

  /**
   * Se obtiene el c�digo de de expediente cabecera al que pertenece el
   * expediente seleccionado.
   * 
   * @param codigoExpedienteAsociado
   *          (p.e.: EX-2011-00000025- -MGEYA-MGEYA)
   * @return String del c�digo de expediente cabecera
   */
  public String obtenerExpedienteElectronicoCabecera(String codigoExpedienteAsociado);

  /**
   * Se creará un documento de vinculación de fusión. Se agrega al expediente
   * cabecera.
   *
   * @param expedienteElectronico
   *          the expediente electronico
   * @param movito
   *          the movito
   * @param username
   *          the username
   * @param workingTask
   *          the working task
   * @return the documento
   */
  public DocumentoDTO generarDocumentoDeVinculacionEnFusion(
      ExpedienteElectronicoDTO expedienteElectronico, String movito, String username,
      Task workingTask);

  /**
   * Se creará un documento de desvinculación de fusión. Se agrega al expediente
   * cabecera.
   *
   * @param expedienteElectronico
   *          the expediente electronico
   * @param movito
   *          the movito
   * @param username
   *          the username
   * @param workingTask
   *          the working task
   * @return the documento
   */
  public DocumentoDTO generarDocumentoDeDesvinculacionEnFusion(
      ExpedienteElectronicoDTO expedienteElectronico, String movito, String username,
      Task workingTask);

  /**
   * Es expediente fusion.
   *
   * @param numero
   *          the numero
   * @return true, if successful
   */
  public boolean esExpedienteFusion(Integer numero);

  /**
   * Actualizar estado visualizacion cabecera.
   *
   * @param expedienteCabecera
   *          the expediente cabecera
   * @param username
   *          the username
   * @param listaDocumentos
   *          the lista documentos
   * @throws RuntimeException
   *           the runtime exception
   */
  public void actualizarEstadoVisualizacionCabecera(ExpedienteElectronicoDTO expedienteCabecera,
      String username, List<String> listaDocumentos) throws ArchivoUploadException;
}
