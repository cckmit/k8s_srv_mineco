package com.egoveris.ffdd.web.adm;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.DropEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelArray;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;

import com.egoveris.ffdd.base.service.IComponenteService;
import com.egoveris.ffdd.base.service.IFormularioService;
import com.egoveris.ffdd.base.service.ITipoComponenteService;
import com.egoveris.ffdd.model.exception.DynFormException;
import com.egoveris.ffdd.model.model.ComponenteDTO;
import com.egoveris.ffdd.model.model.DynamicComponentFieldDTO;
import com.egoveris.ffdd.model.model.FormularioComponenteDTO;
import com.egoveris.ffdd.model.model.FormularioDTO;
import com.egoveris.ffdd.model.model.TipoComponenteDTO;
import com.egoveris.ffdd.render.model.ComplexComponent;
import com.egoveris.ffdd.render.model.ComplexField;
import com.egoveris.ffdd.render.service.IComplexComponentService;
import com.ibm.icu.text.DateFormat.Field;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class CComplejoComposer extends GenericForwardComposer<Component> {



	private static final String CCOMPLEJO_DETAIL_WINDOW = "/administradorFC/ccomplejoDetailWindow.zul";

	private static final String COMPONENT_TYPE_WINDOW = "/administradorFC/componentTypeWindow.zul";

	/**
	 * 
	 */
	private static final long serialVersionUID = 6854321948254903032L;

	private static final Logger logger = LoggerFactory.getLogger(CComplejoComposer.class);

	private static final String TITLE_CONFIRMACION = "fc.eli.title.confirmacion";

	private static final String COMPLEX_COMPONENT_FACTORY = "complexComponentFactory";

	private static final String ERROR_SHOW_TYPES = "ccomplejos.error.showTypes";

	private static final String MESSAGE_DELETED = "ccomplejos.message.deleted";

	private static final String MESSAGE_CONFIRM_DELETE = "ccomplejos.message.confirmDelete";

	private static final String ERROR_PREVIEW = "ccomplejos.error.preview";

	private static final String WINDOW_ATENCION_TITLE = "fc.export.atencion.title";

	private static final String MESSAGE_OK = "ccomplejos.message.ok";

	private static final String ERROR_USED_NAME = "ccomplejos.error.usedName";

	private static final String ERROR_SELECTED_COMPONENT = "ccomplejos.error.selectedComponent";

	private static final String ERROR_COMPONENT_SETTINGS = "ccomplejos.error.componentSettings";

	private static final String PREVIEW_TITLE = "ccomplejos.preview.title";

	private static final String PREVISUALIZACION_FC_WINDOW = "/administradorFC/previsualizacionFC.zul";

	Window ccomplejo;
	Window componentTypeWindow;
	Window previsualizarWindow;
	Window ccomplejoDetailWindow;
	Combobox entity;
	Combobox type;
	Textbox componentName;
	Textbox description;
	Combobox fieldName;
	Textbox fieldLabel;
	Combobox fieldParent;
	Checkbox hidden;
	Checkbox required;
	Listbox listboxDynamicComponentFields;
	Vbox vboxComponents;
	Bandbox componentes;
	Listbox listboxComponentes;
	Button btnEliminar;
	
	private Boolean disabledEdition = false;

	private List<DynamicComponentFieldDTO> listDynamicComponentFields;
	private ComponenteDTO selectedComponent;
	private Integer idComponente;
	private Boolean vboxComponentsVisible = false;
	private Map<String, TipoComponenteDTO> tipos = new HashMap<>();
	private Map<String, ComplexField> baseSettings = new HashMap<>();
	private List<ComponenteDTO> listComponents;

	@WireVariable("componenteService")
	private IComponenteService componentService;
	@WireVariable("complexComponentService")
	private IComplexComponentService complexComponentService;
	@WireVariable("formularioService")
	private IFormularioService formularioService;
	@WireVariable("tipoComponenteService")
	private ITipoComponenteService tipoComponenteService;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
		logger.debug("doAfterCompose");
		listDynamicComponentFields = new ArrayList<>();

		// listaEntidades
		List<String> entidades = componentService.findDistinctNombreXml();
		entity.setModel(new ListModelArray<>(entidades));

		loadComponentTypes();

	}

	private void loadComponentTypes() {
		// listaTipos
		List<TipoComponenteDTO> listTipoComponente = tipoComponenteService.findByFactory(COMPLEX_COMPONENT_FACTORY);
		tipos.clear();
		if (CollectionUtils.isNotEmpty(listTipoComponente)) {
			for (TipoComponenteDTO tipo : listTipoComponente) {
				tipos.put(tipo.getNombre(), tipo);
			}
		}
		type.setModel(new ListModelList<>(tipos.keySet()));
	}

	/**
	 * 
	 */
	public void onSelectEntidad() {
		loadEntitySettings();
		refreshListboxs();
	}

	/**
	 * 
	 */
	public void onSelectComponente() {
		componentes.setValue(selectedComponent.getNombre());
		final Boolean disableDelete = componentService.findIfComponentIsUsed(selectedComponent.getNombre());
		btnEliminar.setDisabled(disableDelete);
		componentes.close();
	}

	/**
	 * @param event
	 */
	public void onDropComponent(ForwardEvent event) {
		final Listitem item = (Listitem) ((DropEvent) event.getOrigin()).getDragged();
		this.selectedComponent = item.getValue();
		listboxComponentes.setSelectedItem(item);
		onSelectComponente();
		loadSelectedComponent();
		refreshListboxs();
	}

	/**
	 * 
	 */
	public void onModificar() {
		loadSelectedComponent();
		refreshListboxs();
	}

	/**
	 * 
	 */
	public void onClonar() {
		loadSelectedComponent();
		idComponente = null;
		componentName.setRawValue(null);
		entity.setDisabled(false);
		componentName.setDisabled(false);
		if (CollectionUtils.isNotEmpty(listDynamicComponentFields)) {
			for (DynamicComponentFieldDTO field : listDynamicComponentFields) {
				field.setId(null);
			}
		}
		componentes.setRawValue(null);
		listboxComponentes.setSelectedItem(null);
		btnEliminar.setDisabled(true);
		vboxComponentsVisible = false;
		vboxComponents.setVisible(false);

		refreshListboxs();
	}

	private void loadSelectedComponent() {
		if (null != selectedComponent) {
			idComponente = selectedComponent.getId();
			entity.setValue(selectedComponent.getNombreXml());
			entity.setDisabled(true);
			loadEntitySettings();
			componentName.setValue(selectedComponent.getNombre());
			componentName.setDisabled(true);
			description.setValue(selectedComponent.getDescripcion());
			type.setValue(selectedComponent.getTipoComponente());
			if (CollectionUtils.isNotEmpty(selectedComponent.getDynamicFields())) {
				loadComponentDynamicFields();
			}
		} else {
			throw new DynFormException(Labels.getLabel(ERROR_SELECTED_COMPONENT));
		}
	}

	private void loadEntitySettings() {
		listDynamicComponentFields.clear();
		baseSettings.clear();
		try {
			ComplexComponent componentSettings = complexComponentService.getComponent(entity.getValue());
			if (null != componentSettings) {
				if (tipos.containsKey(componentSettings.getType())) {
					type.setValue(componentSettings.getType());
				}
				if (CollectionUtils.isNotEmpty(componentSettings.getField())) {
					disabledEdition(componentSettings);
					loadDefaultFieldSettings(componentSettings.getField());
				}
			} else {
				entity.setRawValue(null);
				throw new DynFormException(Labels.getLabel(ERROR_COMPONENT_SETTINGS));
			}
		} catch (IllegalArgumentException e) {
			entity.setRawValue(null);
			throw new DynFormException(Labels.getLabel(ERROR_COMPONENT_SETTINGS), e);
		}
	}

	private void disabledEdition(ComplexComponent componentSettings) {
		
		ComplexField disabled = componentSettings
				.getField().stream()
				.filter(field -> field.getName().equals("flagDisabled"))
				.findFirst().orElse(null);
		
		this.disabledEdition = disabled != null ? true : false;
		
		if(disabledEdition) {
			componentSettings.getField().removeIf(field-> field.getName().equals("flagDisabled"));
		}
	}

	/**
	 * 
	 * @param componentSettings
	 */
	private void loadDefaultFieldSettings(List<ComplexField> fields) {
		for (ComplexField field : fields) {
			DynamicComponentFieldDTO fieldDTO = new DynamicComponentFieldDTO();
			fieldDTO.setName(field.getName());
			fieldDTO.setLabel(field.getLabel());
			fieldDTO.setHidden(field.isHidden());
			fieldDTO.setRequired(field.isRequired());
			if(disabledEdition) {
				fieldDTO.setDisabled(false);
			}else {				
				fieldDTO.setDisabled(field.isDisabled());
			}

			if (null != field.getFactory()) {
				fieldDTO.setType(field.getFactory());
			} else {
				String[] names = field.getClazz().split("\\.");
				fieldDTO.setType(names[names.length - 1]);
			}
			if (Boolean.TRUE.equals(field.isRequired())) {
				fieldDTO.setDisableRequired(Boolean.TRUE);
			}
			if (StringUtils.isNotEmpty(field.getComboName())) {
				fieldDTO.setParent(field.getComboName());
			}
			baseSettings.put(field.getName(), field);
			listDynamicComponentFields.add(fieldDTO);
		}
	}

	/**
	 * 
	 */
	private void loadComponentDynamicFields() {
		for (DynamicComponentFieldDTO field : listDynamicComponentFields) {
			DynamicComponentFieldDTO componentField = null;
			final Iterator<DynamicComponentFieldDTO> iterator = selectedComponent.getDynamicFields().iterator();
			while (null == componentField && iterator.hasNext()) {
				final DynamicComponentFieldDTO dynamicComponentFieldDTO = iterator.next();
				if (null != dynamicComponentFieldDTO.getName()
						&& dynamicComponentFieldDTO.getName().equals(field.getName())) {
					componentField = dynamicComponentFieldDTO;
				}

			}
			if (null != componentField) {
				field.setId(componentField.getId());
				field.setName(componentField.getName());
				field.setLabel(componentField.getLabel());
				field.setHidden(componentField.getHidden());
				field.setRequired(componentField.getRequired());
				field.setParent(componentField.getParent());
				field.setDefaultValue(componentField.getDefaultValue());
				field.setHeight(componentField.getHeight());
				field.setStyle(componentField.getStyle());
				field.setTooltip(componentField.getTooltip());
				field.setWidth(componentField.getWidth());
				field.setDependence(componentField.getDependence());
			}
		}
	}

	/**
	 * 
	 */
	public void onShowExistentes() {
		vboxComponentsVisible = !vboxComponentsVisible;
		searchComponents();
		vboxComponents.setVisible(vboxComponentsVisible);
	}

	/**
	 * 
	 */
	private void searchComponents() {
		if (vboxComponentsVisible) {
			listComponents = componentService.findComplexComponents();
			refreshListboxs();
		} else {
			componentes.setRawValue(null);
		}
	}

	/**
	 * 
	 */
	public void onGuardar() {
		final ComponenteDTO componenteDTO = fillComponent();
		final ComponenteDTO existent = componentService.buscarComponentePorNombre(componenteDTO.getNombre());
		if (null != existent && !existent.getId().equals(componenteDTO.getId())) {
			Messagebox.show(Labels.getLabel(ERROR_USED_NAME), Labels.getLabel(WINDOW_ATENCION_TITLE), Messagebox.OK,
					Messagebox.ERROR);
		} else {
			idComponente = componentService.guardarComponente(componenteDTO).getId();
			Messagebox.show(Labels.getLabel(MESSAGE_OK), Labels.getLabel(WINDOW_ATENCION_TITLE), Messagebox.OK,
					Messagebox.INFORMATION);
			searchComponents();
		}
	}

	/**
	 * 
	 * @return
	 */
	private ComponenteDTO fillComponent() {
		final ComponenteDTO componenteDTO = new ComponenteDTO();
		componenteDTO.setId(idComponente);
		componenteDTO.setNombre(componentName.getValue());
		componenteDTO.setNombreXml(entity.getValue());
		componenteDTO.setDescripcion(description.getValue());
		componenteDTO.setDynamicFields(new HashSet<>(listDynamicComponentFields));
		componenteDTO.setTipoComponenteEnt(tipos.get(type.getValue()));
		componenteDTO.setItems(new HashSet<>());
		componenteDTO.setAtributos(new HashMap<>());
		if (null == idComponente) {
			componenteDTO.setUsuarioCreador(Executions.getCurrent().getRemoteUser());
			componenteDTO.setFechaCreacion(Calendar.getInstance().getTime());
		} else {
			componenteDTO.setUsuarioModificador(Executions.getCurrent().getRemoteUser());
			componenteDTO.setFechaModificacion(Calendar.getInstance().getTime());
		}
		return componenteDTO;
	}

	/**
	 * 
	 */
	public void onClearFields() {
		idComponente = null;
		vboxComponentsVisible = false;
		vboxComponents.setVisible(false);
		componentes.setValue(null);
		selectedComponent = null;
		entity.setDisabled(false);
		entity.setRawValue(null);
		type.setRawValue(null);
		componentName.setRawValue(null);
		componentName.setDisabled(false);
		description.setRawValue(null);
		listDynamicComponentFields.clear();
		btnEliminar.setDisabled(true);

		refreshListboxs();
	}

	/**
	 * 
	 */
	public void onPrevisualizar() {
		ComponenteDTO componenteDTO = fillComponent();
		componenteDTO.setId(null);
		componenteDTO.setNombre("TEMP-- " + componenteDTO.getNombre());
		componenteDTO.setDynamicFields(new HashSet<>());
		for (DynamicComponentFieldDTO field : listDynamicComponentFields) {
			DynamicComponentFieldDTO clone = SerializationUtils.clone(field);
			clone.setId(null);
			componenteDTO.getDynamicFields().add(clone);
		}
		componenteDTO = componentService.guardarComponente(componenteDTO);
		FormularioDTO tempForm = fillTemporaryForm(componenteDTO);
		Map<String, Object> map = new HashMap<>();
		map.put("formularioControlado", tempForm.getNombre());
		map.put("modo", "modoConsultaFC");
		formularioService.guardarFormulario(tempForm);
		try {
			if (this.previsualizarWindow != null) {
				this.previsualizarWindow.detach();
				this.previsualizarWindow = (Window) Executions
						.createComponents(PREVISUALIZACION_FC_WINDOW, this.self, map);
				this.previsualizarWindow.setTitle(Labels.getLabel(PREVIEW_TITLE));
				this.previsualizarWindow.doModal();
			}
		} catch (UiException e) {
			logger.error(e.getMessage(), e);
			Messagebox.show(Labels.getLabel(ERROR_PREVIEW), Labels.getLabel(WINDOW_ATENCION_TITLE), Messagebox.OK,
					Messagebox.ERROR);

		} finally {
			formularioService.eliminarFormulariosTemporales();
			componentService.eliminarComponente(componenteDTO);
		}
	}

	/**
	 * 
	 * @param componenteDTO
	 * @return
	 */
	private FormularioDTO fillTemporaryForm(ComponenteDTO componenteDTO) {
		// NOMBRE TEMPORAL ids NULL
		FormularioDTO formTemp = new FormularioDTO();
		String nombreFC = "--TEMP" + UUID.randomUUID().toString().substring(0, 5) + "CComplejo";
		formTemp.setNombre(nombreFC);
		formTemp.setDescripcion(nombreFC);
		formTemp.setFormularioComponentes(new LinkedHashSet<FormularioComponenteDTO>());
		FormularioComponenteDTO fcDTO = new FormularioComponenteDTO();
		fcDTO.setComponente(componenteDTO);
		fcDTO.setOrden(0);
		fcDTO.setEtiqueta("");
		fcDTO.setNombre("ccomplejo");
		fcDTO.setObligatorio(false);
		fcDTO.setRelevanciaBusqueda(0);
		fcDTO.setTextoMultilinea(null);
		fcDTO.setOculto(false);
		formTemp.getFormularioComponentes().add(fcDTO);
		return formTemp;
	}

	/**
	 * 
	 */
	private void refreshListboxs() {
		final AnnotateDataBinder ccomplejoBinder = new AnnotateDataBinder(ccomplejo);
		ccomplejoBinder.loadComponent(listboxDynamicComponentFields);
		if (vboxComponentsVisible) {
			ccomplejoBinder.loadComponent(listboxComponentes);
		}
	}
	

	public Boolean getDisabledEdition() {
		return disabledEdition;
	}

	public void setDisabledEdition(Boolean disabledEdition) {
		this.disabledEdition = disabledEdition;
	}

	/**
	 * 
	 */
	public void onEliminar() {
		if (selectedComponent != null) {
			final String message = new StringBuilder().append(Labels.getLabel(MESSAGE_CONFIRM_DELETE)).append(" ")
					.append(selectedComponent.getNombre()).append("?").toString();
			Messagebox.show(message, Labels.getLabel(TITLE_CONFIRMACION),
					Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener<Event>() {

						@Override
						public void onEvent(Event event) throws Exception {
							if (Messagebox.YES == (int) event.getData()) {
								componentService.eliminarComponente(selectedComponent);
								onClearFields();
								Messagebox.show(Labels.getLabel(MESSAGE_DELETED),
										Labels.getLabel(WINDOW_ATENCION_TITLE), Messagebox.OK, Messagebox.INFORMATION);
							}
						}
					});

		}
	}

	public void onShowTypes() {
		Map<String, Object> map = new HashMap<>();
		try {
			if (this.componentTypeWindow != null) {
				this.componentTypeWindow.detach();
				this.componentTypeWindow = (Window) Executions
						.createComponents(COMPONENT_TYPE_WINDOW, this.self, map);

				this.componentTypeWindow.doModal();
				this.componentTypeWindow.addEventListener(Events.ON_CLOSE, new EventListener<Event>() {

					@Override
					public void onEvent(Event event) throws Exception {
						loadComponentTypes();
					}
				});
			}
		} catch (UiException e) {
			logger.error(e.getMessage(), e);
			Messagebox.show(Labels.getLabel(ERROR_SHOW_TYPES), Labels.getLabel(WINDOW_ATENCION_TITLE), Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	/**
	 * 
	 * @param event
	 */
	public void onShowDetails(ForwardEvent event) {

		final DynamicComponentFieldDTO selectedField = ((Listitem) event.getOrigin().getTarget()
				.getParent().getParent()).getValue();
		Map<String, Object> args = new HashMap<>();
		args.put("field", selectedField);
		args.put("listComponents", listDynamicComponentFields);
		args.put("baseSettings", baseSettings.get(selectedField.getName()));
		try {
			if (this.ccomplejoDetailWindow != null) {
				this.ccomplejoDetailWindow.detach();
				this.ccomplejoDetailWindow = (Window) Executions
						.createComponents(CCOMPLEJO_DETAIL_WINDOW, this.self, args);
				this.ccomplejoDetailWindow.doModal();
				this.ccomplejoDetailWindow.addEventListener(Events.ON_CLOSE, new EventListener<Event>() {

					@Override
					public void onEvent(Event event) throws Exception {
						refreshListboxs();
					}
				});
			}
		} catch (UiException e) {
			logger.error(e.getMessage(), e);
			Messagebox.show(Labels.getLabel(ERROR_PREVIEW), Labels.getLabel(WINDOW_ATENCION_TITLE), Messagebox.OK,
					Messagebox.ERROR);

		}

	}

	/**
	 * @return the listDynamicComponentFields
	 */
	public List<DynamicComponentFieldDTO> getListDynamicComponentFields() {
		return listDynamicComponentFields;
	}

	/**
	 * @param listDynamicComponentFields
	 *            the listDynamicComponentFields to set
	 */
	public void setListDynamicComponentFields(List<DynamicComponentFieldDTO> listDynamicComponentFields) {
		this.listDynamicComponentFields = listDynamicComponentFields;
	}

	/**
	 * @return the selectedComponent
	 */
	public ComponenteDTO getSelectedComponent() {
		return selectedComponent;
	}

	/**
	 * @param selectedComponent
	 *            the selectedComponent to set
	 */
	public void setSelectedComponent(ComponenteDTO selectedComponent) {
		this.selectedComponent = selectedComponent;
	}

	/**
	 * @return the listboxComponentes
	 */
	public Listbox getListboxComponentes() {
		return listboxComponentes;
	}

	/**
	 * @param listboxComponentes
	 *            the listboxComponentes to set
	 */
	public void setListboxComponentes(Listbox listboxComponentes) {
		this.listboxComponentes = listboxComponentes;
	}

	/**
	 * @return the listComponents
	 */
	public List<ComponenteDTO> getListComponents() {
		return listComponents;
	}

	/**
	 * @param listComponents
	 *            the listComponents to set
	 */
	public void setListComponents(List<ComponenteDTO> listComponents) {
		this.listComponents = listComponents;
	}

}
