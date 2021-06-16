package com.egoveris.edt.web.admin.pl;

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
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.egoveris.edt.base.model.eu.feriado.FeriadoDTO;
import com.egoveris.edt.base.service.feriado.FeriadoService;
import com.egoveris.edt.web.admin.pl.renderers.FeriadosItemRenderer;
import com.egoveris.sharedsecurity.base.model.ConstantesSesion;
import com.egoveris.sharedsecurity.conf.service.Utilitarios;

public class AdminCalendarioComposer extends GenericForwardComposer {

  private static final long serialVersionUID = -7620613460978549646L;
  private static Logger logger = LoggerFactory.getLogger(AdminCalendarioComposer.class);
  private Listbox feriadosId;
  private AnnotateDataBinder binder;
  private Window hiddenWindow;
  private List<FeriadoDTO> feriados;
  private FeriadoService feriadoService;

  @Override
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    this.binder = new AnnotateDataBinder(this.feriadosId);
    this.feriadoService = (FeriadoService) SpringUtil.getBean("feriadoService");
    this.self.addEventListener(Events.ON_NOTIFY, new FeriadosEventListener(this));
    this.feriadosId.setItemRenderer(new FeriadosItemRenderer());
    this.cargarListbox();

  }

  public void cargarListbox() {
    this.feriados = this.feriadoService.obtenerFeriados();
    this.feriadosId.setModel(new ListModelList(feriados));
    this.binder.loadComponent(this.feriadosId);
  }

  public void onClick$crearFeriado() {
		Utilitarios.closePopUps("win_NuevoFeriado");
    HashMap<String, Object> hm = new HashMap<>();
    hm.put("feriados", this.feriados);
    hiddenWindow = (Window) Executions.createComponents("/abmMisSistemas/nuevoFeriado.zul",
        this.self, hm);
    hiddenWindow.setClosable(true);
    hiddenWindow.setPosition("center");
    hiddenWindow.doModal();

  }

  public void onModificarFeriado() {
		Utilitarios.closePopUps("win_NuevoFeriado");
    HashMap<String, Object> hm = new HashMap<>();
    hm.put("feriados", this.feriados);
    hm.put("feriado", this.feriadosId.getSelectedItem().getValue());
    hiddenWindow = (Window) Executions.createComponents("/abmMisSistemas/nuevoFeriado.zul",
        this.self, hm);
    hiddenWindow.setClosable(true);
    hiddenWindow.setPosition("center");
    hiddenWindow.doModal();
  }

  public void onEliminarFeriado() throws InterruptedException {
    Messagebox.show(Labels.getLabel("eu.abm.configuracion.feriados.question.eliminarFeriado"),
        Labels.getLabel("eu.adminSade.usuario.generales.confirmacion"),
        Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
        new org.zkoss.zk.ui.event.EventListener() {
          @Override
          public void onEvent(Event evt) throws InterruptedException {
            switch (((Integer) evt.getData()).intValue()) {
            case Messagebox.YES:
              eliminarFeriado();
              break;
            case Messagebox.NO:
              break;
            }
          }

        });

  }

  private void eliminarFeriado() {
    String usuario = (String) Executions.getCurrent().getSession()
        .getAttribute(ConstantesSesion.SESSION_USERNAME);
    this.feriadoService.eliminarFeriado((FeriadoDTO) this.feriadosId.getSelectedItem().getValue(),
        usuario);
    this.feriados.remove((FeriadoDTO) this.feriadosId.getSelectedItem().getValue());
    this.cargarListbox();

  }

}

/**
 * 
 * @author lfishkel
 *
 */
final class FeriadosEventListener implements EventListener {

  private AdminCalendarioComposer adminCalendarioComposer;

  public FeriadosEventListener(AdminCalendarioComposer adminCalendarioComposer) {
    this.adminCalendarioComposer = adminCalendarioComposer;
  }

  @Override
  public void onEvent(Event event) throws Exception {
    if (event.getName().equals(Events.ON_NOTIFY)) {
      this.adminCalendarioComposer.cargarListbox();

    }
  }

}
