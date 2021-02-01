package com.egoveris.edt.base.service.impl.cargo;

import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.edt.base.repository.eu.cargo.CargoHistRepository;
import com.egoveris.edt.base.service.usuario.ICargoHistService;
import com.egoveris.shared.map.ListMapper;
import com.egoveris.sharedsecurity.base.model.cargo.CargoHistoricoDTO;

@Service("cargoHistService")
@Transactional
public class CargoHistServiceImpl implements ICargoHistService {

  private DozerBeanMapper mapper = new DozerBeanMapper();

  @Autowired
  private CargoHistRepository cargoHistRepository;

  @SuppressWarnings("unchecked")
  @Override
  public List<CargoHistoricoDTO> getHistorial(Integer id) {
    return ListMapper.mapList(cargoHistRepository.findByRevisionOrderByFechaModificacionDesc(id),
        mapper, CargoHistoricoDTO.class);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<CargoHistoricoDTO> getHistoriales() {
    return ListMapper.mapList(cargoHistRepository.findAllByOrderByFechaModificacionDesc(), mapper,
        CargoHistoricoDTO.class);
  }

}
