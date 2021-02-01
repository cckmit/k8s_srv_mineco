package com.egoveris.edt.base.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.egoveris.edt.base.repository.TareasRepository;
import com.egoveris.edt.base.service.ITareasService;
import com.egoveris.edt.base.service.grupos.IGruposAplicacionService;

@Service
public class TareasServiceBaseImpl implements ITareasService {

  private TareasRepository tareaRepository;
  private IGruposAplicacionService gruposAplicacionService;

  public void setTareaRepository(TareasRepository tareaRepository) {
    this.tareaRepository = tareaRepository;
  }

  @Override
  public List<Date> buscarTareasPorUsuario(String username) {
    List<Date> listaFechas;
    listaFechas = this.tareaRepository.buscarTareasPorUsuarioSistemas(username);
    return listaFechas;
  }

  @Override
  public List<Date> buscarTareasBuzonGrupal(String userName, List<String> grupos) {
    return this.tareaRepository.buscarTareasBuzonGrupal(userName, grupos);
  }

  @Override
  public List<String> buscarGruposUsuarioAplicacion(String user) {
    return this.gruposAplicacionService.buscarGruposUsuarioAplicacion(user);
  }

  public void setGruposAplicacionService(IGruposAplicacionService gruposAplicacionService) {
    this.gruposAplicacionService = gruposAplicacionService;
  }

}
