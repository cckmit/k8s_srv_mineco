package com.egoveris.te.base.composer;

import java.util.ArrayList;
import java.util.Collection;
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
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelArray;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.egoveris.te.base.model.ParametrosSistemaExternoDTO;
import com.egoveris.te.base.model.PropertyConfigurationDTO;
import com.egoveris.te.base.model.ReparticionDTO;
import com.egoveris.te.base.model.TipoDocumentoDTO;
import com.egoveris.te.base.model.TrataDTO;
import com.egoveris.te.base.model.TrataIntegracionReparticionDTO;
import com.egoveris.te.base.model.TrataTipoDocumentoDTO;
import com.egoveris.te.base.model.TrataTipoResultadoDTO;
import com.egoveris.te.base.service.IPropertyConfigurationService;
import com.egoveris.te.base.service.TipoDocumentoService;
import com.egoveris.te.base.service.TipoReservaService;
import com.egoveris.te.base.service.TrataService;
import com.egoveris.te.base.util.ComboTipoResultadoHelper;
import com.egoveris.te.base.util.ConfiguracionInicialModuloEEFactory;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.MantainerPrefix;
import com.egoveris.te.base.util.TipoTramiteEnum;
import com.egoveris.te.base.util.TramitacionHelper;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class CrearTrataComposer extends EEGenericForwardComposer {

	final class TramitacionOnNotifyWindowListener implements EventListener {
		private final TramitacionComposer composer;

		public TramitacionOnNotifyWindowListener(final TramitacionComposer tramitacionComposer) {
			this.composer = tramitacionComposer;
		}

		@Override
		public void onEvent(final Event event) throws Exception {
			if (event.getData().equals("envioSistemaExterno")) {
				this.composer.enviarASistemaExterno();
			}
		}
	}

	private static final String RESERVA_PARCIAL = "PARCIAL";
	private static final String RESERVA_TOTAL = "TOTAL";
	private static final String SIN_RESERVA = "SIN RESERVA";
	private static final String SIN_TEMPLATE = "Sin Template";

	public static String getSinTemplate() {
		return SIN_TEMPLATE;
	}

	@Autowired
	private Window crearTrataWindow;

	private List<TrataDTO> tiposTrata = null;
	private TrataDTO selectedTipoTrata = null;
	private TrataDTO trata = new TrataDTO();
	private List<TrataDTO> tratasSADE;
	private List<String> workflows;
	private TrataDTO selectedTrataSADE = new TrataDTO();

	private String selectedWorkflow;
	private String cuil;
	private ParametrosSistemaExternoDTO model;

	@Autowired
	private Textbox codigoTrata;
	@Autowired
	private Textbox descripcionTrata;
	@Autowired
	private Button guardar;
	@Autowired
	private Bandbox trataSADE;
	@Autowired
	private Radio sinReserva;
	@Autowired
	private Radio reservaParcial;
	@Autowired
	private Radio reservaTotal;
	@Autowired
	private Radio automatica;
	@Autowired
	private Radio ambos;
	@Autowired
	private Radio manual;
	@Autowired
	private Combobox workflow;
	@Autowired
	private Intbox tiempoRes;
	@Autowired
	private Radio interno;
	@Autowired
	private Radio externo;
	@Autowired
	private Checkbox envioGT;
	@Autowired
	private Radio ambosRadioCarat;

	private TrataDTO selectedTrata;

	private List<String> actuaciones;
	private String selectedActuacion;

	@Autowired
	private Combobox comboActuacion;
	@Autowired
	private Bandbox tiposDocumento;

	protected List<TipoDocumentoDTO> tiposDeDocumento;
	protected List<TipoDocumentoDTO> tiposDeDocumentoSeleccionados;
	protected Listbox documento;
	protected List<TipoDocumentoDTO> listaDocs;
	protected TipoDocumentoDTO tipoDoc;
	private String tipoDocSel;

	private String tipoDocComp;
	private Button botonSistemaExterno;
	private int flagCreate;

	@WireVariable(ConstantesServicios.TIPO_DOCUMENTO_SERVICE)
	private TipoDocumentoService tipoDocumentoService;

	@Autowired
	private Listbox trataListbox;
	private List<TrataDTO> tratas;
	private String descripcionTrata2;
	private String trataSadeString;

	@WireVariable(ConstantesServicios.TRATA_SERVICE)
	private TrataService trataService;

	@WireVariable(ConstantesServicios.TIPO_RESERVA_TE_SERVICE)
	private TipoReservaService tipoReservaService;

	private String usuario;
	private List<TrataDTO> tratasSeleccionadas;
	private AnnotateDataBinder binder;

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
	private boolean hasResultTypes;
	// FIN CAMPO TIPO RESULTADO

	private void agregarValorTodas(final String cod) {
		if (!existeCodigoEnListasExistentes(cod)) {
			final TrataIntegracionReparticionDTO t = new TrataIntegracionReparticionDTO();
			t.setCodigoReparticion(cod);
			t.setIdTrata(trata.getId());
			t.setHabilitada(true);
			model.getReparticionesIntegracion().addAll(0, (Collection<? extends TrataIntegracionReparticionDTO>) t);
			model.setIdTrata(trata.getId());
			model.setCodigo("");
			model.setUrl("");
			model.setEsactivo(true);
			ConfiguracionInicialModuloEEFactory.guardarParametros(model);
		}
	}

	private boolean contieneAcronimo() {
		if (this.tipoDoc != null) {
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
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));

		this.binder = new AnnotateDataBinder(comp);

		flagCreate = this.trataService.buscarFlagCreacionTramite();
		setAllResultTypes(propertyConfigurationService.getPropertiesWithPrefix(MantainerPrefix.RESULT_TYPE));
		this.tipoBuzonGrupal.setChecked(true);
		if (flagCreate == 1) {
			this.tipoBuzonGrupal.setChecked(true);
			codigoTrata.setReadonly(false);
			descripcionTrata.setReadonly(false);
			workflow.setReadonly(false);
			comboActuacion.setReadonly(false);
			tiempoRes.setReadonly(true);
			this.trataSADE.setReadonly(true);
			this.trataSADE.setDisabled(true);
			this.descripcionTrata2 = "";
			this.trataSadeString = "";
			this.tratas = this.getTratasSADE();

			if (null != this.trata.getAcronimoDocumento() && !this.trata.getAcronimoDocumento().equals("")) {
				this.tiposDocumento.setValue(this.trata.getAcronimoDocumento());
			} else {
				this.tiposDocumento.setValue(SIN_TEMPLATE);
			}

			this.descripcionTrata.setText("");
			loadComboActuaciones();
			listaDocs = loadComboTipoDoc();
			tiposDeDocumentoSeleccionados = new ArrayList<TipoDocumentoDTO>(this.listaDocs);

			this.workflow.setValue("solicitud");
			selectedWorkflow = "solicitud";
			this.guardar.setDisabled(false);
		} else {
			this.tipoBuzonGrupal.setChecked(true);
			codigoTrata.setReadonly(true);
			descripcionTrata.setReadonly(true);
			workflow.setReadonly(true);
			comboActuacion.setReadonly(true);
			tiempoRes.setReadonly(false);

			this.descripcionTrata2 = "";
			this.trataSadeString = "";
			this.tratas = this.getTratasSADE();

			if (null != this.trata.getAcronimoDocumento() && !this.trata.getAcronimoDocumento().equals("")) {
				this.tiposDocumento.setValue(this.trata.getAcronimoDocumento());
			} else {
				this.tiposDocumento.setValue(SIN_TEMPLATE);
			}

			this.tratasSeleccionadas = new ArrayList<>(this.tratas);
			this.descripcionTrata.setText("");
			loadComboActuaciones();
			listaDocs = loadComboTipoDoc();
			tiposDeDocumentoSeleccionados = new ArrayList<>(this.listaDocs);

			this.workflow.setValue("solicitud");
			selectedWorkflow = "solicitud";
		}
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

	private void inicializarEmpty() {
		model = new ParametrosSistemaExternoDTO();
		model.setReparticionesIntegracion(new ArrayList<TrataIntegracionReparticionDTO>());
	}

	private void loadComboActuaciones() {
		comboActuacion.setModel(new ListModelArray(this.getActuaciones()));
		comboActuacion.setItemRenderer(new ComboitemRenderer() {

			@Override
			public void render(final Comboitem item, final Object data, final int arg1) throws Exception {
				final String actuacion = (String) data;
				item.setLabel(actuacion);
				item.setValue(actuacion);

				if (actuacion.equals(ConstantesWeb.ACTUACION_EX)) {
					comboActuacion.setSelectedItem(item);
				}
			}
		});
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

	public void onBlur$tiposDocumento() {
		if (this.tiposDocumento.getValue() != null && !this.tiposDocumento.getValue().trim().equals("")) {
			this.tipoDocSel = this.tiposDocumento.getValue().toUpperCase();
		}
	}

	public void onBlur$trataSADE() throws InterruptedException {
		String trataSel = null;
		if (flagCreate == 1) {
			trataSel = this.codigoTrata.getValue();
			this.selectedTrataSADE.setDescripcion(this.descripcionTrata.getValue());
			this.trataSADE.setValue(trataSel.toUpperCase());
		} else {
			if (this.trataSADE.getValue() != null && !this.trataSADE.getValue().trim().equals("")) {
				trataSel = this.trataSADE.getValue().toUpperCase();
			}
		}

		if (this.selectedTrataSADE.getDescripcion() != null) {
			this.descripcionTrata2 = this.selectedTrataSADE.getDescripcion();
		} else {
			if (trataSel != null) {
				this.descripcionTrata2 = this.trataService.obtenerDescripcionTrataByCodigo(trataSel);
			}
		}

		this.trataSadeString = this.trataService.formatoToStringTrata(trataSel, this.descripcionTrata2);
		if (!this.trataService.esTrataUtilizadaEnEE(trataSel)) {
			String descripcionTrata = null;
			if (this.selectedTrataSADE.getDescripcion() != null
					&& this.selectedTrataSADE.getDescripcion().length() > 1) {
				descripcionTrata = selectedTrataSADE.getDescripcion();
			} else {
				if (trataSel != null) {
					descripcionTrata = this.trataService
							.obtenerDescripcionTrataByCodigo(this.selectedTrataSADE.getCodigoTrata());
				}
			}

			this.codigoTrata.setValue(trataSel);
			this.descripcionTrata.setValue(descripcionTrata);
			this.trata.setCodigoTrata(trataSel);
			this.trata.setDescripcion(descripcionTrata);
			this.guardar.setDisabled(false);
		} else {
			this.guardar.setDisabled(true);
			Messagebox.show(Labels.getLabel("ee.crearTrataComp.msgbox.tratSelecYaTratElec"),
					Labels.getLabel("ee.general.error"), Messagebox.OK, Messagebox.ERROR);
		}

		if (trataSel == null) {
			this.guardar.setDisabled(true);
			Messagebox.show(Labels.getLabel("ee.crearTrataComp.msgbox.debeSelecTrata"),
					Labels.getLabel("ee.general.error"), Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void onCancelar() throws InterruptedException {
		((Window) this.self).onClose();
	}

	public void onChange$trataSADE() throws InterruptedException {
		final String codigoTrata = this.selectedTrataSADE.getCodigoTrata();
		this.descripcionTrata2 = this.trataService.obtenerDescripcionTrataByCodigo(codigoTrata);
		this.trataSadeString = this.trataService.formatoToStringTrata(codigoTrata, this.descripcionTrata2);

		if (!this.trataService.esTrataUtilizadaEnEE(codigoTrata)) {
			final String descripcionTrata = this.trataService
					.obtenerDescripcionTrataByCodigo(this.selectedTrataSADE.getCodigoTrata());
			this.codigoTrata.setValue(codigoTrata);
			this.descripcionTrata.setValue(descripcionTrata);
			this.trata.setCodigoTrata(codigoTrata);
			this.trata.setDescripcion(descripcionTrata);
			this.guardar.setDisabled(false);
		} else {
			this.guardar.setDisabled(true);
			// throw new WrongValueException("La trata seleccionada ya es una
			// trata electrónica.");
			Messagebox.show(Labels.getLabel("ee.crearTrataComp.msgbox.tratSelecYaTratElec"),
					Labels.getLabel("ee.general.error"), Messagebox.OK, Messagebox.ERROR);
		}

		if (codigoTrata == null) {
			this.guardar.setDisabled(true);
			// throw new WrongValueException("Debe seleccionar correctamente una
			// trata.");
			Messagebox.show(Labels.getLabel("ee.crearTrataComp.msgbox.debeSelecTrata"),
					Labels.getLabel("ee.general.error"), Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void onChanging$tiposDocumento(final InputEvent e) {
		String matchingText = e.getValue();
		this.tiposDeDocumentoSeleccionados.clear();

		if (!matchingText.equals("") && matchingText.length() >= 3) {
			if (this.listaDocs != null) {
				matchingText = matchingText.toUpperCase();

				final Iterator<TipoDocumentoDTO> iterator = this.listaDocs.iterator();
				TipoDocumentoDTO documento = null;

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

	public void onClick$botonSistemaExterno() throws SuspendNotAllowedException, InterruptedException {

		final TrataTipoDocumentoDTO tdt = validarTrata();

		final Map<String, Object> params = new HashMap<String, Object>();
		params.put("trata", this.trata);
		params.put("trataNueva", true);
		params.put("trataTipoDocumento", tdt);
		final Window w = (Window) Executions.createComponents("/inbox/trataIntegracion.zul", this.self, params);
		w.doModal();
	}

	public void onFocus$tiposDocumento() {
		if (SIN_TEMPLATE.equals(tiposDocumento.getValue())) {
			tiposDocumento.setValue("");
		}
	}

	public void onGuardarTrata() throws InterruptedException {
		if (flagCreate == 1) {
			
			if (tipoBuzonGrupal != null && tipoBuzonGrupal.isChecked()) { 
			} else {
				if (tipoCarga != null && tipoCarga.isChecked()) { 
				} else {
					throw new WrongValueException(this.tipoCarga,"Debe seleccionar un tipo de Asignacion");
				}
				
			}
			
			if (this.codigoTrata.getValue().isEmpty() || this.descripcionTrata.getValue().isEmpty()) {
				
				if (this.codigoTrata.getValue().isEmpty()) {
					throw new WrongValueException(this.codigoTrata,
							Labels.getLabel("ee.nuevaTrata.falta.codigo.tramite"));
				} else if (this.descripcionTrata.getValue().isEmpty()) {
					throw new WrongValueException(this.descripcionTrata,
							Labels.getLabel("ee.nuevaTrata.falta.codigo.descripcion"));
				} else {
					throw new WrongValueException(this.codigoTrata,
							Labels.getLabel("ee.nuevaTrata.falta.codigo.tramite"));
				}
			} else if (!isHasResultTypes()) {
				throw new WrongValueException(this.bandboxTipoResultado,
						Labels.getLabel("ee.nuevaTrata.noHay.tipoResultado"));
			} else if (this.listboxTipoResultado.getSelectedItems().isEmpty()) {
				throw new WrongValueException(this.bandboxTipoResultado,
						Labels.getLabel("ee.nuevaTrata.falta.tipoResultado"));
			}
			
			
			
			

			onBlur$trataSADE();
		}

		final TrataTipoDocumentoDTO tt = validarTrata();

		if (this.trata.getIntegracionSisExt()) {
			throw new WrongValueException(this.botonSistemaExterno, Labels.getLabel("ee.nuevaTrata.sistemaExterno"));
		}
		
		
		if (tipoBuzonGrupal != null && tipoBuzonGrupal.isChecked()) {
			tt.getTrata().setTipoCarga(0);
		} else {
			if (tipoCarga != null && tipoCarga.isChecked()) {
				tt.getTrata().setTipoCarga(1);
			} else {
				throw new WrongValueException(this.tipoCarga,"Debe seleccionar un tipo de Asignacion");
			}
			
		}
		
		

		// Hay que mover al servicio para dar de alta la trata
		this.trataService.darAltaTrata(this.trata, usuario);
		this.tipoDocumentoService.cargarAuditoria(tt, TrataTipoDocumentoDTO.ESTADO_ACTIVO, usuario);

		if (this.trata.getIntegracionAFJG()) {
			inicializarEmpty();
			agregarValorTodas("--TODAS--");
		}

		Messagebox.show(Labels.getLabel("ee.nuevaTrata.nuevoTipoCreado", new String[] { this.trata.getCodigoTrata() }),
				Labels.getLabel("ee.general.information"), Messagebox.OK, Messagebox.INFORMATION);
		this.closeAndNotifyAssociatedWindow(null);
	}

	public void onSelect$documento() {
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
	}

	public void onSelect$trataListbox() throws InterruptedException {
		String trataSel;
		if (flagCreate == 1) {
			trataSel = this.codigoTrata.getValue();
		} else {
			trataSel = this.selectedTrata.getCodigoTrata();
		}
		this.trataSADE.setValue(trataSel.toUpperCase());
		this.binder.loadAll();
		this.trataSADE.close();
		this.selectedTrataSADE = this.selectedTrata;
		this.onBlur$trataSADE();
	}

	public TrataTipoDocumentoDTO validarTrata() {
		this.usuario = (String) Executions.getCurrent().getDesktop().getSession()
				.getAttribute(ConstantesWeb.SESSION_USERNAME);

		if (this.trataSADE.getValue() == null || this.trataSADE.getValue().equals("")) {
			throw new WrongValueException(this.trataSADE, Labels.getLabel("ee.nuevaTrata.faltaTrataSade"));
		}

		// Nueva funcionalidad
		if (this.automatica.isChecked()) {
			this.trata.setEsManual(false);
			this.trata.setEsAutomatica(true);
		} else if (this.ambos.isChecked()) {
			this.trata.setEsManual(true);
			this.trata.setEsAutomatica(true);
		} else if (this.manual.isChecked()) {
			this.trata.setEsManual(true);
			this.trata.setEsAutomatica(false);
		}

		if (this.reservaParcial.isChecked()) {
			this.trata.setTipoReserva(this.tipoReservaService.buscarTipoReserva(RESERVA_PARCIAL));
		} else if (this.reservaTotal.isChecked()) {
			this.trata.setTipoReserva(this.tipoReservaService.buscarTipoReserva(RESERVA_TOTAL));
		} else if (this.sinReserva.isChecked()) {
			this.trata.setTipoReserva(this.tipoReservaService.buscarTipoReserva(SIN_RESERVA));
		}

		// Caratulacion
		if (this.interno.isChecked()) {
			this.trata.setEsExterno(false);
			this.trata.setEsInterno(true);
		} else if (this.ambosRadioCarat.isChecked()) {
			this.trata.setEsExterno(true);
			this.trata.setEsInterno(true);
		} else if (this.externo.isChecked()) {
			this.trata.setEsExterno(true);
			this.trata.setEsInterno(false);
		}

		this.trata.setEsNotificableTad(false);

		// Envio Automatico a Guarda Temporal
		if (this.envioGT.isChecked()) {
			this.trata.setEsEnvioAutomaticoGT(true);
		} else {
			this.trata.setEsEnvioAutomaticoGT(false);
		}

		this.trata.setNotificableJMS(false);

		this.trata.setIntegracionAFJG(false);
		this.trata.setIntegracionSisExt(false);

		final TrataTipoDocumentoDTO trataTipoDocumento = new TrataTipoDocumentoDTO();
		trataTipoDocumento.setAcronimoGEDO(ConstantesWeb.SELECCIONAR_TODOS);
		trataTipoDocumento.setTrata(this.trata);
		this.trata.getListaTrataTipoDocumento().add(trataTipoDocumento);

		String acronimo = "";
		String selectedWF = "";

		// Esto se hace para evitar ingresar un texto en el combo que no
		// corresponda a ninguna opcion
		for (String wf : workflows) {
			if (wf.equalsIgnoreCase(selectedWorkflow)) {
				selectedWF = selectedWorkflow;
				break;
			}
		}

		this.trata.setWorkflow(selectedWF);
		acronimo = trata.getAcronimoDocumento();

		if (null != this.tiposDocumento.getValue()) {
			acronimo = this.tiposDocumento.getValue();
		}

		if (!acronimo.equals(SIN_TEMPLATE) && !acronimo.equals("") && !this.contieneAcronimo()) {
			throw new WrongValueException(this.tiposDocumento,
					Labels.getLabel("ee.nuevasolicitud.error.tipoDocumento"));
		}

		if (acronimo != null && !acronimo.equals("") && !SIN_TEMPLATE.equals(acronimo)) {
			trata.setAcronimoDocumento(acronimo);
		} else {
			trata.setAcronimoDocumento(null);
		}

		if (this.comboActuacion.getValue() != null && !this.comboActuacion.getValue().equals("")) {
			this.trata.setTipoActuacion((String) this.comboActuacion.getSelectedItem().getValue());
		}

		if (this.tiempoRes.getValue() != null && !this.tiempoRes.getValue().equals("")) {
			this.trata.setTiempoResolucion(Integer.valueOf(this.tiempoRes.getValue()));
		}

		// INI CAMPO TIPO TRAMITE
		if (tipoTrSubproceso != null && tipoTrSubproceso.isChecked()) {
			this.trata.setTipoTramite(TipoTramiteEnum.SUBPROCESO.getValue());
		} else if (tipoTrExpediente != null && tipoTrExpediente.isChecked()) {
			this.trata.setTipoTramite(TipoTramiteEnum.EXPEDIENTE.getValue());
		} else if (tipoTrAmbos != null && tipoTrAmbos.isChecked()) {
			this.trata.setTipoTramite(TipoTramiteEnum.AMBOS.getValue());
		} else {
			// Default
			this.trata.setTipoTramite(TipoTramiteEnum.AMBOS.getValue());
		}
		// FIN CAMPO TIPO TRAMITE

		// INI CAMPO TIPO RESULTADO
		if (listboxTipoResultado != null) {
			List<TrataTipoResultadoDTO> tipoResultadosTrata = new ArrayList<>();

			for (Listitem listitem : listboxTipoResultado.getSelectedItems()) {
				PropertyConfigurationDTO property = listitem.getValue();
				tipoResultadosTrata.add(new TrataTipoResultadoDTO(null, property.getClave()));
			}

			this.trata.setTipoResultadosTrata(tipoResultadosTrata);
		}
		// FIN CAMPO TIPO RESULTADO

		this.binder.loadAll();
		return trataTipoDocumento;
	}

	public void onSelectComboTipoResultado() {
		bandboxTipoResultado.setValue(ComboTipoResultadoHelper
				.getLabelComboTipoResultado(listboxTipoResultado.getSelectedItems(), getAllResultTypes().size()));
	}

	// Getters - setters

	public List<String> getActuaciones() {
		if (actuaciones == null) {
			this.actuaciones = TramitacionHelper.findActuaciones();
		}

		return actuaciones;
	}

	public Radio getAmbos() {
		return ambos;
	}

	public Radio getAutomatica() {
		return automatica;
	}

	public Textbox getCodigoTrata() {
		return codigoTrata;
	}

	public Window getCrearTrataWindow() {
		return crearTrataWindow;
	}

	public String getCuil() {
		return cuil;
	}

	public Textbox getDescripcionTrata() {
		return descripcionTrata;
	}

	public String getDescripcionTrata2() {
		return descripcionTrata2;
	}

	public Listbox getDocumento() {
		return documento;
	}

	public List<TipoDocumentoDTO> getListaDocs() {
		return listaDocs;
	}

	public Radio getManual() {
		return manual;
	}

	public Radio getReservaParcial() {
		return reservaParcial;
	}

	public Radio getReservaTotal() {
		return reservaTotal;
	}

	public String getSelectedActuacion() {
		return selectedActuacion;
	}

	public TrataDTO getSelectedTipoTrata() {
		return selectedTipoTrata;
	}

	public TrataDTO getSelectedTrata() {
		return selectedTrata;
	}

	public TrataDTO getSelectedTrataSADE() {
		return selectedTrataSADE;
	}

	public String getSelectedWorkflow() {
		return selectedWorkflow;
	}

	public Radio getSinReserva() {
		return sinReserva;
	}

	public Intbox getTiempoRes() {
		return this.tiempoRes;
	}

	public TipoDocumentoDTO getTipoDoc() {
		return tipoDoc;
	}

	public String getTipoDocSel() {
		return tipoDocSel;
	}

	public TipoDocumentoService getTipoDocumentoService() {
		return tipoDocumentoService;
	}

	public List<TipoDocumentoDTO> getTiposDeDocumento() {
		return tiposDeDocumento;
	}

	public List<TipoDocumentoDTO> getTiposDeDocumentoSeleccionados() {
		return tiposDeDocumentoSeleccionados;
	}

	public Bandbox getTiposDocumento() {
		return tiposDocumento;
	}

	public List<TrataDTO> getTiposTrata() {
		return tiposTrata;
	}

	public TrataDTO getTrata() {
		return trata;
	}

	public Listbox getTrataListbox() {
		return trataListbox;
	}

	public String getTrataSade() {
		return trataSadeString;
	}

	public List<TrataDTO> getTratasSADE() {
		if (this.tratasSADE == null) {
			this.tratasSADE = this.trataService.obtenerListaTodasLasTratas();

			if (tratasSADE != null && tratasSADE.size() > 0) {
				Collections.sort(this.tratasSADE, new Comparator<TrataDTO>() {
					@Override
					public int compare(final TrataDTO td1, final TrataDTO td2) {
						return trataService.formatoToStringTrata(td1.getCodigoTrata(), td1.getDescripcion()).compareTo(
								trataService.formatoToStringTrata(td2.getCodigoTrata(), td2.getDescripcion()));
					}
				});

			}
		}

		return tratasSADE;
	}

	public List<TrataDTO> getTratasSeleccionadas() {
		return tratasSeleccionadas;
	}

	public List<String> getWorkflows() {
		if (workflows == null) {
			this.workflows = TramitacionHelper.findActiveWorkflows();
		}

		return workflows;
	}

	public void setActuaciones(final List<String> actuaciones) {
		this.actuaciones = actuaciones;
	}

	public void setAmbos(final Radio ambos) {
		this.ambos = ambos;
	}

	public void setAutomatica(final Radio automatica) {
		this.automatica = automatica;
	}

	public void setCodigoTrata(final Textbox codigoTrata) {
		this.codigoTrata = codigoTrata;
	}

	public void setCrearTrataWindow(final Window crearTrataWindow) {
		this.crearTrataWindow = crearTrataWindow;
	}

	public void setCuil(final String cuil) {
		this.cuil = cuil;
	}

	public void setDescripcionTrata(final Textbox descripcionTrata) {
		this.descripcionTrata = descripcionTrata;
	}

	public void setDescripcionTrata2(final String descripcionTrata2) {
		this.descripcionTrata2 = descripcionTrata2;
	}

	public void setDocumento(final Listbox documento) {
		this.documento = documento;
	}

	public void setListaDocs(final List<TipoDocumentoDTO> listaDocs) {
		this.listaDocs = listaDocs;
	}

	public void setManual(final Radio manual) {
		this.manual = manual;
	}

	public void setReservaParcial(final Radio reservaParcial) {
		this.reservaParcial = reservaParcial;
	}

	public void setReservaTotal(final Radio reservaTotal) {
		this.reservaTotal = reservaTotal;
	}

	public void setSelectedActuacion(final String selectedActuacion) {
		this.selectedActuacion = selectedActuacion;
	}

	public void setSelectedTipoTrata(final TrataDTO selectedTipoTrata) {
		this.selectedTipoTrata = selectedTipoTrata;
	}

	public void setSelectedTrata(final TrataDTO selectedTrata) {
		this.selectedTrata = selectedTrata;
	}

	public void setSelectedTrataSADE(final TrataDTO selectedTrataSADE) {
		this.selectedTrataSADE = selectedTrataSADE;
	}

	public void setSelectedWorkflow(final String selectedWorkflow) {
		this.selectedWorkflow = selectedWorkflow;
	}

	public void setSinReserva(final Radio sinReserva) {
		this.sinReserva = sinReserva;
	}

	public void setTiempoRes(final Intbox tiempoRes) {
		this.tiempoRes = tiempoRes;
	}

	public void setTipoDoc(final TipoDocumentoDTO tipoDoc) {
		this.tipoDoc = tipoDoc;
	}

	public void setTipoDocSel(final String tipoDocSel) {
		this.tipoDocSel = tipoDocSel;
	}

	public void setTipoDocumentoService(final TipoDocumentoService tipoDocumentoService) {
		this.tipoDocumentoService = tipoDocumentoService;
	}

	public void setTiposDeDocumento(final List<TipoDocumentoDTO> tiposDeDocumento) {
		this.tiposDeDocumento = tiposDeDocumento;
	}

	public void setTiposDeDocumentoSeleccionados(final List<TipoDocumentoDTO> tiposDeDocumentoSeleccionados) {
		this.tiposDeDocumentoSeleccionados = tiposDeDocumentoSeleccionados;
	}

	public void setTiposDocumento(final Bandbox tiposDocumento) {
		this.tiposDocumento = tiposDocumento;
	}

	public void setTiposTrata(final List<TrataDTO> tiposTrata) {
		this.tiposTrata = tiposTrata;
	}

	public void setTrata(final TrataDTO trata) {
		this.trata = trata;
	}

	public void setTrataListbox(final Listbox trataListbox) {
		this.trataListbox = trataListbox;
	}

	public void setTrataSade(final String trataSade) {
		this.trataSadeString = trataSade;
	}

	public void setTratasSADE(final List<TrataDTO> tratasSADE) {
		this.tratasSADE = tratasSADE;
	}

	public void setTratasSeleccionadas(final List<TrataDTO> tratasSeleccionadas) {
		this.tratasSeleccionadas = tratasSeleccionadas;
	}

	public void setWorkflows(final List<String> workflows) {
		this.workflows = workflows;
	}

	public Radio getTipoTrSubproceso() {
		return tipoTrSubproceso;
	}

	/**
	 * @return the tipoBuzonGrupal
	 */
	public Radio getTipoBuzonGrupal() {
		return tipoBuzonGrupal;
	}

	/**
	 * @param tipoBuzonGrupal
	 *            the tipoBuzonGrupal to set
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
	 * @param tipoCarga
	 *            the tipoCarga to set
	 */
	public void setTipoCarga(Radio tipoCarga) {
		this.tipoCarga = tipoCarga;
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
		return allResultTypes;
	}

	public void setAllResultTypes(List<PropertyConfigurationDTO> allResultTypes) {
		this.allResultTypes = allResultTypes;
	}

	public boolean isHasResultTypes() {
		return allResultTypes != null && !allResultTypes.isEmpty();
	}

	public void setHasResultTypes(boolean hasResultTypes) {
		this.hasResultTypes = hasResultTypes;
	}

}
