package com.egoveris.ffdd.web.adm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelArray;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.egoveris.ffdd.base.service.IComponenteService;
import com.egoveris.ffdd.model.model.DynamicComponentFieldDTO;
import com.egoveris.ffdd.render.model.ComplexField;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class CComplejoDetailComposer extends GenericForwardComposer<Component> {


	/**
	 * 
	 */
	private static final long serialVersionUID = -1906307368901180407L;

	private static final Logger logger = LoggerFactory.getLogger(CComplejoDetailComposer.class);

	private static final String WINDOW_ATENCION_TITLE = "fc.export.atencion.title";

	private static final String ERROR_PARENT = "ccomplejoDetail.error.parent";
	
	private static final String Combo = "comboBoxFactory";

	private static final String DEFAULT_VALUE_WINDOW = "/administradorFC/defaultValueWindow.zul";

	@WireVariable("componenteService")
	private IComponenteService componentService;
	
	Window ccomplejoDetail;
	Label fieldName;
	Label factory;
	Textbox label;
	Textbox defaultValue;
	Combobox parent;
	Combobox dependence;
	Label lblDependence;
	Checkbox hidden;
	Checkbox required;
	Checkbox disabled;
	Textbox style;
	Textbox width;
	Textbox height;
	Textbox tooltip;
	Button btnChangeDefaultValue;

	private DynamicComponentFieldDTO field;
	private ComplexField baseSettings;
	private List<DynamicComponentFieldDTO> listComponents;
	private List<DynamicComponentFieldDTO> listComponentsClean;
	private DynamicComponentFieldDTO selectListComponents;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
		field = (DynamicComponentFieldDTO) Executions.getCurrent().getArg().get("field");
		baseSettings = (ComplexField) Executions.getCurrent().getArg().get("baseSettings");
		listComponents = (List<DynamicComponentFieldDTO>) Executions.getCurrent().getArg().get("listComponents");
		
	
		listComponentsClean = new ArrayList<>();
		
		if (!listComponents.isEmpty()) {
			for (DynamicComponentFieldDTO listComponentsAux : listComponents) {
				if (!baseSettings.getName().equals(listComponentsAux.getName())) {
					listComponentsClean.add(listComponentsAux);
					
				}
			}
		}
		fieldName.setValue(field.getName());
		factory.setValue(field.getType());
		label.setValue(field.getLabel());
		defaultValue.setValue(field.getDefaultValue());
		hidden.setValue(field.getHidden());
		hidden.setChecked(Boolean.TRUE.equals(field.getHidden()));
		hidden.setDisabled(Boolean.TRUE.equals(field.getDisableHidden()));
		required.setValue(field.getRequired());
		required.setChecked(Boolean.TRUE.equals(field.getRequired()));
		required.setDisabled(Boolean.TRUE.equals(field.getDisableRequired()));
		if (Combo.equals(field.getType())) {
			dependence.setVisible(true);
			lblDependence.setVisible(true);
			dependence.setValue(field.getDependence());
		} else {
			dependence.setVisible(false);
			lblDependence.setVisible(false);
		}
		style.setValue(field.getStyle());
		width.setValue(field.getWidth());
		height.setValue(field.getHeight());
		tooltip.setValue(field.getTooltip());
		disabled.setChecked(Boolean.TRUE.equals(field.getDisabled()));

		btnChangeDefaultValue.setVisible(!"SeparatorComplex".equals(field.getType()));	
		
		// TODO Seleccionar por tipo?
		List<String> opcionesParent = null;
		if (null != baseSettings.getXmlFile()) {
			opcionesParent = componentService.findComponentsByXml(baseSettings.getXmlFile());
		} else if (null != baseSettings.getFactory()) {
			opcionesParent = componentService.findComponentsByFactory(baseSettings.getFactory());
		}

		if (CollectionUtils.isNotEmpty(opcionesParent)) {
			parent.setModel(new ListModelArray<>(opcionesParent));
			if ( opcionesParent.contains(field.getParent())) {
				parent.setValue(field.getParent());
			} else if (StringUtils.isNotBlank(field.getParent())) {
				Messagebox.show(Labels.getLabel(ERROR_PARENT), Labels.getLabel(WINDOW_ATENCION_TITLE), Messagebox.OK,
						Messagebox.EXCLAMATION);
			}
		} else {
			parent.setDisabled(true);
		}
	}

	/**
	 * 
	 */
	public void onGuardar() {
		field.setLabel(label.getValue());
		field.setDefaultValue(defaultValue.getValue());
		field.setHidden(hidden.isChecked());
		field.setRequired(required.isChecked());
		field.setDisabled(disabled.isChecked());
		field.setStyle(style.getValue());
		field.setWidth(width.getValue());
		field.setHeight(height.getValue());	
		field.setTooltip(tooltip.getValue());
		field.setParent(parent.getValue());
		field.setDependence(dependence.getValue());
		Events.sendEvent(Events.ON_CLOSE, ccomplejoDetail, null);
	}

	/**
	 * 
	 */
	public void onChangeDefaultValue() {
		final DynamicComponentFieldDTO tempField = new DynamicComponentFieldDTO();
		tempField.setLabel(label.getValue());
		tempField.setDefaultValue(defaultValue.getValue());
		tempField.setHidden(hidden.isChecked());
		tempField.setRequired(required.isChecked());
		tempField.setDisabled(disabled.isChecked());
		tempField.setStyle(style.getValue());
		tempField.setWidth(width.getValue());
		tempField.setHeight(height.getValue());
		tempField.setTooltip(tooltip.getValue());
		tempField.setParent(parent.getValue());
		Map<String, Object> arg = new HashMap<>();
		arg.put("field", tempField);
		arg.put("baseSettings", baseSettings);
		Window modal = (Window) Executions.createComponents(DEFAULT_VALUE_WINDOW, this.self, arg);
		modal.doModal();
		modal.addEventListener(Events.ON_CLOSE, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				defaultValue.setValue(tempField.getDefaultValue());
			}
		});
	}

//	Getters Y Setters
	public List<DynamicComponentFieldDTO> getListComponents() {
		return listComponents;
	}

	public void setListComponents(List<DynamicComponentFieldDTO> listComponents) {
		this.listComponents = listComponents;
	}

	public List<DynamicComponentFieldDTO> getListComponentsClean() {
		return listComponentsClean;
	}

	public void setListComponentsClean(List<DynamicComponentFieldDTO> listComponentsClean) {
		this.listComponentsClean = listComponentsClean;
	}

	public DynamicComponentFieldDTO getSelectListComponents() {
		return selectListComponents;
	}

	public void setSelectListComponents(DynamicComponentFieldDTO selectListComponents) {
		this.selectListComponents = selectListComponents;
	}



}
