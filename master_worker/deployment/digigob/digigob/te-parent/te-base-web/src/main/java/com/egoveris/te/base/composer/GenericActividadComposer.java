package com.egoveris.te.base.composer;

import com.egoveris.te.base.model.ActividadInbox;
import com.egoveris.te.base.util.ConstantesWeb.VISTA;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public abstract class GenericActividadComposer extends EEGenericForwardComposer {

  private static final long serialVersionUID = 5448229114016852780L;
  private final static Logger logger = LoggerFactory.getLogger(GenericActividadComposer.class);

  // Estas variables son seteadas en el Desktop de ZK.
  private ActividadInbox popupActividad;
  private VISTA popupVistaActividad;
  private Map<String, Boolean> popupParametrosVisualizacion;
  private String userName;

  public final void doAfterCompose(Component c) throws Exception {
    super.doAfterCompose(c);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));

    try {
      switch (getPopupVistaActividad()) {
      case LECTURA:
        modoLecturaComposer(c);
        break;
      case EDICION:
    	  modoEdicionComposer(c);
        break;
      default:
        modoEdicionComposer(c);
        break;
      }
      // Evento al cerrar para que refresque el buzon de actividades
      ((Window) this.self).addEventListener(Events.ON_CLOSE, onCloseListener());
    } catch (Exception e) {
      logger.error("Error al iniciarlizar actividad", e);
      ((Window) this.self).addEventListener(Events.ON_CREATE, new ErrorEventListener(this.self));
      Messagebox.show(Labels.getLabel("ee.subsanacion.msg.init.error"),
          Labels.getLabel("ee.subsanacion.msg.title.error"), Messagebox.OK, Messagebox.ERROR);
    }
  }

  protected abstract void modoEdicionComposer(Component c) throws Exception;

  protected abstract void modoLecturaComposer(Component c) throws Exception;

  protected EventListener onCloseListener() {
    return new CloseEventListener(this.self);
  }

  public ActividadInbox getPopupActividad() {
    return popupActividad;
  }

  public void setPopupActividad(ActividadInbox popupActividad) {
    this.popupActividad = popupActividad;
  }

  public Map<String, Boolean> getPopupParametrosVisualizacion() {
    return popupParametrosVisualizacion;
  }

  public void setPopupParametrosVisualizacion(Map<String, Boolean> popupParametrosVisualizacion) {
    this.popupParametrosVisualizacion = popupParametrosVisualizacion;
  }

  public VISTA getPopupVistaActividad() {
    return popupVistaActividad;
  }

  public void setPopupVistaActividad(VISTA popupVistaActividad) {
    this.popupVistaActividad = popupVistaActividad;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  private class ErrorEventListener implements EventListener {

    private Component component;

    public ErrorEventListener(Component c) {
      this.component = c;
    }

    @Override
    public void onEvent(Event event) throws Exception {
      ((Window) this.component).onClose();
    }
  }

  private class CloseEventListener implements EventListener {

    Component comp;

    public CloseEventListener(Component comp) {
      this.comp = comp;
    }

    @Override
    public void onEvent(Event event) throws Exception {
      // al mandar un evento ON_USER al padre .. refresca grilla de actividades
      Events.sendEvent(Events.ON_USER, this.comp.getParent(), "actCerrada");
    }
  }
}
