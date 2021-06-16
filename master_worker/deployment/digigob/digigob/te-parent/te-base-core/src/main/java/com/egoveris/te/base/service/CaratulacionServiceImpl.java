package com.egoveris.te.base.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.stereotype.Service;
import org.zkoss.util.resource.Labels;

import com.egoveris.deo.model.model.RequestExternalGenerarDocumento;
import com.egoveris.deo.model.model.ResponseExternalGenerarDocumento;
import com.egoveris.deo.ws.exception.ErrorGeneracionDocumentoException;
import com.egoveris.deo.ws.service.IExternalGenerarDocumentoService;
import com.egoveris.numerador.model.model.NumeroDTO;
import com.egoveris.numerador.ws.service.ExternalNumeroService;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.te.base.exception.external.TeRuntimeException;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.NroSADEProceso;
import com.egoveris.te.base.model.SolicitudExpedienteDTO;
import com.egoveris.te.base.model.TipoDocumentoDTO;
import com.egoveris.te.base.model.expediente.ExpedienteElectronico;
import com.egoveris.te.base.repository.expediente.ExpedienteElectronicoRepository;
import com.egoveris.te.base.util.ConstantesDB;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.DocumentoCaratulaGedoTemplate;

@Service
@Transactional
public class CaratulacionServiceImpl implements CaratulacionService {

  @Autowired
  private IExternalGenerarDocumentoService generarDocumentoService;
  @Autowired
  private UsuariosSADEService usuariosSADEService;
  @Autowired
  private ConstantesDB constantesDB;
  @Autowired
  private DocumentoCaratulaGedoTemplate template;
  @Autowired
  private TipoDocumentoService tipoDocumentoService;
  @Autowired
  private ExternalNumeroService numeradorService;
  @Autowired
  private ExpedienteElectronicoRepository expedienteElectronicoRepository;

  private final static Logger logger = LoggerFactory.getLogger(CaratulacionServiceImpl.class);
  private static String CARATULA = "CARATULA";
  private static String CARATULA_RESERVADA = "CARATULA_RESERVADA";

  /**
   * Realiza la caratulacion. Le pide a SADE un numero SADE para el expediente y
   * con eso genera un stream que envia a gedo para generar el documento
   * caratula.
   */

  public ExpedienteElectronicoDTO caratular(ExpedienteElectronicoDTO expedienteElectronico,
      SolicitudExpedienteDTO solicitud, String username) {
    if (logger.isDebugEnabled()) {
      logger.debug("caratular(expedienteElectronico={}, solicitud={}, username={}) - start",
          expedienteElectronico, solicitud, username);
    }

    NumeroDTO numeroSade = null;

    try {
      if (expedienteElectronico.getFechaModificacion() == null) {

        String tipoActuacion;

        if (expedienteElectronico.getTrata().getTipoActuacion() != null) {
          tipoActuacion = expedienteElectronico.getTrata().getTipoActuacion();
        } else {
          tipoActuacion = ConstantesWeb.TIPO_ACTUACION;
        }

        Usuario usuario = usuariosSADEService.getDatosUsuario(username);
        numeroSade = this.numeradorService.obtenerNumeroSade(usuario.getUsername(),
            ConstantesWeb.SIGLA_MODULO_ORIGEN, tipoActuacion,
            constantesDB.getNombreReparticionActuacion(), usuario.getCodigoReparticion());

        expedienteElectronico.setTipoDocumento(tipoActuacion);
        expedienteElectronico.setAnio(numeroSade.getAnio());
        expedienteElectronico.setNumero(numeroSade.getNumero());
        expedienteElectronico.setSecuencia(ConstantesWeb.SECUENCIA_GENERICA);
        expedienteElectronico
            .setCodigoReparticionActuacion(constantesDB.getNombreReparticionActuacion());
        expedienteElectronico.setCodigoReparticionUsuario(usuario.getCodigoReparticion());

      }

      RequestExternalGenerarDocumento request = this
          .armarRequestParaGenerarCaratulaEnGedo(expedienteElectronico, solicitud);
      ResponseExternalGenerarDocumento response;
      DocumentoDTO documentoCaratula;
      try {
        response = this.generarDocumentoEnGedo(request);
        documentoCaratula = generarDocumentoCaratula(expedienteElectronico, username, response);
      } catch (RemoteAccessException e) {
        String mensaje = Labels.getLabel("ee.servicio.gedo.errorComunicacionTSA");
        logger.error(mensaje);
        throw e;
      }

      documentoCaratula.setTipoDocAcronimo(request.getAcronimoTipoDocumento());

      // Añadir caratula como documento del expediente
      expedienteElectronico.getDocumentos().add(documentoCaratula);

      // confirmar el numero pedido
      if (numeroSade != null) {
        this.numeradorService.confimarNumeroSade(numeroSade.getAnio(), numeroSade.getNumero());
      }

      if (logger.isDebugEnabled()) {
        logger.debug(
            "caratular(ExpedienteElectronico, SolicitudExpediente, String) - end - return value={}",
            expedienteElectronico);
      }
      return expedienteElectronico;

    } catch (RemoteAccessException e) {
      throw e;
    } catch (Exception rte) {
      logger.error("caratular(ExpedienteElectronico, SolicitudExpediente, String)", rte);

      // Anula nro pedido ante cualquier error
      try {
        if (numeroSade != null)
          this.numeradorService.anularNumeroSade(numeroSade.getAnio(), numeroSade.getNumero());
      } catch (Exception numerar) {
        logger.error("Error al anular el número:", numerar);
        logger.error("Error al anular el número: " + numeroSade + " - " + numeroSade + " : "
            + numerar.getMessage());
      }
      throw new TeRuntimeException(rte);
    }
  }

  /**
   * Se genera el documentoCaratula para ser agregado en los documentos del
   * ExpedienteElectrónico.
   * 
   * @param expedienteElectronico
   * @param username
   * @param response
   * @return documentoCaratula
   */
  private DocumentoDTO generarDocumentoCaratula(ExpedienteElectronicoDTO expedienteElectronico,
      String username, ResponseExternalGenerarDocumento response) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "generarDocumentoCaratula(expedienteElectronico={}, username={}, response={}) - start",
          expedienteElectronico, username, response);
    }

    DocumentoDTO documentoCaratula = new DocumentoDTO();
    documentoCaratula.setFechaCreacion(expedienteElectronico.getFechaCreacion());
    documentoCaratula.setFechaAsociacion(new Date());
    documentoCaratula.setNombreUsuarioGenerador(username);
    documentoCaratula.setDefinitivo(true);
    // TODO revisar necesidad de almacenar nombre de archivo tal como se
    // hace aquí (sin ruta absoluta, asumiendo .pdf)
    documentoCaratula.setNombreArchivo(response.getNumero() + ".pdf");
    documentoCaratula.setNumeroSade(response.getNumero());
    documentoCaratula.setUsuarioAsociador(username);
    if (expedienteElectronico.getFechaModificacion() == null) {
      documentoCaratula.setMotivo(ConstantesWeb.MOTIVO_CARATULA);
    } else {
      documentoCaratula.setMotivo(ConstantesWeb.MOTIVO_MODIFICACION_CARATULA);
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "generarDocumentoCaratula(ExpedienteElectronico, String, ResponseExternalGenerarDocumento) - end - return value={}",
          documentoCaratula);
    }
    return documentoCaratula;
  }

  /**
   * Genera un documento en GEDO con la caratula del expediente.
   * 
   * @param request
   */
  private ResponseExternalGenerarDocumento generarDocumentoEnGedo(
      RequestExternalGenerarDocumento request) {
    if (logger.isDebugEnabled()) {
      logger.debug("generarDocumentoEnGedo(request={}) - start", request);
    }

    try {
      ResponseExternalGenerarDocumento response = generarDocumentoService
          .generarDocumentoGEDO(request);

      if (response == null) {
        throw new TeRuntimeException("Se produjo un error al generar la caratula", null);
      } else {
        if (logger.isDebugEnabled()) {
          logger.debug(
              "generarDocumentoEnGedo(RequestExternalGenerarDocumento) - end - return value={}",
              response);
        }
        return response;
      }
    } catch (RemoteAccessException e) {
      String mensajeError = Labels.getLabel("ee.servicio.gedo.errorComunicacionGEDO");
      logger.error(mensajeError, e);
      throw e;
    } catch (ErrorGeneracionDocumentoException e) {
      String error = Labels.getLabel("ee.servicio.gedo.errorComunicacionTSA");
      logger.error(error, e);
      throw new RemoteAccessException(error);
    } catch (Exception e) {
      logger.error("Se produjo un error al generar la caratula", e);
      throw new TeRuntimeException("Se produjo un error al generar la caratula", e);
    }

  }

  /**
   * Arma el request para generar la caratula en gedo.
   * 
   * @param caratula
   * @return
   * @throws InterruptedException
   */
  private RequestExternalGenerarDocumento armarRequestParaGenerarCaratulaEnGedo(
      ExpedienteElectronicoDTO expedienteElectronico, SolicitudExpedienteDTO solicitud)
      throws RuntimeException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "armarRequestParaGenerarCaratulaEnGedo(expedienteElectronico={}, solicitud={}) - start",
          expedienteElectronico, solicitud);
    }

    TipoDocumentoDTO tipoDocumentoCaratulaDeEE;

    if (expedienteElectronico.getEsReservado()) {
      tipoDocumentoCaratulaDeEE = this.tipoDocumentoService
          .buscarTipoDocumentoByUso(CARATULA_RESERVADA);
    } else {
      tipoDocumentoCaratulaDeEE = this.tipoDocumentoService.buscarTipoDocumentoByUso(CARATULA);
    }

    if (tipoDocumentoCaratulaDeEE == null) {
      throw new TeRuntimeException(
          "El administrador debe seleccionar un documento Caratula para poder generar el trámite.",
          null);
    }

    RequestExternalGenerarDocumento request = new RequestExternalGenerarDocumento();
    request.setData(template.generarCaratulaComoByteArray(expedienteElectronico, solicitud));
    request.setReferencia("Carátula del expediente " + expedienteElectronico.getCodigoCaratula());
    // Se pone el Acronimo para diferenciar los documentos de SADE con los
    // de GEDO.
    request.setAcronimoTipoDocumento(tipoDocumentoCaratulaDeEE.getAcronimo());
    request.setUsuario(expedienteElectronico.getUsuarioCreador());
    request.setSistemaOrigen(ConstantesWeb.SIGLA_MODULO_ORIGEN);

    if (logger.isDebugEnabled()) {
      logger.debug(
          "armarRequestParaGenerarCaratulaEnGedo(ExpedienteElectronico, SolicitudExpediente) - end - return value={}",
          request);
    }
    return request;
  }

  public void setUsuariosSADEService(UsuariosSADEService usuariosSADEService) {
    this.usuariosSADEService = usuariosSADEService;
  }

  public UsuariosSADEService getUsuariosSADEService() {
    return usuariosSADEService;
  }

  public List<NroSADEProceso> buscarNumeros(int anio, List<Integer> numerosTransitorios) {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarNumeros(anio={}, numerosTransitorios={}) - start", anio,
          numerosTransitorios);
    }

    List<NroSADEProceso> nrosEncontrados = new ArrayList<NroSADEProceso>();
    List<Integer> buscarNros = new ArrayList<Integer>();

    List<ExpedienteElectronico> entidades = expedienteElectronicoRepository
        .findByAnioAndNumeroIn(anio, numerosTransitorios);
    for (ExpedienteElectronico expedienteElectronico : entidades) {
      buscarNros.add(expedienteElectronico.getNumero());
    }

    for (Integer numeroTransitorio : numerosTransitorios) {

      String estado;

      if (buscarNros.contains(numeroTransitorio)) {
        estado = com.egoveris.numerador.util.Constantes.ESTADO_USADO;
      } else {
        estado = com.egoveris.numerador.util.Constantes.ESTADO_BAJA;
      }
      nrosEncontrados.add(new NroSADEProceso(anio, numeroTransitorio, estado));
    }

    if (logger.isDebugEnabled()) {
      logger.debug("buscarNumeros(int, List<Integer>) - end - return value={}", nrosEncontrados);
    }
    return nrosEncontrados;

  }
}
