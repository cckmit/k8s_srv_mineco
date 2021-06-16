package com.egoveris.deo.base.service.impl;

import com.egoveris.deo.base.exception.EjecucionSiglaException;
import com.egoveris.deo.base.model.HistorialReserva;
import com.egoveris.deo.base.repository.HistorialReservaRepository;
import com.egoveris.deo.base.service.HistorialReservaService;
import com.egoveris.deo.model.model.HistorialReservaDTO;
import com.egoveris.shared.map.ListMapper;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("unchecked")
@Service
@Transactional
public class HistorialReservaServiceImpl implements HistorialReservaService {
  /**
   * Logger for this class
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(HistorialReservaServiceImpl.class);

  @Autowired
  private HistorialReservaRepository historialReservaRepo;
  @Autowired
  @Qualifier("deoCoreMapper")
  private Mapper mapper;

  public void guardarHistorialReserva(HistorialReservaDTO historialReserva) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("guardarHistorialReserva(HistorialReservaDTO) - start"); //$NON-NLS-1$
    }

    historialReservaRepo.save(this.mapper.map(historialReserva, HistorialReserva.class));

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("guardarHistorialReserva(HistorialReservaDTO) - end"); //$NON-NLS-1$
    }
  }


	public List<HistorialReservaDTO> obtenerHistorialReservaPorUsuario(String usuario, String rectora,
			String documento) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerHistorialReservaPorUsuario(String, String, String) - start"); //$NON-NLS-1$
		}
		List<HistorialReserva> list = historialReservaRepo.findByUsuarioAndDocumentoAndReparticionRectora(usuario,
				documento, rectora);
		if (CollectionUtils.isNotEmpty(list)) {
			return ListMapper.mapList(list, this.mapper, HistorialReservaDTO.class);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("obtenerHistorialReservaPorUsuario(String, String, String) - end"); //$NON-NLS-1$
		}
		return null;
	}

  public List<HistorialReservaDTO> buscarDocumentoPorRectora(String documento,
      String reparticion) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarDocumentoPorRectora(String, String) - start"); //$NON-NLS-1$
    }

    List<HistorialReservaDTO> returnList = ListMapper.mapList(
        historialReservaRepo.findByDocumentoAndReparticionRectora(documento, reparticion),
        this.mapper, HistorialReservaDTO.class);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarDocumentoPorRectora(String, String) - end"); //$NON-NLS-1$
    }
    return returnList;
  }

  public List<HistorialReservaDTO> buscarDocumentoPorUsuario(String username, String documento) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarDocumentoPorUsuario(String, String) - start"); //$NON-NLS-1$
    }

    List<HistorialReservaDTO> returnList = ListMapper.mapList(
        this.historialReservaRepo.findByUsuarioAndDocumento(username, documento), this.mapper,
        HistorialReserva.class);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarDocumentoPorUsuario(String, String) - end"); //$NON-NLS-1$
    }
    return returnList;
  }

  public List<HistorialReservaDTO> buscarDocumentoPorReparticion(String reparticion,
      String documento) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarDocumentoPorReparticion(String, String) - start"); //$NON-NLS-1$
    }

    List<HistorialReservaDTO> returnList = ListMapper.mapList(
        this.historialReservaRepo.findByDocumentoAndReparticion(documento, reparticion),
        this.mapper, HistorialReserva.class);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarDocumentoPorReparticion(String, String) - end"); //$NON-NLS-1$
    }
    return returnList;
  }

  public List<HistorialReservaDTO> buscarDocumentoPorSector(String sector, String documento,
      String reparticion) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarDocumentoPorSector(String, String, String) - start"); //$NON-NLS-1$
    }

    List<HistorialReservaDTO> returnList = ListMapper.mapList(this.historialReservaRepo
        .findByDocumentoAndSectorAndReparticion(documento, sector, reparticion), this.mapper,
        HistorialReserva.class);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarDocumentoPorSector(String, String, String) - end"); //$NON-NLS-1$
    }
    return returnList;
  }

  public List<HistorialReservaDTO> buscarTodas(String documento) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarTodas(String) - start"); //$NON-NLS-1$
    }

    List<HistorialReservaDTO> returnList = ListMapper.mapList(
        this.historialReservaRepo.findByDocumentoAndReparticionRectora(documento, "--TODAS--"),
        this.mapper, HistorialReserva.class);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarTodas(String) - end"); //$NON-NLS-1$
    }
    return returnList;
  }

  public void actualizarReparticionHistorialVisualizacion(String reparticionOrigen,
      String reparticionDestino) throws EjecucionSiglaException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("actualizarReparticionHistorialVisualizacion(String, String) - start"); //$NON-NLS-1$
    }

    historialReservaRepo.actualizarReparticionHistorialVisualizacion(reparticionOrigen,
        reparticionDestino);

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("actualizarReparticionHistorialVisualizacion(String, String) - end"); //$NON-NLS-1$
    }
  }

  public void actualizarReparticionRectoraHistorialVisualizacion(String reparticionRectoraOrigen,
      String reparticionRectoraDestino) throws EjecucionSiglaException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("actualizarReparticionRectoraHistorialVisualizacion(String, String) - start"); //$NON-NLS-1$
    }

    historialReservaRepo.actualizarReparticionRectoraHistorialVisualizacion(
        reparticionRectoraOrigen, reparticionRectoraDestino);

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("actualizarReparticionRectoraHistorialVisualizacion(String, String) - end"); //$NON-NLS-1$
    }
  }

  public void actualizarSectorHistorialVisualizacion(String sectorOrigen, String sectorDestino,
      String reparticionOrigen, String reparticionDestino) throws EjecucionSiglaException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER
          .debug("actualizarSectorHistorialVisualizacion(String, String, String, String) - start"); //$NON-NLS-1$
    }

    historialReservaRepo.actualizarSectorHistorialVisualizacion(sectorOrigen, sectorDestino,
        reparticionOrigen, reparticionDestino);

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("actualizarSectorHistorialVisualizacion(String, String, String, String) - end"); //$NON-NLS-1$
    }
  }

}
