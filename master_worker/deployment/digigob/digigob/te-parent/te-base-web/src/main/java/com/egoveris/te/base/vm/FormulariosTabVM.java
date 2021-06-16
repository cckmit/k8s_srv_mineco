package com.egoveris.te.base.vm;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.egoveris.ffdd.model.exception.DynFormException;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.service.iface.IExpedienteFormularioService;
import com.egoveris.te.base.service.iface.IExternalFormsService;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.ConstantesWeb.VISTA;
import com.egoveris.te.model.model.ExpedienteFormularioDTO;


@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class FormulariosTabVM {

  private static final Logger logger = LoggerFactory.getLogger(FormulariosTabVM.class);

  private static final String NAME_FORM = "nameForm";
  private static final String ID_TRANSACTION = "idTransaction";
  private static final String ID_EXPEDIENT = "idExpedient";
  private static final String IS_NEW = "isNew";
  private static final String PATH_FORM_WEB_ZUL = "/expediente/macros/formWeb.zul";

  @Wire
  Bandbox tipoFormBandbox;
 
  @WireVariable(ConstantesServicios.EXP_FORMULARIO_SERVICE)
  private IExpedienteFormularioService expedienteFormularioService;
  
  @WireVariable(ConstantesServicios.EXTERNAL_FORM_SERVICE)
  private IExternalFormsService externalFormsService;

  private ExpedienteElectronicoDTO expediente;
  private List<ExpedienteFormularioDTO> listaFormularios;
  private String totalFormularios;

  /**
   * Init de FormulariosTabVM. Inicializa servicios, setea el expediente y carga
   * la lista de formularios.
   * 
   * @param view
   * @param expediente
   *          Expediente
   */
  @AfterCompose
  public void init(@ContextParam(ContextType.VIEW) Component view,
      @ExecutionArgParam("expediente") ExpedienteElectronicoDTO expediente) {
    Selectors.wireComponents(view, this, false);
    Executions.getCurrent().getDesktop().setAttribute("expedienteElectronico", expediente);
    setExpediente(expediente);
    cargarListaFormularios(); 
   
  }

  /**
   * Comando que se ejecuta al presionar el boton de Vincular Formulario.
   * Levanta el popup con la vista formWeb.zul con el parametro isNew = 0
   * (vincular)
   */
  @Command
  public void onVincularFormulario() {
    if (expediente != null && tipoFormBandbox != null && tipoFormBandbox.getValue() != null
        && !StringUtils.isBlank(tipoFormBandbox.getValue())) {
      try {
        HashMap<String, Object> hm = new HashMap<>();
        hm.put(NAME_FORM, this.tipoFormBandbox.getValue());
        hm.put(ID_EXPEDIENT, expediente.getId());
        hm.put(IS_NEW, 0);
        Executions.getCurrent().getDesktop().setAttribute(ConstantesWeb.VISTA_ACTIVIDAD, VISTA.EDICION);
        Window vincularWindow = (Window) Executions.createComponents(PATH_FORM_WEB_ZUL, null, hm);
        vincularWindow.setClosable(true);
        vincularWindow.doModal();
      }
      catch (WrongValueException e) {
        logger.debug("Error en FormulariosTabVM.onVincularFormulario(): ", e);
        String message = e.getMessage();
        
        if (message != null && message.startsWith("[")) {
          message = message.replaceFirst("\\[", "");
        }
        
        Messagebox.show(message, Labels.getLabel("ee.vincularForm.noTransaction.title"), Messagebox.OK, Messagebox.ERROR);
      }
    }
  }

  /**
   * Comando que se ejecuta al presionar el boton de Visualizar. Levanta el
   * popup con la vista viewFormGenerate.zul con el parametro isNew = 1 (Ver)
   */
  @Command
  public void onVisualizarFormulario(
      @BindingParam("formulario") ExpedienteFormularioDTO formulario) {
    if (formulario != null) {
      HashMap<String, Object> hm = new HashMap<>();
      hm.put(NAME_FORM, formulario.getFormName());
      hm.put(ID_TRANSACTION, formulario.getIdDfTransaction());
      hm.put(IS_NEW, 1);
      Executions.getCurrent().getDesktop().setAttribute(ConstantesWeb.VISTA_ACTIVIDAD, VISTA.EDICION);
      Window visualizarWindow = (Window) Executions
          .createComponents("/expediente/macros/viewFormGenerate.zul", null, hm);
      visualizarWindow.setClosable(true);
      visualizarWindow.doModal();
    }
  }

  /**
   * Comando que se ejecuta al presionar el boton de Editar. Levanta el popup
   * con la vista formWeb.zul con el parametro isNew = 1 (Editar)
   */
  @Command
  public void onEditarFormulario(@BindingParam("formulario") ExpedienteFormularioDTO formulario) {
    if (formulario != null) {
      HashMap<String, Object> hm = new HashMap<>();
      hm.put(NAME_FORM, formulario.getFormName());
      hm.put(ID_TRANSACTION, formulario.getIdDfTransaction());
      hm.put(IS_NEW, 2);
      Executions.getCurrent().getDesktop().setAttribute(ConstantesWeb.VISTA_ACTIVIDAD, VISTA.EDICION);
      Window editarWindow = (Window) Executions.createComponents(PATH_FORM_WEB_ZUL, null, hm);
      editarWindow.setClosable(true);
      editarWindow.doModal();
    }
  }

  /**
   * Comando que se ejecuta al presionar el boton de Eliminar. Realiza una
   * pregunta de confirmacion que si es afirmativa, llamara al metodo
   * confirmaEliminarFormulario
   */
  @Command
  public void onEliminarFormulario(
      @BindingParam("formulario") final ExpedienteFormularioDTO formulario) {
    if (formulario != null) {
      Messagebox.show(Labels.getLabel("ee.tramitacion.composer.deleteConfirm"),
          Labels.getLabel("ee.tramitacion.composer.delete"), Messagebox.YES | Messagebox.NO,
          Messagebox.QUESTION, new EventListener<Event>() {

            @Override
            public void onEvent(Event event) {
              if (event.getName().equals(Messagebox.ON_YES)) {
                confirmaEliminarFormulario(formulario);
              }
            }
          });
    }
  }

  /**
   * Comando que se ejecuta al presionar el boton de Clonar. Levanta el popup
   * con la vista formWeb.zul con el parametro isNew = 3 (Clonar)
   */
  @Command
  public void onClonarFormulario(@BindingParam("formulario") ExpedienteFormularioDTO formulario) {
    if (formulario != null) {
      HashMap<String, Object> hm = new HashMap<>();
      hm.put(NAME_FORM, formulario.getFormName());
      hm.put(ID_TRANSACTION, formulario.getIdDfTransaction());
      hm.put(ID_EXPEDIENT, formulario.getIdEeExpedient());
      hm.put(IS_NEW, 3);
      Executions.getCurrent().getDesktop().setAttribute(ConstantesWeb.VISTA_ACTIVIDAD, VISTA.EDICION);
      Window clonarWindow = (Window) Executions.createComponents(PATH_FORM_WEB_ZUL, null, hm);
      clonarWindow.setClosable(true);
      clonarWindow.doModal();
    }
  }

  /**
   * Comando global que refresca la grilla de formularios
   */
  @GlobalCommand
  @NotifyChange({ "listaFormularios", "totalFormularios" })
  public void refreshListaFormularios() {
    cargarListaFormularios();
  }

  /**
   * Metodo que carga la lista de formularios. Es invocado al levantar la
   * pantalla o agregar/eliminar un formulario.
   */
  private void cargarListaFormularios() {
    if (expediente != null) {
      ExpedienteFormularioDTO expedienteFormulario = new ExpedienteFormularioDTO();
      expedienteFormulario.setIdEeExpedient(expediente.getId());

      try {
        setListaFormularios(
            expedienteFormularioService.buscarFormulariosPorExpediente(expedienteFormulario));
      } catch (DynFormException e) {
        logger.error("Error en FormulariosTabVM.cargarListaFormularios(): ", e);
        Messagebox.show(Labels.getLabel("te.formulario.cargar"), "Error",
            Messagebox.OK, Messagebox.ERROR);
      }
    }
  }

  /**
   * En el caso de confirmar eliminacion, elimina el formulario.
   * 
   * @param formulario
   */
  private void confirmaEliminarFormulario(ExpedienteFormularioDTO formulario) {
    try {
      expedienteFormularioService.eliminarFormulario(formulario);
      externalFormsService.eliminarFormularioFFDD(formulario);
      BindUtils.postGlobalCommand(null, null, "refreshListaFormularios", null);
    } catch (DynFormException e) {
      logger.error("Error en FormulariosTabVM.confirmaEliminarFormulario(): ", e);
      Messagebox.show(Labels.getLabel("te.formulario.eliminar"), "Error", Messagebox.OK,
          Messagebox.ERROR);
    }
  }

  // - LOGICA PERSONALIZADA GRILLA -

  /**
   * Retorna true si el formulario es definitivo. Esta logica se utiliza en la
   * vista para saber si puede editar/eliminar el formulario o si lo puede
   * clonar
   * 
   * @param formulario
   * @return true si es definitivo. False si no.
   */
  public boolean esFormularioDefinitivo(ExpedienteFormularioDTO formulario) {
    boolean definitivo = false;

    if (formulario != null && formulario.getIsDefinitive() != null
        && formulario.getIsDefinitive().equals(1)) {
      definitivo = true;
    }

    return definitivo;
  }

  // Getters - setters

  public ExpedienteElectronicoDTO getExpediente() {
    return expediente;
  }

  public void setExpediente(ExpedienteElectronicoDTO expediente) {
    this.expediente = expediente;
  }

  public List<ExpedienteFormularioDTO> getListaFormularios() {
    return listaFormularios;
  }

  public void setListaFormularios(List<ExpedienteFormularioDTO> listaFormularios) {
    this.listaFormularios = listaFormularios;
  }

  public String getTotalFormularios() {
    totalFormularios = "0";

    if (getListaFormularios() != null) {
      totalFormularios = String.valueOf(getListaFormularios().size());
    }

    return totalFormularios;
  }

  public void setTotalFormularios(String totalFormularios) {
    this.totalFormularios = totalFormularios;
  }

}
