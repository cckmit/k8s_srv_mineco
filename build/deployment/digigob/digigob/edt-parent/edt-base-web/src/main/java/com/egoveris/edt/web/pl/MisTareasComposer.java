package com.egoveris.edt.web.pl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Window;

import com.egoveris.edt.base.exception.SystemException;
import com.egoveris.edt.base.model.TareasPorSistemaBean;
import com.egoveris.edt.base.model.eu.usuario.UsuarioFrecuenciaDTO;
import com.egoveris.edt.base.service.vista.IVistaBuzonGrupalService;
import com.egoveris.edt.base.service.vista.IVistaMisTareasService;
import com.egoveris.edt.web.pl.Actualiza.ActualizaEvent;
import com.egoveris.sharedsecurity.base.model.ConstantesSesion;

/**
 * @author pfolgar
 * 
 */
public class MisTareasComposer extends GenericForwardComposer {

  private static final long serialVersionUID = 8852393247881265908L;
  private static Logger logger = LoggerFactory.getLogger(MisTareasComposer.class);
  private Window misTareasDesktop;
  private List<TareasPorSistemaBean> misTareas;
  private String userName;
  private AnnotateDataBinder misTareasDesktopBinder;
  private Listbox misTareasId;
  private Label labelMisTareasSinAplicacionId;
  private TareasPorSistemaBean tareasPorSistema;
  private List<Integer> listaIdsAplicacionesMisTareas;

  private Map<Integer, List<String>> listaIdsAplicacionesbuzon;
  private UsuarioFrecuenciaDTO usuarioFrecuencia;
  private Listheader porcentajeFrecuenciaMenor;
  private Listheader porcentajeFrecuenciaMayor;
  private Listheader frec1;
  private Listheader frec2;
  private Listheader frec3;
  private Listheader frec4;
  private Session csession;
  private Label timeoutMisTareas;
  private boolean traerMisTareas = true;
  private IVistaMisTareasService vistaMisTareasService;
  private IVistaBuzonGrupalService vistaBuzonGrupalService;
  String tarea;

  @Override
  public void doAfterCompose(Component c) throws Exception {
    super.doAfterCompose(c);
    this.vistaMisTareasService = (IVistaMisTareasService) SpringUtil
        .getBean("vistaMisTareasService");
    this.vistaBuzonGrupalService = (IVistaBuzonGrupalService) SpringUtil
        .getBean("vistaBuzonGrupalService");

    csession = Executions.getCurrent().getDesktop().getSession();
    this.userName = (String) csession.getAttribute(ConstantesSesion.SESSION_USERNAME);
    timeoutMisTareas.setValue((String) csession.getAttribute(ConstantesSesion.TIEMPO_REFRESCO));
    c.addEventListener(Events.ON_NOTIFY, new MisTareasOnNotifyWindowListener(this));
    misTareasDesktopBinder = new AnnotateDataBinder(c);
    misTareasDesktopBinder.loadAll();
  }

  public List<TareasPorSistemaBean> getMisTareas() {
    logger.debug("{} - Se ingresa al método getMisTareas()", userName);
    Date start = new Date();
    if (this.misTareas == null) {
      this.usuarioFrecuencia = (UsuarioFrecuenciaDTO) csession
          .getAttribute(ConstantesSesion.SESSION_USUARIO_FRECUENCIA);
      this.listaIdsAplicacionesMisTareas = (List<Integer>) csession
          .getAttribute(ConstantesSesion.SESSION_LISTA_USUARIO_MIS_TAREAS);

      this.listaIdsAplicacionesbuzon = (Map<Integer, List<String>>) csession
          .getAttribute(ConstantesSesion.SESSION_LISTA_USUARIO_BUZONGRUPAL_TAREAS);

      /*
       * Temporalmente removido por el costo de las consultas JBPM. 17/02/17
       */
      try {
        if (this.traerMisTareas) {
          this.misTareas = new ArrayList<>(); // this.vistaMisTareasService.obtenerVistaMisTareas(listaIdsAplicacionesMisTareas,
                                              // userName,
                                              // this.usuarioFrecuencia);

        } else {
          this.misTareas = new ArrayList<>();// this.vistaBuzonGrupalService.obtenerVistaTareas(this.listaIdsAplicacionesbuzon,
                                             // userName,
                                             // this.usuarioFrecuencia);

        }

        if (this.misTareas.isEmpty()) {
          this.labelMisTareasSinAplicacionId.setVisible(true);
          this.misTareasId.setVisible(false);
        } else {
          this.frec1.setLabel(Labels.getLabel("eu.escritorioUnico.misTareas.frec1",
              new String[] { String.valueOf(this.usuarioFrecuencia.getFrecuenciaMenor()) }));
          this.frec2.setLabel(Labels.getLabel("eu.escritorioUnico.misTareas.frec2",
              new String[] { String.valueOf(this.usuarioFrecuencia.getFrecuenciaMedia()) }));
          this.frec3.setLabel(Labels.getLabel("eu.escritorioUnico.misTareas.frec3",
              new String[] { String.valueOf(this.usuarioFrecuencia.getFrecuenciaMayor()) }));
          this.frec4.setLabel(Labels.getLabel("eu.escritorioUnico.misTareas.frec4",
              new String[] { String.valueOf(this.usuarioFrecuencia.getFrecuenciaMayor()) }));
          this.porcentajeFrecuenciaMenor
              .setLabel(Labels.getLabel("eu.escritorioUnico.misTareas.porcentajeFrecuenciaMenor",
                  new String[] { String.valueOf(this.usuarioFrecuencia.getFrecuenciaMayor()) }));
          this.porcentajeFrecuenciaMayor
              .setLabel(Labels.getLabel("eu.escritorioUnico.misTareas.porcentajeFrecuenciaMayor",
                  new String[] { String.valueOf(this.usuarioFrecuencia.getFrecuenciaMayor()) }));
        }
      } catch (SystemException se) {
        logger.error(se.getMessage(), se);
        Messagebox.show(se.getMessage(), "Error", Messagebox.OK, Messagebox.ERROR);
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        Messagebox.show(Labels.getLabel("eu.verificarPass.label.ingresePass"),
            Labels.getLabel("eu.adminSade.usuario.generales.error"), Messagebox.OK,
            Messagebox.ERROR);
      }
    }
    Date end = new Date();
    Long dif = end.getTime() - start.getTime();
    logger.debug("{} - Finaliza el metodo getMisTareas() {} ms", userName, dif.toString());
    return misTareas;
  }

  public IVistaBuzonGrupalService getVistaBuzonGrupalService() {
    return vistaBuzonGrupalService;
  }

  public void setVistaBuzonGrupalService(IVistaBuzonGrupalService vistaBuzonGrupalService) {
    this.vistaBuzonGrupalService = vistaBuzonGrupalService;
  }

  public void refreshInbox() {
    this.misTareas = null;
    this.misTareas = this.getMisTareas();
    this.misTareasDesktopBinder.loadComponent(this.misTareasId);
  }

  public void onIrMisTareas() {
    if (this.misTareasDesktop != null) {
      if (this.traerMisTareas) {
        Executions.sendRedirect(this.tareasPorSistema.getAplicacion().getUrlAplicacionInbox());
      } else {
        Executions.sendRedirect(this.tareasPorSistema.getAplicacion().getUrlAplicacionBuzon());
      }
    } else {
      Messagebox.show(Labels.getLabel("eu.misAppComposer.msgbox.noInicializarVista"),
          Labels.getLabel("eu.misAppComposer.msgbox.errorCom"), Messagebox.OK, Messagebox.ERROR);
    }
  }

  public void admiTareas(Tabbox tab) {
    this.tarea = (String) tab.getSelectedPanel().getChildren().toString();
    if (!this.tarea.contains("includeGrupalesBuzon")) {
      this.traerMisTareas = true;
    } else
      this.traerMisTareas = false;

    this.refreshInbox();
  }

  public void setMisTareas(List<TareasPorSistemaBean> misTareas) {
    this.misTareas = misTareas;
  }

  public Window getMisTareasDesktop() {
    return misTareasDesktop;
  }

  public void setMisTareasDesktop(Window misTareasDesktop) {
    this.misTareasDesktop = misTareasDesktop;
  }

  public Listbox getMisTareasId() {
    return misTareasId;
  }

  public void setMisTareasId(Listbox misTareasId) {
    this.misTareasId = misTareasId;
  }

  public Label getLabelMisTareasSinAplicacionId() {
    return labelMisTareasSinAplicacionId;
  }

  public void setLabelMisTareasSinAplicacionId(Label labelMisTareasSinAplicacionId) {
    this.labelMisTareasSinAplicacionId = labelMisTareasSinAplicacionId;
  }

  public TareasPorSistemaBean getTareasPorSistema() {
    return tareasPorSistema;
  }

  public void setTareasPorSistema(TareasPorSistemaBean tareasPorSistema) {
    this.tareasPorSistema = tareasPorSistema;
  }

  public AnnotateDataBinder getMisTareasDesktopBinder() {
    return misTareasDesktopBinder;
  }

  public void setMisTareasDesktopBinder(AnnotateDataBinder misTareasDesktopBinder) {
    this.misTareasDesktopBinder = misTareasDesktopBinder;
  }

  public List<Integer> getListaIdsAplicacionesMisTareas() {
    return listaIdsAplicacionesMisTareas;
  }

  public void setListaIdsAplicacionesMisTareas(List<Integer> listaIdsAplicacionesMisTareas) {
    this.listaIdsAplicacionesMisTareas = listaIdsAplicacionesMisTareas;
  }

  public UsuarioFrecuenciaDTO getUsuarioFrecuencia() {
    return usuarioFrecuencia;
  }

  public void setUsuarioFrecuencia(UsuarioFrecuenciaDTO usuarioFrecuencia) {
    this.usuarioFrecuencia = usuarioFrecuencia;
  }

  public Listheader getPorcentajeFrecuenciaMenor() {
    return porcentajeFrecuenciaMenor;
  }

  public void setPorcentajeFrecuenciaMenor(Listheader porcentajeFrecuenciaMenor) {
    this.porcentajeFrecuenciaMenor = porcentajeFrecuenciaMenor;
  }

  public Listheader getPorcentajeFrecuenciaMayor() {
    return porcentajeFrecuenciaMayor;
  }

  public void setPorcentajeFrecuenciaMayor(Listheader porcentajeFrecuenciaMayor) {
    this.porcentajeFrecuenciaMayor = porcentajeFrecuenciaMayor;
  }

  public Listheader getFrec1() {
    return frec1;
  }

  public void setFrec1(Listheader frec1) {
    this.frec1 = frec1;
  }

  public Listheader getFrec2() {
    return frec2;
  }

  public void setFrec2(Listheader frec2) {
    this.frec2 = frec2;
  }

  public Listheader getFrec3() {
    return frec3;
  }

  public void setFrec3(Listheader frec3) {
    this.frec3 = frec3;
  }

  public Listheader getFrec4() {
    return frec4;
  }

  public void setFrec4(Listheader frec4) {
    this.frec4 = frec4;
  }

  public void setTimeoutMisTareas(Label timeoutMisTareas) {
    this.timeoutMisTareas = timeoutMisTareas;
  }

  public Label getTimeoutMisTareas() {
    return timeoutMisTareas;
  }

}

final class MisTareasOnNotifyWindowListener implements EventListener {
  private MisTareasComposer composer;

  public MisTareasOnNotifyWindowListener(MisTareasComposer comp) {
    this.composer = comp;
  }

  @Override
  public void onEvent(Event event) throws Exception {
    if (event.getName().equals(Events.ON_NOTIFY)) {
      if (event.getData() == null) {
        this.composer.refreshInbox();
      } else if (event.getData() instanceof ActualizaEvent) {
        ActualizaEvent eventFirma = (ActualizaEvent) event.getData();
        if ("onActualizarMisTareas".equals(eventFirma.getEventName())) {
          this.composer.refreshInbox();
        }
      } else if (event.getData() instanceof Tabbox) {
        if (event.getName().equals(Events.ON_NOTIFY)) {
          Tabbox tab = (Tabbox) event.getData();
          this.composer.admiTareas(tab);
        }
      }

    }
  }
}
