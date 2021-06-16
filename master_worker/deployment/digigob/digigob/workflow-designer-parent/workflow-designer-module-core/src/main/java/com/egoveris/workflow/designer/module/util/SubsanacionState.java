package com.egoveris.workflow.designer.module.util;
/**
 *
 */

import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Window;

import com.egoveris.plugins.tools.ZkUtil;
import com.egoveris.te.web.ee.satra.pl.helpers.states.GenericState;

/**
 * @author difarias
 *
 */
public class SubsanacionState extends GenericState {
	private static String ESTADO_PREVIO_SB_KEY = "estado_PrevioSubsanacion";
	private static String USUARIO_PREVIO_SB_KEY = "usuario_PrevioSubsanacion";

	/*
	 * (non-Javadoc)
	 *
	 * @see com.egoveris.te.core.api.ee.satra.pl.helpers.states.GenericState#
	 * aceptReject()
	 */
	@Override
	public boolean acceptReject() {
		return false;
	}

	@Override
	public boolean isValid() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.egoveris.te.core.api.ee.satra.pl.helpers.states.GenericState#
	 * getTabName()
	 */
	@Override
	public String getTabName() {
		return super.getTabName();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.egoveris.te.core.api.ee.satra.pl.helpers.states.GenericState#
	 * setWorkflowName(java.lang.String)
	 */
	@Override
	public void setWorkflowName(final String workflowName) {
		super.setWorkflow_name(workflowName);
	}

	public String getWorkflowName() {
		return super.getWorkflowName();
	}

	public boolean isWorkflow(final String workflowName) {
		return super.isWorkflow(workflowName);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.egoveris.te.core.api.ee.satra.pl.helpers.states.GenericState#
	 * drawInclude(org.zkoss.zk.ui.Component)
	 */
	@Override
	public void drawInclude(final Component includeComp) {
		final Window mainParent = ZkUtil.findParentByType(includeComp, Window.class);
		super.drawInclude(includeComp);

		final org.zkoss.zul.Toolbarbutton btnSubsanar = ZkUtil.findById(mainParent, "subsanar");
		if (btnSubsanar != null) {
			btnSubsanar.setDisabled(true);
		}

		componentStates(mainParent, false);
		// drawTramitacionLibreBtn(includeComp); // Boton para Tramitacion Libre
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.egoveris.te.core.api.ee.satra.pl.helpers.states.GenericState#
	 * customizePase(org.zkoss.zk.ui.Component, java.lang.String)
	 */
	@Override
	public void customizePase(final Component windowPase, final String nextState) {
		final Map<String, Object> data = getTramitacionHelper().getVariables(super.getWorkingTask().getExecutionId());

		final String estado_previo = (String) data.get(ESTADO_PREVIO_SB_KEY);
		final String usuario_previo = (String) data.get(USUARIO_PREVIO_SB_KEY);

		System.out.println("SubsanacionState.customizePase() !!! " + estado_previo + " - " + usuario_previo);

		Grid grid = (Grid) windowPase.getFellow("gridEstado");
		grid.setHeight("0px");

		grid = (Grid) windowPase.getFellow("grillaDestino");
		grid.setHeight("0px");

		final Combobox estadoCmb = (Combobox) windowPase.getFellow("estado");
		try {
			estadoCmb.setValue(estado_previo);
		} catch (final WrongValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		estadoCmb.setReadonly(true);
		estadoCmb.setDisabled(true);

		selectUsuario(windowPase, usuario_previo);
	}

}
