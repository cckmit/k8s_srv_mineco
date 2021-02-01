package com.egoveris.ffdd.render.zk.comp.cc;

import java.util.Date;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Cell;
import org.zkoss.zul.impl.InputElement;

public class DateReadonlyComposer extends ComplexComponentComposer {

	private static final long serialVersionUID = 8989623688678301110L;

	Cell fechaDiv;
	InputElement fecha;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		fecha.setRawValue(new Date());
		fecha.setDisabled(true);
	}

	@Override
	protected String getName() {
		return "dateReadonly";
	}	
	@Override
	protected void setDefaultValues(final String name) {
	}
	
}