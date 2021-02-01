package com.egoveris.ffdd.web.adm;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.spring.SpringUtil;
import org.zkoss.text.DateFormats;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.impl.InputElement;

import com.egoveris.ffdd.model.model.DynamicComponentFieldDTO;
import com.egoveris.ffdd.model.model.FormularioComponenteDTO;
import com.egoveris.ffdd.render.model.ComplexField;
import com.egoveris.ffdd.render.model.ComponentZkExt;
import com.egoveris.ffdd.render.model.InputComponent;
import com.egoveris.ffdd.render.service.IComplexFieldService;
import com.egoveris.ffdd.render.service.IComponentFactory;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class DefaultValueComposer extends GenericForwardComposer<Component> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3723043735328473128L;

	private static final Logger logger = LoggerFactory.getLogger(DefaultValueComposer.class);

	@WireVariable
	private IComplexFieldService complexFieldService;

	Window defaultValueWindow;
	Cell valueCell;
	Component valueComponent;

	private DynamicComponentFieldDTO field;
	private ComplexField baseSettings;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));

		field = (DynamicComponentFieldDTO) Executions.getCurrent().getArg().get("field");
		baseSettings = (ComplexField) Executions.getCurrent().getArg().get("baseSettings");

		// build component
		valueComponent = createElement();
		if (null != valueComponent) {
			valueComponent.setParent(valueCell);
			setDefaultValue();
		}

	}

	public void onSaveValue() {
		final Object defaultValue = getDefaultValue();
		if (null != defaultValue) {
			field.setDefaultValue(defaultValue.toString());
		} else {
			field.setDefaultValue(null);
		}

		Events.sendEvent(Events.ON_CLOSE, defaultValueWindow, null);
	}

	/**
	 * Creates the component instance, setting its attributes from the
	 * definition.
	 *
	 * @param componentSettings
	 *            Complex component definition.
	 * @param field
	 *            Field name to create. This field class and attributes are read
	 *            from the definition.
	 * @return
	 */
	private Component createElement() {
		final FormularioComponenteDTO formComp = complexFieldService.createFormComp(field, baseSettings);
		Component instance = null;

		try {
			// se genera una instancia del tipo indicado en el xml
			if (null != baseSettings.getFactory()) {
				IComponentFactory<ComponentZkExt> factory = (IComponentFactory<ComponentZkExt>) SpringUtil
						.getBean(baseSettings.getFactory());
				if (null != factory) {
					instance = factory.create(formComp);
				}

			} else {
				instance = complexFieldService.createInstance(baseSettings);

			}

		} catch (ReflectiveOperationException e) {
			logger.error(
					"Error creando elemento campo " + field + " componente " + formComp.getComponente().getNombre(), e);
		}
		return instance;
	}

	private void setDefaultValue() {
		if (StringUtils.isNotEmpty(field.getDefaultValue())) {
			if (valueComponent instanceof Datebox) {
				final Datebox dateboxExt = (Datebox) valueComponent;
				String strFormat = dateboxExt.getFormat();
				if (StringUtils.isEmpty(strFormat)) {
					strFormat =  DateFormats.getDateFormat(DateFormat.DEFAULT, dateboxExt.getLocale(), "dd-MMM-yyyy");
				}
				final DateFormat format = new SimpleDateFormat(strFormat);
				try {
					final Date value = format.parse(field.getDefaultValue());
					dateboxExt.setRawValue(value);
				} catch (ParseException e) {
					logger.error("Wrong default value: " + field.getDefaultValue() + ", format: " + strFormat, e);
				}
			} else if (valueComponent instanceof InputElement) {
				try{
					((InputElement) valueComponent).setRawValue(field.getDefaultValue());	
				}catch (ClassCastException cce){
					((InputElement) valueComponent).setRawValue(Long.valueOf(field.getDefaultValue()));
				}
				
			} else if (valueComponent instanceof InputComponent) {
				((InputComponent) valueComponent).setRawValue(field.getDefaultValue());
			}
		}
	}

	private Object getDefaultValue() {
		Object defaultValue = null;
		if (valueComponent instanceof Datebox) {
			final Datebox dateboxExt = (Datebox) valueComponent;
			String strFormat = dateboxExt.getFormat();
			if (StringUtils.isEmpty(strFormat)) {
				strFormat = DateFormats.getDateFormat(DateFormat.DEFAULT, dateboxExt.getLocale(), "dd-MMM-yyyy");
			}
			final DateFormat format = new SimpleDateFormat(strFormat);
			final Date value = dateboxExt.getValue();
			if (null != value) {
				defaultValue = format.format(value);
			}
		} else if (valueComponent instanceof InputElement) {
			defaultValue = ((InputElement) valueComponent).getRawValue();
		} else if (valueComponent instanceof InputComponent) {
			defaultValue = ((InputComponent) valueComponent).getRawValue();
		}
		return defaultValue;
	}

}
