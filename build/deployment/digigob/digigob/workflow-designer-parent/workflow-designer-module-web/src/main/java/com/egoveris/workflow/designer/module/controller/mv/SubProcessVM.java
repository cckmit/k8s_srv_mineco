/**
 *
 */
package com.egoveris.workflow.designer.module.controller.mv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.spring.SpringUtil;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.egoveris.te.model.exception.ProcesoFallidoException;
import com.egoveris.te.model.model.TrataEE;
import com.egoveris.te.ws.service.IAdministracionTrataService;
import com.egoveris.workflow.designer.module.exception.ServicesException;
import com.egoveris.workflow.designer.module.model.SubProcessDTO;
import com.egoveris.workflow.designer.module.service.SubProcessService;
import com.egoveris.workflow.designer.module.util.DesignerUtil;

public class SubProcessVM {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(SubProcessVM.class);
	
	private SubProcessService subProcesssService;
	private List<TrataEE> procedures;
	private TrataEE procedureSelected;
	private List<String> lockTypes;
	private String lockTypeSelected;
	private List<String> startTypes;
	private String startTypeSelected;
	private List<SubProcessDTO> subprocess;
	private String stateFlow;
	private String stateName;
	private String scriptStart;
	private String scriptEnd;
	private int version;
	
	@Wire("#subProcess")
	private Window win;
	@Wire("#btnEditSubprocess")
	private Button btnEditSubprocess;
	@Wire("#btnCancelEditSubprocess")
	private Button btnCancelEditSubprocess;
	@Wire("#btnAddSubprocess")
	private Button btnAddSubprocess;
	@Wire("#listSubprocess")
	private Listbox listSubProcess;
	@Wire("#groupBox")
	private Groupbox groupBox;
	private SubProcessDTO subProcessTemp;
	@Wire("#cbxStartType")
	private Combobox cbxStartType;
	@Wire("#cbxLockType")
	private Combobox cbxLockType;
	@Wire("#cbxProcedure")
	private Combobox cbxProcedure;

	@SuppressWarnings("unchecked")
	@Init
	public void init(@ExecutionArgParam("map") final Object map) {
		if (logger.isDebugEnabled()) {
			logger.debug("init(Object) - start"); //$NON-NLS-1$
		}

		try {
			Map<String, Object> m = (HashMap<String, Object>) map;
			setStateFlow((String) m.get("stateFlow"));
			setStateName((String) m.get("stateName"));
			setVersion((int) m.get("version"));
			IAdministracionTrataService trataService = (IAdministracionTrataService) SpringUtil
					.getBean("administracionTrataExternalService");
			subProcesssService = (SubProcessService) SpringUtil.getBean("subProcessServiceImpl");
			this.procedures = trataService.buscarTratasEEByTipoSubproceso();
			subprocess = subProcesssService.findAllByStateNameVersion(DesignerUtil.camelName(stateFlow), stateName,
					version);
			
			if (subprocess == null) {
				subprocess = new ArrayList<>();
			}
			
			lockTypes = new ArrayList<>();
			lockTypes.add("Parcial");
			lockTypes.add("Total");
			lockTypes.add("Ninguno");

			startTypes = new ArrayList<>();
			startTypes.add("Automatico");
			startTypes.add("Manual");
		} catch (ProcesoFallidoException | ServicesException e) {
			logger.error("init(Object)", e); //$NON-NLS-1$
			Messagebox.show("", "", Messagebox.OK, Messagebox.ERROR);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("init(Object) - end"); //$NON-NLS-1$
		}
	}

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) final Component view) {
		if (logger.isDebugEnabled()) {
			logger.debug("afterCompose(Component) - start"); //$NON-NLS-1$
		}
		
		Selectors.wireComponents(view, this, false);
		groupBox.setTitle("Agregar");
		
		if (logger.isDebugEnabled()) {
			logger.debug("afterCompose(Component) - end"); //$NON-NLS-1$
		}
	}

	@Command
	@NotifyChange({ "lockTypeSelected", "procedureSelected", "startTypeSelected", "subprocess", "scriptStart", "scriptEnd" })
	public void addSubProcess() {
		if (logger.isDebugEnabled()) {
			logger.debug("addSubProcess() - start"); //$NON-NLS-1$
		}

		try {
			validate();
			final SubProcessDTO sub = new SubProcessDTO();
			sub.setIdProcedure(procedureSelected.getId().longValue());
			sub.setLockType(lockTypeSelected);
			sub.setStateFlow(DesignerUtil.camelName(stateFlow));
			sub.setStateName(stateName);
			sub.setVersion(version);
			sub.setProcedureName(procedureSelected.getCodigoTrata());
			sub.setStartType(startTypeSelected);
			sub.setScriptStart(scriptStart);
			sub.setScriptEnd(scriptEnd);
			
			final SubProcessDTO process = subProcesssService.saveOrUpdate(sub);
			
			if (process != null) {
				subprocess.add(process);
			}
			
			clean();
			Messagebox.show(Labels.getLabel("msg.procesoAgregadoExito"), Labels.getLabel("msg.informacion"), Messagebox.OK, Messagebox.INFORMATION);
		} catch (ServicesException | WrongValueException e) {
			logger.error("addSubProcess()", e); //$NON-NLS-1$
			Messagebox.show(e.getMessage(), "Error", Messagebox.OK, Messagebox.ERROR);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("addSubProcess() - end"); //$NON-NLS-1$
		}
	}

	@Command
	public void deleteSubProcess(@BindingParam("subprocess") final SubProcessDTO subProcessDTO) {
		if (logger.isDebugEnabled()) {
			logger.debug("deleteSubProcess(SubProcessDTO) - start"); //$NON-NLS-1$
		}

		Messagebox.show(Labels.getLabel("msg.question.elimiarProceso"), Labels.getLabel("msg.confirmacion"), Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION, new EventListener<Event>() {
					@Override
					public void onEvent(final Event evt) throws InterruptedException {
						if (logger.isDebugEnabled()) {
							logger.debug("$EventListener<Event>.onEvent(Event) - start"); //$NON-NLS-1$
						}

						if ("onOK".equalsIgnoreCase(evt.getName())) {
							confirmDelete(subProcessDTO);
						}

						if (logger.isDebugEnabled()) {
							logger.debug("$EventListener<Event>.onEvent(Event) - end"); //$NON-NLS-1$
						}
					}
				});

		if (logger.isDebugEnabled()) {
			logger.debug("deleteSubProcess(SubProcessDTO) - end"); //$NON-NLS-1$
		}
	}

	@NotifyChange({ "subprocess", "listSubprocess" })
	private void confirmDelete(final SubProcessDTO subprocessDto) {
		if (logger.isDebugEnabled()) {
			logger.debug("confirmDelete(SubProcessDTO) - start"); //$NON-NLS-1$
		}

		try {
			subProcesssService.delete(subprocessDto);
			subprocess.remove(subprocessDto);
			BindUtils.postNotifyChange(null, null, this, "subprocess");
		} catch (final ServicesException e) {
			logger.error("confirmDelete(SubProcessDTO)", e); //$NON-NLS-1$

			Messagebox.show("", "", Messagebox.OK, Messagebox.ERROR);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("confirmDelete(SubProcessDTO) - end"); //$NON-NLS-1$
		}
	}

	@Command
	@NotifyChange({ "lockTypeSelected", "procedureSelected", "startTypeSelected", "scriptStart", "scriptEnd" })
	public void editSubProcess(@BindingParam("subprocess") final SubProcessDTO subProcessDTO) {
		if (logger.isDebugEnabled()) {
			logger.debug("editSubProcess(SubProcessDTO) - start"); //$NON-NLS-1$
		}

		setLockTypeSelected(subProcessDTO.getLockType());
		TrataEE trata = null;
		
		for (final TrataEE t : procedures) {
			if (t.getId().equals(subProcessDTO.getIdProcedure())) {
				trata = t;
				break;
			}
		}
		
		setProcedureSelected(trata);
		setStartTypeSelected(subProcessDTO.getStartType());
		setScriptStart(subProcessDTO.getScriptStart());
		setScriptEnd(subProcessDTO.getScriptEnd());
		btnEditSubprocess.setVisible(true);
		btnCancelEditSubprocess.setVisible(true);
		btnAddSubprocess.setVisible(false);
		subProcessTemp = subProcessDTO;
		groupBox.setTitle("Editar");
		
		if (logger.isDebugEnabled()) {
			logger.debug("editSubProcess(SubProcessDTO) - end"); //$NON-NLS-1$
		}
	}

	@Command
	@NotifyChange({ "lockTypeSelected", "procedureSelected", "startTypeSelected", "subprocess", "scriptStart", "scriptEnd" })
	public void updateSubProcess() {
		if (logger.isDebugEnabled()) {
			logger.debug("saveSubProcess() - start"); //$NON-NLS-1$
		}

		if (subProcessTemp != null) {
			try {
				subProcessTemp.setStartType(startTypeSelected);
				subProcessTemp.setIdProcedure(procedureSelected.getId().longValue());
				subProcessTemp.setLockType(procedureSelected.getCodigoTrata());
				subProcessTemp.setLockType(lockTypeSelected);
				subProcessTemp.setScriptStart(scriptStart);
				subProcessTemp.setScriptEnd(scriptEnd);
				subProcesssService.saveOrUpdate(subProcessTemp);
				clean();
				Messagebox.show(Labels.getLabel("msg.procesoActualizadoExito"), Labels.getLabel("msg.informacion"), Messagebox.OK, Messagebox.INFORMATION);
				cbxStartType.invalidate();
			} catch (final ServicesException e) {
				logger.error("saveSubProcess()", e); //$NON-NLS-1$
				Messagebox.show(Labels.getLabel("msg.errorActualizar"), "Error", Messagebox.OK, Messagebox.ERROR);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("saveSubProcess() - end"); //$NON-NLS-1$
		}
	}

	@Command
	@NotifyChange({ "lockTypeSelected", "procedureSelected", "startTypeSelected", "subprocess", "scriptStart", "scriptEnd" })
	public void cancelEditSubProcess() {
		if (logger.isDebugEnabled()) {
			logger.debug("cancelEditSubProcess() - start"); //$NON-NLS-1$
		}
		
		clean();
		
		if (logger.isDebugEnabled()) {
			logger.debug("cancelEditSubProcess() - end"); //$NON-NLS-1$
		}
	}

	@Command
	public void close() {
		if (logger.isDebugEnabled()) {
			logger.debug("cerrar() - start"); //$NON-NLS-1$
		}
		
		win.detach();
		
		if (logger.isDebugEnabled()) {
			logger.debug("cerrar() - end"); //$NON-NLS-1$
		}
	}

	private void clean() {
		if (logger.isDebugEnabled()) {
			logger.debug("clean() - start"); //$NON-NLS-1$
		}

		btnEditSubprocess.setVisible(false);
		btnCancelEditSubprocess.setVisible(false);
		btnAddSubprocess.setVisible(true);
		lockTypeSelected = null;
		setProcedureSelected(null);
		startTypeSelected = null;
		cbxProcedure.setValue(null);
		cbxLockType.setValue(null);
		cbxStartType.setValue(null);
		setScriptStart(null);
		setScriptEnd(null);
		groupBox.setTitle("Agregar");

		if (logger.isDebugEnabled()) {
			logger.debug("clean() - end"); //$NON-NLS-1$
		}
	}

	private void validate() {
		if (logger.isDebugEnabled()) {
			logger.debug("validar() - start"); //$NON-NLS-1$
		}

		if (procedureSelected == null) {
			throw new WrongValueException("Seleccione un trámite");
		}
		
		if (StringUtils.isEmpty(lockTypeSelected)) {
			throw new WrongValueException("Seleccione un tipo de bloqueo");
		}
		
		if (StringUtils.isEmpty(startTypeSelected)) {
			throw new WrongValueException("Seleccione un tipo de inicio");
		}
		
		for (final SubProcessDTO s : subprocess) {
			if (procedureSelected.getId().equals(s.getIdProcedure().intValue())) {
				throw new WrongValueException(
						"Ya existe un estado asociado al tramite :" + procedureSelected.getCodigoTrata());
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validar() - end"); //$NON-NLS-1$
		}
	}

	/* GETTERS AND SETTERS */
	public SubProcessService getSubProcesssService() {
		return subProcesssService;
	}

	public void setSubProcesssService(final SubProcessService subProcesssService) {
		this.subProcesssService = subProcesssService;
	}

	public List<TrataEE> getProcedures() {
		return procedures;
	}

	public void setProcedures(final List<TrataEE> procedures) {
		this.procedures = procedures;
	}

	public TrataEE getProcedureSelected() {
		return procedureSelected;
	}

	public void setProcedureSelected(final TrataEE procedureSelected) {
		this.procedureSelected = procedureSelected;
	}

	public List<String> getLockTypes() {
		return lockTypes;
	}

	public void setLockTypes(final List<String> lockTypes) {
		this.lockTypes = lockTypes;
	}

	public String getLockTypeSelected() {
		return lockTypeSelected;
	}

	public void setLockTypeSelected(final String lockTypeSelected) {
		if (lockTypeSelected !=null) //Fix. Deja a nulo el valor aun que se seleccione algo.
			this.lockTypeSelected = lockTypeSelected;
	}

	public List<SubProcessDTO> getSubprocess() {
		return subprocess;
	}

	public void setSubprocess(final List<SubProcessDTO> subprocess) {
		this.subprocess = subprocess;
	}

	public String getStateFlow() {
		return stateFlow;
	}

	public void setStateFlow(final String stateFlow) {
		this.stateFlow = stateFlow;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(final String stateName) {
		this.stateName = stateName;
	}

	public String getScriptStart() {
		return scriptStart;
	}

	public void setScriptStart(String scriptStart) {
		this.scriptStart = scriptStart;
	}

	public String getScriptEnd() {
		return scriptEnd;
	}

	public void setScriptEnd(String scriptEnd) {
		this.scriptEnd = scriptEnd;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(final int version) {
		this.version = version;
	}

	public Window getWin() {
		return win;
	}

	public void setWin(final Window win) {
		this.win = win;
	}

	public Button getBtnEditSubprocess() {
		return btnEditSubprocess;
	}

	public void setBtnEditSubprocess(final Button btnEditSubprocess) {
		this.btnEditSubprocess = btnEditSubprocess;
	}

	public Button getBtnCancelEditSubprocess() {
		return btnCancelEditSubprocess;
	}

	public void setBtnCancelEditSubprocess(final Button btnCancelEditSubprocess) {
		this.btnCancelEditSubprocess = btnCancelEditSubprocess;
	}

	public Button getBtnAddSubprocess() {
		return btnAddSubprocess;
	}

	public void setBtnAddSubprocess(final Button btnAddSubprocess) {
		this.btnAddSubprocess = btnAddSubprocess;
	}

	public Listbox getListSubProcess() {
		return listSubProcess;
	}

	public void setListSubProcess(final Listbox listSubProcess) {
		this.listSubProcess = listSubProcess;
	}

	public Groupbox getGroupBox() {
		return groupBox;
	}

	public void setGroupBox(final Groupbox groupBox) {
		this.groupBox = groupBox;
	}

	public SubProcessDTO getSubProcessTemp() {
		return subProcessTemp;
	}

	public void setSubProcessTemp(final SubProcessDTO subProcessTemp) {
		this.subProcessTemp = subProcessTemp;
	}

	public List<String> getStartTypes() {
		return startTypes;
	}

	public void setStartTypes(final List<String> startTypes) {
		this.startTypes = startTypes;
	}

	public String getStartTypeSelected() {
		return startTypeSelected;
	}

	public void setStartTypeSelected(final String startTypeSelected) {
		if (startTypeSelected != null)
			this.startTypeSelected = startTypeSelected;
	}

	public Combobox getCbxStartType() {
		return cbxStartType;
	}

	public void setCbxStartType(final Combobox cbxStartType) {
		this.cbxStartType = cbxStartType;
	}

	public Combobox getCbxLockType() {
		return cbxLockType;
	}

	public void setCbxLockType(final Combobox cbxLockType) {
		this.cbxLockType = cbxLockType;
	}

	public Combobox getCbxProcedure() {
		return cbxProcedure;
	}

	public void setCbxProcedure(final Combobox cbxProcedure) {
		this.cbxProcedure = cbxProcedure;
	}
}
