package com.egoveris.te.web.ee.satra.pl.helpers.states;

import com.egoveris.te.base.util.ZkUtil;

import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

public class TramitacionLibreState extends GenericState {
	
	private static final long serialVersionUID = 5572546554777159200L;
	
	private static final String FORWARD = "Tramitación Libre";
	private static final String BACKWARD = "";
	private static final String ZUL_PATH = "";
	private static final String WINDOWS_ID = "";
	private static final String ACRONYM_DOCUMENT_PASS="";
	private static final String WORKFLOW_NAME="*";
	
	public static final String STATE_NAME = "Tramitación Libre";
	
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
		return WORKFLOW_NAME;
	}

	@Override
	public String getName() {
		return STATE_NAME;
	}

	@Override
	public String getStateMacroURI() {
		return ZUL_PATH;
	}

	@Override
	public String getAcronymPase() {
		return ACRONYM_DOCUMENT_PASS;
	}
	
	@Override
	public String getWindowId() {
		return WINDOWS_ID;
	}

	@Override
	public boolean acceptReject() {
		return false;
	}

	@Override
	public  String getBackward() {
		return BACKWARD;
	}
	
	@Override
	public void drawInclude(Component includeComp) {
		Window mainParent = ZkUtil.findParentByType(includeComp, Window.class);
		super.drawInclude(includeComp);
		
		Toolbarbutton btnSubsanar = ZkUtil.findById(mainParent, "subsanar");
		
		if (btnSubsanar!=null) {
			btnSubsanar.setDisabled(true);
		}
		
		componentStates(mainParent, false);
		drawTramitacionLibreBtn(includeComp); // Boton para Tramitacion Libre	
		
	}

	@Override
	public void customizePase(Component windowPase, String nextState) {
		Map<String,Object> data = getTramitacionHelper().getVariables(super.getWorkingTask().getExecutionId());
		
		String estadoPrevio = (String) data.get(super.ESTADO_PREVIO_TL_KEY);
		String usuarioPrevio = (String) data.get(super.USUARIO_PREVIO_TL_KEY);
		
        Grid grid = (Grid) windowPase.getFellow("gridEstado");
        grid.setHeight("0px");
        
        grid = (Grid) windowPase.getFellow("grillaDestino");
        grid.setHeight("0px");                
  	
		Combobox estadoCmb = (Combobox) windowPase.getFellow("estado");
		estadoCmb.setValue(estadoPrevio);
		estadoCmb.setReadonly(true);
		estadoCmb.setDisabled(true);

		selectUsuario(windowPase, usuarioPrevio);
	}

	@Override
	public boolean isValid() {
		return false;
	}
	
}
