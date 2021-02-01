/**
 * 
 */
package com.egoveris.te.base.rendered;

import com.egoveris.te.base.model.ExpedienteAsociadoEntDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

/**
 * @author jnorvert
 *
 */
public class GenericListaExpedienteEnFusionConsultaItemRenderer {

	@Autowired
	private ExpedienteElectronicoService expedienteElectronicoService;

	public Hbox render(Object data, Listitem item) throws Exception {

		ExpedienteAsociadoEntDTO expedienteFusion = (ExpedienteAsociadoEntDTO) data;

		Listcell currentCell;
		// TIPO DOCUMENTO
		new Listcell(expedienteFusion.getTipoDocumento()).setParent(item);
		// AÑO
		new Listcell(expedienteFusion.getAnio().toString()).setParent(item);
		// NUMERO
		new Listcell(expedienteFusion.getNumero().toString()).setParent(item);
		// REPARTICION
		new Listcell(
				expedienteFusion.getCodigoReparticionActuacion() + "-" + expedienteFusion.getCodigoReparticionUsuario())
						.setParent(item);
		// CODIGO TRATA
		// new Listcell(expedienteFusion.getTrata()).setParent(item);
		ExpedienteElectronicoDTO ee2;
		// TODO refactorizar porque esta mal hecho.
		ee2 = expedienteElectronicoService.obtenerExpedienteElectronico(expedienteFusion.getTipoDocumento(),
				expedienteFusion.getAnio(), expedienteFusion.getNumero(),
				expedienteFusion.getCodigoReparticionUsuario());

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
				"onClick=expedienteEnFusionWindow$composer.onVerExpediente");
		org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(visualizar,
				"onClick=expedienteEnFusionWindow$composer.onVerExpediente");

		hbox.setParent(currentCell);
		return hbox;

	}

}
