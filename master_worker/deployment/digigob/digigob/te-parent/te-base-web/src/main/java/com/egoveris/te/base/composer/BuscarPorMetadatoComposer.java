package com.egoveris.te.base.composer;
 
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.api.task.Task;
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
import org.zkoss.zul.Bandpopup;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.ListModels;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listfooter;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;
import org.zkoss.zul.event.ZulEvents;
import org.zkoss.zul.ext.Paginal;

import com.egoveris.deo.model.model.DocumentoSolrResponse;
import com.egoveris.deo.model.model.RequestExternalBuscarDocumentos;
import com.egoveris.deo.model.model.ResponseExternalBuscarDocumentos;
import com.egoveris.deo.ws.exception.ErrorConsultaNumeroSadeException;
import com.egoveris.ffdd.model.exception.DynFormException;
import com.egoveris.ffdd.model.model.CampoBusqueda;
import com.egoveris.ffdd.render.model.InputComponent;
import com.egoveris.ffdd.render.service.IFormManager;
import com.egoveris.ffdd.render.service.IFormManagerFactory;
import com.egoveris.ffdd.render.zk.comp.ext.TextboxExt;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.MetaDocumento;
import com.egoveris.te.base.model.MetaDocumento.TipoConsulta;
import com.egoveris.te.base.model.MetadataDTO;
import com.egoveris.te.base.model.TipoDocumentoDTO;
import com.egoveris.te.base.model.TipoDocumentoTemplateDTO;
import com.egoveris.te.base.rendered.DocumentosEncontradosRender;
import com.egoveris.te.base.rendered.MetadatosGridRender;
import com.egoveris.te.base.service.DocumentoGedoService;
import com.egoveris.te.base.service.TipoDocumentoService;
import com.egoveris.te.base.service.UsuariosSADEService;
import com.egoveris.te.base.util.ConstantesServicios;

/**
 * Esta clase se encarga de buscar documentos por metadatos, un rango de fechas,
 * por reparticion o usuario firmante, luego lo vincula a la lista de documentos
 * del expediente.
 *
 * Esta clase la usa exclusivamente tramitacionComposer, no llamarla de otro
 * lugar.
 *
 * @author MAGARCES
 *
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class BuscarPorMetadatoComposer extends GenericDocumentoComposer {

	@SuppressWarnings("rawtypes")
  final class BuscarPorMetadatoComposerListener implements EventListener {
		private final BuscarPorMetadatoComposer composer;

		public BuscarPorMetadatoComposerListener(final BuscarPorMetadatoComposer comp) {
			this.composer = comp;
		}

		@Override
		public void onEvent(final Event event) throws Exception {

			if (event.getName().equals(Events.ON_NOTIFY) && event.getData() != null) {
				final TipoDocumentoDTO data = (TipoDocumentoDTO) event.getData();
				this.composer.cargarTipoDocumento(data);
			}
		}
	}

	// Variables de instancia

	@SuppressWarnings("rawtypes")
	private class CheckListener implements EventListener {

		@Override
		public void onEvent(final Event event) throws Exception {
			if (event.getName().equals(Events.ON_CHECK)) {
				if ("generadosPorMiRadio".equals(event.getTarget().getId())) {
					usuarioFirmanteHbox.setVisible(false);
				}
				
				if ("generadosPorMiReparticionRadio".equals(event.getTarget().getId())) {
					usuarioFirmanteHbox.setVisible(true);
				}
			}
		}

	}

	@SuppressWarnings("rawtypes")
	private class PagingListener implements EventListener {

		@Override
		public void onEvent(final Event event) throws Exception {

			if (event.getName().equals(ZulEvents.ON_PAGING)) {
				getRequestExternalBuscarDocumentos().setPageIndex(((PagingEvent) event).getActivePage());
			}
			
			buscarDocumento(getRequestExternalBuscarDocumentos());
			refreshResultado();
		}
	}

	private static final Logger logger = LoggerFactory.getLogger(BuscarPorMetadatoComposer.class);
	private static final long serialVersionUID = 1106140561769453743L;
	private static final String SIN_DATOS_DE_BUSQUEDA_ERROR = Labels.getLabel("ee.buscarPorDatoPropio.criterio.vacio");
	private static final String SIN_FECHA_DESDE_ERROR = Labels.getLabel("ee.buscarPorDatoPropio.fechaDesde.vacio");
	private static final String SIN_FECHA_HASTA_ERROR = Labels.getLabel("ee.buscarPorDatoPropio.fechaHasta.vacio");
	private static final String FECHA_HASTA_SUPERIOR_ERROR = Labels.getLabel("ee.buscarPorDatoPropio.fechaHasta.superior");
	private static final String SIN_DOCUMENTO_A_VINCULAR_ERROR = Labels.getLabel("ee.buscarPorDatoPropio.documento.vacio");

	// Inyeccion de dependencias

	private static final String DOCUMENTO_YA_VINCULADO_ERROR = Labels.getLabel("ee.buscarPorDatoPropio.documento.vinculado.aviso");
	private static final String DOCUMENTOS_YA_VINCULADO_ERROR = Labels.getLabel("ee.buscarPorDatoPropio.documentos.vinculados.aviso");
	private static final String SIN_RESULTADO_MENSAJE = Labels.getLabel("ee.buscarPorDatoPropio.busqueda.sinResultado.aviso");
	
	@Autowired
	private Window buscarPorMetadatoWindow;
	@Autowired
	private Datebox fechaDesdeDateBox;
	@Autowired
	private Datebox fechaHastaDateBox;
	@Autowired
	private Bandbox buscadorTipoDocumento;
	@Autowired
	public AnnotateDataBinder binder;
	public Paginal pagingDocumento;
	public List<MetadataDTO> listaMetadata = new ArrayList<>();
	public List<MetaDocumento> listaDocMetadata = new ArrayList<>();

	private List<CampoBusqueda> listaMetaFc = new ArrayList<>();
	private Listbox documentosEncontradosListBox;
	private MetadataDTO selectedMetadata;
	private TipoDocumentoDTO selectedTipoDocumento;
	private Combobox nombreMetadato;
	private Combobox usuarioFirmanteCombobox;
	private Combobox comboMetaFc;
	private Grid grillaValoresAgregados;
	private Hbox datosPropioHbox;
	private List<DocumentoDTO> documentosList;
	private Button agregar;
	private Button agregarFc;
	private ExpedienteElectronicoDTO expedienteElectronico;
	private Hbox agregaDataFcHlayout;
	
	@WireVariable(ConstantesServicios.TIPO_DOCUMENTO_SERVICE)
	private TipoDocumentoService tipoDocumentoService;
	private CampoBusqueda selectedMetaFc;
	private Textbox nuevoValor;
	private Hbox grillalayout;
	private Div divFc;
	private UsuariosSADEService usuariosSADEService;
	private Checkbox usuarioFirmanteCheckbox;
	private Radio generadosPorMiRadio;
	private Radio generadosPorMiReparticionRadio;
	private DocumentoGedoService documentoGedoService;
	private RequestExternalBuscarDocumentos requestExternalBuscarDocumentos;
	private ResponseExternalBuscarDocumentos responseExternalBuscarDocumentos;
	private Checkbox soloActivosCheckbox;
	private Checkbox todosCheckbox;
	private Bandpopup familia;
	private Listfooter foot;
	private Hbox usuarioFirmanteHbox;
	private Map<String, DocumentoDTO> documentosSelected;
	protected IFormManagerFactory<IFormManager<Component>> formManagerFact;

	protected IFormManager<Component> manager;

	private Task task;

	private TramitacionComposer ventanaAsociada;

	private RequestExternalBuscarDocumentos armarConsulta() {

		final String userName = (String) Executions.getCurrent().getDesktop().getSession().getAttribute("userName");

		String tipoBusqueda = "buscarDocumentoPorMetadatosPorUsuario";

		final RequestExternalBuscarDocumentos request = new RequestExternalBuscarDocumentos();
		request.setPageSize(pagingDocumento.getPageSize());

		request.setFechaDesde(this.fechaDesdeDateBox.getValue());
		request.setFechaHasta(this.fechaHastaDateBox.getValue());

		if (this.selectedTipoDocumento != null) {
			request.setTipoDocAcr(this.selectedTipoDocumento.getAcronimo());
			request.setTipoDocDescr(this.selectedTipoDocumento.getNombre());
		}

		if (this.generadosPorMiRadio.isChecked()) {
			request.setGeneradosPorMi(true);
			request.setUsuarioGenerador(userName);
		}

		if (this.generadosPorMiReparticionRadio.isChecked()) {
			final Usuario datosUsuario = this.usuariosSADEService.obtenerUsuarioActual();
			request.setCodigoReparticion(datosUsuario.getCodigoReparticion());
			request.setGeneradosPorMiReparticion(true);
			tipoBusqueda = "buscarDocumentoMetadatosPorReparticion";
		}

		if (this.usuarioFirmanteCombobox.getSelectedItem() != null) {
			final Usuario usuario = (Usuario) this.usuarioFirmanteCombobox.getSelectedItem().getValue();
			request.setUsuarioFirmante(usuario.getUsername());
		}

		for (final MetaDocumento metaData : this.listaDocMetadata) {
			switch (metaData.getTipoConsulta()) {
			case LEGACY_CONSULTA:
				request.addLegacyField(metaData.getNombre(), (String) metaData.getValor());
				break;
			case CONTAIN_CONSULTA:
				request.addContainsDynamicField(metaData.getNombre(), (String) metaData.getValor());
				break;
			default:
				request.addDynamicField(metaData.getNombre(), metaData.getValor());
				break;
			}
		}

		request.setTipoBusqueda(tipoBusqueda);
		return request;
	}

	private void buscar() throws InterruptedException, ErrorConsultaNumeroSadeException {

		this.setRequestExternalBuscarDocumentos(this.armarConsulta());
		this.buscarDocumento(this.getRequestExternalBuscarDocumentos());
	}

	private void buscarDocumento(final RequestExternalBuscarDocumentos requestExternalBuscarDocumentos)
			throws InterruptedException, ErrorConsultaNumeroSadeException {
		List<DocumentoDTO> documentosList;
		
		try {
			// Inicio la lista
			this.setDocumentosEncontrados(new ArrayList<>());

			// Realizo la busqueda y la seteo al composer
      ResponseExternalBuscarDocumentos responseExternalBuscarDocumentos = this.documentoGedoService
          .buscarDocumentoGEDOPorDatosPropios(requestExternalBuscarDocumentos);

			if (responseExternalBuscarDocumentos != null) {
				this.setResponseExternalBuscarDocumentos(responseExternalBuscarDocumentos);

				documentosList = this
						.convertDocumentoSolrResponseListToDocumentoList(this.getResponseExternalBuscarDocumentos());
				this.getDocumentosEncontrados().addAll(documentosList);

				getPagingDocumento().setTotalSize(this.getResponseExternalBuscarDocumentos().getTotalSize());
				setearFoot(getPagingDocumento().getTotalSize());

				documentosEncontradosListBox.setModel(getDocumentModel());
				documentosEncontradosListBox.setItemRenderer(new DocumentosEncontradosRender(this));
				this.binder.loadAll();
			} else {
				Messagebox.show(SIN_RESULTADO_MENSAJE, Labels.getLabel("ee.act.msg.title.ok"), Messagebox.OK, Messagebox.INFORMATION);
			}
		} catch (final ErrorConsultaNumeroSadeException e) {
			logger.error(e.getMessage());
		}

	}

	@SuppressWarnings("unchecked")
	private void cargarComboFc() throws DynFormException {
		final List<CampoBusqueda> modelComboFc = (List<CampoBusqueda>) this.comboMetaFc.getModel();
		modelComboFc.clear();
		final List<CampoBusqueda> campoBusq = obtenerCamposBusquedaFC();
		if (!campoBusq.isEmpty()) {
			this.listaMetaFc = campoBusq;
			modelComboFc.addAll(listaMetaFc);
			this.agregarFc.setDisabled(false);
			this.comboMetaFc.setDisabled(false);
			this.agregaDataFcHlayout.setVisible(true);
		} else {
			this.agregaDataFcHlayout.setVisible(false);
		}
		this.binder.loadComponent(this.comboMetaFc);
	}

	public void cargarTipoDocumento(final TipoDocumentoDTO data) throws DynFormException {
		this.buscadorTipoDocumento.setText(data.getAcronimo().toUpperCase());
		this.buscadorTipoDocumento.close();
		this.selectedTipoDocumento = data;
		onSelectTipoDocumento();
	}

	private List<DocumentoDTO> convertDocumentoSolrResponseListToDocumentoList(
			final ResponseExternalBuscarDocumentos response) {

		final List<DocumentoDTO> documentosList = new ArrayList<>();

		for (final DocumentoSolrResponse documentoSolr : response.getContent()) {

			final DocumentoDTO documento = new DocumentoDTO();

			documento.setId(new Long(documentoSolr.getId().toString()));
			documento.setNumeroSade(documentoSolr.getNroSade());
			documento.setFechaCreacion(documentoSolr.getFechaCreacion());
			documento.setNumeroEspecial(documentoSolr.getNroEspecialSade());
			documento.setTipoDocAcronimo(documentoSolr.getTipoDocAcr());
			documento.setNombreUsuarioGenerador(documentoSolr.getUsuarioGenerador());
			documento.setNombreArchivo(documentoSolr.getNombre());
			documento.setMotivo(documentoSolr.getReferencia());

			documentosList.add(documento);
		}
		return documentosList;
	}

	// Eventos
	@Override
	public void doAfterCompose(final Component comp) throws Exception {
		super.doAfterCompose(comp);
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
		
		this.expedienteElectronico = (ExpedienteElectronicoDTO) Executions.getCurrent().getArg()
				.get("expedienteElectronico");
		task = (Task) Executions.getCurrent().getArg().get("task");
		ventanaAsociada = (TramitacionComposer) Executions.getCurrent().getArg().get("composer");

		buscadorTipoDocumento.addEventListener(Events.ON_NOTIFY, new BuscarPorMetadatoComposerListener(this));
		getPagingDocumento().addEventListener(ZulEvents.ON_PAGING, new PagingListener());
		generadosPorMiRadio.addEventListener(Events.ON_CHECK, new CheckListener());
		generadosPorMiReparticionRadio.addEventListener(Events.ON_CHECK, new CheckListener());
		usuariosSADEService = (UsuariosSADEService) SpringUtil.getBean(ConstantesServicios.USUARIOS_SADE_SERVICE);
		documentoGedoService = (DocumentoGedoService) SpringUtil.getBean(ConstantesServicios.DOCUMENTO_GEDO_SERVICE);
		usuarioFirmanteCombobox.setModel(ListModels.toListSubModel(
				new ListModelList(this.getUsuariosSADEService().getTodosLosUsuarios()), new UsuariosComparatorConsultaExpediente(), 30));
		getFoot().setVisible(false);

	}

	// Metodos de instancia

	private boolean documentosVinculadosValidacion() throws InterruptedException {

		final List<String> errorList = new ArrayList<>();
		int error = 0;
		boolean valido = false;

		if (this.getDocumentosSelected().isEmpty()) {
			error = 1;
		}
		if (error == 0) {
			for (final DocumentoDTO documento : this.getDocumentosSelected().values()) {

				if (this.expedienteElectronico.getDocumentos().contains(documento)) {
					errorList.add(documento.getNombreArchivo());
					error = 2;
				}
			}
		}
		switch (error) {
		case 0:
			valido = true;
			break;
		case 1:
			valido = false;
			Messagebox.show(SIN_DOCUMENTO_A_VINCULAR_ERROR, Labels.getLabel("ee.act.msg.title.ok"), Messagebox.OK, Messagebox.INFORMATION);
			break;
		case 2:
			valido = false;
			if (errorList.size() == 1) {
				Messagebox.show(DOCUMENTO_YA_VINCULADO_ERROR,Labels.getLabel("ee.act.msg.title.ok"), Messagebox.OK, Messagebox.INFORMATION);
			} else {
				String nombres = "";

				for (final String errores : errorList) {
					nombres = nombres + "\n" + errores;

				}
				Messagebox.show(DOCUMENTOS_YA_VINCULADO_ERROR + nombres, Labels.getLabel("ee.act.msg.title.ok"), Messagebox.OK,
						Messagebox.INFORMATION);
			}

			break;
		default:
			break;
		}

		return valido;
	}

	private void filtroRepetido(final String nombre) {
		for (final MetaDocumento metadata : this.listaDocMetadata) {
			if (metadata.getNombre().equals(nombre)) {
				throw new WrongValueException(Labels.getLabel("ee.buscarPorDatoPropio.busqueda.nombre.repetido"));
			}
		}
	}

	public ListModel getDocumentModel() {
		return new ListModelList(this.getDocumentosEncontrados());
	}

	public List<DocumentoDTO> getDocumentosEncontrados() {

		if (documentosList == null) {
			documentosList = new ArrayList<>();
		}

		return documentosList;
	}

	public Map<String, DocumentoDTO> getDocumentosSelected() {
		if (documentosSelected == null) {
			documentosSelected = new HashMap<>();
		}
		return documentosSelected;
	}

	private Listfooter getFoot() {
		return this.foot;
	}

	public Paginal getPagingDocumento() {
		return pagingDocumento;
	}

	public RequestExternalBuscarDocumentos getRequestExternalBuscarDocumentos() {
		if (requestExternalBuscarDocumentos == null) {
			requestExternalBuscarDocumentos = new RequestExternalBuscarDocumentos();
		}
		return requestExternalBuscarDocumentos;
	}

	// Getters and Setters

	public ResponseExternalBuscarDocumentos getResponseExternalBuscarDocumentos() {
		return responseExternalBuscarDocumentos;
	}

	public UsuariosSADEService getUsuariosSADEService() {
		return usuariosSADEService;
	}

	private void limpiarDatos() {

		this.fechaDesdeDateBox.setText("");
		this.fechaHastaDateBox.setText("");
		this.generadosPorMiRadio.setChecked(true);
		this.usuarioFirmanteCombobox.setText("");
		this.datosPropioHbox.setVisible(false);
		this.agregaDataFcHlayout.setVisible(false);
		this.grillalayout.setVisible(false);
		this.buscadorTipoDocumento.setText("");
		this.selectedTipoDocumento = null;
		this.selectedDocumento = null;
		this.selectedMetadata = null;
		this.selectedMetaFc = null;
		this.listaDocMetadata.clear();

	}

	public void limpiarResultados() {
		setDocumentosEncontrados(null);
		this.documentosEncontradosListBox.setModel(getDocumentModel());
		this.refreshResultado();
		getFoot().setVisible(false);
		getPagingDocumento().setTotalSize(0);
	}

	private List<CampoBusqueda> obtenerCamposBusquedaFC() throws DynFormException {
		final List<CampoBusqueda> campoBusqs = new ArrayList<>();
		final String formName = obtenerIdFormulario();
		if (formName != null) {
			this.manager = formManagerFact.create(formName);
			for (final CampoBusqueda campoBusqueda : manager.searchFields()) {
				if (campoBusqueda.getRelevanciaBusqueda() > 0) {
					campoBusqs.add(campoBusqueda);
				}
			}
		}
		return campoBusqs;
	}

	private String obtenerIdFormulario() {
		final TipoDocumentoTemplateDTO ultimoTemplatePorTipoDocumento = this
				.obtenerUltimoTemplatePorTipoDocumento(this.selectedTipoDocumento);
		if (ultimoTemplatePorTipoDocumento != null) {
			return ultimoTemplatePorTipoDocumento.getIdFormulario();
		} else {
			return null;
		}
	}

	public TipoDocumentoTemplateDTO obtenerUltimoTemplatePorTipoDocumento(final TipoDocumentoDTO tipoDocumento) {

		final TipoDocumentoDTO tipoDocumentoObject = tipoDocumentoService
				.obtenerTipoDocumento(tipoDocumento.getAcronimo());

		int i = 0;
		TipoDocumentoTemplateDTO tipoDocumentoTemplate = null;

		for (final TipoDocumentoTemplateDTO tipoDocumentoTemplateAux : tipoDocumentoObject.getListaTemplates()) {
			if (tipoDocumentoTemplateAux.getTipoDocumentoTemplatePK().getVersion() > i) {
				tipoDocumentoTemplate = tipoDocumentoTemplateAux;
				i = tipoDocumentoTemplateAux.getTipoDocumentoTemplatePK().getVersion();
			}
		}

		return tipoDocumentoTemplate;
	}

	/**
	 * Busca Documentos por rango de fecha o metadatos
	 *
	 * @throws InterruptedException
	 * @throws RemoteException
	 * @throws com.egoveris.deo.generardocumento.service.client.external.consulta.ErrorConsultaNumeroSadeException 
	 */
	public synchronized void onBuscarDocumentos() throws InterruptedException, RemoteException, ErrorConsultaNumeroSadeException {

		this.limpiarResultados();
		if (this.validarDatosDeBusqueda()) {
			this.buscar();
			this.limpiarDatos();
		}

	}

	public void onCancelar() {
		this.buscarPorMetadatoWindow.detach();
	}

	public void onCheck$soloActivosCheckbox() {
		if (this.soloActivosCheckbox.isChecked()) {
			Events.sendEvent(new Event(Events.ON_NOTIFY, this.familia, false));
		} else {
			Events.sendEvent(new Event(Events.ON_NOTIFY, this.familia, true));
		}
	}

	public void onCheck$todosCheckbox() {
		if (this.todosCheckbox.isChecked()) {
			buscadorTipoDocumento.setDisabled(true);
			buscadorTipoDocumento.invalidate();
		} else {
			buscadorTipoDocumento.setDisabled(false);
		}
	}

	public void onCheck$usuarioFirmanteCheckbox() {

		if (this.usuarioFirmanteCheckbox.isChecked()) {
			this.usuarioFirmanteCombobox.setDisabled(false);
		} else {
			this.usuarioFirmanteCombobox.setDisabled(true);
		}
	}

	@SuppressWarnings("unchecked")
	public void onClick$agregar() {

		if (selectedMetadata != null) {
			final String newValue = this.nuevoValor.getValue();
			final String nombre = this.selectedMetadata.getNombre();

			if (newValue == null || newValue.trim() == "") {
				throw new WrongValueException(this.nuevoValor,
						Labels.getLabel("ee.buscarPorDatoPropio.busqueda.nombre.vacio"));
			}

			filtroRepetido(nombre);

			this.grillalayout.setVisible(true);
			this.listaDocMetadata = (List<MetaDocumento>) grillaValoresAgregados.getModel();
			final MetaDocumento newMetadata = new MetaDocumento();
			newMetadata.setNombre(nombre);
			newMetadata.setValor(newValue);
			newMetadata.setTipoConsulta(TipoConsulta.LEGACY_CONSULTA);

			this.nuevoValor.setValue(null);

			listaDocMetadata.add(newMetadata);
			refreshGrid();

		}
	}

	public void onClick$agregarFc() {

		if (selectedMetaFc != null) {

			final String nombre = this.selectedMetaFc.getNombre();
			final InputComponent comp = (InputComponent) divFc.getFirstChild();

			comp.getText();
			filtroRepetido(nombre);

			final MetaDocumento newMetadata = new MetaDocumento();
			newMetadata.setNombre(nombre);
			newMetadata.setValor(comp.getRawValue());
			if (comp instanceof TextboxExt) {
				newMetadata.setTipoConsulta(TipoConsulta.CONTAIN_CONSULTA);
			} else {
				newMetadata.setTipoConsulta(TipoConsulta.IS_CONSULTA);
			}

			comp.setRawValue(null);

			listaDocMetadata.add(newMetadata);
			refreshGrid();
		}
	}

	@SuppressWarnings("unchecked")
	public void onSelectTipoDocumento() throws DynFormException {
		final List<MetadataDTO> modelCombo = (List<MetadataDTO>) this.nombreMetadato.getModel();
		this.listaMetadata = selectedTipoDocumento.getListaDatosVariables();
		modelCombo.clear();
		modelCombo.addAll(listaMetadata);

		this.datosPropioHbox.setVisible(!listaMetadata.isEmpty());
		this.binder.loadComponent(this.nombreMetadato);

		this.listaDocMetadata = (List<MetaDocumento>) this.grillaValoresAgregados.getModel();
		this.listaDocMetadata.clear();
		this.grillaValoresAgregados.setRowRenderer(new MetadatosGridRender());
		this.grillaValoresAgregados.setVisible(false);
		this.binder.loadComponent(this.grillaValoresAgregados);

		cargarComboFc();
	}

	public synchronized void onVincularDocumentos() throws InterruptedException, RemoteException {

		if (this.documentosVinculadosValidacion()) {
			for (final DocumentoDTO documento : this.getDocumentosSelected().values()) {

				final String[] array = documento.getNumeroSade().split("-");
				ventanaAsociada.buscarDoc(array[0], Integer.valueOf(array[1]), Integer.valueOf(array[2]), array[4]);
			}
			this.expedienteElectronico.setFechaModificacion(new Date());
			Events.sendEvent("onUser", this.self.getParent(), "actualizar");

			this.buscarPorMetadatoWindow.detach();
		}
	}

	public void refreshGrid() {
		if (this.listaDocMetadata.size() >= 5) {
			this.agregar.setDisabled(true);
			this.agregarFc.setDisabled(true);
			this.comboMetaFc.setDisabled(true);
		}

		this.grillaValoresAgregados.setVisible(true);
		this.binder.loadComponent(this.grillaValoresAgregados);
	}

	private void refreshResultado() {
		this.binder.loadComponent(this.documentosEncontradosListBox);
	}

	public void setDocumentosEncontrados(final List<DocumentoDTO> documentosEncontrados) {
		this.documentosList = documentosEncontrados;
	}

	public void setDocumentosSelected(final Map<String, DocumentoDTO> documentosSelected) {
		this.documentosSelected = documentosSelected;
	}

	private void setearFoot(final int totalSize) {
		getFoot().setVisible(true);
		getFoot().setLabel(Labels.getLabel("ee.buscarPorDatoPropio.busqueda.registros.encontrados") + totalSize);
		getFoot().setSpan(7);

	}

	public void setPagingDocumento(final Paginal pagingDocumento) {
		this.pagingDocumento = pagingDocumento;
	}

	public void setRequestExternalBuscarDocumentos(
			final RequestExternalBuscarDocumentos requestExternalBuscarDocumentos) {
		this.requestExternalBuscarDocumentos = requestExternalBuscarDocumentos;
	}

	public void setResponseExternalBuscarDocumentos(
			final ResponseExternalBuscarDocumentos responseExternalBuscarDocumentos) {
		this.responseExternalBuscarDocumentos = responseExternalBuscarDocumentos;
	}

	public void setUsuariosSADEService(final UsuariosSADEService usuariosSADEService) {
		this.usuariosSADEService = usuariosSADEService;
	}

	private boolean validarDatosDeBusqueda() throws InterruptedException {

		int error = 0;
		boolean valido = false;

		if (this.fechaDesdeDateBox.getValue() == null && this.fechaHastaDateBox.getValue() != null) {
			error = 3;
		}
		if (this.fechaDesdeDateBox.getValue() != null && this.fechaHastaDateBox.getValue() == null) {
			error = 4;
		}

		if (error == 0 && this.fechaDesdeDateBox.getValue() != null && this.fechaHastaDateBox.getValue() != null) {
			if (this.fechaDesdeDateBox.getValue().compareTo(fechaHastaDateBox.getValue()) == 1) {
				error = 2;
			}

			if (this.fechaDesdeDateBox.getValue().compareTo(this.fechaHastaDateBox.getValue()) == 0) {
				final Calendar c = Calendar.getInstance();
				c.setTime(fechaHastaDateBox.getValue());
				c.add(Calendar.DATE, 1);
				fechaHastaDateBox.setValue(c.getTime());
			}
		}

		switch (error) {
		case 0:
			valido = true;
			break;
		case 1:
			valido = false;
			Messagebox.show(SIN_DATOS_DE_BUSQUEDA_ERROR, Labels.getLabel("ee.act.msg.title.ok"), Messagebox.OK, Messagebox.INFORMATION);
			break;
		case 2:
			valido = false;
			Messagebox.show(FECHA_HASTA_SUPERIOR_ERROR, Labels.getLabel("ee.act.msg.title.ok"), Messagebox.OK, Messagebox.INFORMATION);
			break;
		case 3:
			valido = false;
			Messagebox.show(SIN_FECHA_DESDE_ERROR, Labels.getLabel("ee.act.msg.title.ok"), Messagebox.OK, Messagebox.INFORMATION);
			break;
		case 4:
			valido = false;
			Messagebox.show(SIN_FECHA_HASTA_ERROR, Labels.getLabel("ee.act.msg.title.ok"), Messagebox.OK, Messagebox.INFORMATION);
			break;
		default:
			break;
		}

		return valido;

	}
}
