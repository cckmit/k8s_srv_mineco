package com.egoveris.edt.base.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.edt.base.model.eu.Aplicacion;
import com.egoveris.edt.base.model.eu.AplicacionDTO;
import com.egoveris.edt.base.repository.eu.AplicacionRepository;
import com.egoveris.edt.base.service.IAplicacionService;
import com.egoveris.shared.map.ListMapper;

@Service("aplicacionesService")
@Transactional
public class AplicacionServiceImpl implements IAplicacionService {

  private DozerBeanMapper mapper = new DozerBeanMapper();

  @Autowired
  AplicacionRepository aplicacionRepository;

  @SuppressWarnings("unchecked")
  @Override
  public List<AplicacionDTO> getAll() {
    return ListMapper.mapList(aplicacionRepository.findAllByOrderByNombreAplicacionAsc(), mapper,
        AplicacionDTO.class);
  }

  @Override
  public AplicacionDTO getAplicacionPorNombre(String nombreAplicacion) {
    AplicacionDTO retorno = null;
    final Aplicacion resultado = aplicacionRepository.findByNombreAplicacion(nombreAplicacion);
    if (resultado != null) {
      retorno = mapper.map(resultado, AplicacionDTO.class);
    }
    return retorno;
  }

  @Override
  public AplicacionDTO getAplicacionPorId(Integer idAplicacion) {
    AplicacionDTO retorno = null;
    final Aplicacion resultado = aplicacionRepository.findByIdAplicacion(idAplicacion);
    if (resultado != null) {
      retorno = mapper.map(resultado, AplicacionDTO.class);
    }
    return retorno;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<AplicacionDTO> getTodasLasAplicaciones() {
    return ListMapper.mapList(aplicacionRepository.findByVisibleTrue(), mapper,
        AplicacionDTO.class);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<AplicacionDTO> buscarAplicaciones(List<Integer> listaIdAplicacion) {

    List<Aplicacion> allApps = aplicacionRepository.findByVisibleTrue();
    List<Aplicacion> retorno = new ArrayList<>();

    if (allApps != null) {
      // Recorro todas las aplicaciones para ver cuales son las
      // que el usuario tiene configuradas para su vista
      for (Integer idAplicacion : listaIdAplicacion) {
        for (Aplicacion aplicacion : allApps) {
          if (idAplicacion.equals(aplicacion.getIdAplicacion())) {
            retorno.add(aplicacion);
            break;
          }
        }
      }
    }

    return ListMapper.mapList(retorno, mapper, AplicacionDTO.class);

  }

}