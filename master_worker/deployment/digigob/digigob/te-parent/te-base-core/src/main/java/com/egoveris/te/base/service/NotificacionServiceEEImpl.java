package com.egoveris.te.base.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.egoveris.te.base.exception.AsignacionException;
import com.egoveris.te.base.exception.NegocioException;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.service.iface.IAuditoriaService;
import com.egoveris.te.base.service.iface.INotificacionEEService;
import com.egoveris.vucfront.ws.service.ExternalNotificacionService;
import com.egoveris.vucfront.ws.service.NotificacionesClient;

@Service
@Transactional
public class NotificacionServiceEEImpl implements INotificacionEEService {

  private final static Logger logger = LoggerFactory.getLogger(NotificacionServiceEEImpl.class);

  @Autowired
//  private ExternalNotificacionService externalNotificacionVucService;
  private NotificacionesClient notificacionVucService;
  @Autowired
  private IAuditoriaService auditoriaService;
  @Autowired
  private TipoDocumentoService tipoDocumentoService;
//  @Autowired
//  private ExternalNotificacionService externalNotificacionService;
  @Value("${ee.acronimo.notificacion}")
  private String tipoDocNotificacion;

  public List<DocumentoDTO> obtenerDocumentosNotificables(
      ExpedienteElectronicoDTO expedienteElectronico) {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerDocumentosNotificables(expedienteElectronico={}) - start",
          expedienteElectronico);
    }

    List<DocumentoDTO> docsNotificables = new ArrayList<DocumentoDTO>();
    List<DocumentoDTO> docs = expedienteElectronico.getDocumentos();

    for (DocumentoDTO aux : docs) {
      // Omite los documentos que genera el sistema por cada notificación
      if (aux.getTipoDocAcronimo().equals(tipoDocNotificacion)) {
        continue;
      } else {
        if (isDocumentoNotificable(aux) && !notificacionVucService
            .isDocumentNotified(expedienteElectronico.getCodigoCaratula(), aux.getNumeroSade())) {
          docsNotificables.add(aux);
        }
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("obtenerDocumentosNotificables(ExpedienteElectronico) - end - return value={}",
          docsNotificables);
    }
    return docsNotificables;
  }

  /**
   * Checks if a Documento is Notificable.
   * 
   * @param documento
   * @return
   */
  private Boolean isDocumentoNotificable(DocumentoDTO documento) {
    if (documento.getTipoDocumento() == null) {
      documento.setTipoDocumento(
          tipoDocumentoService.obtenerTipoDocumento(documento.getTipoDocAcronimo()));
    }
    if (documento.getTipoDocumento().getEsNotificable()) {
      return true;
    }
    return false;
  }

  public void altaNotificacionVUC(String usuario, ExpedienteElectronicoDTO ee,
      List<DocumentoDTO> listaDocumento, String motivoDeNotificacion) throws AsignacionException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "altaNotificacionTAD(usuario={}, ee={}, listaDocumento={}, motivoDeNotificacion={}) - start",
          usuario, ee, listaDocumento, motivoDeNotificacion);
    }

    List<com.egoveris.vucfront.model.model.DocumentoDTO> documentosVuc = new ArrayList<>();

    for (DocumentoDTO documento : listaDocumento) {
      com.egoveris.vucfront.model.model.DocumentoDTO docVuc = new com.egoveris.vucfront.model.model.DocumentoDTO();

      if (documento.getId() != null) {
        docVuc.setId(new Long(documento.getId()));
      }
      docVuc.setNumeroDocumento(documento.getNumeroSade());
      docVuc.setUsuarioCreacion(usuario);
      docVuc.setReferencia(documento.getMotivo());

      documentosVuc.add(docVuc);

      notificacionVucService.altaNotificacionTAD(ee.getCodigoCaratula(), docVuc,
          motivoDeNotificacion);
      // grabarAuditoria(documentoDTO);
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "altaNotificacionTAD(String, ExpedienteElectronico, List<Documento>, String) - end");
    }
  }

  public void notificarBuzonJudicial(String usuario, ExpedienteElectronicoDTO ee, DocumentoDTO d,
      String motivoNotificacion, String cuit) throws NegocioException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "notificarBuzonJudicial(usuario={}, ee={}, d={}, motivoNotificacion={}, cuit={}) - start",
          usuario, ee, d, motivoNotificacion, cuit);
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "notificarBuzonJudicial(String, ExpedienteElectronico, Documento, String, String) - end");
    }
  }

  public void setAuditoriaService(IAuditoriaService auditoriaService) {
    this.auditoriaService = auditoriaService;
  }

  public IAuditoriaService getAuditoriaService() {
    return auditoriaService;
  }

}