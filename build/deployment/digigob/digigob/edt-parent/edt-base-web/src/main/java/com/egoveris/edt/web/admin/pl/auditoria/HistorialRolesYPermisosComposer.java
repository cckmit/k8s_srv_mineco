package com.egoveris.edt.web.admin.pl.auditoria;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;

import com.egoveris.edt.base.service.novedad.INovedadHistService;
import com.egoveris.edt.base.service.usuario.ICargoHistService;
import com.egoveris.sharedsecurity.base.model.cargo.CargoDTO;
import com.egoveris.sharedsecurity.base.model.cargo.CargoHistoricoDTO;

public class HistorialRolesYPermisosComposer extends AuditoriaTabComposer {

  /**
  * 
  */
  private static final long serialVersionUID = 3679529439248027509L;

  @Autowired
  private Listbox lstResultadoCargos;

  private AnnotateDataBinder binder;

  private INovedadHistService novedadHistService;

  @Autowired
  private ICargoHistService cargoHistService;

  @Autowired
  private List<CargoHistoricoDTO> listaResultadoCargos;

  private Integer resultados;

  /**
   * Componentes y acciones para la carga inicial de la pantalla
   */
  @Override
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    binder = new AnnotateDataBinder(comp);
    this.cargoHistService = (ICargoHistService) SpringUtil.getBean("cargoHistService");
    // this.cargarListbox();
  }

  /**
   * Carga el listBox con el historial de la novedad seleccionada anteriormente
   */
  private void cargarListbox() {
    CargoDTO cargo = (CargoDTO) Executions.getCurrent().getArg().get("cargo");
    this.listaResultadoCargos = this.cargoHistService.getHistorial(cargo.getId());
    this.lstResultadoCargos.setModel(new ListModelList(listaResultadoCargos));
    this.binder.loadComponent(lstResultadoCargos);
  }

  /**
   * Cierra la ventana
   */
  public void onClick$btn_volver() {
    this.self.detach();
  }

  public Listbox getLstResultadoCargos() {
    return lstResultadoCargos;
  }

  public void setLstResultadoCargos(Listbox lstResultadoCargos) {
    this.lstResultadoCargos = lstResultadoCargos;
  }

  public List<CargoHistoricoDTO> getListaResultadoCargos() {
    return listaResultadoCargos;
  }

  public void setListaResultadoCargos(List<CargoHistoricoDTO> listaResultadoCargos) {
    this.listaResultadoCargos = listaResultadoCargos;
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

}
