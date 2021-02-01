package com.egoveris.deo.base.service.impl;

import com.egoveris.deo.base.exception.ArchivoDuplicadoException;
import com.egoveris.deo.base.exception.ExtensionesFaltantesException;
import com.egoveris.deo.base.exception.FormatoInvalidoException;
import com.egoveris.deo.base.exception.TamanoInvalidoException;
import com.egoveris.deo.base.model.ArchivoEmbebido;
import com.egoveris.deo.base.model.TipoDocumento;
import com.egoveris.deo.base.model.TipoDocumentoEmbebidos;
import com.egoveris.deo.base.model.TipoDocumentoEmbebidosPK;
import com.egoveris.deo.base.repository.ArchivoEmbebidoRepository;
import com.egoveris.deo.base.repository.TipoDocumentoEmbebidosRepository;
import com.egoveris.deo.base.service.ArchivosEmbebidosService;
import com.egoveris.deo.base.service.GestionArchivosEmbebidosWebDavService;
import com.egoveris.deo.model.model.ArchivoEmbebidoDTO;
import com.egoveris.deo.model.model.ExtensionMimetypeDTO;
import com.egoveris.deo.model.model.FormatoTamanoArchivoDTO;
import com.egoveris.deo.model.model.TipoDocumentoDTO;
import com.egoveris.deo.model.model.TipoDocumentoEmbebidosDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.shared.map.ListMapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.tika.Tika;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.terasoluna.plus.common.exception.ApplicationException;

@Service
@Transactional
public class ArchivosEmbebidosServiceImpl implements ArchivosEmbebidosService {
  /**
   * Logger for this class
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(ArchivosEmbebidosServiceImpl.class);

  @Autowired
  private TipoDocumentoEmbebidosRepository tipoDocumentoEmbebidosRepo;
  @Autowired
  private GestionArchivosEmbebidosWebDavService gestionArchivosEmbebidosWebDavService;
  @Autowired
  private ArchivoEmbebidoRepository archivoEmbebidoRepo;
  @Autowired
  @Qualifier("deoCoreMapper")
  private Mapper mapper;

  @Override
  public void subirArchivoEmbebidoTemporal(ArchivoEmbebidoDTO archivoEmbebido) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("subirArchivoEmbebidoTemporal(ArchivoEmbebidoDTO) - start"); //$NON-NLS-1$
    }

    try {
      gestionArchivosEmbebidosWebDavService.subirArchivoEmbebidoTemporalWebDav(archivoEmbebido,
          archivoEmbebido.getDataArchivo());
    } catch (Exception e) {
      throw e;
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("subirArchivoEmbebidoTemporal(ArchivoEmbebidoDTO) - end"); //$NON-NLS-1$
    }
  }

  @Override
  public void verificarArchivo(byte[] dataFile, TipoDocumentoDTO tipoDocumento)
      throws FormatoInvalidoException, TamanoInvalidoException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("verificarArchivo(byte[], TipoDocumentoDTO) - start"); //$NON-NLS-1$
    }

    String mimeType = getMimetype(dataFile);
    Integer tamanioEmbebido;

    List<FormatoTamanoArchivoDTO> formatoTamanoList = new ArrayList<>();
    List<TipoDocumentoEmbebidosDTO> listaTipoDocumentoEmbebidos = new ArrayList<>(
        tipoDocumento.getTipoDocumentoEmbebidos());
    if (formatoTamanoList.isEmpty()) {
      for (TipoDocumentoEmbebidosDTO tipoDocumentoEmbebidos : listaTipoDocumentoEmbebidos) {
      	formatoTamanoList.add(tipoDocumentoEmbebidos.getTipoDocumentoEmbebidosPK().getFormatoTamanoId());
      }
    }
    for (TipoDocumentoEmbebidosDTO tde : tipoDocumento.getTipoDocumentoEmbebidos()) {
      List<ExtensionMimetypeDTO> listaMimetypes = new ArrayList<>(
      		tde.getTipoDocumentoEmbebidosPK().getFormatoTamanoId().getExtensionMimetypes());
      for (ExtensionMimetypeDTO extensionMimetype : listaMimetypes) {
        if (extensionMimetype.getExtensionMimetypePK().getMimetype().equals(mimeType)) {
          if (tde.getSizeArchivoEmb() != null) {
            tamanioEmbebido = tde.getSizeArchivoEmb();
          } else {
            tamanioEmbebido = tde.getTipoDocumentoEmbebidosPK().getFormatoTamanoId().getTamano();
          }
          if (tamanioEmbebido <= (dataFile.length / Constantes.FACTOR_CONVERSION_MB)) {
            throw new TamanoInvalidoException(
                "El archivo tiene un tamaño mayor al permitido por el tipo de documento seleccionado.");
          } else {
            if (LOGGER.isDebugEnabled()) {
              LOGGER.debug("verificarArchivo(byte[], TipoDocumentoDTO) - end"); //$NON-NLS-1$
            }
            return;
          }
        }
      }
    }
    throw new FormatoInvalidoException(
        "El archivo tiene un formato no permitido por el tipo de documento seleccionado.");
  }

  @Override
  public void validarNombre(List<ArchivoEmbebidoDTO> listaArchivosEmbebidos,
      ArchivoEmbebidoDTO archivoEmbebidoEntrante) throws ArchivoDuplicadoException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("validarNombre(List<ArchivoEmbebidoDTO>, ArchivoEmbebidoDTO) - start"); //$NON-NLS-1$
    }

    boolean duplicado = false;
    int contadorEmbebidos = 0;
    if (listaArchivosEmbebidos == null || listaArchivosEmbebidos.isEmpty()) {
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("validarNombre(List<ArchivoEmbebidoDTO>, ArchivoEmbebidoDTO) - end"); //$NON-NLS-1$
      }
      return;
    } else {
      for (ArchivoEmbebidoDTO archivoEmbebido : listaArchivosEmbebidos) {

        if (archivoEmbebidoEntrante.getNombreArchivo()
            .equals(archivoEmbebido.getNombreArchivo())) {
          duplicado = true;
          break;
        }
        contadorEmbebidos++;
      }
      if (duplicado) {
        throw new ArchivoDuplicadoException(
            "El archivo \"" + listaArchivosEmbebidos.get(contadorEmbebidos).getNombreArchivo()
                + "\" ya se encuentra en la lista de archivos embebidos.");

      } else {
        if (LOGGER.isDebugEnabled()) {
          LOGGER.debug("validarNombre(List<ArchivoEmbebidoDTO>, ArchivoEmbebidoDTO) - end"); //$NON-NLS-1$
        }
        return;
      }
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<ArchivoEmbebidoDTO> buscarArchivosEmbebidosPorProceso(String workflowId) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarArchivosEmbebidosPorProceso(String) - start"); //$NON-NLS-1$
    }

    List<ArchivoEmbebidoDTO> returnList = ListMapper
        .mapList(archivoEmbebidoRepo.findByIdTask(workflowId), mapper, ArchivoEmbebidoDTO.class);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarArchivosEmbebidosPorProceso(String) - end"); //$NON-NLS-1$
    }
    return returnList;
  }

  @Override
  public byte[] obtenerArchivoEmbebidoWebDav(String pathRelativo, String nombreArchivo)
      throws ApplicationException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerArchivoEmbebidoWebDav(String, String) - start"); //$NON-NLS-1$
    }

    byte[] returnbyteArray = this.gestionArchivosEmbebidosWebDavService
        .obtenerArchivosEmbebidosWebDav(pathRelativo, nombreArchivo);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerArchivoEmbebidoWebDav(String, String) - end"); //$NON-NLS-1$
    }
    return returnbyteArray;
  }

  @Override
  public void borrarArchivoEmbebidoWebDav(String pathRelativo, String nombreArchivo) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("borrarArchivoEmbebidoWebDav(String, String) - start"); //$NON-NLS-1$
    }

    gestionArchivosEmbebidosWebDavService.borrarArchivoEmbebidoWebDav(pathRelativo, nombreArchivo);

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("borrarArchivoEmbebidoWebDav(String, String) - end"); //$NON-NLS-1$
    }
  }

  @Override
  public void eliminarAchivoEmbebido(ArchivoEmbebidoDTO archivoEmbebido) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("eliminarAchivoEmbebido(ArchivoEmbebidoDTO) - start"); //$NON-NLS-1$
    }

    archivoEmbebidoRepo.delete(mapper.map(archivoEmbebido, ArchivoEmbebido.class));

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("eliminarAchivoEmbebido(ArchivoEmbebidoDTO) - end"); //$NON-NLS-1$
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public Set<TipoDocumentoEmbebidosDTO> buscarTipoDocEmbebidos(TipoDocumentoDTO tipoDocumento) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarTipoDocEmbebidos(TipoDocumentoDTO) - start"); //$NON-NLS-1$
    }

    TipoDocumento td = new TipoDocumento();
    td.setId(tipoDocumento.getId());
    TipoDocumentoEmbebidosPK tdEpk = new TipoDocumentoEmbebidosPK(td,null);
//    List<TipoDocumentoEmbebidos> resultList = tipoDocumentoEmbebidosRepo.findByTipoDocumentoEmbebidosPK(new TipoDocumentoEmbebidosPK(tipoDocumento.getId(), null));
    List<TipoDocumentoEmbebidos> resultList = tipoDocumentoEmbebidosRepo.findByTipoDocumentoEmbebidosPK(tdEpk);
    
    Set<TipoDocumentoEmbebidosDTO> returnSet = new HashSet<>(
        ListMapper.mapList(resultList, mapper, TipoDocumentoEmbebidosDTO.class));
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarTipoDocEmbebidos(TipoDocumentoDTO) - end"); //$NON-NLS-1$
    }
    return returnSet;
  }

  @Override
  public String getMimetype(byte[] dataFile) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("getMimetype(byte[]) - start"); //$NON-NLS-1$
    }

    Tika tika = new Tika();
    String mimeType = tika.detect(dataFile);

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("getMimetype(byte[]) - end"); //$NON-NLS-1$
    }
    return mimeType;

  }

  @Override
  public void verificarObligatoriedadExtensiones(List<ArchivoEmbebidoDTO> listaArchivosEmbebidos,
      TipoDocumentoDTO tipoDocumento) throws ExtensionesFaltantesException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug(
          "verificarObligatoriedadExtensiones(List<ArchivoEmbebidoDTO>, TipoDocumentoDTO) - start"); //$NON-NLS-1$
    }

    Set<TipoDocumentoEmbebidosDTO> listaEmbebidos = tipoDocumento.getTipoDocumentoEmbebidos();
    Set<TipoDocumentoEmbebidosDTO> listaTipoDocumentoEmbebidosObligatorios = new HashSet<>();
    for (TipoDocumentoEmbebidosDTO tde : listaEmbebidos) {
      if (tde.getObligatorio())
        listaTipoDocumentoEmbebidosObligatorios.add(tde);
    }
    if (!listaTipoDocumentoEmbebidosObligatorios.isEmpty()) {
      if (listaArchivosEmbebidos != null && !listaArchivosEmbebidos.isEmpty()) {

        for (TipoDocumentoEmbebidosDTO tipoDocumentoEmbebidosObligatorio : listaTipoDocumentoEmbebidosObligatorios) {

        	if (!tipoDocumentoEmbebidosObligatorio.getTipoDocumentoEmbebidosPK().getFormatoTamanoId()
              .existeExtension(listaArchivosEmbebidos)) {

            throw new ExtensionesFaltantesException("Extensiones no encontradas");
          }
        }
      } else {
        throw new ExtensionesFaltantesException("Extensiones no encontradas");
      }
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug(
          "verificarObligatoriedadExtensiones(List<ArchivoEmbebidoDTO>, TipoDocumentoDTO) - end"); //$NON-NLS-1$
    }
  }

  @Override
  public Integer guardarArchivoEmbebido(ArchivoEmbebidoDTO archivoEmbebido) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("guardarArchivoEmbebido(ArchivoEmbebidoDTO) - start"); //$NON-NLS-1$
    }

    ArchivoEmbebido result = this.archivoEmbebidoRepo
        .save(mapper.map(archivoEmbebido, ArchivoEmbebido.class));
    if (result != null) {
      Integer returnInteger = result.getId();
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("guardarArchivoEmbebido(ArchivoEmbebidoDTO) - end"); //$NON-NLS-1$
      }
      return returnInteger;
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("guardarArchivoEmbebido(ArchivoEmbebidoDTO) - end"); //$NON-NLS-1$
    }
    return null;
  }
}