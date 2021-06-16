package com.egoveris.te.base.vm;

import com.egoveris.deo.model.model.RequestExternalConsultaDocumento;
import com.egoveris.deo.model.model.ResponseExternalGenerarDocumento;
import com.egoveris.deo.ws.service.IExternalConsultaDocumentoServiceExt;
import com.egoveris.te.base.exception.ServiceException;
import com.egoveris.te.base.helper.DocumentosOperHelper;
import com.egoveris.te.base.model.OperacionDocumentoDTO;
import com.egoveris.te.base.service.iface.IOperacionDocumentoService;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.ConstantesServicios;

import java.io.File;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;


@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class DocumentosOperGridVM {
	
	private static final Logger logger = LoggerFactory.getLogger(DocumentosOperGridVM.class);
	
	private static final String KEY_ERROR_UPLOAD_TITLE = "ee.operacion.documentosOperacion.error.upload.title";
	private static final String KEY_ERROR_UPLOAD_MSG = "ee.operacion.documentosOperacion.error.upload.message";
	private static final String KEY_ERROR_NO_PDF_TITLE = "ee.operacion.documentosOperacion.error.uploadnoPdf.title";
	private static final String KEY_ERROR_NO_PDF_MSG = "ee.operacion.documentosOperacion.error.uploadnoPdf.message";
	private static final String KEY_ERROR_DOWNLOAD_TITLE = "ee.operacion.documentosOperacion.error.download.title";
	private static final String KEY_ERROR_DOWNLOAD_MSG = "ee.operacion.documentosOperacion.error.download.message";
	private static final String KEY_CONFIRM_DELETE_TITLE = "ee.operacion.documentosOperacion.popupEliminar.title";
	private static final String KEY_CONFIRM_DELETE_MSG = "ee.operacion.documentosOperacion.popupEliminar.message";
	
	@WireVariable(ConstantesServicios.OPERACION_DOC_SERVICE)
	private IOperacionDocumentoService operacionDocumentoService;
	
	@WireVariable(ConstantesServicios.CONSULTA_DOC_EXTERNAL_SERVICE)
	private IExternalConsultaDocumentoServiceExt consultaDocumentoService;
	
	private List<OperacionDocumentoDTO> documentos;
	private boolean obligatorios;
	private boolean soloLectura;
	private boolean hayDocumentos;
	
	/**
	 * Init. Este VM sirve tanto para los documentos de la operacion
	 * obligatorios como opcionales. En la vista (documentosOperGrid.zul)
	 * presenta un comportamiento distinto si es obligatorio u opcional
	 * 
	 * @param documentos Lista de documentos
	 * @param obligatorios True o false dependiendo de si son obligatorios
	 */
	@Init
	public void init(@ExecutionArgParam("documentos") List<OperacionDocumentoDTO> documentos,
			@ExecutionArgParam("obligatorios") boolean obligatorios, @ExecutionArgParam("soloLectura") boolean soloLectura) {
		setDocumentos(documentos);
		setObligatorios(obligatorios);
		setSoloLectura(soloLectura);
		
		if (!documentos.isEmpty()) {
		  setHayDocumentos(true);
		}
	}
	
  /**
	 * Comando que se ejecuta al subir un documento por primera vez, o modificar
	 * un documento existente
	 * 
	 * @param uploadEvent
	 * @param documentoOperacion
	 */
	@Command
	public void onUploadDocumento(@ContextParam(ContextType.TRIGGER_EVENT) UploadEvent uploadEvent,
			@BindingParam("documentoOperacion") OperacionDocumentoDTO documentoOperacion) {
		
		if (documentoOperacion != null && uploadEvent != null) {
			if ("application/pdf".equalsIgnoreCase(uploadEvent.getMedia().getContentType())) {
			  // Se guarda el documento en DEO
				ResponseExternalGenerarDocumento response = DocumentosOperHelper
						.uploadDocumentoOperGedo(documentoOperacion, uploadEvent.getMedia(), isObligatorios());
				
				// Se guarda la relacion en TE
				if (response != null) {
					documentoOperacion.setNumeroDocumento(response.getNumero());
					documentoOperacion.setNombreArchivo(getNombreArchivoUploaded(uploadEvent));
					
					try {
						operacionDocumentoService.saveDocumentoOperacion(documentoOperacion);
						BindUtils.postNotifyChange(null, null, this, "documentos");
					} catch (ServiceException e) {
						logger.debug("Error en DocumentosOperGridVM.onUploadDocumento(): ", e);
						Messagebox.show(Labels.getLabel(KEY_ERROR_UPLOAD_MSG), Labels.getLabel(KEY_ERROR_UPLOAD_TITLE), Messagebox.OK, Messagebox.ERROR);
					}
				}
			}
			else {
				Messagebox.show(Labels.getLabel(KEY_ERROR_NO_PDF_MSG), Labels.getLabel(KEY_ERROR_NO_PDF_TITLE), Messagebox.OK, Messagebox.EXCLAMATION);
			}
		}
	}
	
	/**
	 * Obtiene el nombre del archivo subido
	 * 
	 * @param uploadEvent
	 * @return
	 */
	private String getNombreArchivoUploaded(UploadEvent uploadEvent) {
		String nombreArchivo = "";
		
		if (uploadEvent.getMedia() != null) {
			nombreArchivo = uploadEvent.getMedia().getName();
			
			if (nombreArchivo.length() > 100) {
				nombreArchivo = nombreArchivo.substring(0, 96).concat(".pdf");
			}
		}
		
		return nombreArchivo;
	}
	
	/**
	 * Comando que descarga un documento dado
	 * 
	 * @param documentoOperacion
	 */
	@Command
	public void onDescargarDocumento(@BindingParam("documentoOperacion") OperacionDocumentoDTO documentoOperacion) {
	  if (documentoOperacion != null) {
	    String loggedUsername = (String) Executions.getCurrent().getDesktop().getSession()
	        .getAttribute(ConstantesWeb.SESSION_USERNAME);
	    
	    RequestExternalConsultaDocumento request = new RequestExternalConsultaDocumento();
	    request.setNumeroDocumento(documentoOperacion.getNumeroDocumento());
	    request.setUsuarioConsulta(loggedUsername);
	    
	    byte[] content = null;
	    
	    try {
	      content = consultaDocumentoService.consultarDocumentoPdf(request);
	    }
	    catch (Exception e) {
	      logger.debug("Error en DocumentosOperGridVM.onDescargarDocumento(): ", e);
        Messagebox.show(Labels.getLabel(KEY_ERROR_DOWNLOAD_MSG), Labels.getLabel(KEY_ERROR_DOWNLOAD_TITLE), Messagebox.OK, Messagebox.ERROR);
	    }
	    
	    if (content != null) {
	      File fichero = new File(documentoOperacion.getNombreArchivo());
	      String tipoFichero = new MimetypesFileTypeMap().getContentType(fichero);
	      String nombreArchivo = documentoOperacion.getNombreArchivo();
	      
	      Filedownload.save(content, tipoFichero, nombreArchivo);
	    }
	  }
	}
	
	/**
	 * Comando que elimina un documento dado, previa
	 * confirmacion de una popup Si/No
	 * 
	 * @param documentoOperacion
	 */
	@Command
	public void onEliminarDocumento(@BindingParam("documentoOperacion") OperacionDocumentoDTO documentoOperacion) {
	  if (documentoOperacion != null) {
	    Messagebox.show(Labels.getLabel(KEY_CONFIRM_DELETE_MSG), Labels.getLabel(KEY_CONFIRM_DELETE_TITLE), Messagebox.YES | Messagebox.NO,
	        Messagebox.QUESTION, new EventListener<Event>() {

	          @Override
	          public void onEvent(Event event) throws Exception {
	            if (event.getName().equals(Messagebox.ON_YES)) {
	              // Elimina el documento en BDD
	              operacionDocumentoService.deleteDocumentoOperacion(documentoOperacion);
	              
	              // Reestablece los datos del DTO
	              documentoOperacion.setDocumentoGedo(null);
	              documentoOperacion.setNombreArchivo(null);
	              documentoOperacion.setNumeroDocumento(null);
	              
	              // Refresh pantalla
	              BindUtils.postGlobalCommand(null, null, "refreshDocumentos", null);
	              
	              // TODO hvogelva: ¿como elimino el archivo fisico / registro en DEO?
	            }
	          }
	        });
	  }
	}
	
  /**
   * Comando global que refresca los documentos
   */
  @GlobalCommand
  @NotifyChange("documentos")
  public void refreshDocumentos() {
    //
  }
	
	// GETTERS - SETTERS
	
	public List<OperacionDocumentoDTO> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<OperacionDocumentoDTO> documentos) {
		this.documentos = documentos;
	}

	public boolean isObligatorios() {
		return obligatorios;
	}

	public void setObligatorios(boolean obligatorios) {
		this.obligatorios = obligatorios;
	}
	
	public boolean isSoloLectura() {
    return soloLectura;
  }

  public void setSoloLectura(boolean soloLectura) {
    this.soloLectura = soloLectura;
  }

  public boolean isHayDocumentos() {
    return hayDocumentos;
  }

  public void setHayDocumentos(boolean hayDocumentos) {
    this.hayDocumentos = hayDocumentos;
  }
	
}