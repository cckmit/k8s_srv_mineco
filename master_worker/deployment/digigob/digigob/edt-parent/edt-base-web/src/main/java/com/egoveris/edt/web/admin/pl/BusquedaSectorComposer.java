package com.egoveris.edt.web.admin.pl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
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
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.egoveris.edt.base.service.admin.IAdminReparticionService;
import com.egoveris.edt.base.service.sector.ISectorService;
import com.egoveris.edt.web.common.BaseComposer;
import com.egoveris.sharedsecurity.base.model.AdminReparticionDTO;
import com.egoveris.sharedsecurity.base.model.ConstantesSesion;
import com.egoveris.sharedsecurity.base.model.reparticion.ReparticionDTO;
import com.egoveris.sharedsecurity.base.model.sector.SectorDTO;
import com.egoveris.sharedsecurity.base.service.IReparticionEDTService;
import com.egoveris.sharedsecurity.conf.service.Utilitarios;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class BusquedaSectorComposer extends BaseComposer {

	/** The Constant serialVersionUID. */
  private static final long serialVersionUID = -7470447255066378087L;
  
  /** The logger. */
  private static Logger logger = LoggerFactory.getLogger(BusquedaSectorComposer.class);

	/** The Constant WIN_ABM_SECTOR. */
	private static final String WIN_ABM_SECTOR = "win_abmSector";
	
  @WireVariable("sectorService")
  private ISectorService sectorService;
  
  @WireVariable("reparticionEDTService")
  private IReparticionEDTService reparticionService;

  @Autowired
  protected AnnotateDataBinder binder;
  @Autowired
  private List<SectorDTO> listaSectores;
  @Autowired
  private Integer resultados;
  @Autowired
  private SectorDTO sectorSeleccionado;

  @Autowired
  private Listbox lstbx_sectores;
  @Autowired
  private Textbox txbx_sectorBuscadoPorCodigo;
  @Autowired
  private Textbox txbx_sectorBuscadoPorDescripcion;
  @Autowired
  private Textbox txbx_sectorBuscadoPorCodigoReparticion;

  @WireVariable("adminReparticionService")
  private IAdminReparticionService adminReparticionService;

  private Set<AdminReparticionDTO> reparticionesAdministradas;

  @SuppressWarnings("unchecked")
	@Override
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    this.binder = new AnnotateDataBinder(comp);
    
    sectorSeleccionado = new SectorDTO();
    listaSectores = new ArrayList<SectorDTO>();
    resultados = listaSectores.size();
    comp.addEventListener(Events.ON_USER, new BusquedaSectorComposerListener(this));
    comp.addEventListener(Events.ON_NOTIFY, new BusquedaSectorComposerListener(this));
  }

	/**
	 * On click$tbbtn alta sector.
	 *
	 * @throws InterruptedException the interrupted exception
	 */
	public void onClick$tbbtn_altaSector() throws InterruptedException {
		try {
			Utilitarios.closePopUps(WIN_ABM_SECTOR);
			Map<String, Object> parametros = new HashMap<>();
			parametros.put(ConstantesSesion.KEY_ALTA, true);
			Window window = (Window) Executions.createComponents(ABMSectorComposer.ABM_SECTOR_ZUL, this.self, parametros);
			window.setPosition("center");
			window.setVisible(true);
			window.doModal();
		} catch (SuspendNotAllowedException e) {
			logger.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), Labels.getLabel("eu.adminSade.usuario.generales.error"), Messagebox.OK,
					Messagebox.ERROR);
		}
	}

  /**
   * On click$btn buscar por codigo.
   *
   * @throws InterruptedException the interrupted exception
   */
  public void onClick$btn_buscarPorCodigo() throws InterruptedException {
    if (txbx_sectorBuscadoPorCodigo.getValue().trim().equals("*")
    		|| txbx_sectorBuscadoPorCodigo.getValue().length() >= 1) {
      buscarSectoresPorCodigo();
    } else {
      throw new WrongValueException(txbx_sectorBuscadoPorCodigo,
          Labels.getLabel("eu.adminSade.validacion.minimoBusqueda"));
    }
    this.binder.loadAll();
  }

  /**
   * On click$btn buscar por codigo reparticion.
   *
   * @throws InterruptedException the interrupted exception
   */
  public void onClick$btn_buscarPorCodigoReparticion() throws InterruptedException {
    txbx_sectorBuscadoPorCodigoReparticion
        .setValue(txbx_sectorBuscadoPorCodigoReparticion.getValue().toUpperCase());
    if (txbx_sectorBuscadoPorCodigoReparticion.getValue().trim().equals("*")
    		||
    		txbx_sectorBuscadoPorCodigoReparticion.getValue().length() >= 2) {
      buscarSectoresPorCodigoReparticion();
    } else {
      throw new WrongValueException(txbx_sectorBuscadoPorCodigoReparticion,
          Labels.getLabel("eu.adminSade.validacion.minimoBusqueda"));
    }
    this.binder.loadAll();
  }

  /**
   * On click$btn buscar por descripcion.
   *
   * @throws InterruptedException the interrupted exception
   */
  public void onClick$btn_buscarPorDescripcion() throws InterruptedException {
    if (txbx_sectorBuscadoPorDescripcion.getValue().trim().equals("*")
    		|| txbx_sectorBuscadoPorDescripcion.getValue().length() >= 2) {
      buscarSectoresPorDescripcion();
    } else {
      throw new WrongValueException(txbx_sectorBuscadoPorDescripcion,
          Labels.getLabel("eu.adminSade.validacion.minimoBusqueda"));
    }
    this.binder.loadAll();
  }

  /**
   * Buscar sectores por codigo reparticion.
   *
   * @throws InterruptedException the interrupted exception
   */
  public void buscarSectoresPorCodigoReparticion() throws InterruptedException {

    String valor = txbx_sectorBuscadoPorCodigoReparticion.getValue().trim().toUpperCase();
   
    boolean todos = false;
    
    List<ReparticionDTO> reparticionesEncontrada = new ArrayList<>();

    if (Utilitarios.isAdministradorLocalReparticion() && !Utilitarios.isAdministradorCentral()) {
      List<ReparticionDTO> poolDereparticiones = new ArrayList<>();

      this.reparticionesAdministradas = new HashSet<>(
          adminReparticionService.obtenerReparticionesAdministradasByUsername(getUsername()));

      for (AdminReparticionDTO reparticionAdmin : reparticionesAdministradas) {
        poolDereparticiones.add(reparticionAdmin.getReparticion());
      }

      poolDereparticiones.add(reparticionService.getReparticionByUserName(getUsername()));

      for (ReparticionDTO reparticion : poolDereparticiones) {
      	
      	if(valor.equals("*")) {
          reparticionesEncontrada.add(reparticion);
          continue;
      	}
        if (StringUtils.containsIgnoreCase(valor, reparticion.getCodigo())) {
          reparticionesEncontrada.add(reparticion);
        }
      }

    } else {
    	if(valor.equals("*")) {
    		todos = true;
    	}else {    		
    		reparticionesEncontrada = reparticionService.buscarReparticionByCodigoComodin(valor);
    	}
    }

    if(todos) {
      listaSectores = sectorService.listaSectores();
      resultados = listaSectores.size();
    	
    }else if (reparticionesEncontrada != null && !reparticionesEncontrada.isEmpty()) {
    	listaSectores = sectorService.buscarSectoresPorReparticiones(reparticionesEncontrada);
      resultados = listaSectores.size();
    	
    } else {
      throw new WrongValueException(txbx_sectorBuscadoPorCodigoReparticion, Labels
          .getLabel("eu.adminSade.validacion.BusquedaSectorByReparticion.NoExiteReparticion"));
    }
    this.binder.loadAll();
  }

  public void buscarSectoresPorCodigo() throws InterruptedException {
    
  	if (!txbx_sectorBuscadoPorCodigo.getValue().isEmpty()
  			&& txbx_sectorBuscadoPorCodigo.getValue().trim().equals("*")) {
      listaSectores = sectorService.listaSectores();
      resultados = listaSectores.size();
      this.binder.loadAll();
    }else if(!txbx_sectorBuscadoPorCodigo.getValue().isEmpty()) {
      listaSectores = sectorService
          .buscarSectoresPorCodigoDeSectorComodin(txbx_sectorBuscadoPorCodigo.getValue());
      resultados = listaSectores.size();
      this.binder.loadAll();    	
    	
    }
  }

  public void buscarSectoresPorDescripcion() throws InterruptedException {
    if (!txbx_sectorBuscadoPorDescripcion.getValue().isEmpty()
    		&& txbx_sectorBuscadoPorDescripcion.getValue().trim().equals("*")) {

      listaSectores = sectorService.listaSectores();
      resultados = listaSectores.size();
      this.binder.loadAll();
    	
    }else if(!txbx_sectorBuscadoPorDescripcion.getValue().isEmpty()){
    	
      listaSectores = sectorService
          .buscarSectoresPorDescripcion(txbx_sectorBuscadoPorDescripcion.getValue());
      resultados = listaSectores.size();
      this.binder.loadAll();    	
    }
  }

  /**
   * On visualizar sectores.
   *
   * @throws InterruptedException the interrupted exception
   */
  public void onVisualizarSectores() throws InterruptedException {
    try {
			Utilitarios.closePopUps(WIN_ABM_SECTOR);
      Map<String, Object> parametros = new HashMap<>();
      parametros.put(ConstantesSesion.KEY_SECTOR, sectorSeleccionado);
      parametros.put(ConstantesSesion.KEY_VISUALIZAR, true);
      parametros.put(ConstantesSesion.KEY_REPARTICIONES_SOLO_LECTURA, true);
      Window win = (Window) Executions.createComponents(ABMSectorComposer.ABM_SECTOR_ZUL, this.self,
          parametros);
      win.setMode("modal");
      win.setClosable(true);
      win.setTitle(Labels.getLabel("eu.busquedaSectorComposer.winTitle.visSector"));
      win.setPosition("center");
      win.setVisible(true);
      win.setBorder("normal");
      win.doModal();
    } catch (SuspendNotAllowedException e) {
      logger.error(e.getMessage(), e);
      Messagebox.show(e.getMessage(), Labels.getLabel("eu.adminSade.usuario.generales.error"),
          Messagebox.OK, Messagebox.ERROR);
    }
  }

  /**
   * On modificar sector.
   *
   * @throws InterruptedException the interrupted exception
   */
  public void onModificarSector() throws InterruptedException {
    try {
			Utilitarios.closePopUps(WIN_ABM_SECTOR);
      Map<String, Object> parametros = new HashMap<>();
      parametros.put("sector", sectorSeleccionado);
      parametros.put(ConstantesSesion.KEY_MODIFICAR, true);
      parametros.put(ConstantesSesion.KEY_REPARTICIONES_SOLO_LECTURA, true);
      Window win = (Window) Executions.createComponents(ABMSectorComposer.ABM_SECTOR_ZUL, this.self,
          parametros);
      win.setMode("modal");
      win.setClosable(true);
      win.setTitle(Labels.getLabel("eu.adminSade.modificarSector"));
      win.setPosition("center");
      win.setVisible(true);
      win.setBorder("normal");
      win.doModal();
    } catch (SuspendNotAllowedException e) {
      logger.error(e.getMessage(), e);
      Messagebox.show(e.getMessage(), Labels.getLabel("eu.adminSade.usuario.generales.error"),
          Messagebox.OK, Messagebox.ERROR);
    }
  }
  
  public ISectorService getSectorService() {
    return sectorService;
  }

  public void setSectorService(ISectorService sectorService) {
    this.sectorService = sectorService;
  }

  public SectorDTO getSectorSeleccionado() {
    return sectorSeleccionado;
  }

  public void setSectorSeleccionado(SectorDTO sectorSeleccionado) {
    this.sectorSeleccionado = sectorSeleccionado;
  }

  public Listbox getLstbx_sectores() {
    return lstbx_sectores;
  }

  public void setLstbx_sectores(Listbox lstbx_sectores) {
    this.lstbx_sectores = lstbx_sectores;
  }

  public Textbox getTxbx_sectorBuscadoPorCodigo() {
    return txbx_sectorBuscadoPorCodigo;
  }

  public void setTxbx_sectorBuscadoPorCodigo(Textbox txbx_sectorBuscadoPorCodigo) {
    this.txbx_sectorBuscadoPorCodigo = txbx_sectorBuscadoPorCodigo;
  }

  public Textbox getTxbx_sectorBuscadoPorDescripcion() {
    return txbx_sectorBuscadoPorDescripcion;
  }

  public void setTxbx_sectorBuscadoPorDescripcion(Textbox txbx_sectorBuscadoPorDescripcion) {
    this.txbx_sectorBuscadoPorDescripcion = txbx_sectorBuscadoPorDescripcion;
  }

  public void actSector(SectorDTO sector) {
    this.listaSectores.remove(sectorIndex(this.sectorSeleccionado));
    this.listaSectores.add(sector);
  }

  private SectorDTO sectorIndex(SectorDTO sectorSeleccionado2) {
    for (SectorDTO sectorActual : this.listaSectores) {
      if (sectorActual.getNombre().trim()
          .equals(sectorSeleccionado2.getNombre().trim())) {
        return sectorActual;
      }
    }
    return null;
  }

  public void onOK$txbx_sectorBuscadoPorCodigo() throws InterruptedException {
    this.buscarSectoresPorCodigo();
  }

  public void onOK$txbx_sectorBuscadoPorDescripcion() throws InterruptedException {
    this.buscarSectoresPorDescripcion();
  }

  public void setResultados(Integer resultados) {
    this.resultados = resultados;
  }

  public Integer getResultados() {
    return resultados;
  }

  public List<SectorDTO> getListaSectores() {
    return listaSectores;
  }

  public void setListaSectores(List<SectorDTO> listaSectores) {
    this.listaSectores = listaSectores;
  }

  public IReparticionEDTService getReparticionService() {
    return reparticionService;
  }

  public void setReparticionService(IReparticionEDTService reparticionService) {
    this.reparticionService = reparticionService;
  }

  public IAdminReparticionService getAdminReparticionService() {
    return adminReparticionService;
  }

  public void setAdminReparticionService(IAdminReparticionService adminReparticionService) {
    this.adminReparticionService = adminReparticionService;
  }
}

final class BusquedaSectorComposerListener implements EventListener {
  private BusquedaSectorComposer composer;

  public BusquedaSectorComposerListener(BusquedaSectorComposer comp) {
    this.composer = comp;
  }

  @Override
  public void onEvent(Event event) throws Exception {
    if (event.getName().equals(Events.ON_USER)) {
      this.composer.buscarSectoresPorCodigo();
      if (event.getData() instanceof SectorDTO) {
        this.composer.actSector((SectorDTO) event.getData());
      }
    }
    if (event.getName().equals(Events.ON_NOTIFY)) {
      composer.binder.loadAll();
    }
  }
}
