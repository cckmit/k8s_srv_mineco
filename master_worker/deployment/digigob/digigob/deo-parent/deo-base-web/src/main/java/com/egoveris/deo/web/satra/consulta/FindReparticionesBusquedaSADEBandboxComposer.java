package com.egoveris.deo.web.satra.consulta;

import com.egoveris.deo.base.service.ObtenerReparticionServices;
import com.egoveris.deo.util.Constantes;
import com.egoveris.sharedorganismo.base.model.ReparticionBean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class FindReparticionesBusquedaSADEBandboxComposer extends GenericForwardComposer {

  /**
  * 
  */
  private static final long serialVersionUID = 7420446970613798790L;
  private Listbox reparticionesBusquedaSADEListbox;
  private Textbox textoReparticionBusquedaSADE;
  private Bandbox reparticionBusquedaSADE;

  @WireVariable("obtenerReparticionServicesImpl")
  private ObtenerReparticionServices obtenerReparticionService;
  private AnnotateDataBinder binder;
  private List<ReparticionBean> listaReparticionSADESeleccionada;
  private ReparticionBean reparticionSeleccionada;

  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    this.binder = new AnnotateDataBinder(reparticionesBusquedaSADEListbox);
    this.listaReparticionSADESeleccionada = new ArrayList<>();
    binder.bindBean("listaReparticionSADESeleccionada", this.listaReparticionSADESeleccionada);
    binder.bindBean("reparticionSeleccionada", this.reparticionSeleccionada);
    binder.loadAll();
  }

  public void onChanging$textoReparticionBusquedaSADE(InputEvent e) {
    String matchingText = e.getValue();
    if (StringUtils.isNotEmpty(matchingText) && (matchingText.length() >= 3)) {
      List<ReparticionBean> listaReparticionesSADECompleta = obtenerReparticionService
          .buscarReparticiones();
      this.listaReparticionSADESeleccionada.clear();
      if (listaReparticionesSADECompleta != null) {
        matchingText = matchingText.toUpperCase();
        Iterator<ReparticionBean> iterator = listaReparticionesSADECompleta.iterator();
        ReparticionBean reparticion = null;
        while ((iterator.hasNext()) && (this.listaReparticionSADESeleccionada
            .size() <= Constantes.MAX_REPARTICION_RESULTS)) {
          reparticion = iterator.next();
          if ((reparticion != null) && (reparticion.getNombre() != null)) {
            if ((reparticion.getNombre().contains(matchingText))
                || (reparticion.getCodigo().contains(matchingText))) {
              listaReparticionSADESeleccionada.add(reparticion);
            }
          }
        }
      }
      this.binder.loadComponent(reparticionesBusquedaSADEListbox);
    }
  }

  public void onBlur$reparticionBusquedaSADE() {
    String value = this.reparticionBusquedaSADE.getValue();
    if (StringUtils.isNotEmpty(value)) {
      if (!(obtenerReparticionService.validarCodigoReparticion(value.trim()))) {
        this.reparticionBusquedaSADE.focus();
        throw new WrongValueException(this.reparticionBusquedaSADE,
            Labels.getLabel("gedo.general.reparticionInvalida"));
      }
      this.reparticionBusquedaSADE.setValue(value.toUpperCase());
    }
  }

  public void onSelect$reparticionesBusquedaSADEListbox() {
    ReparticionBean rsb = this.listaReparticionSADESeleccionada
        .get(reparticionesBusquedaSADEListbox.getSelectedIndex());
    this.reparticionBusquedaSADE.setValue(rsb.getCodigo());
    this.textoReparticionBusquedaSADE.setValue(null);
    this.listaReparticionSADESeleccionada.clear();
    this.binder.loadComponent(this.reparticionBusquedaSADE);
    this.textoReparticionBusquedaSADE.setValue(null);
    reparticionBusquedaSADE.close();
  }
}