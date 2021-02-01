package com.egoveris.te.base.service;

import java.util.Set;

import org.jbpm.api.ProcessEngine;
import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.service.iface.IConsultaEstadosPaseService;
import com.egoveris.te.base.util.ConstantesCommon;
import com.egoveris.te.model.exception.ParametroIncorrectoException;
import com.egoveris.te.model.exception.ProcesoFallidoException;

@Service
@Transactional
public class ConsultaEstadosPaseServiceImpl extends ExternalServiceAbstract
    implements IConsultaEstadosPaseService {
  private static final Logger logger = LoggerFactory
      .getLogger(ConsultaEstadosPaseServiceImpl.class);

  @Autowired
  private ProcessEngine processEngine;

  @Transactional
  public Set<String> consultaEstadosPosiblesPaseExpediente(String codigoEE)
      throws ParametroIncorrectoException, ProcesoFallidoException {
    if (logger.isDebugEnabled()) {
      logger.debug("consultaEstadosPosiblesPaseExpediente(codigoEE={}) - start", codigoEE);
    }

    Task task;
    ExpedienteElectronicoDTO expedienteElectronico;
    Set<String> listaEstados;

    expedienteElectronico = obtenerExpedienteElectronico(codigoEE);
    if (expedienteElectronico == null) {
      throw new ProcesoFallidoException(
          "No fue posible obtener el expediente electrónico de código " + codigoEE, null);
    } else {
      task = obtenerWorkingTask(expedienteElectronico);
      listaEstados = processEngine.getTaskService().getOutcomes(task.getId());
      listaEstados.remove(ConstantesCommon.ESTADO_SUBSANACION);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("consultaEstadosPosiblesPaseExpediente(String) - end - return value={}",
          listaEstados);
    }
    return listaEstados;
  }

  @Transactional
  public String consultaEstadoActualExpediente(String codigoEE)
      throws ParametroIncorrectoException, ProcesoFallidoException {
    if (logger.isDebugEnabled()) {
      logger.debug("consultaEstadoActualExpediente(codigoEE={}) - start", codigoEE);
    }

    ExpedienteElectronicoDTO expedienteElectronico;

    expedienteElectronico = obtenerExpedienteElectronico(codigoEE);
    if (expedienteElectronico == null) {
      throw new ProcesoFallidoException(
          "No fue posible obtener el expediente electrónico de código " + codigoEE, null);
    } else {
      String returnString = expedienteElectronico.getEstado();
      if (logger.isDebugEnabled()) {
        logger.debug("consultaEstadoActualExpediente(String) - end - return value={}",
            returnString);
      }
      return returnString;
    }
  }

  @Transactional
  public boolean esEstadoDePaseValido(String codigoEE, String estadoDestino)
      throws ParametroIncorrectoException, ProcesoFallidoException {
    if (logger.isDebugEnabled()) {
      logger.debug("esEstadoDePaseValido(codigoEE={}, estadoDestino={}) - start", codigoEE,
          estadoDestino);
    }

    ExpedienteElectronicoDTO expedienteElectronico;

    expedienteElectronico = obtenerExpedienteElectronico(codigoEE);
    if (expedienteElectronico == null) {
      throw new ProcesoFallidoException(
          "No fue posible obtener el expediente electrónico de código " + codigoEE, null);
    } else {
      Set<String> estadosPosibles = consultaEstadosPosiblesPaseExpediente(codigoEE);
      if (estadosPosibles.contains(estadoDestino.trim())) {
        if (logger.isDebugEnabled()) {
          logger.debug("esEstadoDePaseValido(String, String) - end - return value={}", true);
        }
        return true;
      } else {
        if (logger.isDebugEnabled()) {
          logger.debug("esEstadoDePaseValido(String, String) - end - return value={}", false);
        }
        return false;
      }
    }
  }

}
