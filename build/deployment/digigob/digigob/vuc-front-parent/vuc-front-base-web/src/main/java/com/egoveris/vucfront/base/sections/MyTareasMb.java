package com.egoveris.vucfront.base.sections;

import com.egoveris.vucfront.base.exception.NegocioException;
import com.egoveris.vucfront.base.mbeans.LoginMb;
import com.egoveris.vucfront.base.util.AbstractMb;
import com.egoveris.vucfront.base.util.MessageType;
import com.egoveris.vucfront.model.model.DocumentoDTO;
import com.egoveris.vucfront.model.model.DocumentoEstadoDTO;
import com.egoveris.vucfront.model.model.EstadoTareaDTO;
import com.egoveris.vucfront.model.model.ExpedienteBaseDTO;
import com.egoveris.vucfront.model.model.TareaDTO;
import com.egoveris.vucfront.model.model.TipoDocumentoDTO;
import com.egoveris.vucfront.model.model.TipoTramiteTipoDocDTO;
import com.egoveris.vucfront.model.service.DocumentoService;
import com.egoveris.vucfront.model.service.TareaService;
import com.egoveris.vucfront.model.util.DocumentoEstadoEnum;
import com.egoveris.vucfront.model.util.EstadoTareaEnum;
import com.egoveris.vucfront.model.util.TipadoDocumentoEnum;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Predicate;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasoluna.plus.core.i18n.DefaultLocaleMessageSource;

@ManagedBean
@ViewScoped
public class MyTareasMb extends AbstractMb {

  private static final long serialVersionUID = -6391507225989251338L;
  private Logger logger = LoggerFactory.getLogger(this.getClass());

  private static final String DIV_CARGADOCUMENTOS_ERROR = "myTareas:cargaDocumentosSubsanacion:messages";
private static final Object Boolean = null;

  @ManagedProperty("#{documentoServiceImpl}")
  private DocumentoService documentoService;
  @ManagedProperty("#{loginMb}")
  private LoginMb login;
  @ManagedProperty("#{tareaServiceImpl}")
  private TareaService tareaService;
  @ManagedProperty("#{msg}")
  private DefaultLocaleMessageSource bundle;

  private List<TareaDTO> tareasList;
  private Map<ExpedienteBaseDTO, List<TareaDTO>> mapaExpedienteListaTareas;
  private Entry<ExpedienteBaseDTO, List<TareaDTO>> selectedExpedienteMap;

  private UploadedFile uploadedFile;
  private ExpedienteBaseDTO expedienteUploadedFile;
  private TipoDocumentoDTO tipoDocumentoUploadedFile;
  private List<DocumentoDTO> uploadedDocumentosSubsanados;
  private boolean finalizarTareaEnabled;
  private boolean viewFfdd;
  private boolean subsanarView= true;
  private Map<Long, Boolean> uploadedTiposDocumento;

  public void init() {
    if (login.getPersona() == null) {
      throw new NegocioException("Not logged.");
    } else {
      if (tareasList == null) {
        tareasList = tareaService.getTareasPendientesByPersona(login.getPersona());
      }
      if (uploadedDocumentosSubsanados == null) {
        uploadedDocumentosSubsanados = new ArrayList<>();
      }
     
      fillExpedienteTareasMap(tareasList);
    
    }
  }

  /**
   * Map Expedientes with a list of Tareas ordered by date.
   */
  private void fillExpedienteTareasMap(List<TareaDTO> tareasList) {
    if (mapaExpedienteListaTareas == null) {
      mapaExpedienteListaTareas = new LinkedHashMap<>();
    } else {
      mapaExpedienteListaTareas.clear();
    }

    if (tareasList != null && !tareasList.isEmpty()) {
      for (TareaDTO aux : tareasList) {
        if (mapaExpedienteListaTareas.get(aux.getExpedienteBase()) == null) {
          mapaExpedienteListaTareas.put(aux.getExpedienteBase(), new ArrayList<TareaDTO>());
        }
        mapaExpedienteListaTareas.get(aux.getExpedienteBase()).add(aux);
      }
    }
  }

  /**
   * Set the current Expediente and it's Tareas for the dialog display.
   * 
   * @param expedienteEntry
   */
  public void cmdViewTarea(Entry<ExpedienteBaseDTO, List<TareaDTO>> expedienteEntry) {
    selectedExpedienteMap = expedienteEntry;
    // Set's the Tareas as viewed
    for (TareaDTO aux : expedienteEntry.getValue()) {
      tareaService.setTareaAsViewed(aux);
    }
  }

  public void cmdFinalizarTarea() {
    subsanarDocumentos();
    finalizarTareasVuc();
    showDialogMessage(bundle.getMessage("myTareasFinalizada", null), MessageType.INFO);
  }

  private void subsanarDocumentos() {
    if (!uploadedDocumentosSubsanados.isEmpty()) {
    	tareaService.confirmarTareaSubsanacion(expedienteUploadedFile, uploadedDocumentosSubsanados);
      }     
  }

  /**
   * Cambia el estado de las Tareas a FINALIZADO.
   */
  private void finalizarTareasVuc() {
    // Cambia el estado de las tareas a FINALIZADO
    for (TareaDTO aux : selectedExpedienteMap.getValue()) {
      aux.setEstado(new EstadoTareaDTO(EstadoTareaEnum.FINALIZADO));
      tareaService.saveTarea(aux);
    }
    // Refresca la lista de tareas con el nuevo estado
    tareasList = tareaService.getTareasPendientesByPersona(login.getPersona());
    fillExpedienteTareasMap(tareasList);
  }

  /**
   * Checks if an Expediente has any unseen Tarea.
   * 
   * @param expedienteEntry
   * @return
   */
  public boolean hasExpedienteAnyUnseenTarea(
      Entry<ExpedienteBaseDTO, List<TareaDTO>> expedienteEntry) {
    boolean unseenTarea = false;
    for (TareaDTO aux : expedienteEntry.getValue()) {
      if (!aux.getNotificado()) {
        unseenTarea = true;
        break;
      }
    }
    return unseenTarea;
  }

  public void handleFileUpload(FileUploadEvent event) {
    if (event.getFile() != null) {
      // Valida que el archivo no se ha subido previamente.
      if (getUploadedDocumentoByFilename(event.getFile().getFileName()) != null) {
        addMessageById(DIV_CARGADOCUMENTOS_ERROR,
            "El archivo " + event.getFile().getFileName() + " ya se ha subido previamente.",
            MessageType.ERROR);
        uploadedFile = null;
        // Valida que el archivo no se encuentre ya firmado.
      } else if (isFirmado(event.getFile().getContents(), event.getFile().getFileName())) {
        addMessageById(DIV_CARGADOCUMENTOS_ERROR,
            "El archivo " + event.getFile().getFileName() + " ya se encuentra firmado.",
            MessageType.ERROR);
        uploadedFile = null;
      } else {
        uploadedFile = event.getFile();
        addMessageById(DIV_CARGADOCUMENTOS_ERROR,
            "Archivo " + uploadedFile.getFileName() + " cargado.", MessageType.INFO);
      }
    }
  }

  private DocumentoDTO getUploadedDocumentoByFilename(String filename) {
    DocumentoDTO retorno = null;

    if (uploadedDocumentosSubsanados != null && !uploadedDocumentosSubsanados.isEmpty()) {
      Predicate<DocumentoDTO> predicate = c -> c.getNombreOriginal().equals(filename);
      Optional<DocumentoDTO> result = uploadedDocumentosSubsanados.stream().filter(predicate)
          .findFirst();
      if (result.isPresent()) {
        retorno = result.get();
      }
    }

    return retorno;
  }

  private DocumentoDTO getUploadedDocumentoByTipoDocumento(TipoDocumentoDTO tipoDocumento) {
    DocumentoDTO retorno = null;

    if (uploadedDocumentosSubsanados != null && !uploadedDocumentosSubsanados.isEmpty()) {
      Predicate<DocumentoDTO> predicate = c -> c.getTipoDocumento().getId()
          .equals(tipoDocumento.getId());
      Optional<DocumentoDTO> result = uploadedDocumentosSubsanados.stream().filter(predicate)
          .findFirst();
      if (result.isPresent()) {
        retorno = result.get();
      }
    }

    return retorno;
  }

  public void upload() {
    // Valida que se haya subido un archivo.
    if (uploadedFile != null) {
      // Si ya se subió un documento del mismo TipoDocumento, lo borra
      DocumentoDTO searchedDocumento = getUploadedDocumentoByTipoDocumento(
          tipoDocumentoUploadedFile);
      if (searchedDocumento != null) {
        uploadedDocumentosSubsanados.remove(searchedDocumento);
      }
      // Genera el documento que se agregará al expediente.
      DocumentoDTO nuevoDocumento = new DocumentoDTO();
      nuevoDocumento.setTipoDocumento(tipoDocumentoUploadedFile);
      nuevoDocumento.setNombreOriginal(uploadedFile.getFileName());
      nuevoDocumento.setArchivo(uploadedFile.getContents());
      getDocumentoEstado(nuevoDocumento);
      uploadedDocumentosSubsanados.add(nuevoDocumento);
      uploadedTiposDocumento.put(tipoDocumentoUploadedFile.getId(), true);
      uploadedFile = null;
      execute("PF('cargaDocumentosSubsanacion').hide()");
      changeFinalizarTareaBtn ();
      
     
    } else {
      addMessageById(DIV_CARGADOCUMENTOS_ERROR, "Debe ingresar un archivo valido.",
          MessageType.ERROR);
      update("mainLayout:cargaDocumentosSubsanacionForm:messagesCdS");
    }
  }

  public void setUploadFileData(ExpedienteBaseDTO expediente, TipoDocumentoDTO tipoDocumento) {
    setExpedienteUploadedFile(expediente);
    setTipoDocumentoUploadedFile(tipoDocumento);
    fillUploadedDocuments();
  }

  /**
   * Checks if a Document is signed.
   * 
   * @return
   */
  private Boolean isFirmado(byte[] bytes, String name) {
    return false;
  }

  public boolean isFinalizarTareaEnabled() {
    return finalizarTareaEnabled;
  }

  public Map<ExpedienteBaseDTO, List<TareaDTO>> getMapaExpedienteListaTareas() {
    return mapaExpedienteListaTareas;
  }

  public Entry<ExpedienteBaseDTO, List<TareaDTO>> getSelectedExpedienteMap() {
    return selectedExpedienteMap;
  }

  public void setDocumentoService(DocumentoService documentoService) {
    this.documentoService = documentoService;
  }

  public void setLogin(LoginMb login) {
    this.login = login;
  }

  public void setTareaService(TareaService tareaService) {
    this.tareaService = tareaService;
  }
  

  public boolean isTipoDocumentoFfdd(TipoDocumentoDTO tipoDocumento, ExpedienteBaseDTO expediente) {
	  	if (tipoDocumento.getTipadoDcto().getId().equals(TipadoDocumentoEnum.TEMPLATE.getId())) {
	  		setViewFfdd(false);
	  		setTipoDocumentoUploadedFile(tipoDocumento);
	  		setExpedienteUploadedFile(expediente);
	  		return true;
	  	}
		return false;
  }
  /**
	 * Close event for FFDD Dialog.
	 */
	public void closeEventFfddDialog() {
		uploadedDocumentosSubsanados.add(getFFCCSubsanado(getExpedienteUploadedFile(), getTipoDocumentoUploadedFile()));
		changeFinalizarTareaBtn ();
	}

	public boolean isViewFfdd() {
		return viewFfdd;
	}
	
	public void setViewFfdd(boolean viewFfdd) {
		this.viewFfdd = viewFfdd;
	}
	
	public TipoDocumentoDTO getTipoDocumentoUploadedFile() {
		return tipoDocumentoUploadedFile;
	}


	public void setTipoDocumentoUploadedFile(TipoDocumentoDTO tipoDocumentoUploadedFile) {
		this.tipoDocumentoUploadedFile = tipoDocumentoUploadedFile;
	}
	
	public ExpedienteBaseDTO getExpedienteUploadedFile() {
		return expedienteUploadedFile;
	}
	
	public void setExpedienteUploadedFile(ExpedienteBaseDTO expedienteUploadedFile) {
		this.expedienteUploadedFile = expedienteUploadedFile;
	}

	public boolean isSubsanarView() {
		return subsanarView;
	}

	public void setSubsanarView(boolean subsanarView) {
		this.subsanarView = subsanarView;
	}

	public void setBundle(DefaultLocaleMessageSource bundle) {
		this.bundle = bundle;
	}
	
	private DocumentoDTO getFFCCSubsanado(ExpedienteBaseDTO expedienteUploadedFile, TipoDocumentoDTO tipoDocumento ) {
		for (DocumentoDTO documento : expedienteUploadedFile.getDocumentosList()) {
			if (documento.getTipoDocumento().getId()== tipoDocumento.getId() 
					&&documento.getTipoDocumento().getTipadoDcto().getId().equals(TipadoDocumentoEnum.TEMPLATE.getId())) {
				uploadedTiposDocumento.put(tipoDocumento.getId(), true);
				documento.setUrlTemporal(null);
				return documento;
			}
		}
		
		return null;
				
	}
	
	private void changeFinalizarTareaBtn () {
		int count = 0;
		for (Boolean bool : uploadedTiposDocumento.values()) {
			if (bool.booleanValue()) {
				count++;
			}
		}
		
		if (selectedExpedienteMap.getValue().get(0).getTipoDocumentos().size() == count) {
			finalizarTareaEnabled = true;
		} else {
			finalizarTareaEnabled = false;
		}
		update("mainLayout:tareasDialogForm:cmdFinalizarTarea");
	}
	private void getDocumentoEstado (DocumentoDTO documento) {
		if (documento.getDocumentoEstados()== null) {
			documento.setDocumentoEstados(new ArrayList<>());
		}
		DocumentoEstadoDTO estado = new DocumentoEstadoDTO();
		estado.setDocumento(documento);
		estado.setFecha(new Date());
		estado.setEstado(DocumentoEstadoEnum.SUBSANADO.getEstado());
		documento.getDocumentoEstados().add(estado);
		
	}
	
	/**
	 * Fill HashMap with TipoDocumento ID and Boolean if a Document has been
	 * uploaded.
	 */
	private void fillUploadedDocuments() {
		if (uploadedTiposDocumento == null) {
			this.uploadedTiposDocumento = new HashMap<>();
			for (TipoTramiteTipoDocDTO aux : this.getExpedienteUploadedFile().getTipoTramite().getTipoTramiteTipoDoc()) {
				if (getUploadedDocumentoByTipodocumento(aux.getTipoDoc().getId()) != null) {
					this.uploadedTiposDocumento.put(aux.getTipoDoc().getId(), false);
				}
			}
		}
	}
	
	public DocumentoDTO getUploadedDocumentoByTipodocumento(Long idTipoDocumento) {
			DocumentoDTO retorno = null;
			if (!getExpedienteUploadedFile().getDocumentosList().isEmpty()) {
				Predicate<DocumentoDTO> predicate = c -> c.getTipoDocumento().getId().equals(idTipoDocumento);
				Optional<DocumentoDTO> result = getExpedienteUploadedFile().getDocumentosList().stream().filter(predicate).findFirst();
				if (result.isPresent()) {
					retorno = result.get();
				}
			}

			return retorno;
		}

}