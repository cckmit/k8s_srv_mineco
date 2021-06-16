package com.egoveris.te.base.composer;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Window;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ProducirDocumentoVM {

	/**
	 * 
	 */
	private static final long serialVersionUID = -85524705646812652L;

	@Wire
	private Iframe ifrProducirDocumento;

	@WireVariable("GEDOurl")
	private String gedoUrl;
	private Window tramitacionWindow;
	
	@AfterCompose
	public void init(@ContextParam(ContextType.VIEW) final Component view) {
		Selectors.wireComponents(view, this, false);
		String numeroSade = "";
		if (view.getParent() != null && view.getParent() instanceof Window) {
			tramitacionWindow = (Window) view.getParent();
			view.setParent(null);
		}
		
		if (Executions.getCurrent().getArg().get("numeroSade") != null) {
			numeroSade = (String) Executions.getCurrent().getArg().get("numeroSade");
			tramitacionWindow = (Window) Executions.getCurrent().getArg().get("tramitacionWindow");
		}
		
		String iframeUrl = gedoUrl + "/inbox.zul?numeroSade=" + numeroSade;
		
		ifrProducirDocumento.setSrc(iframeUrl);
	}
	
	@Command
	public void cerrarYActualizarExpediente() {
	 Events.echoEvent(Events.ON_USER, (Component) tramitacionWindow, "actualizarTramitacionRender");
	}

	/**
	 * @return the tramitacionWindow
	 */
	public Window getTramitacionWindow() {
		return tramitacionWindow;
	}

	/**
	 * @param tramitacionWindow the tramitacionWindow to set
	 */
	public void setTramitacionWindow(Window tramitacionWindow) {
		this.tramitacionWindow = tramitacionWindow;
	}
	
}
