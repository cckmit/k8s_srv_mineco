package com.egoveris.te.base.composer;

import org.terasoluna.plus.core.util.ApplicationContextUtil;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Window;

import com.egoveris.commons.databaseconfiguration.propiedades.AppProperty;
import com.egoveris.te.base.model.HistorialOperacionDTO;
import com.egoveris.te.base.util.ConstantesServicios;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class GeoLocationVM {

	/**
	 * 
	 */
	private static final long serialVersionUID = -85524705646812652L;

	@Wire
	private Iframe iframeMap;
	
	@Wire
    Window iframeWindow;

	private AppProperty properties;
	
	@AfterCompose
	public void init(@ContextParam(ContextType.VIEW) final Component view, @ExecutionArgParam("pase") HistorialOperacionDTO historial){

		Selectors.wireComponents(view, this, false);
		properties =  (AppProperty) ApplicationContextUtil.getBean(ConstantesServicios.APP_PROPERTY);
		String iframeUrl = properties.getString("app.ee.url") + "/expediente/gmap.html?lat=" + historial.getLatitude()+"&lon="+historial.getLongitude();
		iframeMap.setSrc(iframeUrl);
	}

	@Command
    public void onCerrar() {
		iframeWindow.onClose();
    }

	public AppProperty getProperties() {
		return properties;
	}

	public void setProperties(AppProperty properties) {
		this.properties = properties;
	}

	public Iframe getIframeMap() {
		return iframeMap;
	}

	public void setIframeMap(Iframe iframeMap) {
		this.iframeMap = iframeMap;
	}
	public Window getIframeWindow() {
		return iframeWindow;
	}

	public void setIframeWindow(Window iframeWindow) {
		this.iframeWindow = iframeWindow;
	}

	
}
