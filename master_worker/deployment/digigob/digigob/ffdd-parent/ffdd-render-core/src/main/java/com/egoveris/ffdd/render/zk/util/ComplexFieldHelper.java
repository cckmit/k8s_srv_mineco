package com.egoveris.ffdd.render.zk.util;

import java.util.Iterator;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;

import com.egoveris.ffdd.model.model.DynamicComponentFieldDTO;
import com.egoveris.ffdd.render.model.ComplexComponent;
import com.egoveris.ffdd.render.model.ComplexField;
import com.egoveris.ffdd.render.zk.comp.ext.TextboxExt;

public final class ComplexFieldHelper {

	/**
	 * 
	 * @param compSettings
	 * @param field
	 * @return
	 */
	public static ComplexField getField(final ComplexComponent compSettings, final String field) {
		ComplexField fieldSettings = null;
		if (null != compSettings.getField()) {
			Iterator<ComplexField> iterator = compSettings.getField().iterator();
			while (fieldSettings == null && iterator.hasNext()) {
				ComplexField complexField = iterator.next();
				if (field.equals(complexField.getName())) {
					fieldSettings = complexField;
				}
			}
		}
		if (fieldSettings == null) {
			// If field is not defined in xml we create a default Text field.
			fieldSettings = new ComplexField();
		}
		// si no está declarado en el xml se asume tipo TextboxExt
		if (fieldSettings.getClazz() == null) {
			fieldSettings.setClazz(TextboxExt.class.getCanonicalName());
			fieldSettings.setName(field);
		}
		return fieldSettings;
	}

	/**
	 * 
	 * @param dynamicFields
	 * @param name
	 * @return
	 */
	public static DynamicComponentFieldDTO obtenerDynamicField(Set<DynamicComponentFieldDTO> dynamicFields,
			String name) {
		DynamicComponentFieldDTO dynamicField = null;
		if (CollectionUtils.isNotEmpty(dynamicFields) && null != name) {
			Iterator<DynamicComponentFieldDTO> iterator = dynamicFields.iterator();
			while (null == dynamicField && iterator.hasNext()) {
				DynamicComponentFieldDTO dynamicComponentFieldDTO = iterator.next();
				if (name.equals(dynamicComponentFieldDTO.getName())) {
					dynamicField = dynamicComponentFieldDTO;
				}
			}
		}
		if (null == dynamicField) {
			dynamicField = new DynamicComponentFieldDTO();
		}
		return dynamicField;
	}

	private ComplexFieldHelper() {
	}
}
