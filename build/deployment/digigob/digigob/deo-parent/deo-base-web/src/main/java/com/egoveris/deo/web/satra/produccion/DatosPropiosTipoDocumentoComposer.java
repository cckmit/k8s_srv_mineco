package com.egoveris.deo.web.satra.produccion;

import com.egoveris.deo.base.service.TipoDocumentoService;
import com.egoveris.deo.model.model.MetadataDTO;
import com.egoveris.deo.model.model.TipoDocumentoDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.deo.web.satra.GEDOGenericForwardComposer;
import com.egoveris.deo.web.satra.renderers.DatosTipoDocumentoRenderer;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class DatosPropiosTipoDocumentoComposer extends GEDOGenericForwardComposer {

  /**
  * 
  */
  private static final long serialVersionUID = -7353673854647965652L;
  /**
  * 
  */
  /**
   * @author jnorvert
   * 
   *         Se realizan todas las operaciones que se encuentran en la ventana
   *         de los datos propios de la trata
   */

  private Window datosPropiosTipoDocumentoWindow;
  private Grid grillaDatos;
  private Textbox nuevo_nombre;
  private Button agregar;

  @WireVariable("tipoDocumentoServiceImpl")
  private TipoDocumentoService tipoDocumentoService;

  private TipoDocumentoDTO tipoDocumento;
  private List<MetadataDTO> listDatosVariables;
  private AnnotateDataBinder binder;

  public static final String METADATA = "metadata";
  private static Logger logger = LoggerFactory.getLogger(DatosPropiosTipoDocumentoComposer.class);

  public AnnotateDataBinder getBinder() {
    return binder;
  }

  public void setBinder(AnnotateDataBinder binder) {
    this.binder = binder;
  }

  @Override
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    this.tipoDocumento = (TipoDocumentoDTO) Executions.getCurrent().getArg().get("tipoDocumento");
    binder = new AnnotateDataBinder(comp);
  }

  public void onClick$agregar() {
    String nuevo_nombre = this.nuevo_nombre.getValue();

    if (nuevo_nombre == null || nuevo_nombre.trim().equals("")) {
      throw new WrongValueException(this.nuevo_nombre,
          Labels.getLabel("gedo.datosPropiosDocumento.exception.nombreNoVacio"));
    }

    for (MetadataDTO metadata : this.listDatosVariables) {
      if (metadata.getNombre().toUpperCase().equals(nuevo_nombre.trim().toUpperCase())) {
        throw new WrongValueException(this.nuevo_nombre,
            Labels.getLabel("gedo.datosPropiosDocumento.exception.nombreCamposNoRepetirce"));
      }
    }

    MetadataDTO newMetadata = new MetadataDTO();
    newMetadata.setNombre(nuevo_nombre.trim());
    this.listDatosVariables.add(newMetadata);
    grillaDatos.setRowRenderer(new DatosTipoDocumentoRenderer());

    refreshGrid();
  }

  public void refreshGrid() {
    if (this.tipoDocumento.getListaDatosVariables().size() == 15) {
      this.agregar.setDisabled(true);
    }

    this.nuevo_nombre.setValue(null);
    this.binder.loadComponent(this.grillaDatos);
  }

  public void onClick$cancelar() {
    this.datosPropiosTipoDocumentoWindow.detach();
  }

  public void onClick$guardar() throws InterruptedException {
    List<Component> rows = (List<Component>) grillaDatos.getRows().getChildren();
    int i = 0;
    for (Component hijo : rows) {
      List lista = hijo.getChildren();
      for (Object object : lista) {
        if ((object.getClass().equals(org.zkoss.zul.Checkbox.class))) {
          Checkbox check = (Checkbox) object;
          this.listDatosVariables.get(i).setObligatoriedad(check.isChecked());
          i++;
        }
      }
    }
    this.tipoDocumento.setListaDatosVariables(new ArrayList<MetadataDTO>(this.listDatosVariables));
    String userName = (String) Executions.getCurrent().getDesktop().getSession()
        .getAttribute(Constantes.SESSION_USERNAME);
    tipoDocumentoService.modificarTipoDocumento(this.tipoDocumento, userName);
    this.datosPropiosTipoDocumentoWindow.detach();

    Messagebox.show(Labels.getLabel("gedo.familiaTipoDocumento.guardar"),
        Labels.getLabel("ccoo.definirDestinatario.mensaje"), Messagebox.OK, Messagebox.INFORMATION,
        new EventListener() {
          public void onEvent(Event evt) {

          }
        });

  }

  public void onEliminar(Event evt) {

    logger.debug("Entro a eliminar");
    // The event is a ForwardEvent...
    ForwardEvent fe = (ForwardEvent) evt;
    // Getting the original Event
    Event event = fe.getOrigin();
    // Getting the component that triggered the original event (i.e. the
    // button)
    Button btn = (Button) event.getTarget();
    MetadataDTO metaData = (MetadataDTO) btn.getAttribute("metaData");
    if (listDatosVariables.size() > 0) {
      listDatosVariables.remove(metaData);
    }
    this.binder.loadComponent(this.grillaDatos);
  }

  public TipoDocumentoDTO getTipoDocumento() {
    return tipoDocumento;
  }

  public void setTipoDocumento(TipoDocumentoDTO tipoDocumento) {
    this.tipoDocumento = tipoDocumento;
  }

  public TipoDocumentoService getTipoDocumentoService() {
    return tipoDocumentoService;
  }

  public List<MetadataDTO> getListDatosVariables() {
    if (this.listDatosVariables == null) {
      this.listDatosVariables = new ArrayList<MetadataDTO>(
          this.tipoDocumento.getListaDatosVariables());
    }
    return listDatosVariables;
  }

  public void setListDatosVariables(List<MetadataDTO> listDatosVariables) {
    this.listDatosVariables = listDatosVariables;
  }

  public Window getDatosPropiosTipoDocumentoWindow() {
    return datosPropiosTipoDocumentoWindow;
  }

  public void setDatosPropiosTipoDocumentoWindow(Window datosPropiosTipoDocumentoWindow) {
    this.datosPropiosTipoDocumentoWindow = datosPropiosTipoDocumentoWindow;
  }

}
