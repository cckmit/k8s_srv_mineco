package com.egoveris.deo.base.service.impl;


import com.egoveris.deo.base.dao.IDatosUsuarioDAO;
import com.egoveris.deo.base.model.ProcesoLog;
import com.egoveris.deo.base.repository.ProcesoLogRepository;
import com.egoveris.deo.base.service.AvisoService;
import com.egoveris.deo.base.service.BorradoTemporal;
import com.egoveris.deo.base.service.GedoADestinatarios;
import com.egoveris.deo.base.service.ISupervisadosService;
import com.egoveris.deo.base.service.ITaskViewService;
import com.egoveris.deo.base.service.LdapAccessor;
import com.egoveris.deo.model.exception.NegocioException;
import com.egoveris.deo.model.model.DocumentoTemporalDTO;
import com.egoveris.deo.model.model.TareasUsuarioDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.TaskQuery;
import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.plus.common.exception.ApplicationException;


@Service
@Transactional
public class SupervisadosServiceImpl implements ISupervisadosService {

  @Autowired
  private IDatosUsuarioDAO datosUsuarioDao;
  @Autowired
  private LdapAccessor ldapAccessor;
  @Autowired
  private ProcessEngine processEngine;
  @Autowired
  private IUsuarioService usuarioService;
  @Autowired
  private GedoADestinatarios gedoADestinatarios;
  @Autowired
  private ProcesoLogRepository procesoLogRepo;
  @Autowired
  private BorradoTemporal borradoTemporal;
  @Autowired
  private AvisoService avisoService;
  @Autowired
  private ITaskViewService taskViewService;

  private static final Logger LOGGER = LoggerFactory.getLogger(SupervisadosServiceImpl.class);

  public List<TareasUsuarioDTO> obtenerResumenTareasSupervisados(String username) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerResumenTareasSupervisados(String) - start"); //$NON-NLS-1$
    }

    List<TareasUsuarioDTO> tareasSupervisados = new ArrayList<>();
    List<Usuario> supervisados;
    try {
      supervisados = this.datosUsuarioDao.obtenerUsuariosSupervisados(username);
    } catch (SQLException e) {
      LOGGER.error("obtenerResumenTareasSupervisados(String)", e); //$NON-NLS-1$

      throw new NegocioException("obtenerResumenTareasSupervisados()",e);
    }
    if (supervisados != null) {
      for (Usuario datosUsuario : supervisados) {
        datosUsuario
            .setNombreApellido(ldapAccessor.getNombreYApellido(datosUsuario.getUsername()));
        tareasSupervisados.add(new TareasUsuarioDTO(datosUsuario,
            taskViewService.countTasks(datosUsuario.getUsername())));
      }
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerResumenTareasSupervisados(String) - end"); //$NON-NLS-1$
    }
    return tareasSupervisados;
  }

  private void cargarResumenTareasSupervisados(List<Usuario> supervisados,
      List<TareasUsuarioDTO> tareasSupervisados) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER
          .debug("cargarResumenTareasSupervisados(List<Usuario>, List<TareasUsuarioDTO>) - start"); //$NON-NLS-1$
    }

    for (Usuario datosUsuario : supervisados) {
      datosUsuario.setNombreApellido(ldapAccessor.getNombreYApellido(datosUsuario.getUsername()));
      tareasSupervisados.add(new TareasUsuarioDTO(datosUsuario,
          taskViewService.countTasksComunicables(datosUsuario.getUsername())));

    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("cargarResumenTareasSupervisados(List<Usuario>, List<TareasUsuarioDTO>) - end"); //$NON-NLS-1$
    }
  }

  public List<TareasUsuarioDTO> obtenerResumenTareasSupervisadosCCOO(String username) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerResumenTareasSupervisadosCCOO(String) - start"); //$NON-NLS-1$
    }

    List<TareasUsuarioDTO> tareasSupervisados = new ArrayList<>();
    List<Usuario> listaUsuariosJefe;
    List<Usuario> supervisados;
    try {
      supervisados = this.datosUsuarioDao.obtenerUsuariosSupervisados(username);
      listaUsuariosJefe = this.datosUsuarioDao.obtenerUsuariosJefe(username);
    } catch (SQLException e) {
      LOGGER.error("obtenerResumenTareasSupervisadosCCOO(String)", e); //$NON-NLS-1$

      throw new NegocioException("unexpected error",e);
    }

    if (supervisados != null) {
      cargarResumenTareasSupervisados(supervisados, tareasSupervisados);
    }
    if (listaUsuariosJefe != null) {
      cargarResumenTareasSupervisados(listaUsuariosJefe, tareasSupervisados);
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerResumenTareasSupervisadosCCOO(String) - end"); //$NON-NLS-1$
    }
    return tareasSupervisados;
  }

  public void cancelarTarea(Task task, String usuarioCancelacion) throws ApplicationException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("cancelarTarea(Task, String) - start"); //$NON-NLS-1$
    }

    DocumentoTemporalDTO documentoTemporal = null;
    Map<String, String> datos = null;
    List<String> receptores = null;
    boolean envioAviso = false;

    try {

      String referencia = (String) processEngine.getExecutionService()
          .getVariable(task.getExecutionId(), Constantes.VAR_MOTIVO);

      if (referencia != null && !"".equals(referencia)) {
        datos = new HashMap<>();

        datos.put("referencia", referencia);
        datos.put("motivo", "Tarea cancelada por usuario: " + usuarioCancelacion);

        receptores = new ArrayList<>();
        receptores.add(task.getAssignee());

        envioAviso = true;
      }

      // 5.1- Se obtienen los documentos que seran borrados del
      // repositorio
      documentoTemporal = borradoTemporal.obtenerDocumentosABorrar(task.getExecutionId());
      LOGGER.info("Se han obtenido los datos necesarios para el borrado para la solicitud: "
          + task.getExecutionId());

      // 5.2- Borrar registros de la BBDD
      borradoTemporal.borrarRegistrosTemporales(task.getExecutionId());
      LOGGER.info("Se han borrado los registros de las tablas de GEDO para la solicitud: "
          + task.getExecutionId());

      try {
        // 5.3- Borrado de Archivos Temporales del repositorio
        borradoTemporal.borrarDocumentosTemporales(documentoTemporal);
        LOGGER.info("Se han borrado los archivos del repostorio para la solicitud: "
            + task.getExecutionId());
      } catch (Exception e) {
        LOGGER.error(
            "Ha ocurrido un error al borrar los archivos temporales del repositorio para la solicitud: "
                + task.getExecutionId() + " - " + e.getMessage(),
            e);
      }

      if (envioAviso) {
        avisoService.guardarAvisosFalla(receptores, datos, usuarioCancelacion);
      }

    } catch (Exception e) {
      LOGGER.error("Ha ocurrido un error al cancelar la tarea para la solicitud: "
          + task.getExecutionId() + " - " + e.getMessage(), e);
      throw new NegocioException("Ha ocurrido un error al cancelar la tarea: " + task.getExecutionId()
          + " - " + e.getMessage());
    }

    try {
//      gedoADestinatarios.notificarADestinatarios(task.getExecutionId(),
//          GedoADestinatarios.CANCELAR_DOCUMENTO);
    } catch (Exception e) {
      LOGGER.error("Ha ocurrido un error al rechazar la tarea para la solicitud: "
          + task.getExecutionId() + " - " + e.getMessage(), e);
      persistirLog(task.getExecutionId(),
          "No se pudo enviar a notificar a suscriptores - " + e.getMessage(),
          GedoADestinatarios.NOMBRE_PROCESO, Constantes.PROCESO_LOG_ESTADO_ERROR, "GEDO");
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("cancelarTarea(Task, String) - end"); //$NON-NLS-1$
    }
  }

  /**
   * Persistencia de un determinado proceso en la tabla de logs para OK o para
   * ERROR.
   * 
   * @param workflowId
   * @param numeroSade
   * @param descripcion
   * @param proceso
   * @param estado
   * @param reintento
   */
  private void persistirLog(String workflowId, String descripcion, String proceso, String estado,
      String sistemaOrigen) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("persistirLog(String, String, String, String, String) - start"); //$NON-NLS-1$
    }

    ProcesoLog log = new ProcesoLog();

    log.setDescripcion(descripcion);
    log.setEstado(estado);
    log.setFechaCreacion(new Date());
    log.setProceso(proceso);
    log.setSistemaOrigen(sistemaOrigen);
    log.setWorkflowId(workflowId);

    try {
      procesoLogRepo.save(log);
    } catch (Exception e) {
      LOGGER.error("Ha ocurrido un error al persistir el Log del WorflowId: " + workflowId + " - "
          + e.getMessage(), e);
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("persistirLog(String, String, String, String, String) - end"); //$NON-NLS-1$
    }
  }

  public void reasignarTarea(Task task, String username) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("reasignarTarea(Task, String) - start"); //$NON-NLS-1$
    }

    if (StringUtils.isNotEmpty(username)) {
      Usuario usuarioApoderado = null;
      try {
        if (usuarioService.licenciaActiva(username, new Date())) {
          usuarioApoderado = usuarioService.obtenerUsuario(username);
        }
      } catch (Exception e) {
        LOGGER.error("Mensaje de error", e);
      }
      if (usuarioApoderado != null) {
        processEngine.getTaskService().assignTask(task.getId(), usuarioApoderado.getUsername());
      } else {
        processEngine.getTaskService().assignTask(task.getId(), username);
      }
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("reasignarTarea(Task, String) - end"); //$NON-NLS-1$
    }
  }

  public List<Usuario> obtenerListaSupervisados(String username) throws NegocioException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerListaSupervisados(String) - start"); //$NON-NLS-1$
    }

    List<Usuario> supervisados;
    try {
      supervisados = this.datosUsuarioDao.obtenerUsuariosSupervisados(username);
    } catch (SQLException e) {
      LOGGER.error("obtenerListaSupervisados(String)", e); //$NON-NLS-1$

      throw new NegocioException("Error al obtener lista de supervisados",e);
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerListaSupervisados(String) - end"); //$NON-NLS-1$
    }
    return supervisados;
  }

  // TODO: Ojo que trae todo siempre y no hay cache
  public Usuario getSupervisado(String username, String usernameSupervisado) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("getSupervisado(String, String) - start"); //$NON-NLS-1$
    }

    List<Usuario> supervisados;
    supervisados = this.obtenerListaSupervisados(username);
    if (supervisados != null) {
      for (Usuario usuario : supervisados) {
        if (usuario.getUsername().equalsIgnoreCase(usernameSupervisado)) {
          if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("getSupervisado(String, String) - end"); //$NON-NLS-1$
          }
          return usuario;
        }
      }
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("getSupervisado(String, String) - end"); //$NON-NLS-1$
    }
    return null;
  }

  public List<Task> obtenerTareasSupervisadosCCOO(String username) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerTareasSupervisadosCCOO(String) - start"); //$NON-NLS-1$
    }

    Map<String, Object> parametrosConsulta = new HashMap<>();
    parametrosConsulta.put("criterio", TaskQuery.PROPERTY_CREATEDATE);
    parametrosConsulta.put("orden", "descending");
    parametrosConsulta.put("usuario", username);
    List<Task> returnList = taskViewService.buscarTasksPorUsuarioYComunicable(parametrosConsulta,
        null);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerTareasSupervisadosCCOO(String) - end"); //$NON-NLS-1$
    }
    return returnList;
  }

  public List<Usuario> obtenerUsuariosJefe(String username) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerUsuariosJefe(String) - start"); //$NON-NLS-1$
    }

    try {

      List<Usuario> returnList = this.datosUsuarioDao.obtenerUsuariosJefe(username);
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("obtenerUsuariosJefe(String) - end"); //$NON-NLS-1$
      }
      return returnList;
    } catch (SQLException e) {
      LOGGER.error("obtenerUsuariosJefe(String)", e); //$NON-NLS-1$

      throw new NegocioException("unexpected error",e);
    }

  }

}