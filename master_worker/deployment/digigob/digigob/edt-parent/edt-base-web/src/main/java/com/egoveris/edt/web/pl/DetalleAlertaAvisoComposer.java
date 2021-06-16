package com.egoveris.edt.web.pl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Textbox;

import com.egoveris.edt.web.common.BaseComposer;
import com.egoveris.sharedsecurity.base.model.ConstantesSesion;

public class DetalleAlertaAvisoComposer extends BaseComposer {

  /**
  * 
  */
  private static final long serialVersionUID = 6341693437685494962L;
  /**
  * 
  */

  private AnnotateDataBinder binder;
  private Map<?, ?> parametros;
  @Autowired
  private Textbox txbx_detalle;

  public Textbox getTxbx_detalle() {
    return txbx_detalle;
  }

  public void setTxbx_detalle(Textbox txbx_detalle) {
    this.txbx_detalle = txbx_detalle;
  }

  private String detalle;

  @SuppressWarnings("unchecked")
  @Override
  public void doAfterCompose(Component c) throws Exception {

    super.doAfterCompose(c);

    parametros = Executions.getCurrent().getArg();
    if (!parametros.isEmpty()) {
      detalle = (String) parametros.get(ConstantesSesion.KEY_ALERTA_AVISO_DETALLE);
      if (detalle == null || detalle.trim().isEmpty()) {
        txbx_detalle
            .setValue(Labels.getLabel("eu.detalleAlertaAvisoComposer.txbxDetalle.noDetalle"));
      } else {
        txbx_detalle.setValue(detalle);
      }

      binder = new AnnotateDataBinder(c);
      binder.loadAll();

    }
  }

  public void onClick$btn_cerrar() throws InterruptedException {
    cerrar();
  }

  private void cerrar() {
    this.self.detach();
  }

  public String getDetalle() {
    return detalle;
  }

  public void setDetalle(String detalle) {
    this.detalle = detalle;
  }

  public AnnotateDataBinder getBinder() {
    return binder;
  }

  public void setBinder(AnnotateDataBinder binder) {
    this.binder = binder;
  }

}
