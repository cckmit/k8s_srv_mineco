package com.egoveris.te.base.composer;

import com.egoveris.sharedorganismo.base.model.ReparticionBean;
import com.egoveris.te.base.model.ParametrosSistemaExternoDTO;
import com.egoveris.te.base.model.ReparticionDTO;
import com.egoveris.te.base.model.TrataDTO;
import com.egoveris.te.base.model.TrataIntegracionReparticionDTO;
import com.egoveris.te.base.model.TrataTipoDocumentoDTO;
import com.egoveris.te.base.service.ObtenerReparticionServices;
import com.egoveris.te.base.service.TipoDocumentoService;
import com.egoveris.te.base.service.TrataService;
import com.egoveris.te.base.util.ConfiguracionInicialModuloEEFactory;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.ConstantesServicios;

import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;


@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class TrataIntegracionComposer extends GenericForwardComposer {

	private Textbox codigoTextbox;
	private Textbox urlTextbox;
	private Bandbox reparticionImportarDocumentoSADE;
	private AnnotateDataBinder binder;
	private Listbox reparticionesTrataListBox;

	private TrataIntegracionReparticionDTO reparticionSeleccionada;
 @WireVariable(ConstantesServicios.OBTENER_REPARTICION_SERVICE)
	private ObtenerReparticionServices obtenerReparticionService;
	private TrataDTO trata;
	private ParametrosSistemaExternoDTO model;
	
	@WireVariable(ConstantesServicios.TRATA_SERVICE) 
	private TrataService trataService;
	private String usuario;
	private boolean trataNueva;
	@Autowired
	private CrearTrataComposer crearTrata;
	TipoDocumentoService tipoDocumentoService;
	private TrataTipoDocumentoDTO tdt;
	@Autowired
	private Window trataIntegracionWindow;

	public void doAfterCompose(Component c) throws Exception {
		super.doAfterCompose(c);
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
		binder = new AnnotateDataBinder(c);
		c.addEventListener(Events.ON_USER, new TrataIntegracionOnNotifyWindowListener(this)); 
		 
		trata = (TrataDTO) Executions.getCurrent().getArg().get("trata");
		model = ConfiguracionInicialModuloEEFactory.obtenerParametrosPorTrata(trata.getId());
		trataNueva = (Boolean) Executions.getCurrent().getArg().get("trataNueva");
		tdt = (TrataTipoDocumentoDTO) Executions.getCurrent().getArg().get("trataTipoDocumento");
		if (model != null) {
			agregarValorTodas("--TODAS--");
			inicializarConDatos();
		} else {
			inicializarEmpty();
			agregarValorTodas("--TODAS--");
		}
		reparticionesTrataListBox.setItemRenderer(new RepaItemRenderer(this));
		binder.loadAll();
	}

	private void agregarValorTodas(String cod) {
		if (!existeCodigoEnListasExistentes(cod)) {
			TrataIntegracionReparticionDTO  t = new TrataIntegracionReparticionDTO();
			t.setCodigoReparticion(cod);
			t.setIdTrata(trata.getId());
			t.setHabilitada(false);
			model.getReparticionesIntegracion().add(0, t);
		}
	}

	private void inicializarEmpty() {
		model = new ParametrosSistemaExternoDTO();
		model.setReparticionesIntegracion(new ArrayList<TrataIntegracionReparticionDTO>());
	}

	private void inicializarConDatos() {
		if (model.getReparticiones() != null) {
			for (ReparticionDTO r : model.getReparticiones()) {
				if (!existeEnListaIntegracion(r.getCodigoReparticion())) {
					TrataIntegracionReparticionDTO t = new TrataIntegracionReparticionDTO();
					t.setCodigoReparticion(r.getCodigoReparticion());
					t.setIdTrata(model.getId());
					model.agregarTrataIntegracion(t);
				}
			}
		}
		urlTextbox.setValue(model.getUrl());
		codigoTextbox.setValue(model.getCodigo());

	}

	private void validacionesParaGuardar() {
		if (StringUtils.isBlank(urlTextbox.getValue())) {
			throw new WrongValueException(urlTextbox, "La URL no debe ser vacía.");
		}

		if (urlTextbox.getValue().contains(" ")) {
			throw new WrongValueException(urlTextbox, "La URL no debe contener espacios vacíos.");
		}

		if (StringUtils.isBlank(codigoTextbox.getValue())) {
			throw new WrongValueException(codigoTextbox, "El código no debe ser vacío.");
		}

		if (codigoTextbox.getValue().contains(" ")) {
			throw new WrongValueException(codigoTextbox, "El código no debe contener espacios vacíos.");
		}

		if (model.getReparticionesIntegracion() == null || model.getReparticionesIntegracion().size() == 0) {
			throw new WrongValueException(reparticionesTrataListBox, "Debe agregar al menos una repartición.");
		}
	}

	public void onGuardarTrataCambios() {
		validacionesParaGuardar();

		if (trataNueva) {
			preguntarTrata();

		} else {

			model.setCodigo(codigoTextbox.getValue());
			model.setIdTrata(trata.getId());
			model.setUrl(urlTextbox.getValue());
			model.setEsactivo(true);
			ConfiguracionInicialModuloEEFactory.guardarParametros(model);
			alert("Se ha guardado con éxito.");
			this.self.detach();
		}
	}

	public void preguntarTrata() {

		String mensaje = "Al configurar el sistema externo se guardará la trata definitivamente. ¿Desea continuar con la operación?";
		String titulo = "Guardar Trata";

		Messagebox.show(mensaje, titulo, Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
				new org.zkoss.zk.ui.event.EventListener() {
					public void onEvent(Event evt) {
						switch (((Integer) evt.getData()).intValue()) {
						case Messagebox.YES:
							Clients.showBusy("Procesando...");
							Events.echoEvent(Events.ON_USER, (Component) trataIntegracionWindow, "altaTrata");
							break;
						case Messagebox.NO:
							break;
						}
					}
				});
	}

	public void altaTrata() {

		String usuario = (String) Executions.getCurrent().getDesktop().getSession()
				.getAttribute(ConstantesWeb.SESSION_USERNAME);
		this.trataService.darAltaTrata(this.trata, usuario);
		this.tipoDocumentoService.cargarAuditoria(tdt, TrataTipoDocumentoDTO.ESTADO_ACTIVO, usuario);

		model.setCodigo(codigoTextbox.getValue());
		model.setIdTrata(trata.getId());
		model.setUrl(urlTextbox.getValue());
		model.setEsactivo(true);

		for (TrataIntegracionReparticionDTO tic : model.getReparticionesIntegracion()) {
			if (tic != null)
				tic.setIdTrata(trata.getId());
		}

		ConfiguracionInicialModuloEEFactory.guardarParametros(model);
		Messagebox.show(Labels.getLabel("ee.nuevaTrata.nuevoTipoCreado", new String[] { this.trata.getCodigoTrata() }),
				Labels.getLabel("ee.general.information"), Messagebox.OK, Messagebox.INFORMATION);
		Clients.clearBusy();
		this.self.getParent().detach();
		this.self.detach();
	}

	public void onClick$cancelar() {
		this.self.detach();
	}

	public boolean existeEnListaIntegracion(String codigoRep) {
		for (TrataIntegracionReparticionDTO t : model.getReparticionesIntegracion()) {
			if (t.getCodigoReparticion().equals(codigoRep)) {
				return true;
			}
		}
		return false;
	}

	public boolean existeCodigoEnListasExistentes(String codigoRep) {
		for (ReparticionDTO r : model.getReparticiones()) {
			if (r.getCodigoReparticion().equals(codigoRep)) {
				return true;
			}
		}

		return existeEnListaIntegracion(codigoRep);
	}

	public void onAgregarReparticionSade() {
		String descRepar = (String) this.reparticionImportarDocumentoSADE.getValue();
		if (descRepar == null || descRepar.isEmpty()) {
			throw new WrongValueException(this.reparticionImportarDocumentoSADE,
					"Debe Seleccionar una repartición para agregar");
		}
		for (TrataIntegracionReparticionDTO reparticionAuxiliar : model.getReparticionesIntegracion()) {
			if (reparticionAuxiliar.getCodigoReparticion().compareTo(descRepar) == 0) {
				throw new WrongValueException(this.reparticionImportarDocumentoSADE,
						Labels.getLabel("ee.nuevoDocumento.reparticionYaAgregada.value"));
			}
		}

		ReparticionBean rp = this.obtenerReparticionService.getReparticionBycodigoReparticion(descRepar);

		if (rp == null || rp.getVigenciaHasta() == null || rp.getVigenciaHasta().before(new Date())
				|| rp.getVigenciaDesde().after(new Date())) {
			throw new WrongValueException(this.reparticionImportarDocumentoSADE,
					"La repartición no existe o no se encuentra con vigencia activa.");
		}
		TrataIntegracionReparticionDTO repa = new TrataIntegracionReparticionDTO();
		repa.setCodigoReparticion(rp.getCodigo());
		repa.setHabilitada(true);
		repa.setIdTrata(trata.getId());
		model.agregarTrataIntegracion(repa);
		this.reparticionImportarDocumentoSADE.setText("");
		this.binder.loadAll();
	}

	private void checkTodas(boolean value) {
		for (TrataIntegracionReparticionDTO t : model.getReparticionesIntegracion()) {
			t.setHabilitada(value);
		}
		this.binder.loadAll();
	}

	private void unCheckRepaTodas() {
		for (TrataIntegracionReparticionDTO t : model.getReparticionesIntegracion()) {
			if (t.getCodigoReparticion().equals("--TODAS--")) {
				t.setHabilitada(false);
			}
		}
	}

	private void checkTarget(TrataIntegracionReparticionDTO target, boolean value) {
		if (value) {
			target.setHabilitada(value);
		} else {
			target.setHabilitada(value);
			unCheckRepaTodas();
		}
	}

	public void check(TrataIntegracionReparticionDTO target, Checkbox c) {
		if (target.getCodigoReparticion().equals("--TODAS--")) {
			checkTodas(c.isChecked());
		} else {
			checkTarget(target, c.isChecked());
		}
		this.binder.loadAll();
	}

	public ObtenerReparticionServices getObtenerReparticionService() {
		return obtenerReparticionService;
	}

	public void setObtenerReparticionService(ObtenerReparticionServices obtenerReparticionService) {
		this.obtenerReparticionService = obtenerReparticionService;
	}

	public TrataDTO getTrata() {
		return trata;
	}

	public void setTrata(TrataDTO trata) {
		this.trata = trata;
	}

	public ParametrosSistemaExternoDTO getModel() {
		return model;
	}

	public void setModel(ParametrosSistemaExternoDTO model) {
		this.model = model;
	}

	public TrataIntegracionReparticionDTO getReparticionSeleccionada() {
		return reparticionSeleccionada;
	}

	public void setReparticionSeleccionada(TrataIntegracionReparticionDTO reparticionSeleccionada) {
		this.reparticionSeleccionada = reparticionSeleccionada;
	}

}

class RepaItemRenderer implements ListitemRenderer {

	TrataIntegracionComposer comp;

	public RepaItemRenderer(TrataIntegracionComposer c) {
		comp = c;
	}

	@Override
	public void render(Listitem item, Object data,int arg1) throws Exception {
		TrataIntegracionReparticionDTO it = (TrataIntegracionReparticionDTO) data;
		Listcell c1 = new Listcell();
		Listcell c2 = new Listcell();
		Label l = new Label(it.getCodigoReparticion());
		Checkbox c = new Checkbox();
		Div d = new Div();
		d.setAlign("center");
		c.setChecked(it.isHabilitada());

		l.setParent(c1);
		c.setParent(d);
		d.setParent(c2);
		c1.setParent(item);
		c2.setParent(item);

		c.addEventListener(Events.ON_CHECK, new CheckListener(comp, it, c));

	}

}

class CheckListener implements EventListener {

	TrataIntegracionComposer composer;
	TrataIntegracionReparticionDTO target;
	Checkbox c;

	public CheckListener(TrataIntegracionComposer composer, TrataIntegracionReparticionDTO target, Checkbox c) {
		this.composer = composer;
		this.target = target;
		this.c = c;
	}

	@Override
	public void onEvent(Event evt) throws Exception {
		composer.check(target, c);

	}

}

final class TrataIntegracionOnNotifyWindowListener implements EventListener {
	private TrataIntegracionComposer composer;

	public TrataIntegracionOnNotifyWindowListener(TrataIntegracionComposer trataIntegracionComposer) {
		this.composer = trataIntegracionComposer;
	}

	public void onEvent(Event event) throws Exception {
		if (event.getName().equals(Events.ON_USER)) {
			if (event.getData().equals("altaTrata")) {
				this.composer.altaTrata();
			}
		}

	}
}