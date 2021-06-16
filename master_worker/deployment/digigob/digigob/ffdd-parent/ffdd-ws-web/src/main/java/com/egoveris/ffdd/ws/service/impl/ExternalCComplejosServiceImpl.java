package com.egoveris.ffdd.ws.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.ccomplejos.base.model.AbstractCComplejoDTO;
import com.egoveris.ccomplejos.base.service.ICComplejosService;
import com.egoveris.ffdd.ws.service.ExternalCComplejosService;

@Service
public class ExternalCComplejosServiceImpl implements ExternalCComplejosService {

	@Autowired
	private ICComplejosService iCComplejosService;

	@Override
	public void guardarDatosComponentes(List<AbstractCComplejoDTO> cComplejoDTOs) {
		iCComplejosService.guardarDatosComponentes(cComplejoDTOs);
	}

	@Override
	public List<AbstractCComplejoDTO> buscarDatosComponente(AbstractCComplejoDTO cComplejoDTO) {
		return iCComplejosService.buscarDatosComponente(cComplejoDTO);
	}


}
