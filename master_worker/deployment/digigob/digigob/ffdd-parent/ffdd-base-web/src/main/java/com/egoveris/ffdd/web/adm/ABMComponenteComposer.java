package com.egoveris.ffdd.web.adm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.DropEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelArray;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.egoveris.ffdd.base.service.IComponenteService;
import com.egoveris.ffdd.base.service.IFormularioService;
import com.egoveris.ffdd.base.service.ITipoComponenteService;
import com.egoveris.ffdd.model.exception.DynFormException;
import com.egoveris.ffdd.model.model.AtributoComponenteDTO;
import com.egoveris.ffdd.model.model.ComponenteDTO;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ABMComponenteComposer extends GenericForwardComposer {

	private static final String VALOR_INVALIDO = "El campo ingresado es incorrecto";

	private static final long serialVersionUID = -1046223622245127317L;

	public static final String MODO_ALTA_FC = "modoAltaComp";
	public static final String MODO_MODIFICACION_FC = "modoModificacionComp";
	public static final String MODO_CONSULTA_FC = "modoConsultaComp";

	private static final Logger logger = LoggerFactory.getLogger(ABMComponenteComposer.class);

	// Componentes ZUL
	private Window abmComponenteWindow;
	private Button btnModificar;
	private Button btnEliminar;

	// Atributos
	private Listheader tituloExistenSugerencias;
	private List<ComponenteDTO> listaComponenteDTO;
	private Listitem componenteExistente;
	private Listbox listboxComponentesExistenSugerencias;

	private Textbox nombreComponente;
	private Textbox descripcionComponente;
	private Textbox busquedaComponenteExistente;
	private Textbox expresionRegular;
	private Textbox mascara;
	private Textbox mensaje;
	private Radiogroup tipoComponente;
	private Radio textbox;
	private Radio longbox;
	private Label idEtiquetaMsj;

	private boolean verExistentes = false;
	private boolean modificarComponente = false;

	private ComponenteDTO selectedComponente;

	private String modo;
	private String loggedUser;

	private AnnotateDataBinder componenteBinder;

	// Beans
	@WireVariable("componenteService")
	private IComponenteService componenteService;
	@WireVariable("tipoComponenteService")
	private ITipoComponenteService tipoComponenteService;
	@WireVariable("formularioService")
	private IFormularioService formularioService;

	@Override
	public void doAfterCompose(final Component comp) throws DynFormException {
		try {
			super.doAfterCompose(comp);
			Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
			obtenerDatos();
			limpiarPantalla();
			this.componenteBinder = new AnnotateDataBinder(abmComponenteWindow);
			this.componenteBinder.loadAll();
		} catch (final Exception e) {
			throw new DynFormException(
					Labels.getLabel("abmComboboxComposer.exception.errorAdm") + Labels.getLabel("abmComboboxComposer.exception.errorAdm"), e);
		}
	}

	private void fillListaComponente() throws DynFormException {
		final List<ComponenteDTO> lComponentes = this.componenteService.obtenerComponentesABMLongBoxYTextBox();
		setListaComponenteDTO(lComponentes);
	}

	public void onChanging$busquedaComponenteExistente(final InputEvent e) throws DynFormException {

		final List<ComponenteDTO> listaAux = new ArrayList<ComponenteDTO>();

		for (final ComponenteDTO componente : listaComponenteDTO) {
			if (componente.getNombre().toLowerCase().contains(e.getValue().toLowerCase())) {
				listaAux.add(componente);
			}
		}
		if (!e.getValue().equals("")) {
			this.listboxComponentesExistenSugerencias.setModel(new ListModelArray(listaAux));
		} else {
			this.listboxComponentesExistenSugerencias.setModel(new ListModelArray(listaComponenteDTO));
		}
	}

	/**
	 * Obtiene todos los comboboxs existentes en la base de datos.
	 */
	public void obtenerDatos() throws DynFormException {

		this.loggedUser = Executions.getCurrent().getRemoteUser();
		this.listaComponenteDTO = new ArrayList<ComponenteDTO>();
		this.modo = (String) Executions.getCurrent().getArg().get("modo");
		fillListaComponente();
	}

	public void actualizarComponentesExistentes() throws DynFormException {
		obtenerDatos();
		this.listboxComponentesExistenSugerencias.setModel(new ListModelArray(this.listaComponenteDTO));
		sortListaExistentes();
		this.componenteBinder.loadComponent(this.listboxComponentesExistenSugerencias);
	}

	public void actualizarPantalla(final boolean existentes) throws Exception {
		if (existentes) {
			actualizarComponentesExistentes();
		}
		this.modificarComponente = false;
		this.descripcionComponente.setReadonly(false);
		this.nombreComponente.setReadonly(false);
		this.expresionRegular.setReadonly(false);
		this.mascara.setReadonly(false);
		this.mensaje.setReadonly(false);
		this.descripcionComponente.setValue(null);
		this.nombreComponente.setValue(null);
		this.expresionRegular.setValue(null);
		this.mascara.setValue(null);
		this.mensaje.setValue(null);
		this.btnEliminar.setVisible(false);
		this.btnModificar.setVisible(false);
		this.componenteBinder.loadAll();
	}

	/**
	 * Genera un nuevo componente vacio.
	 */
	public void onNuevo() throws Exception {
		if (!StringUtils.isBlank(this.nombreComponente.getValue()) || !StringUtils.isBlank(this.descripcionComponente.getValue())) {
			Messagebox.show(Labels.getLabel("abmComponente.nuevo.msj"), Labels.getLabel("abmComponente.nuevo"),
					Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
						@Override
						public void onEvent(final Event evt) throws InterruptedException, Exception {
							if ("onYes".equals(evt.getName())) {
								try {
									limpiarPantalla();
									actualizarPantalla(getVerExistentes());
								} catch (final Exception e) {
									logger.error("Error al reiniciar la pantalla - " + e.getMessage());
									throw e;
								}
							}
						}
					});
		} else {
			actualizarPantalla(this.verExistentes);
		}
	}

	public void sortListaExistentes() {
		if (this.listaComponenteDTO.size() > 0) {
			Collections.sort(this.listaComponenteDTO, new Comparator<ComponenteDTO>() {
				@Override
				public int compare(final ComponenteDTO object1, final ComponenteDTO object2) {
					return object1.getNombre().trim().toLowerCase().compareTo(object2.getNombre().trim().toLowerCase());
				}
			});
		}
	}

	public void modificarComponente() throws Exception {
		try {
			this.selectedComponente.setDescripcion(this.descripcionComponente.getValue());
			final ComponenteDTO componente = this.componenteService
					.buscarComponentePorNombre(this.selectedComponente.getNombre());
			componente.setFechaModificacion(new Date());
			componente.setUsuarioModificador(loggedUser);
			componente.setDescripcion(this.descripcionComponente.getValue());
			componente.setMascara(this.mascara.getValue());
			componente.setMensaje(this.mensaje.getValue());
			
			// Does trim of description
			componente.setDescripcion(componente.getDescripcion().trim());
			
			if (!this.tipoComponente.getSelectedItem().getLabel().toUpperCase()
					.equals(componente.getTipoComponenteEnt().getNombre())) {
				componente.setTipoComponenteEnt(this.tipoComponenteService.obtenerTipoComponentePorNombre(
						this.tipoComponente.getSelectedItem().getLabel().toUpperCase()));
			}
			final Map<String, AtributoComponenteDTO> atributosComponente = componente.getAtributos();
			AtributoComponenteDTO atriComponente = atributosComponente.get("constraint");
			if (atriComponente != null) {
				if (!this.mensaje.getValue().isEmpty()) {
					atriComponente.setValor(this.expresionRegular.getValue() + ":" + this.mensaje.getValue());
				} else {
					atriComponente.setValor(this.expresionRegular.getValue() + ":" + VALOR_INVALIDO);
				}
				atributosComponente.put("constraint", atriComponente);
			} else {
				atriComponente = new AtributoComponenteDTO();
				atriComponente.setKey("constraint");
				if (!this.mensaje.getValue().isEmpty()) {
					atriComponente.setValor(this.expresionRegular.getValue() + ":" + this.mensaje.getValue());
				} else {
					atriComponente.setValor(this.expresionRegular.getValue() + ":" + VALOR_INVALIDO);
				}
				atriComponente.setComponente(componente);
				atributosComponente.put("constraint", atriComponente);
			}
			componente.setAtributos(atributosComponente);
			this.componenteService.guardarComponenteDTO(componente);
			Messagebox.show(Labels.getLabel("abmCajaDatos.modifCaja"), Labels.getLabel("abmCajaDatos.titulo"),
					Messagebox.OK, Messagebox.INFORMATION);
		} catch (final Exception e) {
			logger.error("Error en la Modificación del ComponenteDTO - " + e.getMessage());
			throw e;
		}

	}

	private ComponenteDTO prepararComponente() throws DynFormException {
		final ComponenteDTO componenteDTO = new ComponenteDTO();
		componenteDTO.setId(null);
		componenteDTO.setNombre(this.nombreComponente.getValue());
		componenteDTO.setDescripcion(this.descripcionComponente.getValue());
		componenteDTO.setMascara(this.mascara.getValue());
		componenteDTO.setMensaje(this.mensaje.getValue());
		componenteDTO.setTipoComponenteEnt(tipoComponenteService
				.obtenerTipoComponentePorNombre(this.tipoComponente.getSelectedItem().getLabel().toUpperCase()));
		componenteDTO.setAbmComponent(true);
		
		final Map<String, AtributoComponenteDTO> atributosComponentes = new HashMap<String, AtributoComponenteDTO>();
		if (!this.expresionRegular.getValue().isEmpty()) {
			final AtributoComponenteDTO atributoComponente = new AtributoComponenteDTO();
			final String key = "constraint";
			if (!this.mensaje.getValue().isEmpty()) {
				atributoComponente.setValor(this.expresionRegular.getValue() + ":" + this.mensaje.getValue());
				atributoComponente.setKey(key);
			} else {
				atributoComponente.setValor(this.expresionRegular.getValue() + ":" + VALOR_INVALIDO);
				atributoComponente.setKey(key);
			}
			atributoComponente.setComponente(componenteDTO);
			atributosComponentes.put(key, atributoComponente);
		}
		componenteDTO.setAtributos(atributosComponentes);
		
		return componenteDTO;
	}

	public void onEliminar() throws InterruptedException {
		Messagebox.show(Labels.getLabel("abmComponente.eliminarComponente.pregunta"),
				Labels.getLabel("abmComponente.eliminarComponente.titulo"), Messagebox.YES | Messagebox.NO,
				Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(final Event evt) throws Exception {
						if ("onYes".equals(evt.getName())) {
							final Boolean result = formularioService.verificarUsoComponente(componenteService
									.buscarComponentePorNombre(nombreComponente.getValue()).getId().toString());
							if (result) {
								Messagebox.show(Labels.getLabel("abmCombobox.fallaElimComp.msg"));
								limpiarPantalla();
								setModo(MODO_ALTA_FC);
								prepararBotones();
							} else {
								eliminarComponente();
							}
						}
					}
				});
	}

	/**
	 * Establece los atributos de determinados componentes a sus valores
	 * iniciales.
	 */
	public void limpiarPantalla() {
		this.nombreComponente.setValue(null);
		this.descripcionComponente.setValue(null);
		this.btnModificar.setDisabled(true);
		this.btnEliminar.setDisabled(true);
		this.nombreComponente.setReadonly(false);
		this.nombreComponente.setValue("");
		this.nombreComponente.setTooltiptext("");
		this.descripcionComponente.setReadonly(false);
		this.descripcionComponente.setValue("");
		this.descripcionComponente.setTooltiptext("");
		this.mascara.setReadonly(false);
		this.mascara.setValue("");
		this.mascara.setTooltiptext("");
		this.expresionRegular.setReadonly(false);
		this.expresionRegular.setValue("");
		this.expresionRegular.setTooltiptext("");
		this.mensaje.setReadonly(false);
		this.mensaje.setValue("");
		this.mensaje.setTooltiptext("");
		this.tipoComponente.setSelectedIndex(0);
		busquedaComponenteExistente.setValue("");

	}

	public void eliminarComponente() throws Exception {
		try {
			this.componenteService.eliminarComponente(this.selectedComponente.getNombre());
			Messagebox.show(Labels.getLabel("abmCombobox.exitoElimComp.msg"),
					Labels.getLabel("abmComponente.eliminarComponente.titulo"), Messagebox.OK, Messagebox.INFORMATION);
			actualizarPantalla(this.verExistentes);
		} catch (final Exception e) {
			logger.error("Error al intentar eliminar la Caja de Datos - ", e.getMessage());
			throw e;
		}
	}

	public void onGuardar() throws Exception {
		try {
			if (esComponenteValido()) {
				if (!this.modificarComponente) {
					if (this.componenteService.buscarComponentePorNombre(this.nombreComponente.getValue()) == null) {
						final ComponenteDTO componente = prepararComponente();
						if (componente.getFechaCreacion() == null) {
							componente.setFechaCreacion(new Date());
							componente.setUsuarioCreador(loggedUser);
						} else {
							componente.setFechaModificacion(new Date());
							componente.setUsuarioModificador(loggedUser);
						}
						
						// Does trim of description
						componente.setDescripcion(componente.getDescripcion().trim());
						
						this.componenteService.guardarComponente(componente);
						Messagebox.show(Labels.getLabel("abmComponente.altaComponente"),
								Labels.getLabel("fc.informacion"), Messagebox.OK,
								Messagebox.INFORMATION);
					} else {
						Messagebox.show(Labels.getLabel("abmComponente.errorAlta"),
								Labels.getLabel("fc.informacion"), Messagebox.OK,
								Messagebox.INFORMATION);
					}
				} else {
					modificarComponente();
				}
				actualizarPantalla(this.verExistentes);
			}

		} catch (final Exception e) {
			logger.error("Error en el alta del ComponenteDTO - " + e.getMessage());
			throw e;
		}
	}

	public void onCheck$longbox() {
		if (this.tipoComponente.getSelectedItem().getLabel().equalsIgnoreCase("longbox")) {
			this.mascara.setReadonly(true);
			this.idEtiquetaMsj.setValue(Labels.getLabel("abmComponente.restriccion"));
		} else {
			this.mascara.setReadonly(false);
			this.idEtiquetaMsj.setValue(Labels.getLabel("abmComponente.expresion"));
		}
	}

	public void onCheck$textbox() {
		if (this.tipoComponente.getSelectedItem().getLabel().equalsIgnoreCase("textbox")) {
			this.mascara.setReadonly(false);
			this.idEtiquetaMsj.setValue(Labels.getLabel("abmComponente.expresion"));
		} else {
			this.mascara.setReadonly(true);
			this.idEtiquetaMsj.setValue(Labels.getLabel("abmComponente.restriccion"));
		}
	}

	private boolean esComponenteValido() throws InterruptedException, WrongValueException, DynFormException {

		if (StringUtils.isBlank(this.nombreComponente.getValue())) {
			this.nombreComponente.setErrorMessage(Labels.getLabel("abmComponente.nomCompo.vali"));
			return false;
		}

		if (StringUtils.isBlank(this.descripcionComponente.getValue())) {
			this.descripcionComponente.setErrorMessage(Labels.getLabel("abmComponente.desCompo.vali"));
			return false;
		}

		final Map<String, Textbox> map = new HashMap<String, Textbox>();
		final Pattern p = Pattern.compile("[^a-z0-9_]");
		final Pattern pnum = Pattern.compile("[^a-z_]");
		final Pattern pletra = Pattern.compile("[(?=.*[a-z])]");
		final Textbox tb = nombreComponente;
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
				tb.setErrorMessage(Labels.getLabel("abmFC.nomCaract.vali.num"));
				return false;
			}
		}

		if (map.get(tb.getValue()) == null) {
			map.put(tb.getValue(), tb);
		} else {
			tb.setErrorMessage(Labels.getLabel("abmFC.nombIguales.vali"));
			return false;
		}

		return true;
	}

	/**
	 * Muestra listado ordenado de comboboxs existentes en la base de datos.
	 */
	public void onExistentes() throws DynFormException {

		obtenerDatos();
		busquedaComponenteExistente.setValue("");
		this.verExistentes = true;
		this.busquedaComponenteExistente.setDisabled(false);
		this.btnModificar.setVisible(true);
		this.btnModificar.setDisabled(true);
		this.btnEliminar.setVisible(true);
		this.btnEliminar.setDisabled(true);
		this.tituloExistenSugerencias.setLabel("Componentes Existentes");
		sortListaExistentes();
		this.listboxComponentesExistenSugerencias.setModel(new ListModelArray(getListaComponenteDTO()));
	}

	public void prepararBotones() {
		this.btnEliminar.setVisible(true);
		this.btnModificar.setVisible(true);
		this.btnModificar.setDisabled(false);
		this.btnEliminar.setDisabled(false);
	}

	private static Map<String, String> mostrarExpresionRegular(final String cadena) {
		final Map<String, String> mapaExpresionMensaje = new HashMap<String, String>();
		if (cadena != null && cadena.lastIndexOf(':') != -1) {
			mapaExpresionMensaje.put("expresion", cadena.substring(0, cadena.lastIndexOf(':')));
			mapaExpresionMensaje.put("mensaje", cadena.substring(cadena.lastIndexOf(':') + 1));
		} else {
			mapaExpresionMensaje.put("expresion", cadena);
		}
		return mapaExpresionMensaje;
	}

	public void mostrarComponenteExistente(final Listitem draggedComp) {

		prepararBotones();
		final ComponenteDTO componenteDTO = (ComponenteDTO) draggedComp.getValue();
		this.nombreComponente.setValue(componenteDTO.getNombre());
		this.nombreComponente.setReadonly(true);
		this.nombreComponente.setTooltiptext(componenteDTO.getNombre());
		this.descripcionComponente.setValue(componenteDTO.getDescripcion());
		this.descripcionComponente.setReadonly(true);
		Map<String, String> mapaExpresionMensaje = new HashMap<String, String>();
		if(!componenteDTO.getAtributos().isEmpty()){
			mapaExpresionMensaje = mostrarExpresionRegular(
					componenteDTO.getAtributos().get("constraint").getValor());		
		}
		this.expresionRegular.setReadonly(true);
		this.expresionRegular.setValue(mapaExpresionMensaje.get("expresion"));
		this.mascara.setReadonly(true);
		this.mascara.setValue(componenteDTO.getMascara());
		this.mensaje.setReadonly(true);
		this.mensaje.setValue(mapaExpresionMensaje.get("mensaje"));
		
		if (componenteDTO.getTipoComponente().equalsIgnoreCase("textbox")) {
			this.textbox.setSelected(true);
			this.longbox.setDisabled(true);
			this.idEtiquetaMsj.setValue(Labels.getLabel("abmComponente.expresion"));
		}
		if (componenteDTO.getTipoComponente().equalsIgnoreCase("longbox")) {
			this.longbox.setSelected(true);
			this.textbox.setDisabled(true);
			this.idEtiquetaMsj.setValue(Labels.getLabel("abmComponente.restriccion"));
		}

		for (int i = 0; i < this.tipoComponente.getItems().size(); i++) {
			final Radio item = this.tipoComponente.getItems().get(i);
			if (item.getLabel().equalsIgnoreCase(componenteDTO.getTipoComponente())) {
				this.tipoComponente.setSelectedItem(item);
			}
		}

		this.descripcionComponente.setValue(componenteDTO.getDescripcion());
		this.descripcionComponente.setReadonly(true);

		setSelectedComponente(componenteDTO);
		this.listboxComponentesExistenSugerencias.setDraggable("false");
		this.componenteBinder.loadAll();
	}

	public void onModificar() {
		this.componenteExistente.setDraggable("true");
		this.componenteExistente.setDroppable("true");
		this.nombreComponente.setReadonly(true);
		this.descripcionComponente.setReadonly(false);
		this.expresionRegular.setReadonly(false);
		this.mascara.setReadonly(false);
		this.mensaje.setReadonly(false);
		this.textbox.setDisabled(false);
		this.longbox.setDisabled(false);
		if (tipoComponente.getSelectedItem().getLabel().equalsIgnoreCase("longbox")) {
			this.mascara.setReadonly(true);
		} else {
			this.mascara.setReadonly(false);
		}
		this.componenteBinder.loadAll();
		this.modificarComponente = true;

	}

	public void onDropItem(final ForwardEvent fe) throws InterruptedException {
		final DropEvent event = (DropEvent) fe.getOrigin();
		final Listitem draggedComp = (Listitem) event.getDragged();
		if (draggedComp.getParent().getId().equals(listboxComponentesExistenSugerencias.getId())) {
			if (!(event.getTarget() instanceof Button)) {
				if (this.listaComponenteDTO.size() > 0) {

					Messagebox.show(Labels.getLabel("abmComponente.mostrarComponente.vali"),
							Labels.getLabel("fc.export.atencion.title"), Messagebox.YES | Messagebox.NO,
							Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
								@Override
								public void onEvent(final Event evt) throws Exception {
									if ("onYes".equals(evt.getName())) {
										mostrarComponenteExistente(draggedComp);
									}
								}
							});

				} else {
					mostrarComponenteExistente(draggedComp);
				}
			}
		}
	}

	public void onCancelar() throws Exception {
		try {

			Messagebox.show(Labels.getLabel("fc.export.salirPreg.msg"), Labels.getLabel("fc.confirmacion"),
					Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
						@Override
						public void onEvent(final Event evt) throws InterruptedException {
							if ("onYes".equals(evt.getName())) {
								abmComponenteWindow.onClose();
							}
						}
					});
		} catch (final Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
	}

	public void setListaComponenteDTO(final List<ComponenteDTO> listaComponenteDTO) {
		this.listaComponenteDTO = listaComponenteDTO;
	}

	public List<ComponenteDTO> getListaComponenteDTO() {
		return listaComponenteDTO;
	}

	public String getModo() {
		return modo;
	}

	public void setModo(final String modo) {
		this.modo = modo;
	}

	public ComponenteDTO getSelectedComponente() {
		return selectedComponente;
	}

	public void setSelectedComponente(final ComponenteDTO selectedComponente) {
		this.selectedComponente = selectedComponente;
	}

	public boolean getVerExistentes() {
		return verExistentes;
	}

	public void setVerExistentes(final boolean verExistentes) {
		this.verExistentes = verExistentes;
	}
}
