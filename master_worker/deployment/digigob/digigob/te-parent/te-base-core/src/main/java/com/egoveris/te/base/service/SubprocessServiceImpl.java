package com.egoveris.te.base.service;

import java.util.List;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.te.base.model.SubProceso;
import com.egoveris.te.base.model.SubProcesoDTO;
import com.egoveris.te.base.model.SubProcesoOperacion;
import com.egoveris.te.base.model.SubProcesoOperacionDTO;
import com.egoveris.te.base.model.TrataDTO;
import com.egoveris.te.base.model.trata.Trata;
import com.egoveris.te.base.repository.SubProcesoOperacionRepository;
import com.egoveris.te.base.repository.SubProcesoRepository;
import com.egoveris.te.base.util.BloqueoOperacion;
import com.egoveris.te.base.util.TipoTramiteEnum;

@Service
@Transactional
public class SubprocessServiceImpl implements SubprocessService {
  
  private static final String SOLICITUD_START_TYPE = "SolProductOp";
  private static final String SOLICITUD_PROC_NAME = "SolProductOp";
  private static final String SIN_RESERVA = "SIN RESERVA";

  private static final Logger logger = LoggerFactory.getLogger(SubprocessServiceImpl.class);
  
  @Autowired
  private SubProcesoOperacionRepository subProcesoOperacionRepository;
  
  @Autowired
  private SubProcesoRepository subProcesoRepository;
  
  @Autowired
  private TrataService trataService;
  
  @Autowired
  private TipoReservaService tipoReservaService;
  
  @Autowired
  @Qualifier("teCoreMapper")
  private Mapper mapper;
  
  @Override
  public SubProcesoOperacionDTO getSubprocessFromEE(Long idEe) {
    if (logger.isDebugEnabled()) {
      logger.debug("getSubprocessFromEE(idEe={}) - start", idEe);
    }
    
    SubProcesoOperacionDTO subprocesoOperacionDTO = null;
    SubProcesoOperacion subprocesoOperacion = subProcesoOperacionRepository.findByExpedienteElectronico(idEe);
    
    if (subprocesoOperacion != null) {
      subprocesoOperacionDTO = mapper.map(subprocesoOperacion, SubProcesoOperacionDTO.class);
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("getSubprocessFromEE(SubProcesoOperacionDTO) - end - return value={}", subprocesoOperacionDTO);
    }
    
    return subprocesoOperacionDTO;
  }

  @Override
  public SubProcesoDTO getSubprocessFromId(Long id) {
    if (logger.isDebugEnabled()) {
      logger.debug("getSubprocessFromId(id={}) - start", id);
    }
    
    SubProcesoDTO subprocessDTO = null;
    
    if (id != null) {
      SubProceso subprocess = subProcesoRepository.findById(id);
      
      if (subprocess != null) {
        subprocessDTO = mapper.map(subprocess, SubProcesoDTO.class);
      }
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("getSubprocessFromId(SubProcesoDTO) - end - return value={}", subprocessDTO);
    }
    
    return subprocessDTO;
  }
  
  public SubProcesoDTO getSolProductSubprocessForState(String stateFlow, String stateName, String username) {
    if (logger.isDebugEnabled()) {
      logger.debug("getSolicitudSubprocessForState(stateFlow={}, stateName={}) - start", stateFlow, stateName);
    }
    
    SubProcesoDTO solicitudSubprocess;
    
    List<SubProceso> solicitudSubprocessList = subProcesoRepository.findSolicitudSubprocess(stateFlow, stateName,
        SOLICITUD_START_TYPE, SOLICITUD_PROC_NAME);
    
    // If SolProductOp subprocess exists, return it
    if (solicitudSubprocessList != null && !solicitudSubprocessList.isEmpty()) {
      solicitudSubprocess = mapper.map(solicitudSubprocessList.get(0), SubProcesoDTO.class);
    }
    // If SolProductOp subprocess doesn't exists, create it
    else {
      // Searchs for SolProductOp trata type
      TrataDTO trataDTO = trataService.buscarTrataByCodigo(SOLICITUD_PROC_NAME);
      
      // If doesn't exists, create it
      if (trataDTO == null) {
        trataService.darAltaTrata(generateSolicitudTrataDTO(), username);
        trataDTO = trataService.buscarTrataByCodigo(SOLICITUD_PROC_NAME);
      }
      
      SubProceso subprocessEnt = new SubProceso();
      subprocessEnt.setLockType(BloqueoOperacion.NINGUNO.getValue());
      subprocessEnt.setStartType(SOLICITUD_START_TYPE);
      subprocessEnt.setStateFlow(stateFlow);
      subprocessEnt.setStateName(stateName);
      subprocessEnt.setVersion(0);
      subprocessEnt.setVersionProcedure(0);
      
      Trata trata = new Trata();
      trata.setId(trataDTO.getId());
      subprocessEnt.setTramite(trata);
      
      subprocessEnt = subProcesoRepository.save(subprocessEnt);
      solicitudSubprocess = mapper.map(subprocessEnt, SubProcesoDTO.class);
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("getSolicitudSubprocessForState(SubProcesoDTO) - end - return value={}", solicitudSubprocess);
    }
    
    return solicitudSubprocess;
  }
  
  /**
   * Generates the SolProductOp trata type DTO
   * and returns it
   * @return
   */
  private TrataDTO generateSolicitudTrataDTO() {
    TrataDTO trataDTO = new TrataDTO();
    trataDTO.setCodigoTrata(SOLICITUD_PROC_NAME);
    trataDTO.setEstado("Alta");
    trataDTO.setTipoReserva(tipoReservaService.buscarTipoReserva(SIN_RESERVA));
    trataDTO.setEsAutomatica(true);
    trataDTO.setEsManual(true);
    trataDTO.setWorkflow("solicitud");
    trataDTO.setTipoActuacion("EX");
    trataDTO.setEsInterno(true);
    trataDTO.setEsExterno(true);
    trataDTO.setEsNotificableTad(false);
    trataDTO.setEsEnvioAutomaticoGT(false);
    trataDTO.setIntegracionSisExt(false);
    trataDTO.setIntegracionAFJG(false);
    trataDTO.setDescripcion("Tramitacion producto generica");
    trataDTO.setTipoTramite(TipoTramiteEnum.AMBOS.getValue());
    
    return trataDTO;
  }
}
