package com.egoveris.te.base.composer;

import com.egoveris.shareddocument.base.model.WebDAVResourceBean;
import com.egoveris.shareddocument.base.service.IWebDavService;
import com.egoveris.te.base.model.ArchivoDeTrabajoDTO;
import com.egoveris.te.base.model.AuditoriaDeConsultaDTO;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.ExpedienteAsociadoEntDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.service.TrataService;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.service.iface.IAuditoriaService;
import com.egoveris.te.base.util.BusinessFormatHelper;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.ConstantesServicios;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.spring.SpringUtil;
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
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;


/**
 * Composer correspondiente a caratulas.zul
 * 
 * @author eumolina
 * 
 */
@Deprecated
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ConsultarCaratulasComposer extends EEGenericForwardComposer {
	
	private static Logger logger = LoggerFactory.getLogger(ConsultarCaratulasComposer.class);

	private static final long serialVersionUID = 5264963751977971300L;

	public static final String MEMORANDUM = "ME";

	public static final String NOTA = "NO";

	private IAuditoriaService auditoriaService;
	@Autowired
	private Textbox reparticionActuacion;
	
	@WireVariable(ConstantesServicios.TRATA_SERVICE)
	private TrataService trataService;
	private ExpedienteElectronicoDTO expedienteElectronico;
	@Autowired
	private Intbox anioSADE;
	@Autowired
	private Window consultaCaratulasWindows;
	@Autowired
	private Intbox numeroSADE;
	@Autowired
	private Label codigoDeTrata;
	@Autowired
	private Label descripcion;
	@Autowired
	private Label tipoDeExpediente;
	@Autowired
	private Label tipoDeDocumento;
	@Autowired
	private Label nombre;
	@Autowired
	private Label apellido;
	@Autowired
	private Label numeroDocumento;
	@Autowired
	private Label razonSocial;
	@Autowired
	private Combobox tipoExpediente;

	// private Listbox expediente;
	@Autowired
	private Listbox expedientesAsociados = null;
	@Autowired
	private Listbox documentosOficiales = null;
	@Autowired
	private Listbox archivosDeTrabajo = null;

	private byte[] inputPdfBytes;

	private List<ArchivoDeTrabajoDTO> archivoDeTrabajoList = new ArrayList<>();

	private List<ExpedienteAsociadoEntDTO> expedientesAsociadosList = new ArrayList<>();

	private List<DocumentoDTO> documentosOficialesList = new ArrayList<>();
	@Autowired
	private AnnotateDataBinder binder;
	@Autowired
	private Bandbox reparticionBusquedaSADE;

	private String numDocumento = null;

	/**
	 * Defino los servicios que voy a utilizar
	 */
	private ExpedienteElectronicoService expedienteElectronicoService;
	private IWebDavService webDavService;

	public IAuditoriaService getAuditoriaService() {
		return auditoriaService;
	}

	public void setAuditoriaService(IAuditoriaService auditoriaService) {
		this.auditoriaService = auditoriaService;
	}

	public Textbox getReparticionActuacion() {
		return reparticionActuacion;
	}

	public void setReparticionActuacion(Textbox reparticionActuacion) {
		this.reparticionActuacion = reparticionActuacion;
	}

	public Intbox getAnioSADE() {
		return anioSADE;
	}

	public void setAnioSADE(Intbox anioSADE) {
		this.anioSADE = anioSADE;
	}

	public Intbox getNumeroSADE() {
		return numeroSADE;
	}

	public void setNumeroSADE(Intbox numeroSADE) {
		this.numeroSADE = numeroSADE;
	}

	public Combobox getTipoExpediente() {
		return tipoExpediente;
	}

	public void setTipoExpediente(Combobox tipoExpediente) {
		this.tipoExpediente = tipoExpediente;
	}

	public ExpedienteElectronicoDTO getExpedienteElectronico() {
		return expedienteElectronico;
	}

	public void setExpedienteElectronico(
			ExpedienteElectronicoDTO expedienteElectronico) {
		this.expedienteElectronico = expedienteElectronico;
	}

	public Window getConsultaCaratulasWindows() {
		return consultaCaratulasWindows;
	}

	public void setConsultaCaratulasWindows(Window consultaCaratulasWindows) {
		this.consultaCaratulasWindows = consultaCaratulasWindows;
	}

	public List<ArchivoDeTrabajoDTO> getArchivoDeTrabajoList() {
		return archivoDeTrabajoList;
	}

	public void setArchivoDeTrabajoList(
			List<ArchivoDeTrabajoDTO> archivoDeTrabajoList) {
		this.archivoDeTrabajoList = archivoDeTrabajoList;
	}

	public List<DocumentoDTO> getDocumentosOficialesList() {
		return documentosOficialesList;
	}

	public void setDocumentosOficialesList(
			List<DocumentoDTO> documentosOficialesList) {
		this.documentosOficialesList = documentosOficialesList;
	}

	public void setExpedientesAsociadosList(
			List<ExpedienteAsociadoEntDTO> expedientesAsociadosList) {
		this.expedientesAsociadosList = expedientesAsociadosList;
	}

	public List<ExpedienteAsociadoEntDTO> getExpedientesAsociadosList() {
		return expedientesAsociadosList;
	}

	public Listbox getExpedientesAsociados() {
		return expedientesAsociados;
	}

	public void setExpedientesAsociados(Listbox expedientesAsociados) {
		this.expedientesAsociados = expedientesAsociados;
	}

	public Listbox getDocumentosOficiales() {
		return documentosOficiales;
	}

	public void setDocumentosOficiales(Listbox documentosOficiales) {
		this.documentosOficiales = documentosOficiales;
	}

	public Listbox getArchivosDeTrabajo() {
		return archivosDeTrabajo;
	}

	public void setArchivosDeTrabajo(Listbox archivosDeTrabajo) {
		this.archivosDeTrabajo = archivosDeTrabajo;
	}

	public IWebDavService getWebDavService() {
		return webDavService;
	}

	public void setWebDavService(IWebDavService webDavService) {
		this.webDavService = webDavService;
	}

	public byte[] getInputPdfBytes() {
		return inputPdfBytes;
	}

	public void setInputPdfBytes(byte[] inputPdfBytes) {
		this.inputPdfBytes = inputPdfBytes;
	}

	public Bandbox getReparticionBusquedaSADE() {
		return reparticionBusquedaSADE;
	}

	public void setReparticionBusquedaSADE(Bandbox reparticionBusquedaSADE) {
		this.reparticionBusquedaSADE = reparticionBusquedaSADE;
	}

	public AnnotateDataBinder getBinder() {
		return binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public void onCreate$consultaCaratulasWindows(Event event) {
		this.binder = (AnnotateDataBinder) event.getTarget().getAttribute(
				"binder", true);

	}

	/**
	 * Consulta dependiendo del numero SADE el expediente correpondiente, para
	 * ser presentado en la vista consulta por caratula
	 */
	public void onConsultarCaratulas() throws InterruptedException {

		validarDatosBuscarExpediente();
		cargarExpediente();
	}

	private void validarDatosBuscarExpediente() {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		String fechaActual = sdf.format(new Date());
		String anioFormateado = fechaActual.substring(6, 10);

		int anioActual = Integer.parseInt(anioFormateado);

		Integer anioValido = Integer.valueOf(anioActual);

		if (this.anioSADE.getValue() == null) {

			throw new WrongValueException(this.anioSADE,
					Labels.getLabel("ee.historialpase.faltaanio"));
		}

		if (this.anioSADE.getValue() > anioValido) {

			throw new WrongValueException(this.anioSADE,
					Labels.getLabel("ee.historialpase.anioInvalido"));
		}

		if (this.numeroSADE.getValue() == null) {

			throw new WrongValueException(this.numeroSADE,
					Labels.getLabel("ee.historialpase.faltatnumero"));
		}

		if ((this.reparticionBusquedaSADE.getValue() == null) || ("".equals(this.reparticionBusquedaSADE
				.getValue()))) {

			throw new WrongValueException(this.reparticionBusquedaSADE,
					Labels.getLabel("ee.historialpase.faltaReparcicion"));
		}
	}

	/**
	 * Carga Expediente Electronico
	 * 
	 **/
	private void cargarExpediente() throws InterruptedException {

		limpiarGrillaExpedienteElectronico();
		limpiarGrillaExpedientesAsociados();
		limpiarGrillaDocumentosAsociados();
		limpiarGrillaArchivosDeTrabajoAsociados();

		this.binder.loadComponent(expedientesAsociados);
		this.binder.loadComponent(documentosOficiales);
		this.binder.loadComponent(archivosDeTrabajo);

		// Grabo la auditoria de la consulta de caratula.
		grabarAuditoriaDeConsulta(tipoExpediente.getValue().toString(),
				anioSADE.getValue(), numeroSADE.getValue(),
				reparticionActuacion.getValue().toString(),
				this.reparticionBusquedaSADE.getValue().toString().trim());

		this.expedienteElectronico = expedienteElectronicoService
				.obtenerExpedienteElectronico(tipoExpediente.getValue()
						.toString(), anioSADE.getValue(),
						numeroSADE.getValue(), this.reparticionBusquedaSADE
								.getValue().toString().trim());

		if (this.expedienteElectronico != null) {

			cargarGrilla();

		} else {

			Messagebox
					.show(Labels.getLabel("ee.caratulas.caratulaNoExiste"),
							Labels.getLabel("ee.caratulas.informacion.caratulaNoExiste"),
							Messagebox.OK, Messagebox.EXCLAMATION);

		}

	}

	/**
	 * Carga informacion a grilla informacionExpediente
	 */
	private void cargarGrilla() {

		cargarDatosExpedienteElectronico();
		cargarExpedientesAsociadosDefinitivos();
		caragarArchivosDeTrabajoDefinitivos();
		caragarDocumentosDefinitivos();

		this.binder.loadComponent(expedientesAsociados);
		this.binder.loadComponent(documentosOficiales);
		this.binder.loadComponent(archivosDeTrabajo);

	}

	public void onLimpiarDatos() {

		limpiarFormulario();
		limpiarGrillaExpedienteElectronico();
		limpiarGrillaExpedientesAsociados();
		limpiarGrillaDocumentosAsociados();
		limpiarGrillaArchivosDeTrabajoAsociados();

		this.binder.loadComponent(expedientesAsociados);
		this.binder.loadComponent(documentosOficiales);
		this.binder.loadComponent(archivosDeTrabajo);

	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {

		super.doAfterCompose(comp);
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
		comp.addEventListener(Events.ON_NOTIFY,
				new ConsultarCaratulasOnNotifyWindowListener(this));

		this.expedienteElectronicoService = (ExpedienteElectronicoService) SpringUtil
				.getBean(ConstantesServicios.EXP_ELECTRONICO_SERVICE);
		this.webDavService = (IWebDavService) SpringUtil
				.getBean(ConstantesServicios.WEBDAV_SERVICE);
		this.auditoriaService = (IAuditoriaService) SpringUtil
				.getBean(ConstantesServicios.AUDITORIA_SERVICE);
		this.binder = new AnnotateDataBinder(comp);
		this.binder.loadAll();
	}

	public void cargarDatosExpedienteElectronico() {

		codigoDeTrata.setValue(expedienteElectronico.getTrata()	.getCodigoTrata());
		descripcion.setValue(this.trataService.obtenerDescripcionTrataByCodigo(expedienteElectronico.getTrata()	.getCodigoTrata()));

		if (expedienteElectronico.getSolicitudIniciadora().isEsSolicitudInterna()) {
			tipoDeExpediente.setValue("Interno");
		} else {
			tipoDeExpediente.setValue("Externo");
		}

		if (expedienteElectronico.getSolicitudIniciadora().getSolicitante()
				.getDocumento() != null) {
			tipoDeDocumento.setValue(expedienteElectronico
					.getSolicitudIniciadora().getSolicitante().getDocumento()
					.getTipoDocumento());

			numDocumento = expedienteElectronico.getSolicitudIniciadora()
					.getSolicitante().getDocumento().getNumeroDocumento();
		}

		if (numDocumento == null) {
			numeroDocumento.setValue("");
		} else {
			numeroDocumento.setValue(numDocumento);
		}

		razonSocial.setValue(expedienteElectronico.getSolicitudIniciadora()
				.getSolicitante().getRazonSocialSolicitante());
		nombre.setValue(expedienteElectronico.getSolicitudIniciadora()
				.getSolicitante().getNombreSolicitante());
		apellido.setValue(expedienteElectronico.getSolicitudIniciadora()
				.getSolicitante().getApellidoSolicitante());

	}

	public void cargarExpedientesAsociadosDefinitivos() {

		Iterator<ExpedienteAsociadoEntDTO> itcdc = expedienteElectronico
				.getListaExpedientesAsociados().iterator();

		ExpedienteAsociadoEntDTO ea;

		while (itcdc.hasNext()) {

			ea = (ExpedienteAsociadoEntDTO) itcdc.next();

			if (ea.getDefinitivo()) {

				this.expedientesAsociadosList.add(ea);

			}
		}

	}

	public void caragarArchivosDeTrabajoDefinitivos() {

		Iterator<ArchivoDeTrabajoDTO> itadt = expedienteElectronico
				.getArchivosDeTrabajo().iterator();

		ArchivoDeTrabajoDTO adt;

		while (itadt.hasNext()) {

			adt = (ArchivoDeTrabajoDTO) itadt.next();

			if (adt.isDefinitivo()) {

				this.archivoDeTrabajoList.add(adt);

			}
		}

	}

	public void caragarDocumentosDefinitivos() {

		Iterator<DocumentoDTO> itd = expedienteElectronico.getDocumentos()
				.iterator();

		DocumentoDTO doc;
		int posicion = 0;
		while (itd.hasNext()) {

			doc = (DocumentoDTO) itd.next();

			if (doc.getDefinitivo()) {
				doc.setPosicion(posicion + 1);
				this.documentosOficialesList.add(doc);
			}
		}

	}

	/**
	 * Metodo que visualiza un Archivo de trabajo
	 */
	public void onVisualizarArchivo() throws InterruptedException {

		try {

			ArchivoDeTrabajoDTO archivoDeTrabajo = this.archivoDeTrabajoList
					.get(archivosDeTrabajo.getSelectedIndex());

			String nombreSpace = BusinessFormatHelper
					.formarPathWebDavApache(this.expedienteElectronico
							.getCodigoCaratula());

			String pathDocumentoDeTrabajo = "Documentos_De_Trabajo";

			String fileName = pathDocumentoDeTrabajo + "/" + nombreSpace + "/"
					+ archivoDeTrabajo.getNombreArchivo();

			WebDAVResourceBean file = webDavService.getFile(fileName, null,
					null);

			Filedownload.save(file.getArchivo(), file.getMimeType(),
					file.getNombre());

		} catch (Exception e) {

			logger.error(e.getMessage());

			Messagebox
					.show(Labels
							.getLabel("ee.tramitacion.archivoNoExisteEnRepositorio"),
							Labels.getLabel("ee.tramitacion.informacion.archivoNoExiste"),
							Messagebox.OK, Messagebox.EXCLAMATION);

		}

	}

	/**
	 * Metodo que visuliza un documento oficial
	 */
	public void onVisualizarDocumentos() throws InterruptedException {

		try {

			DocumentoDTO documentoOficial = this.documentosOficialesList
					.get(documentosOficiales.getSelectedIndex());

			String numeroSadeConEspacio = documentoOficial.getNumeroSade();

			String fileName;
			String pathDocumento = "SADE";

			if (MEMORANDUM.equals(documentoOficial.getNumeroSade().substring(0,
					2))
					|| NOTA.equals(documentoOficial.getNumeroSade().substring(
							0, 2))) {
				String pathComunicaciones = BusinessFormatHelper
						.nombreCarpetaWebDavComunicaciones(numeroSadeConEspacio);

				// Los documentos de comunicaciones caen sobre
				// guarda-documental/Actuacion/numeroSade
				fileName = pathDocumento + "/" + pathComunicaciones + "/"
						+ documentoOficial.getNombreArchivo();
			} else {

				// Los documentos de gedo caen sobre
				// guarda-documental/numeroSade
				String pathGedo = BusinessFormatHelper
						.nombreCarpetaWebDavGedo(numeroSadeConEspacio);
				fileName = pathDocumento + "/" + pathGedo + "/"
						+ documentoOficial.getNombreArchivo();
			}

			WebDAVResourceBean file = webDavService.getFile(fileName, null,
					null);

			Filedownload.save(file.getArchivo(), file.getMimeType(),
					file.getNombre());

		} catch (Exception e) {

			logger.error(e.getMessage());

			Messagebox
					.show(Labels
							.getLabel("ee.tramitacion.documentoNoExisteEnRepositorio"),
							Labels.getLabel("ee.tramitacion.informacion.documentoNoExiste"),
							Messagebox.OK, Messagebox.EXCLAMATION);

		}

	}

	public void refreshArchivosDeTrabajoAsociados() {

		this.binder.loadComponent(this.archivosDeTrabajo);
	}

	public void refreshDocumentosAsociados() {

		this.binder.loadComponent(this.documentosOficiales);
	}

	public void refreshExpedientesAsociados() {

		this.binder.loadComponent(this.expedientesAsociados);
	}

	public void limpiarFormulario() {

		anioSADE.setValue(null);
		numeroSADE.setValue(null);
		reparticionBusquedaSADE.setValue(null);
		anioSADE.setFocus(true);

	}

	public void limpiarGrillaExpedienteElectronico() {

		codigoDeTrata.setValue(null);
		descripcion.setValue(null);
		tipoDeExpediente.setValue(null);
		tipoDeDocumento.setValue(null);
		numeroDocumento.setValue(null);
		nombre.setValue(null);
		apellido.setValue(null);
		razonSocial.setValue(null);
	}

	public void limpiarGrillaExpedientesAsociados() {

		this.expedientesAsociadosList.removeAll(getExpedientesAsociadosList());

	}

	public void limpiarGrillaDocumentosAsociados() {

		this.documentosOficialesList.removeAll(getDocumentosOficialesList());

	}

	public void limpiarGrillaArchivosDeTrabajoAsociados() {

		this.archivoDeTrabajoList.removeAll(getArchivoDeTrabajoList());

	}

	final class ConsultarCaratulasOnNotifyWindowListener implements
			EventListener {
		private ConsultarCaratulasComposer composer;

		public ConsultarCaratulasOnNotifyWindowListener(
				ConsultarCaratulasComposer consultarCaratulaComposer) {
			this.composer = consultarCaratulaComposer;
		}

		public void onEvent(Event event) throws Exception {
			if (event.getName().equals(Events.ON_NOTIFY)) {

				limpiarFormulario();
				limpiarGrillaExpedienteElectronico();
				limpiarGrillaExpedientesAsociados();
				limpiarGrillaDocumentosAsociados();
				limpiarGrillaArchivosDeTrabajoAsociados();

				this.composer.refreshExpedientesAsociados();
				this.composer.refreshDocumentosAsociados();
				this.composer.refreshArchivosDeTrabajoAsociados();

			}
		}

	}

	public void grabarAuditoriaDeConsulta(String tipoActuacion,
			Integer anioActuacion, Integer numeroActuacion,
			String reparticionActuacion, String reparticionUsuario) {

		AuditoriaDeConsultaDTO auditoriaDeConsulta = new AuditoriaDeConsultaDTO();

		auditoriaDeConsulta.setTipoActuacion(tipoActuacion);
		auditoriaDeConsulta.setAnioActuacion(anioActuacion);
		auditoriaDeConsulta.setNumeroActuacion(numeroActuacion);
		auditoriaDeConsulta.setReparticionActuacion(reparticionActuacion);
		auditoriaDeConsulta.setReparticionUsuario(reparticionUsuario);
		String usuario = (String) Executions.getCurrent().getSession()
				.getAttribute(ConstantesWeb.SESSION_USERNAME);
		auditoriaDeConsulta.setUsuario(usuario);
		auditoriaDeConsulta.setFechaConsulta(new Date());

		auditoriaService.grabarAuditoriaDeConsulta(auditoriaDeConsulta);
	}

	public TrataService getTrataService() {
		return trataService;
	}

	public void setTrataService(TrataService trataService) {
		this.trataService = trataService;
	}
}
