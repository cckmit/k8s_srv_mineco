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
 * @author jnorvert
 *
 */
public class GenericListaExpedienteEnTramitacionConjuntaConsultaItemRenderer implements ListitemRenderer {

	@Autowired
	private ExpedienteElectronicoService expedienteElectronicoService;

	public void render(Listitem item, Object data, int arg1) throws Exception {

		ExpedienteAsociadoEntDTO expedienteTramitacionConjunta = (ExpedienteAsociadoEntDTO) data;

		Listcell currentCell;
		// TIPO DOCUMENTO
		new Listcell(expedienteTramitacionConjunta.getTipoDocumento()).setParent(item);
		// AÑO
		new Listcell(expedienteTramitacionConjunta.getAnio().toString()).setParent(item);
		// NUMERO
		new Listcell(expedienteTramitacionConjunta.getNumero().toString()).setParent(item);
		// REPARTICION
		new Listcell(expedienteTramitacionConjunta.getCodigoReparticionActuacion() + "-"
				+ expedienteTramitacionConjunta.getCodigoReparticionUsuario()).setParent(item);
		// CODIGO TRATA
		ExpedienteElectronicoDTO ee2;
		// TODO refactorizar porque esta mal hecho.
		ee2 = expedienteElectronicoService.obtenerExpedienteElectronico(
				expedienteTramitacionConjunta.getTipoDocumento(), expedienteTramitacionConjunta.getAnio(),
				expedienteTramitacionConjunta.getNumero(), expedienteTramitacionConjunta.getCodigoReparticionUsuario());

		String codigoTrata = ee2.getTrata().getCodigoTrata();

		new Listcell(codigoTrata).setParent(item);

		// EXPEDIENTE

		currentCell = new Listcell();
		currentCell.setParent(item);

		Hbox hbox = new Hbox();

		Image visualizarImage = new Image("/imagenes/edit-find.png");
		Label visualizar = new Label("Visualizar");
		visualizar.setStyle("font-size:10px");
		visualizarImage.setParent(hbox);
		visualizar.setParent(hbox);
		org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(visualizarImage,
				"onClick=expedienteEnTramitacionConjuntaWindow$composer.onVerExpediente");
		org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(visualizar,
				"onClick=expedienteEnTramitacionConjuntaWindow$composer.onVerExpediente");

		if (!expedienteTramitacionConjunta.getDefinitivo()) {
			Image runImage = new Image("/imagenes/play.png");
			org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(runImage,
					"onClick=expedienteEnTramitacionConjuntaWindow$composer.onExecuteDesasociar");
			Label ejecutar = new Label(Labels.getLabel("ee.asociarexpediente.label.value.desvincular"));
			ejecutar.setStyle("font-size:10px");
			org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(ejecutar,
					"onClick=expedienteEnTramitacionConjuntaWindow$composer.onExecuteDesasociar");
			runImage.setParent(hbox);
			ejecutar.setParent(hbox);
		}

		hbox.setParent(currentCell);

	}

}
