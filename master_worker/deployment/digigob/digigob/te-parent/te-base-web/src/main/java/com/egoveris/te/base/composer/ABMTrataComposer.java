package com.egoveris.te.base.composer;

import com.egoveris.te.base.model.TipoDocumentoDTO;
import com.egoveris.te.base.model.TrataDTO;
import com.egoveris.te.base.rendered.TrataABMItemRenderer;
import com.egoveris.te.base.service.TipoDocumentoService;
import com.egoveris.te.base.service.TrataService;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.ConstantesServicios;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Image;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;


@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ABMTrataComposer extends AbstractComposer {

	private static Logger logger = LoggerFactory.getLogger(ABMTrataComposer.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 8929107505501831079L;
	@Autowired
	private Window abmTrataWindow;
	@Autowired
	private Window hiddenView;
	private List<TrataDTO> tratas;
	private List<String> numeroDescripcionTrataList;
	@Autowired
	private Listbox trataLb;
	private TrataDTO selectedTrata;
	private TrataDTO trata;
	private Image checked;
	private Image unChecked;
	@Autowired
	private AnnotateDataBinder binder;
	@Autowired
	private Combobox tipoDocumentoDeEECaratula;
	private Combobox tipoDocumentoDeEECaratulaReservada;
	private List<TipoDocumentoDTO> listaTiposDocumentosDeEECaratula;
	private List<TipoDocumentoDTO> listaTiposDocumentosDeEECaratulaReservada;
	private TipoDocumentoDTO selectedTipoDocumentoEECaratula;
	private TipoDocumentoDTO selectedTipoDocumentoEECaratulaReservada;
	@Autowired
	private Button guardarButton;
	private String userName;
	private TrataABMItemRenderer trataABMItemRenderer;
	@WireVariable(ConstantesServicios.TRATA_SERVICE)
	private TrataService trataService;
	
	@WireVariable(ConstantesServicios.EXP_ELECTRONICO_SERVICE)
	private ExpedienteElectronicoService expedienteElectronicoService;
	
	@WireVariable(ConstantesServicios.TIPO_DOCUMENTO_SERVICE)
	private TipoDocumentoService tipoDocumentoService;
	
	private Combobox busquedaTrataComboBox;

	private static String CARATULA = "CARATULA";
	private static String CARATULA_RESERVADA = "CARATULA_RESERVADA";

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		this.binder = new AnnotateDataBinder(comp);
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
		
		this.userName = (String) Executions.getCurrent().getDesktop().getSession()
				.getAttribute(ConstantesWeb.SESSION_USERNAME);
		refreshTrata();
		this.self.addEventListener(Events.ON_NOTIFY, new ABMTraraOnNotifyWindowListener(this));
		cargarDocumentoCaratula();
		cargarDocumentoCaratulaReservada();
		refreshTrata();
		this.trataABMItemRenderer = new TrataABMItemRenderer(this);
		this.trataLb.setItemRenderer(trataABMItemRenderer);
		this.binder.loadComponent(this.tipoDocumentoDeEECaratula);
		this.binder.loadComponent(this.tipoDocumentoDeEECaratulaReservada);
		this.binder.loadAll();
	}

	private void cargarDocumentoCaratula() {
		TipoDocumentoDTO tipoDocumentoCaratulaDeEE = this.tipoDocumentoService.buscarTipoDocumentoByUso(CARATULA);
		if (tipoDocumentoCaratulaDeEE != null) {
			tipoDocumentoCaratulaDeEE.setDescripcionTipoDocumentoSade(tipoDocumentoCaratulaDeEE.getNombre());
			this.selectedTipoDocumentoEECaratula = tipoDocumentoCaratulaDeEE;
			this.tipoDocumentoDeEECaratula.setValue(tipoDocumentoCaratulaDeEE.toString());
		}
	}

	private void cargarDocumentoCaratulaReservada() {
		TipoDocumentoDTO tipoDocumentoCaratulaReservadaDeEE = this.tipoDocumentoService
				.buscarTipoDocumentoByUso(CARATULA_RESERVADA);
		if (tipoDocumentoCaratulaReservadaDeEE != null) {
			tipoDocumentoCaratulaReservadaDeEE
					.setDescripcionTipoDocumentoSade(tipoDocumentoCaratulaReservadaDeEE.getNombre());
			this.selectedTipoDocumentoEECaratulaReservada = tipoDocumentoCaratulaReservadaDeEE;
			this.tipoDocumentoDeEECaratulaReservada.setValue(tipoDocumentoCaratulaReservadaDeEE.toString());
		}
	}

	public AnnotateDataBinder getBinder() {
		return binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public Window getHiddenView() {
		return hiddenView;
	}

	public void setHiddenView(Window hiddenView) {
		this.hiddenView = hiddenView;
	}

	public void onDetalle() {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("trata", this.selectedTrata);
		if (this.hiddenView != null) {
			this.hiddenView.detach();
			this.hiddenView.invalidate();
			this.hiddenView = (Window) Executions.createComponents("/inbox/detalleTrata.zul", this.self, hm);
			this.hiddenView.setPosition("center");
			this.hiddenView.doModal();
		} else {
			Messagebox.show(Labels.getLabel("ee.general.imposibleIniciarVista"), Labels.getLabel("ee.general.error"),
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void onTiposDocs() {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("trata", this.selectedTrata);
		if (this.hiddenView != null) {
			this.hiddenView.detach();
			this.hiddenView.invalidate();
			this.hiddenView = (Window) Executions.createComponents("/inbox/tiposDocs.zul", this.self, hm);
			this.hiddenView.setPosition("center");
				this.hiddenView.doModal();

		} else {
				Messagebox.show(Labels.getLabel("ee.general.imposibleIniciarVista"),
						Labels.getLabel("ee.general.error"), Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void onEliminar() throws InterruptedException {
		Messagebox.show(Labels.getLabel("ee.abmTrata.question.eliminarDocumento"),
				Labels.getLabel("ee.abmTrata.question"), Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
				new org.zkoss.zk.ui.event.EventListener() {
					public void onEvent(Event evt) throws InterruptedException {
						switch (((Integer) evt.getData()).intValue()) {
						case Messagebox.YES:
							eliminarTrata();
							break;
						case Messagebox.NO:
							break;
						}
					}
				});
	}

	private void eliminarTrata() throws InterruptedException {
		try {
			boolean existeTrata = expedienteElectronicoService.buscarIdTrata(selectedTrata);
			if (existeTrata) {
				this.trataService.eliminarTrata(selectedTrata, userName);
				((ListModelList) trataLb.getModel()).remove(selectedTrata);
				binder.loadComponent(trataLb);
			} else {
				Messagebox.show(Labels.getLabel("ee.abmTrata.mensajeTrata"), Labels.getLabel("ee.abmTrata.error"),
						Messagebox.OK, Messagebox.ERROR);
			}
		} catch (Exception edte) {
		  logger.error("error en metodo eliminarTrata()", edte);
			Messagebox.show(
					Labels.getLabel("gedo.abmTipoDocumento.question.imposibleEliminar",
							new String[] { this.selectedTrata.getCodigoTrata() }),
					Labels.getLabel("gedo.general.error"), Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void onChecked(final TrataDTO trata) throws InterruptedException {
		Messagebox.show(Labels.getLabel("ee.abmTrata.activarTrata", new String[] { trata.getCodigoTrata() }),
				Labels.getLabel("ee.general.question"), Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
				new org.zkoss.zk.ui.event.EventListener() {
					public void onEvent(Event evt) throws InterruptedException {
						switch (((Integer) evt.getData()).intValue()) {
						case Messagebox.YES:
							trata.setEstado(TrataDTO.ACTIVO);
							trataService.modificarTrata(trata, userName);
							refreshTrata();
							break;
						case Messagebox.NO:
							refreshTrata();
							break;
						}
					}
				});
		return;
	}

	public void onUnChecked(final TrataDTO trata) throws InterruptedException {
		Messagebox.show(Labels.getLabel("ee.abmTrata.desactivarTrata", new String[] { trata.getCodigoTrata() }),
				Labels.getLabel("ee.general.question"), Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
				new org.zkoss.zk.ui.event.EventListener() {
					public void onEvent(Event evt) throws InterruptedException {
						switch (((Integer) evt.getData()).intValue()) {
						case Messagebox.YES:
							trata.setEstado(TrataDTO.ESTADO_INACTIVO);
							trataService.modificarTrata(trata, userName);
							refreshTrata();
							break;
						case Messagebox.NO:
							refreshTrata();
							break;
						}
					}
				});
		return;
	}

	public void onNuevaTrataCreada() {
		this.binder.loadComponent(this.trataLb);
	}

	public void onCreateNuevaTrata() {
		if (this.hiddenView != null) {
			this.hiddenView.invalidate();
			this.hiddenView = (Window) Executions.createComponents("/inbox/nuevaTrata.zul", this.self,
					new HashMap<String, Object>());
			this.hiddenView.setParent(this.self);
			this.hiddenView.setPosition("center");
			this.hiddenView.setVisible(true);
			this.hiddenView.setClosable(true);
				this.hiddenView.doModal();
		} else {
				Messagebox.show(Labels.getLabel("gedo.general.imposibleIniciarVista"),
						Labels.getLabel("gedo.general.error"), Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void refreshTrata() {
		this.tratas = this.trataService.obtenerTodasLasTratasConDescripcion();
		this.binder.loadComponent(this.trataLb);
	}

	public synchronized void onBuscarPorCodigo() {

		String selectedItem;

		if (StringUtils.contains(busquedaTrataComboBox.getValue(), '-')) {
			selectedItem = StringUtils.substringBefore(busquedaTrataComboBox.getValue(), "-");
		} else {
			selectedItem = busquedaTrataComboBox.getValue();
		}

		List<TrataDTO> tratasBuscadasList = new ArrayList<>();

		for (TrataDTO DTO : this.tratas) {

			if (DTO.getCodigoTrata() != null) {

				Pattern pat = Pattern.compile(".*" + selectedItem.trim() + ".*");
				Matcher mat = pat.matcher(DTO.getCodigoTrata());

				if (mat.matches()) {

					tratasBuscadasList.add(DTO);
				}
			}
			
		}

		this.tratas.clear();
		this.tratas.addAll(tratasBuscadasList);
		this.binder.loadComponent(this.trataLb);
		this.busquedaTrataComboBox.setValue("");

	}

	public synchronized void onBuscarPorDescripcion() {

		String selectedItem;

		if (StringUtils.containsIgnoreCase(busquedaTrataComboBox.getValue(), "-")) {
			selectedItem = StringUtils.substringAfter(busquedaTrataComboBox.getValue().toLowerCase(), "-");
		} else {
			selectedItem = busquedaTrataComboBox.getValue().toLowerCase();
		}

		List<TrataDTO> tratasBuscadasList = new ArrayList<>();

		for (TrataDTO trataDTO : this.tratas) {

			if (trataDTO.getDescripcion() != null) {

				Pattern pat = Pattern.compile(".*" + selectedItem.trim() + ".*");
				Matcher mat = pat.matcher(trataDTO.getDescripcion().toLowerCase());

				if (mat.matches()) {

					tratasBuscadasList.add(trataDTO);
				}
			}
		}

		this.tratas.clear();
		this.tratas.addAll(tratasBuscadasList);
		this.binder.loadComponent(this.trataLb);
		this.busquedaTrataComboBox.setValue("");

	}

	public synchronized void onLimpiarBusqueda() {
		this.busquedaTrataComboBox.setText("");
		this.refreshTrata();
	}

	public void onChanging$busquedaTrataComboBox(InputEvent e) {
		this.tratas = this.getTrataService().obtenerTodasLasTratasConDescripcion();
		this.numeroDescripcionTrataList = new ArrayList<String>();
		this.cargarTratas(e);
	}

	public void cargarTratas(InputEvent e) {
		String matchingText = e.getValue();
		this.numeroDescripcionTrataList.clear();

		if (!matchingText.equals("") && (matchingText.length() >= 3)) {
			if (this.tratas != null) {
				matchingText = matchingText.toUpperCase();

				for (TrataDTO trataDTO : tratas) {

					String codigoDescripcion = (trataDTO.getCodigoTrata() != null ? trataDTO.getCodigoTrata() : "") + " - "
							+ (trataDTO.getDescripcion() != null ? trataDTO.getDescripcion() : "");

					Pattern pat = Pattern.compile(".*" + matchingText.trim() + ".*");
					Matcher mat = pat.matcher(codigoDescripcion);

					if (mat.matches()) {
						this.numeroDescripcionTrataList.add(codigoDescripcion);
					}
				}
			}

		} else if (matchingText.trim().equals("")) {

			this.numeroDescripcionTrataList = new ArrayList<String>();
		}

		this.busquedaTrataComboBox.setModel(new ListModelList(numeroDescripcionTrataList));

		this.binder.loadComponent(busquedaTrataComboBox);
	}

	public Window getAbmTrataWindow() {
		return abmTrataWindow;
	}

	public List<TrataDTO> getTratas() {
		return tratas;
	}

	public Listbox getTrataLb() {
		return trataLb;
	}

	public TrataDTO getSelectedTrata() {
		return selectedTrata;
	}

	public TrataService getTrataService() {
		return trataService;
	}

	public TrataDTO getTrata() {
		return trata;
	}

	public void setAbmTrataWindow(Window abmTrataWindow) {
		this.abmTrataWindow = abmTrataWindow;
	}

	public void setTratas(List<TrataDTO> tratas) {
		this.tratas = tratas;
	}

	public void setTrataLb(Listbox trataLb) {
		this.trataLb = trataLb;
	}

	public void setSelectedTrata(TrataDTO selectedTrata) {
		this.selectedTrata = selectedTrata;
	}

	public void setTrataService(TrataService trataService) {
		this.trataService = trataService;
	}

	public void setTrata(TrataDTO trata) {
		this.trata = trata;
	}

	/**
	 * Agrego funcionalidad para la busqueda y seteo de los tipo de documentos
	 * que usa EE
	 */
	public Combobox getTipoDocumento() {
		return tipoDocumentoDeEECaratula;
	}

	public void setTipoDocumentos(Combobox tipoDocumento) {
		this.tipoDocumentoDeEECaratula = tipoDocumento;
	}

	public List<TipoDocumentoDTO> getListaTiposDocumentosDeEECaratula() {
		List<TipoDocumentoDTO> lista = tipoDocumentoService.obtenerTiposDocumentoGedo();

		this.listaTiposDocumentosDeEECaratula = new ArrayList<>();
		for (int i = 0; i < lista.size(); i++) {
			if (!lista.get(i).getEsConfidencial()) {
				this.listaTiposDocumentosDeEECaratula.add(lista.get(i));
			}
		}

		Collections.sort(this.listaTiposDocumentosDeEECaratula, new Comparator<TipoDocumentoDTO>() {
			public int compare(TipoDocumentoDTO td1, TipoDocumentoDTO td2) {
				return td1.getAcronimo().compareTo(td2.getAcronimo());
			}
		});
		return listaTiposDocumentosDeEECaratula;
	}

	public List<TipoDocumentoDTO> getListaTiposDocumentosDeEECaratulaReservada() {
		List<TipoDocumentoDTO> lista = tipoDocumentoService.obtenerTiposDocumentoGedo();
		this.listaTiposDocumentosDeEECaratulaReservada = new ArrayList<>();
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getEsConfidencial()) {
				this.listaTiposDocumentosDeEECaratulaReservada.add(lista.get(i));
			}
		}

		Collections.sort(this.listaTiposDocumentosDeEECaratulaReservada, new Comparator<TipoDocumentoDTO>() {
			public int compare(TipoDocumentoDTO td1, TipoDocumentoDTO td2) {
				return td1.getAcronimo().compareTo(td2.getAcronimo());
			}
		});

		return listaTiposDocumentosDeEECaratulaReservada;
	}

	public void setListaTiposDocumentosDeEECaratula(List<TipoDocumentoDTO> listaTiposDocumentosDeEECaratula) {
		this.listaTiposDocumentosDeEECaratula = listaTiposDocumentosDeEECaratula;
	}

	public TipoDocumentoDTO getSelectedTipoDocumentoEECaratula() {
		return selectedTipoDocumentoEECaratula;
	}

	public void setSelectedTipoDocumentoEECaratula(TipoDocumentoDTO selectedTipoDocumentoEECaratula) {
		this.selectedTipoDocumentoEECaratula = selectedTipoDocumentoEECaratula;
	}

	public TipoDocumentoDTO getSelectedTipoDocumentoEECaratulaReservada() {
		return selectedTipoDocumentoEECaratulaReservada;
	}

	public void setSelectedTipoDocumentoEECaratulaReservada(TipoDocumentoDTO selectedTipoDocumentoEECaratulaReservada) {
		this.selectedTipoDocumentoEECaratulaReservada = selectedTipoDocumentoEECaratulaReservada;
	}

	/**
	 * Fin
	 * 
	 * public void onDatosVariables(){ HashMap<String,Object> hm = new HashMap
	 * <String,Object>(); hm.put("trata", this.selectedTrata); if
	 * (this.hiddenView != null) { this.hiddenView.detach();
	 * this.hiddenView.invalidate(); this.hiddenView = (Window)
	 * Executions.createComponents( "/expediente/datosPropiosDeTrata.zul",
	 * this.self, hm); this.hiddenView.setParent(this.self);
	 * this.hiddenView.setPosition("center"); this.hiddenView.setVisible(true);
	 * this.hiddenView.setMaximizable(true); this.hiddenView.setClosable(true);
	 * try { this.hiddenView.doModal(); } catch (SuspendNotAllowedException e) {
	 * logger.error(e.getMessage()); } catch (InterruptedException e) {
	 * logger.error(e.getMessage()); } } else { try { Messagebox.show(Labels
	 * .getLabel("gedo.general.imposibleIniciarVista"), Labels
	 * .getLabel("gedo.general.error"), Messagebox.OK, Messagebox.ERROR); }
	 * catch (InterruptedException ie) { // En caso de error retornamos una
	 * lista vacia para que no se // rompa la vista } } }
	 */
	public void onRepTrata() {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("trata", this.selectedTrata);
		if (this.hiddenView != null) {
			this.hiddenView.detach();
			this.hiddenView.invalidate();
			this.hiddenView = (Window) Executions.createComponents("/inbox/reparticionesTrata.zul", this.self, hm);
			this.hiddenView.setParent(this.self);
			this.hiddenView.setPosition("center");
			this.hiddenView.setVisible(true);
			this.hiddenView.setMaximizable(true);
			this.hiddenView.setClosable(true);
				this.hiddenView.doModal();
		} else {
				Messagebox.show(Labels.getLabel("gedo.general.imposibleIniciarVista"),
						Labels.getLabel("gedo.general.error"), Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void onSelectTipoDocumentoEECaratula() {
		this.selectedTipoDocumentoEECaratula.setUsoEnEE("CARATULA");
		this.guardarButton.setDisabled(false);
	}

	public void onSelectTipoDocumentoEECaratulaReservada() {
		this.selectedTipoDocumentoEECaratulaReservada.setUsoEnEE("CARATULA_RESERVADA");
		this.guardarButton.setDisabled(false);
	}

	public void onGuardarDocumentoCaratula() {
		TipoDocumentoDTO tipoDocumentoCaratulaEE = this.tipoDocumentoService.buscarTipoDocumentoByUso(CARATULA);
		if (tipoDocumentoCaratulaEE == null) {
			tipoDocumentoCaratulaEE = new TipoDocumentoDTO();
			tipoDocumentoCaratulaEE.setAcronimo(this.selectedTipoDocumentoEECaratula.getAcronimo());
			tipoDocumentoCaratulaEE
					.setCodigoTipoDocumentoSade(this.selectedTipoDocumentoEECaratula.getCodigoTipoDocumentoSade());
			tipoDocumentoCaratulaEE.setEsEspecial(this.selectedTipoDocumentoEECaratula.getEsEspecial());
			tipoDocumentoCaratulaEE.setNombre(this.selectedTipoDocumentoEECaratula.getNombre());
			tipoDocumentoCaratulaEE.setUsoEnEE(CARATULA);
		} else {
			tipoDocumentoCaratulaEE.setAcronimo(this.selectedTipoDocumentoEECaratula.getAcronimo());
			tipoDocumentoCaratulaEE
					.setCodigoTipoDocumentoSade(this.selectedTipoDocumentoEECaratula.getCodigoTipoDocumentoSade());
			tipoDocumentoCaratulaEE.setEsEspecial(this.selectedTipoDocumentoEECaratula.getEsEspecial());
			tipoDocumentoCaratulaEE.setNombre(this.selectedTipoDocumentoEECaratula.getNombre());
		}

		TipoDocumentoDTO tipoDocumentoCaratulaReservadaEE = this.tipoDocumentoService
				.buscarTipoDocumentoByUso(CARATULA_RESERVADA);
		if (tipoDocumentoCaratulaReservadaEE == null) {
			tipoDocumentoCaratulaReservadaEE = new TipoDocumentoDTO();
			tipoDocumentoCaratulaReservadaEE.setAcronimo(this.selectedTipoDocumentoEECaratulaReservada.getAcronimo());
			tipoDocumentoCaratulaReservadaEE.setCodigoTipoDocumentoSade(
					this.selectedTipoDocumentoEECaratulaReservada.getCodigoTipoDocumentoSade());
			tipoDocumentoCaratulaReservadaEE
					.setEsEspecial(this.selectedTipoDocumentoEECaratulaReservada.getEsEspecial());
			tipoDocumentoCaratulaReservadaEE.setNombre(this.selectedTipoDocumentoEECaratulaReservada.getNombre());
			tipoDocumentoCaratulaReservadaEE.setUsoEnEE(CARATULA_RESERVADA);
		} else {
			tipoDocumentoCaratulaReservadaEE.setAcronimo(this.selectedTipoDocumentoEECaratulaReservada.getAcronimo());
			tipoDocumentoCaratulaReservadaEE.setCodigoTipoDocumentoSade(
					this.selectedTipoDocumentoEECaratulaReservada.getCodigoTipoDocumentoSade());
			tipoDocumentoCaratulaReservadaEE
					.setEsEspecial(this.selectedTipoDocumentoEECaratulaReservada.getEsEspecial());
			tipoDocumentoCaratulaReservadaEE.setNombre(this.selectedTipoDocumentoEECaratulaReservada.getNombre());
		}

		this.tipoDocumentoService.guardar(tipoDocumentoCaratulaReservadaEE);
		this.tipoDocumentoService.guardar(tipoDocumentoCaratulaEE);
		this.guardarButton.setDisabled(true);
	}

	public Image getChecked() {
		return checked;
	}

	public void setChecked(Image checked) {
		this.checked = checked;
	}

	public Image getUnChecked() {
		return unChecked;
	}

	public void setUnChecked(Image unChecked) {
		this.unChecked = unChecked;
	}
}

final class ABMTraraOnNotifyWindowListener implements EventListener {
	private ABMTrataComposer composer;

	public ABMTraraOnNotifyWindowListener(ABMTrataComposer comp) {
		this.composer = comp;
	}

	public void onEvent(Event event) throws Exception {
		if (event.getName().equals(Events.ON_NOTIFY)) {
			this.composer.refreshTrata();
		}
	}
}