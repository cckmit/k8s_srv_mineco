package com.egoveris.edt.web.admin.pl;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;

import com.egoveris.sharedsecurity.base.model.UsuarioBaseDTO;

public class DatosPersonalesTabComposer extends GenericForwardComposer {

	
  private static final long serialVersionUID = -5362549948387867821L;

  private UsuarioBaseDTO usuario;
  private List<String> listaTipo = new ArrayList<String>();

  @Override
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    // Map<String, Object> parametros;
    //
    // parametros = Executions.getCurrent().getArg();
    // if(!parametros.isEmpty()){
    // usuario = (UsuarioSadeDTO) parametros.get("usuario");
    // } else {
    // usuario = new UsuarioSadeDTO();
    //
    // listaTipo.add("Central");
    // listaTipo.add("Local");
    // listaTipo.add("Usuario");
    //
    // usuario.setNombre("agustin");
    // usuario.setApellido("mandaglio");
    // usuario.setMail("a.mandaglio");
    // usuario.setUid("amandagl");
    // usuario.setTipo("Central");
    // }
  }

  public void setUsuario(UsuarioBaseDTO usuario) {
    this.usuario = usuario;
  }

  public UsuarioBaseDTO getUsuario() {
    return usuario;
  }

  public void setListaTipo(List<String> listaTipo) {
    this.listaTipo = listaTipo;
  }

  public List<String> getListaTipo() {
    return listaTipo;
  }
}
