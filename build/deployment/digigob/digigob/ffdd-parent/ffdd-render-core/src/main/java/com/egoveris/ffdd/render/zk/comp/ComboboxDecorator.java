package com.egoveris.ffdd.render.zk.comp;

import com.egoveris.ffdd.model.model.ComponenteDTO;
import com.egoveris.ffdd.model.model.FormularioComponenteDTO;
import com.egoveris.ffdd.model.model.ItemDTO;
import com.egoveris.ffdd.render.model.InputComponent;
import com.egoveris.ffdd.render.service.IComponentFactory;
import com.egoveris.ffdd.render.zk.comp.ext.ComboboxExt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.zkoss.zul.Comboitem;

/**
 * Decorator de ComponentFactory
 * 
 * @author ilupi
 * 
 */
public class ComboboxDecorator implements IComponentFactory<ComboboxExt> {

	IComponentFactory<InputComponent> compFact;
	
	public static final String VACIO = "[Vacio]";

	public ComboboxDecorator(IComponentFactory<InputComponent> compFact) {
		this.compFact = compFact;
	}

	public ComboboxExt create(FormularioComponenteDTO formComp) {
		ComponenteDTO comp = formComp.getComponente();
		ComboboxExt c = (ComboboxExt) this.compFact.create(formComp);
		
		
		if (comp.getItems() != null) {
			List<ItemDTO> list = new ArrayList<ItemDTO>(comp.getItems());
			Collections.sort(list);	
			
			if(!formComp.getObligatorio()){
				   Comboitem cItem = new Comboitem(VACIO);
				   cItem.setValue(VACIO);
				   cItem.setParent(c);
				}
						
			for (ItemDTO item : list) {
				Comboitem cItem = new Comboitem(item.getDescripcion());
				cItem.setValue(item.getValor());
				cItem.setParent(c);
			}
		}
		
		
		return c;
	}
}