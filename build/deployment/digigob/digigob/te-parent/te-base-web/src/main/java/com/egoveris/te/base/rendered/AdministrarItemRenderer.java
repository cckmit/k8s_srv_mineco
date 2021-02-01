package com.egoveris.te.base.rendered;

import java.util.List;
import java.util.Map;

import org.jbpm.api.ProcessEngine;
import org.jbpm.api.task.Participation;
import org.jbpm.api.task.Task;
import org.zkoss.spring.SpringUtil;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Separator;

import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.util.ConstantesServicios;

@SuppressWarnings("rawtypes")
public class AdministrarItemRenderer implements ListitemRenderer {
  
  private  ProcessEngine processEngine;
  
  @SuppressWarnings("unchecked")
  public void render(final Listitem item, Object data, int arg1) throws Exception {
    final Map<String, ExpedienteElectronicoDTO> mapCheck = ((Map<String, ExpedienteElectronicoDTO>) Executions
        .getCurrent().getDesktop().getAttribute("mapCheck"));

    final ExpedienteElectronicoDTO expedienteElectronico = (ExpedienteElectronicoDTO) data; 

    final Checkbox seleccionarCheckbox = new Checkbox();
    seleccionarCheckbox.setChecked(false);

    Listcell seleccionar = new Listcell();
    seleccionarCheckbox.setParent(seleccionar);
    seleccionar.setParent(item);

    seleccionarCheckbox.addEventListener("onCheck", new EventListener() {

      @Override
      public void onEvent(Event event) throws Exception {
        Checkbox checkBox = (Checkbox) event.getTarget();
        
        if (checkBox.isChecked()) {
          mapCheck.put(expedienteElectronico.getId().toString(), expedienteElectronico);
        } else {
          mapCheck.remove(expedienteElectronico.getId().toString());
        }
      }
    });

    // TipoDocumento (EX) Expediente
    String tipoDocumento = expedienteElectronico.getTipoDocumento();
    new Listcell(tipoDocumento).setParent(item);

    // Anio Expediente
    Integer anioSADE = expedienteElectronico.getAnio();
    new Listcell(anioSADE.toString()).setParent(item);

    // Número Expediente
    Integer numeroSADE = expedienteElectronico.getNumero();
    new Listcell(numeroSADE.toString()).setParent(item);

    // Repartición Usuario
    String reparticionUsuario = expedienteElectronico.getCodigoReparticionUsuario();
    new Listcell(reparticionUsuario).setParent(item);

    // Ubicación actual
    String ubicacion = obtenerUbicacion(expedienteElectronico);
    new Listcell(ubicacion).setParent(item);

    // Descripción
    String sistOrigen = expedienteElectronico.getSistemaCreador();
    new Listcell(sistOrigen).setParent(item);

    // Accion a realizar
    Listcell currentCell = new Listcell();
    currentCell.setParent(item);
    Hbox hbox = new Hbox();
    Image runImage = new Image("/imagenes/ver_expediente.png");
    org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(runImage,
        "onClick=admexpedientesWindow$AdmExpedientesComposer.onVerExpediente");
    runImage.setTooltiptext(Labels.getLabel("ee.administrarexpediente.expediente.accionvisualizar"));
//    Label visualizar = new Label(
//        Labels.getLabel("ee.administrarexpediente.expediente.accionvisualizar"));
//    org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(visualizar,
//        "onClick=admexpedientesWindow$AdmExpedientesComposer.onVerExpediente");
    runImage.setParent(hbox);
//    visualizar.setParent(hbox);
    
    /* rehabilitar expediente */
    Image imageRehabilitar = new Image("/imagenes/page_white_text.png");    
    org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(imageRehabilitar,
        "onClick=admexpedientesWindow$AdmExpedientesComposer.onRehabilitarExp");    
    imageRehabilitar.setTooltiptext(Labels.getLabel("ee.administrarexpediente.expediente.accionRehabilitar"));
//    Label rehabilitar = new Label(
//        Labels.getLabel("ee.administrarexpediente.expediente.accionRehabilitar"));

//    org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(rehabilitar,
//        "onClick=admexpedientesWindow$AdmExpedientesComposer.onRehabilitarExp");    

    Separator separador = new Separator("horizontal");
    separador.setSpacing("15");
    separador.setParent(hbox);
    imageRehabilitar.setParent(hbox);
//    rehabilitar.setParent(hbox);
    /* agregar botonera en acciones */
    hbox.setParent(currentCell);
  }

  private String obtenerUbicacion(ExpedienteElectronicoDTO expedienteElectronico) {
    String ubicacion = "";
    
    if (expedienteElectronico.getIdWorkflow() != null) {
      Task task = getProcessEngine().getTaskService().createTaskQuery()
          .executionId(expedienteElectronico.getIdWorkflow()).uniqueResult();

      if (task != null && task.getAssignee() == null) {
        List<Participation> participants = processEngine.getTaskService()
            .getTaskParticipations(task.getId());
        for (Participation participant : participants) {
          if (participant.getGroupId() != null) {
            ubicacion += participant.getGroupId();
          }
        }
      } else {
        if (task != null) {
          ubicacion = task.getAssignee();
        }
      }
    }

    return ubicacion;
  }

  public ProcessEngine getProcessEngine() {
    if (processEngine == null) {
      processEngine = (ProcessEngine) SpringUtil.getBean(ConstantesServicios.PROCESS_ENGINE_SERVICE);
    }
    
    return processEngine;
  }

  public void setProcessEngine(ProcessEngine processEngine) {
    this.processEngine = processEngine;
  }
}
