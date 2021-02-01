package com.egoveris.edt.web.admin.pl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.egoveris.edt.web.common.BaseComposer;
import com.egoveris.sharedsecurity.base.model.ConstantesSesion;
import com.egoveris.sharedsecurity.base.model.cargo.CargoDTO;
import com.egoveris.sharedsecurity.base.service.ICargoService;
import com.egoveris.sharedsecurity.conf.service.Utilitarios;

public class AdmCargosComposer extends BaseComposer {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5130266395958899749L;

	/** The logger. */
	private static Logger logger = LoggerFactory.getLogger(AdmCargosComposer.class);

	/** The Constant WIN_ALTA_CARGO. */
	private static final String WIN_ALTA_CARGO = "win_altaCargo";

	@Autowired
	protected AnnotateDataBinder binder;

	private List<CargoDTO> listaResultadoCargos;

	private Integer resultados;

	private CargoDTO cargo;

	private Textbox txbx_cargoBuscado;

	private ICargoService cargoService;

	// private ICargoHistService cargoHistService;

	public CargoDTO getSelectedCargo() {
		return cargo;
	}

	@Autowired
	private Listbox lstbx_cargos;

	@Override
	public void doAfterCompose(final Component comp) throws Exception {
		super.doAfterCompose(comp);

		comp.addEventListener(Events.ON_USER, new BusquedaCargoComposerListener(this));
		comp.addEventListener(Events.ON_NOTIFY, new BusquedaCargoComposerListener(this));

		cargoService = (ICargoService) SpringUtil.getBean("cargoService");
		// cargoHistService = (ICargoHistService)
		// SpringUtil.getBean("cargoHistService");
		// selectedCargo = new CargoDTO();

		binder = new AnnotateDataBinder(comp);

		cargarListbox();
	}

	/**
	 * Carga el listBox de novedades con todas las que tengan el estado ACTIVA
	 */
	private void cargarListbox() {
		listaResultadoCargos = new ArrayList<CargoDTO>();
		listaResultadoCargos.addAll(cargoService.getCargosActivos());
		Collections.sort(listaResultadoCargos, new CustomComparator());
		resultados = listaResultadoCargos.size();
		this.lstbx_cargos.setModel(new ListModelList(listaResultadoCargos));
		this.binder.loadComponent(lstbx_cargos);
	}

	public void onClick$tbbtn_altaCargo() throws InterruptedException {
		try {
			Utilitarios.closePopUps(WIN_ALTA_CARGO);
			final HashMap<String, Object> hm = new HashMap<>();
			hm.put("cargo", null);
			hm.put("alta", true);
			final Window window = (Window) Executions.createComponents(AltaCargoComposer.CARGOS_ALTA_CARGO_ZUL,
					this.self, hm);
			window.setPosition("center");
			window.setVisible(true);
			window.doModal();
		} catch (final SuspendNotAllowedException e) {
			logger.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), Labels.getLabel("eu.adminSade.usuario.generales.error"), Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	/**
	 * Crea el popUp para visualizar el historial de una Novedad
	 */
	public void onVerHistorialCargo() {
		Utilitarios.closePopUps("win_historialCargos");
		final HashMap<String, Object> hm = new HashMap<>();
		hm.put("cargo", cargo);
		final Window window = (Window) Executions.createComponents("/administrator/tabsCargos/historialCargo.zul",
				this.self, hm);
		window.setClosable(true);
		window.setPosition("center");
		window.doModal();
	}

	public void onClick$btn_buscar() throws InterruptedException {
		busquedaCargos();
	}

	private void busquedaCargos() throws InterruptedException {
		if (validarTextboxDeBusqueda()) {
			buscarCargos();
		} else {
			throw new WrongValueException(txbx_cargoBuscado, Labels.getLabel("eu.adminSade.validacion.minimoBusqueda"));
		}
	}

	public void buscarCargos() throws InterruptedException {
		if (!txbx_cargoBuscado.getValue().isEmpty()) {

			final List<CargoDTO> listBusqueda = new ArrayList<>();
			listBusqueda.addAll(cargoService.getCargosActivos());

			final Predicate predicate = new Predicate() {

				@Override
				public boolean evaluate(final Object object) {
					return ((CargoDTO) object).getCargoNombre().toLowerCase()
							.startsWith(txbx_cargoBuscado.getValue().toLowerCase());
				}
			};
			final List<CargoDTO> filtered = (List<CargoDTO>) CollectionUtils.select(listBusqueda, predicate);

			listaResultadoCargos = filtered;

			resultados = listaResultadoCargos.size();

			this.binder.loadComponent(lstbx_cargos);
		}
	}

	private boolean validarTextboxDeBusqueda() {
		if (Utilitarios.isAdministradorCentral()) {
			txbx_cargoBuscado.setValue(txbx_cargoBuscado.getValue().trim());
			if (this.txbx_cargoBuscado.getValue().length() < 3) {
				return false;
			}
		}
		return true;
	}

	public void onVisualizarCargo() throws InterruptedException {
		try {
			Utilitarios.closePopUps(WIN_ALTA_CARGO);
			final Map<String, Object> parametros = new HashMap<>();
			parametros.put(ConstantesSesion.KEY_CARGO, cargo);
			parametros.put(ConstantesSesion.KEY_VISUALIZAR, true);
			final Window win = (Window) Executions.createComponents(AltaCargoComposer.CARGOS_ALTA_CARGO_ZUL, this.self,
					parametros);
			win.setMode("modal");
			win.setClosable(true);
			win.setTitle("Visualizar CargoDTO");
			win.setWidth("500px");
			win.setPosition("center");
			win.setVisible(true);
			win.setBorder("normal");
			win.doModal();
		} catch (final SuspendNotAllowedException e) {
			logger.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), Labels.getLabel("eu.adminSade.usuario.generales.error"), Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	public void onModificarCargo() throws InterruptedException {
		try {
			Utilitarios.closePopUps(WIN_ALTA_CARGO);
			final HashMap<String, Object> hm = new HashMap<>();
			hm.put("cargo", cargo);
			hm.put("alta", false);
			final Window window = (Window) Executions.createComponents(AltaCargoComposer.CARGOS_ALTA_CARGO_ZUL,
					this.self, hm);
			window.setPosition("center");
			window.setVisible(true);
			window.doModal();
		} catch (final SuspendNotAllowedException e) {
			logger.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), Labels.getLabel("eu.adminSade.usuario.generales.error"), Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	/**
	 * Elimina la novedad seleccionada (cambia el estado a ELIMIADO)
	 * 
	 * @throws InterruptedException
	 */
	public void onEliminarCargo() throws InterruptedException {
		Messagebox.show(Labels.getLabel("eu.adminSade.cargo.mensajes.confirm.eliminacion"),
				Labels.getLabel("eu.adminSade.usuario.generales.confirmacion"), Messagebox.YES | Messagebox.NO,
				Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {

					@Override
					public void onEvent(final Event evt) throws InterruptedException {
						switch (((Integer) evt.getData()).intValue()) {
						case Messagebox.YES:
							eliminarCargo();
							break;
						case Messagebox.NO:
							break;
						}
					}
				});
	}

	private void eliminarCargo() throws InterruptedException {
		if (cargoService.eliminar(cargo)) {
			refreshCargos();
			Messagebox.show(Labels.getLabel("eu.adminSade.bajaCargo.mensajes.bajaExitosa"),
					Labels.getLabel("eu.general.information"), Messagebox.OK, Messagebox.INFORMATION);
		} else {

			Messagebox.show(Labels.getLabel("eu.admCargosComposer.msgBox"),
					Labels.getLabel("eu.adminSade.usuario.generales.error"), Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void onOK$txbx_cargoBuscado() throws InterruptedException {
		busquedaCargos();
	}

	public List<CargoDTO> getListaResultadoCargos() {
		return listaResultadoCargos;
	}

	public void setListaResultadoCargos(final List<CargoDTO> listaResultadoCargos) {
		this.listaResultadoCargos = listaResultadoCargos;
	}

	public void setResultados(final Integer resultados) {
		this.resultados = resultados;
	}

	public Integer getResultados() {
		return resultados;
	}

	public CargoDTO getCargo() {
		return cargo;
	}

	public void setCargo(final CargoDTO cargo) {
		this.cargo = cargo;
	}

	public void refreshCargos() throws InterruptedException {
		listaResultadoCargos.clear();
		listaResultadoCargos.addAll(cargoService.getCargosActivos());
		Collections.sort(listaResultadoCargos, new CustomComparator());

		this.resultados = listaResultadoCargos.size();
		this.lstbx_cargos.setModel(new ListModelList(listaResultadoCargos));
		this.binder.loadComponent(lstbx_cargos);

	}

	public Listbox getLstbx_cargos() {
		return lstbx_cargos;
	}

	public void setLstbx_cargos(final Listbox lstbx_cargos) {
		this.lstbx_cargos = lstbx_cargos;
	}

	public AnnotateDataBinder getBinder() {
		return binder;
	}

	public void setBinder(final AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public ICargoService getCargoService() {
		return cargoService;
	}

	public void setCargoService(final ICargoService cargoService) {
		this.cargoService = cargoService;
	}
}

final class BusquedaCargoComposerListener implements EventListener {
	private final AdmCargosComposer composer;

	public BusquedaCargoComposerListener(final AdmCargosComposer comp) {
		this.composer = comp;
	}

	@Override
	public void onEvent(final Event event) throws Exception {
		if (event.getName().equals(Events.ON_USER)) {
			this.composer.refreshCargos();
		}
		if (event.getName().equals(Events.ON_NOTIFY)) {
			composer.binder.loadAll();
		}
	}
}

final class CustomComparator implements Comparator<CargoDTO> {
	@Override
	public int compare(final CargoDTO o1, final CargoDTO o2) {
		return o1.getCargoNombre().compareTo(o2.getCargoNombre());
	}
}