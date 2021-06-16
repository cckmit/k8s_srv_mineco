package com.egoveris.edt.web.admin.pl.auditoria;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;

import com.egoveris.edt.base.model.eu.usuario.DatosUsuarioDTO;
import com.egoveris.edt.base.service.usuario.IDatosUsuarioService;

public class HistorialDatosPersonalesComposer extends AuditoriaTabComposer {

	
  /**
  * 
  */
  private static final long serialVersionUID = -2153617390791623274L;

  @Autowired
  private Listbox lstResultado;

  private AnnotateDataBinder binder;

  @Autowired
  private List<DatosUsuarioDTO> listaResultado = new ArrayList<DatosUsuarioDTO>();

  private Integer resultados;

  private Window win_auditoriaDatosPersonales;

  /**
   * Componentes y acciones para la carga inicial de la pantalla
   */
  @Override
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    binder = new AnnotateDataBinder(comp);
    this.cargarListbox();
  }

  /**
   * Carga el listBox con el historial de la novedad seleccionada anteriormente
   */
  private void cargarListbox() {
    DatosUsuarioDTO datosUsuario = new DatosUsuarioDTO();
    datosUsuario.setUsuario(getUsuario().getUid());
    this.listaResultado = datosUsuarioService.getHistorico(datosUsuario);

    this.lstResultado.setModel(new ListModelList(listaResultado));
    this.binder.loadComponent(lstResultado);
  }

  @Override
  public AnnotateDataBinder getBinder() {
    return binder;
  }

  @Override
  public void setBinder(AnnotateDataBinder binder) {
    this.binder = binder;
  }

  public Listbox getLstResultado() {
    return lstResultado;
  }

  public void setLstResultado(Listbox lstResultado) {
    this.lstResultado = lstResultado;
  }

  @Override
  public IDatosUsuarioService getDatosUsuarioService() {
    return datosUsuarioService;
  }

  @Override
  public void setDatosUsuarioService(IDatosUsuarioService datosUsuarioService) {
    this.datosUsuarioService = datosUsuarioService;
  }

  public List<DatosUsuarioDTO> getListaResultado() {
    return listaResultado;
  }

  public void setListaResultado(List<DatosUsuarioDTO> listaResultado) {
    this.listaResultado = listaResultado;
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

  public Window getWin_auditoriaDatosPersonales() {
    return win_auditoriaDatosPersonales;
  }

  public void setWin_auditoriaDatosPersonales(Window win_auditoriaDatosPersonales) {
    this.win_auditoriaDatosPersonales = win_auditoriaDatosPersonales;
  }

  public void onClick$btn_exportar() throws InterruptedException {
    export_to_csv(lstResultado, "export_datos_personales_");
  }
}
