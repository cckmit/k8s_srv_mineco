/**
 * 
 */
package com.egoveris.te.base.rendered;

import org.zkoss.util.resource.Labels;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

import com.egoveris.te.base.model.ExpedienteAsociadoEntDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;

/**
 * @author jnorvert
 *
 */
public class GenericListaExpedienteEnTramitacionConjuntaItemRenderer extends GenericListitemRenderer {

	private ExpedienteElectronicoService expedienteElectronicoService = (ExpedienteElectronicoService) SpringUtil
			.getBean("expedienteElectronicoServiceImpl");

	public void render(Listitem item, Object data, int arg1) throws Exception {

    ExpedienteAsociadoEntDTO expedienteTramitacionConjunta = (ExpedienteAsociadoEntDTO) data;
  
    // CODIGO TRATA

    ExpedienteElectronicoDTO ee2;
    // TODO refactorizar porque esta mal hecho.
    ee2 = expedienteElectronicoService.obtenerExpedienteElectronico(
        expedienteTramitacionConjunta.getTipoDocumento(), expedienteTramitacionConjunta.getAnio(),
        expedienteTramitacionConjunta.getNumero(),
        expedienteTramitacionConjunta.getCodigoReparticionUsuario());

    String codigoTrata = ee2.getTrata().getCodigoTrata();

    Listcell currentCell;
    // DESCRIPCION TRATA
    new Listcell(expedienteTramitacionConjunta.getDescrpTrata()).setParent(item);
    // AÑO
    new Listcell(expedienteTramitacionConjunta.getAnio().toString()).setParent(item);
    // NUMERO
    new Listcell(expedienteTramitacionConjunta.getNumero().toString()).setParent(item);
    // REPARTICION
    new Listcell(expedienteTramitacionConjunta.getCodigoReparticionActuacion() + "-"
        + expedienteTramitacionConjunta.getCodigoReparticionUsuario()).setParent(item);

    new Listcell(codigoTrata).setParent(item);

    // EXPEDIENTE
    // new Listcell(expedienteTramitacionConjunta).setParent(item);

    currentCell = new Listcell();
    currentCell.setParent(item);

    Hbox hbox = new Hbox();

    Image visualizarImage = new Image("/imagenes/ver_expediente.png");
    Label visualizar = new Label("Visualizar");
    visualizar.setStyle("font-size:10px");
    visualizarImage.setParent(hbox);
    visualizar.setParent(hbox);
    org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(visualizarImage,
        "onClick=expedienteEnTramitacionConjuntaConsultaWindow$composer.onVerExpediente");
    org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(visualizar,
        "onClick=expedienteEnTramitacionConjuntaConsultaWindow$composer.onVerExpediente");

    if (!expedienteTramitacionConjunta.getDefinitivo()) {
      Image runImage = new Image("/imagenes/desasociarexpedientes.png");
      org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(runImage,
          "onClick=expedienteEnTramitacionConjuntaConsultaWindow$composer.onExecuteDesasociar");
      Label ejecutar = new Label(Labels.getLabel("ee.asociarexpediente.label.value.desvincular"));
      ejecutar.setStyle("font-size:10px");
      org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(ejecutar,
          "onClick=expedienteEnTramitacionConjuntaConsultaWindow$composer.onExecuteDesasociar");
      runImage.setParent(hbox);
      ejecutar.setParent(hbox);
    }

    hbox.setParent(currentCell);

  }

}
