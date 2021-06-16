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
import com.egoveris.te.base.exception.ValidacionDeFuisionException;
import com.egoveris.te.base.exception.external.TeRuntimeException;
import com.egoveris.te.base.model.ArchivoDeTrabajoDTO;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.ExpedienteAsociadoEntDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.TrataReparticionDTO;
import com.egoveris.te.base.model.expediente.ExpedienteAsociado;
import com.egoveris.te.base.model.expediente.ExpedienteElectronico;
import com.egoveris.te.base.repository.expediente.ExpedienteAsociadoRepository;
import com.egoveris.te.base.repository.expediente.ExpedienteElectronicoRepository;
import com.egoveris.te.base.service.expediente.ExpedienteAsociadoService;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.util.BusinessFormatHelper;

@Service
@Transactional
public class FusionServiceImpl implements FusionService {
  @Autowired
  private ExpedienteElectronicoService expedienteElectronicoService;

  @Autowired
  private ExpedienteAsociadoRepository expedienteAsociadoRepo;

  @Autowired
  private ExpedienteElectronicoRepository expedienteRepository;

  @Autowired
  private ExpedienteElectronicoDAO expedienteElectronicoDAO;
  @Autowired
  private UsuariosSADEService usuariosSADEService;
  @Autowired
  private ArchivoDeTrabajoService archivoDeTrabajoService;
  @Autowired
  private DocumentoGedoService documentoGedoService;

  private final static Logger logger = LoggerFactory.getLogger(FusionServiceImpl.class);
  @Autowired
  private ProcessEngine processEngine;
  @Autowired
  private ExpedienteAsociadoService expedienteAsociadoService;

  @Autowired
  private IExternalActualizarVisualizacionService externalActualizarVisualizacionService;

  private DozerBeanMapper mapper = new DozerBeanMapper();

  /**
   * Realiza validaciones sobre los expedientes y crea el Expediente Fusion
   * Asociado
   * 
   * @param expedienteElectronico
   * @param loggedUsername
   * @param estado
   * @return
   */
  public ExpedienteAsociadoEntDTO obtenerExpedienteFusion(
      ExpedienteElectronicoDTO expedienteElectronico, String loggedUsername, String estado) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "obtenerExpedienteFusion(expedienteElectronico={}, loggedUsername={}, estado={}) - start",
          expedienteElectronico, loggedUsername, estado);
    }

    ExpedienteAsociadoEntDTO expedienteFusion;

    // Obtengo la task para saber si esta asignada al usuario que quiere
    // iniciar la fusión.
    TaskQuery taskQuery = this.processEngine.getTaskService().createTaskQuery()
        .executionId(expedienteElectronico.getIdWorkflow());
    Task task = taskQuery.uniqueResult();

    if (task.getActivityName().equals("Tramitacion")
        || task.getActivityName().equals("Ejecucion")) {

      if (estado.equals(task.getActivityName())) {

        if (task.getAssignee().equals(loggedUsername)) {
          // HAY QUE VALIDAR QUE NO SE ENCUENTRE NINGUN RECURSO
          // PENDIENTE EN EL EXPEDIENTE QUE SE QUIERE FUSIONAR
          validarExpedientesAsociadosDefinitivosEnExpedienteAFusionar(expedienteElectronico);

          validarArchivosDeTrabajoDefinitivosEnExpedienteAFusionar(expedienteElectronico);

          validarDocumentosDefinitivosEnExpedienteAFusionar(expedienteElectronico);

          // SI VALIDA TODOS ENTONCES ASOCIO EL EXPEDIENTE
          expedienteFusion = asociarExpediente(expedienteElectronico, loggedUsername);

        } else {
          throw new TeRuntimeException(
              "No puede adjuntar el expediente. El mismo no se encuentra asignado a "
                  + task.getAssignee(),
              null);
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
          "obtenerExpedienteFusion(ExpedienteElectronico, String, String) - end - return value={}",
          expedienteFusion);
    }
    return expedienteFusion;
  }

  /**
   * Crea un expediente Asociado
   * 
   * @param expedienteElectronico
   * @param loggedUsername
   * @return
   */
  private ExpedienteAsociadoEntDTO asociarExpediente(
      ExpedienteElectronicoDTO expedienteElectronico, String loggedUsername) {
    if (logger.isDebugEnabled()) {
      logger.debug("asociarExpediente(expedienteElectronico={}, loggedUsername={}) - start",
          expedienteElectronico, loggedUsername);
    }

    ExpedienteAsociadoEntDTO expedienteFusion;
    expedienteFusion = new ExpedienteAsociadoEntDTO();

    expedienteFusion.setTipoDocumento(expedienteElectronico.getTipoDocumento());
    expedienteFusion.setAnio(expedienteElectronico.getAnio());
    expedienteFusion.setNumero(expedienteElectronico.getNumero());
    expedienteFusion.setSecuencia(expedienteElectronico.getSecuencia());
    expedienteFusion.setDefinitivo(expedienteElectronico.getDefinitivo());
    expedienteFusion
        .setCodigoReparticionActuacion(expedienteElectronico.getCodigoReparticionActuacion());
    expedienteFusion
        .setCodigoReparticionUsuario(expedienteElectronico.getCodigoReparticionUsuario());
    expedienteFusion.setEsElectronico(expedienteElectronico.getEsElectronico());
    expedienteFusion.setIdCodigoCaratula(expedienteElectronico.getId());
    expedienteFusion.setTrata(expedienteElectronico.getTrata().getCodigoTrata());
    expedienteFusion.setEsExpedienteAsociadoFusion(true);
    expedienteFusion.setFechaAsociacion(new Date());
    expedienteFusion.setUsuarioAsociador(loggedUsername);
    expedienteFusion.setIdTask(expedienteElectronico.getIdWorkflow());

    if (logger.isDebugEnabled()) {
      logger.debug("asociarExpediente(ExpedienteElectronico, String) - end - return value={}",
          expedienteFusion);
    }
    return expedienteFusion;
  }

  /**
   * Valida que los documentos de expedienteElectronico sean definitivos
   * 
   * @param expedienteElectronico
   * @throws ValidacionDeFuisionException
   */
  private void validarDocumentosDefinitivosEnExpedienteAFusionar(
      ExpedienteElectronicoDTO expedienteElectronico) throws ValidacionDeFuisionException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "validarDocumentosDefinitivosEnExpedienteAFusionar(expedienteElectronico={}) - start",
          expedienteElectronico);
    }

    if (expedienteElectronico.getDocumentos() != null
        && !expedienteElectronico.getDocumentos().isEmpty()) {
      for (DocumentoDTO documentoEnExpedienteAFusionar : expedienteElectronico.getDocumentos()) {
        if (!documentoEnExpedienteAFusionar.getDefinitivo()) {
          throw new ValidacionDeFuisionException(
              "No puede vincular el expediente. El mismo contiene Documento pendientes de vincular.",
              null);
        }
      }
    }

    if (logger.isDebugEnabled()) {
      logger
          .debug("validarDocumentosDefinitivosEnExpedienteAFusionar(ExpedienteElectronico) - end");
    }
  }

  /**
   * Valida que los Archivos de Trabajo de expedienteElectronico sean
   * definitivos
   * 
   * @param expedienteElectronico
   * @throws ValidacionDeFuisionException
   */
  private void validarArchivosDeTrabajoDefinitivosEnExpedienteAFusionar(
      ExpedienteElectronicoDTO expedienteElectronico) throws ValidacionDeFuisionException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "validarArchivosDeTrabajoDefinitivosEnExpedienteAFusionar(expedienteElectronico={}) - start",
          expedienteElectronico);
    }

    if (expedienteElectronico.getArchivosDeTrabajo() != null
        && !expedienteElectronico.getArchivosDeTrabajo().isEmpty()) {
      for (ArchivoDeTrabajoDTO archivoDeTrabajoEnExpedienteAfusionar : expedienteElectronico
          .getArchivosDeTrabajo()) {
        if (!archivoDeTrabajoEnExpedienteAfusionar.isDefinitivo()) {
          throw new ValidacionDeFuisionException(
              "No puede vincular el expediente. El mismo contiene archivos de trabajo pendientes de añadir.",
              null);
        }
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "validarArchivosDeTrabajoDefinitivosEnExpedienteAFusionar(ExpedienteElectronico) - end");
    }
  }

  /**
   * Valida que los Expedientes Asociados de expedienteElectronico sean
   * definitivos ArchivosDeTrabajo
   * 
   * @param expedienteElectronico
   * @throws ValidacionDeFuisionException
   */
  private void validarExpedientesAsociadosDefinitivosEnExpedienteAFusionar(
      ExpedienteElectronicoDTO expedienteElectronico) throws ValidacionDeFuisionException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "validarExpedientesAsociadosDefinitivosEnExpedienteAFusionar(expedienteElectronico={}) - start",
          expedienteElectronico);
    }

    if (expedienteElectronico.getListaExpedientesAsociados() != null
        && !expedienteElectronico.getListaExpedientesAsociados().isEmpty()) {
      for (ExpedienteAsociadoEntDTO expedienteAsociadoEnExpedienteAFusionar : expedienteElectronico
          .getListaExpedientesAsociados()) {
        if (!expedienteAsociadoEnExpedienteAFusionar.getDefinitivo()) {
          throw new ValidacionDeFuisionException(
              "No puede vincular el expediente. El mismo contiene Expedientes Asociados pendientes de asociar.",
              null);
        }
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "validarExpedientesAsociadosDefinitivosEnExpedienteAFusionar(ExpedienteElectronico) - end");
    }
  }

  public void actulizarDocumentosEnFusion(ExpedienteElectronicoDTO expedienteElectronico) {
    if (logger.isDebugEnabled()) {
      logger.debug("actulizarDocumentosEnFusion(expedienteElectronico={}) - start",
          expedienteElectronico);
    }

    for (ExpedienteAsociadoEntDTO expedienteasociado : expedienteElectronico
        .getListaExpedientesAsociados()) {

      if (expedienteasociado.getEsExpedienteAsociadoFusion() != null
          && expedienteasociado.getEsExpedienteAsociadoFusion()) {
        ExpedienteElectronicoDTO expElectronico = expedienteElectronicoService
            .obtenerExpedienteElectronico(expedienteasociado.getTipoDocumento(),
                expedienteasociado.getAnio(), expedienteasociado.getNumero(),
                expedienteasociado.getCodigoReparticionUsuario());

        for (DocumentoDTO documento : expedienteElectronico.getDocumentos()) {
          if (documento.getIdExpCabeceraTC() != null
              && documento.getIdExpCabeceraTC().equals(expedienteElectronico.getId())) {

            // El documento se agrego despues de haber confirmado la
            // Fusion, pero debo verificar que el
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
            }
          }

        }
        expedienteElectronicoService.modificarExpedienteElectronico(expElectronico);

      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("actulizarDocumentosEnFusion(ExpedienteElectronico) - end");
    }
  }

  public void actualizarArchivosDeTrabajoEnFusion(ExpedienteElectronicoDTO expedienteElectronico) {
    if (logger.isDebugEnabled()) {
      logger.debug("actualizarArchivosDeTrabajoEnFusion(expedienteElectronico={}) - start",
          expedienteElectronico);
    }

    // Ciclo por cada expediente adjunto al expediente cabecera
    for (ExpedienteAsociadoEntDTO expedienteasociado : expedienteElectronico
        .getListaExpedientesAsociados()) {

      // Tomo aquellos expedientes adjuntos que son adjuntos por
      // Fusion
      if (expedienteasociado.getEsExpedienteAsociadoFusion() != null
          && expedienteasociado.getEsExpedienteAsociadoFusion()) {
        ExpedienteElectronicoDTO expElectronico = expedienteElectronicoService
            .obtenerExpedienteElectronico(expedienteasociado.getTipoDocumento(),
                expedienteasociado.getAnio(), expedienteasociado.getNumero(),
                expedienteasociado.getCodigoReparticionUsuario());

        // Ciclo cada archivo de trabajo del expediente cabecera
        for (ArchivoDeTrabajoDTO archivoDeTrabajo : expedienteElectronico.getArchivosDeTrabajo()) {

          // Pregunto por cada archivo de trabajo si fue subido luego
          // de que se confirmo la Fusion
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
                break;
              }
            }
            if (!estaEnLista) {

              // Se agregan los ceros al expediente para que tome
              // bien la caratula
              // Subo un archivo por cada uno para que cuando se
              // separe la fusión
              // no se borre para todos si borro el archivo.

              // SE COMENTA POR REQUERIMIENTO DE EE-FILENET
              // this.archivoDeTrabajoService.subirArchivoDeTrabajo(
              // archivoDeTrabajo, nombreSpace);
              // coloco el archivo en la lista de cada expediente
              // hijo
              expElectronico.getArchivosDeTrabajo().add(archivoDeTrabajo);

              // Devuelvo el numero oriinal para que guarde bien
              // el expediente.
            }
          }

        }
        // Actualizo cada expediente hijo.
        expedienteElectronicoService.modificarExpedienteElectronico(expElectronico);

      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("actualizarArchivosDeTrabajoEnFusion(ExpedienteElectronico) - end");
    }
  }

  public void actualizarExpedientesAsociadosEnFusion(
      ExpedienteElectronicoDTO expedienteElectronico) {
    if (logger.isDebugEnabled()) {
      logger.debug("actualizarExpedientesAsociadosEnFusion(expedienteElectronico={}) - start",
          expedienteElectronico);
    }

    // Lista de expedientes hijos que forman la Fusion
    List<ExpedienteElectronicoDTO> listaExpedientesElectronicosDeFusion = new ArrayList<>();

    // Lista de expedientes asociados del cabecera que deben copiarse a los
    // expedientes hijos de la tramitacion
    List<ExpedienteAsociadoEntDTO> listaExpedientesAsociadosDelCabecera = new ArrayList<>();

    // Obtengo el expediente de cada expediente
    for (ExpedienteAsociadoEntDTO expedienteasociado : expedienteElectronico
        .getListaExpedientesAsociados()) {

      if (expedienteasociado.getEsExpedienteAsociadoFusion() != null
          && expedienteasociado.getEsExpedienteAsociadoFusion()) {
        ExpedienteElectronicoDTO expElectronico = expedienteElectronicoService
            .obtenerExpedienteElectronico(expedienteasociado.getTipoDocumento(),
                expedienteasociado.getAnio(), expedienteasociado.getNumero(),
                expedienteasociado.getCodigoReparticionUsuario());

        listaExpedientesElectronicosDeFusion.add(expElectronico);

        // Expedientes asociados que se adjuntaron al cabecera luego de
        // confirmar la Fusion (por que tienen id de
        // cabecera)
      } else if (expedienteasociado.getIdExpCabeceraTC() != null
          && expedienteasociado.getIdExpCabeceraTC().equals(expedienteElectronico.getId())) {

        listaExpedientesAsociadosDelCabecera.add(expedienteasociado);
      }
    }

    // Los copio a los expedientes hijos y actualizo.
    for (ExpedienteElectronicoDTO ExpedienteHijoDeFusion : listaExpedientesElectronicosDeFusion) {

      for (ExpedienteAsociadoEntDTO expAsociadoAlCabecera : listaExpedientesAsociadosDelCabecera) {

        if (!ExpedienteHijoDeFusion.getListaExpedientesAsociados()
            .contains(expAsociadoAlCabecera)) {

          ExpedienteHijoDeFusion.getListaExpedientesAsociados().add(expAsociadoAlCabecera);
        }
      }

      expedienteElectronicoService.modificarExpedienteElectronico(ExpedienteHijoDeFusion);

    }

    if (logger.isDebugEnabled()) {
      logger.debug("actualizarExpedientesAsociadosEnFusion(ExpedienteElectronico) - end");
    }
  }

  public ExpedienteElectronicoDTO desvincularExpedientesFusion(
      ExpedienteElectronicoDTO expedienteElectronico, String loggedUsername,
      DocumentoDTO documentoFusion) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "desvincularExpedientesFusion(expedienteElectronico={}, loggedUsername={}, documentoFusion={}) - start",
          expedienteElectronico, loggedUsername, documentoFusion);
    }

    List<ExpedienteAsociadoEntDTO> listaAux = new ArrayList<>();
    listaAux.addAll(expedienteElectronico.getListaExpedientesAsociados());

    for (int i = 0; i < listaAux.size(); i++) {

      ExpedienteAsociadoEntDTO expFusion = listaAux.get(i);

      if (expFusion.getEsExpedienteAsociadoFusion() != null
          && expFusion.getEsExpedienteAsociadoFusion()) {

        // Obtengo el expediente de la tabla expedienteElectronico
        ExpedienteElectronicoDTO expElectronico = this.expedienteElectronicoService
            .obtenerExpedienteElectronico("EX", expFusion.getAnio(), expFusion.getNumero(),
                expFusion.getCodigoReparticionUsuario());

        // obtengo la task de cada expediente electronico.
        TaskQuery taskQuery = this.processEngine.getTaskService().createTaskQuery()
            .executionId(expElectronico.getIdWorkflow());
        Task task = taskQuery.uniqueResult();

        // me asigno la task para que el expediente que desvincule quede
        // asignado al usuario loggueado, el que actualmente tiene
        // la Fusion.
        this.processEngine.getTaskService().assignTask(task.getId(), loggedUsername);

        documentoFusion.setIdTask(task.getId());
        expElectronico.getDocumentos().add(documentoFusion);

        // Elimino el expediente asociado de la lista del expediente
        // cabecera
        expedienteElectronico.getListaExpedientesAsociados().remove(expFusion);

        this.expedienteElectronicoService.modificarExpedienteElectronico(expElectronico);
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "desvincularExpedientesFusion(ExpedienteElectronico, String, Documento) - end - return value={}",
          expedienteElectronico);
    }
    return expedienteElectronico;
  }

  public void guardarExpedienteFusion(ExpedienteAsociadoEntDTO expedienteAsociado) {
    if (logger.isDebugEnabled()) {
      logger.debug("guardarExpedienteFusion(expedienteAsociado={}) - start", expedienteAsociado);
    }

    try {
      ExpedienteAsociado entidad = mapper.map(expedienteAsociado, ExpedienteAsociado.class);

      expedienteAsociadoRepo.save(entidad);
    } catch (Exception e) {
      logger.error("Se produjo un error al guardar un expediente adjunto", e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("guardarExpedienteFusion(ExpedienteAsociado) - end");
    }
  }

  public void eliminarExpedienteFusion(ExpedienteAsociadoEntDTO expedienteAsociado) {
    if (logger.isDebugEnabled()) {
      logger.debug("eliminarExpedienteFusion(expedienteAsociado={}) - start", expedienteAsociado);
    }

    try {
      ExpedienteAsociado entidad = mapper.map(expedienteAsociado, ExpedienteAsociado.class);

      expedienteAsociadoRepo.delete(entidad);
    } catch (Exception e) {
      logger.error("Se produjo un error al aliminar un expediente adjunto", e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("eliminarExpedienteFusion(ExpedienteAsociado) - end");
    }
  }

  public ExpedienteAsociadoEntDTO obtenerExpedientesDeFusion(int idExpedienteAsociado) {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerExpedientesDeFusion(idExpedienteAsociado={}) - start",
          idExpedienteAsociado);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("obtenerExpedientesDeFusion(int) - end - return value={null}");
    }
    return null;
  }

  public void movimientoFusion(ExpedienteElectronicoDTO expedienteElectronico,
      String loggedUsername, Map<String, String> detalles, String estadoAnterior,
      String estadoSeleccionado, String motivoExpediente, String destino) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "movimientoFusion(expedienteElectronico={}, loggedUsername={}, detalles={}, estadoAnterior={}, estadoSeleccionado={}, motivoExpediente={}, destino={}) - start",
          expedienteElectronico, loggedUsername, detalles, estadoAnterior, estadoSeleccionado,
          motivoExpediente, destino);
    }

    Usuario datosUsuario = usuariosSADEService.getDatosUsuario(destino);

    try {

      for (ExpedienteAsociadoEntDTO expedienteAsociado : expedienteElectronico
          .getListaExpedientesAsociados()) {

        if (expedienteAsociado.getEsExpedienteAsociadoFusion() != null
            && expedienteAsociado.getEsExpedienteAsociadoFusion()) {

          // Obtengo el expediente de la tabla expedienteElectronico
          ExpedienteElectronicoDTO expElectronico = this.expedienteElectronicoService
              .obtenerExpedienteElectronico("EX", expedienteAsociado.getAnio(),
                  expedienteAsociado.getNumero(),
                  expedienteAsociado.getCodigoReparticionUsuario());

          // obtengo la task de cada expediente electronico.
          TaskQuery taskQuery = this.processEngine.getTaskService().createTaskQuery()
              .executionId(expElectronico.getIdWorkflow());
          Task task = taskQuery.uniqueResult();

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
                  "usuarioSeleccionado", destino + ".fusion");
              this.processEngine.getExecutionService().setVariable(task.getExecutionId(),
                  "grupoSeleccionado", null);

            } else {

              this.processEngine.getExecutionService().setVariable(task.getExecutionId(),
                  "usuarioSeleccionado", null);
              this.processEngine.getExecutionService().setVariable(task.getExecutionId(),
                  "grupoSeleccionado", destino + ".fusion");

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
              this.processEngine.getTaskService().assignTask(task.getId(), destino + ".fusion");

            } else {

              this.processEngine.getTaskService().assignTask(task.getId(), null);
              this.processEngine.getTaskService().addTaskParticipatingGroup(task.getId(),
                  destino + ".fusion", Participation.CANDIDATE);

            }
          }
          // Actualizo el usuario anterior para cada expediente hijo.
          processEngine.getExecutionService().setVariable(task.getExecutionId(), "usuarioAnterior",
              loggedUsername);

        }
      }
    } catch (Exception e) {
      logger.error("Error al realizar el pase de la fusión.", e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "movimientoFusion(ExpedienteElectronico, String, Map<String,String>, String, String, String, String) - end");
    }
  }

  public boolean esExpedienteEnProcesoDeFusion(String codigoExpediente) {
    if (logger.isDebugEnabled()) {
      logger.debug("esExpedienteEnProcesoDeFusion(codigoExpediente={}) - start", codigoExpediente);
    }

    String tipoActaucion = BusinessFormatHelper.obtenerActuacion(codigoExpediente);
    Integer anio = BusinessFormatHelper.obtenerAnio(codigoExpediente);
    Integer numero = BusinessFormatHelper.obtenerNumeroSade(codigoExpediente);
    String reparticion = BusinessFormatHelper.obtenerReparticionUsuario(codigoExpediente);

    ExpedienteAsociado entidad = expedienteAsociadoRepo
        .findByTipoDocumentoAndAnioAndNumeroAndCodigoReparticionUsuarioAndEsExpedienteAsociadoFusion(
            tipoActaucion, anio, numero, reparticion, true);
    if (logger.isDebugEnabled()) {
      logger.debug("esExpedienteEnProcesoDeFusion(String) - end - return value={}", entidad);
    }
    if (entidad != null) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "esExpedienteEnProcesoDeFusion(String, Integer, Integer, String) - end - return value={}",
            true);
      }
      return true;
    } else {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "esExpedienteEnProcesoDeFusion(String, Integer, Integer, String) - end - return value={}",
            false);
      }
      return false;
    }
  }

  public boolean esExpedienteFusion(Integer codigoExpediente) {
    if (logger.isDebugEnabled()) {
      logger.debug("esExpedienteFusion(codigoExpediente={}) - start", codigoExpediente);
    }

    ExpedienteAsociado entidad = expedienteAsociadoRepo
        .findByNumeroAndEsExpedienteAsociadoFusionTrue(codigoExpediente);

    if (entidad != null) {
      if (logger.isDebugEnabled()) {
        logger.debug("esExpedienteFusion(Integer) - end - return value={}", true);
      }
      return true;
    } else {
      if (logger.isDebugEnabled()) {
        logger.debug("esExpedienteFusion(Integer) - end - return value={}", false);
      }
      return false;
    }
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

    ExpedienteAsociado expedienteAsociado = expedienteAsociadoRepo
        .findByTipoDocumentoAndAnioAndNumeroAndCodigoReparticionUsuarioAndIdExpCabeceraTCIsNotNull(
            tipoActaucion, anio, numero, reparticion);

    ExpedienteElectronico expedienteElectronico = expedienteRepository
        .findOne(expedienteAsociado.getIdExpCabeceraTC());

    ExpedienteElectronicoDTO expedienteElectronicoDto = mapper.map(expedienteElectronico,
        ExpedienteElectronicoDTO.class);
    if (null != expedienteElectronico) {
      expedienteElectronicoDto = subsanarOrdenDocumentos(expedienteElectronicoDto);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("obtenerExpedienteElectronicoCabecera(String) - end - return value={}",
          expedienteElectronicoDto);
    }
    return expedienteElectronicoDto.toString();
  }

  public DocumentoDTO generarDocumentoDeDesvinculacionEnFusion(
      ExpedienteElectronicoDTO expedienteElectronico, String movito, String username,
      Task workingTask) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "generarDocumentoDeDesvinculacionEnFusion(expedienteElectronico={}, movito={}, username={}, workingTask={}) - start",
          expedienteElectronico, movito, username, workingTask);
    }

    DocumentoDTO documentoFusion = documentoGedoService
        .generarDocumentoDeTramitacionConjunta(expedienteElectronico, movito, username);
    documentoFusion.setUsuarioAsociador(username);
    documentoFusion.setIdTask(workingTask.getExecutionId());

    expedienteElectronico.getDocumentos().add(documentoFusion);

    if (logger.isDebugEnabled()) {
      logger.debug(
          "generarDocumentoDeDesvinculacionEnFusion(ExpedienteElectronico, String, String, Task) - end - return value={}",
          documentoFusion);
    }
    return documentoFusion;

  }

  public DocumentoDTO generarDocumentoDeVinculacionEnFusion(
      ExpedienteElectronicoDTO expedienteElectronico, String motivo, String username,
      Task workingTask) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "generarDocumentoDeVinculacionEnFusion(expedienteElectronico={}, motivo={}, username={}, workingTask={}) - start",
          expedienteElectronico, motivo, username, workingTask);
    }

    StringBuilder buf = new StringBuilder();
    buf.append(motivo);

    for (ExpedienteAsociadoEntDTO expedienteAsociadoFusion : expedienteElectronico
        .getListaExpedientesAsociados()) {
      // TENGO QUE PONER LOS 0s en el NUMERO
      Integer numeroExpAsoc = expedienteAsociadoFusion.getNumero();
      expedienteAsociadoFusion.setNumero(numeroExpAsoc);

      if (expedienteAsociadoFusion.getEsExpedienteAsociadoFusion() != null
          && expedienteAsociadoFusion.getEsExpedienteAsociadoFusion()) {

        buf.append(" \n" + expedienteAsociadoFusion.toString());
      }
    }

    motivo = buf.toString();

    DocumentoDTO documentoFusion = documentoGedoService
        .generarDocumentoDeFusion(expedienteElectronico, motivo, username);
    if (documentoFusion != null) {
      documentoFusion.setUsuarioAsociador(username);
      documentoFusion.setIdTask(workingTask.getExecutionId());

      expedienteElectronico.getDocumentos().add(documentoFusion);
    } else {
      throw new TeRuntimeException("Se produjo un error al generar el documento de Gedo", null);

    }
    if (expedienteElectronico.getEsReservado()
        || (expedienteElectronico.getTrata().getTipoReserva() != null
            && expedienteElectronico.getTrata().getTipoReserva().getId() > 1)) {

      List<String> listaDocumentos = new ArrayList<>();
      listaDocumentos.add(documentoFusion.getNumeroSade());
      try {

        externalActualizarVisualizacionService.actualizarEstadoVisualizacion(username,
            new ArrayList<String>(), listaDocumentos);

      } catch (Exception e) {
        logger.error(e.getMessage());
        throw new TeRuntimeException(e);
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "generarDocumentoDeVinculacionEnFusion(ExpedienteElectronico, String, String, Task) - end - return value={}",
          documentoFusion);
    }
    return documentoFusion;

  }

  public void actualizarEstadoVisualizacionCabecera(ExpedienteElectronicoDTO expedienteCabecera,
      String username, List<String> listaDocumentos) throws RuntimeException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "actualizarEstadoVisualizacionCabecera(expedienteCabecera={}, username={}, listaDocumentos={}) - start",
          expedienteCabecera, username, listaDocumentos);
    }

    if (expedienteCabecera.getEsReservado()
        || (expedienteCabecera.getTrata().getTipoReserva() != null
            && expedienteCabecera.getTrata().getTipoReserva().getId() > 1)) {

      try {
        // TODO ver si el campo va siempre en null o solo para exp adyacente
        // externalActualizarVisualizacionService.actualizarEstadoVisualizacion(username,
        // obtenerReparticionesRectoras(expedienteCabecera), listaDocumentos);
        externalActualizarVisualizacionService.actualizarEstadoVisualizacion(username,
            new ArrayList<String>(), listaDocumentos);

      } catch (Exception e) {
        logger.error(e.getMessage());
        throw new TeRuntimeException(e);
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "actualizarEstadoVisualizacionCabecera(ExpedienteElectronico, String, List<String>) - end");
    }
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

  private ExpedienteElectronicoDTO subsanarOrdenDocumentos(ExpedienteElectronicoDTO e) {
    if (logger.isDebugEnabled()) {
      logger.debug("subsanarOrdenDocumentos(e={}) - start", e);
    }

    if (e.getDocumentos() != null && e.getDocumentos().size() > 0) {
      {
        List<DocumentoDTO> listaAuxiliarDepurada = tieneDuplicadosoNulos(e.getDocumentos());
        List<DocumentoDTO> listaSubSanada = new ArrayList<>();
        Integer i = 0;
        for (DocumentoDTO d : listaAuxiliarDepurada) {
          if (d != null && d.getNumeroSade() != null) {
            DocumentoDTO dSub;
            dSub = d;
            dSub.setPosicion(i);
            listaSubSanada.add(dSub);
            i++;
          }
        }
        e.setDocumentos(listaSubSanada);

        if (logger.isDebugEnabled()) {
          logger.debug("subsanarOrdenDocumentos(ExpedienteElectronico) - end - return value={}",
              e);
        }
        return e;
      }
    } else {
      if (logger.isDebugEnabled()) {
        logger.debug("subsanarOrdenDocumentos(ExpedienteElectronico) - end - return value={}", e);
      }
      return e;
    }

  }

  private ArrayList<DocumentoDTO> tieneDuplicadosoNulos(List<DocumentoDTO> list) {
    if (logger.isDebugEnabled()) {
      logger.debug("tieneDuplicadosoNulos(list={}) - start", list);
    }

    ArrayList<DocumentoDTO> listaAux = new ArrayList<>();
    for (DocumentoDTO d : list) {
      if (d != null && d.getNumeroSade() != null) {
        listaAux.add(d);

      }
    }

    for (int i = 0; i < listaAux.size() - 1; i++) {
      for (int j = i + 1; j < listaAux.size(); j++) {
        if (listaAux.get(i).getNumeroSade() == listaAux.get(j).getNumeroSade()) {
          ArrayList<DocumentoDTO> returnArrayList = this
              .removerDocumentosDuplicadosCodigoSade(listaAux);
          if (logger.isDebugEnabled()) {
            logger.debug("tieneDuplicadosoNulos(List<Documento>) - end - return value={}",
                returnArrayList);
          }
          return returnArrayList;
        }

      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("tieneDuplicadosoNulos(List<Documento>) - end - return value={}", listaAux);
    }
    return listaAux;
  }

  private ArrayList<DocumentoDTO> removerDocumentosDuplicadosCodigoSade(
      ArrayList<DocumentoDTO> lista) {
    if (logger.isDebugEnabled()) {
      logger.debug("removerDocumentosDuplicadosCodigoSade(lista={}) - start", lista);
    }

    ArrayList<DocumentoDTO> depurados = new ArrayList<>();

    for (int i = 0; i < lista.size() - 1; i++) {
      for (int j = i + 1; j < lista.size(); j++) {
        if (lista.get(i).getNumeroSade().equals(lista.get(j).getNumeroSade())) {
          depurados.add(lista.get(j));
        }
      }
    }
    if (depurados.size() > 0) {
      lista.removeAll(depurados);
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "removerDocumentosDuplicadosCodigoSade(ArrayList<Documento>) - end - return value={}",
          lista);
    }
    return lista;
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

}
