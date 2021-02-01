package com.egoveris.edt.web.admin.pl;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;

import com.egoveris.edt.base.service.usuario.ICargoHistService;
import com.egoveris.edt.web.common.BaseComposer;
import com.egoveris.sharedsecurity.base.model.cargo.CargoHistoricoDTO;

/**
 * 
 * @author esroveda
 *
 */
public class AdminCargosHistComposer extends BaseComposer {

  /**
  * 
  */
  private static final long serialVersionUID = -3545337534684274436L;

  private static Logger logger = LoggerFactory.getLogger(AdminCargosHistComposer.class);

  @Autowired
  private Listbox lst_CargosHistorial;

  @Autowired
  private List<CargoHistoricoDTO> listaResultadoCargos;

  private CargoHistoricoDTO cargoHistorico;

  @Autowired
  private AnnotateDataBinder binder;

  @Autowired
  private ICargoHistService cargoHistService;

  @Autowired
  private Window win_admincargosHistorial;

  private Integer resultados;

  /**
   * Componentes y acciones para la carga inicial de la pantalla
   */
  @Override
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    this.self.addEventListener(Events.ON_CHANGE, new NovedadHistEventListener(this));
    binder = new AnnotateDataBinder(comp);
    this.cargoHistService = (ICargoHistService) SpringUtil.getBean("cargoHistService");
    this.cargarListbox();

    comp.addEventListener(Events.ON_USER, new EventListener() {

      @Override
      public void onEvent(Event event) throws Exception {
        onEvento(event);
      }
    });
  }

  public void onEvento(Event event) {
    if (event.getName().equals(Events.ON_USER) && "refresh".equals(event.getData())) {
      cargarListbox();
    }
  }

  /**
   * Crea el popUp para visualizar el historial de una Novedad
   */
  public void onVerHistorialCargo() {
    HashMap<String, Object> hm = new HashMap<String, Object>();
    hm.put("cargoHistorico", cargoHistorico);
    Window window = (Window) Executions
        .createComponents("administrator/tabsCargos/historialCargo.zul", this.self, hm);
    window.setClosable(true);
    window.setPosition("center");
    window.doModal();
  }

  /**
   * Carga el listBox de novedades con todas las que tengan que aparecer en el
   * historial
   */
  public void cargarListbox() {
    this.listaResultadoCargos = this.cargoHistService.getHistoriales();
    this.lst_CargosHistorial.setModel(new ListModelList(listaResultadoCargos));
    this.binder.loadComponent(lst_CargosHistorial);
  }

  final class NovedadHistEventListener implements EventListener {
    private AdminCargosHistComposer composer;

    public NovedadHistEventListener(AdminCargosHistComposer comp) {
      this.composer = comp;
    }

    @Override
    public void onEvent(Event event) throws Exception {
      if (event.getName().equals(Events.ON_USER)) {
        this.composer.cargarListbox();
      }
    }
  }

  public Listbox getLst_CargosHistorial() {
    return lst_CargosHistorial;
  }

  public void setLst_CargosHistorial(Listbox lst_CargosHistorial) {
    this.lst_CargosHistorial = lst_CargosHistorial;
  }

  public List<CargoHistoricoDTO> getListaResultadoCargos() {
    return listaResultadoCargos;
  }

  public void setListaResultadoCargos(List<CargoHistoricoDTO> listaResultadoCargos) {
    this.listaResultadoCargos = listaResultadoCargos;
  }

  public CargoHistoricoDTO getCargoHistorico() {
    return cargoHistorico;
  }

  public void setCargoHistorico(CargoHistoricoDTO cargoHistorico) {
    this.cargoHistorico = cargoHistorico;
  }

  public ICargoHistService getCargoHistService() {
    return cargoHistService;
  }

  public void setCargoHistService(ICargoHistService cargoHistService) {
    this.cargoHistService = cargoHistService;
  }

  public AnnotateDataBinder getBinder() {
    return binder;
  }

  public void setBinder(AnnotateDataBinder binder) {
    this.binder = binder;
  }

  void refreshWindows() {
    this.cargarListbox();
  }

  public Window getWin_admincargosHistorial() {
    return win_admincargosHistorial;
  }

  public void setWin_admincargosHistorial(Window win_admincargosHistorial) {
    this.win_admincargosHistorial = win_admincargosHistorial;
  }

  public Integer getResultados() {
    return resultados;
  }

  public void setResultados(Integer resultados) {
    this.resultados = resultados;
  }
}