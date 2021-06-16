package com.egoveris.deo.web.satra;

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

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class FindReparticionesDigitalizarBandboxComposer extends GenericForwardComposer {

  /**
  * 
  */
  private static final long serialVersionUID = 7420446970613798790L;
  private Listbox reparticionesDigitalizarListbox;
  private Bandbox reparticionImportarDocumentoEspecial;
  @WireVariable("obtenerReparticionServicesImpl")
  private ObtenerReparticionServices obtenerReparticionService;
  private AnnotateDataBinder binder;
  private List<ReparticionBean> listaReparticionSADESeleccionada;
  private ReparticionBean reparticionSeleccionada;

  @SuppressWarnings("unchecked")
  @Override
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    this.binder = new AnnotateDataBinder(reparticionesDigitalizarListbox);
    this.listaReparticionSADESeleccionada = new ArrayList<>();
    binder.bindBean("listaReparticionSADESeleccionada", this.listaReparticionSADESeleccionada);
    binder.bindBean("reparticionSeleccionada", this.reparticionSeleccionada);
    binder.loadAll();
  }

  public void onChanging$textoReparticionImportarDocumentoEspecial(InputEvent e) {
    String matchingText = e.getValue();
    if (StringUtils.isNotEmpty(matchingText) && (matchingText.length() >= 3)) {
      List<ReparticionBean> listaReparticionesSADECompleta = obtenerReparticionService
          .buscarReparticiones();
      this.listaReparticionSADESeleccionada.clear();
      if (listaReparticionesSADECompleta != null) {
        matchingText = matchingText.toUpperCase();
        Iterator<ReparticionBean> iterator = listaReparticionesSADECompleta.iterator();
        ReparticionBean reparticion;
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
      this.binder.loadComponent(reparticionesDigitalizarListbox);
    }
  }

  public void onBlur$reparticionImportarDocumentoEspecial() {
    String value = this.reparticionImportarDocumentoEspecial.getValue();
    if (StringUtils.isNotEmpty(value)) {
      if (!(obtenerReparticionService.validarCodigoReparticion(value.trim()))) {
        this.reparticionImportarDocumentoEspecial.focus();
        throw new WrongValueException(this.reparticionImportarDocumentoEspecial,
            Labels.getLabel("gedo.general.reparticionInvalida"));
      }
      this.reparticionImportarDocumentoEspecial.setValue(value.toUpperCase());
    }
  }

  public void onSelect$reparticionImportarDocumentoEspecial() {
    ReparticionBean rsb = this.listaReparticionSADESeleccionada
        .get(reparticionesDigitalizarListbox.getSelectedIndex());
    this.reparticionImportarDocumentoEspecial.setValue(rsb.getCodigo());
    this.listaReparticionSADESeleccionada.clear();
    this.binder.loadComponent(this.reparticionImportarDocumentoEspecial);
    this.reparticionImportarDocumentoEspecial.close();
  }
}