package com.egoveris.te.base.composer;

import com.egoveris.te.base.util.Utils;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Label;

public class UsuarioInvalidoComposer extends GenericForwardComposer {

	private static final long serialVersionUID = -2904415530452607122L;
	private AnnotateDataBinder binder;
	private Label uiLablMessage;

	public void onCreate$usuarioInvalido(Event event) {
		this.binder = (AnnotateDataBinder) event.getTarget().getAttribute(
				"binder", true);
		String username = Executions.getCurrent().getRemoteUser();
		this.uiLablMessage.setValue(Labels.getLabel("ee.login.noConfigurado",new Object[]{username}));
		this.binder.loadComponent(uiLablMessage);
	}

	public void onLogout() {
		Utils.doLogout();
	}

	public Label getUiLablMessage() {
		return uiLablMessage;
	}

	public void setUiLablMessage(Label uiLablMessage) {
		this.uiLablMessage = uiLablMessage;
	}

}