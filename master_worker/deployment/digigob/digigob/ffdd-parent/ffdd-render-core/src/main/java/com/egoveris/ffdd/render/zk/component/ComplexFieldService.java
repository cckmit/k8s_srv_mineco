package com.egoveris.ffdd.render.zk.component;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.impl.InputElement;

import com.egoveris.ffdd.model.exception.DynFormException;
import com.egoveris.ffdd.model.exception.NotFoundException;
import com.egoveris.ffdd.model.model.AtributoComponenteDTO;
import com.egoveris.ffdd.model.model.ComponenteDTO;
import com.egoveris.ffdd.model.model.DynamicComponentFieldDTO;
import com.egoveris.ffdd.model.model.FormularioComponenteDTO;
import com.egoveris.ffdd.model.model.FormularioDTO;
import com.egoveris.ffdd.model.model.ItemDTO;
import com.egoveris.ffdd.model.model.TipoComponenteDTO;
import com.egoveris.ffdd.render.model.ComplexField;
import com.egoveris.ffdd.render.service.IComplexFieldService;
import com.egoveris.ffdd.render.zk.comp.ext.ComboboxExt;
import com.egoveris.ffdd.render.zk.comp.ext.DateboxExt;
import com.egoveris.ffdd.render.zk.comp.ext.TextboxExt;
import com.egoveris.ffdd.ws.service.ExternalComponentService;
import com.egoveris.ffdd.ws.service.ExternalFormularioService;

@Service
public class ComplexFieldService implements IComplexFieldService {

	private static final Logger logger = LoggerFactory.getLogger(ComplexFieldService.class);

	protected static final String DEFAULT_WIDTH = "80px";
	protected static final String DEFAULT_HEIGHT = "20px";

	@Autowired
	protected ExternalFormularioService externalFormularioService;

	@Autowired
	protected ExternalComponentService externalComponentService;

	public Component createInstance(final ComplexField fieldSettings) throws ReflectiveOperationException {
		Component instance;
		final Class<?> clazz = Class.forName(fieldSettings.getClazz());

		instance = (Component) clazz.newInstance();
		if (instance instanceof DateboxExt) {
			setDateAttributes(fieldSettings, (DateboxExt) instance);
		}
		if (instance instanceof TextboxExt) {
			setTextAttributes(fieldSettings, (TextboxExt) instance);
		}
		if (instance instanceof ComboboxExt) {
			setComboAttributes(fieldSettings, (ComboboxExt) instance);
		}
		setGenericAttributes(fieldSettings, instance);
		return instance;
	}

	private void setDateAttributes(final ComplexField fieldSettings, final DateboxExt instance) {
		instance.setFormat(fieldSettings.getFormat());
	}

	private void setTextAttributes(final ComplexField fieldSettings, final TextboxExt instance) {
		if (fieldSettings.isMultiline() != null) {
			instance.setMultiline(fieldSettings.isMultiline());
		}
	}

	/**
	 * Sets generic attributes like css style, required and tooltip text.
	 *
	 * @param fieldSettings
	 * @param instance
	 */
	private void setGenericAttributes(final ComplexField fieldSettings, final Component instance) {
		if (instance instanceof HtmlBasedComponent) {
			final HtmlBasedComponent in = (HtmlBasedComponent) instance;
			if (fieldSettings.getTooltip() != null) {
				in.setTooltiptext(fieldSettings.getTooltip());
			}
			if (fieldSettings.getStyle() != null) {
				in.setStyle(fieldSettings.getStyle());
			}
			in.setWidth(getWidth(fieldSettings));
			in.setHeight(getHeight(fieldSettings));
		}
		if (instance instanceof InputElement) {
			((InputElement) instance).setDisabled(fieldSettings.isDisabled());
		}
		if (instance instanceof Checkbox) {
			((Checkbox) instance).setDisabled(fieldSettings.isDisabled());
		}
		if (instance instanceof Combobox) {
			((Combobox) instance).setDisabled(fieldSettings.isDisabled());
		}

	}

	private void setComboAttributes(final ComplexField fieldSettings, final ComboboxExt instance) {

		if (null != fieldSettings.getComboName()) {
			try {
				final ComponenteDTO combo = externalFormularioService
						.buscarComponentePorNombre(fieldSettings.getComboName());
				if (combo == null) {
					throw new NotFoundException(
							"No se encuentra el combo con el nombre " + fieldSettings.getComboName());
				}
				for (final ItemDTO it : combo.getItems()) {
					final Comboitem cItem = new Comboitem(it.getValor());
					cItem.setDescription(it.getDescripcion());
					cItem.setParent(instance);
				}
			} catch (final DynFormException e) {
				logger.error("Error obteniendo el combo " + fieldSettings.getComboName(), e);
			}
		}

	}

	private String getWidth(final ComplexField fieldSettings) {

		String width = DEFAULT_WIDTH;
		if (fieldSettings != null && null != fieldSettings.getWidth()) {
			width = fieldSettings.getWidth();
		}
		return width;
	}

	private String getHeight(final ComplexField fieldSettings) {
		String height = DEFAULT_HEIGHT;
		if (fieldSettings != null && null != fieldSettings.getHeight()) {
			height = fieldSettings.getHeight();
		}
		return height;
	}

	/**
	 * 
	 * @param dynamicField
	 * @param fieldSettings
	 * @return
	 */
	public FormularioComponenteDTO createFormComp(DynamicComponentFieldDTO dynamicField, ComplexField fieldSettings) {

		FormularioComponenteDTO formComp = new FormularioComponenteDTO();
		formComp.setId(0);
		formComp.setComponente(new ComponenteDTO());
		formComp.getComponente().setAtributos(new HashMap<>());
		formComp.getComponente().setTipoComponenteEnt(new TipoComponenteDTO());
		formComp.setFormulario(new FormularioDTO());
		formComp.setObligatorio(Boolean.TRUE.equals(fieldSettings.isRequired()));
		formComp.setOculto(Boolean.TRUE.equals(fieldSettings.isHidden()));
		formComp.setEtiqueta(fieldSettings.getLabel());
		formComp.getComponente().setNombreXml(fieldSettings.getXmlFile());

		String parent = fieldSettings.getComboName();

		if (null != dynamicField.getRequired() && !formComp.getObligatorio()) {
			formComp.setObligatorio(dynamicField.getRequired());
		}
		if (null != dynamicField.getHidden()) {
			formComp.setOculto(dynamicField.getHidden());
		}
		if (StringUtils.isNotBlank(dynamicField.getLabel())) {
			formComp.setEtiqueta(dynamicField.getLabel());
		}

		if (StringUtils.isNotBlank(dynamicField.getParent())) {
			parent = dynamicField.getParent();
		}

		if (StringUtils.isNotBlank(parent)) {
			ComponenteDTO componenteDTO = externalComponentService.buscarComponentePorNombre(parent);
			if (componenteDTO != null) {
				formComp.setComponente(componenteDTO);
			}
		}

		if (null == formComp.getComponente().getAtributos()) {
			formComp.getComponente().setAtributos(new HashMap<>());
		}

		fieldSettings.setHeight(addComponenteAtributos(formComp.getComponente().getAtributos(), "height",
				dynamicField.getHeight(), fieldSettings.getHeight()));
		fieldSettings.setStyle(addComponenteAtributos(formComp.getComponente().getAtributos(), "style",
				dynamicField.getStyle(), fieldSettings.getStyle()));
		fieldSettings.setTooltip(addComponenteAtributos(formComp.getComponente().getAtributos(), "tooltiptext",
				dynamicField.getTooltip(), fieldSettings.getTooltip()));
		fieldSettings.setWidth(addComponenteAtributos(formComp.getComponente().getAtributos(), "width",
				dynamicField.getWidth(), fieldSettings.getWidth()));
		fieldSettings.setDisabled(Boolean.valueOf(addComponenteAtributos(formComp.getComponente().getAtributos(),
				"disabled", dynamicField.getDisabled(), fieldSettings.isDisabled())));
		addComponenteAtributos(formComp.getComponente().getAtributos(), "format", null, fieldSettings.getFormat());

		return formComp;
	}

	/**
	 * 
	 * @param atributos
	 * @param key
	 * @param valor
	 */
	private String addComponenteAtributos(final Map<String, AtributoComponenteDTO> atributos, final String key,
			final Object valor, final Object defaultValue) {
		String finalValue = null;
		final boolean validValue = null != valor && StringUtils.isNotBlank(valor.toString());
		if (validValue || null != defaultValue) {
			final AtributoComponenteDTO atributo = new AtributoComponenteDTO();
			atributo.setKey(key);
			if (validValue) {
				finalValue = valor.toString();
			} else {
				finalValue = defaultValue.toString();
			}

			atributo.setValor(finalValue);
			atributos.put(key, atributo);
		}
		return finalValue;
	}
}
