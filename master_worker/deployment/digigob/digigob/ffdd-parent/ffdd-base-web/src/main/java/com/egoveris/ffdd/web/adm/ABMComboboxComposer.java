package com.egoveris.ffdd.web.adm;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.jdom.JDOMException;
import org.jdom.input.JDOMParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.image.AImage;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.DropEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.ListModelArray;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.egoveris.ffdd.base.service.IComponenteService;
import com.egoveris.ffdd.base.service.IFormularioService;
import com.egoveris.ffdd.model.exception.DynFormException;
import com.egoveris.ffdd.model.model.AtributoComponenteDTO;
import com.egoveris.ffdd.model.model.ComponenteDTO;
import com.egoveris.ffdd.model.model.ItemDTO;
import com.egoveris.ffdd.model.model.TipoComponenteDTO;
import com.egoveris.shared.collection.CollectionUtils;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ABMComboboxComposer extends GenericForwardComposer {

	private static final long serialVersionUID = -1046223622245127317L;

	public static final String MODO_ALTA_FC = "modoAltaFC";
	public static final String MODO_MODIFICACION_FC = "modoModificacionFC";
	public static final String MODO_CONSULTA_FC = "modoConsultaFC";

	private static final Logger logger = LoggerFactory.getLogger(ABMComboboxComposer.class);

	// Componentes ZUL
	private Window abmComboboxWindow;
	private Window previsualizarWindow;
	private Window previsualizarMultivaloresWindow;
	private Listbox listboxItemsCombo;
	private Listbox listboxCombosExistenSugerencias;
	private Listitem itemComponenteCombo;
	private Textbox nombreCombobox;
	private Textbox descripcionCombobox;
	private Textbox txtBoxDescripcion;
	private Button btnPrevisualizar;
	private Button btnGuardar;
	private Button btnModificar;
	private Button btnBorrar;
	private Button btnEliminar;
	private Checkbox bandBoxCheck;
	private Textbox buscarSugerencia;

	// Atributos
	private ComboboxDTO elementoSeleccionado;
	private Listheader tituloExistenSugerencias;
	private List<ComboboxDTO> listaItemsCombo;
	private List<ItemDTO> items;
	private List<ComponenteDTO> listaMultivalores;
	private List<ComponenteDTO> listaMultivaloresAux;
	private List<ComboboxDTO> listaCompOrdenados;
	private ComponenteDTO comboboxModificacionConsulta;
	private int indexItemCombo = 0;
	private int orderItemCombo = 1;
	private Boolean previsualizar = false;
	private Boolean continuaCreacion = false;
	private String modo;
	private String loggedUser;

	// Beans
	@WireVariable("componenteService")
	private IComponenteService componenteService;
	@WireVariable("formularioService")
	private IFormularioService formularioService;

	public void doAfterCompose(Component comp) throws Exception {
		try {
			super.doAfterCompose(comp);
			Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
			obtenerDatos();
		} catch (DynFormException e) {
			throw new DynFormException(
					Labels.getLabel("abmComboboxComposer.exception.errorAdm") + Labels.getLabel("abmComboboxComposer.exception.intMinutos"), e);
		}
	}

	/**
	 * Obtiene todos los comboboxs existentes en la base de datos.
	 */
	public void obtenerDatos() throws DynFormException {

		this.loggedUser = Executions.getCurrent().getRemoteUser();
		this.listaItemsCombo = new ArrayList<ComboboxDTO>();
		this.modo = (String) Executions.getCurrent().getArg().get("modo");
		setListaComboboxs(this.componenteService.obtenerTodosLosComponentesMultivalores());
		listaMultivaloresAux = getListaComboboxs();
	}

	/**
	 * Genera un nuevo combobox vacio.
	 * @throws InterruptedException 
	 */
	public void onNuevo() throws InterruptedException {

		try {
			if (listaItemsCombo.size() == 0) {
				if (esComboValido()) {
					prepararBotones();
					ListModelList model = (ListModelList) listboxItemsCombo.getModel();
					ComboboxDTO componente = new ComboboxDTO();
					componente.setMas(true);
					componente.setOrder(orderItemCombo);
					model.add(model.size(), componente);
					reCargarLista();

				}
			} else {
				Messagebox.show(Labels.getLabel("abmCombobox.nuevoComboPreg.msg"),
						Labels.getLabel("fc.export.atencion.title"), Messagebox.YES | Messagebox.NO | Messagebox.CANCEL,
						Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
							public void onEvent(Event evt) throws Exception {
								if ("onYes".equals(evt.getName())) {
									continuaCreacion = true;
									onGuardar();
								} else {
									if ("onNo".equals(evt.getName())) {
										setModo(MODO_ALTA_FC);
										prepararBotones();
										limpiarPantalla();
									}
								}
							}
						});
			}
		} catch (InterruptedException e) {
			logger.error("Mensaje de Error" + e);
			throw e;
		}

	}

	/**
	 * Genera un nuevo item de combobox.
	 * @throws InterruptedException 
	 */
	public void onMas() throws InterruptedException {

		try {
			if (esComboValido()) {
				this.orderItemCombo++;
				ListModelList model = (ListModelList) listboxItemsCombo.getModel();

				for (int i = 0; i < model.getInnerList().size(); i++) {
					ComboboxDTO componente1 = (ComboboxDTO) model.getInnerList().get(i);
					componente1.setMas(false);
					model.set(i, componente1);
				}

				ComboboxDTO componente = new ComboboxDTO();
				ComboboxDTO dato = listaItemsCombo.get(this.indexItemCombo);
				dato.setMas(false);
				model.remove(this.indexItemCombo);
				model.add(this.indexItemCombo, dato);
				componente.setMas(true);
				componente.setOrder(this.orderItemCombo);
				model.add(model.size(), componente);
				reCargarLista();
				this.indexItemCombo++;
			}
		} catch (InterruptedException e) {
			logger.error("Mensaje de Error" + e);
			throw e;
		}
	}
	
	
	
	/**
	 * Abre popUp para Agregar multivalores
	 *
	 */
	public void onAddMultivalor() {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("listaMultivalores", listaMultivalores);
		map.put("comboboxModificacionConsulta", comboboxModificacionConsulta);
		map.put("elementoSeleccionado", elementoSeleccionado);
		map.put("previsualizarMultivaloresWindow", previsualizarMultivaloresWindow);
		this.previsualizarMultivaloresWindow = (Window) Executions.createComponents("/administradorFC/addMultivalor.zul",
				this.self, map);
		previsualizarMultivaloresWindow.doModal();
		
	}
	
	/**
	 * Muestra listado ordenado de comboboxs existentes en la base de datos.
	 */
	public void onExistentes() throws DynFormException {

		if (getModo().equals(MODO_ALTA_FC)) {
			this.btnModificar.setVisible(true);
			this.btnModificar.setDisabled(true);
			this.btnEliminar.setVisible(true);
			this.btnEliminar.setDisabled(true);
			this.tituloExistenSugerencias.setLabel("Combos Existentes");
			ordenarLista();
			this.listboxCombosExistenSugerencias.setModel(new ListModelArray(getListaComboboxs()));
			this.buscarSugerencia.setDisabled(false);
		}
	}

	private void ordenarLista() {
		ComponenteDTO aux = null;
		for (int i = 0; i < this.listaMultivalores.size() - 1; i++)
			for (int j = 0; j < this.listaMultivalores.size() - i - 1; j++)
				if (this.listaMultivalores.get(j + 1).getNombre()
						.compareTo(this.listaMultivalores.get(j).getNombre()) < 0) {
					aux = this.listaMultivalores.get(j + 1);
					this.listaMultivalores.set(j + 1, this.listaMultivalores.get(j));
					this.listaMultivalores.set(j, aux);
				}
	}

	/**
	 * Determina que componente genero el evento y lo redirige para realizar la
	 * accion que corresponde.
	 */
	public void onDropItem(ForwardEvent fe) throws InterruptedException {

		reCargarLista();
		DropEvent event = (DropEvent) fe.getOrigin();
		final Listitem draggedComp = (Listitem) event.getDragged();

		if (draggedComp.getParent().getId().equals(this.listboxCombosExistenSugerencias.getId())) {
			if (this.listaItemsCombo.size() != 0) {
				Messagebox.show(Labels.getLabel("abmCombobox.mostrarCombo.vali"),
						Labels.getLabel("fc.export.atencion.title"), Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener() {
							public void onEvent(Event evt) throws Exception {
								if ("onYes".equals(evt.getName())) {
									mostrarComboExistente(draggedComp);
								}
							}
						});
			} else {
				mostrarComboExistente(draggedComp);
			}
		} else {
			ListModelList model = (ListModelList) listboxItemsCombo.getModel();
			ComboboxDTO componente = new ComboboxDTO();
			if (draggedComp.getParent() == listboxItemsCombo) {
				componente = (ComboboxDTO) draggedComp.getValue();
			}
			if (event.getTarget() instanceof Listitem && componente.getDescripcion() != null) {
				cambiarOrdenItem(componente, event, model, draggedComp);
			} else {
				if (event.getTarget() instanceof Button && draggedComp.getParent() == listboxItemsCombo) {

					borrarItem(event, draggedComp, model, componente);

					this.itemComponenteCombo.setDraggable("true");
					indexItemCombo--;
				}

			}

			reCargarLista();

			if (this.indexItemCombo < 0) {
				this.indexItemCombo = 0;
				this.orderItemCombo = 1;
			}
		}
	}

	public void reCargarLista() {
		ListModelList model = (ListModelList) this.listboxItemsCombo.getModel();
		setListaComponentes(CollectionUtils.asList(model.getInnerList(), ComboboxDTO.class));
	}

	/**
	 * Muestra lista ordenada de items de un combobox existente.
	 */
	public void mostrarComboExistente(Listitem draggedComp) {

		ComboboxDTO componente = null;
		this.listaCompOrdenados = new ArrayList<ComboboxDTO>();
		setModo(MODO_CONSULTA_FC);
		prepararBotones();

		ListModelList model = (ListModelList) this.listboxItemsCombo.getModel();
		if (this.listaItemsCombo.size() != 0) {
			model.removeAll(this.listaItemsCombo);
			this.indexItemCombo = 0;
		}

		this.comboboxModificacionConsulta = (ComponenteDTO) draggedComp.getValue();
		Set<ItemDTO> listaItems = this.comboboxModificacionConsulta.getItems();
		this.nombreCombobox.setValue(this.comboboxModificacionConsulta.getNombre());
		this.nombreCombobox.setTooltiptext(nombreCombobox.getValue());
		this.descripcionCombobox.setValue(this.comboboxModificacionConsulta.getDescripcion());

		for (ItemDTO item : listaItems) {
			componente = new ComboboxDTO();
			componente.setId(item.getId());
			if (item.getComponente().getTipoComponente().equals("COMPLEXBANDBOX"))
				bandBoxCheck.setChecked(true);
			else
				bandBoxCheck.setChecked(false);
			componente.setValor(item.getDescripcion());
			componente.setDescripcion(item.getDescripcion());
			componente.setOrder(item.getOrden());
			this.listaCompOrdenados.add(componente);
		}

		this.listaCompOrdenados = ordenarItemsCombo(this.listaCompOrdenados);

		for (ComboboxDTO item : this.listaCompOrdenados) {
			componente = new ComboboxDTO();
			componente.setId(item.getId());
			componente.setValor(item.getDescripcion());
			componente.setDescripcion(item.getDescripcion());
			componente.setOrder(item.getOrder());
			componente.setMas(false);
			model.add(this.indexItemCombo, componente);
			++indexItemCombo;
			setOrder(componente.getOrder() + 1);
			reCargarLista();
		}
		this.itemComponenteCombo.setDraggable("false");
	}

	/**
	 * Cambia la posicion de los items manteniendo el orden de los mismos.
	 */
	public boolean cambiarOrdenItem(ComboboxDTO componente, DropEvent event, ListModelList model, Listitem draggedComp)
			throws InterruptedException {
		int aux = 0;
		Listitem lis = ((Listitem) event.getTarget());
		ComboboxDTO dropComb = (ComboboxDTO) lis.getValue();

		if (esComboValido()) {
			if (componente.getDescripcion().equals(this.listaItemsCombo.get(this.indexItemCombo).getDescripcion())) {

				if (dropComb.getDescripcion()
						.equals(this.listaItemsCombo.get(this.indexItemCombo - 1).getDescripcion())) {

					this.indexItemCombo--;

					aux = dropComb.getOrder();
					dropComb.setOrder(componente.getOrder());
					componente.setOrder(aux);
					model.remove(this.indexItemCombo);
					model.add(this.indexItemCombo, componente);
					this.indexItemCombo++;
					model.add(this.indexItemCombo, dropComb);

				} else {

					this.indexItemCombo--;
					ComboboxDTO comp = this.listaItemsCombo.get(this.indexItemCombo);
					comp.setOrder(componente.getOrder());
					model.remove(this.indexItemCombo);
					model.add(this.indexItemCombo, comp);
					this.indexItemCombo++;
					int orden = dropComb.getOrder();
					int ordenFin = componente.getOrder() - 1;
					componente.setOrder(dropComb.getOrder());
					aux = lis.getIndex();
					for (int i = aux; i < ordenFin; i++) {
						if (i < listaItemsCombo.size()) {
							dropComb = (ComboboxDTO) this.listaItemsCombo.get(i);
							dropComb.setOrder(++orden);
						}
					}
					model.add(((Listitem) event.getTarget()).getIndex(), componente);
				}
			} else {

				if (componente.getOrder() > dropComb.getOrder()) {
					int orden = dropComb.getOrder();
					int ordenFin = componente.getOrder() - 1;
					componente.setOrder(orden);
					aux = lis.getIndex();
					for (int i = aux; i < ordenFin; i++) {
						dropComb = (ComboboxDTO) this.listaItemsCombo.get(i);
						dropComb.setOrder(++orden);
					}
					model.add(((Listitem) event.getTarget()).getIndex(), componente);
				} else {
					return false;
				}

			}
			if (draggedComp.getParent() == this.listboxItemsCombo) {
				model.remove(draggedComp.getIndex());
			}

		}
		int i;
		for (i = 0; i < model.getInnerList().size(); i++) {
			ComboboxDTO componente1 = (ComboboxDTO) model.getInnerList().get(i);
			componente1.setMas(false);
			model.set(i, componente1);
		}
		ComboboxDTO componente1 = (ComboboxDTO) model.getInnerList().get(i - 1);
		componente1.setMas(true);
		model.set(i - 1, componente1);
		return true;
	}

	/**
	 * Elimina un item de la lista conservando el orden de los demas items.
	 */
	public boolean borrarItem(DropEvent event, Listitem draggedComp, ListModelList model, ComboboxDTO componente)
			throws InterruptedException {

		if (getModo().equals(MODO_ALTA_FC)) {
			if (this.indexItemCombo > 0 && componente.getOrder() == (this.indexItemCombo + 1)) {
				this.indexItemCombo--;
				ComboboxDTO comp = this.listaItemsCombo.get(this.indexItemCombo);
				model.remove(this.indexItemCombo);
				model.add(this.indexItemCombo, comp);
				this.orderItemCombo--;
				model.remove(draggedComp.getIndex());
			} else {
				for (int i = componente.getOrder(); i <= this.indexItemCombo; i++) {
					ComboboxDTO dato = (ComboboxDTO) this.listaItemsCombo.get(i);
					dato.setOrder(dato.getOrder() - 1);
				}
				
				model.remove(draggedComp.getIndex());
				
				// This if ensure correct order (ascending)
				if (indexItemCombo + 1 > orderItemCombo) {
					this.orderItemCombo--;
				}
				
				this.indexItemCombo--;
			}
		} else {
			ComboboxDTO dato = (ComboboxDTO) draggedComp.getValue();
			
			if (getModo().equals(MODO_MODIFICACION_FC)) {
				if (dato.getOrder() == this.indexItemCombo + 1) {
					if (this.listaItemsCombo.size() == this.listaCompOrdenados.size() + 1) {
						model.remove(draggedComp.getIndex());
						setModo(MODO_MODIFICACION_FC);
						prepararBotones();
						this.indexItemCombo--;
						this.itemComponenteCombo.setDraggable("true");
					} else {
						ComboboxDTO comp = this.listaItemsCombo.get(this.indexItemCombo);
						model.add(this.indexItemCombo, comp);
						model.remove(draggedComp.getIndex());
						model.remove(this.indexItemCombo);
						this.orderItemCombo--;
					}
				} else {
					for (int i = componente.getOrder(); i <= this.indexItemCombo; i++) {
						// Prevent ArrayIndexOutOfBoundsException
						if (i >= 0) {
							ComboboxDTO data = (ComboboxDTO) this.listaItemsCombo.get(i);
							data.setOrder(data.getOrder() - 1);
						}
					}
					
					model.remove(draggedComp.getIndex());
					this.indexItemCombo--;
					this.orderItemCombo--;
				}
			}
		}
		
		// Prevent ArrayIndexOutOfBoundsException
		if (listaItemsCombo != null && !listaItemsCombo.isEmpty()) {
			ComboboxDTO combo = listaItemsCombo.get(listaItemsCombo.size() - 1);
			model.remove(combo);
			combo.setMas(true);
			model.add(combo);
		}
		
		return true;
	}

	/**
	 * Ordena una lista de items de menor a mayor de acuerdo a su valor de
	 * orden.
	 */
	public List<ComboboxDTO> ordenarItemsCombo(List<ComboboxDTO> lista) {

		ComboboxDTO aux = null;
		for (int i = 0; i < lista.size() - 1; i++)
			for (int j = 0; j < lista.size() - i - 1; j++)
				if (lista.get(j + 1).getOrder() < lista.get(j).getOrder()) {
					aux = lista.get(j + 1);
					lista.set(j + 1, lista.get(j));
					lista.set(j, aux);
				}
		return lista;
	}

	/**
	 * Verifica si un item seleccionado es perteneciente a un combobox existente
	 * en la base de datos.
	 */
	public boolean esItemExistente(ComboboxDTO dato, String msj) throws InterruptedException {
		for (ComboboxDTO item : this.listaCompOrdenados) {
			if (dato != null && item != null && dato.getDescripcion().equals(item.getDescripcion())) {
				Messagebox.show(Labels.getLabel(msj), Labels.getLabel("fc.export.atencion.title"), Messagebox.OK,
						Messagebox.EXCLAMATION);
				return false;
			}
		}
		return true;
	}

	/**
	 * Permite la modificacion de un combobox existente agregandolo un nuevo
	 * item.
	 */
	public void onModificar() {

		if (getModo().equals(MODO_CONSULTA_FC)) {
			setModo(MODO_MODIFICACION_FC);
			prepararBotones();
			this.itemComponenteCombo.setDraggable("true");
			ListModelList model = (ListModelList) this.listboxItemsCombo.getModel();

			ComboboxDTO componente = new ComboboxDTO();
			componente.setMas(true);

			this.btnModificar.setDisabled(false);
			this.btnEliminar.setDisabled(false);
			this.txtBoxDescripcion.setDisabled(false);
			model.removeAll(this.listaItemsCombo);
			for (ComboboxDTO c : this.listaCompOrdenados) {
				model.add(model.size(), c);
			}
			componente.setOrder(this.orderItemCombo);
			model.add(model.size(), componente);
		}
	}

	public void onEliminar() throws Exception {

		if (getModo().equals(MODO_CONSULTA_FC)) {
			setModo(MODO_MODIFICACION_FC);
			prepararBotones();

			this.btnGuardar.setVisible(false);

			for (final ComponenteDTO item : this.listaMultivalores) {
				if (this.nombreCombobox.getValue().toUpperCase().equals(item.getNombre().toUpperCase())) {
					Boolean result = this.formularioService.verificarUsoComponente(item.getId().toString());
					if (result.equals(new Boolean(false))) {

						Messagebox.show(Labels.getLabel("abmCombobox.eliminar.vali"),
								Labels.getLabel("fc.export.atencion.title"), Messagebox.YES | Messagebox.NO,
								Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
									public void onEvent(Event evt) throws Exception {
										if ("onYes".equals(evt.getName())) {

											listaMultivalores.remove(item);
											listboxCombosExistenSugerencias
													.setModel(new ListModelArray(getListaComboboxs()));
											setListaMultivaloresAux(getListaComboboxs());
											limpiarPantalla();
											setModo(MODO_ALTA_FC);
											prepararBotones();
											componenteService.eliminarComponente(item);
											Messagebox.show(Labels.getLabel("abmCombobox.exitoElimComp.msg"),
													Labels.getLabel("fc.export.atencion.title"), Messagebox.OK,
													Messagebox.INFORMATION);
										}
									}
								});
					} else {
						Messagebox.show(Labels.getLabel("abmCombobox.fallaElimComp.msg"));
						limpiarPantalla();
						setModo(MODO_ALTA_FC);
						prepararBotones();
					}
				}
			}
		}
	}

	/**
	 * Permite la visualizacion del combobox a generar con todos sus items.
	 */
	public void onPrevisualizar() throws Exception {

		if (this.listaItemsCombo.size() != 0) {
			setListaComponentes(listaItemsCombo);
			if (!getModo().equals(MODO_ALTA_FC) && listaItemsCombo.size() == indexItemCombo) {
				this.previsualizar = true;
			}

			if (esComboValido()) {
				reCargarLista();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("nombreCombobox", nombreCombobox.getValue());
				map.put("items", listaItemsCombo);
				map.put("mostrar", this.bandBoxCheck.isChecked());
				this.previsualizarWindow = (Window) Executions.createComponents("/administradorFC/previsualizacionCombo.zul",
						this.self, map);
				this.previsualizarWindow.setClosable(true);
				this.previsualizarWindow.doModal();

			}
		} else {
			Messagebox.show(Labels.getLabel("abmCombobox.prev.vali"), Labels.getLabel("fc.export.atencion.title"),
					Messagebox.OK, Messagebox.INFORMATION);
		}
	}

	/**
	 * Guarda un nuevo combobox o un combobox existente modificado luego de
	 * cumplir ciertas validaciones.
	 */
	public void onGuardar() throws Exception {

		try {

			if (getModo().equals(MODO_ALTA_FC)) {
				if (this.listaItemsCombo.size() != 0) {
					setListaComponentes(this.listaItemsCombo);
					if (esComboValido()) {
						Messagebox.show(Labels.getLabel("abmCombobox.guardar.componente.vali"),
								Labels.getLabel("fc.confirmacion"), Messagebox.YES | Messagebox.NO,
								Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
									public void onEvent(Event evt) throws Exception {

										if ("onYes".equals(evt.getName()) && !bandBoxCheck.isChecked()) {
											componenteService.guardarComponente(prepararComboOBandbox(true));
											Messagebox.show(Labels.getLabel("abmCombobox.exitoNuevo.msg"),
													Labels.getLabel("fc.export.atencion.title"), Messagebox.OK,
													Messagebox.INFORMATION);
											if (!continuaCreacion) {
												abmComboboxWindow.onClose();

											} else {
												limpiarPantalla();
												setModo(MODO_ALTA_FC);
												prepararBotones();
											}

										}
										if (bandBoxCheck.isChecked()) {
											componenteService.guardarComponente(prepararComboOBandbox(true));
											Messagebox.show(Labels.getLabel("abmCombobox.exitoNuevoBandbox.msg"),
													Labels.getLabel("fc.export.atencion.title"), Messagebox.OK,
													Messagebox.INFORMATION);
											if (!continuaCreacion) {
												abmComboboxWindow.onClose();

											} else {
												limpiarPantalla();
												setModo(MODO_ALTA_FC);
												prepararBotones();
											}

										}
									}
								});
					}
				} else {
					Messagebox.show(Labels.getLabel("abmCombobox.guardarCombo.msg"),
							Labels.getLabel("fc.export.atencion.title"), Messagebox.OK, Messagebox.INFORMATION);
				}
			} else {
				if (getModo().equals(MODO_CONSULTA_FC)) {
					Messagebox.show(Labels.getLabel("abmCombobox.guardarModoConsulta.msg"),
							Labels.getLabel("fc.export.atencion.title"), Messagebox.OK, Messagebox.INFORMATION);
				} else {
					if (this.listaItemsCombo.size() != 0) {
						if (esComboValido()) {
							Messagebox.show(Labels.getLabel("abmCombobox.guardarModifica.vali"),
									Labels.getLabel("fc.export.atencion.title"), Messagebox.YES | Messagebox.NO,
									Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
										public void onEvent(Event evt) throws Exception {
											if ("onYes".equals(evt.getName())) {
												componenteService.guardarComponenteDTO(prepararComboOBandbox(false));
												Messagebox.show(Labels.getLabel("abmCombobox.exitoModifica.msg"),
														Labels.getLabel("fc.export.atencion.title"), Messagebox.OK,
														Messagebox.INFORMATION);
												if (!continuaCreacion) {
													abmComboboxWindow.onClose();
												} else {
													limpiarPantalla();
													setModo(MODO_ALTA_FC);
													prepararBotones();

												}
											}
										}
									});
							}
					}
					else {
						// If the combo list is empty (modification case), we show a message
						Messagebox.show(Labels.getLabel("abmCombobox.modificarCombo.msg"),
						    Labels.getLabel("fc.export.atencion.title"), Messagebox.OK, Messagebox.INFORMATION);
					}
				}
			}

		} catch (Exception e) {
			logger.error("Error en el alta del Combobox - " + e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * Verifica que todos los datos del combobox sean validos.
	 * 
	 * @throws InterruptedException
	 */
	public Boolean esComboValido() throws InterruptedException {

		if (StringUtils.isEmpty(this.nombreCombobox.getValue())) {
			this.nombreCombobox.setErrorMessage(Labels.getLabel("abmCombobox.nomCombo.vali"));
			return false;
		}

		if (getModo().equals(MODO_ALTA_FC)) {
			for (ComponenteDTO item : this.listaMultivalores) {
				if (this.nombreCombobox.getValue().toUpperCase().equals(item.getNombre().toUpperCase())) {
					this.nombreCombobox.setErrorMessage(Labels.getLabel("abmCombobox.nomCombo.existe"));
					return false;
				}
			}
		}

		if (StringUtils.isEmpty(this.descripcionCombobox.getValue())) {
			this.descripcionCombobox.setErrorMessage(Labels.getLabel("abmCombobox.desCombo.vali"));
			return false;
		}

		if (this.listaItemsCombo.size() != 0) {
			for (ComboboxDTO item : this.listaItemsCombo) {
				if (item.getDescripcion() == null || item.getDescripcion().equals("")) {
					Messagebox.show(Labels.getLabel("abmCombobox.descripcionVacia.vali"),
							Labels.getLabel("fc.export.atencion.title"), Messagebox.OK, Messagebox.INFORMATION);
					return false;
				}
			}

			if (!this.previsualizar) {
				int posicion = this.indexItemCombo;
				ComboboxDTO dato;
				dato = this.listaItemsCombo.get(posicion);
				int x = 0;
				for (ComboboxDTO item : this.listaItemsCombo) {
					if (dato.getDescripcion().toUpperCase().equals(item.getDescripcion().toUpperCase())) {
						x++;
						if (x == 2) {
							x = 0;
							Messagebox.show(Labels.getLabel("abmCombobox.datosRepetidos.vali"),
									Labels.getLabel("fc.export.atencion.title"), Messagebox.OK,
									Messagebox.INFORMATION);
							return false;
						}
					}
				}
			}
			this.previsualizar = false;
		}
		return true;
	}

	/**
	 * Guarda todos los datos del combobox en un tipo de dato componenteDTO que
	 * sera guardado en la base de datos.
	 */
	public ComponenteDTO prepararComboOBandbox(Boolean nuevo) {

		reCargarLista();
		items = new ArrayList<ItemDTO>();

		ComponenteDTO componente = new ComponenteDTO();
		componente.setId(this.comboboxModificacionConsulta != null ? this.comboboxModificacionConsulta.getId() : null);

		componente.setNombre(this.nombreCombobox.getValue());
		componente.setDescripcion(this.descripcionCombobox.getValue());
		componente.setFechaCreacion(this.comboboxModificacionConsulta != null
				? this.comboboxModificacionConsulta.getFechaCreacion() : (Date) Calendar.getInstance().getTime());

		componente.setUsuarioCreador(this.comboboxModificacionConsulta != null
				? this.comboboxModificacionConsulta.getUsuarioCreador() : loggedUser);
		componente.setUsuarioModificador(loggedUser);

		for (ComboboxDTO comp : this.listaItemsCombo) {
			ItemDTO item = new ItemDTO();
			item.setId(comp.getId() != null ? comp.getId() : null);
			item.setValor(comp.getDescripcion());
			item.setDescripcion(comp.getDescripcion());
			item.setOrden(comp.getOrder());
			item.setComponente(componente);
			items.add(item);
		}

		componente.setItems(new LinkedHashSet<ItemDTO>(getItems()));

		TipoComponenteDTO tipo = new TipoComponenteDTO();
		if (bandBoxCheck.isChecked()) {
			tipo.setId(5);
			tipo.setNombre("COMPLEXBANDBOX");
			tipo.setDescripcion(null);
			componente.setTipoComponenteEnt(tipo);
		} else {
			tipo.setId(4);
			tipo.setDescripcion(null);
			tipo.setNombre("COMBOBOX");
			componente.setTipoComponenteEnt(tipo);
		}

		AtributoComponenteDTO atributo = new AtributoComponenteDTO();
		atributo.setValor("true");
		atributo.setKey("readonly");
		atributo.setComponente(componente);

		componente.setAtributos(new HashMap<String, AtributoComponenteDTO>());
		componente.getAtributos().put(atributo.getKey(), atributo);

		return componente;
	}

	/**
	 * Establece los atributos de determinados componentes a sus valores
	 * iniciales.
	 */
	public void limpiarPantalla() {
		this.nombreCombobox.setValue(null);
		this.descripcionCombobox.setValue(null);
		ListModelList model = (ListModelList) this.listboxItemsCombo.getModel();
		model.removeAll(this.listaItemsCombo);
		if (listaCompOrdenados != null) {
			this.listaCompOrdenados.clear();
		}
		this.comboboxModificacionConsulta = null;
		this.continuaCreacion = false;
		this.previsualizar = false;
		this.indexItemCombo = 0;
		this.orderItemCombo = 1;
		this.btnModificar.setDisabled(true);
		this.btnEliminar.setDisabled(true);
		this.nombreCombobox.setReadonly(false);
		this.nombreCombobox.setValue("");
		this.nombreCombobox.setTooltiptext("");
	}

	public void modificarPantalla() {
		this.btnModificar.setDisabled(false);
		this.btnEliminar.setDisabled(false);
		this.txtBoxDescripcion.setDisabled(false);
		ListModelList model = (ListModelList) this.listboxItemsCombo.getModel();
		model.removeAll(this.listaItemsCombo);
		for (ComboboxDTO c : this.listaCompOrdenados) {
			model.add(model.size(), c);
		}
		reCargarLista();

	}

	/**
	 * Establece los valores de los atributos de los botones de acuerdo al modo
	 * de ejecuccion en que se encuentra.
	 */
	public void prepararBotones() {
		if (getModo().equals(MODO_ALTA_FC) && this.listaItemsCombo.size() == 0) {
			this.btnPrevisualizar.setVisible(true);
			this.btnGuardar.setVisible(true);
			this.nombreCombobox.setReadonly(false);
		} else {

			if (getModo().equals(MODO_CONSULTA_FC)) {
				this.btnModificar.setDisabled(false);
				this.btnEliminar.setDisabled(false);
				this.btnBorrar.setDisabled(true);
				this.txtBoxDescripcion.setDisabled(true);
				this.btnPrevisualizar.setVisible(true);
				this.nombreCombobox.setReadonly(true);
				this.btnGuardar.setVisible(false);
			} else {
				if (getModo().equals(MODO_MODIFICACION_FC)) {
					this.txtBoxDescripcion.setDisabled(false);
					this.btnPrevisualizar.setVisible(true);
					this.btnGuardar.setVisible(true);
					this.btnBorrar.setDisabled(false);
				} else {
					this.btnPrevisualizar.setVisible(false);
					this.btnGuardar.setVisible(false);
					this.btnBorrar.setDisabled(false);
					this.txtBoxDescripcion.setDisabled(false);
					this.nombreCombobox.setReadonly(false);
					this.itemComponenteCombo.setDraggable("true");

				}
			}
		}
	}

	public void onUpload$uploadCombo(UploadEvent event)
			throws IOException, JDOMException, InterruptedException, JDOMParseException {
		Boolean tipoBandBox = false;
		uploadComponente(event, tipoBandBox);
	}

	public void onUpload$uploadBandbox(UploadEvent event)
			throws IOException, JDOMException, InterruptedException, JDOMParseException {
		Boolean tipoBandBox = true;
		uploadComponente(event, tipoBandBox);
	}

	private void uploadComponente(UploadEvent event, Boolean tipoBandBox) throws InterruptedException {
		setModo(MODO_ALTA_FC);
		bandBoxCheck.setChecked(tipoBandBox);
		prepararBotones();
		limpiarPantalla();
		String nameFile = event.getMedia().getName();
		String[] splitName = nameFile.split("\\.");
		String extension = splitName[1];
		String part = new String(event.getMedia().getByteData());
		if ((!extension.equalsIgnoreCase("csv") == true || (event.getMedia() instanceof AImage == true))
				&& (!event.getMedia().getFormat().equalsIgnoreCase("xls")) || part.contains("<") == true) {
			msgTipoArchivoInvalido();
		} else {
			AMedia media = (AMedia) event.getMedia();
			String cvsSplitBy = "\r\n";
			byte[] read = media.getByteData();
			String linea;
			try {
				linea = new String(read, "ISO-8859-1");
				final String[] csvReaded = linea.split(cvsSplitBy);
				ComponenteDTO comp = this.ExisteCombo(csvReaded[0]);
				if (comp == null) {
					cargarCombo(csvReaded, tipoBandBox);
				} else {
					Messagebox.show(Labels.getLabel("abmCombobox.importar.error.nombreYaUsado"),
							Labels.getLabel("fc.export.atencion.title"), Messagebox.OK, Messagebox.EXCLAMATION);
					reCargarLista();
				}

			} catch (UnsupportedEncodingException e) {
				logger.error("Mensaje de error", e);
			}
		}
	}

	private boolean validarCargaComboElementosNulos(String[] csvReaded) {
		for (String string : csvReaded) {
			if (string.equals("")) {
				return true;
			}
		}
		return false;
	}

	private boolean validarCargaComboRepetidos(String[] csvReaded) {
		Set<ItemDTO> items = new HashSet<ItemDTO>();
		Set<String> nombresUsados = new HashSet<String>();
		ComponenteDTO componente = new ComponenteDTO();

		for (int i = 2, j = 0; i < csvReaded.length; i++, j++) {
			ItemDTO item = new ItemDTO();
			item.setId(componente.getId() != null ? componente.getId() : null);
			item.setValor(csvReaded[i]);
			item.setDescripcion(csvReaded[i]);
			item.setOrden(j);
			item.setComponente(componente);
			items.add(item);
			nombresUsados.add(item.getValor());
		}

		if (nombresUsados.size() != items.size()) {
			return true;
		}

		return false;

	}

	private void cargarCombo(String[] csvReaded, boolean tipoBandBox) throws InterruptedException {
		ComponenteDTO componente = new ComponenteDTO();

		Boolean resultNulo = validarCargaComboElementosNulos(csvReaded);
		Boolean resultRepetidos = validarCargaComboRepetidos(csvReaded);

		if (!resultNulo && !resultRepetidos) {
			componente.setNombre(csvReaded[0]);
			componente.setDescripcion(csvReaded[1]);
			componente.setUsuarioModificador(loggedUser);

			Set<ItemDTO> items = new HashSet<ItemDTO>();

			for (int i = 2, j = 0; i < csvReaded.length; i++, j++) {
				ItemDTO item = new ItemDTO();
				item.setValor(csvReaded[i]);
				item.setDescripcion(csvReaded[i]);
				item.setOrden(j);
				item.setComponente(componente);
				items.add(item);
			}

			componente.setItems(items);

			if (tipoBandBox == false) {
				TipoComponenteDTO tipo = new TipoComponenteDTO();
				tipo.setId(4);
				tipo.setDescripcion(null);
				tipo.setNombre("COMBOBOX");
				componente.setTipoComponenteEnt(tipo);
			} else {
				TipoComponenteDTO tipo = new TipoComponenteDTO();
				tipo.setId(5);
				tipo.setNombre("COMPLEXBANDBOX");
				tipo.setDescripcion(null);
				componente.setTipoComponenteEnt(tipo);
			}

			AtributoComponenteDTO atributo = new AtributoComponenteDTO();
			atributo.setValor("true");
			atributo.setKey("readonly");
			atributo.setComponente(componente);

			componente.setAtributos(new HashMap<String, AtributoComponenteDTO>());
			componente.getAtributos().put(atributo.getKey(), atributo);

			try {
				componenteService.guardarComponente(componente);
				listaMultivalores.add(componente);
				ordenarLista();
				if (tipoBandBox == false) {
					Messagebox.show(Labels.getLabel("abmCombobox.exitoNuevo.msg"),
							Labels.getLabel("fc.export.atencion.title"), Messagebox.OK, Messagebox.INFORMATION);
				} else {
					Messagebox.show(Labels.getLabel("abmCombobox.exitoNuevoBandbox.msg"),
							Labels.getLabel("fc.export.atencion.title"), Messagebox.OK, Messagebox.INFORMATION);
				}

				reCargarLista();
				limpiarPantalla();
			} catch (Exception e) {
				logger.error("Mensaje de Error" + e);
			}
		} else {
			try {
				if (resultNulo) {
					this.msgTipoParametroInvalido();
				}
				if (resultRepetidos) {
					this.msgTipoItemsRepetidos();
				}
			} catch (InterruptedException e) {
				logger.error("Error al mostrar mensaje de parametros invalido " + e);
				throw e;
			}
		}
		limpiarPantalla();
		try {
			onExistentes();
		} catch (DynFormException e) {
			logger.error("Error al mostrar los componentes existentes" + e);
		}
	}

	private ComponenteDTO ExisteCombo(String nombre) {
		for (ComponenteDTO combo : listaMultivalores) {
			if (combo.getNombre().equals(nombre))
				return combo;
		}
		return null;
	}

	/**
	 * Confirma la cancelacion de creacion de combobox y procede al cierre de
	 * ventana.
	 */
	public void onCancelar() throws Exception {
		try {

			Messagebox.show(Labels.getLabel("fc.export.salirPreg.msg"), Labels.getLabel("fc.confirmacion"),
					Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
						public void onEvent(Event evt) throws InterruptedException {
							if ("onYes".equals(evt.getName())) {
								abmComboboxWindow.onClose();
							}
						}
					});
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
	}

	public void onChanging$buscarSugerencia(InputEvent e) {

		List<ComponenteDTO> listaAux = new ArrayList<ComponenteDTO>();

		for (ComponenteDTO componente : listaMultivaloresAux) {
			if (componente.getNombre().toLowerCase().contains(e.getValue().toLowerCase())) {
				listaAux.add(componente);
			}
		}
		if (!e.getValue().equals("")) {
			this.listboxCombosExistenSugerencias.setModel(new ListModelArray(listaAux));
		} else {
			this.listboxCombosExistenSugerencias.setModel(new ListModelArray(listaMultivaloresAux));
		}
	}

	public void msgTipoArchivoInvalido() throws InterruptedException {
		Messagebox.show(Labels.getLabel("abmCombobox.importar.error.tipoFile"),
				Labels.getLabel("fc.export.atencion.title"), Messagebox.OK, Messagebox.EXCLAMATION);
	}

	public void msgTipoParametroInvalido() throws InterruptedException {
		Messagebox.show(Labels.getLabel("abmCombobox.importar.error.parametros"),
				Labels.getLabel("fc.export.atencion.title"), Messagebox.OK, Messagebox.EXCLAMATION);
	}

	public void msgTipoItemsRepetidos() throws InterruptedException {
		Messagebox.show(Labels.getLabel("abmCombobox.item.repetidos"), Labels.getLabel("fc.export.atencion.title"),
				Messagebox.OK, Messagebox.EXCLAMATION);
	}

	public List<ComboboxDTO> getListaComponentes() {
		return listaItemsCombo;
	}

	public void setListaComponentes(List<ComboboxDTO> listaComponentes) {
		this.listaItemsCombo = listaComponentes;
	}

	public List<ComponenteDTO> getListaComboboxs() {
		return listaMultivalores;
	}

	public void setListaComboboxs(List<ComponenteDTO> listaComboboxs) {
		this.listaMultivalores = listaComboboxs;
	}

	public List<ItemDTO> getItems() {
		return items;
	}

	public void setItems(List<ItemDTO> items) {
		this.items = items;
	}

	public String getModo() {
		return modo;
	}

	public void setModo(String modo) {
		this.modo = modo;
	}

	public int getIndex() {
		return indexItemCombo;
	}

	public void setIndex(int index) {
		this.indexItemCombo = index;
	}

	public int getOrder() {
		return orderItemCombo;
	}

	public void setOrder(int order) {
		this.orderItemCombo = order;
	}

	public List<ComponenteDTO> getListaMultivaloresAux() {
		return listaMultivaloresAux;
	}

	public void setListaMultivaloresAux(List<ComponenteDTO> listaMultivaloresAux) {
		this.listaMultivaloresAux = listaMultivaloresAux;
	}

	public ComboboxDTO getElementoSeleccionado() {
		return elementoSeleccionado;
	}

	public void setElementoSeleccionado(ComboboxDTO elementoSeleccionado) {
		this.elementoSeleccionado = elementoSeleccionado;
	}

	public List<ComboboxDTO> getListaItemsCombo() {
		return listaItemsCombo;
	}

	public void setListaItemsCombo(List<ComboboxDTO> listaItemsCombo) {
		this.listaItemsCombo = listaItemsCombo;
	}

}