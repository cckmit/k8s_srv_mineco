package com.egoveris.vucfront.base.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.egoveris.shared.map.ListMapper;
import com.egoveris.vucfront.base.model.Grupo;
import com.egoveris.vucfront.base.model.TipoTramite;
import com.egoveris.vucfront.base.repository.GrupoRepository;
import com.egoveris.vucfront.base.repository.TipoTramiteRepository;
import com.egoveris.vucfront.base.service.ApplicationService;
import com.egoveris.vucfront.model.model.GrupoDTO;
import com.egoveris.vucfront.model.model.TipoTramiteDTO;

@Service
@Transactional
public class ApplicationServiceImpl implements ApplicationService {

  @Autowired
  @Qualifier("vucMapper")
  private Mapper mapper;

  @Autowired
  private GrupoRepository grupoRepository;
  @Autowired
  private TipoTramiteRepository tipoTramiteRepository;

  @SuppressWarnings("unchecked")
  @Override
  public List<GrupoDTO> getAllGrupos() {
    List<GrupoDTO> retorno = null;
    List<Grupo> resultado = grupoRepository.findAll();
    if (resultado != null) {
      retorno = ListMapper.mapList(resultado, mapper, GrupoDTO.class);
    }
    return retorno;
  }

  @Override
  public TipoTramiteDTO getTipoTramiteById(Long id) {
    TipoTramiteDTO retorno = null;
    TipoTramite resultado = tipoTramiteRepository.findOne(id);
    if (resultado != null) {
      retorno = mapper.map(resultado, TipoTramiteDTO.class);
    }
    return retorno;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<TipoTramiteDTO> getAllTipoTramite() {
    List<TipoTramiteDTO> retorno = new ArrayList<>();

    List<TipoTramite> result = tipoTramiteRepository.findAll();
    if (result != null) {
      retorno = ListMapper.mapList(result, mapper, TipoTramiteDTO.class);
    }

    return retorno;
  }

}
