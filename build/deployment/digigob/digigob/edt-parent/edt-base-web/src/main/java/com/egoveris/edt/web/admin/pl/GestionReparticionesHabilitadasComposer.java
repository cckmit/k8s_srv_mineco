package com.egoveris.edt.web.admin.pl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.egoveris.edt.base.exception.NegocioException;
import com.egoveris.edt.base.service.usuario.IDatosUsuarioService;
import com.egoveris.edt.web.common.BaseComposer;
import com.egoveris.sharedsecurity.base.model.ConstantesSesion;
import com.egoveris.sharedsecurity.base.model.UsuarioBaseDTO;
import com.egoveris.sharedsecurity.base.model.UsuarioReparticionHabilitadaDTO;
import com.egoveris.sharedsecurity.base.model.cargo.CargoDTO;
import com.egoveris.sharedsecurity.base.model.reparticion.ReparticionDTO;
import com.egoveris.sharedsecurity.base.model.sector.SectorDTO;
import com.egoveris.sharedsecurity.base.service.ISectorUsuarioService;
import com.egoveris.sharedsecurity.base.service.IUsuarioReparticionHabilitadaService;

public class GestionReparticionesHabilitadasComposer extends BaseComposer {

  private static final Logger logger = LoggerFactory
      .getLogger(GestionReparticionesHabilitadasComposer.class);

  /**
  * 
  */
  private static final long serialVersionUID = 1L;

  @Autowired
  protected AnnotateDataBinder binder;

  private String username;
  private ReparticionDTO reparticionSeleccionada;
  private SectorDTO sectorSeleccionado;
  private IUsuarioReparticionHabilitadaService usuarioReparticionHabilitadaService;
  private List<UsuarioReparticionHabilitadaDTO> reparticionesHabilitadas;
  private List<UsuarioReparticionHabilitadaDTO> reparticionesAgregadas;
  private List<UsuarioReparticionHabilitadaDTO> repsHabilitadasBorrar;
  private UsuarioReparticionHabilitadaDTO reparticionHabilitadaSeleccionada;
  private Label lbl_reparticionAsignada;
  private CargoDTO cargoSeleccionado;

  @Autowired
  private Window win_reparticionesHabilitadas;
  @Autowired
  private Include inc_reparticionSectorSelector;
  private Listbox lbx_reparticionesHabilitadas;
  @Autowired
  private Window ventanaContenedora;

  @Autowired
  private IDatosUsuarioService datosUsuarioService;

  private ISectorUsuarioService sectorUsuarioService;

  @Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		UsuarioBaseDTO usuario = (UsuarioBaseDTO) Executions.getCurrent().getAttribute(ConstantesSesion.KEY_USUARIO);
		username = usuario.getUid();

		comp.addEventListener(Events.ON_CHANGE, new GestionReparticionesHabilitadasListener(this));

		usuarioReparticionHabilitadaService = (IUsuarioReparticionHabilitadaService) SpringUtil
				.getBean("usuarioReparticionHabilitadaService");

		repsHabilitadasBorrar = new ArrayList<>();
		reparticionesAgregadas = new ArrayList<>();
		reparticionesHabilitadas = new ArrayList<>(
				usuarioReparticionHabilitadaService.obtenerReparticionesHabilitadasByUsernameSinLaPropia(username));

		datosUsuarioService = (IDatosUsuarioService) SpringUtil.getBean("datosUsuarioService");

		sectorUsuarioService = (ISectorUsuarioService) SpringUtil.getBean("sectorUsuarioService");

		ventanaContenedora = (Window) Executions.getCurrent()
				.getAttribute(ConstantesSesion.KEY_WINDOW_REPARTICIONES_ADMINISTRADAS_USUARIO);

		UsuarioReparticionHabilitadaDTO reparticionDelUsuarioSeleccionadousuario = usuarioReparticionHabilitadaService
				.obtenerReparticionHabilitadaByReparticionAsignadaAlUsuario(username);
		if (reparticionDelUsuarioSeleccionadousuario != null) {
			lbl_reparticionAsignada
					.setValue(Labels.getLabel("eu.adminSade.reparticiones.administradas.label.reparticionAsignadaDelUsuario")
							+ " " + reparticionDelUsuarioSeleccionadousuario.getReparticion().getCodigo());
		} else {
			lbl_reparticionAsignada.setVisible(false);
		}
		ventanaContenedora = (Window) Executions.getCurrent()
				.getAttribute(ConstantesSesion.KEY_WINDOW_REPARTICIONES_ADMINISTRADAS_USUARIO);
		configurarComboSectorReparticion();
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
	}

  private void configurarComboSectorReparticion() {
    inc_reparticionSectorSelector.setDynamicProperty(ConstantesSesion.KEY_VISIBILIDAD_COMBO_SECTOR,
        true);
    inc_reparticionSectorSelector.setDynamicProperty(ConstantesSesion.KEY_HABILITAR_COMBO_REPARTICION,
        true);
    inc_reparticionSectorSelector.setDynamicProperty(ConstantesSesion.KEY_CARGAR_COMBO_REPARTICION,
        false);
    inc_reparticionSectorSelector.setDynamicProperty(ConstantesSesion.KEY_VISIBILIDAD_COMBO_CARGO, true);
    inc_reparticionSectorSelector.setSrc(ConstantesSesion.PANEL_REPARTICION_SECTOR_SELECTOR);

  }

	/**
	 * Agregar usuario reparticiones habilitadas.
	 *
	 * @throws NegocioException the negocio exception
	 * @throws InterruptedException the interrupted exception
	 */
	private void agregarUsuarioReparticionesHabilitadas() throws NegocioException, InterruptedException {
		UsuarioReparticionHabilitadaDTO usuarioReparticionHabilitada = new UsuarioReparticionHabilitadaDTO(username,
				reparticionSeleccionada, sectorSeleccionado, cargoSeleccionado);
		reparticionesHabilitadas.add(usuarioReparticionHabilitada);
    reparticionesAgregadas.add(usuarioReparticionHabilitada);
		Messagebox.show(Labels.getLabel("eu.adminSade.reparticiones.habilitadas.altaExitosa"),
				Labels.getLabel("eu.adminSade.reparticion.generales.informacion"), Messagebox.OK, Messagebox.INFORMATION);
		this.binder.loadComponent(lbx_reparticionesHabilitadas);
	}
  
  /**
   * Clean organismo bandbox.
   */
  private void cleanOrganismoBandbox() {
    Component comp = inc_reparticionSectorSelector.getFellow("win_reparticionSectorSelector");
    Events.sendEvent("onCleanOrganismoBandbox", comp, null);
  }

	/**
	 * On click$btn habilitar reparticion.
	 *
	 * @throws InterruptedException the interrupted exception
	 */
	public void onClick$btn_habilitarReparticion() throws InterruptedException {
		setReparticionSeleccionada((ReparticionDTO) getSession().getAttribute(ConstantesSesion.REPARTICION_SELECCIONADA));
		setSectorSeleccionado((SectorDTO) getSession().getAttribute(ConstantesSesion.SECTOR_SELECCIONADO));
		setCargoSeleccionado((CargoDTO) getSession().getAttribute(ConstantesSesion.CARGO_SELECCIONADO));
		validarCampos();
		if (validarReparticionCargo()) {
			try {
				if ((reparticionesHabilitadas != null) && (sectorSeleccionado != null)) {
					agregarUsuarioReparticionesHabilitadas();
					cleanOrganismoBandbox();
				}
			} catch (NegocioException e) {
				logger.error(e.getMessage(), e);
				Messagebox.show(e.getMessage(), Labels.getLabel("eu.adminSade.usuario.generales.error"), Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

  /**
   * Validar reparticion cargo.
   *
   * @return true, if successful
   * @throws InterruptedException the interrupted exception
   */
  private boolean validarReparticionCargo() throws InterruptedException {
    boolean agregar = true;
    for (UsuarioReparticionHabilitadaDTO o : reparticionesHabilitadas) {
      if (o.getCargo().equals(cargoSeleccionado)
          && o.getSector().getCodigo().equals(sectorSeleccionado.getCodigo())
          && o.getNombreUsuario().equalsIgnoreCase(username)
          && o.getReparticion().equals(reparticionSeleccionada)) {
        agregar = false;
        break;
      }
    }

    if (!agregar) {
      Messagebox.show(Labels.getLabel("eu.gestRepHabComp.msgbox.orgSecCargoExisten"),
          Labels.getLabel("eu.gestRepHabComp.msgbox.orgDuplicado"), Messagebox.OK,
          Messagebox.INFORMATION);
      return false;
    }
    return true;
  }

  public void onClick$btn_guardar() throws InterruptedException {

    try {

      // Utilizo el listado de RH para borrar y las elimino
      for (UsuarioReparticionHabilitadaDTO usuarioReparticionHabilitada : repsHabilitadasBorrar) {
        usuarioReparticionHabilitadaService
            .eliminarReparticionHabilitada(usuarioReparticionHabilitada);
      }

			for (UsuarioReparticionHabilitadaDTO usuarioReparticionHabilitada : reparticionesAgregadas) {
				usuarioReparticionHabilitada.setCargoId(usuarioReparticionHabilitada.getCargo().getId());
				usuarioReparticionHabilitadaService.guardarReparticionHabilitada(usuarioReparticionHabilitada);
			}
      // Guardo Las RH vigentes

      Messagebox.show(Labels.getLabel("eu.gestRepHabComp.msgbox.guardadoOrganismos"),
          Labels.getLabel("eu.adminSade.usuario.generales.informacion"), Messagebox.OK,
          Messagebox.INFORMATION);
    } catch (NegocioException e) {
      logger.error(e.getMessage(), e);
      Messagebox.show(e.getMessage(), Labels.getLabel("eu.adminSade.usuario.generales.error"),
          Messagebox.OK, Messagebox.ERROR);
    }
    Events.sendEvent(ventanaContenedora, new Event(Events.ON_CLOSE));
  }

  private boolean validarCampos() {
    Component comp = inc_reparticionSectorSelector.getFellow("win_reparticionSectorSelector");
    Events.sendEvent(Events.ON_CHECK, comp, null);
    return true;
  }

  public void onEliminarRH() throws InterruptedException {
    try {
      repsHabilitadasBorrar.add(reparticionHabilitadaSeleccionada);
      reparticionesHabilitadas.remove(reparticionHabilitadaSeleccionada);
      reparticionesAgregadas.remove(reparticionHabilitadaSeleccionada);
      Messagebox.show(Labels.getLabel("eu.adminSade.reparticiones.habilitadas.eliminacion"),
          Labels.getLabel("eu.adminSade.reparticion.generales.informacion"), Messagebox.OK,
          Messagebox.INFORMATION);
      binder.loadComponent(lbx_reparticionesHabilitadas);
    } catch (SuspendNotAllowedException e) {
      logger.error(e.getMessage(), e);
      Messagebox.show(e.getMessage(), Labels.getLabel("eu.adminSade.usuario.generales.error"),
          Messagebox.OK, Messagebox.ERROR);
    }
  }

  public ReparticionDTO getReparticionSeleccionada() {
    return reparticionSeleccionada;
  }

  public void setReparticionSeleccionada(ReparticionDTO reparticionSeleccionada) {
    this.reparticionSeleccionada = reparticionSeleccionada;
  }

  public SectorDTO getSectorSeleccionado() {
    return sectorSeleccionado;
  }

  public void setSectorSeleccionado(SectorDTO sectorSeleccionado) {
    this.sectorSeleccionado = sectorSeleccionado;
  }

  public CargoDTO getCargoSeleccionado() {
    return cargoSeleccionado;
  }

  public void setCargoSeleccionado(CargoDTO cargoSeleccionado) {
    this.cargoSeleccionado = cargoSeleccionado;
  }

  public UsuarioReparticionHabilitadaDTO getReparticionHabilitadaSeleccionada() {
    return reparticionHabilitadaSeleccionada;
  }

  public void setReparticionHabilitadaSeleccionada(
      UsuarioReparticionHabilitadaDTO reparticionHabilitadaSeleccionada) {
    this.reparticionHabilitadaSeleccionada = reparticionHabilitadaSeleccionada;
  }

  public Include getInc_reparticionSectorSelector() {
    return inc_reparticionSectorSelector;
  }

  public void setInc_reparticionSectorSelector(Include inc_reparticionSectorSelector) {
    this.inc_reparticionSectorSelector = inc_reparticionSectorSelector;
  }

  public Window getWin_reparticionesHabilitadas() {
    return win_reparticionesHabilitadas;
  }

  public void setWin_reparticionesHabilitadas(Window win_reparticionesHabilitadas) {
    this.win_reparticionesHabilitadas = win_reparticionesHabilitadas;
  }

  public Listbox getLbx_reparticionesHabilitadas() {
    return lbx_reparticionesHabilitadas;
  }

  public void setLbx_reparticionesHabilitadas(Listbox lbx_reparticionesHabilitadas) {
    this.lbx_reparticionesHabilitadas = lbx_reparticionesHabilitadas;
  }

  private void resetearCombosReparticionSector() {
    Component comp = inc_reparticionSectorSelector.getFellow("win_reparticionSectorSelector");
    Events.sendEvent(Events.ON_CHANGE, comp, null);
  }

  final class GestionReparticionesHabilitadasListener implements EventListener {
    @SuppressWarnings("unused")
    private GestionReparticionesHabilitadasComposer composer;

    public GestionReparticionesHabilitadasListener(GestionReparticionesHabilitadasComposer comp) {
      this.composer = comp;
    }

    @Override
    public void onEvent(Event event) throws Exception {
      if (event.getName().equals(Events.ON_CHANGE)) {
        resetearCombosReparticionSector();
      }
    }

  }

	/**
	 * @return the reparticionesHabilitadas
	 */
	public List<UsuarioReparticionHabilitadaDTO> getReparticionesHabilitadas() {
		return reparticionesHabilitadas;
	}

	/**
	 * @param reparticionesHabilitadas the reparticionesHabilitadas to set
	 */
	public void setReparticionesHabilitadas(List<UsuarioReparticionHabilitadaDTO> reparticionesHabilitadas) {
		this.reparticionesHabilitadas = reparticionesHabilitadas;
	}

}
