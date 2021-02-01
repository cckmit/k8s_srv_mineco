package com.egoveris.deo.web.satra.produccion;

import com.egoveris.deo.base.service.ObtenerReparticionServices;
import com.egoveris.deo.base.service.ReparticionHabilitadaService;
import com.egoveris.deo.base.service.TipoDocumentoService;
import com.egoveris.deo.base.util.UtilsDate;
import com.egoveris.deo.model.model.NumeracionEspecialDTO;
import com.egoveris.deo.model.model.ReparticionHabilitadaDTO;
import com.egoveris.deo.model.model.TipoDocumentoDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.deo.web.satra.GEDOGenericForwardComposer;
import com.egoveris.deo.web.satra.renderers.ReparticionesItemRenderer;
import com.egoveris.sharedorganismo.base.model.ReparticionBean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

/**
 * @author pfolgar
 * 
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class AgregarReparticionesComposer extends GEDOGenericForwardComposer {

  /**
   * FIXME MULTIREPARTICION
   */
  private static final long serialVersionUID = -5399890101217556924L;
  /**
  * 
  */
  private Bandbox reparticionImportarDocumentoSADE;
  private Listbox reparticionesAgregadosListBox;
  private Radio reparticionesTodas;
  private Radio reparticionesSeleccionadas;
  private Window agregarReparticionesWindow;
  private Toolbarbutton guardar;

  @WireVariable("tipoDocumentoServiceImpl")
  private TipoDocumentoService tipoDocumentoService;
  private List<ReparticionBean> listaReparticiones;
  private Set<ReparticionHabilitadaDTO> reparticionesHabilitadas;
  private Set<ReparticionHabilitadaDTO> auxReparticionesHabilitadas;

  private ReparticionHabilitadaDTO reparticionSeleccionada;
  private TipoDocumentoDTO tipoDocumento;
  private Boolean habilitar = false;
  public AnnotateDataBinder agregarReparticionesComposerBinder;

  @WireVariable("obtenerReparticionServicesImpl")
  private ObtenerReparticionServices obtenerReparticionService;
  @WireVariable("reparticionHabilitadaServiceImpl")
  private ReparticionHabilitadaService reparticionHabilitadaService;
  private List<ReparticionHabilitadaDTO> aux;
  private List<String> reparticionesIncompletas;

  private static transient Logger logger = LoggerFactory
      .getLogger(AgregarReparticionesComposer.class);

  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    Execution exec = Executions.getCurrent();
    Map map = exec.getArg();
    tipoDocumento = (TipoDocumentoDTO) map.get("tipoDocumento");
    this.reparticionesIncompletas = new ArrayList<String>();
    tipoDocumento.setListaReparticiones((Set<ReparticionHabilitadaDTO>) this.tipoDocumentoService
        .cargarReparticionesHabilitadas(tipoDocumento));
    this.reparticionesHabilitadas = this.reparticionHabilitadaService
        .cargarReparticionesHabilitadas(tipoDocumento);
    this.auxReparticionesHabilitadas = new HashSet<ReparticionHabilitadaDTO>(
        this.reparticionesHabilitadas);
    ReparticionesItemRenderer reparticionesItemRenderer = new ReparticionesItemRenderer(this);
    this.reparticionesAgregadosListBox.setItemRenderer(reparticionesItemRenderer);
    this.agregarReparticionesComposerBinder = new AnnotateDataBinder(comp);
    this.agregarReparticionesComposerBinder.loadAll();
  }

  /**
   * Adicionar una repartición a la lista de habilitadas
   */
  public void onAgregarReparticionSade() {
    if ((this.reparticionImportarDocumentoSADE == null)
        || StringUtils.isEmpty(reparticionImportarDocumentoSADE.getValue())) {
      throw new WrongValueException(this.reparticionImportarDocumentoSADE,
          Labels.getLabel("gedo.agregarReparticiones.exception.SeleccionOrganismo"));
    }
    String descRepar = (String) this.reparticionImportarDocumentoSADE.getValue();
    ReparticionBean reparticion = this.obtenerReparticionService
        .getReparticionBycodigoReparticion(descRepar);
    ReparticionHabilitadaDTO reparticionTodas = null;
    // Validación de que la repartición ya no esté agregada
    for (ReparticionHabilitadaDTO reparticionHabilitada : reparticionesHabilitadas) {
      if (reparticionHabilitada.getCodigoReparticion().trim()
          .compareTo(reparticion.getCodigo().trim()) == 0) {
        throw new WrongValueException(this.reparticionImportarDocumentoSADE,
            Labels.getLabel("gedo.nuevoDocumento.reparticionYaAgregada"));
      }
      if (reparticionHabilitada.getCodigoReparticion()
          .compareTo(Constantes.TODAS_REPARTICIONES_HABILITADAS) == 0) {
        reparticionTodas = reparticionHabilitada;
      }
    }
    ReparticionHabilitadaDTO reparticionHabilitada = new ReparticionHabilitadaDTO();
    reparticionHabilitada.setCodigoReparticion(reparticion.getCodigo());
    reparticionHabilitada.setTipoDocumento(tipoDocumento);
    reparticionHabilitada.setPermisoIniciar(true);
    reparticionHabilitada.setPermisoFirmar(true);
    reparticionHabilitada.setEstado(true);
    reparticionHabilitada.setEdicionNumeracionEspecial(tipoDocumento.getEsEspecial());
    if (reparticionHabilitada.getNumeracionEspecial() == null) {
      reparticionHabilitada.setNumeracionEspecial(new NumeracionEspecialDTO());
      reparticionHabilitada.getNumeracionEspecial().setAnio(UtilsDate.obtenerAnioActual());
    }
    this.reparticionesHabilitadas.add(reparticionHabilitada);
    if (reparticionTodas != null) {
      reparticionTodas.setPermisoIniciar(false);
      reparticionTodas.setPermisoFirmar(false);
    }
    this.reparticionImportarDocumentoSADE.setValue(null);
    refreshListboxReparticionesAgregadas();
  }

  public void refreshListboxReparticionesAgregadas() {
    this.agregarReparticionesComposerBinder.loadComponent(this.reparticionesAgregadosListBox);
  }

  public Bandbox getReparticionImportarDocumentoSADE() {
    return reparticionImportarDocumentoSADE;
  }

  public void setReparticionImportarDocumentoSADE(Bandbox reparticionImportarDocumentoSADE) {
    this.reparticionImportarDocumentoSADE = reparticionImportarDocumentoSADE;
  }

  public Listbox getReparticionesAgregadosListBox() {
    return reparticionesAgregadosListBox;
  }

  public void setReparticionesAgregadosListBox(Listbox reparticionesAgregadosListBox) {
    this.reparticionesAgregadosListBox = reparticionesAgregadosListBox;
  }

  /**
   * Cargar la información de reparticiones
   * 
   * @return
   */
  public List<ReparticionBean> getListaReparticiones() {
    return listaReparticiones;
  }

  /**
   * Cancela el proceso de edición de reparticiones habilitadas.
   * 
   * @throws InterruptedException
   */
  public void onCancelar() throws InterruptedException {
    this.agregarReparticionesWindow.detach();
  }

  /**
   * Guarda la información de reparticiones habilitadas.
   * 
   * @throws InterruptedException
   */
  public void onGuardarTipoDocumento() throws InterruptedException {

    Set<ReparticionHabilitadaDTO> reparticionesAGuardar = reparticionesHabilitadas;
    boolean permisosFirma = false;
    boolean permisosInicio = false;

    for (ReparticionHabilitadaDTO reparticionHabilitada : reparticionesAGuardar) {
      if (reparticionHabilitada.getPermisoIniciar() && reparticionHabilitada.getEstado()) {
        permisosInicio = true;
      }
      if (reparticionHabilitada.getPermisoFirmar() && reparticionHabilitada.getEstado()) {
        permisosFirma = true;
      } else {
        reparticionHabilitada.setEdicionNumeracionEspecial(Boolean.FALSE);
      }
      if (reparticionHabilitada.getEdicionNumeracionEspecial()) {
        if (reparticionHabilitada.getNumeracionEspecial().getAnio()
            .compareTo(UtilsDate.obtenerAnioActual()) < 0) {
          throw new WrongValueException(this.reparticionesAgregadosListBox,
              Labels.getLabel("gedo.reparticionesHabilitadas.error.anioInvalido"));
        }
        if (reparticionHabilitada.getNumeracionEspecial().getNumero() == null) {
          throw new WrongValueException(this.reparticionesAgregadosListBox,
              Labels.getLabel("gedo.reparticionesHabilitadas.error.anioInvalido"));
        }
      }
    }
    if (!permisosInicio || !permisosFirma) {
      throw new WrongValueException(this.reparticionesAgregadosListBox,
          Labels.getLabel("gedo.reparticionesHabilitadas.error.ningunPermiso"));
    }
    try {
      this.tipoDocumento
          .setListaReparticiones((Set<ReparticionHabilitadaDTO>) reparticionesAGuardar);
      String userName = (String) Executions.getCurrent().getDesktop().getSession()
          .getAttribute(Constantes.SESSION_USERNAME);
      tipoDocumentoService.modificarTipoDocumentoReparticiones(this.tipoDocumento, userName);
      Messagebox.show(
          Labels.getLabel("gedo.detalleDocumento.tipoDocumentoModificado",
              new String[] { this.tipoDocumento.getNombre() }),
          Labels.getLabel("gedo.general.information"), Messagebox.OK, Messagebox.INFORMATION);
    } catch (Exception e) {
      logger.error("Error al almacenar cambios en organismos habilitados", e);
      Messagebox.show(
          Labels.getLabel("gedo.reparticionesHabilitadas.error.guardar",
              new String[] { this.tipoDocumento.getNombre() }),
          Labels.getLabel("gedo.general.error"), Messagebox.OK, Messagebox.ERROR);
    } finally {
      // this.closeAndNotifyAssociatedWindow(null);
      this.agregarReparticionesWindow.detach();
    }
  }

  public Set<ReparticionHabilitadaDTO> getAuxReparticionesHabilitadas() {
    return auxReparticionesHabilitadas;
  }

  public void setAuxReparticionesHabilitadas(
      Set<ReparticionHabilitadaDTO> auxReparticionesHabilitadas) {
    this.auxReparticionesHabilitadas = auxReparticionesHabilitadas;
  }

  public void setListaReparticiones(List<ReparticionBean> listaReparticiones) {
    this.listaReparticiones = listaReparticiones;
  }

  public Radio getReparticionesTodas() {
    return reparticionesTodas;
  }

  public void setReparticionesTodas(Radio reparticionesTodas) {
    this.reparticionesTodas = reparticionesTodas;
  }

  public Radio getReparticionesSeleccionadas() {
    return reparticionesSeleccionadas;
  }

  public void setReparticionesSeleccionadas(Radio reparticionesSeleccionadas) {
    this.reparticionesSeleccionadas = reparticionesSeleccionadas;
  }

  public TipoDocumentoService getTipoDocumentoService() {
    return tipoDocumentoService;
  }

  public AnnotateDataBinder getAgregarReparticionesComposerBinder() {
    return agregarReparticionesComposerBinder;
  }

  public void setAgregarReparticionesComposerBinder(
      AnnotateDataBinder agregarReparticionesComposerBinder) {
    this.agregarReparticionesComposerBinder = agregarReparticionesComposerBinder;
  }

  public ObtenerReparticionServices getObtenerReparticionService() {
    return obtenerReparticionService;
  }

  public Boolean getHabilitar() {
    return habilitar;
  }

  public void setHabilitar(Boolean habilitar) {
    this.habilitar = habilitar;
  }

  public Window getAgregarReparticionesWindow() {
    return agregarReparticionesWindow;
  }

  public TipoDocumentoDTO getTipoDocumento() {
    return tipoDocumento;
  }

  public void setTipoDocumento(TipoDocumentoDTO tipoDocumento) {
    this.tipoDocumento = tipoDocumento;
  }

  public void setAgregarReparticionesWindow(Window agregarReparticionesWindow) {
    this.agregarReparticionesWindow = agregarReparticionesWindow;
  }

  public Set<ReparticionHabilitadaDTO> getReparticionesHabilitadas() {
    return reparticionesHabilitadas;
  }

  public void setReparticionesHabilitadas(Set<ReparticionHabilitadaDTO> reparticionesHabilitadas) {
    this.reparticionesHabilitadas = reparticionesHabilitadas;
  }

  public ReparticionHabilitadaDTO getReparticionSeleccionada() {
    return reparticionSeleccionada;
  }

  public void setReparticionSeleccionada(ReparticionHabilitadaDTO reparticionSeleccionada) {
    this.reparticionSeleccionada = reparticionSeleccionada;
  }

  /**
   * Presenta mensaje de error en la actualización del año, cuando el tipo de
   * documento tiene numeración especial.
   */
  public void validacionAnio() {
    throw new WrongValueException(this.reparticionesAgregadosListBox,
        Labels.getLabel("gedo.reparticionesHabilitadas.error.anioInvalido"));
  }

  public List<ReparticionHabilitadaDTO> getAux() {
    return aux;
  }

  public void setAux(List<ReparticionHabilitadaDTO> aux) {
    this.aux = aux;
  }

  public Toolbarbutton getGuardar() {
    return guardar;
  }

  public void setGuardar(Toolbarbutton guardar) {
    this.guardar = guardar;
  }

  public List<String> getReparticionesIncompletas() {
    return reparticionesIncompletas;
  }

  public void setReparticionesIncompletas(List<String> reparticionesIncompletas) {
    this.reparticionesIncompletas = reparticionesIncompletas;
  }
}