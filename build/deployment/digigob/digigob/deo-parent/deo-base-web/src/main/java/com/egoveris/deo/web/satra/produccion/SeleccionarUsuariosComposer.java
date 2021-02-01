package com.egoveris.deo.web.satra.produccion;

import com.egoveris.deo.web.satra.GEDOGenericForwardComposer;
import com.egoveris.deo.web.utils.UsuariosComparator;
import com.egoveris.sharedsecurity.base.model.Usuario;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.ListModels;
import org.zkoss.zul.Window;


public class SeleccionarUsuariosComposer extends GEDOGenericForwardComposer {

	private static final long serialVersionUID = 1L;

	private Window seleccionarUsuario;
	private Combobox usuario;	
	private String origen;

	public void doAfterCompose(Component component) throws Exception {

		super.doAfterCompose(component);
		origen=(String)Executions.getCurrent().getArg().get("origen");
		this.usuario.setModel(ListModels.toListSubModel(
				new ListModelList(this.getUsuarioService().obtenerUsuarios()), new UsuariosComparator(), 30));				
	}
		
	
    public void onClick$aceptar(){
    	if(this.usuario.getSelectedItem() != null){
    		Map<String, Object> map = new HashMap<String, Object>();
        	map.put("origen", origen);
        	
    		map.put("usuario", ((Usuario)this.usuario.getSelectedItem().getValue()).getUsername());
    		if(origen.equals("REDIRIGIR")){
    			Events.sendEvent(new Event(Events.ON_OK, this.self.getParent(),
        				map));	
    		}else{
    			Events.sendEvent(new Event(Events.ON_NOTIFY, this.self.getParent(),
        				map));	
    		}  
    		this.seleccionarUsuario.onClose();
    	}else{
    		throw new WrongValueException(this.usuario,
    				Labels.getLabel("ccoo.bandeja.seleccionarUusuario.valida"));
    	}   
    	
    	
    }
    
    public void onClick$cancelar(){
    	this.seleccionarUsuario.onClose();
    }

}
