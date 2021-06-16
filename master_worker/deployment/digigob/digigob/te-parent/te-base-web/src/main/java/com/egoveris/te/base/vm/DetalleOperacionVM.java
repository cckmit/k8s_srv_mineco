package com.egoveris.te.base.vm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.script.ScriptException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.remoting.RemoteAccessException;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;
import org.zkoss.zul.Window;

import com.egoveris.ffdd.model.exception.DynFormException;
import com.egoveris.ffdd.model.model.FormularioDTO;
import com.egoveris.ffdd.render.service.IFormManager;
import com.egoveris.ffdd.render.service.IFormManagerFactory;
import com.egoveris.te.base.exception.ServiceException;
import com.egoveris.te.base.helper.OperacionesHelper;
import com.egoveris.te.base.helper.TipoOperacionesHelper;
import com.egoveris.te.base.model.OperacionDTO;
import com.egoveris.te.base.model.OperacionDocumentoDTO;
import com.egoveris.te.base.model.SubProcesoOperacionDTO;
import com.egoveris.te.base.model.TipoDocumentoGedoDTO;
import com.egoveris.te.base.model.TipoOperacionDocDTO;
import com.egoveris.te.base.service.OperacionService;
import com.egoveris.te.base.service.iface.IExpedienteFormularioService;
import com.egoveris.te.base.service.iface.IExternalFormsService;
import com.egoveris.te.base.service.iface.IOperacionDocumentoService;
import com.egoveris.te.base.service.iface.ITipoOperacionService;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.ScriptType;
import com.egoveris.te.base.util.WorkFlowScriptUtils;
import com.egoveris.te.model.model.ExpedienteFormularioDTO;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class DetalleOperacionVM extends SelectorComposer<Component> {

  private static final Logger logger = LoggerFactory.getLogger(DetalleOperacionVM.class);
  private static final long serialVersionUID = 1L;
  private static final String LITERAL_ERROR = "Error";

  @Wire("#boInOp")
  Window boInOp;

  @Wire("#tabDinamico")
  Tabbox tabDinamico;

  private OperacionDTO operacion;
  private List<FormularioDTO> formulariosDinamicos;
  private FormularioDTO formulario;
  private List<ExpedienteFormularioDTO> listExpediente;
  private List<Integer> listIdTransaccion = new ArrayList<>();

  // Servicios
  @WireVariable(ConstantesServicios.FORM_MANAGER_FACTORY)
  private IFormManagerFactory<IFormManager<Component>> formManagerFact;
  
  @WireVariable(ConstantesServicios.EXTERNAL_FORM_SERVICE)
  private IExternalFormsService formularioService;
  
  @WireVariable(ConstantesServicios.TIPO_OPERACION_SERVICE)
  private ITipoOperacionService tipoOperacionService;
  
  @WireVariable(ConstantesServicios.OPERACION_SERVICE)
  private OperacionService operacionService;
  
  @WireVariable(ConstantesServicios.EXP_FORMULARIO_SERVICE)
  private IExpedienteFormularioService iExpedienteFormulario;
  
  @WireVariable(ConstantesServicios.OPERACION_DOC_SERVICE)
  private IOperacionDocumentoService operacionDocumentoService;
  
  private IFormManager<Component> formManager;
  private List<IFormManager<Component>> listFormManager;

  /**
   * Inicializa y setea la operacion y carga los formularios dinamicos asociados
   * a la operacion.
   * 
   * @param operacion
   *          Dto con los datos de la operacion
   */
  @Init
  public void init(@ExecutionArgParam("operacion") OperacionDTO operacion) { 
    if (operacion != null) {
      setOperacion(operacion);

      try {
        List<FormularioDTO> listaFormularios = formularioService
            .buscarFormulariosFFDD();
            formulariosDinamicos = TipoOperacionesHelper.getFormulariosTipoOperacion(
            tipoOperacionService, listaFormularios, operacion.getTipoOperacionOb().getId());
      } catch (DynFormException | RemoteAccessException e) {
        logger.error("Error en DetalleOperacionVM.init(): ", e);
        Messagebox.show(Labels.getLabel("te.detalleoperacion.formularioDin"), LITERAL_ERROR,
            Messagebox.OK, Messagebox.ERROR);
      }
    }
  }

  /**
   * Luego del init, en este metodo se pinta el formulario dinamico
   * 
   * @param view
   */
  @AfterCompose
  public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
    Selectors.wireComponents(view, this, false);
    listFormManager = new ArrayList<>();

    if (operacion != null && formulariosDinamicos != null) {
      try {
        ExpedienteFormularioDTO expediente = new ExpedienteFormularioDTO();
        expediente.setIdEeExpedient(operacion.getId());
        listExpediente = iExpedienteFormulario.buscarFormulariosPorExpediente(expediente);

        Tabs tabs = new Tabs();
        Tabpanels tabpanels = new Tabpanels();

        if (!listExpediente.isEmpty()) {
          // Refactor
          creaTabsExpedienteFormulario(tabs, tabpanels, listExpediente);
        } else if (!formulariosDinamicos.isEmpty()) {
          // Refactor
          creaTabsFormularioDTO(tabs, tabpanels, formulariosDinamicos);
        }
        
        tabDinamico.appendChild(tabs);
        tabDinamico.appendChild(tabpanels);
      } catch (Exception e) {
        logger.error("Error en DetalleOperacionVM.afterCompose(): ", e);
        Messagebox.show(Labels.getLabel("te.detalleoperacion.formularioDin"), LITERAL_ERROR,
            Messagebox.OK, Messagebox.ERROR);
      }
    }
  }

  private void creaTabsExpedienteFormulario(Tabs tabs, Tabpanels tabpanels,
      List<ExpedienteFormularioDTO> listExpediente) throws DynFormException {
    Tab tab;
    Tabpanel tabpanel;

    for (ExpedienteFormularioDTO expedienteFor : listExpediente) {
      String nombreFC = expedienteFor.getFormName();

      if (!StringUtils.isEmpty(nombreFC)) {
        formManager = formManagerFact.create(nombreFC);
        tab = new Tab(nombreFC);
        tabs.appendChild(tab);
        tabpanel = new Tabpanel();
        
        if (operacion.getId() != null) {
          formManager.fillCompValues(operacion.getId().intValue(), expedienteFor.getIdDfTransaction());
        } else {
          formManager.fillCompValues(expedienteFor.getIdDfTransaction());
        }
          
        tabpanel.appendChild(formManager.getFormComponent());
        tabpanels.appendChild(tabpanel);
        listFormManager.add(formManager);
      }
    }
  }

  private void creaTabsFormularioDTO(Tabs tabs, Tabpanels tabpanels,
      List<FormularioDTO> formulariosDinamicos) throws DynFormException {
    Tab tab;
    Tabpanel tabpanel;
    
    if (formulariosDinamicos != null) {
      for (FormularioDTO formu : formulariosDinamicos) {
        String nombreFC = formu.getNombre();
        
        if (!StringUtils.isEmpty(nombreFC)) {
          formManager = formManagerFact.create(nombreFC);
          tab = new Tab(nombreFC);
          tabs.appendChild(tab);
          tabpanel = new Tabpanel();
		  if (operacion.getId() != null) {
			formManager.fillCompValues(operacion.getId().intValue(), null);
		  }
          tabpanel.appendChild(formManager.getFormComponent());
          tabpanels.appendChild(tabpanel);
          listFormManager.add(formManager);
        }
      }
    }
  }

  /**
   * Comando que guarda los datos ingresados en el formulario dinamico
   */
  @Command
  public void onGuardar(@BindingParam("mostrarConfirmacion") boolean mostrarConfirmacion) {
    try {
      Integer index = 0;

      if (listExpediente != null && !listExpediente.isEmpty() && !listFormManager.isEmpty()) {
        for (ExpedienteFormularioDTO expediente : listExpediente) {
          if (operacion.getId() != null) {
            listFormManager.get(index).updateFormWeb(operacion.getId().intValue(), expediente.getIdDefForm());
          } else {
            listFormManager.get(index).updateFormWeb(expediente.getIdDefForm());
          }
          
          index++;
        }
      } else if (!listIdTransaccion.isEmpty()) {
        for (IFormManager<Component> formM : listFormManager) {
          if (operacion.getId() != null) {
            formM.updateFormWeb(operacion.getId().intValue(), listIdTransaccion.get(index));
          } else {
            formM.updateFormWeb(listIdTransaccion.get(index));
          }
          
          index++;
        }
      } else {
        // Refactor
        guardaFormulario(listFormManager);
      }
      
      operacion = operacionService.saveOrUpdate(operacion);
      
      if (mostrarConfirmacion) {
        Messagebox.show(Labels.getLabel("te.detalleoperacion.guardado"), Labels.getLabel("ee.general.information"), Messagebox.OK,
            Messagebox.INFORMATION);
      }
    } catch (Exception e) {
      logger.error("Error en DetalleOperacionVM.onGuardar(): ", e);
      Messagebox.show(Labels.getLabel("te.detalleoperacion.error.guardado"), LITERAL_ERROR, Messagebox.OK,
          Messagebox.ERROR);
    }
  }

  private void guardaFormulario(List<IFormManager<Component>> listFormManager)
      throws DynFormException {
    Integer index = 0;
   
    // Se quita try/catch porque ya se captura la excepcion desde el metodo
    // que invoca a este
    //if (listFormManager != null) {
      for (IFormManager<Component> formM : listFormManager) {
        Integer idTransaccion;
        
        if (null == operacion.getId()) {
          idTransaccion = formM.saveValues();
        } else {
          idTransaccion = formM.saveValues(operacion.getId().intValue());
        }
        
        /*
         * Si idTransaccion es null, entonces no tiene sentido guardar
         * en ExpedienteFormulario.
         */
        if (idTransaccion != null) {
	        ExpedienteFormularioDTO expedienteFormulario = new ExpedienteFormularioDTO();
	        expedienteFormulario.setIdDfTransaction(idTransaccion);
	        expedienteFormulario.setFormName(formulariosDinamicos.get(index).getNombre());
	        expedienteFormulario.setIdDefForm(idTransaccion);
	        expedienteFormulario.setDateCration(new Date());
	
	        if (null != operacion.getId()) {
	          expedienteFormulario.setIdEeExpedient(operacion.getId());
	        } else {
	          expedienteFormulario.setIdEeExpedient(0l);
	        }
	
	        expedienteFormulario.setObservation(null);
	        expedienteFormulario.setOrganism("");
	        expedienteFormulario.setUserCreation("");
	        expedienteFormulario.setIsDefinitive(0);
	        iExpedienteFormulario.guardarFormulario(expedienteFormulario);
	        index++;
	        
	        listIdTransaccion.add(idTransaccion);
        }
      }
    //}
  }

  /**
   * Comando que confirma la operacion. Primero guarda los datos del formulario
   * que esten ingresados
   */
  @Command
  public void onConfirmar() throws ServiceException {
    if (operacion != null && listExpediente != null && !listFormManager.isEmpty()) {
      if (validarDocumentos(operacion)) {
        try {
          onGuardar(false);
          // La logica que estaba escrita aqui, ahora se hace en
          // OperacionService.confirmarOperacion
          operacion = operacionService.confirmarOperacion(operacion);
          WorkFlowScriptUtils.getInstance().executeScript(ScriptType.START_STATE, operacion, null,
        		  Executions.getCurrent().getRemoteUser());
          List<SubProcesoOperacionDTO> listSubprocess = operacionService.startAutomaticSubProcess(operacion);
          
          if (listSubprocess != null && !listSubprocess.isEmpty()) {
            for (SubProcesoOperacionDTO subProcesoOperacion : listSubprocess) {
              // Script TAREA (SI POSEE)
              WorkFlowScriptUtils.getInstance().executeScript(ScriptType.START, subProcesoOperacion.getExpediente(),
                  null, Executions.getCurrent().getRemoteUser());
              
              // Script INICIO SUBPROCESO (SI POSEE)
              WorkFlowScriptUtils.getInstance().executeSubprocessScript(
                  subProcesoOperacion.getSubproceso().getScriptStart(), subProcesoOperacion.getExpediente(), null,
                  Executions.getCurrent().getRemoteUser());
            }
          }
          
          // Script TAREA
          //operacionService.initScriptSubprocess(operacion); // ¿Es necesario?
          confirmado();
        } catch (ServiceException | ScriptException e) {
          logger.error("Error en DetalleOperacionVM.onConfirmar(): ", e);
          Messagebox.show(Labels.getLabel("te.detalleoperacion.error.confirmar"), LITERAL_ERROR, Messagebox.OK,
              Messagebox.ERROR);
        }
      }
      else {
        Messagebox.show(Labels.getLabel("te.detalleoperacion.error.adjuntar"), LITERAL_ERROR,
            Messagebox.OK, Messagebox.ERROR);
      }
    }
  }
  
  /**
   * Comando que regresa a la pantalla de operaciones
   */
  @Command
  public void onVolver() {
    Executions.sendRedirect("/operaciones/misOperaciones.zul");
  }

  /**
   * Este metodo redirige hacia la pantalla de resumen luego de confirmada la
   * operacion
   */
  public void confirmado() {
    OperacionesHelper.redirectResumenOperacion(operacion);
  }
  
  /**
   * Metodo que valida que esten ingresados los documentos obligatorios
   * 
   * @param operacion
   * @return
   */
  private boolean validarDocumentos(OperacionDTO operacion) {
    boolean validarDocs = true;
    
    if (operacion.getTipoOperacionOb() != null && operacion.getTipoOperacionOb().getTiposOpDocumento() != null) {
      List<OperacionDocumentoDTO> docsOper = operacionDocumentoService.getDocumentosOperacion(operacion.getId());
      
      // Recorre la lista de tipos documentos
      for (TipoOperacionDocDTO tipoOperDoc : operacion.getTipoOperacionOb().getTiposOpDocumento()) {
        if (tipoOperDoc.isObligatorio() && tipoOperDoc.getTipoDocumentoGedo() != null) {
          // Si encuentra un tipo de documento obligatorio, revisa que este ingresado
          validarDocs = estaIngresadoDocObligatorio(tipoOperDoc.getTipoDocumentoGedo(), docsOper);
        }
        
        if (!validarDocs) {
          break;
        }
      }
    }
    
    return validarDocs;
  }
  
  /**
   * Valida que este ingresado un documento obligatorio segun el tipo
   * de documento dado
   * 
   * @param tipoDoc
   * @param docsOper
   * @return
   */
  private boolean estaIngresadoDocObligatorio(TipoDocumentoGedoDTO tipoDoc, List<OperacionDocumentoDTO> docsOper) {
    boolean estaIngresado = false;
    
    // Busca el documento correspondiente al tipo dado
    for (OperacionDocumentoDTO operacionDocumentoDTO : docsOper) {
      if (operacionDocumentoDTO.getTipoDocumentoGedo() != null
          && operacionDocumentoDTO.getTipoDocumentoGedo().getId().equals(tipoDoc.getId())) {
        // Si tiene nombre de archivo es porque se subio previamente
        if (operacionDocumentoDTO.getNombreArchivo() != null) {
          estaIngresado = true;
        }
      }
    }
    
    return estaIngresado;
  }

  public OperacionDTO getOperacion() {
    return operacion;
  }

  public void setOperacion(OperacionDTO operacion) {
    this.operacion = operacion;
  }

  public List<FormularioDTO> getFormulariosDinamicos() {
    return formulariosDinamicos;
  }

  public void setFormulariosDinamicos(List<FormularioDTO> formulariosDinamicos) {
    this.formulariosDinamicos = formulariosDinamicos;
  }

  public FormularioDTO getFormulario() {
    return formulario;
  }

  public void setFormulario(FormularioDTO formulario) {
    this.formulario = formulario;
  }

}
