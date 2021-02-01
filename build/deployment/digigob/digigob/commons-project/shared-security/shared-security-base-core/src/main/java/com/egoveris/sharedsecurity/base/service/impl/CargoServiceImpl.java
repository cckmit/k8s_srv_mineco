package com.egoveris.sharedsecurity.base.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.shared.map.ListMapper;
import com.egoveris.sharedsecurity.base.model.Cargo;
import com.egoveris.sharedsecurity.base.model.DatosUsuario;
import com.egoveris.sharedsecurity.base.model.Permiso;
import com.egoveris.sharedsecurity.base.model.Rol;
import com.egoveris.sharedsecurity.base.model.SectorUsuario;
import com.egoveris.sharedsecurity.base.model.UsuarioReparticionHabilitada;
import com.egoveris.sharedsecurity.base.model.cargo.CargoDTO;
import com.egoveris.sharedsecurity.base.model.reparticion.ReparticionDTO;
import com.egoveris.sharedsecurity.base.repository.CargoRepository;
import com.egoveris.sharedsecurity.base.repository.impl.DatosUsuarioRepository;
import com.egoveris.sharedsecurity.base.repository.impl.SectorUsuarioEDTRepository;
import com.egoveris.sharedsecurity.base.repository.impl.UsuarioReparticionHabilitadaRepository;
import com.egoveris.sharedsecurity.base.service.ICargoService;

@Service("cargoService")
@Transactional("jpaTransactionManagerSec")
public class CargoServiceImpl implements ICargoService {

  private final DozerBeanMapper mapper = new DozerBeanMapper();

  @Autowired
  private CargoRepository cargoRepository;
  @Autowired
  private DatosUsuarioRepository datosUsuarioRepository;
  @Autowired
  private SectorUsuarioEDTRepository sectorUsuarioRepository;
  @Autowired
  private UsuarioReparticionHabilitadaRepository userRepaHabilRepository;

  @SuppressWarnings("unchecked")
  @Override
  public List<CargoDTO> getCargosActivos() {
    final List<Cargo> cargos = cargoRepository.findAll();
//    for (final Cargo cargo : cargos) {
//      //cargo.getListaRoles().size();
//    }

    return ListMapper.mapList(cargos, mapper, CargoDTO.class);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<CargoDTO> getCargosActivosVigentes() {
    List<CargoDTO> retorno = null;
    List<Cargo> resultado = cargoRepository.findByVigenteTrue();
    if (resultado != null) {
      retorno = ListMapper.mapList(resultado, mapper, CargoDTO.class);
    }
    return retorno;

  }

  @Override
  public CargoDTO getCargoByUsuario(final String username) {
    CargoDTO retorno = null;
    DatosUsuario result = datosUsuarioRepository.findByUsuario(username);
    if (result != null) {
      retorno = mapper.map(result.getCargoAsignado(), CargoDTO.class);
    }
    return retorno;
  }

  @Override
  public boolean eliminar(final CargoDTO cargo) {
    boolean cargosEnRepartciones = false;
    boolean cargosEnSectorUsuario = false;

    final List<UsuarioReparticionHabilitada> lstUsrRpaHbl = userRepaHabilRepository
        .findByCargoId(cargo.getId());
    if (lstUsrRpaHbl != null && !lstUsrRpaHbl.isEmpty()) {
      cargosEnRepartciones = true;
    }
    final List<SectorUsuario> lstSectorUsuario = sectorUsuarioRepository
        .findByCargoId(cargo.getId());
    if (lstSectorUsuario != null && !lstSectorUsuario.isEmpty()) {
      cargosEnSectorUsuario = true;
    }

    if (!cargosEnRepartciones && !cargosEnSectorUsuario) {
      final Cargo cargoToBeDeleted = mapper.map(cargo, Cargo.class);
      cargoToBeDeleted.setFechaModificacion(new Date());
      cargoRepository.delete(cargoToBeDeleted);
      return true;
    }
    return false;
  }

  @Override
  public void save(final CargoDTO cargo) {
    final Cargo cargoToBeSaved = mapper.map(cargo, Cargo.class);
    cargoToBeSaved.setFechaModificacion(new Date());
    cargoRepository.save(cargoToBeSaved);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<CargoDTO> buscarCargosPorReparticion(final ReparticionDTO reparticion) {
    return ListMapper.mapList(
        cargoRepository.findByIdReparticionAndVigenteTrue(reparticion.getId()), mapper,
        CargoDTO.class);
  }

//  @Override
//  public List<String> buscarPermisosPorCargo(final CargoDTO cargo) {
//    final List<String> listPermisos = new ArrayList<>();
//
//    final Cargo cargoFetchPermisos = cargoRepository.getOne(cargo.getId());
//    final List<Permiso> permisos = new ArrayList<>();
//    for (final Rol rol : cargoFetchPermisos.getListaRoles()) {
//      for (final Permiso permiso : rol.getListaPermisos()) {
//      	// Evitar duplicidad
//      	if (!permisos.contains(permiso)) {
//      		permisos.add(permiso);
//      	}
//      }
//    }
//
//    for (int j = 0; j < permisos.size(); j++) {
//      final Permiso permiso = permisos.get(j);
//      listPermisos.add(permiso.getPermiso());
//    }
//    return listPermisos;
//  }

  @Override
  public CargoDTO obtenerCargoUsuario(final String usuario) {
    final Cargo cargo = cargoRepository.obtenerCargoUsuario(usuario);
    return mapper.map(cargo, CargoDTO.class);
  }

  @Override
  public CargoDTO obtenerById(final Integer id) {
    CargoDTO retorno = null;
    final Cargo resultado = cargoRepository.findOne(id);
    if (resultado != null) {
      retorno = mapper.map(resultado, CargoDTO.class);
    }
    return retorno;
  }

	/**
	 * Gets the cargos by rol id.
	 *
	 * @return the cargos by rol id
	 */
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<CargoDTO> getCargosByRolId(Integer id) {
//		return ListMapper.mapList(cargoRepository.findByListaRoles_Id(id), mapper, CargoDTO.class);
//	}

}
