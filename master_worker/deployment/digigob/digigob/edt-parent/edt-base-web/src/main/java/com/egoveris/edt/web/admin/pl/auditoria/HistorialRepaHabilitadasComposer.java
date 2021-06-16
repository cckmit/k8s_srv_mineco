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

import com.egoveris.sharedsecurity.base.model.UsuarioReparticionHabilitadaDTO;

public class HistorialRepaHabilitadasComposer extends AuditoriaTabComposer {

  /**
  * 
  */
  private static final long serialVersionUID = -772895811020672883L;

  @Autowired
  private Listbox lstResultadoHab;

  private AnnotateDataBinder binder;

  @Autowired
  private List<UsuarioReparticionHabilitadaDTO> listaResultadoHabilitada = new ArrayList<UsuarioReparticionHabilitadaDTO>();

  private Integer resultados;

  private boolean cargoLista = false;

  /**
   * Componentes y acciones para la carga inicial de la pantalla
   */
  @Override
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    binder = new AnnotateDataBinder(comp);
    this.self.addEventListener(Events.ON_CHECK, new HistorialRepaHabilitadasWindowsListener(this));
  }

  /**
   * Cierra la ventana
   */
  public void onClick$btn_volver() {
    this.self.detach();
  }

  public Listbox getLstResultadoHab() {
    return lstResultadoHab;
  }

  public void setLstResultadoHab(Listbox lstResultadoHab) {
    this.lstResultadoHab = lstResultadoHab;
  }

  public List<UsuarioReparticionHabilitadaDTO> getListaResultadoHabilitada() {
    return listaResultadoHabilitada;
  }

  public void setListaResultadoHabilitada(
      List<UsuarioReparticionHabilitadaDTO> listaResultadoHabilitada) {
    this.listaResultadoHabilitada = listaResultadoHabilitada;
  }

  @Override
  public AnnotateDataBinder getBinder() {
    return binder;
  }

  @Override
  public void setBinder(AnnotateDataBinder binder) {
    this.binder = binder;
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
    export_to_csv(lstResultadoHab, "export_rep_habilitadas_");
  }

  /**
   * 
   * @author administradorit
   *
   */
  final class HistorialRepaHabilitadasWindowsListener implements EventListener {
    private HistorialRepaHabilitadasComposer composer;

    public HistorialRepaHabilitadasWindowsListener(HistorialRepaHabilitadasComposer comp) {
      this.composer = comp;
    }

    @Override
    public void onEvent(Event event) throws Exception {
      if (event.getName().equals(Events.ON_CHECK) && !cargoLista) {
        listaResultadoHabilitada = getUsuarioReparticionHabilitadaService()
            .getHistorico(getUsuario().getUid());
        lstResultadoHab.setModel(new ListModelList(listaResultadoHabilitada));
        composer.binder.loadAll();
        cargoLista = true;
      }
    }
  }
}
