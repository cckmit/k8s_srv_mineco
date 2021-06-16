package com.egoveris.sharedsecurity.base.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.sharedsecurity.base.model.DatosUsuario;
import com.egoveris.sharedsecurity.base.model.SectorUsuario;
import com.egoveris.sharedsecurity.base.model.sector.SectorUsuarioDTO;
import com.egoveris.sharedsecurity.base.repository.impl.DatosUsuarioRepository;
import com.egoveris.sharedsecurity.base.repository.impl.SectorUsuarioEDTRepository;
import com.egoveris.sharedsecurity.base.service.ISectorUsuarioService;

@Service("sectorUsuarioService")
@Transactional("jpaTransactionManagerSec")
public class SectorUsuarioServiceImpl implements ISectorUsuarioService {

  private DozerBeanMapper mapper = new DozerBeanMapper();

  @Autowired
  private SectorUsuarioEDTRepository sectorUsuarioRepository;
  @Autowired
  private DatosUsuarioRepository datosUsuarioRepository;

  @Override
  public void vincularSectorUsuario(SectorUsuarioDTO sectorUsuario) {
    sectorUsuarioRepository.save(mapper.map(sectorUsuario, SectorUsuario.class));
  }

  @Override
  public void desvincularSectorUsuario(String userName) {
    SectorUsuario sectorUsuario = sectorUsuarioRepository
        .findByNombreUsuarioAndEstadoTrue(userName);
    if (sectorUsuario != null) {
      sectorUsuario.setEstado(false);
      sectorUsuarioRepository.save(sectorUsuario);
    }
  }

  @Override
  public Boolean sectorMesaActualizado(String userName) {
    DatosUsuario du = datosUsuarioRepository.findByUsuario(userName);
    if (du.getCambiarMesa() != null) {
      return !du.getCambiarMesa();
    }

    return true;
  }

  @Override
  public void modificarSectorUsuario(SectorUsuarioDTO sectorUsuario) {
    sectorUsuarioRepository.save(mapper.map(sectorUsuario, SectorUsuario.class));
  }

  @Override
  public SectorUsuarioDTO getByUsername(String username) {
    SectorUsuario result = sectorUsuarioRepository.findByNombreUsuarioAndEstadoTrue(username);
    if (result != null) {
      return mapper.map(result,  SectorUsuarioDTO.class);
    } else {
      return null;
    }
  }

  @Override
  public List<String> obtenerUsernamePorSector(Integer idSector) {
    List<String> retorno = new ArrayList<>();
    List<SectorUsuario> lstSectorUsuario = sectorUsuarioRepository
        .findBySector_IdAndEstadoTrue(idSector);

    for (SectorUsuario sctrUsr : lstSectorUsuario) {
      retorno.add(sctrUsr.getNombreUsuario());
    }

    return retorno;
  }

}