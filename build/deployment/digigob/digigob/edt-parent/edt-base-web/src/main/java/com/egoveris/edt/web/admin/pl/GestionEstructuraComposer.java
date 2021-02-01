package com.egoveris.edt.web.admin.pl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
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
import com.egoveris.edt.base.service.estructura.IEstructuraHelper;
import com.egoveris.edt.base.service.estructura.IEstructuraService;
import com.egoveris.sharedsecurity.base.model.ConstantesSesion;
import com.egoveris.sharedsecurity.base.model.EstructuraDTO;
import com.egoveris.sharedsecurity.base.service.IReparticionEDTService;
import com.egoveris.sharedsecurity.conf.service.Utilitarios;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class GestionEstructuraComposer extends GenericForwardComposer {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -3653535674201948178L;
  
	/** The logger. */
	private static Logger logger = LoggerFactory.getLogger(GestionEstructuraComposer.class);

  /** The Constant WIN_ADM_ESTRUCTURA. */
  private static final String WIN_ADM_ESTRUCTURA = "win_AdmEstructura";
  
  private EstructuraDTO selectedEstructuraBorrar;
  @Autowired
  protected AnnotateDataBinder binder;
  private List<EstructuraDTO> listaResultadoEstructuras;
  private Integer resultados;
  private EstructuraDTO selectedEstructura;

  @Autowired
  private Textbox txbx_estructuraBuscada;
  
  /** The estructura service. */
  @WireVariable("estructuraService")
  private IEstructuraService estructuraService;

  /** The reparticion service. */
  @WireVariable("reparticionEDTService")
  private IReparticionEDTService reparticionService;
  
  // lista que viene del o
  private Listbox lstbx_estructura;

  private IEstructuraHelper estructuraHelper;

  private Boolean busqueda;

  @Override
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    this.binder = new AnnotateDataBinder(comp);
    
    selectedEstructuraBorrar = new EstructuraDTO();
    selectedEstructura = new EstructuraDTO();
    listaResultadoEstructuras = new ArrayList<>();
    listaResultadoEstructuras = estructuraService.listEstructuras();
    resultados = listaResultadoEstructuras.size();
    
    lstbx_estructura.setModel(new BindingListModelList(listaResultadoEstructuras, true));
    this.binder.loadAll();
    
    comp.addEventListener(Events.ON_USER, new GestionEstructuraComposerListener(this));
    comp.addEventListener(Events.ON_NOTIFY, new GestionEstructuraComposerListener(this));
  }

  public void onClick$tbbtn_altaReparticion() throws InterruptedException {
    try {
  		Utilitarios.closePopUps(WIN_ADM_ESTRUCTURA);
      Map<String, Object> parametros = new HashMap<>();
      parametros.put(ConstantesSesion.LISTAREFRESCARESTRUCTURA, listaResultadoEstructuras);
      parametros.put(ConstantesSesion.COMPONENTE, lstbx_estructura);
      Window window = (Window) Executions.createComponents("/administrator/agregarEstructura.zul",
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
    busquedaEstructuras();
  }

  private void busquedaEstructuras() throws InterruptedException {
    if (txbx_estructuraBuscada.getValue().length() >= 2) {
      buscarEstructuras();
    } else {
      throw new WrongValueException(txbx_estructuraBuscada,
          Labels.getLabel("eu.adminSade.validacion.minimoBusqueda"));
    }
  }

  public void buscarEstructuras() throws InterruptedException {
    try {
      if (!txbx_estructuraBuscada.getValue().isEmpty()) {
        listaResultadoEstructuras = estructuraService
            .buscarEstructuraByCodigoYNombreComodin(txbx_estructuraBuscada.getValue());
        resultados = listaResultadoEstructuras.size();
        busqueda = true;
        this.binder.loadAll();
      }
    } catch (NegocioException e) {
      logger.error(e.getMessage(), e);
      Messagebox.show(e.getMessage(), Labels.getLabel("eu.adminSade.usuario.generales.error"),
          Messagebox.OK, Messagebox.ERROR);
    }
  }

  public void onVisualizarEstructura() throws InterruptedException {
    try {
  		Utilitarios.closePopUps(WIN_ADM_ESTRUCTURA);
      Map<String, Object> parametros = new HashMap<>();
      parametros.put(ConstantesSesion.KEY_ESTRUCTURA, selectedEstructura);
      parametros.put(ConstantesSesion.KEY_VISUALIZAR, true);
      Window win = (Window) Executions.createComponents("/administrator/agregarEstructura.zul", this.self,
          parametros);
      win.setMode("modal");
      win.setClosable(true);
      win.setTitle(Labels.getLabel("eu.gestionEstructurasComposer.winTitle.visEstruc"));
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

  public void onModificarEstructura() throws InterruptedException {
    try {
  		Utilitarios.closePopUps(WIN_ADM_ESTRUCTURA);
      Map<String, Object> parametros = new HashMap<>();
      parametros.put(ConstantesSesion.KEY_ESTRUCTURA_MODIFICAR, selectedEstructura);
      parametros.put(ConstantesSesion.KEY_MODIFICAR, true);
      Window win = (Window) Executions.createComponents("/administrator/agregarEstructura.zul", this.self,
          parametros);
      win.setMode("modal");
      win.setClosable(true);
      win.setTitle(Labels.getLabel("eu.gestionEstructurasComposer.winTitle.modEstruc"));
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

  public void onBorrarEstructura() throws InterruptedException {
  	if (CollectionUtils.isEmpty(reparticionService.getOrganismoByEstructura(selectedEstructura.getId()))) {
      Messagebox.show(Labels.getLabel("mensaje_confirmacion.borrar_Estructura"),
          Labels.getLabel("eu.adminSade.usuario.generales.confirmacion"), Messagebox.YES | Messagebox.NO,
          Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener<Event>() {
            @Override
            public void onEvent(Event evt) throws InterruptedException {
              switch (((Integer) evt.getData()).intValue()) {
              case Messagebox.YES:
                eliminarEstructura();
                break;
              case Messagebox.NO:
                break;
              }
            }
          });
		} else {
			Messagebox.show(Labels.getLabel("eu.adminSade.validacion.estructura.reparticion"),
					Labels.getLabel("eu.general.information"), Messagebox.OK, Messagebox.INFORMATION);
		}
  }

	/**
	 * Eliminar estructura.
	 */
	private void eliminarEstructura() {
		selectedEstructura.setEstadoRegistro(false);
		estructuraService.guardarEstructura(selectedEstructura);
		refrescarListado();
	}

  /**
   * Refrescar listado.
   */
  public void refrescarListado() {
    if (Boolean.TRUE.equals(busqueda)) {
      listaResultadoEstructuras = estructuraService
          .buscarEstructuraByCodigoYNombreComodin(txbx_estructuraBuscada.getValue());
    } else {
      listaResultadoEstructuras = estructuraService.listEstructuras();
    }
    resultados = listaResultadoEstructuras.size();
    binder.loadAll();
  }

  public void actEstructura(EstructuraDTO estructura) {
    this.listaResultadoEstructuras.remove(estructuraIndex(this.selectedEstructura));
    this.listaResultadoEstructuras.add(estructura);
  }

  private EstructuraDTO estructuraIndex(EstructuraDTO selectedEstructura2) {
    for (EstructuraDTO estructura : this.listaResultadoEstructuras) {
      if (estructura.getNombreEstructura().trim()
          .equals(selectedEstructura2.getNombreEstructura().trim())) {
        return estructura;
      }
    }
    return null;
  }

  public void onOK$txbx_estructuraBuscada() throws InterruptedException {
    busquedaEstructuras();
  }

  public void setResultados(Integer resultados) {
    this.resultados = resultados;
  }

  public Integer getResultados() {
    return resultados;
  }

  public List<EstructuraDTO> getListaResultadoEstructuras() {
    return listaResultadoEstructuras;
  }

  public void setListaResultadoEstructuras(List<EstructuraDTO> listaResultadoEstructuras) {
    this.listaResultadoEstructuras = listaResultadoEstructuras;
  }

  public EstructuraDTO getSelectedEstructura() {
    return selectedEstructura;
  }

  public void setSelectedEstructura(EstructuraDTO selectedEstructura) {
    this.selectedEstructura = selectedEstructura;
  }

}

final class GestionEstructuraComposerListener implements EventListener {
  private GestionEstructuraComposer composer;

  public GestionEstructuraComposerListener(GestionEstructuraComposer comp) {
    this.composer = comp;
  }

  @Override
  public void onEvent(Event event) throws Exception {
    if (event.getName().equals(Events.ON_USER)) {
      this.composer.buscarEstructuras();
    }
    if (event.getName().equals(Events.ON_NOTIFY)) {
      composer.binder.loadAll();
    }
  }

}
