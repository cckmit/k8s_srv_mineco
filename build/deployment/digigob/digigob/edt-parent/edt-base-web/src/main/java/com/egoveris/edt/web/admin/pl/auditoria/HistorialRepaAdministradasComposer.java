package com.egoveris.edt.web.admin.pl.auditoria;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;

import com.egoveris.edt.base.service.novedad.INovedadHistService;
import com.egoveris.edt.base.service.usuario.ICargoHistService;
import com.egoveris.sharedsecurity.base.model.AdminReparticionDTO;

public class HistorialRepaAdministradasComposer extends AuditoriaTabComposer {

  /**
  * 
  */
  private static final long serialVersionUID = 5229970360661806461L;

  @Autowired
  private Listbox lstResultado;

  private AnnotateDataBinder binder;

  private INovedadHistService novedadHistService;

  @Autowired
  private ICargoHistService cargoHistService;

  @Autowired
  private List<AdminReparticionDTO> listaResultadoRepAdmin = new ArrayList<AdminReparticionDTO>();

  private Integer resultados;

  private boolean cargoLista = false;

  /**
   * Componentes y acciones para la carga inicial de la pantalla
   */
  @Override
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    binder = new AnnotateDataBinder(comp);
    this.self.addEventListener(Events.ON_CHECK,
        new HistorialRepaAdministradasWindowsListener(this));
  }

  public Listbox getLstResultado() {
    return lstResultado;
  }

  public void setLstResultado(Listbox lstResultado) {
    this.lstResultado = lstResultado;
  }

  public List<AdminReparticionDTO> getListaResultadoRepAdmin() {
    return listaResultadoRepAdmin;
  }

  public void setListaResultadoRepAdmin(List<AdminReparticionDTO> listaResultadoRepAdmin) {
    this.listaResultadoRepAdmin = listaResultadoRepAdmin;
  }

  public ICargoHistService getCargoHistService() {
    return cargoHistService;
  }

  public void setCargoHistService(ICargoHistService cargoHistService) {
    this.cargoHistService = cargoHistService;
  }

  @Override
  public AnnotateDataBinder getBinder() {
    return binder;
  }

  @Override
  public void setBinder(AnnotateDataBinder binder) {
    this.binder = binder;
  }

  public INovedadHistService getNovedadHistService() {
    return novedadHistService;
  }

  public void setNovedadHistService(INovedadHistService novedadHistService) {
    this.novedadHistService = novedadHistService;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  public Integer getResultados() {
    return resultados;
  }

  public void setResultados(Integer resultados) {
    this.resultados = resultados;
  }

  public void onClick$btn_exportar() throws InterruptedException {
    export_to_csv(lstResultado, "export_rep_administradas_");
  }

  /**
   * 
   * @author administradorit
   *
   */
  final class HistorialRepaAdministradasWindowsListener implements EventListener {
    @SuppressWarnings("unused")
    private HistorialRepaAdministradasComposer composer;

    public HistorialRepaAdministradasWindowsListener(HistorialRepaAdministradasComposer comp) {
      this.composer = comp;
    }

    @Override
    public void onEvent(Event event) throws Exception {
      if (event.getName().equals(Events.ON_CHECK) && !cargoLista) {
        listaResultadoRepAdmin = getAdminReparticionService().getHistorico(getUsuario().getUid());
        lstResultado.setModel(new ListModelList(listaResultadoRepAdmin));
        binder.loadAll();
        cargoLista = true;
      }
    }
  }
}
