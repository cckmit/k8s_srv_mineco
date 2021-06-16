package com.egoveris.te.base.util;

import com.egoveris.sharedorganismo.base.model.ReparticionBean;
import com.egoveris.te.base.service.ObtenerReparticionServices;

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
public class FindReparticionesImportar extends GenericForwardComposer {

  /**
  * 
  */
  private static final long serialVersionUID = -8826295810008568404L;

  @WireVariable(ConstantesServicios.OBTENER_REPARTICION_SERVICE)
  private ObtenerReparticionServices obtenerReparticionService;

  @Autowired
  protected Listbox reparticionesImportarDocumentoSADEListbox;
  @Autowired
  protected Textbox textoReparticionImportarDocumentoSADE;
  @Autowired
  protected Bandbox reparticionImportarDocumentoSADE;
  protected AnnotateDataBinder binder;

  protected ReparticionBean reparticionSeleccionada;
  protected List<ReparticionBean> listaReparticionSADESeleccionada;
  protected List<ReparticionBean> listaReparticionesSADECompleta;

  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    this.binder = new AnnotateDataBinder(reparticionesImportarDocumentoSADEListbox);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    
    this.listaReparticionSADESeleccionada = new ArrayList<ReparticionBean>();
    this.listaReparticionesSADECompleta = obtenerReparticionService
        .buscarReparticionesVigentesActivas();

    binder.bindBean("listaReparticionSADESeleccionada", this.listaReparticionSADESeleccionada);
    binder.bindBean("reparticionSeleccionada", this.reparticionSeleccionada);
    binder.loadAll();
  }

  public void onChanging$textoReparticionImportarDocumentoSADE(InputEvent e) {
    this.cargarReparticiones(e);
  }

  public void cargarReparticiones(InputEvent e) {
    String matchingText = e.getValue();
    if (!matchingText.equals("") && (matchingText.length() >= 3)) {
      this.listaReparticionSADESeleccionada.clear();
      if (listaReparticionesSADECompleta != null) {
        matchingText = matchingText.toUpperCase();
        Iterator<ReparticionBean> iterator = listaReparticionesSADECompleta.iterator();
        ReparticionBean reparticion;
        while ((iterator.hasNext()) && (this.listaReparticionSADESeleccionada
            .size() <= ConstantesWeb.MAX_REPARTICION_RESULTS)) {
          reparticion = iterator.next();
          if ((reparticion != null) && (reparticion.getNombre() != null)) {
            if ((reparticion.getNombre().contains(matchingText))
                || (reparticion.getCodigo().contains(matchingText))) {
              listaReparticionSADESeleccionada.add(reparticion);
            }
          }
        }
      }
      this.binder.loadComponent(reparticionesImportarDocumentoSADEListbox);
    } else if (matchingText.equals("")) {
      this.listaReparticionSADESeleccionada.clear();
      this.binder.loadComponent(reparticionesImportarDocumentoSADEListbox);
    }
  }

  public void onBlur$reparticionImportarDocumentoSADE() {
    String value = this.reparticionImportarDocumentoSADE.getValue();
    if (StringUtils.isNotEmpty(value)) {
      if (!(obtenerReparticionService.validarCodigoReparticion(value.trim()))) {
        this.reparticionImportarDocumentoSADE.focus();
        throw new WrongValueException(this.reparticionImportarDocumentoSADE,
            Labels.getLabel("ee.general.adm.reparticionInvalida"));
      }
      this.reparticionImportarDocumentoSADE.setValue(value.toUpperCase());
    }
  }

  public void onSelect$reparticionesImportarDocumentoSADEListbox() {
    ReparticionBean rsb = this.listaReparticionSADESeleccionada
        .get(reparticionesImportarDocumentoSADEListbox.getSelectedIndex());
    this.reparticionImportarDocumentoSADE.setValue(rsb.getCodigo());
    this.textoReparticionImportarDocumentoSADE.setValue(null);
    this.listaReparticionSADESeleccionada.clear();
    this.binder.loadComponent(this.reparticionImportarDocumentoSADE);
    this.textoReparticionImportarDocumentoSADE.setValue(null);
    reparticionImportarDocumentoSADE.close();
  }
}
