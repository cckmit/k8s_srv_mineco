package com.egoveris.te.base.composer;

import com.egoveris.sharedorganismo.base.model.ReparticionBean;
import com.egoveris.te.base.util.ConstantesWeb;

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
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class FindReparticionesBusquedaUsuarioBandboxComposer
    extends AbstracFindReparticionesBusquedaBandboxComposer {
  /**
  * 
  */
  private static final long serialVersionUID = 7420446970613798790L;
  @Autowired
  private Listbox reparticionesBusquedaUsuarioListbox;
  @Autowired
  private Textbox textoReparticionBusquedaUsuario;
  @Autowired
  private Bandbox reparticionBusquedaUsuario;

  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    this.binder = new AnnotateDataBinder(reparticionesBusquedaUsuarioListbox);
    this.listaReparticionSeleccionada = new ArrayList<ReparticionBean>();
    binder.bindBean("listaReparticionSADESeleccionada", this.listaReparticionSADESeleccionada);
    binder.bindBean("reparticionSeleccionada", this.reparticionSeleccionada);
    binder.loadAll();


    this.listaReparticionSeleccionada = new ArrayList<ReparticionBean>();
    binder.bindBean("listaReparticionSADESeleccionada", this.listaReparticionSeleccionada);

  }

  public void onChanging$textoReparticionBusquedaUsuario(InputEvent e) {
    String matchingText = e.getValue();
    if (StringUtils.isNotEmpty(matchingText) && (matchingText.length() >= 3)) {
      List<ReparticionBean> listaReparticionesCompleta = this.reparticionServ
          .buscarReparticionesVigentesActivas();
      this.listaReparticionSeleccionada.clear();
      if (listaReparticionesCompleta != null) {
        matchingText = matchingText.toUpperCase();
        Iterator<ReparticionBean> iterator = listaReparticionesCompleta.iterator();
        ReparticionBean reparticion = null;
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
      this.binder.loadComponent(reparticionesBusquedaUsuarioListbox);
    }
  }

  public void onBlur$reparticionBusquedaUsuario() {
    String value = this.reparticionBusquedaUsuario.getValue();
    if (StringUtils.isNotEmpty(value)) {
      if (!(this.reparticionServ.validarCodigoReparticion(value.trim()))) {
        this.reparticionBusquedaUsuario.focus();
        throw new WrongValueException(this.reparticionBusquedaUsuario,
            Labels.getLabel("ee.general.reparticionInvalida"));
      }
      this.reparticionBusquedaUsuario.setValue(value.toUpperCase());
    }
  }

  public void onSelect$reparticionesBusquedaUsuarioListbox() {
    ReparticionBean rsb = this.listaReparticionSeleccionada
        .get(reparticionesBusquedaUsuarioListbox.getSelectedIndex());
    this.reparticionBusquedaUsuario.setValue(rsb.getCodigo());
    this.textoReparticionBusquedaUsuario.setValue(null);
    this.listaReparticionSeleccionada.clear();
    this.binder.loadComponent(this.reparticionBusquedaUsuario);
    this.textoReparticionBusquedaUsuario.setValue(null);
    reparticionBusquedaUsuario.close();
  }

}
