package com.egoveris.ffdd.web.adm;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.DropEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.BindingListModelArray;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.egoveris.ffdd.base.service.IFormularioService;
import com.egoveris.ffdd.base.service.ITransaccionService;
import com.egoveris.ffdd.base.util.ConstantesConstraint;
import com.egoveris.ffdd.base.util.ConstantesValidarFormulario;
import com.egoveris.ffdd.model.exception.DynFormException;
import com.egoveris.ffdd.model.model.ComponenteDTO;
import com.egoveris.ffdd.model.model.CondicionDTO;
import com.egoveris.ffdd.model.model.ConstraintDTO;
import com.egoveris.ffdd.model.model.FormularioComponenteDTO;
import com.egoveris.ffdd.model.model.FormularioDTO;
import com.egoveris.ffdd.model.model.GrupoComponenteDTO;
import com.egoveris.ffdd.model.model.GrupoDTO;
import com.egoveris.ffdd.model.model.VisibilidadComponenteDTO;
import com.egoveris.ffdd.render.service.IComplexComponentService;
import com.egoveris.ffdd.render.service.IFormManager;
import com.egoveris.ffdd.render.service.IFormManagerFactory;


@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ABMFCComposer extends GenericForwardComposer<Component> {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory
			.getLogger(ABMFCComposer.class);

	public static final String MODO_ALTA_FC = "modoAltaFC";
	public static final String MODO_MODIFICACION_FC = "modoModificacionFC";
	public static final String MODO_CONSULTA_FC = "modoConsultaFC";
	public static final String MODO_CLONACION_FC = "modoClonacionFC";
	public static final String MODO_ALTA_CAJAS = "modoAltaCajas";
	public static final String TEXTBOX_MULTILINEA = "TextBox - Multilinea";
	public static final String MODO_ALTA_COMP = "modoAltaComp";

	// Beans
	@WireVariable("formularioService")
	private IFormularioService formularioService;
	@WireVariable("transaccionService")
	private ITransaccionService transaccionService;
	@WireVariable("zkFormManagerFactory")
	private IFormManagerFactory<IFormManager<Component>> formManagerFact;
	@WireVariable("complexComponentService")
	private IComplexComponentService complexComponentService;
	
	// Componentes ZUL
	private Window abmFC;
	private Window hiddenView;
	private Window constraintView;
	private Window restriccionWindow;
	private Window agregarTextoMultilinea;
	private Textbox nombreFormulario;
	private Textbox descripcionFormulario;
	private Listbox listboxComponentes;
	private Listbox listboxCajas;
	private Listbox listboxComponentesInstancia;
	private Listbox listboxComponentesCaja;
	private Combobox comboboxTipoComponentes;
	private Button eliminar;
	private Checkbox checkObligatorio;
	private Checkbox checkIndexado;
	private Checkbox checkOcultar;
	private Grid gridComponentes;
	private Grid gridCajas;

	// Atributos
	private String modo;
	private FormularioDTO formularioModificacionConsulta;
	private List<ComponenteDTO> listaComponente;
	private List<GrupoDTO> listaCajas;
	private List<FormularioComponenteDTO> listaFormularioComponente;
	private List<FormularioComponenteDTO> listaFormularioComponenteComplejo;
	private List<String> listaGrupoComponente;
	private ComponenteDTO selectedComponente;
	private FormularioComponenteDTO selectedFormComponent;
	private GrupoDTO selectedCaja;
	private Set<String> listaTipoComponente;
	private String loggedUser;
	private AnnotateDataBinder abmFCBinder;
	private List<ConstraintDTO> listConstraintDTO = new ArrayList<ConstraintDTO>();
	private List<VisibilidadComponenteDTO> listRestriccionDTO = new ArrayList<VisibilidadComponenteDTO>();

	public void doAfterCompose(Component comp) throws Exception {
		try {
			super.doAfterCompose(comp);
			comp.addEventListener(Events.ON_NOTIFY, new ABMFCComposerListener(
					this));
			Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
			asignarListeners();
			obtenerDatos();
			cargarListaConstraints();
			this.abmFCBinder = new AnnotateDataBinder(abmFC);
			this.abmFCBinder.loadAll();
		} catch (DynFormException e) {
			throw new DynFormException(
					Labels.getLabel("abmComboboxComposer.exception.errorAdm")
							+ Labels.getLabel("abmComboboxComposer.exception.intMinutos"), e);
		}
	}

	private void asignarListeners() {
		abmFC.addEventListener(Events.ON_CLOSE, new EventListener() {
			public void onEvent(Event event) throws Exception {
				onCancelar();
				event.stopPropagation();
			}
		});
	}

	private void obtenerDatos() throws DynFormException {

		this.loggedUser = Executions.getCurrent().getRemoteUser();
		this.modo = (String) Executions.getCurrent().getArg().get("modo");
		this.listaFormularioComponente = new ArrayList<FormularioComponenteDTO>();

		if ((!modo.equals(MODO_ALTA_FC)) && (!modo.equals(MODO_CONSULTA_FC))
				&& (!modo.equals(MODO_MODIFICACION_FC))
				&& (!modo.equals(MODO_CLONACION_FC))) {
			throw new DynFormException(
					Labels.getLabel("abmFcComposer.exception.modoIncorrecto")
							+ this.modo + Labels.getLabel("abmFcComposer.exception.noExiste"));
		}

		this.listaGrupoComponente = new ArrayList<String>();
		fillListaComponente();
		fillListaTipoComponente();
		fillListaCajas();
		fillDatosFormulario();
	}

	private void fillListaComponente() throws DynFormException {
		List<ComponenteDTO> lComponentes = this.formularioService
				.obtenerTodosLosComponentes();
		setListaComponente(lComponentes);
	}

	private void fillListaTipoComponente() {
		getListaComponente();
		Set<String> result = new TreeSet<String>();
		for (ComponenteDTO comp : getListaComponente()) {
			result.add(comp.getTipoComponente());
		}
		setListaTipoComponente(result);
	}

	private void fillListaCajas() throws DynFormException {
		List<GrupoDTO> lstCajas = this.formularioService
				.obtenerTodosLosGrupos();
		setListaCajas(lstCajas);
		Collections.sort(this.listaCajas, new Comparator<GrupoDTO>() {
			@Override
			public int compare(GrupoDTO object1, GrupoDTO object2) {
				return object1.getNombre().trim().toLowerCase()
						.compareTo(object2.getNombre().trim().toLowerCase());
			}
		});
	}

	private void cargarListaConstraints(){
		for(FormularioComponenteDTO formComp : this.listaFormularioComponente){
			if(!formComp.getConstraintList().isEmpty()){
				this.listConstraintDTO.addAll(formComp.getConstraintList());
			}
		}
		if(this.formularioModificacionConsulta != null){
			for(VisibilidadComponenteDTO compVisible : this.formularioModificacionConsulta.getListaComponentesOcultos()){
				this.listRestriccionDTO.add(compVisible);
			}			
		}
	}
	
	private void fillDatosFormulario() throws DynFormException {

		if (!modo.equals(MODO_ALTA_FC) && !modo.equals(MODO_CLONACION_FC)) {
			this.formularioModificacionConsulta = (FormularioDTO) Executions
					.getCurrent().getArg().get("formularioControlado");

			this.nombreFormulario.setValue(this.formularioModificacionConsulta
					.getNombre());
			this.descripcionFormulario
					.setValue(this.formularioModificacionConsulta
							.getDescripcion());
			setListaFormularioComponente(new ArrayList<FormularioComponenteDTO>(
					this.formularioModificacionConsulta
							.getFormularioComponentes()));

		} else {
			if (modo.equals(MODO_CLONACION_FC)) {
				this.formularioModificacionConsulta = (FormularioDTO) Executions
						.getCurrent().getArg().get("formularioControlado");

				this.nombreFormulario.setValue("");
				this.descripcionFormulario.setValue("");
				this.nombreFormulario.setReadonly(false);
				this.eliminar.setDisabled(true);
				setListaFormularioComponente(new ArrayList<FormularioComponenteDTO>(
						this.formularioModificacionConsulta
								.getFormularioComponentes()));

				for (FormularioComponenteDTO componente : listaFormularioComponente) {
					componente.setId(null);
				}
			}
		}
	}

	public void onGuardar() throws Exception {
		try {
			if (esFormularioValido()) {
				if (this.modo.equals(MODO_ALTA_FC)) {
					this.formularioService
							.guardarFormulario(prepararFormulario());

					Map<String, Object> map = new HashMap<String, Object>();
					map.put("accion", AdministradorFCComposer.ACCION_ALTA);
					Events.sendEvent(new Event(Events.ON_NOTIFY, this.self
							.getParent(), map));
					this.closeWindow();
				} else if (this.modo.equals(MODO_MODIFICACION_FC)) {
					this.formularioService
							.guardarFormulario(prepararFormulario());
					// Vuelvo a buscar el formulario para obtener todos los ids
					// necesarios
					this.formularioModificacionConsulta = formularioService
							.buscarFormularioPorNombre(formularioModificacionConsulta
									.getNombre());
					setListaFormularioComponente(new ArrayList<FormularioComponenteDTO>(
							this.formularioModificacionConsulta
									.getFormularioComponentes()));
					this.abmFCBinder.loadComponent(listboxComponentesInstancia);
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("accion", AdministradorFCComposer.ACCION_MODIF);
					Events.sendEvent(new Event(Events.ON_NOTIFY, this.self
							.getParent(), map));
					Messagebox.show(Labels.getLabel("abmCajaDatos.modifCaja"),
							Labels.getLabel("abmFC.exitoModif.title"),
							Messagebox.OK, Messagebox.INFORMATION);
				} else {
					if (this.modo.equals(MODO_CLONACION_FC)) {
						this.formularioModificacionConsulta = null;
						this.formularioService
								.guardarFormulario(prepararFormulario());
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("accion", AdministradorFCComposer.ACCION_ALTA);
						Events.sendEvent(new Event(Events.ON_NOTIFY, this.self
								.getParent(), map));
						this.closeWindow();
					}
				}

			}
		} catch (Exception e) {
			logger.error("Error en el alta del FC - " + e.getMessage());
			throw e;
		}
	}

	public void onChanging$buscarComponente(InputEvent e) {
		List<ComponenteDTO> model = new ArrayList<ComponenteDTO>();
		ListModel model1 = listboxComponentes.getModel();
		for (int i = 0; i < model1.getSize(); i++) {
			ComponenteDTO element = (ComponenteDTO) model1.getElementAt(i);
			if (element.getNombre().toLowerCase()
					.contains(e.getValue().toLowerCase())) {
				model.add(element);
			}
		}
		if (!e.getValue().equals("")) {
			listboxComponentes.setModel(new BindingListModelArray(model
					.toArray(), true));
		} else {
			if (comboboxTipoComponentes.getValue().equals("")) {
				model = new ArrayList<ComponenteDTO>();
				for (ComponenteDTO componenteDTO : getListaComponente()) {
					model.add(componenteDTO);
				}
				Collections.sort(model, new Comparator<ComponenteDTO>() {
					public int compare(ComponenteDTO c1, ComponenteDTO c2) {
						return c1.getNombre().compareToIgnoreCase(
								c2.getNombre());
					}
				});
				listboxComponentes.setModel(new BindingListModelArray(model
						.toArray(), true));
			} else {
				onSelect$comboboxTipoComponentes();
			}
		}
	}

	public void onCancelar() throws Exception {
		try {
			if (!this.modo.equals(MODO_CONSULTA_FC)) {
				Messagebox.show(Labels.getLabel("abmFC.salirPreg.msg"),
						Labels.getLabel("fc.confirmacion"), Messagebox.YES
								| Messagebox.NO, Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener() {
							public void onEvent(Event evt)
									throws InterruptedException {
								if ("onYes".equals(evt.getName())) {
									closeWindow();
								}
							}
						});
			} else {
				this.closeWindow();
			}
		} catch (Exception e) {
			logger.error("Error en el alta del FC - " + e.getMessage());
			throw e;
		}
	}

	public void onPrevisualizar() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String nombreFC = null;
		try {
			if (this.modo.equals(MODO_CONSULTA_FC)) {
				nombreFC = this.formularioModificacionConsulta.getNombre();
			} else {
				if (this.esFormularioValido()) {
					FormularioDTO formTemp = generarFormularioTemporal();
					nombreFC = formTemp.getNombre();
					this.formularioService.guardarFormulario(formTemp);
				}
			}
			if (nombreFC != null) {
				map.put("formularioControlado", nombreFC);
				map.put("modo", this.modo);
				if (this.hiddenView != null) {
					this.hiddenView.detach();
					this.hiddenView = (Window) Executions.createComponents(
							"/administradorFC/previsualizacionFC.zul", this.self, map);
					try {
						this.hiddenView.doModal();
					} catch (SuspendNotAllowedException snae) {
						logger.error(
								"Error al intentar mostrar la ventana de detalle - ",
								snae);
					}
				}
			}
		} catch (Exception e) {
			logger.error("Error al realizar la previsualizacion - "
					+ e.getMessage(), e);
			throw e;
		}
	}

	private FormularioDTO generarFormularioTemporal() {
		// NOMBRE TEMPORAL ids NULL
		FormularioDTO formTemp = new FormularioDTO();
		String nombreFC = "--TEMP"
				+ UUID.randomUUID().toString().substring(0, 5)
				+ this.nombreFormulario.getValue();
		formTemp.setNombre(nombreFC);
		formTemp.setDescripcion(this.descripcionFormulario.getValue());
		formTemp.setFormularioComponentes(new LinkedHashSet<FormularioComponenteDTO>());
		for (FormularioComponenteDTO formCompDto : getListaFormularioComponente()) {
			FormularioComponenteDTO fcDTO = new FormularioComponenteDTO();
			fcDTO.setComponente(formCompDto.getComponente());
			fcDTO.setOrden(formCompDto.getOrden());
			fcDTO.setEtiqueta(formCompDto.getEtiqueta());
			fcDTO.setNombre(formCompDto.getNombre());
			fcDTO.setObligatorio(formCompDto.getObligatorio());
			fcDTO.setRelevanciaBusqueda(formCompDto.getRelevanciaBusqueda());
			fcDTO.setTextoMultilinea(formCompDto.getTextoMultilinea() != null ? formCompDto
					.getTextoMultilinea() : null);
			fcDTO.setConstraintList(formCompDto.getConstraintList());
			fcDTO.setOculto(formCompDto.getOculto());
			formTemp.getFormularioComponentes().add(fcDTO);
		}
		formTemp.setListaComponentesOcultos(this.listRestriccionDTO);
		return formTemp;
	}

	public void onEliminar() throws InterruptedException, DynFormException {
		try {
			if (this.transaccionService
					.existeTransaccionParaFormulario(this.formularioModificacionConsulta
							.getNombre())) {
				Messagebox.show(Labels.getLabel("fc.noPuedeBorrar.msg"),
						Labels.getLabel("fc.eli.title"), Messagebox.OK,
						Messagebox.EXCLAMATION);
			} else {
				Messagebox.show(Labels.getLabel("fc.estaSeguroBorrar.msg"),
						Labels.getLabel("fc.eli.title"), Messagebox.YES
								| Messagebox.NO, Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener() {
							public void onEvent(Event evt) throws Exception {
								if ("onYes".equals(evt.getName())) {
									eliminarFormulario();
								}
							}
						});
			}
		} catch (DynFormException e) {
			logger.error(
					"Error al intentar eliminar el formulario controlado - ",
					e.getMessage());
			Messagebox.show(Labels.getLabel("fc.noPuedeBorrarAdm.msg"),
					Labels.getLabel("fc.eli.title"), Messagebox.OK,
					Messagebox.EXCLAMATION);
			throw e;
		}
	}

	public void onSelect$comboboxTipoComponentes() {

		List<ComponenteDTO> model = new ArrayList<ComponenteDTO>();

		for (ComponenteDTO componenteDTO : getListaComponente()) {
			if (comboboxTipoComponentes.getValue().equals(
					componenteDTO.getTipoComponente())) {
				model.add(componenteDTO);
			}
		}

		Collections.sort(model, new Comparator<ComponenteDTO>() {
			@Override
			public int compare(ComponenteDTO c1, ComponenteDTO c2) {
				return c1.getNombre().compareToIgnoreCase(c2.getNombre());
			}
		});

		listboxComponentes.setModel(new BindingListModelArray(model.toArray(),
				true));
	}

	private void eliminarFormulario() throws DynFormException {
		try {
			
			for(FormularioComponenteDTO componente : this.formularioModificacionConsulta.getFormularioComponentes()){
				if(componente.isMultilinea()){
					this.formularioService.eliminarMultilinea(componente.getId());
				}
			}
			this.formularioService
					.eliminarFormulario(this.formularioModificacionConsulta
							.getNombre());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("accion", AdministradorFCComposer.ACCION_BAJA);
			Events.sendEvent(new Event(Events.ON_NOTIFY, this.self.getParent(),
					map));
			this.closeWindow();
		} catch (DynFormException e) {
			logger.error(
					"Error al intentar eliminar el formulario controlado  ",
					e.getMessage());
			throw e;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private FormularioDTO prepararFormulario() throws DynFormException {
		FormularioDTO formularioDTO = new FormularioDTO();
		formularioDTO
				.setId(this.formularioModificacionConsulta != null ? this.formularioModificacionConsulta
						.getId() : null);
		formularioDTO.setNombre(this.nombreFormulario.getValue());
		formularioDTO.setDescripcion(this.descripcionFormulario.getValue());
		formularioDTO
				.setUsuarioCreador(this.formularioModificacionConsulta != null ? this.formularioModificacionConsulta
						.getUsuarioCreador() : loggedUser);
		formularioDTO
				.setFechaCreacion(this.formularioModificacionConsulta != null ? this.formularioModificacionConsulta
						.getFechaCreacion() : (Date) Calendar.getInstance()
						.getTime());
		formularioDTO.setUsuarioModificador(loggedUser);
		formularioDTO.setFechaModificacion((Date) Calendar.getInstance()
				.getTime());
		formularioDTO.setFormularioComponentes(new LinkedHashSet(
				getListaFormularioComponente()));
		formularioDTO.setListaComponentesOcultos(this.formularioModificacionConsulta != null ? this.formularioModificacionConsulta.getListaComponentesOcultos():this.listRestriccionDTO);

		if (this.formularioModificacionConsulta != null) {
			Set<FormularioComponenteDTO> setComponentesOriginal = this.formularioModificacionConsulta
					.getFormularioComponentes();
			Set<FormularioComponenteDTO> setComponentes = formularioDTO
					.getFormularioComponentes();
			for (FormularioComponenteDTO componente : setComponentes) {
				for (FormularioComponenteDTO componenteOriginal : setComponentesOriginal) {
					if (componente.getNombre().equals(
							componenteOriginal.getNombre())) {
						componente.setId(componenteOriginal.getId());
						break;
					}
				}
			}
		}
		if (this.modo.equals(MODO_MODIFICACION_FC)) {
			Boolean existe = false;
			for (FormularioComponenteDTO componenteViejo : this.formularioModificacionConsulta
					.getFormularioComponentes()) {
				for (FormularioComponenteDTO componenteNuevo : getListaFormularioComponente()) {
					if (componenteViejo.getId().equals(componenteNuevo.getId())) {
						existe = true;
					}
				}
				if (!existe
						&& componenteViejo.isMultilinea()) {
					this.formularioService.eliminarMultilinea(componenteViejo
							.getId());
				}
				existe = false;
			}
		}		

		return formularioDTO;
	}

	private void cargarConstraintListaFormularioComponenteDTO() {
		List<ConstraintDTO> constraintComponetList =null;
		for(FormularioComponenteDTO formComponent : this.listaFormularioComponente){
			constraintComponetList = new ArrayList<ConstraintDTO>();
			for(ConstraintDTO constraint : this.listConstraintDTO){
				if(formComponent.getNombre().equals(constraint.getNombreComponente())){
					constraintComponetList.add(constraint);
				}
			}
			formComponent.setConstraintList(constraintComponetList);			
		}
	}
				
	private boolean esFormularioValido() throws InterruptedException,
			WrongValueException, DynFormException {

		if (StringUtils.isEmpty(this.nombreFormulario.getValue().trim())) {
			this.nombreFormulario.setErrorMessage(Labels
					.getLabel("abmFC.nomForm.vali"));
			return false;
		}

		if (this.modo.equals(MODO_ALTA_FC)
				|| this.modo.equals(MODO_CLONACION_FC)) {
			if (this.formularioService
					.buscarFormularioPorNombre(this.nombreFormulario.getValue()) != null) {
				this.nombreFormulario.setErrorMessage(Labels
						.getLabel("abmFC.yaExiste.vali"));
				return false;
			}
		}

		if (StringUtils.isEmpty(this.descripcionFormulario.getValue())) {
			this.descripcionFormulario.setErrorMessage(Labels
					.getLabel("abmFC.desForm.vali"));
			return false;
		}

		if (this.listboxComponentesInstancia.getItems() == null
				|| this.listboxComponentesInstancia.getItems().isEmpty()) {

			Messagebox.show(Labels.getLabel("abmFC.arrastreAlgo.msg"),
					Labels.getLabel("fc.export.atencion.title"), Messagebox.OK,
					Messagebox.ERROR);
			return false;
		}

		Map<String, Textbox> map = new HashMap<>();
		Pattern p = Pattern.compile("[^a-zA-Z0-9]");
		Pattern pnum = Pattern.compile("[^a-z]");
		Pattern pletra = Pattern.compile("[(?=.*[a-z])]");
		for (Object obj : this.listboxComponentesInstancia.getItems()) {
			if (obj instanceof Listitem) {
				Listitem li = (Listitem) obj;
				// Obtengo el segundo hijo del array (posicion 1 - celda con
				// Textbox)
				// Obtengo el primer y unico hijo de la celda (posicion 0 -
				// Textbox)
				Listcell lc = (Listcell) li.getChildren().get(2);
				Textbox tb = (Textbox) lc.getChildren().get(0);
				if (tb.getValue() == null || tb.getValue().equals("")) {
					tb.setErrorMessage(Labels.getLabel("abmFC.nomComp.vali"));
					return false;
				}
				if (tb.getValue().length() > 30) {
					tb.setErrorMessage(Labels.getLabel("abmFC.nomLargo.vali"));
					return false;
				}

				Matcher m = p.matcher(tb.getValue());
				while (m.find()) {
					tb.setErrorMessage(Labels.getLabel("abmFC.nomCaract.vali"));
					return false;
				}

				m = pnum.matcher(tb.getValue());
				while (m.find()) {
					m = pletra.matcher(tb.getValue());
					if (m.find()) {
						break;
					} else {
						tb.setErrorMessage(Labels
								.getLabel("abmFC.nomCaract.vali.num"));
						return false;
					}
				}

				if (map.get(tb.getValue()) == null) {
					map.put(tb.getValue(), tb);
				} else {
					tb.setErrorMessage(Labels
							.getLabel("abmFC.nombIguales.vali"));
					return false;
				}
			}
		}

		for (Object obj : this.listboxComponentesInstancia.getItems()) {
			if (obj instanceof Listitem) {
				Listitem li = (Listitem) obj;

				Listcell celda = (Listcell) li.getChildren().get(1);
				if (!celda.getLabel().equals(TEXTBOX_MULTILINEA)) {

					Listcell lc = (Listcell) li.getChildren().get(2);
					Textbox tb = (Textbox) lc.getChildren().get(0);
					if (tb.getValue() == null || tb.getValue().equals("")) {
						tb.setErrorMessage(Labels
								.getLabel("abmFC.etiVacia.vali"));
						return false;
					}
				} else {
					if (((FormularioComponenteDTO) li.getValue())
							.getTextoMultilinea() == null
							|| (((FormularioComponenteDTO) li.getValue())
									.getTextoMultilinea() != null && ((FormularioComponenteDTO) li
									.getValue()).getTextoMultilinea().trim()
									.isEmpty())) {
						Listcell lcell = (Listcell) li.getChildren().get(3);
						Hbox hb = (Hbox) lcell.getChildren().get(1);
						throw new WrongValueException(
								hb,
								Labels.getLabel("abmFC.textoMultilineaVacio.vali"));
					}
				}
			}
		}

		Boolean separadorRepetidor = false;
		Boolean componentesVarios = false;
		for (FormularioComponenteDTO componente : listaFormularioComponente) {
			if (componente.getComponente().getNombre()
					.equals("Separator - Repetidor")) {
				if (separadorRepetidor && !componentesVarios) {
					break;
				} else {
					separadorRepetidor = true;
				}
			} else {
				if (!componente.getComponente().getNombre()
						.equals("Separator - Generico")
						&& !componente.getComponente().getNombre()
								.equals("Separator - Interno")) {
					componentesVarios = true;
					separadorRepetidor = false;
				}
			}
		}
		if (separadorRepetidor) {
			Messagebox.show(Labels.getLabel("abmFC.separadorRepetidor.vali"),
					Labels.getLabel("fc.export.atencion.title"), Messagebox.OK,
					Messagebox.EXCLAMATION);
			return false;
		}
		if (!componentesVarios) {
			Messagebox.show(Labels.getLabel("abmFC.separadores.vali"),
					Labels.getLabel("fc.export.atencion.title"), Messagebox.OK,
					Messagebox.EXCLAMATION);
			return false;
		}
		
		boolean flag = false;
		for(String componentesUsados: armarListaComponentesUtilizados()){
			flag = false;
			for(FormularioComponenteDTO formComp : this.listaFormularioComponente){
				if(componentesUsados.equals(formComp.getNombre())){
					flag = true;
					break;
				}
			}
			if(!flag && !this.listConstraintDTO.isEmpty()){
				Messagebox
				.show(Labels.getLabel("abmFcComposer.msgbox.constNoCorresponden"),
						Labels.getLabel("fc.export.atencion.title"),
						Messagebox.OK, Messagebox.EXCLAMATION);
				return false;
			}
		}
		
		boolean isOK = false;
		for (String componentesUsadosHidden : armarListaComponentesOcultosUtilizados()) {
			isOK = false;
			for (FormularioComponenteDTO formComp : this.listaFormularioComponente) {
				//los componentes complejos se lo toma como uno solo
				/*if(complexComponentService.hasComponentDTO(formComp.getComponente().getNombreXml())){
					Map<String, String> mapCComplejoFields = this.formManagerFact.create(formularioModificacionConsulta)
							.getComponentMapTypes();
					// logica componentesComplejos
				
					for (Map.Entry<String, String> entry : mapCComplejoFields.entrySet()) {
						if (componentesUsadosHidden.equals(entry.getKey())) {
							isOK = true;
							break;
						}
					}
				}*/
				if (componentesUsadosHidden.equals(formComp.getNombre())) {
					isOK = true;
					break;
				}
			}
			if (!isOK && !this.listRestriccionDTO.isEmpty()) {
				Messagebox
						.show(Labels.getLabel("abmFcComposer.msgbox.compOcultarNoCorresponden"),
								Labels.getLabel("fc.export.atencion.title"),
								Messagebox.OK, Messagebox.EXCLAMATION);
				return false;
			}
		}		
		return true;
	}
	
	private Set<String> armarListaComponentesUtilizados(){
		Set<String> listaComponentes = new HashSet<String>();
		for(ConstraintDTO constraint : this.listConstraintDTO){
			listaComponentes.add(constraint.getNombreComponente());
			for(CondicionDTO condicion: constraint.getCondiciones()){
				listaComponentes.add(condicion.getNombreComponente());
				if(condicion.getNombreCompComparacion() != null){
					listaComponentes.add(condicion.getNombreCompComparacion());
				}
			}
		}
		return listaComponentes;
	}
	
	private Set<String> armarListaComponentesOcultosUtilizados(){
		Set<String> listaComponentes = new HashSet<String>();
		for(VisibilidadComponenteDTO visibilidadComponenteDTO : this.listRestriccionDTO){
			for(String nombre : visibilidadComponenteDTO.getComponentesOcultos()){
				listaComponentes.add(nombre);
			}
			for(CondicionDTO condicion: visibilidadComponenteDTO.getCondiciones()){
				listaComponentes.add(condicion.getNombreComponente());
				if(condicion.getNombreCompComparacion() != null){
					listaComponentes.add(condicion.getNombreCompComparacion());
				}
			}
		}
		return listaComponentes;
	}
	
	
	private boolean validarNombreComponentes() {
		Map<String, Textbox> map = new HashMap<String, Textbox>();
		Pattern p = Pattern.compile("[^a-z0-9_]");
		Pattern pnum = Pattern.compile("[^a-z_]");
		Pattern pletra = Pattern.compile("[(?=.*[a-z])]");
		for (Object obj : this.listboxComponentesInstancia.getItems()) {
			if (obj instanceof Listitem) {
				Listitem li = (Listitem) obj;
				// Obtengo el segundo hijo del array (posicion 1 - celda con
				// Textbox)
				// Obtengo el primer y unico hijo de la celda (posicion 0 -
				// Textbox)
				Listcell lc = (Listcell) li.getChildren().get(2);
				Textbox tb = (Textbox) lc.getChildren().get(0);
				if (tb.getValue() == null || tb.getValue().equals("")) {
					tb.setErrorMessage(Labels.getLabel("abmFC.nomComp.vali"));
					return false;
				}
				if (tb.getValue().length() > 30) {
					tb.setErrorMessage(Labels.getLabel("abmFC.nomLargo.vali"));
					return false;
				}

				Matcher m = p.matcher(tb.getValue());
				while (m.find()) {
					tb.setErrorMessage(Labels.getLabel("abmFC.nomCaract.vali"));
					return false;
				}

				m = pnum.matcher(tb.getValue());
				while (m.find()) {
					m = pletra.matcher(tb.getValue());
					if (m.find()) {
						break;
					} else {
						tb.setErrorMessage(Labels
								.getLabel("abmFC.nomCaract.vali.num"));
						return false;
					}
				}

				if (map.get(tb.getValue()) == null) {
					map.put(tb.getValue(), tb);
				} else {
					tb.setErrorMessage(Labels
							.getLabel("abmFC.nombIguales.vali"));
					return false;
				}
			}
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	public void onDropItem(ForwardEvent fe) throws InterruptedException {
		DropEvent event = (DropEvent) fe.getOrigin();
		ListModelList model = (ListModelList) listboxComponentesInstancia
				.getModel();
		final Listitem draggedComp = (Listitem) event.getDragged();

		if (draggedComp.getParent().getId().equals(listboxComponentes.getId())
				|| draggedComp.getParent().getId()
						.equals(listboxComponentesInstancia.getId())) {
			FormularioComponenteDTO fc = null;
			if (draggedComp.getParent() == listboxComponentes) {
				fc = new FormularioComponenteDTO();
				fc.setComponente((ComponenteDTO) draggedComp.getValue());
			} else if (draggedComp.getParent() == listboxComponentesInstancia) {
				fc = (FormularioComponenteDTO) draggedComp.getValue();
			} else {
				throw new AssertionError(Labels.getLabel("abmFcComposer.msgbox.errorInterno") +  "onDropItem");
			}

			if (fc.getComponente().getTipoComponente().equals("CHECKBOX")) {
				fc.setObligatorio(true);
				this.checkObligatorio.setDisabled(true);					
			} 			
			if(fc.isMultilinea()){
				this.checkObligatorio.setDisabled(true);
				this.checkIndexado.setDisabled(true);
			}
			if (fc.getComponente().getTipoComponente()
					.equals(ConstantesValidarFormulario.USIGBOX)
					|| fc.getComponente().getTipoComponente()
							.equals(ConstantesValidarFormulario.NROSADEBOX) || fc.getComponente().getTipoComponente().equals(ConstantesValidarFormulario.SEPARATOR)) {
				this.checkOcultar.setDisabled(true);
			}
			if (event.getTarget() instanceof Listbox
					&& draggedComp.getParent() == listboxComponentes) {
				fc.setOrden(model.size());
				model.add(model.size(), fc);
			} else if (event.getTarget() instanceof Listitem) {
				model.add(((Listitem) event.getTarget()).getIndex(), fc);
				if (draggedComp.getParent() == listboxComponentesInstancia) {
					model.remove(draggedComp.getIndex());
				}
			} else if (event.getTarget() instanceof Button
					&& draggedComp.getParent() == listboxComponentesInstancia) {
				model.remove(draggedComp.getIndex());
			}
			setListaFormularioComponente(ordenarModel(model.getInnerList()));
			abmFCBinder.loadComponent(listboxComponentesInstancia);
			this.checkObligatorio.setDisabled(false);
			this.checkIndexado.setDisabled(false);
			this.checkOcultar.setDisabled(false);
		} else if (draggedComp.getParent().getId().equals(listboxCajas.getId())) {
			if (!(event.getTarget() instanceof Button)) {
				mostrarComponenteCajas(
						draggedComp,
						event.getTarget() instanceof Listitem ? ((Listitem) event
								.getTarget()).getIndex() : -1);
			}
		}
	}
	
	private List<FormularioComponenteDTO> ordenarModel(List<FormularioComponenteDTO> lista) {
		for (int i = 0; i < lista.size(); i++) {
			((FormularioComponenteDTO)lista.get(i)).setOrden(i);
		}
		return lista;
	}
	
	public void onAgregarTextoMultilinea(ForwardEvent event)
			throws InterruptedException, WrongValueException, DynFormException {

		Listitem itemComponente = (Listitem) event.getOrigin().getTarget()
				.getParent().getParent();
		Textbox tb = (Textbox) ((Listcell) (itemComponente.getChildren().get(2)))
				.getFirstChild();

		if (!tb.getValue().trim().isEmpty()) {

			this.selectedFormComponent.setNombre(tb.getValue());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("TEXTOMULTILINEA",
					this.selectedFormComponent.getTextoMultilinea() != null ? this.selectedFormComponent
							.getTextoMultilinea() : "");
			this.agregarTextoMultilinea = (Window) Executions.createComponents(
					"/administradorFC/redaccionTextoMultilineaFC.zul", this.self, map);
			this.agregarTextoMultilinea.setClosable(true);
			try {
				this.agregarTextoMultilinea.setPosition("center");
				this.agregarTextoMultilinea.doModal();
			} catch (SuspendNotAllowedException e) {
				logger.error("mensaje de error" + e);
			}
		} else {
			tb.setErrorMessage(Labels.getLabel("abmFC.nomComp.vali"));
		}
	}

	@SuppressWarnings("unchecked")
	public void onCajaSelected() {
		ListModelList model = (ListModelList) this.listboxComponentesCaja
				.getModel();
		if (this.listaGrupoComponente.size() != 0) {
			model.removeAll(this.listaGrupoComponente);
		}

		GrupoDTO grupoComponente = (GrupoDTO) selectedCaja;
		Set<GrupoComponenteDTO> listaComponentes = grupoComponente
				.getGrupoComponentes();
		int indexItemGrupo = 0;
		for (GrupoComponenteDTO componente : listaComponentes) {
			model.add(indexItemGrupo, componente.getEtiqueta());
			++indexItemGrupo;
			setListaGrupoComponente(model.getInnerList());
		}
	}

	@SuppressWarnings("unchecked")
	public void cargarListaCajas(List<FormularioComponenteDTO> listaFormulario,
			int index) {
		ListModelList model = (ListModelList) listboxComponentesInstancia
				.getModel();
		int indice = index != -1 ? index : model.size();
		for (FormularioComponenteDTO componente : listaFormulario) {
			model.add(indice, componente);
			indice = indice + 1;

		}
		setListaFormularioComponente(model.getInnerList());
	}

	public void mostrarComponenteCajas(Listitem draggedComp, int index) {

		GrupoDTO grupoComponente = (GrupoDTO) draggedComp.getValue();
		Set<GrupoComponenteDTO> listaComponentes = grupoComponente
				.getGrupoComponentes();
		FormularioComponenteDTO formulario;
		List<FormularioComponenteDTO> listaFormulario = new ArrayList<FormularioComponenteDTO>();
		for (GrupoComponenteDTO componente : listaComponentes) {
			formulario = new FormularioComponenteDTO();
			formulario.setEtiqueta(componente.getEtiqueta());
			formulario.setNombre(componente.getNombre());
			formulario.setComponente(componente.getComponente());
			formulario.setObligatorio(false);
			formulario.setOrden(componente.getOrden());
			formulario.setRelevanciaBusqueda(0);
			listaFormulario.add(formulario);
		}
		cargarListaCajas(listaFormulario, index);
	}

	public void onVerCajas() {
		this.listboxComponentes.setVisible(false);
		this.listboxCajas.setVisible(true);
		this.gridComponentes.setVisible(false);
		this.gridCajas.setVisible(true);
	}

	public void onSelectCombo() {
		this.listboxComponentes.setVisible(true);
		this.listboxCajas.setVisible(false);
		this.gridComponentes.setVisible(true);
		this.gridCajas.setVisible(false);
	}

	/**
	 * Evento al ejecutar el boton agregar Constraint, genera la ventana
	 * ABMFCConstraint
	 * 
	 * @throws InterruptedException
	 */
	public void onConstraint() throws InterruptedException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<FormularioComponenteDTO> listaFormComponent = removerComponentesNoAdmitidosConstraint();
		List<FormularioComponenteDTO> listaConstraintsExistentes = obtenerFormCompConstraint(listaFormComponent);
		map.put("listaComponentesConstraint", listaConstraintsExistentes);
		map.put("listComponentesSeleccionados", listaFormComponent);
		if(this.formularioModificacionConsulta != null){
			map.put("formulario", this.formularioModificacionConsulta);
		}
		if(!this.listaFormularioComponente.isEmpty()){
			if (validarNombreComponentes()) {
				map.put("modo", this.modo);
				cargarVentanaConstraint(map, "/administradorFC/abmFCConstraintEdit.zul");
			}
		}else{
			Messagebox.show(Labels.getLabel("abmFC.arrastreAlgo.msg"),
					Labels.getLabel("fc.export.atencion.title"), Messagebox.OK,
					Messagebox.INFORMATION);
		}
	}
	
	public void onRestriccion() throws InterruptedException{
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<FormularioComponenteDTO> listaFormComponent = removerComponentesNoAdmitidosMostrarOcultar();
		List<VisibilidadComponenteDTO> listaRestricciones = null ;
		if(this.formularioModificacionConsulta != null){
			listaRestricciones = this.formularioModificacionConsulta.getListaComponentesOcultos();
			map.put("formulario", this.formularioModificacionConsulta);
		}else{
			listaRestricciones = this.listRestriccionDTO;
		}
		map.put("listaRestriccion", listaRestricciones);
		map.put("listComponentesSeleccionados", listaFormComponent);
		if(!this.listaFormularioComponente.isEmpty()){
			if (validarNombreComponentes()) {
				map.put("modo", this.modo);
				this.restriccionWindow = (Window) Executions.createComponents("/administradorFC/abmFCRestricciones.zul",
						this.self, map);
				this.restriccionWindow.setClosable(true);
				try {
					this.restriccionWindow.setPosition("center");
					this.restriccionWindow.doModal();
				} catch (SuspendNotAllowedException e) {
					logger.error("mensaje de error" + e , e);
				}
			}
		}else{
			Messagebox.show(Labels.getLabel("abmFC.arrastreAlgo.msg"),
					Labels.getLabel("fc.export.atencion.title"), Messagebox.OK,
					Messagebox.INFORMATION);
		}
	}

	private void cargarVentanaConstraint(HashMap<String, Object> map,
			String ventana) throws InterruptedException {
		this.constraintView = (Window) Executions.createComponents(ventana,
				this.self, map);
		this.constraintView.setClosable(true);
		try {
			this.constraintView.setPosition("center");
			this.constraintView.doModal();
		} catch (SuspendNotAllowedException e) {
			logger.error("mensaje de error" + e , e);
		}
	}

	private List<FormularioComponenteDTO> obtenerFormCompConstraint (
			List<FormularioComponenteDTO> listaFormCompCompleta) {
		List<FormularioComponenteDTO> listaConstraint = new ArrayList<FormularioComponenteDTO>();
		for (FormularioComponenteDTO formComp : listaFormCompCompleta) {
			if (formComp.getConstraintList() != null && !formComp.getConstraintList().isEmpty()) {
				listaConstraint.add(formComp);
			}
		}
		return listaConstraint;
	}

	private List<FormularioComponenteDTO> removerComponentesNoAdmitidosConstraint() {
		List<FormularioComponenteDTO> lstComponentes = new ArrayList<FormularioComponenteDTO>();
		for (FormularioComponenteDTO form : this.listaFormularioComponente) {
			if (!form.getComponente().getTipoComponente().equals(ConstantesValidarFormulario.SEPARATOR)
					&& !form.getComponente().getTipoComponente().equals(ConstantesValidarFormulario.USIGBOX)
					&& !form.getComponente().getTipoComponente().equals(ConstantesValidarFormulario.NROSADEBOX)
					&& !form.isMultilinea()) {
				lstComponentes.add(form);
			}
		}
		return lstComponentes;
	}
	
	private List<FormularioComponenteDTO> removerComponentesNoAdmitidosMostrarOcultar() {
		List<FormularioComponenteDTO> lstComponentes = new ArrayList<FormularioComponenteDTO>();
		for (FormularioComponenteDTO form : this.listaFormularioComponente) {
			if (!form.getComponente().getTipoComponente().equals(ConstantesValidarFormulario.SEPARATOR)
					&& !form.getComponente().getTipoComponente().equals(ConstantesValidarFormulario.USIGBOX)
					&& !form.getComponente().getTipoComponente().equals(ConstantesValidarFormulario.NROSADEBOX)){
				lstComponentes.add(form);
			}
		}
		return lstComponentes;
	}

	public void closeWindow() {
		this.abmFC.onClose();
	}

	public List<ComponenteDTO> getListaComponente() {
		return listaComponente;
	}

	public void setListaComponente(List<ComponenteDTO> listaComponente) {
		this.listaComponente = listaComponente;
	}

	public ComponenteDTO getSelectedComponente() {
		return selectedComponente;
	}

	public void setSelectedComponente(ComponenteDTO selectedComponente) {
		this.selectedComponente = selectedComponente;
	}

	public List<FormularioComponenteDTO> getListaFormularioComponente() {
		return listaFormularioComponente;
	}

	public void setListaFormularioComponente(
			List<FormularioComponenteDTO> listaFormularioComponente) {
		this.listaFormularioComponente = listaFormularioComponente;
	}

	public Set<String> getListaTipoComponente() {
		return listaTipoComponente;
	}

	public void setListaTipoComponente(Set<String> listaTipoComponente) {
		this.listaTipoComponente = listaTipoComponente;
	}

	public String getModo() {
		return modo;
	}

	public void setModo(String modo) {
		this.modo = modo;
	}

	public Listbox getListboxCajas() {
		return listboxCajas;
	}

	public void setListboxCajas(Listbox listboxCajas) {
		this.listboxCajas = listboxCajas;
	}

	public GrupoDTO getSelectedCaja() {
		return selectedCaja;
	}

	public void setSelectedCaja(GrupoDTO selectedCaja) {
		this.selectedCaja = selectedCaja;
	}

	public void setListaCajas(List<GrupoDTO> listaCajas) {
		this.listaCajas = listaCajas;
	}

	public List<GrupoDTO> getListaCajas() {
		return listaCajas;
	}

	public void setGridComponentes(Grid gridComponentes) {
		this.gridComponentes = gridComponentes;
	}

	public Grid getGridComponentes() {
		return gridComponentes;
	}

	public void setGridCajas(Grid gridCajas) {
		this.gridCajas = gridCajas;
	}

	public Grid getGridCajas() {
		return gridCajas;
	}

	public List<String> getListaGrupoComponente() {
		return listaGrupoComponente;
	}

	public void setListaGrupoComponente(List<String> listaGrupoComponente) {
		this.listaGrupoComponente = listaGrupoComponente;
	}

	public FormularioComponenteDTO getSelectedFormComponent() {
		return selectedFormComponent;
	}

	public void setSelectedFormComponent(
			FormularioComponenteDTO selectedFormComponent) {
		this.selectedFormComponent = selectedFormComponent;
	}

	private void guardarTextoMultilinea(String texto) {
		this.selectedFormComponent.setTextoMultilinea(texto);
		this.selectedFormComponent.setEtiqueta(this.selectedFormComponent
				.getNombre());
	}

	private void inicializarConstraintsDTOFormulario(
			List<ConstraintDTO> listConstraintDTO) {
		this.setListConstraintDTO(listConstraintDTO);
		this.cargarConstraintListaFormularioComponenteDTO();
	}

	private void inicializarRestriccionDTOFormulario(
			List<VisibilidadComponenteDTO> listRestriccionDto) {
		this.setListRestriccionDTO(listRestriccionDto);
		if(this.formularioModificacionConsulta != null){
			this.formularioModificacionConsulta.setListaComponentesOcultos(listRestriccionDto);
		}
	}
	
	public List<VisibilidadComponenteDTO> getListRestriccionDTO() {
		return listRestriccionDTO;
	}

	public void setListRestriccionDTO(List<VisibilidadComponenteDTO> listRestriccionDTO) {
		this.listRestriccionDTO = listRestriccionDTO;
	}

	public List<ConstraintDTO> getListConstraintDTO() {
		return listConstraintDTO;
	}

	public void setListConstraintDTO(List<ConstraintDTO> listConstraintDTO) {
		this.listConstraintDTO = listConstraintDTO;
	}

	final class ABMFCComposerListener implements EventListener {

		private ABMFCComposer composer;

		public ABMFCComposerListener(ABMFCComposer comp) {
			this.setComposer(comp);
		}

		@SuppressWarnings("unchecked")
		public void onEvent(Event event) throws Exception {
			if (event.getName().equals(Events.ON_NOTIFY)) {
				Map<String, Object> map = (Map<String, Object>) event.getData();
				if (map != null) {
					String accion = (String) map.get("accion");
					if (accion!=null && (accion
							.equals(ConstantesConstraint.CONSTRAINTS_COMPONENTES_GUARDAR)
							|| accion.equals(ConstantesConstraint.CONSTRAINT_COMPONENTES_SALIR_SIN_GUARDAR))) {
						inicializarConstraintsDTOFormulario((List<ConstraintDTO>) map
								.get("listConstraintDTO"));
					}else if(accion!=null && (accion
							.equals(ConstantesConstraint.RESTRICCION_COMPONENTES_GUARDAR)
							|| accion.equals(ConstantesConstraint.RESTRICCION_COMPONENTES_SALIR_SIN_GUARDAR))){
						inicializarRestriccionDTOFormulario((List<VisibilidadComponenteDTO>) map
								.get("listRestriccionDTO"));
					}else {
						guardarTextoMultilinea((String) map
								.get("textoMultilinea"));
					}
				}
			}
		}

		public ABMFCComposer getComposer() {
			return composer;
		}

		public void setComposer(ABMFCComposer composer) {
			this.composer = composer;
		}

	}
	
}