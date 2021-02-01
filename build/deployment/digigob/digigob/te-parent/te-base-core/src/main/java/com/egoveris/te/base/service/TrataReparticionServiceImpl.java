package com.egoveris.te.base.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.te.base.model.TrataDTO;
import com.egoveris.te.base.model.TrataReparticionDTO;
import com.egoveris.te.base.model.trata.Trata;
import com.egoveris.te.base.model.trata.TrataReparticion;
import com.egoveris.te.base.model.trata.TrataReparticionPK;
import com.egoveris.te.base.repository.trata.TrataRepository;
import com.egoveris.te.base.util.ConstantesWeb;

@Service
@Transactional
public class TrataReparticionServiceImpl implements TrataReparticionService {
  private static final Logger logger = LoggerFactory.getLogger(TrataReparticionServiceImpl.class);

  @Autowired
  private TrataRepository trataReparticionDAO;
  private DozerBeanMapper mapper = new DozerBeanMapper();

  public List<TrataReparticionDTO> cargarReparticionesHabilitadas(TrataDTO trata) {
    if (logger.isDebugEnabled()) {
      logger.debug("cargarReparticionesHabilitadas(trata={}) - start", trata);
    }
    
    List<TrataReparticionDTO> reparticionesHabilitadas = new ArrayList<TrataReparticionDTO>();
    
    for (TrataReparticionDTO reparticion : trata.getReparticionesTrata()) {
      TrataReparticionDTO reparticionHabilitada = new TrataReparticionDTO();
      
      reparticionHabilitada.setIdTrata(reparticion.getIdTrata());
      reparticionHabilitada.setOrden(reparticion.getOrden());
      reparticionHabilitada.setCodigoReparticion(reparticion.getCodigoReparticion());
      reparticionHabilitada.setHabilitacion(reparticion.getHabilitacion());
      reparticionHabilitada.setReserva(reparticion.getReserva());
      reparticionesHabilitadas.add(reparticionHabilitada);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("cargarReparticionesHabilitadas(Trata) - end - return value={}",
          reparticionesHabilitadas);
    }
    
    return reparticionesHabilitadas;
  }

  public void modificarListReparticion(TrataDTO trata) {
    if (logger.isDebugEnabled()) {
      logger.debug("modificarListReparticion(trata={}) - start", trata);
    }

    Trata trataEnt = mapper.map(trata, Trata.class);
    
    for (TrataReparticion trataReparticion : trataEnt.getReparticionesTrata()) {
		trataReparticion.setPk(new TrataReparticionPK(trataReparticion.getIdTrata(), trataReparticion.getOrden()));
	}
    
    this.trataReparticionDAO.save(mapper.map(trataEnt, Trata.class));

    if (logger.isDebugEnabled()) {
      logger.debug("modificarListReparticion(Trata) - end");
    }
  }

  public boolean validarPermisoReparticion(TrataDTO trata, String reparticion, Usuario usuario) {
    if (logger.isDebugEnabled()) {
      logger.debug("validarPermisoReparticion(trata={}, reparticion={}, usuario={}) - start",
          trata, reparticion, usuario);
    }

    Boolean tienePermiso;

    if (trata != null) {
      List<TrataReparticionDTO> listaReparticiones = trata.getReparticionesTrata();
      
      if (tienePermisoTodasReparticion(listaReparticiones)) {
        if (logger.isDebugEnabled()) {
          logger.debug("validarPermisoReparticion(Trata, String, Usuario) - end - return value={}",
              true);
        }
        
        return true;
      }

      String reparticionSelecionada = usuario.getCodigoReparticion();
      
      if (reparticionSelecionada == null) {
        if (logger.isDebugEnabled()) {
          logger.debug("validarPermisoReparticion(Trata, String, Usuario) - end - return value={}",
              false);
        }
        
        return false;
      }

      Boolean tienePermisoRep = tienePermisoReparticion(listaReparticiones,
          reparticionSelecionada);
      
      if (tienePermisoRep != null) {
        tienePermiso = tienePermisoRep;
      } else {
        tienePermiso = false;
      }
    } else {
      tienePermiso = true;
    }

    if (logger.isDebugEnabled()) {
      logger.debug("validarPermisoReparticion(Trata, String, Usuario) - end - return value={}",
          tienePermiso);
    }
    
    return tienePermiso;
  }

  private Boolean tienePermisoReparticion(List<TrataReparticionDTO> listaReparticiones,
      String reparticion) {
    if (logger.isDebugEnabled()) {
      logger.debug("tienePermisoReparticion(listaReparticiones={}, reparticion={}) - start",
          listaReparticiones, reparticion);
    }

    Boolean tienePermiso = null;

    if (reparticion.equals(ConstantesWeb.TODAS_REPARTICIONES_HABILITADAS)) {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "tienePermisoReparticion(List<TrataReparticion>, String) - end - return value={}",
            true);
      }
      
      return true;
    }

    for (TrataReparticionDTO trataReparticion : listaReparticiones) {
      if (trataReparticion.getCodigoReparticion().trim().compareTo(reparticion.trim()) == 0) {
        tienePermiso = trataReparticion.getHabilitacion();
        break;
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "tienePermisoReparticion(List<TrataReparticion>, String) - end - return value={}",
          tienePermiso);
    }
    
    return tienePermiso;
  }

  private Boolean tienePermisoTodasReparticion(List<TrataReparticionDTO> listaReparticiones) {
    if (logger.isDebugEnabled()) {
      logger.debug("tienePermisoTodasReparticion(listaReparticiones={}) - start",
          listaReparticiones);
    }

    Boolean tienePermiso = false;
    
    for (TrataReparticionDTO trataReparticion : listaReparticiones) {
      if (trataReparticion.getCodigoReparticion().trim()
          .compareTo(ConstantesWeb.TODAS_REPARTICIONES_HABILITADAS) == 0) {
        tienePermiso = trataReparticion.getHabilitacion();
        break;
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("tienePermisoTodasReparticion(List<TrataReparticion>) - end - return value={}",
          tienePermiso);
    }
    
    return tienePermiso;
  }
}
