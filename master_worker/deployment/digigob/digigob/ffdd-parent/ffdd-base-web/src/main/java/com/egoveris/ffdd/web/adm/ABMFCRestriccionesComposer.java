package com.egoveris.ffdd.web.adm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.zkoss.zul.Button;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.egoveris.ffdd.base.exception.AccesoDatoException;
import com.egoveris.ffdd.base.service.IConstraintService;
import com.egoveris.ffdd.base.util.ConstantesConstraint;
import com.egoveris.ffdd.model.model.CondicionDTO;
import com.egoveris.ffdd.model.model.FormularioComponenteDTO;
import com.egoveris.ffdd.model.model.FormularioDTO;
import com.egoveris.ffdd.model.model.VisibilidadComponenteDTO;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ABMFCRestriccionesComposer extends ABMConstraintComposer {

	private static final long serialVersionUID = 3805835902590644525L;
	private static final Logger logger = LoggerFactory.getLogger(ABMFCRestriccionesComposer.class);
	private Window abmFCRestriccionWindow;
	private Button botonGuardar;
	private String modo;
	private boolean guardar = false; // true base, false normal
	
	@WireVariable("constraintService")
	private IConstraintService constraintService;
	
	private List<VisibilidadComponenteDTO> listaRestricciones;
	private List<VisibilidadComponenteDTO> listaRestriccionesAuxiliar;
	private List<VisibilidadComponenteDTO> listaFormularioRestricciones;
	private FormularioDTO formulario;
	private List<VisibilidadComponenteDTO> listaComponentesOcultos = new ArrayList<VisibilidadComponenteDTO>();

	@Override
	@SuppressWarnings("unchecked")
	public void doAfterCompose(final Component comp) throws Exception {
		super.doAfterCompose(comp);
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
		this.listaRestricciones = new ArrayList<VisibilidadComponenteDTO>();
		this.listaRestriccionesAuxiliar = new ArrayList<VisibilidadComponenteDTO>();
		this.self.addEventListener(Events.ON_NOTIFY, new ABMFCRestriccionesComposerListener(this));
		this.formulario = (FormularioDTO) Executions.getCurrent().getArg().get("formulario");
		this.listaFormularioComponente = (List<FormularioComponenteDTO>) Executions.getCurrent().getArg()
				.get("listComponentesSeleccionados");
		this.listaFormularioRestricciones = (List<VisibilidadComponenteDTO>) Executions.getCurrent().getArg()
				.get("listaRestriccion");
		this.modo = (String) Executions.getCurrent().getArg().get("modo");
		if (modo.equals(ConstantesConstraint.MODO_CONSULTA_FC)) {
			this.guardar = true; // guarda base
			this.botonGuardar.setImage("/imagenes/Guardar_mini.png");
			this.botonGuardar.setLabel(Labels.getLabel("abmCombobox.guardar"));
		}
		this.self.addEventListener(Events.ON_CLOSE, new ABMFCRestriccionesComposerListener(this));
		verificarExistenciaComponentes();
		guardarRestriccionesAuxiliares();
		crearVentanas();
	}

	private void verificarExistenciaComponentes() {
		for (final VisibilidadComponenteDTO visibilidadComponenteDTO : this.listaFormularioRestricciones) {
			final List<String> auxComponentesOcultos = new ArrayList<String>();
			for (final FormularioComponenteDTO comp : this.listaFormularioComponente) {
				for (final String compOculto : visibilidadComponenteDTO.getComponentesOcultos()) {
					if (compOculto.equals(comp.getNombre())) {
						auxComponentesOcultos.add(compOculto);
					}
				}
			}
			visibilidadComponenteDTO.setComponentesOcultos(auxComponentesOcultos);
		}
	}

	private void crearVentanas() {
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("listComponentesSeleccionados", super.listaFormularioComponente);
		if (!this.listaRestricciones.isEmpty()) {
			for (final VisibilidadComponenteDTO restriccion : this.listaRestricciones) {
				map.put("restriccion", restriccion);
				Executions.createComponents("/administradorFC/formRestriccionEdit.zul", this.self, map);
			}
		} else {
			Executions.createComponents("/administradorFC/formRestriccionGenerico.zul", this.self, map);
		}
	}

	private void guardarRestriccionesAuxiliares() {
		for (final VisibilidadComponenteDTO restriccion : this.listaFormularioRestricciones) {
			final VisibilidadComponenteDTO restriccionAux = new VisibilidadComponenteDTO();
			final List<CondicionDTO> newLista = new ArrayList<CondicionDTO>();
			for (final CondicionDTO condicion : restriccion.getCondiciones()) {
				final CondicionDTO newCondicion = new CondicionDTO();
				newCondicion.setAnd(condicion.isAnd());
				newCondicion.setOr(condicion.isOr());
				newCondicion.setCondicion(condicion.getCondicion());
				newCondicion.setPrimero(condicion.isPrimero());
				newCondicion.setNombreComponente(condicion.getNombreComponente());
				newCondicion.setValorComparacion(condicion.getValorComparacion());
				newCondicion.setNombreCompComparacion(condicion.getNombreCompComparacion());
				newLista.add(newCondicion);
			}
			restriccionAux.setCondiciones(newLista);
			restriccionAux.setId(restriccion.getId());
			restriccionAux.setComponentesOcultos(restriccion.getComponentesOcultos());
			this.listaRestricciones.add(restriccion);
			this.listaRestriccionesAuxiliar.add(restriccionAux);
		}
	}

	public void onAltaConstraint() throws InterruptedException {
		Executions.createComponents("/administradorFC/formRestriccionGenerico.zul", this.self, inicializarMapComponentes());
	}

	private HashMap<String, Object> inicializarMapComponentes() {
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("listComponentesSeleccionados", listaFormularioComponente);
		map.put("mapaRestriccion", mapaConstraints);
		return map;
	}

	public void onGuardarConstraint() throws InterruptedException {
		final Set<VisibilidadComponenteDTO> listRestriccionDTO = new HashSet<VisibilidadComponenteDTO>();
		final Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("accion", ConstantesConstraint.RESTRICCION_COMPONENTES_TEMPORALES_GUARDAR);
		boolean flag = false;
		boolean tieneVentana = true;
		for (final Object children : this.abmFCRestriccionWindow.getChildren()) {
			if (children instanceof Window) {
				tieneVentana = true;
				Events.sendEvent(new Event(Events.ON_NOTIFY, (Component) children, mapa));
				flag = tieneCondiciones(this.listaComponentesOcultos);
				if (!flag) {
					mapa.clear();
					tieneVentana = false;
					break;
				} else {
					listRestriccionDTO.addAll(this.listaComponentesOcultos);
				}
			}
		}
		if (!flag && !tieneVentana) {
			Messagebox.show(Labels.getLabel("abmConstraint.constraintVacia"),
					Labels.getLabel("fc.export.atencion.title"), Messagebox.OK, Messagebox.INFORMATION);
		} else {
			if (this.guardar) {
				try {
					guardarFormTrigger(listRestriccionDTO);
				} catch (final AccesoDatoException e) {
					logger.error("Error al persistir las constraint", e);
				}
			} else {
				enviarListConstraintDTOVentanaABMFCComposer(listRestriccionDTO);
			}
			this.abmFCRestriccionWindow.onClose();
		}

	}

	/**
	 * Permite enviar un evento a ABMFCComposer, con un mapa, que contiene la
	 * lista de constraintDTO, para poder visualizarla despues del formulario y
	 * la ventana ya guardada
	 *
	 * @param listRestriccionDTO
	 */
	private void enviarListConstraintDTOVentanaABMFCComposer(final Set<VisibilidadComponenteDTO> listRestriccionDTO) {
		final HashMap<String, Object> mapABMFC = new HashMap<String, Object>();
		mapABMFC.put("listRestriccionDTO", new ArrayList<VisibilidadComponenteDTO>(listRestriccionDTO));
		mapABMFC.put("accion", ConstantesConstraint.RESTRICCION_COMPONENTES_GUARDAR);
		Events.sendEvent(new Event(Events.ON_NOTIFY, this.self.getParent(), mapABMFC));
	}

	private void closeWindow() {
		final HashMap<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("accion", ConstantesConstraint.RESTRICCION_COMPONENTES_SALIR_SIN_GUARDAR);
		mapa.put("listRestriccionDTO", this.listaRestriccionesAuxiliar);
		Events.sendEvent(new Event(Events.ON_NOTIFY, this.self.getParent(), mapa));
		this.abmFCRestriccionWindow.onClose();
	}

	public void onCancelar() throws Exception {
		Messagebox.show(Labels.getLabel("abmFC.salirPreg.msg"), Labels.getLabel("fc.confirmacion"),
				Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(final Event evt) throws InterruptedException {
						if ("onYes".equals(evt.getName())) {
							closeWindow();
						}
					}
				});
	}

	private boolean tieneCondiciones(final List<VisibilidadComponenteDTO> listaRestricciones) {
		for (final VisibilidadComponenteDTO restriccion : listaRestricciones) {
			if (restriccion.getComponentesOcultos() == null || restriccion.getComponentesOcultos().isEmpty()) {
				return false;
			}
			if (restriccion.getCondiciones() != null && !restriccion.getCondiciones().isEmpty()) {
				for (final CondicionDTO condicion : restriccion.getCondiciones()) {
					if (condicion.isPrimero()) {
						if (!validarCondicionVacia(condicion)) {
							return false;
						}
					} else {
						if (!condicion.isAnd() && !condicion.isOr()) {
							return false;
						} else if (!validarCondicionVacia(condicion)) {
							return false;
						}
					}
				}
			} else {
				return false;
			}
		}
		return true;
	}

	private void guardarFormTrigger(final Set<VisibilidadComponenteDTO> listaRestriccionDto)
			throws AccesoDatoException {
		this.formulario.setListaComponentesOcultos(new ArrayList<VisibilidadComponenteDTO>(listaRestriccionDto));
		constraintService.modificarRestriccionList(this.formulario, ConstantesConstraint.TYPE_RESTRICCIONDTO);
	}

	public List<VisibilidadComponenteDTO> getListaComponentesOcultos() {
		return listaComponentesOcultos;
	}

	public void setListaComponentesOcultos(final List<VisibilidadComponenteDTO> listaComponentesOcultos) {
		this.listaComponentesOcultos = listaComponentesOcultos;
	}

	final class ABMFCRestriccionesComposerListener implements EventListener {

		private ABMFCRestriccionesComposer composer;

		public ABMFCRestriccionesComposerListener(final ABMFCRestriccionesComposer composer) {
			this.setComposer(composer);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void onEvent(final Event event) throws Exception {
			if (event.getName().equals(Events.ON_NOTIFY)) {
				final Map<String, Object> map = (Map<String, Object>) event.getData();
				this.composer.setListaComponentesOcultos(
						(List<VisibilidadComponenteDTO>) map.get("listaComponentesOcultos"));
			} else if (event.getName().equals(Events.ON_CLOSE)) {
				this.composer.onCancelar();
				event.stopPropagation();
			}
		}

		public ABMFCRestriccionesComposer getComposer() {
			return composer;
		}

		public void setComposer(final ABMFCRestriccionesComposer composer) {
			this.composer = composer;
		}

	}
}
