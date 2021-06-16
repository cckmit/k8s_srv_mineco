package com.egoveris.te.base.composer;

import java.util.Date;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.bind.BindUtils;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.egoveris.deo.ws.exception.ParametroInvalidoException;
import com.egoveris.ffdd.model.exception.DynFormException;
import com.egoveris.ffdd.model.model.FormularioDTO;
import com.egoveris.ffdd.model.model.TransaccionDTO;
import com.egoveris.ffdd.render.service.IFormManager;
import com.egoveris.ffdd.render.service.IFormManagerFactory;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.service.iface.IExpedienteFormularioService;
import com.egoveris.te.base.service.iface.IExternalFormsService;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.model.model.ExpedienteFormularioDTO;


@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class FormWebComposer extends EEGenericForwardComposer {

	private static final long serialVersionUID = 1241392901353641742L;
	private final static Logger logger = LoggerFactory.getLogger(FormWebComposer.class);

	@WireVariable(ConstantesServicios.EXP_FORMULARIO_SERVICE)
	private IExpedienteFormularioService iExpedienteFormulario;

 	@WireVariable(ConstantesServicios.EXTERNAL_FORM_SERVICE)
	private IExternalFormsService externalFormsService;

    @Autowired
    private Button guardar;
    
    @Autowired
    private Button cancelar;
    @Autowired
    private Window tramitacionWindow;
    @Autowired
    private Window formWeb;
    
	public Div div;
	IFormManager<Component> manager;
	
	Integer isNew;

	private ExpedienteElectronicoDTO ee;
	private String loggedUsername;
	private String organismUser;
	String formComp;
	
	@WireVariable(ConstantesServicios.FORM_MANAGER_FACTORY)
	private IFormManagerFactory<IFormManager<Component>> formManagerFact;
	
	HashMap<String,Object> parametros = new HashMap<String,Object>();

	@SuppressWarnings("unchecked")
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		ee = (ExpedienteElectronicoDTO) Executions.getCurrent().getDesktop().getAttribute("expedienteElectronico");
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
		formComp = (String) Executions.getCurrent().getArg().get("nombreFormulario");

		
    loggedUsername = Executions.getCurrent().getSession().getAttribute(ConstantesWeb.SESSION_USERNAME).toString();
	    organismUser= (String) Executions.getCurrent().getDesktop().getSession().getAttribute(ConstantesWeb.SESSION_USER_REPARTICION);
		
	    if (organismUser == null) {
	    	organismUser = "";
	    }
	    
		parametros = (HashMap<String, Object>) Executions.getCurrent().getArg();
		String title = (String) parametros.get("nameForm");
		this.formWeb.setTitle(title); 
		isNew = (Integer) parametros.get("isNew");
		
		if (isNew == 0){
			newForm();
			
		}else if (isNew == 1) {
			viewForm();
		}else if (isNew == 2){
			editForm();
		}else if (isNew == 3){
			cloneForm();
		}
	}

	private void viewForm() {
		try {
			String formComp = (String) parametros.get("nameForm");
			Integer uuid =(Integer)parametros.get("idTransaction");
			this.manager = formManagerFact.create(formComp);
			if(ee != null){
			  manager.getFormComponent(ee.getIdOperacion(), uuid, true).setParent(div);
			}else{
			  manager.getFormComponent(uuid, true).setParent(div);
			}
		} catch (DynFormException e) {
			logger.error(" " + e.getCause());
		}	
		
	}

	private void editForm() {
		try {
			String formComp = (String) parametros.get("nameForm");
			Integer uuid =(Integer)parametros.get("idTransaction");
			this.manager = formManagerFact.create(formComp);
			if(ee != null){
			  manager.getFormComponent(ee.getIdOperacion(), uuid, false).setParent(div);
			}else{
			  manager.getFormComponent(uuid).setParent(div);
			}
		} catch (DynFormException e) {
			logger.error(" " + e.getCause());
		}	
		
	}

	private void cloneForm() {
		try {
			String formComp = (String) parametros.get("nameForm");
			Integer uuid =(Integer)parametros.get("idTransaction");
			this.manager = formManagerFact.create(formComp);
			if(ee != null){
			  manager.getFormComponent(ee.getIdOperacion(), uuid, false).setParent(div);
			}else{
			  manager.getFormComponent(uuid).setParent(div);
			}
		} catch (DynFormException e) {
			logger.error(" " + e.getCause());
		}	
		
	}

	private void newForm() {
		try {
			String formComp = (String) parametros.get("nameForm");
			this.manager = formManagerFact.create(formComp);			
			manager.getFormComponent().setParent(div);
		} catch (DynFormException e) {
			onCancelar();
			throw new WrongValueException(e.getMessage());
		} catch(Exception e ){
			this.guardar.setDisabled(true);
			String error = Labels.getLabel("ee.servicio.dynform.errorComunicacionDynform");
			logger.error(error + e.getStackTrace());
			throw new WrongValueException(error);
		}
		
	}



	public int onGuardarExp() throws InterruptedException, DynFormException, ParametroInvalidoException {
		int rtnValue = 0;

		if (isNew == 3 ){
			cloneFormWeb();

		} else if (isNew == 2 ){
			    updateFormWeb();

		} else{
			newFormWeb();
		}
		return rtnValue;
	}
	
  private void newFormWeb() throws InterruptedException, DynFormException, ParametroInvalidoException {
    ExpedienteFormularioDTO expedienteFormulario = new ExpedienteFormularioDTO();
    Date date = new Date();

    Integer idTransaccion = null;

    try {
      if (ee != null && ee.getIdOperacion() != null) {
        idTransaccion = this.manager.saveValues(ee.getIdOperacion());
      } else {
        idTransaccion = this.manager.saveValues();
      }

      if (idTransaccion != null) {
        this.guardar.setDisabled(true);
        Integer idExpedient = ((Number) parametros.get("idExpedient")).intValue();

        logger.info("id transaccion " + idTransaccion);
        TransaccionDTO transaccionDTO = externalFormsService.buscarTransaccionPorIdFFDD(idTransaccion);

        expedienteFormulario.setIdDfTransaction(idTransaccion);
        expedienteFormulario.setFormName(transaccionDTO.getNombreFormulario());

        FormularioDTO formularioDTO = externalFormsService.buscarFormularioNombreFFDD(expedienteFormulario);
        expedienteFormulario.setDateCration(date);
        expedienteFormulario.setIdDefForm(formularioDTO.getId());
        expedienteFormulario.setIdEeExpedient(idExpedient.longValue());
        expedienteFormulario.setObservation(null);
        expedienteFormulario.setOrganism(organismUser);
        expedienteFormulario.setUserCreation(loggedUsername);
        expedienteFormulario.setIsDefinitive(0);

        iExpedienteFormulario.guardarFormulario(expedienteFormulario);

        if (tramitacionWindow != null) {
          // Si se esta ejecutando desde la ventana de tramitacion (Tareas)
          Events.echoEvent("onUser", (Component) tramitacionWindow, "updateListForm");
        } else {
          // Sino, se esta ejecutando desde la nueva ventana de tramitacion y se
          // llama
          // a un comando global
          BindUtils.postGlobalCommand(null, null, "refreshListaFormularios", null);
        }
        
        Events.sendEvent(this.self, new Event(Events.ON_CLOSE));
      } else {
        Messagebox.show(Labels.getLabel("ee.vincularForm.noTransaction.message"),
            Labels.getLabel("ee.vincularForm.noTransaction.title"), Messagebox.OK, Messagebox.EXCLAMATION);
      }
    } catch (WrongValueException wVExc) {
      logger.error("El formulario controlado no ha sido completado. " + wVExc);
      throw new WrongValueException(wVExc.getMessage());
    }
  }

	private void updateFormWeb() {
		try {
		Integer uuid =(Integer)parametros.get("idTransaction");
		System.out.println(" Id transaction "+uuid);
		if(ee != null && ee.getIdOperacion() != null){
		  this.manager.updateFormWeb(ee.getIdOperacion(), uuid);
		}else{
		  this.manager.updateFormWeb(uuid);
		}
		Events.sendEvent(this.self, new Event(Events.ON_CLOSE));
		}catch (Exception e){
			logger.error(e.getMessage()+e);
		}
	}

	private void cloneFormWeb() throws InterruptedException, DynFormException, ParametroInvalidoException {
		ExpedienteFormularioDTO expedienteFormulario = new ExpedienteFormularioDTO();
		Date date = new Date();

		Integer idTransaccion = null;

		try {
		  if(ee != null && ee.getIdOperacion() != null){
		    idTransaccion = this.manager.saveValues(ee.getIdOperacion());
		}else{
		    idTransaccion = this.manager.saveValues();
		}
			this.guardar.setDisabled(true);
			Integer idExpedient = ((Number) parametros.get("idExpedient")).intValue();
			String formName = (String) parametros.get("nameForm");

			expedienteFormulario.setIdDfTransaction(idTransaccion);
			expedienteFormulario.setFormName(formName);
			logger.info("id transaccion "+idTransaccion);
			FormularioDTO formularioDTO = externalFormsService.buscarFormularioNombreFFDD(expedienteFormulario);
			expedienteFormulario.setIdDefForm(formularioDTO.getId());
			expedienteFormulario.setDateCration(date);
			expedienteFormulario.setIdEeExpedient(idExpedient.longValue());
			expedienteFormulario.setObservation(null);
			expedienteFormulario.setOrganism(organismUser);
			expedienteFormulario.setUserCreation(loggedUsername);
			expedienteFormulario.setIsDefinitive(0);

			iExpedienteFormulario.guardarFormulario(expedienteFormulario );
		} catch (WrongValueException wVExc) {
			logger.error("El formulario controlado no ha sido completado. " + wVExc);
			throw new WrongValueException(wVExc.getMessage());
		}
		Events.echoEvent("onUser",(Component) tramitacionWindow , "updateListForm");
		Events.sendEvent(this.self, new Event(Events.ON_CLOSE));
	}

	public void onCancelar() {
			logger.error("Boton cancelar ");
			Events.sendEvent(this.self, new Event(Events.ON_CLOSE));
	}

	public void setGuardar(Button guardar) {
		this.guardar = guardar;
	}

	public Button getGuardar() {
		return guardar;
	}

	public void setCancelar(Button cancelar) {
		this.cancelar = cancelar;
	}

	public Button getCancelar() {
		return cancelar;
	}

	public Window getFormWeb() {
		return formWeb;
	}

	public void setFormWeb(Window formWeb) {
		this.formWeb = formWeb;
	}
}
