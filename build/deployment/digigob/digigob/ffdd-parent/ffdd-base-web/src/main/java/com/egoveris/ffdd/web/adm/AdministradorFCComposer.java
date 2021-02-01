package com.egoveris.ffdd.web.adm;

import java.io.IOException;
import java.io.Reader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.ObjectUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.JDOMParseException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;
import org.zkoss.image.AImage;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listfooter;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.egoveris.ffdd.base.exception.AccesoDatoException;
import com.egoveris.ffdd.base.service.IComponenteService;
import com.egoveris.ffdd.base.service.IConstraintService;
import com.egoveris.ffdd.base.service.IFormularioService;
import com.egoveris.ffdd.base.service.ITransaccionService;
import com.egoveris.ffdd.model.exception.DynFormException;
import com.egoveris.ffdd.model.exception.DynFormValorComponente;
import com.egoveris.ffdd.model.model.AtributoComponenteDTO;
import com.egoveris.ffdd.model.model.ComponenteDTO;
import com.egoveris.ffdd.model.model.FormTriggerDTO;
import com.egoveris.ffdd.model.model.FormularioComponenteDTO;
import com.egoveris.ffdd.model.model.FormularioDTO;
import com.egoveris.ffdd.model.model.ItemDTO;
import com.egoveris.ffdd.model.model.TipoComponenteDTO;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class AdministradorFCComposer extends GenericForwardComposer {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(AdministradorFCComposer.class);

	public static final String ACCION_ALTA = "alta";
	public static final String ACCION_BAJA = "baja";
	public static final String ACCION_MODIF = "modif";

	public static final String FORMULARIO_CREADO = Labels.getLabel("admFcComposer.finalString.formGenerado");
	public static final String FORMULARIO_NO_CREADO = Labels.getLabel("admFcComposer.finalString.formNoGenerado");
	public static final String FORMULARIO_ACTUALIZADO = Labels.getLabel("admFcComposer.finalString.formActualizado");
	public static final String FORMULARIO_CON_INSTANCIA = Labels.getLabel("admFcComposer.finalString.formNoActualizado");
	public static final String COMBOBOX_ACTUALIZADO = Labels.getLabel("admFcComposer.finalString.formNoActulCombIncluido");

	public static String md5Spring(final String text) {
		return DigestUtils.md5DigestAsHex(text.getBytes());
	}

	// Beans
	@WireVariable("formularioService")
	private IFormularioService formularioService;
	@WireVariable("transaccionService")
	private ITransaccionService transaccionService;
	@WireVariable("componenteService")
	private IComponenteService componenteService;
	@WireVariable("constraintService")
	private IConstraintService constraintService;

	// Componentes ZUL
	private Window hiddenView;
	private AnnotateDataBinder binder;
	private Listbox listboxFC;
	private Window newComboWindow;
	private Window hiddenViewCajas;
	private Window hiddenViewComp;
	private Window exportarWindow;
	private Window ccomplejosWindow;

	// Atributos
	private List<FormularioDTO> listaFormulariosValidar; //FIXME the old object type was Formulario. It's still necessary?
	private List<FormularioDTO> listaFormularios;
	private List<ComponenteDTO> listaComponentesExistentes;
	private FormularioDTO selectedFormulario;
	private LinkedHashMap<String, String> formulariosImportados;
	private String loggedUser;
	private ComponenteDTO componenteNuevo = null;
	private Boolean huboCambioCombo = false;
	private Textbox buscarFormulario;
	private Listfooter cantidadFormulariosLf;
	private Listfooter labelFormulariosLf;

	private void agregarEventSortListbox() {
		final Listhead listhead = this.listboxFC.getListhead();
		final List<?> list = listhead.getChildren();
		for (final Object object : list) {
			if (object instanceof Listheader) {
				final Listheader lheader = (Listheader) object;
				if (lheader.getSortAscending() != null || lheader.getSortDescending() != null) {
					lheader.addEventListener(Events.ON_SORT, new EventListener() {
						@Override
						public void onEvent(final Event event) throws Exception {
							// fix si se selecciona un formulario y se ordena,
							// se deselecciona el formulario y vuelve pag 1.
							Events.postEvent(Events.ON_SELECT, listboxFC, null);
						}
					});
					if (lheader.getLabel().equals(Labels.getLabel("fc.form.fechaCreacion"))) {
						lheader.setSortDirection("descending");
					}
				}
			}
		}
	}

	private void asignarListeners(final Component comp) throws DynFormValorComponente {
		try {
			comp.addEventListener(Events.ON_NOTIFY, new AdministradorFCComposerListener(this));
		} catch (final DynFormValorComponente e) {
			logger.error("Error al asignar listeners - " + e.getMessage());
			throw new DynFormValorComponente(Labels.getLabel("admFcComposer.exception.errorListeners"), e);
		}
	}

	@Override
	public void doAfterCompose(final Component comp) throws Exception {
		try {
			super.doAfterCompose(comp);
			this.binder = new AnnotateDataBinder(comp);
			this.binder.loadAll();
			Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
			obtenerDatos();
			asignarListeners(comp);
			agregarEventSortListbox();
		} catch (final DynFormException e) {
			throw new DynFormException(Labels.getLabel("abmComboboxComposer.exception.errorAdm")
					+ Labels.getLabel("abmComboboxComposer.exception.intMinutos") + e.getMessage(), e);
		}
	}

	private void eliminarComponetesMultilinea(final FormularioDTO formActulaizar, final FormularioDTO formExistente)
			throws DynFormException {

		Boolean textBoxExiste = false;
		for (final FormularioComponenteDTO componenteExistente : formExistente.getFormularioComponentes()) {
			textBoxExiste = false;
			if (componenteExistente.isMultilinea()) {
				for (final FormularioComponenteDTO componenteNuevo : formActulaizar.getFormularioComponentes()) {
					if (componenteNuevo.getId() != null) {
						final int viejo = componenteExistente.getId();
						final int nuevo = componenteNuevo.getId();
						if (viejo == nuevo) {
							textBoxExiste = true;
							break;
						}
					}
				}
				if (!textBoxExiste) {
					this.formularioService.eliminarMultilinea(componenteExistente.getId());
				}
			}
		}
	}

	private void eliminarFormulario() throws Exception {
		try {

			for (final FormularioComponenteDTO componente : this.selectedFormulario.getFormularioComponentes()) {
				if (componente.isMultilinea()) {
					this.formularioService.eliminarMultilinea(componente.getId());
				}
			}

			this.formularioService.eliminarFormulario(this.selectedFormulario.getNombre());
			this.refrescarFC();
		} catch (final Exception e) {
			logger.error("Error al intentar eliminar el formulario controlado - ", e.getMessage());
			throw e;
		}
	}

	public Listfooter getCantidadFormulariosLf() {
		return cantidadFormulariosLf;
	}

	public List<FormularioDTO> getListaFormularios() {
		return listaFormularios;
	}

	public FormularioDTO getSelectedFormulario() {
		return selectedFormulario;
	}

	private void guardarComponentesMultilinea(final FormularioDTO form) throws AccesoDatoException {
		if(form != null){
			this.formularioService.guardarComponenteMultilinea(form);
		}
	}

	private void guardarFormTriggers(final FormularioDTO formulario, final List<FormTriggerDTO> listaTrigger)
			throws DynFormException, AccesoDatoException {
		if (!listaTrigger.isEmpty()) {
			int idFormulario;
			if (formulario.getId() != null) {
				idFormulario = formulario.getId();
			} else {
				final FormularioDTO form = formularioService.buscarFormularioPorNombre(formulario.getNombre());
				idFormulario = form.getId();
			}
			for (final FormTriggerDTO trigger : listaTrigger) {
				trigger.setIdForm(idFormulario);
			}
			this.constraintService.guardarListConstraints(listaTrigger);
		}
	}

	private void modificarComponentesMultilinea(final FormularioDTO form) throws AccesoDatoException {
		if(form != null){
			this.formularioService.guardarComponenteMultilinea(form);
		}
	}

	public void modificarFormTriggers(final Integer idFormulario, final List<FormTriggerDTO> listaTrigger)
			throws DynFormException, AccesoDatoException {
		List<FormTriggerDTO> triggersAux = null;
		triggersAux = this.constraintService.obtenerConstraintsPorComponente(idFormulario);
		this.constraintService.eliminarConstraints(triggersAux);
		for (final FormTriggerDTO trigger : listaTrigger) {
			trigger.setId(idFormulario);
		}
		this.constraintService.guardarListConstraints(listaTrigger);
	}

	public void msgTipoArchivoInvalido() throws InterruptedException {
		Messagebox.show(Labels.getLabel("fc.export.importar.error.tipoFile"),
				Labels.getLabel("fc.export.atencion.title"), Messagebox.OK, Messagebox.EXCLAMATION);
	}

	private void obtenerDatos() throws Exception {
		try {
			this.refrescarFC();
			this.loggedUser = Executions.getCurrent().getRemoteUser();
		} catch (final Exception e) {
			logger.error("Error al obtener datos - " + e.getMessage());
			throw e;
		}
	}

	private void onABM(final Map<String, Object> map) throws InterruptedException {
		if (this.hiddenView != null) {
			this.hiddenView.detach();
			this.hiddenView = (Window) Executions.createComponents("/administradorFC/abmFC.zul", this.self, map);
			try {
				this.hiddenView.doModal();
			} catch (final SuspendNotAllowedException snae) {
				logger.error("Error al intentar mostrar la ventana de detalle - ", snae);
			}
		} else {
			Messagebox.show(Labels.getLabel("gedo.general.imposibleIniciarVista"),
					Labels.getLabel("gedo.general.error"), Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void onABMC() throws InterruptedException {
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("modo", ABMFCComposer.MODO_ALTA_CAJAS);
		this.hiddenViewCajas = (Window) Executions.createComponents("/administradorFC/abmCajasDeDatos.zul", this.self, map);
		this.hiddenViewCajas.setClosable(true);
		try {
			this.hiddenViewCajas.setPosition("center");
			this.hiddenViewCajas.doModal();
		} catch (final SuspendNotAllowedException e) {
			logger.error("mensaje de error" + e);
		}
	}

	public void onABMCOMP() throws InterruptedException {
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("modo", ABMFCComposer.MODO_ALTA_COMP);
		this.hiddenViewComp = (Window) Executions.createComponents("/administradorFC/abmComponentes.zul", this.self, map);
		this.hiddenViewComp.setClosable(true);
		try {
			this.hiddenViewComp.setPosition("center");
			this.hiddenViewComp.doModal();
		} catch (final SuspendNotAllowedException e) {
			logger.error("mensaje de error" + e);
		}
	}

	public void onAC() throws InterruptedException {

		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("modo", ABMFCComposer.MODO_ALTA_FC);
		this.newComboWindow = (Window) Executions.createComponents("/administradorFC/abmCombobox.zul", this.self, map);
		this.newComboWindow.setClosable(true);
		try {
			this.newComboWindow.setPosition("center");
			this.newComboWindow.doModal();
		} catch (final SuspendNotAllowedException e) {
			logger.error("mensaje de error" + e);
		}
	}

	public void onAFC() throws InterruptedException {
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("modo", ABMFCComposer.MODO_ALTA_FC);
		onABM(map);
	}

	/**
	 * busqueda formularios
	 */
	public void onBuscarFormulario() {

		final List<FormularioDTO> lista = new ArrayList<FormularioDTO>();
		for (final FormularioDTO form : listaFormularios) {
			if (form.getNombre().toLowerCase().contains(buscarFormulario.getValue().toLowerCase())
					|| form.getDescripcion().toLowerCase().contains(buscarFormulario.getValue().toLowerCase())) {
				lista.add(form);
			}
		}
		setListaNueva(lista);
	}

	public void onCFC() throws DynFormException, InterruptedException {

		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("formularioControlado",
				this.formularioService.buscarFormularioPorNombre(this.selectedFormulario.getNombre()));
		map.put("modo", ABMFCComposer.MODO_CLONACION_FC);
		onABM(map);
	}

	public void onEliminarFC() throws InterruptedException {
		try {
			this.selectedFormulario = this.formularioService
					.buscarFormularioPorNombre(this.selectedFormulario.getNombre());
			if (this.transaccionService.existeTransaccionParaFormulario(this.selectedFormulario.getNombre())) {
				Messagebox.show(Labels.getLabel("fc.noPuedeBorrar.msg"), Labels.getLabel("fc.eli.title.confirmacion"), Messagebox.OK,
						Messagebox.EXCLAMATION);
			} else {
				Messagebox.show(Labels.getLabel("fc.estaSeguroBorrar.msg"), Labels.getLabel("fc.eli.title.confirmacion"),
						Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener<Event>() {
							@Override
							public void onEvent(final Event evt) throws Exception {
								if ("onYes".equals(evt.getName())) {
									eliminarFormulario();
								}
							}
						});
			}
		} catch (final Exception e) {
			logger.error("Error al intentar eliminar el formulario controlado - ", e);
			Messagebox.show(Labels.getLabel("fc.noPuedeBorrarAdm.msg"), Labels.getLabel("fc.eli.title.confirmacion"), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	public void onExportar() throws InterruptedException {

		this.exportarWindow = (Window) Executions.createComponents("/administradorFC/administradorExportFC.zul", this.self, null);
		this.exportarWindow.setClosable(true);
		try {
			this.exportarWindow.setPosition("center");
			this.exportarWindow.doModal();
		} catch (final SuspendNotAllowedException e) {
			logger.error("mensaje de error" + e);
		}
	}

	public void onLimpiarBusqueda() {
		listboxFC.setModel(new BindingListModelList(listaFormularios, true));
		cantidadFormulariosLf.setStyle("color:#026290");
		labelFormulariosLf.setStyle("color:#026290");
		buscarFormulario.setValue("");
		cantidadFormulariosLf.setLabel(String.valueOf(listboxFC.getItemCount()));
	}

	public void onMFC() throws InterruptedException, DynFormException {
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("formularioControlado",
				this.formularioService.buscarFormularioPorNombre(this.selectedFormulario.getNombre()));

		if (this.transaccionService.existeTransaccionParaFormulario(this.selectedFormulario.getNombre())) {
			map.put("modo", ABMFCComposer.MODO_CONSULTA_FC);
		} else {
			map.put("modo", ABMFCComposer.MODO_MODIFICACION_FC);
		}

		onABM(map);
	}

	public void onUpload$uploadFormulario(final UploadEvent event)
			throws IOException, JDOMException, InterruptedException, AccesoDatoException {

		this.listaFormulariosValidar = this.formularioService.obtenerTodosLosFormularios();
		this.listaComponentesExistentes = this.formularioService.obtenerTodosLosComponentes();

		if (event.getMedia() instanceof AImage) {
			msgTipoArchivoInvalido();
		} else {
			final AMedia media = (AMedia) event.getMedia();
			if (media.isBinary()) {
				msgTipoArchivoInvalido();
			} else {
				if (media.getFormat().equals("xml")) {
					try {

						final Reader read = media.getReaderData();
						final SAXBuilder builder = new SAXBuilder();
						final Document a = builder.build(read);
						read.close();

						final Element formularios = a.getRootElement();
						final String md5 = formularios.getChild("md5").getValue();
						formularios.removeChild("md5");

						final XMLOutputter xmlOutput = new XMLOutputter();
						xmlOutput.setFormat(Format.getPrettyFormat());
						final String docString = xmlOutput.outputString(a);

						if (!md5Spring(docString).equals(md5)) {
							Messagebox.show(Labels.getLabel("fc.export.importar.file.vali"),
									Labels.getLabel("fc.export.atencion.title"), Messagebox.OK, Messagebox.EXCLAMATION);
						} else {
							validarFormularios(formularios);

							final StringBuffer msj = new StringBuffer();
							msj.append(Labels.getLabel("admFcComposer.msjApp.formImportados") + " \n\n");
							for (final Entry<String, String> entry : formulariosImportados.entrySet()) {
								msj.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n\n");
							}
							Messagebox.show(msj.toString(), Labels.getLabel("fc.export.atencion.title"), Messagebox.OK,
									Messagebox.INFORMATION);
							refrescarFC();
						}
					} catch (final JDOMParseException pe) {
						logger.trace("Dom parsging error: ", pe);
						Messagebox.show(Labels.getLabel("fc.export.importar.file.vali"),
								Labels.getLabel("fc.export.atencion.title"), Messagebox.OK, Messagebox.EXCLAMATION);
					} catch (final Exception e) {
						logger.error("mensaje de error: " + e.getMessage(), e);
					}
				} else {
					msgTipoArchivoInvalido();
				}
			}
		}

	}

	private void ordenarListaPorFechaCreacion(final List<FormularioDTO> listFormulario) {
		Collections.sort(listFormulario, new Comparator<FormularioDTO>() {
			@Override
			public int compare(final FormularioDTO s1, final FormularioDTO s2) {
				return ObjectUtils.compare(s2.getFechaCreacion(), s1.getFechaCreacion());
			}
		});
	}

	@SuppressWarnings("rawtypes")
	public ComponenteDTO prepararComponente(final Element componente, final ComponenteDTO compExistente) {

		final ComponenteDTO componenteNuevo = new ComponenteDTO();

		if (compExistente == null) {
			componenteNuevo.setId(null);
			componenteNuevo.setFechaCreacion(Calendar.getInstance().getTime());
			componenteNuevo.setUsuarioCreador(loggedUser);
			componenteNuevo.setUsuarioModificador(loggedUser);

		} else {
			componenteNuevo.setId(compExistente.getId());
			componenteNuevo.setFechaCreacion(compExistente.getFechaCreacion());
			componenteNuevo.setUsuarioCreador(compExistente.getUsuarioCreador());
			componenteNuevo.setUsuarioModificador(loggedUser);
		}

		componenteNuevo.setNombre(componente.getChildText("name"));
		componenteNuevo.setDescripcion(componente.getChildText("description"));

		final TipoComponenteDTO tipoComp = new TipoComponenteDTO();
		final Element tipo = componente.getChild("type");
		tipoComp.setId(Integer.parseInt(tipo.getChildText("id")));
		tipoComp.setNombre(tipo.getChildText("name"));
		tipoComp.setDescripcion(tipo.getChildText("description"));
		componenteNuevo.setTipoComponenteEnt(tipoComp);

		final Map<String, AtributoComponenteDTO> mapAtributos = new HashMap<String, AtributoComponenteDTO>();
		AtributoComponenteDTO atributoNuevo;
		final Element atributos = componente.getChild("atributtes");
		final List listAtributos = atributos.getChildren("atributte");
		for (int k = 0; k < listAtributos.size(); k++) {
			atributoNuevo = new AtributoComponenteDTO();
			final Element atributo = (Element) listAtributos.get(k);
			atributoNuevo.setValor(atributo.getChildText("value"));
			atributoNuevo.setKey(atributo.getChildText("key"));
			atributoNuevo.setComponente(componenteNuevo);
			mapAtributos.put(atributoNuevo.getKey(), atributoNuevo);
		}

		componenteNuevo.setAtributos(mapAtributos);

		final Set<ItemDTO> setItems = new HashSet<ItemDTO>();
		ItemDTO item;

		final Element multivalues = componente.getChild("multivalues");
		final List listaValores = multivalues.getChildren("value");
		for (int l = 0; l < listaValores.size(); l++) {
			item = new ItemDTO();
			final Element valor = (Element) listaValores.get(l);
			if (compExistente != null) {
				final Set<ItemDTO> setItem = compExistente.getItems();
				for (final ItemDTO items : setItem) {
					if (items.getValor().equals(valor.getChildText("value"))) {
						item.setId(items.getId());
						break;
					} else {
						item.setId(null);
					}
				}
			} else {
				item.setId(null);
			}
			item.setValor(valor.getChildText("value"));
			item.setDescripcion(valor.getChildText("description"));
			item.setOrden(Integer.parseInt(valor.getChildText("order")));
			item.setComponente(componenteNuevo);
			setItems.add(item);
		}
		componenteNuevo.setItems(setItems);
		return componenteNuevo;
	}

	@SuppressWarnings("rawtypes")
	public FormularioDTO prepararFormulario(final Element form, final FormularioDTO formularioExistente,
			final List<FormTriggerDTO> listaTrigger) {

		final FormularioDTO formulario = new FormularioDTO();
		FormularioComponenteDTO compFormActualizar;
		ComponenteDTO comp;
		ComponenteDTO compExistente = new ComponenteDTO();
		final Element componentesFormulario = form.getChild("formcomponents");
		final List listComponentesForm = componentesFormulario.getChildren("formcomponent");
		final Set<FormularioComponenteDTO> setComponentesForm = new HashSet<FormularioComponenteDTO>();

		formulario.setId(formularioExistente != null ? formularioExistente.getId() : null);
		formulario.setFechaCreacion(formularioExistente != null ? formularioExistente.getFechaCreacion()
				: (Date) Calendar.getInstance().getTime());
		formulario
				.setUsuarioCreador(formularioExistente != null ? formularioExistente.getUsuarioCreador() : loggedUser);
		formulario.setUsuarioModificador(loggedUser);
		formulario.setFechaModificacion(Calendar.getInstance().getTime());
		formulario.setNombre(form.getAttributeValue("name"));
		formulario.setDescripcion(form.getAttributeValue("description"));

		for (int i = 0; i < listComponentesForm.size(); i++) {
			compFormActualizar = new FormularioComponenteDTO();
			final Element componenteForm = (Element) listComponentesForm.get(i);

			if (formularioExistente == null) {
				compFormActualizar.setId(null);
			} else {
				final Set<FormularioComponenteDTO> setComponentesFormExiste = formularioExistente
						.getFormularioComponentes();
				for (final FormularioComponenteDTO dato : setComponentesFormExiste) {
					if (dato.getNombre().equals(componenteForm.getChildText("name"))) {
						compFormActualizar.setId(dato.getId());
						break;
					}
				}
			}

			compFormActualizar.setNombre(componenteForm.getChildText("name"));
			compFormActualizar.setEtiqueta(componenteForm.getChildText("label"));
			compFormActualizar.setOrden(Integer.parseInt(componenteForm.getChildText("order")));
			compFormActualizar.setObligatorio(Boolean.parseBoolean(componenteForm.getChildText("obligatory")));
			compFormActualizar.setRelevanciaBusqueda(Integer.parseInt(componenteForm.getChildText("searchrelevancy")));
			compFormActualizar.setOculto(Boolean.parseBoolean(componenteForm.getChildText("hidden")));

			compFormActualizar.setTextoMultilinea(componenteForm.getChildText("textoMultilinea") != null
					? componenteForm.getChildText("textoMultilinea") : null);

			final Element componentes = form.getChild("components");
			final List listaComponentes = componentes.getChildren("component");
			final Element componente = (Element) listaComponentes.get(i);
			comp = new ComponenteDTO();

			if (this.componenteNuevo != null && componente.getChildText("name").equals(componenteNuevo.getNombre())) {
				comp.setId(this.componenteNuevo.getId());
			} else {
				for (final ComponenteDTO dato : this.listaComponentesExistentes) {
					if (dato.getNombre().equals(componente.getChildText("name"))) {
						comp.setId(dato.getId());
						compExistente = dato;
						break;
					}
				}
			}
			comp.setNombre(componente.getChildText("name"));
			comp.setDescripcion(componente.getChildText("description"));

			final Element tipo = componente.getChild("type");
			final TipoComponenteDTO tipoComp = new TipoComponenteDTO();
			tipoComp.setId(this.componenteNuevo != null ? this.componenteNuevo.getTipoComponenteEnt().getId()
					: Integer.parseInt(tipo.getChildText("id")));
			tipoComp.setNombre(tipo.getChildText("name"));
			tipoComp.setDescripcion(tipo.getChildText("description"));
			comp.setTipoComponenteEnt(tipoComp);

			final Map<String, AtributoComponenteDTO> mapAtributos = new HashMap<String, AtributoComponenteDTO>();
			AtributoComponenteDTO atributoNuevo;
			final Element atributos = componente.getChild("atributtes");
			final List listAtributos = atributos.getChildren("atributte");
			for (int k = 0; k < listAtributos.size(); k++) {
				atributoNuevo = new AtributoComponenteDTO();
				final Element atributo = (Element) listAtributos.get(k);
				if (this.componenteNuevo != null) {
					final Map<String, AtributoComponenteDTO> mapAtributoComponente = this.componenteNuevo.getAtributos();
					final Set<String> setLlaves = this.componenteNuevo.getAtributos().keySet();
					for (final String key : setLlaves) {
						final AtributoComponenteDTO aux = mapAtributoComponente.get(key);
						atributoNuevo.setId(aux.getId());
						break;
					}
				}
				atributoNuevo.setValor(atributo.getChildText("value"));
				atributoNuevo.setKey(atributo.getChildText("key"));
				atributoNuevo.setComponente(comp);
				mapAtributos.put(atributoNuevo.getKey(), atributoNuevo);
			}

			comp.setAtributos(mapAtributos);

			final Set<ItemDTO> setItems = new HashSet<ItemDTO>();
			ItemDTO item;
			final Element multivalues = componente.getChild("multivalues");
			final List listaValores = multivalues.getChildren("value");

			for (int l = 0; l < listaValores.size(); l++) {
				item = new ItemDTO();
				final Element valor = (Element) listaValores.get(l);
				if (this.componenteNuevo != null) {
					if (this.componenteNuevo.getNombre().equals(componente.getChildText("name"))) {
						final Set<ItemDTO> setItem = this.componenteNuevo.getItems();
						for (final ItemDTO items : setItem) {
							if (items.getValor().equals(valor.getChildText("value"))) {
								item.setId(items.getId());
								break;
							}
						}
					}
				} else {
					final Set<ItemDTO> setItemsExistentes = compExistente.getItems();
					for (final ItemDTO itemExistente : setItemsExistentes) {
						if (itemExistente.getValor().equals(valor.getChildText("value"))) {
							item.setId(itemExistente.getId());
							break;
						}
					}
				}
				item.setValor(valor.getChildText("value"));
				item.setDescripcion(valor.getChildText("description"));
				item.setOrden(Integer.parseInt(valor.getChildText("order")));
				item.setComponente(comp);
				setItems.add(item);
			}
			comp.setItems(setItems);

			compFormActualizar.setComponente(comp);
			compFormActualizar.setFormulario(formulario);
			setComponentesForm.add(compFormActualizar);
		}

		final Element formTriggers = form.getChild("triggers");
		final List listaTriggers = formTriggers.getChildren("trigger");

		for (int j = 0; j < listaTriggers.size(); j++) {
			final Element triggerAux = (Element) listaTriggers.get(j);
			final FormTriggerDTO trigger = new FormTriggerDTO();
			// trigger.setIdForm(Integer.valueOf(triggerAux
			// .getChildText("formulario")));
			trigger.setType(triggerAux.getChildText("type"));
			final String json = triggerAux.getChildText("json");
			trigger.setJson(json);
			final String fechaCreacion = triggerAux.getChildText("fechaCreacion");
			final DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date fecha = new Date();
			try {
				fecha = df.parse(fechaCreacion);
			} catch (final ParseException e) {
				logger.error("Error al convertir fecha de alta: " + e.getMessage());
			}
			trigger.setFechaCreacion(fecha);
			listaTrigger.add(trigger);
		}

		formulario.setFormularioComponentes(setComponentesForm);
		return formulario;
	}

	public void refrescarFC() throws Exception {
		try {
			final List<FormularioDTO> listFormulario = this.formularioService.obtenerTodosLosFormulariosSinRelaciones();
			ordenarListaPorFechaCreacion(listFormulario);
			this.listaFormularios = listFormulario;
			this.buscarFormulario.setText("");
			binder.loadComponent(this.listboxFC);
		} catch (final Exception e) {
			logger.error("Error al refrescar datos - ", e);
			throw e;
		}
	}

	public void setCantidadFormulariosLf(final Listfooter cantidadFormulariosLf) {
		this.cantidadFormulariosLf = cantidadFormulariosLf;
	}

	public void setListaFormularios(final List<FormularioDTO> listaFormularios) {
		this.listaFormularios = listaFormularios;
	}

	private void setListaNueva(final List<FormularioDTO> lista) {

		if (!lista.isEmpty()) {
			listboxFC.setModel(new BindingListModelList(lista, true));
			cantidadFormulariosLf.setStyle("color:green");
			labelFormulariosLf.setStyle("color:green");
			cantidadFormulariosLf.setLabel(String.valueOf(listboxFC.getItemCount()));
		} else {
			try {
        Messagebox.show(Labels.getLabel("admFcComposer..msgbox.noFormDTO"), Labels.getLabel("fc.informacion"),
            Messagebox.OK, Messagebox.INFORMATION);
			} catch (final Exception e) {
				logger.error("No se encontro el FormularioDTO" + e);
			}
			listboxFC.setModel(new BindingListModelList(listaFormularios, true));
		}
	}

	public void setSelectedFormulario(final FormularioDTO selectedFormulario) {
		this.selectedFormulario = selectedFormulario;
	}

	@SuppressWarnings("rawtypes")
	public Boolean validarComponentes(final Element form) {

		Boolean huboCambio = false;
		ComponenteDTO componenteExistente = null;

		try {
			final Element componentes = form.getChild("components");
			final List listComponentes = componentes.getChildren("component");

			this.listaComponentesExistentes = this.formularioService.obtenerTodosLosComponentes();
			for (int i = 0; i < listComponentes.size(); i++) {
				componenteExistente = null;
				final Element componente = (Element) listComponentes.get(i);
				for (final ComponenteDTO comp : this.listaComponentesExistentes) {
					if (comp.getNombre().equals(componente.getChildText("name"))) {
						componenteExistente = comp;
						break;
					}
				}
				if (componenteExistente != null) {
					if (componenteExistente.getTipoComponente().equals("COMBOBOX")
							|| componenteExistente.getTipoComponente().equals("COMPLEXBANDBOX")) {
						if (validarMultiValores(componente, componenteExistente)) {
							componenteService.guardarComponente(prepararComponente(componente, componenteExistente));
							huboCambio = true;
							this.huboCambioCombo = true;
							this.listaComponentesExistentes = this.formularioService.obtenerTodosLosComponentes();
						}
					}
				} else {
					this.componenteNuevo = componenteService.guardarComponente(prepararComponente(componente, null));
					huboCambio = true;
					this.listaComponentesExistentes = this.formularioService.obtenerTodosLosComponentes();
				}
			}
		} catch (final AccesoDatoException e) {
			logger.error("mensaje de error", e);
		} catch (final DynFormException e) {
			logger.error("mensaje de error", e);
		}
		return huboCambio;
	}

	public FormularioDTO validarComponentesFE(final Element form, final FormularioDTO formExistente,
			final List<FormTriggerDTO> listaTrigger) {

		FormularioDTO formActualizar = new FormularioDTO();

		if (!form.getAttributeValue("description").equals(formExistente.getDescripcion())) {
			validarComponentes(form);
			formActualizar = prepararFormulario(form, formExistente, listaTrigger);
		} else {
			if (validarComponentesFormulario(form, formExistente)) {
				validarComponentes(form);
				formActualizar = prepararFormulario(form, formExistente, listaTrigger);
			} else {
				if (validarComponentes(form)) {
					formActualizar = prepararFormulario(form, formExistente, listaTrigger);
				} else {
					formActualizar = null;
				}
			}
		}
		return formActualizar;
	}

	public FormularioDTO validarComponentesFN(final Element form, final List<FormTriggerDTO> listaTrigger) {

		validarComponentes(form);
		return prepararFormulario(form, null, listaTrigger);
	}

	@SuppressWarnings("rawtypes")
	public Boolean validarComponentesFormulario(final Element form, final FormularioDTO formulario) {

		Boolean huboCambio = false;

		final Element ComponentesForm = form.getChild("formcomponents");
		final List listaComponentesForm = ComponentesForm.getChildren("formcomponent");
		final Set<FormularioComponenteDTO> setComponentesExistentes = formulario.getFormularioComponentes();
		int contador = 0;

		for (int i = 0; i < listaComponentesForm.size(); i++) {
			final Element componenteForm = (Element) listaComponentesForm.get(i);
			final Element componentes = form.getChild("components");
			final List listacomponentes = componentes.getChildren("component");
			final Element componente = (Element) listacomponentes.get(i);

			for (final FormularioComponenteDTO compExiste : setComponentesExistentes) {
				if (compExiste.getNombre().equals(componenteForm.getChildText("name"))
						&& componente.getChildText("name").equals(compExiste.getComponente().getNombre())) {
					contador++;
					if (!componenteForm.getChild("label").getValue().equals(compExiste.getEtiqueta())
							|| !componenteForm.getChild("order").getValue().equals(compExiste.getOrden().toString())
							|| !componenteForm.getChild("obligatory").getValue()
									.equals(compExiste.getObligatorio().toString())
							|| !componenteForm.getChildText("searchrelevancy")
									.equals(compExiste.getRelevanciaBusqueda().toString())
							|| !componenteForm.getChild("hidden").getValue()
									.equals(compExiste.getOculto().toString())) {

						huboCambio = true;
						break;
					} else {
						huboCambio = validarTextoMultilinea(compExiste, componenteForm);
					}
				}
			}
			if (huboCambio) {
				break;
			}
		}
		if (contador < listaComponentesForm.size() || setComponentesExistentes.size() != listaComponentesForm.size()) {
			huboCambio = true;
		}
		return huboCambio;
	}

	/**
	 * Validar existencia del formulario si no existe, se lo crea. Si alguno de
	 * los componentes(tipo) de ese formulario no existe se lo crea. Si el
	 * formulario existe, se comprueba si fue modificado: si fue modificado se
	 * lo actualiza de lo contrario se informa que no fue generado ese
	 * formulario.
	 */
	@SuppressWarnings("rawtypes")
	public boolean validarFormularios(final Element formularios) {

		try {
			obtenerDatos();
			formulariosImportados = new LinkedHashMap<String, String>();
			FormularioDTO formExistente = null;
			final List listaFormularios = formularios.getChildren("form");
			final List<FormTriggerDTO> listaTriggers = new ArrayList<FormTriggerDTO>();
			for (int b = 0; b < listaFormularios.size(); b++) {
				listaTriggers.clear();
				final Element form = (Element) listaFormularios.get(b);
				this.componenteNuevo = null;
				this.huboCambioCombo = false;
				for (final FormularioDTO formE : listaFormulariosValidar) {
					if (form.getAttributeValue("name").equals(formE.getNombre())) {
						formExistente = formE;
						break;
					}
				}
				if (formExistente != null) {
					modificarFormTriggers(formExistente.getId(), listaTriggers);
					final FormularioDTO formActualizar = validarComponentesFE(form,
							formularioService.buscarFormularioPorNombre(formExistente.getNombre()), listaTriggers);

					if (formActualizar == null) {
						formulariosImportados.put(form.getAttributeValue("name"), FORMULARIO_NO_CREADO);
					} else {

						if (this.transaccionService.existeTransaccionParaFormulario(formExistente.getNombre())) {
							if (this.huboCambioCombo) {
								formulariosImportados.put(form.getAttributeValue("name"), COMBOBOX_ACTUALIZADO);
							} else {
								formulariosImportados.put(form.getAttributeValue("name"), FORMULARIO_CON_INSTANCIA);
							}
						} else {
							eliminarComponetesMultilinea(formActualizar, formExistente);
							this.formularioService.guardarFormulario(formActualizar);
							// modificarFormTriggers(formActualizar.getId(),
							// listaTriggers);
							modificarComponentesMultilinea(formActualizar);
							formulariosImportados.put(form.getAttributeValue("name"), FORMULARIO_ACTUALIZADO);
						}
					}
				} else {

					final FormularioDTO formulario = validarComponentesFN(form, listaTriggers);
					this.formularioService.guardarFormulario(formulario);
					guardarComponentesMultilinea(formulario);
					guardarFormTriggers(formulario, listaTriggers);
					formulariosImportados.put(form.getAttributeValue("name"), FORMULARIO_CREADO);
				}
			}
		} catch (final Exception e) {
			logger.error("mensaje de error" + e);
		}
		return true;
	}

	@SuppressWarnings("rawtypes")
	public boolean validarMultiValores(final Element componente, final ComponenteDTO componenteExistente) {

		final Set<ItemDTO> setItems = componenteExistente.getItems();
		int contador = 0;
		final Element multivalues = componente.getChild("multivalues");
		final List listaValores = multivalues.getChildren("value");

		if (setItems != null && setItems.size() > listaValores.size()) {
			return false;
		}

		for (int l = 0; l < listaValores.size(); l++) {
			final Element valor = (Element) listaValores.get(l);
			for (final ItemDTO item : setItems) {
				if (item.getValor().equals(valor.getChildText("value"))) {
					contador++;
					break;
				}
			}
		}
		return contador < listaValores.size() ? true : false;
	}

	public boolean validarTextoMultilinea(final FormularioComponenteDTO componente, final Element componenteForm) {
		if (componente.isMultilinea()) {
			if (componenteForm.getChildText("textoMultilinea") != null) {
				if (!componente.getTextoMultilinea().equals(componenteForm.getChildText("textoMultilinea"))) {
					return true;
				}
			}
		}
		return false;
	}

	public void onComplexComponent() {
		this.ccomplejosWindow = (Window) Executions.createComponents("/administradorFC/ccomplejoWindow.zul",
				this.self, null);
		this.ccomplejosWindow.setClosable(true);
		try {
			this.ccomplejosWindow.setPosition("center");
			this.ccomplejosWindow.doModal();
		} catch (final SuspendNotAllowedException e) {
			logger.error("mensaje de error" + e);
		}
	}

}

final class AdministradorFCComposerListener implements EventListener {
	private final AdministradorFCComposer composer;

	public AdministradorFCComposerListener(final AdministradorFCComposer comp) {
		this.composer = comp;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void onEvent(final Event event) throws Exception {

		if (event.getName().equals(Events.ON_NOTIFY)) {
			final Map<String, String> map = (Map<String, String>) event.getData();
			if (map != null) {
				final String accion = map.get("accion");
				if (accion.equals(AdministradorFCComposer.ACCION_ALTA)) {
					Messagebox.show(Labels.getLabel("fc.exitoNuevo.msg"), Labels.getLabel("fc.informacion"),
							Messagebox.OK, Messagebox.INFORMATION);
					this.composer.refrescarFC();
				} else if (accion.equals(AdministradorFCComposer.ACCION_BAJA)) {
					Messagebox.show(Labels.getLabel("fc.exitoBorrar.msg"), Labels.getLabel("fc.eliminado.title"),
							Messagebox.OK, Messagebox.INFORMATION);
					this.composer.refrescarFC();
				} else if (accion.equals(AdministradorFCComposer.ACCION_MODIF)) {
					this.composer.refrescarFC();
				}
			}
		}
	}
}
