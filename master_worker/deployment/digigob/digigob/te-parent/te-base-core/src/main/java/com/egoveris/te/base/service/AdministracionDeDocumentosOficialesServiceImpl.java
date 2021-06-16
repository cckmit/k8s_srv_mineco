/**
 *
 */
package com.egoveris.te.base.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jws.WebParam;

import org.apache.commons.collections.CollectionUtils;
import org.dozer.DozerBeanMapper;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.commons.databaseconfiguration.propiedades.AppProperty;
import com.egoveris.deo.ws.service.IExternalConsultaDocumentoService;
import com.egoveris.shared.map.ListMapper;
import com.egoveris.te.base.exception.SinPersistirException;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.TrataTipoDocumentoDTO;
import com.egoveris.te.base.model.trata.Trata;
import com.egoveris.te.base.model.trata.TrataTipoDocumento;
import com.egoveris.te.base.repository.trata.TrataTipoDocumentoRepository;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.service.iface.IAccesoWebDavService;
import com.egoveris.te.base.service.iface.IAdministracionDeDocumentosOficialesService;
import com.egoveris.te.base.util.BusinessFormatHelper;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.NumeroEspecial2NumeroDocumentoTransformer;
import com.egoveris.te.model.exception.ExpedienteNoDisponibleException;
import com.egoveris.te.model.exception.ParametroIncorrectoException;
import com.egoveris.te.model.exception.ProcesoFallidoException;
// TODO COmentado lo de VUC ExpedienteInexistenteException
//import com.egoveris.vuc.external.service.client.service.external.exception.ExpedienteInexistenteException;
import com.egoveris.te.model.model.RequestRelacionDocumentoOficialEE;
import com.egoveris.te.model.model.VinculacionDefinitivaDeDocsRequest;

@Service
@Transactional
public class AdministracionDeDocumentosOficialesServiceImpl extends ExternalServiceAbstract
    implements IAdministracionDeDocumentosOficialesService {
  private transient Logger logger = LoggerFactory
      .getLogger(AdministracionDeDocumentosOficialesServiceImpl.class);
  private DozerBeanMapper mapper = new DozerBeanMapper();
  
  public static String MEMORANDUM = "ME";
  public static String NOTA = "NO";
  
  @Autowired
  private DocumentoManagerService documentoManagerService;
  @Autowired
  private ExpedienteElectronicoService expedienteElectronicoService;
  @Autowired
  @Qualifier("consultaDocumento3Service")
  private IExternalConsultaDocumentoService consultaDocumentoService;
  @Autowired
  private ServicioAdministracion servicioAdministracion;
  @Autowired
  private DocumentoGedoService documentoGedoService;
  @Autowired
  protected IAccesoWebDavService visualizaDocumentoService;
  @Autowired
  private AppProperty appProperty;
  private List<TrataTipoDocumentoDTO> tiposDocumentosGEDOBD;
  @Autowired
  private TrataTipoDocumentoRepository trataTipoDocumentoRepository;

  public static String VINCULARDOCUMENTOSOFICIALES = "vincularDocumentosOficiales";
  public static String VINCULARDOCUMENTOSOFICIALESFC = "vincularDocumentosOficialesConTransaccionFC";

  private void validacionesParaAdjuntarDocumentosOficialesAEE(String sistemaUsuario,
      String usuario, ExpedienteElectronicoDTO expediente, List<String> listaDocumentos)
      throws ParametroIncorrectoException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "validacionesParaAdjuntarDocumentosOficialesAEE(sistemaUsuario={}, usuario={}, expediente={}, listaDocumentos={}) - start",
          sistemaUsuario, usuario, expediente, listaDocumentos);
    }

    if (expediente.getEstado().equals(ConstantesWeb.ESTADO_GUARDA_TEMPORAL)) {
      throw new ParametroIncorrectoException(
          "No se pueden adjuntar documentos a un EE que se encuentra en estado 'Guarda Temporal'",
          null);
    }

    validacionListaDocumentos(listaDocumentos);

    if (logger.isDebugEnabled()) {
      logger.debug(
          "validacionesParaAdjuntarDocumentosOficialesAEE(String, String, ExpedienteElectronico, List<String>) - end");
    }
  }

  private void validacionListaDocumentos(List<String> listaDocumentos)
      throws ParametroIncorrectoException {
    if (logger.isDebugEnabled()) {
      logger.debug("validacionListaDocumentos(listaDocumentos={}) - start", listaDocumentos);
    }

    if (listaDocumentos == null || listaDocumentos.isEmpty()) {
      throw new ParametroIncorrectoException("La lista de documentos a adjuntar es nula", null);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("validacionListaDocumentos(List<String>) - end");
    }
  }

  private void vincularDocumentosAEE(ExpedienteElectronicoDTO expediente,
      List<String> listaDocumentos, String usuarioAsociador,
      boolean esGuardaTemporalOSolciudArchivo)
      throws ParametroIncorrectoException, ProcesoFallidoException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "vincularDocumentosAEE(expediente={}, listaDocumentos={}, usuarioAsociador={}, esGuardaTemporalOSolciudArchivo={}) - start",
          expediente, listaDocumentos, usuarioAsociador, esGuardaTemporalOSolciudArchivo);
    }

    // Valido si el usuario esta de licencia

    String usuarioApoderado = null;
    if (usuariosSADEService.licenciaActiva(usuarioAsociador)) {
      usuarioApoderado = this.validarLicencia(usuarioAsociador.toUpperCase());
    }
    if (usuarioApoderado != null) {
      throw new ParametroIncorrectoException(
          "El usuario seleccionado no puede vincular documentos ya que está de licencia.", null);
    }

    for (String codigoDocumento : listaDocumentos) {

      DocumentoDTO documentoGEDO = documentoManagerService.buscarDocumentoEstandar(codigoDocumento);
      if (documentoGEDO != null) {
        if (!esGuardaTemporalOSolciudArchivo && documentoGEDO.getMotivoDepuracion() != null) {
          buscarDocumentoEnWebDav(documentoGEDO);
        }

        if (!estaHabilitado(documentoGEDO.getTipoDocAcronimo(), expediente)) {
          throw new ParametroIncorrectoException(
              "Tipo de Documento no habilitado para la trata. Verifique los datos ingresados.",
              null);
        }

        List<String> arrayDocumentos = new ArrayList<>();

        if (expediente.getDocumentos().contains(documentoGEDO)) {
          arrayDocumentos.add(codigoDocumento);
          try {
            this.desvincularDocumentosOficiales(expediente.getSistemaCreador(),
                expediente.getUsuarioCreador(), expediente.getCodigoCaratula(), arrayDocumentos);
            arrayDocumentos.remove(codigoDocumento);
          } catch (Exception e) {
            if (logger.isErrorEnabled()) {
              logger.error(
                  "Hubo un error en el método desvincularDocumentosOficiales - Sistema Usuario: "
                      + expediente.getSistemaCreador() + " - usuario: "
                      + expediente.getUsuarioCreador() + " - codigoEE: "
                      + expediente.getCodigoCaratula() + " Doc: " + codigoDocumento
                      + " Vinculado anteriormente ",
                  e);
            }
          }

        }

        ExpedienteElectronicoDTO expedienteElectronicoaux = expediente = obtenerExpedienteElectronico(
            expediente.getCodigoCaratula());

        if (!arrayDocumentos.contains(codigoDocumento)) {
          documentoGEDO.setFechaAsociacion(new Date());
          documentoGEDO.setUsuarioAsociador(usuarioAsociador);

          if (!expedienteElectronicoaux.getEstado().equals(ConstantesWeb.ESTADO_GUARDA_TEMPORAL)
              && !expedienteElectronicoaux.getEstado()
                  .equals(ConstantesWeb.ESTADO_SOLICITUD_ARCHIVO)) {
            documentoGEDO.setIdTask(obtenerWorkingTask(expedienteElectronicoaux).getExecutionId());
          }
          documentoGEDO.setDefinitivo(false);
          if ((expedienteElectronicoaux.getEsCabeceraTC() != null)
              && expedienteElectronicoaux.getEsCabeceraTC()) {
            documentoGEDO.setIdExpCabeceraTC(expedienteElectronicoaux.getId());
          }
          expedienteElectronicoaux.getDocumentos().add(documentoGEDO);
          expedienteElectronicoaux.setFechaModificacion(new Date());
          try {
            expedienteElectronicoService.modificarExpedienteElectronico(expedienteElectronicoaux);
          } catch (HibernateException he) {
            logger.error(he.toString());
            throw new SinPersistirException("No se pudo persistir el documento: " + he, null);
          } catch (Exception he) {
            logger.error(he.toString());
            throw new SinPersistirException("No se pudo persistir el documento: " + he, null);
          }
        } else {
          logger.info("vincularDocumentosOficiales - Sistema Usuario: "
              + expedienteElectronicoaux.getSistemaCreador() + " - usuario: "
              + expedienteElectronicoaux.getUsuarioCreador() + " - codigoEE: "
              + expedienteElectronicoaux.getCodigoCaratula() + " Doc: " + codigoDocumento
              + " Vinculado anteriormente ");

          throw new ProcesoFallidoException(
              "El documento ya se encuentra vinculado al expediente.", null);
        }
      } else {
        throw new ProcesoFallidoException("No fue posible encontrar el documento", null);
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "vincularDocumentosAEE(ExpedienteElectronico, List<String>, String, boolean) - end");
    }
  }

  private void buscarDocumentoEnWebDav(DocumentoDTO documentoGEDO) throws ProcesoFallidoException {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarDocumentoEnWebDav(documentoGEDO={}) - start", documentoGEDO);
    }

    String numeroSadeConEspacio = documentoGEDO.getNumeroSade();
    String pathDocumento = "SADE";
    String fileName;
    String nombre = documentoGEDO.getNumeroSade() + ".pdf";
    String pathGedo = BusinessFormatHelper.nombreCarpetaWebDavGedo(numeroSadeConEspacio);
    fileName = pathDocumento + "/" + pathGedo + "/" + nombre;

    if (logger.isDebugEnabled()) {
      logger.debug("buscarDocumentoEnWebDav(Documento) - end");
    }
  }

  @SuppressWarnings("static-access")
  protected void vincularDocumentosOficialesGenerico(String sistemaUsuario, String usuario,
      String codigoEE, List<String> listaDocumentos, boolean esGuardaTemporalOSolciudArchivo)
      throws ProcesoFallidoException,
      /* ExpedienteInexistenteException, */ ParametroIncorrectoException,
      ExpedienteNoDisponibleException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "vincularDocumentosOficialesGenerico(sistemaUsuario={}, usuario={}, codigoEE={}, listaDocumentos={}, esGuardaTemporalOSolciudArchivo={}) - start",
          sistemaUsuario, usuario, codigoEE, listaDocumentos, esGuardaTemporalOSolciudArchivo);
    }

    ExpedienteElectronicoDTO expedienteElectronico;
    if (!esGuardaTemporalOSolciudArchivo) {
      expedienteElectronico = servicioAdministracion
          .crearExpedienteElectronicoAsociados(codigoEE, sistemaUsuario, usuario, false);
      validacionesParaAdjuntarDocumentosOficialesAEE(sistemaUsuario, usuario,
          expedienteElectronico, listaDocumentos);
      vincularDocumentosAEE(expedienteElectronico, listaDocumentos, usuario,
          esGuardaTemporalOSolciudArchivo);
    } else {
      expedienteElectronico = servicioAdministracion
          .crearEESolicitudArchivoYGTemporalParaAdjuntarDoc(codigoEE, sistemaUsuario, usuario);
      validacionListaDocumentos(listaDocumentos);
      vincularDocumentosAEE(expedienteElectronico, listaDocumentos, usuario,
          esGuardaTemporalOSolciudArchivo);
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "vincularDocumentosOficialesGenerico(String, String, String, List<String>, boolean) - end");
    }
  }

  @Transactional
  public synchronized void vincularDocumentosOficiales(String sistemaUsuario, String usuario,
      String codigoEE, List<String> listaDocumentos) throws ProcesoFallidoException,
      /* ExpedienteInexistenteException, */ ParametroIncorrectoException,
      ExpedienteNoDisponibleException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "vincularDocumentosOficiales(sistemaUsuario={}, usuario={}, codigoEE={}, listaDocumentos={}) - start",
          sistemaUsuario, usuario, codigoEE, listaDocumentos);
    }

    vincularDocumentosOficialesGenerico(sistemaUsuario, usuario, codigoEE, listaDocumentos, false);

    if (logger.isDebugEnabled()) {
      logger.debug("vincularDocumentosOficiales(String, String, String, List<String>) - end");
    }
  }

  public IAccesoWebDavService getVisualizaDocumentoService() {
    return visualizaDocumentoService;
  }

  public void setVisualizaDocumentoService(IAccesoWebDavService visualizaDocumentoService) {
    this.visualizaDocumentoService = visualizaDocumentoService;
  }

  @Transactional
  public synchronized void vincularDocumentosOficialesAEEGuardaTemporalOSolicitudArchivo(
      String sistemaUsuario, String usuario, String codigoEE, List<String> listaDocumentos)
      throws ProcesoFallidoException,
      /* ExpedienteInexistenteException, */ ParametroIncorrectoException,
      ExpedienteNoDisponibleException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "vincularDocumentosOficialesAEEGuardaTemporalOSolicitudArchivo(sistemaUsuario={}, usuario={}, codigoEE={}, listaDocumentos={}) - start",
          sistemaUsuario, usuario, codigoEE, listaDocumentos);
    }

    vincularDocumentosOficialesGenerico(sistemaUsuario, usuario, codigoEE, listaDocumentos, true);

    if (logger.isDebugEnabled()) {
      logger.debug(
          "vincularDocumentosOficialesAEEGuardaTemporalOSolicitudArchivo(String, String, String, List<String>) - end");
    }
  }

  @SuppressWarnings("static-access")
  @Transactional
  public synchronized void desvincularDocumentosOficiales(String sistemaUsuario, String usuario,
      String codigoEE, List<String> listaDocumentos) throws ProcesoFallidoException,
      /* ExpedienteInexistenteException, */ ParametroIncorrectoException,
      ExpedienteNoDisponibleException {
    logger.debug("desvincularDocumentosOficiales - Sistema Usuario: " + sistemaUsuario
        + " - usuario: " + usuario + " - codigoEE: " + codigoEE);

    ExpedienteElectronicoDTO expedienteElectronico;

    if (listaDocumentos == null) {
      throw new ParametroIncorrectoException("La lista de documentos a desvincular, es nula.",
          null);
    }

    try {

      // ************************************************************************
      // ** TODO: REFACTOR REFACTOR (ISSUE) BISADE-1805 Excepciones de negocio
      // se muestran en el log de EE (Martes 15-Marzo-2014) grinberg.
      // ** Desc: Validación Documentacion de EE (Business Service)
      // ** En la clase ExternServiceAbstract en vez verificarAsignación en la
      // ejecución del servicio,
      // ** se arma con la referencia ya validada desde el principio del CU.
      // ** Asi al invocar la llama desde un factory de vÃ­nculacion, ya está
      // referenciada
      // ** la asociación y no se permite su ejecución sino antes de a ver
      // llegado a la invocación del servicio.
      // ** Caso de Uso afectado: Vinculacion de Documentacion al Expediente
      // Electrónico
      // ************************************************************************
      expedienteElectronico = servicioAdministracion
          .crearExpedienteElectronicoAsociados(codigoEE, sistemaUsuario, usuario, false);

      for (String codigoDocumento : listaDocumentos) {
        List<String> listDesgloseCodigoDocumento;
        listDesgloseCodigoDocumento = BusinessFormatHelper
            .obtenerDesgloseCodigoDocumento(codigoDocumento);

        DocumentoDTO documentoGEDO = documentoManagerService.buscarDocumentoEstandar(
            listDesgloseCodigoDocumento.get(0),
            Integer.valueOf(listDesgloseCodigoDocumento.get(1)),
            Integer.valueOf(listDesgloseCodigoDocumento.get(2)),
            listDesgloseCodigoDocumento.get(4));

        // Validación para ver si el doc es definitivo y ver si hay que
        // desvincularlo o no
        expedienteElectronico.removeDoc(documentoGEDO);
      }

      expedienteElectronicoService.modificarExpedienteElectronico(expedienteElectronico);

    } catch (Exception exception) {
      logger.error("desvincularDocumentosOficiales(String, String, String, List<String>)",
          exception);

      throw new ProcesoFallidoException(exception.getMessage(), null);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("desvincularDocumentosOficiales(String, String, String, List<String>) - end");
    }
  }

  @Override
  public synchronized void eliminarDocumentosNoDefinitivos(String sistemaUsuario, String usuario,
      String codigoEE) throws ProcesoFallidoException,
      /* ExpedienteInexistenteException, */ ParametroIncorrectoException,
      ExpedienteNoDisponibleException {
    logger.debug("eliminarDocumentosNoDefinitivos - Sistema Usuario: " + sistemaUsuario
        + " - usuario: " + usuario + " - codigoEE: " + codigoEE);

    // ************************************************************************
    // ** TODO: REFACTOR REFACTOR (ISSUE) BISADE-2180 Excepciones de negocio se
    // muestran en el log de EE (Martes 15-Marzo-2014) grinberg.
    // ** Desc: Validación Administracion de EE (Business Service)
    // ** En la clase ExternServiceAbstract en vez verificarAsignación en la
    // ejecución del servicio,
    // ** se arma con la referencia ya validada desde el principio del CU.
    // ** Asi al invocar la llama desde un factory de vÃ­nculacion, ya está
    // referenciada
    // ** la asociación y no se permite su ejecución sino antes de a ver llegado
    // a la invocación del servicio.
    // ** Caso de Uso afectado: Documentos Definitivos
    // ************************************************************************
    @SuppressWarnings("static-access")
    ExpedienteElectronicoDTO expedienteElectronico = servicioAdministracion
        .crearExpedienteElectronicoConDocumentos(codigoEE, sistemaUsuario, usuario, false);
    expedienteElectronico.removeDocsNoDefinitivos();
    expedienteElectronicoService.modificarExpedienteElectronico(expedienteElectronico);

    if (logger.isDebugEnabled()) {
      logger.debug("eliminarDocumentosNoDefinitivos(String, String, String) - end");
    }
  }

  /**
   *
   * Se realiza la vinculacion de un <code>DocumentoOficial</code> que no esta
   * difinitivo, al <code>ExpedienteElectronico</code>. Las acciones de la
   * operacion son,
   * 
   * @param <code>RequestRelacionDocumentoOficialEE</code>
   *          request, los parametros son,
   * @throws <code>ProcesoFallidoException</code>,
   *           <code>ExpedienteInexistenteException</code>,
   *           <code>ParametroIncorrectoException</code>,
   *           <code>ExpedienteNoDisponibleException</code>
   */
  @SuppressWarnings("unchecked")
  @Transactional
  public synchronized void vincularDocumentosOficialesNumeroEspecial(
      RequestRelacionDocumentoOficialEE request) throws ProcesoFallidoException,
      /* ExpedienteInexistenteException, */ ParametroIncorrectoException,
      ExpedienteNoDisponibleException {
    logger.debug("vincularDocumentosOficialesNumeroEspecial - Sistema Usuario: "
        + request.getSistemaUsuario() + " - usuario: " + request.getUsuario() + " - numEE: "
        + request.getNumeroExpedienteElectronico());

    NumeroEspecial2NumeroDocumentoTransformer transformer = new NumeroEspecial2NumeroDocumentoTransformer(
        request.getUsuario(), consultaDocumentoService);

    try {
      if (request.getDocumentosOficiales().size() == 0) {
        throw new ParametroIncorrectoException(
            "Error falta ingresar un numero especial para vincularse.", null);
      }

      this.vincularDocumentosOficiales(request.getSistemaUsuario(), request.getUsuario(),
          request.getNumeroExpedienteElectronico(),
          ((List<String>) CollectionUtils.collect(request.getDocumentosOficiales(), transformer)));
    } catch (Exception e) {
      logger.error("vincularDocumentosOficialesNumeroEspecial(RequestRelacionDocumentoOficialEE)",
          e);

      if (transformer.getMsgError() != null) {
        throw new ProcesoFallidoException(transformer.getMsgError(), null);
      } else {
        throw new ProcesoFallidoException(e.getMessage(), null);
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "vincularDocumentosOficialesNumeroEspecial(RequestRelacionDocumentoOficialEE) - end");
    }
    return;
  }

  /**
   *
   * Se realiza la desvinculacion de un <code>DocumentoOficial</code> que no
   * esta difinitivo, al <code>ExpedienteElectronico</code>. Las acciones de la
   * operacion que se realiza es
   * 
   * @param <code>RequestRelacionDocumentoOficialEE</code>
   *          request, los parametros que se usan son,
   *          <p>
   * @throws <code>ProcesoFallidoException</code>,
   *           <code>ExpedienteInexistenteException</code>,
   *           <code>ParametroIncorrectoException</code>,
   *           <code>ExpedienteNoDisponibleException</code>
   */
  @SuppressWarnings("unchecked")
  @Transactional
  public synchronized void desvincularDocumentosOficialesNumeroEspecial(
      RequestRelacionDocumentoOficialEE request) throws ProcesoFallidoException,
      /* ExpedienteInexistenteException, */ ParametroIncorrectoException,
      ExpedienteNoDisponibleException {
    if (logger.isDebugEnabled()) {
      logger.debug("desvincularDocumentosOficialesNumeroEspecial(request={}) - start", request);
    }

    NumeroEspecial2NumeroDocumentoTransformer transformer = new NumeroEspecial2NumeroDocumentoTransformer(
        request.getUsuario(), consultaDocumentoService);

    try {
      if (request.getDocumentosOficiales().size() == 0) {
        throw new ParametroIncorrectoException(
            "Error falta ingresar un numero especial para desvincularse.", null);
      }

      this.desvincularDocumentosOficiales(request.getSistemaUsuario(), request.getUsuario(),
          request.getNumeroExpedienteElectronico(),
          ((List<String>) CollectionUtils.collect(request.getDocumentosOficiales(), transformer)));
    } catch (Exception e) {
      logger.error(
          "desvincularDocumentosOficialesNumeroEspecial(RequestRelacionDocumentoOficialEE)", e);

      if (transformer.getMsgError() != null) {
        throw new ProcesoFallidoException(transformer.getMsgError(), null);
      } else {
        throw new ProcesoFallidoException(e.getMessage(), null);
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "desvincularDocumentosOficialesNumeroEspecial(RequestRelacionDocumentoOficialEE) - end");
    }
    return;
  }

  @Override
  public synchronized void vincularDocumentosOficialesConTransaccionFC(String sistemaUsuario,
      String usuario, String codigoEE, String codigoDocumento, Long idTransaccionFC)
      throws ProcesoFallidoException, /* ExpedienteInexistenteException, */
      ParametroIncorrectoException, ExpedienteNoDisponibleException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "vincularDocumentosOficialesConTransaccionFC(sistemaUsuario={}, usuario={}, codigoEE={}, codigoDocumento={}, idTransaccionFC={}) - start",
          sistemaUsuario, usuario, codigoEE, codigoDocumento, idTransaccionFC);
    }

    if (codigoDocumento == null) {
      throw new ParametroIncorrectoException("El codigo de documento es nulo.", null);
    }

    if (idTransaccionFC == null) {
      throw new ParametroIncorrectoException("El id de transacción es nulo.", null);
    }

    List<String> listaDocumentos = new ArrayList<>();
    listaDocumentos.add(codigoDocumento);
    this.vincularDocumentosOficialesGenerico(sistemaUsuario, usuario, codigoEE, listaDocumentos,
        idTransaccionFC);

    if (logger.isDebugEnabled()) {
      logger.debug(
          "vincularDocumentosOficialesConTransaccionFC(String, String, String, String, Integer) - end");
    }
  }

  @SuppressWarnings("static-access")
  private void vincularDocumentosOficialesGenerico(String sistemaUsuario, String usuario,
      String codigoEE, List<String> listaDocumentos, Long idTransaccionFC)
      throws ProcesoFallidoException, /* ExpedienteInexistenteException, */
      ParametroIncorrectoException, ExpedienteNoDisponibleException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "vincularDocumentosOficialesGenerico(sistemaUsuario={}, usuario={}, codigoEE={}, listaDocumentos={}, idTransaccionFC={}) - start",
          sistemaUsuario, usuario, codigoEE, listaDocumentos, idTransaccionFC);
    }

    if (idTransaccionFC != null) {
      logger.debug(VINCULARDOCUMENTOSOFICIALES + " - Sistema Usuario: " + sistemaUsuario
          + " - usuario: " + usuario + " - codigoEE: " + codigoEE);
    } else {
      logger.debug(VINCULARDOCUMENTOSOFICIALESFC + " - Sistema Usuario: " + sistemaUsuario
          + " - usuario: " + usuario + " - codigoEE: " + codigoEE);
    }

    ExpedienteElectronicoDTO expedienteElectronico;

    if (listaDocumentos == null) {
      throw new ParametroIncorrectoException("La lista de documentos a vincular, es nula.", null);
    }

    expedienteElectronico = servicioAdministracion
        .crearExpedienteElectronicoAsociados(codigoEE, sistemaUsuario, usuario, false);

    for (String codigoDocumento : listaDocumentos) {
      List<String> listDesgloseCodigoDocumento;
      listDesgloseCodigoDocumento = BusinessFormatHelper
          .obtenerDesgloseCodigoDocumento(codigoDocumento);

      DocumentoDTO documentoGEDO = documentoManagerService.buscarDocumentoEstandar(
          listDesgloseCodigoDocumento.get(0), Integer.valueOf(listDesgloseCodigoDocumento.get(1)),
          Integer.valueOf(listDesgloseCodigoDocumento.get(2)), listDesgloseCodigoDocumento.get(4));

      if (documentoGEDO != null) {
        buscarDocumentoEnWebDav(documentoGEDO);

        if (!estaHabilitado(documentoGEDO.getTipoDocAcronimo(), expedienteElectronico)) {
          throw new ParametroIncorrectoException(
              "Tipo de Documento no habilitado para la trata. Verifique los datos ingresados.",
              null);
        }

        List<String> arrayDocumentos = new ArrayList<>();
        if (expedienteElectronico.getDocumentos().contains(documentoGEDO)) {
          arrayDocumentos.add(codigoDocumento);

          try {
            this.desvincularDocumentosOficiales(sistemaUsuario, usuario, codigoEE,
                arrayDocumentos);
          } catch (Exception e) {
            if (logger.isErrorEnabled()) {
              logger.error(
                  "Hubo un error en el mÃ©todo desvincularDocumentosOficiales - Sistema Usuario: "
                      + sistemaUsuario + " - usuario: " + usuario + " - codigoEE: " + codigoEE
                      + " Doc: " + codigoDocumento + " Vinculado anteriormente ",
                  e);
            }
          }

          arrayDocumentos.remove(codigoDocumento);
        }

        ExpedienteElectronicoDTO expedienteElectronicoaux = expedienteElectronico = obtenerExpedienteElectronico(
            codigoEE);

        if (!arrayDocumentos.contains(codigoDocumento)) {
          documentoGEDO.setFechaAsociacion(new Date());
          documentoGEDO.setUsuarioAsociador(usuario);
          documentoGEDO.setIdTransaccionFC(idTransaccionFC);
          documentoGEDO.setIdTask(obtenerWorkingTask(expedienteElectronico).getExecutionId());
          documentoGEDO.setDefinitivo(false); // se hace definitivo al
                                              // efectuarse el pase
          expedienteElectronicoaux.getDocumentos().add(documentoGEDO);
          expedienteElectronicoService.modificarExpedienteElectronico(expedienteElectronicoaux);
        } else {
          if (idTransaccionFC != null) {
            logger.info(VINCULARDOCUMENTOSOFICIALES + " - Sistema Usuario: " + sistemaUsuario
                + " - usuario: " + usuario + " - codigoEE: " + codigoEE + " Doc: "
                + codigoDocumento + " Vinculado anteriormente ");
          } else {
            logger.info(VINCULARDOCUMENTOSOFICIALESFC + " - Sistema Usuario: " + sistemaUsuario
                + " - usuario: " + usuario + " - codigoEE: " + codigoEE + " Doc: "
                + codigoDocumento + " Vinculado anteriormente ");
          }
          throw new ProcesoFallidoException(
              "El documento ya se encuentra vinculado al expediente.", null);
        }
      } else {
        throw new ParametroIncorrectoException("No fue posible encontrar el documento.", null);
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "vincularDocumentosOficialesGenerico(String, String, String, List<String>, Integer) - end");
    }
  }

  public AppProperty getAppProperty() {
    return appProperty;
  }

  public void setAppProperty(AppProperty appProperty) {
    this.appProperty = appProperty;
  }

  @Override
  @Transactional
  public synchronized void hacerDefinitivosDocsDeEE(
      @WebParam(name = "request") VinculacionDefinitivaDeDocsRequest request)
      throws ProcesoFallidoException, /* ExpedienteInexistenteException, */
      ParametroIncorrectoException, ExpedienteNoDisponibleException {
    if (logger.isDebugEnabled()) {
      logger.debug("hacerDefinitivosDocsDeEE(request={}) - start", request);
    }

    ExpedienteElectronicoDTO ee = servicioAdministracion
        .obtenerExpedienteParaHacerDocsDefinitivos(request.getCodigoEE(),
            request.getSistemaUsuario(), request.getUsuario());
    ee.hacerDocsDefinitivos();
    expedienteElectronicoService.modificarExpedienteElectronico(ee);

    if (logger.isDebugEnabled()) {
      logger.debug("hacerDefinitivosDocsDeEE(VinculacionDefinitivaDeDocsRequest) - end");
    }
  }

  private String validarLicencia(String username) {
    if (logger.isDebugEnabled()) {
      logger.debug("validarLicencia(username={}) - start", username);
    }

    String returnString = this.usuariosSADEService.getDatosUsuario(username).getApoderado();
    if (logger.isDebugEnabled()) {
      logger.debug("validarLicencia(String) - end - return value={}", returnString);
    }
    return returnString;
  }

  @SuppressWarnings("unchecked")
  private boolean estaHabilitado(String acronimo, ExpedienteElectronicoDTO expediente) {
    if (logger.isDebugEnabled()) {
      logger.debug("estaHabilitado(acronimo={}, expediente={}) - start", acronimo, expediente);
    }

    this.tiposDocumentosGEDOBD = new ArrayList<>();
    
    List<TrataTipoDocumento> resultado = trataTipoDocumentoRepository.findByTrata(mapper.map(expediente.getTrata(), Trata.class));
    if (resultado != null && !resultado.isEmpty()) {
      this.tiposDocumentosGEDOBD = ListMapper.mapList(resultado, mapper, TrataTipoDocumentoDTO.class);
    }

    if ((this.tiposDocumentosGEDOBD.size() > 0) && !this.tiposDocumentosGEDOBD.get(0)
        .getAcronimoGEDO().trim().equals(ConstantesWeb.SELECCIONAR_TODOS)) {
      for (TrataTipoDocumentoDTO documentoBD : this.tiposDocumentosGEDOBD) {
        if (acronimo.trim().equals(documentoBD.getAcronimoGEDO().trim())) {
          if (logger.isDebugEnabled()) {
            logger.debug("estaHabilitado(String, ExpedienteElectronico) - end - return value={}",
                true);
          }
          return true;
        }
      }
    } else {
      if ((this.tiposDocumentosGEDOBD.size() > 0) && this.tiposDocumentosGEDOBD.get(0)
          .getAcronimoGEDO().trim().equals(ConstantesWeb.SELECCIONAR_TODOS)) {
        if (logger.isDebugEnabled()) {
          logger.debug("estaHabilitado(String, ExpedienteElectronico) - end - return value={}",
              true);
        }
        return true;
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("estaHabilitado(String, ExpedienteElectronico) - end - return value={}", false);
    }
    return false;
  }
}
