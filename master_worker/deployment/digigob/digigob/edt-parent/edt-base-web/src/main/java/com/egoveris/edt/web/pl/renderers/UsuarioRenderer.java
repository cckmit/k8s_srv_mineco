package com.egoveris.edt.web.pl.renderers;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.egoveris.sharedsecurity.base.model.UsuarioReducido;

@SuppressWarnings("rawtypes")
public class UsuarioRenderer implements ListitemRenderer {

  @Override
  public void render(Listitem item, Object data, int arg2) throws Exception {
    UsuarioReducido usuario = (UsuarioReducido) data;

    Listcell username = new Listcell(usuario.getUsername());
    username.setParent(item);
    
    Listcell nombreApellido = new Listcell(usuario.getNombreApellido());
    nombreApellido.setParent(item);
    item.addForward("onClick","./bandBoxUsuario","onElegirUsuario", usuario);
  }

}
