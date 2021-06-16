package com.egoveris.ffdd.web.adm;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Combobox;


public class ConstraintRowComposer extends GenericForwardComposer{

	private static final long serialVersionUID = -7359757319259080738L;
	
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		Combobox combo2 = (Combobox)comp.getChildren().get(0);
		String path = Path.getPath(combo2);
		Component componente = (Component) Path.getComponent(path);
		System.out.println(componente);
	}
	
	

}
