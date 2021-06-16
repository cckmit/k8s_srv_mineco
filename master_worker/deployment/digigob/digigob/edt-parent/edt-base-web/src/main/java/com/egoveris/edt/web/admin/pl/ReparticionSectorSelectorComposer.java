package com.egoveris.edt.web.admin.pl;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;

import com.egoveris.edt.base.service.admin.IAdminReparticionService;
import com.egoveris.edt.base.service.reparticion.IReparticionHelper;
import com.egoveris.edt.base.service.sector.ISectorService;
import com.egoveris.edt.web.common.BaseComposer;
import com.egoveris.sharedsecurity.base.model.ConstantesSesion;
import com.egoveris.sharedsecurity.base.model.cargo.CargoDTO;
import com.egoveris.sharedsecurity.base.model.reparticion.ReparticionDTO;
import com.egoveris.sharedsecurity.base.model.sector.SectorDTO;
import com.egoveris.sharedsecurity.base.service.ICargoService;
import com.egoveris.sharedsecurity.conf.service.Utilitarios;

public class ReparticionSectorSelectorComposer extends BaseComposer {

  private static Logger logger = LoggerFactory.getLogger(ReparticionSectorSelectorComposer.class);

  /**
  * 
  */
  private static final long serialVersionUID = 1L;

  private ICargoService cargoService;
  private ISectorService sectorService;
  private IAdminReparticionService adminReparticionService;
  private List<ReparticionDTO> listaReparticiones;
  private ReparticionDTO reparticionSeleccionada;
  private SectorDTO sectorSeleccionado;
  private List<ReparticionDTO> listaReparticionSeleccionada;
  private List<SectorDTO> listaSectores;
  private List<SectorDTO> listaSectoresSeleccionados;

  private boolean visualizarComboCargo;
  private List<CargoDTO> listaCargos;
  private List<CargoDTO> listaCargosSeleccionados;
  private boolean visualizarComboRolo;
  private CargoDTO cargoSeleccionado;
  private Listbox cargosListbox;
  private String textoCargo;
  private Row filaCargo;
  private Bandbox bandBoxCargo;

  private Listbox reparticionesSADEListbox;
  private Bandbox bandBoxReparticion;
  private Listbox sectoresSADEListbox;
  private Bandbox bandBoxSector;
  private Row filaSector;
  private boolean visualizarComboSector;
  private boolean habilitarComboReparticion;
  private boolean cargarCombos;

  private boolean readOnly = false;
  private boolean readOnlySectorCombo = false;
  private boolean readOnlyCargoCombo = false;

  private AnnotateDataBinder binder;

  private IReparticionHelper reparticionHelper;

  private String textoReparticion;
  private String textoSector;
  // SOLO SE UTILIZA EN EL CONTEXTO DE AUTENTICACION CON ACTIVE DIRECTORY
  private Window altaUsuarioAD;

  @Override
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    this.binder = new AnnotateDataBinder(comp);
    sectorService = (ISectorService) SpringUtil.getBean("sectorService");
    reparticionHelper = (IReparticionHelper) SpringUtil.getBean("reparticionHelper");
    cargoService = (ICargoService) SpringUtil.getBean("cargoService");

    adminReparticionService = (IAdminReparticionService) SpringUtil
        .getBean("adminReparticionService");
    listaReparticionSeleccionada = new ArrayList<>();
    listaSectoresSeleccionados = new ArrayList<>();
    listaCargosSeleccionados = new ArrayList<>();

		configurarPantalla();
		if (habilitarComboReparticion) {
			cargarListas();
		}
		llenarCombos();

    comp.addEventListener(Events.ON_USER, new AltaUsuarioADComposer(this));
    comp.addEventListener(Events.ON_CHECK, new ReparticionSectorSelectorListener(this));
    comp.addEventListener(Events.ON_CHANGE, new ReparticionSectorSelectorListener(this));
    comp.addEventListener("onCleanOrganismoBandbox", new ReparticionSectorSelectorListener(this));

    this.binder.loadAll();
  }

  private void cargarListas() {
    if (Utilitarios.isAdministradorCentral()) {
      this.listaReparticiones = reparticionHelper.obtenerTodosReparticiones();
    } else if (Utilitarios.isAdministradorLocalReparticion()) {
      listaReparticiones = adminReparticionService
          .obtenerReparticionesRelacionadasByUsername(getUsername());
    }
    listaCargos = cargoService.getCargosActivosVigentes();
    listaCargosSeleccionados.addAll(listaCargos);
  }

  /**
   * Llenar combos.
   */
	private void llenarCombos() {
		cargarCombos = (Boolean) Executions.getCurrent().getAttribute(ConstantesSesion.KEY_CARGAR_COMBO_REPARTICION);

		if (cargarCombos) {
			reparticionSeleccionada = (ReparticionDTO) Executions.getCurrent()
					.getAttribute(ConstantesSesion.REPARTICION_SELECCIONADA);
			if (reparticionSeleccionada != null) {
				bandBoxReparticion.setText(reparticionSeleccionada.getCodigo());
				textoReparticion = reparticionSeleccionada.getCodigo();
				getSession().setAttribute(ConstantesSesion.REPARTICION_SELECCIONADA, this.reparticionSeleccionada);

				if (visualizarComboSector) {
					listaSectores = sectorService.buscarSectoresPorReparticion(reparticionSeleccionada);
					sectorSeleccionado = (SectorDTO) Executions.getCurrent().getAttribute(ConstantesSesion.SECTOR_SELECCIONADO);
					bandBoxSector.setDisabled(
							!(!bandBoxReparticion.isDisabled() || (bandBoxReparticion.getText().trim().equals(StringUtils.EMPTY))));
					bandBoxSector.setText(sectorSeleccionado.getCodigo());
					textoSector = sectorSeleccionado.getCodigo();
					getSession().setAttribute(ConstantesSesion.SECTOR_SELECCIONADO, this.sectorSeleccionado);

					listaCargos = cargoService.getCargosActivosVigentes();
					cargoSeleccionado = (CargoDTO) Executions.getCurrent().getAttribute(ConstantesSesion.CARGO_SELECCIONADO);
					if (cargoSeleccionado != null) {
						bandBoxCargo.setText(cargoSeleccionado.getCargoNombre().toString());
						textoCargo = cargoSeleccionado.getCargoNombre();
						getSession().setAttribute(ConstantesSesion.CARGO_SELECCIONADO, this.cargoSeleccionado);
					}
					listaCargosSeleccionados.addAll(listaCargos);
				}
			}
			Boolean soloLectura = (Boolean) Executions.getCurrent()
					.getAttribute(ConstantesSesion.KEY_REPARTICIONES_SOLO_LECTURA);
			if (Boolean.TRUE.equals(soloLectura)) {
				bandBoxReparticion.setReadonly(true);
				bandBoxReparticion.setDisabled(true);

				bandBoxSector.setReadonly(true);
				bandBoxSector.setDisabled(true);

				bandBoxCargo.setReadonly(true);
				bandBoxCargo.setDisabled(true);

			} else {
				if (readOnly) {
					bandBoxReparticion.setReadonly(true);
					bandBoxReparticion.setDisabled(true);
				}

				if (readOnlySectorCombo) {
					bandBoxSector.setReadonly(true);
					bandBoxSector.setDisabled(true);
				}

				if (readOnlyCargoCombo) {
					bandBoxCargo.setReadonly(true);
					bandBoxCargo.setDisabled(true);
				}
			}
		} else {
			bandBoxSector.setDisabled(true);
		}
		this.binder.loadAll();
	}

  private void configurarPantalla() {
    visualizarComboSector = (Boolean) Executions.getCurrent()
        .getAttribute(ConstantesSesion.KEY_VISIBILIDAD_COMBO_SECTOR);
    visualizarComboCargo = (Boolean) Executions.getCurrent()
        .getAttribute(ConstantesSesion.KEY_VISIBILIDAD_COMBO_CARGO);
    habilitarComboReparticion = (Boolean) Executions.getCurrent()
        .getAttribute(ConstantesSesion.KEY_HABILITAR_COMBO_REPARTICION);

    try {
      if (Executions.getCurrent()
          .getAttribute(ConstantesSesion.KEY_REPARTICIONES_SOLO_LECTURA) != null) {
        readOnly = (Boolean) Executions.getCurrent()
            .getAttribute(ConstantesSesion.KEY_REPARTICIONES_SOLO_LECTURA);
      } else {
        readOnly = false;
      }
    } catch (NullPointerException ex) {
      logger.error(ex.getMessage(), ex);
      readOnly = false;
    }

    try {

      if (Executions.getCurrent()
          .getAttribute(ConstantesSesion.KEY_REPARTICIONES_SOLO_LECTURA_SECTOR) != null) {
        readOnlySectorCombo = (Boolean) Executions.getCurrent()
            .getAttribute(ConstantesSesion.KEY_REPARTICIONES_SOLO_LECTURA_SECTOR);
        readOnlyCargoCombo = (Boolean) Executions.getCurrent()
            .getAttribute(ConstantesSesion.KEY_REPARTICIONES_SOLO_LECTURA_CARGO);
      } else {
        readOnlySectorCombo = false;
      }
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      readOnlySectorCombo = false;
      readOnlyCargoCombo = false;
    }

    filaSector.setVisible(visualizarComboSector);
    bandBoxSector.setVisible(visualizarComboSector);
    bandBoxSector.setDisabled(true);
    filaCargo.setVisible(visualizarComboCargo);
    bandBoxCargo.setVisible(visualizarComboCargo);
    bandBoxReparticion.setDisabled(!habilitarComboReparticion);
    Boolean habilitacionBandBoxs = (Boolean) Executions.getCurrent()
        .getAttribute(ConstantesSesion.KEY_HABILITAR_BANDBOXS);
    if (habilitacionBandBoxs == null) {
      habilitacionBandBoxs = Boolean.TRUE;
    }
    habilitarBandBoxs(habilitacionBandBoxs);
    this.binder.loadAll();
  }

  /**
   * On select$reparticiones SADE listbox.
   */
  public void onSelect$reparticionesSADEListbox() {
    reparticionSeleccionada = listaReparticionSeleccionada
        .get(reparticionesSADEListbox.getSelectedIndex());
    bandBoxReparticion.setText(reparticionSeleccionada.getCodigo());
    textoReparticion = reparticionSeleccionada.getCodigo();
    /* SectorDTO */
    sectorSeleccionado = null;
    textoSector = null;
		listaSectores = sectorService.buscarSectoresPorReparticion(reparticionSeleccionada);
    bandBoxSector.setDisabled(false);
    bandBoxSector.setText(StringUtils.EMPTY);
    listaSectoresSeleccionados.clear();
    binder.loadAll();
    bandBoxReparticion.close();
    getSession().setAttribute(ConstantesSesion.REPARTICION_SELECCIONADA, this.reparticionSeleccionada);
    if (altaUsuarioAD != null) {
      Events.sendEvent(Events.ON_USER, altaUsuarioAD, null);
    }
  }

  public void onSelect$sectoresSADEListbox() {
    sectorSeleccionado = listaSectoresSeleccionados.get(sectoresSADEListbox.getSelectedIndex());
    bandBoxSector.setText(sectorSeleccionado.getCodigo());
    textoSector = sectorSeleccionado.getCodigo();
    binder.loadComponent(sectoresSADEListbox);
    bandBoxSector.close();
    getSession().setAttribute(ConstantesSesion.SECTOR_SELECCIONADO, this.sectorSeleccionado);
  }

  public void onSelect$cargosListbox() {
    cargoSeleccionado = listaCargosSeleccionados.get(cargosListbox.getSelectedIndex());
    bandBoxCargo.setText(cargoSeleccionado.getCargoNombre());
    textoCargo = cargoSeleccionado.getCargoNombre();
    binder.loadComponent(cargosListbox);
    bandBoxCargo.close();
    getSession().setAttribute(ConstantesSesion.CARGO_SELECCIONADO, this.cargoSeleccionado);
  }

  public void onChanging$bandBoxReparticion(InputEvent e) {
    sectorSeleccionado = null;
    bandBoxSector.setText(StringUtils.EMPTY);
    bandBoxSector.setDisabled(true);
    textoReparticion = e.getValue();
    this.cargarReparticiones(textoReparticion == null ? "" : textoReparticion);
  }

  public void onOpen$bandBoxReparticion() {
    sectorSeleccionado = null;
    bandBoxSector.setText(StringUtils.EMPTY);
    bandBoxSector.setDisabled(true);
    reparticionSeleccionada = null;
    this.cargarReparticiones(textoReparticion == null ? "" : textoReparticion);
  }

  public void onChanging$bandBoxSector(InputEvent e) {
    textoSector = e.getValue();
    this.cargarSectores(textoSector == null ? "" : textoSector);
  }

  public void onOpen$bandBoxSector() {
    sectorSeleccionado = null;
    this.cargarSectores(textoSector == null ? "" : textoSector);
  }

  public void onChanging$bandBoxCargo(InputEvent e) {
    textoCargo = e.getValue();
    this.cargarCargos(textoCargo == null ? "" : textoCargo);
  }

  public void onOpen$bandBoxCargo() {
    cargoSeleccionado = null;
    this.cargarCargos(textoCargo == null ? "" : textoCargo);
  }

  /**
   * Cargar reparticiones.
   *
   * @param matchingText the matching text
   */
  public void cargarReparticiones(String matchingText) {
    this.listaReparticionSeleccionada.clear();
    this.listaSectoresSeleccionados.clear();
    if (!"*".equals(matchingText.trim()) && matchingText.trim().length() > 2) {
      if (listaReparticiones != null) {
        String matchingTextTemp = matchingText.toUpperCase();
        for (ReparticionDTO reparticion : listaReparticiones) {
          String normalizadoValorA = Normalizer.normalize(matchingTextTemp, Normalizer.Form.NFKD)
              .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
          if ((reparticion != null)) {
            String normalizadoValorB = Normalizer
                .normalize(reparticion.getNombre(), Normalizer.Form.NFKD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
            if (reparticion.getNombre() != null) {
              if ((reparticion.getCodigo().contains(matchingTextTemp))
                  || StringUtils.containsIgnoreCase(normalizadoValorB, normalizadoValorA)) {
                listaReparticionSeleccionada.add(reparticion);
              }
            }
          }
        }
      }
    } else if ("*".equals(matchingText.trim())) {
      this.listaReparticionSeleccionada.addAll(listaReparticiones);
    } else if (matchingText.trim().length() < 2) {
      this.bandBoxSector.setValue(StringUtils.EMPTY);
      this.bandBoxSector.setDisabled(true);
      this.reparticionSeleccionada = null;
    }
    reparticionesSADEListbox.setActivePage(0);
    binder.loadComponent(reparticionesSADEListbox);
  }

  /**
   * Cargar sectores.
   *
   * @param matchingText the matching text
   */
  public void cargarSectores(String matchingText) {
    listaSectoresSeleccionados.clear();
    if (!matchingText.trim().equals("*") && matchingText.trim().length() > 0) {
      if (listaSectores != null) {
        String matchingTextTemp = matchingText.toUpperCase();
        for (SectorDTO sector : listaSectores) {
          if ((sector != null) && (sector.getNombre() != null)) {
            if ((sector.getCodigo().contains(matchingTextTemp))) {
              listaSectoresSeleccionados.add(sector);
            }
          }
        }

      }
    }else if(matchingText.trim().equals("*")) {
      this.listaSectoresSeleccionados.addAll(listaSectores);    	
    }
    else if (matchingText.trim().length() < 1) {
      this.listaSectoresSeleccionados.clear();
    }

    sectoresSADEListbox.setActivePage(0);
    binder.loadComponent(sectoresSADEListbox);
  }

  /**
   * Cargar cargos.
   *
   * @param matchingText the matching text
   */
  public void cargarCargos(String matchingText) {
    listaCargosSeleccionados.clear();
    if (!matchingText.trim().equals("*") && matchingText.trim().length() > 2) {
      if (listaCargos != null) {
        String matchingTextTemp = matchingText.toUpperCase();
        for (CargoDTO cargo : listaCargos) {
          if ((cargo != null) && (cargo.getCargoNombre() != null)) {
            if ((cargo.getCargoNombre().equals(matchingTextTemp))) {
              listaCargosSeleccionados.add(cargo);
            }
          }
        }
        if (listaCargosSeleccionados.isEmpty()) {
          listaCargosSeleccionados.addAll(listaCargos);
        }
      }
    }else if(matchingText.trim().equals("*")) {
      listaCargosSeleccionados.addAll(listaCargos);	
    }
    
    else if (matchingText.trim().length() < 2) {
      listaCargosSeleccionados.clear();
    }
    cargosListbox.setActivePage(0);
    binder.loadComponent(cargosListbox);
  }

  /**
   * Validar.
   *
   * @return true, if successful
   */
  private boolean validar() {
    if (bandBoxReparticion.getValue().trim().isEmpty() || reparticionSeleccionada == null) {
      throw new WrongValueException(bandBoxReparticion,
          Labels.getLabel("eu.adminSade.validacion.reparticion.codigo"));
    }

		if (!bandBoxReparticion.getValue().equals(reparticionSeleccionada.getCodigo())) {
			throw new WrongValueException(this.bandBoxReparticion,
					Labels.getLabel("eu.panelAdmin.tabMigraciones.validacion.reparticionInvalida"));
		}

		if (visualizarComboSector && (bandBoxSector.getValue().trim().isEmpty() || sectorSeleccionado == null)) {
			throw new WrongValueException(this.bandBoxSector, Labels.getLabel("eu.adminSade.validacion.sector.codigo"));
		}

		if (visualizarComboSector && !bandBoxSector.getValue().equals(sectorSeleccionado.getCodigo())) {
			throw new WrongValueException(this.bandBoxSector,
					Labels.getLabel("eu.panelAdmin.tabMigraciones.validacion.sectorInvalido"));
		}

		if (visualizarComboSector && !sectorService.esSectorActivo(sectorSeleccionado)) {
			throw new WrongValueException(this.bandBoxSector, Labels.getLabel("eu.adminSade.validacion.sector"));
		}

		if (visualizarComboCargo && (bandBoxCargo.getValue().trim().isEmpty() || cargoSeleccionado == null)) {
			throw new WrongValueException(this.bandBoxCargo, Labels.getLabel("eu.adminSade.validacion.cargo.codigo"));
		}
		
		if (visualizarComboCargo && !bandBoxCargo.getValue().equals(cargoSeleccionado.getCargoNombre())) {
			throw new WrongValueException(this.bandBoxCargo,
					Labels.getLabel("eu.panelAdmin.tabMigraciones.validacion.cargoInvalido"));
		}

    return true;
  }

  private void habilitarBandBoxs(boolean estado) {
    bandBoxReparticion.setDisabled(!estado);
    bandBoxSector.setDisabled(!estado);
    bandBoxCargo.setDisabled(!estado);
  }

  /**
   * Limpiar band boxs.
   */
  private void limpiarBandBoxs() {
    reparticionSeleccionada = null;
    bandBoxReparticion.setText(StringUtils.EMPTY);
    listaReparticionSeleccionada.clear();
    sectorSeleccionado = null;
    bandBoxSector.setText(StringUtils.EMPTY);
    bandBoxSector.setDisabled(true);
    listaSectoresSeleccionados.clear();
    cargoSeleccionado = null;
    bandBoxCargo.setText(StringUtils.EMPTY);
    listaCargosSeleccionados.clear();
  }
  
  public ISectorService getSectorService() {
    return sectorService;
  }

  public void setSectorService(ISectorService sectorService) {
    this.sectorService = sectorService;
  }

  public List<ReparticionDTO> getListaReparticiones() {
    return listaReparticiones;
  }

  public boolean isHabilitarComboSector() {
    return visualizarComboSector;
  }

  public void setHabilitarComboSector(boolean habilitarComboSector) {
    this.visualizarComboSector = habilitarComboSector;
  }

  public void setListaReparticiones(List<ReparticionDTO> listaReparticiones) {
    this.listaReparticiones = listaReparticiones;
  }

  public ReparticionDTO getReparticionSeleccionada() {
    return reparticionSeleccionada;
  }

  public void setReparticionSeleccionada(ReparticionDTO reparticionSeleccionada) {
    this.reparticionSeleccionada = reparticionSeleccionada;
  }

  public List<ReparticionDTO> getListaReparticionSeleccionada() {
    return listaReparticionSeleccionada;
  }

  public void setListaReparticionSeleccionada(List<ReparticionDTO> listaReparticionSeleccionada) {
    this.listaReparticionSeleccionada = listaReparticionSeleccionada;
  }

  public Window getAltaUsuarioAD() {
    return altaUsuarioAD;
  }

  public void setAltaUsuarioAD(Window altaUsuarioAD) {
    this.altaUsuarioAD = altaUsuarioAD;
  }

  public List<SectorDTO> getListaSectores() {
    return listaSectores;
  }

  public void setListaSectores(List<SectorDTO> listaSectores) {
    this.listaSectores = listaSectores;
  }

  public List<SectorDTO> getListaSectoresSeleccionados() {
    return listaSectoresSeleccionados;
  }

  public void setListaSectoresSeleccionados(List<SectorDTO> listaSectoresSeleccionados) {
    this.listaSectoresSeleccionados = listaSectoresSeleccionados;
  }

  public Listbox getReparticionesSADEListbox() {
    return reparticionesSADEListbox;
  }

  public void setReparticionesSADEListbox(Listbox reparticionesSADEListbox) {
    this.reparticionesSADEListbox = reparticionesSADEListbox;
  }

  public Bandbox getBandBoxReparticion() {
    return bandBoxReparticion;
  }

  public void setBandBoxReparticion(Bandbox reparticionAltaUsuario) {
    this.bandBoxReparticion = reparticionAltaUsuario;
  }

  public Listbox getSectoresSADEListbox() {
    return sectoresSADEListbox;
  }

  public void setSectoresSADEListbox(Listbox sectoresSADEListbox) {
    this.sectoresSADEListbox = sectoresSADEListbox;
  }

  public Bandbox getBandBoxSector() {
    return bandBoxSector;
  }

  public void setBandBoxSector(Bandbox sectorAltaUsuario) {
    this.bandBoxSector = sectorAltaUsuario;
  }

  public SectorDTO getSectorSeleccionado() {
    return sectorSeleccionado;
  }

  public void setSectorSeleccionado(SectorDTO sectorSeleccionado) {
    this.sectorSeleccionado = sectorSeleccionado;
  }

  public Row getFilaSector() {
    return filaSector;
  }

  public void setFilaSector(Row filaSector) {
    this.filaSector = filaSector;
  }

  public boolean isReadOnly() {
    return readOnly;
  }

  public void setReadOnly(boolean readOnly) {
    this.readOnly = readOnly;
  }

  public void onChanging$txtBusquedaReparticioBusquedaSADE(InputEvent e) {
    onChanging$bandBoxReparticion(e);
  }

  public List<CargoDTO> getListaCargosSeleccionados() {
    return listaCargosSeleccionados;
  }

  public void setListaCargosSeleccionados(List<CargoDTO> listaCargosSeleccionados) {
    this.listaCargosSeleccionados = listaCargosSeleccionados;
  }

  public List<CargoDTO> getListaCargos() {
    return listaCargos;
  }

  public void setListaCargos(List<CargoDTO> listaCargos) {
    this.listaCargos = listaCargos;
  }

  public boolean isVisualizarComboSector() {
    return visualizarComboSector;
  }

  public void setVisualizarComboSector(boolean visualizarComboSector) {
    this.visualizarComboSector = visualizarComboSector;
  }

  public boolean isVisualizarComboRolo() {
    return visualizarComboRolo;
  }

  public void setVisualizarComboRolo(boolean visualizarComboRolo) {
    this.visualizarComboRolo = visualizarComboRolo;
  }

  public boolean isVisualizarComboCargo() {
    return visualizarComboCargo;
  }

  public void setVisualizarComboCargo(boolean visualizarComboCargo) {
    this.visualizarComboCargo = visualizarComboCargo;
  }

  public Row getFilaCargo() {
    return filaCargo;
  }

  public void setFilaCargo(Row filaCargo) {
    this.filaCargo = filaCargo;
  }

  public Listbox getCargosListbox() {
    return cargosListbox;
  }

  public void setCargosListbox(Listbox cargosListbox) {
    this.cargosListbox = cargosListbox;
  }

  public CargoDTO getCargoSeleccionado() {
    return cargoSeleccionado;
  }

  public void setCargoSeleccionado(CargoDTO cargoSeleccionado) {
    this.cargoSeleccionado = cargoSeleccionado;
  }

  /**
   * The listener interface for receiving reparticionSectorSelector events.
   * The class that is interested in processing a reparticionSectorSelector
   * event implements this interface, and the object created
   * with that class is registered with a component using the
   * component's <code>addReparticionSectorSelectorListener<code> method. When
   * the reparticionSectorSelector event occurs, that object's appropriate
   * method is invoked.
   *
   * @see ReparticionSectorSelectorEvent
   */
  @SuppressWarnings("rawtypes")
	final class ReparticionSectorSelectorListener implements EventListener {
    @SuppressWarnings("unused")
    private ReparticionSectorSelectorComposer composer;

    public ReparticionSectorSelectorListener(ReparticionSectorSelectorComposer comp) {
      this.composer = comp;
    }

    @Override
    public void onEvent(Event event) throws Exception {
      if (event.getName().equals(Events.ON_CHANGE)) {
        resetearCombosReparticionSector();
      } else if (event.getName().equals(Events.ON_CHECK)) {
      	validar();
      } else if ("onCleanOrganismoBandbox".equals(event.getName())) {
      	limpiarBandBoxs();
        binder.loadComponent(bandBoxReparticion);
      }
    }
    
    /**
     * Resetear combos reparticion sector.
     */
    private void resetearCombosReparticionSector() {
      bandBoxReparticion.setText(StringUtils.EMPTY);
      bandBoxSector.setText(StringUtils.EMPTY);
      bandBoxCargo.setText(StringUtils.EMPTY);
      reparticionSeleccionada = null;
      sectorSeleccionado = null;
      cargoSeleccionado = null;
      binder.loadComponent(bandBoxReparticion);
      binder.loadComponent(bandBoxSector);
      binder.loadComponent(bandBoxCargo);
    }
  }

  final class AltaUsuarioADComposer implements EventListener {
    private ReparticionSectorSelectorComposer composer;

    public AltaUsuarioADComposer(ReparticionSectorSelectorComposer c) {
      composer = c;
    }

    @Override
    public void onEvent(Event event) throws Exception {
      Window w = (Window) event.getData();
      composer.setAltaUsuarioAD(w);

    }
  }

  public boolean isReadOnlyCargoCombo() {
    return readOnlyCargoCombo;
  }

  public void setReadOnlyCargoCombo(boolean readOnlyCargoCombo) {
    this.readOnlyCargoCombo = readOnlyCargoCombo;
  }

}
