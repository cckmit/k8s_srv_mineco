package com.egoveris.edt.web.pl.renderers;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.egoveris.edt.base.model.eu.usuario.RolDTO;

@SuppressWarnings("rawtypes")
public class RolRenderer implements ListitemRenderer {

  @Override
  public void render(Listitem item, Object data, int arg2) throws Exception {
    RolDTO rol = (RolDTO) data;

    Listcell rolCell = new Listcell(rol.getRolNombre());
    rolCell.setParent(item);
    

    item.addForward("onClick","./bandBoxRol","onElegirRol", rol);
  }

}
