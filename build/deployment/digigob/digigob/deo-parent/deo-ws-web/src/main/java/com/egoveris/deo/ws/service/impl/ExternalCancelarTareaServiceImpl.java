package com.egoveris.deo.ws.service.impl;

import com.egoveris.deo.base.service.BorradoTemporal;
import com.egoveris.deo.base.service.GedoADestinatarios;
import com.egoveris.deo.base.service.ProcesoLogService;
import com.egoveris.deo.model.model.DocumentoTemporalDTO;
import com.egoveris.deo.model.model.ProcesoLogDTO;
import com.egoveris.deo.model.model.RequestExternalCancelarTarea;
import com.egoveris.deo.model.model.ResponseExternalCancelarTarea;
import com.egoveris.deo.util.Constantes;
import com.egoveris.deo.ws.exception.ErrorCancelarTareaException;
import com.egoveris.deo.ws.service.IExternalCancelarTareaService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.jws.WebMethod;
import javax.jws.WebParam;

import org.dozer.Mapper;
import org.jbpm.api.ProcessEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.terasoluna.plus.common.exception.ApplicationException;

@Service
public class ExternalCancelarTareaServiceImpl implements IExternalCancelarTareaService {
  /**
   * Logger for this class
   */
  private static final Logger logger = LoggerFactory
      .getLogger(ExternalCancelarTareaServiceImpl.class);

  @Autowired
  private ProcessEngine processEngine;
  @Autowired
  private BorradoTemporal borradoTemporal;
  @Autowired
  private GedoADestinatarios gedoADestinatarios;
  @Autowired
  private ProcesoLogService procesoLogService;
   @Autowired
  private Mapper mapper;

  @WebMethod(operationName = "cancelarTarea")
  public ResponseExternalCancelarTarea cancelarTarea(
      @WebParam(name = "request") RequestExternalCancelarTarea request)
      throws ErrorCancelarTareaException {
    if (logger.isDebugEnabled()) {
      logger.debug("cancelarTarea(RequestExternalCancelarTarea) - start"); //$NON-NLS-1$
    }

    ResponseExternalCancelarTarea response = new ResponseExternalCancelarTarea();

    try {
      cancelarTareaPorWorkflow(request.getWorkflowID());
      response.setEstado("Ok");
    } catch (Exception e) {
      logger.error("cancelarTarea(RequestExternalCancelarTarea)", e); //$NON-NLS-1$

      throw new ErrorCancelarTareaException(e.getMessage(), e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("cancelarTarea(RequestExternalCancelarTarea) - end"); //$NON-NLS-1$
    }
    return response;
  }

  private void persistirLog(String workflowId, String descripcion, String proceso, String estado,
      String sistemaOrigen) {
    if (logger.isDebugEnabled()) {
      logger.debug("persistirLog(String, String, String, String, String) - start"); //$NON-NLS-1$
    }

    ProcesoLogDTO log = new ProcesoLogDTO();

    log.setDescripcion(descripcion);
    log.setEstado(estado);
    log.setFechaCreacion(new Date());
    log.setProceso(proceso);
    log.setSistemaOrigen(sistemaOrigen);
    log.setWorkflowId(workflowId);

    try {
      this.procesoLogService.save(log);
    } catch (ApplicationException e) {
      logger.error("Ha ocurrido un error al persistir el Log del WorflowId: " + workflowId + " - "
          + e.getMessage());
    }

    if (logger.isDebugEnabled()) {
      logger.debug("persistirLog(String, String, String, String, String) - end"); //$NON-NLS-1$
    }
  }

  private void cancelarTareaPorWorkflow(String workflowID) throws Exception {
    if (logger.isDebugEnabled()) {
      logger.debug("cancelarTareaPorWorkflow(String) - start"); //$NON-NLS-1$
    }

    DocumentoTemporalDTO documentoTemporal = null;
    Map<String, String> datos = null;

    try {

      String referencia = (String) processEngine.getExecutionService().getVariable(workflowID,
          Constantes.VAR_MOTIVO);

      if (referencia != null && !referencia.equals("")) {
        datos = new HashMap<>();

        datos.put("referencia", referencia);
        datos.put("motivo", "Tarea cancelada por medio del servicio de cancelarTarea");

      }

      // 5.1- Se obtienen los documentos que seran borrados de Webdav
      documentoTemporal = borradoTemporal.obtenerDocumentosABorrar(workflowID);
      logger.info(
          "Se han obtenido los datos necesarios para el borrado para la solicitud: " + workflowID);

      // 5.2- Borrar registros de la BBDD
      borradoTemporal.borrarRegistrosTemporales(workflowID);
      logger.info(
          "Se han borrado los registros de las tablas de GEDO para la solicitud: " + workflowID);

      try {
        // 5.3- Borrado de Archivos Temporales de WebDav
        borradoTemporal.borrarDocumentosTemporales(documentoTemporal);
        logger.info("Se han borrado los archivos de Webdav para la solicitud: " + workflowID);
      } catch (Exception e) {
        logger.error("Error al borrar los archivos temporales de Webdav para la solicitud: "
            + workflowID + " - " + e.getMessage());
      }

    } catch (Exception e) {
      logger.error("cancelarTareaPorWorkflow(String)", e); //$NON-NLS-1$

      throw new Exception("Error al cancelar la tarea: " + workflowID + " - " + e.getMessage());
    }

    try {
//      gedoADestinatarios.notificarADestinatarios(workflowID,
//          GedoADestinatarios.CANCELAR_DOCUMENTO);
    } catch (Exception e) {
      logger.error("cancelarTareaPorWorkflow(String)", e); //$NON-NLS-1$

      persistirLog(workflowID, "No se pudo enviar a notificar a suscriptores - " + e.getMessage(),
          GedoADestinatarios.NOMBRE_PROCESO, Constantes.PROCESO_LOG_ESTADO_ERROR, "GEDO");
    }

    if (logger.isDebugEnabled()) {
      logger.debug("cancelarTareaPorWorkflow(String) - end"); //$NON-NLS-1$
    }
  }

}
