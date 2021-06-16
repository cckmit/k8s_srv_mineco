package com.egoveris.te.base.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.TaskQuery;
import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.te.base.model.ArchivoDeTrabajoDTO;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.ExpedienteAsociadoEntDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.HistorialOperacionDTO;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.util.BusinessFormatHelper;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.model.exception.ExpedienteNoDisponibleException;
import com.egoveris.te.model.exception.ParametroIncorrectoException;
import com.egoveris.te.model.exception.ProcesoFallidoException;

@Service
@Transactional
public class ExternalServiceAbstract {

  private static Logger logger = LoggerFactory.getLogger(ExternalServiceAbstract.class);

  @Autowired
  ExpedienteElectronicoService expedienteElectronicoService;
  @Autowired
  ProcessEngine processEngine;
  @Autowired
  UsuariosSADEService usuariosSADEService;
  @Autowired
  ValidaUsuarioExpedientesService validacionUsuario;
  @Autowired
  private HistorialOperacionService historialOperacionService;

  /**
   * Obtiene el objeto ExpedienteElectronico a partir del codigo del mismo.
   * 
   * RETORNA NULL SI NO ENCUENTRA EL EXPEDIENTE
   * 
   * @param codigoEE
   * @return el objeto ExpedienteElectronico cuyo código fue otorgado por
   *         parámetro. O NULL SI NO LO ENCUENTRA
   * @throws ParametroIncorrectoException
   */
  public ExpedienteElectronicoDTO obtenerExpedienteElectronico(String codigoEE)
      throws ParametroIncorrectoException, ProcesoFallidoException {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerExpedienteElectronico(codigoEE={}) - start", codigoEE);
    }

    List<String> listDesgloseCodigoEE;

    listDesgloseCodigoEE = BusinessFormatHelper.obtenerDesgloseCodigoEE(codigoEE);

    ExpedienteElectronicoDTO expedienteElectronico = null;
    try {
      expedienteElectronico = expedienteElectronicoService.obtenerExpedienteElectronico(
          listDesgloseCodigoEE.get(0), Integer.valueOf(listDesgloseCodigoEE.get(1)),
          Integer.valueOf(listDesgloseCodigoEE.get(2)), listDesgloseCodigoEE.get(5));
    } catch (Exception e) {
      logger.error("obtenerExpedienteElectronico(String)", e);

      throw new ProcesoFallidoException(
          "No se ha podido obtener el expediente electrónico " + codigoEE, null);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("obtenerExpedienteElectronico(String) - end - return value={}",
          expedienteElectronico);
    }
    return expedienteElectronico;
  }

  /**
   * Obtiene la tarea del expediente electrónico otorgado por parámetro.
   * 
   * @param expedienteElectronico
   * @return la tarea correspondiente al expediente electrónico otorgado por
   *         parámetro.
   * @throws ParametroIncorrectoException
   */
  public Task obtenerWorkingTask(ExpedienteElectronicoDTO expedienteElectronico)
      throws ParametroIncorrectoException {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerWorkingTask(expedienteElectronico={}) - start", expedienteElectronico);
    }

    if (expedienteElectronico != null) {
      // obtengo la task del expediente electronico.
      TaskQuery taskQuery = processEngine.getTaskService().createTaskQuery()
          .executionId(expedienteElectronico.getIdWorkflow());
      Task returnTask = taskQuery.uniqueResult();
      if (logger.isDebugEnabled()) {
        logger.debug("obtenerWorkingTask(ExpedienteElectronico) - end - return value={}",
            returnTask);
      }
      return returnTask;
    } else {
      throw new ParametroIncorrectoException("El expediente electrónico es nulo.", null);
    }
  }

  /**
   * Verifica que el usuario cuyo nombre se otorga por parámetro, exista en
   * CCOO.
   * 
   * @param usuario
   *          : String conteniendo el nombre del usuario que se desea verificar.
   * @return DatosUsuarioBean: un objeto DatosUsuarioBean que refiere al usuario
   *         cuyo nombre fue otorgado por parámetro. Se retornará null en caso
   *         de no encontrarse el usuario de nombre dado.
   * @throws ParametroIncorrectoException
   *           : Cuando el parámetro otorgado no cumple con ser una cadena de
   *           caractÃ©res válida.
   * @throws ProcesoFallidoException
   *           : No se ha podido continuar con la ejecución del proceso, por
   *           razones externas. (conexión,etc)
   * 
   */
  public Usuario verificarExistenciaDeUsuario(String usuario)
      throws ParametroIncorrectoException, ProcesoFallidoException {
    if (logger.isDebugEnabled()) {
      logger.debug("verificarExistenciaDeUsuario(usuario={}) - start", usuario);
    }

    Usuario usuarioBean = null;

    try {
      if (StringUtils.isEmpty(usuario) || !validacionUsuario.validaUsuarioExpedientes(usuario)) {
        throw new ParametroIncorrectoException(
            "El usuario origen (" + usuario + ") no es un usuario válido de CCOO.", null);
      }
      usuarioBean = usuariosSADEService.getDatosUsuario(usuario);
    } catch (InterruptedException e1) {
      logger.error("verificarExistenciaDeUsuario(String)", e1);

      throw new ProcesoFallidoException("No fue posible validar el usuario otorgado.", null);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("verificarExistenciaDeUsuario(String) - end - return value={}", usuarioBean);
    }
    return usuarioBean;
  }

  /**
   * Verifica que el usuario pueda apoderarse del expediente, segÃºn usuario,
   * sector, repartición.
   * 
   * @param usuarioBean
   * @param expediente
   * @throws ExpedienteNoDisponibleException
   * @deprecated
   */
  private void verificarAsignacion(final String usuario, ExpedienteElectronicoDTO expediente)
      throws ExpedienteNoDisponibleException, ParametroIncorrectoException {
    if (logger.isDebugEnabled()) {
      logger.debug("verificarAsignacion(usuario={}, expediente={}) - start", usuario, expediente);
    }

    // Chequea la tenga asignada el usuario:
    long cantTaskAsignee = processEngine.getTaskService().createTaskQuery()
        .executionId(expediente.getIdWorkflow()).assignee(usuario).count();

    if (cantTaskAsignee == 0) {

      // Si no la tiene asignada, entonces chequea la tenga un grupo del
      // usuario:
      long cantTaskCandidate = processEngine.getTaskService().createTaskQuery()
          .executionId(expediente.getIdWorkflow()).candidate(usuario).count();

      if (cantTaskCandidate == 0) {
        String motivo = "";
        try {
          motivo = traerUltimoHistorial(expediente);
        } catch (ProcesoFallidoException e) {

          logger.error("error al verificar asignacion", e);
        }
        // Se agrega el MOTIVO en el throw por que necesario para menejar el EE
        throw new ExpedienteNoDisponibleException(
            "El expediente se encuentra asignado a un usuario o grupo distinto. MOTIVO: " + motivo,
            null);
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("verificarAsignacion(String, ExpedienteElectronico) - end");
    }
  }

  private String traerUltimoHistorial(ExpedienteElectronicoDTO expediente)
      throws ProcesoFallidoException {
    if (logger.isDebugEnabled()) {
      logger.debug("traerUltimoHistorial(expediente={}) - start", expediente);
    }

    String salida = null;
    List<HistorialOperacionDTO> historiales = this.historialOperacionService
        .buscarHistorialporExpediente(expediente.getId());
    int a = 0;
    for (HistorialOperacionDTO aux : historiales) {
      if (aux.getId() >= a) {
        salida = aux.getDetalleOperacion().get("motivo");
        a = aux.getId();
      }
    }
    if (StringUtils.isEmpty(salida)) {
      throw new ProcesoFallidoException(
          "No es posible obtener el motivo del ultimo pase del expediente "
              + expediente.getCodigoCaratula(),
          null);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("traerUltimoHistorial(ExpedienteElectronico) - end - return value={}", salida);
    }
    return salida;
  }

  /**
   * Verifica que el usuario pueda apoderarse del expediente, para asÃ­ operar
   * con Ã©l. SegÃºn sistema de pertenencia, repartición, sector, usuario
   * 
   * MÃ©todo de conveniencia para operaciones que no son bloqueantes por sÃ­
   * mismas, tal como adjuntar documentos.
   * 
   * @param expediente
   * @param sistemaUsuario
   * @param usuario
   * @throws ParametroIncorrectoException
   * @throws ProcesoFallidoException
   * @throws ExpedienteNoDisponibleException
   */
  public void verificarUsuarioApodereExpediente(ExpedienteElectronicoDTO expediente,
      String sistemaUsuario, String usuario) throws ParametroIncorrectoException,
      ProcesoFallidoException, ExpedienteNoDisponibleException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "verificarUsuarioApodereExpediente(expediente={}, sistemaUsuario={}, usuario={}) - start",
          expediente, sistemaUsuario, usuario);
    }

    this.verificarUsuarioApodereExpediente(expediente, sistemaUsuario, usuario, false);

    if (logger.isDebugEnabled()) {
      logger
          .debug("verificarUsuarioApodereExpediente(ExpedienteElectronico, String, String) - end");
    }
  }

  /**
   * @deprecated
   */
  protected void verificarUsuarioApodereExpediente(ExpedienteElectronicoDTO expediente,
      String sistemaUsuario, String usuario, boolean operacionBloqueante)
      throws ParametroIncorrectoException, ProcesoFallidoException,
      ExpedienteNoDisponibleException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "verificarUsuarioApodereExpediente(expediente={}, sistemaUsuario={}, usuario={}, operacionBloqueante={}) - start",
          expediente, sistemaUsuario, usuario, operacionBloqueante);
    }

    if (sistemaUsuario == null || sistemaUsuario.equals("")) {
      throw new ParametroIncorrectoException(
          "Debe otorgar un nombre de sistema de usuario válido.", null);
    }

    if (!expediente.getEstado().equalsIgnoreCase(ConstantesWeb.ESTADO_GUARDA_TEMPORAL)) {

      this.verificarExistenciaDeUsuario(usuario);

      if (expediente.getBloqueado() || operacionBloqueante) {

        if (expediente.getSistemaApoderado().equalsIgnoreCase(sistemaUsuario)) {
          verificarAsignacion(usuario + ConstantesWeb.SUFIJO_BLOQUEADO, expediente);
        } else {
          if (operacionBloqueante) {
            verificarAsignacion(usuario, expediente);
          } else {
            // el sistema que tiene al expediente es uno distinto a EE
            throw new ExpedienteNoDisponibleException(
                "El expediente está bloqueado por el sistema " + expediente.getSistemaApoderado(),
                null);
          }
        }

      } else {
        throw new ProcesoFallidoException(
            "No se puede operar sobre un expediente que no se encuentra bloqueado.", null);
      }

    } else {
      throw new ExpedienteNoDisponibleException("El expediente se encuentra en Guarda Temporal.",
          null);
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "verificarUsuarioApodereExpediente(ExpedienteElectronico, String, String, boolean) - end");
    }
  }

  public void hacerDefinitivosCambiosExpediente(ExpedienteElectronicoDTO expediente)
      throws ParametroIncorrectoException {
    if (logger.isDebugEnabled()) {
      logger.debug("hacerDefinitivosCambiosExpediente(expediente={}) - start", expediente);
    }

    if (expediente != null) {
      hacerDefinitivosDocumentosOficiales(expediente);
      hacerDefinitivosDocumentosDeTrabajo(expediente);
      hacerDefinitivosExpedientesAsociados(expediente);
      expedienteElectronicoService.modificarExpedienteElectronico(expediente);
    } else {
      throw new ParametroIncorrectoException("El expediente es nulo.", null);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("hacerDefinitivosCambiosExpediente(ExpedienteElectronico) - end");
    }
  }

  /**
   * Pone en estado definitivo TRUE a todos los documentos oficiales vinculados
   * al expediente dado como parámetro.
   * 
   * @param codigoEEexpediente
   *          que posee los documentos oficiales
   * @throws ParametroIncorrectoException
   */
  private void hacerDefinitivosDocumentosOficiales(ExpedienteElectronicoDTO expediente) {
    if (logger.isDebugEnabled()) {
      logger.debug("hacerDefinitivosDocumentosOficiales(expediente={}) - start", expediente);
    }

    List<DocumentoDTO> listaDefinitivos = new ArrayList<>();

    for (DocumentoDTO documentoOficial : expediente.getDocumentos()) {
      if (documentoOficial != null) {
        documentoOficial.setDefinitivo(true);
        listaDefinitivos.add(documentoOficial);
      }
    }
    expediente.setDocumentos(listaDefinitivos);

    if (logger.isDebugEnabled()) {
      logger.debug("hacerDefinitivosDocumentosOficiales(ExpedienteElectronico) - end");
    }
  }

  /**
   * Pone en estado definitivo TRUE a todos los documentos de trabajo adjuntos
   * al expediente dado como parámetro.
   * 
   * @param expediente
   *          que posee los documentos de trabajo
   * @throws ParametroIncorrectoException
   */
  private void hacerDefinitivosDocumentosDeTrabajo(ExpedienteElectronicoDTO expediente) {
    if (logger.isDebugEnabled()) {
      logger.debug("hacerDefinitivosDocumentosDeTrabajo(expediente={}) - start", expediente);
    }

    List<ArchivoDeTrabajoDTO> listaDefinitivos = new ArrayList<>();
    for (ArchivoDeTrabajoDTO documentoDeTrabajo : expediente.getArchivosDeTrabajo()) {
      if (documentoDeTrabajo != null) {
        documentoDeTrabajo.setDefinitivo(true);
        listaDefinitivos.add(documentoDeTrabajo);
      }
    }
    expediente.setArchivosDeTrabajo(listaDefinitivos);

    if (logger.isDebugEnabled()) {
      logger.debug("hacerDefinitivosDocumentosDeTrabajo(ExpedienteElectronico) - end");
    }
  }

  /**
   * Pone en estado definitivo TRUE a todos los expedientes asociados al
   * expediente dado como parámetro.
   * 
   * @param expediente
   *          que posee los expedientes asociados
   * @throws ParametroIncorrectoException
   */
  private void hacerDefinitivosExpedientesAsociados(ExpedienteElectronicoDTO expediente) {
    if (logger.isDebugEnabled()) {
      logger.debug("hacerDefinitivosExpedientesAsociados(expediente={}) - start", expediente);
    }

    List<ExpedienteAsociadoEntDTO> listaDefinitivos = new ArrayList<>();
    for (ExpedienteAsociadoEntDTO expedienteAsociado : expediente.getListaExpedientesAsociados()) {
      if (expedienteAsociado != null) {
        expedienteAsociado.setDefinitivo(true);
        listaDefinitivos.add(expedienteAsociado);
      }
    }
    expediente.setListaExpedientesAsociados(listaDefinitivos);

    if (logger.isDebugEnabled()) {
      logger.debug("hacerDefinitivosExpedientesAsociados(ExpedienteElectronico) - end");
    }
  }

}
