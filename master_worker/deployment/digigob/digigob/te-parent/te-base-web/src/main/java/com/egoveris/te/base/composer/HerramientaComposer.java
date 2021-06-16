package com.egoveris.te.base.composer;

import com.egoveris.plugins.manager.PluginManager;
import com.egoveris.plugins.manager.model.ExecutableInfo;
import com.egoveris.plugins.manager.plugins.exceptions.ExecutableException;
import com.egoveris.plugins.manager.plugins.exceptions.UnauthorizedException;
import com.egoveris.plugins.manager.plugins.interfaces.IAuthorization;
import com.egoveris.te.base.util.ConstantesServicios;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Messagebox;
 


@SuppressWarnings("serial") 
public class HerramientaComposer extends GenericForwardComposer {

	private static Logger logger = LoggerFactory.getLogger(HerramientaComposer.class);
	
	 
    private PluginManager pm;
    
	private PluginManager getPm() { 
		if (pm==null) {
			this.pm = (PluginManager) SpringUtil.getBean(ConstantesServicios.PLUGIN_MANAGER);
		}
		return pm;
	}
	
	/* (non-Javadoc)
	 * @see org.zkoss.zk.ui.util.GenericComposer#doBeforeCompose(org.zkoss.zk.ui.Page, org.zkoss.zk.ui.Component, org.zkoss.zk.ui.metainfo.ComponentInfo)
	 */
	@Override
	public ComponentInfo doBeforeCompose(Page page, Component parent, ComponentInfo compInfo) {
		List<ExecutableInfo> executables = getPm().getExecsInfo();		
		page.setAttribute("executables", executables);
		
		return super.doBeforeCompose(page, parent, compInfo);
	}
	
	/**
	 * Metodo para ejecutar las diferentes aplicaciones
	 * @param event
	 * @throws InterruptedException
	 */
	public void onEjecutar(Event event) throws InterruptedException {
		try {
			getPm().execute((String) event.getData(), new IAuthorization() {
				@Override
				public boolean isAutorized(ExecutableInfo execInfo) {
					return true;
				}
			});
		} catch (UnauthorizedException e) {
			Messagebox.show(e.getExecutableInfo().getName() + Labels.getLabel("te.herramienta.error"));
		} catch (ExecutableException e) {
			logger.error(e.getMessage());
		}
	}
}
