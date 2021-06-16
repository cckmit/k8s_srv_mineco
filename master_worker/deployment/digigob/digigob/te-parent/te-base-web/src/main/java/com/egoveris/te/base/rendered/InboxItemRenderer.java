package com.egoveris.te.base.rendered;

import com.egoveris.te.base.model.ExpedienteElectronicoConSuspensionDTO;
import com.egoveris.te.base.model.SolicitudExpedienteDTO;
import com.egoveris.te.base.model.Tarea;
import com.egoveris.te.base.model.TareaParaleloDTO;
import com.egoveris.te.base.model.TrataDTO;
import com.egoveris.te.base.service.FusionService;
import com.egoveris.te.base.service.SolicitudExpedienteService;
import com.egoveris.te.base.service.TareaParaleloService;
import com.egoveris.te.base.service.TramitacionConjuntaService;
import com.egoveris.te.base.service.TrataService;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoConSuspensionService;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.IrASistemaOrigenLinkHandler;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.ProcessEngine;
import org.zkoss.util.resource.Labels;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Vbox;

public class InboxItemRenderer implements ListitemRenderer<Object> {
  private static final String ES_TAREA_GRUPAL = "esTareaGrupal";
  private static final String ANULAR_MODIFICAR = "Anular/Modificar Solicitud";
  private static final String INICIAR_EXPEDIENTE = "Iniciar Expediente";

  private SolicitudExpedienteService solicitudExpedienteService;
  private ExpedienteElectronicoService eeService;
  private TrataService trataService;
  private ExpedienteElectronicoConSuspensionService eeConSuspensionService;
  private TareaParaleloService tareaParaleloService;
  private TramitacionConjuntaService tramitacionConjuntaService;
  private FusionService fusionService;

  private ProcessEngine processEngine;
  private ExecutionService executionService;

  public void render(Listitem item, Object data, int arg1) throws Exception {
    solicitudExpedienteService = (SolicitudExpedienteService) SpringUtil
        .getBean(ConstantesServicios.SOLICITUD_EXPEDIENTE_SERVICE);
    eeService = (ExpedienteElectronicoService) SpringUtil
        .getBean(ConstantesServicios.EXP_ELECTRONICO_SERVICE);
    trataService = (TrataService) SpringUtil.getBean(ConstantesServicios.TRATA_SERVICE);
    eeConSuspensionService = (ExpedienteElectronicoConSuspensionService) SpringUtil
        .getBean(ConstantesServicios.EXP_ELECTRONICO_SUSP_SERVICE);
    tareaParaleloService = (TareaParaleloService) SpringUtil
        .getBean(ConstantesServicios.TAREA_PARALELO_SERVICE);
    tramitacionConjuntaService = (TramitacionConjuntaService) SpringUtil
        .getBean(ConstantesServicios.TRAMITACION_CONJUNTA_SERVICE);
    fusionService = (FusionService) SpringUtil.getBean(ConstantesServicios.FUSION_SERVICE);
    processEngine = (ProcessEngine) SpringUtil.getBean(ConstantesServicios.PROCESS_ENGINE_SERVICE);
    executionService = processEngine.getExecutionService();

    Tarea task = (Tarea) data;
    if (task != null) {
      Label ejecutar;
      Hbox hbox;
      Vbox vbox;
      List<ExpedienteElectronicoConSuspensionDTO> eeConSuspensions;
	    SolicitudExpedienteDTO solicitud;
	    Long idSolicitud = task.getIdSolicitud();
	    if (idSolicitud != null && idSolicitud.compareTo(0l) == 1) {
	      solicitud = solicitudExpedienteService.obtenerSolitudByIdSolicitud(idSolicitud);
	    } else {
	      // En caso de que el idSolicitud (sera el caso de una solicitud
	      // anterior a
	      // esta actualizacion) se obtendrá la
	      // solicitud seteada en el workflow y 7se setea el id para que se
	      // vayan
	      // actualizando todas las solicitudes que hayan quedado viejas
	      solicitud = (SolicitudExpedienteDTO) executionService
	          .getVariable(task.getTask().getExecutionId(), "solicitud");

        if (solicitud != null) {
          executionService.setVariable(task.getTask().getExecutionId(), "idSolicitud",
              solicitud.getId());
        }
      }

      Listcell currentCell;
      new Listcell("").setParent(item);
      // Nombre Tarea

      if (task != null && task.getNombreTarea() != null
          && task.getNombreTarea().equals("Guarda Temporal")) {

        this.eeService.actualizarWorkflowsEnGuardaTemporalCallaBleStatement();

        task.setNombreTarea(this.eeService
            .obtenerExpedienteElectronicoPorCodigo(task.getCodigoExpediente()).getEstado());

      }
      new Listcell(task.getNombreTarea()).setParent(item);
      // Fin Nombre Tarea

      // Ultima Modificacion
      if (task.getFechaModificacion() != null) {
        new Listcell(task.getFechaModificacion()).setParent(item);
      } else {
        new Listcell(task.getFechaCreacion()).setParent(item);
      }
      // Codigo Expediente
      new Listcell(task.getCodigoExpediente()).setParent(item);

      if (StringUtils.isEmpty(task.getCodigoTrata())
          && StringUtils.isEmpty(task.getDescripcionTrata())) {
        TrataDTO trataSugerida = null;
        Long idTrataSugerida;
        if (solicitud != null && solicitud.getIdTrataSugerida() != null) {
          idTrataSugerida = solicitud.getIdTrataSugerida();
          trataSugerida = trataService.obtenerTrataByIdTrataSugerida(idTrataSugerida);
        }

        if (trataSugerida == null) {
          // Codigo Trata
          new Listcell(task.getCodigoTrata()).setParent(item);
          // Descripcion
          new Listcell(task.getDescripcionTrata()).setParent(item);
        } else {
          // Codigo Trata
          new Listcell(trataSugerida.getCodigoTrata()).setParent(item);
          // Descripcion
          new Listcell(
              this.trataService.obtenerDescripcionTrataByCodigo(trataSugerida.getCodigoTrata()))
                  .setParent(item);
        }

      } else {
        // Codigo Trata
        new Listcell(task.getCodigoTrata()).setParent(item);
        // Descripcion
        new Listcell(task.getDescripcionTrata()).setParent(item);
      }

      // MOTIVO
      if (task.getNombreTarea().equals(ANULAR_MODIFICAR)) {
        String motivo = new String("");
        if (solicitud.getMotivoDeRechazo() != null) {
          motivo = solicitud.getMotivoDeRechazo();
        }
        Listcell listCellMotivo = new Listcell(motivoParseado(motivo));
        listCellMotivo.setTooltiptext(motivo);
        listCellMotivo.setParent(item);
      } else {
        if (task.getNombreTarea().equals(INICIAR_EXPEDIENTE)) {
          String motivo = null;
          if (solicitud != null && solicitud.getMotivo() != null
              && !solicitud.getMotivo().isEmpty()) {
            motivo = solicitud.getMotivo();
          } else if (solicitud != null) {
            motivo = solicitud.getMotivoExterno();
          }
          Listcell listCellMotivo = new Listcell(motivoParseado(motivo));
          listCellMotivo.setTooltiptext(motivo);
          listCellMotivo.setParent(item);
        } else {
          String motivo = task.getMotivo();
          Listcell listCellMotivo = new Listcell(motivoParseado(motivo));
          listCellMotivo.setTooltiptext(motivo);
          listCellMotivo.setParent(item);
        }
      }

      // Usuario Anterior
      new Listcell(task.getUsuarioAnterior()).setParent(item);

      hbox = new Hbox();
      vbox = new Vbox();
      vbox.setParent(hbox);

      currentCell = new Listcell();

      currentCell.setParent(item);

      eeConSuspensions = eeConSuspensionService.obtenerAllEEConSuspension();

      if (task.getCodigoExpediente() != null) {
        task.getCodigoExpediente();

        if (eeConSuspensions != null && validarExpSuspension(eeConSuspensions, task)) {
          ejecutar = new Label(Labels.getLabel("ee.inbox.tv"));
          ejecutar.setParent(vbox);
          hbox.setParent(currentCell);
          return;
        } else {
          dibujarHbox(hbox, vbox, currentCell);
        }
      } else {
        dibujarHbox(hbox, vbox, currentCell);
      }

      // ************************************************************************
      // ** REFACTORME
      // ** GAP: [ISSUED001] - Crear el component ee:link
      // id="boton.irasistemaexterno"
      // ** onclick="goToSO();" nombreAccion="GO_TO_SISTEMA_ORIGEN"
      // validation=""
      // ** Para ser re utilizado por los mecanismos de render, por el momento
      // se
      // usa un handler.
      // ************************************************************************
      if (!StringUtils.isEmpty(task.getCodigoTrata())
          && !StringUtils.isEmpty(task.getDescripcionTrata())) {
        IrASistemaOrigenLinkHandler irASistemaOrigenLink = new IrASistemaOrigenLinkHandler();
        TrataDTO t = trataService.buscarTrataByCodigo(task.getCodigoTrata());
        if (t != null && (t.getIntegracionSisExt())) {
          irASistemaOrigenLink.initComponentLink(task, hbox);
        }

      }
      /**
       * Agrega el Devolver en los casos que sea necesario. Vemos si la tarea
       * asignada viene de una tarea que tenga un grupo.
       */

      TareaParaleloDTO tareaParalelo = this.tareaParaleloService
          .buscarTareaEnParaleloByIdTask(task.getTask().getExecutionId());

      boolean esExpedienteEnTramitacionEspecial = false;
      if (task != null && task.getCodigoExpediente() != null
          && !task.getCodigoExpediente().isEmpty()) {
        esExpedienteEnTramitacionEspecial = validarExpediente(task.getCodigoExpediente());
      }

      if (((task.getTareaGrupal() != null) && (task.getTareaGrupal().equals(ES_TAREA_GRUPAL))
          && !esExpedienteEnTramitacionEspecial)
          || (tareaParalelo != null && tareaParalelo.getTareaGrupal())) {
        Hbox hboxInterno = new Hbox();

        Image runImage2 = new Image("/imagenes/egovReturn.png");
        org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(runImage2,
            "onClick=inboxWindow$InboxComposer.onDevolverTarea");
        Label devolver = new Label(Labels.getLabel("ee.inbox.devolver"));
        devolver.setTooltiptext(Labels.getLabel("ee.inbox.devolver.tooltip"));
        org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(devolver,
            "onClick=inboxWindow$InboxComposer.onDevolverTarea");
        runImage2.setParent(hboxInterno);
        devolver.setParent(hboxInterno);
        hboxInterno.setParent(vbox);
        vbox.setParent(hbox);
      }
    }
  }

  private boolean validarExpSuspension(List<ExpedienteElectronicoConSuspensionDTO> listaSuspension,
      Tarea t) {
    for (ExpedienteElectronicoConSuspensionDTO exp : listaSuspension) {
      if (exp.getCodigoCaratula() != null) {
        if (t.getCodigoExpediente().equals(exp.getCodigoCaratula())) {
          return true;
        }
      }
    }

    return false;
  }

  private void dibujarHbox(Hbox hbox, Vbox vbox, Listcell currentCell) {
    Label ejecutar;
    Image runImage;
    Hbox hboxInterno = new Hbox();
    runImage = new Image("/imagenes/play.png");
    ejecutar = new Label(Labels.getLabel("ee.inbox.ejecutar"));
    runImage.setParent(hboxInterno);
    ejecutar.setParent(hboxInterno);
    hboxInterno.setParent(vbox);
    org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(vbox,
        "onClick=inboxWindow$InboxComposer.onVerifyTipoDocumentoCaratulaDeEE");
    vbox.setParent(hbox);
    hbox.setParent(currentCell);
  }

  private String motivoParseado(String motivo) {
    int cantidadCaracteres = 20;
    String substringMotivo;
    if (motivo == null) {
      substringMotivo = "SIN MOTIVO";
      return substringMotivo;
    }
    if (motivo.length() > cantidadCaracteres) {
      substringMotivo = motivo.substring(0, cantidadCaracteres) + "...";
    } else {
      substringMotivo = motivo.substring(0, motivo.length());
    }
    return substringMotivo;
  }

  private boolean validarExpediente(String codigoExpedienteElectronico) {

    boolean esExpedienteEnTC = tramitacionConjuntaService
        .esExpedienteEnProcesoDeTramitacionConjunta(codigoExpedienteElectronico);

    boolean esExpedienteEnFusion = fusionService
        .esExpedienteEnProcesoDeFusion(codigoExpedienteElectronico);

    if (esExpedienteEnTC) {
      return esExpedienteEnTC;
    } else if (esExpedienteEnFusion) {
      return esExpedienteEnFusion;
    }

    return false;
  }

  public void setEeService(ExpedienteElectronicoService eeService) {
    this.eeService = eeService;
  }

  public ExpedienteElectronicoService getEeService() {
    return eeService;
  }

}