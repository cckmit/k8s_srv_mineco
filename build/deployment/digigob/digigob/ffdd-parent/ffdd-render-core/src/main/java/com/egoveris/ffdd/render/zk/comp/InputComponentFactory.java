package com.egoveris.ffdd.render.zk.comp;

import com.egoveris.ffdd.model.model.FormularioComponenteDTO;
import com.egoveris.ffdd.render.model.InputComponent;
import com.egoveris.ffdd.render.service.IComponentFactory;
import com.egoveris.ffdd.render.zk.comp.ext.ComboboxExt;

import org.dozer.Mapper;
import org.zkoss.zul.Textbox;

public class InputComponentFactory<T extends InputComponent> implements IComponentFactory<InputComponent> {

	private Mapper mapper;
	private final Class<T> type;

	public InputComponentFactory(Class<T> clase) {
		this.type = clase;
	}

	public InputComponent create(FormularioComponenteDTO formComp) {
		InputComponent result = getMapper().map(formComp.getComponente(), this.type);
		agregarPropiedadesDeInstancia(formComp, result);
		return result;
	}

	public Mapper getMapper() {
		return mapper;
	}

	public void setMapper(Mapper mapper) {
		this.mapper = mapper;
	}
	
	private void agregarPropiedadesDeInstancia(FormularioComponenteDTO formComp, InputComponent zkComponent) {
		zkComponent.setName(formComp.getNombre());
		zkComponent.setIdComponentForm(formComp.getId());
		zkComponent.setVisible(formComp.getOculto() == null? true : !formComp.getOculto());
		if (zkComponent instanceof Textbox) {
			((Textbox) zkComponent).setWidth("90%");
			((Textbox) zkComponent).setHeight("20px");
		} else if (zkComponent instanceof ComboboxExt) {
			((ComboboxExt) zkComponent).setWidth("90%");
			((ComboboxExt) zkComponent).setHeight("20px");
		}
	}
}
