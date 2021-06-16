package com.egoveris.sharedorganismo.base.service.impl;

import com.egoveris.shared.map.ListMapper;
import com.egoveris.sharedorganismo.base.model.SectorInterno;
import com.egoveris.sharedorganismo.base.model.SectorInternoBean;
import com.egoveris.sharedorganismo.base.model.SectorUsuario;
import com.egoveris.sharedorganismo.base.repository.SectorInternoRepository;
import com.egoveris.sharedorganismo.base.repository.SectorUsuarioRepository;
import com.egoveris.sharedorganismo.base.service.SectorInternoServ;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(value = "jpaTransactionManagerOrg", readOnly = true, propagation = Propagation.NOT_SUPPORTED)
public class SectorInternoServImpl implements SectorInternoServ {
  /**
   * Logger for this class
   */
  private static final Logger logger = LoggerFactory.getLogger(SectorInternoServImpl.class);

  @Autowired
  private SectorInternoRepository sectorInternoRepo;
  @Autowired
  private SectorUsuarioRepository sectorUsuarioRepo;
  private String tiempo;
  @Autowired
  @Qualifier("organismoMapper")
  private Mapper mapper;

  Map<String, SectorInterno> mapSectorUsuario = new HashMap<>();

  public String getTiempo() {
    return tiempo;
  }

  public void setTiempo(String tiempo) {
    this.tiempo = tiempo;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<SectorInternoBean> buscarSectoresPorReparticion(Long idReparticion) {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarSectoresPorReparticion(Integer) - start"); //$NON-NLS-1$
    }

    Date fechaHoy = new Date();
    List<SectorInterno> listaSectoresInternos = sectorInternoRepo.getSectorInternoByReparticion(idReparticion, fechaHoy, fechaHoy);
    List<SectorInternoBean> returnList = new ArrayList<>();
    
    if (listaSectoresInternos != null) {
      returnList.addAll(ListMapper.mapList(listaSectoresInternos, mapper, SectorInternoBean.class));
    }
    

    if (logger.isDebugEnabled()) {
      logger.debug("buscarSectoresPorReparticion(Integer) - end"); //$NON-NLS-1$
    }
    
    return returnList;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<SectorInternoBean> buscarTodosSectores() {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarTodosSectores() - start"); //$NON-NLS-1$
    }

    List<SectorInternoBean> returnList = ListMapper.mapList(this.sectorInternoRepo.findAll(),
        this.mapper, SectorInternoBean.class);
    if (logger.isDebugEnabled()) {
      logger.debug("buscarTodosSectores() - end"); //$NON-NLS-1$
    }
    return returnList;
  }

  @SuppressWarnings("unchecked")
  @Override

  public List<SectorInternoBean> buscarSectoresPorReparticionOrderByMesa(Long idReparticion) {

    if (logger.isDebugEnabled()) {
      logger.debug("buscarSectoresPorReparticionOrderByMesa(Integer) - start"); //$NON-NLS-1$
    }
    List<SectorInternoBean> returnList = ListMapper.mapList(this.sectorInternoRepo
        .findByCodigoReparticionOrderBySectorMesa(idReparticion), this.mapper,SectorInternoBean.class);
    if (logger.isDebugEnabled()) {
      logger.debug("buscarSectoresPorReparticionOrderByMesa(Integer) - end"); //$NON-NLS-1$
    }
    return returnList;
  }

  public void loadSectoresInternos() {
    if (logger.isDebugEnabled()) {
      logger.debug("loadSectoresInternos() - start"); //$NON-NLS-1$
    }

    this.cargarSectoresUsuario();

    if (logger.isDebugEnabled()) {
      logger.debug("loadSectoresInternos() - end"); //$NON-NLS-1$
    }
  }

  @Override
  public SectorInternoBean buscarSectorInternoUsername(String username) {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarSectorInternoUsername(String) - start"); //$NON-NLS-1$
    }

    synchronized (this.mapSectorUsuario) {
      SectorInterno sector = mapSectorUsuario.get(username);
      SectorInternoBean returnSectorInternoBean = (sector != null)
          ? this.mapper.map(sector, SectorInternoBean.class) : null;
      if (logger.isDebugEnabled()) {
        logger.debug("buscarSectorInternoUsername(String) - end"); //$NON-NLS-1$
      }
      return returnSectorInternoBean;
    }
  }

  private void cargarSectoresUsuario() {
    if (logger.isDebugEnabled()) {
      logger.debug("cargarSectoresUsuario() - start"); //$NON-NLS-1$
    }

    Date fechaHoy = new Date();
    List<SectorUsuario> resultList = this.sectorUsuarioRepo.findSectoresUsuarios(fechaHoy,
        fechaHoy);
    Map<String, SectorInterno> mapSectorUsuarioTmp = new HashMap<>();
    for (SectorUsuario sectorUsuario : resultList) {
      mapSectorUsuarioTmp.put(sectorUsuario.getNombre(), sectorUsuario.getSectorInterno());
    }

    synchronized (this.mapSectorUsuario) {
      mapSectorUsuario = mapSectorUsuarioTmp;
    }

    if (logger.isDebugEnabled()) {
      logger.debug("cargarSectoresUsuario() - end"); //$NON-NLS-1$
    }
  }

  @Override
  public boolean validarCodigoSector(String value, Long idReparticion) {
    if (logger.isDebugEnabled()) {
      logger.debug("validarCodigoSector(String, Integer) - start"); //$NON-NLS-1$
    }

    SectorInternoBean current;
    Iterator<SectorInternoBean> iterator = this.buscarSectoresPorReparticion(idReparticion)
        .iterator();
    while (iterator.hasNext()) {
      current = iterator.next();
      if (current.getCodigo() != null && current.getCodigo().trim().equalsIgnoreCase(value)) {
        if (logger.isDebugEnabled()) {
          logger.debug("validarCodigoSector(String, Integer) - end"); //$NON-NLS-1$
        }
        return true;
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("validarCodigoSector(String, Integer) - end"); //$NON-NLS-1$
    }
    return false;
  }

  @Override
  public boolean validarCodigoSectorMesa(String value, Long idReparticion) {
    if (logger.isDebugEnabled()) {
      logger.debug("validarCodigoSectorMesa(String, Integer) - start"); //$NON-NLS-1$
    }

    SectorInternoBean current;
    Iterator<SectorInternoBean> iterator = this
        .buscarSectoresPorReparticionOrderByMesa(idReparticion).iterator();
    while (iterator.hasNext()) {
      current = iterator.next();
      if (current.getCodigo() != null && current.getCodigo().trim().equalsIgnoreCase(value)) {
        if (logger.isDebugEnabled()) {
          logger.debug("validarCodigoSectorMesa(String, Integer) - end"); //$NON-NLS-1$
        }
        return true;
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("validarCodigoSectorMesa(String, Integer) - end"); //$NON-NLS-1$
    }
    return false;
  }
  
	@Override
	public String buscarNombreSectorPorCodigo(String codigoSector, String codigoReparticion) {
		return sectorInternoRepo.buscarNombreSectorPorCodigo(codigoSector, codigoReparticion);
	}
}
