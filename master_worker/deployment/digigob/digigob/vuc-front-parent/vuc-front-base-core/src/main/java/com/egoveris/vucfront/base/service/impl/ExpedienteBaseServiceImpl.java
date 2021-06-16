package com.egoveris.vucfront.base.service.impl;

import java.util.Date;
import java.util.List;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.shared.map.ListMapper;
import com.egoveris.vucfront.base.model.ExpedienteBase;
import com.egoveris.vucfront.base.model.ExpedienteDocumento;
import com.egoveris.vucfront.base.model.ExpedienteFamiliaSolicitud;
import com.egoveris.vucfront.base.repository.ExpedienteBaseRepository;
import com.egoveris.vucfront.base.repository.ExpedienteDocumentoRepository;
import com.egoveris.vucfront.base.repository.ExpedienteFamiliaSolicitudRepository;
import com.egoveris.vucfront.model.model.ExpedienteBaseDTO;
import com.egoveris.vucfront.model.model.ExpedienteDocumentoDTO;
import com.egoveris.vucfront.model.model.ExpedienteFamiliaSolicitudDTO;
import com.egoveris.vucfront.model.service.ExpedienteBaseService;

@Service
public class ExpedienteBaseServiceImpl implements ExpedienteBaseService {
	private static final Logger LOG = LoggerFactory.getLogger(ExpedienteBaseServiceImpl.class);

	@Autowired
	@Qualifier("vucMapper")
	private Mapper mapper;
	
	@Autowired
	private ExpedienteBaseRepository expedienteRepository;

	@Value("${acronimo.pago.documento:CP}")
	private String acronimoDocumentoPago;
	
	@Autowired
	private ExpedienteFamiliaSolicitudRepository expedienteFamiliaSolicitudRepository;
	@Autowired
	private ExpedienteDocumentoRepository expedienteDocumentoRepository;

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ExpedienteFamiliaSolicitudDTO saveExpedienteFamiliaSolicitud(ExpedienteFamiliaSolicitudDTO expediente) {
		ExpedienteFamiliaSolicitud expedienteToBeSaved = mapper.map(expediente, ExpedienteFamiliaSolicitud.class);
		expedienteToBeSaved = expedienteFamiliaSolicitudRepository.save(expedienteToBeSaved);

		return mapper.map(expedienteToBeSaved, ExpedienteFamiliaSolicitudDTO.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<ExpedienteBaseDTO> buscarConsolidacionPorFecha(Date fechaDesde, Date fechaHasta, String organismoUsuario) {
	
		List<ExpedienteBase> resultado = this.expedienteRepository
				.findConsolidacionByFechas(fechaDesde, fechaHasta, organismoUsuario);
	
		return ListMapper.mapList(resultado, mapper, ExpedienteBaseDTO.class);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void saveExpedienteDocumento(ExpedienteDocumentoDTO eeDoc) {
		ExpedienteDocumento expedienteToBeSaved = mapper.map(eeDoc, ExpedienteDocumento.class);
		expedienteDocumentoRepository.save(expedienteToBeSaved);
	}
}
