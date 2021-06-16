package com.egoveris.ffdd.render.zk.constr;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Constraint;

public class CuitConstraint implements Constraint {

	@Override
	public void validate(Component comp, Object value) throws WrongValueException {
		
		if (value == null) {
			return;
		}
		
		String numeroCuit = String.valueOf((Long) value);
		if (numeroCuit.length() != 11) {
			throw new WrongValueException(comp, "El CUIT debe contener 11 dígitos");
		}
		
		// la secuencia de valores de factor es 5, 4, 3, 2, 7, 6, 5, 4, 3, 2
		int factor = 5;

		int[] c = new int[11];
		int resultado = 0;

		for (int i = 0; i < 10; i++) {
			// se toma el valor de cada cifra
			c[i] = Integer.valueOf(Character.toString(numeroCuit.charAt(i))).intValue();

			// se suma al resultado el producto de la cifra por el factor que
			// corresponde
			resultado = resultado + c[i] * factor;
			// se actualiza el valor del factor
			factor = (factor == 2) ? 7 : factor - 1;
		}

		c[10] = Integer.valueOf(Character.toString(numeroCuit.charAt(10))).intValue();

		// se obtiene el valor calculado a comparar
		int control = (11 - (resultado % 11)) % 11;

		// Si la cifra de control es distinta del valor calculado
		if (control != c[10]) {
			throw new WrongValueException(comp, "El CUIT no es válido");
		}
	}
}
