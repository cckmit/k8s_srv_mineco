/**
 * 
 */
package com.egoveris.te.base.rendered;

import com.egoveris.te.base.model.ExpedienteAsociadoEntDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.util.resource.Labels;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

/**
 * @author cearagon
 *
 */
public class GenericListaExpedienteEnFusionItemRenderer implements ListitemRenderer {

	@Autowired
	private ExpedienteElectronicoService expedienteElectronicoService;
	
	
  public void render(Listitem item, Object data, int arg1) throws Exception {

    ExpedienteAsociadoEntDTO expedienteFusion = (ExpedienteAsociadoEntDTO) data;
 
    Listcell currentCell;
    // TIPO DOCUMENTO
    new Listcell(expedienteFusion.getTipoDocumento()).setParent(item);
    // AÑO
    new Listcell(expedienteFusion.getAnio().toString()).setParent(item);
    // NUMERO
    new Listcell(expedienteFusion.getNumero().toString()).setParent(item);
    // REPARTICION
    new Listcell(expedienteFusion.getCodigoReparticionActuacion() + "-"
        + expedienteFusion.getCodigoReparticionUsuario()).setParent(item);
    // CODIGO TRATA
    // new Listcell(expedienteFusion.getTrata()).setParent(item);
    ExpedienteElectronicoDTO ee2;
    // TODO refactorizar porque esta mal hecho.
    ee2 = expedienteElectronicoService.obtenerExpedienteElectronico(
        expedienteFusion.getTipoDocumento(), expedienteFusion.getAnio(),
        expedienteFusion.getNumero(), expedienteFusion.getCodigoReparticionUsuario());

    String codigoTrata = ee2.getTrata().getCodigoTrata();

    new Listcell(codigoTrata).setParent(item);

    // EXPEDIENTE
    // new Listcell(expedienteFusion).setParent(item);

    currentCell = new Listcell();
    currentCell.setParent(item);

    Hbox hbox = new Hbox();

    Image visualizarImage = new Image("/imagenes/ver_expediente.png");
    Label visualizar = new Label("Visualizar");
    visualizar.setStyle("font-size:10px");
    visualizarImage.setParent(hbox);
    visualizar.setParent(hbox);
    org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(visualizarImage,
        "onClick=expedienteEnFusionConsultaWindow$composer.onVerExpediente");
    org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(visualizar,
        "onClick=expedienteEnFusionConsultaWindow$composer.onVerExpediente");

    if (!expedienteFusion.getDefinitivo()) {
      Image runImage = new Image("/imagenes/desasociarexpedientes.png");
      org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(runImage,
          "onClick=expedienteEnFusionConsultaWindow$composer.onExecuteDesasociar");
      Label ejecutar = new Label(Labels.getLabel("ee.asociarexpediente.label.value.desvincular"));
      ejecutar.setStyle("font-size:10px");
      org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(ejecutar,
          "onClick=expedienteEnFusionConsultaWindow$composer.onExecuteDesasociar");
      runImage.setParent(hbox);
      ejecutar.setParent(hbox);
    }

    hbox.setParent(currentCell);

  }

}
