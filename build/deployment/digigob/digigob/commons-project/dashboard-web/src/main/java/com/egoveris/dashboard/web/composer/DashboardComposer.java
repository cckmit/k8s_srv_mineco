package com.egoveris.dashboard.web.composer;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkmax.zul.Navbar;
import org.zkoss.zul.A;
import org.zkoss.zul.Div;


public class DashboardComposer extends SelectorComposer<Component> {
	
	private static final long serialVersionUID = 1L;
	    
	@Wire
	Div sidebar;
	
	@Wire("#navegacion #navbar")
	Navbar navbar;

	@Wire
	A toggler;
	
	// Toggle sidebar to collapse or expand
	@Listen("onClick = #toggler")
	public void toggleSidebarCollapsed() {
		if (navbar.isCollapsed()) {
			sidebar.setSclass("sidebar");
			navbar.setCollapsed(false);
			toggler.setIconSclass("z-icon-angle-double-left");
		} else {
			sidebar.setSclass("sidebar sidebar-min");
			navbar.setCollapsed(true);
			toggler.setIconSclass("z-icon-angle-double-right");
		}
		
		// Force the hlayout contains sidebar to recalculate its size
		Clients.resize(sidebar.getRoot().query("#main"));
	}
	
}