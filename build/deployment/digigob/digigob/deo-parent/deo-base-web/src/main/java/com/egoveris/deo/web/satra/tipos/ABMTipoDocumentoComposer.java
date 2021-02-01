package com.egoveris.deo.web.satra.tipos;

import com.egoveris.deo.base.exception.ExistenDocumentosDelTipoException;
import com.egoveris.deo.base.exception.ExistenDocumentosDelTipoJbpmException;
import com.egoveris.deo.base.service.FamiliaTipoDocumentoService;
import com.egoveris.deo.base.service.TipoDocumentoService;
import com.egoveris.deo.model.model.FamiliaTipoDocumentoDTO;
import com.egoveris.deo.model.model.TipoDocumentoDTO;
import com.egoveris.deo.model.model.TipoDocumentoReducidoDTO;
import com.egoveris.deo.web.satra.AbstractComposer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.Button;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listfooter;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ABMTipoDocumentoComposer extends AbstractComposer {

  private static final long serialVersionUID = -2194955781121747076L;

  private Window abmTipoDocumentoWindow;
  private Window hiddenView;
  private Listbox tiposDocumentoLb;
  private Image checked;
  private Image unChecked;
  private Button imagenTipoProduccion;

  @WireVariable("tipoDocumentoServiceImpl")
  private TipoDocumentoService tipoDocumentoService;
  @WireVariable("familiaTipoDocumentoServiceImpl")
  private FamiliaTipoDocumentoService familiaTipoDocumentoService;

  private TipoDocumentoReducidoDTO selectedTipoDocumento;
  private TipoDocumentoReducidoDTO tipoDocumento;
  private AnnotateDataBinder binder;
  private List<TipoDocumentoReducidoDTO> tiposDocumentos;
  private Textbox buscarPorNombreOAcronimoTb;
  private Listfooter cantidadTipoDocumentosLf;
  private boolean porAcronimo = true;
  private boolean existeFiltro = false;

  private static final Logger logger = LoggerFactory.getLogger(ABMTipoDocumentoComposer.class);

  public Listfooter getCantidadTipoDocumentosLf() {
    return cantidadTipoDocumentosLf;
  }

  public void setCantidadTipoDocumentosLf(Listfooter cantidadTipoDocumentosLf) {
    this.cantidadTipoDocumentosLf = cantidadTipoDocumentosLf;
  }

  private String userName;

  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    this.self.addEventListener(Events.ON_NOTIFY, new ABMTipoDocumentoOnNotifyWindowListener(this));
    this.tipoDocumento = getSelectedTipoDocumento();
  }

  public AnnotateDataBinder getBinder() {
    return binder;
  }

  public void setBinder(AnnotateDataBinder binder) {
    this.binder = binder;
  }

  public Listbox getTiposDocumentoLb() {
    return tiposDocumentoLb;
  }

  public void setTiposDocumentoLb(Listbox tiposDocumentoLb) {
    this.tiposDocumentoLb = tiposDocumentoLb;
  }

  public void setTipoDocumento(TipoDocumentoReducidoDTO tipoDocumento) {
    this.tipoDocumento = tipoDocumento;
  }

  public TipoDocumentoReducidoDTO getTipoDocumento() {
    return tipoDocumento;
  }

  public void setAbmTipoDocumentoWindow(Window abmTipoDocumentoWindow) {
    this.abmTipoDocumentoWindow = abmTipoDocumentoWindow;
  }

  public Window getAbmTipoDocumentoWindow() {
    return abmTipoDocumentoWindow;
  }

  public List<TipoDocumentoReducidoDTO> getTiposDocumentos() {
    if (this.tiposDocumentos == null || this.tiposDocumentos.isEmpty()) {
      this.tiposDocumentos = this.tipoDocumentoService.buscarTipoDocumentoReducido();
    }
    return this.tiposDocumentos;
  }

  public void setTiposDocumentos(List<TipoDocumentoReducidoDTO> tiposDocumentos) {
    this.tiposDocumentos = tiposDocumentos;
  }

  public TipoDocumentoReducidoDTO getSelectedTipoDocumento() {
    return selectedTipoDocumento;
  }

  public void setSelectedTipoDocumento(TipoDocumentoReducidoDTO selectedTipoDocumento) {
    this.selectedTipoDocumento = selectedTipoDocumento;
  }

  public Window getHiddenView() {
    return hiddenView;
  }

  public void setHiddenView(Window hiddenView) {
    this.hiddenView = hiddenView;
  }

  public void onDetalle() {
    if (this.hiddenView != null) {
      this.hiddenView.detach();
      TipoDocumentoDTO td = this.tipoDocumentoService
          .buscarTipoDocumentoPorId(selectedTipoDocumento.getId());
      List<String> listaNombreFamilias = familiaTipoDocumentoService
          .traerNombresFamiliasByIdTipoDocumento(td.getId());
      FamiliaTipoDocumentoDTO familiaTipoDocumento = familiaTipoDocumentoService
          .traerFamiliaTipoDocumentoByNombre(listaNombreFamilias.get(0));
      td.setFamilia(familiaTipoDocumento);
      HashMap<String, Object> hm = new HashMap<String, Object>();
      // probable crash. Left commented for help
      // if (!Hibernate.isInitialized(td
      // .getListaReparticiones())) {
      // Hibernate.initialize(td
      // .getListaReparticiones());
      // }
      hm.put("tipoDocumento", td);
      this.hiddenView = (Window) Executions.createComponents("/inbox/detalleDocumento.zul",
          this.self, hm);
      hiddenViewDoModal();
    } else {
      imposibleIniciarVista();
    }
  }

  public void onCreateFamilia() {
    if (this.hiddenView != null) {
      this.hiddenView.invalidate();
      this.hiddenView = (Window) Executions.createComponents(
          "/inbox/crearNuevaFamiliaTipoDocumento.zul", this.self, new HashMap<Object, Object>());
      this.hiddenView.setParent(this.self);
      this.hiddenView.setVisible(true);
      this.hiddenView.setClosable(true);
      hiddenViewDoModal();
    } else {
      imposibleIniciarVista();
    }
  }

  public void onPerfilesDeConversion() {
    if (this.hiddenView != null) {
      this.hiddenView.detach();
      HashMap<String, Object> hm = new HashMap<String, Object>();
      hm.put("tipoDocumento",
          this.tipoDocumentoService.buscarTipoDocumentoPorId(selectedTipoDocumento.getId()));
      this.hiddenView = (Window) Executions.createComponents("/inbox/perfilesDeConversion.zul",
          this.self, hm);
      hiddenViewDoModal();
    } else {
      imposibleIniciarVista();
    }
  }

  public void onEliminar() throws InterruptedException {
    Messagebox.show(Labels.getLabel("gedo.abmTipoDocumento.question.eliminarDocumento"),
        Labels.getLabel("gedo.general.question"), Messagebox.YES | Messagebox.NO,
        Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
          public void onEvent(Event evt) throws InterruptedException {
            switch (((Integer) evt.getData()).intValue()) {
            case Messagebox.YES:
              eliminarTipoDocumento();
              break;
            case Messagebox.NO:
              break;
            }
          }
        });
  }

  private void eliminarTipoDocumento() throws InterruptedException {
    try {
      TipoDocumentoDTO td = this.tipoDocumentoService
          .buscarTipoDocumentoPorId(selectedTipoDocumento.getId());
      this.tipoDocumentoService.eliminarTipoDocumento(td, userName);

      refreshTiposDocumento();

      Messagebox.show(Labels.getLabel("gedo.abmTipoDocComp.msgbox.elimConExito"),
          Labels.getLabel("gedo.general.information"), Messagebox.OK,
          Messagebox.INFORMATION);

    } catch (ExistenDocumentosDelTipoException edte) {
      logger.error("Error al eliminar tipo de documento " + edte.getMessage(), edte);
      Messagebox.show(
          Labels.getLabel("gedo.abmTipoDocumento.question.imposibleEliminar",
              new String[] { this.selectedTipoDocumento.getNombre() }),
          Labels.getLabel("gedo.general.error"), Messagebox.OK, Messagebox.ERROR);
    } catch (ExistenDocumentosDelTipoJbpmException edte) {
      logger.error("Error al eliminar tipo de documento " + edte.getMessage(), edte);
      Messagebox.show(
          Labels.getLabel("gedo.abmTipoDocumento.question.imposibleEliminarJbpmAsociado",
              new String[] { this.selectedTipoDocumento.getNombre() }),
          Labels.getLabel("gedo.general.error"), Messagebox.OK, Messagebox.ERROR);
    } catch (Exception e) {
      logger.error("Error al eliminar tipo de doc", e);
    }

  }

  public void onNuevoDocumentoCreado() {
    this.binder.loadComponent(this.tiposDocumentoLb);
  }

  public void onCreateNuevoTipoDocumento() {
    if (this.hiddenView != null) {
      this.hiddenView.detach();
      this.hiddenView = (Window) Executions.createComponents("/inbox/nuevoDocumento.zul",
          this.self, new HashMap<Object, Object>());
      this.hiddenView.setParent(this.self);
      this.hiddenView.setVisible(true);
      this.hiddenView.setClosable(true);
      hiddenViewDoModal();
    } else {
      imposibleIniciarVista();
    }
  }

  public void onReparticionesHabilitadas() {
    if (this.hiddenView != null) {
      this.hiddenView.detach();
      TipoDocumentoDTO td = this.tipoDocumentoService
          .buscarTipoDocumentoPorId(selectedTipoDocumento.getId());
      List<String> listaNombreFamilias = familiaTipoDocumentoService
          .traerNombresFamiliasByIdTipoDocumento(td.getId());
      FamiliaTipoDocumentoDTO familiaTipoDocumento = familiaTipoDocumentoService
          .traerFamiliaTipoDocumentoByNombre(listaNombreFamilias.get(0));
      td.setFamilia(familiaTipoDocumento);
      HashMap<String, Object> hm = new HashMap<String, Object>();
      hm.put("tipoDocumento", td);
      this.hiddenView = (Window) Executions.createComponents("/inbox/agregarReparticiones.zul",
          this.self, hm);
      hiddenViewDoModal();
    } else {
      imposibleIniciarVista();
    }
  }

  public void onTipoDocumentoMetadata() {
    TipoDocumentoDTO td = this.tipoDocumentoService
        .buscarTipoDocumentoPorId(selectedTipoDocumento.getId());
    List<String> listaNombreFamilias = familiaTipoDocumentoService
        .traerNombresFamiliasByIdTipoDocumento(td.getId());
    FamiliaTipoDocumentoDTO familiaTipoDocumento = familiaTipoDocumentoService
        .traerFamiliaTipoDocumentoByNombre(listaNombreFamilias.get(0));
    td.setFamilia(familiaTipoDocumento);
    HashMap<String, Object> hm = new HashMap<String, Object>();
    hm.put("tipoDocumento", td);
    if (this.hiddenView != null) {
      this.hiddenView.detach();
      this.hiddenView = (Window) Executions
          .createComponents("/inbox/datosPropiosDelTipoDocumento.zul", this.self, hm);
      this.hiddenView.setParent(this.self);
      this.hiddenView.setVisible(true);
      this.hiddenView.setMaximizable(true);
      this.hiddenView.setClosable(true);
      hiddenViewDoModal();
    } else {
      imposibleIniciarVista();
    }

  }

  public void refreshTiposDocumento() {

    this.tiposDocumentos = this.tipoDocumentoService.buscarTipoDocumentoReducido();
    tiposDocumentoLb.setModel(new BindingListModelList(tiposDocumentos, true));
    buscarPorNombreOAcronimoTb.setValue(null);
    this.tiposDocumentoLb.setActivePage(0);
    setExisteFiltro(false);
    binder.loadAll();

  }

  public void refreshTiposDocumentoFiltrado() {
    this.tiposDocumentos = this.tipoDocumentoService.buscarTipoDocumentoReducido();
    if (isPorAcronimo()) {
      onBuscarPorAcronimo();
    } else {
      onBuscarPorNombre();
    }
  }

  public void onClick$unChecked() throws InterruptedException {
    Messagebox.show(
        Labels.getLabel("gedo.abmTipoDocumento.desactivarTipoDocumento",
            new String[] { this.selectedTipoDocumento.getAcronimo() }),
        Labels.getLabel("gedo.general.question"), Messagebox.YES | Messagebox.NO,
        Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
          public void onEvent(Event evt) throws InterruptedException {
            switch (((Integer) evt.getData()).intValue()) {
            case Messagebox.YES:

              TipoDocumentoDTO td = tipoDocumentoService
                  .buscarTipoDocumentoPorId(selectedTipoDocumento.getId());
              td.setEstado(TipoDocumentoDTO.ESTADO_INACTIVO);
              tipoDocumentoService.modificarTipoDocumento(td, userName);
              if (buscarPorNombreOAcronimoTb.getValue() != null) {
                refreshTiposDocumentoFiltrado();
              } else {
                refreshTiposDocumento();
              }
              break;
            case Messagebox.NO:
              break;
            }
          }
        });
    return;
  }

  public void onClick$checked() throws InterruptedException {
    Messagebox.show(
        Labels.getLabel("gedo.abmTipoDocumento.activarTipoDocumento",
            new String[] { this.selectedTipoDocumento.getAcronimo() }),
        Labels.getLabel("gedo.general.question"), Messagebox.YES | Messagebox.NO,
        Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
          public void onEvent(Event evt) throws InterruptedException {
            switch (((Integer) evt.getData()).intValue()) {
            case Messagebox.YES:
              TipoDocumentoDTO td = tipoDocumentoService
                  .buscarTipoDocumentoPorId(selectedTipoDocumento.getId());
              td.setEstado(TipoDocumentoDTO.ESTADO_ACTIVO);
              tipoDocumentoService.modificarTipoDocumento(td, userName);
              if (buscarPorNombreOAcronimoTb.getValue() != null) {
                refreshTiposDocumentoFiltrado();
              } else {
                refreshTiposDocumento();
              }
              break;
            case Messagebox.NO:
              break;
            }
          }
        });
    return;
  }

  public void onCreatePerfilConversion() {
    if (this.hiddenView != null) {
      this.hiddenView.detach();
      this.hiddenView = (Window) Executions.createComponents("/inbox/nuevoPerfilConversion.zul",
          this.self, null);
      hiddenViewDoModal();
    } else {
      imposibleIniciarVista();
    }
  }

  private void imposibleIniciarVista() {
    Messagebox.show(Labels.getLabel("gedo.general.imposibleIniciarVista"),
        Labels.getLabel("gedo.general.error"), Messagebox.OK, Messagebox.ERROR);
  }

  private void hiddenViewDoModal() {
    this.hiddenView.setPosition("center");
    this.hiddenView.doModal();
  }

  public Image getChecked() {
    return checked;
  }

  public void setChecked(Image checked) {
    this.checked = checked;
  }

  public Image getUnChecked() {
    return unChecked;
  }

  public void setUnChecked(Image unChecked) {
    this.unChecked = unChecked;
  }

  public Button getImagenTipoProduccion() {
    return imagenTipoProduccion;
  }

  public void setImagenTipoProduccion(Button imagenTipoProduccion) {
    this.imagenTipoProduccion = imagenTipoProduccion;
  }

  public Textbox getBuscarPorNombreOAcronimoTb() {
    return buscarPorNombreOAcronimoTb;
  }

  public void setBuscarPorNombreOAcronimoTb(Textbox buscarPorNombreOAcronimoTb) {
    this.buscarPorNombreOAcronimoTb = buscarPorNombreOAcronimoTb;
  }

  /**
   * busqueda de documento por nombre
   */
  public void onBuscarPorNombre() {
    setExisteFiltro(true);
    this.tipoDocumento = getSelectedTipoDocumento();
    List<TipoDocumentoReducidoDTO> lista = new ArrayList<TipoDocumentoReducidoDTO>();
    for (TipoDocumentoReducidoDTO td : tiposDocumentos) {
      if (td.getNombre().toLowerCase()
          .contains(buscarPorNombreOAcronimoTb.getValue().toLowerCase())) {
        lista.add(td);
      }
    }
    this.porAcronimo = false;
    setListaNueva(lista);
  }

  /**
   * busqueda de documento por acronimo
   */
  public void onBuscarPorAcronimo() {
    setExisteFiltro(true);
    List<TipoDocumentoReducidoDTO> lista = new ArrayList<TipoDocumentoReducidoDTO>();
    for (TipoDocumentoReducidoDTO td : tiposDocumentos) {
      if (td.getAcronimo().toLowerCase()
          .contains(buscarPorNombreOAcronimoTb.getValue().toLowerCase())) {
        lista.add(td);
      }
    }
    setListaNueva(lista);
  }

  public boolean isPorAcronimo() {
    return porAcronimo;
  }

  public void setPorAcronimo(boolean porAcronimo) {
    this.porAcronimo = porAcronimo;
  }

  private void setListaNueva(List<TipoDocumentoReducidoDTO> lista) {

	  List<TipoDocumentoReducidoDTO> auxList = new ArrayList();
	  auxList.addAll(lista);
	    if (lista.size() > 0) {
	    this.tiposDocumentos.clear();
	    this.tiposDocumentos.addAll(auxList);
	      tiposDocumentoLb.setModel(new BindingListModelList(lista, true));
	      cantidadTipoDocumentosLf.setLabel(String.valueOf(tiposDocumentoLb.getItemCount()));
	    } else {
	      Messagebox.show(Labels.getLabel("gedo.abmTipoDocComp.msgbox.noEcontroDoc"));
	      tiposDocumentoLb.setModel(new BindingListModelList(tiposDocumentos, true));
	
	    }

    this.binder.loadAll();
  }

  public void onLimpiarBusqueda() {
	this.tiposDocumentos.clear();
	this.tiposDocumentos = getTiposDocumentos();
    existeFiltro = false;
    buscarPorNombreOAcronimoTb.setText("");
    setListaNueva(tiposDocumentos);
  }

  public boolean isExisteFiltro() {
    return existeFiltro;
  }

  public void setExisteFiltro(boolean existeFiltro) {
    this.existeFiltro = existeFiltro;
  }
}

final class ABMTipoDocumentoOnNotifyWindowListener implements EventListener {
  private ABMTipoDocumentoComposer composer;

  public ABMTipoDocumentoOnNotifyWindowListener(ABMTipoDocumentoComposer comp) {
    this.composer = comp;
  }

  public void onEvent(Event event) throws Exception {
    if (event.getName().equals(Events.ON_NOTIFY)) {
      composer.setExisteFiltro(event.getTarget() != null && event.getData() == null ? false
          : composer.isExisteFiltro());
      if (composer.isExisteFiltro()) {
        this.composer.refreshTiposDocumentoFiltrado();
      } else {
        this.composer.refreshTiposDocumento();
      }
    }
  }
}
