package com.egoveris.edt.base.service.impl.admin;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.edt.base.service.admin.IAdministracionSistemasService;
import com.egoveris.sharedsecurity.base.model.admin.AdministradorSistema;
import com.egoveris.sharedsecurity.base.repository.admin.AdministracionSistemaRepository;

@Service("administracionSistemasService")
@Transactional
public class AdministracionSistemasServiceImpl implements IAdministracionSistemasService {

  @Autowired
  private AdministracionSistemaRepository adminSistemaRepository;

  @Override
  public List<String> obtenerSistemasPorUsuario(String nombreUsuario) {
    List<String> result = new ArrayList<>();
    for (AdministradorSistema admin : adminSistemaRepository.findByNombreUsuario(nombreUsuario)) {
      result.add(admin.getSistema());
    }
    return result;
  }

}