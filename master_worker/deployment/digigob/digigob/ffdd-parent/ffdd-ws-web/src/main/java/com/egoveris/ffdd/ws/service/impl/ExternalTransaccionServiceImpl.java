package com.egoveris.ffdd.ws.service.impl;

import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.ffdd.model.exception.DynFormException;
import com.egoveris.ffdd.model.exception.DynFormValidarTransaccionException;
import com.egoveris.ffdd.model.model.TransaccionDTO;
import com.egoveris.ffdd.model.model.TransaccionValidaDTO;
import com.egoveris.ffdd.ws.model.complex.ComplexComponentDTO;
import com.egoveris.ffdd.ws.service.ExternalTransaccionService;
import com.egoveris.shared.map.ListMapper;

@Service
public class ExternalTransaccionServiceImpl implements ExternalTransaccionService {

	@Autowired
	private com.egoveris.ffdd.base.service.ITransaccionService baseService;

	private DozerBeanMapper mapper = new DozerBeanMapper();

	@Override
	public TransaccionDTO buscarTransaccionPorUUID(Integer uuid) throws DynFormException {
		return baseService.buscarTransaccionPorUUID(uuid);
	}

	@Override
	public void deleteFormWebExpt(TransaccionDTO trans) throws DynFormException {
		baseService.deleteFormWebExpt(mapper.map(trans, com.egoveris.ffdd.model.model.TransaccionDTO.class));
	}

	@Override
	public boolean existeTransaccionParaFormulario(String formulario) throws DynFormException {
		return baseService.existeTransaccionParaFormulario(formulario);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ComplexComponentDTO> getComplexComponents(Integer idTransaccion) throws DynFormException {
		return ListMapper.mapList(baseService.getComplexComponents(idTransaccion), mapper, ComplexComponentDTO.class);
	}

	@Override
	public Integer grabarTransaccion(TransaccionDTO trans) throws DynFormException {
		return baseService.grabarTransaccion(mapper.map(trans, com.egoveris.ffdd.model.model.TransaccionDTO.class));
	}

	@Override
	public void updateFormWebExpt(TransaccionDTO trans) throws DynFormException {
		baseService.updateFormWebExpt(mapper.map(trans, com.egoveris.ffdd.model.model.TransaccionDTO.class));
	}

	@Override
	public Integer grabarTransaccionValida(TransaccionValidaDTO validarDTO) throws DynFormValidarTransaccionException {
		return baseService.grabarTransaccionValida(mapper.map(validarDTO, com.egoveris.ffdd.model.model.TransaccionValidaDTO.class));
	}

}
