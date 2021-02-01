package com.egoveris.deo.web.satra.produccion;

import com.egoveris.deo.model.model.UsuarioExternoDTO;
import com.egoveris.deo.web.satra.GEDOGenericForwardComposer;
import com.egoveris.sharedsecurity.base.model.Usuario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class SeleccionarUsuarioExternoComposer extends GEDOGenericForwardComposer {

  private static final long serialVersionUID = 1L;

  private Window seleccionarUsuarioExternoWindow;
  private Textbox nombreUsuario;
  private Textbox destinoUsuario;
  private AnnotateDataBinder usuarioExternoBinder;

  private UsuarioExternoDTO selectedUsuario;
  private List<UsuarioExternoDTO> listaUsuariosExternos;
  private List<UsuarioExternoDTO> listaUsuariosExternosOriginal;

  @SuppressWarnings("unchecked")
  public void doAfterCompose(Component component) throws Exception {

    super.doAfterCompose(component);
    this.listaUsuariosExternos = new ArrayList<UsuarioExternoDTO>();
    this.listaUsuariosExternosOriginal = new ArrayList<UsuarioExternoDTO>();
    this.listaUsuariosExternos = (List<UsuarioExternoDTO>) Executions.getCurrent().getArg()
        .get("destinosExternos");
    for (UsuarioExternoDTO ex : listaUsuariosExternos) {
      listaUsuariosExternosOriginal.add(ex);
    }

    this.usuarioExternoBinder = new AnnotateDataBinder(seleccionarUsuarioExternoWindow);
    this.usuarioExternoBinder.loadAll();
  }

  public void onSelectItem(ForwardEvent event) {
    Combobox comboUsuarios = (Combobox) event.getOrigin().getTarget();
    if (comboUsuarios.getSelectedItem() != null) {
      comboUsuarios.setValue(((Usuario) comboUsuarios.getSelectedItem().getValue()).getUsername());
    }
  }

  public void onAgregarUsuario() {

    if (this.nombreUsuario.getValue().trim().isEmpty()) {
      throw new WrongValueException(this.nombreUsuario,
          Labels.getLabel("ccoo.sectorMesa.usuario.valida"));
    }

    if (this.destinoUsuario.getValue().trim().isEmpty()) {
      throw new WrongValueException(this.destinoUsuario,
          Labels.getLabel("ccoo.sectorMesa.destino.valida"));
    }

    UsuarioExternoDTO nuevoUsuario = new UsuarioExternoDTO();
    nuevoUsuario.setNombre(this.nombreUsuario.getValue().trim());
    nuevoUsuario.setDestino(this.destinoUsuario.getValue().trim());

    limpiarPantalla();

    this.listaUsuariosExternos.add(nuevoUsuario);
    this.usuarioExternoBinder.loadAll();
  }

  private void limpiarPantalla() {

    this.nombreUsuario.setValue("");
    this.destinoUsuario.setValue("");
  }

  public void onEliminarUsuario() {
    this.listaUsuariosExternos.remove(this.selectedUsuario);
    this.usuarioExternoBinder.loadAll();
  }

  public void onClick$aceptar() throws InterruptedException {

    Map<String, Object> map = new HashMap<String, Object>();
    map.put("origen", "EXTERNO");
    map.put("destinosExternos", this.listaUsuariosExternos);
    Events.sendEvent(new Event(Events.ON_NOTIFY, this.self.getParent(), map));

    this.seleccionarUsuarioExternoWindow.onClose();
  }

  public void onClick$cancelar() {

    Map<String, Object> map = new HashMap<String, Object>();
    map.put("origen", "EXTERNO");
    map.put("destinosExternos", this.listaUsuariosExternosOriginal);
    Events.sendEvent(new Event(Events.ON_NOTIFY, this.self.getParent(), map));

    this.seleccionarUsuarioExternoWindow.onClose();
  }

  public List<UsuarioExternoDTO> getUsuariosExternos() {
    return listaUsuariosExternos;
  }

  public void setUsuariosExternos(List<UsuarioExternoDTO> usuariosExternos) {
    this.listaUsuariosExternos = usuariosExternos;
  }

  public UsuarioExternoDTO getSelectedUsuario() {
    return selectedUsuario;
  }

  public void setSelectedUsuario(UsuarioExternoDTO selectedUsuario) {
    this.selectedUsuario = selectedUsuario;
  }

}
