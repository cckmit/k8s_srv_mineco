package com.egoveris.te.base.composer;

import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listfooter;
import org.zkoss.zul.Paging;


public class DocumentosConPaseComposer extends GenericDocumentoComposer{

	/**
	 * 
	 * @Autor lFishkel
	 */
	
	private static final long serialVersionUID = 8887776870918530381L;
	@Autowired
	private Listbox listboxDocumentos;
	@Autowired
	private Paging pagingDocumento;
	@Autowired
	private Label labelPagina;
	private List<DocumentoDTO> listaDocumentosConPase;
	private AnnotateDataBinder binder;
	private Listfooter footerSize;
	
	
	public void doAfterCompose(Component comp) throws Exception{
		super.doAfterCompose(comp);
		this.binder = new AnnotateDataBinder(listboxDocumentos);
		comp.addEventListener(Events.ON_CLICK, new DocumentosConPaseEventListener(this));
		comp.addEventListener(Events.ON_USER, new DocumentosConPaseEventListener(this));
		super.setearVentanaAbierta(this.self);
		super.setExpedienteElectronico((ExpedienteElectronicoDTO)Executions.getCurrent().getDesktop().getAttribute("eeAcordeon"));
		super.initializeDocumentsList(false);
		paginadoDeDocumentos();
	}
	
	
	@Override
	public void onDesasociarDocumentos() {
		this.listaDocumentosConPase.remove(this.selectedDocumento);
		super.onDesasociarDocumentos();
	}
	
	
	
	// TODO ver todo de documentos filtro composer

	public void onDescargarTodos() throws IOException, InterruptedException{
		this.onDescargarTodos("Documentos-"+getExpedienteElectronico().getCodigoCaratula(), listaDocumentosConPase, "CON PASE", null);
		super.ordenarDocumentos(listaDocumentosConPase);
	}
	
	
	
	
	
	
	@SuppressWarnings("unchecked")
	public void refreshList(List<?> model){
		this.listaDocumentosConPase = (List<DocumentoDTO>) model;
		//Collections.sort(this.listaDocumentosConPase, new ComparatorDocumento());
		listaDocumentosConPase = super.ordenarDocumentos(listaDocumentosConPase);
		super.refreshList(this.listboxDocumentos, this.listaDocumentosConPase);
		this.footerSize.setLabel((new Integer(this.listaDocumentosConPase.size())).toString());
		this.binder.loadComponent(this.listboxDocumentos);
	}
	private void paginadoDeDocumentos() {
		if (pagingDocumento==null || this.listaDocumentosConPase==null) return;
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
	
	

}final class DocumentosConPaseEventListener implements EventListener{

	private DocumentosConPaseComposer comp;
	public DocumentosConPaseEventListener(DocumentosConPaseComposer comp){
		this.comp = comp;
	}
	@SuppressWarnings("unchecked")
	@Override
	public void onEvent(Event event) throws Exception {
		if(event.getName().equals(Events.ON_CLICK)){
			if(event.getData()!=null){
				List<?> model = (List<?>) ((Map<String,Object>)event.getData()).get("model");
				if(model!=null){
					this.comp.refreshList(model);
				}
			}
		}
		if (event.getName().equals(Events.ON_USER)){
			comp.onDescargarTodos("Documentos-"+comp.getExpedienteElectronico().getCodigoCaratula(), comp.getListaDocumentosConPase(), "CON PASE", null);						
		}
	}
	
}

/*
 class ComparatorDocumento implements Comparator<Documento>{
		public int compare(Documento o1, Documento o2) {
			Date fecha1 = o1.getFechaAsociacion();
			Date fecha2 = o2.getFechaAsociacion();
			return fecha2.compareTo(fecha1);	
		}
}	*/