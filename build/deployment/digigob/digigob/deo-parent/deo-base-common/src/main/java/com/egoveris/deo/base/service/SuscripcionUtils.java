package com.egoveris.deo.base.service;

import com.egoveris.deo.model.model.DocumentoDTO;
import com.egoveris.deo.model.model.ErrorReintentoDTO;
import com.egoveris.deo.model.model.SistemaOrigenDTO;
import com.egoveris.deo.model.model.SuscripcionDTO;

import java.util.List;

import org.terasoluna.plus.common.exception.ApplicationException;

public interface SuscripcionUtils {

  /**
   * Persistencia de un determinado proceso en la tabla de logs para OK o para
   * ERROR.
   * 
   * @param workflowId
   * @param descripcion
   * @param proceso
   * @param estado
   * @param reintento
   */
  public void persistirLog(String workflowId, String descripcion, String proceso, String estado,
      String sistemaOrigen);

  /**
   * Obtiene la lista de suscriptores en base a un determinado WorkflowId y
   * Estado.
   * 
   * @param workflowId
   * @param estado
   * @return
   * @throws Exception
   */
  public List<SuscripcionDTO> obtenerSuscripciones(String workflowId, String Estado)
      throws ApplicationException;

  /**
   * Obtiene un objeto Suscripcion en base a un workflowId y a un SistemaOrigen.
   * 
   * @param workflowId
   * @param sistemaOrigen
   * @return
   * @throws Exception
   */
  public SuscripcionDTO obtenerSuscripcion(String workflowId, SistemaOrigenDTO sistemaOrigen)
      throws ApplicationException;

  /**
   * Obtiene un objeto SistemaOrigen en base al nombre del mismo.
   * 
   * @param nombreSistemaOrigen
   * @return
   * @throws Exception
   */
  public SistemaOrigenDTO obtenerSistemaOrigen(String nombreSistemaOrigen)
      throws ApplicationException;

  /**
   * Obtiene un objeto Documento en base a un workflowId.
   * 
   * @param workflowId
   * @return
   * @throws Exception
   */
  public DocumentoDTO obtenerDocumento(String workflowId) throws ApplicationException;

  /**
   * Obtiene un objeto ErrorReintento en base a un id de error.
   * 
   * @param idError
   * @return
   * @throws Exception
   */
  public ErrorReintentoDTO obtenerErrorReintento(int idError) throws ApplicationException;

  /**
   * Persistencia de una suscripcion determinada.
   * 
   * @param suscripcion
   * @return
   * @throws Exception
   */
  public void persistirSuscripcion(SuscripcionDTO suscripcion) throws ApplicationException;
}
