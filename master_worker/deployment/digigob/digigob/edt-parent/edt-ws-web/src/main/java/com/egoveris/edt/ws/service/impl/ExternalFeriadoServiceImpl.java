package com.egoveris.edt.ws.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.edt.base.model.eu.feriado.FeriadoDTO;
import com.egoveris.edt.base.service.feriado.FeriadoService;
import com.egoveris.edt.ws.service.IExternalFeriadoService;

@Service
public class ExternalFeriadoServiceImpl implements IExternalFeriadoService {

	@Autowired
	private FeriadoService feriadoService;
	
	@Override
	public List<Date> obtenerFeriados() {
		
		List<FeriadoDTO> feriados = feriadoService.obtenerFeriados();
		
		return feriados.stream().map(FeriadoDTO::getFecha).collect(Collectors.toList());
	}

}
