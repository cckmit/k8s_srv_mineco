/**
 *
 */
package com.egoveris.workflow.designer.module.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.json.JSONObject;
import org.zkoss.json.parser.JSONParser;
import org.zkoss.json.parser.ParseException;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.egoveris.te.base.util.ZkUtil;
import com.egoveris.workflow.designer.module.exception.DesginerException;
import com.egoveris.workflow.designer.module.model.Project;
import com.egoveris.workflow.designer.module.model.State;
import com.egoveris.workflow.designer.module.util.TypeWorkFlow;

public class StateFlowComposer extends AppComposer {

	private static final long serialVersionUID = 7709729990479038638L;
	private static final Logger logger = LoggerFactory.getLogger(StateFlowComposer.class);
	@Autowired
	private Window info;
	@Autowired
	private Textbox stateName;

	@Override
	public void doAfterCompose(final Component comp) throws Exception {
		super.doAfterCompose(comp);
		super.setWindow(info);
	}

	public void onSaveModel(final Event event) {
		super.onSaveModel(event, TypeWorkFlow.STATE);
		Messagebox.show(Labels.getLabel("msg.wfStateGuardado"), Labels.getLabel("msg.informacion"), Messagebox.OK, Messagebox.INFORMATION);
		event.stopPropagation();
	}

	public void onOpenSubProcess() {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("stateFlow", getProject().getName());
			map.put("stateName", stateName.getValue());
			map.put("version", getProject().getVersion());
			Map<String, Object> m = new HashMap<>();
			m.put("map", map);
			final Window win = (Window) Executions.createComponents("/pantallas/subprocess.zul", null, m);
			win.doModal();
		} catch (Exception e) {
			logger.error("",e);
		}
	}

	public void onNewProject(final Event event) {
		editar(null);
		event.stopPropagation();
	}

	public void onNuevoProyecto(final Event event) {
		editar(null);
		event.stopPropagation();
	}

	public void editar(final Project project) {
		super.editar(project, TypeWorkFlow.STATE);
	}

	public void onShow() {
		final Window win = ZkUtil.createComponent("/pantallas/ConfirmacionGuardar.zul", null, null);
		final Button btnGuardar = ZkUtil.findById(win, "btnGuardar");
		final Button btnCancelar = ZkUtil.findById(win, "btnCancelar");
		btnGuardar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(final Event event) throws Exception {
				Clients.evalJavaScript("saveModel();");
				Clients.evalJavaScript("firstProject = false");
				editar(null, TypeWorkFlow.STATE);
				win.detach();
			}
		});
		btnCancelar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(final Event event) throws Exception {
				win.detach();
			}
		});
		win.doModal();

	}

	public void onMakePackageOnWebDav(final Event event) {
		try {
			JSONObject o = (JSONObject) new JSONParser().parse((String) event.getData());
			Project proj = getDesignerService().fromJson(o.get("project").toString(), Project.class);
			
			Map<String, String> finalSubProcess = getSubProcesssService().getAllSubProcessProject(proj.getName());
				
			boolean equalsProcedures = true;
			
			if ((getInitialSubProcess() == null || getInitialSubProcess().size() == 0)
			    || (finalSubProcess == null || finalSubProcess.isEmpty())) {
				equalsProcedures = false;
			} else {
				for (Entry<String, String> finalS : finalSubProcess.entrySet()) {
					for (Entry<String, String> initialS : getInitialSubProcess().entrySet()) {
						if (!finalS.getValue().equalsIgnoreCase(initialS.getValue())) {
							equalsProcedures = false;
							break;
						}
					}
				}
			}
			
			// Start - remove unused subprocess (from deleted states)
			List<String> statesToKeep = new ArrayList<>();
			
			for (State state : proj.getStates()) {
				statesToKeep.add(state.getProperties().getName());
			}
			
			getSubProcesssService().removeUnusedStateSubprocess(getProject().getName(), getProject().getVersion(), statesToKeep);
			// End - remove unused subprocess (from deleted states)
			
			if (!equalsProcedures && finalSubProcess != null && finalSubProcess.size() > 0) {
				int versionP = proj.getVersionProcedure() + 1;
				proj.setVersionProcedure(versionP);
				getSubProcesssService().updateVersionProcedure(proj.getName(), versionP);
				getDesignerService().saveOrUpdateProject(proj);
				loadProject(proj);
			}
			
			super.onMakePackageOnWebDav(proj, o, TypeWorkFlow.STATE);
			
			if (getVersionProject() != proj.getVersion() && finalSubProcess != null && finalSubProcess.size() > 0) {
				getSubProcesssService().updateSubProcessVersion(proj.getName(), proj.getVersion());
			}
			
			setInitialSubProcess(finalSubProcess);
			Messagebox.show(Labels.getLabel("msg.wfStateGenerado"), Labels.getLabel("msg.informacion"), Messagebox.OK, Messagebox.INFORMATION);
			event.stopPropagation();
		} catch (DesginerException e) {
			logger.error("Error al generar jar",e);
			Messagebox.show(Labels.getLabel("msg.errorJar"), "Error", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void onAbrirProyecto(final Event event) {
		super.onAbrirProyecto(TypeWorkFlow.STATE);
		event.stopPropagation();
	}

	public void onSelectServices(final Event event){
		super.onSelectServices(TypeWorkFlow.STATE);
		event.stopPropagation();
	}
	
	public void onRenameState(final Event event) {
		String lastName = null;
		String newName = null;
		
		if (event.getData() != null) {
			JSONObject obj = null;
			
			try {
				obj = (JSONObject) new JSONParser().parse((String) event.getData());
			}
			catch (ParseException e) {
				
			}
			
			if (obj != null) {
				lastName = obj.containsKey("lastName") ? obj.get("lastName").toString() : null;
				newName = obj.containsKey("newName") ? obj.get("newName").toString() : null;
			}
		}
		
		if (lastName != null && newName != null) {
			getSubProcesssService().copySubprocessOfState(getProject().getName(), getProject().getVersion(), lastName, newName);
			getStateName().setValue(newName);
		}
	}
	
	/* GETTERS & SETTERS */
	
	public Textbox getStateName() {
		return stateName;
	}

	public void setStateName(final Textbox stateName) {
		this.stateName = stateName;
	}

}
