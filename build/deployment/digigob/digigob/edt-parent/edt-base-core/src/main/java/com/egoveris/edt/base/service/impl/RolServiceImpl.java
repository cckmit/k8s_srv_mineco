package com.egoveris.edt.base.service.impl;

import java.util.Date;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.edt.base.model.eu.Rol;
import com.egoveris.edt.base.model.eu.usuario.RolDTO;
import com.egoveris.edt.base.repository.eu.RolRepository;
import com.egoveris.edt.base.service.IRolService;
import com.egoveris.shared.map.ListMapper;
import com.egoveris.sharedsecurity.base.repository.CargoRepository;

@Service("rolService")
@Transactional
public class RolServiceImpl implements IRolService {

  private DozerBeanMapper mapper = new DozerBeanMapper();

  @Autowired
  RolRepository rolRepository;
  @Autowired
  CargoRepository cargoRepository;

  @SuppressWarnings("unchecked")
  @Override
  @Transactional
  public List<RolDTO> getRolesActivos() {
    List<Rol> roles = rolRepository.findAll();
    for (Rol rol : roles) {
      rol.getListaPermisos().size();
    }
    return ListMapper.mapList(roles, mapper, RolDTO.class);
  }

  @Override
  public List<RolDTO> getRolesOcultos() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<RolDTO> getRolesOcultosVigentes() {
    // TODO Auto-generated method stub
    return null;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<RolDTO> getRolesVigentes() {
    return ListMapper.mapList(rolRepository.findByEsVigenteTrue(), mapper, RolDTO.class);
  }

  @Override
  public boolean eliminar(RolDTO rol) {
    Rol rolToBeDeleted = mapper.map(rol, Rol.class);

    boolean cargoTieneRol = false;
//    List<Cargo> cargos = cargoRepository.findByListaRoles_Id(rol.getId());
//    if (!cargos.isEmpty()) {
//      cargoTieneRol = true;
//    }

//    if (!cargoTieneRol) {
      rolToBeDeleted.setFechaModificacion(new Date());
      rolRepository.delete(rolToBeDeleted);
 //   }
    return cargoTieneRol;
  }

  @Override
  public void save(RolDTO rol) {
    Rol rolToBeSaved = mapper.map(rol, Rol.class);

    rolToBeSaved.setFechaModificacion(new Date());
    rolRepository.save(rolToBeSaved);
  }

  @Override
  public void refreshCache() {
    // TODO Auto-generated method stub

  }
  
  @Override
  public RolDTO getRolByRolNombre(String nombre) {
		return mapper.map(rolRepository.findByRolNombre(nombre), RolDTO.class);
  	
  }
  
  @Override
  public RolDTO getRolById(Integer id) {
  	return mapper.map(rolRepository.findOne(id), RolDTO.class);
  }

}