package com.egoveris.edt.web.pl.renderers;

import org.zkoss.util.resource.Labels;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.egoveris.edt.base.model.eu.usuario.RolDTO;

@SuppressWarnings("rawtypes")
public class RolAsignadoRenderer implements ListitemRenderer {

	private boolean activarAccion;
	
	public RolAsignadoRenderer() {
		super();
		this.activarAccion = false;
		
	}
	
	public RolAsignadoRenderer(Boolean activarAccion) {
		super();
		this.activarAccion = activarAccion;
	}
	
  @Override
  public void render(Listitem item, Object data, int arg2) throws Exception {
    RolDTO rol = (RolDTO) data;

    Listcell rolCell = new Listcell(rol.getRolNombre());
    rolCell.setParent(item);
    
    if(activarAccion) {
      Listcell accionCell = new Listcell();
      accionCell.setParent(item);
      
      Image quitar = new Image("/imagenes/Eliminar.png");

      quitar.setTooltiptext(Labels.getLabel("edt.webapp.altausuario.rolesAsignados.borrar"));
      quitar.addForward("onClick","","onQuitarRol", rol);
      quitar.setParent(accionCell);
    }

  }

}
