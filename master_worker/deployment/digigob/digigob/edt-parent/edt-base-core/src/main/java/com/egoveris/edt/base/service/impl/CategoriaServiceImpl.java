package com.egoveris.edt.base.service.impl;

import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.edt.base.model.eu.Categoria;
import com.egoveris.edt.base.model.eu.CategoriaDTO;
import com.egoveris.edt.base.repository.eu.CategoriaRepository;
import com.egoveris.edt.base.service.ICategoriaService;
import com.egoveris.shared.map.ListMapper;

@Service("categoriaService")
@Transactional
public class CategoriaServiceImpl implements ICategoriaService {

  private DozerBeanMapper mapper = new DozerBeanMapper();

  @Autowired
  private CategoriaRepository categoriaRepository;

  @SuppressWarnings("unchecked")
  @Override
  public List<CategoriaDTO> getAll() {
    return ListMapper.mapList(categoriaRepository.findAll(), mapper, CategoriaDTO.class);
  }

  @Override
  public CategoriaDTO getById(Integer id) {
    CategoriaDTO retorno = null;
    final Categoria resultado = categoriaRepository.getOne(id);
    if (resultado != null) {
      retorno = mapper.map(resultado, CategoriaDTO.class);
    }
    return retorno;
  }
}