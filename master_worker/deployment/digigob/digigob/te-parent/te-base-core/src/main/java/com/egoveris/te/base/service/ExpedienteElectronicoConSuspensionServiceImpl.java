package com.egoveris.te.base.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.shared.map.ListMapper;
import com.egoveris.te.base.model.ExpedienteElectronicoConSuspensionDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.expediente.ExpedienteElectronico;
import com.egoveris.te.base.model.expediente.ExpedienteElectronicoConSuspension;
import com.egoveris.te.base.repository.expediente.ExpedienteElectronicoConSuspensionRepository;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoConSuspensionService;
import com.egoveris.te.model.exception.ParametroIncorrectoException;

@Service
@Transactional
public class ExpedienteElectronicoConSuspensionServiceImpl
    implements ExpedienteElectronicoConSuspensionService {

  private Mapper mapper;

  @Autowired
  private ExpedienteElectronicoConSuspensionRepository eeConSuspensionRepository;

  public void bloquearExpedienteElectronicoTV(ExpedienteElectronicoDTO expedienteElectronico) {
    ExpedienteElectronicoConSuspension eeSuspendido = new ExpedienteElectronicoConSuspension();
    eeSuspendido.setEe(mapper.map(expedienteElectronico, ExpedienteElectronico.class));
    eeSuspendido.setUsuarioSuspension(expedienteElectronico.getUsuarioCreador());
    eeSuspendido.setFechaSuspension(new Date());
    eeSuspendido.setCodigoCaratula(expedienteElectronico.getCodigoCaratula());

    this.eeConSuspensionRepository.save(eeSuspendido);
  }

  public void actualizarExpedienteElectronicoTV(ExpedienteElectronicoConSuspensionDTO eeConSuspension)
      throws ParametroIncorrectoException {
    this.eeConSuspensionRepository.save(mapper.map(eeConSuspension, ExpedienteElectronicoConSuspension.class));
  }

  public void eliminarEEConSuspension(ExpedienteElectronicoConSuspensionDTO eeConSuspension)
      throws ParametroIncorrectoException {
    this.eeConSuspensionRepository
        .delete(mapper.map(eeConSuspension, ExpedienteElectronicoConSuspension.class));
  }

  @Override
  public ExpedienteElectronicoConSuspensionDTO obtenerEEConSuspensionPorCodigo(
      Long idEEConSuspension) throws ParametroIncorrectoException {
    return mapper.map(this.eeConSuspensionRepository.findOne(idEEConSuspension),
        ExpedienteElectronicoConSuspensionDTO.class);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<ExpedienteElectronicoConSuspensionDTO> obtenerAllEEConSuspension()
      throws ParametroIncorrectoException {

    return ListMapper.mapList(this.eeConSuspensionRepository.findAll(), mapper,
        ExpedienteElectronicoConSuspensionDTO.class);
  }

}