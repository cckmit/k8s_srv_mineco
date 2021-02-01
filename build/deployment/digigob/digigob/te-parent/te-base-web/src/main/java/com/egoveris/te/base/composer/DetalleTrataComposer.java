package com.egoveris.te.base.composer;

import com.egoveris.te.base.model.ParametrosSistemaExternoDTO;
import com.egoveris.te.base.model.PropertyConfigurationDTO;
import com.egoveris.te.base.model.ReparticionDTO;
import com.egoveris.te.base.model.TipoDocumentoDTO;
import com.egoveris.te.base.model.TrataDTO;
import com.egoveris.te.base.model.TrataIntegracionReparticionDTO;
import com.egoveris.te.base.model.TrataTipoResultadoDTO;
import com.egoveris.te.base.service.IPropertyConfigurationService;
import com.egoveris.te.base.service.TipoDocumentoService;
import com.egoveris.te.base.service.TrataService;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.util.ComboTipoResultadoHelper;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.MantainerPrefix;
import com.egoveris.te.base.util.TipoTramiteEnum;
import com.egoveris.te.base.util.TramitacionHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class DetalleTrataComposer extends AbstractComposer {
	/**
	*
	*/
	private static final long serialVersionUID = 1538367998718043842L;
	private static final String SIN_TEMPLATE = "Sin Template";
	@Autowired
	private Window detalleTrataWindow;
	private TrataDTO trata;
	private Boolean ambos;
	private AnnotateDataBinder binder;
	@Autowired
	private Radio automatica;
	@Autowired
	private Radio ambosRadios;
	@Autowired
	private Radio manual;
	@Autowired
	private Radio sinReservaDetalle;
	@Autowired
	private Radio reservaParcialDetalle;
	@Autowired
	private Radio reservaTotalDetalle;
	@Autowired
	private Radio interno;
	@Autowired
	private Radio externo;
	@Autowired
	private Radio ambosRadioCarat;
	private Boolean ambosCarat;
	@WireVariable(ConstantesServicios.EXP_ELECTRONICO_SERVICE)
	private ExpedienteElectronicoService expedienteElectronicoService;
	private Bandbox tiposDocumento;
	protected List<TipoDocumentoDTO> tiposDeDocumento;
	protected List<TipoDocumentoDTO> tiposDeDocumentoSeleccionados;
	@Autowired
	private Combobox comboWorkflows;
	private Checkbox envioGT;
	@Autowired
	protected List<TipoDocumentoDTO> listaDocs;
	protected TipoDocumentoDTO tipoDoc;
	private String tipoDocSel;
	private String tipoDocComp;

	private ParametrosSistemaExternoDTO model;

	@Autowired
	private Textbox tipoDocumento;

	private Button verHistorial;

	@Autowired
	private Intbox tiempoRes;
	private String descripcionTrata;
	private String trataSade;
	private List<String> workflows;
	private String selectedWorkflow;
	@WireVariable(ConstantesServicios.TRATA_SERVICE)
	private TrataService trataService;

	@WireVariable(ConstantesServicios.TIPO_DOCUMENTO_SERVICE)
	private TipoDocumentoService tipoDocumentoService;

	private String tipoActuacion;

	@Autowired
	private Window historialView;

	// INI CAMPO TIPO TRAMITE
	@Autowired
	private Radio tipoTrSubproceso;

	@Autowired
	private Radio tipoTrExpediente;

	@Autowired
	private Radio tipoBuzonGrupal;

	@Autowired
	private Radio tipoCarga;

	@Autowired
	private Radio tipoTrAmbos;
	// FIN CAMPO TIPO TRAMITE

	// INI CAMPO TIPO RESULTADO
	@WireVariable(ConstantesServicios.PROPERTY_CONF_SERVICE)
	private IPropertyConfigurationService propertyConfigurationService;

	@Autowired
	private Bandbox bandboxTipoResultado;

	@Autowired
	private Listbox listboxTipoResultado;

	private List<PropertyConfigurationDTO> allResultTypes;
	private List<PropertyConfigurationDTO> selectedResultTypes;
	private boolean hasResultTypes;
	// FIN CAMPO TIPO RESULTADO

	public void cargarTiposDeDocumento(final InputEvent e) {
		String matchingText = e.getValue();
		this.tiposDeDocumentoSeleccionados.clear();

		if (!matchingText.equals("") && matchingText.length() >= 3) {
			if (this.listaDocs != null) {
				matchingText = matchingText.toUpperCase();

				final Iterator<TipoDocumentoDTO> iterator = this.listaDocs.iterator();
				TipoDocumentoDTO documento;

				while (iterator.hasNext()) {
					documento = iterator.next();

					if (documento != null && !SIN_TEMPLATE.equals(documento.getAcronimo())) {
						if (documento.getAcronimo().toUpperCase().trim().contains(matchingText.trim()) || documento
								.getDescripcionTipoDocumentoSade().toUpperCase().trim().contains(matchingText.trim())) {
							this.tiposDeDocumentoSeleccionados.add(documento);
						}
					}
				}
			}
		} else if (matchingText.trim().equals("")) {
			this.tiposDeDocumentoSeleccionados = new ArrayList<>(this.listaDocs);
		}

		this.binder.loadAll();
	}

	private boolean contieneAcronimo() {
		if (tipoDoc != null) {
			tipoDocComp = this.tipoDoc.getCodigoTipoDocumentoSade() + " - "
					+ this.tipoDoc.getDescripcionTipoDocumentoSade();
			for (final TipoDocumentoDTO td : tiposDeDocumentoSeleccionados) {
				if (td.esIgual(tipoDocComp)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void doAfterCompose(final Component comp) throws Exception {
		super.doAfterCompose(comp);
		this.binder = new AnnotateDataBinder(comp);
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));

		setAllResultTypes(propertyConfigurationService.getPropertiesWithPrefix(MantainerPrefix.RESULT_TYPE));
		comp.addEventListener(Events.ON_USER, new ManejadorEvento(this));

		final Map map = Executions.getCurrent().getArg();
		this.trata = (TrataDTO) map.get("trata");
		this.setAmbos(this.trata.getEsAutomatica() && this.trata.getEsManual());
		this.automatica.setChecked(this.trata.getEsAutomatica());
		this.manual.setChecked(this.trata.getEsManual());
		this.ambosRadios.setChecked(this.getAmbos());

		this.setAmbosCarat(this.trata.getEsInterno() && this.trata.getEsExterno());
		this.interno.setChecked(this.trata.getEsInterno());
		this.externo.setChecked(this.trata.getEsExterno());
		this.ambosRadioCarat.setChecked(this.getAmbosCarat());
		this.sinReservaDetalle.setChecked(tieneReserva());
		this.reservaParcialDetalle.setChecked(esReservaParcial());
		this.reservaTotalDetalle.setChecked(esReservaTotal());

		this.descripcionTrata = this.trataService.obtenerDescripcionTrataByCodigo(this.trata.getCodigoTrata());
		this.trataSade = this.trata.getCodigoTrata();
		this.workflows = getWorkflows();
		this.comboWorkflows.setValue(trata.getWorkflow());
		selectedWorkflow = trata.getWorkflow();
		this.envioGT.setChecked(this.trata.getEsEnvioAutomaticoGT());

		this.tipoActuacion = this.trata.getTipoActuacion();

		if (this.trata.getAcronimoDocumento() != null) {
			tipoDoc = tipoDocumentoService.obtenerTipoDocumento(this.trata.getAcronimoDocumento());
			if (tipoDoc != null) {
				if (null != this.tipoDocumento) {
					this.tipoDocumento.setValue(tipoDoc.getDescripcion());
				}
			}
		}

		// ************************************************************************
		// **
		// ** Feature: Habilitacion de funcionalidad tratamiento en
		// paralelo/tratamiento en conjunto.
		// ** Se modifica la pantalla de modificación de Tramites. Solo muestra
		// los tildes. No permite modificar salvo que no se haya generado un
		// expediente para ese tipo de trata.
		// ************************************************************************
		// boolean existeTrata =
		// this.getExpedienteElectronicoService().buscarIdTrata(this.trata);
		listaDocs = loadComboTipoDoc();
		tiposDeDocumentoSeleccionados = new ArrayList<>(this.listaDocs);

		if (null != this.trata.getAcronimoDocumento() && !this.trata.getAcronimoDocumento().equals("")) {
			this.tiposDocumento.setValue(this.trata.getAcronimoDocumento());
		} else {
			this.tiposDocumento.setValue(SIN_TEMPLATE);
		}

		// INI CAMPO TIPO TRAMITE
		if (this.trata.getTipoTramite() != null
				&& this.trata.getTipoTramite().equalsIgnoreCase(TipoTramiteEnum.SUBPROCESO.getValue())) {
			this.tipoTrSubproceso.setChecked(true);
		} else if (this.trata.getTipoTramite() != null
				&& this.trata.getTipoTramite().equalsIgnoreCase(TipoTramiteEnum.EXPEDIENTE.getValue())) {
			this.tipoTrExpediente.setChecked(true);
		} else if (this.trata.getTipoTramite() != null
				&& this.trata.getTipoTramite().equalsIgnoreCase(TipoTramiteEnum.AMBOS.getValue())) {
			this.tipoTrAmbos.setChecked(true);
		} else {
			// Default
			this.tipoTrAmbos.setChecked(true);
		}
		if (this.trata.getTipoCarga() != null && this.trata.getTipoCarga().equals(0)) {
			this.tipoBuzonGrupal.setChecked(true);
		} else {
			if (this.trata.getTipoCarga() != null && this.trata.getTipoCarga().equals(1)) {
				this.tipoCarga.setChecked(true);
			}
		}
		
		// FIN CAMPO TIPO TRAMITE

		// INI CAMPO TIPO RESULTADO
		for (PropertyConfigurationDTO property : getAllResultTypes()) {
			for (TrataTipoResultadoDTO trataTipoResultado : trata.getTipoResultadosTrata()) {
				if (trataTipoResultado.getClave().equals(property.getClave())) {
					// Lo marca como seleccionado
					property.setSelected(true);
					getSelectedResultTypes().add(property);
				}
			}
		}

		if (bandboxTipoResultado != null) {
			bandboxTipoResultado.setValue(ComboTipoResultadoHelper.getLabelComboTipoResultado(getSelectedResultTypes(),
					getAllResultTypes().size()));
		}
		// FIN CAMPO TIPO RESULTADO

		this.binder.loadAll();
	}

	private boolean esReservaParcial() {
		return this.trata.getTipoReserva().getTipoReserva().endsWith(ConstantesWeb.RESERVA_PARCIAL) ? true : false;
	}

	private boolean esReservaTotal() {
		return this.trata.getTipoReserva().getTipoReserva().equals(ConstantesWeb.RESERVA_TOTAL) ? true : false;
	}

	public boolean existeCodigoEnListasExistentes(final String codigoRep) {
		for (final ReparticionDTO r : model.getReparticiones()) {
			if (r.getCodigoReparticion().equals(codigoRep)) {
				return true;
			}
		}

		return existeEnListaIntegracion(codigoRep);
	}

	public boolean existeEnListaIntegracion(final String codigoRep) {
		for (final TrataIntegracionReparticionDTO t : model.getReparticionesIntegracion()) {
			if (t.getCodigoReparticion().equals(codigoRep)) {
				return true;
			}
		}
		return false;
	}

	private List<TipoDocumentoDTO> loadComboTipoDoc() {

		final List<TipoDocumentoDTO> result = this.tipoDocumentoService.obtenerTemplates();
		Collections.sort(result, new Comparator<TipoDocumentoDTO>() {
			@Override
			public int compare(final TipoDocumentoDTO td1, final TipoDocumentoDTO td2) {
				return td1.getAcronimo().compareTo(td2.getAcronimo());
			}
		});
		result.add(0, new TipoDocumentoDTO(SIN_TEMPLATE));
		return result;
	}

	protected void mostrarForegroundBloqueante() {
		Clients.showBusy(Labels.getLabel("ee.subsanacion.msg.procesando"));
	}

	public void onBlur$tiposDocumento() {
		if (this.tiposDocumento.getValue() != null && !this.tiposDocumento.getValue().trim().equals("")) {
			this.tipoDocSel = this.tiposDocumento.getValue().toUpperCase();
		}
	}

	public void onCancelar() throws InterruptedException {
		this.detalleTrataWindow.detach();
	}

	public void onChanging$tiposDocumento(final InputEvent e) {
		this.cargarTiposDeDocumento(e);
	}

	public void onClick$botonSistemaExterno() throws SuspendNotAllowedException, InterruptedException {
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put("trata", this.trata);
		params.put("trataNueva", false);
		final Window w = (Window) Executions.createComponents("/inbox/trataIntegracion.zul", this.self, params);
		w.doModal();
	}

	public void onFocus$tiposDocumento() {
		if (SIN_TEMPLATE.equals(tiposDocumento.getValue())) {
			tiposDocumento.setValue("");
		}
	}

	public void onGuardarTrata() throws InterruptedException {
		final String usuario = (String) Executions.getCurrent().getDesktop().getSession()
				.getAttribute(ConstantesWeb.SESSION_USERNAME);

		String acronimo;

		TrataDTO t = new TrataDTO();
		t.setCodigoTrata(this.trata.getCodigoTrata());
		t = this.trataService.buscarTrataByCodigo(t.getCodigoTrata());
		acronimo = t.getAcronimoDocumento();

		if (null != this.tiposDocumento.getValue()) {
			acronimo = this.tiposDocumento.getValue();
		}

		if (this.tiempoRes.getValue() != null) {
			t.setTiempoResolucion(Integer.valueOf(this.tiempoRes.getValue()));
		} else {
			t.setTiempoResolucion(new Integer(0));
		}

		// Se debe poder guardar sin ningun tipo de documento
		if (!acronimo.equals(SIN_TEMPLATE) && !acronimo.equals("") && !this.contieneAcronimo()) {
			throw new WrongValueException(this.tiposDocumento,
					Labels.getLabel("ee.nuevasolicitud.error.tipoDocumento"));
		}

		if (acronimo != null && !acronimo.equals("") && !SIN_TEMPLATE.equals(acronimo)) {
			t.setAcronimoDocumento(acronimo);
		} else {
			t.setAcronimoDocumento(null);
		}

		// Caratulacion
		if (this.interno.isChecked()) {
			t.setEsExterno(false);
			t.setEsInterno(true);
		} else if (this.ambosRadioCarat.isChecked()) {
			t.setEsExterno(true);
			t.setEsInterno(true);
		} else if (this.externo.isChecked()) {
			t.setEsExterno(true);
			t.setEsInterno(false);
		}

		// Envio Automatico a Guarda Temporal
		if (this.envioGT.isChecked()) {
			t.setEsEnvioAutomaticoGT(true);
		} else {
			t.setEsEnvioAutomaticoGT(false);
		}

		// TODO: GGV - Propiedades no usadas en egoveris. Por eliminar
		// fisicamente del modelo.
		t.setIntegracionAFJG(false);
		t.setIntegracionSisExt(false);

		t.setWorkflow(selectedWorkflow);

		// INI CAMPO TIPO TRAMITE
		if (tipoTrSubproceso != null && tipoTrSubproceso.isChecked()) {
			t.setTipoTramite(TipoTramiteEnum.SUBPROCESO.getValue());
		} else if (tipoTrExpediente != null && tipoTrExpediente.isChecked()) {
			t.setTipoTramite(TipoTramiteEnum.EXPEDIENTE.getValue());
		} else if (tipoTrAmbos != null && tipoTrAmbos.isChecked()) {
			t.setTipoTramite(TipoTramiteEnum.AMBOS.getValue());
		} else {
			// Default
			t.setTipoTramite(TipoTramiteEnum.AMBOS.getValue());
		}
		// FIN CAMPO TIPO TRAMITE

		if (tipoBuzonGrupal != null && tipoBuzonGrupal.isChecked()) {
			t.setTipoCarga(0);
		} else {
			if (tipoCarga != null && tipoCarga.isChecked()) {
				t.setTipoCarga(1);	
			} else {
				throw new WrongValueException(this.tipoCarga,"Debe seleccionar un tipo de Asignacion");
			}
			
		}
		
		 
		
		// INI CAMPO TIPO RESULTADO
		if (listboxTipoResultado != null && !isHasResultTypes()) {
			throw new WrongValueException(this.bandboxTipoResultado,
					Labels.getLabel("ee.nuevaTrata.noHay.tipoResultado"));
		} else if (listboxTipoResultado != null && listboxTipoResultado.getSelectedItems().isEmpty()) {
			throw new WrongValueException(this.bandboxTipoResultado,
					Labels.getLabel("ee.nuevaTrata.falta.tipoResultado"));
		}

		if (listboxTipoResultado != null) {
			List<TrataTipoResultadoDTO> tipoResultadosTrata = new ArrayList<>();

			for (Listitem listitem : listboxTipoResultado.getSelectedItems()) {
				PropertyConfigurationDTO property = listitem.getValue();
				tipoResultadosTrata.add(new TrataTipoResultadoDTO(null, property.getClave()));
			}

			t.setTipoResultadosTrata(tipoResultadosTrata);
		}
		// FIN CAMPO TIPO RESULTADO

		this.trataService.modificarTrata(t, usuario);
		Events.sendEvent(Events.ON_NOTIFY, this.self.getParent(), null);
		this.detalleTrataWindow.onClose();
		Messagebox.show(Labels.getLabel("ee.detalleTrataComp.msgbox.modSatisfacTrata"),
				Labels.getLabel("ee.general.information"), Messagebox.OK, Messagebox.INFORMATION);
	}

	public void onSelect$documento() {
		mostrarForegroundBloqueante();
		Events.echoEvent(Events.ON_USER, this.self, "selectDoc");
	}

	public void onVerHistorial() {
		final HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("trata", this.trata);
		if (this.historialView != null) {
			this.historialView.detach();
			this.historialView.invalidate();
			this.historialView = (Window) Executions.createComponents("/inbox/historialTrata.zul", this.self, hm);
			this.historialView.setPosition("center");
			this.historialView.doModal();
		} else {
			Messagebox.show(Labels.getLabel("ee.general.imposibleIniciarVista"), Labels.getLabel("ee.general.error"),
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void seleccionarDocumento() {

		if (this.tipoDoc != null) {

			tipoDocSel = this.tipoDoc.getAcronimo();
			tipoDocComp = this.tipoDoc.getCodigoTipoDocumentoSade() + " - "
					+ this.tipoDoc.getDescripcionTipoDocumentoSade();
			if (SIN_TEMPLATE.equals(tipoDocSel)) {
				this.tiposDocumento.setValue(tipoDocSel);
			} else {
				this.tiposDocumento.setValue(tipoDocSel.toUpperCase());
			}
			this.binder.loadAll();
			this.tiposDocumento.close();
		}
		Clients.clearBusy();
	}

	public void onSelectComboTipoResultado() {
		bandboxTipoResultado.setValue(ComboTipoResultadoHelper
				.getLabelComboTipoResultado(listboxTipoResultado.getSelectedItems(), getAllResultTypes().size()));
	}

	// Getters - setters

	public Boolean getAmbos() {
		return ambos;
	}

	public Boolean getAmbosCarat() {
		return ambosCarat;
	}

	public Radio getAmbosRadioCarat() {
		return ambosRadioCarat;
	}

	public Radio getAmbosRadios() {
		return ambosRadios;
	}

	public Radio getAutomatica() {
		return automatica;
	}

	public AnnotateDataBinder getBinder() {
		return binder;
	}

	public String getDescripcionTrata() {
		return descripcionTrata;
	}

	public Window getDetalleTrataWindow() {
		return detalleTrataWindow;
	}

	public Checkbox getEnvioGT() {
		return envioGT;
	}

	public ExpedienteElectronicoService getExpedienteElectronicoService() {
		return this.expedienteElectronicoService;
	}

	public Radio getExterno() {
		return externo;
	}

	public Window getHistorialView() {
		return historialView;
	}

	public Radio getInterno() {
		return interno;
	}

	public List<TipoDocumentoDTO> getListaDocs() {
		return listaDocs;
	}

	public Radio getManual() {
		return manual;
	}

	public Radio getReservaParcialDetalle() {
		return reservaParcialDetalle;
	}

	public Radio getReservaTotalDetalle() {
		return reservaTotalDetalle;
	}

	public String getSelectedWorkflow() {
		return selectedWorkflow;
	}

	public Radio getSinReservaDetalle() {
		return sinReservaDetalle;
	}

	public Intbox getTiempoRes() {
		return this.tiempoRes;
	}

	public String getTipoActuacion() {
		return tipoActuacion;
	}

	public TipoDocumentoDTO getTipoDoc() {
		return tipoDoc;
	}

	public List<TipoDocumentoDTO> getTiposDeDocumento() {
		return tiposDeDocumento;
	}

	public List<TipoDocumentoDTO> getTiposDeDocumentoSeleccionados() {
		return tiposDeDocumentoSeleccionados;
	}

	public TrataDTO getTrata() {
		return trata;
	}

	public String getTrataSade() {
		return trataSade;
	}

	public TrataService getTrataService() {
		return trataService;
	}

	public Button getVerHistorial() {
		return verHistorial;
	}

	/**
	 * @return the workflow
	 */
	public List<String> getWorkflows() {
		if (workflows == null) {
			this.workflows = TramitacionHelper.findActiveWorkflows();
		}
		return workflows;
	}

	public void setAmbos(final Boolean ambos) {
		this.ambos = ambos;
	}

	public void setAmbosCarat(final Boolean esAmbosCarat) {
		this.ambosCarat = esAmbosCarat;
	}

	public void setAmbosRadioCarat(final Radio ambosRadioCarat) {
		this.ambosRadioCarat = ambosRadioCarat;
	}

	public void setAmbosRadios(final Radio ambosRadios) {
		this.ambosRadios = ambosRadios;
	}

	public void setAutomatica(final Radio automatica) {
		this.automatica = automatica;
	}

	public void setBinder(final AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public void setDescripcionTrata(final String descripcionTrata) {
		this.descripcionTrata = descripcionTrata;
	}

	public void setDetalleTrataWindow(final Window detalleTrataWindow) {
		this.detalleTrataWindow = detalleTrataWindow;
	}

	public void setEnvioGT(final Checkbox envioGT) {
		this.envioGT = envioGT;
	}

	public void setExpedienteElectronicoService(final ExpedienteElectronicoService expedienteElectronicoService) {
		this.expedienteElectronicoService = expedienteElectronicoService;
	}

	public void setExterno(final Radio externo) {
		this.externo = externo;
	}

	public void setHistorialView(final Window historialView) {
		this.historialView = historialView;
	}

	public void setInterno(final Radio interno) {
		this.interno = interno;
	}

	public void setListaDocs(final List<TipoDocumentoDTO> listaDocs) {
		this.listaDocs = listaDocs;
	}

	public void setManual(final Radio manual) {
		this.manual = manual;
	}

	public void setReservaParcialDetalle(final Radio reservaParcialDetalle) {
		this.reservaParcialDetalle = reservaParcialDetalle;
	}

	public void setReservaTotalDetalle(final Radio reservaTotalDetalle) {
		this.reservaTotalDetalle = reservaTotalDetalle;
	}

	/**
	 * @param selectedWorkflow
	 *            the selectedWorkflow to set
	 */
	public void setSelectedWorkflow(final String selectedWorkflow) {
		this.selectedWorkflow = selectedWorkflow;
	}

	public void setSinReservaDetalle(final Radio sinReservaDetalle) {
		this.sinReservaDetalle = sinReservaDetalle;
	}

	public void setTiempoRes(final Intbox tiempoRes) {
		this.tiempoRes = tiempoRes;
	}

	public void setTipoActuacion(final String tipoActuacion) {
		this.tipoActuacion = tipoActuacion;
	}

	public void setTipoDoc(final TipoDocumentoDTO tipoDoc) {
		this.tipoDoc = tipoDoc;
	}

	public void setTiposDeDocumento(final List<TipoDocumentoDTO> tiposDeDocumento) {
		this.tiposDeDocumento = tiposDeDocumento;
	}

	public void setTiposDeDocumentoSeleccionados(final List<TipoDocumentoDTO> tiposDeDocumentoSeleccionados) {
		this.tiposDeDocumentoSeleccionados = tiposDeDocumentoSeleccionados;
	}

	public void setTrata(final TrataDTO trata) {
		this.trata = trata;
	}

	public void setTrataSade(final String trataSade) {
		this.trataSade = trataSade;
	}

	public void setTrataService(final TrataService trataService) {
		this.trataService = trataService;
	}

	public void setVerHistorial(final Button verHistorial) {
		this.verHistorial = verHistorial;
	}
	 /**
	 * @return the tipoBuzonGrupal
	 */
	public Radio getTipoBuzonGrupal() {
		return tipoBuzonGrupal;
	}

	/**
	 * @param tipoBuzonGrupal the tipoBuzonGrupal to set
	 */
	public void setTipoBuzonGrupal(Radio tipoBuzonGrupal) {
		this.tipoBuzonGrupal = tipoBuzonGrupal;
	}

	/**
	 * @return the tipoCarga
	 */
	public Radio getTipoCarga() {
		return tipoCarga;
	}

	/**
	 * @param tipoCarga the tipoCarga to set
	 */
	public void setTipoCarga(Radio tipoCarga) {
		this.tipoCarga = tipoCarga;
	}
	/**
	 * @param workflow
	 *            the workflow to set
	 */
	public void setWorkflows(final List<String> workflow) {
		this.workflows = workflow;
	}

	private boolean tieneReserva() {
		return this.trata.getTipoReserva().getTipoReserva().equals(ConstantesWeb.SIN_RESERVA) ? true : false;
	}

	public Radio getTipoTrSubproceso() {
		return tipoTrSubproceso;
	}

	public void setTipoTrSubproceso(Radio tipoTrSubproceso) {
		this.tipoTrSubproceso = tipoTrSubproceso;
	}

	public Radio getTipoTrExpediente() {
		return tipoTrExpediente;
	}

	public void setTipoTrExpediente(Radio tipoTrExpediente) {
		this.tipoTrExpediente = tipoTrExpediente;
	}

	public Bandbox getBandboxTipoResultado() {
		return bandboxTipoResultado;
	}

	public void setBandboxTipoResultado(Bandbox bandboxTipoResultado) {
		this.bandboxTipoResultado = bandboxTipoResultado;
	}

	public Listbox getListboxTipoResultado() {
		return listboxTipoResultado;
	}

	public void setListboxTipoResultado(Listbox listboxTipoResultado) {
		this.listboxTipoResultado = listboxTipoResultado;
	}

	public List<PropertyConfigurationDTO> getAllResultTypes() {
		if (allResultTypes == null) {
			allResultTypes = new ArrayList<>();
		}

		return allResultTypes;
	}

	public void setAllResultTypes(List<PropertyConfigurationDTO> allResultTypes) {
		this.allResultTypes = allResultTypes;
	}

	public List<PropertyConfigurationDTO> getSelectedResultTypes() {
		if (selectedResultTypes == null) {
			selectedResultTypes = new ArrayList<>();
		}

		return selectedResultTypes;
	}

	public void setSelectedResultTypes(List<PropertyConfigurationDTO> selectedResultTypes) {
		this.selectedResultTypes = selectedResultTypes;
	}

	public boolean isHasResultTypes() {
		return allResultTypes != null && !allResultTypes.isEmpty();
	}

	public void setHasResultTypes(boolean hasResultTypes) {
		this.hasResultTypes = hasResultTypes;
	}
}

class ManejadorEvento implements EventListener {
	DetalleTrataComposer composer;

	public ManejadorEvento(final DetalleTrataComposer c) {
		this.composer = c;
	}

	@Override
	public void onEvent(final Event e) throws Exception {

		if (e.getName().equals(Events.ON_USER) && e.getData() != null && e.getData().equals("selectDoc")) {
			composer.seleccionarDocumento();
		}

	}
}
