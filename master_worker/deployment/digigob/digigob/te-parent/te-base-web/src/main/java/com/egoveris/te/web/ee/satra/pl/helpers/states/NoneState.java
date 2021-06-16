package com.egoveris.te.web.ee.satra.pl.helpers.states;

import com.egoveris.plugins.tools.ZkUtil;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

public class NoneState extends GenericState {
	
	private static final long serialVersionUID = -205011005340651641L;
	
	private static final String FORWARD = "";
	private static final String BACKWARD = "";
	private static final String ACRONYM_DOCUMENT_PASS = "PV";
	private static final String SUBSANACION = "Subsanacion";
	
	public static final String STATE_NAME = "NONE";

	@Override
	public String getForward() {
		return FORWARD;
	}

	@Override
	public String getBackwardState() {
		return BACKWARD;
	}

	@Override
	public String getWorkflowName() {
		return ALL_WORKFLOW;
	}

	@Override
	public String getName() {
		return STATE_NAME;
	}

	@Override
	public String getStateMacroURI() {
		return "";
	}

	@Override
	public String getAcronymPase() {
		return ACRONYM_DOCUMENT_PASS;
	}

	@Override
	public String getWindowId() {
		return "";
	}

	@Override
	public void generateDocumentOfPase(String motive) {
		//
	}

	@Override
	public boolean acceptReject() {
		return false;
	}

	@Override
	public void drawInclude(Component includeComp) {
		if (getEe().getIdWorkflow() == null) {
			Messagebox.show("El campo workflow se encuentra vacío. Comuniquese con el administrador.", "Atención",
					Messagebox.OK, Messagebox.ERROR);
		}
		else {
			String workflowName = getEe().getIdWorkflow().substring(0, getEe().getIdWorkflow().indexOf("."));
			
			if (!workflowName.equalsIgnoreCase("solicitud")) {
				if (!getEe().getEstado().equalsIgnoreCase(SUBSANACION)) {
	
					Window mainWin = ZkUtil.findParentByType(includeComp, Window.class);
					Hbox pie = ZkUtil.findById(mainWin, "pie");
					List<Toolbarbutton> lstButtons = ZkUtil.findByType(pie, Toolbarbutton.class);
	
					if (lstButtons != null && !lstButtons.isEmpty()) {
						for (Toolbarbutton tb : lstButtons) {
							if (!tb.getId().equalsIgnoreCase("guardar"))
								tb.setDisabled(true);
						}
					}
	
					Messagebox.show("No se encuentra disponible el workflow [" + workflowName
							+ "]. Comuniquese con el administrador.", "Atención", Messagebox.OK, Messagebox.ERROR);
				}
			}
		}
	}

	@Override
	public Integer getParallelMinUsers() {
		return null;
	}

	@Override
	public Integer getParallelMaxUsers() {
		return null;
	}

	@Override
	public Integer getParallelMinSector() {
		return null;
	}

	@Override
	public Integer getParallelMaxSector() {
		return null;
	}

	@Override
	public boolean isParallelExclusive() {
		return false;
	}

	@Override
	public void customizePase(Component windowPase, String nextState) {
		//
	}

	@Override
	public void customizePase(Window envio) {
		//
	}

	@Override
	public boolean isValid() {
		return false;
	}
	
}
