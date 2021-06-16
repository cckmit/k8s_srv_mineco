package com.egoveris.edt.ws.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.edt.base.model.eu.PeriodoLicenciaDTO;
import com.egoveris.edt.base.service.IPeriodoLicenciaService;
import com.egoveris.edt.ws.service.IExternalPeriodoLicenciaService;

@Service
public class ExternalPeriodoLicenciaServiceImpl implements IExternalPeriodoLicenciaService {

	private static final Logger logger = LoggerFactory.getLogger(IExternalPeriodoLicenciaService.class);
	
	@Autowired
	IPeriodoLicenciaService periodoLicenciaService;
	
	@Override
	public boolean licenciaActiva(String username) {
		if (logger.isDebugEnabled()) {
			logger.debug("licenciaActiva(username={}) - start", username);
		}
		
		boolean licenciaActiva = false;
		
		PeriodoLicenciaDTO periodoLicencia = periodoLicenciaService.traerPeriodoLicenciaPorUserName(username);
		
		if (periodoLicencia != null && periodoLicencia.getId() != null) {
			licenciaActiva = true;
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("licenciaActiva(boolean) - end - return value={}", licenciaActiva);
		}
		
		return licenciaActiva;
	}

	@Override
	public String obtenerApoderadoUsuarioLicencia(String username) {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerApoderadoUsuarioLicencia(username={}) - start", username);
		}
		
		String usuarioRetorno = null;
		String usuarioActual = username;
		Set<String> usuariosConsultados = new HashSet<>();
		
		boolean buscar = true;
		
		while (buscar) {
			if (usuarioActual != null && !licenciaActiva(usuarioActual)) {
				usuarioRetorno = usuarioActual;
        buscar = false;
      }
      else if (usuarioActual != null && !usuariosConsultados.contains(usuarioActual)) {
        usuariosConsultados.add(usuarioActual);
        
        PeriodoLicenciaDTO periodoLicencia = periodoLicenciaService.traerPeriodoLicenciaPorUserName(usuarioActual);
        
        if (periodoLicencia != null && periodoLicencia.getId() != null) {
        	usuarioActual = periodoLicencia.getApoderado();
        }
        else {
        	usuarioActual = null;
        }
      }
      else {
        buscar = false;
      }
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerApoderadoUsuarioLicencia(String) - end - return value={}", "");
		}
		
		return usuarioRetorno;
	}

}
