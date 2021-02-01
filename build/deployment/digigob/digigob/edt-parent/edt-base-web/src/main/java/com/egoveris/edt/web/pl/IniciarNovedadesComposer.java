package com.egoveris.edt.web.pl;

import java.util.List;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Window;

import com.egoveris.edt.base.model.eu.novedad.NovedadDTO;
import com.egoveris.edt.base.service.novedad.INovedadHelper;
import com.egoveris.sharedsecurity.base.model.ConstantesSesion;

public class IniciarNovedadesComposer extends GenericForwardComposer {

  /**
  * 
  */
  private static final long serialVersionUID = -4701705076413154156L;

  private static final String PUNTOS_SUSPENSIVOS = "...";
  private static final int CANT_MAX_CARACTERES_A_MOSTRAR_EN_DESC = 23;
  private static final int CANT_MAX_CARACTERES_A_MOSTRAR_EN_NOMBRE = 21;

  /**
  * 
  */

  private Window iniciarNovedadesWindow;

  private AnnotateDataBinder iniciarNovedadesBinder;

  private INovedadHelper novedadHelper;

  private List<NovedadDTO> listNovedades;

  private Rows a;

  @Override
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);

    iniciarNovedadesBinder = new AnnotateDataBinder(comp);
    this.novedadHelper = (INovedadHelper) SpringUtil.getBean("novedadHelper");
    this.iniciarNovedadesBinder.loadAll();
  }

  protected void enviarBloqueoPantalla(Object data) {
    Clients.showBusy(Labels.getLabel("ee.general.procesando.procesandoSolicitud"));
    Events.echoEvent("onUser", this.self, data);
  }

  public List<NovedadDTO> getListNovedades() {
    this.listNovedades = novedadHelper.obtenerTodas();

    return listNovedades;
  }

  public void setListNovedades(List<NovedadDTO> listNovedades) {
    this.listNovedades = listNovedades;
  }

  public void onClick$btnAceptar() {
    Session session = Executions.getCurrent().getDesktop().getSession();
    session.setAttribute(ConstantesSesion.KEY_MOSTRAR_NOVEDADES, false);
    this.self.detach();
  }
}
