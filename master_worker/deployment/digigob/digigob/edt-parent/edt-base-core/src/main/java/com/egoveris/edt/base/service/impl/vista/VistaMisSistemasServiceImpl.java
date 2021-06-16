package com.egoveris.edt.base.service.impl.vista;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.edt.base.model.eu.AplicacionDTO;
import com.egoveris.edt.base.service.IAplicacionService;
import com.egoveris.edt.base.service.vista.IVistaMisSistemasService;

@Service("vistaMisSistemasService")
public class VistaMisSistemasServiceImpl implements IVistaMisSistemasService {

  private static Logger logger = LoggerFactory.getLogger(VistaMisSistemasServiceImpl.class);

  @Autowired
  private IAplicacionService aplicacionService;

  @Override
  public List<AplicacionDTO> obtenerVistaMisSistemas(List<Integer> listaIdsAplicaciones,
      String userName) {

    logger.debug("{} - Iniciando", userName);
    List<AplicacionDTO> listaAplicaciones = new ArrayList<>();
    // Primero: con la lista de id's de las aplicaciones configuradas para
    // el usuario para la vista de mis sistemas busco la aplicaciones
    if (listaIdsAplicaciones != null && !listaIdsAplicaciones.isEmpty()) {
      listaAplicaciones = this.aplicacionService.buscarAplicaciones(listaIdsAplicaciones);
    }

    logger.debug("{} - Fin - cantidad de sistemas: {}", userName, listaAplicaciones.size());
    return listaAplicaciones;
  }

  public void setAplicacionService(IAplicacionService aplicacionService) {
    this.aplicacionService = aplicacionService;
  }

}
