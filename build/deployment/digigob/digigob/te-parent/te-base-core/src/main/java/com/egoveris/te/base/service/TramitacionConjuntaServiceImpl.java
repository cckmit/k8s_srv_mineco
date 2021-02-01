package com.egoveris.te.base.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.dozer.DozerBeanMapper;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.TaskQuery;
import org.jbpm.api.task.Participation;
import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.deo.ws.service.IExternalActualizarVisualizacionService;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.te.base.dao.ExpedienteElectronicoDAO;
import com.egoveris.te.base.exception.AsignacionException;
import com.egoveris.te.base.exception.ValidacionDeTramitacionConjuntaException;
import com.egoveris.te.base.exception.external.TeRuntimeException;
import com.egoveris.te.base.model.ArchivoDeTrabajoDTO;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.ExpedienteAsociadoEntDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.ReparticionParticipanteDTO;
import com.egoveris.te.base.model.TrataReparticionDTO;
import com.egoveris.te.base.model.expediente.ExpedienteAsociado;
import com.egoveris.te.base.repository.TramitacionConjuntaRepository;
import com.egoveris.te.base.service.expediente.ExpedienteAsociadoService;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.service.iface.IAccesoWebDavService;
import com.egoveris.te.base.util.BusinessFormatHelper;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.model.model.ExpedienteAsociadoDTO;

@Service
@Transactional
public class TramitacionConjuntaServiceImpl implements TramitacionConjuntaService {
  @Autowired
  private ExpedienteElectronicoService expedienteElectronicoService;
  @Autowired
  private TramitacionConjuntaRepository tramitacionConjuntaDAO;
  @Autowired
  private ExpedienteElectronicoDAO expedienteElectronicoDAO;
  @Autowired
  private UsuariosSADEService usuariosSADEService;
  @Autowired
  private ArchivoDeTrabajoService archivoDeTrabajoService;
  @Autowired
  private DocumentoGedoService documentoGedoService;
  @Autowired
  private IAccesoWebDavService accesoWebDavService;
  @Autowired
  private IExternalActualizarVisualizacionService externalActualizarVisualizacionService;

  private DozerBeanMapper mapper = new DozerBeanMapper();

  private final static Logger logger = LoggerFactory
      .getLogger(TramitacionConjuntaServiceImpl.class);
  @Autowired
  private ProcessEngine processEngine;
  @Autowired
  private ExpedienteAsociadoService expedienteAsociadoService;

  public ExpedienteAsociadoEntDTO obtenerExpedienteTramitacionConjunta(
      ExpedienteElectronicoDTO expedienteElectronico, String loggedUsername, String estado) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "obtenerExpedienteTramitacionConjunta(expedienteElectronico={}, loggedUsername={}, estado={}) - start",
          expedienteElectronico, loggedUsername, estado);
    }

    ExpedienteAsociadoEntDTO expedienteTramitacionConjunta = null;

    // Obtengo la task para saber si esta asignada al usuario que quiere
    // iniciar la tramitacion Conjunta.
    TaskQuery taskQuery = this.processEngine.getTaskService().createTaskQuery()
        .executionId(expedienteElectronico.getIdWorkflow());
    Task task = taskQuery.uniqueResult();

    if (task.getActivityName().equals("Tramitacion")
        || task.getActivityName().equals("Ejecucion")) {

      if (estado.equals(task.getActivityName())) {

        if (task.getAssignee().equals(loggedUsername)) {

          // HAY QUE VALIDAR QUE NO SE ENCUENTRE NINGUN RECURSO
          // PENDIENTE EN EL EXPEDIENTE QUE SE QUIERE TRAMITAR EN CONJUNTO
          validarExpedientesAsociadoEnExpedienteAFusionar(expedienteElectronico);

          validarArchivosDeTrabajoEnExpedienteAFusionar(expedienteElectronico);

          validarDocumentosEnExpedienteAFusionar(expedienteElectronico);

          expedienteTramitacionConjunta = crearExpedienteAsociado(expedienteElectronico,
              loggedUsername);

        }
      } else {
        throw new TeRuntimeException(
            "No puede adjuntar el expediente. El estado no es el mismo que el estado del expediente cabecera.",
            null);
      }

    } else {

      throw new TeRuntimeException(
          "No puede adjuntar el expediente. El mismo no se encuentra en Tramitacion o Ejecucion.",
          null);
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "obtenerExpedienteTramitacionConjunta(ExpedienteElectronico, String, String) - end - return value={}",
          expedienteTramitacionConjunta);
    }
    return expedienteTramitacionConjunta;
  }

  /**
   * Valida los Documentos del Expediente a Fusionar sean definitivos
   * 
   * @param expedienteElectronico
   * @throws ValidacionDeTramitacionConjuntaException
   */
  private void validarDocumentosEnExpedienteAFusionar(
      ExpedienteElectronicoDTO expedienteElectronico)
      throws ValidacionDeTramitacionConjuntaException {
    if (logger.isDebugEnabled()) {
      logger.debug("validarDocumentosEnExpedienteAFusionar(expedienteElectronico={}) - start",
          expedienteElectronico);
    }

    if (expedienteElectronico.getDocumentos() != null
        && !expedienteElectronico.getDocumentos().isEmpty()) {

      for (DocumentoDTO documentoEnExpedienteAFusionar : expedienteElectronico.getDocumentos()) {
        if (!documentoEnExpedienteAFusionar.getDefinitivo()) {
          throw new ValidacionDeTramitacionConjuntaException(
              "No puede vincular el expediente. El mismo contiene Documento "
                  + "pendientes de vincular.",
              null);
        }
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("validarDocumentosEnExpedienteAFusionar(ExpedienteElectronico) - end");
    }
  }

  /**
   * Valida que los Archivos de Trabajo del Expediente sean definitivos
   * 
   * @param expedienteElectronico
   * @throws ValidacionDeTramitacionConjuntaException
   */
  private void validarArchivosDeTrabajoEnExpedienteAFusionar(
      ExpedienteElectronicoDTO expedienteElectronico)
      throws ValidacionDeTramitacionConjuntaException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "validarArchivosDeTrabajoEnExpedienteAFusionar(expedienteElectronico={}) - start",
          expedienteElectronico);
    }

    if (expedienteElectronico.getArchivosDeTrabajo() != null
        && !expedienteElectronico.getArchivosDeTrabajo().isEmpty()) {
      for (ArchivoDeTrabajoDTO archivoDeTrabajoEnExpedienteAfusionar : expedienteElectronico
          .getArchivosDeTrabajo()) {
        if (!archivoDeTrabajoEnExpedienteAfusionar.isDefinitivo()) {
          throw new ValidacionDeTramitacionConjuntaException(
              "No puede vincular el expediente. El mismo contiene "
                  + "archivos de trabajo pendientes de añadir.",
              null);
        }
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("validarArchivosDeTrabajoEnExpedienteAFusionar(ExpedienteElectronico) - end");
    }
  }

  /**
   * Valida los Expedientes Asociados del Expediente a Fusionar sean definitivos
   * 
   * @param expedienteElectronico
   * @throws ValidacionDeTramitacionConjuntaException
   */
  private void validarExpedientesAsociadoEnExpedienteAFusionar(
      ExpedienteElectronicoDTO expedienteElectronico)
      throws ValidacionDeTramitacionConjuntaException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "validarExpedientesAsociadoEnExpedienteAFusionar(expedienteElectronico={}) - start",
          expedienteElectronico);
    }

    if (expedienteElectronico.getListaExpedientesAsociados() != null
        && !expedienteElectronico.getListaExpedientesAsociados().isEmpty()) {
      for (ExpedienteAsociadoEntDTO expedienteAsociadoEnExpedienteAFusionar : expedienteElectronico
          .getListaExpedientesAsociados()) {
        if (!expedienteAsociadoEnExpedienteAFusionar.getDefinitivo()) {
          throw new ValidacionDeTramitacionConjuntaException(
              "No puede vincular el expediente. El mismo contiene Expedientes Asociados pendientes de asociar.",
              null);
        }
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("validarExpedientesAsociadoEnExpedienteAFusionar(ExpedienteElectronico) - end");
    }
  }

  /**
   * Devuelve un expediente en tramitacion conjunta
   * 
   * @param expedienteElectronico
   * @param loggedUsername
   * @return
   */
  private ExpedienteAsociadoEntDTO crearExpedienteAsociado(
      ExpedienteElectronicoDTO expedienteElectronico, String loggedUsername) {
    if (logger.isDebugEnabled()) {
      logger.debug("crearExpedienteAsociado(expedienteElectronico={}, loggedUsername={}) - start",
          expedienteElectronico, loggedUsername);
    }

    ExpedienteAsociadoEntDTO expedienteTramitacionConjunta;
    expedienteTramitacionConjunta = new ExpedienteAsociadoEntDTO();

    expedienteTramitacionConjunta.setTipoDocumento(expedienteElectronico.getTipoDocumento());
    expedienteTramitacionConjunta.setAnio(expedienteElectronico.getAnio());
    expedienteTramitacionConjunta.setNumero(expedienteElectronico.getNumero());
    expedienteTramitacionConjunta.setSecuencia(expedienteElectronico.getSecuencia());
    expedienteTramitacionConjunta.setDefinitivo(expedienteElectronico.getDefinitivo());
    expedienteTramitacionConjunta
        .setCodigoReparticionActuacion(expedienteElectronico.getCodigoReparticionActuacion());
    expedienteTramitacionConjunta
        .setCodigoReparticionUsuario(expedienteElectronico.getCodigoReparticionUsuario());
    expedienteTramitacionConjunta.setEsElectronico(expedienteElectronico.getEsElectronico());
    expedienteTramitacionConjunta.setIdCodigoCaratula(expedienteElectronico.getId());
    expedienteTramitacionConjunta.setTrata(expedienteElectronico.getTrata().getCodigoTrata());
    expedienteTramitacionConjunta.setEsExpedienteAsociadoTC(true);
    expedienteTramitacionConjunta.setFechaAsociacion(new Date());
    expedienteTramitacionConjunta.setUsuarioAsociador(loggedUsername);
    expedienteTramitacionConjunta.setIdTask(expedienteElectronico.getIdWorkflow());

    if (logger.isDebugEnabled()) {
      logger.debug(
          "crearExpedienteAsociado(ExpedienteElectronico, String) - end - return value={}",
          expedienteTramitacionConjunta);
    }
    return expedienteTramitacionConjunta;
  }

  public void actulizarDocumentosEnTramitacionConjunta(
      ExpedienteElectronicoDTO expedienteElectronico) {
    if (logger.isDebugEnabled()) {
      logger.debug("actulizarDocumentosEnTramitacionConjunta(expedienteElectronico={}) - start",
          expedienteElectronico);
    }

    for (ExpedienteAsociadoEntDTO expedienteasociado : expedienteElectronico
        .getListaExpedientesAsociados()) {

      if (expedienteasociado.getEsExpedienteAsociadoTC() != null
          && expedienteasociado.getEsExpedienteAsociadoTC()) {
        ExpedienteElectronicoDTO expElectronico = expedienteElectronicoService
            .obtenerExpedienteElectronico(expedienteasociado.getTipoDocumento(),
                expedienteasociado.getAnio(), expedienteasociado.getNumero(),
                expedienteasociado.getCodigoReparticionUsuario());

        for (DocumentoDTO documento : expedienteElectronico.getDocumentos()) {

          if (documento.getIdExpCabeceraTC() != null
              && documento.getIdExpCabeceraTC().equals(expedienteElectronico.getId())) {

            // El documento se agrego despues de haber confirmado la
            // tramitacion Conjunta, pero debo verificar que el
            // documento
            // no esta en el hijo para agregarlo sin duplicar.
            boolean estaEnLista = false;
            for (DocumentoDTO documentoDelExpHijo : expElectronico.getDocumentos()) {
              // Si no esta, entonces si lo agrego a la lista del
              // expediente hijo.
              if (documento.getNombreArchivo().equals(documentoDelExpHijo.getNombreArchivo())) {
                estaEnLista = true;
                break;
              }
            }
            if (!estaEnLista) {

              expElectronico.getDocumentos().add(documento);
              expElectronico.setFechaModificacion(new Date());

            }
          }

        }
        try {
          expedienteElectronicoService.mergeExpedienteElectronico(expElectronico);
        } catch (Exception e) {
          logger.error("actulizarDocumentosEnTramitacionConjunta(ExpedienteElectronico)", e);

          expedienteElectronicoService.modificarExpedienteElectronico(expElectronico);
        }

      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("actulizarDocumentosEnTramitacionConjunta(ExpedienteElectronico) - end");
    }
  }

  public void actualizarArchivosDeTrabajoEnTramitacionConjunta(
      ExpedienteElectronicoDTO expedienteElectronico, String loggedUsername) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "actualizarArchivosDeTrabajoEnTramitacionConjunta(expedienteElectronico={}, loggedUsername={}) - start",
          expedienteElectronico, loggedUsername);
    }

    // Ciclo por cada expediente adjunto al expediente cabecera
    for (ExpedienteAsociadoEntDTO expedienteasociado : expedienteElectronico
        .getListaExpedientesAsociados()) {

      // Tomo aquellos expedientes adjuntos que son adjuntos por
      // tramitacion conjunta
      if (expedienteasociado.getEsExpedienteAsociadoTC() != null
          && expedienteasociado.getEsExpedienteAsociadoTC()) {
        ExpedienteElectronicoDTO expElectronico = expedienteElectronicoService
            .obtenerExpedienteElectronico(expedienteasociado.getTipoDocumento(),
                expedienteasociado.getAnio(), expedienteasociado.getNumero(),
                expedienteasociado.getCodigoReparticionUsuario());

        // Ciclo cada archivo de trabajo del expediente cabecera
        for (ArchivoDeTrabajoDTO archivoDeTrabajo : expedienteElectronico.getArchivosDeTrabajo()) {

          // Pregunto por cada archivo de trabajo si fue subido luego
          // de que se confirmo la tramitacion conjunta
          if (archivoDeTrabajo.getIdExpCabeceraTC() != null
              && archivoDeTrabajo.getIdExpCabeceraTC().equals(expedienteElectronico.getId())) {

            // Busco si el archivo que agregue al cabecera ya estaba
            // en el expediente hijo
            boolean estaEnLista = false;

            for (ArchivoDeTrabajoDTO archivoDeTrabajoEnExpedienteHijo : expElectronico
                .getArchivosDeTrabajo()) {
              // Si no esta, entonces si lo agrego a la lista del
              // expediente hijo.
              if (archivoDeTrabajo.getNombreArchivo()
                  .equals(archivoDeTrabajoEnExpedienteHijo.getNombreArchivo())) {
                estaEnLista = true;

                if (expElectronico.getEsReservado() && loggedUsername != null) {
                  Usuario usuario = usuariosSADEService.getDatosUsuario(loggedUsername);
                  if (usuario != null) {
                    actualizarArchivosDeTrabajoEnHijosDeTC(expElectronico, loggedUsername,
                        usuario);
                  }
                }
                break;
              }
            }
            if (!estaEnLista && archivoDeTrabajo.isDefinitivo()) {

              // Se actualiza la fecha de modificacion
              expElectronico.setFechaModificacion(new Date());

              // Se agregan los ceros al expediente para que tome
              // bien la caratula

              String nombreSpace = expElectronico.getCodigoCaratula();
              // Subo un archivo por cada uno para que cuando se
              // separe la tramitacion conjunta
              // no se borre para todos si borro el archivo.

              //////////////////////////////////////////////////
              String nSpace = BusinessFormatHelper
                  .formarPathWebDavApache(expedienteElectronico.getCodigoCaratula());
              String nombreSpaceWebDav = nSpace.trim();
              String pathDocumentoDeTrabajo = "Documentos_De_Trabajo";
              String path = pathDocumentoDeTrabajo + "/" + nombreSpaceWebDav;
              try {
                archivoDeTrabajo.setDataArchivo(this.accesoWebDavService
                    .obtenerArchivoDeTrabajoWebDav(path, archivoDeTrabajo.getNombreArchivo()));
              } catch (Exception e) {
                logger.error("error al actualizar el archivo de trabajo", e);
              }
              //////////////////////////////////////////////////
              // SE COMENTA LA LINEA DE ABAJO POR REQUERIMIENTO EE - FILENET
              // coloco el archivo en la lista de cada expediente
              // hijo
              ArchivoDeTrabajoDTO auxArchivoDeTrabajo = new ArchivoDeTrabajoDTO();
              auxArchivoDeTrabajo.copiarAtributosParaTC(archivoDeTrabajo);
              // auxArchivoDeTrabajo = archivoDeTrabajo;
              auxArchivoDeTrabajo.setIdTask(expElectronico.getIdWorkflow());
              this.archivoDeTrabajoService.subirArchivoDeTrabajo(auxArchivoDeTrabajo, nombreSpace);

              expElectronico.getArchivosDeTrabajo().add(auxArchivoDeTrabajo);

              if (expElectronico.getEsReservado() && loggedUsername != null) {
                Usuario usuario = usuariosSADEService.getDatosUsuario(loggedUsername);
                if (usuario != null) {
                  actualizarArchivosDeTrabajoEnHijosDeTC(expElectronico, loggedUsername, usuario);
                }
              }

              // Devuelvo el numero oriinal para que guarde bien
              // el expediente.
            }
          }

        }
        // Actualizo cada expediente hijo.
        try {
          expedienteElectronicoService.mergeExpedienteElectronico(expElectronico);
        } catch (Exception e) {
          logger.error(
              "actualizarArchivosDeTrabajoEnTramitacionConjunta(ExpedienteElectronico, String)",
              e);

          expedienteElectronicoService.modificarExpedienteElectronico(expElectronico);
        }

      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "actualizarArchivosDeTrabajoEnTramitacionConjunta(ExpedienteElectronico, String) - end");
    }
  }

  private void actualizarArchivosDeTrabajoEnHijosDeTC(ExpedienteElectronicoDTO ee,
      String loggedUsername, Usuario u) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "actualizarArchivosDeTrabajoEnHijosDeTC(ee={}, loggedUsername={}, u={}) - start", ee,
          loggedUsername, u);
    }

    if (ee.getEsReservado() && loggedUsername != null) {
      Usuario usuario = usuariosSADEService.getDatosUsuario(loggedUsername);
      if (usuario != null) {
        expedienteElectronicoService.actualizarArchivosDeTrabajoEnLaReservaPorTramitacion(ee,
            loggedUsername, new ArrayList<ReparticionParticipanteDTO>(), usuario);
        // expedienteElectronicoService.asignarPermisosVisualizacionArchivo(expElectronico,
        // usuario, new ArrayList<ReparticionParticipante>());
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "actualizarArchivosDeTrabajoEnHijosDeTC(ExpedienteElectronico, String, Usuario) - end");
    }
  }

  public void actualizarExpedientesAsociadosEnTramitacionConjunta(
      ExpedienteElectronicoDTO expedienteElectronico) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "actualizarExpedientesAsociadosEnTramitacionConjunta(expedienteElectronico={}) - start",
          expedienteElectronico);
    }

    // Lista de expedientes hijos que forman la tramitacion Conjunta
    List<ExpedienteElectronicoDTO> listaExpedientesElectronicosDeTramitacionConjunta = new ArrayList<>();

    // Lista de expedientes asociados del cabecera que deben copiarse a los
    // expedientes hijos de la tramitacion
    List<ExpedienteAsociadoEntDTO> listaExpedientesAsociadosDelCabecera = new ArrayList<>();

    // Obtengo el expediente de cada expediente
    for (ExpedienteAsociadoEntDTO expedienteasociado : expedienteElectronico
        .getListaExpedientesAsociados()) {

      if (expedienteasociado.getEsExpedienteAsociadoTC() != null
          && expedienteasociado.getEsExpedienteAsociadoTC()) {
        ExpedienteElectronicoDTO expElectronico = expedienteElectronicoService
            .obtenerExpedienteElectronico(expedienteasociado.getTipoDocumento(),
                expedienteasociado.getAnio(), expedienteasociado.getNumero(),
                expedienteasociado.getCodigoReparticionUsuario());

        listaExpedientesElectronicosDeTramitacionConjunta.add(expElectronico);

        // Expedientes asociados que se adjuntaron al cabecera luego de
        // confirmar la tramitacion conjunta (por que tienen id de
        // cabecera)
      } else if (expedienteasociado.getIdExpCabeceraTC() != null
          && expedienteasociado.getIdExpCabeceraTC().equals(expedienteElectronico.getId())) {

        listaExpedientesAsociadosDelCabecera.add(expedienteasociado);
      }
    }

    // Los copio a los expedientes hijos y actualizo.
    for (ExpedienteElectronicoDTO ExpedienteHijoDeTC : listaExpedientesElectronicosDeTramitacionConjunta) {

      for (ExpedienteAsociadoEntDTO expAsociadoAlCabecera : listaExpedientesAsociadosDelCabecera) {

        if (!ExpedienteHijoDeTC.getListaExpedientesAsociados().contains(expAsociadoAlCabecera)) {

          ExpedienteHijoDeTC.getListaExpedientesAsociados().add(expAsociadoAlCabecera);
        }
      }
      try {
        expedienteElectronicoService.mergeExpedienteElectronico(ExpedienteHijoDeTC);
      } catch (Exception e) {
        logger.error("actualizarExpedientesAsociadosEnTramitacionConjunta(ExpedienteElectronico)",
            e);

        expedienteElectronicoService.modificarExpedienteElectronico(ExpedienteHijoDeTC);
      }

    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "actualizarExpedientesAsociadosEnTramitacionConjunta(ExpedienteElectronico) - end");
    }
  }

  public ExpedienteElectronicoDTO desvincularExpedientesTramitacionConjunta(
      ExpedienteElectronicoDTO expedienteElectronico, String loggedUsername,
      DocumentoDTO documentoTCDesvinculacion) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "desvincularExpedientesTramitacionConjunta(expedienteElectronico={}, loggedUsername={}, documentoTCDesvinculacion={}) - start",
          expedienteElectronico, loggedUsername, documentoTCDesvinculacion);
    }

    List<ExpedienteAsociadoEntDTO> listaAux = new ArrayList<>();
    listaAux.addAll(expedienteElectronico.getListaExpedientesAsociados());

    for (int i = 0; i < listaAux.size(); i++) {

      ExpedienteAsociadoEntDTO expTramitacionConjunta = listaAux.get(i);

      if (expTramitacionConjunta.getEsExpedienteAsociadoTC() != null
          && expTramitacionConjunta.getEsExpedienteAsociadoTC()) {

        // Obtengo el expediente de la tabla expedienteElectronico
        ExpedienteElectronicoDTO expElectronico = this.expedienteElectronicoService
            .obtenerExpedienteElectronico(expTramitacionConjunta.getTipoDocumento(),
                expTramitacionConjunta.getAnio(), expTramitacionConjunta.getNumero(),
                expTramitacionConjunta.getCodigoReparticionUsuario());

        // obtengo la task de cada expediente electronico.
        TaskQuery taskQuery = this.processEngine.getTaskService().createTaskQuery()
            .executionId(expElectronico.getIdWorkflow());
        Task task = taskQuery.uniqueResult();
        // me asigno la task para que el expediente que desvincule quede
        // asignado al usuario loggueado, el que actualmente tiene
        // la tramitacion conjunta.
        this.processEngine.getTaskService().assignTask(task.getId(), loggedUsername);

        documentoTCDesvinculacion.setIdTask(task.getId());
        expElectronico.getDocumentos().add(documentoTCDesvinculacion);

        for (DocumentoDTO doc : expElectronico.getDocumentos()) {
          if (doc != null) {
            doc.setDefinitivo(true);
          }
        }

        expElectronico.setFechaModificacion(new Date());

        // Elimino el expediente asociado de la lista del expediente
        // cabecera
        if (expTramitacionConjunta.getEsExpedienteAsociadoTC() != null
            && expTramitacionConjunta.getEsExpedienteAsociadoTC()) {
          expedienteElectronico.borrarExpAsociadoByIdTask(expTramitacionConjunta.getIdTask());
        }

        // Se quitan los ceros para guardar el expediente en la base
        this.expedienteElectronicoService.modificarExpedienteElectronico(expElectronico);
        // Se devuelven los ceros
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "desvincularExpedientesTramitacionConjunta(ExpedienteElectronico, String, Documento) - end - return value={}",
          expedienteElectronico);
    }
    return expedienteElectronico;
  }

  public ExpedienteElectronicoDTO desvincularExpedienteHijoTramitacionConjunta(
      ExpedienteElectronicoDTO expPadre, ExpedienteElectronicoDTO expHijo, String loggedUsername,
      DocumentoDTO documentoTCDesvinculacion) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "desvincularExpedienteHijoTramitacionConjunta(expPadre={}, expHijo={}, loggedUsername={}, documentoTCDesvinculacion={}) - start",
          expPadre, expHijo, loggedUsername, documentoTCDesvinculacion);
    }

    ExpedienteAsociadoEntDTO ea = this.expedienteAsociadoService
        .obtenerExpedienteAsociadoPorTC(expHijo.getNumero(), expHijo.getAnio(), true);

    // obtengo la task de cada expediente electronico.
    TaskQuery taskQuery = this.processEngine.getTaskService().createTaskQuery()
        .executionId(expHijo.getIdWorkflow());
    Task task = taskQuery.uniqueResult();
    // me asigno la task para que el expediente que desvincule quede
    // asignado al usuario loggueado, el que actualmente tiene
    // la tramitacion conjunta.
    this.processEngine.getTaskService().assignTask(task.getId(), loggedUsername);

    documentoTCDesvinculacion.setIdTask(task.getId());
    expHijo.getDocumentos().add(documentoTCDesvinculacion);

    for (DocumentoDTO doc : expHijo.getDocumentos()) {
      if (doc != null) {
        doc.setDefinitivo(true);
      }
    }

    // Elimino el expediente asociado de la lista del expediente
    // cabecera
    if (ea.getEsExpedienteAsociadoTC() != null && ea.getEsExpedienteAsociadoTC()) {
      expPadre.borrarExpAsociadoByIdTask(ea.getIdTask());
    }

    // Se quitan los ceros para guardar el expediente en la base
    this.expedienteElectronicoService.modificarExpedienteElectronico(expHijo);
    // Se devuelven los ceros

    if (logger.isDebugEnabled()) {
      logger.debug(
          "desvincularExpedienteHijoTramitacionConjunta(ExpedienteElectronico, ExpedienteElectronico, String, Documento) - end - return value={}",
          expPadre);
    }
    return expPadre;
  }

  public void guardarExpedienteTramitacionConjunta(ExpedienteAsociadoEntDTO expedienteAsociado) {
    if (logger.isDebugEnabled()) {
      logger.debug("guardarExpedienteTramitacionConjunta(expedienteAsociado={}) - start",
          expedienteAsociado);
    }

    try {

      ExpedienteAsociado expeEnt = mapper.map(expedienteAsociado, ExpedienteAsociado.class);

      this.tramitacionConjuntaDAO.save(expeEnt);

    } catch (Exception e) {
      logger.error("Se produjo un error al guardar un expediente adjunto", e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("guardarExpedienteTramitacionConjunta(ExpedienteAsociado) - end");
    }
  }

  public void eliminarExpedienteTramitacionConjunta(ExpedienteAsociadoEntDTO expedienteAsociado) {
    if (logger.isDebugEnabled()) {
      logger.debug("eliminarExpedienteTramitacionConjunta(expedienteAsociado={}) - start",
          expedienteAsociado);
    }

    try {

      ExpedienteAsociado expeEnt = mapper.map(expedienteAsociado, ExpedienteAsociado.class);

      this.tramitacionConjuntaDAO.delete(expeEnt);

    } catch (Exception e) {
      logger.error("Se produjo un error al eliminar un expediente adjunto", e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("eliminarExpedienteTramitacionConjunta(ExpedienteAsociado) - end");
    }
  }

  public ExpedienteAsociadoEntDTO obtenerExpedientesDeTramitacionConjunta(
      int idExpedienteAsociado) {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerExpedientesDeTramitacionConjunta(idExpedienteAsociado={}) - start",
          idExpedienteAsociado);
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "obtenerExpedientesDeTramitacionConjunta(int) - end - return value={} return null");
    }
    return null;
  }

  public void movimientoTramitacionConjunta(ExpedienteElectronicoDTO expedienteElectronico,
      String loggedUsername, Map<String, String> detalles, String estadoAnterior,
      String estadoSeleccionado, String motivoExpediente, String destino) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "movimientoTramitacionConjunta(expedienteElectronico={}, loggedUsername={}, detalles={}, estadoAnterior={}, estadoSeleccionado={}, motivoExpediente={}, destino={}) - start",
          expedienteElectronico, loggedUsername, detalles, estadoAnterior, estadoSeleccionado,
          motivoExpediente, destino);
    }

    Usuario datosUsuario = usuariosSADEService.getDatosUsuario(destino);

    try {

      for (ExpedienteAsociadoEntDTO expedienteAsociado : expedienteElectronico
          .getListaExpedientesAsociados()) {

        // Obtengo el expediente de la tabla expedienteElectronico
        ExpedienteElectronicoDTO expElectronico = this.expedienteElectronicoService
            .obtenerExpedienteElectronico(expedienteAsociado.getTipoDocumento(),
                expedienteAsociado.getAnio(), expedienteAsociado.getNumero(),
                expedienteAsociado.getCodigoReparticionUsuario());

        if (expedienteAsociado.getEsExpedienteAsociadoTC() != null
            && expedienteAsociado.getEsExpedienteAsociadoTC()) {

          // obtengo la task de cada expediente electronico.
          TaskQuery taskQuery = this.processEngine.getTaskService().createTaskQuery()
              .executionId(expElectronico.getIdWorkflow());
          Task task = taskQuery.uniqueResult();

          String valorDestino;
          if (destino.contains(ConstantesWeb.SUFIJO_CONJUNTA)) {
            valorDestino = destino;
          } else {
            valorDestino = destino + ConstantesWeb.SUFIJO_CONJUNTA;
          }

          /**
           * Si el estado seleccionado es distinto al estado anterior avanza la
           * task en el workflow. No se realiza el assignne de la task por que
           * el mismo se hace en el handler asignarTarea.java.
           */
          if (!estadoSeleccionado.equalsIgnoreCase(estadoAnterior)) {

            // Si es !=null significa que es un usuario.
            if (datosUsuario != null) {

              // seteo las variables que el handler
              // (asignarTarea.java) utiliza para asignar las
              // tareas al avanzar en el workflow

              this.processEngine.getExecutionService().setVariable(task.getExecutionId(),
                  "usuarioSeleccionado", valorDestino);
              this.processEngine.getExecutionService().setVariable(task.getExecutionId(),
                  "grupoSeleccionado", null);

            } else {

              this.processEngine.getExecutionService().setVariable(task.getExecutionId(),
                  "usuarioSeleccionado", null);
              this.processEngine.getExecutionService().setVariable(task.getExecutionId(),
                  "grupoSeleccionado", valorDestino);

            }

            processEngine.getExecutionService().signalExecutionById(task.getExecutionId(),
                estadoSeleccionado);

            // luego de avanzar la task en el workflow. Actualizo el
            // campo "estado" de cada expediente electronico
            // adjunto.
            expElectronico.setEstado(estadoSeleccionado);
            this.expedienteElectronicoService.modificarExpedienteElectronico(expElectronico);

          } else {

            /**
             * Si el estado seleccionado es igual al estado anterior no se debe
             * avanzar en el workflow pero si se debe actualizar el assignee. En
             * el caso de que se envie a una repartición se modifica el
             * "addTaskParticipatingGroup".
             */

            // Si es !=null significa que es un usuario.
            if (datosUsuario != null) {
              this.processEngine.getTaskService().assignTask(task.getId(), valorDestino);

            } else {

              this.processEngine.getTaskService().assignTask(task.getId(), null);
              List<Participation> participations = this.processEngine.getTaskService()
                  .getTaskParticipations(task.getId());
              for (Participation participation : participations) {
                this.processEngine.getTaskService().removeTaskParticipatingGroup(task.getId(),
                    participation.getGroupId(), Participation.CANDIDATE);
              }
              this.processEngine.getTaskService().addTaskParticipatingGroup(task.getId(),
                  valorDestino, Participation.CANDIDATE);

            }
          }
          // Actualizo el usuario anterior para cada expediente hijo.
          processEngine.getExecutionService().setVariable(task.getExecutionId(), "usuarioAnterior",
              loggedUsername);
        }

      }
    } catch (Exception e) {
      logger.error("Error al realizar el pase de la tramitacion conjunta.", e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "movimientoTramitacionConjunta(ExpedienteElectronico, String, Map<String,String>, String, String, String, String) - end");
    }
  }

  public boolean esExpedienteEnProcesoDeTramitacionConjunta(String codigoExpediente) {
    if (logger.isDebugEnabled()) {
      logger.debug("esExpedienteEnProcesoDeTramitacionConjunta(codigoExpediente={}) - start",
          codigoExpediente);
    }

    String tipoActaucion = BusinessFormatHelper.obtenerActuacion(codigoExpediente);
    Integer anio = BusinessFormatHelper.obtenerAnio(codigoExpediente);
    Integer numero = BusinessFormatHelper.obtenerNumeroSade(codigoExpediente);
    String reparticion = BusinessFormatHelper.obtenerReparticionUsuario(codigoExpediente);

//    ExpedienteAsociado expedienteEnt = tramitacionConjuntaDAO
//        .findByTipoDocumentoAndAnioAndNumeroAndCodigoReparticionUsuario(
//            tipoActaucion, anio, numero, reparticion);

    boolean esExpedienteEnTramitacionConjunta = false;
//
//    if (null != expedienteEnt) {
//      esExpedienteEnTramitacionConjunta = true;
//    }

    if (logger.isDebugEnabled()) {
      logger.debug("esExpedienteEnProcesoDeTramitacionConjunta(String) - end - return value={}",
          esExpedienteEnTramitacionConjunta);
    }
    return esExpedienteEnTramitacionConjunta;
  }

  public String obtenerExpedienteElectronicoCabecera(String codigoExpedienteAsociado) {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerExpedienteElectronicoCabecera(codigoExpedienteAsociado={}) - start",
          codigoExpedienteAsociado);
    }

    String tipoActaucion = BusinessFormatHelper.obtenerActuacion(codigoExpedienteAsociado);
    Integer anio = BusinessFormatHelper.obtenerAnio(codigoExpedienteAsociado);
    Integer numero = BusinessFormatHelper.obtenerNumeroSade(codigoExpedienteAsociado);
    String reparticion = BusinessFormatHelper.obtenerReparticionUsuario(codigoExpedienteAsociado);

//    ExpedienteAsociado expeEnt = tramitacionConjuntaDAO
//        .findByTipoDocumentoAndAnioAndNumeroAndCodigoReparticionUsuarioAndIdExpCabeceraTCNotNull(
//            tipoActaucion, anio, numero, reparticion);

    //Integer idExpedieneteCabeceraDTO = expeEnt.getIdExpCabeceraTC();

//    String codigoExpedienteElectronicoCabecera = (expedienteElectronicoDAO
//        .buscarExpedienteElectronico(idExpedieneteCabeceraDTO)).getCodigoCaratula();

    if (logger.isDebugEnabled()) {
//      logger.debug("obtenerExpedienteElectronicoCabecera(String) - end - return value={}",
//          codigoExpedienteElectronicoCabecera);
    }
    return null;
  }

  public DocumentoDTO generarDocumentoDeDesvinculacionEnTramitacionConjunta(
      ExpedienteElectronicoDTO expedienteElectronico, String movito, String username,
      Task workingTask) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "generarDocumentoDeDesvinculacionEnTramitacionConjunta(expedienteElectronico={}, movito={}, username={}, workingTask={}) - start",
          expedienteElectronico, movito, username, workingTask);
    }

    DocumentoDTO documentoTC = documentoGedoService
        .generarDocumentoDeTramitacionConjunta(expedienteElectronico, movito, username);

    documentoTC.setUsuarioAsociador(username);
    documentoTC.setIdTask(workingTask.getExecutionId());

    expedienteElectronico.getDocumentos().add(documentoTC);
    if (expedienteElectronico.getEsReservado()
        || (expedienteElectronico.getTrata().getTipoReserva() != null
            && expedienteElectronico.getTrata().getTipoReserva().getId() > 1)) {

      List<String> listaDocumentos = new ArrayList<>();
      listaDocumentos.add(documentoTC.getNumeroSade());
      try {

        externalActualizarVisualizacionService.actualizarEstadoVisualizacion(username,
            obtenerReparticionesRectoras(expedienteElectronico), listaDocumentos);

      } catch (Exception e) {
        logger.error(e.getMessage());
        throw new TeRuntimeException(e);
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "generarDocumentoDeDesvinculacionEnTramitacionConjunta(ExpedienteElectronico, String, String, Task) - end - return value={}",
          documentoTC);
    }
    return documentoTC;

  }

  public DocumentoDTO generarDocumentoDeVinculacionEnTramitacionConjunta(
      ExpedienteElectronicoDTO expedienteElectronico, String motivo, String username,
      Task workingTask) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "generarDocumentoDeVinculacionEnTramitacionConjunta(expedienteElectronico={}, motivo={}, username={}, workingTask={}) - start",
          expedienteElectronico, motivo, username, workingTask);
    }

    StringBuilder buf = new StringBuilder();
    buf.append(motivo);

    for (ExpedienteAsociadoEntDTO expedienteAsociadoTC : expedienteElectronico
        .getListaExpedientesAsociados()) {
      // TENGO QUE PONER LOS 0s en el NUMERO
      if (expedienteAsociadoTC.getEsExpedienteAsociadoTC() != null
          && expedienteAsociadoTC.getEsExpedienteAsociadoTC()) {

        buf.append(" \n " + expedienteAsociadoTC.toString());
      }
    }

    motivo = buf.toString();

    DocumentoDTO documentoTC = documentoGedoService
        .generarDocumentoDeTramitacionConjunta(expedienteElectronico, motivo, username);
    if (documentoTC != null) {
      documentoTC.setUsuarioAsociador(username);
      documentoTC.setIdTask(workingTask.getExecutionId());

      expedienteElectronico.getDocumentos().add(documentoTC);
    } else {
      throw new TeRuntimeException("Se produjo un error al generar el documento de Gedo", null);

    }
    if (expedienteElectronico.getEsReservado()
        || (expedienteElectronico.getTrata().getTipoReserva() != null
            && expedienteElectronico.getTrata().getTipoReserva().getId() > 1)) {

      List<String> listaDocumentos = new ArrayList<>();
      listaDocumentos.add(documentoTC.getNumeroSade());
      try {

        externalActualizarVisualizacionService.actualizarEstadoVisualizacion(username,
            obtenerReparticionesRectoras(expedienteElectronico), listaDocumentos);

      } catch (Exception e) {
        logger.error(e.getMessage());
        throw new TeRuntimeException(e);
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "generarDocumentoDeVinculacionEnTramitacionConjunta(ExpedienteElectronico, String, String, Task) - end - return value={}",
          documentoTC);
    }
    return documentoTC;

  }

  private List<String> obtenerReparticionesRectoras(
      ExpedienteElectronicoDTO expedienteElectronico) {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerReparticionesRectoras(expedienteElectronico={}) - start",
          expedienteElectronico);
    }

    List<TrataReparticionDTO> listRepRect = expedienteElectronico.getTrata()
        .getReparticionesRectoras();
    List<String> reparticionesRectoras = new ArrayList<>();
    for (TrataReparticionDTO trata : listRepRect) {
      if (trata.getReserva()) {
        reparticionesRectoras.add(trata.getCodigoReparticion());
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("obtenerReparticionesRectoras(ExpedienteElectronico) - end - return value={}",
          reparticionesRectoras);
    }
    return reparticionesRectoras;
  }

  public ExpedienteElectronicoService getExpedienteElectronicoService() {
    return expedienteElectronicoService;
  }

  public void setExpedienteElectronicoService(
      ExpedienteElectronicoService expedienteElectronicoService) {
    this.expedienteElectronicoService = expedienteElectronicoService;
  }

  public ExpedienteElectronicoDAO getExpedienteElectronicoDAO() {
    return expedienteElectronicoDAO;
  }

  public void setExpedienteElectronicoDAO(ExpedienteElectronicoDAO expedienteElectronicoDAO) {
    this.expedienteElectronicoDAO = expedienteElectronicoDAO;
  }

  public ExpedienteAsociadoService getExpedienteAsociadoService() {
    return expedienteAsociadoService;
  }

  public void setExpedienteAsociadoService(ExpedienteAsociadoService expedienteAsociadoService) {
    this.expedienteAsociadoService = expedienteAsociadoService;
  }

  public DocumentoDTO generarDocumentoQuitarReserva(ExpedienteElectronicoDTO expedienteElectronico,
      String motivo, String username, Task workingTask) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "generarDocumentoQuitarReserva(expedienteElectronico={}, motivo={}, username={}, workingTask={}) - start",
          expedienteElectronico, motivo, username, workingTask);
    }

    DocumentoDTO documentoQR = documentoGedoService
        .generarDocumentoQuitarReserva(expedienteElectronico, motivo, username);

    if (documentoQR != null) {
      documentoQR.setUsuarioAsociador(username);
      documentoQR.setIdTask(workingTask.getExecutionId());

      expedienteElectronico.getDocumentos().add(documentoQR);
    } else {
      throw new TeRuntimeException("Se produjo un error al generar el documento de Gedo", null);

    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "generarDocumentoQuitarReserva(ExpedienteElectronico, String, String, Task) - end - return value={}",
          documentoQR);
    }
    return documentoQR;
  }

  @Override
  public ExpedienteElectronicoDTO vincularExpedientesTramitacionConjunta(
      List<ExpedienteAsociadoEntDTO> listaExpedienteEnTramitacionConjunta, String loggedUsername,
      DocumentoDTO documentoTC, String asignadoAnterior,
      ExpedienteElectronicoDTO expedienteElectronico) throws AsignacionException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "vincularExpedientesTramitacionConjunta(listaExpedienteEnTramitacionConjunta={}, loggedUsername={}, documentoTC={}, asignadoAnterior={}, expedienteElectronico={}) - start",
          listaExpedienteEnTramitacionConjunta, loggedUsername, documentoTC, asignadoAnterior,
          expedienteElectronico);
    }

    for (ExpedienteAsociadoEntDTO expTramitacionConjunta : listaExpedienteEnTramitacionConjunta) {

      expTramitacionConjunta.setDefinitivo(true);
      expTramitacionConjunta.setFechaModificacion(new Date());
      expTramitacionConjunta.setUsuarioModificacion(loggedUsername);

      // debo traerme cada task y cambiarle el assignee para que no se
      // vea mas en el inbox
      TaskQuery taskQuery = this.processEngine.getTaskService().createTaskQuery()
          .executionId(expTramitacionConjunta.getIdTask());
      Task task = taskQuery.uniqueResult();

      // Obtengo el expediente de la tabla expedienteElectronico
      ExpedienteElectronicoDTO expElectronico = this.expedienteElectronicoService
          .obtenerExpedienteElectronico(expTramitacionConjunta.getTipoDocumento(),
              expTramitacionConjunta.getAnio(), expTramitacionConjunta.getNumero(),
              expTramitacionConjunta.getCodigoReparticionUsuario());

      documentoTC.setIdTask(task.getId());

      expElectronico.getDocumentos().add(documentoTC);
      expElectronico.setFechaModificacion(new Date());

      String asignacion = task.getAssignee();
      if (!asignacion.contains(ConstantesWeb.SUFIJO_CONJUNTA))
        asignacion = asignacion + ConstantesWeb.SUFIJO_CONJUNTA;

      this.processEngine.getTaskService().assignTask(task.getId(), asignacion);

      // Quito los ceros del
      this.expedienteElectronicoService.modificarExpedienteElectronico(expElectronico);

    } // fin for

//    expedienteElectronicoService.modificarExpedienteElectronico(expedienteElectronico);
    expedienteElectronico = expedienteElectronicoService.modificarExpedienteElectronico2(expedienteElectronico);

    if (logger.isDebugEnabled()) {
      logger.debug(
          "vincularExpedientesTramitacionConjunta(List<ExpedienteAsociado>, String, Documento, String, ExpedienteElectronico) - end - return value={}",
          expedienteElectronico);
    }
    return expedienteElectronico;
  }

  @Override
  public void guardarExpedienteTramitacionConjunta(ExpedienteAsociadoDTO expedienteAsociado) {
    // TODO Auto-generated method stub

  }

  @Override
  public void eliminarExpedienteTramitacionConjunta(ExpedienteAsociadoDTO expedienteAsociado) {
    // TODO Auto-generated method stub

  }

}
