package com.egoveris.te.base.composer;

import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.DocumentoSubsanableDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.rendered.SubsanacionDeDocumentosItemRenderer;

import java.awt.Button;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listfooter;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window;


public class SubsanacionDeDocumentosComposer extends GenericDocumentoComposer {

	private static Logger logger = LoggerFactory.getLogger(SubsanacionDeDocumentosComposer.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private Listbox listboxDocumentos;
	@Autowired
	private Paging pagingDocumento;
	@Autowired
	private Label labelPagina;
	private List<DocumentoDTO> listaDocumentosConPase;
	private AnnotateDataBinder binder;
	private Listfooter footerSize;
	@Autowired
	private Window subsanacionDeDocumentosWindow;
	@Autowired
	private Button aceptar;
	@Autowired
	private Window subsanacionDocVinculacionActoAdmWindow;

	Map<String, DocumentoSubsanableDTO> mapSubsanarOriginal = new HashMap<String, DocumentoSubsanableDTO>();
	Map<String, DocumentoSubsanableDTO> mapSubsanar;

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		this.binder = new AnnotateDataBinder(listboxDocumentos);
		comp.addEventListener(Events.ON_CLICK, new SubsanacionDeDocumentoEventListener(this));
		super.setearVentanaAbierta(this.self);
		super.setExpedienteElectronico(
				(ExpedienteElectronicoDTO) Executions.getCurrent().getDesktop().getAttribute("eeAcordeon"));
		super.initializeDocumentsList(false);
		paginadoDeDocumentos();

		Executions.getCurrent().getDesktop().setAttribute("mapSubsanacion",
				clonarAtribSubsanacion(getListaDocumentosConPase()));

		SubsanacionDeDocumentosItemRenderer subsanacionDeDocumentosItemRenderer = new SubsanacionDeDocumentosItemRenderer(
				this);
		listboxDocumentos.setItemRenderer(subsanacionDeDocumentosItemRenderer);
	}

	private Map<String, DocumentoSubsanableDTO> clonarAtribSubsanacion(List<DocumentoDTO> listDocPase) {

		Map<String, DocumentoSubsanableDTO> mapSubsanar = new HashMap<String, DocumentoSubsanableDTO>();

		for (DocumentoDTO documento : listDocPase) {
			mapSubsanar.put(documento.getNumeroSade(),
					new DocumentoSubsanableDTO(documento.isSubsanado(), documento.isSubsanadoLimitado()));
			this.mapSubsanarOriginal.put(documento.getNumeroSade(),
					new DocumentoSubsanableDTO(documento.isSubsanado(), documento.isSubsanadoLimitado()));
		}
		this.mapSubsanar = mapSubsanar;
		return mapSubsanar;
	}

	public void onDescargarTodos() throws IOException, InterruptedException {
		this.onDescargarTodos("Documentos-" + super.expedienteElectronico.getCodigoCaratula(), listaDocumentosConPase,
				"CON PASE", null);
	}

	@SuppressWarnings("unchecked")
	public void refreshList(List<?> model) {
		this.listaDocumentosConPase = (List<DocumentoDTO>) model;
		Collections.sort(this.listaDocumentosConPase, new ComparatorDocumentos());
		super.refreshList(this.listboxDocumentos, this.listaDocumentosConPase);
		this.footerSize.setLabel((Integer.valueOf(this.listaDocumentosConPase.size())).toString());
		this.binder.loadComponent(this.listboxDocumentos);
	}

	private void paginadoDeDocumentos() {
		if (pagingDocumento.getPageSize() == 10 && this.listaDocumentosConPase.size() > 10) {
			labelPagina.setVisible(true);
		}
	}

	public Listbox getListboxDocumentos() {
		return listboxDocumentos;
	}

	public void setListboxDocumentos(Listbox listboxDocumentos) {
		this.listboxDocumentos = listboxDocumentos;
	}

	public List<DocumentoDTO> getListaDocumentosConPase() {
		return listaDocumentosConPase;
	}

	public void setListaDocumentosConPase(List<DocumentoDTO> listaDocumentosConPase) {
		this.listaDocumentosConPase = listaDocumentosConPase;
	}

	public void onClick$cancelar() {
		try {
			super.initializeDocumentsList(false);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		this.subsanacionDeDocumentosWindow.detach();
	}

	public void onAceptar() {

		if (equalMaps(this.mapSubsanarOriginal, this.mapSubsanar)) {
			Messagebox.show(Labels.getLabel("ee.tramitacion.subsanacion.buttonConfirmar.actoAdministrativo"),
					Labels.getLabel("ee.tramitacion.subsanacion.titulo"), Messagebox.OK, Messagebox.EXCLAMATION);
		} else {
			HashMap<String, Object> hma = new HashMap<>();
			hma.put("ModuleMainController", this);

			this.subsanacionDocVinculacionActoAdmWindow = (Window) Executions
					.createComponents("/expediente/subsanacionDocVinculacionActoAdm.zul", this.self, hma);
			this.subsanacionDocVinculacionActoAdmWindow.setClosable(true);

			this.subsanacionDocVinculacionActoAdmWindow.doModal();
		}
	}

	private boolean equalMaps(Map<String, DocumentoSubsanableDTO> m1, Map<String, DocumentoSubsanableDTO> m2) {
		if (m1.size() != m2.size())
			return false;
		for (String key : m1.keySet()) {
			if (m1.get(key).isSubsanado() != m2.get(key).isSubsanado()) {
				return false;
			} else if (m1.get(key).isSubsanadoLimitado() != m2.get(key).isSubsanadoLimitado()) {
				return false;
			}
		}
		return true;
	}
}

final class SubsanacionDeDocumentoEventListener implements EventListener {

	private SubsanacionDeDocumentosComposer comp;

	public SubsanacionDeDocumentoEventListener(SubsanacionDeDocumentosComposer comp) {
		this.comp = comp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onEvent(Event event) throws Exception {
		if (event.getName().equals(Events.ON_CLICK)) {
			if (event.getData() != null) {
				List<?> model = (List<?>) ((Map<String, Object>) event.getData()).get("model");
				if (model != null) {
					this.comp.refreshList(model);
				}
			}
		}
	}
}

class ComparatorDocumentos implements Comparator<DocumentoDTO> {
	public int compare(DocumentoDTO o1, DocumentoDTO o2) {
		Date fecha1 = o1.getFechaAsociacion();
		Date fecha2 = o2.getFechaAsociacion();
		return fecha2.compareTo(fecha1);
	}
}