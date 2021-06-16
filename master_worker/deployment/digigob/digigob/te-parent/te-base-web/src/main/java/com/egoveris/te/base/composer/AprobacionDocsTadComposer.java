package com.egoveris.te.base.composer;

import com.egoveris.te.base.model.ActividadAprobDocTad;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.rendered.AprobacionDocsTadItemRender;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.service.iface.IActivAsociarDocsTadService;

import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.ConstantesWeb.VISTA;


import com.egoveris.te.model.exception.ParametroIncorrectoException;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

public class AprobacionDocsTadComposer extends GenericActividadComposer {

  private static final long serialVersionUID = -6095545008220491313L;
  private static final Logger logger = LoggerFactory.getLogger("AprobacionDocsTadComposer");
  private Listbox actividadesListbox;
  private Label expLabel;

  // services
  private IActivAsociarDocsTadService activAsociarDocsTadService;

  @Override
  protected void modoEdicionComposer(Component c) throws Exception {
    initComposer(c, true);
  }

  @Override
  protected void modoLecturaComposer(Component c) throws Exception {
    initComposer(c, false);
  }

  private void initComposer(Component c, boolean permisoParaModificar) throws Exception {
    activAsociarDocsTadService = (IActivAsociarDocsTadService) SpringUtil
        .getBean(ConstantesServicios.ACTIV_ASOCIAR_DOCSTAD_SERVICE);

    Executions.getCurrent().getDesktop().setAttribute("codigoExp",
        getPopupActividad().getNroExpediente());

    // Titulo de la ventana con tipo de actividad
    ((Window) this.self).setTitle(getPopupActividad().getTipoActividadDecrip());

    // nro expediente
    expLabel.setValue(getPopupActividad().getNroExpediente());

    List<ActividadAprobDocTad> subActivAprobDoc = activAsociarDocsTadService
        .buscarActividadesAprobDoc(getPopupActividad().getId());

    actividadesListbox.setItemRenderer(new AprobacionDocsTadItemRender(permisoParaModificar));

    actividadesListbox.setModel(new BindingListModelList(subActivAprobDoc, true));
  }

  public void onClick$cerrar() throws InterruptedException {
    Events.sendEvent(this.self, new Event(Events.ON_CLOSE));
  }

  private boolean requiereOtraSubsanacion() {

    boolean hayRechazados = false;

    List<ActividadAprobDocTad> subActivAprobDoc = activAsociarDocsTadService
        .buscarActividadesAprobDoc(getPopupActividad().getId());

    for (ActividadAprobDocTad actAprob : subActivAprobDoc) {
      if (actAprob.getFechaBaja() != null) {
        if (actAprob.isDocRechazado()) {
          hayRechazados = true;
        }
      } else {
        // no tiene fecha de baja
        return false;
      }
    }
    return hayRechazados;
  }

  // onclick visualizar
  public void onVerExpediente() throws SuspendNotAllowedException, InterruptedException {
    DetalleExpedienteElectronicoComposer.openInModal(this.self,
        getPopupActividad().getNroExpediente());
  }

  private void onSubsanar(Component padre)
      throws SuspendNotAllowedException, InterruptedException {

    ExpedienteElectronicoService eeService = (ExpedienteElectronicoService) SpringUtil
        .getBean(ConstantesServicios.EXP_ELECTRONICO_SERVICE);
    ExpedienteElectronicoDTO ee;
    try {
      ee = eeService.obtenerExpedienteElectronicoPorCodigo(getPopupActividad().getNroExpediente());
    } catch (ParametroIncorrectoException e) {
      logger.error("error en metodo onSubsanar() - ParametroIncorrectoException", e);
      throw new InterruptedException("No puede obtener el expediente por código");
    }

    Executions.getCurrent().getDesktop().setAttribute("expedienteElectronico", ee);
    Executions.getCurrent().getDesktop().setAttribute(ConstantesWeb.VISTA_ACTIVIDAD, VISTA.EDICION);
    // "this.self.getParent()" no funciona .. pierde el padre
    Window subsanarWin = (Window) Executions.createComponents(
        "/expediente/subsanacionExpediente.zul", padre, new HashMap<Object, Object>());
    subsanarWin.setClosable(true);
    subsanarWin.doModal();
  }

  @Override
  protected EventListener onCloseListener() {
    return new EventListener() {

      @Override
      public void onEvent(Event event) throws Exception {

        final Component padre = self.getParent();

        // al mandar un evento ON_USER al padre .. refresca grilla de
        // actividades
        Events.sendEvent(Events.ON_USER, padre, "actCerrada");

        // Si estoy en modo edicion y requiere subsanacion le pregunto
        // si quiere ser redirigido
        if (getPopupVistaActividad().equals(VISTA.EDICION)) {

          Events.sendEvent(Events.ON_USER, padre, "aprobDocCerrado");

          if (requiereOtraSubsanacion()) {
            // preguntar si quiere otra subsanacion
            Messagebox.show(Labels.getLabel("ee.subsanacion.msg.subsRequerida"),
                Labels.getLabel("ee.general.information"), Messagebox.YES | Messagebox.NO,
                Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
                  public void onEvent(Event evt) throws InterruptedException {
                    switch (((Integer) evt.getData()).intValue()) {
                    case Messagebox.YES:
                      onSubsanar(padre);
                      break;
                    case Messagebox.NO:
                      break;
                    }
                  }

                });
          }
        }
      }
    };
  }
}