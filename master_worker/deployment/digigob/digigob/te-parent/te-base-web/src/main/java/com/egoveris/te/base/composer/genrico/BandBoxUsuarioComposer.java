package com.egoveris.te.base.composer.genrico;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.UsuarioReducido;
import com.egoveris.te.base.rendered.UsuarioRenderer;
import com.egoveris.te.base.service.iface.IUsuarioCacheService;


@SuppressWarnings({ "rawtypes", "deprecation" })
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class BandBoxUsuarioComposer extends GenericForwardComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8390157638560022796L;
	
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(BandBoxUsuarioComposer.class);
	
	public static final String ON_SELECT_USUARIO = "onSelectUsuario";
	
	public static final String COMPONENTE_PADRE="padre";
	
	public static final String DISABLED_BANDBOX = "disabledBandboxUsuario";
	
	public static final String EVENT_DISABLED = "onDisabledBandboxUsuario";
	
	public static final String EVENT_CLEAN = "onCleanBandboxusuario";
	
	public static final String EVENT_VALIDAR = "onValidarBandboxUsuario";
	
	public static final String SRC = "/generico/bandboxUsuario.zul";
	
	private AnnotateDataBinder binder;
	private Listbox listBoxUsuario;
	private Listfooter totalUsuarios;
	private Bandbox bandBoxUsuario;
	
	private transient IUsuarioCacheService usuarioCacheService;
	
	private List<UsuarioReducido> listaTodosLosUsuarios;
	
	private List<UsuarioReducido> listaUsuariosSeleccionados;
	
	private UsuarioReducido usuarioSeleccionado;
	
	private Component componentePadre;
	
	@SuppressWarnings({ "unchecked" })
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
		comp.addEventListener(EVENT_DISABLED, new BandBoxUsuarioListener());
		comp.addEventListener(EVENT_CLEAN, new BandBoxUsuarioListener());
		listBoxUsuario.setItemRenderer(new UsuarioRenderer());
		inicializarServicio();
		configurarBandbox();
		inicializarListasDeUsuarios();
		
		this.binder = new AnnotateDataBinder(comp);
		binder.loadAll();
	}
	
	private void configurarBandbox() throws Exception {
		listaUsuariosSeleccionados = new ArrayList<UsuarioReducido>();
		componentePadre = (Component) Executions.getCurrent().getAttribute(COMPONENTE_PADRE);
		Boolean disabledBandbox = (Boolean) Executions.getCurrent().getAttribute(DISABLED_BANDBOX);
		if(componentePadre==null) {
			throw new Exception("El componente padre es una configuracion requerida para el bandbox de usuario");
		}
		bandBoxUsuario.setDisabled(disabledBandbox==null ? Boolean.FALSE : disabledBandbox);
		
	}

	private void inicializarServicio() {
		this.usuarioCacheService = (IUsuarioCacheService) SpringUtil.getBean("usuarioCacheService");
	}

	public void onChanging$bandBoxUsuario(final InputEvent e) {
		if(!bandBoxUsuario.isOpen()){
			bandBoxUsuario.open();
		}
		
		this.cargarUsuarios(e, this.listaTodosLosUsuarios, listBoxUsuario, listaUsuariosSeleccionados,
				totalUsuarios);
	}
		
	public void onBlur$bandBoxUsuario() {
		if(usuarioSeleccionado!=null) {
			bandBoxUsuario.setValue(usuarioSeleccionado.getUsername());
		}
	}
	
	public void onElegirUsuario(ForwardEvent event) {
		this.usuarioSeleccionado = (UsuarioReducido) event.getData();
	
		bandBoxUsuario.setValue(usuarioSeleccionado.getUsername());
		Events.sendEvent(ON_SELECT_USUARIO, componentePadre, usuarioSeleccionado);
		this.binder.loadComponent(bandBoxUsuario);
		bandBoxUsuario.close();
	}
	
	private void cargarUsuarios(final InputEvent e, final List<UsuarioReducido> listaTotalUsuarios,
			final Listbox listbox, final List<UsuarioReducido> listaReducida, final Listfooter totalUsuarios) {
		String matchingText = e.getValue();
		if (matchingText.trim().length() > 2 && !"*".equals(matchingText.trim())) {
			listaReducida.clear();
			if (listaTotalUsuarios != null) {
				matchingText = matchingText.toUpperCase();
				for (final UsuarioReducido usuarioReducido : listaTotalUsuarios) {
					if (usuarioReducido != null && usuarioReducido.getUsername() != null) {
						if (usuarioReducido.toString().toUpperCase().contains(matchingText)) {
							listaReducida.add(usuarioReducido);
						}
					}
				}
			}
		} else if ("*".equals(matchingText.trim())) {
			listaReducida.addAll(listaTotalUsuarios);
		} else if (matchingText.trim().length() < 2) {
			listaReducida.clear();
		}
		listbox.setActivePage(0);
		totalUsuarios.setLabel(
				Labels.getLabel("te.bandbox.label.totalUsuarios") + ": " + listaReducida.size());
		this.binder.loadComponent(listbox);

	}
	
	private void inicializarListasDeUsuarios() throws SecurityNegocioException {
		listaTodosLosUsuarios = usuarioCacheService.obtenerTodosUsuarios();
		listaUsuariosSeleccionados = new ArrayList<>();
	}

	/* getters and setters */
	
	public AnnotateDataBinder getBinder() {
		return binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public Listfooter getTotalUsuarios() {
		return totalUsuarios;
	}

	public void setTotalUsuarios(Listfooter totalUsuarios) {
		this.totalUsuarios = totalUsuarios;
	}

	public List<UsuarioReducido> getListaTodosLosUsuarios() {
		return listaTodosLosUsuarios;
	}

	public void setListaTodosLosUsuarios(List<UsuarioReducido> listaTodosLosUsuarios) {
		this.listaTodosLosUsuarios = listaTodosLosUsuarios;
	}

	public List<UsuarioReducido> getListaUsuariosSeleccionados() {
		return listaUsuariosSeleccionados;
	}

	public void setListaUsuariosSeleccionados(List<UsuarioReducido> listaUsuariosSeleccionados) {
		this.listaUsuariosSeleccionados = listaUsuariosSeleccionados;
	}

	public UsuarioReducido getUsuarioSeleccionado() {
		return usuarioSeleccionado;
	}

	public void setUsuarioSeleccionado(UsuarioReducido usuarioSeleccionado) {
		this.usuarioSeleccionado = usuarioSeleccionado;
	}
	
	public class BandBoxUsuarioListener implements EventListener{

		@Override
		public void onEvent(Event event) throws Exception {
			
			if(event.getName().equals(EVENT_DISABLED)) {
				bandBoxUsuario.setDisabled((boolean) event.getData());
				binder.loadComponent(bandBoxUsuario);
			}else if(event.getName().equals(EVENT_CLEAN)) {
				bandBoxUsuario.setValue(StringUtils.EMPTY);
				listBoxUsuario.clearSelection();
				listaUsuariosSeleccionados.clear();
			}else if(event.getName().equals(EVENT_VALIDAR)) {
				String mensajeError = (String) event.getData();
				throw new WrongValueException(bandBoxUsuario, mensajeError);
			}
		}
		
	}
}
