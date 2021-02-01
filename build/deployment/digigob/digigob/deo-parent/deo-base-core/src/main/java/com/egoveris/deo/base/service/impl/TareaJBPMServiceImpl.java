package com.egoveris.deo.base.service.impl;

import com.egoveris.deo.base.dao.TaskGedoDAO;
import com.egoveris.deo.base.exception.EjecucionSiglaException;
import com.egoveris.deo.base.exception.ErrorBloquearTareaException;
import com.egoveris.deo.base.model.TareaBusqueda;
import com.egoveris.deo.base.service.TareaJBPMService;
import com.egoveris.deo.model.model.TareaBusquedaDTO;
import com.egoveris.deo.model.model.TareaJBPMDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.shared.map.ListMapper;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TareaJBPMServiceImpl extends JdbcDaoSupport implements TareaJBPMService {

  @Autowired
  private TaskGedoDAO taskGedoDAO;
  @Autowired
  private JdbcTemplate jdbcTemplate;
  @Autowired
  @Qualifier("jpaDataSource")
  private DataSource dataSource;

  @Autowired
  @Qualifier("deoCoreMapper")
  private Mapper mapper;

  private static final Logger logger = LoggerFactory.getLogger(TareaJBPMServiceImpl.class);

  private static final String ELIMINAR_JBPM_VARIABLES_BY_EXECUTION = "delete JBPM4_VARIABLE where execution_= ?";

  private static final String ELIMINAR_TAREAS_JBPM_POR_ACTIVIDAD = "delete JBPM4_TASK where assignee_= ? and activity_name_= ? ";

  private static final String ELIMINAR_JBPM_TASK_BY_EXECUTION = "delete JBPM4_TASK where execution_ = ? ";

  private static final String ACTUALIZAR_TAREA_JBPM_TASK_RECHAZO = "update JBPM4_TASK set name_ = ?, assignee_ = ?, form_ = ?, activity_name_ = ?,taskdefname_ = ? where execution_ = ? ";

  private static final String ACTUALIZAR_TAREA_JBPM_TASK_FIRMA = "update JBPM4_TASK set name_ = ?, assignee_ = ?, form_ = ?, activity_name_ = ?,taskdefname_ = ? where execution_ = ? ";

  private static final String BLOQUEAR_DESBLOQUEAR_TAREA_JBPM_TASK_FIRMA = "update JBPM4_TASK set assignee_ = ? where execution_id_ = ? ";

  private static final String OBTENER_ASSIGNEE_TAREA_JBPM_TASK_FIRMA = "select assignee_ from JBPM4_TASK where execution_id_ = ? ";

  @PostConstruct
  private void initialize() {
    setDataSource(dataSource);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<TareaJBPMDTO> buscarTareasDelUsuarioPorActividad(String usuarioBaja,
      String actividad) {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarTareasDelUsuarioPorActividad(String, String) - start"); //$NON-NLS-1$
    }

    List<TareaJBPMDTO> returnList = ListMapper.mapList(
        taskGedoDAO.buscarTareasDelUsuarioPorActividad(usuarioBaja, actividad), mapper,
        TareaJBPMDTO.class);

    if (logger.isDebugEnabled()) {
      logger.debug("buscarTareasDelUsuarioPorActividad(String, String) - end"); //$NON-NLS-1$
    }
    return returnList;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<TareaJBPMDTO> buscarTareasDelUsuarioPorActividadRevisionRechazo(String usuarioBaja) {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarTareasDelUsuarioPorActividadRevisionRechazo(String) - start"); //$NON-NLS-1$
    }

    List<TareaJBPMDTO> returnList = ListMapper.mapList(
        taskGedoDAO.buscarTareasDelUsuarioPorActividadRevisionRechazo(usuarioBaja), mapper,
        TareaJBPMDTO.class);
    if (logger.isDebugEnabled()) {
      logger.debug("buscarTareasDelUsuarioPorActividadRevisionRechazo(String) - end"); //$NON-NLS-1$
    }
    return returnList;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<TareaJBPMDTO> buscarTareasPorUsuarioFirmante(String usuario)
      throws EjecucionSiglaException {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarTareasPorUsuarioFirmante(String) - start"); //$NON-NLS-1$
    }

    List<TareaJBPMDTO> returnList = ListMapper
        .mapList(taskGedoDAO.buscarTareasPorUsuarioFirmante(usuario), mapper, TareaJBPMDTO.class);
    if (logger.isDebugEnabled()) {
      logger.debug("buscarTareasPorUsuarioFirmante(String) - end"); //$NON-NLS-1$
    }
    return returnList;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<TareaJBPMDTO> buscarTareasPorUsuarioRevisor(String usuario, String actividad)
      throws EjecucionSiglaException {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarTareasPorUsuarioRevisor(String, String) - start"); //$NON-NLS-1$
    }

    List<TareaJBPMDTO> returnList = ListMapper.mapList(
        taskGedoDAO.buscarTareasPorUsuarioRevisor(usuario, actividad), mapper, TareaJBPMDTO.class);

    if (logger.isDebugEnabled()) {
      logger.debug("buscarTareasPorUsuarioRevisor(String, String) - end"); //$NON-NLS-1$
    }
    return returnList;
  }

  @Override
  public void eliminarVariablesJBPM(String execution) throws EjecucionSiglaException {
    if (logger.isDebugEnabled()) {
      logger.debug("eliminarVariablesJBPM(String) - start"); //$NON-NLS-1$
    }

    Object[] parametros = new Object[1];
    parametros[0] = execution;

    this.jdbcTemplate.update(ELIMINAR_JBPM_VARIABLES_BY_EXECUTION, parametros);

    if (logger.isDebugEnabled()) {
      logger.debug("eliminarVariablesJBPM(String) - end"); //$NON-NLS-1$
    }
  }

  @Override
  public void eliminarLasTareasDelUsuarioPorActividad(String usuarioBaja, String actividad)
      throws EjecucionSiglaException {
    if (logger.isDebugEnabled()) {
      logger.debug("eliminarLasTareasDelUsuarioPorActividad(String, String) - start"); //$NON-NLS-1$
    }

    Object[] parametros = new Object[1];
    parametros[0] = usuarioBaja;
    parametros[1] = actividad;

    this.jdbcTemplate.update(ELIMINAR_TAREAS_JBPM_POR_ACTIVIDAD, parametros);

    if (logger.isDebugEnabled()) {
      logger.debug("eliminarLasTareasDelUsuarioPorActividad(String, String) - end"); //$NON-NLS-1$
    }
  }

  @Override
  public void eliminarTarea(String execution) throws EjecucionSiglaException {
    if (logger.isDebugEnabled()) {
      logger.debug("eliminarTarea(String) - start"); //$NON-NLS-1$
    }

    Object[] parametros = new Object[1];
    parametros[0] = execution;

    this.jdbcTemplate.update(ELIMINAR_JBPM_TASK_BY_EXECUTION, parametros);

    if (logger.isDebugEnabled()) {
      logger.debug("eliminarTarea(String) - end"); //$NON-NLS-1$
    }
  }

  @Override
  public void generarTareaDeRechazo(String usuario, String execution)
      throws EjecucionSiglaException {
    if (logger.isDebugEnabled()) {
      logger.debug("generarTareaDeRechazo(String, String) - start"); //$NON-NLS-1$
    }

    Object[] parametros = new Object[1];
    parametros[0] = Constantes.TASK_RECHAZADO;
    parametros[1] = usuario;
    parametros[2] = Constantes.FORM_RECHAZADO_TEMPLATE;
    parametros[3] = Constantes.TASK_RECHAZADO;
    parametros[4] = Constantes.TASK_RECHAZADO;
    parametros[5] = execution;

    this.jdbcTemplate.update(ACTUALIZAR_TAREA_JBPM_TASK_RECHAZO, parametros);

    if (logger.isDebugEnabled()) {
      logger.debug("generarTareaDeRechazo(String, String) - end"); //$NON-NLS-1$
    }
  }

  @Override
  public void bloquearTarea(String operacion, String execution)
      throws ErrorBloquearTareaException {
    if (logger.isDebugEnabled()) {
      logger.debug("bloquearTarea(String, String) - start"); //$NON-NLS-1$
    }

    List<Map<String, Object>> results = this.jdbcTemplate
        .queryForList(OBTENER_ASSIGNEE_TAREA_JBPM_TASK_FIRMA, execution);

    String assignee = "";
    for (Map<String, Object> result : results) {
      assignee = (String) result.get("ASSIGNEE_");
    }
    if (assignee.isEmpty()) {
      throw new ErrorBloquearTareaException("El process Id No Existe :" + execution);
    }
    if (!assignee.isEmpty() && !".bloqueado".contains(assignee)
        && "bloquear".equalsIgnoreCase(operacion)) {
      Object[] parametros = new Object[1];
      parametros[0] = assignee + ".bloqueado";
      parametros[1] = execution;
      this.jdbcTemplate.update(BLOQUEAR_DESBLOQUEAR_TAREA_JBPM_TASK_FIRMA, parametros);

    } else if (!assignee.isEmpty() && assignee.contains(".bloqueado")
        && "desbloquear".equalsIgnoreCase(operacion)) {
      Object[] parametros = new Object[1];
      assignee = assignee.substring(0, assignee.indexOf("."));
      parametros[0] = assignee;
      parametros[1] = execution;
      this.jdbcTemplate.update(BLOQUEAR_DESBLOQUEAR_TAREA_JBPM_TASK_FIRMA, parametros);

    }
    if (logger.isDebugEnabled()) {
      logger.debug("bloquearTarea(String, String) - end"); //$NON-NLS-1$
    }
  }

  @Override
  public void generarTareaDeFirma(String usuario, String execution)
      throws EjecucionSiglaException {
    if (logger.isDebugEnabled()) {
      logger.debug("generarTareaDeFirma(String, String) - start"); //$NON-NLS-1$
    }

    Object[] parametros = new Object[1];
    parametros[0] = Constantes.TASK_FIRMAR_DOCUMENTO;
    parametros[1] = usuario;
    parametros[2] = Constantes.FORM_TAREA_FIRMA;
    parametros[3] = Constantes.TASK_FIRMAR_DOCUMENTO;
    parametros[4] = Constantes.TASK_FIRMAR_DOCUMENTO;
    parametros[5] = execution;

    this.jdbcTemplate.update(ACTUALIZAR_TAREA_JBPM_TASK_FIRMA, parametros);

    if (logger.isDebugEnabled()) {
      logger.debug("generarTareaDeFirma(String, String) - end"); //$NON-NLS-1$
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<TareaBusquedaDTO> buscarTareasPorUsuarioInvolucrado(
      Map<String, Object> parametrosConsulta) {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarTareasPorUsuarioInvolucrado(Map<String,Object>) - start"); //$NON-NLS-1$
    }

    List<TareaBusquedaDTO> returnList = ListMapper.mapList(
        this.taskGedoDAO.buscarTareasPorUsuarioInvolucrado(parametrosConsulta), mapper,
        TareaBusqueda.class);

    if (logger.isDebugEnabled()) {
      logger.debug("buscarTareasPorUsuarioInvolucrado(Map<String,Object>) - end"); //$NON-NLS-1$
    }
    return returnList;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<TareaBusquedaDTO> buscarTareasPorUsuarioInvolucradoSinFiltrar(
      Map<String, Object> parametrosConsulta) {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarTareasPorUsuarioInvolucradoSinFiltrar(Map<String,Object>) - start"); //$NON-NLS-1$
    }

    List<TareaBusquedaDTO> returnList = ListMapper.mapList(
        this.taskGedoDAO.buscarTareasPorUsuarioInvolucradoSinFiltrar(parametrosConsulta), mapper,
        TareaBusquedaDTO.class);
    if (logger.isDebugEnabled()) {
      logger.debug("buscarTareasPorUsuarioInvolucradoSinFiltrar(Map<String,Object>) - end"); //$NON-NLS-1$
    }
    return returnList;
  }

  @Override
  public Integer cantidadTotalTareasPorUsuarioInvolucrado(Map<String, Object> parametrosConsulta) {
    if (logger.isDebugEnabled()) {
      logger.debug("cantidadTotalTareasPorUsuarioInvolucrado(Map<String,Object>) - start"); //$NON-NLS-1$
    }

    Integer returnInteger = this.taskGedoDAO
        .cantidadTotalTareasPorUsuarioInvolucrado(parametrosConsulta);
    if (logger.isDebugEnabled()) {
      logger.debug("cantidadTotalTareasPorUsuarioInvolucrado(Map<String,Object>) - end"); //$NON-NLS-1$
    }
    return returnInteger;
  }

}
