package com.egoveris.deo.base.service;

import com.egoveris.deo.model.model.DocumentoDTO;
import com.egoveris.deo.model.model.NumerosUsadosDTO;
import com.egoveris.deo.model.model.RequestGenerarDocumento;

import java.util.List;

import org.terasoluna.plus.common.exception.ApplicationException;

public interface CerrarDocumentoService {

  /**
   * Guardar documento y actualizar numeros especiales en el caso en el que se
   * requiera. Si el proceso falla, se hace roolback de todas las acciones
   * realizadas en este método.
   * 
   * @param request
   * @param receptoresAviso
   *          : Lista de avisos.
   * @param numerosUsados:
   *          numero Usado
   */
  public void guardarCierreDocumento(RequestGenerarDocumento request, List<String> receptoresAviso,
      NumerosUsadosDTO numerosUsados);

  /**
   * Copia los archivos de trabajo de la ubicación temporal a la ubicación
   * definitiva. Se encuentra dentro del mismo contexto transaccional del método
   * cierreDocumento, si se presenta un error posterior a la copia se hace
   * rollback de las actualizaciones en base de datos correspondientes a los
   * archivos de trabajo.
   * 
   * @param request
   * @throws Exception
   */
  public void copiarArchivosDeTrabajoTemporales(RequestGenerarDocumento request) throws ApplicationException;

  /**
   * Borra los archivos de trabajo de la ubicación temporal
   * 
   * @param request
   * @param documento
   * @throws Exception
   */
  public void borrarArchivosDeTrabajoTemporales(String workFlowId) throws ApplicationException;

  /**
   * Guarda el documento del tipo recibo en GEDO,Si el proceso falla, se hace
   * roolback de todas las acciones realizadas en este método.
   */
  public DocumentoDTO guardarInformacionDocumento(RequestGenerarDocumento request);
}