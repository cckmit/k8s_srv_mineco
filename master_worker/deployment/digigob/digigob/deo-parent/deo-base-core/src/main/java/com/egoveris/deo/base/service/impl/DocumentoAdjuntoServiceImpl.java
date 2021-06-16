package com.egoveris.deo.base.service.impl;

import com.egoveris.deo.base.model.DocumentoAdjunto;
import com.egoveris.deo.base.repository.DocumentoAdjuntoRepository;
import com.egoveris.deo.base.service.DocumentoAdjuntoService;
import com.egoveris.deo.base.service.GestionArchivosWebDavService;
import com.egoveris.deo.base.service.PdfService;
import com.egoveris.deo.model.model.DocumentoAdjuntoDTO;
import com.egoveris.shared.map.ListMapper;
import com.egoveris.shareddocument.base.exception.WebDAVResourceNotFoundException;

import java.util.List;

import javax.transaction.Transactional;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.terasoluna.plus.common.exception.ApplicationException;

@Service
@Transactional
public class DocumentoAdjuntoServiceImpl implements DocumentoAdjuntoService {

  @Autowired
  protected PdfService pdfService;
  @Autowired
  private DocumentoAdjuntoRepository documentoAdjuntoRepo;
  @Autowired
  @Qualifier("gestionArchivosWebDavServiceImpl")
  private GestionArchivosWebDavService gestionArchivosWebDavService;
  @Autowired
  @Qualifier("deoCoreMapper")
  private Mapper mapper;

  private static final Logger LOGGER = LoggerFactory.getLogger(DocumentoAdjuntoServiceImpl.class);

  @Override
  public Integer grabarDocumentoAdjuntoBD(DocumentoAdjuntoDTO documento) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("grabarDocumentoAdjuntoBD(DocumentoAdjuntoDTO) - start"); //$NON-NLS-1$
    }

    DocumentoAdjunto result = documentoAdjuntoRepo
        .save(mapper.map(documento, DocumentoAdjunto.class));
    if (result != null) {
      Integer returnInteger = result.getId();
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("grabarDocumentoAdjuntoBD(DocumentoAdjuntoDTO) - end"); //$NON-NLS-1$
      }
      return returnInteger;
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("grabarDocumentoAdjuntoBD(DocumentoAdjuntoDTO) - end"); //$NON-NLS-1$
    }
    return null;
  }

  @Override
  public void eliminarDocumentoAdjuntoBD(DocumentoAdjuntoDTO documento) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("eliminarDocumentoAdjuntoBD(DocumentoAdjuntoDTO) - start"); //$NON-NLS-1$
    }

    this.documentoAdjuntoRepo.delete(mapper.map(documento, DocumentoAdjunto.class));

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("eliminarDocumentoAdjuntoBD(DocumentoAdjuntoDTO) - end"); //$NON-NLS-1$
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<DocumentoAdjuntoDTO> buscarArchivosDeTrabajoPorProceso(String workflowId) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarArchivosDeTrabajoPorProceso(String) - start"); //$NON-NLS-1$
    }

    List<DocumentoAdjuntoDTO> returnList = ListMapper.mapList(
        this.documentoAdjuntoRepo.findByIdTask(workflowId), this.mapper,
        DocumentoAdjuntoDTO.class);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarArchivosDeTrabajoPorProceso(String) - end"); //$NON-NLS-1$
    }
    return returnList;
  }

  @Override
  public void subirDocumentoAdjuntoWebDav(DocumentoAdjuntoDTO documento) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("subirDocumentoAdjuntoWebDav(DocumentoAdjuntoDTO) - start"); //$NON-NLS-1$
    }

    try {
      gestionArchivosWebDavService.subirDocumentoAdjuntoTemporalWebDav(documento,
          documento.getDataArchivo());
    } catch (Exception e) {
      LOGGER.error("Mensaje de error", e);
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("subirDocumentoAdjuntoWebDav(DocumentoAdjuntoDTO) - end"); //$NON-NLS-1$
    }
  }

  @Override
  public byte[] obtenerDocumentoAdjuntoTemporalWebDav(String pathRelativo,
      String nombreDocumentoAdjunto) throws ApplicationException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerDocumentoAdjuntoTemporalWebDav(String, String) - start"); //$NON-NLS-1$
    }

    try {
      byte[] returnbyteArray = gestionArchivosWebDavService
          .obtenerDocumentoAdjuntoTemporalWebDav(pathRelativo, nombreDocumentoAdjunto);
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("obtenerDocumentoAdjuntoTemporalWebDav(String, String) - end"); //$NON-NLS-1$
      }
      return returnbyteArray;
    } catch (WebDAVResourceNotFoundException e) {
      LOGGER.error("obtenerDocumentoAdjuntoTemporalWebDav(String, String)", e); //$NON-NLS-1$

      LOGGER.error("Mensaje de error", e);
      return new byte[0];
    }
  }

  @Override
  public void borrarDocumentoAdjuntoTemporalWebDav(String pathRelativo,
      String nombreDocumentoAdjunto) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("borrarDocumentoAdjuntoTemporalWebDav(String, String) - start"); //$NON-NLS-1$
    }

    try {
      gestionArchivosWebDavService.borrarDocumentoAdjuntoTemporalWebDav(pathRelativo,
          nombreDocumentoAdjunto);
    } catch (Exception e) {
      LOGGER.error("Mensaje de error", e);
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("borrarDocumentoAdjuntoTemporalWebDav(String, String) - end"); //$NON-NLS-1$
    }
  }

  @Override
  public void regularizacionDocumentoAdjuntoNuevoRepositorio(DocumentoAdjuntoDTO documento)
      throws ApplicationException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("regularizacionDocumentoAdjuntoNuevoRepositorio(DocumentoAdjuntoDTO) - start"); //$NON-NLS-1$
    }

    try {
      grabarDocumentoAdjuntoBD(documento);

      byte[] dataFileSinUltimaHoja = quitarUltimaPagina(documento.getDataArchivo());

      documento.setDataArchivo(dataFileSinUltimaHoja);

      subirDocumentoAdjuntoWebDav(documento);
    } catch (ApplicationException e) {
      throw e;
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("regularizacionDocumentoAdjuntoNuevoRepositorio(DocumentoAdjuntoDTO) - end"); //$NON-NLS-1$
    }
  }

  private byte[] quitarUltimaPagina(byte[] dataArchivo) throws ApplicationException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("quitarUltimaPagina(byte[]) - start"); //$NON-NLS-1$
    }

    byte[] returnbyteArray = pdfService.quitarUltimaPagina(dataArchivo);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("quitarUltimaPagina(byte[]) - end"); //$NON-NLS-1$
    }
    return returnbyteArray;
  }

}
