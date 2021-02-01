package com.egoveris.ffdd.ws.model.complex;

import java.io.Serializable;
import java.lang.reflect.Field;

import com.egoveris.ffdd.model.exception.UnexpectedFormError;
import com.egoveris.ffdd.model.model.TransaccionDTO;
import com.egoveris.ffdd.model.model.ValorFormCompDTO;
import com.egoveris.ffdd.ws.util.FFDDConstants;

/**
 * All complex components created in FFDD that must be sent as concret java
 * classes must extends this class. All attributes of child classes must have
 * package access for the constructor sets their fields with reflection
 *
 * @author ggefaell
 *
 */
public class ComplexComponentDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;

	public ComplexComponentDTO() {
	}

	/**
	 * Creats a complex component given a transaction.
	 *
	 * @param transaccion
	 * @param parentName
	 * @param repeat
	 */
	public ComplexComponentDTO(final TransaccionDTO transaccion, final String parentName, final Integer repeat) {
		this.name = parentName;
		for (final ValorFormCompDTO value : transaccion.getValorFormComps()) {
			for (final Field field : this.getClass().getDeclaredFields()) {
				if (value.getInputName().split(FFDDConstants.NAME_SEPARATOR).length == 2 && repeat == 0) {
					if (value.getInputName().split(FFDDConstants.NAME_SEPARATOR)[0].equals(parentName)
							&& value.getInputName().split(FFDDConstants.NAME_SEPARATOR)[1].equals(field.getName())) {
						try {
							field.set(this, value.getValor());
						} catch (final IllegalArgumentException | IllegalAccessException e) {
							throw new UnexpectedFormError("Errr setting field value to complex DTO", e);
						}
					}
				}
				if (value.getInputName().split(FFDDConstants.NAME_SEPARATOR).length == 3 && repeat > 0) {
					if (value.getInputName().split(FFDDConstants.NAME_SEPARATOR)[0].equals(parentName)
							&& value.getInputName().split(FFDDConstants.NAME_SEPARATOR)[1].equals(field.getName())
							&& value.getInputName().split(FFDDConstants.NAME_SEPARATOR)[2].equals("R" + repeat)) {
						try {
							field.set(this, value.getValor());
						} catch (final IllegalArgumentException | IllegalAccessException e) {
							throw new UnexpectedFormError("Errr setting field value to complex DTO", e);
						}
					}
				}
			}
		}
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ComplexComponentDTO [name=").append(name).append("]");
		return builder.toString();
	}
}