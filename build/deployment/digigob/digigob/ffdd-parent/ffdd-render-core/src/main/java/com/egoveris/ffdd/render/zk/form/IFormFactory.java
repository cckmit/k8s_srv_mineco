package com.egoveris.ffdd.render.zk.form;

import org.zkoss.zk.ui.Component;

import com.egoveris.ffdd.model.model.FormularioDTO;
import com.egoveris.ffdd.model.model.TipoComponenteDTO;
import com.egoveris.ffdd.render.model.ComponentZkExt;
import com.egoveris.ffdd.render.service.IComponentFactory;

public interface IFormFactory {

	public Component create(FormularioDTO form);
	
	public IComponentFactory<ComponentZkExt> obtenerCompFactory(TipoComponenteDTO tipoComp);
	
}
