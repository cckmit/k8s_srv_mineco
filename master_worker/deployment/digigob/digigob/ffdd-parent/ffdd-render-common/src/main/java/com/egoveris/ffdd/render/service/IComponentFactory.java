package com.egoveris.ffdd.render.service;

import com.egoveris.ffdd.model.model.FormularioComponenteDTO;
import com.egoveris.ffdd.render.model.ComponentZkExt;

public interface IComponentFactory<T extends ComponentZkExt> {
	
	public T create(FormularioComponenteDTO formComp);

}
