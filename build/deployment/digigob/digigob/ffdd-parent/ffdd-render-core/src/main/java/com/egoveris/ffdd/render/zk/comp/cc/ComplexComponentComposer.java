package com.egoveris.ffdd.render.zk.comp.cc;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Auxhead;
import org.zkoss.zul.Auxheader;
import org.zkoss.zul.Box;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.SimpleConstraint;
import org.zkoss.zul.ext.Constrainted;
import org.zkoss.zul.impl.InputElement;

import com.egoveris.ffdd.model.model.ComponenteDTO;
import com.egoveris.ffdd.model.model.DynamicComponentFieldDTO;
import com.egoveris.ffdd.model.model.FormularioComponenteDTO;
import com.egoveris.ffdd.model.model.ItemDTO;
import com.egoveris.ffdd.render.model.ComplexComponent;
import com.egoveris.ffdd.render.model.ComplexField;
import com.egoveris.ffdd.render.model.ComponentZkExt;
import com.egoveris.ffdd.render.model.InputComponent;
import com.egoveris.ffdd.render.service.IComplexComponentService;
import com.egoveris.ffdd.render.service.IComplexFieldService;
import com.egoveris.ffdd.render.service.IComponentFactory;
import com.egoveris.ffdd.render.service.IFormManager;
import com.egoveris.ffdd.render.service.IFormManagerFactory;
import com.egoveris.ffdd.render.zk.comp.ext.ComboboxExt;
import com.egoveris.ffdd.render.zk.comp.ext.DateboxExt;
import com.egoveris.ffdd.render.zk.comp.ext.SeparatorComplex;
import com.egoveris.ffdd.render.zk.manager.ZkFormManager;
import com.egoveris.ffdd.render.zk.util.ComplexFieldHelper;
import com.egoveris.ffdd.render.zk.util.ComponentUtils;
import com.egoveris.ffdd.ws.service.ExternalComponentService;
import com.egoveris.ffdd.ws.service.ExternalFormularioService;
import com.egoveris.ffdd.ws.service.ExternalItemService;
import com.egoveris.ffdd.ws.util.FFDDConstants;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public abstract class ComplexComponentComposer extends GenericForwardComposer<Component> {

	/**
	* 
	*/
	private static final long serialVersionUID = -3052118597748855419L;

	private static final Logger logger = LoggerFactory.getLogger(ComplexComponentComposer.class);

	protected static final String NO_EMPTY = "no empty";
	protected static final String SEPARATOR_COMPLEX = "SeparatorComplex";
	private static final String REPEAT_SUFFIX = "[0]";

	private static final List<Class<?>> INPUT_CLASS = Arrays.asList(InputElement.class, InputComponent.class,
			SeparatorComplex.class);
	private static final List<Class<?>> DIV_CLASS = Arrays.asList(Cell.class, Box.class);

	private static final String DYNAMIC_FIELD_NAME = "dynamicFieldName";

	@WireVariable
	protected ExternalFormularioService externalFormularioService;

	@WireVariable
	private IFormManagerFactory<IFormManager<Component>> formManagerFact;

	@WireVariable
	private IComplexComponentService complexComponentService;

	@WireVariable
	private ExternalComponentService externalComponentService;

	@WireVariable
	private IComplexFieldService complexFieldService;
	
	@WireVariable("externalItemServiceImpl")
	private ExternalItemService externalItemServiceImpl;

	private Map<String, Map<String, ComboboxExt>> mapDynamicLists = new HashMap<>();
	private Map<String, Set<ItemDTO>> mapItems = new HashMap<>();
	private Map<String, String> mapComponentsNames = new HashMap<>();

	@Override
	public void doAfterCompose(final Component comp) throws Exception {
		super.doAfterCompose(comp);

		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));

		final FormularioComponenteDTO formComp = (FormularioComponenteDTO) Executions.getCurrent().getArg()
				.get("formComp");

		// busca los campos tipo cell del composer y les asigna el componente
		// asociado al id ${arg.formComp.nombre}_nombreCampo
		bindComp(comp, formComp.getNombre());

		generateComponents(formComp);
	}

	public static List<Field> getAllFields(final Class<?> type) {
		final List<Field> fields = new ArrayList<>();
		for (Class<?> c = type; c != null; c = c.getSuperclass()) {
			fields.addAll(Arrays.asList(c.getDeclaredFields()));
		}
		return fields;
	}

	/**
	 * Se buscan los campos Bindea las celdas donde iran los componentes
	 *
	 * @param comp
	 * @param nombre
	 * @throws IllegalAccessException
	 */
	protected void bindComp(final Component comp, final String nombre) throws IllegalAccessException {

		final String formCompPrefix = new StringBuilder().append(nombre).append(FFDDConstants.NAME_SEPARATOR)
				.toString();

		for (final Field field : getAllFields(this.getClass())) {
			// Valida que el campo sea tipo Cell
			if (DIV_CLASS.contains(field.getType())) {

				final String cellId = new StringBuilder(formCompPrefix).append(field.getName()).toString();
				final Component cell = comp.getFellow(cellId);

				field.set(this, cell);
			}
		}
	}

	/**
	 * Creates all InputElemens acording to the XML definition file. If a
	 * InputElement in the Composer class is not present in the XML we asume a
	 * TextBox with no special properties. If a complex component has components
	 * that are not inputfields, like the FileUpload
	 *
	 * @param formComp
	 *            Component to create.
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 */
	protected void generateComponents(final FormularioComponenteDTO formComp)
			throws IllegalAccessException, NoSuchFieldException {

		final String prefixName = formComp.getNombre();

		// obtiene la configuraciones del componente desde el xml
		final ComplexComponent compSettings = getComponentSettings(formComp);
		
		for (final Field field : getAllFields(this.getClass())) {
			// busca todos los campos del tipo InputElement y les asigna un
			// componente creado según las especificaciones del xml
			if (INPUT_CLASS.contains(field.getType())) {

				final String cellID = new StringBuilder().append(field.getName()).append(FFDDConstants.CELL_SUFIX)
						.toString();
				final Field cellField = this.getClass().getDeclaredField(cellID);

				final String inputElementID = new StringBuilder().append(prefixName)
						.append(FFDDConstants.NAME_SEPARATOR).append(field.getName()).toString();

				final DynamicComponentFieldDTO dynamicField = ComplexFieldHelper.obtenerDynamicField(
						formComp.getComponente().getDynamicFields(), field.getName());
				final ComplexField fieldSettings = ComplexFieldHelper.getField(compSettings, field.getName());
				final FormularioComponenteDTO formCompTemp = complexFieldService.createFormComp(dynamicField,
						fieldSettings);

				Component parentCell = (Component) cellField.get(this);
				parentCell.setVisible(!formCompTemp.getOculto());

				Grid grid = new Grid();
				grid.setParent(parentCell);
				Rows rows = new Rows();
				rows.setParent(grid);
				Row row = new Row();
				row.setParent(rows);

				Component element;
				if (field.getType().equals(SeparatorComplex.class)) {
					element = newSeparatorComplex(prefixName, field, fieldSettings, formCompTemp, grid, row);
				} else {
					element = newZkComponent(field, inputElementID, fieldSettings, formCompTemp, row);
				}
				setDefaultValue(dynamicField, element);

				completeDynamicListMap(dynamicField, formCompTemp.getComponente(), element);
				onChangeDynamicList(dynamicField, element);

				field.set(this, element);
			}
		}
	}

	private void setDefaultValue(final DynamicComponentFieldDTO dynamicField, Component element) {
		if (StringUtils.isNotEmpty(dynamicField.getDefaultValue())) {
			if (element instanceof DateboxExt) {
				final DateboxExt dateboxExt = (DateboxExt) element;
				String strFormat = dateboxExt.getFormat();
				if (StringUtils.isEmpty(strFormat)) {
					strFormat = "dd-MM-yyyy";
				}
				final DateFormat format = new SimpleDateFormat(strFormat);
				try {
					final Date value = format.parse(dynamicField.getDefaultValue());
					dateboxExt.setRawValue(value);
				} catch (ParseException e) {
					logger.error("Wrong default value: " + dynamicField.getDefaultValue() + ", format: "
							+ strFormat, e);
				}
			} else if (element instanceof InputElement) {
				try{
				((InputElement) element).setRawValue(dynamicField.getDefaultValue());
				} catch (ClassCastException cce){
					((InputElement) element).setRawValue(Long.valueOf(dynamicField.getDefaultValue()));
				}
			} else if (element instanceof InputComponent) {
				((InputComponent) element).setRawValue(dynamicField.getDefaultValue());
			}
		}
	}


	private Component newZkComponent(final Field field, final String inputElementID, final ComplexField fieldSettings,
			final FormularioComponenteDTO formCompTemp, Row row) {
		if (null != formCompTemp.getEtiqueta()) {
			Label label = new Label();
			label.setValue(formCompTemp.getEtiqueta());
			label.setParent(row);
		}

		Component element = createElement(fieldSettings, field.getName(), formCompTemp);
		if (null != element) {
			ComponentZkExt component = (ComponentZkExt) element;
			component.setIdComponentForm(0);
			component.setName(inputElementID);
			component.setParent(row);
			if (element instanceof Constrainted && formCompTemp.getObligatorio()) {
				((Constrainted) element).setConstraint(SimpleConstraint.getInstance(NO_EMPTY));
			}
		}
		return element;
	}

	/**
	 * 
	 * @param prefixName
	 * @param field
	 * @param fieldSettings
	 * @param formComp
	 * @param grid
	 * @param row
	 * @return
	 */
	private Component newSeparatorComplex(final String prefixName, final Field field, final ComplexField fieldSettings,
			final FormularioComponenteDTO formComp, Grid grid, Row row) {
		Component element = null;
		// El id del campo tendra el formato (cc1.cc2)
		final StringBuilder innerElementID = new StringBuilder().append(prefixName).append(".").append(field.getName());

		if (Boolean.TRUE.equals(fieldSettings.isRepetidor())) {
			innerElementID.append(REPEAT_SUFFIX);
		}

		final String innerElementName = innerElementID.toString();
		formComp.setNombre(innerElementName);

		if (null != formComp.getEtiqueta() && !Boolean.TRUE.equals(fieldSettings.isRepetidor())) {
			Columns columns = new Columns();
			columns.setParent(grid);
			Column column = new Column();
			column.setParent(columns);
			Auxhead head = new Auxhead();
			head.setParent(grid);
			Auxheader header = new Auxheader(formComp.getEtiqueta());
			header.setParent(head);
		}
		// obtiene la configuracione del componente desde el xml
		ComplexComponent compSettingsSeparator = complexComponentService.getComponent(fieldSettings.getXmlFile());

		// obtener el .zul desde el xml y pasarlo como parametro al
		// SeparatorComplex
		if (compSettingsSeparator.getZul() != null) {
			element = new SeparatorComplex(formComp, compSettingsSeparator.getZul(),
					Boolean.TRUE.equals(fieldSettings.isRepetidor()));
		}
		if (element != null) {
			SeparatorComplex separator = (SeparatorComplex) element;
			separator.setId(innerElementName);
			separator.setParent(row);
			if (separator.getRepetidor()) {
				ComponentUtils.generarSeparatorRepetidor(formComp).setParent(separator.getFirstChild().getFirstChild());
			}

		}
		return element;
	}

	/**
	 * 
	 * @param formComp
	 * @return
	 */
	private ComplexComponent getComponentSettings(final FormularioComponenteDTO formComp) {
		final ComplexComponent compSettings = complexComponentService
				.getComponent(formComp.getComponente().getNombreXml());
		if (compSettings == null) {
			throw new IllegalArgumentException(
					"No se puede crear un elementon InputElement sin un complexComponent definido");
		}
		return compSettings;
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
	@SuppressWarnings("unchecked")
	private Component createElement(final ComplexField fieldSettings, final String field,
			final FormularioComponenteDTO formComp) {
		Component instance = null;

		try {
			// se genera una instancia del tipo indicado en el xml
			if (null != fieldSettings.getFactory()) {
				IComponentFactory<ComponentZkExt> factory = (IComponentFactory<ComponentZkExt>) SpringUtil
						.getBean(fieldSettings.getFactory());
				if (null != factory) {
					instance = factory.create(formComp);
				}

			} else {
				instance = complexFieldService.createInstance(fieldSettings);
			}

		} catch (ReflectiveOperationException e) {
			logger.error(
					"Error creando elemento campo " + field + " componente " + formComp.getComponente().getNombre(), e);
		}
		return instance;
	}

	private void completeDynamicListMap(DynamicComponentFieldDTO dynamicField, ComponenteDTO componentDTO,
			Component element) {
		if (element instanceof ComboboxExt) {
			if (!StringUtils.isEmpty(dynamicField.getDependence())) {
				Map<String, ComboboxExt> mapChild;

				if (mapDynamicLists.containsKey(dynamicField.getDependence())) {
					mapChild = mapDynamicLists.get(dynamicField.getDependence());
				} else {
					mapChild = new HashMap<>();
				}

				mapChild.put(dynamicField.getName(), (ComboboxExt) element);
				mapDynamicLists.put(dynamicField.getDependence(), mapChild);

				clearCombo((ComboboxExt) element);
			}

			Set<ItemDTO> setItems = new HashSet<>();

			if (componentDTO.getItems() != null) {
				setItems = componentDTO.getItems();
			}

			mapItems.put(dynamicField.getName(), setItems);
			mapComponentsNames.put(dynamicField.getName(), dynamicField.getParent());
		}
	}

	private void onChangeDynamicList(DynamicComponentFieldDTO dynamicField, Component element) {
		if (element instanceof ComboboxExt) {
			element.setAttribute(DYNAMIC_FIELD_NAME, dynamicField);
			element.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {

				@Override
				public void onEvent(Event event) throws Exception {
					if (event.getTarget().hasAttribute(DYNAMIC_FIELD_NAME)) {
						ComboboxExt comboboxExt = (ComboboxExt) event.getTarget();
						DynamicComponentFieldDTO dynamicField = (DynamicComponentFieldDTO) event.getTarget()
								.getAttribute(DYNAMIC_FIELD_NAME);

						if (mapDynamicLists.containsKey(dynamicField.getName())) {
							// Este es un combo padre (se actualizaran los valores de los combos anidados)
							Set<ItemDTO> itemsCombo = mapItems.get(dynamicField.getName());

							// Busca el ItemDTO seleccionado (segun el orden)
							ItemDTO itemSeleccionado = null;
							Map<String, ComboboxExt> mapCombosAnidados = mapDynamicLists.get(dynamicField.getName());
							Iterator<ItemDTO> it = itemsCombo.iterator();

							while (it.hasNext()) {
								ItemDTO itemDTO = it.next();

								if (itemDTO.getOrden().equals(comboboxExt.getSelectedIndex())) {
									itemSeleccionado = itemDTO;
									break;
								}
							}

							if (itemSeleccionado != null) {
								// Se iteran los combos dependientes del que
								// genero el onChange
								for (Entry<String, ComboboxExt> entry : mapCombosAnidados.entrySet()) {
									List<ItemDTO> valoresComboHijo = externalItemServiceImpl
											.obtenerDependencia(itemSeleccionado.getId(),
													mapComponentsNames.get(entry.getKey()));
									// Actualiza el map de items de combos
									mapItems.get(entry.getKey()).clear();
									mapItems.get(entry.getKey()).addAll(valoresComboHijo);
									
									clearCombo(entry.getValue());
									recursiveComboClear(entry.getKey());

									// Agrega los nuevos valores al combo
									for (ItemDTO valorCombo : valoresComboHijo) {
										entry.getValue().getItems().add(new Comboitem(valorCombo.getValor()));
									}
								}
							}
						}
					}
				}
			});
		}
	}

	private void clearCombo(ComboboxExt combobox) {
		// Resetea el combo y agrega el elemento
		// VACIO
		combobox.setSelectedItem(null);
		combobox.setText("");

		combobox.getItems().clear();
		combobox.getItems().add(new Comboitem(ZkFormManager.VACIO));
	}

	private void recursiveComboClear(String name) {
		if (mapDynamicLists.containsKey(name)) {
			Map<String, ComboboxExt> mapChild = mapDynamicLists.get(name);

			for (Entry<String, ComboboxExt> entry : mapChild.entrySet()) {
				clearCombo(entry.getValue());
				recursiveComboClear(entry.getKey());
			}
		}
	}

	protected abstract void setDefaultValues(String prefixName);

	protected String getName() {
		return null;
	}
}
