package com.egoveris.te.ws.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.HistorialOperacionDTO;
import com.egoveris.te.base.service.HistorialOperacionService;
import com.egoveris.te.base.service.UsuariosSADEService;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.ws.exception.ExpedienteInexistenteException;
import com.egoveris.te.ws.model.ExternalHistorialOperacionDTO;
import com.egoveris.te.ws.service.ExternalHistorialOperacionService;

@Service
public class ExternalHistorialOperacionServiceImpl implements ExternalHistorialOperacionService {

	@Autowired
	private ExpedienteElectronicoService expedienteElectronicoService;
	@Autowired
	private HistorialOperacionService historialOperacionService;
	@Autowired
	private UsuariosSADEService usuarioSadeService;

	@Override
	public List<ExternalHistorialOperacionDTO> getHistorialByExpediente(String codigoExpediente) {
		List<ExternalHistorialOperacionDTO> retorno = new ArrayList<>();

		if (codigoExpediente != null && !codigoExpediente.trim().isEmpty()) {
			ExpedienteElectronicoDTO expediente = expedienteElectronicoService
					.obtenerExpedienteElectronicoPorCodigo(codigoExpediente);

			if (expediente == null) {
				throw new ExpedienteInexistenteException(codigoExpediente);
			}

			List<HistorialOperacionDTO> pases = historialOperacionService
					.buscarHistorialporExpediente(expediente.getId());

			if (pases != null && !pases.isEmpty()) {
				fillHistorialPases(retorno, pases);
			}
		}

		return retorno;
	}

	/**
	 * Arma la lista de retorno.
	 * 
	 * @param retorno
	 * @param pases
	 */
	private void fillHistorialPases(List<ExternalHistorialOperacionDTO> retorno, List<HistorialOperacionDTO> pases) {
		// Mapa que optimiza las querys hacia usuarioSadeService
		Map<String, String> mapaUsuarioOrganismo = new HashMap<>();
		ExternalHistorialOperacionDTO historialOperacionTemp;
		Usuario usuario;

		if (pases.size() > 2) {
			// No agrega los dos primeros pases, los que son generados automáticamente
			// por el sistema al enviar el expediente a TE.
			// No agrega los pases hacia organismos, ya que estos van al buzón grupal.
			for (int i = 0; i < pases.size(); i++) {
				if (i == 0 || i == 1 || pases.get(i).getDestinatario().indexOf('-') > 0) {
					continue;
				}

				if (mapaUsuarioOrganismo.get(pases.get(i).getDestinatario()) == null) {
					usuario = usuarioSadeService.getDatosUsuario(pases.get(i).getDestinatario());
					mapaUsuarioOrganismo.put(usuario.getUsername(), usuario.getCodigoReparticion());
				}

				historialOperacionTemp = new ExternalHistorialOperacionDTO();
				historialOperacionTemp.setFechaOperacion(pases.get(i).getFechaOperacion());
				historialOperacionTemp.setOrganismoDestino(mapaUsuarioOrganismo.get(pases.get(i).getDestinatario()));
				retorno.add(historialOperacionTemp);
			}
		}
	}

}