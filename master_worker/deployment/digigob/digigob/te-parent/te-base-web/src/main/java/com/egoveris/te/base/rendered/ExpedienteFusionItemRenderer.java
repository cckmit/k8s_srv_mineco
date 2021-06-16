package com.egoveris.te.base.rendered;

import com.egoveris.te.base.model.ExpedienteAsociadoEntDTO;

import org.zkoss.util.resource.Labels;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class ExpedienteFusionItemRenderer
    extends GenericListaExpedienteEnFusionConsultaItemRenderer implements ListitemRenderer {

  public void render(Listitem item, Object data, int arg2) throws Exception {
    Hbox hbox = super.render(data, item);
    ExpedienteAsociadoEntDTO expedienteFusion = (ExpedienteAsociadoEntDTO) data;
    if (!expedienteFusion.getDefinitivo()) {
      Image runImage = new Image("/imagenes/play.png");
      org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(runImage,
          "onClick=expedienteEnFusionWindow$composer.onExecuteDesasociar");
      Label ejecutar = new Label(Labels.getLabel("ee.asociarexpediente.label.value.desvincular"));
      ejecutar.setStyle("font-size:10px");
      org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(ejecutar,
          "onClick=expedienteEnFusionWindow$composer.onExecuteDesasociar");
      runImage.setParent(hbox);
      ejecutar.setParent(hbox);
    }
  }
}
