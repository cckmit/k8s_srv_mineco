package com.egoveris.te.base.vm;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.ScriptException;

import org.hibernate.service.spi.ServiceException;
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
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.egoveris.te.base.helper.OperacionesHelper;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.OperacionDTO;
import com.egoveris.te.base.model.SubProcesoDTO;
import com.egoveris.te.base.model.SubProcesoOperacionDTO;
import com.egoveris.te.base.service.OperacionService;
import com.egoveris.te.base.service.WorkFlowService;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.util.BloqueoOperacion;
import com.egoveris.te.base.util.ConstantesCommon;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.ScriptType;
import com.egoveris.te.base.util.WorkFlowScriptUtils;
import com.egoveris.te.model.exception.ParametroIncorrectoException;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ResumenOperacionVM extends SelectorComposer<Component> {

  /**
   * Logger for this class
   */
  private static final Logger logger = LoggerFactory.getLogger(ResumenOperacionVM.class);
  private static final long serialVersionUID = 1L;

  private static final String LITERAL_ERROR = "Error";
  private static final String LITERAL_INFO = "Información";
  private static final String ESTADO_CIERRE = "Cierre";

  // Pantalla
  @Wire("#resumenOperacionWindow")
  private Window window;
  private Window ejecutarSubprocesoWindow;

  private OperacionDTO operacion;

  private List<String> transiciones;
  private String estadoSelected;

  private int indexSubProcessSel = -1;
  private String comboSubProcessVal = "";
  private List<SubProcesoDTO> subProcess;
  private List<SubProcesoDTO> subProcessAutomatic;

  private List<SubProcesoOperacionDTO> subProcessOp;

  // Servicio
  @WireVariable(ConstantesServicios.EXP_ELECTRONICO_SERVICE)
  private ExpedienteElectronicoService expedienteElectronicoService;
  
  @WireVariable(ConstantesServicios.OPERACION_SERVICE)
  private OperacionService operacionService;
  
  @WireVariable(ConstantesServicios.WORKFLOW_SERVICE)
  private WorkFlowService workflowService;


  /**
   * Init de IniciarSubprocesosVM Inicializa los beans de servicio y carga los
   * datos iniciales de la pantalla
   * 
   * @param operacion
   *          Dto con los datos de operacion
   */
  @Init
  public void init(@ExecutionArgParam("operacion") OperacionDTO operacion) {
    if (logger.isDebugEnabled()) {
      logger.debug("init(Component, OperacionDTO) - start");
    }

    if (!isOpBorrador(operacion)) {
      setOperacion(operacion);
    } else {
      redirectBorrador(operacion);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("init(Component, OperacionDTO) - end");
    }
  }

  @AfterCompose
  public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
    Selectors.wireComponents(view, this, false);

    if (operacion != null) {
      cargarDatos();
      OperacionesHelper.actualizaBloqueoOp(operacion, subProcessOp);
    }
  }

  /**
   * Metodo que determina si la operacion es un borrador o no
   * 
   * @param operacion
   *          Dto con los datos de operacion
   * @return true si es borrador, false si no lo es
   */
  private boolean isOpBorrador(OperacionDTO operacion) {
    boolean borrador = true;

    if (operacion != null && operacion.getEstadoOperacion() != null 
        && !operacion.getEstadoOperacion().isEmpty()) {
      borrador = false;
    }

    return borrador;
  }

  /**
   * Redirige el flujo hacia la pantalla Detalle Operacion
   * 
   * Nota: el comando global 'changeContainerContent' esta dentro de
   * dashboard-web y lo que hace es cambiar la zona de contenido segun el zul
   * enviado como argumento
   * 
   * @param operacion
   *          Dto con los datos de operacion
   */
  private void redirectBorrador(OperacionDTO operacion) {
    Map<String, Object> cmdArgs = new HashMap<>();
    cmdArgs.put("targetZul", "/operaciones/detalleOperacion.zul");

    Map<String, Object> zulArgs = new HashMap<>();
    zulArgs.put("operacion", operacion);
    zulArgs.put("vistaBorrador", true);
    cmdArgs.put("args", zulArgs);
    BindUtils.postGlobalCommand(null, null, "changeContainerContent", cmdArgs);
  }

  /**
   * Comando que abre la ventana de detalle (Boton Mas Datos)
   */
  @Command
  public void onDetalleOperacion() {
    if (operacion != null) {

      try {
        Map<String, Object> map = new HashMap<>();
        map.put("operacion", operacion);
        map.put("vistaBorrador", false);

        Window win = (Window) Executions.createComponents("/operaciones/detalleOperacion.zul",
            window, map);
        win.setTitle("Detalle Operación");
        win.setClosable(true);
        win.setWidth("950px");
        win.doModal();
      } catch (Exception e) {
        logger.warn("Error en ResumenOperacionVM.detalleOperacion(): ", e);
        Messagebox.show(Labels.getLabel("te.resumenoperacion.error.previsualizar"),
            LITERAL_ERROR, Messagebox.OK, Messagebox.ERROR);
        window.getLastChild().detach();
      }
    }
  }

  /**
   * Comando que retorna a la pantalla listado de operaciones
   * Deprecated, porque el cambio de pagina se realiza desde resumenOperacion.zul directamente
   * a traves de la etiqueta <a>
   */
  @Command
  @Deprecated
  public void onVolver() {
    if (logger.isDebugEnabled()) {
      logger.debug("onVolver() - start");
    }

    Executions.sendRedirect("/operaciones/misOperaciones.zul");
    
    if (logger.isDebugEnabled()) {
      logger.debug("onVolver() - end");
    }
  }
  
    /**
   * Cambia de estado operación. Cuando esto ocurre, cambia el id de ejecucion JBPM,
   * por lo tanto es necesario refrescar nuevamente los combos.
   */
  @Command
  @NotifyChange({"operacion", "transiciones", "estadoSelected", "subProcess", "indexSubProcessSel", "comboSubProcessVal", "subProcessOp"})
  public void onCambiarEstado() {
    // Si la operacion no esta bloqueada
    if (estadoSelected != null && operacion != null
        && !operacion.getEstadoBloq().equalsIgnoreCase(BloqueoOperacion.PARCIAL.getValue())
        && !operacion.getEstadoBloq().equalsIgnoreCase(BloqueoOperacion.TOTAL.getValue())) {
      try {
        String actualState = operacion.getEstadoOperacion();
        String executionId = workflowService.nextState(operacion.getJbpmExecutionId(), 
        		estadoSelected, operacion, Executions.getCurrent().getRemoteUser());
        operacion.setEstadoOperacion(estadoSelected);
        operacion.setJbpmExecutionId(executionId);
        operacion.setEstadoBloq(BloqueoOperacion.NINGUNO.getValue());
        
        if (ESTADO_CIERRE.equalsIgnoreCase(estadoSelected)) {
          operacion.setFechaFin(new Date());
        }
        else {
          // Save estadoAnterior variable (If not estado == CIERRE)
          Map<String, Object> mapVars = workflowService.getVariables(executionId);
          mapVars.put(ConstantesCommon.ESTADO_ANTERIOR, actualState);
          workflowService.setVariables(workflowService.getProcessEngine(), executionId, mapVars);
        }
        
        operacionService.saveOrUpdate(operacion);
        
        // Se reinician combos y se recargan datos
        estadoSelected = null;
        indexSubProcessSel = -1;
        comboSubProcessVal = "";
        
        if (!ESTADO_CIERRE.equalsIgnoreCase(estadoSelected)) {
          // Se inician subprocesos automaticos
          List<SubProcesoOperacionDTO> listSubprocess = operacionService.startAutomaticSubProcess(operacion);
          
          if (listSubprocess != null && !listSubprocess.isEmpty()) {
            for (SubProcesoOperacionDTO subProcesoOperacion : listSubprocess) {
              // Script TAREA (SI POSEE)
              WorkFlowScriptUtils.getInstance().executeScript(ScriptType.START, subProcesoOperacion.getExpediente(), null, Executions.getCurrent().getRemoteUser());
              
              // Script INICIO SUBPROCESO (SI POSEE)
              WorkFlowScriptUtils.getInstance().executeSubprocessScript(subProcesoOperacion.getSubproceso().getScriptStart(),
              		subProcesoOperacion.getExpediente(), null, Executions.getCurrent().getRemoteUser());
            }
          }
          
          // Script TAREA
          //operacionService.initScriptSubprocess(operacion); // ¿Es necesario?
        }
        
        cargarDatos();
        
        Messagebox.show(Labels.getLabel("te.resumentoperacion.cambio"), Labels.getLabel("ee.general.information"), Messagebox.OK, Messagebox.INFORMATION);
      } catch (ServiceException | ScriptException e) {
        logger.error("Error en ResumenOperacionVM.cambiarEstado(): ", e);
        Messagebox.show(Labels.getLabel("te.resumentoperacion.error.cambio"), LITERAL_ERROR, Messagebox.OK, Messagebox.ERROR);
      }
    }
    else if (estadoSelected != null && operacion != null) {
      Messagebox.show(Labels.getLabel("te.resumentoperacion.bloqueo") + operacion.getEstadoBloq() + 
    		  Labels.getLabel("te.resumenoperacion.bloqueos"), Labels.getLabel("ee.general.information"), Messagebox.OK, Messagebox.EXCLAMATION);
    }
  }

	/**
	 * Inicia un subproceso dado. Cuando esto ocurre, 
	 * se debe refrescar la grilla de subprocesos en curso.
	 */
	@Command
	@NotifyChange({"operacion", "subProcessOp", "indexSubProcessSel", "comboSubProcessVal"})
	public void onIniciarSubprocess() {
		if (indexSubProcessSel >= 0) {
			if (!operacion.getEstadoBloq().equalsIgnoreCase(BloqueoOperacion.TOTAL.getValue())) {
				SubProcesoDTO subproceso = subProcess.get(indexSubProcessSel);
				
				if (subproceso.getLockType() != null
						&& subproceso.getLockType().equals(BloqueoOperacion.PARCIAL.getValue())
						|| subproceso.getLockType().equals(BloqueoOperacion.TOTAL.getValue())) {
					confirmarIniciarSubproceso(subproceso);
				}
				else {
					// Refactor
					iniciarSubproceso(subproceso);
				}
			}
			else {
				Messagebox.show(Labels.getLabel("te.resumenoperacion.bloqueototal"), Labels.getLabel("ee.general.information"), Messagebox.OK, Messagebox.EXCLAMATION);
			}
		}
	}
	
	/**
	 * Abre un dialog (YES/NO) avisando de que el subproceso posee un bloqueo
	 * 
	 * @param subproceso
	 */
	public void confirmarIniciarSubproceso(final SubProcesoDTO subproceso) {
		Messagebox.show(Labels.getLabel("te.resumenoperacion.sebloqueara") + subproceso.getLockType() 
		+ Labels.getLabel("te.resumenoperacion.sebloquearas"), Labels.getLabel("ee.tramitacion.informacion.documentoNoAsociado"),
			Messagebox.YES | Messagebox.NO, Messagebox.EXCLAMATION, new EventListener<Event>() {
		
				@Override
				public void onEvent(Event event) throws Exception {
					if (event.getName().equals(Messagebox.ON_YES)) {
						iniciarSubproceso(subproceso);
						OperacionesHelper.actualizaBloqueoOp(operacion, subProcessOp);
						BindUtils.postGlobalCommand(null, null, "refreshConfirmar", null);
					}
				}
			}
		);
	}
	
	/**
	 * Inicia el subproceso dado
	 * 
	 * @param subproceso
	 */
	private void iniciarSubproceso(SubProcesoDTO subproceso) {
		try {
			String loggedUsername = (String) Executions.getCurrent().getDesktop().getSession()
					.getAttribute(ConstantesWeb.SESSION_USERNAME);
			
			ExpedienteElectronicoDTO ee = operacionService.iniciarSubProceso(subproceso, operacion, loggedUsername, null);
      // Script INICIO TAREA (SI POSEE)
      WorkFlowScriptUtils.getInstance().executeScript(ScriptType.START, ee, null, Executions.getCurrent().getRemoteUser());
      // Script INICIO SUBPROCESO (SI POSEE)
      WorkFlowScriptUtils.getInstance().executeSubprocessScript(subproceso.getScriptStart(), ee, null,Executions.getCurrent().getRemoteUser());
			
      // Se reinician combos y se recargan datos
			indexSubProcessSel = -1;
			comboSubProcessVal = "";
			cargarDatos();
			
			Messagebox.show(Labels.getLabel("te.resumenoperacion.exito"), Labels.getLabel("ee.general.information"), Messagebox.OK, Messagebox.INFORMATION);
		} catch (ServiceException | ScriptException  e) {
			logger.error("Error en ResumenOperacionVM.iniciarSubprocess(): ", e);
			Messagebox.show(Labels.getLabel("te.resumenoperacion.error.iniciar"), LITERAL_ERROR, Messagebox.OK, Messagebox.ERROR);
		}
	}

  /**
   * Comando que levanta el popup que inicia la tramitacion del subproceso
   * (expediente)
   * 
   * @param subproceso
   */
  @Command
  public void onEjecutarSubproceso(@BindingParam("subproceso") SubProcesoOperacionDTO subproceso) {
    if (logger.isDebugEnabled()) {
      logger.debug("onEjecutarSubproceso() - start");
    }

    String codExpedienteElectronico = null;

    if (subproceso != null && subproceso.getExpediente() != null) {
      codExpedienteElectronico = subproceso.getExpediente().getCodigoCaratula();
    }

    try {
      ExpedienteElectronicoDTO expediente = expedienteElectronicoService
          .obtenerExpedienteElectronicoPorCodigo(codExpedienteElectronico);

      Map<String, Object> args = new HashMap<>();
      args.put("expediente", expediente);
      args.put("codigoExpediente", codExpedienteElectronico);

      ejecutarSubprocesoWindow = (Window) Executions
          .createComponents("/operaciones/tramitacion/tramitacion.zul", window, args);
      ejecutarSubprocesoWindow.doModal();
    } catch (ParametroIncorrectoException e) {
      logger.error("Error en ResumenOperacionVM.onEjecutarSubproceso(): ", e);
      Messagebox.show(Labels.getLabel("te.resumentperacion.error.ejecutar"), LITERAL_ERROR, Messagebox.OK,
          Messagebox.ERROR);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("onEjecutarSubproceso() - end");
    }
  }

  /**
   * Carga los datos de los combos y de la grilla de subprocesos a partir de la
   * operacion activa
   */
  private void cargarDatos() {
    try {
      transiciones = operacionService.getTransicionesOperacion(operacion.getJbpmExecutionId());

      if (operacion.getJbpmExecutionId() != null && !"0".equals(operacion.getJbpmExecutionId())) {
        subProcess = operacionService.getWorkFlowSubProcess(
            operacion.getTipoOperacionOb().getWorkflow(),
            operacion.getJbpmExecutionId(), "Manual", 
            operacion.getVersionProcedure());
      }

      subProcessOp = operacionService.getSubProcesos(operacion.getId().longValue());
    } catch (ServiceException e) {
      logger.error("Error en ResumenOperacionVM.cargarDatos(): ", e);
      Messagebox.show(Labels.getLabel("te.resumenoperacion.error.lista"), LITERAL_ERROR, Messagebox.OK,
          Messagebox.ERROR);
    }
  }
  
  /**
   * Comando global que se ejecuta al confirmar una operacion. Su unico
   * proposito es refrescar componentes
   */
  @GlobalCommand
  @NotifyChange("*")
  public void refreshConfirmar() {
    // Refresh
  }
  
  @GlobalCommand
  @NotifyChange("*")
  public void refreshExternal() {
      cargarDatos();
  }

  /**
   * Comando global que es invocado al momento de finalizar el envio de pase
   */
  @GlobalCommand
  @NotifyChange("*")
  public void closeTramitacionWindow() {
    ejecutarSubprocesoWindow.onClose();
    cargarDatos();
    OperacionesHelper.actualizaBloqueoOp(operacion, subProcessOp);
  }
  
  /**
   * Devuelve el motivo de un expediente
   * 
   * @param jbpmExecutionId Id ejecucion jbpm del expediente
   * @return
   */
  public String getMotivoExpediente(String jbpmExecutionId) {
    String motivo = "";
    
    Map<String, Object> mapVars = workflowService.getVariables(jbpmExecutionId);
    
    if (mapVars != null && mapVars.containsKey(ConstantesCommon.MOTIVO)) {
      motivo = mapVars.get(ConstantesCommon.MOTIVO).toString();
    }
    
    return motivo;
  }

  // Getters - setters

  public OperacionDTO getOperacion() {
    return operacion;
  }

  public void setOperacion(OperacionDTO operacion) {
    this.operacion = operacion;
  }

  public List<String> getTransiciones() {
    return transiciones;
  }

  public void setTransiciones(List<String> transiciones) {
    this.transiciones = transiciones;
  }

  public String getEstadoSelected() {
    return estadoSelected;
  }

  public void setEstadoSelected(String estadoSelected) {
    this.estadoSelected = estadoSelected;
  }

  public int getIndexSubProcessSel() {
    return indexSubProcessSel;
  }

  public void setIndexSubProcessSel(int indexSubProcessSel) {
    this.indexSubProcessSel = indexSubProcessSel;
  }

  public String getComboSubProcessVal() {
    return comboSubProcessVal;
  }

  public void setComboSubProcessVal(String comboSubProcessVal) {
    this.comboSubProcessVal = comboSubProcessVal;
  }

  public List<SubProcesoDTO> getSubProcess() {
    return subProcess;
  }

  public void setSubProcess(List<SubProcesoDTO> subProcess) {
    this.subProcess = subProcess;
  }

  public List<SubProcesoOperacionDTO> getSubProcessOp() {
    return subProcessOp;
  }

  public void setSubProcessOp(List<SubProcesoOperacionDTO> subProcessOp) {
    this.subProcessOp = subProcessOp;
  }

  public List<SubProcesoDTO> getSubProcessAutomatic() {
    return subProcessAutomatic;
  }

  public void setSubProcessAutomatic(List<SubProcesoDTO> subProcessAutomatic) {
    this.subProcessAutomatic = subProcessAutomatic;
  }
}
