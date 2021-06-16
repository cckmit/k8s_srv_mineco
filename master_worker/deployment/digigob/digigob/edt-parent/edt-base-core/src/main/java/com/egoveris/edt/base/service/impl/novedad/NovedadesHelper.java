package com.egoveris.edt.base.service.impl.novedad;

import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.egoveris.edt.base.model.eu.novedad.Novedad;
import com.egoveris.edt.base.model.eu.novedad.NovedadDTO;
import com.egoveris.edt.base.service.novedad.INovedadHelper;
import com.egoveris.edt.base.service.novedad.INovedadService;
import com.egoveris.shared.map.ListMapper;

@Service("novedadHelper")
public class NovedadesHelper implements INovedadHelper {

  private DozerBeanMapper mapper = new DozerBeanMapper();

  @Autowired
  private INovedadService novedadService;

  private List<Novedad> listaNovedades;

  @SuppressWarnings("unchecked")
  @Override
  @Scheduled(fixedRate = 600000)
  public void cachearListaNovedades() {
    listaNovedades = ListMapper.mapList(novedadService.getActivas(), mapper, NovedadDTO.class);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<NovedadDTO> obtenerTodas() {
    if (listaNovedades == null) {
      cachearListaNovedades();
    }

    return ListMapper.mapList(novedadService.getActivas(), mapper, NovedadDTO.class);
  }
}