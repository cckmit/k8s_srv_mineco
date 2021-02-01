package com.egoveris.deo.web.satra.produccion;

import com.egoveris.deo.base.service.FamiliaTipoDocumentoService;
import com.egoveris.deo.model.model.FamiliaTipoDocumentoDTO;
import com.egoveris.deo.web.satra.GEDOGenericForwardComposer;
import com.egoveris.deo.web.satra.renderers.InboxFamiliaTipoDocumentoRenderer;
import com.egoveris.deo.web.utils.ComboBoxFamiliaComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class NuevaFamiliaTipoDocumentoComposer extends GEDOGenericForwardComposer {

  /**
  * 
  */
  private static final long serialVersionUID = -6749792007557977128L;
  private static final Logger logger = LoggerFactory
      .getLogger(NuevaFamiliaTipoDocumentoComposer.class);

  @Autowired
  private Window nuevaFamiliaTipoDocumentoWindow;
  private Window editarFamiliaTipoDocumentoWindow;
  private List<FamiliaTipoDocumentoDTO> listaFamilias;
  private List<FamiliaTipoDocumentoDTO> listaFamiliasEliminar;
  private List<FamiliaTipoDocumentoDTO> listaFamiliasAgregar;
  private FamiliaTipoDocumentoDTO selectedFamilia;
  @WireVariable("familiaTipoDocumentoServiceImpl")
  private FamiliaTipoDocumentoService familiaTipoDocumentoService;
  @Autowired
  private Listbox familiasDocumentos;

  @Autowired
  private Textbox nfamilia;
  @Autowired
  private AnnotateDataBinder binder;
  @Autowired
  private Textbox dfamilia;
  private String usuario;

  @Override
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    this.self.addEventListener(Events.ON_NOTIFY,
        new NuevaFamiliaTipoDocumentoWindowListener(this));
    this.usuario = (String) Executions.getCurrent().getDesktop().getSession()
        .getAttribute("userName");

    this.listaFamilias = this.familiaTipoDocumentoService.traerfamilias();
    this.familiasDocumentos.setItemRenderer(new InboxFamiliaTipoDocumentoRenderer(this));
    Collections.sort(this.listaFamilias, new ComboBoxFamiliaComparator());
    this.binder = new AnnotateDataBinder(comp);
    this.binder.loadAll();
  }

  public void refreshTiposDocumento() {
    this.binder.loadComponent(this.familiasDocumentos);
  }

  public void refreshTiposDocumentoUpdate() {
    this.listaFamilias = (ArrayList<FamiliaTipoDocumentoDTO>) this.familiaTipoDocumentoService
        .traerfamilias();
    familiasDocumentos.setItemRenderer(new InboxFamiliaTipoDocumentoRenderer(this));
    Collections.sort(this.listaFamilias, new ComboBoxFamiliaComparator());
    this.binder.loadComponent(this.familiasDocumentos);
  }

  public void onGuardar() throws InterruptedException {
    try {

      this.familiaTipoDocumentoService.guardarFamilia(this.listaFamiliasAgregar,
          this.listaFamiliasEliminar, this.listaFamilias.get(0), usuario);

      Messagebox.show(Labels.getLabel("gedo.familiaTipoDocumento.guardar"),
          Labels.getLabel("gedo.abmTipoDocumento.informacion"), Messagebox.OK,
          Messagebox.INFORMATION);
      Events.sendEvent(new Event(Events.ON_NOTIFY, this.self.getParent()));
      this.nuevaFamiliaTipoDocumentoWindow.detach();
    } catch (Exception e) {
      logger.error("Error al guardar familia de tipo de documento " + e.getMessage(), e);
      Messagebox.show(Labels.getLabel("gedo.familiaTipoDocumento.error.guardar"),
          Labels.getLabel("gedo.inbox.error.title"), Messagebox.OK, Messagebox.ERROR);
    }
  }

  private void validarEntrada() {
    String nombre = this.nfamilia.getValue();
    if (yaExisteNombre(nombre)) {
      throw new WrongValueException(this.nfamilia,
          Labels.getLabel("gedo.familiaTipoDocumento.error.nombreExiste"));
    }
    if (this.nfamilia.getValue().trim().isEmpty()) {
      throw new WrongValueException(this.nfamilia,
          Labels.getLabel("gedo.familiaTipoDocumento.error.nombreRequerido"));
    }
    if (this.dfamilia.getValue().trim().isEmpty()) {
      throw new WrongValueException(this.dfamilia,
          Labels.getLabel("gedo.familiaTipoDocumento.error.descripcionRequerida"));
    }

  }

  private boolean yaExisteNombre(String nombre) {
    for (FamiliaTipoDocumentoDTO aux : this.listaFamilias) {
      if (aux.getNombre().trim().equalsIgnoreCase(nombre.trim())) {
        return true;
      }
    }
    return false;
  }

  public void onClick$agregar() {
    validarEntrada();
    if (this.listaFamiliasAgregar == null) {
      this.listaFamiliasAgregar = new ArrayList<FamiliaTipoDocumentoDTO>();
    }
    FamiliaTipoDocumentoDTO familiaTipoDocumento = new FamiliaTipoDocumentoDTO();
    familiaTipoDocumento.setDescripcion(this.dfamilia.getValue());
    familiaTipoDocumento.setNombre(this.nfamilia.getValue());
    this.listaFamiliasAgregar.add(familiaTipoDocumento);
    this.listaFamilias.add(familiaTipoDocumento);
    this.nfamilia.setText("");
    this.dfamilia.setText("");
    this.binder.loadComponent(this.familiasDocumentos);
  }

  public void onCancelar() throws InterruptedException {
	 this.nuevaFamiliaTipoDocumentoWindow.detach();
  }

  public void onEliminarFamilia() {
    if (this.listaFamiliasEliminar == null) {
      this.listaFamiliasEliminar = new ArrayList<FamiliaTipoDocumentoDTO>();
    }
    this.listaFamiliasEliminar.add(this.selectedFamilia);
    this.listaFamilias.remove(this.selectedFamilia);
    this.binder.loadComponent(this.familiasDocumentos);
  }

  public void onEditarFamilia() {
    HashMap<Object, Object> mapSelectedItem = new HashMap<Object, Object>();
    mapSelectedItem.put("ItemSeleted", this.selectedFamilia);

    if (this.editarFamiliaTipoDocumentoWindow != null) {
      this.editarFamiliaTipoDocumentoWindow.detach();
      this.editarFamiliaTipoDocumentoWindow = (Window) Executions
          .createComponents("/inbox/editarFamiliaTipoDocumento.zul", this.self, mapSelectedItem);
      this.editarFamiliaTipoDocumentoWindow.setParent(this.self);
      this.editarFamiliaTipoDocumentoWindow.setPosition("center");
      this.editarFamiliaTipoDocumentoWindow.setVisible(true);
      this.editarFamiliaTipoDocumentoWindow.setClosable(true);
      this.editarFamiliaTipoDocumentoWindow.doModal();
    } else {
      Messagebox.show(Labels.getLabel("gedo.general.imposibleIniciarVista"),
          Labels.getLabel("gedo.general.error"), Messagebox.OK, Messagebox.ERROR);
    }
  }

  public void closeWindow() {
    this.nuevaFamiliaTipoDocumentoWindow.detach();
  }

  public FamiliaTipoDocumentoDTO getSelectedFamilia() {
    return selectedFamilia;
  }

  public void setSelectedFamilia(FamiliaTipoDocumentoDTO selectedFamilia) {
    this.selectedFamilia = selectedFamilia;
  }

  public List<FamiliaTipoDocumentoDTO> getListaFamilias() {
    return listaFamilias;
  }

  public void setListaFamilias(List<FamiliaTipoDocumentoDTO> listaFamilias) {
    this.listaFamilias = listaFamilias;
  }

  public List<FamiliaTipoDocumentoDTO> getListaFamiliasEliminar() {
    return listaFamiliasEliminar;
  }

  public void setListaFamiliasEliminar(List<FamiliaTipoDocumentoDTO> listaFamiliasEliminar) {
    this.listaFamiliasEliminar = listaFamiliasEliminar;
  }

  public List<FamiliaTipoDocumentoDTO> getListaFamiliasAgregar() {
    return listaFamiliasAgregar;
  }

  public void setListaFamiliasAgregar(List<FamiliaTipoDocumentoDTO> listaFamiliasAgregar) {
    this.listaFamiliasAgregar = listaFamiliasAgregar;
  }

  public Listbox getFamiliasDocumentos() {
    return familiasDocumentos;
  }

  public void setFamiliasDocumentos(Listbox familiasDocumentos) {
    this.familiasDocumentos = familiasDocumentos;
  }

  public Window getEditarFamiliaTipoDocumentoWindow() {
    return editarFamiliaTipoDocumentoWindow;
  }

  public void setEditarFamiliaTipoDocumentoWindow(Window editarFamiliaTipoDocumentoWindow) {
    this.editarFamiliaTipoDocumentoWindow = editarFamiliaTipoDocumentoWindow;
  }
}

final class NuevaFamiliaTipoDocumentoWindowListener implements EventListener {
  private NuevaFamiliaTipoDocumentoComposer composer;

  public NuevaFamiliaTipoDocumentoWindowListener(NuevaFamiliaTipoDocumentoComposer comp) {
    this.composer = comp;
  }

  public void onEvent(Event event) throws Exception {
    if (event.getName().equals(Events.ON_NOTIFY)) {
      this.composer.refreshTiposDocumentoUpdate();
    }
  }
}