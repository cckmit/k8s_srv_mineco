package com.egoveris.deo.web.satra.produccion;

import com.egoveris.deo.base.service.ObtenerReparticionServices;
import com.egoveris.deo.model.model.ReparticionDTO;
import com.egoveris.deo.web.satra.GEDOGenericForwardComposer;
import com.egoveris.sharedorganismo.base.model.ReparticionBean;
import com.egoveris.sharedorganismo.base.model.SectorInternoBean;

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
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class SeleccionarReparticionComposer extends GEDOGenericForwardComposer {

  private static final long serialVersionUID = 1L;

  private Window seleccionarReparticionWindow;
  private Textbox busquedaReparticion;

  private AnnotateDataBinder seleccionarReparticionBinder;

  private String origen;
  private List<ReparticionDTO> reparticionesList;
  private ReparticionDTO selectedReparticion;
  private List<ReparticionBean> reparticiones;
  private Listheader seleccionRep;

  @WireVariable("obtenerReparticionServicesImpl")
  private ObtenerReparticionServices obtenerReparticionService;

  public void doAfterCompose(Component component) throws Exception {
    
    super.doAfterCompose(component);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    origen = (String) Executions.getCurrent().getArg().get("origen");
    this.reparticionesList = new ArrayList<ReparticionDTO>();
    this.reparticiones = this.obtenerReparticionService.buscarReparticiones();

    this.seleccionarReparticionBinder = new AnnotateDataBinder(this.seleccionarReparticionWindow);
    this.seleccionarReparticionBinder.loadAll();

  }

  public void onBuscar() {

    this.reparticionesList.clear();
    String textoBusqueda = this.busquedaReparticion.getValue().toUpperCase();
    if (!textoBusqueda.isEmpty()) {
      for (ReparticionBean rep : this.reparticiones) {
        if (rep.getCodigo().startsWith(textoBusqueda)) {
          List<SectorInternoBean> listaSectores = this.sectorservice
              .buscarSectoresPorReparticion(rep.getId());
          for (SectorInternoBean sector : listaSectores) {
            if (sector.isSectorMesa()) {
              ReparticionDTO reparticion = new ReparticionDTO();
              reparticion.setNombre(rep.getCodigo());
              reparticion.setSectorInterno(sector.getCodigo());
              reparticion.setCodigo(sector.getNombre());
              this.reparticionesList.add(reparticion);
            }
          }
        }
      }
    }
    this.seleccionarReparticionBinder.loadAll();
  }

  public void onClick$aceptar() {

    if (this.selectedReparticion != null) {
      Map<String, Object> map = new HashMap<String, Object>();
      map.put("origen", origen);
      map.put("reparticion", this.selectedReparticion);
      Events.sendEvent(new Event(Events.ON_NOTIFY, this.self.getParent(), map));
      this.seleccionarReparticionWindow.onClose();
    } else {
      throw new WrongValueException(this.seleccionRep,
          Labels.getLabel("ccoo.sectorMesa.reparticionValida"));
    }

  }

  public void onClick$cancelar() {
    this.seleccionarReparticionWindow.onClose();
  }

  public Textbox getBusquedaReparticion() {
    return busquedaReparticion;
  }

  public void setBusquedaReparticion(Textbox busquedaReparticion) {
    this.busquedaReparticion = busquedaReparticion;
  }

  public List<ReparticionDTO> getReparticionesList() {
    return reparticionesList;
  }

  public void setReparticionesList(List<ReparticionDTO> reparticionesList) {
    this.reparticionesList = reparticionesList;
  }

  public ReparticionDTO getSelectedReparticion() {
    return selectedReparticion;
  }

  public void setSelectedReparticion(ReparticionDTO selectedReparticion) {
    this.selectedReparticion = selectedReparticion;
  }

  public AnnotateDataBinder getSeleccionarReparticionBinder() {
    return seleccionarReparticionBinder;
  }

  public void setSeleccionarReparticionBinder(AnnotateDataBinder seleccionarReparticionBinder) {
    this.seleccionarReparticionBinder = seleccionarReparticionBinder;
  }

}
