package com.egoveris.edt.web.pl.renderers;

import com.egoveris.sharedsecurity.base.model.UsuarioReducido;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class JefeGobiernoItemRenderer implements ListitemRenderer {

  @Override
  public void render(Listitem item, Object data, int arg2) throws Exception {
    UsuarioReducido usuario = (UsuarioReducido) data;

    Listcell codigo = new Listcell(usuario.getUsername());
    codigo.setParent(item);

    Listcell nombre = new Listcell(usuario.getNombreApellido());
    nombre.setParent(item);
  }
}
