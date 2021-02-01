package com.egoveris.vucfront.base.vm;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Window;

import com.egoveris.deo.model.model.ResponseTipoDocumento;
import com.egoveris.deo.ws.service.IExternalGenerarDocumentoService;
import com.egoveris.deo.ws.service.IExternalTipoDocumentoService;
import com.egoveris.ffdd.render.service.IFormManager;
import com.egoveris.ffdd.render.service.IFormManagerFactory;
import com.egoveris.vucfront.model.model.DocumentoDTO;
import com.egoveris.vucfront.model.model.DocumentoEstadoDTO;
import com.egoveris.vucfront.model.model.ExpedienteFamiliaSolicitudDTO;
import com.egoveris.vucfront.model.model.TipoDocumentoDTO;
import com.egoveris.vucfront.model.service.DocumentoService;
import com.egoveris.vucfront.model.service.ExpedienteService;
import com.egoveris.vucfront.model.util.DocumentoEstadoEnum;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class FormularioDinamicoVM {
	private static final Logger LOG = LoggerFactory.getLogger(FormularioDinamicoVM.class);

	private static final String ID_TIPODOC = "idTipoDoc";
	private static final String ID_EXPEDIENTE = "idExp";
	private static final String VIEWFFDD = "view";
	private static final String SUBSANARVIEW = "subsanar";

	@WireVariable("documentoServiceImpl")
	private DocumentoService documentoService;
	@WireVariable("expedienteServiceImpl")
	private ExpedienteService expedienteService;
	@WireVariable("generarDocumentoService")
	private IExternalGenerarDocumentoService externalGenerarDocumentoService;
	@WireVariable("tipoDocumentoService")
	private IExternalTipoDocumentoService externalTipoDocumentoService;
	@WireVariable("zkFormManagerFactory")
	private IFormManagerFactory<IFormManager<Component>> formManagerFactory;
	private IFormManager<Component> manager;

	@Wire("#formularioDinamico")
	private Window window;
	@Wire("#mainFormulario")
	private Hbox hbox;
	@Wire("#cmdGuardar")
	private Button btnGuardar;

	private ExpedienteFamiliaSolicitudDTO expediente;
	private TipoDocumentoDTO tipoDocumentoVuc;
	private Integer idTransaccion;
	private HashMap<String, String> getParameters;

	@AfterCompose
	public void init(@ContextParam(ContextType.VIEW) final Component view) {
		Selectors.wireComponents(view, this, false);

		setGetParameters();
		printFfdd();
	}

	/**
	 * Set the GET parameters into the VM.
	 */
	private void setGetParameters() {
		if (getParameters == null) {
			getParameters = new HashMap<>();
		}

		if (!Executions.getCurrent().getParameter(ID_TIPODOC).isEmpty()) {
			getParameters.put(ID_TIPODOC, Executions.getCurrent().getParameter(ID_TIPODOC));
		}
		if (!Executions.getCurrent().getParameter(ID_EXPEDIENTE).isEmpty()) {
			getParameters.put(ID_EXPEDIENTE, Executions.getCurrent().getParameter(ID_EXPEDIENTE));
		}
		if (!Executions.getCurrent().getParameter(VIEWFFDD).isEmpty()) {
			getParameters.put(VIEWFFDD, Executions.getCurrent().getParameter(VIEWFFDD));
		}
		if (Executions.getCurrent().getParameter(SUBSANARVIEW) != null && !Executions.getCurrent().getParameter(SUBSANARVIEW).isEmpty()) {
			getParameters.put(SUBSANARVIEW, Executions.getCurrent().getParameter(SUBSANARVIEW));
		}
	}

	/**
	 * Print the FFDD in the Zul
	 * 
	 */
	private void printFfdd() {
		boolean showFfddDialog = true;
		//Revisar esto, para que sea dinamico.
		if (getParameters.get(ID_TIPODOC) != null && getParameters.get(ID_EXPEDIENTE) != null) {
			try {
				// Set the current Expediente
				Long idExp = Long.valueOf(getParameters.get(ID_EXPEDIENTE));
				expediente = expedienteService.getExpedienteFamiliaSolicitudById(idExp);

				tipoDocumentoVuc = documentoService.getTipoDocumentoById(Long.valueOf(getParameters.get(ID_TIPODOC)));
				if (tipoDocumentoVuc != null) {
					ResponseTipoDocumento tipoDocumentoDeo = externalTipoDocumentoService
							.consultarTipoDocumentoPorAcronimo(tipoDocumentoVuc.getAcronimoGedo());

					if (tipoDocumentoDeo != null) {
						String ffddName = tipoDocumentoDeo.getIdFormulario();
						manager = formManagerFactory.create(ffddName);
						Component formulario;

						// Print an empty new Document or a filled previous one.
						DocumentoDTO ffddFromExpediente = getFfddFromExpediente(tipoDocumentoVuc);
						if (ffddFromExpediente != null) {
							this.idTransaccion = ffddFromExpediente.getIdTransaccion().intValue();
							formulario = manager.getFormComponent(idTransaccion);
							if ("true".equals(getParameters.get(VIEWFFDD))) {
								manager.readOnlyMode(true);
								btnGuardar.setVisible(false);
							}
						} else {
							List<DocumentoDTO> ffddDiscreto = documentoService
									.getDocumentoDiscretoByIdexpediente(idExp);
							if (CollectionUtils.isNotEmpty(ffddDiscreto)) {
								for (DocumentoDTO aux : ffddDiscreto) {
									if (tipoDocumentoVuc.getId() == aux.getTipoDocumento().getId()) {
										this.idTransaccion = aux.getIdTransaccion().intValue();
										manager.deleteFormWeb(idTransaccion);
										documentoService.deleteDocument(aux);
										this.idTransaccion = null;
									}
								}
							}
							formulario = manager.getFormComponent();
						}
						hbox.appendChild(formulario);
					}
				}
			} catch (Exception e) {
				Clients.evalJavaScript("parent.showErrorJs('Se ha producido un error al recuperar el formulario.');");
				LOG.error("ERROR al recuperar el formulario dinámico: {}", e.getMessage());
				showFfddDialog = false;
			}

			if (showFfddDialog && !("true".equals(getParameters.get(SUBSANARVIEW)))) {
				Clients.evalJavaScript("parent.PF('ffddDialog').show();");
			}

			Clients.evalJavaScript("parent.PF('statusDialog').hide();");
		}
	}

	@Command
	public void cmdGuardar() {
		// Actualiza formulario existente
		DocumentoDTO newFormDocumento = null;
		if (this.idTransaccion != null) {
			manager.updateFormWeb(idTransaccion);
			newFormDocumento = documentoService.getDocumentoByIdTransaccion(Long.valueOf(idTransaccion));
			newFormDocumento.getDocumentoEstados().add(getDocumentoEstado(newFormDocumento));
		}else {
			newFormDocumento = new DocumentoDTO();
		}
		// Genera un Documento nuevo asociado al formulario y lo anexa al
		// expediente
		
		idTransaccion = manager.saveValues();
		 
		newFormDocumento.setIdTransaccion(idTransaccion.longValue());
		newFormDocumento.setTipoDocumento(new TipoDocumentoDTO(tipoDocumentoVuc.getId().longValue()));
		newFormDocumento.setReferencia(tipoDocumentoVuc.getNombre());
		newFormDocumento.setUsuarioCreacion(expediente.getPersona().getNombreApellido());
		newFormDocumento.setNombreOriginal("Documento generado con datos del formulario.");
		// Se setea "discretamente" el id del expediente al Documento en el campo
		// urlTemporal. Mientras no sea persistido, se mantendrá el Documento sin
		// asociarse al expediente formalmente.
		newFormDocumento.setUrlTemporal(expediente.getId().toString());
		
		documentoService.saveDocument(newFormDocumento);
		

		Clients.evalJavaScript("parent.PF('ffddDialog').hide();");
	}

	private DocumentoDTO getFfddFromExpediente(TipoDocumentoDTO tipoDocumento) {
		DocumentoDTO foundDocumento = null;
		// Rescata el documento desde el expediente (ya fue persistido)
		for (DocumentoDTO aux : expediente.getDocumentosList()) {
			if (aux.getTipoDocumento().getId().equals(tipoDocumento.getId())) {
				foundDocumento = aux;
				break;
			}
		}

		return foundDocumento;
	}

	private DocumentoEstadoDTO getDocumentoEstado (DocumentoDTO documento) {
		DocumentoEstadoDTO estado = new DocumentoEstadoDTO();
		estado.setDocumento(documento);
		estado.setFecha(new Date());
		estado.setEstado(DocumentoEstadoEnum.SUBSANADO.getEstado());
		return estado;
	}

}