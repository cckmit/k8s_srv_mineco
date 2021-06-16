package com.egoveris.te.base.vm;

import java.util.HashMap;
import java.util.List;

import javax.script.ScriptException;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;
import org.jbpm.api.cmd.Environment;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.model.ActivityImpl;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Window;

import com.egoveris.deo.model.model.TipoDocumentoDTO;
import com.egoveris.plugins.manager.PluginManager;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.te.base.helper.EnviarPaseHelper;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.service.UsuariosSADEService;
import com.egoveris.te.base.service.WorkFlowService;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.ReflectionUtil;
import com.egoveris.te.base.util.ScriptType;
import com.egoveris.te.base.util.TramitacionHelper;
import com.egoveris.te.base.util.WorkFlowScriptUtils;
import com.egoveris.te.web.ee.satra.pl.helpers.states.IVisualState;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class TramitacionVM extends SelectorComposer<Component> {

  private static final Logger logger = LoggerFactory.getLogger(TramitacionVM.class);
  private static final long serialVersionUID = 1L;
  private static final String LITERAL_ATENCION = "Atención";

  @Wire
  private Window tramitacionWindow;

  private TramitacionHelper tramitacionHelper;
  
  @WireVariable(ConstantesServicios.PLUGIN_MANAGER)
  private PluginManager pluginManager;
  
  @WireVariable(ConstantesServicios.WORKFLOW_SERVICE)
  private WorkFlowService workflowService;
  
  @WireVariable(ConstantesServicios.USUARIOS_SADE_SERVICE)
  private UsuariosSADEService usuariosSADEService;

  private ExpedienteElectronicoDTO expediente;
  private String codigoExpediente;
  private Task task;
  private boolean soloLectura; 


  /**
   * Init de TramitacionVM. Instancia servicios y setea el expediente y codigo
   * de expediente recibidos
   * 
   * @param expediente
   *          Expediente Electronico
   * @param codigoExpediente
   *          Codigo del Expediente Electronico
   */
  @Init
  public void init(@ExecutionArgParam("expediente") ExpedienteElectronicoDTO expediente,
      @ExecutionArgParam("codigoExpediente") String codigoExpediente) {
	  
    setExpediente(expediente);
    setCodigoExpediente(codigoExpediente);
    
    String loggedUsername = Executions.getCurrent().getSession()
        .getAttribute(ConstantesWeb.SESSION_USERNAME).toString();
    tramitacionHelper = new TramitacionHelper(loggedUsername, expediente);
  }

  /**
   * Luego del init, obtiene la tarea relacionada con el expediente y pinta el
   * tab correspondiente al formulario dinamico
   * 
   * @param view
   */
  @AfterCompose
  public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
    Selectors.wireComponents(view, this, false);

    if (expediente != null) {
    	try {
	      setTask(obtenerTarea(expediente));
	      
	      if (expediente.getEstado() != null && !expediente.getEstado().equals(ConstantesWeb.ESTADO_GUARDA_TEMPORAL)) {
          WorkFlowScriptUtils.getInstance().executeScript(ScriptType.INITIATION, expediente, null, Executions.getCurrent().getRemoteUser());
        }
	      
	      evaluateSoloLectura(expediente);
    	} catch (ScriptException e) {
			logger.error("", e);
		}
    }
  }

  /**
   * Comando que se lanza al cancelar o salir sin pase (cierra la ventana)
   */
  @Command
  public void onCancelar() {
    if (expediente != null && expediente.getListaExpedientesAsociados() != null) {
      expediente.getListaExpedientesAsociados().clear();
    }

    tramitacionWindow.onClose();
  }

  /**
   * Comando que se lanza al enviar pase. Para ello abre la ventana de envio de
   * pase (codigo original en TramitacionComposer.onEnviarTramitacion(). Existe
   * una breve refactorizacion de los eventos, en EnviarPaseHelper.
   * 
   * @throws InterruptedException
   */
  @Command
  public void onEnviarPase() throws InterruptedException {
    try {
      if (expediente != null) {
      //if (expediente != null && tramitacionHelper.getActiveState().validateBeforePass()) {
        HashMap<String, Object> args = new HashMap<>();
        args.put("expedienteElectronico", expediente);

        String estadoAnterior = "";

        if (getTask() != null) {
          estadoAnterior = getTask().getActivityName();
        }

        args.put("estadoAnterior", estadoAnterior);
        args.put("tramitacionWindow", tramitacionWindow);
        Executions.getCurrent().getDesktop().setAttribute("selectedTask", getTask());

        Window enviarPaseWindow = (Window) Executions.createComponents("/pase/envio.zul", null,
            args);
        enviarPaseWindow.setParent(this.tramitacionWindow);
        enviarPaseWindow.setPosition("center");
        enviarPaseWindow.setClosable(true);
        
        EnviarPaseHelper enviarPaseHelper = new EnviarPaseHelper(tramitacionHelper,
            enviarPaseWindow, tramitacionWindow);

        enviarPaseWindow.addEventListener(Events.ON_USER, enviarPaseHelper.onUserEvent());
        enviarPaseWindow.addEventListener(Events.ON_CLOSE, enviarPaseHelper.onCancelOrExitEvent());

        Component cancelarBtn = enviarPaseWindow.getFellow("cancelar");
        cancelarBtn.addEventListener(Events.ON_CLICK, enviarPaseHelper.onCancelOrExitEvent());

        tramitacionHelper.getActiveState().customizePase(enviarPaseWindow);
        enviarPaseWindow.doModal();
      }
    } catch (Exception e) {
      logger.error("Error en TramitacionVM.onEnviarPase(): ", e);
      Messagebox.show(Labels.getLabel("te.tramitacion.error.cargarenvio"), "Error",
          Messagebox.OK, Messagebox.ERROR);
      throw e;
    }
  }
    
  /**
   * Metodo que evalua si el expediente se abre en modo de solo lectura
   * 
   * @param expediente
   */
  private void evaluateSoloLectura(ExpedienteElectronicoDTO expediente) {
		String loggedUsername = Executions.getCurrent().getSession().getAttribute(ConstantesWeb.SESSION_USERNAME).toString();
    
		if (expediente != null && expediente.getEstado() != null && ConstantesWeb.ESTADO_GUARDA_TEMPORAL.equalsIgnoreCase(expediente.getEstado())) {
      // El subproceso esta en estado guarda temporal
      setSoloLectura(true);
      Messagebox.show(
    		  Labels.getLabel("te.tramitacion.lecturaGT"),
    		  Labels.getLabel("ee.tramitacion.informacion.documentoNoAsociado"), Messagebox.OK, Messagebox.EXCLAMATION);
    }
    else if (expediente != null && expediente.getUsuarioCreador() != null && !expediente.getUsuarioCreador().equalsIgnoreCase(loggedUsername)) {
      // El subproceso pertenece a otro usuario
      setSoloLectura(true);
      Messagebox.show(
    		  Labels.getLabel("te.tramitacion.lecturaUsuario"),
          Labels.getLabel("ee.tramitacion.informacion.documentoNoAsociado"), Messagebox.OK, Messagebox.EXCLAMATION);
    } if(expediente != null && expediente.getBlocked()) {
      setSoloLectura(true);
      Messagebox.show(
    		  Labels.getLabel("te.tramitacion.lecturaExterna"),
          Labels.getLabel("ee.tramitacion.informacion.documentoNoAsociado"), Messagebox.OK, Messagebox.EXCLAMATION);
    } else {
        Boolean isTemp = (Boolean) workflowService.getProcessEngine().execute(new  org.jbpm.api.cmd.Command<Object>() {
              private static final long serialVersionUID = 1L;
              @Override
              public Object execute(final Environment env) {
                final ExecutionImpl exe = (ExecutionImpl) workflowService.getProcessEngine()
                    .getExecutionService().findExecutionById(expediente.getIdWorkflow());
                if (null != exe) {
                  ActivityImpl act = exe.getActivity();
                  if(CollectionUtils.isNotEmpty(act.getVariableDefinitions())){
                    return true;
                  }
                }
                return false;
              }
            });
          
          if(isTemp){
            setSoloLectura(true);
          Messagebox.show(
        		  Labels.getLabel("te.tramitacion.bloqueado.tarea"),
              Labels.getLabel("ee.tramitacion.informacion.documentoNoAsociado"), Messagebox.OK, Messagebox.EXCLAMATION);
          }
    }
  }
  
  /**
   * Obtiene las instancias de "VisualState" relacionadas al workflow y estado
   * dados.
   * 
   * @param workflowName
   * @param stateName
   * @return
   */
  private List<IVisualState> getVisualStates(final String workflowName, final String stateName) {
    List<IVisualState> visualStates = ReflectionUtil.searchClasses(IVisualState.class);

    List<Object> instances = pluginManager.getInstancesOf(new Predicate<Class<?>>() {
      @Override
      public boolean evaluate(Class<?> clazz) { // CLASS EVALUATOR
        return ReflectionUtil.hasInterface(clazz, IVisualState.class);
      }
    }, new Predicate<Object>() { // OBJECT EVALUATOR
      @Override
      public boolean evaluate(Object object) {
        return (object != null) && ((IVisualState) object).is(workflowName, stateName);
      }
    });

    for (Object obj : instances) {
      visualStates.add((IVisualState) obj);
    }

    return visualStates;
  }

  /**
   * Obtiene la tarea relacionada al expediente electronico. En realidad el
   * expediente posee un Id de ejecucion JBPM y se obtiene la tarea con dicho id
   * de ejecucion
   * 
   * @param expediente
   * @return
   */
  private Task obtenerTarea(ExpedienteElectronicoDTO expediente) {
    Task tarea = null;

    List<Task> listaTareas = tramitacionHelper.getProcessEngine().getTaskService()
        .createTaskQuery().executionId(expediente.getIdWorkflow()).list();

    if (!listaTareas.isEmpty()) {
      tarea = listaTareas.get(0);
    }

    return tarea;
  }

  // Getters - setters

  public ExpedienteElectronicoDTO getExpediente() {
    return expediente;
  }

  public void setExpediente(ExpedienteElectronicoDTO expediente) {
    this.expediente = expediente;
  }

  public String getCodigoExpediente() {
    return codigoExpediente;
  }

  public void setCodigoExpediente(String codigoExpediente) {
    this.codigoExpediente = codigoExpediente;
  }
  
  public Task getTask() {
    return task;
  }

  public void setTask(Task task) {
    this.task = task;
  }

  public boolean isSoloLectura() {
    return soloLectura;
   }

   public void setSoloLectura(boolean soloLectura) {
     this.soloLectura = soloLectura;
   }

}
