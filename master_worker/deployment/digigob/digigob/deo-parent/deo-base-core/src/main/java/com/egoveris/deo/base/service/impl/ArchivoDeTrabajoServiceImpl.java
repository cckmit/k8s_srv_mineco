package com.egoveris.deo.base.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.egoveris.deo.base.model.ArchivoDeTrabajo;
import com.egoveris.deo.base.repository.ArchivoDeTrabajoRepository;
import com.egoveris.deo.base.service.ArchivoDeTrabajoService;
import com.egoveris.deo.base.service.GestionArchivosWebDavService;
import com.egoveris.deo.model.model.ArchivoDeTrabajoDTO;
import com.egoveris.shared.map.ListMapper;

@Service
@Transactional
public class ArchivoDeTrabajoServiceImpl implements ArchivoDeTrabajoService {
  private static final Logger LOGGER = LoggerFactory.getLogger(ArchivoDeTrabajoServiceImpl.class);

  @Autowired
  private ArchivoDeTrabajoRepository archivoDeTrabajoRepository;
  @Autowired
  @Qualifier("gestionArchivosWebDavServiceImpl")
  private GestionArchivosWebDavService gestionArchivosWebDavService;
  @Autowired
  @Qualifier("deoCoreMapper")
  private Mapper mapper;

  @Override
  public Integer grabarArchivoDeTrabajo(ArchivoDeTrabajoDTO archivo) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("grabarArchivoDeTrabajo(archivo={}) - start", archivo);
    }
    ArchivoDeTrabajo result = archivoDeTrabajoRepository
        .save(mapper.map(archivo, ArchivoDeTrabajo.class));

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("grabarArchivoDeTrabajo(archivo) - end - return value={}", result.getId());
    }
    return result.getId();
  }

  @Override
  public void subirArchivoDeTrabajoTemporal(ArchivoDeTrabajoDTO archivoDeTrabajo) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("subirArchivoDeTrabajoTemporal(ArchivoDeTrabajo={}) - start", archivoDeTrabajo);
    }
    gestionArchivosWebDavService.subirArchivoDeTrabajoTemporalWebDav(archivoDeTrabajo,
        archivoDeTrabajo.getDataArchivo());

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("subirArchivoDeTrabajoTemporal(archivoDeTrabajo) - end");
    }
  }

  @Override
  public void borrarArchivoDeTrabajoTemporal(String pathRelativo, String nombreArchivo) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("borrarArchivoDeTrabajoTemporal(nombreArchivo={}) - start", nombreArchivo);
    }
    gestionArchivosWebDavService.borrarArchivoDeTrabajoTemporalWebDav(pathRelativo, nombreArchivo);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("borrarArchivoDeTrabajoTemporal(nombreArchivo) - end");
    }
  }

  @Override
  public byte[] obtenerArchivoDeTrabajoTemporalWebDav(String pathRelativo, String nombreArchivo) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerArchivoDeTrabajoTemporalWebDav(String, String) - start"); //$NON-NLS-1$
    }

    byte[] returnbyteArray = gestionArchivosWebDavService
        .obtenerArchivoDeTrabajoTemporalWebDav(pathRelativo, nombreArchivo);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerArchivoDeTrabajoTemporalWebDav(String, String) - end"); //$NON-NLS-1$
    }
    return returnbyteArray;

  }

  @SuppressWarnings("unchecked")
  @Override
  public List<ArchivoDeTrabajoDTO> buscarArchivosDeTrabajoPorProceso(String workflowId) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarArchivosDeTrabajoPorProceso(String) - start"); //$NON-NLS-1$
    }
    
    List<ArchivoDeTrabajoDTO> returnList = new ArrayList<>();
    List<ArchivoDeTrabajo> archivoDeTrabajos = null;
    
    if (workflowId != null) {
      archivoDeTrabajos = archivoDeTrabajoRepository.findByIdTask(workflowId);
    }
    
    if (archivoDeTrabajos != null) {
      returnList = ListMapper.mapList(archivoDeTrabajos, mapper, ArchivoDeTrabajoDTO.class);
    }
    
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarArchivosDeTrabajoPorProceso(String) - end"); //$NON-NLS-1$
    }
    
    return returnList;
  }

  /**
   * Sube al servidor WebDav un archivo de trabajo, retorna el nuevo path
   * relativo
   */
  @Override
  public String subirArchivoDeTrabajoWebDav(String numeroSade, byte[] contenido,
      String nombreArchivo) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("subirArchivoDeTrabajoWebDav(String, byte[], String) - start"); //$NON-NLS-1$
    }
    String returnString = this.gestionArchivosWebDavService.subirArchivoDeTrabajoWebDav(numeroSade,
        contenido, nombreArchivo);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("subirArchivoDeTrabajoWebDav(String, byte[], String) - end"); //$NON-NLS-1$
    }
    return returnString;
  }

  @Override
  public void eliminarAchivoDeTrabajo(ArchivoDeTrabajoDTO archivo) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("eliminarAchivoDeTrabajo(ArchivoDeTrabajoDTO) - start"); //$NON-NLS-1$
    }

    this.archivoDeTrabajoRepository.delete(mapper.map(archivo, ArchivoDeTrabajo.class));

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("eliminarAchivoDeTrabajo(ArchivoDeTrabajoDTO) - end"); //$NON-NLS-1$
    }
  }

  @Override
  public byte[] obtenerArchivoDeTrabajoWebDav(String pathRelativo, String nombreArchivo) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerArchivoDeTrabajoWebDav(String, String) - start"); //$NON-NLS-1$
    }

    byte[] returnbyteArray = this.gestionArchivosWebDavService
        .obtenerArchivoDeTrabajoWebDav(pathRelativo, nombreArchivo);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerArchivoDeTrabajoWebDav(String, String) - end"); //$NON-NLS-1$
    }
    return returnbyteArray;
  }
}