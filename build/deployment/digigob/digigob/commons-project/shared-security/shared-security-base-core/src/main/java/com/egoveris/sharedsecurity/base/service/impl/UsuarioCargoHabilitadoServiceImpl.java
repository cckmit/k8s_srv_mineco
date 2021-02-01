package com.egoveris.sharedsecurity.base.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.shared.map.ListMapper;
import com.egoveris.sharedsecurity.base.model.Cargo;
import com.egoveris.sharedsecurity.base.model.Permiso;
import com.egoveris.sharedsecurity.base.model.Rol;
import com.egoveris.sharedsecurity.base.model.SectorUsuario;
import com.egoveris.sharedsecurity.base.model.UsuarioReparticionHabilitadaDTO;
import com.egoveris.sharedsecurity.base.model.cargo.CargoDTO;
import com.egoveris.sharedsecurity.base.model.sector.SectorUsuarioDTO;
import com.egoveris.sharedsecurity.base.repository.CargoRepository;
import com.egoveris.sharedsecurity.base.repository.SectorUsuarioSecurityRepository;
import com.egoveris.sharedsecurity.base.repository.UsuarioCargosHabilitadosRepository;
import com.egoveris.sharedsecurity.base.service.IUsuarioCargoHabilitadoService;

@Service("usuarioCargoHabilitadoService")
@Transactional("jpaTransactionManagerSec")
public class UsuarioCargoHabilitadoServiceImpl
    implements IUsuarioCargoHabilitadoService {

  private DozerBeanMapper mapper = new DozerBeanMapper();

  @Autowired
  UsuarioCargosHabilitadosRepository usrRprtcnHbltdaRepository;
  @Autowired
  private SectorUsuarioSecurityRepository sectorUsuarioRepository;
  @Autowired
  private CargoRepository cargoRepository;

  @SuppressWarnings("unchecked")
  @Override
  public List<UsuarioReparticionHabilitadaDTO> obtenerCargosHabilitadosByUsername(
      String userName) {
    Date now = new Date();
    List<UsuarioReparticionHabilitadaDTO> resultado = ListMapper.mapList(usrRprtcnHbltdaRepository
        .findByNombreUsuarioAndReparticion_VigenciaHastaAfterAndReparticion_VigenciaDesdeBefore(
            userName, now, now), mapper, UsuarioReparticionHabilitadaDTO.class);    
    Set<UsuarioReparticionHabilitadaDTO> set = new HashSet<>(resultado);
    for (UsuarioReparticionHabilitadaDTO o : set) {
      o.setCargo(mapper.map(cargoRepository.findOne(o.getCargoId()),CargoDTO.class));
    }

    UsuarioReparticionHabilitadaDTO reparticionPropia = obtenerCargoHabilitadoByReparticionAsignadaAlUsuario(
        userName);
    set.add(reparticionPropia);
    return new ArrayList<>(set);
  }

  @Override
  public UsuarioReparticionHabilitadaDTO obtenerCargoHabilitadoByReparticionAsignadaAlUsuario(
      String userName) {
    UsuarioReparticionHabilitadaDTO reparticionPropia = null;
    SectorUsuario sectorUsuarioDelUsuario = sectorUsuarioRepository.findByNombreUsuarioAndEstadoTrue(userName);
    SectorUsuarioDTO sectorUsiario = mapper.map(sectorUsuarioDelUsuario, SectorUsuarioDTO.class);
    if (sectorUsuarioDelUsuario != null) {
      CargoDTO cargo = mapper.map(cargoRepository.findOne(sectorUsiario.getCargoId()),CargoDTO.class);
      reparticionPropia = new UsuarioReparticionHabilitadaDTO(userName,
    		  sectorUsiario.getSector().getReparticion(), sectorUsiario.getSector(), cargo);
    }
    return reparticionPropia;
  }

//  @Override
//  public List<String> buscarPermisosPorCargo(CargoDTO cargo){
//	  final List<String> listPermisos = new ArrayList<>();
//	    final Cargo cargoFetchPermisos = cargoRepository.getOne(cargo.getId());
//	    final List<Permiso> permisos = new ArrayList<>();
//	    for (final Rol rol : cargoFetchPermisos.getListaRoles()) {
//	      for (final Permiso permiso : rol.getListaPermisos()) {
//	        permisos.add(permiso);
//	      }
//	    }
//
//	    for (int j = 0; j < permisos.size(); j++) {
//	      final Permiso permiso = permisos.get(j);
//	      listPermisos.add(permiso.getPermiso());
//	    }
//	    return listPermisos;
//  }
}
