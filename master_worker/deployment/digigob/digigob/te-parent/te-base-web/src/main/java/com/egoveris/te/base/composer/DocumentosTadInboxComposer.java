package com.egoveris.te.base.composer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.remoting.RemoteAccessException;
import org.zkoss.spring.SpringUtil;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.egoveris.te.base.exception.NegocioException;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.service.DocumentoGedoService;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.service.iface.INotificacionEEService;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.ConstantesWeb;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class DocumentosTadInboxComposer extends GenericDocumentoComposer {

  private static final String LABEL_INFORMATION = Labels.getLabel("ee.general.information");

  private static final Logger logger = LoggerFactory.getLogger(DocumentosTadInboxComposer.class);

  /**
  * 
  */
  private static final long serialVersionUID = -919264189593120853L;

  @Autowired
  private AnnotateDataBinder binder;

  @Autowired
  private Window comunicarTadWindow;

  private ExpedienteElectronicoDTO expedienteElectronico;

  private List<DocumentoDTO> documentosOrdenados = new ArrayList<>();

  private List<DocumentoDTO> documentos = new ArrayList<>();

  @Autowired
  private Listbox listboxDocumentos;

  @Autowired
  private Paging pagingDocumento;

  @Autowired
  private DocumentoGedoService documentoGedoService;

  @Autowired
  private Textbox motivoNotificacion;

  @Autowired
  private Label labelPagina;

  @WireVariable(ConstantesServicios.NOTIFICACION_SERVICE)
  private INotificacionEEService notificacionEEService;

  @WireVariable(ConstantesServicios.EXP_ELECTRONICO_SERVICE)
  private ExpedienteElectronicoService expedienteElectronicoService;

  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    
    expedienteElectronico = (ExpedienteElectronicoDTO) Executions.getCurrent().getArg()
        .get("expediente");
 
    documentoGedoService = (DocumentoGedoService) SpringUtil.getBean(ConstantesServicios.DOCUMENTO_GEDO_SERVICE);
 
    this.binder = new AnnotateDataBinder(listboxDocumentos);

    documentosOrdenados = notificacionEEService
        .obtenerDocumentosNotificables(expedienteElectronico);
    Collections.sort(documentosOrdenados, new ComparatorDocumento());
    documentos.addAll(documentosOrdenados);

    paginadoDeDocumentos();
    comp.addEventListener(Events.ON_NOTIFY, new NotificacionOnNotifyWindowListener(this));

    this.self.setAttribute("dontAskBeforeClose", true);
  }

  public Window getComunicarTadWindow() {
    return comunicarTadWindow;
  }

  public void setComunicarTadWindow(Window comunicarTadWindow) {
    this.comunicarTadWindow = comunicarTadWindow;
  }

  public void setDocumentos(List<DocumentoDTO> documentos) {
    this.documentos = documentos;
  }

  public List<DocumentoDTO> getDocumentos() {
    return documentos;
  }

  public Textbox getMotivoNotificacion() {
    return motivoNotificacion;
  }

  public void setMotivoNotificacion(Textbox motivoNotificacion) {
    this.motivoNotificacion = motivoNotificacion;
  }

  public DocumentoGedoService getDocumentoGEDOService() {
    return documentoGedoService;
  }

  public void setDocumentoGEDOService(DocumentoGedoService documentoGEDOService) {
    this.documentoGedoService = documentoGEDOService;
  }

  public void setExpedienteElectronicoService(
      ExpedienteElectronicoService expedienteElectronicoService) {
    this.expedienteElectronicoService = expedienteElectronicoService;
  }

  public ExpedienteElectronicoService getExpedienteElectronicoService() {
    return expedienteElectronicoService;
  }

  public void onClick$cancelar() {
    this.comunicarTadWindow.detach();
  }

  public void onNotificar() throws IOException, InterruptedException {

    @SuppressWarnings("rawtypes")
    Set selectedItems = this.listboxDocumentos.getSelectedItems();

    if (selectedItems.size() < 0) {
      Messagebox.show(Labels.getLabel("ee.general.ningunaTareaSeleccionada"),
          LABEL_INFORMATION, Messagebox.OK, Messagebox.EXCLAMATION);
    } else {
      notificar();
    }

  }

  @SuppressWarnings("unchecked")
  public void refreshList(List<?> model) {
    this.documentos = (List<DocumentoDTO>) model;
    Collections.sort(this.documentos, new ComparatorDocumento());
    super.refreshList(this.listboxDocumentos, this.documentos);
    this.binder.loadComponent(this.listboxDocumentos);
  }

  private void paginadoDeDocumentos() {
    if (pagingDocumento.getPageSize() == 10 && this.documentos.size() > 10) {
      labelPagina.setVisible(true);
    }
  }

  public Listbox getListboxDocumentos() {
    return listboxDocumentos;
  }

  public void setListboxDocumentos(Listbox listboxDocumentos) {
    this.listboxDocumentos = listboxDocumentos;
  }

  public List<DocumentoDTO> documentos() {
    return documentos;
  }

  @SuppressWarnings("rawtypes")
  void notificar() throws InterruptedException {
    String loggedUsername = (String) Executions.getCurrent().getDesktop().getSession()
        .getAttribute(ConstantesWeb.SESSION_USERNAME);
    Set selectedItems = this.listboxDocumentos.getSelectedItems();

    if (selectedItems != null && !selectedItems.isEmpty()) {

      try {

        Iterator it = selectedItems.iterator();

        List<DocumentoDTO> documentosSeleccionados = new ArrayList<>();

        while (it.hasNext()) {
          Listitem li = (Listitem) it.next();
          Calendar cal = Calendar.getInstance();
          cal.setTime(new Date());

          DocumentoDTO docTrabajo = this.documentos.get(li.getIndex());
          documentosSeleccionados.add(docTrabajo);
          docTrabajo.setDefinitivo(true);
        }

        String motivoDeNotificacion = motivoNotificacion.getValue();
        if (motivoDeNotificacion == null || "".equals(motivoDeNotificacion)) {
          motivoDeNotificacion = Labels.getLabel("ee.notificacion.motivo");
        }
        
       notificacionEEService.altaNotificacionVUC(loggedUsername, expedienteElectronico,
                documentosSeleccionados, motivoDeNotificacion);

       this.adjuntarDocsDeNotificacion(documentosSeleccionados, loggedUsername,
                motivoDeNotificacion);

        Events.sendEvent(this.self.getParent(), new Event(Events.ON_CHANGE));

        Messagebox.show(Labels.getLabel("ee.docTadinbox.msgbox.enviadoNotificacionCorrec"),
            LABEL_INFORMATION, Messagebox.OK, Messagebox.INFORMATION);
        this.comunicarTadWindow.detach();

      } catch (NegocioException ne) {
        Messagebox.show(ne.getMessage(), Labels.getLabel("ee.docTadinbox.msgbox.errorNot"),
            Messagebox.OK, Messagebox.EXCLAMATION);
        logger.error("Error al notificar: " + ne.getMessage());

      } catch (RemoteAccessException e) {
        logger.error(e.getMessage(), e);
        Messagebox.show(e.getMessage(), Labels.getLabel("ee.general.advertencia"), Messagebox.OK, Messagebox.ERROR);
      } catch (Exception e) {
        Messagebox.show(Labels.getLabel("ee.notificacion.error"),
            LABEL_INFORMATION, Messagebox.OK, Messagebox.EXCLAMATION);
        logger.error("Error al notificar: " + e.getMessage());
      }
      this.binder.loadComponent(this.listboxDocumentos);
    } else {
      Messagebox.show(Labels.getLabel("ee.subsanacion.lists.validation"),
          LABEL_INFORMATION, Messagebox.OK, Messagebox.INFORMATION);
    }
  }

  private void adjuntarDocsDeNotificacion(List<DocumentoDTO> docs, String loggedUsername,
      String motivo) {

    String referenciaExpediente;
    StringBuilder numerosDocNotificar = new StringBuilder("");
    String referencia = Labels.getLabel("ee.subsanacion.generacion.de.doc.notificacion");

    for (int i = 0; i < docs.size(); i++) {

      if (docs.size() - 1 == i) {
        numerosDocNotificar.append(docs.get(i).getNumeroSade());
      } else {
        numerosDocNotificar.append(docs.get(i).getNumeroSade()).append("\n");
      }
    }

    referenciaExpediente = "\n\n Se han notificado los siguientes documentos:\n\n"
        + numerosDocNotificar.toString();
    DocumentoDTO d = documentoGedoService.armarDocDeNotificacion(expedienteElectronico,
        loggedUsername, referencia, motivo + referenciaExpediente);
    d.setMotivo(referencia);
    d.setDefinitivo(true);
    
    hacerDefinitivoDocNot(docs);
    
    expedienteElectronico.agregarDocumento(d);
    expedienteElectronicoService.modificarExpedienteElectronico(expedienteElectronico);
  }

	private void hacerDefinitivoDocNot(List<DocumentoDTO> docs) {
		List<String> numeroDocs = docs.stream().map(DocumentoDTO::getNumeroSade).collect(Collectors.toList());
		for (DocumentoDTO docEE : expedienteElectronico.getDocumentos()) {
			if (numeroDocs.contains(docEE.getNumeroSade())) {
				docEE.setDefinitivo(true);
			}
		}
	}

}

final class NotificacionOnNotifyWindowListener implements EventListener {
  private DocumentosTadInboxComposer composer;

  public NotificacionOnNotifyWindowListener(DocumentosTadInboxComposer comp) {
    this.composer = comp;
  }

  public void onEvent(Event event) throws Exception {
    if (event.getName().equals(Events.ON_CLICK) && event.getData() != null) {
      List<?> model = (List<?>) ((Map<String, Object>) event.getData()).get("model");
      
      if (model != null) {
        this.composer.refreshList(model);
      }
    }
  }
}

class ComparatorDocumento implements Comparator<DocumentoDTO> {

  @Override
  public int compare(DocumentoDTO o1, DocumentoDTO o2) {
    Date fecha1 = o1.getFechaAsociacion();
    Date fecha2 = o2.getFechaAsociacion();
    return fecha2.compareTo(fecha1);
  }
}
