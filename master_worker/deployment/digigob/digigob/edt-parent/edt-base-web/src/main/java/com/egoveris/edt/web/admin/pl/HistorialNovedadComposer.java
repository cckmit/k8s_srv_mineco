package com.egoveris.edt.web.admin.pl;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;

import com.egoveris.edt.base.model.eu.novedad.NovedadDTO;
import com.egoveris.edt.base.model.eu.novedad.NovedadHistDTO;
import com.egoveris.edt.base.service.novedad.INovedadHistService;
import com.egoveris.edt.web.common.BaseComposer;

public class HistorialNovedadComposer extends BaseComposer {

  private static final long serialVersionUID = -4986437065576664693L;

  private Listbox listBoxNovedadesHist;
  private List<NovedadHistDTO> novedadesHist;
  private NovedadDTO novedad;
  private AnnotateDataBinder binder;
  private INovedadHistService novedadHistService;

  private Window win_historialNovedades;

  /**
   * Componentes y acciones para la carga inicial de la pantalla
   */
  @Override
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    binder = new AnnotateDataBinder(comp);
    this.novedadHistService = (INovedadHistService) SpringUtil.getBean("novedadHistService");
    this.cargarListbox();
  }

  /**
   * Carga el listBox con el historial de la novedad seleccionada anteriormente
   */
  private void cargarListbox() {
    novedad = (NovedadDTO) Executions.getCurrent().getArg().get("novedad");
    this.novedadesHist = this.novedadHistService.getHistorial(novedad.getId());
    this.listBoxNovedadesHist.setModel(new ListModelList(novedadesHist));
    this.binder.loadComponent(listBoxNovedadesHist);
  }

  /**
   * Cierra la ventana
   */
  public void onClick$btn_volver() {
    this.self.detach();
  }

  public void setWin_historialNovedades(Window win_historialNovedades) {
    this.win_historialNovedades = win_historialNovedades;
  }

  public Window getWin_historialNovedades() {
    return win_historialNovedades;
  }

  public Listbox getListBoxNovedadesHist() {
    return listBoxNovedadesHist;
  }

  public void setListBoxNovedadesHist(Listbox listBoxNovedadesHist) {
    this.listBoxNovedadesHist = listBoxNovedadesHist;
  }

  public List<NovedadHistDTO> getNovedadesHist() {
    return novedadesHist;
  }

  public void setNovedadesHist(List<NovedadHistDTO> novedadesHist) {
    this.novedadesHist = novedadesHist;
  }

  public NovedadDTO getNovedad() {
    return novedad;
  }

  public void setNovedad(NovedadDTO novedad) {
    this.novedad = novedad;
  }

  public AnnotateDataBinder getBinder() {
    return binder;
  }

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

}
