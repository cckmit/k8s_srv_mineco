package com.egoveris.te.base.rendered;

import com.egoveris.te.base.model.ExpedienteAsociadoEntDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.service.expediente.ExpedienteSadeService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class ExpedientesAsociadosTramitacionEspecialItemRenderer implements ListitemRenderer {

	@Autowired
	private ExpedienteSadeService expedienteSadeService;

	@Autowired
	private ExpedienteElectronicoService expedienteElectronicoService;

	private ExpedienteElectronicoDTO ee;

	public ExpedienteElectronicoDTO getEe() {
		return ee;
	}

	public void setEe(ExpedienteElectronicoDTO ee) {
		this.ee = ee;
	}

	public void render(Listitem item, Object data, int arg1) throws Exception {

		ExpedienteAsociadoEntDTO expedienteAsociado = (ExpedienteAsociadoEntDTO) data;

		// TipoDocumento (EX) Expediente
		new Listcell(expedienteAsociado.getTipoDocumento()).setParent(item);
		// Anio Expediente
		new Listcell(expedienteAsociado.getAnio().toString()).setParent(item);
		// Número Expediente
		new Listcell(expedienteAsociado.getNumero().toString()).setParent(item);
		// Repartición Expediente + Repartición Usuario
		String codigoReparticionActuacionYUsuario = "";
		if (expedienteAsociado.getCodigoReparticionActuacion() != null)
			codigoReparticionActuacionYUsuario = expedienteAsociado.getCodigoReparticionActuacion().trim() + "-";
		if (expedienteAsociado.getCodigoReparticionUsuario() != null)
			codigoReparticionActuacionYUsuario = codigoReparticionActuacionYUsuario
					+ (expedienteAsociado.getCodigoReparticionUsuario().trim());
		new Listcell(codigoReparticionActuacionYUsuario).setParent(item);

		// Código de Trata

		ExpedienteElectronicoDTO ee2 = null;
		if (!expedienteAsociado.getEsElectronico()) {
			String codigoTrataSADE = expedienteSadeService.obtenerCodigoTrataSADE(expedienteAsociado);
			new Listcell(codigoTrataSADE).setParent(item);

		} else {

			// TODO refactorizar porque esta mal hecho.
			ee2 = expedienteElectronicoService.obtenerExpedienteElectronico(expedienteAsociado.getTipoDocumento(),
					expedienteAsociado.getAnio(), expedienteAsociado.getNumero(),
					expedienteAsociado.getCodigoReparticionUsuario());

			String codigoTrata = ee2.getTrata().getCodigoTrata();

			new Listcell(codigoTrata).setParent(item);

		}

		// ES Electrónico/SADE
		String tipoExpediente;
		if (expedienteAsociado.getEsElectronico()) {
			tipoExpediente = "Elect.";
		} else {
			tipoExpediente = "SADE";
		}
		new Listcell(tipoExpediente).setParent(item);

		// Estado
		if (ee2 != null && StringUtils.isNotEmpty(ee2.getEstado())) {
			String estado = ee2.getEstado();
			new Listcell(estado).setParent(item);

		} else {
			new Listcell("").setParent(item);
		}

	}

}
