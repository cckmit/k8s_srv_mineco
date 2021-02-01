package com.egoveris.workflow.designer.module.util;

/**
 */

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.egoveris.plugins.tools.ZkUtil;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.web.ee.satra.pl.helpers.states.GenericState;

/**
 * @author difarias
 *
 */
public class GenericStateTemplate extends GenericState implements EventListener<Event> {

	/**
	 *
	 */
	private static final long serialVersionUID = 8397802008163364542L;

	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(GenericStateTemplate.class);

	private org.zkoss.zk.ui.Component ventanaPase;
	private boolean acceptCierreExpediente = false;

	private String hint;

	private final String ESTADO_PREVIO_SB_KEY = "estado_PrevioSubsanacion";
	private final String USUARIO_PREVIO_SB_KEY = "usuario_PrevioSubsanacion";

	private final String GUARDA_TEMPORAL = "Guarda Temporal";
	public final List<EventListener<Event>> enviarListeners = new LinkedList<>();

	private Window winContainer;

	/* Parallel Workflow Add */
	private boolean forkOnlyLink = false;
	private String forkName = "";
	private String stateConnectedToJoinNamed = "";
	/* End */

	public void setWorkflowName(final String workflowName) {
		if (logger.isDebugEnabled()) {
			logger.debug("setWorkflowName(String) - start"); //$NON-NLS-1$
		}

		super.setWorkflow_name(workflowName);

		if (logger.isDebugEnabled()) {
			logger.debug("setWorkflowName(String) - end"); //$NON-NLS-1$
		}
	}

	public String getWorkflowName() {
		if (logger.isDebugEnabled()) {
			logger.debug("getWorkflowName() - start"); //$NON-NLS-1$
		}

		final String returnString = super.getWorkflowName();
		if (logger.isDebugEnabled()) {
			logger.debug("getWorkflowName() - end"); //$NON-NLS-1$
		}
		return returnString;
	}

	public boolean isWorkflow(final String workflowName) {
		if (logger.isDebugEnabled()) {
			logger.debug("isWorkflow(String) - start"); //$NON-NLS-1$
		}

		final boolean returnboolean = super.isWorkflow(workflowName);
		if (logger.isDebugEnabled()) {
			logger.debug("isWorkflow(String) - end"); //$NON-NLS-1$
		}
		return returnboolean;
	}

	public boolean isAcceptCierreExpediente() {
		return acceptCierreExpediente;
	}

	public void setAcceptCierreExpediente(final boolean acceptCierreExpediente) {
		this.acceptCierreExpediente = acceptCierreExpediente;
	}

	public boolean isForkOnlyLink() {
		return forkOnlyLink;
	}

	public void setForkOnlyLink(final boolean forkOnlyLink) {
		this.forkOnlyLink = forkOnlyLink;
	}

	public String getHint() {
		return hint;
	}

	public void setHint(final String hint) {
		this.hint = hint;
	}

	public String getTabName() {
		if (logger.isDebugEnabled()) {
			logger.debug("getTabName() - start"); //$NON-NLS-1$
		}

		final String returnString = super.getTabName();
		if (logger.isDebugEnabled()) {
			logger.debug("getTabName() - end"); //$NON-NLS-1$
		}
		return returnString;
	}

	public ExpedienteElectronicoDTO loadLinkedExpedient() {
		if (logger.isDebugEnabled()) {
			logger.debug("loadLinkedExpedient() - start"); //$NON-NLS-1$
		}

		final ExpedienteElectronicoDTO returnExpedienteElectronico = super.getEe();
		if (logger.isDebugEnabled()) {
			logger.debug("loadLinkedExpedient() - end"); //$NON-NLS-1$
		}
		return returnExpedienteElectronico;
	}

	public Window getWinContainer() {
		return winContainer;
	}

	public Window setWinContainer(final Window winContainer) {
		if (logger.isDebugEnabled()) {
			logger.debug("setWinContainer(Window) - start"); //$NON-NLS-1$
		}

		final Window returnWindow = this.winContainer = winContainer;
		if (logger.isDebugEnabled()) {
			logger.debug("setWinContainer(Window) - end"); //$NON-NLS-1$
		}
		return returnWindow;
	}

	public String getForkName() {
		return forkName;
	}

	public void setForkName(final String forkName) {
		this.forkName = forkName;
	}

	public String getStateConnectedToJoinNamed() {
		return stateConnectedToJoinNamed;
	}

	public void setStateConnectedToJoinNamed(final String stateConnectedToJoinNamed) {
		this.stateConnectedToJoinNamed = stateConnectedToJoinNamed;
	}

	/**
	 * Metodo para vincular documentos
	 *
	 * @param workingTask
	 * @param motivo
	 * @param acronimoPase
	 * @param camposTemplate
	 */
	public void vincularDocumentoPase(final Task workingTask, final String motivo, final String acronimoPase,
			final Map<String, String> camposTemplate) {
		if (logger.isDebugEnabled()) {
			logger.debug("vincularDocumentoPase(Task, String, String, Map<String,String>) - start"); //$NON-NLS-1$
		}

		final String referenciaPase = String.format("Informe de %s", workingTask.getName().toLowerCase());
		final DocumentoDTO doc = getTramitacionHelper().getDocumentacionGedoService().generarDocumentoGEDOPase(getEe(),
				motivo, getUserLogged().getUsername(), acronimoPase, referenciaPase, camposTemplate);
		doc.setFechaAsociacion(new Date());
		doc.setUsuarioAsociador(getUserLogged().getUsername());
		doc.setIdTask(workingTask.getExecutionId());
		if (getEe().getEsCabeceraTC() != null && getEe().getEsCabeceraTC()) {
			doc.setIdExpCabeceraTC(getEe().getId());
		}
		getEe().getDocumentos().add(doc);

		if (logger.isDebugEnabled()) {
			logger.debug("vincularDocumentoPase(Task, String, String, Map<String,String>) - end"); //$NON-NLS-1$
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.egoveris.te.core.api.ee.satra.pl.helpers.states.GenericState#
	 * generateDocumentOfPase (java.lang.String)
	 */
	@Override
	public void generateDocumentOfPase(final String motive) {
		if (logger.isDebugEnabled()) {
			logger.debug("generateDocumentOfPase(String) - start"); //$NON-NLS-1$
		}

		if (getAcronymPase().isEmpty()) {
			return;
		}
		if (!isRejecting()) { // generate a interpass document, only if the
								// reject flag is false
			vincularDocumentoPase(getWorkingTask(), motive, getAcronymPase(), getStateVariables());
		}

		if (logger.isDebugEnabled()) {
			logger.debug("generateDocumentOfPase(String) - end"); //$NON-NLS-1$
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.egoveris.te.core.api.ee.satra.pl.helpers.states.GenericState#
	 * createDocumentThroughFFCC()
	 */
	@Override
	public void createDocumentThroughFFCC() throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("createDocumentThroughFFCC() - start"); //$NON-NLS-1$
		}

		try {
			super.createDocumentThroughFFCC();
		} catch (final Exception e) {
			Messagebox.show(e.getMessage());
			if (ventanaPase != null) {
				ventanaPase.detach();
			}
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("createDocumentThroughFFCC() - end"); //$NON-NLS-1$
		}
	}

	public void showHint() {
		if (logger.isDebugEnabled()) {
			logger.debug("showHint() - start"); //$NON-NLS-1$
		}

		if (getHint() != null && !getHint().isEmpty()) {
			final Div hintPanel = (Div) ZkUtil.findById(getParentContainer(), "hintPanel");
			final Textbox txt = (Textbox) ZkUtil.findById(getParentContainer(), "hint");
			if (!getHint().isEmpty() && getHint() != null) {
				txt.setValue(getHint());
				hintPanel.setVisible(true);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("showHint() - end"); //$NON-NLS-1$
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.egoveris.te.core.api.ee.satra.pl.helpers.states.GenericState#
	 * drawInclude(org.zkoss.zk.ui.Component)
	 */
	@Override
	public void drawInclude(final Component includeComp) {
		if (logger.isDebugEnabled()) {
			logger.debug("drawInclude(Component) - start"); //$NON-NLS-1$
		}

		final Window winContainer = ZkUtil.findParentByType(includeComp, Window.class);
		setWinContainer(winContainer);
		super.drawInclude(includeComp);
		showHint();

		if (this.getEe().getSistemaCreador().equals("TAD")) {
			final Window mainParent = ZkUtil.findParentByType(getParentContainer().getParent(), Window.class);
			final Toolbarbutton comunicarTadButton = ZkUtil.findById(mainParent, "comunicarTadButton");
			if (comunicarTadButton != null) {
				comunicarTadButton.setDisabled(false);
			}
		}

		if (isAcceptCierreExpediente() && !getWorkflowName().contains("NIC")) {
			drawCerrarExpediente(includeComp);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("drawInclude(Component) - end"); //$NON-NLS-1$
		}
	}

	public void cargarUsuarioTask() {
		if (logger.isDebugEnabled()) {
			logger.debug("cargarUsuarioTask() - start"); //$NON-NLS-1$
		}

		final Usuario userLogueado = getTramitacionHelper().getUsuariosSADEService()
				.getDatosUsuario(getWorkingTask().getAssignee());
		setUserLogged(userLogueado);

		if (logger.isDebugEnabled()) {
			logger.debug("cargarUsuarioTask() - end"); //$NON-NLS-1$
		}
	}

	public void drawCerrarExpediente(final Component includeComp) {
		if (logger.isDebugEnabled()) {
			logger.debug("drawCerrarExpediente(Component) - start"); //$NON-NLS-1$
		}

		if (isWorkflow(getWorkflowName())) {
			final Window winContainer = ZkUtil.findParentByType(includeComp, Window.class);
			setWinContainer(winContainer);
			final Hbox botonera = ZkUtil.findById(winContainer, "pie");
			final Toolbarbutton cerrarExpedienteBtn = new org.zkoss.zul.Toolbarbutton();
			cerrarExpedienteBtn.setId("cerrarExpedienteBtn");
			cerrarExpedienteBtn.setAttribute("onClick", getEe());
			cerrarExpedienteBtn.setIconSclass("z-icon-times");
			cerrarExpedienteBtn.setLabel("Cerrar expediente");
			cerrarExpedienteBtn.setZclass("z-button");
			cerrarExpedienteBtn.setStyle("padding:7px 10px");
			cerrarExpedienteBtn.addEventListener(Events.ON_CLICK, this);
			cerrarExpedienteBtn.setParent(botonera);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("drawCerrarExpediente(Component) - end"); //$NON-NLS-1$
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.egoveris.te.core.api.ee.satra.pl.helpers.states.GenericState#
	 * customizePase(org .zkoss.zk.ui.Component, java.lang.String)
	 */
	@Override
	public void customizePase(final Component windowPase, String nextState) {
		if (logger.isDebugEnabled()) {
			logger.debug("customizePase(Component, String) - start"); //$NON-NLS-1$
		}

		ventanaPase = windowPase;
		if (isForkOnlyLink()) {
			nextState = getForkName();
		} else {
			if (!getStateConnectedToJoinNamed().isEmpty() && getStateConnectedToJoinNamed() != null) {
				nextState = getStateConnectedToJoinNamed();
			}
		}
		super.customizePase(windowPase, nextState);
		final Combobox estadoCmb = ZkUtil.findById(ventanaPase, "estado");
		estadoCmb.setReadonly(true);
		estadoCmb.setDisabled(false);

		if (logger.isDebugEnabled()) {
			logger.debug("customizePase(Component, String) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Metodo para cambiar el estado de los TABs de la pantalla
	 *
	 * @param tabName
	 */
	public void tabState(final String tabName, final Boolean visible, final Boolean enabled) {
		if (logger.isDebugEnabled()) {
			logger.debug("tabState(String, Boolean, Boolean) - start"); //$NON-NLS-1$
		}

		final Window mainParent = ZkUtil.findParentByType(getParentContainer().getParent(), Window.class);
		final List<Tab> lstTabs = ZkUtil.findByType(mainParent, Tab.class);
		if (lstTabs != null && !lstTabs.isEmpty()) {
			for (final java.util.Iterator<Tab> it = lstTabs.iterator(); it.hasNext();) {
				final Tab tab = it.next();
				if (tabName.equalsIgnoreCase("*") || tab.getId().equalsIgnoreCase(tabName)
						|| tab.getLabel().equalsIgnoreCase(tabName)) {
					if (visible != null) {
						tab.setVisible(visible);
					}
					if (enabled != null) {
						tab.setDisabled(!enabled);
					}
				}
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("tabState(String, Boolean, Boolean) - end"); //$NON-NLS-1$
		}
	}

	public void focusTab(final String tabName) {
		if (logger.isDebugEnabled()) {
			logger.debug("focusTab(String) - start"); //$NON-NLS-1$
		}

		final Window mainParent = ZkUtil.findParentByType(getParentContainer().getParent(), Window.class);
		final List<Tab> lstTabs = ZkUtil.findByType(mainParent, Tab.class);
		if (lstTabs != null && !lstTabs.isEmpty()) {
			for (final Tab tab : lstTabs) {
				if (tabName.equalsIgnoreCase("*") || tab.getId().equalsIgnoreCase(tabName)
						|| tab.getLabel().equalsIgnoreCase(tabName)) {
					Events.sendEvent(new Event(Events.ON_CLICK, tab));
					tab.setVisible(true);
					tab.setDisabled(false);
					final Tabbox tabbox = (Tabbox) tab.getParent().getParent();
					tabbox.setSelectedTab(tab);

					if (logger.isDebugEnabled()) {
						logger.debug("focusTab(String) - end"); //$NON-NLS-1$
					}
					return;
				}
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("focusTab(String) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Desahabilita un TAB (Disabled)
	 *
	 * @param tabName
	 *            String nombre del tab
	 */
	public void disableTab(final String tabName) {
		if (logger.isDebugEnabled()) {
			logger.debug("disableTab(String) - start"); //$NON-NLS-1$
		}

		tabState(tabName, null, false);

		if (logger.isDebugEnabled()) {
			logger.debug("disableTab(String) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Habilita el estado de un TAB (not Disabled)
	 *
	 * @param tabName
	 *            String nombre del tab
	 */
	public void enableTab(final String tabName) {
		if (logger.isDebugEnabled()) {
			logger.debug("enableTab(String) - start"); //$NON-NLS-1$
		}

		tabState(tabName, null, true);

		if (logger.isDebugEnabled()) {
			logger.debug("enableTab(String) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Desahabilita un TAB (Disabled)
	 *
	 * @param tabName
	 *            String nombre del tab
	 */
	public void visibleTab(final String tabName) {
		if (logger.isDebugEnabled()) {
			logger.debug("visibleTab(String) - start"); //$NON-NLS-1$
		}

		tabState(tabName, true, null);

		if (logger.isDebugEnabled()) {
			logger.debug("visibleTab(String) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Habilita el estado de un TAB (not Disabled)
	 *
	 * @param tabName
	 *            String nombre del tab
	 */
	public void invisibleTab(final String tabName) {
		if (logger.isDebugEnabled()) {
			logger.debug("invisibleTab(String) - start"); //$NON-NLS-1$
		}

		tabState(tabName, false, null);

		if (logger.isDebugEnabled()) {
			logger.debug("invisibleTab(String) - end"); //$NON-NLS-1$
		}
	}

	public void disabledAll() {
		if (logger.isDebugEnabled()) {
			logger.debug("disabledAll() - start"); //$NON-NLS-1$
		}

		tabState("*", null, false);

		if (logger.isDebugEnabled()) {
			logger.debug("disabledAll() - end"); //$NON-NLS-1$
		}
	}

	public void invisibleAll() {
		if (logger.isDebugEnabled()) {
			logger.debug("invisibleAll() - start"); //$NON-NLS-1$
		}

		tabState("*", false, null);

		if (logger.isDebugEnabled()) {
			logger.debug("invisibleAll() - end"); //$NON-NLS-1$
		}
	}

	public void visibleAll() {
		if (logger.isDebugEnabled()) {
			logger.debug("visibleAll() - start"); //$NON-NLS-1$
		}

		tabState("*", true, false);

		if (logger.isDebugEnabled()) {
			logger.debug("visibleAll() - end"); //$NON-NLS-1$
		}
	}

	public void closeWindow(final Component comp) {
		if (logger.isDebugEnabled()) {
			logger.debug("closeWindow(Component) - start"); //$NON-NLS-1$
		}

		Events.sendEvent(new Event(Events.ON_CLOSE, comp));

		if (logger.isDebugEnabled()) {
			logger.debug("closeWindow(Component) - end"); //$NON-NLS-1$
		}
	}

	public void newMessageOK(final String text) {
		if (logger.isDebugEnabled()) {
			logger.debug("newMessageOK(String) - start"); //$NON-NLS-1$
		}

		Messagebox.show(text, Labels.getLabel("msg.confirmarOperacion"), Messagebox.OK, Messagebox.INFORMATION);

		if (logger.isDebugEnabled()) {
			logger.debug("newMessageOK(String) - end"); //$NON-NLS-1$
		}
	}

	public void newErrorMessage(final String text) {
		if (logger.isDebugEnabled()) {
			logger.debug("newErrorMessage(String) - start"); //$NON-NLS-1$
		}

		Messagebox.show(text, Labels.getLabel("msg.errorOperacion"), Messagebox.OK, Messagebox.ERROR);

		if (logger.isDebugEnabled()) {
			logger.debug("newErrorMessage(String) - end"); //$NON-NLS-1$
		}
	}

	@Override
	public void onEvent(final Event event) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("onEvent(Event) - start"); //$NON-NLS-1$
		}

		if (event.getTarget().getId().equalsIgnoreCase("subsanar")) {
			final HashMap<String, Object> hma = new HashMap<String, Object>();
			hma.put(ESTADO_PREVIO_SB_KEY, getWorkingTask().getActivityName());
			hma.put(USUARIO_PREVIO_SB_KEY, getUserLogged().getUsername());
			getTramitacionHelper().addVariables(getWorkingTask(), hma);
		}
		if (event.getTarget().getId().equalsIgnoreCase("cerrarExpedienteBtn")) {
			final HashMap<String, Object> hma = new HashMap<String, Object>();
			if (!getWorkingTask().getActivityName().equalsIgnoreCase(GUARDA_TEMPORAL)) {
				getTramitacionHelper().addVariables(getWorkingTask(), hma);
				hma.clear();
			}
			hma.put("expedienteElectronico", getEe());

			final Window envio = (Window) Executions.createComponents("/pase/envio.zul", getWinContainer(), hma);
			envio.setTitle("Cerrar expediente : " + getEe().getCodigoCaratula());

			// System.out.println(getWinContainer().getId());
			envio.setParent(getWinContainer());
			envio.setPosition("center");
			envio.setClosable(true);
			envio.doModal();

			final Combobox estadoCmb = (Combobox) envio.getFellow("estado");
			estadoCmb.setValue(GUARDA_TEMPORAL);
			estadoCmb.setReadonly(true);
			estadoCmb.setDisabled(true);

			Grid grid = (Grid) envio.getFellow("gridEstado");
			grid.setHeight("0px");
			grid = (Grid) envio.getFellow("grillaDestino");
			grid.setHeight("0px");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("onEvent(Event) - end"); //$NON-NLS-1$
		}
	}
}