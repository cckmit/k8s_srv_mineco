package com.egoveris.ffdd.render.zk.comp;

import com.egoveris.ffdd.model.model.FormularioComponenteDTO;
import com.egoveris.ffdd.render.model.ComponentZkExt;
import com.egoveris.ffdd.render.service.IComponentFactory;
import com.egoveris.ffdd.render.zk.comp.ext.FileUploadExt;

import org.springframework.stereotype.Service;

@Service
public class FileUploadFactory implements IComponentFactory<ComponentZkExt> {

	@Override
	public ComponentZkExt create(final FormularioComponenteDTO formComp) {
		return new FileUploadExt(formComp.getNombre(), formComp.getEtiqueta());
	}

}
