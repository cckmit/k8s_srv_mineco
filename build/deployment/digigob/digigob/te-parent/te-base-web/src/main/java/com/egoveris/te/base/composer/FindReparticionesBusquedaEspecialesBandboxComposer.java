package com.egoveris.te.base.composer;

import com.egoveris.sharedorganismo.base.model.ReparticionBean;
import com.egoveris.sharedorganismo.base.service.ReparticionServ;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.TruncatedResults;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
public class FindReparticionesBusquedaEspecialesBandboxComposer extends GenericForwardComposer
    implements TruncatedResults {

  /**
  * 
  */
  private static final long serialVersionUID = 7420446970613798790L;
  @Autowired
  private Listbox reparticionesBusquedaEspecialesListbox;
  @Autowired
  private Textbox textoReparticionBusquedaEspeciales;
  @Autowired
  private Bandbox reparticionBusquedaEspeciales; 
  @Autowired
  private AnnotateDataBinder binder;
  private List<ReparticionBean> listaReparticionSADESeleccionada;
  private List<ReparticionBean> listaReparticionSeleccionada;
  private ReparticionBean reparticionSeleccionada;

  @WireVariable(ConstantesServicios.REPARTICION_SERVICE)
  private ReparticionServ reparticionServ;
  
  
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));

    this.binder = new AnnotateDataBinder(reparticionesBusquedaEspecialesListbox);

    binder.bindBean("reparticionSeleccionada", this.reparticionSeleccionada);
    binder.loadAll();


    this.listaReparticionSeleccionada = new ArrayList<ReparticionBean>();
    binder.bindBean("listaReparticionSADESeleccionada", this.listaReparticionSeleccionada);
  }

  public void onChanging$textoReparticionBusquedaEspeciales(InputEvent e) {
    String matchingText = e.getValue();
    if (StringUtils.isNotEmpty(matchingText) && (matchingText.length() >= 3)) {
      List<ReparticionBean> listaReparticionesCompleta = this.reparticionServ
          .buscarTodasLasReparticiones();
      this.listaReparticionSeleccionada.clear();
      if (listaReparticionesCompleta != null) {
        matchingText = matchingText.toUpperCase();
        Iterator<ReparticionBean> iterator = listaReparticionesCompleta.iterator();
        ReparticionBean reparticion;
        while ((iterator.hasNext())
            && (this.listaReparticionSeleccionada.size() <= ConstantesWeb.MAX_REPARTICION_RESULTS)) {
          reparticion = iterator.next();
          if ((reparticion != null) && (StringUtils.isNotEmpty(reparticion.getNombre())
              && (StringUtils.isNotEmpty(reparticion.getCodigo())))) {
            if ((reparticion.getNombre().contains(matchingText))
                || (reparticion.getCodigo().contains(matchingText))) {
              listaReparticionSeleccionada.add(reparticion);
            }
          }
        }
      }
      this.binder.loadComponent(reparticionesBusquedaEspecialesListbox);
    }
  }

  public void onBlur$reparticionBusquedaEspeciales() {
    String value = this.reparticionBusquedaEspeciales.getValue();
    if (StringUtils.isNotEmpty(value)) {
      if (!(this.reparticionServ.validarCodigoReparticion(value.trim()))) {
        this.reparticionBusquedaEspeciales.focus();
        throw new WrongValueException(this.reparticionBusquedaEspeciales,
            Labels.getLabel("gedo.general.reparticionInvalida"));
      }
      this.reparticionBusquedaEspeciales.setValue(value.toUpperCase());
    }
  }

  public void onSelect$reparticionesBusquedaEspecialesListbox() {
    ReparticionBean rsb = this.listaReparticionSeleccionada
        .get(reparticionesBusquedaEspecialesListbox.getSelectedIndex());
    this.reparticionBusquedaEspeciales.setValue(rsb.getCodigo());
    this.textoReparticionBusquedaEspeciales.setValue(null);
    this.listaReparticionSeleccionada.clear();
    this.binder.loadComponent(this.reparticionBusquedaEspeciales);
    this.textoReparticionBusquedaEspeciales.setValue(null);
    reparticionBusquedaEspeciales.close();
  }

  public void setListaReparticionSADESeleccionada(
      List<ReparticionBean> listaReparticionSADESeleccionada) {
    this.listaReparticionSADESeleccionada = listaReparticionSADESeleccionada;
  }

  public List<ReparticionBean> getListaReparticionSADESeleccionada() {
    return listaReparticionSADESeleccionada;
  }

  
}