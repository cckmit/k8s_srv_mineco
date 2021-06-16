package com.egoveris.edt.base.service.impl;

import java.util.Date;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.edt.base.model.eu.PeriodoLicencia;
import com.egoveris.edt.base.model.eu.PeriodoLicenciaDTO;
import com.egoveris.edt.base.repository.eu.PeriodoLicenciaRepository;
import com.egoveris.edt.base.service.IPeriodoLicenciaService;

@Service("periodoLicenciaService")
@Transactional
public class PeriodoLicenciaServiceImpl implements IPeriodoLicenciaService {

  private DozerBeanMapper mapper = new DozerBeanMapper();

  @Autowired
  private PeriodoLicenciaRepository periodoLicenciaRepository;

  @Override
  public PeriodoLicenciaDTO traerPeriodoLicenciaPorUserName(String userName) {
    PeriodoLicenciaDTO periodoLicencia = null;
  	
  	List<PeriodoLicencia> listPeriodoLicencia = periodoLicenciaRepository
        .getPeriodoLicencia(userName, PeriodoLicenciaDTO.TERMINADO);
    
    if (listPeriodoLicencia != null && !listPeriodoLicencia.isEmpty()) {
    	periodoLicencia = mapper.map(listPeriodoLicencia.get(0), PeriodoLicenciaDTO.class);
    }
    
    return periodoLicencia;
  }

  @Override
  public void cancelarLicencia(PeriodoLicenciaDTO periodoLicencia) {
    PeriodoLicencia periodoLicenciaToCancel = mapper.map(periodoLicencia, PeriodoLicencia.class);
    periodoLicenciaToCancel.setFechaCancelacion(new Date());
    periodoLicenciaToCancel.setCondicionPeriodo(PeriodoLicenciaDTO.TERMINADO);
    periodoLicenciaRepository.save(periodoLicenciaToCancel);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void save(PeriodoLicenciaDTO periodoLicencia) {
    periodoLicenciaRepository.save(mapper.map(periodoLicencia, PeriodoLicencia.class));
  }

	@Override
	public void terminarLicenciasPasadas() {
		periodoLicenciaRepository.terminarLicenciasPasadas(PeriodoLicenciaDTO.TERMINADO, new Date());
	}

}