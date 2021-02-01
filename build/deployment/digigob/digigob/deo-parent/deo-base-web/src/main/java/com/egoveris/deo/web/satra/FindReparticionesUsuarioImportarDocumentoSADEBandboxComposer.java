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
import org.zkoss.zul.Textbox;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class FindReparticionesUsuarioImportarDocumentoSADEBandboxComposer
    extends GenericForwardComposer {

  private static final long serialVersionUID = 5177382364840199302L;

  @WireVariable("obtenerReparticionServicesImpl")
  private ObtenerReparticionServices obtenerReparticionService;

  protected Listbox reparticionesUsuarioImportarDocumentoSADEListbox;
  protected Textbox textoReparticionUsuarioImportarDocumentoSADE;
  protected Bandbox reparticionUsuarioImportarDocumentoSADE;
  protected AnnotateDataBinder binder;

  protected ReparticionBean reparticionUsuarioSeleccionada;
  protected List<ReparticionBean> listaReparticionUsuarioSADESeleccionada;
  protected List<ReparticionBean> listaReparticionesUsuarioSADECompleta;

  @Override
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    this.binder = new AnnotateDataBinder(reparticionesUsuarioImportarDocumentoSADEListbox);
    this.listaReparticionUsuarioSADESeleccionada = new ArrayList<ReparticionBean>();

    this.listaReparticionesUsuarioSADECompleta = obtenerReparticionService
        .buscarReparticionesVigentesActivas();

    binder.bindBean("listaReparticionUsuarioSADESeleccionada",
        this.listaReparticionUsuarioSADESeleccionada);
    binder.bindBean("reparticionUsuarioSeleccionada", this.reparticionUsuarioSeleccionada);
    binder.loadAll();
  }

  public void onChanging$textoReparticionUsuarioImportarDocumentoSADE(InputEvent e) {
    this.cargarReparticiones(e);
  }

  public void cargarReparticiones(InputEvent e) {
    String matchingText = e.getValue();
    if (!"".equals(matchingText) && (matchingText.length() >= 3)) {
      this.listaReparticionUsuarioSADESeleccionada.clear();
      if (listaReparticionesUsuarioSADECompleta != null) {
        matchingText = matchingText.toUpperCase();
        Iterator<ReparticionBean> iterator = listaReparticionesUsuarioSADECompleta.iterator();
        ReparticionBean reparticion;
        while ((iterator.hasNext()) && (this.listaReparticionUsuarioSADESeleccionada
            .size() <= Constantes.MAX_REPARTICION_RESULTS)) {
          reparticion = iterator.next();
          if ((reparticion != null) && (reparticion.getNombre() != null)) {
            if ((reparticion.getNombre().contains(matchingText))
                || (reparticion.getCodigo().contains(matchingText))) {
              listaReparticionUsuarioSADESeleccionada.add(reparticion);
            }
          }
        }
      }
      this.binder.loadComponent(reparticionesUsuarioImportarDocumentoSADEListbox);
    } else if (matchingText.equals("")) {
      this.listaReparticionUsuarioSADESeleccionada.clear();
      this.binder.loadComponent(reparticionesUsuarioImportarDocumentoSADEListbox);
    }
  }

  public void onBlur$reparticionUsuarioImportarDocumentoSADE() {
    String value = this.reparticionUsuarioImportarDocumentoSADE.getValue();
    if (StringUtils.isNotEmpty(value)) {
      if (!(obtenerReparticionService.validarCodigoReparticion(value.trim()))) {
        this.reparticionUsuarioImportarDocumentoSADE.focus();
        throw new WrongValueException(this.reparticionUsuarioImportarDocumentoSADE,
            Labels.getLabel("gedo.general.reparticionInvalida"));
      }
      this.reparticionUsuarioImportarDocumentoSADE.setValue(value.toUpperCase());
    }
  }

  public void onSelect$reparticionesUsuarioImportarDocumentoSADEListbox() {
    ReparticionBean rsb = this.listaReparticionUsuarioSADESeleccionada
        .get(reparticionesUsuarioImportarDocumentoSADEListbox.getSelectedIndex());
    this.reparticionUsuarioImportarDocumentoSADE.setValue(rsb.getCodigo());
    this.textoReparticionUsuarioImportarDocumentoSADE.setValue(null);
    this.listaReparticionUsuarioSADESeleccionada.clear();
    this.binder.loadComponent(this.reparticionUsuarioImportarDocumentoSADE);
    this.textoReparticionUsuarioImportarDocumentoSADE.setValue(null);
    reparticionUsuarioImportarDocumentoSADE.close();
  }
}