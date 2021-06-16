package com.egoveris.deo.web.satra.firma;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.zkoss.json.JSONArray;
import org.zkoss.json.JSONObject;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Div;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.ListModels;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.egoveris.commons.databaseconfiguration.propiedades.AppProperty;
import com.egoveris.deo.base.exception.FirmaDocumentoException;
import com.egoveris.deo.base.exception.VariableWorkFlowNoExisteException;
import com.egoveris.deo.base.service.ArchivoDeTrabajoService;
import com.egoveris.deo.base.service.ArchivosEmbebidosService;
import com.egoveris.deo.base.service.BuscarDocumentosGedoService;
import com.egoveris.deo.base.service.DobleFactorService;
import com.egoveris.deo.base.service.DocumentoAdjuntoService;
import com.egoveris.deo.base.service.FirmaConjuntaService;
import com.egoveris.deo.base.service.FirmaDocumentoService;
import com.egoveris.deo.base.service.GestionArchivosWebDavService;
import com.egoveris.deo.base.service.HistorialService;
import com.egoveris.deo.base.service.NotificacionMailService;
import com.egoveris.deo.model.model.ArchivoDeTrabajoDTO;
import com.egoveris.deo.model.model.ArchivoEmbebidoDTO;
import com.egoveris.deo.model.model.DobleFactorDTO;
import com.egoveris.deo.model.model.DocumentoAdjuntoDTO;
import com.egoveris.deo.model.model.DocumentoDTO;
import com.egoveris.deo.model.model.DocumentoMetadataDTO;
import com.egoveris.deo.model.model.FirmaEvent;
import com.egoveris.deo.model.model.FirmaResponse;
import com.egoveris.deo.model.model.FirmaTokenRequest;
import com.egoveris.deo.model.model.FirmanteDTO;
import com.egoveris.deo.model.model.MacroEventData;
import com.egoveris.deo.model.model.PrepararFirmaResponse;
import com.egoveris.deo.model.model.RequestGenerarDocumento;
import com.egoveris.deo.util.Constantes;
import com.egoveris.deo.web.satra.produccion.ArchivosDeTrabajoComposer;
import com.egoveris.deo.web.utils.UsuariosComparator;
import com.egoveris.deo.web.utils.Utilitarios;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class FirmaDocumentoV2Composer extends FirmaDocumentoGenericComposer {

private static final long serialVersionUID = -1905485289820077229L;

private static final String TEMPLATE_MAIL_DOBLE_FACTOR = "templateMailDobleFactor";
private static final Logger logger = LoggerFactory.getLogger(FirmaDocumentoV2Composer.class);

@WireVariable("historialServiceImpl")
private HistorialService historialService;
@WireVariable("usuarioServiceImpl")
private IUsuarioService usuarioService;
@WireVariable("buscarDocumentosGedoServiceImpl")
private BuscarDocumentosGedoService buscarDocumentosGedoService;
@WireVariable("firmaConjuntaServiceImpl")
private FirmaConjuntaService firmaConjuntaService;
@WireVariable("archivoDeTrabajoServiceImpl")
private ArchivoDeTrabajoService archivoDeTrabajoService;
@WireVariable("archivosEmbebidosServiceImpl")
private ArchivosEmbebidosService archivosEmbebidosService;
@WireVariable("documentoAdjuntoServiceImpl")
private DocumentoAdjuntoService documentoAdjuntoService;
@WireVariable("gestionArchivosWebDavServiceImpl")
private GestionArchivosWebDavService gestionArchivosWebDavService;
@WireVariable("firmaDocumentoServiceImpl")
private FirmaDocumentoService firmaDocumentoService;
@WireVariable("dobleFactorServiceImpl")
private DobleFactorService dobleFactorService;
@WireVariable("notificacionMailServiceImpl")
private NotificacionMailService notificacionMailServiceImpl;

@WireVariable("dBProperty")
private AppProperty appProperty;

private List<ArchivoEmbebidoDTO> listaArchivosEmbebidos;
private FirmanteDTO firmante;
private Task selectedTask;

private String usuarioDerivador;
public String imagenArchivoDeTrabajoSinMensaje = "/imagenes/NoArchivosDeTrabajo.png";
public String imagenArchivoDeTrabajoConMensaje = "/imagenes/ANIMADO_ArchivosDeTrabajo.gif";
private static final String DESCARGAR_AUTO_FIRMA="https://estaticos.redsara.es/comunes/autofirma/currentversion/AutoFirma64.zip";

private List<ArchivoDeTrabajoDTO> listaArhivosDeTrabajo = null;
private List<String> listaUsuariosReservados = null;

private String mensajeArchivosDeTrabajo;
private String estadoExpediente = null;
private String mensajeArchivosEmbebidos;
protected AnnotateDataBinder firmaDocumentoComposerBinder;
private Window descargaDocumento;
private Window firmaDocumento;
private Image iconoArchivosEmbebidos;
private Image iconoUsuariosReservados;
private Textbox usuariosReservados;
private Window selectCert;
private Toolbarbutton firmarButton;
private Div subtiSinCert;
private Div subtiConCert;

// --Firma Digital--
private byte[] certificados;
private Combobox comboSelectCert;
private Button firmarConToken;
private Button cancelarFirma;

private PrepararFirmaResponse pFR;

public void doAfterCompose(Component component) throws Exception {
super.doAfterCompose(component);
Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));

setSelectedTask((Task) this.self.getDesktop().getAttribute("selectedTask"));
//this.get
//estadoExpediente = (String) this.self.getDesktop().getAttribute(Constantes.VAR_NUMERO_SA);
estadoExpediente = (String) Executions.getCurrent().getArg().get(Constantes.VAR_NUMERO_SA);
component.addEventListener(Events.ON_NOTIFY, new ExtensionEventListener(this));
// page.getAttributes()
component.addEventListener(Events.ON_USER, new FirmaV2EventListener(this));
component.addEventListener(Events.ON_CLOSE, new FirmaV2EventListener(this));

this.cargarVariablesInstancia();

this.usuarioRevisor.setModel(ListModels.toListSubModel(new ListModelList(getUsuarioService().obtenerUsuarios()), new UsuariosComparator(), 30));

this.self.setAttribute("dontAskBeforeClose", true);

listaArhivosDeTrabajo = archivoDeTrabajoService.buscarArchivosDeTrabajoPorProceso(this.workingTask.getExecutionId());
this.listaArchivosEmbebidos = archivosEmbebidosService.buscarArchivosEmbebidosPorProceso(this.workingTask.getExecutionId());

if (super.tipoDocumento.getPermiteEmbebidos()) {
	this.iconoArchivosEmbebidos.setVisible(true);
}
if (super.tipoDocumento.getEsConfidencial() && listaUsuariosReservados != null
		&& listaUsuariosReservados.size() > 0) {
	iconoUsuariosReservados.setVisible(true);
	this.cargarPopUpUsuariosReservados();
}
if (super.tipoDocumento.getTieneToken()) {
	if(this.firmarConCertificado!=null) {
		this.firmarConCertificado.setDisabled(true);				
	}
}
this.firmaDocumentoComposerBinder = new AnnotateDataBinder(component);
this.firmaDocumentoComposerBinder.loadAll();

}

/**
* Cargar la información para previsualización en el momento en el que se crea
* la ventana.
* 
* @param event
* @throws InterruptedException
*/
public void onCreate$firmaDocumento(Event event) throws InterruptedException {
RequestGenerarDocumento request = this.createAndSetRequest();
if (listaArchivosEmbebidos != null && listaArchivosEmbebidos.size() > 0) {
	try {
		String mensaje = "Este documento contiene archivos embebidos, los cuales no pueden ser previsualizados. "
				+ "¿Desea descargar el documento?";
		Map<String, Object> mensajes = new HashMap<String, Object>();
		mensajes.put("informacionPadre", mensaje);
		mensajes.put("titulo", "Información");
		mensajes.put("contieneEmbebidos", true);
		mensajes.put("request", request);
		this.self.getDesktop().setAttribute("workFlowOrigen", this.workingTask.getExecutionId());
		this.self.getDesktop().setAttribute("mensajes", mensajes);

		descargaDocumento = (Window) Executions.createComponents("inbox/descargaDocumento.zul", this.self, null);
		descargaDocumento.setParent(this.firmaDocumento);
		descargaDocumento.setVisible(true);
		descargaDocumento.setClosable(true);
		descargaDocumento.doModal();
	} catch (Exception ex) {
		logger.error("Mensaje de error", ex);
	}
}
this.habilitarBotones();
this.cargarComboCertificadosToken();

try {
	super.contenidoTemporal = this.gestionArchivosWebDavService
			.obtenerRecursoTemporalWebDav(nombreArchivoTemporal);
} catch (Exception e) {
	logger.error("Error al descargar archivo del Webdav: " + nombreArchivoTemporal, e);
	Messagebox.show(Labels.getLabel("gedo.error.obteniendoArchivoWebDav", new String[] { e.getMessage() }),
			Labels.getLabel("gedo.general.error"), Messagebox.OK, Messagebox.ERROR);
}

if (super.contenidoTemporal == null || !super.generarPreviewInicial()) {
	super.closeAndNotifyAssociatedWindow(null);
}
if (!this.tipoDocumento.getTieneAviso()) {
	solicitudAvisoFirma.setDisabled(true);
	super.solicitudAvisoFirma.setChecked(false);
} else {
	super.solicitudAvisoFirma.setChecked(true);
	if (!super.tipoDocumento.getEsFirmaConjunta()) {
		super.solicitudAvisoFirma.setDisabled(true);
	}
}

if (super.tipoDocumento.getEsFirmaConjunta()) {
	this.verFirmantes.setVisible(true);
	this.verRevisores.setVisible(true);
	this.firmaDocumento.setHeight("650px");
	this.firmaDocumento.setWidth("1010px");
}
}

private void cargarComboCertificadosToken() {
comboSelectCert = (Combobox) selectCert.getFellow("comboSelectCert");
firmarConToken = (Button) selectCert.getFellow("firmarConToken");
cancelarFirma = (Button) selectCert.getFellow("cancelarFirma");
subtiSinCert = (Div) selectCert.getFellow("subtiSinCert");
subtiConCert = (Div) selectCert.getFellow("subtiConCert");

// Agrego listener

// Agrego listener
cancelarFirma.addEventListener(Events.ON_CLICK, new EventListener() {
	public void onEvent(Event event) throws Exception {
		selectCert.setVisible(false);
	}
});
}

/**
* Carga valores desde las variables almacenadas en el workflow
*/
@SuppressWarnings("unchecked")
private void cargarVariablesInstancia() {

super.acronimo = (String) super.getVariableWorkFlow(Constantes.VAR_TIPO_DOCUMENTO);

super.tipoDocumento = super.tipoDocumentoService.buscarTipoDocumentoPorId(Integer.valueOf(super.acronimo));

this.usuarioDerivador = (String) this.processEngine.getExecutionService().getVariable(this.workingTask.getExecutionId(), Constantes.VAR_USUARIO_DERIVADOR);
if (this.usuarioDerivador == null) {
	this.usuarioDerivador = (String) this.processEngine.getExecutionService()
			.getVariable(this.workingTask.getExecutionId(), Constantes.VAR_USUARIO_PRODUCTOR);
	this.processEngine.getExecutionService().setVariable(this.workingTask.getExecutionId(), Constantes.VAR_USUARIO_DERIVADOR, this.usuarioDerivador);
}

// la variable nombreArchivoTemporal se usa en el
// onCreate$firmaDocumento
super.nombreArchivoTemporal = (String) super.getVariableWorkFlow(
		Constantes.VAR_ARCHIVO_TEMPORAL_FIRMA);
super.usuarioFirmante = getCurrentUser();
if (super.tipoDocumento.getEsConfidencial()) {

	List<Object> rotos = (List<Object>) this.processEngine.getExecutionService().getVariable(this.workingTask.getExecutionId(), Constantes.VAR_USUARIOS_RESERVADOS);

	if (rotos != null && rotos.size() > 0) {
		if (rotos.get(0) instanceof Usuario) {
			List<Usuario> listaUsuariosReservados = new ArrayList<Usuario>();
			for (Object user : rotos) {
				listaUsuariosReservados.add((Usuario) user);
			}
			this.listaUsuariosReservados = subsanarListasMalGrabadasSoloreservados(
					listaUsuariosReservados);
		} else {
			this.listaUsuariosReservados = (List<String>) this.processEngine.getExecutionService().getVariable(this.workingTask.getExecutionId(), Constantes.VAR_USUARIOS_RESERVADOS);
			if (this.listaUsuariosReservados == null) {
				this.listaUsuariosReservados = new ArrayList<String>();
			}
		}
	} else {
		this.listaUsuariosReservados = new ArrayList<String>();
	}
}
}

private List<String> subsanarListasMalGrabadasSoloreservados(
	List<Usuario> listaUsuariosReservados) {
// INICIO FIX: se agrega logica para generar los usuarios faltantes que
// no se grabaron en la BPM
List<Usuario> usuariosAArreglar = new ArrayList<Usuario>();
usuariosAArreglar.addAll(listaUsuariosReservados);
List<String> usuariosArreglados = new ArrayList<String>();
Boolean flagSeDebeArreglar = false;
for (Usuario auxUSU : listaUsuariosReservados) {
	if (auxUSU.getUsername() == null) {
		flagSeDebeArreglar = true;
	}
}
if (flagSeDebeArreglar) {
	try {
		Map<String, Usuario> mapaAux = usuarioService.obtenerMapaUsuarios();
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
		logger.error(e.getMessage(), e);
	}
	this.processEngine.getExecutionService().setVariable(this.workingTask.getExecutionId(), Constantes.VAR_USUARIOS_RESERVADOS, usuariosArreglados);
	return usuariosArreglados;
} else {
	for (Usuario auxUSU : listaUsuariosReservados) {
		usuariosArreglados.add(((Usuario) auxUSU).getUsername());
	}
	return usuariosArreglados;
}
// FIN FIX logica para generar los usuarios faltantes que no se grabaron
// en la BPM
}

private void cargarPopUpUsuariosReservados() throws SecurityNegocioException {
this.usuariosReservados.setMultiline(true);

StringBuilder listaUsuarios = new StringBuilder();
for (String us : this.listaUsuariosReservados) {
	Usuario user = this.usuarioService.obtenerUsuario(us);
	listaUsuarios.append(user.getNombreApellido());
	if (this.listaUsuariosReservados.indexOf(us) == this.listaUsuariosReservados.size() - 1) {
		listaUsuarios.append("(" + user.getUsername() + ")");
	} else {
		listaUsuarios.append("(" + user.getUsername() + "),\n");
	}
}
usuariosReservados.setValue(new String(listaUsuarios));
}

public void onClick$archivosDeTrabajo() {

HashMap<String, Object> hm = new HashMap<String, Object>();
hm.put(ArchivosDeTrabajoComposer.MOSTRAR_UPLOAD, true);
hm.put(ArchivosDeTrabajoComposer.WORK_FLOW_ORIGEN, this.workingTask.getExecutionId());
hm.put(ArchivoDeTrabajoDTO.ARCHIVOS_DE_TRABAJO, this.listaArhivosDeTrabajo);

this.archivosDeTrabajoWindow = (Window) Executions.createComponents("archivosDeTrabajo.zul", this.self, hm);
this.archivosDeTrabajoWindow.setHeight("440px");
this.archivosDeTrabajoWindow.setWidth("550px");
this.archivosDeTrabajoWindow.setTitle("Archivos de trabajo");

// this.archivosDeTrabajoWindow.setMode("modal");
this.archivosDeTrabajoWindow.setClosable(true);
this.archivosDeTrabajoWindow.doModal();
}

public void onClick$historialDocumento() {
HashMap<String, Object> hm = new HashMap<String, Object>();
hm.put("executionId", this.workingTask.getExecutionId());
Window historialDocumentoWindow = (Window) Executions.createComponents("/consultas/consultaHistorialDelDocumento.zul", this.self, hm);
historialDocumentoWindow.setClosable(true);
historialDocumentoWindow.doModal();

}

/**
* Habilita botones de acuerdo a atributos del tipo de documento.
*/
private void habilitarBotones() {
boolean esPrimerFirmante = true;
if (super.tipoDocumento.getEsFirmaConjunta()) {
	String usuarioApoderador = (String) processEngine.getExecutionService().getVariable(this.workingTask.getExecutionId(), Constantes.VAR_USUARIO_APODERADOR);
	String usuarioFirmanteBuscar = StringUtils.EMPTY;
	if (StringUtils.isEmpty(usuarioApoderador)) {
		usuarioFirmanteBuscar = getCurrentUser();
	} else {
		usuarioFirmanteBuscar = usuarioApoderador;
	}
	this.firmante = this.firmaConjuntaService.buscarFirmante(usuarioFirmanteBuscar, this.workingTask.getExecutionId(), false);
	if (this.firmante.getOrden().intValue() != 1) {
		esPrimerFirmante = false;
	}
	if (this.firmaConjuntaService.esUltimoFirmante(this.firmante.getUsuarioFirmante(), this.workingTask.getExecutionId(), false) == true) {
		this.solicitudEnvioCorreo.setVisible(false);
	}
}
/*
 * No se permite la modificación del documento: - Si el tipo de documento no
 * tiene template, caso de importación e incorporación de documentos. - Si
 * el tipo de documento soporta firma conjunta y el primer firmante ya firmó
 * el documento.
 */
if (!esPrimerFirmante) {
	this.usuarioRevisor.setDisabled(true);
	this.selfRevision.setDisabled(true);
	this.mensajeARevisor.setDisabled(true);
	this.revisarButton.setDisabled(true);
	this.firmaDocumento.invalidate();
	this.revisionGrid.invalidate();
}

if (this.tipoDocumento.getEsFirmaExterna()) {
	this.firmarConCertificado.setVisible(false);
	this.firmarButton.setVisible(false);
	this.importarDocumentoButton.setVisible(true);
}

	if(this.tipoDocumento.getTieneToken()) {
		this.firmarConAutoFirma.setVisible(true);
		this.firmarConAutoFirma.setDisabled(false);
	}

}

public Task getSelectedTask() {
return selectedTask;
}

public void setSelectedTask(Task selectedTask) {
this.selectedTask = selectedTask;
}

public void onClick$selfRevision() throws InterruptedException {
MacroEventData med = null;
try {
	this.setVariableWorkFlow(Constantes.VAR_SOLICITUD_ENVIO_MAIL,
			solicitudEnvioCorreo.isChecked());
	this.setVariableWorkFlow(Constantes.VAR_USUARIO_REVISOR, this.getCurrentUser());
	this.historialService.actualizarHistorial(this.workingTask.getExecutionId());
	this.signalExecution(Constantes.TRANSICION_REVISAR, this.getCurrentUser());
	this.actualizarEstadoAvisoFirma();
	this.workingTask.setProgress(100);
	String nombreArchivoTemporal = (String) super.getVariableWorkFlow(
			Constantes.VAR_ARCHIVO_TEMPORAL_FIRMA);
	this.gestionArchivosWebDavService.borrarArchivoTemporalWebDav(nombreArchivoTemporal);
	this.firmaConjuntaService.actualizarEstadoRevisores(this.getWorkingTask().getExecutionId(),
			false);
	med = new MacroEventData();
	med.setExecutionId(this.getWorkingTask().getExecutionId());
} catch (Exception e) {
	logger.error("Error al enviar a modificar en firmar", e);
	Messagebox.show(Labels.getLabel("gedo.error.modificarYoMismo"),
			Labels.getLabel("gedo.general.error"), Messagebox.OK, Messagebox.ERROR);
} finally {
	this.closeAndNotifyAssociatedWindow(med);
}
}

private void mostrarMensajeImportar(String workflowId) throws InterruptedException {
String mensaje = null;

try {
	DocumentoDTO documento = this.buscarDocumentosGedoService
			.buscarDocumentoPorProceso(workflowId);

	// Si no se generó un documento aún, es porque está en reintento
	// cierre o hubo un error grave que implica cancelar el proceso
	if (documento != null) {
		if (super.tipoDocumento.getEsEspecial()) {
			mensaje = Labels.getLabel("gedo.firmarDocumento.documentoGeneradoNumeroEspecial",
					new String[] { documento.getNumero(), documento.getNumeroEspecial() });
		} else {
			mensaje = Labels.getLabel("gedo.firmarDocumento.documentoGenerado",
					new String[] { documento.getNumero() });
		}
		super.mostrarMensajeDescarga(documento.getNumero(), mensaje, this.firmaDocumento);
	} else {
		boolean enEsperaCierre = this.evaluarEstadoEspera(workflowId);
		if (enEsperaCierre) {
			Messagebox.show(Labels.getLabel("gedo.firmarDocumento.reintentoCierre"),
					Labels.getLabel("gedo.general.information"), Messagebox.OK, Messagebox.INFORMATION);
		} else {
			Messagebox.show(Labels.getLabel("gedo.firmarDocumento.error"),
					Labels.getLabel("gedo.general.error"), Messagebox.OK, Messagebox.ERROR);
		}
	}
} catch (Exception e) {
	logger.error("Error al mostrar el mensaje de firma Simple", e);
	Messagebox.show(Labels.getLabel("gedo.firmarDocumento.error.mostrandoMensajeFirma"),
			Labels.getLabel("gedo.general.information"), Messagebox.OK, Messagebox.ERROR);
}
}

private static void clienteBusy(String idMensajeI3) {
Clients.showBusy(Labels.getLabel(idMensajeI3));
}

public void onClick$firmarButton() throws InterruptedException {
clienteBusy("gedo.firma.mensajeFirmando.token");
Clients.evalJavaScript("iniciar();");
}

public void onClick$revisarButton() throws InterruptedException, SecurityNegocioException {
if (this.usuarioRevisor.getSelectedItem() == null) {
	throw new WrongValueException(usuarioRevisor, "Debe seleccionar un usuario.");
}

/**
 * Valida si el usuario revisor pertenece a la misma repartición del usuario
 * actual.
 */
Usuario usuarioReducido = (Usuario) this.usuarioRevisor.getSelectedItem().getValue();
Usuario revisor = getUsuarioService().obtenerUsuario(usuarioReducido.getUsername());

Map<String, Object> datos = new HashMap<String, Object>();
datos.put("funcion", "validarApoderamiento");
datos.put("datos", revisor);
enviarBloqueoPantalla(datos);
}

/**
* Validaciones de adoderamiento y usuarios que pertenecen a la misma
* repartición.
* 
* @throws InterruptedException
*/
public void validarUsuarios(Usuario usuarioAValidar) throws InterruptedException {
super.validarApoderamiento(usuarioAValidar, null);
}

/**
* Realiza las actividades necesarias para enviar a revisar el documento
*/

private void enviarARevisar() throws InterruptedException {

try {
	this.workingTask.setProgress(100);
	Usuario usuarioReducido = (Usuario) this.usuarioRevisor.getSelectedItem().getValue();
	Usuario usuario = getUsuarioService().obtenerUsuario(usuarioReducido.getUsername());

	setVariableWorkFlow(Constantes.VAR_USUARIO_REVISOR, usuario.getUsername());
	setVariableWorkFlow(Constantes.VAR_SOLICITUD_ENVIO_MAIL, solicitudEnvioCorreo.isChecked());
	this.setVariableWorkFlow(Constantes.VAR_MENSAJE_A_REVISOR, this.mensajeARevisor.getValue());
	String nombreArchivoTemporal = (String) super.getVariableWorkFlow(
			Constantes.VAR_ARCHIVO_TEMPORAL_FIRMA);
	this.gestionArchivosWebDavService.borrarArchivoTemporalWebDav(nombreArchivoTemporal);
	this.firmaConjuntaService.actualizarEstadoRevisores(this.getWorkingTask().getExecutionId(),false);
	this.historialService.actualizarHistorial(this.workingTask.getExecutionId());
	signalExecution(Constantes.TRANSICION_REVISAR, this.getCurrentUser());
	this.mostrarMensajeEnvioMail();
} catch (Exception e) {
	logger.error("Error al enviar a revisar en firmar", e);
	Messagebox.show(Labels.getLabel("gedo.error.enviarARevisar"),
			Labels.getLabel("gedo.general.error"), Messagebox.OK, Messagebox.ERROR);
} finally {
	Clients.clearBusy();
	this.closeAndNotifyAssociatedWindow(null);
}
}

public void onClick$firmarConCertificado() throws InterruptedException {
clienteBusy("gedo.firma.mensajeFirmando.certificado");

try {
		setVariableWorkFlow(Constantes.REVISAR_DOCUMENTO_CON_CERTIFICADO, true);
		
		if (Boolean.TRUE.equals(super.tipoDocumento.getEsDobleFactor())) {
			String cod = this.dobleFactorService.obtenerCodigo(this.workingTask.getExecutionId());
			if (cod == null || cod.isEmpty()) {
				cod = RandomStringUtils.randomAlphanumeric(5);
				String codEnc = Utilitarios.encriptar(cod);
				DobleFactorDTO dobleFactor = new DobleFactorDTO(this.getWorkingTask().getExecutionId(), new Date(),
						codEnc, Constantes.ESTADO_PENDIENTE);
				this.dobleFactorService.guardar(dobleFactor);
				envioMailDobleFactor(cod, false);
			}else {
				cod = Utilitarios.desencriptar(cod);
			}
			abrirVentanaDobleFactor(cod);
		} else {
			finalizarFirmaConCertificado();
		}
	} catch (Exception e) {
		exceptionHandler(e);
	} finally {
		super.restaurarVentanaFirma();
	}
}

private void abrirVentanaDobleFactor(String cod) {
Map<String, String> datos = new HashMap<>();
datos.put("codigo", cod);
Window codigoDobleFactorWindow = (Window) Executions
		.createComponents("/taskViews/codigoDobleFactor.zul", this.self, datos);
codigoDobleFactorWindow.setParent(this.firmaDocumento);
codigoDobleFactorWindow.setPosition("center");
codigoDobleFactorWindow.setVisible(true);
codigoDobleFactorWindow.setClosable(true);
codigoDobleFactorWindow.doModal();
}

public void onClick$firmarConAutoFirma() {


	Map<String, Object> mapData = firmaDocumentoService.obtenerDocumentoParaFirmarConAutoFirma(
			getCurrentUser(),
			this.workingTask.getExecutionId());

byte[] encoded = org.apache.commons.codec.binary.Base64.encodeBase64((byte[]) mapData.get("data"));


Clients.evalJavaScript("doSign('"+ new String(encoded) +"',"
		+ " '"+ mapData.get("field") +"', '"+ mapData.get("page") +"')");
Clients.showBusy("Procesando...");
}


protected void envioMailDobleFactor(String codigo, boolean cambiarCodigo) throws InterruptedException {
try {
	if (cambiarCodigo) {
		this.dobleFactorService.actualizarCodigo(codigo, this.workingTask.getExecutionId());
		codigo = Utilitarios.desencriptar(codigo);
	}

	Usuario usuario = this.getUsuarioService().obtenerUsuario(getCurrentUser());
//	StringBuilder template = new StringBuilder();
//	template.append("egoveris");
//	template.append("/templateMailDobleFactor.ftl");

	Date date = Calendar.getInstance().getTime();  
	DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");  
	String strDate = dateFormat.format(date);  
	Object refDoc = processEngine.getExecutionService().getVariable(this.workingTask.getExecutionId(), Constantes.VAR_MOTIVO);
	Object motivDoc = processEngine.getExecutionService().getVariable(this.workingTask.getExecutionId(), Constantes.VAR_MOTIVO);
	Map<String, String> variablesTemplate = new HashMap<>();
	variablesTemplate.put("codigo", codigo);
	variablesTemplate.put("nombreCompletoDestinatario", usuario.getNombreApellido());
	variablesTemplate.put("referenciadocumento", refDoc != null ? (String)refDoc:Labels.getLabel("deo.firma.doblefactor.sinReferencia"));
	variablesTemplate.put("motivo", motivDoc != null ? (String)motivDoc:Labels.getLabel("deo.firma.doblefactor.sinMotivo"));
	variablesTemplate.put("mensaje", Labels.getLabel("deo.firma.doblefactor.avisoFirmaDobreFactor"));
	variablesTemplate.put("fechamodi", strDate);
	variablesTemplate.put("descripcion", "Descripcion");
	variablesTemplate.put("datosdocumento", tipoDocumento.getAcronimo() + " - " + tipoDocumento.getDescripcion());
	
	notificacionMailServiceImpl.componerCorreo(Constantes.MAIL_DOBLEFACTOR_SUBJECT, usuario.getEmail(),
			TEMPLATE_MAIL_DOBLE_FACTOR, variablesTemplate);
} catch (Exception e) {
	exceptionHandler(e);
}
}

protected void validarCodigo() throws InterruptedException {
try {
	finalizarFirmaConCertificado();
	this.dobleFactorService.actualizarEstado(Constantes.ESTADO_USADO, this.workingTask.getExecutionId());
} catch (Exception e) {
	logger.error("Error al firmar con doble factor", e);
	Messagebox.show(Labels.getLabel("gedo.error.dobleFactor"), Labels.getLabel("gedo.general.error"),
			Messagebox.OK, Messagebox.ERROR);
} finally {
	super.restaurarVentanaFirma();
}
}

private void finalizarFirmaConCertificado() throws InterruptedException {
this.actualizarEstadoAvisoFirma();
long start = System.currentTimeMillis();
FirmaResponse fresponse = firmaDocumentoService.firmFaDocumentoConServ(getCurrentUser(),
		this.workingTask.getExecutionId());
logger.info("Proceso de firma en GEDO: " + (System.currentTimeMillis() - start) + " ms.");
this.mostrarMensajeFirma(fresponse);
}

/**
* Agregado por Franco
*/
public void onClick$importarDocumentoButton() throws InterruptedException {
if (super.tipoDocumento == null) {
	this.cancelarTarea();
	super.restaurarVentanaFirma();
	Messagebox.show(Labels.getLabel("gedo.firmarDocumento.error.tipoDocumentoYaNoExiste"),
			Labels.getLabel("gedo.general.error"), Messagebox.OK, Messagebox.ERROR);
	super.closeAndNotifyAssociatedWindow(null);
} else {
	try {
		this.cargarVariablesFirma();
		RequestGenerarDocumento request = super.createAndSetRequest();
		this.actualizarEstadoAvisoFirma();
		this.restaurarVentanaFirma();
		this.workingTask.setProgress(100);
		this.historialService.actualizarHistorial(this.workingTask.getExecutionId());
		this.signalExecution(Constantes.TRANSICION_FIRMA_PENDIENTE, getCurrentUser());
		this.mostrarMensajeImportar(request.getWorkflowId());
		super.closeAndNotifyAssociatedWindow(null);

	} catch (Exception e) {
		super.restaurarVentanaFirma();
		logger.error("Error en Importacion", e);
		Messagebox.show(Labels.getLabel("gedo.importacionDocumento.error"),
				Labels.getLabel("gedo.general.error"), Messagebox.OK, Messagebox.ERROR);
		super.closeAndNotifyAssociatedWindow(null);
	}
}
}

public void onClick$editar() throws InterruptedException {
Executions.sendRedirect("/revisarDocumento.zul");
}

private void mostrarMensajeFirma(FirmaResponse fResp) throws InterruptedException {
String mensaje = null;

if (fResp.getNroSade() != null) {
	if (fResp.getNroEspecial() != null) {
		mensaje = Labels.getLabel("gedo.firmarDocumento.documentoGeneradoNumeroEspecial",
				new String[] { fResp.getNroSade(), fResp.getNroEspecial() });
	} else {
		mensaje = Labels.getLabel("gedo.firmarDocumento.documentoGenerado",
				new String[] { fResp.getNroSade() });
		if (fResp.getNroSadeRectif() != null) {
			mensaje = mensaje.concat("\n").concat("Tambien se firmó el Documento Asociado ").concat(fResp.getNroSadeRectif());
		}
	}

	super.mostrarMensajeDescarga(fResp.getNroSade(), mensaje, this.firmaDocumento);

} else {
	if (fResp.getEstado().equals(Constantes.ACT_ESPERAR_REINTENTO)) {
		mensaje = Labels.getLabel("gedo.firmarDocumento.reintentoCierre");
	} else if (fResp.getEstado().equals(Constantes.ACT_FIRMAR) || fResp.getEstado().equals(Constantes.ACT_REVISAR_FIRMA_CONJUNTA)) {
		mensaje = super.solicitudAvisoFirma.isChecked()
				? Labels.getLabel("gedo.firmarDocumento.envioAvisoFirmaPendiente")
				: Labels.getLabel("gedo.firmarDocumento.noEnvioAviso");
	}
	Messagebox.show(mensaje, Labels.getLabel("gedo.general.information"), Messagebox.OK, Messagebox.INFORMATION);
}
if (estadoExpediente != null) {
Clients.evalJavaScript("parent.closeIframe();");
}
super.closeAndNotifyAssociatedWindow(null);
}

public void prepararHashAFirmar(FirmaEvent eventFirma) throws InterruptedException {

logger.info("Se preparan los certificados");
JSONArray json = (JSONArray) eventFirma.getData().values().toArray()[0];
JSONArray certificadosJson = (JSONArray) json.get(0);

// Muestro window popup
selectCert.doPopup();

// Vacio el combo
comboSelectCert.getItems().clear();

// Lleno el combo
for (Object ob : certificadosJson) {
	JSONObject jsonObject = (JSONObject) ob;
	Comboitem comboI = new Comboitem();
	comboI.setLabel(jsonObject.get("alias").toString());
	comboI.setValue(jsonObject.get("pem").toString());
	comboSelectCert.appendChild(comboI);
}

if (!comboSelectCert.getItems().isEmpty()) {
	comboSelectCert.setSelectedIndex(0);
	comboSelectCert.setVisible(true);
	subtiSinCert.setVisible(false);
	subtiConCert.setVisible(true);
	firmarConToken.setVisible(true);
} else {
	comboSelectCert.setVisible(false);
	subtiSinCert.setVisible(true);
	subtiConCert.setVisible(false);
	firmarConToken.setVisible(false);
}

Clients.clearBusy();
}

private static void exceptionHandler(Exception e) throws InterruptedException {

logger.error(e.getMessage(), e);

String title = Labels.getLabel("gedo.general.error");
String icon = Messagebox.ERROR;

if (e instanceof FirmaDocumentoException) {
	FirmaDocumentoException fde = (FirmaDocumentoException) e;
	if (fde.getErrorType() == FirmaDocumentoException.INFO) {
		title = Labels.getLabel("gedo.general.information");
		icon = Messagebox.INFORMATION;
	}
}

Messagebox.show(e.getMessage(), title, Messagebox.OK, icon);
}

/**
* Finaliza el proceso de firma, Recibe el pdf con el hash firmado,sube el
* documento a un repositorio temporal. Actualiza la tabla historial, y
* gedo_firmantes en caso de firma conjunta. Si ocurre un error realiza
* rollback de los cambios realizados.
*/
public void firmarDocumento(FirmaEvent eventFirma) throws InterruptedException {
try {
	logger.info("Se Recibe el hash firmado, Se firma el Documento...");
	JSONArray infoJson = (JSONArray) eventFirma.getData().values().toArray()[0];
	byte[] hashFirmado = convertBase64ToBytes((String) infoJson.get(0));
	byte[] hashFirmadoRect = convertBase64ToBytes((String) infoJson.get(1));

	FirmaTokenRequest fr = new FirmaTokenRequest(workingTask.getExecutionId(), getCurrentUser(),
			pFR.getFieldName(), this.certificados, hashFirmado, hashFirmadoRect, pFR.getHash(),
			pFR.getHashRect(), pFR.getSignDate());

} catch (Exception e) {
	exceptionHandler(e);
} finally {
	super.restaurarVentanaFirma();
}
}

private static byte[] convertBase64ToBytes(String base64) {
byte[] resp = null;
if (!StringUtils.isEmpty(base64)) {
	resp = DatatypeConverter.parseBase64Binary(base64);
}
return resp;
}

public void errorFirma(FirmaEvent eventFirma) throws InterruptedException {

JSONArray json = (JSONArray) eventFirma.getData().values().toArray()[0];
String errorCode = json.get(0).toString();
String errorDesc = json.get(1).toString();
String ex = json.get(2).toString();

logger.info("Error en la firma " + errorCode + " - " + ex + " - " + errorDesc);
String descMensaje;
if (errorCode != null && errorCode.equals("600")) {
	descMensaje = Labels.getLabel("gedo.token.actDriver");
} else {
	descMensaje = Labels.getLabel("gedo.token.errorGeneral");
}
Messagebox.show(descMensaje, Labels.getLabel("gedo.general.error"), Messagebox.OK, Messagebox.ERROR);

super.restaurarVentanaFirma();
}

/**
* Actualiza la lista de usuarios receptores del aviso de firma dependiendo
* del estado de la solicitud.
*/
private void actualizarEstadoAvisoFirma() {
if (this.solicitudAvisoFirma.isChecked()) {
	this.adicionarReceptoresAviso();
} else {
	this.eliminarReceptoresAviso();
}
}

/**
* Evalua si se el proceso se encuentra en estado de espera para reintentar el
* cierre del documento, después de que se presente alguno de los siguientes
* errores: - Error al generar el número SADE. - Error en interacción con Base
* de datos, para generación de números especiales, o persistencia del
* documento. - Almacenamiento de documento firmado en repositorio.
* 
* @return
*/
public boolean evaluarEstadoEspera(String workflowId) {
boolean esperaReintento = false;
ProcessInstance pInstance = this.processEngine.getExecutionService()
		.findProcessInstanceById(workflowId);
if (pInstance != null) {
	esperaReintento = pInstance.isActive(Constantes.ACT_ESPERAR_REINTENTO);
}
return esperaReintento;
}

/**
* Permite visualizar los usuarios firmantes de este proceso.
* 
* @throws InterruptedException
*/
public void onClick$verFirmantes() throws InterruptedException {
List<Usuario> usuariosFirmaConjunta = this.firmaConjuntaService
		.buscarFirmantesPorProceso(this.workingTask.getExecutionId());
if (usuariosFirmaConjunta != null && usuariosFirmaConjunta.size() != 0) {
	this.usuariosAgregadosTxt.setMultiline(true);
	this.usuariosAgregadosTxt.setRows(usuariosFirmaConjunta.size() + 1);
	StringBuilder listaUsuarios = new StringBuilder();
	for (Usuario us : usuariosFirmaConjunta) {
		listaUsuarios.append(us.getNombreApellido());
		if (usuariosFirmaConjunta.indexOf(us) == usuariosFirmaConjunta.size() - 1) {
			listaUsuarios.append("(" + us.getUsername() + ")");
		} else {
			listaUsuarios.append("(" + us.getUsername() + "),\n");
		}
	}
	this.usuariosAgregadosTxt.setValue(listaUsuarios.toString());
} else {
	this.usuariosAgregadosTxt.setValue(Labels.getLabel("gedo.general.noUsuariosFirmantes"));
}
}

/**
* Permite visualizar los usuarios revisores de este proceso.
* 
* @throws InterruptedException
*/
public void onClick$verRevisores() throws InterruptedException {
List<Usuario> usuariosFirmaConjunta = this.firmaConjuntaService
		.buscarFirmantesPorProceso(this.workingTask.getExecutionId());
if (usuariosFirmaConjunta != null && usuariosFirmaConjunta.size() != 0) {
	this.usuariosRevisoresAgregados.setMultiline(true);
	this.usuariosRevisoresAgregados.setRows(usuariosFirmaConjunta.size() + 1);
	StringBuilder listaRevisores = new StringBuilder();

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

	this.usuariosRevisoresAgregados.setValue(listaRevisores.toString());
} else {
	this.usuariosRevisoresAgregados
			.setValue(Labels.getLabel("gedo.general.noUsuariosRevisores"));
}
}

@Override
protected void asignarTarea() throws InterruptedException {
if (this.usuarioRevisor.getSelectedItem() != null) {
	this.enviarARevisar();
}
}

/**
* Valida condiciones del usuario derivador, que es el usuario al que se le
* enviaría la tarea de rechazo.
* 
* @throws InterruptedException
*/
public void onClick$rechazarFirmaButton() throws InterruptedException {
Map<String, Object> datos = new HashMap<String, Object>();
datos.put("funcion", "rechazarFirma");
enviarBloqueoPantalla(datos);
}

/**
* Invoca a la ventana de rechazar documento, para registrar el motivo.
* 
* @throws InterruptedException
*/
public void rechazarFirma() throws InterruptedException {
this.actualizarEstadoAvisoFirma();
String usuarioReceptorRechazo = null;
String userRechazado = null;
Map<String, String> datos = new HashMap<String, String>();

Usuario usuarioApoderador;
try {
	String userApod = this.getUsuarioApoderado(
			this.usuarioService.obtenerUsuario(this.usuarioDerivador).getUsername());
	if (userApod != null) {
		usuarioApoderador = this.usuarioService.obtenerUsuario(userApod);
		usuarioReceptorRechazo = usuarioApoderador.getNombreApellido();
		userRechazado = usuarioApoderador.getUsername();
	} else {

		usuarioReceptorRechazo = this.usuarioService.obtenerUsuario(usuarioDerivador).getNombreApellido();
		userRechazado = this.usuarioService.obtenerUsuario(usuarioDerivador).getUsername();
	}

} catch (SecurityNegocioException e1) {
	logger.error("Error al obtener los datos del Usuario: " + e1);
}

datos.put("usuarioReceptorRechazo", usuarioReceptorRechazo);
datos.put("user", userRechazado);
datos.put("workflowId", this.workingTask.getExecutionId());
Window rechazarDocumentoVentana = (Window) Executions.createComponents("rechazarDocumento.zul", this.self, datos);
rechazarDocumentoVentana.setParent(this.self);
rechazarDocumentoVentana.setPosition("center");
rechazarDocumentoVentana.setVisible(true);
Clients.clearBusy();
try {
	rechazarDocumentoVentana.doModal();
} catch (Exception e) {
	logger.error("Error al abrir GUI", e);
	Messagebox.show(Labels.getLabel("gedo.supervisadosComposer.msg.noPosibleIniciarVista"),
			Labels.getLabel("gedo.general.titulo.error.comunicacion"), Messagebox.OK, Messagebox.ERROR);
}
}

public void regularizacionDocumentoAdjuntoNuevoRepositorio() {

List<DocumentoAdjuntoDTO> documentosAdjuntos = this.documentoAdjuntoService
		.buscarArchivosDeTrabajoPorProceso(this.workingTask.getExecutionId());
String pathRelativo = (String) super.getVariableWorkFlow(
		Constantes.VAR_ARCHIVO_TEMPORAL_FIRMA);

try {
	if (documentosAdjuntos.isEmpty()
			&& super.tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO) {

		DocumentoAdjuntoDTO documentoAdjunto = llenarDocumentoAdjunto(pathRelativo);
		this.documentoAdjuntoService
				.regularizacionDocumentoAdjuntoNuevoRepositorio(documentoAdjunto);
	}

	this.gestionArchivosWebDavService.borrarArchivoTemporalWebDav(pathRelativo);
} catch (Exception e) {
	logger.error("Mensaje de error", e);
}
}

private DocumentoAdjuntoDTO llenarDocumentoAdjunto(String pathRelativo) {

DocumentoAdjuntoDTO documentoAdjunto = new DocumentoAdjuntoDTO();

documentoAdjunto.setIdTask(this.workingTask.getExecutionId());
documentoAdjunto.setNombreArchivo(pathRelativo); // Pongo como nombre el
// pathRelativo dado
// que no tengo otro
// nombre
documentoAdjunto.setDataArchivo(super.contenidoTemporal);
documentoAdjunto.setUsuarioAsociador(
		Executions.getCurrent().getSession().getAttribute("userName").toString());
documentoAdjunto.setFechaAsociacion(new Date());
documentoAdjunto.setDefinitivo(false);

// Elimina la extension .pdf
pathRelativo = eliminarExtensionPath(pathRelativo);
documentoAdjunto.setPathRelativo(pathRelativo);

return documentoAdjunto;
}

private static String eliminarExtensionPath(String pathRelativo) {
String espacios[] = pathRelativo.split("-");
String idAleatorioConExtension = espacios[3];
String idAleatorioSplit[] = idAleatorioConExtension.split("\\.");

String idAleatorio = idAleatorioSplit[0];
StringBuilder url = new StringBuilder("");
for (int i = 0; i < espacios.length - 1; i++) {
	url.append(espacios[i] + "-");
}
url.append(idAleatorio);
pathRelativo = url.toString();
return pathRelativo;
}

@Override
protected void enviarBloqueoPantalla(Object data) {
clienteBusy("gedo.firmarDocumento.validaciones.procesandoSolicitud");
Events.echoEvent(Events.ON_USER, this.self, data);
}

private void mostrarMensajeEnvioMail() throws InterruptedException {
Boolean falloEnvioSolicitudMail = (Boolean) this.processEngine.getExecutionService().getVariable(this.workingTask.getExecutionId(), Constantes.VAR_SOLICITUD_ENVIO_MAIL_FAIL);
if (falloEnvioSolicitudMail != null && falloEnvioSolicitudMail) {
	Messagebox.show(Labels.getLabel("gedo.error.enviarCorreo"), Labels.getLabel("gedo.general.information"), Messagebox.OK, Messagebox.EXCLAMATION);
}
}

public String iconoArchivosDeTrabajoMensaje() {
// Si el tipo de documento no tiene template pq es para importar
// --> no tiene q mostrar el icono de archivos de trabajo
// Internal Bug #2351
if (super.tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO) {
	return StringUtils.EMPTY;
} else {
	if (!CollectionUtils.isEmpty(listaArhivosDeTrabajo)) {
		this.setMensajeArchivosDeTrabajo(
				Labels.getLabel("gedo.archivosDeTrabajo.popup.conArchivos"));
		return imagenArchivoDeTrabajoConMensaje;
	} else {
		this.setMensajeArchivosDeTrabajo(
				Labels.getLabel("gedo.archivosDeTrabajo.popup.sinArchivos"));
		return imagenArchivoDeTrabajoSinMensaje;
	}
}
}

public String iconoArchivosEmbebidosMensaje() {
if (!CollectionUtils.isEmpty(listaArchivosEmbebidos)) {
	this.setMensajeArchivosEmbebidos(
			Labels.getLabel("gedo.archivosEmbebidos.popup.conArchivos"));
	return Constantes.IMAGEN_CON_ARCHIVO_EMBEBIDO;
} else {
	this.setMensajeArchivosEmbebidos(
			Labels.getLabel("gedo.archivosEmbebidos.popup.sinArchivos"));
	return Constantes.IMAGEN_SIN_ARCHIVO_EMBEBIDO;
}
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

public AnnotateDataBinder getFirmaDocumentoComposerBinder() {
return firmaDocumentoComposerBinder;
}

public void setFirmaDocumentoComposerBinder(AnnotateDataBinder firmaDocumentoComposerBinder) {
this.firmaDocumentoComposerBinder = firmaDocumentoComposerBinder;
}

@SuppressWarnings("unchecked")
private void cargarVariablesFirma() {

super.motivo = (String) super.getVariableWorkFlow(Constantes.VAR_MOTIVO);

try {
	super.documentoMetadata = (List<DocumentoMetadataDTO>) super.getVariableWorkFlow(
			Constantes.VAR_DOCUMENTO_DATA);
} catch (VariableWorkFlowNoExisteException e) {
	logger.debug("Este documento no tiene metadatos", e);
	super.documentoMetadata = new ArrayList<DocumentoMetadataDTO>();
}

try {
	super.numeroSadePapel = (String) super.getVariableWorkFlow(Constantes.VAR_NUMERO_SADE_PAPEL);
} catch (VariableWorkFlowNoExisteException e) {
	logger.error("Error al obtener variable -continua proceso- " + e.getMessage(), e);
	this.setVariableWorkFlow(Constantes.VAR_NUMERO_SADE_PAPEL, StringUtils.EMPTY);
}

}

public Window getFirmaDocumento() {
return firmaDocumento;
}

public void setFirmaDocumento(Window firmaDocumento) {
this.firmaDocumento = firmaDocumento;
}

public void extError(FirmaEvent eventFirma) throws InterruptedException {
JSONArray infoJson = (JSONArray) eventFirma.getData().values().toArray()[0];
String errorMessage = (String) infoJson.get(0);

if (errorMessage.equals("requiere-extension-chrome")) {
	logger.info("Error en ext chrome " + errorMessage);
	Messagebox.show(Labels.getLabel("gedo.firmarDocumento.requiere-extension-chrome"),
			Labels.getLabel("gedo.general.information"), Messagebox.OK, Messagebox.INFORMATION,
			new org.zkoss.zk.ui.event.EventListener() {
				public void onEvent(Event evt) throws InterruptedException {
					if ("onOK".equals(evt.getName())) {
						Clients.showBusy("Procesando");
						Executions
								.sendRedirect(Labels.getLabel("gedo.firmarDocumento.url-extension-chrome"));
					}
				}
			});
} else if (errorMessage.equals("requiere-host-chrome")) {
	logger.info("Error en host chrome " + errorMessage);
	Messagebox.show(Labels.getLabel("gedo.firmarDocumento.requiere-host-chrome"),
			Labels.getLabel("gedo.general.information"), Messagebox.OK, Messagebox.INFORMATION,
			new org.zkoss.zk.ui.event.EventListener() {
				public void onEvent(Event evt) throws InterruptedException {
					if ("onOK".equals(evt.getName())) {
						Executions.sendRedirect(
								Labels.getLabel("gedo.firmarDocumento.url-extension-chrome-msi"));
					}
				}
			});
} else if (errorMessage.equals("requiere-extension-firefox")) {
	logger.info("Error en ext chrome " + errorMessage);
	final String version = (String) infoJson.get(1);
	Messagebox.show(Labels.getLabel("gedo.firmarDocumento.requiere-extension-firefox"),
			Labels.getLabel("gedo.general.information"), Messagebox.OK, Messagebox.INFORMATION,
			new org.zkoss.zk.ui.event.EventListener() {
				public void onEvent(Event evt) throws InterruptedException {
					if ("onOK".equals(evt.getName())) {
						Clients.showBusy("Procesando");
						Clients.evalJavaScript("instalarTokenFirefox(\'"
								+ Labels.getLabel("gedo.firmarDocumento.url-extension-firefox-prefix")
								+ version + ".xpi\');");
					}
				}
			});
}
Clients.clearBusy();
}

public void autoFirmaDocumento(FirmaEvent eventFirma) throws InterruptedException {

try {
this.actualizarEstadoAvisoFirma();

JSONObject jsonDoc =	(JSONObject) ((JSONArray) eventFirma.getData().get("")).get(0);
String docFirmadoBase64 = jsonDoc.get("data").toString();

byte[] docFirmado = org.apache.commons.codec.binary.Base64.decodeBase64(docFirmadoBase64);

FirmaResponse fresponse = firmaDocumentoService.documentoFirmadoConAutoFirma(docFirmado, 
			getCurrentUser(),
			this.workingTask.getExecutionId());

	
this.mostrarMensajeFirma(fresponse);
			

} catch (Exception e) {
exceptionHandler(e);
}finally {
super.restaurarVentanaFirma();
Clients.clearBusy();

}


}

@SuppressWarnings({ "rawtypes", "unchecked" })
public void mensajeErrorAutoFirma(FirmaEvent eventFirma) {

JSONObject jsonError =	(JSONObject) ((JSONArray) eventFirma.getData().get("")).get(0);

if(jsonError.get("errorType").toString().equals("java.lang.Exception") && 
		jsonError.get("mensajeError").toString().contains("No hay ningun")) {
	
	
	Messagebox.show(Labels.getLabel("gedo.autofirmaDocumento.no-hay-certificado"), 
			Labels.getLabel("gedo.general.information"), Messagebox.OK, Messagebox.ERROR);
	
	
	
}else if(jsonError.get("errorType").toString()
		.equals("es.gob.afirma.standalone.ApplicationNotFoundException")) {
				
	Messagebox.show(Labels.getLabel("gedo.autofirmaDocumento.descargar"),
					Labels.getLabel("gedo.general.information"), Messagebox.OK | Messagebox.CANCEL, Messagebox.INFORMATION,
					new org.zkoss.zk.ui.event.EventListener() {
						public void onEvent(Event evt) throws InterruptedException, FileNotFoundException {
							if ("onOK".equals(evt.getName())) {
							descargarAppAutoFirma();
							}
						}
					});

}else {
	Messagebox.show(jsonError.get("mensajeError").toString(), 
			Labels.getLabel("gedo.general.information"), Messagebox.OK, Messagebox.ERROR);
	
}

Clients.clearBusy();
}

public void descargarAppAutoFirma() throws InterruptedException {

try {
		String urlDescarga = appProperty.getString("descargaAutoFirmaLink")!=null
				? appProperty.getString("descargaAutoFirmaLink") : DESCARGAR_AUTO_FIRMA;
		
	Clients.showBusy("Procesando");
	Clients.evalJavaScript("descargarAutoFirma('" + urlDescarga + "')");
	
} catch (Exception e) {
	exceptionHandler(e);
}finally {
	Clients.clearBusy();
	
}

}

}

class ExtensionEventListener implements EventListener {
	private FirmaDocumentoV2Composer composer;

	public ExtensionEventListener(FirmaDocumentoV2Composer composer) {
		this.composer = composer;
	}

	public void onEvent(Event event) throws Exception {
		if (event.getName().equals(Events.ON_NOTIFY)) {
			if (event.getData() == null) {
				this.composer.iconoArchivosDeTrabajoMensaje();
				this.composer.iconoArchivosEmbebidosMensaje();
				this.composer.firmaDocumentoComposerBinder.loadAll();
			} else if (event.getData() instanceof FirmaEvent) {
				FirmaEvent eventFirma = (FirmaEvent) event.getData();
				if (eventFirma.getEventName().equals("onFirmaError")) {
					this.composer.errorFirma(eventFirma);
				} else if (eventFirma.getEventName().equals("onFirmaExtError")) {
					this.composer.extError(eventFirma);
				} else if (eventFirma.getEventName().equals("onFirmaPrepararPdf")) {
					this.composer.prepararHashAFirmar(eventFirma);
				} else if (eventFirma.getEventName().equals("onFirmaFirmarDoc")) {
					this.composer.firmarDocumento(eventFirma);
				} else if (eventFirma.getEventName().equals("onAutoFirmaError")) {
					this.composer.mensajeErrorAutoFirma(eventFirma);
				}else if (eventFirma.getEventName().equals("onAutoFirma")) {
					this.composer.autoFirmaDocumento(eventFirma);
				}
			}
		}
	}
}

class FirmaV2EventListener implements EventListener {
	private FirmaDocumentoV2Composer composer;

	public FirmaV2EventListener(FirmaDocumentoV2Composer composer) {
		this.composer = composer;
	}

	/**
	 * Recibe el evento de firma desde el browser luego que el propio browser
	 * bloqueó el acceso a la UI. http://www.zkoss.org/smalltalks/echoevent/
	 */
	@SuppressWarnings("unchecked")
	public void onEvent(Event event) throws Exception {
		if (event.getName().equals(Events.ON_USER)) {
			Map<String, Object> datos = (Map<String, Object>) event.getData();
			if (datos.get("funcion").equals("validarApoderamiento")) {
				Usuario usuario = (Usuario) datos.get("datos");
				this.composer.validarUsuarios(usuario);
			}
			if (datos.get("funcion").equals("validarReparticion")) {
				Usuario usuario = (Usuario) datos.get("datos");
				this.composer.validacionesReparticion(usuario);
			}
			if (datos.get("funcion").equals("asignarTarea")) {
				this.composer.asignarTarea();
			}
			if (datos.get("funcion").equals("rechazarFirma")) {
				this.composer.rechazarFirma();
			}
			if (datos.get("funcion").equals("regularizacionDocumentoAdjuntoNuevoRepositorio")) {
				this.composer.regularizacionDocumentoAdjuntoNuevoRepositorio();
			}
			if (datos.get("funcion").equals("validarCodigo")) {
				this.composer.validarCodigo();
			}
			if (datos.get("funcion").equals("reenviarMail")) {
				this.composer.envioMailDobleFactor(datos.get("codigo").toString(), true);			
			}
		}
	}
}
