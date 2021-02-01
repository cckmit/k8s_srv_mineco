package com.egoveris.te.base.composer;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.spring.SpringUtil;
import org.zkoss.util.media.Media;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.ListModels;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.te.base.exception.external.ActividadException;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.TipoDocumentoDTO;
import com.egoveris.te.base.service.DocumentoGedoService;
import com.egoveris.te.base.service.DocumentoManagerService;
import com.egoveris.te.base.service.TipoDocumentoService;
import com.egoveris.te.base.service.UsuariosSADEService;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.service.iface.IActividadExternalService;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.model.model.GuardaTempRequest;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class InicioDocumentoComposer extends ValidarApoderamientoComposer {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6552519369569565165L;

	@Autowired
	private Window inicioDocumentoWindow;

	@Autowired
	private Bandbox familiaEstructuraTree;

	@Autowired
	private Window tramitacionWindow;

	private TipoDocumentoDTO selectedTipoDocumento;

	private AnnotateDataBinder inicioDocumentoBinder;

	private Label labelDetalle;

	private Image imagenDocumentoTemplate;
	private Image imagenDocumentoEspecial;
	private Image imagenDocumentoFirmaExterna;
	private Image imagenDocumentoReservado;
	private Image imagenDocumentoFirmaConToken;
	private Image imagenDocumentoFirmaConjunta;

	private String mensaje;

	private Usuario datosUsuario;
	private Usuario usuarioProductorInfo;
	private String loggedUsername;

	@Autowired
	private Combobox usuarioProductor;
	
	@Autowired
	private Button inciarDocumentoButton;

	@WireVariable(ConstantesServicios.USUARIOS_SADE_SERVICE)
	private UsuariosSADEService usuariosSADEService;
	
	@WireVariable(ConstantesServicios.TIPO_DOCUMENTO_SERVICE)
	private TipoDocumentoService tipoDocumentoService; 
	@WireVariable(ConstantesServicios.DOCUMENTO_MANAGER_SERVICE)
	private DocumentoManagerService documentoManagerService;
//	@WireVariable("documentoGedoServiceImpl")
	private DocumentoGedoService documentoGedoService;
	private IActividadExternalService actividadExternalService;

	private ExpedienteElectronicoDTO ee;

	private AnnotateDataBinder binder;
	
	private String sistema;
	private String transacionId;
	@WireVariable(ConstantesServicios.EXP_ELECTRONICO_SERVICE)
	private ExpedienteElectronicoService expedienteElectronicoService;
	private boolean iniciarDocumentoYoMismo;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
		comp.addEventListener(Events.ON_USER, new InicioDocumentoComposerListener(this));
		this.ee = (ExpedienteElectronicoDTO) Executions.getCurrent().getArg()
				.get("expediente");
		familiaEstructuraTree.addEventListener(Events.ON_NOTIFY,
				new InicioDocumentoComposerListener(this));
		this.labelDetalle.setValue(null);
		this.documentoGedoService = (DocumentoGedoService) SpringUtil
				.getBean("documentoGedoServiceImpl");
		this.actividadExternalService = (IActividadExternalService) SpringUtil
				.getBean("actividadExternalServiceImpl"); 
		loggedUsername = Executions.getCurrent().getSession()
				.getAttribute(ConstantesWeb.SESSION_USERNAME).toString();
		this.datosUsuario = this.usuariosSADEService.obtenerUsuarioActual();

		usuarioProductor.setModel(ListModels.toListSubModel(new ListModelList(
				this.getUsuariosSADEService().getTodosLosUsuarios()),
				new UsuariosComparatorConsultaExpediente(), 30));
		this.binder = new AnnotateDataBinder(usuarioProductor);
		binder.loadComponent(usuarioProductor);
		
		
		Execution execution = Executions.getCurrent();
		transacionId = execution.getParameter("transacionId");
		sistema = execution.getParameter("sistema");
		
		this.inicioDocumentoBinder = new AnnotateDataBinder(comp);
		this.inicioDocumentoBinder.loadAll();
	}

	public void cargarTipoDocumento(TipoDocumentoDTO data) {
		this.familiaEstructuraTree.setText(data.getAcronimo().toUpperCase());
		this.familiaEstructuraTree.close();
		setSelectedTipoDocumento(data);
		onSelectTipoDocumento();
		this.inicioDocumentoBinder.loadAll();
	}

	/**
	 * Invoca diferentes validaciones antes de enviar a producir el documento
	 * por otro usuario.
	 * 
	 * @throws InterruptedException
	 */
	public void onIniciarDocumento() throws InterruptedException {
		this.iniciarDocumentoYoMismo = false;
		this.iniciarDocumento();
	}
	
	/**
	 * On iniciar documento yo mismo.
	 *
	 * @throws InterruptedException the interrupted exception
	 */
	public void onIniciarDocumentoYoMismo() throws InterruptedException{
		this.iniciarDocumentoYoMismo = true;
		this.iniciarDocumento();
	}
	
	/**
	 * Iniciar documento.
	 *
	 * @param yoMismo the yo mismo
	 * @throws InterruptedException the interrupted exception
	 */
	private void iniciarDocumento() throws InterruptedException{
		try {
			if (this.getSelectedTipoDocumento() == null) {
				throw new WrongValueException(this.familiaEstructuraTree,
						Labels.getLabel("ee.general.tipoDocumentoInvalido"));
			}
			if (!iniciarDocumentoYoMismo) {
				if ((this.usuarioProductor.getSelectedItem() == null)
						|| this.usuarioProductor.getSelectedItem().getValue() == "") {
					throw new WrongValueException(this.usuarioProductor,
							Labels.getLabel("ee.iniciarDocumento.errores.faltausuarioProductor"));
				}
				Usuario item = (Usuario) this.usuarioProductor.getSelectedItem().getValue();
				usuarioProductorInfo = usuariosSADEService.getDatosUsuario(item.getUsername());
			} else {
				usuarioProductorInfo = this.datosUsuario;
			}

			Map<String, Object> datos = new HashMap<>();
			datos.put("funcion", "validarApoderamiento");
			datos.put("datos", usuarioProductorInfo);
			enviarBloqueoPantalla(datos);
		} finally {
			Clients.clearBusy();
			inicioDocumentoBinder.loadComponent(inicioDocumentoWindow);
		}
	}

	/**
	 * Iniciar tarea.
	 *
	 * @param usuarioProductorInfo the usuario productor info
	 * @throws InterruptedException the interrupted exception
	 */
	private void iniciarTarea() throws InterruptedException {
		Clients.clearBusy();
		DocumentoDTO doc = documentoGedoService.generarPeticionGeneracionDocumentoGEDO(ee, loggedUsername,
				usuarioProductorInfo, this.getMensaje(), this.getSelectedTipoDocumento());

		GuardaTempRequest request = new GuardaTempRequest();
		// En esta instancia no esta creado el expediente
		request.setMotivo("Petición generación de Documento en DEO");
		request.setNumeroExpediente(ee.getCodigoCaratula());
		request.setTipo(ConstantesWeb.PETICION_PENDIENTE_GEDO);
		// Guardo el numero referencial de generar tarea en el valor
		request.setValor(doc.getNumeroSade());
		request.setUserMailDestino(usuarioProductorInfo.getEmail());

		try {
			Long result = actividadExternalService.generarActividadGuardaTemporal(request);
			inicioDocumentoWindow.onClose();

			if (result != 0 && !iniciarDocumentoYoMismo) {
				Messagebox.show(
						Labels.getLabel("te.inicioDocumento.errorSolicitud"),
						Labels.getLabel("ee.general.information"), Messagebox.OK, Messagebox.INFORMATION, new EventListener() {
							public void onEvent(Event evt) {
							}
						});
			}

			// SE COMENTA PORQUE ABRE UNA VENTANA EN BLANCO
			/*
			 * if (yoMismo) { String numeroWorkflow = doc.getNumeroSade().substring(12);
			 * Executions.getCurrent()
			 * .sendRedirect(this.documentoGedoService.getPathTareaExterna() +
			 * numeroWorkflow, "_blank"); }
			 */

			// Se actualiza la fecha de modificacion
			this.ee.setFechaModificacion(new Date());
			// expedienteElectronicoService.vincularDocumentoGedo(getEe(), doc,
			// loggedUsername);
			expedienteElectronicoService.modificarExpedienteElectronico(ee);
//				Events.echoEvent(Events.ON_USER, (Component) tramitacionWindow, "actualizarTramitacionRender");

			if (iniciarDocumentoYoMismo) {
				Map<String, Object> mapVars = new HashMap<>();
				mapVars.put("numeroSade", doc.getNumeroSade());
				mapVars.put("tramitacionWindow", tramitacionWindow);

				Window producirDocumentoIfr = (Window) Executions
						.createComponents("/expediente/producirDocumento/producirDocumentoIfr.zul", null, mapVars);
				producirDocumentoIfr.doModal();
//					Events.echoEvent(Events.ON_USER, (Component) tramitacionWindow, "actualizarTramitacionRender");
			} else {
				Events.echoEvent(Events.ON_USER, (Component) tramitacionWindow, "actualizarTramitacionRender");
			}
      Clients.clearBusy();
		} catch (ActividadException e) {
			throw new InterruptedException(e.getMessage());
		}
	}
	
	public void onUpload$inciarDocumentoButton(UploadEvent event) throws IOException {
		int limitBytes = 5242880; // 5 Mb
		int fileSizeBytes = event.getMedia().getStreamData().available();
		if (event.getMedias() != null && fileSizeBytes <= limitBytes) {
			Media mediaUpld = event.getMedia();
			if ("pdf".equalsIgnoreCase(mediaUpld.getFormat())) {
				Messagebox.show(Labels.getLabel("te.inicioDocumento.question") + mediaUpld.getName() + Labels.getLabel("te.inicioDocumento.questions"),
						Labels.getLabel("ee.general.question"), Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
						(EventListener<Event>) evt -> {
							Usuario usuario = this.usuariosSADEService.obtenerUsuarioActual();
							if (((Integer) evt.getData()).equals(Messagebox.YES)) {
								String acronimoGedo = documentoManagerService.generarDocumentoGedo(mediaUpld,
										"NOTA",usuario);
								expedienteElectronicoService.vinculacionDocumentosAExpedienteTe(acronimoGedo,
										this.ee.getCodigoCaratula(),loggedUsername );
								Messagebox.show(Labels.getLabel("te.inicioDocumento.agregado"), Labels.getLabel("ee.general.information"), Messagebox.OK,
										Messagebox.INFORMATION);
//								compBotoninciarDocumento(); 
								inciarDocumentoButton.setVisible(false);
							}
						});
			} else {
				Messagebox.show(Labels.getLabel("te.inicioDocumento.pdf"), "Error", Messagebox.OK, Messagebox.EXCLAMATION);
			}
		} else {
			Messagebox.show(Labels.getLabel("ee.general.mensaje.fileupload"), Labels.getLabel("ee.general.advertencia"),
					Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	@Override
	protected void asignarTarea() throws Exception {
		this.iniciarTarea();
	}
	
	@Override
	protected void enviarBloqueoPantalla(Object data) {
		Clients.showBusy(Labels.getLabel("ee.general.procesando.procesandoSolicitud"));
		Events.echoEvent(Events.ON_USER, this.self, data);
	}

	public void onSelectTipoDocumento() {
		this.labelDetalle.setValue(this.getSelectedTipoDocumento()
				.getDescripcionGedo());

		if (this.getSelectedTipoDocumento().getTieneTemplate()
				.equals(Boolean.TRUE)) {
			this.imagenDocumentoTemplate.setSrc(ConstantesWeb.IMG_TIENE_TEMPLATE);
			this.imagenDocumentoTemplate.setVisible(true);
			this.imagenDocumentoTemplate.setTooltip(Labels
					.getLabel("ee.general.imagenesCaracteristicas.template"));
		} else {
			this.imagenDocumentoTemplate.setVisible(false);
		}

		if (this.getSelectedTipoDocumento().getEsEspecial()
				.equals(Boolean.TRUE)) {
			this.imagenDocumentoEspecial.setSrc(ConstantesWeb.IMG_ES_ESPECIAL);
			this.imagenDocumentoEspecial.setVisible(true);
			this.imagenDocumentoEspecial.setTooltip(Labels
					.getLabel("ee.general.imagenesCaracteristicas.especial"));
		} else {
			this.imagenDocumentoEspecial.setVisible(false);
		}

		if (this.getSelectedTipoDocumento().getEsFirmaExterna()
				.equals(Boolean.TRUE)) {
			this.imagenDocumentoFirmaExterna
					.setSrc(ConstantesWeb.IMG_ES_FIRMA_EXTERNA);
			this.imagenDocumentoFirmaExterna.setVisible(true);
			this.imagenDocumentoFirmaExterna
					.setTooltip(Labels
							.getLabel("ee.general.imagenesCaracteristicas.firmaExterna"));
		} else {
			this.imagenDocumentoFirmaExterna.setVisible(false);
		}

		if (this.getSelectedTipoDocumento().getEsConfidencial()
				.equals(Boolean.TRUE)) {
			this.imagenDocumentoReservado
					.setSrc(ConstantesWeb.IMG_ES_CONFIDENCIAL);
			this.imagenDocumentoReservado.setVisible(true);
			this.imagenDocumentoReservado.setTooltip(Labels
					.getLabel("ee.general.imagenesCaracteristicas.reservado"));
		} else {
			this.imagenDocumentoReservado.setVisible(false);
		}

		if (this.getSelectedTipoDocumento().getTieneToken()
				.equals(Boolean.TRUE)) {
			this.imagenDocumentoFirmaConToken
					.setSrc(ConstantesWeb.IMG_TIENE_TOKEN);
			this.imagenDocumentoFirmaConToken.setVisible(true);
			this.imagenDocumentoFirmaConToken.setTooltip(Labels
					.getLabel("ee.general.imagenesCaracteristicas.token"));
		} else {
			this.imagenDocumentoFirmaConToken.setVisible(false);
		}

		if (this.getSelectedTipoDocumento().getEsFirmaConjunta()
				.equals(Boolean.TRUE)) {
			this.imagenDocumentoFirmaConjunta
					.setSrc(ConstantesWeb.IMG_ES_FIRMA_CONJUNTA);
			this.imagenDocumentoFirmaConjunta.setVisible(true);
			this.imagenDocumentoFirmaConjunta
					.setTooltip(Labels
							.getLabel("ee.general.imagenesCaracteristicas.firmaConjunta"));
		} else {
			this.imagenDocumentoFirmaConjunta.setVisible(false);
		}

		this.inicioDocumentoBinder.loadComponent(this.labelDetalle);
	}

	public void setSelectedTipoDocumento(TipoDocumentoDTO selectedTipoDocumento) {
		this.selectedTipoDocumento = selectedTipoDocumento;
	}

	public TipoDocumentoDTO getSelectedTipoDocumento() {
		return selectedTipoDocumento;
	}

	public Bandbox getFamiliaEstructuraTree() {
		return familiaEstructuraTree;
	}

	public void setFamiliaEstructuraTree(Bandbox familiaEstructuraTree) {
		this.familiaEstructuraTree = familiaEstructuraTree;
	}

	public Window getInicioDocumentoWindow() {
		return inicioDocumentoWindow;
	}

	public void setInicioDocumentoWindow(Window inicioDocumentoWindow) {
		this.inicioDocumentoWindow = inicioDocumentoWindow;
	}

	public Image getImagenDocumentoTemplate() {
		return imagenDocumentoTemplate;
	}

	public void setImagenDocumentoTemplate(Image imagenDocumentoTemplate) {
		this.imagenDocumentoTemplate = imagenDocumentoTemplate;
	}

	public Image getImagenDocumentoEspecial() {
		return imagenDocumentoEspecial;
	}

	public void setImagenDocumentoEspecial(Image imagenDocumentoEspecial) {
		this.imagenDocumentoEspecial = imagenDocumentoEspecial;
	}

	public Image getImagenDocumentoFirmaExterna() {
		return imagenDocumentoFirmaExterna;
	}

	public void setImagenDocumentoFirmaExterna(Image imagenDocumentoFirmaExterna) {
		this.imagenDocumentoFirmaExterna = imagenDocumentoFirmaExterna;
	}

	public Image getImagenDocumentoReservado() {
		return imagenDocumentoReservado;
	}

	public void setImagenDocumentoReservado(Image imagenDocumentoReservado) {
		this.imagenDocumentoReservado = imagenDocumentoReservado;
	}

	public Image getImagenDocumentoFirmaConToken() {
		return imagenDocumentoFirmaConToken;
	}

	public void setImagenDocumentoFirmaConToken(
			Image imagenDocumentoFirmaConToken) {
		this.imagenDocumentoFirmaConToken = imagenDocumentoFirmaConToken;
	}

	public Image getImagenDocumentoFirmaConjunta() {
		return imagenDocumentoFirmaConjunta;
	}

	public void setImagenDocumentoFirmaConjunta(
			Image imagenDocumentoFirmaConjunta) {
		this.imagenDocumentoFirmaConjunta = imagenDocumentoFirmaConjunta;
	}

	public Label getLabelDetalle() {
		return labelDetalle;
	}

	public void setLabelDetalle(Label labelDetalle) {
		this.labelDetalle = labelDetalle;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Combobox getUsuarioProductor() {
		return usuarioProductor;
	}

	public void setUsuarioProductor(Combobox usuarioProductor) {
		this.usuarioProductor = usuarioProductor;
	}

	public TipoDocumentoService getTipoDocumentoService() {
		return tipoDocumentoService;
	}

	public ExpedienteElectronicoDTO getEe() {
		return ee;
	}

	public void setEe(ExpedienteElectronicoDTO ee) {
		this.ee = ee;
	}

	public UsuariosSADEService getUsuariosSADEService() {
		return usuariosSADEService;
	}

	public void setUsuariosSADEService(UsuariosSADEService usuariosSADEService) {
		this.usuariosSADEService = usuariosSADEService;
	}

	/**
	 * Se habilitan y deshabilitan los campos según que opción se eliga.
	 */
	public void onUsuarioClick() {
		this.usuarioProductor.setDisabled(false);
	}

	final class InicioDocumentoComposerListener implements EventListener {
		private InicioDocumentoComposer composer;

		public InicioDocumentoComposerListener(InicioDocumentoComposer comp) {
			this.composer = comp;
		}

		@SuppressWarnings("unchecked")
		public void onEvent(Event event) throws Exception {

			if (event.getName().equals(Events.ON_USER)) {
				Map<String, Object> datos = (Map<String, Object>) event.getData();
				String funcion = (String) datos.get("funcion");
				if ("validarApoderamiento".equals(funcion)) {
					Usuario usuario = (Usuario) datos.get("datos");
					this.composer.validarUsuarios(usuario);
				}
				if ("validarReparticion".equals(funcion)) {
					Usuario usuario = (Usuario) datos.get("datos");
					this.composer.validacionesReparticion(usuario);
				}
	      if ("asignarTarea".equals(funcion)) {
	      	usuarioProductorInfo = (Usuario) datos.get("datos");
	        this.composer.asignarTarea();
	      }
			}
			if (event.getName().equals(Events.ON_CLOSE)) {
				switch (((Integer) event.getData()).intValue()) {
				case Messagebox.YES:
					Events.sendEvent(this.composer.getInicioDocumentoWindow(), new Event(Events.ON_CLOSE));
					break;
				case Messagebox.NO:
					break;
				}
			} else if (event.getName().equals(Events.ON_NOTIFY)) {

				if (event.getData() != null) {

					if ((event.getData() instanceof TipoDocumentoDTO)) {
						TipoDocumentoDTO data = (TipoDocumentoDTO) event.getData();
						this.composer.cargarTipoDocumento(data);
					} else {
						Map<String, Object> map = (Map<String, Object>) event.getData();
						String origen = (String) map.get("origen");
					}
				}
			}
		}
	}

  /**
   * Validaciones de adoderamiento y usuarios que pertenecen a la misma
   * repartición.
   * 
   * @throws InterruptedException
   */
  public void validarUsuarios(Usuario usuarioAValidar) throws InterruptedException {
    super.validarApoderamiento(usuarioAValidar);
  }

}
