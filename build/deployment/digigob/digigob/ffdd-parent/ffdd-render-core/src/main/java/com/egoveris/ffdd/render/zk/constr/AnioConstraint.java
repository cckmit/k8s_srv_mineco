package com.egoveris.ffdd.render.zk.constr;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Constraint;

public class AnioConstraint implements Constraint {

	@Override
	public void validate(Component comp, Object value) throws WrongValueException {
		
		Long anio = (Long) value;
		if (anio == null) {
			return;
		}
		
		if (anio < 1900 || anio > 2999) {
			throw new WrongValueException(comp, "El año debe estar comprendido entre 1900 y 2999.");
		}
	}
}