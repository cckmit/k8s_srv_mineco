package com.egoveris.vucfront.base.mbeans.steps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasoluna.plus.core.i18n.DefaultLocaleMessageSource;
import org.zkoss.zk.ui.Component;

import com.egoveris.deo.model.model.ResponseTipoDocumento;
import com.egoveris.deo.ws.service.IExternalTipoDocumentoService;
import com.egoveris.ffdd.render.service.IFormManager;
import com.egoveris.ffdd.render.service.IFormManagerFactory;
import com.egoveris.vucfront.base.mbeans.LoginMb;
import com.egoveris.vucfront.base.service.ApplicationService;
import com.egoveris.vucfront.base.service.WebDavService;
import com.egoveris.vucfront.base.util.ConstantsUrl;
import com.egoveris.vucfront.base.util.Encryption;
import com.egoveris.vucfront.base.util.MessageType;
import com.egoveris.vucfront.model.model.DocumentoDTO;
import com.egoveris.vucfront.model.model.ExpedienteFamiliaSolicitudDTO;
import com.egoveris.vucfront.model.model.NotificacionDTO;
import com.egoveris.vucfront.model.model.TipoDocumentoDTO;
import com.egoveris.vucfront.model.model.TipoTramiteTipoDocDTO;
import com.egoveris.vucfront.model.service.DocumentoService;
import com.egoveris.vucfront.model.service.NotificacionService;
import com.egoveris.vucfront.model.util.BusinessFormatHelper;
import com.egoveris.vucfront.model.util.TipadoDocumentoEnum;

@ManagedBean
@ViewScoped
public class Step2Mb extends StepParentMb {
	private static final long serialVersionUID = -2983827506105005129L;
	private static final Logger LOG = LoggerFactory.getLogger(Step2Mb.class);

	private static final String DIV_CARGADOCUMENTOS_ERROR = "mainLayout:step2Form:cargaDocumentos:messages";
	private static final String DIV_TABLADOCUMENTOS = "mainLayout:step2Form:tblDocumentos";
	
	private static final Long STEP_2 = 2l;

	@ManagedProperty("#{tipoDocumentoService}")
	private IExternalTipoDocumentoService externalTipoDocumentoService;
	@ManagedProperty("#{zkFormManagerFactory}")
	private IFormManagerFactory<IFormManager<Component>> formManagerFactory;
	private IFormManager<Component> manager;
	@ManagedProperty("#{applicationServiceImpl}")
	private ApplicationService applicationService;
	@ManagedProperty("#{documentoServiceImpl}")
	private DocumentoService documentoService;
	@ManagedProperty("#{loginMb}")
	private LoginMb login;
	@ManagedProperty("#{notificacionServiceImpl}")
	private NotificacionService notificacionService;
	@ManagedProperty("#{webDavServiceImpl}")
	private WebDavService webDavService;
	@ManagedProperty("#{msg}")
	private DefaultLocaleMessageSource bundle;

	private Map<Long, Boolean> uploadedTiposDocumento;
	private List<DocumentoDTO> documentsToBeDeleted;

	private TipoDocumentoDTO tipoDocumentoSeleccionado;
	private UploadedFile uploadedFile;
	private String pdfViewUrl;
	private boolean viewFfdd;

	private Map<String, List<DocumentoDTO>> mapMyDocuments;
	private Integer currentDocumentosCount;

	private int documentListHash;
	
	public void init() {
		if (login.getPersona() == null) {
			redirect(ConstantsUrl.LOGIN);
		} else {
			setPersona(login.getPersona());
		}

		if (getExpediente() == null) {
			setExpediente(getExpedienteService().getExpedienteFamiliaSolicitudById(getIdExpediente()));
			if (getExpediente() != null) {
				sortTipsoDocumentoFromTipoTramite(getExpediente());
				documentsToBeDeleted = new ArrayList<>();

				fillUploadedDocuments();

//				currentDocumentosCount = getExpediente().getDocumentosList().size();
				documentListHash = getExpediente().getDocumentosList().hashCode();

			} else {
				LOG.info("EXPEDIENTE NO EXISTE");
			}
		}
	}

	/**
	 * Fill HashMap with TipoDocumento ID and Boolean if a Document has been
	 * uploaded.
	 */
	private void fillUploadedDocuments() {
		if (uploadedTiposDocumento == null) {
			this.uploadedTiposDocumento = new HashMap<>();
		}

		for (TipoTramiteTipoDocDTO aux : this.getExpediente().getTipoTramite().getTipoTramiteTipoDoc()) {
			if (getUploadedDocumentoByTipodocumento(aux.getTipoDoc().getId()) != null) {
				this.uploadedTiposDocumento.put(aux.getTipoDoc().getId(), true);
			} else {
				this.uploadedTiposDocumento.put(aux.getTipoDoc().getId(), false);
			}
		}
	}

	/**
	 * Sorts the TipoDocumentos from the TipoTramite associated to the Expediente by
	 * Required first and Optional second.
	 * 
	 * @param expediente
	 */
	private void sortTipsoDocumentoFromTipoTramite(ExpedienteFamiliaSolicitudDTO expediente) {
		Collections.sort(expediente.getTipoTramite().getTipoTramiteTipoDoc(), new Comparator<TipoTramiteTipoDocDTO>() {
			@Override
			public int compare(TipoTramiteTipoDocDTO abc1, TipoTramiteTipoDocDTO abc2) {
				return Boolean.compare(abc2.getObligatorio(), abc1.getObligatorio());
			}
		});
	}

	/**
	 * Checks if a Document is signed.
	 * 
	 * @return
	 */
	private Boolean isFirmado(byte[] bytes, String name) {
		return false;
	}

	/**
	 * setUploadedFile (only unsigned Document).
	 * 
	 * @param event
	 */
	public void handleFileUpload(FileUploadEvent event) {
		if (event.getFile() != null) {
			// Valida que el archivo no se ha subido previamente.
			if (getUploadedDocumentoByFilename(event.getFile().getFileName()) != null) {
				addMessageById(DIV_CARGADOCUMENTOS_ERROR,
						"El archivo " + event.getFile().getFileName() + " ya se ha subido previamente.",
						MessageType.ERROR);
				setUploadedFile(null);
				// Valida que el archivo no se encuentre ya firmado.
			} else if (isFirmado(event.getFile().getContents(), event.getFile().getFileName())) {
				addMessageById(DIV_CARGADOCUMENTOS_ERROR,
						"El archivo " + event.getFile().getFileName() + " ya se encuentra firmado.", MessageType.ERROR);
				setUploadedFile(null);
			} else {
				setUploadedFile(event.getFile());
				addMessageById(DIV_CARGADOCUMENTOS_ERROR, "Archivo " + getUploadedFile().getFileName() + " cargado.",
						MessageType.INFO);
			}
		}
	}

	/**
	 * Add the uploaded Documento to the Expediente.
	 */
	public void upload() {
		// Valida que se haya subido un archivo.
		if (getUploadedFile() != null) {
			// Genera el documento que se agregará al expediente.
			DocumentoDTO nuevoDocumento = new DocumentoDTO();
			nuevoDocumento.setTipoDocumento(getTipoDocumentoSeleccionado());
			nuevoDocumento.setNombreOriginal(uploadedFile.getFileName());
			nuevoDocumento.setArchivo(uploadedFile.getContents());

			addDocumentoToExpediente(nuevoDocumento);

			setUploadedFile(null);
			update(DIV_TABLADOCUMENTOS);
			execute("PF('cargaDocumentos').hide()");
		} else {
			addMessageById(DIV_CARGADOCUMENTOS_ERROR, "Debe ingresar un archivo válido.", MessageType.ERROR);
			update("step2Form:messages");
		}
	}

	/**
	 * Adds the Documento to the Expediente. If a previous Documents with the same
	 * Tipo Documento exists in the Expediente, it replaces it.
	 * 
	 * @param documento
	 */
	private void addDocumentoToExpediente(DocumentoDTO documento) {
		// Si es un TipoDocumento definido por el trámite y ya se ha sido subido uno
		// anteriormente,
		if (uploadedTiposDocumento.get(documento.getTipoDocumento().getId())) {
			DocumentoDTO uploadedDoc = getUploadedDocumentoByTipodocumento(documento.getTipoDocumento().getId());
			// se borra.
			deleteDocument(uploadedDoc);
		}
		// Se agrega al expediente.
		getExpediente().getDocumentosList().add(documento);
		// TipoDocumento requerido por el trámite lo marca como subido.
		uploadedTiposDocumento.put(documento.getTipoDocumento().getId(), true);
	}

	/**
	 * Loads a DEO Document to the Expediente.
	 * 
	 * @param documento
	 */
	public void loadDeoDocument(DocumentoDTO documento) {
		addDocumentoToExpediente(documento);
		update(DIV_TABLADOCUMENTOS);
		execute("PF('misDocumentos').hide()");
	}

	/**
	 * Get's the uploaded Documento in the Expediente by it's file name.
	 * 
	 * @param filename
	 * @return
	 */
	public DocumentoDTO getUploadedDocumentoByFilename(String filename) {
		DocumentoDTO retorno = null;

		if (!getExpediente().getDocumentosList().isEmpty()) {
			Predicate<DocumentoDTO> predicate = c -> c.getNombreOriginal().equals(filename);
			Optional<DocumentoDTO> result = getExpediente().getDocumentosList().stream().filter(predicate).findFirst();
			if (result.isPresent()) {
				retorno = result.get();
			}
		}

		return retorno;
	}

	/**
	 * Get's the uploaded Documento in the Expediente by it's TipoDocumento.
	 * 
	 * @param idTipoDocumento
	 * @return
	 */
	public DocumentoDTO getUploadedDocumentoByTipodocumento(Long idTipoDocumento) {
		DocumentoDTO retorno = null;

		if (!getExpediente().getDocumentosList().isEmpty()) {
			Predicate<DocumentoDTO> predicate = c -> c.getTipoDocumento().getId().equals(idTipoDocumento);
			Optional<DocumentoDTO> result = getExpediente().getDocumentosList().stream().filter(predicate).findFirst();
			if (result.isPresent()) {
				retorno = result.get();
			}
		}

		return retorno;
	}

	/**
	 * View the selected Documento.
	 * 
	 * @param doc
	 */
	public void viewDocument(DocumentoDTO document) {
		if (document != null) {
			// Si es un FFDD
			if (document.getIdTransaccion() != null) {
				setTipoDocumentoSeleccionado(document.getTipoDocumento());
				viewFfdd = true;
			}
			// Si es un documento regular
			else {
				if (document.getArchivo() != null) {
					pdfViewUrl = "/viewArchivo?nombreArchivo=" + document.getNombreOriginal();
					FacesContext facesContext = FacesContext.getCurrentInstance();
					HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
					session.setAttribute("archivo", document.getArchivo());
				} else if (document.getId() != null) {
					Encryption encrypt = Encryption.getInstance();
					pdfViewUrl = "/viewArchivo?idDocumento=" + encrypt.encryptString(document.getId().toString());
				} else {
					pdfViewUrl = "/viewArchivo?codDeo=" + document.getNumeroDocumento();
				}
			}
		}
	}

	public void preViewDocument(DocumentoDTO document, String usuario) {
	    document.setArchivo(documentoService.getDocumentoGedoArrayBytes(document.getNumeroDocumento(), usuario));
	    viewDocument(document);
	}
	/**
	 * Removes an uploaded Documento from the Expediente.
	 * 
	 * @param idDocumento
	 */
	public void deleteDocument(DocumentoDTO documento) {
		// Borra el documento del expediente
		int posicionObjetoEnArreglo = getExpediente().getDocumentosList().indexOf(documento);
		getExpediente().getDocumentosList().remove(posicionObjetoEnArreglo);
		// Setea el mapa de documento subidos en falso para el id correspondiente
		uploadedTiposDocumento.put(documento.getTipoDocumento().getId(), false);
		// Agrega el documento para ser borrado si éste se encuentra almacenado en
		// BBDD. Si el documento se encuentra en DEO, no lo borra, ya que puede ser
		// reutilizado en otro trámite. Si es obligatorio no lo agrego a la lista
		// porque será eliminado al abrir nuevamente el formulario para cargarlo.
		boolean esObligatorio = this.getExpediente()
				.getTipoTramite()
				.getTipoTramiteTipoDoc()
				.stream().filter(
						tttd -> tttd.getTipoDoc().getAcronimoGedo().contentEquals(documento.getTipoDocumento().getAcronimoGedo())
				).findFirst().orElse(null).getObligatorio();
		
		if (documento.getId() != null && documento.getCodigoSade() == null && !(esObligatorio && documento.getIdTransaccion() != null)) {
			documentsToBeDeleted.add(documento);
		}
		

		update(DIV_TABLADOCUMENTOS);
	}

	/**
	 * Get all new uploaded Documents from the Expediente.
	 * 
	 * @return
	 */
	private List<DocumentoDTO> getRecentlyUploadedDocuments() {
		List<DocumentoDTO> retorno = new ArrayList<>();
		for (DocumentoDTO aux : getExpediente().getDocumentosList()) {
			if (aux.getId() == null) {
				retorno.add(aux);
			}
		}
		return retorno;
	}

	private void clearDeletedDocumentsList() {
		if (!documentsToBeDeleted.isEmpty()) {
			documentsToBeDeleted.clear();
		}
	}

	/**
	 * To the previous step.
	 */
	public void cmdBack() {
		redirect(ConstantsUrl.STEP1WITHEXP.concat(getExpediente().getId().toString()));
	}

	public void cmdSave() {
		/*
		 * Upload to WebDav the recently uploaded Documents.
		 */
		// INI - + de 1 formulario
		// Eliminar duplicados desde documentList del objeto Expediente
		List<DocumentoDTO> sinDuplicado = new ArrayList();
		List<DocumentoDTO> duplicado = new ArrayList();
		// duplicado = getExpediente().getDocumentosList();
		for (DocumentoDTO aux : getExpediente().getDocumentosList()) {
			if (sinDuplicado.isEmpty()) {
				sinDuplicado.add(aux);
			} else {
				boolean valida = false;
				for (DocumentoDTO aux1 : sinDuplicado) {
					if (aux.getId() != null && aux1.getId() != null && aux.getId().equals(aux1.getId())) {
						valida = true;
						break;
					}
				}
				if (!valida) {
					sinDuplicado.add(aux);
				}
			}
		}
		getExpediente().setDocumentosList(sinDuplicado);
		// Guardo step
		if (getExpediente().getStep() == null || getExpediente().getStep() < STEP_2) {
			getExpediente().setStep(STEP_2);
		}

		// FIN - + de 1 formulario
		List<DocumentoDTO> recentlyUploadedDocuments = getRecentlyUploadedDocuments();
		if (!recentlyUploadedDocuments.isEmpty()) {
			Map<String, String> mapNombrearchivoPathwebdav = webDavService.uploadDocuments(recentlyUploadedDocuments);

			// Complete the fields of the DTO
			for (DocumentoDTO aux : recentlyUploadedDocuments) {
				aux.setBajaLogica(false);
				aux.setFechaCreacion(new Date());
				aux.setPersona(getPersona());
				aux.setUrlTemporal(mapNombrearchivoPathwebdav.get(aux.getNombreOriginal()));
				aux.setUsuarioCreacion(getPersona().getNombreApellido());
				aux.setVersion(0l);
			}
		}

		if (!documentsToBeDeleted.isEmpty() || !recentlyUploadedDocuments.isEmpty()
				|| hasDocumentListFromExpedienteChanged()) {
			this.cleanFfddUrltemporal();
			setExpediente(getExpedienteBaseService().saveExpedienteFamiliaSolicitud(getExpediente()));
			this.deleteDocumentsFromExpediente();
		}
		showDialogMessage(bundle.getMessage("step1SolicitudGuardada", null), MessageType.INFO);
	}

	private boolean hasDocumentListFromExpedienteChanged() {
//		return (getExpediente().getDocumentosList().size() != currentDocumentosCount);
		return getExpediente().getDocumentosList().hashCode() != documentListHash;
	}

	private void deleteDocumentsFromExpediente() {
		// Si se borraron documentos del expediente, también los borra de WebDav y
		// de FFDD.
		if (documentsToBeDeleted != null && !documentsToBeDeleted.isEmpty()) {
			for (DocumentoDTO aux : documentsToBeDeleted) {
				if (aux.getUrlTemporal() != null) {
					webDavService.deleteDocument(aux);
				}
				if (aux.getIdTransaccion() != null) {
					ResponseTipoDocumento tipoDocumentoDeo = externalTipoDocumentoService
							.consultarTipoDocumentoPorAcronimo(aux.getTipoDocumento().getAcronimoGedo());
					String ffddName = tipoDocumentoDeo.getIdFormulario();
					manager = formManagerFactory.create(ffddName);
					manager.deleteFormWeb(aux.getIdTransaccion().intValue());
				}
				documentoService.deleteDocument(aux);
			}
		}
		this.clearDeletedDocumentsList();
	}

	/**
	 * Set to null the field urlTemporal for temporal FFDD Documents.
	 */
	private void cleanFfddUrltemporal() {
		for (DocumentoDTO aux : getExpediente().getDocumentosList()) {
			if (aux.getTipoDocumento().getTipadoDcto().getId().equals(TipadoDocumentoEnum.TEMPLATE.getId())
					&& aux.getUrlTemporal() != null) {
				aux.setUrlTemporal(null);
			}
		}
	}

	/**
	 * To the next step.
	 */
	public void cmdNext() {
		if (validateRequriedDocuments()) {
			if (isBorrador()) {
				cmdSave();
			}
			redirect(ConstantsUrl.STEP3.concat(getExpediente().getId().toString()));
		} else {
			LOG.info("### PASO 2: NO SE HAN SUBIDO LOS DOCUMENTOS REQUERIDOS POR EL TRÁMITE ###");
			showDialogMessage(bundle.getMessage("step2DocRequeridos", null), MessageType.ERROR);
		}
	}

	/**
	 * Validates that all the required documents are uploaded.
	 * 
	 * @return
	 */
	private Boolean validateRequriedDocuments() {
		boolean retorno = true;

		for (TipoTramiteTipoDocDTO aux : this.getExpediente().getTipoTramite().getTipoTramiteTipoDoc()) {
			if (aux.getObligatorio() && !uploadedTiposDocumento.get(aux.getTipoDoc().getId())) {
				retorno = false;
				break;
			}
		}

		return retorno;
	}

	/**
	 * Prepara el mapa que será el input para el dialogo 'misDocumentos'. Este,
	 * contiene todos los documentos cargados previamente por el usuario que pueden
	 * ser reutilizados en el trámite en curso. Los documentos se muestran separados
	 * por su tipo (el seleccionado + notificados). La clave del mapa es la
	 * categoría (tipo) y el valor la lista de documentos asociados.
	 * 
	 * @param tipoDocumentoSeleccionado
	 */
	public void showMyDocuments(TipoDocumentoDTO tipoDocumentoSeleccionado) {
		// Instancia/limpia mapa
		if (mapMyDocuments == null) {
			mapMyDocuments = new HashMap<>();
		} else {
			mapMyDocuments.clear();
		}

		try {
			// Completa DEO
			addDeoDocuments(mapMyDocuments, tipoDocumentoSeleccionado);

			// Completa Notificaciones
//			addNotificationDocuments(mapMyDocuments);

			execute("PF('misDocumentos').show()");
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);

			mapMyDocuments.clear();

			String errorLabel = bundle.getMessage("step2.error.myDocuments", null);
			showDialogMessage(errorLabel, MessageType.ERROR);
		}

	}

	/**
	 * Agrega los documentos del tipo de documento seleccionado para el dialogo 'Mis
	 * Documentos'.
	 * 
	 * @param mapMyDocuments
	 * @param tipoDocumento
	 */
	private void addDeoDocuments(Map<String, List<DocumentoDTO>> mapMyDocuments, TipoDocumentoDTO tipoDocumento) {
		// Instancia categoria
		mapMyDocuments.put(tipoDocumento.getNombre(), new ArrayList<DocumentoDTO>());

		// Agrega los documentos
		List<DocumentoDTO> documentosDeoList = documentoService
				.getDocumentosDeoByPersonaAndTipodocumento(login.getPersona(), tipoDocumento);

		for (DocumentoDTO aux : documentosDeoList) {
			mapMyDocuments.get(aux.getTipoDocumento().getNombre()).add(aux);
		}
	}

	/**
	 * Agrega los documentos de notificaciones para el dialogo 'Mis Documentos'.
	 * 
	 * @param mapMyDocuments
	 */
	private void addNotificationDocuments(Map<String, List<DocumentoDTO>> mapMyDocuments) {
		// Label categoria
		String notificacionesLabel = bundle.getMessage("step2.notifications", null);

		// Instancia categoria
		mapMyDocuments.put(notificacionesLabel, new ArrayList<DocumentoDTO>());

		// Agrega los documentos
		List<NotificacionDTO> notificacionesVuc = notificacionService.getNotificacionesByPersona(login.getPersona());
		String usuarioIniciadorDelTramite;

		for (NotificacionDTO notificacion : notificacionesVuc) {
			usuarioIniciadorDelTramite = notificacion.getExpediente().getTipoTramite().getUsuarioIniciador();

			DocumentoDTO result = documentoService.getDocumentoDeoByCodigo(notificacion.getCodSade(),
					usuarioIniciadorDelTramite);

			result.setFechaCreacion(notificacion.getFechaNotificacion());
			result.setNombreOriginal("Documento Notificado: ".concat(result.getTipoDocumento().getDescripcion()));
			result.setUsuarioCreacion(usuarioIniciadorDelTramite);

			if (mapMyDocuments.get(notificacionesLabel) != null) {
				mapMyDocuments.get(notificacionesLabel).add(result);	
			}
			
		}

	}

	/**
	 * Check if a TipoDocumento is a FFDD.
	 * 
	 * @return
	 */
	public boolean isTipoDocumentoFfdd(TipoDocumentoDTO tipoDocumento) {
		return tipoDocumento.getTipadoDcto().getId().equals(TipadoDocumentoEnum.TEMPLATE.getId());
	}

	/**
	 * Close event for FFDD Dialog.
	 */
	public void closeEventFfddDialog() {
		viewFfdd = false;
		refreshMyExpediente();
		update(DIV_TABLADOCUMENTOS);
	}

	/**
	 * Muestra el popup de error desde un bean por la ejecucion de javascript
	 */
	public void showErrorDialogFromBean() {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String param = params.get("mensaje");
		if (StringUtils.isNotBlank(param)) {
			showDialogMessage(param, MessageType.ERROR);
		} else {
			LOG.error("Mensaje de entrada vacío.");
		}
	}

	private void refreshMyExpediente() {
		// INI - + de 1 formulario
		List<DocumentoDTO> documentoDiscreto = documentoService
				.getDocumentoDiscretoByIdexpediente(getExpediente().getId());
		if (documentoDiscreto != null) {
			for (DocumentoDTO aux : documentoDiscreto) {
				getExpediente().getDocumentosList().add(aux);
			}
		}
		// FIN - + de 1 formulario

		fillUploadedDocuments();

		// sortTipsoDocumentoFromTipoTramite(getExpediente());
		// documentsToBeDeleted = new ArrayList<>();

		// currentDocumentosCount = getExpediente().getDocumentosList().size();
	}

	public Map<String, List<DocumentoDTO>> getMapMyDocuments() {
		return mapMyDocuments;
	}

	public void setExternalTipoDocumentoService(IExternalTipoDocumentoService externalTipoDocumentoService) {
		this.externalTipoDocumentoService = externalTipoDocumentoService;
	}

	public void setFormManagerFactory(IFormManagerFactory<IFormManager<Component>> formManagerFactory) {
		this.formManagerFactory = formManagerFactory;
	}

	public void setApplicationService(ApplicationService applicationService) {
		this.applicationService = applicationService;
	}

	public void setDocumentoService(DocumentoService documentoService) {
		this.documentoService = documentoService;
	}

	public void setLogin(LoginMb login) {
		this.login = login;
	}

	public void setNotificacionService(NotificacionService notificacionService) {
		this.notificacionService = notificacionService;
	}

	public void setWebDavService(WebDavService webDavService) {
		this.webDavService = webDavService;
	}

	public Map<Long, Boolean> getUploadedTiposDocumento() {
		return uploadedTiposDocumento;
	}

	public TipoDocumentoDTO getTipoDocumentoSeleccionado() {
		return tipoDocumentoSeleccionado;
	}

	public void setTipoDocumentoSeleccionado(TipoDocumentoDTO tipoDocumentoSeleccionado) {
		this.tipoDocumentoSeleccionado = tipoDocumentoSeleccionado;
	}

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public String getPdfViewUrl() {
		return pdfViewUrl;
	}

	public boolean isViewFfdd() {
		return viewFfdd;
	}

	public void setBundle(DefaultLocaleMessageSource bundle) {
		this.bundle = bundle;
	}

}