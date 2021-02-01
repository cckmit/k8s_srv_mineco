package com.egoveris.deo.ws.service.impl;

import com.egoveris.deo.base.exception.ParametroInvalidoException;
import com.egoveris.deo.base.exception.SinPersistirException;
import com.egoveris.deo.base.service.BuscarDocumentosGedoService;
import com.egoveris.deo.base.service.HistorialReservaService;
import com.egoveris.deo.model.model.DocumentoDTO;
import com.egoveris.deo.model.model.HistorialReservaDTO;
import com.egoveris.deo.ws.exception.DocumentoNoExisteException;
import com.egoveris.deo.ws.exception.ErrorConsultaDocumentoException;
import com.egoveris.deo.ws.service.IExternalActualizarVisualizacionService;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExternalActualizarVisualizacionServiceImpl
    implements IExternalActualizarVisualizacionService {
  /**
   * Logger for this class
   */
  private static final Logger logger = LoggerFactory
      .getLogger(ExternalActualizarVisualizacionServiceImpl.class);

  @Autowired
  private IUsuarioService usuarioService;
  @Autowired
  private BuscarDocumentosGedoService buscarDocumentosGedoService;
  @Autowired
  private HistorialReservaService historialReservaService;

  @Override
  public void actualizarEstadoVisualizacion(String username, List<String> lstRectoras,
      List<String> lstDocumentos) throws ParametroInvalidoException, DocumentoNoExisteException,
      ErrorConsultaDocumentoException {
    if (logger.isDebugEnabled()) {
      logger.debug("actualizarEstadoVisualizacion(String, List<String>, List<String>) - start"); //$NON-NLS-1$
    }

    List<HistorialReservaDTO> listaHistorialesGuardar = new ArrayList<>();
    Usuario user = null;
    try {
      user = usuarioService.obtenerUsuario(username);
    } catch (SecurityNegocioException e) {
      logger.error("Error al obtener los datos del usuario: " + e.getMessage(), e);
      throw new ParametroInvalidoException(
          "Error al obtener los datos del usuario: " + e.getMessage());
    }

    List<HistorialReservaDTO> listaHistoriales = new ArrayList<>();

    for (String documentoBusqueda : lstDocumentos) {
      if (lstRectoras != null && !lstRectoras.isEmpty()) {
        for (String rectora : lstRectoras) {
          listaHistoriales.addAll(historialReservaService
              .obtenerHistorialReservaPorUsuario(username, rectora, documentoBusqueda));
        }
      } else {
        listaHistoriales.addAll(historialReservaService.obtenerHistorialReservaPorUsuario(username,
            null, documentoBusqueda));
      }
    }

    List<DocumentoDTO> listaDocumentos = obtenerListaDocumentos(lstDocumentos);

    if (null != lstRectoras && !lstRectoras.isEmpty()) {

      for (String rectora : lstRectoras) {
        crearHistorialDocumentoReserva(listaDocumentos, rectora, user, listaHistorialesGuardar);
      }
    } else {
      crearHistorialDocumentoReserva(listaDocumentos, null, user, listaHistorialesGuardar);
    }

    if (!listaHistoriales.isEmpty()) {
      listaHistorialesGuardar.removeAll(listaHistoriales);
    }
    try {
      for (HistorialReservaDTO historial : listaHistorialesGuardar) {
        historialReservaService.guardarHistorialReserva(historial);
      }
    } catch (SinPersistirException e) {
      logger.error("No se pudo persistir: " + e.getMessage(), e);
      throw new ParametroInvalidoException(e.getMessage());
    }

    if (logger.isDebugEnabled()) {
      logger.debug("actualizarEstadoVisualizacion(String, List<String>, List<String>) - end"); //$NON-NLS-1$
    }
  }

  private void crearHistorialDocumentoReserva(List<DocumentoDTO> documentos, String rectora,
      Usuario usuario, List<HistorialReservaDTO> listaHistorialesGuardar) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "crearHistorialDocumentoReserva(List<Documento>, String, Usuario, List<HistorialReserva>) - start"); //$NON-NLS-1$
    }

    HistorialReservaDTO historialReserva = null;
    for (DocumentoDTO documento : documentos) {
      if (!documento.getTipo().getEsConfidencial()) {
        historialReserva = new HistorialReservaDTO();
        historialReserva.setDocumento(documento.getNumero());
        historialReserva.setUsuario(usuario.getUsername());
        historialReserva.setSector(usuario.getCodigoSectorInterno());
        historialReserva.setReparticion(usuario.getCodigoReparticion());
        historialReserva.setReparticionRectora(rectora);
        historialReserva.setIdDocumento(documento.getId());
        historialReserva.setFechaAlta(new Date());
        listaHistorialesGuardar.add(historialReserva);
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "crearHistorialDocumentoReserva(List<Documento>, String, Usuario, List<HistorialReserva>) - end"); //$NON-NLS-1$
    }
  }

  private List<DocumentoDTO> obtenerListaDocumentos(List<String> nroDocumento)
      throws DocumentoNoExisteException, ErrorConsultaDocumentoException {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerListaDocumentos(List<String>) - start"); //$NON-NLS-1$
    }

    List<DocumentoDTO> documentos = new ArrayList<>();
    for (String documento : nroDocumento) {
      try {
        DocumentoDTO doc = buscarDocumentosGedoService.buscarDocumentoPorNumero(documento);
        if (doc == null) {
          throw new DocumentoNoExisteException(documento);
        } else {
          documentos.add(doc);
        }
      } catch (Exception e) {
        logger.error("Error en la búsqueda de documento por número", e);
        throw new ErrorConsultaDocumentoException(documento + " " + e.getMessage());
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("obtenerListaDocumentos(List<String>) - end"); //$NON-NLS-1$
    }
    return documentos;
  }
}
