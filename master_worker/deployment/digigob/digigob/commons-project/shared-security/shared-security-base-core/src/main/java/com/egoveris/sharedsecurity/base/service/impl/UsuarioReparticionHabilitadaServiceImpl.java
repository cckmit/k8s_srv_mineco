package com.egoveris.sharedsecurity.base.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.shared.map.ListMapper;
import com.egoveris.sharedsecurity.base.model.UsuarioReparticionHabilitada;
import com.egoveris.sharedsecurity.base.model.UsuarioReparticionHabilitadaDTO;
import com.egoveris.sharedsecurity.base.model.cargo.CargoDTO;
import com.egoveris.sharedsecurity.base.model.sector.SectorDTO;
import com.egoveris.sharedsecurity.base.model.sector.SectorUsuarioDTO;
import com.egoveris.sharedsecurity.base.repository.impl.UsuarioReparticionHabilitadaRepository;
import com.egoveris.sharedsecurity.base.service.ICargoService;
import com.egoveris.sharedsecurity.base.service.ISectorUsuarioService;
import com.egoveris.sharedsecurity.base.service.IUsuarioReparticionHabilitadaService;

@Service("usuarioReparticionHabilitadaService")
@Transactional("jpaTransactionManagerSec")
public class UsuarioReparticionHabilitadaServiceImpl
    implements IUsuarioReparticionHabilitadaService {

  private DozerBeanMapper mapper = new DozerBeanMapper();

  @Autowired
  UsuarioReparticionHabilitadaRepository usrRprtcnHbltdaRepository;
  @Autowired
  private ISectorUsuarioService sectorUsuarioService;
  @Autowired
  private ICargoService cargoService;

  @SuppressWarnings("unchecked")
  @Override
  public List<UsuarioReparticionHabilitadaDTO> obtenerReparticionesHabilitadasByUsername(
      String userName) {
		// Obtiene datos desde SADE_USR_REPA_HABILITADA
		List<UsuarioReparticionHabilitadaDTO> resultado = ListMapper.mapList(
				usrRprtcnHbltdaRepository.findByNombreUsuario(userName, new Date()), mapper,
				UsuarioReparticionHabilitadaDTO.class);

		Set<UsuarioReparticionHabilitadaDTO> set = new HashSet<>(resultado);
		// Obtiene datos desde SADE_SECTOR_USUARIO y obtiene el sector
		for (UsuarioReparticionHabilitadaDTO o : set) {
			o.setCargo(cargoService.obtenerById(o.getCargoId()));
		}

    UsuarioReparticionHabilitadaDTO reparticionPropia = obtenerReparticionHabilitadaByReparticionAsignadaAlUsuario(
        userName);
    set.add(reparticionPropia);
    return new ArrayList<>(set);
  }

  @Override
  public UsuarioReparticionHabilitadaDTO obtenerReparticionHabilitadaByReparticionAsignadaAlUsuario(
      String userName) {
    SectorDTO sectorPropioDelUsuario;
    UsuarioReparticionHabilitadaDTO reparticionPropia = null;
    SectorUsuarioDTO sectorUsuarioDelUsuario = sectorUsuarioService.getByUsername(userName);

    if (sectorUsuarioDelUsuario != null) {
      sectorPropioDelUsuario = sectorUsuarioDelUsuario.getSector();
      CargoDTO cargo = cargoService.obtenerById(sectorUsuarioDelUsuario.getCargoId());
      reparticionPropia = new UsuarioReparticionHabilitadaDTO(userName,
          sectorPropioDelUsuario.getReparticion(), sectorPropioDelUsuario, cargo);
    }
    return reparticionPropia;

  }

  @Override
  public boolean eliminarReparticionesHabilitadas(
      Collection<UsuarioReparticionHabilitadaDTO> reparticionesHabilitadas) {
    for (UsuarioReparticionHabilitadaDTO usuarioReparticionHabilitada : reparticionesHabilitadas) {
      usrRprtcnHbltdaRepository
          .delete(mapper.map(usuarioReparticionHabilitada, UsuarioReparticionHabilitada.class));
    }
    return true;
  }

  @Override
  public boolean guardarReparticionesHabilitadas(
      Collection<UsuarioReparticionHabilitadaDTO> reparticionesHabilitadas) {
    for (UsuarioReparticionHabilitadaDTO usuarioReparticionHabilitada : reparticionesHabilitadas) {
      usrRprtcnHbltdaRepository
          .save(mapper.map(usuarioReparticionHabilitada, UsuarioReparticionHabilitada.class));
    }
    return true;
  }

  @Override
	public List<UsuarioReparticionHabilitadaDTO> obtenerReparticionesHabilitadasByUsernameSinLaPropia(String userName) {
		@SuppressWarnings("unchecked")
		Set<UsuarioReparticionHabilitadaDTO> set = new HashSet<>(
				ListMapper.mapList(usrRprtcnHbltdaRepository.findByNombreUsuario(userName, new Date()), mapper,
						UsuarioReparticionHabilitadaDTO.class));
		for (UsuarioReparticionHabilitadaDTO o : set) {
			o.setCargo(cargoService.obtenerById(o.getCargoId()));
		}
		return new ArrayList<>(set);
	}

  @SuppressWarnings("unchecked")
  @Override
	public List<UsuarioReparticionHabilitadaDTO> obtenerReparticionesHabilitadasBySector(String codigoReparticion,
			String codigoSector) {
		Set<UsuarioReparticionHabilitadaDTO> set = new HashSet<>(ListMapper.mapList(
				usrRprtcnHbltdaRepository.findByReparticionCodigoAndSectorCodigo(codigoReparticion, codigoSector, new Date()),
				mapper, UsuarioReparticionHabilitadaDTO.class));
		return new ArrayList<>(set);
	}

  @Override
  public List<UsuarioReparticionHabilitadaDTO> obtenerReparticionesByCodigo(
      String codigoReparticion) {
    @SuppressWarnings("unchecked")
    Set<UsuarioReparticionHabilitadaDTO> set = new HashSet<>(ListMapper.mapList(
        usrRprtcnHbltdaRepository.findByReparticion_Codigo(codigoReparticion), mapper,
        UsuarioReparticionHabilitadaDTO.class));
    return new ArrayList<>(set);
  }

  @Override
	public List<UsuarioReparticionHabilitadaDTO> obtenerReparticionesHabilitadasByCodigoReparticion(
			String codigoReparticion) {
		@SuppressWarnings("unchecked")
		Set<UsuarioReparticionHabilitadaDTO> set = new HashSet<>(
				ListMapper.mapList(usrRprtcnHbltdaRepository.findByReparticionCodigo(codigoReparticion, new Date()), mapper,
						UsuarioReparticionHabilitadaDTO.class));
		return new ArrayList<>(set);
	}

  @Override
  public void eliminarReparticionHabilitada(
      UsuarioReparticionHabilitadaDTO reparticionHabilitada) {
    usrRprtcnHbltdaRepository
        .delete(mapper.map(reparticionHabilitada, UsuarioReparticionHabilitada.class));
  }

  @Override
  public void guardarReparticionHabilitada(
      UsuarioReparticionHabilitadaDTO usuarioReparticionHabilitada) {
    usrRprtcnHbltdaRepository
        .save(mapper.map(usuarioReparticionHabilitada, UsuarioReparticionHabilitada.class));

  }

  @Override
  public List<UsuarioReparticionHabilitadaDTO> getHistorico(String nombreUsuario) {
    // return usrRprtcnHbltdaRepository.getHistorico(nombreUsuario);
    return null;
  }
}
