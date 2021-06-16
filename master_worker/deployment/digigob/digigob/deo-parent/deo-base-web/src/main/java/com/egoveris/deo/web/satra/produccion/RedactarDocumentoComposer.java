package com.egoveris.deo.web.satra.produccion;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.rowset.serial.SerialBlob;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.jbpm.api.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkforge.ckez.CKeditor;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Box;
import org.zkoss.zul.Button;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Space;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;

import com.egoveris.commons.databaseconfiguration.propiedades.AppProperty;
import com.egoveris.deo.base.exception.ExtensionesFaltantesException;
import com.egoveris.deo.base.exception.PDFConversionException;
import com.egoveris.deo.base.exception.VariableWorkFlowNoExisteException;
import com.egoveris.deo.base.service.ArchivoDeTrabajoService;
import com.egoveris.deo.base.service.ArchivosEmbebidosService;
import com.egoveris.deo.base.service.BuscarDocumentosSadeService;
import com.egoveris.deo.base.service.ComunicacionService;
import com.egoveris.deo.base.service.DocumentoTemplateService;
import com.egoveris.deo.base.service.FirmaConjuntaService;
import com.egoveris.deo.base.service.GenerarDocumentoService;
import com.egoveris.deo.base.service.GestionArchivosWebDavService;
import com.egoveris.deo.base.service.HistorialService;
import com.egoveris.deo.base.service.PdfService;
import com.egoveris.deo.base.service.ProcesamientoTemplate;
import com.egoveris.deo.base.service.ReparticionHabilitadaService;
import com.egoveris.deo.base.service.TipoActuacionService;
import com.egoveris.deo.base.service.TipoDocumentoService;
import com.egoveris.deo.base.util.GeneradorDocumentoFactory;
import com.egoveris.deo.base.util.UtilitariosServicios;
import com.egoveris.deo.model.model.ActuacionSADEBean;
import com.egoveris.deo.model.model.ArchivoDeTrabajoDTO;
import com.egoveris.deo.model.model.ArchivoEmbebidoDTO;
import com.egoveris.deo.model.model.ComunicacionDTO;
import com.egoveris.deo.model.model.DocumentoMetadataDTO;
import com.egoveris.deo.model.model.DocumentoTemplateDTO;
import com.egoveris.deo.model.model.DocumentoTemplatePKDTO;
import com.egoveris.deo.model.model.FirmanteDTO;
import com.egoveris.deo.model.model.MacroEventData;
import com.egoveris.deo.model.model.PlantillaDTO;
import com.egoveris.deo.model.model.RequestGenerarDocumento;
import com.egoveris.deo.model.model.TipoDocumentoDTO;
import com.egoveris.deo.model.model.TipoDocumentoTemplateDTO;
import com.egoveris.deo.model.model.UsuarioExternoDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.deo.web.satra.ValidarApoderamientoComposer;
import com.egoveris.deo.web.satra.macros.PrevisualizacionDocumento;
import com.egoveris.deo.web.utils.Utilitarios;
import com.egoveris.deo.ws.exception.ClavesFaltantesException;
import com.egoveris.ffdd.model.model.FormularioDTO;
import com.egoveris.ffdd.render.service.IFormManager;
import com.egoveris.ffdd.render.service.IFormManagerFactory;
import com.egoveris.ffdd.ws.service.ExternalFormularioService;
import com.egoveris.shareddocument.base.exception.AlfrescoException;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class RedactarDocumentoComposer extends ValidarApoderamientoComposer {

	private static final String UPLOAD_DOCUMENTO = "uploadDocumento";

	private static final long serialVersionUID = 6273590591797662072L;
	private static final Logger logger = LoggerFactory.getLogger(RedactarDocumentoComposer.class);

	private static final String IMG_MAIL_SIN_MENSAJE = "/imagenes/email.png";
	private static final String IMG_MAIL_CON_MENSAJE = "/imagenes/ANIMADO_iconosobre.gif";
	private static final String REVISAR_DOCUMENTO = "Revisar Documento";
	private static final String RECHAZADO = "Rechazado";
	private static final String TIPO_PROD_IMPORTADO = "IMPORTADO";
	private static final String VALIDAAPODER = "validarApoderamiento";
	private static final String CENTER = "center";
	private static final String OBLDATOS = "gedo.iniciarDocumento.errores.faltaDatosPropiosObligatorio";
	private static final String FIRMAERR = "Error al enviar a firmar - ";
	private static final String QUESTION = "gedo.licencia.question";
	private static final String CLAVEFALTANTE = "gedo.producirDocumento.error.clavesFaltantes";
	private static final String ERRVER = "Error al previsualizar - ";
	private static final String ERRDOCADJ = "gedo.producirDocumento.error.documentoSinAdjuntar";
	private static final String GENERR = "gedo.general.information";
	private static final String DATOS = "datos";
	private static final String FUNCION = "funcion";
	private static final String GEDOERROR = "gedo.general.error";
	private static final String ADJUNTADOC = "/adjuntarDocumento.zul";
	private static final String EXCE = "Exception: ";
	private static final String PXTRES = "395px";
	private static final String INCORPORAR = "incorporar";
	private static final String INCORPORARACT = "/imagenes/IncorporarActuacionSADE.png";
	private static final String PXDOS = "265px";
	private static final String TIPODOC = "tipoDocumento";
	private static final String TIPO_USUARIO_FIRMANTE = "Firmante";
	private static final String TIPO_USUARIO_REVISOR = "Revisor";

	@WireVariable("buscarDocumentosSadeServiceImpl")
	private BuscarDocumentosSadeService buscarDocumentosSadeService;
	@WireVariable("tipoActuacionServiceImpl")
	private TipoActuacionService tipoActuacionService;
	@WireVariable("pdfServiceImpl")
	private PdfService pdfService;
	@WireVariable("zkFormManagerFactory")
	private IFormManagerFactory<IFormManager<Component>> formManagerFact;
	@WireVariable("procesamientoTemplateImpl")
	private ProcesamientoTemplate procesamientoTemplate;
	@WireVariable("zkFormManager")
	private IFormManager<Component> formManager;
	@WireVariable("documentoTemplateServiceImpl")
	private DocumentoTemplateService documentoTemplateService;
	@WireVariable("historialServiceImpl")
	private HistorialService historialService;
	@WireVariable("tipoDocumentoServiceImpl")
	private TipoDocumentoService tipoDocumentoService;
	@WireVariable("gestionArchivosWebDavServiceImpl")
	private GestionArchivosWebDavService gestionArchivosWebDavService;
	@WireVariable("firmaConjuntaServiceImpl")
	private FirmaConjuntaService firmaConjuntaService;
	@WireVariable("archivoDeTrabajoServiceImpl")
	private ArchivoDeTrabajoService archivoDeTrabajoService;
	@WireVariable("archivosEmbebidosServiceImpl")
	private ArchivosEmbebidosService archivosEmbebidosService;
	@WireVariable("reparticionHabilitadaServiceImpl")
	private ReparticionHabilitadaService reparticionesHabilitadaService;
	@WireVariable("generarDocumentoServiceImpl")
	private GenerarDocumentoService generarDocumentoService;
	@WireVariable("comunicacionServiceImpl")
	private ComunicacionService comunicacionService;

	private Window redactarDocumentoWindow;
	protected Textbox motivoTb;
	protected Button previsualizar;
	protected Box composicionDocumento;
	protected CKeditor ckeditor;
	protected Checkbox solicitudAvisoFirma;
	protected Grid descripcionSadeWindow;
	protected Toolbarbutton incorporacionSade;

	protected Window enviarARevisarWindow;
	protected Window enviarAFirmarWindow;
	protected Window incorporarSadeWindow;
	protected Window definirDestinatarioWindow;
	protected Window detalleComunicacionWindow;
	protected Separator separadorImportado;
	protected Separator separadorSade;
	protected Space templateSpace;
	protected Grid gridSADE;

	protected Window agregarUsuariosFirmaConjuntaView;
	protected Textbox usuariosAgregadosTxt;
	protected Textbox revisoresAgregados;
	protected Textbox usuariosReservados;
	protected Vbox vboxDatos;
	protected Window usuariosFirmaConjunta;
	protected Cell cellMotivo;
	protected Image iconoFirmaConjunta;
	protected Image iconoRevisoresFirmaConjunta;
	protected Image iconoUsuariosReservados;
	protected Button usuariosFirmaConjuntatoolbar;
	protected Toolbarbutton definirDestinatariosButton;
	protected Button selfFirmarButton;
	protected Toolbarbutton selfImportarDocumentoButton;
	protected Button selfFirmaConjuntaButton;
	protected Button firmarButton;
	protected Button historialDocumento;
	protected Toolbarbutton usuariosReservadosButton;
	protected Toolbarbutton detalleCOButton;
	protected Window archivoEmbebidoWindow;

	protected Separator separator;
	protected Tab produccionTab;
	protected Tab embebidoTab;
	protected Tab datosPropiosTab;
	protected Tab archivoTrabajo;
	protected Tabbox conjuntoTabs;

	private AppProperty appProperty;

	private List<ActuacionSADEBean> actuacionesSADE;
	private List<Usuario> usuariosFirmantes;
	private List<String> usuariosReservadosList;
	private AnnotateDataBinder redactarDocumentoBinder;
	private List<DocumentoMetadataDTO> listaDocMetadata;
	private List<ArchivoDeTrabajoDTO> listaArhivosDeTrabajo = null;

	private List<ArchivoEmbebidoDTO> listaArchivosEmbebidos = null;
	private PlantillaDTO selectedPlantilla;
	private List<PlantillaDTO> listaPlantilla;

	private Checkbox solicitudEnvioCorreo;

	private String tareaAsignada;
	private String mensajeArchivosDeTrabajo;
	private String mensajeArchivosEmbebidos;

	private Hbox iconos_derechos;

	private Comboitem userRevisor;
	private Comboitem userFirmante;
	private String msjRevisor;
	private String descripcionTrata;

	private List<String> listaUsuariosDestinatarios;
	private List<String> listaUsuariosDestinatariosCopia;
	private List<String> listaUsuariosDestinatariosCopiOculta;
	private List<UsuarioExternoDTO> listaUsuariosExternos;
	private String mensajeDestinatario;
	private String nombreArchivoTemporal;
	private ComunicacionDTO comunicacion;
	private Integer idComunicacionRespondida = null;
	private Integer idDestinatario = null;
	private String usuarioSupervisado;

	protected Hbox documentoArchivoTrabajoHbox;
	protected Window documentoArchivoTrabajo;
	protected Hbox documentoArchivoEmbebidoHbox;
	protected Window documentoArchivoEmbebido;
	protected Hbox documentoDatosPropiosHbox;
	protected Window documentoDatosPropios;

	protected Hbox documentoWindowHbox;
	protected Window documentoWindow;
	protected Hbox documentoSadeWindowHbox;
	protected Window documentoSadeWindow;
	protected Hbox documentoTemplateWindowHbox;
	protected Div documentoTemplateWindowDiv;
	protected String codigoSadePapel; // Solo se utilizara si el usuario desea
	// ingresar un documento SADE papel

	private Div motivoRechazoDiv;
	private Label motivoRechazoLabel;
	private Label trataSadeLabel;
	private Label datosSadeLabel;
	private Toolbarbutton eliminarSade;

	private TipoDocumentoDTO tipoDocumento;
	private TipoDocumentoTemplateDTO tipoDocumentoTemplate;
	private DocumentoTemplateDTO documentoTemplate;
	private byte[] template;
	private boolean firmoYoMismo;
	private byte[] documentoNoTemplateByte;
	private byte[] documentoTemplateLibreByte;
	private Label nombreArchivoLabel;
	private String pathFileTemporal;
	private String maximoPrevisualizacion;
	private String mensajeRevisor;
	private String user;
	private Integer idTransaccion;
	private boolean salida;
	private boolean bloqueante;
	private boolean puedoFirmar;
	private String tipoProd;
	private String gestorDocumental;
	private String estadoExpediente = null;
	private boolean mensajeDestinatarioLicencia;

	HashMap<String, Object> datos = new HashMap<>();
	
	@Override
	public void doAfterCompose(Component component) throws Exception {

		super.doAfterCompose(component);
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
		this.appProperty = (AppProperty) SpringUtil.getBean("dBProperty");
		estadoExpediente = (String) component.getDesktop().getAttribute(Constantes.VAR_NUMERO_SA);
		component.addEventListener(Events.ON_NOTIFY, new RedactarDocumentoVersionNuevaComposerListener(this));
		component.addEventListener(Events.ON_USER, new RedactarDocumentoVersionNuevaComposerListener(this));
		this.documentoSadeWindowHbox.addEventListener(Events.ON_NOTIFY,
				new RedactarDocumentoVersionNuevaComposerListener(this));
		this.documentoWindowHbox.addEventListener(Events.ON_UPLOAD,
				new RedactarDocumentoVersionNuevaComposerListener(this));
		this.enviarARevisarWindow.addEventListener(Events.ON_NOTIFY,
				new RedactarDocumentoVersionNuevaComposerListener(this));
		this.enviarAFirmarWindow.addEventListener(Events.ON_NOTIFY,
				new RedactarDocumentoVersionNuevaComposerListener(this));
		this.incorporarSadeWindow.addEventListener(Events.ON_NOTIFY,
				new RedactarDocumentoVersionNuevaComposerListener(this));

		this.maximoPrevisualizacion = this.appProperty.getString("gedo.maximoPrevisualizacion");
		user = (String) Executions.getCurrent().getSession().getAttribute(Constantes.SESSION_USERNAME);

		this.solicitudAvisoFirma.setChecked(estadoSolicitudAviso());

		this.redactarDocumentoBinder = new AnnotateDataBinder(component);
		this.redactarDocumentoBinder.loadAll();

		listaArhivosDeTrabajo = archivoDeTrabajoService
				.buscarArchivosDeTrabajoPorProceso(this.getWorkingTask().getExecutionId());
		listaArchivosEmbebidos = archivosEmbebidosService
				.buscarArchivosEmbebidosPorProceso(this.getWorkingTask().getExecutionId());
		this.listaUsuariosDestinatarios = new ArrayList<>();
		this.listaUsuariosDestinatariosCopia = new ArrayList<>();
		this.listaUsuariosDestinatariosCopiOculta = new ArrayList<>();
		this.listaUsuariosExternos = new ArrayList<>();
		this.puedoFirmar = true;

		obtenerDatosTipoDeDocumento();

		inicializarGeneradorDocumento();

		armadoPantalla();
	}

	@SuppressWarnings("unchecked")
	private void obtenerDatosTipoDeDocumento() throws Exception {

		String idTipoDocumento = (String) getVariableWorkFlow(Constantes.VAR_TIPO_DOCUMENTO);
		this.tipoDocumento = this.tipoDocumentoService.buscarTipoDocumentoPorId(Integer.valueOf(idTipoDocumento));

		if (this.tipoDocumento.getEsComunicable()) {

			this.mensajeDestinatario = (String) getVariableWorkFlow(Constantes.VAR_MENSAJE_DESTINATARIO);
			this.listaUsuariosDestinatarios = (List<String>) getVariableWorkFlow(Constantes.VAR_USUARIOS_DESTINATARIOS);
			this.listaUsuariosDestinatariosCopia = (List<String>) getVariableWorkFlow(
					Constantes.VAR_USUARIOS_DESTINATARIOS_COPIA);
			this.listaUsuariosDestinatariosCopiOculta = (List<String>) getVariableWorkFlow(
					Constantes.VAR_USUARIOS_DESTINATARIOS_COPIA_OCULTA);
			this.listaUsuariosExternos = (List<UsuarioExternoDTO>) getVariableWorkFlow(
					Constantes.VAR_USUARIOS_DESTINATARIOS_EXTERNOS);
			this.usuarioSupervisado = this.processEngine.getExecutionService()
					.getVariable(this.workingTask.getExecutionId(), Constantes.VAR_USUARIO_SUPERVISADO) != null
							? (String) getVariableWorkFlow(Constantes.VAR_USUARIO_SUPERVISADO) : " ";
			this.idDestinatario = (Integer) this.processEngine.getExecutionService()
					.getVariable(this.workingTask.getExecutionId(), Constantes.VAR_ID_DESTINATARIO_COMUNICACION);

			if (this.processEngine.getExecutionService().getVariable(this.workingTask.getExecutionId(),
					Constantes.VAR_ID_COMUNICACION_RESPONDIDA) != null) {
				this.idComunicacionRespondida = (Integer) this.processEngine.getExecutionService()
						.getVariable(this.workingTask.getExecutionId(), Constantes.VAR_ID_COMUNICACION_RESPONDIDA);
			} else {
				if (this.processEngine.getExecutionService().getVariable(this.workingTask.getExecutionId(),
						Constantes.VAR_RESPONDE_COMUNICACION) != null) {
					this.idComunicacionRespondida = ((ComunicacionDTO) this.processEngine.getExecutionService()
							.getVariable(this.workingTask.getExecutionId(), Constantes.VAR_RESPONDE_COMUNICACION))
									.getId();
				}
			}
		}

		//TODO: se comenta hasta descubrir para que buscar los embebidos si tipoDocumento, si tiene embebidos, ya los trae seteados
//		this.tipoDocumento.setTipoDocumentoEmbebidos(this.archivosEmbebidosService.buscarTipoDocEmbebidos(this.tipoDocumento));

		if (this.tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_TEMPLATE
				|| this.tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO_TEMPLATE) {
			obtenerDatosTipoDocumentoTemplate(this.tipoDocumento);
		}
	}

	private void obtenerDatosTipoDocumentoTemplate(TipoDocumentoDTO tipoDocumento) throws Exception {
		if (this.getWorkingTask().getActivityName().equals(Constantes.TASK_REVISAR_DOCUMENTO)
				|| this.getWorkingTask().getActivityName().equals(Constantes.TASK_RECHAZADO)) {

			this.documentoTemplate = documentoTemplateService.findByWorkflowId(this.getWorkingTask().getExecutionId(), tipoDocumento);

			if (documentoTemplate != null && documentoTemplate.getTipoDocumentoTemplate() != null) {
				this.tipoDocumentoTemplate = documentoTemplate.getTipoDocumentoTemplate();
			} else {
				throw new Exception(Labels.getLabel("gedo.redactarDocumentoComposer.exception.noObtenerDocAsociado"));
			}
		} else {
			this.tipoDocumentoTemplate = procesamientoTemplate
					.obtenerUltimoTemplatePorTipoDocumento(this.tipoDocumento);
		}
		this.template = this.tipoDocumentoTemplate.getTemplate().getBytes();
	}

	private void validarTipoProduccionLibre() {

		if (this.tipoDocumento.getPermiteEmbebidos()) {
			this.embebidoTab.setDisabled(false);
			HashMap<String, Object> hmae = new HashMap<>();
			hmae.put(ArchivoEmbebidoDTO.ARCHIVOS_EMBEBIDOS, this.listaArchivosEmbebidos);
			hmae.put(TIPODOC, this.tipoDocumento);
			this.self.getDesktop().setAttribute("workFlowOrigen", this.workingTask.getExecutionId());
			if (this.tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO) {
				this.documentoArchivoEmbebido.setHeight(PXDOS);
			}
			setDocumentoArchivoEmbebido(
					(Window) Executions.createComponents("/archivosEmbebidos.zul", documentoArchivoEmbebido, hmae));
		}
	}

	private void validarDocumentoSade(String documentoSade) {

		if (documentoSade != null && !documentoSade.equals("")) {

			// Habilito y envio la señal para que se llene
			// automaticamente el componente
			// (incorporacionDocumentoSADE)
			incorporacionSade.setVisible(true);
			incorporacionSade.setImage(INCORPORARACT);
			incorporacionSade.setDisabled(true);
			obtenerDatosSade(documentoSade);
			this.eliminarSade.setImage("/imagenes/EliminarDocumentoAdjunto.png");
			this.eliminarSade.setVisible(true);
		} else {
			incorporacionSade.setVisible(true);
			incorporacionSade.setImage(INCORPORARACT);
			incorporacionSade.setAttribute(INCORPORAR, false);
		}
	}

	@SuppressWarnings("unchecked")
	private void armadoPantalla() throws InterruptedException {

		HashMap<String, Object> hm = new HashMap<>();
		hm.put("workingTask", this.workingTask);
		hm.put(TIPODOC, this.tipoDocumento);

		iconos_derechos.setVisible(true);
		if (this.tipoDocumento.getEsComunicable()) {
			this.definirDestinatariosButton.setVisible(true);
		}

		if (this.idComunicacionRespondida != null) {
			this.detalleCOButton.setVisible(true);
		}

		this.motivoTb.setFocus(true);
		usuariosReservadosButton.setVisible(false);
		if (!this.tipoDocumento.getTieneAviso()) {
			solicitudAvisoFirma.setDisabled(true);
			solicitudAvisoFirma.setChecked(false);
		} else {
			solicitudAvisoFirma.setDisabled(false);
		}
		if (this.tipoDocumento.getEsConfidencial()) {
			usuariosReservadosButton.setVisible(true);
			iconoUsuariosReservados.setVisible(true);

			List<Object> rotos = (List<Object>) this.processEngine.getExecutionService()
					.getVariable(this.workingTask.getExecutionId(), Constantes.VAR_USUARIOS_RESERVADOS);
			List<Usuario> listaUsuariosReservados = new ArrayList<>();
			if (rotos != null && rotos.size() > 0) {
				if (rotos.get(0) instanceof Usuario) {
					for (Object userP : rotos) {
						listaUsuariosReservados.add((Usuario) userP);
					}
					this.usuariosReservadosList = subsanarListasMalGrabadasSoloreservados(listaUsuariosReservados);
				} else {
					this.usuariosReservadosList = (List<String>) this.processEngine.getExecutionService()
							.getVariable(this.workingTask.getExecutionId(), Constantes.VAR_USUARIOS_RESERVADOS);
					if (this.usuariosReservadosList == null) {
						this.usuariosReservadosList = new ArrayList<>();
					}
				}
			} else {
				this.usuariosReservadosList = new ArrayList<>();
			}
			actualizarPopUpReservados();
		}

		if (this.getWorkingTask().getActivityName().equals(Constantes.TASK_REVISAR_DOCUMENTO)
				|| this.getWorkingTask().getActivityName().equals(Constantes.TASK_RECHAZADO)) {

			mensajeRevisor = (String) this.processEngine.getExecutionService()
					.getVariable(this.getWorkingTask().getExecutionId(), Constantes.VAR_MENSAJE_PRODUCTOR);
			String motivo = (String) this.processEngine.getExecutionService()
					.getVariable(this.workingTask.getExecutionId(), Constantes.VAR_MOTIVO);
			motivoTb.setValue(motivo);

			if (this.getWorkingTask().getActivityName().equals(Constantes.TASK_RECHAZADO)) {
				this.motivoRechazoDiv.setVisible(true);
				this.conjuntoTabs.setHeight("435px");
				this.documentoArchivoTrabajo.setHeight(PXTRES);
				this.documentoDatosPropios.setHeight(PXTRES);
				this.documentoArchivoEmbebido.setHeight(PXTRES);
				String usuarioRechazo = (String) this.processEngine.getExecutionService()
						.getVariable(this.workingTask.getExecutionId(), Constantes.VAR_USUARIO_DERIVADOR);
				String nombreUsuarioRechazo = this.usuarioService.obtenerUsuario(usuarioRechazo).getNombreApellido();
				String motivoRechazo = (String) this.processEngine.getExecutionService()
						.getVariable(this.workingTask.getExecutionId(), Constantes.VAR_MOTIVO_RECHAZO) + " - "
						+ nombreUsuarioRechazo;
				this.motivoRechazoLabel.setValue(Utilitarios.motivoParseado(motivoRechazo, 75));
				this.motivoRechazoLabel.setTooltiptext(motivoRechazo);
			} else {
				mensajeRevisor = (String) this.processEngine.getExecutionService()
						.getVariable(this.getWorkingTask().getExecutionId(), Constantes.VAR_MENSAJE_A_REVISOR);
			}
			validarTipoProduccionLibre();
			if (this.tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_LIBRE) {

				// Habilito y envio la señal para que se llene automaticamente
				// el componente de texto (ckeditor)
				setDocumentoWindow(
						(Window) Executions.createComponents("/producirTemplate.zul", documentoWindowHbox, hm));
				this.ckeditor = (CKeditor) obtenerComponenteVentanaHijo(documentoWindow, "ckeditor");
				byte[] bytes = (byte[]) this.processEngine.getExecutionService()
						.getVariable(this.getWorkingTask().getExecutionId(), Constantes.VAR_CONTENIDO);
				try {
					if (bytes != null) {
						Blob blob = new SerialBlob(bytes);
						String contenido = new String(blob.getBytes(1, (int) blob.length()));
						if (!StringUtils.isEmpty(contenido)) {
							this.ckeditor.setValue(contenido);
						}
					}
				} catch (Exception e) {
					logger.error(EXCE + e.getMessage(), e);
				}
				// DesHabilito la visualizacion del boton de Incorporacion
				// Documento Sade
				incorporacionSade.setVisible(false);

			} else if (this.tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO) {
				this.tipoProd = TIPO_PROD_IMPORTADO;
				this.redactarDocumentoWindow.setHeight("460px");
				this.conjuntoTabs.setHeight("305px");
				this.separadorImportado.setHeight("40px");
				this.separadorSade.setHeight("20px");
				this.documentoArchivoTrabajo.setHeight(PXDOS);
				this.documentoDatosPropios.setHeight(PXDOS);

				setDocumentoWindow((Window) Executions.createComponents(ADJUNTADOC, documentoWindowHbox, hm));
				this.descripcionSadeWindow.setVisible(true);

				String documentoSade = (String) this.processEngine.getExecutionService()
						.getVariable(this.workingTask.getExecutionId(), Constantes.VAR_NUMERO_SADE_PAPEL);

				validarDocumentoSade(documentoSade);

			} else if (this.tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_TEMPLATE) {

				// DesHabilito la visualizacion del boton de Incorporacion
				// Documento Sade
				incorporacionSade.setVisible(false);

				try {
					if (this.documentoTemplate != null) {
						this.documentoTemplateWindowDiv.setVisible(true);
						this.formManager = this.formManagerFact.create(tipoDocumentoTemplate.getIdFormulario());
						this.documentoTemplateWindowHbox
								.appendChild(formManager.getFormComponent(documentoTemplate.getIdTransaccion()));
						this.documentoTemplateLibreByte = this.procesamientoTemplate
								.armarDocumentoTemplate(this.tipoDocumento, this.formManager.getValues());
						this.templateSpace.setHeight("0px");
						this.documentoTemplateWindowDiv.setHeight("370px");

					}
				} catch (Exception e) {
					logger.error(
							"Ha ocurrido un error al renderizar la pantalla para este Tipo de Documento Template - "
									+ e.getMessage(),
							e.getMessage());
					throw e;
				}

			} else if (this.tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO_TEMPLATE) {

				setDocumentoWindow((Window) Executions.createComponents(ADJUNTADOC, documentoWindowHbox, hm));
				this.descripcionSadeWindow.setVisible(true);

				String documentoSade = (String) this.processEngine.getExecutionService()
						.getVariable(this.workingTask.getExecutionId(), Constantes.VAR_NUMERO_SADE_PAPEL);

				validarDocumentoSade(documentoSade);

				try {
					if (this.documentoTemplate != null) {
						this.documentoTemplateWindowDiv.setVisible(true);
						this.formManager = this.formManagerFact.create(tipoDocumentoTemplate.getIdFormulario());
						this.documentoTemplateWindowHbox
								.appendChild(formManager.getFormComponent(documentoTemplate.getIdTransaccion()));
						this.documentoTemplateLibreByte = this.procesamientoTemplate
								.armarDocumentoTemplate(this.tipoDocumento, this.formManager.getValues());
					}
				} catch (Exception e) {
					logger.error(
							"Ha ocurrido un error al renderizar la pantalla para este Tipo de Documento Template - "
									+ e.getMessage(),
							e.getMessage());
					throw e;
				}

			}
		}
		// Producir Documento
		else {

			mensajeRevisor = (String) this.processEngine.getExecutionService()
					.getVariable(this.getWorkingTask().getExecutionId(), Constantes.VAR_MENSAJE_PRODUCTOR);
			validarTipoProduccionLibre();
			if (this.tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_LIBRE) {

				setDocumentoWindow(
						(Window) Executions.createComponents("/producirTemplate.zul", documentoWindowHbox, hm));
				this.ckeditor = (CKeditor) obtenerComponenteVentanaHijo(documentoWindow, "ckeditor");
				incorporacionSade.setVisible(false);

			} else if (this.tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO) {
				this.tipoProd = TIPO_PROD_IMPORTADO;
				this.redactarDocumentoWindow.setHeight("450px");
				this.conjuntoTabs.setHeight("305px");
				this.separadorImportado.setHeight("40px");
				this.separadorSade.setHeight("20px");
				this.documentoArchivoTrabajo.setHeight(PXDOS);
				this.documentoArchivoEmbebido.setHeight(PXDOS);
				this.documentoDatosPropios.setHeight(PXDOS);

				setDocumentoWindow((Window) Executions.createComponents(ADJUNTADOC, documentoWindowHbox, hm));
				this.descripcionSadeWindow.setVisible(true);
				incorporacionSade.setVisible(true);
				incorporacionSade.setImage(INCORPORARACT);
				incorporacionSade.setAttribute(INCORPORAR, false);

			} else if (this.tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_TEMPLATE) {
				if (tipoDocumentoTemplate != null) {
					this.documentoTemplateWindowDiv.setVisible(true);
					this.formManager = this.formManagerFact.create(tipoDocumentoTemplate.getIdFormulario());
					this.documentoTemplateWindowHbox.appendChild(this.formManager.getFormComponent());
					this.templateSpace.setHeight("0px");
					this.documentoTemplateWindowDiv.setHeight("370px");

				}

			} else if (this.tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO_TEMPLATE) {

				setDocumentoWindow((Window) Executions.createComponents(ADJUNTADOC, documentoWindowHbox, hm));
				this.descripcionSadeWindow.setVisible(true);
				incorporacionSade.setVisible(true);
				incorporacionSade.setImage(INCORPORARACT);
				incorporacionSade.setAttribute(INCORPORAR, false);
				if (tipoDocumentoTemplate != null) {
					this.documentoTemplateWindowDiv.setVisible(true);
					this.formManager = this.formManagerFact.create(tipoDocumentoTemplate.getIdFormulario());
					this.documentoTemplateWindowHbox.appendChild(this.formManager.getFormComponent());
				}
			}
		}

		prepararComponentesFirma();
		cargarDatosPropios();
		cargarArchivosDeTrabajo();
	}

	/**
	 * smuzychu:
	 * 
	 * Se crea el metodo subsanarListasMalGrabadasSoloreservados() para
	 * Autosubsanar los casos en los cuales las listas de usuarios no se
	 * grabaron bien en LA BPM debido a que la clase UsuarioReducido no
	 * implementaba Serializable Se agrego la serializacion en la version
	 * 2.3.6-RC0 de jar de security
	 * 
	 * FIN
	 */

	private List<String> subsanarListasMalGrabadasSoloreservados(List<Usuario> listaUsuariosReservados) {
		// INICIO FIX: se agrega logica para generar los usuarios faltantes que
		// no se grabaron en la BPM
		List<Usuario> usuariosAArreglar = new ArrayList<>();
		IUsuarioService auxServicioUsuario = (IUsuarioService) SpringUtil.getBean("usuarioService");
		usuariosAArreglar.addAll(listaUsuariosReservados);
		List<String> usuariosArreglados = new ArrayList<>();
		Boolean flagSeDebeArreglar = false;
		for (Usuario auxUSU : listaUsuariosReservados) {
			if (auxUSU.getUsername() == null) {
				flagSeDebeArreglar = true;
			}
		}
		if (flagSeDebeArreglar) {
			if (!usuariosAArreglar.isEmpty()) {
				try {
					Map<String, Usuario> mapaAux = auxServicioUsuario.obtenerMapaUsuarios();
					if (mapaAux != null && mapaAux.size() > 0) {
						List<Usuario> listadeMapa = new ArrayList<>(mapaAux.values());
						for (Usuario usuauxA : usuariosAArreglar) {
							for (Usuario usuAuxM : listadeMapa) {
								if (usuauxA.getEmail().equals(usuAuxM.getEmail())
										&& usuauxA.getCuit().equals(usuAuxM.getCuit())
										&& usuauxA.getCargo().equals(usuAuxM.getCargo())
										&& usuauxA.getSupervisor().equals(usuAuxM.getSupervisor())) {
									usuariosArreglados.add(usuAuxM.getUsername());
								}
							}
						}
					}
				} catch (SecurityNegocioException e) {
					// TODO Auto-generated catch block
					logger.error(e.getMessage(), e);
				}
			}
			if (!usuariosArreglados.isEmpty()) {
				this.processEngine.getExecutionService().setVariable(this.workingTask.getExecutionId(),
						Constantes.VAR_USUARIOS_RESERVADOS, usuariosArreglados);
				return usuariosArreglados;
			} else {
				return new ArrayList<>();
			}
		} else {
			for (Usuario auxUSU : listaUsuariosReservados) {
				usuariosArreglados.add((auxUSU).getUsername());
			}
			return usuariosArreglados;
		}
		// FIN FIX logica para generar los usuarios faltantes que no se grabaron
		// en la BPM
	}

	/**
	 * Obtiene informacion de la actuacion Sade seleccionada
	 */
	private void obtenerDatosSade(String codSade) {

		int contadorCaracteres = 0;
		boolean armado = false;
		StringBuilder actuacion = new StringBuilder();
		String anio;
		StringBuilder numero = new StringBuilder();
		StringBuilder reparticion = new StringBuilder();
		StringBuilder reparticionUsuario = new StringBuilder();

		this.tipoActuacionService = (TipoActuacionService) SpringUtil.getBean("tipoActuacionService");

		this.buscarDocumentosSadeService = (BuscarDocumentosSadeService) SpringUtil
				.getBean("buscarDocumentosSadeService");

		// Armo Actuacion

		while (!armado) {
			if (Character.isLetter(codSade.charAt(contadorCaracteres))) {
				actuacion.append(String.valueOf(codSade.charAt(contadorCaracteres)));
				contadorCaracteres++;
			} else {
				armado = true;
			}
		}

		armado = false;

		// Armo Año
		anio = StringUtils.substring(codSade, contadorCaracteres, contadorCaracteres + 4);
		contadorCaracteres = contadorCaracteres + 4;

		// Armo Numero
		while (!armado) {
			if (!Character.isSpaceChar(codSade.charAt(contadorCaracteres))) {
				numero.append(String.valueOf(codSade.charAt(contadorCaracteres)));
				contadorCaracteres++;
			} else {
				armado = true;
			}
		}
		armado = false;

		// Salteo los espacios en blanco
		while (!armado) {
			if (Character.isSpaceChar(codSade.charAt(contadorCaracteres))) {
				contadorCaracteres++;
			} else {
				armado = true;
			}
		}
		armado = false;

		// Armo Reparticion
		while (!armado) {
			if (contadorCaracteres < codSade.length() && !Character.isSpaceChar(codSade.charAt(contadorCaracteres))) {
				reparticion.append(String.valueOf(codSade.charAt(contadorCaracteres)));
				contadorCaracteres++;
			} else {
				armado = true;
			}
		}
		armado = false;
		contadorCaracteres++;

		// Armo Reparticion Usuario
		while (!armado) {
			if (contadorCaracteres < codSade.length()) {
				reparticionUsuario.append(String.valueOf(codSade.charAt(contadorCaracteres)));
				contadorCaracteres++;
			} else {
				armado = true;
			}
		}

		try {
			ActuacionSADEBean actuacionTemp = obtenerActuacion(actuacion.toString());
			this.datosSadeLabel.setValue(actuacionTemp != null ? actuacionTemp.getCodigo()
					: "" + "-" + anio + "-" + numero.toString() + "-" + reparticion.toString());
			this.datosSadeLabel.setStyle("color:#026290;");

			String numeroFormateado = formatearNumero(numero.toString());
			String numeroDocumento = null;

			numeroDocumento = obtenerActuacion(actuacion.toString()) + anio + "-" + numeroFormateado + "-" + "   -"
					+ reparticion.toString().trim();
			String descripcionTrataD;

			descripcionTrataD = this.buscarDocumentosSadeService.consultaCaratulaSadeExiste(numeroDocumento, codSade);

			if (StringUtils.isNotEmpty(descripcionTrataD)) {
				trataSadeLabel.setValue(descripcionTrataD);

			} else {

				trataSadeLabel
						.setValue(Labels.getLabel("gedo.redactarDocumentoComposer.label.actEncontradaNoDescripcion"));
			}

		} catch (Exception e) {
			logger.error(EXCE + e.getMessage(), e);
			Messagebox.show(Labels.getLabel(Labels.getLabel("gedo.redactarDocumentoComposer.msgbox.errorCargarDoc"),
					new String[] { codSade }), Labels.getLabel(GEDOERROR), Messagebox.OK, Messagebox.ERROR);
		}

	}

	private String formatearNumero(String numero) {
		DecimalFormat format = new DecimalFormat("00000000");
		Integer numeroAformatear = Integer.valueOf(numero);
		return format.format(numeroAformatear);
	}

	private ActuacionSADEBean obtenerActuacion(String codigoActuacion) {
		actuacionesSADE = this.getActuacionesSADE();
		for (ActuacionSADEBean actuacion : actuacionesSADE) {
			if (actuacion.getCodigo().equals(codigoActuacion)) {
				return actuacion;
			}
		}
		return null;
	}

	public List<ActuacionSADEBean> getActuacionesSADE() {
		if (this.actuacionesSADE == null) {
			this.actuacionesSADE = this.tipoActuacionService.obtenerListaTodasLasActuaciones();
		}
		return this.actuacionesSADE;
	}

	/**
	 * Obtiene la instancia de la clase responsable de la generación del
	 * documento
	 */
	private void inicializarGeneradorDocumento() {
		GeneradorDocumentoFactory generadorDocumentoFactory = (GeneradorDocumentoFactory) SpringUtil
				.getBean("generadorDocumentoFactory");
		this.generarDocumentoService = generadorDocumentoFactory.obtenerGeneradorDocumento(this.tipoDocumento);
	}

	/**
	 * Verifica si el tipo de documento tiene datos propios, y carga los valores
	 * en la vista.
	 */
	@SuppressWarnings("unchecked")
	public void cargarDatosPropios() {
		List<DocumentoMetadataDTO> listaDocumentoMetadatos = null;
		try {
			listaDocumentoMetadatos = (List<DocumentoMetadataDTO>) this
					.getVariableWorkFlow(Constantes.VAR_DOCUMENTO_DATA);
		} catch (VariableWorkFlowNoExisteException ve) {
			logger.error("Error al cargar datos propios " + ve.getMessage(), ve);
			listaDocumentoMetadatos = new ArrayList<>();
		}
		if (listaDocumentoMetadatos != null && !listaDocumentoMetadatos.isEmpty()) {
			this.datosPropiosTab.setDisabled(false);
			this.listaDocMetadata = listaDocumentoMetadatos;

			Map<String, Object> hm = new HashMap<>();
			hm.put(DatosPropiosDocumentoComposer.METADATA, this.listaDocMetadata);
			hm.put(DatosPropiosDocumentoComposer.HABILITAR_PANTALLA, false);
			hm.put(DatosPropiosDocumentoComposer.HABILITAR_CIERRE, false);
			hm.put(DatosPropiosDocumentoComposer.TIPO_PROD, this.tipoProd);

			setDocumentoDatosPropios((Window) Executions.createComponents("/inbox/datosPropiosDelDocumento.zul",
					documentoDatosPropios, hm));
		} else {
			this.listaDocMetadata = new ArrayList<>();
		}

	}

	public void cargarArchivosDeTrabajo() {
		HashMap<String, Object> hmat = new HashMap<>();
		hmat.put(ArchivosDeTrabajoComposer.MOSTRAR_UPLOAD, true);
		hmat.put(ArchivosDeTrabajoComposer.WORK_FLOW_ORIGEN, this.workingTask.getExecutionId());
		hmat.put(ArchivoDeTrabajoDTO.ARCHIVOS_DE_TRABAJO, this.listaArhivosDeTrabajo);
		hmat.put(ArchivosDeTrabajoComposer.TIPO_PROD, this.tipoProd);
		setDocumentoArchivoTrabajo(
				(Window) Executions.createComponents("/archivosDeTrabajo.zul", documentoArchivoTrabajo, hmat));
	}

	/**
	 * Carga la ventana de previsualización del documento en confección.
	 * 
	 * @throws InterruptedException
	 */
	public void onClick$previsualizar() {
		Map<String, Object> datos = new HashMap<>();
		datos.put(FUNCION, "previsualizar");
		datos.put(DATOS, null);
		enviarBloqueoPantalla(datos);
	}

	public void previsualizar() throws InterruptedException {

		if (this.tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO) {
			previsualizarNoTemplate();
		} else if (this.tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO_TEMPLATE) {
			previsualizarImportadoTemplate();
		} else {
			PrevisualizarLibreTemplate();
		}
	}

	private void previsualizarNoTemplate() throws InterruptedException {
		try {
			int maxSizePrevisualizacion = Integer.parseInt(maximoPrevisualizacion);
			int maxSizePrevisualizacionByteslength = maxSizePrevisualizacion * Constantes.FACTOR_CONVERSION;
			int tamanoDocumentoAFirmar = documentoNoTemplateByte.length + Constantes.TAMANO_HOJA_FIRMA;
			if (tamanoDocumentoAFirmar > maxSizePrevisualizacionByteslength) {
				Messagebox.show(Labels.getLabel("gedo.importarDocumento.tamanoMaximo"), Labels.getLabel(GENERR),
						Messagebox.OK, Messagebox.INFORMATION);
				return;
			}
			byte[] contenidoTemporal = this.pdfService.crearDocumentoPDFPrevisualizacion(nombreArchivoLabel.getValue(),
					documentoNoTemplateByte, this.tipoDocumento);

			PrevisualizacionDocumento.window(contenidoTemporal, Boolean.TRUE, this.self);

		} catch (PDFConversionException e) {
			logger.error("Error en conversión de archivo pdf ", e);
			Messagebox.show(Labels.getLabel("gedo.error.coversionAPDF"), Labels.getLabel(GEDOERROR), Messagebox.OK,
					Messagebox.ERROR);
		} catch (Exception e) {
			logger.error("Error en previsualización de archivo pdf ", e);
			Messagebox.show(Labels.getLabel(ERRDOCADJ), Labels.getLabel(GEDOERROR), Messagebox.OK, Messagebox.ERROR);
		} finally {
			Clients.clearBusy();
		}
	}

	private void PrevisualizarLibreTemplate() throws InterruptedException {

		ExternalFormularioService formularioService = (ExternalFormularioService) SpringUtil
				.getBean("externalFormularioService");
		String sdata;
		try {

			if (this.tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_TEMPLATE) {

				FormularioDTO form = formularioService
						.buscarFormularioPorNombre(this.tipoDocumentoTemplate.getIdFormulario());
				byte[] bdata = this.procesamientoTemplate.procesarTemplate(this.template, this.formManager.getValues(),
						form);

				sdata = this.transformarInToString(new ByteArrayInputStream(bdata));

			} else {
				sdata = this.ckeditor.getValue();
			}

			byte[] contenidoPDF;

			if (this.tipoDocumento.getEsComunicable()) {

				RequestGenerarDocumento request = new RequestGenerarDocumento();
				request.setListaUsuariosDestinatarios(this.listaUsuariosDestinatarios);
				request.setListaUsuariosDestinatariosCopia(this.listaUsuariosDestinatariosCopia);
				request.setListaUsuariosDestinatariosExternos(this.listaUsuariosExternos);
				request.setIdComunicacionRespondida(this.idComunicacionRespondida);
				request.setIdDestinatario(this.idDestinatario);
				request.setCodigoReparticion((String) Executions.getCurrent().getDesktop().getSession()
						.getAttribute(Constantes.SESSION_USER_REPARTICION));
				request.setMotivo(this.motivoTb.getValue());

				contenidoPDF = pdfService.generarPDFCCOO(sdata, motivoTb.getValue(), tipoDocumento, request);
			} else {
				contenidoPDF = this.pdfService.generarPDF(sdata, motivoTb.getValue(), tipoDocumento);
			}

			byte[] contenidoPreviewPdf = pdfService.crearDocumentoPDFPrevisualizacion(contenidoPDF, tipoDocumento);
			PrevisualizacionDocumento.window(contenidoPreviewPdf, Boolean.FALSE, this.self);

		} catch (WrongValueException e) {
			logger.error(ERRVER, e.getMessage());
			throw e;
		} catch (ClavesFaltantesException e) {
			logger.error(ERRVER, e);
			Messagebox.show(Labels.getLabel(CLAVEFALTANTE), Labels.getLabel(GEDOERROR), Messagebox.OK,
					Messagebox.ERROR);
		} catch (Exception e) {
			logger.error("Error en creación de archivo pdf ", e);
			Messagebox.show(Labels.getLabel("gedo.previsualizar.error"), Labels.getLabel(GEDOERROR), Messagebox.OK,
					Messagebox.ERROR);
		} finally {
			Clients.clearBusy();
		}
	}

	public void previsualizarImportadoTemplate() throws InterruptedException {
		try {
			byte[] contenidoImportado = this.pdfService.crearDocumentoPDFPrevisualizacion(nombreArchivoLabel.getValue(),
					documentoNoTemplateByte, this.tipoDocumento);
			FormularioDTO form = this.pdfService.obtenerFormularioControlado(this.tipoDocumentoTemplate.getIdFormulario());

			byte[] contenidoTemplate = this.procesamientoTemplate.procesarTemplate(this.template,
					this.formManager.getValues(), form);

			String tipoContenidoTemporal = UtilitariosServicios.obtenerTipoContenido(contenidoTemplate);
			String motivo = this.motivoTb.getValue();
			if (motivo == null) {
				motivo = "";
			}

			if (this.tipoDocumento.getEsComunicable()) {
				RequestGenerarDocumento request = new RequestGenerarDocumento();
				request.setListaUsuariosDestinatarios(this.listaUsuariosDestinatarios);
				request.setListaUsuariosDestinatariosCopia(this.listaUsuariosDestinatariosCopia);
				request.setListaUsuariosDestinatariosExternos(this.listaUsuariosExternos);
				request.setIdComunicacionRespondida(this.idComunicacionRespondida);
				request.setIdDestinatario(this.idDestinatario);
				request.setCodigoReparticion((String) Executions.getCurrent().getDesktop().getSession()
						.getAttribute(Constantes.SESSION_USER_REPARTICION));
				request.setMotivo(this.motivoTb.getValue());

				contenidoImportado = this.pdfService.adicionarNuevaPaginaVisualizacionImpTemp(contenidoImportado,
						contenidoTemplate, tipoContenidoTemporal, this.tipoDocumento, motivo, request);
			} else {
				contenidoImportado = this.pdfService.adicionarNuevaPaginaVisualizacionImpTemp(contenidoImportado,
						contenidoTemplate, tipoContenidoTemporal, this.tipoDocumento, motivo);
			}

			PrevisualizacionDocumento.window(contenidoImportado, Boolean.TRUE, this.self);

		} catch (WrongValueException e) {
			logger.error(ERRVER, e.getMessage());
			throw e;
		} catch (PDFConversionException e) {
			logger.error("Error en conversión de archivo pdf ", e);
			Messagebox.show(Labels.getLabel("gedo.error.coversionAPDF"), Labels.getLabel(GEDOERROR), Messagebox.OK,
					Messagebox.ERROR);
		} catch (Exception e) {
			logger.error("Error en previsualización de archivo pdf ", e);
			throw new WrongValueException(this.documentoWindow.getFellow(UPLOAD_DOCUMENTO), Labels.getLabel(ERRDOCADJ));
		} finally {
			Clients.clearBusy();
		}
	}

	private String transformarInToString(InputStream co) {

		StringBuilder sb = new StringBuilder();
		String line;

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(co, "UTF-8"));
			while ((line = reader.readLine()) != null) {
				sb.append(line).append("\n");
			}
		} catch (UnsupportedEncodingException uee) {
			logger.error("Encoding no soportado", uee);
		} catch (IOException ioe) {
			logger.error("No se pudo cerrar el archivo", ioe);
		} finally {
			try {
				co.close();
			} catch (IOException ioe) {
				logger.error("No se pudo cerrar el archivo", ioe);
			}
		}

		return sb.toString();
	}

	private void checkConstraints() {
		if (StringUtils.isEmpty(motivoTb.getValue())) {
			throw new WrongValueException(this.motivoTb, Labels.getLabel("gedo.producirDocumento.error.motivo"));
		}
	}

	/**
	 * Carga al workflow las variables comunes a los diferentes flujos de
	 * ejecución.
	 */
	private void guardarVariablesComunesWorkflow() {
		this.setVariableWorkFlow(Constantes.VAR_MOTIVO, motivoTb.getValue());
		this.setVariableWorkFlow(Constantes.VAR_DOCUMENTO_DATA, this.listaDocMetadata);
		this.setVariableWorkFlow(Constantes.VAR_SOLICITUD_ENVIO_MAIL, solicitudEnvioCorreo.isChecked());

	}

	/**
	 * 
	 * @throws Exception
	 */
	public void onClick$firmarButton() throws Exception {

		this.checkConstraints();

		if (this.tipoDocumento.getEsComunicable()) {
			validarDestinatario();
		}

		this.produccionTab.setSelected(true);

		if (this.tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_LIBRE) {
			try {
				validarTemplate();
			} catch (InterruptedException e) {
				Messagebox.show(e.getMessage(), Labels.getLabel(QUESTION), Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
		} else if (this.tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO
				|| this.tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO_TEMPLATE) {
			validarNoTemplate();
		}

		try {

			// Generar Documento PDF
			this.generarDocumentoPDF();

			// Persistir Documento Template
			if (this.tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_TEMPLATE) {
				this.persistirDocumentoTemplate();
			}

		} catch (WrongValueException e) {
			logger.error(FIRMAERR, e.getMessage());
			Clients.clearBusy();
			throw e;
		} catch (ClavesFaltantesException e) {
			logger.error(FIRMAERR, e);
			Messagebox.show(Labels.getLabel(CLAVEFALTANTE), Labels.getLabel(GEDOERROR), Messagebox.OK,
					Messagebox.ERROR);
			Clients.clearBusy();
		} catch (AlfrescoException e) {
			logger.error("Error al subir archivo temporal a WebDAv", e);
			Messagebox.show(Labels.getLabel("gedo.error.subirTemporalAlfresco"), Labels.getLabel(GEDOERROR),
					Messagebox.OK, Messagebox.ERROR);
			this.cierreVentana(true);
		} catch (Exception e) {
			logger.error("Error al enviar a firmar", e);
			Messagebox.show(Labels.getLabel("gedo.error.enviarAFirmar"), Labels.getLabel(GEDOERROR), Messagebox.OK,
					Messagebox.ERROR);
			this.cierreVentana(true);
		}

		// valido q si el documento tiene metadatos obligatorios, estos esten
		// completos
		if (this.listaDocMetadata != null && !this.listaDocMetadata.isEmpty()) {
			for (DocumentoMetadataDTO doc : this.listaDocMetadata) {
				if (doc.isObligatoriedad() && StringUtils.isEmpty(doc.getValor())) {
					Messagebox.show(Labels.getLabel(OBLDATOS, new String[] { this.tipoDocumento.getAcronimo() }),
							Labels.getLabel(GENERR), Messagebox.OK, Messagebox.ERROR);
					return;
				}
			}
		}

		// valido que
		String obj = (String) this.processEngine.getExecutionService().getVariable(this.workingTask.getExecutionId(),
				Constantes.VAR_USUARIO_DESTINATARIO_LICENCIA);

		Map<String, Object> mapa = new HashMap<>();
		Usuario userDestinatarioLicencia = null;
		if (obj != null) {
			userDestinatarioLicencia = this.usuarioService.obtenerUsuario(obj);
		}
		mapa.put(Constantes.VAR_USUARIO_DESTINATARIO_LICENCIA, userDestinatarioLicencia);
		this.enviarAFirmarWindow = (Window) Executions.createComponents("enviarAFirmar.zul", this.self, mapa);
		this.enviarAFirmarWindow.setClosable(true);
		try {
			this.enviarAFirmarWindow.setPosition(CENTER);
			this.enviarAFirmarWindow.doModal();
		} catch (SuspendNotAllowedException e) {
			logger.error(EXCE + e.getMessage(), e);
		}
	}

	public void cargarDatosFirmar() throws InterruptedException {

		this.tareaAsignada = Constantes.TASK_FIRMAR_DOCUMENTO;
		firmoYoMismo = false;
		Usuario usuarioReducido = this.userFirmante.getValue();
		Usuario firmante = getUsuarioService().obtenerUsuario(usuarioReducido.getUsername());
		this.setBloqueante(true);
		datos.put(FUNCION, VALIDAAPODER);
		datos.put(TIPODOC, this.tipoDocumento);
		datos.put(DATOS, firmante);
		if (this.mensajeDestinatarioLicencia) {
			Messagebox.show(Labels.getLabel("gedo.licencia.envioFirmar"), Labels.getLabel(GENERR), Messagebox.OK,
					Messagebox.INFORMATION, new EventListener() {
						public void onEvent(Event paramEvent) throws Exception {
							verificarObligatoriedadExtensiones(bloqueante);
						}

					});

		} else {
			this.verificarObligatoriedadExtensiones(this.bloqueante);
		}

	}

	/**
	 * TODO es la misma lógica que en el botón de selfFirmarButton. Hacer un
	 * método a parte e invocarlo desde estas 2 llamadas
	 * método:validarPermisosReparticion()
	 * @throws Exception 
	 */
	public void onClick$selfImportarDocumentoButton() throws InterruptedException {

		validarPermisoReparticion();
		if (this.puedoFirmar) {
			this.checkConstraints();
			if (this.tipoDocumento.getEsComunicable()) {
				validarDestinatario();
			}
			// No firmo yo mismo pero importo yo mismo
			this.firmoYoMismo = true;
			enviarAFirmar();
		}
	}

	/**
	 * Evento sobre el boton de "Incorporar Documento SADE" que permite buscar e
	 * incorporar un documento SADE al documento GEDO.
	 */
	public void onClick$incorporacionSade() {
		Boolean estadoBotonIncorporacionSADE = (Boolean) incorporacionSade.getAttribute(INCORPORAR);

		if (estadoBotonIncorporacionSADE == false) {
			incorporarDocumentoSade();
		} else {
			cancelarDocumentoSade();
		}
	}

	/**
	 * Creacion del window de "Incorporar Documento SADE.
	 */
	private void incorporarDocumentoSade() {

		this.incorporarSadeWindow = (Window) Executions.createComponents("inbox/incorporarSade.zul", this.self, null);
		this.incorporarSadeWindow.setVisible(false);
		this.incorporarSadeWindow.setClosable(true);
		this.incorporarSadeWindow.setPosition(CENTER);
		this.incorporarSadeWindow.doModal();

		incorporacionSade.setAttribute(INCORPORAR, true);

	}

	/**
	 * Eliminacion del window de "Incorporar Documento SADE.
	 */
	private void cancelarDocumentoSade() {
		incorporacionSade.setImage(INCORPORARACT);
		incorporacionSade.setAttribute(INCORPORAR, false);
		if (documentoSadeWindow != null) {
			documentoSadeWindowHbox.removeChild(documentoSadeWindow);
			documentoSadeWindow = null;
		}

	}

	/**
	 * Validaciones de adoderamiento y usuarios que pertenecen a la misma
	 * repartición.
	 * 
	 * @throws InterruptedException
	 */
	public void validarUsuarios(Usuario usuarioAValidar, TipoDocumentoDTO tipoDoc) throws InterruptedException {
		super.validarApoderamiento(usuarioAValidar, tipoDoc);
	}

	/**
	 * Realiza las actividades necesarias para enviar a firmar el documento.
	 * 
	 * @param usuarioActual
	 *            : Flag que indica si se envía a firmar a otro usuario o lo
	 *            firma el usuario acutal.
	 * @throws InterruptedException
	 */
	private void enviarAFirmar() throws InterruptedException {

		/**
		 * TODO Los métodos de validarTemplate y validarNoTemplate no deberían
		 * devolver una excepción ya que sino la atrapa el Exception. Para que
		 * eso no pase se debe sacar dentro del try/catch y duplicar las
		 * validaciones si un documento es TEMPLATE o NO TEMPLATE.
		 */

		datos.put(FUNCION, "validarReparticiones");
		datos.put(TIPODOC, this.tipoDocumento);
		if (this.tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_LIBRE) {
			try {
				validarTemplate();
			} catch (InterruptedException e) {
				Messagebox.show(e.getMessage(), Labels.getLabel(QUESTION), Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
		} else if (this.tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO
				|| this.tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO_TEMPLATE) {
			validarNoTemplate();
		} else {
			// LOGICA DE VALIDACION PARA EL NUEVO TIPO DE DOCUMENTO TEMPLATE
			// LA VALIDACION SE PRODUCE AL MOMENTO DE PEDIRLE AL MANAGER QUE
			// GRABE.
		}

		if (this.tipoDocumento.getEsFirmaConjunta()) {
			procesarFaltanRevisor();
		} else {
			procesarEnvioAFirmar();
		}
	}

	private void validarDestinatario() {

		if (this.listaUsuariosDestinatarios.isEmpty() && this.listaUsuariosExternos.isEmpty()) {
			throw new WrongValueException(this.definirDestinatariosButton,
					Labels.getLabel("ccoo.definirDestinatario.faltaDestinatario"));
		}
	}
	
	
	private Usuario obtenerFirmanteRevisor(String tipoUsuario, boolean estado) {
		
		Usuario usuareBuscado=null;
		if(tipoUsuario.equals(TIPO_USUARIO_REVISOR)) {
			List<Usuario> usuariosRevisores = firmaConjuntaService.buscarRevisoresPorEstado(this.workingTask.getExecutionId(), estado);
			if(usuariosRevisores!=null && !usuariosRevisores.isEmpty()) {
				usuareBuscado = usuariosRevisores.get(0);
			}
		}

		if(tipoUsuario.equals(TIPO_USUARIO_FIRMANTE)) {
			List<Usuario> usuariosFirmantes = firmaConjuntaService.buscarFirmantesPorEstado(this.workingTask.getExecutionId(), estado);
			if(usuariosFirmantes!=null && !usuariosFirmantes.isEmpty()) {
				usuareBuscado = usuariosFirmantes.get(0);
			}
		}

		return usuareBuscado;
	}
	
	
	

	private void procesarFaltanRevisor() throws InterruptedException {

		Boolean faltanUsuariosRevisores = Boolean.FALSE;

		for (Usuario usuarioFirmante : usuariosFirmantes) {
			if (usuarioFirmante.getUsuarioRevisor() == null || usuarioFirmante.getUsuarioRevisor().trim().isEmpty()) {
				faltanUsuariosRevisores = Boolean.TRUE;
				break;
			}
		}

		if (faltanUsuariosRevisores.equals(Boolean.TRUE)) {
			this.formManager.getValues();
			Messagebox.show(Labels.getLabel("gedo.firmaConjunta.faltaUsuarioRevisorAlEnviarAFirmar"),
					Labels.getLabel("gedo.general.question"), Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener() {
						public void onEvent(Event evt) throws InterruptedException {
							if ("onYes".equals(evt.getName())) {
								onAgregarUsuariosFirmaConjunta();
							}

							if ("onNo".equals(evt.getName())) {
								//TODO: Se comenta envio a firma hasta que confirmen que se puede firmar si los firmantes no tienen revisores
								procesarEnvioAFirmar();
//								cierreVentana(false);
							}
						}
					});
		} else {
			procesarEnvioAFirmar();
		}

	}

	private void procesarEnvioAFirmar() throws InterruptedException {

		try {
			
			
			
			if (this.tipoDocumento.getEsComunicable()) {

				this.setVariableWorkFlow(Constantes.VAR_USUARIOS_DESTINATARIOS, this.listaUsuariosDestinatarios);
				this.setVariableWorkFlow(Constantes.VAR_USUARIOS_DESTINATARIOS_COPIA,
						this.listaUsuariosDestinatariosCopia);
				this.setVariableWorkFlow(Constantes.VAR_USUARIOS_DESTINATARIOS_COPIA_OCULTA,
						this.listaUsuariosDestinatariosCopiOculta);
				this.setVariableWorkFlow(Constantes.VAR_USUARIOS_DESTINATARIOS_EXTERNOS, this.listaUsuariosExternos);
				this.setVariableWorkFlow(Constantes.VAR_MENSAJE_DESTINATARIO, this.mensajeDestinatario);
				this.setVariableWorkFlow(Constantes.VAR_ID_COMUNICACION_RESPONDIDA,
						this.idComunicacionRespondida != null ? this.idComunicacionRespondida
								: new ComunicacionDTO().getId());
				this.setVariableWorkFlow(Constantes.VAR_ID_DESTINATARIO_COMUNICACION, this.idDestinatario);
			}

			// Generar Documento PDF
			this.generarDocumentoPDF();

			// Persistir Documento Template
			if (this.tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_TEMPLATE
					|| this.tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO_TEMPLATE) {
				this.persistirDocumentoTemplate();
			}

			// Actualizo los avisos de la solicitud
			this.actualizarEstadoAvisoFirma();

			// Prepara las variables de JBPM para FIRMAR
			FirmanteDTO firmanteActual = this.prepararVariablesWorkFlowFirmar(this.firmoYoMismo);

			// Se envia con null, por que al enviar la tarea a firmar, esta no
			// tiene mensaje
			this.historialService.actualizarHistorial(this.workingTask.getExecutionId());
			this.workingTask.setProgress(100);

			// Ejecuto Transicion de estado de JBPM
			// cuando es frma conjunta: el usuario asignado debe ser el revisor pues la tarea quedara en revision
			String firmante = this.getCurrentUser();
			String usuario = this.getCurrentUser();
			if (tipoDocumento.getEsFirmaConjunta()){
				
				if (firmanteActual.getUsuarioRevisor() != null) {
					Usuario usRevisor = obtenerFirmanteRevisor(TIPO_USUARIO_REVISOR, false);
					if(usRevisor!=null) {
						usuario=usRevisor.getUsername();
					}else {
						usRevisor = obtenerFirmanteRevisor(TIPO_USUARIO_REVISOR, true);
						if(usRevisor!=null) {
							usuario=usRevisor.getUsername();
						}
					}
					
					Usuario usFirmante = obtenerFirmanteRevisor(TIPO_USUARIO_FIRMANTE, false);
					if(usFirmante !=null && usRevisor==null){
						usuario=usFirmante.getUsername();
					}
					
					setVariableWorkFlow(Constantes.REVISAR_DOCUMENTO_CON_FIRMA_CONJUNTA, usRevisor!=null);
					
				} else {
					setVariableWorkFlow(Constantes.REVISAR_DOCUMENTO_CON_FIRMA_CONJUNTA, false);
					setVariableWorkFlow(Constantes.VAR_USUARIO_FIRMANTE, firmanteActual.getUsuarioFirmante());
					setVariableWorkFlow(Constantes.VAR_USUARIO_REVISOR, firmanteActual.getUsuarioRevisor());
				}
				setVariableWorkFlow(Constantes.REVISAR_FIRMA, false);
				setVariableWorkFlow(Constantes.REVISAR_DOCUMENTO_CON_CERTIFICADO, false);
				this.signalExecution(Constantes.TRANSICION_USO_PORTAFIRMA, usuario);
			}else {
				signalExecution(this.getFirmarTransitionName(), firmante);
			}

			
			// Muestro mensaje PF si corresponde.
			if (this.workingTask.getExecutionId() != null) {
				ProcessInstance pInstance = this.processEngine.getExecutionService()
						.findProcessInstanceById(this.workingTask.getExecutionId());
				Set<String> actividades = pInstance.findActiveActivityNames();
				boolean portaFirma = actividades.contains(Constantes.ACT_ENVIAR_PORTAFIRMA);
				if (portaFirma) {
					Clients.clearBusy();
					Messagebox.show(Labels.getLabel("gedo.firmarDocumento.envioAPortaFirma"), Labels.getLabel(GENERR),
							Messagebox.OK, Messagebox.INFORMATION);
				}
			}

			// Muestro mensaje de Envio de mail si corresponde.
			this.mostrarMensajeEnvioMail();

			// Muestro mensaje de inicio de Firma si corresponde.
			if (!firmoYoMismo) {
				Messagebox.show(Labels.getLabel("gedo.general.inicioProcesoFirma"), Labels.getLabel(GENERR),
						Messagebox.OK, Messagebox.INFORMATION);
			}
			this.cierreVentana(false);

		} catch (WrongValueException e) {
			logger.error(FIRMAERR, e.getMessage());
			Clients.clearBusy();
			throw e;
		} catch (ClavesFaltantesException e) {
			logger.error(FIRMAERR, e);
			Messagebox.show(Labels.getLabel(CLAVEFALTANTE), Labels.getLabel(GEDOERROR), Messagebox.OK,
					Messagebox.ERROR);
			Clients.clearBusy();
		} catch (AlfrescoException e) {
			logger.error("Error al subir archivo temporal a WebDAv", e);
			Messagebox.show(Labels.getLabel("gedo.error.subirTemporalAlfresco"), Labels.getLabel(GEDOERROR),
					Messagebox.OK, Messagebox.ERROR);
			this.cierreVentana(true);
		} catch (Exception e) {
			logger.error("Error al enviar a firmar", e);
			Messagebox.show(Labels.getLabel("gedo.error.enviarAFirmar"), Labels.getLabel(GEDOERROR), Messagebox.OK,
					Messagebox.ERROR);
			this.cierreVentana(true);
		}
	}

	private void persistirDocumentoTemplate() {

		DocumentoTemplateDTO documentoTemplateP = new DocumentoTemplateDTO();

		DocumentoTemplatePKDTO documentoTemplatePK = new DocumentoTemplatePKDTO();

		documentoTemplatePK
				.setIdTipoDocumento(this.tipoDocumentoTemplate.getTipoDocumentoTemplatePK().getIdTipoDocumento());
		documentoTemplatePK.setVersion(this.tipoDocumentoTemplate.getTipoDocumentoTemplatePK().getVersion());
		documentoTemplatePK.setWorkflowId(this.getWorkingTask().getExecutionId());

		documentoTemplateP.setDocumentoTemplatePK(documentoTemplatePK);
		documentoTemplateP.setIdTransaccion(this.idTransaccion);
		documentoTemplateP.setTipoDocumentoTemplate(this.tipoDocumentoTemplate);

		try {
			documentoTemplateService.save(documentoTemplateP);
		} catch (Exception e) {
			logger.error("No se ha podido persistir el Documento Template: " + this.getWorkingTask().getExecutionId()
					+ " - " + this.idTransaccion + " - " + e.getMessage(), e.getMessage());
			throw e;
		}
	}

	private void generarDocumentoPDF() throws Exception {

		if (this.tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO) {
			this.generarDocumentoPDFNoTemplate();
		} else if (this.tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_LIBRE
				|| this.tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_TEMPLATE) {
			this.generarDocumentoPDFTemplateLibre();
		} else if (this.tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO_TEMPLATE) {
			this.generarDocumentoPDFImportadoTemplate();
		}

	}

	private void generarDocumentoPDFTemplateLibre() throws IOException {

		RequestGenerarDocumento request = new RequestGenerarDocumento();

		if (this.tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_TEMPLATE) {
			this.idTransaccion = this.formManager.saveValues();

			request.setCamposTemplate((HashMap<String, Object>) this.formManager.getValues());
		} else {
			request.setData(this.ckeditor.getValue().getBytes());
		}

		Integer numeroFirmas = Integer.valueOf(1);
		if (this.tipoDocumento.getEsFirmaConjunta()) {
			numeroFirmas = this.usuariosFirmantes.size();
		}

		if (this.tipoDocumento.getEsComunicable()) {
			request.setMensajeDestinatario(this.mensajeDestinatario);
			request.setListaUsuariosDestinatarios(this.listaUsuariosDestinatarios);
			request.setListaUsuariosDestinatariosCopia(this.listaUsuariosDestinatariosCopia);
			request.setListaUsuariosDestinatariosCopiaOculta(this.listaUsuariosDestinatariosCopiOculta);
			request.setListaUsuariosDestinatariosExternos(this.listaUsuariosExternos);
			request.setIdComunicacionRespondida(this.idComunicacionRespondida);
			request.setIdDestinatario(this.idDestinatario);
			request.setCodigoReparticion((String) Executions.getCurrent().getDesktop().getSession()
					.getAttribute(Constantes.SESSION_USER_REPARTICION));
		}

		request.setTipoDocumentoGedo(this.tipoDocumento);
		request.setMotivo(this.motivoTb.getValue());

		// WEBDAV
		request.setNombreArchivo((String) this.getVariableWorkFlow(Constantes.VAR_ARCHIVO_TEMPORAL_FIRMA));

		// Workflow ID
		request.setWorkflowId(this.workingTask.getExecutionId());

		request.setListaArchivosEmbebidos(this.listaArchivosEmbebidos);
		this.generarDocumentoService.generarDocumentoPDF(request, numeroFirmas, true);
		if (tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_TEMPLATE) {
			this.documentoTemplateLibreByte = request.getDataOriginal();
		}
	}

	Integer numeroFirmas = 0;

	private void generarDocumentoPDFNoTemplate() throws Exception {

		String tipoArchivo = FilenameUtils.getExtension(this.nombreArchivoLabel.getValue()).toLowerCase();

		if (this.tipoDocumento.getEsFirmaConjunta()) {
			numeroFirmas = this.usuariosFirmantes.size();
		} else {
			numeroFirmas = Integer.valueOf(1);
		}
		RequestGenerarDocumento request = new RequestGenerarDocumento();
		request.setTipoDocumentoGedo(this.tipoDocumento);
		request.setMotivo(this.motivoTb.getValue());
		request.setTipoArchivo(tipoArchivo);
		request.setWorkflowId(this.workingTask.getExecutionId());
		request.setData(this.documentoNoTemplateByte);
		if (this.tipoDocumento.getPermiteEmbebidos()) {
			request.setListaArchivosEmbebidos(this.listaArchivosEmbebidos);
		}

		if (this.tipoDocumento.getEsComunicable()) {
			request.setMensajeDestinatario(this.mensajeDestinatario);
			request.setListaUsuariosDestinatarios(this.listaUsuariosDestinatarios);
			request.setListaUsuariosDestinatariosCopia(this.listaUsuariosDestinatariosCopia);
			request.setListaUsuariosDestinatariosCopiaOculta(this.listaUsuariosDestinatariosCopiOculta);
			request.setListaUsuariosDestinatariosExternos(this.listaUsuariosExternos);
			request.setIdComunicacionRespondida(this.idComunicacionRespondida);
			request.setIdDestinatario(this.idDestinatario);
			request.setCodigoReparticion((String) Executions.getCurrent().getDesktop().getSession()
					.getAttribute(Constantes.SESSION_USER_REPARTICION));
		}

		// WEBDAV
		request.setNombreArchivo((String) this.getVariableWorkFlow(Constantes.VAR_ARCHIVO_TEMPORAL_FIRMA));

		this.generarDocumentoService.generarDocumentoPDF(request, numeroFirmas, true);
		if (!Constantes.GESTOR_DOCUMENTAL_FILENET.equals(gestorDocumental)) {
			this.setVariableWorkFlow(Constantes.VAR_ARCHIVO_TEMPORAL_FIRMA, request.getNombreArchivo());
		}
	}

	private void generarDocumentoPDFImportadoTemplate() throws Exception {

		RequestGenerarDocumento request = new RequestGenerarDocumento();

		String tipoArchivo = FilenameUtils.getExtension(this.nombreArchivoLabel.getValue()).toLowerCase();
		if (this.tipoDocumento.getEsFirmaConjunta()) {
			numeroFirmas = this.usuariosFirmantes.size();
		} else {
			numeroFirmas = Integer.valueOf(1);
		}

		if (this.tipoDocumento.getPermiteEmbebidos()) {
			request.setListaArchivosEmbebidos(this.listaArchivosEmbebidos);
		}

		request.setTipoDocumentoGedo(this.tipoDocumento);
		request.setMotivo(this.motivoTb.getValue());
		request.setTipoArchivo(tipoArchivo);
		request.setData(this.documentoNoTemplateByte);
		request.setWorkflowId(this.workingTask.getExecutionId());

		// WEBDAV
		request.setNombreArchivo((String) this.getVariableWorkFlow(Constantes.VAR_ARCHIVO_TEMPORAL_FIRMA));

		this.idTransaccion = this.formManager.saveValues();
		request.setCamposTemplate((HashMap<String, Object>) this.formManager.getValues());

		if (this.tipoDocumento.getEsComunicable()) {
			request.setMensajeDestinatario(this.mensajeDestinatario);
			request.setListaUsuariosDestinatarios(this.listaUsuariosDestinatarios);
			request.setListaUsuariosDestinatariosCopia(this.listaUsuariosDestinatariosCopia);
			request.setListaUsuariosDestinatariosCopiaOculta(this.listaUsuariosDestinatariosCopiOculta);
			request.setListaUsuariosDestinatariosExternos(this.listaUsuariosExternos);
			if (this.idComunicacionRespondida != null) {
				request.setIdComunicacionRespondida(this.idComunicacionRespondida);
			} else {
				request.setComunicacionRespondida(this.comunicacion);
			}
			request.setIdDestinatario(this.idDestinatario);
			request.setCodigoReparticion((String) Executions.getCurrent().getDesktop().getSession()
					.getAttribute(Constantes.SESSION_USER_REPARTICION));
		}

		this.generarDocumentoService.generarDocumentoPDF(request, numeroFirmas, true);

		// WEBDAV
		this.setVariableWorkFlow(Constantes.VAR_ARCHIVO_TEMPORAL_FIRMA, request.getNombreArchivo());

		this.documentoTemplateLibreByte = request.getDataOriginal();
	}

	public void datosPropios() throws InterruptedException {

		if (this.listaDocMetadata != null && this.listaDocMetadata.size() > 0) {
			for (DocumentoMetadataDTO documentoMetadata : listaDocMetadata) {
				if (documentoMetadata.isObligatoriedad() && StringUtils.isEmpty(documentoMetadata.getValor())) {
					Clients.clearBusy();
					throw new InterruptedException(
							Labels.getLabel("gedo.iniciarDocumento.warnings.faltaDatosPropiosObligatorio"));
				}
			}
		}
	}

	public void onClick$revisionButton() throws InterruptedException, UnsupportedEncodingException {

		this.checkConstraints();
		this.produccionTab.setSelected(true);
		if (this.tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_LIBRE) {
			try {
				validarTemplate();
			} catch (InterruptedException e) {
				Messagebox.show(e.getMessage(), Labels.getLabel(QUESTION), Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
		} else if (this.tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO
				|| this.tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO_TEMPLATE) {
			validarNoTemplate();
			Events.sendEvent(new Event(Events.ON_NOTIFY, this.documentoWindow));
		} else {
			// VALIDACIONES PARA EL NUEVO TIPO DE DOCUMENTO TEMPLATE
			// LA VALIDACION SE PRODUCE AL MOMENTO DE PEDIRLE AL MANAGER QUE
			// GRABE.
		}

		try {
			// Realizo operaciones extras si el tipo de Documento es Template
			if (this.tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_TEMPLATE
					|| this.tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO_TEMPLATE) {
				enviarARevisarTemplate();
			}
		} catch (WrongValueException e) {
			logger.error("Error al enviar a revisar - ", e);
			Clients.clearBusy();
			throw e;
		} catch (ClavesFaltantesException e) {
			logger.error("Error al enviar a revisar - ", e);

			Messagebox.show(Labels.getLabel(CLAVEFALTANTE), Labels.getLabel(GEDOERROR), Messagebox.OK,
					Messagebox.ERROR);
			Clients.clearBusy();
			return;
		} catch (Exception e) {
			logger.error("Error al enviar a revisar en redactar", e);
			Messagebox.show(Labels.getLabel("gedo.error.enviarARevisar"), Labels.getLabel(GEDOERROR), Messagebox.OK,
					Messagebox.ERROR);
			this.cierreVentana(true);
		}

		try {
			if (tipoDocumento.getEsFirmaConjunta()) {
				validarUsuariosFirmantes();
				validarDatosPropios();
				return;
			} else {
				validarDatosPropios();
			}
		} catch (InterruptedException ex) {
			Messagebox.show(Labels.getLabel("gedo.general.warnings.faltanUsuariosFirmantes"),
					Labels.getLabel("gedo.general.question"), Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
					new EventListener() {
						public void onEvent(Event evt) throws InterruptedException {
							switch (((Integer) evt.getData()).intValue()) {
							case Messagebox.YES:
								try {
									validarDatosPropios();
									break;
								} catch (Exception e) {
									logger.error(EXCE + e.getMessage(), e);
								}
								break;
							case Messagebox.NO:
								onAgregarUsuariosFirmaConjunta();
								return;
							}
						}
					});
			return;
		} catch (Exception e) {
			logger.error(EXCE + e.getMessage(), e);
		}

	}

	public void mostrarEnviarARevisar() throws InterruptedException {
		this.enviarARevisarWindow = (Window) Executions.createComponents("enviarARevisar.zul", this.self, null);
		this.enviarARevisarWindow.setClosable(true);
		try {
			this.enviarARevisarWindow.setPosition(CENTER);
			this.enviarARevisarWindow.doModal();
		} catch (SuspendNotAllowedException e) {
			logger.error(EXCE + e.getMessage(), e);
		}

	}

	public void validarUsuariosFirmantes() throws InterruptedException, Exception {
		if (this.usuariosFirmantes == null || this.usuariosFirmantes.size() <= 1) {
			Clients.clearBusy();
			throw new InterruptedException(Labels.getLabel("gedo.general.warnings.faltanUsuariosFirmantes"));
		}
	}

	public void cargaDatosRevision() throws SuspendNotAllowedException, InterruptedException, SecurityNegocioException {

		this.firmoYoMismo = false;
		this.tareaAsignada = Constantes.TASK_REVISAR_DOCUMENTO;
		Usuario usuarioReducido = (Usuario) this.userRevisor.getValue();
		Usuario revisor = getUsuarioService().obtenerUsuario(usuarioReducido.getUsername());
		this.setBloqueante(false);
		datos.put(FUNCION, VALIDAAPODER);
		datos.put(DATOS, revisor);
		this.verificarObligatoriedadExtensiones(this.bloqueante);
	}


	/**
	 * Realiza las actividades necesarias para enviar el documento a revisión.
	 */
	protected void enviarARevisar() throws InterruptedException, Exception {

		try {

			// Actualizo los avisos de la solicitud
			this.actualizarEstadoAvisoFirma();

			// Prepara las variables de JBPM para REVISION
			prepararVariablesWorkFlowRevision();

			// Actualizar estado
			this.historialService.actualizarHistorial(this.workingTask.getExecutionId());
			this.workingTask.setProgress(100);

			String transition = this.getRevisionTransitionName();

			this.signalExecution(transition, this.getCurrentUser());
			String mensajeRevision = (String) this.processEngine.getExecutionService()
			          .getVariable(this.workingTask.getExecutionId(), Constantes.VAR_MENSAJE_A_REVISOR);
			if (this.tareaAsignada.compareTo(Constantes.TASK_REVISAR_DOCUMENTO) == 0
					&&
					!(mensajeRevision==null)) {
				
				this.historialService.actualizarHistorial(this.workingTask.getExecutionId(),mensajeRevision);
			}
			
			this.mostrarMensajeEnvioMail();

			this.cierreVentana(false);

		} catch (WrongValueException e) {
			logger.error("Error al enviar a revisar - ", e);
			Clients.clearBusy();
			throw e;
		} catch (ClavesFaltantesException e) {
			logger.error("Error al enviar a revisar - ", e);
			Messagebox.show(Labels.getLabel(CLAVEFALTANTE), Labels.getLabel(GEDOERROR), Messagebox.OK,
					Messagebox.ERROR);
			Clients.clearBusy();
		} catch (Exception e) {
			logger.error("Error al enviar a revisar en redactar", e);
			Messagebox.show(Labels.getLabel("gedo.error.enviarARevisar"), Labels.getLabel(GEDOERROR), Messagebox.OK,
					Messagebox.ERROR);
			this.cierreVentana(true);
		}
	}

	private void enviarARevisarTemplate() throws Exception {
		// Verifico que el Documento puedo armarse
		this.documentoTemplateLibreByte = this.procesamientoTemplate.armarDocumentoTemplate(this.tipoDocumento,
				this.formManager.getValues());

		// Obtengo el Id de Transaccion de Formularios Controlados
		this.idTransaccion = formManager.saveValues();

		// Persistir Documento Template
		this.persistirDocumentoTemplate();
	}

	/**
	 * Permite que el usuario actual firme el documento.
	 * 
	 * @throws Exception
	 */
	public void onClick$selfFirmarButton() throws Exception {
		evaluarFirmanteDestinatarioLicencia(getCurrentUser());
	}

	private void selfFirmar() throws Exception {
		validarPermisoReparticion();
		if (this.puedoFirmar) {
			this.checkConstraints();
			if (this.tipoDocumento.getEsComunicable()) {
				validarDestinatario();
			}
			this.produccionTab.setSelected(true);
			this.tareaAsignada = Constantes.TASK_FIRMAR_DOCUMENTO;
			Usuario firmante = (Usuario) this.getUsuarioService().obtenerUsuario(getCurrentUser());
			firmoYoMismo = true;
			this.setBloqueante(true);
			datos.put(FUNCION, VALIDAAPODER);
			datos.put(DATOS, firmante);
			this.verificarObligatoriedadExtensiones(this.bloqueante);

			// Envia a persistir el documento subido
			Events.sendEvent(new Event(Events.ON_NOTIFY, this.documentoWindow));
		}
	}

	private void validarPermisoReparticion() throws InterruptedException {
		this.puedoFirmar = true;
		if (!(this.reparticionesHabilitadaService.validarPermisoReparticion(this.tipoDocumento, getCurrentUser(),
				Constantes.REPARTICION_PERMISO_FIRMAR))) {
			this.puedoFirmar = false;
			Messagebox.show(
					Labels.getLabel("gedo.iniciarDocumento.sinAutorizacionfirmaUsted",
							new String[] { this.tipoDocumento.getAcronimo() }),
					Labels.getLabel(GENERR), Messagebox.OK, Messagebox.ERROR);
		} // valido q si el documento tiene metadatos obligatorios, estos esten
			// completos
		else if (this.listaDocMetadata != null && this.listaDocMetadata.size() > 0) {
			for (DocumentoMetadataDTO doc : this.listaDocMetadata) {
				if (doc.isObligatoriedad() && StringUtils.isEmpty(doc.getValor())) {
					this.puedoFirmar = false;
					Messagebox.show(Labels.getLabel(OBLDATOS, new String[] { this.tipoDocumento.getAcronimo() }),
							Labels.getLabel(GENERR), Messagebox.OK, Messagebox.ERROR);
					break;
				}
			}
		}
	}

	/**
	 * Realiza las validaciones requeridas para iniciar el proceso de firma
	 * conjunta.
	 * 
	 * @throws Exception
	 */
	public void onClick$selfFirmaConjuntaButton() throws Exception {
		// Validación de datos propios obligatorios del tipo de documento.
		if (this.listaDocMetadata != null && this.listaDocMetadata.size() > 0) {
			for (DocumentoMetadataDTO doc : this.listaDocMetadata) {
				if (doc.isObligatoriedad() && StringUtils.isEmpty(doc.getValor())) {
					Messagebox.show(Labels.getLabel(OBLDATOS, new String[] { this.tipoDocumento.getAcronimo() }),
							Labels.getLabel(GENERR), Messagebox.OK, Messagebox.ERROR);
					return;
				}
			}
		}
		this.checkConstraints();
		if (this.tipoDocumento.getEsComunicable()) {
			validarDestinatario();
		}
		this.produccionTab.setSelected(true);

		// Validaci�n de n�mero de firmantes.
		if (this.usuariosFirmantes == null || this.usuariosFirmantes.size() < 2) {
			throw new WrongValueException(this.usuariosFirmaConjunta,
					Labels.getLabel("gedo.firmaConjunta.error.faltanUsuariosFirmantes"));
		}
		
		enviarAFirmar();
	}

	public PdfService getPdfService() {
		return pdfService;
	}

	public void setPdfService(PdfService pdfService) {
		this.pdfService = pdfService;
	}

	public TipoDocumentoService getTipoDocumentoService() {
		return tipoDocumentoService;
	}

	/**
	 * Actualiza la lista de receptores de aviso de firma dependiendo del estado
	 * de la solicitud.
	 */
	private void actualizarEstadoAvisoFirma() {
		if (this.solicitudAvisoFirma.isChecked()) {
			adicionarReceptoresAviso();
		} else {
			eliminarReceptoresAviso();
		}
	}

	/**
	 * Valida si existen datos propios, y si estos son obligatorios, de lo
	 * contrario, contin�a con otras validaciones.
	 * 
	 * @throws InterruptedException
	 */
	@SuppressWarnings("unchecked")
	public void validarDatosPropios() throws InterruptedException, Exception {
		try {
			datosPropios();
			mostrarEnviarARevisar();
		} catch (InterruptedException e) {
			Messagebox.show(
					Labels.getLabel("gedo.iniciarDocumento.warnings.faltaDatosPropiosObligatorio",
							new String[] { this.tipoDocumento.getAcronimo() }),
					Labels.getLabel("gedo.general.question"), Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener() {
						@Override
						public void onEvent(Event evt) throws InterruptedException {
							switch (((Integer) evt.getData()).intValue()) {
							case Messagebox.YES:
								datosPropiosTab.setSelected(false);
								mostrarEnviarARevisar();
								return;
							case Messagebox.NO:
								datosPropiosTab.setSelected(true);
								return;
							}
						}
					});
			return;
		}

	}

	private void validarNoTemplate() {

		if (this.getDataFile() == null) {
			Clients.clearBusy();
			throw new WrongValueException(this.documentoWindow.getFellow(UPLOAD_DOCUMENTO), Labels.getLabel(ERRDOCADJ));
		}
	}

	private void validarTemplate() throws InterruptedException {

		if (this.ckeditor.getValue().isEmpty()) {
			Clients.clearBusy();

			throw new InterruptedException(
					Labels.getLabel("gedo.redactarDocumentoComposer.exception.ingresarContenido"));
		}
	}

	public GestionArchivosWebDavService getGestionArchivosWebDavService() {
		return gestionArchivosWebDavService;
	}

	public void setGestionArchivosWebDavService(GestionArchivosWebDavService gestionArchivosWebDavService) {
		this.gestionArchivosWebDavService = gestionArchivosWebDavService;
	}

	/**
	 * Si el tipo de documento exige firma conjunta, y no se han definido los
	 * usuarios firmantes, pregunta al usuario, s� desea continuar.
	 * 
	 * @throws InterruptedException
	 */

	/**
	 * Invoca ventana para agregación de usuarios que participan en firma
	 * conjunta.
	 * 
	 * @throws InterruptedException
	 */
	public void onAgregarUsuariosFirmaConjunta() throws InterruptedException {
		if (this.agregarUsuariosFirmaConjuntaView != null) {
			Map<String, Object> datos = new HashMap<>();
			datos.put("modoRenderizado", Constantes.MODO_RENDERIZADO_FIRMACONJUNTA);
			datos.put("usuariosAgregados", this.usuariosFirmantes);
			datos.put(TIPODOC, tipoDocumento);
			agregarUsuarioFirmaConjunta(datos);
		} else {
			Messagebox.show(Labels.getLabel("gedo.supervisadosComposer.msg.noPosibleIniciarVistas"),
					Labels.getLabel("gedo.supervisadosComposer.msg.errorComunicacion"), Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	private void agregarUsuarioFirmaConjunta(Map<String, Object> datos) throws InterruptedException {

		this.agregarUsuariosFirmaConjuntaView.invalidate();
		this.agregarUsuariosFirmaConjuntaView = (Window) Executions.createComponents("agregarUsuariosFirmaConjunta.zul",
				this.self, datos);
		this.agregarUsuariosFirmaConjuntaView.setParent(this.getVentanaPadre());
		this.agregarUsuariosFirmaConjuntaView.setPosition(CENTER);
		this.agregarUsuariosFirmaConjuntaView.setVisible(true);
		try {
			this.agregarUsuariosFirmaConjuntaView.doModal();
		} catch (Exception e) {
			logger.error("Error al abrir GUI", e);
			Messagebox.show(Labels.getLabel("gedo.supervisadosComposer.msg.noPosibleIniciarVista"),
					Labels.getLabel("gedo.supervisadosComposer.msg.errorComunicacion"), Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	public void onClick$usuariosReservadosButton() throws InterruptedException {
		if (this.agregarUsuariosFirmaConjuntaView != null) {

			Map<String, Object> datos = new HashMap<>();
			datos.put("modoRenderizado", Constantes.MODO_RENDERIZADO_USUARIOSRESERVADOS);
			datos.put("usuariosReservadosAgregados", this.usuariosReservadosList);
			datos.put(TIPODOC, tipoDocumento);
			agregarUsuarioFirmaConjunta(datos);
		} else {
			Messagebox.show(Labels.getLabel("gedo.supervisadosComposer.msg.noPosibleIniciarVista"),
					Labels.getLabel("gedo.supervisadosComposer.msg.errorComunicacion"), Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	/**
	 * Actualiza el TextBox correspondiente a los usuarios agregados para firmar
	 */
	public void actualizarUsuariosFirmantes(List<Usuario> usuariosFirmaConjunta) throws InterruptedException {
		try {
			this.firmaConjuntaService.actualizarFirmantes(usuariosFirmaConjunta, this.workingTask.getExecutionId());
		} catch (Exception e) {
			logger.error("Error actualizando lista de firmantes " + e);
			Messagebox.show(Labels.getLabel("gedo.iniciarDocumento.errores.fallaGuardarUsuariosFirmantes"),
					Labels.getLabel(GENERR), Messagebox.OK, Messagebox.ERROR);
		}
		this.setUsuariosFirmantes(usuariosFirmaConjunta);
		this.refrescarUsuariosFirmantes();
	}

	public void actualizarListaRerservadosVariable() {

		this.setVariableWorkFlow(Constantes.VAR_USUARIOS_RESERVADOS, this.usuariosReservadosList);
	}

	/**
	 * 
	 * Verifica que se hayan cargado los archivos embebidos para extensiones
	 * obligatorios dentro del tipo de documento.
	 * 
	 * @param bloqueante
	 * @throws InterruptedException
	 * @throws SuspendNotAllowedException
	 */
	public void verificarObligatoriedadExtensiones(boolean bloqueante)
			throws SuspendNotAllowedException, InterruptedException {
		try {
			archivosEmbebidosService.verificarObligatoriedadExtensiones(listaArchivosEmbebidos, tipoDocumento);
			this.setSalida(true);
			this.determinarSalida();
		} catch (ExtensionesFaltantesException efe) {
			logger.error("Error al verificar obligatoriedad de extensiones " + efe.getMessage(), efe);
			HashMap<String, Object> hm = new HashMap<>();
			hm.put("bloqueante", bloqueante);
			Window validacionObligatoriedad = (Window) Executions
					.createComponents("/inbox/validacionObligatoriedad.zul", this.self, hm);
			validacionObligatoriedad.doModal();
		}
	}

	public void determinarSalida() {
		if (this.salida) {
			enviarBloqueoPantalla(datos);
		} else {
			return;
		}
	}

	/**
	 * Actualiza el textbox que permite mostrar la lista actual de usuarios
	 * firmantes.
	 * 
	 * @param usuariosFirmaConjunta
	 */
	protected void refrescarUsuariosFirmantes() {
		if (this.usuariosFirmantes.size() != 0) {
			this.usuariosAgregadosTxt.setMultiline(true);
			this.revisoresAgregados.setMultiline(true);
			StringBuilder listaUsuarios = new StringBuilder();
			StringBuilder listaRevisores = new StringBuilder();
			for (Usuario usuario : this.usuariosFirmantes) {
				listaUsuarios.append(usuario.getNombreApellido());
				if (this.usuariosFirmantes.indexOf(usuario) == this.usuariosFirmantes.size() - 1) {
					listaUsuarios.append("(" + usuario.getUsername() + ")");
				} else {
					listaUsuarios.append("(" + usuario.getUsername() + "),\n");
				}
			}

			List<Usuario> usuariosRevisores = firmaConjuntaService
					.buscarRevisoresPorProceso(this.workingTask.getExecutionId());
			for (Usuario usuarioRevisor : usuariosRevisores) {
				listaRevisores.append(usuarioRevisor.getNombreApellido());
				if (usuariosRevisores.indexOf(usuarioRevisor) == usuariosRevisores.size() - 1) {
					listaRevisores.append("(" + usuarioRevisor.getUsername() + ")");
				} else {
					listaRevisores.append("(" + usuarioRevisor.getUsername() + "),\n");
				}

			}

			this.usuariosAgregadosTxt.setValue(listaUsuarios.toString());
			this.revisoresAgregados.setValue(listaRevisores.toString());
			this.usuariosAgregadosTxt.setRows(this.usuariosFirmantes.size() + 1);
			this.revisoresAgregados.setRows(this.usuariosFirmantes.size() + 1);
		} else {
			this.usuariosAgregadosTxt.setValue(Labels.getLabel("gedo.general.noUsuariosFirmantes"));
			this.revisoresAgregados.setValue(Labels.getLabel("gedo.general.noUsuariosRevisores"));
			this.redactarDocumentoBinder.loadComponent(this.revisoresAgregados);
			this.redactarDocumentoBinder.loadComponent(this.usuariosAgregadosTxt);
		}
	}

	public void actualizarPopUpReservados() throws SecurityNegocioException {
		if (this.usuariosReservadosList.size() != 0) {
			this.usuariosReservados.setMultiline(true);

			StringBuilder listaUsuarios = new StringBuilder();
			for (String usuario : this.usuariosReservadosList) {
				Usuario user = this.usuarioService.obtenerUsuario(usuario);
				listaUsuarios.append(user.getNombreApellido());
				if (this.usuariosReservadosList.indexOf(usuario) == this.usuariosReservadosList.size() - 1) {
					listaUsuarios.append("(" + user.getUsername() + ")");
				} else {
					listaUsuarios.append("(" + user.getUsername() + "),\n");
				}
			}
			usuariosReservados.setValue(new String(listaUsuarios));
		}

	}

	public void setUsuariosFirmantes(List<Usuario> usuariosFirmantes) {
		this.usuariosFirmantes = usuariosFirmantes;
	}

	protected List<Usuario> getUsuariosFirmantes() {
		this.usuariosFirmantes = firmaConjuntaService.buscarFirmantesPorProceso(this.workingTask.getExecutionId());
		return this.usuariosFirmantes;
	}

	public void onClick$historialDocumento() {
		HashMap<String, Object> hm = new HashMap<>();
		hm.put("executionId", this.workingTask.getExecutionId());
		Window historialDocumentoWindow = (Window) Executions
				.createComponents("/consultas/consultaHistorialDelDocumento.zul", this.self, hm);
		historialDocumentoWindow.setClosable(true);
		historialDocumentoWindow.doModal();
	}

	public void onClick$definirDestinatariosButton() throws InterruptedException {

		Map<String, Object> map = new HashMap<>();

		map.put("mensaje", this.mensajeDestinatario);
		map.put("destinatario", this.listaUsuariosDestinatarios);
		map.put("destinatarioCopia", this.listaUsuariosDestinatariosCopia);
		map.put("destinatarioCopiaOculta", this.listaUsuariosDestinatariosCopiOculta);
		map.put("destinatariosExternos", this.listaUsuariosExternos);

		this.definirDestinatarioWindow = (Window) Executions.createComponents("/co/definirDestinatarios.zul", this.self,
				map);
		this.definirDestinatarioWindow.setClosable(true);
		try {
			this.definirDestinatarioWindow.setPosition(CENTER);
			this.definirDestinatarioWindow.doModal();
		} catch (SuspendNotAllowedException e) {
			logger.error(EXCE + e.getMessage(), e);
		}

	}

	public void onClick$detalleCOButton() throws InterruptedException {
		Map<String, Object> map = new HashMap<>();
		String usuario = this.usuarioSupervisado.equals(" ") ? user : this.usuarioSupervisado;
		ComunicacionDTO co = this.comunicacionService.buscarComunicacionPorId(this.idComunicacionRespondida);

		if (this.usuarioSupervisado.equals(" ")) {
			map.put("selectedComunicationContinue", co);
		}
		map.put("origen", "DETALLE");

		this.detalleComunicacionWindow = (Window) Executions.createComponents("/co/detalleDocumentoCo.zul", this.self,
				map);
		this.detalleComunicacionWindow.setClosable(true);

		try {
			this.detalleComunicacionWindow.setPosition(CENTER);
			this.detalleComunicacionWindow.doModal();
		} catch (SuspendNotAllowedException e) {
			logger.error(EXCE + e.getMessage(), e);
		}

	}

	@Override
	protected void asignarTarea() throws InterruptedException, Exception {
		// En este caso es por revision
		if (this.tareaAsignada.compareTo(Constantes.TASK_REVISAR_DOCUMENTO) == 0) {
			this.enviarARevisar();
		}

		if (this.tareaAsignada.compareTo(Constantes.TASK_FIRMAR_DOCUMENTO) == 0) {
			this.enviarAFirmar();
		}

	}

	@Override
	protected void enviarBloqueoPantalla(Object data) {
		Clients.showBusy(Labels.getLabel("gedo.general.procesando.procesandoSolicitud"));
		Events.echoEvent("onUser", this.self, data);
	}

	private void mostrarMensajeEnvioMail() throws InterruptedException {
		Boolean falloEnvioSolicitudMail = (Boolean) this.processEngine.getExecutionService()
				.getVariable(this.workingTask.getExecutionId(), Constantes.VAR_SOLICITUD_ENVIO_MAIL_FAIL);
		if (falloEnvioSolicitudMail != null && falloEnvioSolicitudMail) {

			Messagebox.show(Labels.getLabel("gedo.error.enviarCorreo"), Labels.getLabel(GENERR), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	/**
	 * Este metodo cierra la ventana vigente y abre otra en caso de que sea
	 * necesario para metodos como FirmarYoMismo. Es importante aclarar si
	 * salimos por Exception o no, dado que eso repercutira en cerrar la ventana
	 * y no abrir otra (Es el caso de salir por Exception que cierro la ventana
	 * de producir y no hago nada mas) o cerrar la ventana transaccion y volver
	 * a abrir una nueva ventana transaccion (Es el caso de FirmarYoMismo,
	 * cierra la venatana de producir y abre inmediatamente la de firmar)
	 * 
	 * @param esException
	 */
	private void cierreVentana(boolean esException) {
		Clients.clearBusy();
		Map<String, Object> datos = new HashMap<>();

		if (this.firmoYoMismo) {
			MacroEventData med = new MacroEventData();
			med.setExecutionId(this.workingTask.getExecutionId());
			datos.put("macroEventData", med);
		}

		datos.put("notificacion", "desactivada");
		datos.put("noAbrirNuevamenteVentanaEnBaseAWorkflow", esException);

		if (estadoExpediente != null) {
			datos.put(Constantes.VAR_NUMERO_SA, Constantes.VAR_NUMERO_SA);
		} else {
			datos.put(Constantes.VAR_NUMERO_SA, null);
		}
		Events.sendEvent(new Event(Events.ON_CLOSE, this.self.getParent(), datos));
	}

	public Image getIconoFirmaConjunta() {
		return iconoFirmaConjunta;
	}

	public void setIconoFirmaConjunta(Image iconoFirmaConjunta) {
		this.iconoFirmaConjunta = iconoFirmaConjunta;
	}

	public ReparticionHabilitadaService getReparticionesHabilitadaService() {
		return reparticionesHabilitadaService;
	}

	public void setReparticionesHabilitadaService(ReparticionHabilitadaService reparticionesHabilitadaService) {
		this.reparticionesHabilitadaService = reparticionesHabilitadaService;
	}

	public String getMensajeArchivosDeTrabajo() {
		return mensajeArchivosDeTrabajo;
	}

	public void setMensajeArchivosDeTrabajo(String mensajeArchivosDeTrabajo) {
		this.mensajeArchivosDeTrabajo = mensajeArchivosDeTrabajo;
	}

	public String getMensajeArchivosEmbebidos() {
		return mensajeArchivosEmbebidos;
	}

	public void setMensajeArchivosEmbebidos(String mensajeArchivosEmbebidos) {
		this.mensajeArchivosEmbebidos = mensajeArchivosEmbebidos;
	}

	/**
	 * Metodo que prepara las variables de JBPM4 en caso de realizarse una
	 * accion de Revision de Documento. Se diferencia enter varaibles para
	 * documento Template y No Tempate
	 * 
	 * @throws Exception
	 */
	public void prepararVariablesWorkFlowRevision() throws Exception {

		guardarVariablesComunesWorkflow();

		// Cuando avanzo el documento elimino la variable que se cargó cuando se
		// inició el documento
		this.setVariableWorkFlow(Constantes.VAR_MENSAJE_PRODUCTOR, StringUtils.EMPTY);

		//TODO: Validar tipo documento
		Usuario usRevisor = obtenerFirmanteRevisor(TIPO_USUARIO_REVISOR, true);
		
		
		Usuario usuarioReducido = (Usuario) this.userRevisor.getValue();
		Usuario usuarioRevisor = getUsuarioService().obtenerUsuario(usuarioReducido.getUsername());

		if (tipoDocumento.getEsFirmaConjunta() && usRevisor != null) {
			this.setVariableWorkFlow(Constantes.VAR_USUARIO_REVISOR, usRevisor.getUsername());
		}else{
			this.setVariableWorkFlow(Constantes.VAR_USUARIO_REVISOR, usuarioRevisor.getUsername());
		}

		
		this.setVariableWorkFlow(Constantes.VAR_MENSAJE_A_REVISOR, this.msjRevisor);
		this.setVariableWorkFlow(Constantes.VAR_USUARIOS_DESTINATARIOS, this.listaUsuariosDestinatarios);
		this.setVariableWorkFlow(Constantes.VAR_USUARIOS_DESTINATARIOS_COPIA, this.listaUsuariosDestinatariosCopia);
		this.setVariableWorkFlow(Constantes.VAR_USUARIOS_DESTINATARIOS_COPIA_OCULTA,
				this.listaUsuariosDestinatariosCopiOculta);
		this.setVariableWorkFlow(Constantes.VAR_USUARIOS_DESTINATARIOS_EXTERNOS, this.listaUsuariosExternos);
		this.setVariableWorkFlow(Constantes.VAR_MENSAJE_DESTINATARIO, this.mensajeDestinatario);

		// ES TEMPLATE
		if (this.tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_LIBRE) {
			this.setLongStringVariableWorkflow(Constantes.VAR_CONTENIDO, ckeditor.getValue());

			// ES NO TEMPLATE
		} else if (this.tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO
				|| this.tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO_TEMPLATE) {

			Boolean estadoBotonIncorporacionSADE = (Boolean) incorporacionSade.getAttribute(INCORPORAR);

			if (estadoBotonIncorporacionSADE == true) {
				this.setVariableWorkFlow(Constantes.VAR_NUMERO_SADE_PAPEL, codigoSadePapel);

			} else {
				this.setVariableWorkFlow(Constantes.VAR_NUMERO_SADE_PAPEL, null);
			}
		} else if (this.tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_TEMPLATE
				|| this.tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO_TEMPLATE) {
			this.setLongStringVariableWorkflow(Constantes.VAR_CONTENIDO,
					this.transformarInToString(new ByteArrayInputStream(this.documentoTemplateLibreByte)));
		}
	}

	/**
	 * Metodo que prepara las variables de JBPM4 en caso de realizarse una
	 * accion de Firma de Documento. Se diferencia enter varaibles para
	 * documento Template y No Tempate
	 * 
	 * @param usuarioActual
	 * @throws InterruptedException
	 * @throws SecurityNegocioException
	 */
	public FirmanteDTO prepararVariablesWorkFlowFirmar(boolean usuarioActual)
			throws InterruptedException, SecurityNegocioException {

		guardarVariablesComunesWorkflow();
		FirmanteDTO firmanteActual = null;

		// Cuando avanzo el documento elimino la variable que se cargó cuando se
		// inició el documento y la de revisión
		this.setVariableWorkFlow(Constantes.VAR_MENSAJE_PRODUCTOR, StringUtils.EMPTY);
		this.setVariableWorkFlow(Constantes.VAR_MENSAJE_A_REVISOR, StringUtils.EMPTY);

		if (!usuarioActual && !this.tipoDocumento.getEsFirmaConjunta()) {
			Usuario usuarioDestino = (Usuario) getUserFirmante().getValue();
//			if (this.usuarioService.licenciaActiva(usuarioDestino.getUsername(), new Date())) {
//				this.setVariableWorkFlow(Constantes.VAR_USUARIO_DESTINATARIO_LICENCIA, usuarioDestino.getUsername());
//			}
		}

		// ES LIBRE
		if (this.tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_LIBRE) {

			this.setLongStringVariableWorkflow(Constantes.VAR_CONTENIDO, ckeditor.getValue());

			// ES IMPORTADO
		} else if (this.tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO
				|| this.tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO_TEMPLATE) {

			this.setVariableWorkFlow(Constantes.VAR_USUARIO_DERIVADOR, this.getCurrentUser());

			Boolean estadoBotonIncorporacionSADE = (Boolean) incorporacionSade.getAttribute(INCORPORAR);
			if (estadoBotonIncorporacionSADE == true) {
				this.setVariableWorkFlow(Constantes.VAR_NUMERO_SADE_PAPEL, codigoSadePapel);
			} else {
				this.setVariableWorkFlow(Constantes.VAR_NUMERO_SADE_PAPEL, "");
			}
		} else {

			this.setLongStringVariableWorkflow(Constantes.VAR_CONTENIDO,
					this.transformarInToString(new ByteArrayInputStream(this.documentoTemplateLibreByte)));
		}
		
		
		if (usuarioActual && !this.tipoDocumento.getEsFirmaConjunta()) {
			this.setVariableWorkFlow(Constantes.VAR_USUARIO_FIRMANTE, this.getCurrentUser());
		} else {
			if (tipoDocumento.getEsFirmaConjunta()) {

				String userFirm = getCurrentUser();
				String useRev = getCurrentUser();
				List<FirmanteDTO> firmantes = this.firmaConjuntaService.buscarRevisorFirmante(this.getWorkingTask().getExecutionId());
				for(FirmanteDTO firm : firmantes) {
					
					if (firmanteActual == null && !firm.getEstadoFirma()) {
						firmanteActual = firm;
					}
					
					if (!(firm.isEstadoRevision() && firm.getEstadoFirma()) && firm.getUsuarioRevisor() != null
							&& !firm.getUsuarioRevisor().isEmpty()) {
						if(!firm.isEstadoRevision()) {
							useRev = firm.getUsuarioRevisor();
							userFirm = firm.getUsuarioRevisor();
							setVariableWorkFlow(Constantes.VAR_USUARIO_REVISOR, useRev);
							break;
						}else if(firm.isEstadoRevision() && !firm.getEstadoFirma()) {
							useRev = firm.getUsuarioRevisor();
							userFirm = firm.getUsuarioFirmante();
							setVariableWorkFlow(Constantes.VAR_USUARIO_REVISOR, useRev);
							break;
						}

					} else {
						break;
					}

				}
				
				this.getWorkingTask().getActivityName();
				
				setVariableWorkFlow(Constantes.VAR_USUARIO_FIRMANTE, userFirm);
				
				// FIXME: REVISAR ESTO PORQUE NO ENTIENDO CUAL ES LA IDEA DE
				// SOLO HACERLO EN LOS IMPORTADOS
				if (this.tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO) {
					this.setVariableWorkFlow(Constantes.VAR_NUMERO_FIRMAS, this.usuariosFirmantes.size());
				}
			} else {
				Usuario usuarioReducido = (Usuario) this.userFirmante.getValue();
				Usuario usuario = getUsuarioService().obtenerUsuario(usuarioReducido.getUsername());
				setVariableWorkFlow(Constantes.VAR_USUARIO_FIRMANTE, usuario.getUsername());
			}
		}
		
		return firmanteActual;

	}

	/**
	 * Metodo que obtiene un componente de un Window hijo. Comunmente utilizado
	 * para manipular cierta informacion contenida en un Window hijo
	 * 
	 * @param windowHijo
	 * @param componenteHijo
	 * @return Component
	 */
	private Component obtenerComponenteVentanaHijo(Window windowHijo, String componenteHijo) {
		@SuppressWarnings("unchecked")
		List<Component> lista = (List<Component>) windowHijo.getChildren();
		for (Component comp : lista) {
			if (comp.getId().toString().equals(componenteHijo)) {
				return comp;
			}
		}
		return null;
	}

	/**
	 * Actualiza estado de componentes dependiendo de si el tipo de documento
	 * soporta firma conjunta.
	 * 
	 * @throws InterruptedException
	 */
	private void prepararComponentesFirma() throws InterruptedException {
		if (this.tipoDocumento.getEsFirmaConjunta()) {
			getUsuariosFirmantes();
			refrescarUsuariosFirmantes();

			this.firmarButton.setVisible(false);
			this.iconoFirmaConjunta.setVisible(true);
			this.iconoRevisoresFirmaConjunta.setVisible(true);
			this.usuariosFirmaConjuntatoolbar.setVisible(true);
			this.selfFirmaConjuntaButton.setVisible(true);
			this.selfFirmarButton.setVisible(false);
			this.selfImportarDocumentoButton.setVisible(false);
		} else {
			this.iconoFirmaConjunta.setVisible(false);
			this.iconoRevisoresFirmaConjunta.setVisible(false);
			this.usuariosFirmaConjuntatoolbar.setVisible(false);
			this.selfFirmaConjuntaButton.setVisible(false);
			this.selfFirmarButton.setVisible(true);
			this.selfImportarDocumentoButton.setVisible(false);

		}

		if (this.tipoDocumento.getEsFirmaExterna()) {
			this.selfImportarDocumentoButton.setVisible(true);
			this.selfFirmarButton.setVisible(false);
			firmarButton.setVisible(false);
			this.solicitudAvisoFirma.setLabel(Labels.getLabel("gedo.general.importar"));
		}

		// Validar si la variable de archivo temporal existe, compatibilidad con
		// versión 2.1.0
		Set<String> listaVariables = this.processEngine.getExecutionService()
				.getVariableNames(this.workingTask.getExecutionId());
		if (!listaVariables.contains(Constantes.VAR_ARCHIVO_TEMPORAL_FIRMA)) {

			// WEBDAV
			String nombreArchivoTemporal = getGestionArchivosWebDavService().crearNombreArchivoTemporal();
			this.setVariableWorkFlow(Constantes.VAR_ARCHIVO_TEMPORAL_FIRMA, nombreArchivoTemporal);

		}
	}

	public void notificarSade() {

		if (this.descripcionTrata != null && !this.descripcionTrata.equals("")) {
			this.eliminarSade.setImage("/imagenes/EliminarDocumentoAdjunto.png");
			this.eliminarSade.setVisible(true);
			this.incorporacionSade.setDisabled(true);
			obtenerDatosSade(descripcionTrata);
		}
	}

	public void onClick$eliminarSade() {
		incorporacionSade.setAttribute(INCORPORAR, false);
		this.gridSADE.setHeight("100%");
		this.trataSadeLabel.setValue(Labels.getLabel("gedo.redactarDocumento.label.noSeleccionActuacion"));
		this.datosSadeLabel.setValue("");
		incorporacionSade.setAttribute(INCORPORAR, false);
		this.eliminarSade.setVisible(false);
		this.incorporacionSade.setDisabled(false);
	}

	private void evaluarFirmanteDestinatarioLicencia(String datosUsuario) throws Exception {
		String userDestinatarioLicencia = (String) this.processEngine.getExecutionService()
				.getVariable(this.workingTask.getExecutionId(), Constantes.VAR_USUARIO_DESTINATARIO_LICENCIA);
		Usuario userAux = userDestinatarioLicencia != null
				? this.usuarioService.obtenerUsuario(userDestinatarioLicencia) : null;
		if (userAux != null && !userAux.getApoderado().equals(datosUsuario)) {
			Messagebox.show(Labels.getLabel("gedo.licencia.envioFirmar"), Labels.getLabel(GENERR), Messagebox.OK,
					Messagebox.INFORMATION, new EventListener() {
						public void onEvent(Event evt) {
							try {
								selfFirmar();
							} catch (Exception e) {
								logger.error("Error al enviar a firmar la tarea: " + e.getMessage(), e);
								throw new RuntimeException(e);
							}
						}
					});
		} else {
			selfFirmar();
		}

	}

	public String iconoMensaje() {
		if (mensajeRevisor != null && !StringUtils.isEmpty(mensajeRevisor)) {
			return IMG_MAIL_CON_MENSAJE;
		} else {
			return IMG_MAIL_SIN_MENSAJE;
		}
	}

	public void onCloseWindow() {
		Map<String, Object> datos = new HashMap<>();
		datos.put("notificacion", "activada");
		if (estadoExpediente != null) {
			datos.put(Constantes.VAR_NUMERO_SA, Constantes.VAR_NUMERO_SA);
		} else {
			datos.put(Constantes.VAR_NUMERO_SA, null);
		}
		Events.sendEvent(new Event(Events.ON_CLOSE, this.self.getParent(), datos));
	}

	protected String getFirmarTransitionName() {
		return Constantes.TRANSICION_USO_PORTAFIRMA;
	}

	protected String getRevisionTransitionName() {
		if (this.getWorkingTask().getActivityName().equals(REVISAR_DOCUMENTO)
				|| this.getWorkingTask().getActivityName().equals(RECHAZADO)) {
			return Constantes.TRANSICION_FORK;
		} else {
			return Constantes.TRANSICION_REVISAR;
		}
	}

	protected Window getVentanaPadre() {
		return this.redactarDocumentoWindow;
	}

	public String getMensajeRevisor() {
		return mensajeRevisor;
	}

	public void setMensajeRevisor(String mensajeRevisor) {
		this.mensajeRevisor = mensajeRevisor;
	}

	public PlantillaDTO getSelectedPlantilla() {
		return selectedPlantilla;
	}

	public void setSelectedPlantilla(PlantillaDTO selectedPlantilla) {
		this.selectedPlantilla = selectedPlantilla;
	}

	public void setListaPlantilla(List<PlantillaDTO> listaPlantilla) {
		this.listaPlantilla = listaPlantilla;
	}

	public List<PlantillaDTO> getListaPlantilla() {
		return listaPlantilla;
	}

	public Window getDocumentoWindow() {
		return documentoWindow;
	}

	public void setDocumentoWindow(Window documentoWindow) {
		this.documentoWindow = documentoWindow;
	}

	public Window getDocumentoSadeWindow() {
		return documentoSadeWindow;
	}

	public void setDocumentoSadeWindow(Window documentoSadeWindow) {
		this.documentoSadeWindow = documentoSadeWindow;
	}

	public Window getIncorporarSadeWindow() {
		return incorporarSadeWindow;
	}

	public void setIncorporarSadeWindow(Window incorporarSadeWindow) {
		this.incorporarSadeWindow = incorporarSadeWindow;
	}

	public String getCodigoSadePapel() {
		return codigoSadePapel;
	}

	public void setCodigoSadePapel(String codigoSadePapel) {
		this.codigoSadePapel = codigoSadePapel;
	}

	public Hbox getIconos_derechos() {
		return iconos_derechos;
	}

	public void setIconos_derechos(Hbox iconos_derechos) {
		this.iconos_derechos = iconos_derechos;
	}

	public void setDataFile(byte[] dataFile) {
		this.documentoNoTemplateByte = dataFile;
	}

	public byte[] getDataFile() {
		return documentoNoTemplateByte;
	}

	public Label setNombreArchivoLabel(Label nombreArchivoLabel) {
		return this.nombreArchivoLabel = nombreArchivoLabel;
	}

	public Label getNombreArchivoLabel() {
		return nombreArchivoLabel;
	}

	public String setPathFileTemporal(String pathFileTemporal) {
		return this.pathFileTemporal = pathFileTemporal;
	}

	public String getPathFileTemporal() {
		return pathFileTemporal;
	}

	public Image getIconoRevisoresFirmaConjunta() {
		return iconoRevisoresFirmaConjunta;
	}

	public void setIconoRevisoresFirmaConjunta(Image iconoRevisoresFirmaConjunta) {
		this.iconoRevisoresFirmaConjunta = iconoRevisoresFirmaConjunta;
	}

	public AnnotateDataBinder getRedactarDocumentoBinder() {
		return redactarDocumentoBinder;
	}

	public void setRedactarDocumentoBinder(AnnotateDataBinder redactarDocumentoBinder) {
		this.redactarDocumentoBinder = redactarDocumentoBinder;
	}

	public boolean isSalida() {
		return salida;
	}

	public void setSalida(boolean salida) {
		this.salida = salida;
	}

	public boolean isBloqueante() {
		return bloqueante;
	}

	public void setBloqueante(boolean bloqueante) {
		this.bloqueante = bloqueante;
	}

	public List<ArchivoEmbebidoDTO> getListaArchivosEmbebidos() {
		return listaArchivosEmbebidos;
	}

	public void setListaArchivosEmbebidos(List<ArchivoEmbebidoDTO> listaArchivosEmbebidos) {
		this.listaArchivosEmbebidos = listaArchivosEmbebidos;
	}

	public Comboitem getUserRevisor() {
		return userRevisor;
	}

	public void setUserRevisor(Comboitem userRevisor) {
		this.userRevisor = userRevisor;
	}

	public Comboitem getUserFirmante() {
		return userFirmante;
	}

	public void setUserFirmante(Comboitem userFirmante) {
		this.userFirmante = userFirmante;
	}

	public String getMjsRevisor() {
		return msjRevisor;
	}

	public void setMjsRevisor(String mjsRevisor) {
		this.msjRevisor = mjsRevisor;
	}

	public String getDescripcionTrata() {
		return descripcionTrata;
	}

	public void setDescripcionTrata(String descripcionTrata) {
		this.descripcionTrata = descripcionTrata;
	}

	public Tab getProduccionTab() {
		return produccionTab;
	}

	public void setProduccionTab(Tab produccionTab) {
		this.produccionTab = produccionTab;
	}

	public Tab getDatosPropiosTab() {
		return datosPropiosTab;
	}

	public void setDatosPropiosTab(Tab datosPropiosTab) {
		this.datosPropiosTab = datosPropiosTab;
	}

	public Window getDocumentoArchivoTrabajo() {
		return documentoArchivoTrabajo;
	}

	public void setDocumentoArchivoTrabajo(Window documentoArchivoTrabajo) {
		this.documentoArchivoTrabajo = documentoArchivoTrabajo;
	}

	public Window getDocumentoArchivoEmbebido() {
		return documentoArchivoEmbebido;
	}

	public void setDocumentoArchivoEmbebido(Window documentoArchivoEmbebido) {
		this.documentoArchivoEmbebido = documentoArchivoEmbebido;
	}

	public Window getDocumentoDatosPropios() {
		return documentoDatosPropios;
	}

	public void setDocumentoDatosPropios(Window documentoDatosPropios) {
		this.documentoDatosPropios = documentoDatosPropios;
	}

	public Toolbarbutton getEliminarSade() {
		return eliminarSade;
	}

	public void setEliminarSade(Toolbarbutton eliminarSade) {
		this.eliminarSade = eliminarSade;
	}

	public List<String> getListaUsuariosDestinatarios() {
		return listaUsuariosDestinatarios;
	}

	public void setListaUsuariosDestinatarios(List<String> listaUsuariosDestinatarios) {
		this.listaUsuariosDestinatarios = listaUsuariosDestinatarios;
	}

	public List<String> getListaUsuariosDestinatariosCopia() {
		return listaUsuariosDestinatariosCopia;
	}

	public void setListaUsuariosDestinatariosCopia(List<String> listaUsuariosDestinatariosCopia) {
		this.listaUsuariosDestinatariosCopia = listaUsuariosDestinatariosCopia;
	}

	public List<String> getListaUsuariosDestinatariosCopiOculta() {
		return listaUsuariosDestinatariosCopiOculta;
	}

	public void setListaUsuariosDestinatariosCopiOculta(List<String> listaUsuariosDestinatariosCopiOculta) {
		this.listaUsuariosDestinatariosCopiOculta = listaUsuariosDestinatariosCopiOculta;
	}

	public String getMensajeDestinatario() {
		return mensajeDestinatario;
	}

	public void setMensajeDestinatario(String mensajeDestinatario) {
		this.mensajeDestinatario = mensajeDestinatario;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public ComunicacionDTO getComunicacion() {
		return comunicacion;
	}

	public void setComunicacion(ComunicacionDTO comunicacion) {
		this.comunicacion = comunicacion;
	}

	public List<UsuarioExternoDTO> getListaUsuariosExternos() {
		return listaUsuariosExternos;
	}

	public void setListaUsuariosExternos(List<UsuarioExternoDTO> listaUsuariosExternos) {
		this.listaUsuariosExternos = listaUsuariosExternos;
	}

	public Integer getIdComunicacionRespondida() {
		return idComunicacionRespondida;
	}

	public void setIdComunicacionRespondida(Integer idComunicacionRespondida) {
		this.idComunicacionRespondida = idComunicacionRespondida;
	}

	public Window getArchivoEmbebidoWindow() {
		return archivoEmbebidoWindow;
	}

	public void setArchivoEmbebidoWindow(Window archivoEmbebidoWindow) {
		this.archivoEmbebidoWindow = archivoEmbebidoWindow;
	}

	public List<String> getUsuariosReservadosList() {
		return usuariosReservadosList;
	}

	public void setUsuariosReservadosList(List<String> usuariosReservadosList) {
		this.usuariosReservadosList = usuariosReservadosList;
	}

	public String getNombreArchivoTemporal() {
		return nombreArchivoTemporal;
	}

	public void setNombreArchivoTemporal(String nombreArchivoTemporal) {
		this.nombreArchivoTemporal = nombreArchivoTemporal;
	}

	public boolean isMensajeDestinatarioLicencia() {
		return mensajeDestinatarioLicencia;
	}

	public void setMensajeDestinatarioLicencia(boolean mensajeDestinatarioLicencia) {
		this.mensajeDestinatarioLicencia = mensajeDestinatarioLicencia;
	}

	public Integer getIdDestinatario() {
		return idDestinatario;
	}

	public void setIdDestinatario(Integer idDestinatario) {
		this.idDestinatario = idDestinatario;
	}

}

final class RedactarDocumentoVersionNuevaComposerListener implements EventListener {
	private RedactarDocumentoComposer composer;
	private static final String VALIDAAPODER = "validarApoderamiento";
	private static final String FUNCION = "funcion";
	private static final String DATOS = "datos";
	private static final String TIPODOC = "tipoDocumento";

	public RedactarDocumentoVersionNuevaComposerListener(RedactarDocumentoComposer comp) {
		this.composer = comp;
	}

	@SuppressWarnings("unchecked")
	public void onEvent(Event event) throws Exception {
		if (event.getName().equals(Events.ON_NOTIFY)) {
			if (event.getData() != null) {
				Map<String, Object> map = (Map<String, Object>) event.getData();
				String origen = (String) map.get("origen");
				if (StringUtils.equals(origen, Constantes.EVENTO_USUARIOS_FIRMANTES)) {
					List<Usuario> datosUsuario = (List<Usuario>) map.get(DATOS);
					composer.actualizarUsuariosFirmantes(datosUsuario);
				}
				if (StringUtils.equals(origen, Constantes.EVENTO_USUARIOS_RESERVADOS)) {
					composer.setUsuariosReservadosList((List<String>) map.get(DATOS));
					composer.actualizarListaRerservadosVariable();
					composer.actualizarPopUpReservados();
				}

				if (StringUtils.equals(origen, Constantes.EVENTO_DOCUMENTO_SADE)) {
					if (composer.getCodigoSadePapel() == null) {
						Clients.clearWrongValue(composer.getIncorporarSadeWindow());
					}
					composer.setCodigoSadePapel((String) map.get("codigoSade"));
				}
				if (StringUtils.equals(origen, Constantes.EVENTO_ARCHIVO_EMBEBIDO)) {
					composer.setListaArchivosEmbebidos(
							(List<ArchivoEmbebidoDTO>) map.get(ArchivoEmbebidoDTO.ARCHIVOS_EMBEBIDOS));
				}
				if (StringUtils.equals(origen, Constantes.EVENTO_OBLIGATORIEDAD_EXTENSIONES)) {
					composer.setSalida((Boolean) map.get("salida"));
					composer.determinarSalida();
				}

				if (StringUtils.equals(origen, Constantes.EVENTO_ENVIAR_A_REVISAR)) {
					composer.setUserRevisor((Comboitem) map.get("revisor"));
					composer.setMjsRevisor((String) map.get("mensaje"));
					composer.cargaDatosRevision();
				}

				if (StringUtils.equals(origen, Constantes.EVENTO_ENVIAR_A_FIRMAR)) {
					composer.setUserFirmante((Comboitem) map.get("firmante"));
					if (map.containsKey("mostrarMensaje")) {
						composer.setMensajeDestinatarioLicencia(true);
					}
					composer.cargarDatosFirmar();
				}

				if (StringUtils.equals(origen, Constantes.EVENTO_INCORPORAR_SADE)) {
					composer.setDescripcionTrata((String) map.get("sade"));
					composer.notificarSade();

				}
				if (StringUtils.equals(origen, Constantes.EVENTO_DEFINIR_DESTINATARIOS)) {
					composer.setListaUsuariosDestinatarios((List<String>) map.get("destinatario"));
					composer.setListaUsuariosDestinatariosCopia((List<String>) map.get("destinatarioCopia"));
					composer.setListaUsuariosDestinatariosCopiOculta((List<String>) map.get("destinatarioCopiaOculta"));
					composer.setListaUsuariosExternos((List<UsuarioExternoDTO>) map.get("destinatariosExternos"));
					composer.setMensajeDestinatario((String) map.get("mensaje"));

				}
			}

			composer.getRedactarDocumentoBinder().loadAll();
		} else if (event.getName().equals(Events.ON_USER)) {
			Map<String, Object> datos = (Map<String, Object>) event.getData();
			String funcion = (String) datos.get(FUNCION);
			if(funcion != null) {
				if (funcion.equals("validarFirmantes")) {
					Boolean firmaConjunta = (Boolean) datos.get(DATOS);
					if (firmaConjunta) {
						composer.validarUsuariosFirmantes();
					} else {
						composer.enviarARevisar();
					}
				}
				if (funcion.equals("enviarARevisar")) {
					composer.enviarARevisar();
				}
				if (funcion.equals(VALIDAAPODER)) {
					Usuario usuario = (Usuario) datos.get(DATOS);
					TipoDocumentoDTO tipoDoc = (TipoDocumentoDTO) datos.get(TIPODOC);
					this.composer.validarUsuarios(usuario, tipoDoc);
				}
				if (funcion.equals("validarReparticion")) {
					Usuario usuario = (Usuario) datos.get(DATOS);
					this.composer.validacionesReparticion(usuario);
				}
				if (funcion.equals("asignarTarea")) {
					this.composer.asignarTarea();
				}
				if (funcion.equals("previsualizar")) {
					this.composer.previsualizar();
				}
			}
		} else if (event.getName().equals(Events.ON_UPLOAD)) {
			Map<String, Object> datos = (Map<String, Object>) event.getData();
			byte[] dataFile = (byte[]) datos.get("dataFile");
			Label nombreArchivoLabel = (Label) datos.get("nombreArchivoLabel");
			composer.setDataFile(dataFile);
			composer.setNombreArchivoLabel(nombreArchivoLabel);
			if (dataFile != null) {
				Clients.clearWrongValue(composer.getDocumentoWindow());
			}
		}
	}

}
