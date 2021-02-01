/**
 * 
 */
package com.egoveris.te.base.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.task.Participation;
import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.sharedorganismo.base.model.ReparticionBean;
import com.egoveris.sharedorganismo.base.model.SectorInternoBean;
import com.egoveris.sharedorganismo.base.service.ReparticionServ;
import com.egoveris.sharedorganismo.base.service.SectorInternoServ;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.te.base.exception.AsignacionException;
import com.egoveris.te.base.model.BuzonBean;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.Grupo;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.WorkFlow;

@Service
@Transactional
public class AsignacionServiceImp implements AsignacionService {

  private static Logger logger = LoggerFactory.getLogger(AsignacionServiceImp.class);

  @Autowired
  private ProcessEngine processEngine;

  @Autowired
  private UsuariosSADEService usuariosSADEService;

  @Autowired
  private ExpedienteElectronicoService expedienteElectronicoService;

  @Autowired
  private HistorialOperacionService historialOperacionService;

  @Autowired
  private SectorInternoServ sectorServ;

  @Autowired
  private ReparticionServ reparticionServ;

  public void asignarTarea(Task task, String username) {
    if (logger.isDebugEnabled()) {
      logger.debug("asignarTarea(task={}, username={}) - start", task, username);
    }

    if (StringUtils.isNotEmpty(username)) {
      Calendar cal = Calendar.getInstance();
      cal.setTime(new Date());
      task.getCreateTime().setTime(cal.getTimeInMillis());
      this.processEngine.getTaskService().saveTask(task);
      Task tarea = processEngine.getTaskService().getTask(task.getId());
      if (tarea.getAssignee() == null) {
        processEngine.getTaskService().assignTask(task.getId(), username);
      } else {
        throw new AsignacionException("La tarea ya fue asignada.", null);
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("asignarTarea(Task, String) - end");
    }
  }

  @Override
  public void asignarExpedienteBandeja(ExpedienteElectronicoDTO expedienteElectronico,
      WorkFlow workFlow) {
    if (logger.isDebugEnabled()) {
      logger.debug("asignarExpedienteBandeja(expedienteElectronico={}, workFlow={}) - start",
          expedienteElectronico, workFlow);
    }

    Map<String, String> mapa = this.obtenerUsuarioOGrupo(expedienteElectronico, workFlow);
     if(mapa != null){
        String grupoAsociado = mapa.get("GRUPO");
      
        if (grupoAsociado != null) {
    
          String[] grupo = grupoAsociado.split("-");
    
          String r = grupo[0];
          String s = null;
    
          // buscar sector mesa para la reparticion
          if (grupo.length == 1) {
            ReparticionBean rep = reparticionServ.buscarReparticionPorCodigo(grupo[0]);
            if (rep != null) {
              List<SectorInternoBean> sectores = sectorServ.buscarSectoresPorReparticion(rep.getId());
              for (SectorInternoBean sec : sectores) {
                if (sec.isMesaVirtual()) {
                  s = sec.getCodigo();
                  break;
                }
              }
            }
          } else {
            s = grupo[1];
          }
    
          if (r == null || s == null) {
            logger
                .error("asignarExpedienteBandeja, error al encontrar Sector para la reparticion:" + r);
            return;
          }
    
          List<Usuario> listaUsuarios = usuariosSADEService.getTodosLosUsuariosXReparticionYSectorEE(r,
              s);
    
          String ROL_ASIGNADOR = "EE.ASIGNADOR";
    
          List<Usuario> usuariosAsignadores = usuariosSADEService.getUsuariosSegunRol(ROL_ASIGNADOR);
    
          List<Usuario> asignadoresPorGrupo = new ArrayList();
    
          for (Usuario user : listaUsuarios) {
            for (Usuario userAsig : usuariosAsignadores) {
              if (user.equals(userAsig)) {
                asignadoresPorGrupo.add(user);
              }
            }
          }
    
          if (asignadoresPorGrupo.size() > 0) {
            Usuario usuario = asignadoresPorGrupo.get(0);
            this.asignarTarea(workFlow.getWorkingTask(), usuario.getUsername());
            }
    
          }
     }
    if (logger.isDebugEnabled()) {
      logger.debug("asignarExpedienteBandeja(ExpedienteElectronico, WorkFlow) - end");
    }
  }

  /**
   * Dado un numero de expediente, verifica que tenga asaciado un usuario, caso
   * contrario Return: Hash - Si tiene user, el Usuario, caso contrario el Grupo
   * asociado
   * 
   * @param codigoExpediente
   */
  private Map<String, String> obtenerUsuarioOGrupo(ExpedienteElectronicoDTO expedienteElectronico,
      WorkFlow workFlow) {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerUsuarioOGrupo(expedienteElectronico={}, workFlow={}) - start",
          expedienteElectronico, workFlow);
    }

    Map<String, String> rta = new HashMap<>();

    // valido que ya tenga asociado un usuario, si ya lo tiene retorno null
    if (workFlow.getWorkingTask().getAssignee() != null) {
      rta.put("USUARIO", workFlow.getWorkingTask().getAssignee());

      if (logger.isDebugEnabled()) {
        logger.debug(
            "obtenerUsuarioOGrupo(ExpedienteElectronico, WorkFlow) - end - return value={}", rta);
      }
      return rta;
    }

    List<Participation> participants = workFlow.getTaskParticipations(workFlow.getWorkingTask());

    Participation participantRta = null;

    // FE de ERRATAS: DEBERIA tener una unica participant, existe un bug viejo,
    // de casos donde habian mas de uno, si llegase a encontrar algun caso de
    // estos, se toma la de mayor ID"
    if (participants.size() > 0) {
      for (Participation participant : participants) {
        if (participant.getGroupId() != null) {
          if (participantRta == null)
            participantRta = participant;

          if (Integer.parseInt(participantRta.getId()) > Integer.parseInt(participant.getId()))
            participantRta = participant;

        }

      }
    }

    if (participantRta != null) {
      rta.put("GRUPO", participantRta.getGroupId());

      if (logger.isDebugEnabled()) {
        logger.debug(
            "obtenerUsuarioOGrupo(ExpedienteElectronico, WorkFlow) - end - return value={}", rta);
      }
      return rta;
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "obtenerUsuarioOGrupo(ExpedienteElectronico, WorkFlow) - end - return value={null}");
    }
    return null;

  }

  /**
   * Dado un numero de expediente, verifica que tenga asaciado un usuario, caso
   * contrario Return: Hash - Si tiene user, el Usuario, caso contrario el Grupo
   * asociado
   * 
   * @param workflowID
   */
  public Map<String, String> obtenerUsuarioOGrupoAPartirDeBatch(String workflowID) {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerUsuarioOGrupoAPartirDeBatch(workflowID={}) - start", workflowID);
    }

    Map<String, String> rta = new HashMap<>();

    Task task = processEngine.getTaskService().createTaskQuery().executionId(workflowID).list()
        .get(0);

    List<Participation> participants = processEngine.getTaskService()
        .getTaskParticipations(task.getId());

    Participation participantRta = null;

    // FE de ERRATAS: DEBERIA tener una unica participant, existe un bug viejo,
    // de casos donde habian mas de uno, si llegase a encontrar algun caso de
    // estos, se toma la de mayor ID"
    if (participants.size() > 0) {
      for (Participation participant : participants) {
        if (participant.getGroupId() != null) {
          if (participantRta == null)
            participantRta = participant;

          if (Integer.parseInt(participantRta.getId()) > Integer.parseInt(participant.getId()))
            participantRta = participant;

        }

      }
    }

    if (participantRta != null) {
      rta.put("GRUPO", participantRta.getGroupId());

      if (logger.isDebugEnabled()) {
        logger.debug("obtenerUsuarioOGrupoAPartirDeBatch(String) - end - return value={}", rta);
      }
      return rta;
    }

    if (logger.isDebugEnabled()) {
      logger.debug("obtenerUsuarioOGrupoAPartirDeBatch(String) - end - return value={null}");
    }
    return null;

  }

  @Override
  public void asignarTarea(Task task, String usernameOrigen, String motivo,
      String usuarioDestino) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "asignarTarea(task={}, usernameOrigen={}, motivo={}, usuarioDestino={}) - start", task,
          usernameOrigen, motivo, usuarioDestino);
    }

    StringBuilder excutionID = new StringBuilder();

    if (task.getName().equals(ConstantesWeb.TASK_NOMBRE_PARALELO)) {
      String[] parser = StringUtils.split(task.getExecutionId(), '.');
      excutionID.append(parser[0]);
      excutionID.append(".");
      excutionID.append(parser[1]);
    } else {
      excutionID.append(task.getExecutionId());
    }

    ExpedienteElectronicoDTO expedienteElectronico = expedienteElectronicoService
        .buscarExpedienteElectronicoByIdTask(excutionID.toString());

    Map<String, String> detalles = new HashMap();

    detalles.put(ConstantesWeb.MOTIVO, motivo);
    
    if (expedienteElectronico != null) {
    	detalles.put(ConstantesWeb.ESTADO, expedienteElectronico.getEstado());
    }
    
    detalles.put(ConstantesWeb.DESTINATARIO, usuarioDestino);

    Usuario datosUsuario = usuariosSADEService.getDatosUsuario(usernameOrigen);

    detalles.put(ConstantesWeb.REPARTICION_USUARIO, datosUsuario.getCodigoReparticion());

    this.asignarTarea(task, usuarioDestino);
    
    if (expedienteElectronico != null) {
    	this.expedienteElectronicoService.guardarDatosEnHistorialOperacionP(expedienteElectronico,
    			usernameOrigen, detalles);
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("asignarTarea(Task, String, String, String) - end");
    }
  }

  @Override
  public Usuario asignarExpedienteBandejaDesdeBatchInboxGrupal(BuzonBean buzonBean,
      Map<String, Grupo> grupos, List<Usuario> usuariosAsignadores) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "asignarExpedienteBandejaDesdeBatchInboxGrupal(buzonBean={}, grupos={}, usuariosAsignadores={}) - start",
          buzonBean, grupos, usuariosAsignadores);
    }

    Map<String, String> mapa = this.obtenerUsuarioOGrupoAPartirDeBatch(buzonBean.getWorkflowId());

    String grupoAsociado = mapa.get("GRUPO");

    Usuario usuarioASignar;

    if (grupoAsociado != null) {

      String[] grupo = grupoAsociado.split("-");

      String r = grupo[0];
      String s = null;

      // buscar sector mesa para la reparticion
      if (grupo.length == 1) {
        ReparticionBean rep = reparticionServ.buscarReparticionPorCodigo(grupo[0]);
        if (rep != null) {
          List<SectorInternoBean> sectores = sectorServ.buscarSectoresPorReparticion(rep.getId());
          for (SectorInternoBean sec : sectores) {
            if (sec.isMesaVirtual()) {
              s = sec.getCodigo();
              break;
            }
          }
        }
      } else {
        s = grupo[1];
      }

      if (r == null || s == null) {
        logger.error(
            "asignarExpedienteBandejaDesdeBatchInboxGrupal, error al encontrar Sector para la reparticion:"
                + r);
        return null;
      }

      List<Usuario> listaUsuariosPorGrupo = usuariosSADEService
          .getTodosLosUsuariosXReparticionYSectorEE(r, s);

      if (listaUsuariosPorGrupo.size() == 0) {
        logger.error(
            "asignarExpedienteBandejaDesdeBatchInboxGrupal, no se encontraron usuarios para la reparticion - sector :"
                + r + "-" + s);
        return null;
      }

      if (usuariosAsignadores.size() == 0) {
        logger.error("asignarExpedienteBandejaDesdeBatchInboxGrupal, la reparticion - sector :" + r
            + "-" + s + " no posee usuarios");
        return null;
      }

      List<Usuario> asignadoresPorGrupo = new ArrayList<>();

      for (Usuario user : listaUsuariosPorGrupo) {
        for (Usuario userAsig : usuariosAsignadores) {
          if (user.getUsername().equals(userAsig.getUsername())) {
            asignadoresPorGrupo.add(user);
          }
        }
      }

      StringBuilder codigoGrupo = new StringBuilder();

      codigoGrupo.append(r);
      codigoGrupo.append("-");
      codigoGrupo.append(s);

      // Asigna al asignador, si no tiene hace un RR entre los usuarios del
      // grupo
      if (asignadoresPorGrupo.size() == 0) {
        analizarRR(grupos, codigoGrupo.toString(), listaUsuariosPorGrupo);
      }

      Task task = processEngine.getTaskService().createTaskQuery()
          .executionId(buzonBean.getWorkflowId()).list().get(0);

      // se asigna al primero de la lista, si es asignador, caso contrario usa
      // RR para asignar.
      if (asignadoresPorGrupo.size() > 0) {
        usuarioASignar = asignadoresPorGrupo.get(0);
        this.asignarTarea(task, usuarioASignar.getUsername());
      } else {
        // Busco el usuario con asignacion de menor valor
        usuarioASignar = buscarElMenor(grupos, codigoGrupo.toString());

        this.asignarTarea(task, usuarioASignar.getUsername());
      }
    } else {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "asignarExpedienteBandejaDesdeBatchInboxGrupal(BuzonBean, Map<String,Grupo>, List<Usuario>) - end - return value={null}");
      }
      return null;
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "asignarExpedienteBandejaDesdeBatchInboxGrupal(BuzonBean, Map<String,Grupo>, List<Usuario>) - end - return value={}",
          usuarioASignar);
    }
    return usuarioASignar;
  }

  private void analizarRR(Map<String, Grupo> grupos, String codigoGrupo,
      List<Usuario> listaUsuariosPorGrupo) {
    if (logger.isDebugEnabled()) {
      logger.debug("analizarRR(grupos={}, codigoGrupo={}, listaUsuariosPorGrupo={}) - start",
          grupos, codigoGrupo, listaUsuariosPorGrupo);
    }

    // Busco en el mapa de RR el grupo
    // Obtengo sus usuarios, si esta le incremento uno, si no lo pongo en la
    // lista de dupla
    Grupo g = grupos.get(codigoGrupo);

    if (g == null) {
      grupos.put(codigoGrupo, new Grupo());
      g = grupos.get(codigoGrupo);
    }

    Map<Usuario, Integer> asignacionRRobin = g.getAsignacionesPorUsuario();

    for (Usuario user : listaUsuariosPorGrupo) {
      Integer asignacion = asignacionRRobin.get(user);

      // Esto se hace porque el get(user) retorna null aunque este la instancia.
      Iterator it = asignacionRRobin.entrySet().iterator();

      while (it.hasNext()) {
        Map.Entry e = (Map.Entry) it.next();
        Usuario userAux = (Usuario) e.getKey();
        if (userAux.getUsername().equals(user.getUsername())) {
          asignacion = (Integer) e.getValue();
        }
      }

      if (asignacion == null) {
        asignacionRRobin.put(user, new Integer(0));
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("analizarRR(Map<String,Grupo>, String, List<Usuario>) - end");
    }
  }

  private Usuario buscarElMenor(Map<String, Grupo> grupos, String codigoGrupo) {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarElMenor(grupos={}, codigoGrupo={}) - start", grupos, codigoGrupo);
    }

    Grupo g = grupos.get(codigoGrupo);

    Map<Usuario, Integer> asignacionRRobin = g.getAsignacionesPorUsuario();

    Usuario menorUsuarioAsignacion = new Usuario();

    Iterator it = asignacionRRobin.keySet().iterator();

    boolean flag = true;

    while (it.hasNext()) {
      Usuario key = (Usuario) it.next();

      if (flag) {
        flag = false;
        menorUsuarioAsignacion = key;
      } else {
        Integer valorMenor = asignacionRRobin.get(menorUsuarioAsignacion);
        Integer valorAux = asignacionRRobin.get(key);
        if (valorAux.compareTo(valorMenor) < 0) {
          menorUsuarioAsignacion = key;
        }
      }
    }

    Integer valorActual = asignacionRRobin.get(menorUsuarioAsignacion);
    asignacionRRobin.put(menorUsuarioAsignacion, valorActual.intValue() + 1);

    if (logger.isDebugEnabled()) {
      logger.debug("buscarElMenor(Map<String,Grupo>, String) - end - return value={}",
          menorUsuarioAsignacion);
    }
    return menorUsuarioAsignacion;
  }
}
