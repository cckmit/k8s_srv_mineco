package com.egoveris.vucfront.ws.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.vucfront.model.model.ExpedienteBaseDTO;
import com.egoveris.vucfront.model.service.ExpedienteBaseService;
import com.egoveris.vucfront.ws.model.ConsolidacionDTO;
import com.egoveris.vucfront.ws.service.ExternalConsolidacionService;

@Service
public class ExternalConsolidacionServiceImpl implements ExternalConsolidacionService {

	@Autowired
	private ExpedienteBaseService expedienteBaseService;
	
	@Override
	public List<ConsolidacionDTO> consultarConsolidacionEntreFecha(Date fechaDesde, Date fechaHasta, String organismoUsuario) {
		
		List<ExpedienteBaseDTO> resultado = expedienteBaseService
				.buscarConsolidacionPorFecha(fechaDesde, fechaHasta, organismoUsuario);
		
		List<ConsolidacionDTO> consolidaciones = new ArrayList<>();
		if(!resultado.isEmpty()) {
			for(ExpedienteBaseDTO expBase : resultado) {
				ConsolidacionDTO consolidacion = new ConsolidacionDTO();
				consolidacion.setCodigoTrata(expBase.getTipoTramite().getTrata());
				consolidacion.setDescripcionTrata(expBase.getTipoTramite().getNombre());
				consolidacion.setNumeroExpediente(expBase.getCodigoSade());
				consolidacion.setNumeroTransaccionTad(expBase.getId());
				consolidacion.setFechaPago(expBase.getFechaPago());
				consolidacion.setNumeroAutorizacion(expBase.getNumeroAutorizacion());
				consolidacion.setTransaccionPago(expBase.getTransaccionPago());
				consolidacion.setOrganismoIniciador(expBase.getTipoTramite().getReparticionIniciadora());
				
				if(expBase.getMonto()!=null) {					
					consolidacion.setMonto(expBase.getMonto().toString());
				}
				consolidacion.setTitularTarjeta(expBase.getNombreTitutarTarjeta());
				consolidacion.setApiKey(expBase.getApiKeyTransaccion());
				consolidacion.setNumeroDUI(expBase.getPersona().getCuit());
				
				consolidaciones.add(consolidacion);
			}
		}
		
		
		return consolidaciones;
	}

}
