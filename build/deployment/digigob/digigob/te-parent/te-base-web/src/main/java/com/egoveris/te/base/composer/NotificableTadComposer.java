package com.egoveris.te.base.composer;

import com.egoveris.te.base.exception.NegocioException;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.service.DocumentoGedoService;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.service.iface.INotificacionEEService;
import com.egoveris.te.base.util.ConstantesWeb;
//TODO comentado en espera de vuc
//import com.egoveris.vuc.external.service.client.service.external.IDocumentoService;
//import com.egoveris.vuc.external.service.client.service.external.bean.DocumentosExternosRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.remoting.RemoteAccessException;
import org.zkoss.spring.SpringUtil;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listfooter;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class NotificableTadComposer extends GenericDocumentoComposer {

	private static final long serialVersionUID = -604736600612270418L;
	final static Logger logger = LoggerFactory
			.getLogger(DocumentosTadInboxComposer.class);

	public static final String EXPEDIENTE = "ee";
	public static final String CLAVE_TAD = "claveTad";
	private ExpedienteElectronicoDTO ee;
	private String claveTad;
	private List<DocumentoDTO> listaDocsDef;
	private Button notificarTad;
	private Listfooter footerSize;
	@Autowired
	private Paging pagingDocumento;
	@Autowired
	private Label labelPagina;
	@Autowired
	private AnnotateDataBinder binder;
	private DocumentoDTO documento;
//	@Autowired
//	private IDocumentoService documentoService;
	@Autowired
	private INotificacionEEService notificacionEEService;
	@Autowired
	private Textbox motivoNotificacion;
	@Autowired
	private DocumentoGedoService documentoGedoService;
	@Autowired
	private ExpedienteElectronicoService expedienteElectronicoService;
	@Autowired
	private Window notificarExpedienteTadWindow;
	private List<String> selectedDocumentos;
	@Autowired
	private Listbox item;
	private String cuit;

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		this.binder = new AnnotateDataBinder(comp);
		comp.addEventListener(Events.ON_USER,
				new NotificableTadOnNotifyWindowListener(this));

		// Obtiene los documentos definitivos
		listaDocsDef = new ArrayList<>();
		for (DocumentoDTO doc : this.ee.getDocumentos()) {
			if (doc.getDefinitivo()) {
				listaDocsDef.add(doc);
			}
		}

		listaDocsDef = super.ordenarDocumentos(listaDocsDef);
		this.footerSize.setLabel((new Integer(this.listaDocsDef.size()))
				.toString());

//		documentoService = (IDocumentoService) SpringUtil
//				.getBean("documentoTadService");
		notificacionEEService = (INotificacionEEService) SpringUtil
				.getBean("notificacionEEService");
	}

	public void notificar() throws IOException, InterruptedException,
			WrongValueException {

		String loggedUsername = (String) Executions.getCurrent().getDesktop()
				.getSession().getAttribute(ConstantesWeb.SESSION_USERNAME);
//		DocumentosExternosRequest documentosExternosRequest = new DocumentosExternosRequest();

		/**
		 * Si la trata tiene clave TAD la obtiene de ahi Sino la obtiene de la
		 * ingresada manualmente
		 */

		// Clave TAD
//		if (ee.getTrata().getClaveTad() != null) {
//			documentosExternosRequest.setCuit(ee.getTrata().getClaveTad());
//		} else {
//			documentosExternosRequest.setCuit(this.claveTad);
//		}
//
//		cuit = documentosExternosRequest.getCuit();
		// Documentos
		obtenerSeleccionados();
		if (this.selectedDocumentos.size() == 0) {
			Messagebox.show(
					Labels.getLabel("ee.general.ningunDocumentoSeleccionado"),
					Labels.getLabel("ee.general.information"), Messagebox.OK,
					Messagebox.EXCLAMATION);
			Clients.clearBusy();
			return;
		}
		List<DocumentoDTO> docs = new ArrayList<>();
		for (DocumentoDTO doc : listaDocsDef) {
			if (selectedDocumentos.contains(doc.getNumeroSade())) {
				docs.add(doc);
			}
		}

		// Motivo
		String motivoDeNotificacion = motivoNotificacion.getValue();
		if (motivoDeNotificacion == null || "".equals(motivoDeNotificacion)) {
			motivoDeNotificacion = Labels.getLabel("ee.notificacion.motivo");
		}
		
//		documentosExternosRequest.setCodigoEE(ee.getCodigoCaratula());
//		documentosExternosRequest.setDocumentos(this.selectedDocumentos);
//		documentosExternosRequest.setMotivo(motivoDeNotificacion);

		try {
			
//			this.documentoService.enviarDocumentosAPersona(documentosExternosRequest);
//
//			DocumentoDTO d = this.adjuntarDocsDeNotificacion(docs, loggedUsername,
//					motivoDeNotificacion);
//			notificacionEEService.notificarBuzonJudicial(loggedUsername, ee,
//					d, motivoDeNotificacion,
//					documentosExternosRequest.getCuit());
			
//			ee.agregarDocumento(d);
			
			// este metodo ya modifica el EE
			realizarVinculacionDefinitiva(loggedUsername);

			Events.sendEvent(this.self.getParent(), new Event(Events.ON_CHANGE));

			Messagebox.show(Labels.getLabel("ee.docTadinbox.msgbox.enviadoNotificacionCorrec"),
					Labels.getLabel("ee.act.msg.title.ok"), Messagebox.OK, Messagebox.INFORMATION);

			this.notificarExpedienteTadWindow.detach();

		} catch (NegocioException ne) {
			Messagebox.show(ne.getMessage(), Labels.getLabel("ee.docTadinbox.msgbox.errorNot"),
					Messagebox.OK, Messagebox.EXCLAMATION);
			logger.error("Error al notificar: " + ne.getMessage());
		 }  catch (RemoteAccessException e) {
	        	logger.error(e.getMessage(), e);
	        	Messagebox.show(e.getMessage(), Labels.getLabel("ee.general.advertencia"), Messagebox.OK, Messagebox.ERROR);	
		} catch (Exception e) {
			Messagebox.show(Labels.getLabel("ee.notificacion.error"),
					Labels.getLabel("ee.general.information"), Messagebox.OK,
					Messagebox.EXCLAMATION);
			logger.error("Error al notificar: " + e.getMessage());
		} finally {
			Clients.clearBusy();
		}
	}
	

	@SuppressWarnings("unchecked")
	private void obtenerSeleccionados() throws InterruptedException {

		this.selectedDocumentos = new ArrayList<String>();
		recorrerLista(this.item.getChildren());
	}

	@SuppressWarnings("unchecked")
	private void recorrerLista(List<Component> children) {

		for (Component child : children) {
			if (child instanceof Checkbox) {
				Checkbox check = (Checkbox) child;
				if (check.isChecked()) {
					this.selectedDocumentos.add(check.getId());
				}
			} else {
				recorrerLista(child.getChildren());
			}
		}
	}

	private DocumentoDTO adjuntarDocsDeNotificacion(List<DocumentoDTO>docs,String loggedUsername,String motivo){
		
		String referenciaExpediente = null;
		String numerosDocNotificar = "";
		String referencia = Labels.getLabel("ee.notificacion.motivo");	
		for (int i = 0; i < docs.size(); i++) {
			
			if (docs.size()-1==i) {
				numerosDocNotificar += docs.get(i).getNumeroSade();
			} else {
				numerosDocNotificar += docs.get(i).getNumeroSade() + " - ";
			}
		}
		referenciaExpediente = "\n Los documentos notificados son: " + numerosDocNotificar + "\n CUIL: " + cuit;
		DocumentoDTO d = documentoGedoService.armarDocDeNotificacion(ee, loggedUsername, referencia,motivo + referenciaExpediente);
		d.setDefinitivo(false);
		return d;
	}
	
	private void realizarVinculacionDefinitiva(String loggedUsername) {

		this.expedienteElectronicoService.realizarVinculacionDefinitiva(this.ee, loggedUsername);
	}

	private void mostrarForegroundBloqueante() {
		Clients.showBusy(Labels.getLabel("ee.tramitacion.notificandoTad"));
	}

	public void onNotificar() throws InterruptedException {

		this.mostrarForegroundBloqueante();
		Events.echoEvent(Events.ON_USER, this.self, "notificar");
	}

	public void onClick$cancelar() {
		((Window) this.self).onClose();
	}

	public DocumentoDTO getDocumento() {
		return documento;
	}

	public void setDocumento(DocumentoDTO documento) {
		this.documento = documento;
	}

	public List<DocumentoDTO> getListaDocsDef() {
		return listaDocsDef;
	}

	public void setListaDocsDef(List<DocumentoDTO> listaDocsDef) {
		this.listaDocsDef = listaDocsDef;
	}

	public Button getNotificarTad() {
		return notificarTad;
	}

	public void setNotificarTad(Button notificarTad) {
		this.notificarTad = notificarTad;
	}

	public String getClaveTad() {
		return claveTad;
	}

	public void setClaveTad(String claveTad) {
		this.claveTad = claveTad;
	}

	public Textbox getMotivoNotificacion() {
		return motivoNotificacion;
	}

	public void setMotivoNotificacion(Textbox motivoNotificacion) {
		this.motivoNotificacion = motivoNotificacion;
	}

	public String getCuit() {
		return cuit;
	}

	public void setCuit(String cuit) {
		this.cuit = cuit;
	}
	
}

final class NotificableTadOnNotifyWindowListener implements EventListener {
	private NotificableTadComposer composer;

	public NotificableTadOnNotifyWindowListener(
			NotificableTadComposer notificableTadComposer) {
		this.composer = notificableTadComposer;
	}

	public void onEvent(Event event) throws Exception {
		if (event.getName().equals(Events.ON_USER)) {
			if (event.getData().equals("notificar")) {
				this.composer.notificar();
			}
		}
	}
}