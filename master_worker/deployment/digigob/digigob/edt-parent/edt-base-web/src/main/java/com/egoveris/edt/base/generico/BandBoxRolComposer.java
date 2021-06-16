package com.egoveris.edt.base.generico;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.zkoss.spring.SpringUtil;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listfooter;

import com.egoveris.edt.base.model.eu.usuario.RolDTO;
import com.egoveris.edt.base.service.IRolService;
import com.egoveris.edt.web.pl.renderers.RolRenderer;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;

@SuppressWarnings({ "rawtypes", "deprecation" })
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class BandBoxRolComposer extends GenericForwardComposer {

	
	public static final String ON_SELECT_ROL = "onSelect";
	
	public static final String COMPONENTE_PADRE="padre";
	
	public static final String DISABLED_BANDBOX = "disabledBandbox";
	
	public static final String EVENT_DISABLED = "onDisabled";
	
	public static final String EVENT_CLEAN = "onCleanBandbox";
	
	public static final String EVENT_VALIDAR = "onValidarBandbox";
	
	public static final String EVENT_CARGAR_DATOS = "onCargar";
	
	public static final String SRC = "/genericos/bandboxRol.zul";

	
	private AnnotateDataBinder binder;
	private Listbox listBoxRoles;
	private Listfooter totalRoles;
	private Bandbox bandBoxRol;
	
	private transient IRolService rolService;
	
	private List<RolDTO> listaTodosLosRoles;
	
	private List<RolDTO> listaRolesSeleccionados;
	
	private RolDTO rolSeleccionado;
	
	private Component componentePadre;

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -16026281752038044L;

	@SuppressWarnings({ "unchecked" })
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
		comp.addEventListener(EVENT_DISABLED, new BandBoxRolListener());
		comp.addEventListener(EVENT_CLEAN, new BandBoxRolListener());
		comp.addEventListener(EVENT_VALIDAR, new BandBoxRolListener());
		comp.addEventListener(EVENT_CARGAR_DATOS, new BandBoxRolListener());
		listBoxRoles.setItemRenderer(new RolRenderer());
		inicializarServicio();
		configurarBandbox();
		inicializarListasDeRoles();
		
		this.binder = new AnnotateDataBinder(comp);
	}
	private void inicializarServicio() {
		this.rolService = (IRolService) SpringUtil.getBean("rolService");
	}
	
	private void configurarBandbox() throws Exception {
		listaRolesSeleccionados = new ArrayList<>();
		componentePadre = (Component) Executions.getCurrent().getAttribute(COMPONENTE_PADRE);
		Boolean disabledBandbox = (Boolean) Executions.getCurrent().getAttribute(DISABLED_BANDBOX);
		if(componentePadre==null) {
			throw new Exception("El componente padre es una configuracion requerida para el bandbox de roles");
		}
		bandBoxRol.setDisabled(disabledBandbox==null ? Boolean.FALSE : disabledBandbox);
		
	}
	
	private void inicializarListasDeRoles() throws SecurityNegocioException {
		listaTodosLosRoles = rolService.getRolesVigentes();
		listaRolesSeleccionados = new ArrayList<>();
	}
	
	public void onChanging$bandBoxRol(final InputEvent e) {
		if(!bandBoxRol.isOpen()){
			bandBoxRol.open();
		}
		
		this.cargarRoles(e, this.listaTodosLosRoles, listBoxRoles, listaRolesSeleccionados,
				totalRoles);
	}
	
	public void onBlur$bandBoxRol() {
		if( rolSeleccionado!=null) {
			bandBoxRol.setValue(rolSeleccionado.getRolNombre());
		}
	}
	
	public void onElegirRol(ForwardEvent event) {
		this.rolSeleccionado = (RolDTO) event.getData();
	
		bandBoxRol.setValue(rolSeleccionado.getRolNombre());
		Events.sendEvent(ON_SELECT_ROL, componentePadre, rolSeleccionado);
		this.binder.loadComponent(bandBoxRol);
		bandBoxRol.close();
	}
	
	private void cargarRoles(final InputEvent e, final List<RolDTO> listaTotalRoles,
			final Listbox listbox, final List<RolDTO> listaReducida, final Listfooter totalRoles) {
		String matchingText = e.getValue();
		if (matchingText.trim().length() > 2 && !"*".equals(matchingText.trim())) {
		
			matchRoles(listaTotalRoles, listaReducida, matchingText);
		
		} else if ("*".equals(matchingText.trim())) {
			listaReducida.addAll(listaTotalRoles);
		}else if(StringUtils.isBlank(matchingText)) {
			listaReducida.clear();
			rolSeleccionado = null;
			Events.sendEvent(ON_SELECT_ROL, componentePadre, rolSeleccionado);
		}
		
		listbox.setActivePage(0);
		totalRoles.setLabel(
				Labels.getLabel("edt.bandboxRol.label.totalRoles") + ": " + listaReducida.size());
		this.binder.loadComponent(listbox);

	}
	private void matchRoles(final List<RolDTO> listaTotalRoles, final List<RolDTO> listaReducida, String matchingText) {
		listaReducida.clear();
    matchingText = depurarString(matchingText);
    
		if (listaTotalRoles != null) {
			for (final RolDTO rol : listaTotalRoles) {
				if (rol != null && rol.getRolNombre() != null
						&& StringUtils.containsIgnoreCase(depurarString(rol.getRolNombre()), matchingText)) {
						
					listaReducida.add(rol);
				}
			}
		}
	}
	private String depurarString(String matchingText) {
		matchingText = Normalizer.normalize(matchingText, Normalizer.Form.NFKD)
        .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
		return matchingText;
	}
	
	public Listbox getListBoxRoles() {
		return listBoxRoles;
	}
	public void setListBoxRoles(Listbox listBoxRoles) {
		this.listBoxRoles = listBoxRoles;
	}
	public Listfooter getTotalRoles() {
		return totalRoles;
	}
	public void setTotalRoles(Listfooter totalRoles) {
		this.totalRoles = totalRoles;
	}
	public Bandbox getBandBoxRol() {
		return bandBoxRol;
	}
	public void setBandBoxRol(Bandbox bandBoxRol) {
		this.bandBoxRol = bandBoxRol;
	}
	public IRolService getRolService() {
		return rolService;
	}
	public void setRolService(IRolService rolService) {
		this.rolService = rolService;
	}
	public List<RolDTO> getListaTodosLosRoles() {
		return listaTodosLosRoles;
	}
	public void setListaTodosLosRoles(List<RolDTO> listaTodosLosRoles) {
		this.listaTodosLosRoles = listaTodosLosRoles;
	}
	public List<RolDTO> getListaRolesSeleccionados() {
		return listaRolesSeleccionados;
	}
	public void setListaRolesSeleccionados(List<RolDTO> listaRolesSeleccionados) {
		this.listaRolesSeleccionados = listaRolesSeleccionados;
	}
	public RolDTO getRolSeleccionado() {
		return rolSeleccionado;
	}
	public void setRolSeleccionado(RolDTO rolSeleccionado) {
		this.rolSeleccionado = rolSeleccionado;
	}
	public Component getComponentePadre() {
		return componentePadre;
	}
	public void setComponentePadre(Component componentePadre) {
		this.componentePadre = componentePadre;
	}

	private class BandBoxRolListener implements EventListener{


		@Override
		public void onEvent(Event event) throws Exception {
			
			if(event.getName().equals(EVENT_DISABLED)) {
				bandBoxRol.setDisabled((boolean) event.getData());
				binder.loadComponent(bandBoxRol);
			}else if(event.getName().equals(EVENT_CLEAN)) {
				bandBoxRol.setValue(StringUtils.EMPTY);
				rolSeleccionado = null;
				listBoxRoles.clearSelection();
				listaRolesSeleccionados.clear();
				Events.sendEvent(ON_SELECT_ROL, componentePadre, null);
			}else if(event.getName().equals(EVENT_VALIDAR)) {
				String mensajeError = (String) event.getData();
				throw new WrongValueException(bandBoxRol, mensajeError);
			}else if(event.getName().equals(EVENT_CARGAR_DATOS)) {
				if(event.getData() instanceof RolDTO) {
						rolSeleccionado = (RolDTO) event.getData();
						bandBoxRol.setValue(rolSeleccionado.getRolNombre());
						Events.sendEvent(ON_SELECT_ROL, componentePadre, rolSeleccionado);
						binder.loadComponent(bandBoxRol);	
				}else {
					throw new Exception("El evento EVENT_CARGAR_DATOS del"
							+ " bandboxRol no puede cargar null o el objeto pasado no corresponde");
				}
			}
		}
		
	}
}
