package com.egoveris.edt.web.admin.pl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.egoveris.edt.base.exception.NegocioException;
import com.egoveris.edt.base.model.eu.actuacion.ActuacionDTO;
import com.egoveris.edt.base.service.actuacion.IActuacionHelper;
import com.egoveris.edt.base.service.actuacion.IActuacionService;
import com.egoveris.sharedsecurity.base.model.ConstantesSesion;
import com.egoveris.sharedsecurity.conf.service.Utilitarios;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class GestionActuacionComposer extends GenericForwardComposer {

	/** The Constant serialVersionUID. */
  private static final long serialVersionUID = -3653535674201948178L;
  
	/** The logger. */
	private static Logger logger = LoggerFactory.getLogger(GestionActuacionComposer.class);

  /** The Constant WIN_ADM_ACTUACION. */
  private static final String WIN_ADM_ACTUACION = "win_AdmActuacion";
  
  private ActuacionDTO selectedActuacionBorrar;
  @Autowired
  protected AnnotateDataBinder binder;
  private List<ActuacionDTO> listaResultadoActuaciones;
  private Integer resultados;
  private ActuacionDTO selectedActuacion;

  // @Autowired
  private Textbox txbx_actuacionBuscada;
  
  @WireVariable("actuacionService")
  private IActuacionService actuacionService;

  // lista que viene del o
  private Listbox lstbx_actuacion;

  IActuacionHelper actuacionHelper;

  @Override
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    this.binder = new AnnotateDataBinder(comp);
    
    selectedActuacionBorrar = new ActuacionDTO();
    selectedActuacion = new ActuacionDTO();
    listaResultadoActuaciones = new ArrayList<>();
    listaResultadoActuaciones = actuacionService.listActuacion();
    resultados = listaResultadoActuaciones.size();
    
    lstbx_actuacion.setModel(new BindingListModelList(listaResultadoActuaciones, true));
    this.binder.loadAll();

    comp.addEventListener(Events.ON_USER, new GestionActuacionComposerListener(this));
    comp.addEventListener(Events.ON_NOTIFY, new GestionActuacionComposerListener(this));
  }

  public void onClick$tbbtn_altaActuacion() throws InterruptedException {
    try {
  		Utilitarios.closePopUps(WIN_ADM_ACTUACION);
      Map<String, Object> parametros = new HashMap<>();
      parametros.put(ConstantesSesion.LISTAREFRESCARACTUACION, listaResultadoActuaciones);
      parametros.put(ConstantesSesion.COMPONENTE_ACTUACION, lstbx_actuacion);
      Window window = (Window) Executions.createComponents(ABMActuacionComposer.AGREGAR_ACTUACION_ZUL,
          this.self, parametros);
      window.setPosition("center");
      window.setVisible(true);
      window.doModal();

    } catch (SuspendNotAllowedException e) {
      logger.error(e.getMessage(), e);
      Messagebox.show(e.getMessage(), Labels.getLabel("eu.adminSade.usuario.generales.error"),
          Messagebox.OK, Messagebox.ERROR);
    }
  }

  public void onClick$btn_buscar() throws InterruptedException {
    busquedaActuaciones();
  }

  private void busquedaActuaciones() throws InterruptedException {
    if (txbx_actuacionBuscada.getValue().length() >= 2) {
      buscarActuaciones();
    } else {
      throw new WrongValueException(txbx_actuacionBuscada,
          Labels.getLabel("eu.adminSade.validacion.minimoBusqueda"));
    }
  }

  public void buscarActuaciones() throws InterruptedException {
    try {
      if (!txbx_actuacionBuscada.getValue().isEmpty()) {
        listaResultadoActuaciones = actuacionService
            .buscarActuacionByCodigoYNombreComodin(txbx_actuacionBuscada.getValue());
        resultados = listaResultadoActuaciones.size();
        this.binder.loadAll();
      }
    } catch (NegocioException e) {
      logger.error(e.getMessage(), e);
      Messagebox.show(e.getMessage(), Labels.getLabel("eu.adminSade.usuario.generales.error"),
          Messagebox.OK, Messagebox.ERROR);
    }
  }

  public void onVisualizarActuacion() throws InterruptedException {
    try {
  		Utilitarios.closePopUps(WIN_ADM_ACTUACION);
      Map<String, Object> parametros = new HashMap<>();
      parametros.put(ConstantesSesion.KEY_ACTUACION, selectedActuacion);
      parametros.put(ConstantesSesion.KEY_VISUALIZAR_ACTUACION, true);
      Window win = (Window) Executions.createComponents(ABMActuacionComposer.AGREGAR_ACTUACION_ZUL, this.self,
          parametros);
      win.setMode("modal");
      win.setClosable(true);
      win.setTitle(Labels.getLabel("eu.gestionActuacionComp.winTitle.visActuacion"));
      win.setWidth("500px");
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

  public void onModificarActuacion() throws InterruptedException {
    try {
  		Utilitarios.closePopUps(WIN_ADM_ACTUACION);
      Map<String, Object> parametros = new HashMap<>();
      parametros.put(ConstantesSesion.KEY_ACTUACION_MODIFICAR, selectedActuacion);
      parametros.put(ConstantesSesion.KEY_MODIFICAR_ACTUACION, true);
      Window win = (Window) Executions.createComponents(ABMActuacionComposer.AGREGAR_ACTUACION_ZUL, this.self,
          parametros);
      win.setMode("modal");
      win.setClosable(true);
      win.setTitle(Labels.getLabel("eu.gestionActuacionComp.winTitle.modActuacion"));
      win.setWidth("700px");
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
   * On borrar actuacion.
   *
   * @throws InterruptedException the interrupted exception
   */
  public void onBorrarActuacion() throws InterruptedException {
  	if (actuacionService.existeDocumentosAsociados(selectedActuacion.getId())) {
  		Messagebox.show(Labels.getLabel("eu.adminSade.validacion.usada"),
					Labels.getLabel("eu.adminSade.usuario.generales.error"), Messagebox.OK, Messagebox.ERROR);
  	} else {
  		deletedAction();
  	}
  }

	/**
	 * Deleted action.
	 */
	private void deletedAction() {
		try {
  		Utilitarios.closePopUps(WIN_ADM_ACTUACION);
      Map<String, Object> parametros = new HashMap<>();
      parametros.put(ConstantesSesion.KEY_ACTUACION_BORRAR, selectedActuacion);
      parametros.put(ConstantesSesion.KEY_BORRAR_ACTUACION, true);
      Window win = (Window) Executions.createComponents(ABMActuacionComposer.AGREGAR_ACTUACION_ZUL, this.self,
          parametros);
      win.setMode("modal");
      win.setClosable(true);
      win.setTitle(Labels.getLabel("eu.adminSade.usuario.generales.confirmacion"));
      win.setWidth("700px");
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

  public void actActuacion(ActuacionDTO actuacion) {
    this.listaResultadoActuaciones.remove(estructuraIndex(this.selectedActuacion));
    this.listaResultadoActuaciones.add(actuacion);
  }

  private ActuacionDTO estructuraIndex(ActuacionDTO selectedActuacion2) {
    for (ActuacionDTO actuacion : this.listaResultadoActuaciones) {
      if (actuacion.getNombreActuacion().trim()
          .equals(selectedActuacion2.getNombreActuacion().trim())) {
        return actuacion;
      }
    }
    return null;
  }

  public void onOK$txbx_actuacionBuscada() throws InterruptedException {
    busquedaActuaciones();
  }

  public void setResultados(Integer resultados) {
    this.resultados = resultados;
  }

  public Integer getResultados() {
    return resultados;
  }

  public List<ActuacionDTO> getListaResultadoEstructuras() {
    return listaResultadoActuaciones;
  }

  public void setListaResultadoEstructuras(List<ActuacionDTO> listaResultadoEstructuras) {
    this.listaResultadoActuaciones = listaResultadoEstructuras;
  }

  public ActuacionDTO getSelectedEstructura() {
    return selectedActuacion;
  }

  public void setSelectedEstructura(ActuacionDTO selectedEstructura) {
    this.selectedActuacion = selectedEstructura;
  }

  public ActuacionDTO getSelectedActuacionBorrar() {
    return selectedActuacionBorrar;
  }

  public void setSelectedActuacionBorrar(ActuacionDTO selectedActuacionBorrar) {
    this.selectedActuacionBorrar = selectedActuacionBorrar;
  }

  public List<ActuacionDTO> getListaResultadoActuaciones() {
    return listaResultadoActuaciones;
  }

  public void setListaResultadoActuaciones(List<ActuacionDTO> listaResultadoActuaciones) {
    this.listaResultadoActuaciones = listaResultadoActuaciones;
  }

  public ActuacionDTO getSelectedActuacion() {
    return selectedActuacion;
  }

  public void setSelectedActuacion(ActuacionDTO selectedActuacion) {
    this.selectedActuacion = selectedActuacion;
  }

  public Textbox getTxbx_actuacionBuscada() {
    return txbx_actuacionBuscada;
  }

  public void setTxbx_actuacionBuscada(Textbox txbx_actuacionBuscada) {
    this.txbx_actuacionBuscada = txbx_actuacionBuscada;
  }

  public Listbox getLstbx_actuacion() {
    return lstbx_actuacion;
  }

  public void setLstbx_actuacion(Listbox lstbx_actuacion) {
    this.lstbx_actuacion = lstbx_actuacion;
  }

}

final class GestionActuacionComposerListener implements EventListener {
  private GestionActuacionComposer composer;

  public GestionActuacionComposerListener(GestionActuacionComposer comp) {
    this.composer = comp;
  }

  @Override
  public void onEvent(Event event) throws Exception {
    if (event.getName().equals(Events.ON_USER)) {
      this.composer.buscarActuaciones();
    }
    if (event.getName().equals(Events.ON_NOTIFY)) {
      composer.binder.loadAll();
    }
  }

}
