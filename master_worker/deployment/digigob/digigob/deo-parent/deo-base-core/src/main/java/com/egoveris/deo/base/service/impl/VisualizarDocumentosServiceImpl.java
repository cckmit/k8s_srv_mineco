package com.egoveris.deo.base.service.impl;

import com.egoveris.deo.base.exception.BuscarDocumentosGedoServiceException;
import com.egoveris.deo.base.exception.VisualizacionDocumentoException;
import com.egoveris.deo.base.exception.obtenerImagenesException;
import com.egoveris.deo.base.model.DocumentoSolicitud;
import com.egoveris.deo.base.repository.DocumentoSolicitudRepository;
import com.egoveris.deo.base.service.BuscarDocumentosGedoService;
import com.egoveris.deo.base.service.GestionArchivosWebDavService;
import com.egoveris.deo.base.service.IPrevisualizacionDocumentoService;
import com.egoveris.deo.base.service.IVisualizarDocumentosService;
import com.egoveris.deo.base.service.ProcesamientoTemplate;
import com.egoveris.deo.model.exception.DataAccessLayerException;
import com.egoveris.deo.model.model.ArchivoDeTrabajoDTO;
import com.egoveris.deo.model.model.ArchivoDeTrabajoDetalle;
import com.egoveris.deo.model.model.DocumentoDetalle;
import com.egoveris.deo.model.model.DocumentoMetadataDTO;
import com.egoveris.deo.model.model.DocumentoMetadataDetalle;
import com.egoveris.deo.model.model.HistorialDTO;
import com.egoveris.deo.model.model.HistorialDetalle;
import com.egoveris.deo.model.model.TipoDocumentoTemplateDTO;
import com.egoveris.deo.model.model.UsuarioReservadoDTO;
import com.egoveris.deo.model.model.VisualizarDocumentoDTO;
import com.egoveris.shared.map.ListMapper;
import com.egoveris.shareddocument.base.exception.WebDAVConnectionException;
import com.egoveris.shareddocument.base.service.IWebDavService;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.io.IOUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.plus.common.exception.ApplicationException;

@Service
@Transactional
public class VisualizarDocumentosServiceImpl implements IVisualizarDocumentosService {

  /**
   * Carpeta raíz en WebDav dentro de la cual se crea la estructura de espacios
   * que almacenarán los documentos definitivos.
   */
  public static final String CARPETA_RAIZ_DOCUMENTOS = "SADE";

  @Autowired
  private BuscarDocumentosGedoService buscarDocumentosGedoService;
  @Autowired
  @Qualifier("gestionArchivosWebDavServiceImpl")
  private GestionArchivosWebDavService gestionArchivosWebDavService;
  @Autowired
  private ProcesamientoTemplate procesamientoTemplate;
  @Autowired
  private IWebDavService webDavService;
  @Autowired
  private DocumentoSolicitudRepository documentoSolicitudRepo;
  @Autowired
  private IPrevisualizacionDocumentoService previsualizacionDocumentoService;
  @Autowired
  @Qualifier("deoCoreMapper")
  private Mapper mapper;

  private static final Logger LOGGER = LoggerFactory
      .getLogger(VisualizarDocumentosServiceImpl.class);

  public VisualizarDocumentoDTO completarDocumentoDTO(String numeroSade, String loggedUsername)
      throws VisualizacionDocumentoException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("completarDocumentoDTO(String, String) - start"); //$NON-NLS-1$
    }

    DocumentoDetalle documentoDetalle;
    VisualizarDocumentoDTO documentoDTO = new VisualizarDocumentoDTO();
    PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();

    try {
      documentoDetalle = this.buscarDocumentosGedoService.buscarDocumentoDetalle(numeroSade,
          loggedUsername);
      propertyUtilsBean.copyProperties(documentoDTO, documentoDetalle);
      documentoDTO
          .setDatosPropios(listDocumentoMetadataToDTO(documentoDetalle.getDatosPropiosDetalle()));
      documentoDTO.setListaArchivosDeTrabajo(
          listArchivoDeTrabajoToDTO(documentoDetalle.getListaArchivosDeTrabajoDetalle()));
      documentoDTO
          .setListaHistorialDTOs(listHistorialToDTO(documentoDetalle.getListaHistorialDetalle()));
      documentoDTO.setIdFormulario(obtenerIdFormulario(numeroSade));
      documentoDTO.setIdTransaccion(obtenerIdTransaccion(documentoDetalle.getNumeroSade()));
      documentoDTO.setMotivoDepuracion(documentoDetalle.getMotivoDepuracion());
      
      documentoDTO.setListaUsuariosReservados(documentoDetalle.getListaUsuariosReservados() != null ?listaUsuariosReservadosToDTO(ListMapper.mapList(
          documentoDetalle.getListaUsuariosReservados(), this.mapper, UsuarioReservadoDTO.class)) : null);
      
      documentoDTO.setIdGuardaDocumental(documentoDetalle.getIdGuardaDocumental());

    } catch (DataAccessLayerException e) {
      LOGGER.error("completarDocumentoDTO(String, String)", e); //$NON-NLS-1$

      throw new DataAccessLayerException(e.getMessage(), e);
    } catch (BuscarDocumentosGedoServiceException e) {
      LOGGER.error("completarDocumentoDTO(String, String)", e); //$NON-NLS-1$

      throw new BuscarDocumentosGedoServiceException(e.getMessage(), e);
    } catch (Exception e) {
      LOGGER.error("completarDocumentoDTO(String, String)", e); //$NON-NLS-1$

      throw new VisualizacionDocumentoException(e.getMessage(), e);
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("completarDocumentoDTO(String, String) - end"); //$NON-NLS-1$
    }
    return documentoDTO;

  }
  
  @Override
  public List<String> obtenerImagenesPrevisualizacion(String numeroSade) throws obtenerImagenesException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerImagenesPrevisualizacion(String) - start"); //$NON-NLS-1$
    }

    String idGuardaDocumental = this.buscarDocumentosGedoService
        .buscarDocumentoPorNumero(numeroSade).getIdGuardaDocumental();
    byte[] contenido;

    // WEBDAV
    contenido = this.gestionArchivosWebDavService.obtenerArchivoDeTrabajoWebDav(
        this.gestionArchivosWebDavService.obtenerUbicacionDescarga(numeroSade));

    List<String> returnList = new ArrayList<>();
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerImagenesPrevisualizacion(String) - end"); //$NON-NLS-1$
    }
    return returnList;// this.pdfService.transformPDFToPNG(contenido);
  }

  public BufferedInputStream descargaArchivoDeTrabajo(ArchivoDeTrabajoDTO archivoDeTrabajoDTO)
      throws IOException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("descargaArchivoDeTrabajo(ArchivoDeTrabajoDTO) - start"); //$NON-NLS-1$
    }

    String fileName = archivoDeTrabajoDTO.getPathRelativo() + "/"
        + archivoDeTrabajoDTO.getNombreArchivo();
    InputStream file;
    String idGuardaDocumental = archivoDeTrabajoDTO.getIdGuardaDocumental();
    ByteArrayInputStream byteArrayInputStream;
    BufferedInputStream bufferedInputStream;
    try {

      // WEBDAV
      file = webDavService.getFileAsStream(fileName, null, null).getFileAsStream();

      byteArrayInputStream = new ByteArrayInputStream(IOUtils.toByteArray(file));
      bufferedInputStream = new BufferedInputStream(byteArrayInputStream);
    } catch (WebDAVConnectionException wde) {
      LOGGER.error("Error obteniendo archivo del WebDav", wde);
      throw new IOException("Error al conectarse al repositorio de documentos", wde);
    } catch (IOException e) {
      LOGGER.error("Error obteniendo contenido del archivo del WebDav", e);
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("descargaArchivoDeTrabajo(ArchivoDeTrabajoDTO) - end"); //$NON-NLS-1$
      }
      throw new IOException(
          "Error al obtener el contenido del archivo en el repositorio de documentos", e);
    }
    return bufferedInputStream;
  }
  
  @Override
  public BufferedInputStream descargaDocumento(VisualizarDocumentoDTO documentoDTO) throws IOException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("descargaDocumento(DocumentoDTO) - start"); //$NON-NLS-1$
    }

    InputStream file;
    BufferedInputStream bufferedInputStream = null;

    String idGuardaDocumental = documentoDTO.getIdGuardaDocumental();

    // WEBDAV
    file = this.gestionArchivosWebDavService.obtenerDocumento(documentoDTO.getNumeroSade())
        .getFileAsStream();

    byte[] contenido = IOUtils.toByteArray(file);
    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(contenido);
    bufferedInputStream = new BufferedInputStream(byteArrayInputStream);

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("descargaDocumento(DocumentoDTO) - end"); //$NON-NLS-1$
    }
    return bufferedInputStream;
  }

  private List<UsuarioReservadoDTO> listaUsuariosReservadosToDTO(
      List<UsuarioReservadoDTO> listUsuarioReservado) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("listaUsuariosReservadosToDTO(List<UsuarioReservadoDTO>) - start"); //$NON-NLS-1$
    }

    if (listUsuarioReservado == null) {
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("listaUsuariosReservadosToDTO(List<UsuarioReservadoDTO>) - end"); //$NON-NLS-1$
      }
      return null;
    }
    List<UsuarioReservadoDTO> listaUsuarioReservadoDTO = new ArrayList<UsuarioReservadoDTO>();
    try {
      Iterator<UsuarioReservadoDTO> iterator = listUsuarioReservado.iterator();
      while (iterator.hasNext()) {
        UsuarioReservadoDTO usuario = iterator.next();
        UsuarioReservadoDTO usuarioDTO = new UsuarioReservadoDTO();
        PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
        propertyUtilsBean.copyProperties(usuarioDTO, usuario);
        listaUsuarioReservadoDTO.add(usuarioDTO);
      }
    } catch (Exception e) {
      LOGGER.error("Transformando UsuariosReservados DocumentoDetalle en UsuariosReservadosDTO",
          e);
      throw new VisualizacionDocumentoException(
          "Transformando UsuariosReservados DocumentoDetalle en UsuariosReservadosDTO", e);
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("listaUsuariosReservadosToDTO(List<UsuarioReservadoDTO>) - end"); //$NON-NLS-1$
    }
    return listaUsuarioReservadoDTO;
  }

  private List<DocumentoMetadataDTO> listDocumentoMetadataToDTO(
      List<DocumentoMetadataDetalle> listDocumentoMetadataDetalle) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("listDocumentoMetadataToDTO(List<DocumentoMetadataDetalle>) - start"); //$NON-NLS-1$
    }

    if (listDocumentoMetadataDetalle == null) {
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("listDocumentoMetadataToDTO(List<DocumentoMetadataDetalle>) - end"); //$NON-NLS-1$
      }
      return null;
    }

    List<DocumentoMetadataDTO> listDocumentoMetadataDTO = new ArrayList<>();
    try {
      Iterator<DocumentoMetadataDetalle> iterator = listDocumentoMetadataDetalle.iterator();
      while (iterator.hasNext()) {
        DocumentoMetadataDetalle documentoMetadataDetalle = iterator.next();
        DocumentoMetadataDTO documentoMetadataDTO = new DocumentoMetadataDTO();
        PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
        propertyUtilsBean.copyProperties(documentoMetadataDTO, documentoMetadataDetalle);
        listDocumentoMetadataDTO.add(documentoMetadataDTO);
      }
    } catch (Exception e) {
      LOGGER.error("Transformando DocumentoMetadataDTO DocumentoDetalle en DocumentoDTO", e);
      throw new VisualizacionDocumentoException(
          "Transformando DocumentoMetadataDTO DocumentoDetalle en DocumentoDTO", e);
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("listDocumentoMetadataToDTO(List<DocumentoMetadataDetalle>) - end"); //$NON-NLS-1$
    }
    return listDocumentoMetadataDTO;
  }

  private List<ArchivoDeTrabajoDTO> listArchivoDeTrabajoToDTO(
      List<ArchivoDeTrabajoDetalle> listArchivoDeTrabajoDetalle) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("listArchivoDeTrabajoToDTO(List<ArchivoDeTrabajoDetalle>) - start"); //$NON-NLS-1$
    }

    if (listArchivoDeTrabajoDetalle == null) {
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("listArchivoDeTrabajoToDTO(List<ArchivoDeTrabajoDetalle>) - end"); //$NON-NLS-1$
      }
      return null;
    }

    List<ArchivoDeTrabajoDTO> listArchivoDeTrabajoDTO = new ArrayList<ArchivoDeTrabajoDTO>();
    try {
      Iterator<ArchivoDeTrabajoDetalle> iterator = listArchivoDeTrabajoDetalle.iterator();
      while (iterator.hasNext()) {
        ArchivoDeTrabajoDetalle archivoDeTrabajoDetalle = iterator.next();
        ArchivoDeTrabajoDTO archivoDeTrabajoDTO = new ArchivoDeTrabajoDTO();
        PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
        propertyUtilsBean.copyProperties(archivoDeTrabajoDTO, archivoDeTrabajoDetalle);
        listArchivoDeTrabajoDTO.add(archivoDeTrabajoDTO);
      }
    } catch (Exception e) {
      LOGGER.error("Transformando ArchivoDeTrabajo DocumentoDetalle en DocumentoDTO", e);
      throw new VisualizacionDocumentoException(
          "Transformando ArchivoDeTrabajo DocumentoDetalle en DocumentoDTO", e);
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("listArchivoDeTrabajoToDTO(List<ArchivoDeTrabajoDetalle>) - end"); //$NON-NLS-1$
    }
    return listArchivoDeTrabajoDTO;
  }

  private List<HistorialDTO> listHistorialToDTO(List<HistorialDetalle> listHistorialDetalle) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("listHistorialToDTO(List<HistorialDetalle>) - start"); //$NON-NLS-1$
    }

    if (listHistorialDetalle == null) {
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("listHistorialToDTO(List<HistorialDetalle>) - end"); //$NON-NLS-1$
      }
      return null;
    }

    List<HistorialDTO> listHistorialDTO = new ArrayList<>();
    try {
      Iterator<HistorialDetalle> iterator = listHistorialDetalle.iterator();
      while (iterator.hasNext()) {
        HistorialDetalle historialDetalle = iterator.next();
        HistorialDTO historialDTO = new HistorialDTO();
        PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
        propertyUtilsBean.copyProperties(historialDTO, historialDetalle);
        listHistorialDTO.add(historialDTO);
      }
    } catch (Exception e) {
      LOGGER.error("Transformando Historial DocumentoDetalle en DocumentoDTO", e);
      throw new VisualizacionDocumentoException(
          "Transformando Historial DocumentoDetalle en DocumentoDTO", e);
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("listHistorialToDTO(List<HistorialDetalle>) - end"); //$NON-NLS-1$
    }
    return listHistorialDTO;
  }

  private String obtenerIdFormulario(String numeroSade) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerIdFormulario(String) - start"); //$NON-NLS-1$
    }

    com.egoveris.deo.model.model.DocumentoDTO doc = buscarDocumentosGedoService
        .buscarDocumentoPorNumero(numeroSade);
    if (doc != null) { 
    com.egoveris.deo.model.model.TipoDocumentoDTO tipo = doc.getTipo();
    TipoDocumentoTemplateDTO ultimoTemplatePorTipoDocumento = procesamientoTemplate
        .obtenerUltimoTemplatePorTipoDocumento(tipo);
    if (ultimoTemplatePorTipoDocumento != null) {
      String returnString = ultimoTemplatePorTipoDocumento.getIdFormulario();
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("obtenerIdFormulario(String) - end"); //$NON-NLS-1$
      }
      return returnString;
    } else {
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("obtenerIdFormulario(String) - end"); //$NON-NLS-1$
      }
      return null;
    } 
    } else {
    	return null;
    } 
  }

  private Integer obtenerIdTransaccion(String numeroSade) throws ApplicationException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerIdTransaccion(String) - start"); //$NON-NLS-1$
    }
    Integer returnInteger = null;
	if (null != numeroSade) {
		// Query 
		DocumentoSolicitud documentoEnti = 	documentoSolicitudRepo.findByNumeroSade(numeroSade);
		// Validation
		if (null != documentoEnti && documentoEnti.getIdTransaccion() != null) {
			returnInteger = documentoEnti.getIdTransaccion().intValue();
		}  
	}
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerIdTransaccion(String) - end"); //$NON-NLS-1$
    }
    return returnInteger;
  }

  public VisualizarDocumentoDTO completarDocumentoDTO(String numeroSade, String loggedUsername,
      boolean assignee) throws VisualizacionDocumentoException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("completarDocumentoDTO(String, String, boolean) - start"); //$NON-NLS-1$
    }

    DocumentoDetalle documentoDetalle;
    VisualizarDocumentoDTO documentoDTO = new VisualizarDocumentoDTO();
    PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();

    try {
      documentoDetalle = this.buscarDocumentosGedoService.buscarDocumentoDetalle(numeroSade,
          loggedUsername, assignee);
      propertyUtilsBean.copyProperties(documentoDTO, documentoDetalle);
      documentoDTO
          .setDatosPropios(listDocumentoMetadataToDTO(documentoDetalle.getDatosPropiosDetalle()));
      documentoDTO.setListaArchivosDeTrabajo(
          listArchivoDeTrabajoToDTO(documentoDetalle.getListaArchivosDeTrabajoDetalle()));
      documentoDTO
          .setListaHistorialDTOs(listHistorialToDTO(documentoDetalle.getListaHistorialDetalle()));
      documentoDTO.setIdFormulario(obtenerIdFormulario(numeroSade));
      documentoDTO.setIdTransaccion(obtenerIdTransaccion(documentoDetalle.getNumeroSade()));
      documentoDTO.setMotivoDepuracion(documentoDetalle.getMotivoDepuracion());
      documentoDTO.setListaUsuariosReservados(listaUsuariosReservadosToDTO(ListMapper.mapList(
          documentoDetalle.getListaUsuariosReservados(), this.mapper, UsuarioReservadoDTO.class)));

    } catch (DataAccessLayerException e) {
      LOGGER.error("completarDocumentoDTO(String, String, boolean)", e); //$NON-NLS-1$

      throw new DataAccessLayerException(e.getMessage(), e);
    } catch (BuscarDocumentosGedoServiceException e) {
      LOGGER.error("completarDocumentoDTO(String, String, boolean)", e); //$NON-NLS-1$

      throw new BuscarDocumentosGedoServiceException(e.getMessage(), e);
    } catch (Exception e) {
      LOGGER.error("completarDocumentoDTO(String, String, boolean)", e); //$NON-NLS-1$

      throw new VisualizacionDocumentoException(e.getMessage(), e);
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("completarDocumentoDTO(String, String, boolean) - end"); //$NON-NLS-1$
    }
    return documentoDTO;

  }

  public byte[] obtenerPrimerasHojasPrevisualizacionDocumento(String numeroSade) throws IOException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerPrimerasHojasPrevisualizacionDocumento(String) - start"); //$NON-NLS-1$
    }

    byte[] returnbyteArray = this.previsualizacionDocumentoService
        .obtenerPrevisualizacionDocumentoReducida(numeroSade);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerPrimerasHojasPrevisualizacionDocumento(String) - end"); //$NON-NLS-1$
    }
    return returnbyteArray;
  }

  public int obtenerMaxCantPrevisualizar() {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerMaxCantPrevisualizar() - start"); //$NON-NLS-1$
    }

    int returnint = this.previsualizacionDocumentoService.obtenerMaximoPrevisualizacion();
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerMaxCantPrevisualizar() - end"); //$NON-NLS-1$
    }
    return returnint;
  }


}
