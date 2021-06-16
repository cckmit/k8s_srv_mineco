package com.egoveris.deo.ws.service.impl;

import com.egoveris.deo.base.exception.ParametroInvalidoException;
import com.egoveris.deo.base.exception.TipoReservaNoExisteException;
import com.egoveris.deo.base.service.BuscarDocumentosGedoService;
import com.egoveris.deo.base.service.ObtenerReparticionServices;
import com.egoveris.deo.base.service.TipoReservaService;
import com.egoveris.deo.model.model.DocumentoDTO;
import com.egoveris.deo.model.model.RequestExternalActualizarReservaReparticionDocumento;
import com.egoveris.deo.model.model.TipoReservaDTO;
import com.egoveris.deo.ws.exception.DocumentoNoExisteException;
import com.egoveris.deo.ws.exception.ErrorConsultaDocumentoException;
import com.egoveris.deo.ws.exception.ParametroInvalidoConsultaException;
import com.egoveris.deo.ws.service.IExternalTipoReservaService;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExternalTipoReservaServiceImpl implements IExternalTipoReservaService {

  @Autowired
  private IUsuarioService usuarioService;
  @Autowired
  private BuscarDocumentosGedoService buscarDocumentosGedoService;
  @Autowired
  private TipoReservaService tipoReservaService;
  @Autowired
  private ObtenerReparticionServices obtenerReparticionService;

  private static final Logger logger = LoggerFactory
      .getLogger(ExternalTipoReservaServiceImpl.class);

  public String actualizarReservaReparticionDocumento(
      RequestExternalActualizarReservaReparticionDocumento request)
      throws ParametroInvalidoConsultaException, DocumentoNoExisteException,
      ErrorConsultaDocumentoException, TipoReservaNoExisteException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "actualizarReservaReparticionDocumento(RequestExternalActualizarReservaReparticionDocumento) - start"); //$NON-NLS-1$
    }

    String reparticion;

    validarParametros(request);

    // Obtengo el documento
    DocumentoDTO documento = obtenerDocumento(request.getNumeroDocumento());
    if (!documento.getTipo().getEsConfidencial()) {
      // Obtengo la reserva
      TipoReservaDTO tipoReserva = tipoReservaService
          .obtenerReserva(request.getReservaDocumento().toUpperCase());

      // Verifico si es una reparticion o si es un usuario
      try {
        if (esReparticion(request.getUsuarioOReparticionConsulta().toUpperCase())) {
          reparticion = request.getUsuarioOReparticionConsulta().toUpperCase();
        } else {
          reparticion = obtenerReparticionUsuario(
              request.getUsuarioOReparticionConsulta().toUpperCase());
        }
      } catch (Exception e) {
        logger.error(
            "actualizarReservaReparticionDocumento(RequestExternalActualizarReservaReparticionDocumento)", //$NON-NLS-1$
            e);

        logger.debug(
            "Ha ocurrido un error al identificar el usuario o reparticion." + e.getMessage(), e);
        return RESPUESTA_ERROR;
      }

      try {
        // Llamo a la actualizacion
        tipoReservaService.actualizarReservaReparticionDocumento(documento, tipoReserva,
            reparticion, request.getReparticionesRectoras(),
            request.getUsuarioOReparticionConsulta());
      } catch (Exception e) {
        logger.debug(
            "Ha ocurrido un error al actualizar las reservas y reparticiones del documento: "
                + request.getNumeroDocumento(),
            e.getCause());
        logger.error("Mensaje de error", e);
        return RESPUESTA_ERROR;
      }

      if (logger.isDebugEnabled()) {
        logger.debug(
            "actualizarReservaReparticionDocumento(RequestExternalActualizarReservaReparticionDocumento) - end"); //$NON-NLS-1$
      }
      return RESPUESTA_OK;
    } else {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "actualizarReservaReparticionDocumento(RequestExternalActualizarReservaReparticionDocumento) - end"); //$NON-NLS-1$
      }
      return RESPUESTA_ERROR;
    }

  }

  /**
   * Verifica que exista una reparticion activa bajo el nombre del parametro
   * recibido.
   * 
   * @param usuarioOReparticionConsulta
   * @return
   * @throws Exception
   */
  private boolean esReparticion(String usuarioOReparticionConsulta) throws Exception {
    if (logger.isDebugEnabled()) {
      logger.debug("esReparticion(String) - start"); //$NON-NLS-1$
    }

    boolean esReparticion;
    try {
      esReparticion = obtenerReparticionService
          .validarCodigoReparticion(usuarioOReparticionConsulta);
    } catch (Exception e) {
      logger.debug("Error al verificar la validez de la reparticion.", e.getCause());
      throw (e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("esReparticion(String) - end"); //$NON-NLS-1$
    }
    return esReparticion;
  }

  /**
   * Valida que los paramétros obligatorios enviados por el sistema solicitante
   * sean diferentes de nulo.
   * 
   * @param request
   *          : Objeto RequestExternalActualizarReservaReparticionDocumento
   * @throws ParametroInvalidoException
   */
  private void validarParametros(RequestExternalActualizarReservaReparticionDocumento request)
      throws ParametroInvalidoConsultaException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "validarParametros(RequestExternalActualizarReservaReparticionDocumento) - start"); //$NON-NLS-1$
    }

    String mensaje = "";
    boolean parametroInvalido = false;
    if (request.getUsuarioOReparticionConsulta() == null) {
      parametroInvalido = true;
      mensaje = "Usuario o Reparticion consulta";
    }
    if (request.getNumeroDocumento() == null) {
      parametroInvalido = true;
      mensaje = "Número del documento";
    }
    if (request.getReservaDocumento() == null) {
      parametroInvalido = true;
      mensaje = "Reserva Documento";
    }
    if (parametroInvalido)
      throw new ParametroInvalidoConsultaException("Parámetro " + mensaje + " es obligatorio");

    if (logger.isDebugEnabled()) {
      logger
          .debug("validarParametros(RequestExternalActualizarReservaReparticionDocumento) - end"); //$NON-NLS-1$
    }
  }

  /**
   * Obtiene la organizacion del usuario, para lo cuál valida que el usuario se
   * encuentre registrado en la base de datos de CCOO.
   * 
   * @param usuario
   */
  private String obtenerReparticionUsuario(String usuario)
      throws ParametroInvalidoConsultaException {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerReparticionUsuario(String) - start"); //$NON-NLS-1$
    }

    Usuario datosUsuario = null;
    String varSalida = null;
    try {
      datosUsuario = this.usuarioService.obtenerUsuario(usuario);
      if (datosUsuario != null) {
         if (datosUsuario.getCodigoReparticion() == null) {
           String mensajeError = usuario
               + " no es una Reparticion ni es un Usuario valido. Favor de verificar dicho dato.";
           throw new ParametroInvalidoConsultaException(mensajeError);
         }
         varSalida = datosUsuario.getCodigoReparticion();
      }else{
        String mensajeError = usuario
            + " no es una Reparticion ni es un Usuario valido. Favor de verificar dicho dato.";
        throw new ParametroInvalidoConsultaException(mensajeError);
      }
    } catch (SecurityNegocioException e) {
      logger.error("Mensaje de error", e);
    }
  
    
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerReparticionUsuario(String) - end"); //$NON-NLS-1$
    }
    return varSalida;

  }

  /**
   * Realiza la consulta del documento por número SADE.
   * 
   * @param numeroDocumento
   *          : Número SADE que identifica el documento generado en GEDO.
   * @return
   * @throws DocumentoNoExisteException
   * @throws ErrorConsultaDocumentoException
   */
  private DocumentoDTO obtenerDocumento(String numeroDocumento)
      throws DocumentoNoExisteException, ErrorConsultaDocumentoException {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerDocumento(String) - start"); //$NON-NLS-1$
    }

    DocumentoDTO documento = null;
    try {
      documento = buscarDocumentosGedoService.buscarDocumentoPorNumero(numeroDocumento);
    } catch (Exception e) {
      logger.error("Error en la búsqueda de documento por número", e);
      throw new ErrorConsultaDocumentoException(numeroDocumento + " " + e.getMessage());
    }
    if (documento == null)
      throw new DocumentoNoExisteException(numeroDocumento);

    if (logger.isDebugEnabled()) {
      logger.debug("obtenerDocumento(String) - end"); //$NON-NLS-1$
    }
    return documento;
  }

}
