package com.egoveris.te.base.composer;

import com.egoveris.te.base.model.DocumentoArchivoDeTrabajoDTO;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.service.iface.IAccesoWebDavService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;



public class DocumentoDetalleComposer extends EEGenericForwardComposer{
/**
	 * 
	 */
	private static final long serialVersionUID = -1186132889159036760L;
@Autowired
private Window documentoDetalleWindow;
@Autowired
private AnnotateDataBinder binder;
@Autowired
private Listbox archivosTrabajoListBox;

private List<DocumentoArchivoDeTrabajoDTO> archDeTrabajo;
private DocumentoArchivoDeTrabajoDTO selectedArchDeTrabajo;
private DocumentoArchivoDeTrabajoDTO documentoArchivoDeTrabajo;
@Autowired
private IAccesoWebDavService visualizaDocumentoService;
private DocumentoDTO documento;
    public void doAfterCompose(Component comp) throws Exception {
    	    super.doAfterCompose(comp);
    	    this.binder = new AnnotateDataBinder(comp);
	        this.documento = (DocumentoDTO) Executions.getCurrent().getArg().get("documento");
	        this.archDeTrabajo = documento.getArchivosDeTrabajo();
    }
    public void onVisualizar() throws InterruptedException {
    try {
			byte[] contenidoArchivo = null;
			DocumentoArchivoDeTrabajoDTO documentoArchivoDeTrabajo = this.selectedArchDeTrabajo;
			contenidoArchivo = this.visualizaDocumentoService.obtenerArchivoDeTrabajoWebDav(documentoArchivoDeTrabajo.getPathRelativo(),documentoArchivoDeTrabajo.getArchivo());
			Filedownload.save(contenidoArchivo, null, documentoArchivoDeTrabajo.getArchivo());
    	}
     catch (Exception e) {
		Messagebox.show(
						Labels.getLabel("ee.tramitacion.archivoNoExisteEnRepositorio"),
						Labels.getLabel("ee.tramitacion.informacion.archivoNoExiste"),
						Messagebox.OK, Messagebox.EXCLAMATION);
	}
    }
    public void onClick$volver(){
    	this.documentoDetalleWindow.detach();
    }
    public Window getDocumentoDetalleWindow() {
    	return documentoDetalleWindow;
    }
    public void setDocumentoDetalleWindow(Window documentoDetalleWindow) {
    	this.documentoDetalleWindow = documentoDetalleWindow;
    }
    public AnnotateDataBinder getBinder() {
    	return binder;
    }
    public void setBinder(AnnotateDataBinder binder) {
    	this.binder = binder;
    }
    public Listbox getArchivosTrabajoDoc() {
    	return archivosTrabajoListBox;
    }
    public void setArchivosTrabajoDoc(Listbox archivosTrabajoDoc) {
    	this.archivosTrabajoListBox = archivosTrabajoDoc;
    }
    public List<DocumentoArchivoDeTrabajoDTO> getArchDeTrabajo() {
    	return archDeTrabajo;
    }
    public void setArchDeTrabajo(List<DocumentoArchivoDeTrabajoDTO> archDeTrabajo) {
    	this.archDeTrabajo = archDeTrabajo;
    }
    public DocumentoArchivoDeTrabajoDTO getSelectedArchDeTrabajo() {
    	return selectedArchDeTrabajo;
    }
    public void setSelectedArchDeTrabajo(
    		DocumentoArchivoDeTrabajoDTO selectedArchDeTrabajo) {
    	this.selectedArchDeTrabajo = selectedArchDeTrabajo;
    }
    public DocumentoArchivoDeTrabajoDTO getDocumentoArchivoDeTrabajo() {
    	return documentoArchivoDeTrabajo;
    }
    public void setDocumentoArchivoDeTrabajo(
    		DocumentoArchivoDeTrabajoDTO documentoArchivoDeTrabajo) {
    	this.documentoArchivoDeTrabajo = documentoArchivoDeTrabajo;
    }
    public DocumentoDTO getDocumento() {
    	return documento;
    }
    public void setDocumento(DocumentoDTO documento) {
    	this.documento = documento;
    }
}
