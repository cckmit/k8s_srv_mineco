package com.egoveris.te.base.composer;

import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.util.FiltroEE;

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
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listfooter;
import org.zkoss.zul.Paging;


public class DocumentosSinPaseComposer extends GenericDocumentoComposer{

	/**
	 * 
	 * @Autor lFishkel
	 */
	
	private static final long serialVersionUID = 8887776870918530381L;
	private List<DocumentoDTO> documentosFiltradosSinPase;
	private List<DocumentoDTO> listaCompleta;
	@Autowired
	private Listbox listboxDocumentosSinPase;
	@Autowired
	private Paging pagingDocumentoSinPase;
	@Autowired
	private Label labelPaginaSinPase;
	@Autowired
	private Checkbox mostrarPase;
	private DocumentoDTO ultimoPase;
	private AnnotateDataBinder binder;
	private Listfooter footerSize;
	
	public void doAfterCompose(Component comp) throws Exception{
		super.doAfterCompose(comp);
		comp.addEventListener(Events.ON_CLICK, new DocumentosSinPaseEventListener(this));
		comp.addEventListener(Events.ON_USER, new DocumentosSinPaseEventListener(this));
		this.binder = new AnnotateDataBinder(this.listboxDocumentosSinPase);
		super.setearVentanaAbierta(this.self);
		super.setExpedienteElectronico((ExpedienteElectronicoDTO)Executions.getCurrent().getDesktop().getAttribute("eeAcordeon"));
		super.initializeDocumentsList(false);
		
	}
	private void paginadoDeDocumentosSinPase() {
			labelPaginaSinPase.setVisible((pagingDocumentoSinPase.getPageSize() == 10 && documentosFiltradosSinPase.size() > 10));
	}
	public void onCheck$mostrarPase(){
		if(this.listaCompleta==null){
			this.listaCompleta=super.cargarDocumentosFiltrados();
		}
		if(mostrarPase.isChecked()){
			if(ultimoPase==null){
				ultimoPase = FiltroEE.buscarUltimoPase(this.listaCompleta);
			}
				this.documentosFiltradosSinPase=FiltroEE.filtrarConUltimoPase(this.listaCompleta);
		}else {
			if(ultimoPase==null)		
				this.documentosFiltradosSinPase=FiltroEE.filtrarSinPase(this.listaCompleta);
			else
				this.documentosFiltradosSinPase.remove(ultimoPase);
		}
		super.refreshList(this.listboxDocumentosSinPase, this.documentosFiltradosSinPase);
		this.binder.loadComponent(this.listboxDocumentosSinPase);
		paginadoDeDocumentosSinPase();
	}


	public void onDescargarTodos() throws IOException, InterruptedException{
		this.onDescargarTodos("Documentos-"+super.expedienteElectronico.getCodigoCaratula(), documentosFiltradosSinPase, "SIN PASE", null);	
		super.ordenarDocumentos(documentosFiltradosSinPase);
	}
	
	
	public List<DocumentoDTO> getDocumentosFiltradosSinPase() {
		return documentosFiltradosSinPase;
	}

	public void setDocumentosFiltradosSinPase(List<DocumentoDTO> documentosFiltradosSinPase) {
		this.documentosFiltradosSinPase = documentosFiltradosSinPase;
	}
	
	public List<DocumentoDTO> getListaCompleta() {
		return listaCompleta;
	}
	public void setListaCompleta(List<DocumentoDTO> listaCompleta) {
		this.listaCompleta = listaCompleta;
	}
	@SuppressWarnings("unchecked")
	public void refreshList(List<?> model) {
		this.listaCompleta=(List<DocumentoDTO>) model;
		onCheck$mostrarPase();
		this.documentosFiltradosSinPase=super.ordenarDocumentos(this.documentosFiltradosSinPase);
		super.refreshList(this.listboxDocumentosSinPase, this.documentosFiltradosSinPase);
		this.footerSize.setLabel((new Integer(this.documentosFiltradosSinPase.size())).toString());
		this.binder.loadComponent(this.listboxDocumentosSinPase);
	}

}
final class DocumentosSinPaseEventListener implements EventListener{

	private DocumentosSinPaseComposer comp;
	public DocumentosSinPaseEventListener(DocumentosSinPaseComposer comp){
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
		if(event.getName().equals(Events.ON_USER)){
			comp.onDescargarTodos("Documentos-"+comp.getExpedienteElectronico().getCodigoCaratula(), comp.getDocumentosFiltradosSinPase(), "SIN PASE", null);						
		}
		
	}
	
}
