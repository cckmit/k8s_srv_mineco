package com.egoveris.deo.base.service.impl;

import com.egoveris.deo.base.model.FamiliaTipoDocumento;
import com.egoveris.deo.base.model.FamiliaTipoDocumentoAuditoria;
import com.egoveris.deo.base.model.TipoDocumento;
import com.egoveris.deo.base.repository.FamiliaTipoDocumentoAuditoriaRepository;
import com.egoveris.deo.base.repository.FamiliaTipoDocumentoRepository;
import com.egoveris.deo.base.repository.ReparticionHabilitadaRepository;
import com.egoveris.deo.base.service.FamiliaTipoDocumentoService;
import com.egoveris.deo.base.service.TipoDocumentoService;
import com.egoveris.deo.model.model.FamiliaTipoDocumentoDTO;
import com.egoveris.deo.model.model.ReparticionHabilitadaDTO;
import com.egoveris.deo.model.model.TipoDocumentoDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.shared.map.ListMapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class FamiliaTipoDocumentoServiceImpl implements FamiliaTipoDocumentoService {
  /**
   * Logger for this class
   */
  private static final Logger LOGGER = LoggerFactory
      .getLogger(FamiliaTipoDocumentoServiceImpl.class);

  @Autowired
  private FamiliaTipoDocumentoRepository familiaTipoDocumentoRepository;
  @Autowired
  private TipoDocumentoService tipoDocumentoServiceImpl;

  private FamiliaTipoDocumentoAuditoria familiaTipoDocumentoAuditoria;
  @Autowired
  private FamiliaTipoDocumentoAuditoriaRepository familiaTipoDocumentoAuditoriaRepository;

  @Autowired
  private ReparticionHabilitadaRepository reparticionHabilitadaRepository;
  @Autowired
  @Qualifier("deoCoreMapper")
  private Mapper mapper;

  public void guardarFamilia(List<FamiliaTipoDocumentoDTO> listaFamiliaAgregar,
      List<FamiliaTipoDocumentoDTO> listaFamiliasEliminar, FamiliaTipoDocumentoDTO familiaGenerica,
      String usuario) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug(
          "guardarFamilia(List<FamiliaTipoDocumentoDTO>, List<FamiliaTipoDocumentoDTO>, FamiliaTipoDocumentoDTO, String) - start"); //$NON-NLS-1$
    }

    if (listaFamiliaAgregar != null && !listaFamiliaAgregar.isEmpty()) {
      for (FamiliaTipoDocumentoDTO familiaTipoDocumento : listaFamiliaAgregar) {
        updateFamilia(familiaTipoDocumento, usuario);
      }
    }
    if (listaFamiliasEliminar != null && !listaFamiliasEliminar.isEmpty()) {
      this.eliminarFamilia(listaFamiliasEliminar, familiaGenerica, usuario);
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug(
          "guardarFamilia(List<FamiliaTipoDocumentoDTO>, List<FamiliaTipoDocumentoDTO>, FamiliaTipoDocumentoDTO, String) - end"); //$NON-NLS-1$
    }
  }

  public void updateFamilia(List<FamiliaTipoDocumentoDTO> listaFamiliaModificar,
      FamiliaTipoDocumentoDTO familiaGenerica, String usuario) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug(
          "updateFamilia(List<FamiliaTipoDocumentoDTO>, FamiliaTipoDocumentoDTO, String) - start"); //$NON-NLS-1$
    }

    if (listaFamiliaModificar != null && !listaFamiliaModificar.isEmpty()) {
      for (FamiliaTipoDocumentoDTO familiaTipoDocumento : listaFamiliaModificar) {
        updateFamilia(familiaTipoDocumento, usuario);
      }
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug(
          "updateFamilia(List<FamiliaTipoDocumentoDTO>, FamiliaTipoDocumentoDTO, String) - end"); //$NON-NLS-1$
    }
  }

  public void updateFamilia(FamiliaTipoDocumentoDTO familiaTipoDocumento, String usuario) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("updateFamilia(FamiliaTipoDocumentoDTO, String) - start"); //$NON-NLS-1$
    }

    this.familiaTipoDocumentoRepository
        .save(mapper.map(familiaTipoDocumento, FamiliaTipoDocumento.class));
    this.cargarAuditoriaFamilia(familiaTipoDocumento, usuario, Constantes.AUDITORIA_OP_ALTA);

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("updateFamilia(FamiliaTipoDocumentoDTO, String) - end"); //$NON-NLS-1$
    }
  }

  private void cargarAuditoriaFamilia(FamiliaTipoDocumentoDTO familiaTipoDocumento, String usuario,
      String estado) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("cargarAuditoriaFamilia(FamiliaTipoDocumentoDTO, String, String) - start"); //$NON-NLS-1$
    }

    familiaTipoDocumentoAuditoria = new FamiliaTipoDocumentoAuditoria(
        mapper.map(familiaTipoDocumento, FamiliaTipoDocumento.class), usuario, estado);
    this.familiaTipoDocumentoAuditoriaRepository.save(familiaTipoDocumentoAuditoria);

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("cargarAuditoriaFamilia(FamiliaTipoDocumentoDTO, String, String) - end"); //$NON-NLS-1$
    }
  }

  @SuppressWarnings("unchecked")
  public List<FamiliaTipoDocumentoDTO> traerfamilias() {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("traerfamilias() - start"); //$NON-NLS-1$
    }
    List<FamiliaTipoDocumentoDTO> returnList = ListMapper.mapList(
        this.familiaTipoDocumentoRepository.findAll(), mapper, FamiliaTipoDocumentoDTO.class);

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("traerfamilias() - end"); //$NON-NLS-1$
    }
    return returnList;
  }

  public List<String> traerNombresFamilias() {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("traerNombresFamilias() - start"); //$NON-NLS-1$
    }
    List<String> returnList = new ArrayList<>();
    List<FamiliaTipoDocumentoDTO> familias = ListMapper.mapList(
        this.familiaTipoDocumentoRepository.findAll(), this.mapper, FamiliaTipoDocumentoDTO.class);
    if (familias != null && !familias.isEmpty()) {
      for (FamiliaTipoDocumentoDTO f : familias) {
        returnList.add(f.getNombre());
      }
    }
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("traerNombresFamilias() - end"); //$NON-NLS-1$
    }
    return returnList;
  }

  @Override
  public List<String> traerNombresFamiliasByIdTipoDocumento(Integer idTipoDocumento) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("traerNombresFamiliasByIdTipoDocumento(Integer) - start"); //$NON-NLS-1$
    }

    List<String> returnList = new ArrayList<>();
    for (FamiliaTipoDocumento ftd : this.familiaTipoDocumentoRepository
        .traerNombresFamiliasByIdTipoDocumento(idTipoDocumento)) {
      returnList.add(ftd.getNombre());
    }
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("traerNombresFamiliasByIdTipoDocumento(Integer) - end"); //$NON-NLS-1$
    }
    return returnList;

  }

  /**
   * Este método cambia la asociación de cada tipo de documento de la familia a
   * eliminar por la familia por defecto y luego elimina la familia
   * 
   * @param familiaTipoDocumento
   * @param familiaInicial
   * @param usuario
   */
  @SuppressWarnings("unchecked")
  private void eliminarFamilia(List<FamiliaTipoDocumentoDTO> listaFamiliasEliminar,
      FamiliaTipoDocumentoDTO familiaInicial, String usuario) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug(
          "eliminarFamilia(List<FamiliaTipoDocumentoDTO>, FamiliaTipoDocumentoDTO, String) - start"); //$NON-NLS-1$
    }

    for (FamiliaTipoDocumentoDTO familiaTipoDocumento : listaFamiliasEliminar) {
      for (TipoDocumentoDTO aux : familiaTipoDocumento.getListaTipoDocumento()) {
        aux.setFamilia(familiaInicial);
        List<ReparticionHabilitadaDTO> listaR = ListMapper.mapList(reparticionHabilitadaRepository
                .findByTipoDocumento(mapper.map(aux, TipoDocumento.class)), mapper,
                TipoDocumentoDTO.class);
        Set<ReparticionHabilitadaDTO> setR = new HashSet<>(listaR);
        aux.setListaReparticiones(setR);
        tipoDocumentoServiceImpl.modificarTipoDocumento(aux, usuario);
      }
      this.familiaTipoDocumentoRepository
          .delete(mapper.map(familiaTipoDocumento, FamiliaTipoDocumento.class));
      this.cargarAuditoriaFamilia(familiaTipoDocumento, usuario, Constantes.AUDITORIA_OP_BAJA);
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug(
          "eliminarFamilia(List<FamiliaTipoDocumentoDTO>, FamiliaTipoDocumentoDTO, String) - end"); //$NON-NLS-1$
    }
  }

  public FamiliaTipoDocumentoDTO traerFamiliaTipoDocumentoByNombre(String nombreFamilia) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("traerFamiliaTipoDocumentoByNombre(String) - start"); //$NON-NLS-1$
    }
    FamiliaTipoDocumentoDTO returnFamiliaTipoDocumentoDTO = null;
    
    if(StringUtils.isNotBlank(nombreFamilia)){
    	FamiliaTipoDocumento familiaTipoDocumento = this.familiaTipoDocumentoRepository
    	        .findByNombre(nombreFamilia);
    	    if(familiaTipoDocumento != null
    	    		&& CollectionUtils.isNotEmpty(familiaTipoDocumento.getListaTipoDocumento())) {
    	    	LOGGER.info("SIZE " + familiaTipoDocumento.getListaTipoDocumento().size());
    	    }
    	    returnFamiliaTipoDocumentoDTO = mapper.map(familiaTipoDocumento,
    	        FamiliaTipoDocumentoDTO.class);
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("traerFamiliaTipoDocumentoByNombre(String) - end"); //$NON-NLS-1$
    }
    return returnFamiliaTipoDocumentoDTO;
  }
  
  public FamiliaTipoDocumentoDTO traerFamiliaTipoDocumentoById(String id) {
	    if (LOGGER.isDebugEnabled()) {
	      LOGGER.debug("traerFamiliaTipoDocumentoById(String) - start"); //$NON-NLS-1$
	    }
	    FamiliaTipoDocumentoDTO returnFamiliaTipoDocumentoDTO = null;
	    
	    if(StringUtils.isNotBlank(id)){
	    	Integer idDoc = Integer.parseInt(id);
	    	FamiliaTipoDocumento familiaTipoDocumento = this.familiaTipoDocumentoRepository
	    	        .findOne(idDoc);
	    	    if(familiaTipoDocumento != null
	    	    		&& CollectionUtils.isNotEmpty(familiaTipoDocumento.getListaTipoDocumento())) {
	    	    	LOGGER.info("SIZE " + familiaTipoDocumento.getListaTipoDocumento().size());
	    	    }
	    	    returnFamiliaTipoDocumentoDTO = mapper.map(familiaTipoDocumento,
	    	        FamiliaTipoDocumentoDTO.class);
	    }

	    if (LOGGER.isDebugEnabled()) {
	      LOGGER.debug("traerFamiliaTipoDocumentoById(String) - end"); //$NON-NLS-1$
	    }
	    return returnFamiliaTipoDocumentoDTO;
	  }
}