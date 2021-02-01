package com.egoveris.te.base.rendered;

import com.egoveris.te.base.model.ActividadAprobDocTad;
import com.egoveris.te.base.service.iface.IActivAsociarDocsTadService;
import com.egoveris.te.base.service.iface.IActivSubsanacionService;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.ZkUtil;
import com.egoveris.te.model.exception.ProcesoFallidoException;
import com.egoveris.vucfront.ws.model.ExternalTipoDocumentoVucDTO;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

public class AprobacionDocsTadItemRender implements ListitemRenderer {

  private static transient Logger logger = LoggerFactory
      .getLogger(AprobacionDocsTadItemRender.class);
  private static final String TIPO_DE_DOC_NO_ENCONTRADO = "Tipo de doc. TAD no encontrado";

  private IActivSubsanacionService activSubsanacionService;
  private IActivAsociarDocsTadService activAsociarDocsTadService;

  private boolean permisoParaModificar = false;

  public AprobacionDocsTadItemRender(boolean b) {
    this.permisoParaModificar = b;
  }

  public void render(Listitem item, Object data, int arg1) {
    activSubsanacionService = (IActivSubsanacionService) SpringUtil
        .getBean(ConstantesServicios.ACTIV_SUBSANACION_SERVICE);
    activAsociarDocsTadService = (IActivAsociarDocsTadService) SpringUtil
        .getBean(ConstantesServicios.ACTIV_ASOCIAR_DOCSTAD_SERVICE);

    final ActividadAprobDocTad data1 = (ActividadAprobDocTad) data;

    final String nroDoc = data1.getNroDocumento();

    Listcell currentCell = new Listcell();
    currentCell.setParent(item);

    // visualizar documento
    Hbox hboxDoc = new Hbox();
    hboxDoc.setParent(currentCell);
    hboxDoc.addEventListener(Events.ON_CLICK, new EventListener() {
      @Override
      public void onEvent(Event event) throws Exception {

        visualizarDocumentoGEDO(nroDoc);
      }
    });
    hboxDoc.setTooltiptext(Labels.getLabel("ee.act.label.doc.tooltip"));
    Image visImage = new Image("/imagenes/edit-find.png");
    visImage.setParent(hboxDoc);

    // Nro doc
    Label vis = new Label(nroDoc);
    vis.setParent(hboxDoc);

    String tipoDoc = null;
    // tipo doc
    try {
      ExternalTipoDocumentoVucDTO tipoDocVuc = activSubsanacionService.getTipoDocVucByCodVuc(data1.getTipoDocTad());
      if (tipoDocVuc == null) {
        tipoDoc = TIPO_DE_DOC_NO_ENCONTRADO;
      } else {
        tipoDoc = tipoDocVuc.getDescripcion() + " (" + tipoDocVuc.getAcronimoTad() + ")";
      }
    } catch (ServiceException e) {
      logger.error(TIPO_DE_DOC_NO_ENCONTRADO, e);
      tipoDoc = TIPO_DE_DOC_NO_ENCONTRADO + " " + data1.getTipoDocTad();
    }
    currentCell = new Listcell(tipoDoc);
    currentCell.setParent(item);

    // accion
    currentCell = new Listcell();
    currentCell.setParent(item);

    Hbox hboxExterior = new Hbox();
    hboxExterior.setParent(currentCell);

    if (data1.getFechaBaja() == null) {
      if (permisoParaModificar) {
        // accion - Aprobar
        Hbox hbox = new Hbox();
        hbox.setParent(hboxExterior);
        hbox.addEventListener(Events.ON_CLICK, new AprobacionEventListener(hboxExterior, data1));

        Image aprobImage = new Image("/imagenes/control_add_blue.png");
        aprobImage.setParent(hbox);

        Label aprobar = new Label(Labels.getLabel("ee.act.label.aprobar"));
        aprobar.setParent(hbox);

        // accion - Rechazar
        Hbox hbox2 = new Hbox();
        hbox2.setParent(hboxExterior);
        hbox2.addEventListener(Events.ON_CLICK, new RechazoEventListener(hboxExterior, data1));

        Image rechazarImage = new Image("/imagenes/decline.png");
        rechazarImage.setParent(hbox2);

        Label rechazar = new Label(Labels.getLabel("ee.act.label.rechazar"));
        rechazar.setParent(hbox2);
      }
    } else {
      // fecha
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
      Date fechaBaja = data1.getFechaBaja();
      // El estado alternativo "getEstadoAlt" esta asociado al estado de
      // la actividad
      // pero en terminos de vinculación de docs. Aprobada = vinculado,
      // rechazada = rechazado
      Label labelFechaAprob = new Label(
          data1.getEstadoAlt() + " ( " + sdf.format(fechaBaja) + " )");
      labelFechaAprob.setParent(hboxExterior);
    }
  }

  private void visualizarDocumentoGEDO(String numeroGedo) {
    Map<String, Object> datos = new HashMap<String, Object>();

    datos.put("nombreArchivo", numeroGedo);

    Window win = (Window) Executions.createComponents("/consultas/ppVisualizarGedo.zul", null,
        datos);

    win.doModal();
  }

  private class AprobacionEventListener implements EventListener {

    ActividadAprobDocTad data;
    Component comp;

    public AprobacionEventListener(Component comp, ActividadAprobDocTad actAprob) {
      this.data = actAprob;
      this.comp = comp;
    }

    @Override
    public void onEvent(Event event) throws Exception {

      String codigoExp = (String) Executions.getCurrent().getDesktop().getAttribute("codigoExp");

      String loggedUser = Executions.getCurrent().getSession()
          .getAttribute(ConstantesWeb.SESSION_USERNAME).toString();

      String estado;
      String msgFin;
      try {
        activAsociarDocsTadService.aprobarAsociarDocTad(data, codigoExp, loggedUser);
        estado = Labels.getLabel("ee.act.aprob.vinculado");
        msgFin = Labels.getLabel("ee.act.aprob.msg.aprob");

        Window acti = ZkUtil.findParentByType(this.comp, Window.class);

        Component histActWindow = acti.getParent();

        Events
            .sendEvent(new Event(Events.ON_USER, (Component) histActWindow, "actulizarBotonPase"));

      } catch (ProcesoFallidoException e) {
        logger.error("error onEvent - ProcesoFallidoException", e);
        estado = Labels.getLabel("ee.act.aprob.rechazado");
        msgFin = Labels.getLabel("ee.act.aprob.msg.recha.excep");
      }
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
      Calendar cal = Calendar.getInstance();
      Date date = cal.getTime();
      Label labelFechaAprob = new Label(estado + " ( " + sdf.format(date) + " )");
      comp.getChildren().clear();
      labelFechaAprob.setParent(comp);

      Messagebox.show(msgFin, Labels.getLabel("ee.general.information"), Messagebox.OK,
          Messagebox.INFORMATION);
    }
  }

  private class RechazoEventListener implements EventListener {

    ActividadAprobDocTad data;
    Component comp;

    public RechazoEventListener(Component comp, ActividadAprobDocTad actAprob) {
      this.data = actAprob;
      this.comp = comp;
    }

    @Override
    public void onEvent(Event event) throws Exception {

      String loggedUser = Executions.getCurrent().getSession()
          .getAttribute(ConstantesWeb.SESSION_USERNAME).toString();

      activAsociarDocsTadService.rechazarActividadAprobDoc(this.data, loggedUser);

      Window acti = ZkUtil.findParentByType(this.comp, Window.class);

      Component histActWindow = acti.getParent();

      Events.sendEvent(new Event(Events.ON_USER, (Component) histActWindow, "actulizarBotonPase"));

      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
      Calendar cal = Calendar.getInstance();
      Date date = cal.getTime();
      Label labelFechaAprob = new Label(
          Labels.getLabel("ee.act.aprob.rechazado") + " ( " + sdf.format(date) + " )");
      comp.getChildren().clear();
      labelFechaAprob.setParent(comp);

      Messagebox.show(Labels.getLabel("ee.act.aprob.msg.recha"),
          Labels.getLabel("ee.general.information"), Messagebox.OK, Messagebox.INFORMATION);
    }
  }
}
