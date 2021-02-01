package com.egoveris.te.base.composer;

import com.egoveris.shareddocument.base.service.IWebDavService;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.te.base.exception.DocumentoOArchivoNoEncontradoException;
import com.egoveris.te.base.exception.VariableWorkFlowNoExisteException;
import com.egoveris.te.base.model.ActividadInbox;
import com.egoveris.te.base.model.ArchivoDeTrabajoDTO;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.ExpedienteAsociadoEntDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.ExpedienteMetadataDTO;
import com.egoveris.te.base.model.ParametrosSistemaExternoDTO;
import com.egoveris.te.base.model.TrataDTO;
import com.egoveris.te.base.model.TrataIntegracionReparticionDTO;
import com.egoveris.te.base.model.TrataReparticionDTO;
import com.egoveris.te.base.service.TrataService;
import com.egoveris.te.base.service.UsuariosSADEService;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.service.iface.IAccesoWebDavService;
import com.egoveris.te.base.service.iface.IActividadExpedienteService;
import com.egoveris.te.base.util.BusinessFormatHelper;
import com.egoveris.te.base.util.ConfiguracionInicialModuloEEFactory;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.IrASistemaOrigenLinkHandler;
import com.egoveris.te.base.util.TipoDocumentoPosible;
import com.egoveris.te.base.util.TipoDocumentoUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;

import org.jbpm.api.ProcessEngine;
import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Label;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class TramitacionEspecialComposer extends EEGenericForwardComposer {

	private static transient Logger logger = LoggerFactory.getLogger(TramitacionEspecialComposer.class);

	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	public static String MEMORANDUM = "ME";
	public static String NOTA = "NO";
	private static final String ESTADO_GUARDA_TEMPORAL = "Guarda Temporal";
	private static final String TRAMITACION_EN_PARALELO = "Paralelo";
	private static final String ES_RESERVADO = " - Expediente Reservado";
	public static final String ESTADO_TRAMITACION = "Tramitacion";
	public static final String ESTADO_EJECUCION = "Ejecucion";

	@Autowired
	private Window datosPropiosWindow;
	@Autowired
	private Window tramitacionEspecialWindow;
	@Autowired
	private Window envio;
	@Autowired
	private Radio expedienteInterno;
	@Autowired
	private Radio expedienteExterno;
	@Autowired
	private Combobox codigoTrata;
	@Autowired
	private Combobox tipoDocumento;
	@Autowired
	private Textbox descripcion;
	@Autowired
	private Textbox estado;
	@Autowired
	private Longbox numeroDocumento;
	@Autowired
	private Textbox motivoExpediente;
	@Autowired
	private Textbox razonSocial;
	@Autowired
	private Textbox nombre;
	@Autowired
	private Textbox apellido;
	@Autowired
	private Textbox email;
	@Autowired
	private Textbox telefono;
	@Autowired
	private Label titulo;
	@Autowired
	public Tab datosdelacaratula;
	// tab
	public Tab tramitacionConjunta;
	public Tab fusion;
	@WireVariable(ConstantesServicios.TRATA_SERVICE)
	private TrataService trataService;
	private List<ExpedienteMetadataDTO> metadatos = new ArrayList<>();
	private DocumentoDTO selectedDocumento;
	private ArchivoDeTrabajoDTO selectedArchivoDeTrabajo;
	private ExpedienteAsociadoEntDTO selectedExpedienteAsociado;
	private List<DocumentoDTO> listaDocumentosEE = new ArrayList<>();
	private List<ArchivoDeTrabajoDTO> listaArchivosDeTrabajoEE = new ArrayList<ArchivoDeTrabajoDTO>();
	private List<ExpedienteAsociadoEntDTO> listaExpedienteAsociado;
	private List<String> tiposDocumentos;
	private ExpedienteElectronicoDTO expedienteElectronico;
	protected Task workingTask = null;
	private Button sistemaExternobtn;
	private ParametrosSistemaExternoDTO params;
	private Usuario datosUsuario;

	// services
	@WireVariable(ConstantesServicios.ACCESO_WEBDAV_SERVICE)
	private IAccesoWebDavService visualizaDocumentoService;
	@WireVariable(ConstantesServicios.ACTIVIDAD_EXPEDIENTE_SERVICE)
	private IActividadExpedienteService actividadExpedienteService;
	private List<ExpedienteAsociadoEntDTO> expedientesAsociadosFiltrados;

	private String loggedUsername;

	/**
	 * Defino los servicios que voy a utilizar
	 */
	@WireVariable(ConstantesServicios.EXP_ELECTRONICO_SERVICE)
	private ExpedienteElectronicoService expedienteElectronicoService;
	
	@WireVariable(ConstantesServicios.PROCESS_ENGINE_SERVICE) 
	protected ProcessEngine processEngine;
	private Component acordeon;
	@WireVariable(ConstantesServicios.WEBDAV_SERVICE)
	private IWebDavService webDavService;
	@WireVariable(ConstantesServicios.USUARIOS_SADE_SERVICE)
	private UsuariosSADEService usuariosSADEService;

	public void onCancelar() {
		this.enviarEventoAcordeon(Events.ON_NOTIFY, null, null);
		this.tramitacionEspecialWindow.detach();
	}

	public void onClick$datosPropios() {
		HashMap<String, Object> hm = new HashMap<>();
		this.metadatos = this.expedienteElectronico.getMetadatosDeTrata();
		hm.put(DatosPropiosTrataCaratulaComposer.METADATOS, this.metadatos);
		hm.put(DatosPropiosTrataCaratulaComposer.ES_SOLO_LECTURA, true);

		this.datosPropiosWindow = (Window) Executions.createComponents("/expediente/datosPropiosDeTrataCaratula.zul",
				this.self, hm);
		this.datosPropiosWindow.setClosable(true);
		this.datosPropiosWindow.doModal();
	}

	/**
	 * Metodo que permite visualizar un archivo de trabajo en el repositorio
	 * 
	 * @autor IES
	 * 
	 *        Servicios utilizados:IAccesoWebDavService
	 *        visualizaDocumentoService Métodos utilizados del
	 *        servicio:visualizarDocumento(String path)
	 * 
	 *        Variables importantes utilizadas:
	 * 
	 * @param String
	 *            path :Cadena que se usa como parámetro en el método
	 *            BufferedInputStream visualizarDocumento(String path) para
	 *            busqueda del Archivo de trabajo, la cual se completa con:
	 * 
	 *            ° pathDocumentoDeTrabajo .- Ubicación del Documento de
	 *            Trabajo. ° nombreSpaceWebDav .- Nombre del Espacio WebDav. °
	 *            archivoDeTrabajo.getNombreArchivo() .- Nombre del Archivo.
	 * 
	 * @param File
	 *            fihero :Fichero utilizado para obtener el tipo de
	 *            fichero(MimeType).
	 * @param String
	 *            tipoFichero : Tipo de fichero del Archivo.
	 * @param BufferedInputStream
	 *            file : Variable que recibe el resultado tipo Inputstream del
	 *            Servicio IAccesoWebDavService visualizaDocumentoService.
	 * 
	 * 
	 * 
	 */

	public void onVisualizarArchivosDeTrabajo() throws Exception {

		try {

			ArchivoDeTrabajoDTO archivoDeTrabajo = this.selectedArchivoDeTrabajo;

			// Se completa el numero con ceros para obtener correctamente el
			// codigo de caratula
			String nombreSpace = BusinessFormatHelper
					.formarPathWebDavApache(this.expedienteElectronico.getCodigoCaratula());

			String nombreSpaceWebDav = nombreSpace.trim();

			String pathDocumentoDeTrabajo = "Documentos_De_Trabajo";

			String fileName = pathDocumentoDeTrabajo + "/" + nombreSpaceWebDav + "/"
					+ archivoDeTrabajo.getNombreArchivo();

			File fichero = new File(archivoDeTrabajo.getNombreArchivo());
			String tipoFichero = new MimetypesFileTypeMap().getContentType(fichero);
			String nombreArchivo = archivoDeTrabajo.getNombreArchivo();

			BufferedInputStream file = this.visualizaDocumentoService.visualizarDocumento(fileName);
			Filedownload.save(file, tipoFichero, nombreArchivo);

		} catch (DocumentoOArchivoNoEncontradoException e) {
			Messagebox.show(Labels.getLabel("ee.tramitacion.archivoNoExisteEnRepositorio"),
					Labels.getLabel("ee.tramitacion.informacion.archivoNoExiste"), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	/**
	 * TODO: persistir los datos del tramite (documento, expediente, etc.)
	 * 
	 * Abro la ventana para hacer el envío correspondiente a otra persona,
	 * sector o repartición.
	 */
	public void onEnviarTramitacion() {

		HashMap<String, Object> hma = new HashMap<>();
		hma.put("expedienteElectronico", this.expedienteElectronico);
		hma.put("estadoAnterior", this.estado.getValue());

		if (!estado.getValue().equals(ESTADO_GUARDA_TEMPORAL)) {

			this.envio = (Window) Executions.createComponents("/pase/envio.zul", null, hma);
		} else {
			this.envio = (Window) Executions.createComponents("/pase/desarchivar.zul", null, hma);
		}
		this.envio.setParent(this.tramitacionEspecialWindow);
		this.envio.setPosition("center");
		this.envio.setClosable(true);
		this.envio.doModal();
	}

	/**
	 * Una vez que el componente se creo, cargo datos, habilito/deshabilito
	 * campos
	 */
	public void doAfterCompose(Component component) throws Exception {
		super.doAfterCompose(component);
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
		component.addEventListener(Events.ON_NOTIFY, new TramitacionEspecialOnNotifyWindowListener(this));
		component.addEventListener(Events.ON_USER, new TramitacionEspecialOnNotifyWindowListener(this));

		loggedUsername = Executions.getCurrent().getSession().getAttribute(ConstantesWeb.SESSION_USERNAME).toString();
		
		this.datosUsuario = this.usuariosSADEService.obtenerUsuarioActual();

		this.setWorkingTask((Task) component.getDesktop().getAttribute("selectedTask"));

		{
			expedienteElectronico = (ExpedienteElectronicoDTO) this.getVariableWorkFlowEEObject("idExpedienteElectronico");
			if (expedienteElectronico != null && expedienteElectronico.getId() != null) {
				setVariableWorkFlow("idExpedienteElectronico", expedienteElectronico.getId());
			}
		}

		this.listaDocumentosEE = ordenarDocumentos(this.expedienteElectronico.getDocumentos());

		Usuario datosUsuario = this.usuariosSADEService.obtenerUsuarioActual();

		String reparticionUsuario = datosUsuario.getCodigoReparticion();
		List<TrataReparticionDTO> reparticionesRestoras = obtenerReparticionesRectora();

		if (expedienteElectronico.getEsReservado()) {

			if (expedienteElectronico.getTrata().getTipoReserva().getTipoReserva().equals(ConstantesWeb.RESERVA_TOTAL)) {

				boolean tieneRolConf = usuariosSADEService.usuarioTieneRol(datosUsuario.getUsername(),						
				    ConstantesWeb.ROL_EE_CONFIDENCIAL);

				if (usuarioPerteneceAReparticionRectora(loggedUsername, reparticionesRestoras)) {
					if (tieneRolConf) {
						this.listaArchivosDeTrabajoEE.addAll(expedienteElectronico.getArchivosDeTrabajo());
					} else {
						this.listaArchivosDeTrabajoEE
								.addAll(expedienteElectronico.obtenerArchivosDeTrabajoAcumulado(reparticionUsuario));
					}
				} else {
					if (tieneRolConf) {
						this.listaArchivosDeTrabajoEE
								.addAll(expedienteElectronico.obtenerArchivosDeTrabajoAcumulado(reparticionUsuario));
					}
				}
			} else {
				this.listaArchivosDeTrabajoEE
						.addAll(expedienteElectronico.obtenerArchivosDeTrabajoAcumulado(reparticionUsuario));
			}
		} else {
			this.listaArchivosDeTrabajoEE.addAll(expedienteElectronico.getArchivosDeTrabajo());
		}

		// CARGA LA LISTA DE EXPEDIENTES ASOCIADOS
		this.listaExpedienteAsociado = new ArrayList<ExpedienteAsociadoEntDTO>();
		cargarExpedientesAsociadosFiltrados();
		String motivo;

		try {
			motivo = (String) getVariableWorkFlow(ConstantesWeb.MOTIVO);
			if (motivo == null) {
				motivo = " ";
			}
		} catch (VariableWorkFlowNoExisteException e) {
			motivo = " ";
		}

		String tipoExpediente;
		boolean esExpedienteInterno = this.expedienteElectronico.getSolicitudIniciadora().isEsSolicitudInterna();
		if (esExpedienteInterno) {
			tipoExpediente = "expedienteInterno";
		} else {
			tipoExpediente = "expedienteExterno";
		}

		String descripcion = (String) getVariableWorkFlow(ConstantesWeb.DESCRIPCION);
		String estado = (String) workingTask.getActivityName();

		String codigoTrata = expedienteElectronico.getTrata().getCodigoTrata();
		String descripcionTrata = this.trataService
				.obtenerDescripcionTrataByCodigo(expedienteElectronico.getTrata().getCodigoTrata());
		String telefono = this.expedienteElectronico.getSolicitudIniciadora().getSolicitante().getTelefono();
		String email = this.expedienteElectronico.getSolicitudIniciadora().getSolicitante().getEmail();

		this.tramitacionEspecialWindow.setTitle(estado);

		if (this.expedienteElectronico.getEsReservado()) {
			this.tramitacionEspecialWindow.setTitle(estado + ES_RESERVADO);

			if (this.expedienteElectronico.getTrata().getTipoReserva().getTipoReserva().equals(ConstantesWeb.RESERVA_TOTAL)
					|| this.expedienteElectronico.getTrata().getTipoReserva().getTipoReserva()
							.equals(ConstantesWeb.RESERVA_PARCIAL)) {
				if (!puedeVerSolapaCaratula(datosUsuario))
					this.datosdelacaratula.setDisabled(true);
			}
		} else {
			this.tramitacionEspecialWindow.setTitle(estado);
		}

		this.motivoExpediente.setValue(motivo);
		this.motivoExpediente.setReadonly(true);
		this.tipoDocumento.setDisabled(true);
		if (this.expedienteExterno.getId().equals(tipoExpediente)) {
			this.expedienteExterno.setChecked(true);
			this.expedienteExterno.setDisabled(true);
			this.expedienteInterno.setChecked(false);
			this.expedienteInterno.setDisabled(true);

			String tipoDocumentoTemp = this.expedienteElectronico.getSolicitudIniciadora().getSolicitante()
					.getDocumento().getTipoDocumento();

			String tipoDocumento;
			if (tipoDocumentoTemp.equals(ConstantesWeb.CU_VALOR)) {
				tipoDocumento = ConstantesWeb.CU_VALOR + "-"
						+ ConstantesWeb.CU_DESCRIPCION;
			} else {
				tipoDocumento = TipoDocumentoPosible.valueOf(tipoDocumentoTemp).getDescripcionCombo();
			}

			String numeroDocumento = this.expedienteElectronico.getSolicitudIniciadora().getSolicitante().getDocumento()
					.getNumeroDocumento();
			String razonSocial = this.expedienteElectronico.getSolicitudIniciadora().getSolicitante()
					.getRazonSocialSolicitante();
			String apellido = this.expedienteElectronico.getSolicitudIniciadora().getSolicitante()
					.getApellidoSolicitante();
			String nombre = this.expedienteElectronico.getSolicitudIniciadora().getSolicitante().getNombreSolicitante();

			this.tipoDocumento.setValue(tipoDocumento);
			this.tipoDocumento.setReadonly(true);
			this.numeroDocumento.setValue(Long.parseLong(numeroDocumento));
			this.numeroDocumento.setReadonly(true);
			this.razonSocial.setValue(razonSocial);
			this.nombre.setValue(nombre);
			this.apellido.setValue(apellido);
			this.razonSocial.setReadonly(true);
			this.apellido.setReadonly(true);
			this.nombre.setReadonly(true);
		} else {
			this.expedienteExterno.setChecked(false);
			this.expedienteInterno.setChecked(true);
			this.expedienteExterno.setDisabled(true);
			this.expedienteInterno.setDisabled(true);
			this.tipoDocumento.setDisabled(true);
			this.numeroDocumento.setDisabled(true);
			this.razonSocial.setDisabled(true);
			this.apellido.setReadonly(true);
			this.nombre.setReadonly(true);
		}
		this.descripcion.setValue(descripcion);
		this.descripcion.setReadonly(true);
		this.codigoTrata.setValue(codigoTrata + "-" + descripcionTrata);
		this.codigoTrata.setReadonly(true);
		this.codigoTrata.setDisabled(true);
		this.estado.setValue(estado);
		this.estado.setReadonly(true);
		this.email.setValue(email);
		this.email.setReadonly(true);
		this.telefono.setValue(telefono);
		this.telefono.setReadonly(true);
		// Se agrega al numero de expediente los 0000 para completar el titulo y
		// luego se los quita nuevamente.
		this.titulo.setValue("Expediente: " + this.expedienteElectronico.getCodigoCaratula());
		this.intercambiarVariablesAcordeon();
		this.enviarEventoAcordeon(Events.ON_RENDER, this.expedienteElectronico, true);
		this.tramitacionConjunta.setDisabled(true);
		this.fusion.setDisabled(true);

		habilitarBotonSistemaExterno();
	}

	private void habilitarBotonSistemaExterno() {
		TrataDTO t = expedienteElectronico.getTrata();
		if (!t.getIntegracionSisExt() && !t.getIntegracionAFJG()) {
			return;
		}

		if (workingTask != null) {
			String wk = workingTask.getExecutionId().split("\\.")[0];
			if (!wk.equalsIgnoreCase("solicitud")) {
				return;
			}
		}
		if (expedienteElectronico.getSistemaCreador()
				.equalsIgnoreCase(ConstantesWeb.SISTEMA_BAC)) {
			return;
		}
		params = ConfiguracionInicialModuloEEFactory.obtenerParametrosPorTrata(t.getId());
		if (params != null) {
			for (TrataIntegracionReparticionDTO trata : params.getReparticionesIntegracion()) {
				if ((trata.getCodigoReparticion().equalsIgnoreCase("--TODAS--")
						|| trata.getCodigoReparticion().equalsIgnoreCase(datosUsuario.getCodigoReparticion()))
						&& trata.isHabilitada()) {
					if (!expedienteElectronico.getTrata().getIntegracionAFJG()) {

						sistemaExternobtn.setLabel("Ir a " + params.getCodigo().toUpperCase());
						sistemaExternobtn.setVisible(true);

					} else if (estadoHabilitadoAFJG()) {

						sistemaExternobtn.setLabel("Enviar a AFJG");
						sistemaExternobtn.setVisible(true);
					}
				}
			}
		}
	}

	private boolean estadoHabilitadoAFJG() {

		return (!this.expedienteElectronico.getEstado().equals(ConstantesWeb.ESTADO_COMUNICACION)
				&& !this.expedienteElectronico.getEstado().equals(ESTADO_EJECUCION));

	}

	public void onClick$sistemaExternobtn() throws InterruptedException {
		try {
			validacionesParaIrASistemaExterno();
		} catch (Exception e) {
			Messagebox.show(e.getMessage(), Labels.getLabel("ee.general.advertencia"), Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if (expedienteElectronico.getTrata().getIntegracionAFJG()) {
			eventoAFJG();
		} else {
			eventoEnvioASistemaExterno();
		}
	}

	public void eventoEnvioASistemaExterno() throws InterruptedException {
		String mensaje = Labels.getLabel("ee.inbox.enviarSistemaExterno",
				new String[] { expedienteElectronico.getCodigoCaratula() });
		String titulo = Labels.getLabel("ee.inbox.enviarSistemaExterno.tituloPopUp");

		Messagebox.show(mensaje, titulo, Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
				new org.zkoss.zk.ui.event.EventListener() {
					public void onEvent(Event evt) throws InterruptedException {
						switch (((Integer) evt.getData()).intValue()) {
						case Messagebox.YES:
							Clients.showBusy("Procesando...");
							Events.echoEvent(Events.ON_USER, (Component) tramitacionEspecialWindow,
									"envioSistemaExterno");
							break;
						case Messagebox.NO:
							break;
						}
					}
				});
	}

	public void enviarASistemaExterno() throws InterruptedException {
		try {
			String host = IrASistemaOrigenLinkHandler.normalizarhost(params.getUrl());

			if (!IrASistemaOrigenLinkHandler.hostAvailabilityCheck(host)) {
				Messagebox.show(
						Labels.getLabel("ee.envio.sistemas.externos.errorAlEnviarEnServicio", new String[] { host }),
						"Error", Messagebox.OK, Messagebox.ERROR);

				return;
			}
			params.setParametros(
					"?usr=" + datosUsuario.getUsername() + "&exp=" + expedienteElectronico.getCodigoCaratula());

			Executions.getCurrent().sendRedirect(params.getURLFull(), "_blank");
			Messagebox.show(
					Labels.getLabel("te.base.composer.enviocomposer.generic.elexpediente") + expedienteElectronico.getCodigoCaratula() + 
					Labels.getLabel("te.tramitacion.enviado")
							+ params.getCodigo().toUpperCase() + Labels.getLabel("te.tramitacion.exito"),
							Labels.getLabel("ee.general.information"), Messagebox.OK, Messagebox.INFORMATION);

			this.closeAssociatedWindow();
		} catch (Exception e) {
			Messagebox.show(Labels.getLabel("te.tramitacion.errorComunicarse") + params.getCodigo(), "Error", Messagebox.OK,
					Messagebox.ERROR);
		} finally {
			Clients.clearBusy();
		}
	}

	private void eventoAFJG() throws InterruptedException {
		String mensaje = Labels.getLabel("ee.inbox.enviarSistemaExterno",
				new String[] { expedienteElectronico.getCodigoCaratula() });
		String titulo = Labels.getLabel("ee.inbox.enviarSistemaExterno.tituloPopUp");

		Messagebox.show(mensaje, titulo, Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
				new org.zkoss.zk.ui.event.EventListener() {
					public void onEvent(Event evt) throws InterruptedException {
						switch (((Integer) evt.getData()).intValue()) {
						case Messagebox.YES:
							Clients.showBusy("Procesando...");
							Events.echoEvent(Events.ON_USER, (Component) tramitacionEspecialWindow,
									"envioExpedienteAFJG");
							break;
						case Messagebox.NO:
							break;
						}
					}
				});
	}

	public void envioExpedienteAFJG() {
		// Eliminada la funcionalidad de envio de expediente a AFJG
	}

	private void validacionesParaIrASistemaExterno() throws InterruptedException {
		for (ExpedienteAsociadoEntDTO ec : expedienteElectronico.getListaExpedientesAsociados()) {
			if (ec.getEsExpedienteAsociadoTC() != null && ec.getEsExpedienteAsociadoTC()) {
				throw new InterruptedException(
						"Es necesario deshacer la Tramitacion Conjunta previo a ir a un sistema externo.");
			} else if (ec.getEsExpedienteAsociadoFusion() != null && ec.getEsExpedienteAsociadoFusion()
					&& !ec.getDefinitivo()) {
				throw new InterruptedException(
						"El expediente se encuentra en proceso de fusión. Debe confirmar o desvincular esta fusión antes de enviar el expediente al sistema externo.");
			}
		}

		if (expedientesAsociadosFiltrados != null && !expedientesAsociadosFiltrados.isEmpty()) {
			for (ExpedienteAsociadoEntDTO ea : expedientesAsociadosFiltrados) {
				if (ea.getEsExpedienteAsociadoTC() == null && ea.getEsExpedienteAsociadoFusion() == null
						&& !ea.getDefinitivo()) {
					throw new InterruptedException(
							"Es necesario finalizar el proceso de asociación previo a ir a un sistema externo.");
				}
			}
		}

		List<String> workIds = new ArrayList<String>();
		workIds.add(expedienteElectronico.getIdWorkflow());
		List<ActividadInbox> actividades = actividadExpedienteService.buscarActividadesVigentes(workIds);

		for (ActividadInbox act : actividades) {
			if (act.getEstado().equals(ConstantesWeb.ESTADO_ABIERTA)
					|| act.getEstado().equals(ConstantesWeb.ESTADO_PENDIENTE)) {
				throw new InterruptedException(
						"Es necesario finalizar las actividades previo a ir a un sistema externo.");
			}
		}

		if (workingTask.getActivityName().equals(TRAMITACION_EN_PARALELO)) {
			throw new InterruptedException(
					"Es necesario finalizar la Tramitacion Paralela previo a ir a un sistema externo.");
		}

		if (expedienteElectronico.getTrata().getIntegracionSisExt()) {
			List<DocumentoDTO> documentos = expedienteElectronico.getDocumentos();
			List<ArchivoDeTrabajoDTO> archivoTrabajo = expedienteElectronico.getArchivosDeTrabajo();
			if (documentos != null) {
				for (DocumentoDTO doc : documentos) {
					if (!doc.getDefinitivo()) {
						throw new InterruptedException(
								"Es necesario hacer definitivos todos los documentos previo a ir a un sistema externo.");
					}
				}
			}

			if (archivoTrabajo != null) {
				for (ArchivoDeTrabajoDTO at : archivoTrabajo) {
					if (!at.isDefinitivo()) {
						throw new InterruptedException(
								"Es necesario hacer definitivos todos los archivos de trabajo previo a ir a un sistema externo.");
					}
				}
			}
		}

		for (TrataIntegracionReparticionDTO t : params.getReparticionesIntegracion()) {
			if ((t.getCodigoReparticion().equalsIgnoreCase("--TODAS--")
					|| t.getCodigoReparticion().equalsIgnoreCase(datosUsuario.getCodigoReparticion()))
					&& t.isHabilitada()) {
				return;
			}
		}

		throw new InterruptedException("Su repartición (" + datosUsuario.getCodigoReparticion()
				+ "). No está habilitada para ir a " + params.getCodigo());
	}

	private List<TrataReparticionDTO> obtenerReparticionesRectora() {
		List<TrataReparticionDTO> reparticionesRestoras = new ArrayList<TrataReparticionDTO>();
		for (int i = 0; i < expedienteElectronico.getTrata().getReparticionesTrata().size(); i++) {
			if (expedienteElectronico.getTrata().getReparticionesTrata().get(i).getReserva() == true) {
				reparticionesRestoras.add(expedienteElectronico.getTrata().getReparticionesTrata().get(i));
			}
		}
		return reparticionesRestoras;
	}

	@SuppressWarnings("unused")
	private Boolean usuarioPerteneceAReparticionRectora(String loggedUsername,
			List<TrataReparticionDTO> reparticionesRestoras) {
		if (reparticionesRestoras.size() != 0) {
			Usuario datosUsuario = this.usuariosSADEService.obtenerUsuarioActual();

			if (!(datosUsuario == null || datosUsuario.getCodigoReparticion() == null)) {
				for (int i = 0; i < reparticionesRestoras.size(); i++) {
					if (reparticionesRestoras.get(i).getCodigoReparticion().trim()
							.equals(ConstantesWeb.TODAS_REPARTICIONES_HABILITADAS)
							|| reparticionesRestoras.get(i).getCodigoReparticion().trim()
									.equals(datosUsuario.getCodigoReparticion()))
						return true;
					break;
				}

			}
		}
		return false;
	}

	private Boolean puedeVerSolapaCaratula(Usuario datosUsuario) {
		return (usuariosSADEService.usuarioTieneRol(datosUsuario.getUsername(),
				ConstantesWeb.ROL_EE_CONFIDENCIAL)
				|| loggedUsername.equalsIgnoreCase(this.expedienteElectronico.getUsuarioCreador()));
	}

	private void intercambiarVariablesAcordeon() {
		if (this.workingTask != null) {
			Executions.getCurrent().getDesktop().setAttribute("selectedTask", this.workingTask);
		}
		Executions.getCurrent().getDesktop().setAttribute("ventanaPadre", this.self);
		this.acordeon = (Component) Executions.getCurrent().getDesktop().getAttribute("acordeonWindow");
	}

	public void enviarEventoAcordeon(String evento, ExpedienteElectronicoDTO ee, Boolean inicial) {
		Map<String, Object> dataHm = new HashMap<>();
		dataHm.put("expedienteElectronico", ee);
		dataHm.put("inicial", inicial);
		Events.sendEvent(evento, this.acordeon, dataHm);

	}

	private List<DocumentoDTO> ordenarDocumentos(final List<DocumentoDTO> documentosFiltrados) {
		Collections.sort(documentosFiltrados, new DocumentoComparator());
		return documentosFiltrados;
	}

	/**
	 * 
	 * @param name:
	 *            nombre de la variable del WF que quiero encontrar
	 * @return objeto guardado en la variable
	 */
	public Object getVariableWorkFlow(String name) {
		Object obj = this.processEngine.getExecutionService().getVariable(this.workingTask.getExecutionId(), name);
		if (obj == null && !name.equals("idExpedienteElectronico")) {
			throw new VariableWorkFlowNoExisteException("No existe la variable para el id de ejecucion. "
					+ this.workingTask.getExecutionId() + ", " + name, null);
		}
		return obj;
	}

	public Object getVariableWorkFlowEEObject(String name) throws VariableWorkFlowNoExisteException {
		Object obj = this.processEngine.getExecutionService().getVariable(this.workingTask.getExecutionId(), name);
		ExpedienteElectronicoDTO ee = null;

		if (obj == null) {
			String auxCodExpediente = "codigoExpediente";
			String codigoEx = (String) this.processEngine.getExecutionService()
					.getVariable(this.workingTask.getExecutionId(), auxCodExpediente);
			try {
				ee = this.expedienteElectronicoService.obtenerExpedienteElectronicoPorCodigo(codigoEx);
			} catch (Exception e) {
			}
			if (codigoEx == null) {

				throw new VariableWorkFlowNoExisteException("No existe la variable para el id de ejecucion. "
						+ this.workingTask.getExecutionId() + ", " + name, null);
			}
		} else {
			ee = expedienteElectronicoService.buscarExpedienteElectronico((Long) obj);

		}

		return ee;
	}

	/**
	 * 
	 * @param name:
	 *            nombre de la variable que quiero setear
	 * @param value:
	 *            valor de la variable
	 */
	public void setVariableWorkFlow(String name, Object value) {
		this.processEngine.getExecutionService().setVariable(this.workingTask.getExecutionId(), name, value);
	}

	/**
	 * 
	 * @param nameTransition:
	 *            nombre de la transición que voy a usar para la proxima tarea
	 * @param usernameDerivador:
	 *            usuario que va a tener asignada la tarea
	 */
	public void signalExecution(String nameTransition, String usernameDerivador) {
		processEngine.getExecutionService().signalExecutionById(this.workingTask.getExecutionId(), nameTransition);
	}

	public void cargarExpedientesAsociadosFiltrados() {

		expedientesAsociadosFiltrados = new ArrayList<>();

		if (!workingTask.getActivityName().equals(TRAMITACION_EN_PARALELO)) {
			for (ExpedienteAsociadoEntDTO expedienteAsociado : this.expedienteElectronico.getListaExpedientesAsociados()) {
				if ((expedienteAsociado.getEsExpedienteAsociadoTC() == null
						|| !expedienteAsociado.getEsExpedienteAsociadoTC())
						&& (expedienteAsociado.getEsExpedienteAsociadoFusion() == null
								|| !expedienteAsociado.getEsExpedienteAsociadoFusion())) {
					this.expedientesAsociadosFiltrados.add(expedienteAsociado);
				}
			}
		} else {
			for (ExpedienteAsociadoEntDTO expedienteAsociado : this.expedienteElectronico.getListaExpedientesAsociados()) {
				if (expedienteAsociado.getEsExpedienteAsociadoTC() != null
						&& !expedienteAsociado.getEsExpedienteAsociadoTC()) {
					if ((workingTask.getCreateTime().compareTo(expedienteAsociado.getFechaAsociacion()) >= 0)
							|| (expedienteAsociado.getIdTask().equals(this.workingTask.getExecutionId()))) {
						this.expedientesAsociadosFiltrados.add(expedienteAsociado);
					}
				}
			}
		}
		// A la lista de filtrados le agrego la lista que esta en memoria.
		this.expedientesAsociadosFiltrados.addAll(listaExpedienteAsociado);

	}

	public IAccesoWebDavService getVisualizaDocumentoService() {
		return visualizaDocumentoService;
	}

	public void setVisualizaDocumentoService(IAccesoWebDavService visualizaDocumentoService) {
		this.visualizaDocumentoService = visualizaDocumentoService;
	}

	public List<ExpedienteAsociadoEntDTO> getExpedientesAsociadosFiltrados() {
		return expedientesAsociadosFiltrados;
	}

	public void setExpedientesAsociadosFiltrados(List<ExpedienteAsociadoEntDTO> expedientesAsociadosFiltrados) {
		this.expedientesAsociadosFiltrados = expedientesAsociadosFiltrados;
	}

	public Task getWorkingTask() {
		return workingTask;
	}

	public void setWorkingTask(Task workingTask) {
		this.workingTask = workingTask;
	}

	public List<DocumentoDTO> getListaDocumentosEE() {
		return listaDocumentosEE;
	}

	public void setListaDocumentosEE(List<DocumentoDTO> listaDocumentosEE) {
		this.listaDocumentosEE = listaDocumentosEE;

	}

	public List<ExpedienteAsociadoEntDTO> getListaExpedienteAsociado() {
		return listaExpedienteAsociado;
	}

	public void setListaExpedienteAsociado(List<ExpedienteAsociadoEntDTO> listaExpedienteAsociado) {
		this.listaExpedienteAsociado = listaExpedienteAsociado;
	}

	public ExpedienteAsociadoEntDTO getSelectedExpedienteAsociado() {
		return selectedExpedienteAsociado;
	}

	public void setSelectedExpedienteAsociado(ExpedienteAsociadoEntDTO selectedExpedienteAsociado) {
		this.selectedExpedienteAsociado = selectedExpedienteAsociado;
	}

	public List<ArchivoDeTrabajoDTO> getListaArchivosDeTrabajoEE() {
		return listaArchivosDeTrabajoEE;
	}

	public void setListaArchivosDeTrabajoEE(List<ArchivoDeTrabajoDTO> listaArchivosDeTrabajoEE) {
		this.listaArchivosDeTrabajoEE = listaArchivosDeTrabajoEE;
	}

	public ArchivoDeTrabajoDTO getSelectedArchivoDeTrabajo() {
		return selectedArchivoDeTrabajo;
	}

	public void setSelectedArchivoDeTrabajo(ArchivoDeTrabajoDTO selectedArchivoDeTrabajo) {
		this.selectedArchivoDeTrabajo = selectedArchivoDeTrabajo;
	}

	public ExpedienteElectronicoDTO getExpedienteElectronico() {
		return expedienteElectronico;
	}

	public void setExpedienteElectronico(ExpedienteElectronicoDTO expedienteElectronico) {
		this.expedienteElectronico = expedienteElectronico;
	}

	public List<String> getTiposDocumentos() {

		if (this.tiposDocumentos == null) {
			this.tiposDocumentos = TipoDocumentoUtils.getTiposDocumentos();
			;
		}
		return this.tiposDocumentos;

	}

	public void setTiposDocumentos(List<String> tiposDocumentos) {
		this.tiposDocumentos = tiposDocumentos;
	}

	public DocumentoDTO getSelectedDocumento() {
		return selectedDocumento;
	}

	public void setSelectedDocumento(DocumentoDTO selectedDocumento) {
		this.selectedDocumento = selectedDocumento;
	}

	public ProcessEngine getProcessEngine() {
		return processEngine;
	}

	public void setProcessEngine(ProcessEngine processEngine) {
		this.processEngine = processEngine;
	}

	public TrataService getTrataService() {
		return trataService;
	}

	public void setTrataService(TrataService trataService) {
		this.trataService = trataService;
	}

}

final class TramitacionEspecialOnNotifyWindowListener implements EventListener {
	private TramitacionEspecialComposer composer;

	public TramitacionEspecialOnNotifyWindowListener(TramitacionEspecialComposer tramitacionEspecialComposer) {
		this.composer = tramitacionEspecialComposer;
	}

	public void onEvent(Event event) throws Exception {
		if (event.getName().equals(Events.ON_NOTIFY)) {
			this.composer.enviarEventoAcordeon(Events.ON_NOTIFY, null, null);
			this.composer.closeAssociatedWindow();
		}
		if (event.getName().equals(Events.ON_USER)) {
			if (event.getData().equals("envioSistemaExterno")) {
				this.composer.enviarASistemaExterno();
			}
		}

	}
}

final class DocumentoComparator implements Comparator<DocumentoDTO> {

	/**
	 * Compara dos instancias de Documento y devuelve la comparación usando el
	 * criterio de más reciente primero en el orden.
	 * 
	 * @param t1
	 *            Primer tarea a comparar
	 * @param t2
	 *            Segunda tarea a comparar con la primera
	 */
	public int compare(DocumentoDTO t1, DocumentoDTO t2) {
		return t2.getFechaAsociacion().compareTo(t1.getFechaAsociacion());

	}
}
