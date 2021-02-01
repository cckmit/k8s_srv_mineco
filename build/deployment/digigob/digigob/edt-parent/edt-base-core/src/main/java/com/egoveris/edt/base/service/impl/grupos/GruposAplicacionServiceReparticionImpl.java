package com.egoveris.edt.base.service.impl.grupos;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.egoveris.edt.base.service.grupos.IGruposAplicacionService;
import com.egoveris.sharedsecurity.base.model.Reparticion;
import com.egoveris.sharedsecurity.base.repository.impl.ReparticionEDTRepository;

/**
 * Implementación que retorna como grupos únicamente la repartición del usuario
 * 
 * @author alelarre
 *
 */
public class GruposAplicacionServiceReparticionImpl implements IGruposAplicacionService {

  @Autowired
  ReparticionEDTRepository reparticionRepository;
  
  @Override
  public List<String> buscarGruposUsuarioAplicacion(String user) {
    List<String> result = new ArrayList<>(1);
    Reparticion repa = reparticionRepository.obtenerReparticionUsername(user);
    if (repa != null) {
      result.add(repa.getCodigo().trim());
    }
    return result;
  }

}