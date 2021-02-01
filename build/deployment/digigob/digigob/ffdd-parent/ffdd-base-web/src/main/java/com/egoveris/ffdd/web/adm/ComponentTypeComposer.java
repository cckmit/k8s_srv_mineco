package com.egoveris.ffdd.web.adm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.spring.SpringUtil;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.DropEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelArray;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;

import com.egoveris.ffdd.base.service.ITipoComponenteService;
import com.egoveris.ffdd.model.model.DynamicComponentFieldDTO;
import com.egoveris.ffdd.model.model.TipoComponenteDTO;
import com.egoveris.ffdd.render.service.IComponentFactory;

/**
 * componentTypeWindow.zul
 * 
 * @author everis
 *
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ComponentTypeComposer extends GenericForwardComposer<Component> {


	/**
	 * 
	 */
	private static final long serialVersionUID = -3684361699912599300L;

	private static final Logger logger = LoggerFactory.getLogger(ComponentTypeComposer.class);

	/**
	 * i3-label property
	 */
	private static final String WINDOW_ATENCION_TITLE = "fc.export.atencion.title";

	/**
	 * i3-label property
	 */
	private static final String ERROR_SELECT_TYPE = "componentType.error.selectType";

	/**
	 * i3-label property
	 */
	private static final String ERROR_USED_NAME = "componentType.error.usedName";

	/**
	 * i3-label property
	 */
	private static final String MESSAGE_OK = "componentType.message.ok";

	/**
	 * i3-label property
	 */
	private static final String CONFIRM_DELETE = "componentType.confirm.delete";

	/**
	 * i3-label property
	 */
	private static final String MESSAGE_DELETED = "componentType.message.deleted";
	
	Window componentType;
	Vbox vboxTypes;
	Bandbox types;
	Listbox listboxTypes;
	Button btnEliminar;
	Combobox factories;
	Textbox typeName;
	Textbox typeDescription;

	private TipoComponenteDTO selectedType;
	private Integer idComponentType;
	private Boolean vboxTypesVisible = false;
	private DynamicComponentFieldDTO selectedDynamicComponentField;
	
	@WireVariable
	private ITipoComponenteService tipoComponenteService;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));

		final Map<String, IComponentFactory> beans = SpringUtil.getApplicationContext()
				.getBeansOfType(IComponentFactory.class);

		if (null != beans) {
			final List<String> factoryNames = new ArrayList<>(beans.keySet());
			factories.setModel(new ListModelArray(factoryNames));
		} else {
			logger.error("No IComponentFactory found in Spring");
		}

	}

	/**
	 * 
	 */
	public void onGuardar() {

		final TipoComponenteDTO existent = tipoComponenteService.obtenerTipoComponentePorNombre(typeName.getValue());
		if (null == existent || existent.getId().equals(idComponentType)) {

			TipoComponenteDTO tipo = new TipoComponenteDTO();
			tipo.setId(idComponentType);
			tipo.setNombre(typeName.getValue());
			tipo.setDescripcion(typeDescription.getValue());
			tipo.setFactory(factories.getValue());

			idComponentType = tipoComponenteService.saveComponentType(tipo).getId();
			Messagebox.show(Labels.getLabel(MESSAGE_OK), Labels.getLabel(WINDOW_ATENCION_TITLE), Messagebox.OK,
					Messagebox.INFORMATION);
			loadTiposComponente();
		} else {
			Messagebox.show(Labels.getLabel(ERROR_USED_NAME), Labels.getLabel(WINDOW_ATENCION_TITLE), Messagebox.OK,
					Messagebox.ERROR);
		}

	}

	/**
	 * 
	 */
	public void onShowTypes() {
		vboxTypesVisible = !vboxTypesVisible;
		if (vboxTypesVisible) {
			loadTiposComponente();
		}
		vboxTypes.setVisible(vboxTypesVisible);
	}

	private void loadTiposComponente() {
		final List<TipoComponenteDTO> listTypes = tipoComponenteService.obtenerTodos();
		listboxTypes.setModel(new BindingListModelList<>(listTypes, true));
	}

	/**
	 * 
	 */
	public void onClearFields() {
		idComponentType = null;
		factories.setRawValue(null);
		factories.setDisabled(false);
		typeName.setRawValue(null);
		typeName.setDisabled(false);
		typeDescription.setRawValue(null);
		vboxTypesVisible = false;
		vboxTypes.setVisible(vboxTypesVisible);
		types.setRawValue(null);
		selectedType = null;
	}

	public void onSelectType() {
		types.setValue(selectedType.getNombre());
		Boolean disableEliminar = tipoComponenteService.findIfTypeIsUsed(selectedType);
		btnEliminar.setDisabled(disableEliminar);
		types.close();
	}

	public void onModificar() {
		if (null != selectedType) {
			idComponentType = selectedType.getId();
			factories.setValue(selectedType.getFactory());
			factories.setDisabled(true);
			typeName.setValue(selectedType.getNombre());
			typeName.setDisabled(true);
			typeDescription.setValue(selectedType.getDescripcion());
		} else {
			Messagebox.show(Labels.getLabel(ERROR_SELECT_TYPE), Labels.getLabel(WINDOW_ATENCION_TITLE), Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	public void onClonar() {
		if (null != selectedType) {
			idComponentType = null;
			factories.setDisabled(false);
			factories.setValue(selectedType.getFactory());
			typeName.setRawValue(null);
			typeName.setDisabled(false);
			typeDescription.setValue(selectedType.getDescripcion());
			selectedType = null;
			types.setRawValue(null);
			listboxTypes.setSelectedItem(null);
			btnEliminar.setDisabled(true);
		} else {
			Messagebox.show(Labels.getLabel(ERROR_SELECT_TYPE), Labels.getLabel(WINDOW_ATENCION_TITLE), Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	public void onEliminar() {
		if (selectedType != null) {
			final String label = new StringBuilder().append(Labels.getLabel(CONFIRM_DELETE)).append(" ")
					.append(selectedType.getNombre()).append("?").toString();
			Messagebox.show(label, Labels.getLabel(WINDOW_ATENCION_TITLE),
					Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener<Event>() {

						@Override
						public void onEvent(Event event) throws Exception {
							if (Messagebox.YES == (int) event.getData()) {
								tipoComponenteService.deleteType(selectedType);
								onClearFields();
								Messagebox.show(Labels.getLabel(MESSAGE_DELETED),
										Labels.getLabel(WINDOW_ATENCION_TITLE), Messagebox.OK, Messagebox.INFORMATION);
							}
						}
					});

		}
	}

	public void onDropType(ForwardEvent event) {
		final Listitem item = (Listitem) ((DropEvent) event.getOrigin()).getDragged();
		this.selectedType = item.getValue();
		listboxTypes.setSelectedItem(item);
		onSelectType();
		onModificar();
	}

	/**
	 * @return the selectedType
	 */
	public TipoComponenteDTO getSelectedType() {
		return selectedType;
	}

	/**
	 * @param selectedType
	 *            the selectedType to set
	 */
	public void setSelectedType(TipoComponenteDTO selectedType) {
		this.selectedType = selectedType;
	}

}
