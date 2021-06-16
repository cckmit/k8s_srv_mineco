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
import com.egoveris.ffdd.model.model.ConstraintDTO;
import com.egoveris.ffdd.model.model.FormTriggerDTO;
import com.egoveris.ffdd.model.model.FormularioComponenteDTO;
import com.egoveris.ffdd.model.model.FormularioDTO;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ABMFCConstraintEditComposer extends ABMConstraintComposer{

	private static final long serialVersionUID = -6723362500795263358L;
	private static final Logger logger = LoggerFactory.getLogger(ABMFCConstraintEditComposer.class);
	private Button botonGuardar;
	private Window abmConstraintEditWindow;
	private List<FormularioComponenteDTO> listaFormularioConstraints;
	private List<ConstraintDTO> listaAuxiliarConstraint;
	private List<ConstraintDTO> listaConstraints;
	private String modo;
	private boolean guardar = false;	//true base, false normal
	private FormularioDTO formulario;
	
	@WireVariable("constraintService")
	private IConstraintService constraintService;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
		this.listaConstraints = new ArrayList<ConstraintDTO>();
		this.listaAuxiliarConstraint  = new ArrayList<ConstraintDTO>();
		this.formulario = (FormularioDTO)Executions.getCurrent().getArg().get("formulario");
		this.listaFormularioComponente = ((List<FormularioComponenteDTO>) Executions.getCurrent().getArg().get("listComponentesSeleccionados"));
		this.listaFormularioConstraints = ((List<FormularioComponenteDTO>) Executions.getCurrent().getArg().get("listaComponentesConstraint"));
		guardarListaConstraintAuxiliar();
		this.modo = (String)Executions.getCurrent().getArg().get("modo");
		if(modo.equals(ConstantesConstraint.MODO_CONSULTA_FC)){	//guarda base
			this.guardar = true;
			this.botonGuardar.setImage("/imagenes/Guardar_mini.png");
			this.botonGuardar.setLabel(Labels.getLabel("abmCombobox.guardar"));
		}
		this.self.addEventListener(Events.ON_CLOSE,new ABMFCConstraintEditComposerListener(this));
		this.self.addEventListener(Events.ON_NOTIFY, new ABMFCConstraintEditComposerListener(this));
		crearVentanas();
	}
	
	private void closeWindow() {
		HashMap<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("accion", "salirSinGuardar");
		mapa.put("listConstraintDTO", this.listaAuxiliarConstraint);
		Events.sendEvent(new Event(Events.ON_NOTIFY, this.self.getParent(),mapa));
		this.abmConstraintEditWindow.onClose();
	}
	
	public void onCancelar() throws Exception {
		Messagebox.show(Labels.getLabel("abmFC.salirPreg.msg"),
				Labels.getLabel("fc.confirmacion"), Messagebox.YES
						| Messagebox.NO, Messagebox.QUESTION,
				new org.zkoss.zk.ui.event.EventListener() {
					public void onEvent(Event evt) throws InterruptedException {
						if ("onYes".equals(evt.getName())) {
							closeWindow();
						}
					}
				});
	}
	
	private void crearVentanas() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("listComponentesSeleccionados", super.listaFormularioComponente);
		map.put("mapaConstraints", getMapaConstraints());
		if (!this.listaConstraints.isEmpty()) {
			for (ConstraintDTO constraint : this.listaConstraints) {
				map.put("constraint", constraint);
				Executions.createComponents("/administradorFC/formConstraintGenericEdit.zul",
						this.self, map);
			}
		} else {
			Executions.createComponents("/administradorFC/formConstraintGenerico.zul",
					this.self, map);
		}
	}
	
	private void guardarListaConstraintAuxiliar(){
		for(FormularioComponenteDTO formCompConstraint: this.listaFormularioConstraints){
			for(ConstraintDTO constraintDto :formCompConstraint.getConstraintList()){					
				ConstraintDTO constraintAux = new ConstraintDTO();
				List<CondicionDTO> newLista = new ArrayList<CondicionDTO>();
				for(CondicionDTO condicion : constraintDto.getCondiciones()){
					CondicionDTO newCondicion = new CondicionDTO();
					newCondicion.setAnd(condicion.isAnd());
					newCondicion.setOr(condicion.isOr());
					newCondicion.setCondicion(condicion.getCondicion());
					newCondicion.setPrimero(condicion.isPrimero());
					newCondicion.setNombreComponente(condicion.getNombreComponente());
					newCondicion.setValorComparacion(condicion.getValorComparacion());
					newCondicion.setNombreCompComparacion(condicion.getNombreCompComparacion());
					newLista.add(newCondicion);
				}
				constraintAux.setCondiciones(newLista);
				constraintAux.setId(constraintDto.getId());
				constraintAux.setMensaje(constraintDto.getMensaje());
				constraintAux.setNombreComponente(constraintDto.getNombreComponente());
				this.listaAuxiliarConstraint.add(constraintAux);
				this.listaConstraints.add(constraintDto);
			}
		}
	}
	
	/**
	 * Permite enviar un evento a ABMFCComposer, con un mapa, que contiene la
	 * lista de constraintDTO, para poder visualizarla despues del formulario y
	 * la ventana ya guardada
	 * 
	 * @param listConstraintDTO
	 */
	private void enviarListConstraintDTOVentanaABMFCComposer(
			List<ConstraintDTO> listConstraintDTO) {
		HashMap<String, Object> mapABMFC = new HashMap<String, Object>();
		mapABMFC.put("listConstraintDTO",listConstraintDTO);
		mapABMFC.put("accion",ConstantesConstraint.CONSTRAINTS_COMPONENTES_GUARDAR);	
		Events.sendEvent(new Event(Events.ON_NOTIFY, this.self.getParent(),mapABMFC));
	}
	
	public void onGuardarConstraint() throws InterruptedException {
		Set<ConstraintDTO> listConstraintDTO = new HashSet<ConstraintDTO>();
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put(
				"accion",
				ConstantesConstraint.CONSTRAINT_COMPONENTES_TEMPORALES_GUARDAR);
		boolean flag = false;
		boolean tieneVentana = true;
		List<ConstraintDTO> lstConstraint = null;
		for (Object children : this.abmConstraintEditWindow.getChildren()) {
			if (children instanceof Window) {
				tieneVentana = true;
				Events.sendEvent(new Event(Events.ON_NOTIFY,
						(Component) children, mapa));
				lstConstraint = this.<ConstraintDTO>crearObjetoCondicionalDto((Window) children);
				flag = tieneCondiciones(lstConstraint);
				if (!flag) {
					vaciarMapa((Window) children);
					tieneVentana = false;
					break;
				} else {
					listConstraintDTO.addAll(lstConstraint);
				}
			}
		}
		if (!flag && !tieneVentana) {
			Messagebox.show(Labels.getLabel("abmConstraint.constraintVacia"),
					Labels.getLabel("fc.export.atencion.title"),
					Messagebox.OK, Messagebox.INFORMATION);
		} else {
			if (this.guardar) {
				try {
					guardarDynamicConstraint(new ArrayList<ConstraintDTO>(listConstraintDTO));
				} catch (AccesoDatoException e) {
					logger.error("Error al persistir las constraint: "
							+ e.getMessage(),e);
				}
			}
			enviarListConstraintDTOVentanaABMFCComposer(new ArrayList<ConstraintDTO>(listConstraintDTO));
			this.abmConstraintEditWindow.onClose();
		}
		
	}
	
	private boolean tieneCondiciones(List<ConstraintDTO> listaConstraint) {
		for (ConstraintDTO constraint : listaConstraint) {
			if (constraint.getCondiciones() != null
					&& !constraint.getCondiciones().isEmpty()) {
				for(CondicionDTO condicion : constraint.getCondiciones()){
					if(condicion.isPrimero()){
						if(!validarCondicionVacia(condicion)){
							return false;
						}
					}else{
						if(!condicion.isAnd() && !condicion.isOr()){
							return false;
						}else if(!validarCondicionVacia(condicion)){
							return false;
						}
					}
				}
			}else{
				return false;
			}
		}
		return true;
	}
	
	
	/**
	 * Guarda las constraint directamente el base, sin la necesidad de guardar el
	 * formulario
	 * 
	 * @param listConstraintDTO
	 */
	private void guardarDynamicConstraint(List<ConstraintDTO> listConstraintDTO)
			throws AccesoDatoException {
		List<FormTriggerDTO> listaTriggers = new ArrayList<FormTriggerDTO>();
		for(ConstraintDTO constraint :listConstraintDTO){
			FormTriggerDTO trigger = constraintService.createDynamicConstraintComponent(this.formulario.getId(), constraint);
			trigger.setType(ConstantesConstraint.TYPE_CONSTRAINTDTO);
			listaTriggers.add(trigger);
		}
		constraintService.modificarConstraintList(listaTriggers,this.formulario.getId());
	}

	public void onAltaConstraint() throws InterruptedException {
		Executions.createComponents("/administradorFC/formConstraintGenerico.zul", this.self,inicializarMapComponentes());
	}
	
	private HashMap<String, Object> inicializarMapComponentes() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("listComponentesSeleccionados", listaFormularioComponente);
		map.put("mapaConstraints", mapaConstraints);
		return map;
	}
	
	public List<FormularioComponenteDTO> getListaFormularioComponente() {
		return listaFormularioComponente;
	}

	public void setListaFormularioComponente(
			List<FormularioComponenteDTO> listaFormularioComponente) {
		this.listaFormularioComponente = listaFormularioComponente;
	}

	public List<FormularioComponenteDTO> getListaFormularioConstraints() {
		return listaFormularioConstraints;
	}

	public void setListaFormularioConstraints(
			List<FormularioComponenteDTO> listaFormularioConstraints) {
		this.listaFormularioConstraints = listaFormularioConstraints;
	}
	
	public Map<String, Object> getMapaConstraints() {
		return super.mapaConstraints;
	}

	public void setMapaConstraints(Map<String, Object> mapaConstraints) {
		super.mapaConstraints = mapaConstraints;
	}


	final class ABMFCConstraintEditComposerListener implements EventListener {

		private ABMFCConstraintEditComposer composer;

		public ABMFCConstraintEditComposerListener(ABMFCConstraintEditComposer composer) {
			this.setComposer(composer);
		}

		public ABMFCConstraintEditComposer getComposer() {
			return composer;
		}

		public void setComposer(ABMFCConstraintEditComposer composer) {
			this.composer = composer;
		}

		@SuppressWarnings("unchecked")
		public void onEvent(Event event) throws Exception {
			if (event.getName().equals(Events.ON_NOTIFY)) {
				Map<String,Object> map = (Map<String, Object>) event.getData();
				this.composer.setMapaConstraints((Map<String, Object>) map.get("mapaConstraints"));
			}
			if (event.getName().equals(Events.ON_CLOSE)) {
				this.composer.onCancelar();
				event.stopPropagation();
			}
		}
	}

}
