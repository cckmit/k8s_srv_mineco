package com.egoveris.deo.base.service.impl;

import com.egoveris.deo.base.model.Plantilla;
import com.egoveris.deo.base.repository.PlantillaRepository;
import com.egoveris.deo.base.repository.UsuarioPlantillaRepository;
import com.egoveris.deo.base.service.IPlantillaFacade;
import com.egoveris.deo.model.model.PlantillaDTO;
import com.egoveris.deo.model.model.UsuarioPlantillaDTO;
import com.egoveris.shared.map.ListMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PlantillaFacadeImpl implements IPlantillaFacade {
  /**
   * Logger for this class
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(PlantillaFacadeImpl.class);

  @Autowired
  private PlantillaRepository plantillaRepo;

  @Autowired
  private UsuarioPlantillaRepository usuarioPlantillaRepo;
  
  @Autowired
  @Qualifier("deoCoreMapper")
  private Mapper mapper;

  private List<PlantillaDTO> listaPlantilla;

  private Set<UsuarioPlantillaDTO> listaUsuarioPlantilla;

  public void crear(String usuario, PlantillaDTO plantilla) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("crear(String, PlantillaDTO) - start"); //$NON-NLS-1$
    }

    UsuarioPlantillaDTO usuarioPlantilla = new UsuarioPlantillaDTO();
    usuarioPlantilla.setPlantilla(plantilla);
    usuarioPlantilla.setUsuario(usuario);

    this.listaUsuarioPlantilla = new HashSet<>();
    plantilla.setSetUsuarioPlantilla(listaUsuarioPlantilla);
    plantilla.addUsuarioPlantilla(usuarioPlantilla);

    // Seteo la fecha de creación de la plantilla
    plantilla.setFechaModificacion(new Date());

    this.plantillaRepo.save(this.mapper.map(plantilla, Plantilla.class));

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("crear(String, PlantillaDTO) - end"); //$NON-NLS-1$
    }
  }

  public void actualizar(PlantillaDTO plantilla) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("actualizar(PlantillaDTO) - start"); //$NON-NLS-1$
    }

    // Seteo la fecha de modificación de la plantilla
    plantilla.setFechaModificacion(new Date());

    this.plantillaRepo.save(this.mapper.map(plantilla, Plantilla.class));

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("actualizar(PlantillaDTO) - end"); //$NON-NLS-1$
    }
  }

  public void eliminar(PlantillaDTO plantilla) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("eliminar(PlantillaDTO) - start"); //$NON-NLS-1$
    }

    this.plantillaRepo.delete(this.mapper.map(plantilla, Plantilla.class));

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("eliminar(PlantillaDTO) - end"); //$NON-NLS-1$
    }
  }

  public List<PlantillaDTO> buscarPlantillaPorUsuarioPlantilla(String usuario) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarPlantillaPorUsuarioPlantilla(String) - start"); //$NON-NLS-1$
    }
    List<PlantillaDTO> resultList = new ArrayList<>();
    List<UsuarioPlantillaDTO> result = ListMapper.mapList(this.usuarioPlantillaRepo.findByUsuario(usuario), this.mapper, UsuarioPlantillaDTO.class);
    for(UsuarioPlantillaDTO up : result){
      PlantillaDTO p;
      resultList.add(up.getPlantilla());
    }
    this.listaPlantilla = resultList;
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarPlantillaPorUsuarioPlantilla(String) - end"); //$NON-NLS-1$
    }
    return listaPlantilla;
  }

  public PlantillaDTO buscarPlantillaPorId(Integer idPlantilla) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarPlantillaPorId(Integer) - start"); //$NON-NLS-1$
    }

    PlantillaDTO returnPlantillaDTO = this.mapper.map(this.plantillaRepo.findOne(idPlantilla), PlantillaDTO.class);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarPlantillaPorId(Integer) - end"); //$NON-NLS-1$
    }
    return returnPlantillaDTO;
  }

}
