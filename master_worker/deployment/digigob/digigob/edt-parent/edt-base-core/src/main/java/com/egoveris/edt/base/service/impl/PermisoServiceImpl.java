package com.egoveris.edt.base.service.impl;

import java.util.Comparator;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.edt.base.model.eu.Permiso;
import com.egoveris.edt.base.repository.eu.PermisoRepository;
import com.egoveris.edt.base.service.IPermisoService;
import com.egoveris.shared.map.ListMapper;
import com.egoveris.sharedsecurity.base.model.PermisoDTO;

@Service("permisoService")
@Transactional
public class PermisoServiceImpl implements IPermisoService {

  private DozerBeanMapper mapper = new DozerBeanMapper();

  @Autowired
  private PermisoRepository permisoRepository;
  @Value("${permisoAsignador}")
  private String permisoAsignador;

  @SuppressWarnings("unchecked")
  @Override
  public List<PermisoDTO> obtenerPermisos() {
    return ListMapper.mapList(permisoRepository.findAll(), mapper, PermisoDTO.class);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<PermisoDTO> filtrarListaPermisosPorSistemaYGrupoUsuario(String nombreGrupoUsuario,
      String sistema) {
    if ("Todos".equals(sistema)) {
      return ListMapper.mapList(
          permisoRepository.findByGrupoPermiso_GrupoUsuario(nombreGrupoUsuario), mapper,
          PermisoDTO.class);
    } else {
      return ListMapper.mapList(
          permisoRepository.findByGrupoPermiso_GrupoUsuarioAndSistema(nombreGrupoUsuario, sistema),
          mapper, PermisoDTO.class);
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<PermisoDTO> filtrarListaPermisosPorGrupoUsuario(String nombreGrupoUsuario) {
    return ListMapper.mapList(
        this.permisoRepository.findByGrupoPermiso_GrupoUsuario(nombreGrupoUsuario), mapper,
        PermisoDTO.class);
  }

  @Override
  public List<String> filtrarListaAplicacionesGrupos() {
    return permisoRepository.filtrarListaAplicacionesGrupos();
  }

  @Override
  public PermisoDTO obtenerPermisoAsignador() {
    Permiso result = permisoRepository.findByPermiso(this.permisoAsignador);
    if (result != null) {
      return mapper.map(result, PermisoDTO.class);
    } else {
      return null;
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<PermisoDTO> filtrarListaPermisosPorSistema(String sistema) {
    return ListMapper.mapList(permisoRepository.findBySistema(sistema), mapper, PermisoDTO.class);
  }
}

class ComparatorSistema implements Comparator<Permiso> {

  @Override
  public int compare(Permiso o1, Permiso o2) {

    String sistema1 = o1.getSistema();
    String sistema2 = o2.getSistema();
    int resultado = sistema1.toUpperCase().compareTo(sistema2.toUpperCase());
    if (resultado != 0) {
      return resultado;
    }
    sistema1 = o1.getPermiso();
    sistema2 = o2.getPermiso();
    resultado = sistema1.toUpperCase().compareTo(sistema2.toUpperCase());
    if (resultado != 0) {
      return resultado;
    }

    return resultado;
  }

}