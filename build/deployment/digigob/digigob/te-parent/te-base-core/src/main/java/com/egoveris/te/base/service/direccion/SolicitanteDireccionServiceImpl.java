package com.egoveris.te.base.service.direccion;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.shared.map.ListMapper;
import com.egoveris.te.base.model.direccion.DataProvincia;
import com.egoveris.te.base.model.direccion.DataProvinciaDTO;
import com.egoveris.te.base.model.direccion.SolicitanteDireccion;
import com.egoveris.te.base.model.direccion.SolicitanteDireccionDTO;
import com.egoveris.te.base.repository.direccion.DataProvinciaRepository;
import com.egoveris.te.base.repository.direccion.SolicitanteDireccionRepository;

@Service
@SuppressWarnings("unchecked")
public class SolicitanteDireccionServiceImpl implements SolicitanteDireccionService {
	
	@Autowired
	private DataProvinciaRepository dataProvinciaRepository;
	
	@Autowired
	private SolicitanteDireccionRepository solicitanteDireccionRepository;
	
	private DozerBeanMapper mapper = new DozerBeanMapper();
	
	@Override
	@Transactional
	public List<DataProvinciaDTO> getProvinciasCascade() {
		List<DataProvinciaDTO> dataProvinciaList = new ArrayList<>();
		List<DataProvincia> dataProvinciaEntList = dataProvinciaRepository.findAll();
		
		if (CollectionUtils.isNotEmpty(dataProvinciaEntList)) {
			dataProvinciaList = ListMapper.mapList(dataProvinciaEntList, mapper, DataProvinciaDTO.class);
		}
		
		return dataProvinciaList;
	}
	
	@Override
	@Transactional
	public void save(SolicitanteDireccionDTO solicitanteDireccionDTO) {
		SolicitanteDireccion entity = mapper.map(solicitanteDireccionDTO, SolicitanteDireccion.class);
		solicitanteDireccionRepository.save(entity);
	}

	@Override
	public SolicitanteDireccionDTO load(Integer idSolicitante) {
		SolicitanteDireccionDTO solicitanteDireccionDTO = null;
		
		SolicitanteDireccion entity;
		
		try {
			entity = solicitanteDireccionRepository.findOne(idSolicitante);
		} catch (Exception e) {
			entity = null;
		}
		
		if (entity != null) {
			solicitanteDireccionDTO = mapper.map(entity, SolicitanteDireccionDTO.class);
		}
		
		return solicitanteDireccionDTO;
	}
	
}
