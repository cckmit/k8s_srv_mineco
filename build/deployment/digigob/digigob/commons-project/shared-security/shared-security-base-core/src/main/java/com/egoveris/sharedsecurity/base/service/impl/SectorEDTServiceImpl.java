package com.egoveris.sharedsecurity.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.sharedsecurity.base.model.Reparticion;
import com.egoveris.sharedsecurity.base.repository.impl.ReparticionEDTRepository;
import com.egoveris.sharedsecurity.base.repository.impl.SectorRepository;
import com.egoveris.sharedsecurity.base.service.ISectorEDTService;

@Service("sectorEDTService")
@Transactional("jpaTransactionManagerSec")
public class SectorEDTServiceImpl implements ISectorEDTService {

	@Autowired
	private SectorRepository sectorRepository;
	
	@Autowired
	private ReparticionEDTRepository reparticionRepository;

	@Override public List<String> obtenerSectores(String reparticion) {
		Reparticion repa = reparticionRepository.findByCodigo(reparticion);
		return sectorRepository.obtenerSectorByIdReparticion(repa.getId());
	}

}
