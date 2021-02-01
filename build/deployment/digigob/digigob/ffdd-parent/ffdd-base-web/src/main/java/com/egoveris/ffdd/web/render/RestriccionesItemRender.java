package com.egoveris.ffdd.web.render;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.egoveris.ffdd.model.model.FormularioComponenteDTO;
import com.egoveris.ffdd.model.model.VisibilidadComponenteDTO;


public class RestriccionesItemRender implements ListitemRenderer{
	
	private VisibilidadComponenteDTO restriccionDto;
	
	public RestriccionesItemRender(VisibilidadComponenteDTO restriccionDto){
		this.restriccionDto = restriccionDto;
	}

	@Override
	public void render(Listitem item, Object data,int index) throws Exception {
		item.setValue(data);
		Listcell lc = new Listcell();
		FormularioComponenteDTO fc = (FormularioComponenteDTO) data;
		lc.setLabel(fc.getNombre());
		lc.setParent(item);
		for(String nombre: restriccionDto.getComponentesOcultos()){
			if(nombre.equals(fc.getNombre())){
				item.setSelected(true);
				//and set checked
			}
		}
	}

}
