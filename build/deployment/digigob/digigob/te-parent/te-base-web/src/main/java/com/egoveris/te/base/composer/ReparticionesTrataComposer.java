package com.egoveris.te.base.composer;

import com.egoveris.sharedorganismo.base.model.ReparticionBean;
import com.egoveris.te.base.model.TrataDTO;
import com.egoveris.te.base.model.TrataReparticionDTO;
import com.egoveris.te.base.rendered.ReparticionesTrataItemRenderer;
import com.egoveris.te.base.service.ObtenerReparticionServices;
import com.egoveris.te.base.service.ReparticionTrataAuditoriaServ;
import com.egoveris.te.base.service.TrataReparticionService;
import com.egoveris.te.base.service.TrataService;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.ConstantesServicios;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;


@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ReparticionesTrataComposer extends AbstractComposer {
  private static final long serialVersionUID = -5399890101217556924L;
  private Bandbox reparticionImportarDocumentoSADE;
  private Listbox reparticionesTrataListBox;
  private Window reparticionesTrataWindow;

  private List<TrataReparticionDTO> reparticionesHabilitadas;
  private TrataDTO trata;
  private AnnotateDataBinder agregarReparticionesComposerBinder;
  
  @WireVariable(ConstantesServicios.OBTENER_REPARTICION_SERVICE)
  private ObtenerReparticionServices obtenerReparticionService;
  
  @WireVariable(ConstantesServicios.TRATA_REPARTICION_SERVICE)
  private TrataReparticionService reparticionTrataService;
  
  @WireVariable(ConstantesServicios.TRATA_AUDITORIA_SERVICE)
  private ReparticionTrataAuditoriaServ reparticionTrataAuditoriaServ;

  @WireVariable(ConstantesServicios.TRATA_SERVICE)
  private TrataService trataService;

  private static transient Logger logger = LoggerFactory
      .getLogger(ReparticionesTrataComposer.class);

  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    this.trata = (TrataDTO) Executions.getCurrent().getArg().get("trata");
    
    this.reparticionesHabilitadas = this.reparticionTrataService
        .cargarReparticionesHabilitadas(trata);
    ReparticionesTrataItemRenderer reparticionesTrataItemRenderer = new ReparticionesTrataItemRenderer(
        this);
    
    this.reparticionesTrataListBox.setItemRenderer(reparticionesTrataItemRenderer);
    this.agregarReparticionesComposerBinder = new AnnotateDataBinder(comp);
    this.agregarReparticionesComposerBinder.loadAll();
  }

  /**
   * Metodo que agrega una reparticion a la lista de reparticiones que tiene la
   * Trata.
   */
  public void onAgregarReparticionSade() {
    String descRepar = (String) this.reparticionImportarDocumentoSADE.getValue();
    if (descRepar == null || descRepar.isEmpty()) {
      throw new WrongValueException(this.reparticionImportarDocumentoSADE,
          "Debe Seleccionar una repartición para agregar");
    }
    for (TrataReparticionDTO reparticionAuxiliar : reparticionesHabilitadas) {
      if (reparticionAuxiliar.getCodigoReparticion().compareTo(descRepar) == 0) {
        throw new WrongValueException(this.reparticionImportarDocumentoSADE,
            Labels.getLabel("ee.nuevoDocumento.reparticionYaAgregada.value"));
      }
    }

    ReparticionBean rp = this.obtenerReparticionService
        .getReparticionBycodigoReparticion(descRepar);

    if (rp == null || rp.getVigenciaHasta() == null || rp.getVigenciaHasta().before(new Date())
        || rp.getVigenciaDesde().after(new Date())) {
      throw new WrongValueException(this.reparticionImportarDocumentoSADE,
          "La repartición no existe o no se encuentra con vigencia activa.");
    }

    TrataReparticionDTO nueva = new TrataReparticionDTO();
    nueva.setCodigoReparticion(descRepar);
    nueva.setHabilitacion(true);
    nueva.setReserva(false);
    nueva.setIdTrata(trata.getId());
    nueva.setOrden(reparticionesHabilitadas.size() + 1);
    this.reparticionesHabilitadas.add(nueva);
    this.reparticionImportarDocumentoSADE.setText("");
    refreshListboxReparticionesAgregadas();

  }

  public void refreshListboxReparticionesAgregadas() {
    this.agregarReparticionesComposerBinder.loadComponent(this.reparticionesTrataListBox);
  }

  public void onClick$cancelar() throws InterruptedException {
    this.reparticionesTrataWindow.detach();
  }

  /**
   * Guarda la información de reparticiones habilitadas.
   * 
   * @throws InterruptedException
   */
  public void onGuardarTrata() throws InterruptedException {
    List<TrataReparticionDTO> reparticionesAGuardar = reparticionesHabilitadas;
    
    // Se fuerza el orden 1 para la reparticion "--TODAS--"
    if (reparticionesHabilitadas != null && !reparticionesHabilitadas.isEmpty() && reparticionesHabilitadas.get(0).getCodigoReparticion().equals(TrataReparticionDTO.TODAS)) {
    	reparticionesHabilitadas.get(0).setOrden(1);
    }
    
    boolean conPermiso = false;
    for (TrataReparticionDTO reparticionesHabilitadas : reparticionesAGuardar) {
      if (reparticionesHabilitadas.getHabilitacion()) { 
        conPermiso = true;
      }
    }
    if (!conPermiso) {
      throw new WrongValueException(this.reparticionesTrataListBox,
          Labels.getLabel("ee.reparticionesTrata.error.conPermiso"));
    }
    try {
      if (!trata.getReparticionesTrata().equals(reparticionesHabilitadas)) {
        String usuario = (String) Executions.getCurrent().getDesktop().getSession()
            .getAttribute(ConstantesWeb.SESSION_USERNAME);
        this.reparticionTrataAuditoriaServ.AuditoriaTrataReparticion(trata, reparticionesAGuardar,
            usuario);
        this.trata.setReparticionesTrata(reparticionesAGuardar);
        this.trataService.modificarTrata(trata, usuario);

      }
    } catch (Exception e) {
      logger.error("Error al almacenar cambios en reparticiones habilitadas", e);
    } finally {
      this.reparticionesTrataWindow.detach();
    }
  }

  public void setReparticionesTrataWindow(Window reparticionesTrataWindow) {
    this.reparticionesTrataWindow = reparticionesTrataWindow;
  }

  public List<TrataReparticionDTO> getReparticionesHabilitadas() {
    return reparticionesHabilitadas;
  }

  public void setReparticionesHabilitadas(List<TrataReparticionDTO> reparticionesHabilitadas) {
    this.reparticionesHabilitadas = reparticionesHabilitadas;
  }
}