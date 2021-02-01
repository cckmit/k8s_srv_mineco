package com.egoveris.deo.web.satra.produccion;

import com.egoveris.deo.model.model.UsuarioExternoDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.deo.web.satra.GEDOGenericForwardComposer;
import com.egoveris.deo.web.utils.UsuariosComparator;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.Usuario;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.ListModels;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;


public class DefinirDestinatariosComposer extends GEDOGenericForwardComposer {

	private static final long serialVersionUID = -6699352350050752828L;

	private static final Logger logger = LoggerFactory
			.getLogger(DefinirDestinatariosComposer.class);
	private Window definirDestinatariosWindow;
	private Window seleccionarUsuarioWindow;
	private Window seleccionarExternoWindow;
	private Combobox usuarioDestinatario;
	private Combobox usuarioCopia;
	private Combobox usuarioCopiaOculta;
	private Textbox mensajeADestinatario;
	private AnnotateDataBinder binder;
	
	private String mensajeAnterior;
	private List<String> destinatariosList;
	private List<String> destinatariosCopiaList;
	private List<String> destinatariosCopiaOcultaList;
	private List<UsuarioExternoDTO> destinatariosExternosList;
	private Collection<Usuario> usuariosSadeList;
	
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component component) throws Exception {

		super.doAfterCompose(component);		
		component.addEventListener(Events.ON_NOTIFY,
				new DefinirDestinatariosComposerListener(this));
		this.usuarioDestinatario.setModel(ListModels.toListSubModel(
				new ListModelList(this.getUsuarioService().obtenerUsuarios()), new UsuariosComparator(), 30));
		this.usuarioCopia.setModel(ListModels.toListSubModel(
				new ListModelList(this.getUsuarioService().obtenerUsuarios()), new UsuariosComparator(), 30));
		this.usuarioCopiaOculta.setModel(ListModels.toListSubModel(
				new ListModelList(this.getUsuarioService().obtenerUsuarios()), new UsuariosComparator(), 30));
				
		mensajeAnterior = (String)Executions.getCurrent().getArg().get("mensaje");
		this.mensajeADestinatario.setText(mensajeAnterior);				
		
		this.usuariosSadeList=  this.getUsuarioService().obtenerUsuarios();						
		this.destinatariosList = (List<String>) Executions.getCurrent().getArg().get("destinatario");
		this.destinatariosCopiaList = (List<String>) Executions.getCurrent().getArg().get("destinatarioCopia");
		this.destinatariosCopiaOcultaList = (List<String>) Executions.getCurrent().getArg().get("destinatarioCopiaOculta");
		this.destinatariosExternosList = (List<UsuarioExternoDTO>) Executions.getCurrent().getArg().get("destinatariosExternos");
		cargarDatosDestinatarios(this.destinatariosList,this.usuarioDestinatario);
		cargarDatosDestinatarios(this.destinatariosCopiaList, this.usuarioCopia);
		cargarDatosDestinatarios(this.destinatariosCopiaOcultaList, this.usuarioCopiaOculta);
		
		this.binder = new AnnotateDataBinder(component);
		this.binder.loadAll();
	}

	private void cargarDatosDestinatarios(List<String> listaDestinatarios, Combobox comboDestinatarios){
		String destinatarios = new String();
		for(String usuario : listaDestinatarios){
			if(listaDestinatarios.size()<2){
				destinatarios = destinatarios.concat(usuario);
			}else{
				destinatarios = destinatarios.concat(usuario) + ",";
			}			 
		}
		comboDestinatarios.setValue(destinatarios);		
	}
	
	public void onClick$destinatarioButton() throws InterruptedException{		
		seleccionarDestinatario("DESTINATARIO");
	}
	
	public void onClick$copiaButton() throws InterruptedException{		
		seleccionarDestinatario("COPIA");
	}	
	
	public void onClick$copiaOcultaButton() throws InterruptedException{				
		seleccionarDestinatario("COPIA-OCULTA");
	}


	public void seleccionarDestinatario(String origen) throws InterruptedException{
		Map<String,String> map = new HashMap<String, String>();
		map.put("origen", origen);
		this.seleccionarUsuarioWindow = (Window) Executions.createComponents(
				"/co/seleccionarUsuario.zul", this.self, map);
		this.seleccionarUsuarioWindow.setClosable(true);
		try {
			this.seleccionarUsuarioWindow.setPosition("center");
			this.seleccionarUsuarioWindow.doModal();
		} catch (SuspendNotAllowedException e) {
			logger.error("Exception: " + e.getMessage(), e);
		}
	}	
		
	private void cargarDestinatarios(String usuario,Combobox comboDestinatarios){
		String destinatarios = new String();		
		if(comboDestinatarios.getValue().isEmpty()){			
			destinatarios = usuario;			
		}else{
			if (comboDestinatarios.getValue().endsWith(",")) {
				destinatarios = comboDestinatarios.getValue();
				destinatarios = destinatarios.concat(usuario);
			} else {
				destinatarios = comboDestinatarios.getValue() + ",";
				comboDestinatarios.setValue("");
				destinatarios = destinatarios.concat(usuario);
			}																					
		}	
		comboDestinatarios.setValue(destinatarios);
	} 
	
	public void onSelectItem(ForwardEvent event) throws SecurityNegocioException{			
		Combobox comboUsuarios = (Combobox) event.getOrigin().getTarget();		
		if(comboUsuarios.getSelectedItem() != null){
			Usuario usuarioReducido = (Usuario) comboUsuarios.getSelectedItem().getValue();
		  comboUsuarios.setValue(usuarioReducido.getUsername());	
		}		
	}
	
	public void onNotaExterna() throws InterruptedException{

		Map<String,Object> map = new HashMap<String, Object>();
		map.put("destinosExternos", this.destinatariosExternosList);
		this.seleccionarExternoWindow = (Window) Executions.createComponents(
				"/co/seleccionarUsuarioExterno.zul", this.self, map);
		this.seleccionarExternoWindow.setClosable(true);
		try {
			this.seleccionarExternoWindow.setPosition("center");
			this.seleccionarExternoWindow.doModal();
		} catch (SuspendNotAllowedException e) {
			logger.error("Exception: " + e.getMessage(), e);
		}
	}
	
    public void onClick$aceptar() throws InterruptedException{
    				  
		guardarDestinatarios(usuarioDestinatario, destinatariosList);
		guardarDestinatarios(usuarioCopia, destinatariosCopiaList);
		guardarDestinatarios(usuarioCopiaOculta, destinatariosCopiaOcultaList);

		if (validarRepetidosMismaLista(destinatariosList, destinatariosList)) {
			throw new WrongValueException(this.usuarioDestinatario,
					Labels.getLabel("ccoo.definirDestinatario.validaRepetido"));
		}
		if (validarRepetidosMismaLista(destinatariosCopiaList,
				destinatariosCopiaList)) {
			throw new WrongValueException(this.usuarioCopia,
					Labels.getLabel("ccoo.definirDestinatario.validaRepetido"));
		}
		if (validarRepetidosMismaLista(destinatariosCopiaOcultaList,
				destinatariosCopiaOcultaList)) {
			throw new WrongValueException(this.usuarioCopiaOculta,
					Labels.getLabel("ccoo.definirDestinatario.validaRepetido"));
		}
		validarRepetidos();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("origen", Constantes.EVENTO_DEFINIR_DESTINATARIOS);
		map.put("destinatario", this.destinatariosList);
		map.put("destinatarioCopia", this.destinatariosCopiaList);
		map.put("destinatarioCopiaOculta", this.destinatariosCopiaOcultaList);
		map.put("destinatariosExternos", this.destinatariosExternosList);
		map.put("mensaje", this.mensajeADestinatario.getValue());
		Events.sendEvent(new Event(Events.ON_NOTIFY, this.self.getParent(), map));

		this.definirDestinatariosWindow.onClose();	
	
    }
	
    private void validarRepetidos(){
    	
    	for(String destinatario : destinatariosList){
    		for(String destinoCopia : destinatariosCopiaList){
    			if(destinatario.equals(destinoCopia)){    				
    				throw new WrongValueException(
    						this.usuarioCopia,
    						Labels.getLabel("ccoo.definirDestinatario.validaRepetido"));    				
    			}
    		}
    	}
    	
    	for(String destinatario : destinatariosList){
    		for(String destinoCopiaOculta : destinatariosCopiaOcultaList){
    			if(destinatario.equals(destinoCopiaOculta)){    				
    				throw new WrongValueException(
    						this.usuarioCopiaOculta,
    						Labels.getLabel("ccoo.definirDestinatario.validaRepetido"));    				
    			}
    		}
    	}
    	
    	for(String destinatarioCopiaOculta : destinatariosCopiaOcultaList){
    		for(String destinoCopia : destinatariosCopiaList){
    			if(destinatarioCopiaOculta.equals(destinoCopia)){    				
    				throw new WrongValueException(
    						this.usuarioCopiaOculta,
    						Labels.getLabel("ccoo.definirDestinatario.validaRepetido"));    				
    			}
    		}
    	}
    	    	
    }
    
    private boolean validarRepetidosMismaLista(List<String> listaUno, List<String> listaDos){
		boolean mismoNombre = false;		
		
		for (String destinatario : listaUno) {
			mismoNombre = false;
			for (String destino : listaDos) {
				if (destinatario.equals(destino) && mismoNombre) {
					return true;
				}else {
					if(destinatario.equals(destino)){
					   mismoNombre =true;
					}					
				}
			}
		}
		return false;
	}
    
    private void guardarDestinatarios(Combobox comboUsuarios, List<String> listaUsuarios){
    	listaUsuarios.clear();
    	if(!comboUsuarios.getValue().isEmpty()){
    		String[] destinatarios = comboUsuarios.getValue().split(",");
    		for(int i=0;i<destinatarios.length;i++){
    			if(!destinatarios[i].equals("")){
    				if(!validarDestinatarios(destinatarios[i].trim())){
        				listaUsuarios.clear();    				
    					throw new WrongValueException(comboUsuarios,
    							Labels.getLabel("gedo.defDestinatarios.exception.destIngresado") + " " +
    							" \"" + destinatarios[i] + "\" " + Labels.getLabel("gedo.defDestinatarios.exception.noValido"));
        			}else{
        				listaUsuarios.add(destinatarios[i].trim());
        			}	
    			}    			    		    		
    		}
    	}    	    	    	
    }
    
    private boolean validarDestinatarios(String usuario){
		for(Usuario usuarioItem : this.usuariosSadeList ){
			if(usuarioItem.getUsername().equals(usuario)){
				return true;
			}
		}		
		return false;
	}
    
	public void onClick$cancelar() {
		
		Map<String,Object> map = new HashMap<String, Object>();   
    	map.put("origen",Constantes.EVENTO_DEFINIR_DESTINATARIOS);
    	map.put("destinatario", this.destinatariosList);
    	map.put("destinatarioCopia", this.destinatariosCopiaList);
    	map.put("destinatarioCopiaOculta", this.destinatariosCopiaOcultaList);
    	map.put("destinatariosExternos",this.destinatariosExternosList);
    	map.put("mensaje",this.mensajeAnterior);
    	Events.sendEvent(new Event(Events.ON_NOTIFY, this.self.getParent(),map));
    	
		this.definirDestinatariosWindow.onClose();
	}
	
	final class DefinirDestinatariosComposerListener implements
			EventListener {
		private DefinirDestinatariosComposer composer;

		public DefinirDestinatariosComposerListener(
				DefinirDestinatariosComposer comp) {
			this.composer = comp;
		}

		@SuppressWarnings("unchecked")
		public void onEvent(Event event) throws Exception {
			if (event.getName().equals(Events.ON_NOTIFY)) {
				if (event.getData() != null) {
					Map<String,Object> map =  (Map<String, Object>) event.getData();
					if (map.get("origen").equals("DESTINATARIO")) {
						cargarDestinatarios((String) map.get("usuario"),
								usuarioDestinatario);
					} else {
						if (map.get("origen").equals("COPIA")) {
							cargarDestinatarios((String) map.get("usuario"),
									usuarioCopia);
						} else {
							if (map.get("origen").equals("EXTERNO")) {
								composer.destinatariosExternosList = (List<UsuarioExternoDTO>) map
										.get("destinosExternos");
							} else {
								cargarDestinatarios(
										(String) map.get("usuario"),
										usuarioCopiaOculta);
							}
						}
					}			
				}
			}
		}
	}
}
