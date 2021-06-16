package com.egoveris.vucfront.base.sections;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasoluna.plus.core.i18n.DefaultLocaleMessageSource;

import com.egoveris.vucfront.base.exception.DownloadDocumentException;
import com.egoveris.vucfront.base.mbeans.LoginMb;
import com.egoveris.vucfront.base.service.ApplicationService;
import com.egoveris.vucfront.base.util.AbstractMb;
import com.egoveris.vucfront.base.util.MessageType;
import com.egoveris.vucfront.model.exception.ValidacionException;
import com.egoveris.vucfront.model.model.DocumentoDTO;
import com.egoveris.vucfront.model.model.TipoTramiteDTO;
import com.egoveris.vucfront.model.service.DocumentoService;

@ManagedBean
@ViewScoped
public class MyDocumentsMb extends AbstractMb {

	private static final long serialVersionUID = -1102042124815168577L;
	private static final Logger LOG = LoggerFactory.getLogger(MyDocumentsMb.class);

	@ManagedProperty("#{applicationServiceImpl}")
	private ApplicationService applicationService;
	@ManagedProperty("#{documentoServiceImpl}")
	private DocumentoService documentoService;
	@ManagedProperty("#{loginMb}")
	private LoginMb login;
	@ManagedProperty("#{msg}")
	private DefaultLocaleMessageSource bundle;

	private List<DocumentoDTO> documentosList;
	private List<Long> systemTipoDocumentosList;
	private List<SelectItem> cmbTiposDocumento;
	private String selectedTipoDocumento;

	public void init() {
		documentosList = documentoService.getDocumentosDeoByPersona(login.getPersona());

		/*
		 * Define una lista con los tipos de documento del sistema. Estos documentos no
		 * serán visualizados en la lista de documentos disponibles para el usuario.
		 * 
		 */
		// Limpia la lista de documentos.
		if (systemTipoDocumentosList == null) {
			systemTipoDocumentosList = new ArrayList<>();
		} else {
			systemTipoDocumentosList.clear();
		}
		// Setea la lista.
		for (TipoTramiteDTO aux : applicationService.getAllTipoTramite()) {
			if (!systemTipoDocumentosList.contains(aux.getTipoDocumentoFormulario().getId())) {
				systemTipoDocumentosList.add(aux.getTipoDocumentoFormulario().getId());
			}
		}
		// Carga Tipos de Documento de la persona para el filtro.
		fillTiposDocumentoCombobox();
	}

	/**
	 * Return all the Documents from a Person that are not system documents and are
	 * stored in DEO.
	 * 
	 * @return
	 */
	public List<DocumentoDTO> getDocumentosList() {
		List<DocumentoDTO> retorno = new ArrayList<>();
		for (DocumentoDTO aux : documentosList) {
			if (!systemTipoDocumentosList.contains(aux.getTipoDocumento().getId())) {
				addFilteredDocumento(retorno, aux);
			}
		}
		return retorno;
	}

	private void fillTiposDocumentoCombobox() {
		if (cmbTiposDocumento == null) {
			cmbTiposDocumento = new ArrayList<>();
		}

		Set<Long> addedTiposDocumento = new HashSet<>();
		for (DocumentoDTO aux : getDocumentosList()) {
			if (!addedTiposDocumento.contains(aux.getTipoDocumento().getId())) {
				addedTiposDocumento.add(aux.getTipoDocumento().getId());
				cmbTiposDocumento
						.add(new SelectItem(aux.getTipoDocumento().getId(), aux.getTipoDocumento().getNombre()));
			}
		}
	}

	/**
	 * If a filter of Tipo Documento is selected, add to the table list only the
	 * Documentos with the selected Tipo of Documento.
	 * 
	 * @param listaResultado
	 * @param documento
	 */
	private void addFilteredDocumento(List<DocumentoDTO> listaResultado, DocumentoDTO documento) {
		if (selectedTipoDocumento == null || selectedTipoDocumento.isEmpty()) {
			listaResultado.add(documento);
		} else {
			if (documento.getTipoDocumento().getId().equals(Long.valueOf(selectedTipoDocumento))) {
				listaResultado.add(documento);
			}
		}
	}

	/**
	 * Downloads the selected Documento.
	 * 
	 * @param doc
	 * @return
	 */
	public StreamedContent cmdDownloadDocument(DocumentoDTO document) {
		String contentType = "application/octet-stream";

		try {
			DefaultStreamedContent returned = new DefaultStreamedContent(documentoService.downloadDocument(document),
					contentType, document.getNumeroDocumento() + ".pdf");
			return returned;
		} catch (DownloadDocumentException e) {
			showDialogMessage(e.getMessage(), MessageType.ERROR);
		} catch (ValidacionException e2) {
			showDialogMessage(bundle.getMessage("myDocumentsErrorSistema", null), MessageType.ERROR);
		}

		return null;
	}

	public String getSelectedTipoDocumento() {
		return selectedTipoDocumento;
	}

	public void setSelectedTipoDocumento(String selectedTipoDocumento) {
		this.selectedTipoDocumento = selectedTipoDocumento;
	}

	public List<SelectItem> getCmbTiposDocumento() {
		return cmbTiposDocumento;
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

	public void setBundle(DefaultLocaleMessageSource bundle) {
		this.bundle = bundle;
	}
}