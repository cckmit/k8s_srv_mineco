package com.egoveris.deo.base.service.impl;

import com.egoveris.deo.base.model.Documento;
import com.egoveris.deo.base.model.TipoDocumento;
import com.egoveris.deo.base.repository.DocumentoRepository;
import com.egoveris.deo.base.service.ArchivoDeTrabajoService;
import com.egoveris.deo.base.service.BuscarDocumentosGedoService;
import com.egoveris.deo.base.service.FirmaConjuntaService;
import com.egoveris.deo.base.service.GestionArchivosWebDavService;
import com.egoveris.deo.base.service.HistorialReservaService;
import com.egoveris.deo.base.service.IHistorialProcesosService;
import com.egoveris.deo.base.service.LdapAccessor;
import com.egoveris.deo.base.service.ObtenerReparticionServices;
import com.egoveris.deo.base.solr.DynamicSearchRepository;
import com.egoveris.deo.base.util.Documento2DocumentoDetalleTransformer;
import com.egoveris.deo.base.util.DocumentoQuery;
import com.egoveris.deo.base.util.DocumentoQueryFactory;
import com.egoveris.deo.model.exception.DataAccessLayerException;
import com.egoveris.deo.model.model.ConsultaSolrRequest;
import com.egoveris.deo.model.model.DocumentoDTO;
import com.egoveris.deo.model.model.DocumentoDetalle;
import com.egoveris.deo.model.model.DocumentoSolr;
import com.egoveris.deo.model.model.FirmanteDTO;
import com.egoveris.deo.model.model.TipoDocumentoDTO;
import com.egoveris.deo.model.model.UsuarioReservadoDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.shared.map.ListMapper;
import com.egoveris.sharedorganismo.base.service.ReparticionServ;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BuscarDocumentosGedoServiceImpl implements BuscarDocumentosGedoService {
  private static final Logger LOGGER = LoggerFactory
      .getLogger(BuscarDocumentosGedoServiceImpl.class);
  @Autowired
  DocumentoQueryFactory documentoQueryFactory;
  @Autowired
  private DocumentoRepository documentoRepo;
  @Autowired
  private IHistorialProcesosService historialProcesosService;
  @Autowired
  @Qualifier("ldapAccessorImpl")
  private LdapAccessor sadeLdapAccessor;
  @Autowired
  private FirmaConjuntaService firmaConjuntaService;
  @Autowired
  private IUsuarioService usuarioService;
  @Autowired
  private ArchivoDeTrabajoService archivoDeTrabajoService;
  @Autowired
  private ReparticionServ reparticionServ;
  @Autowired
  @Qualifier("gestionArchivosWebDavServiceImpl")
  private GestionArchivosWebDavService gestionArchivosWebDavService;
  @Autowired
  public DynamicSearchRepository<DocumentoSolr> dynamicSearchRepository;
  @Autowired
  private HistorialReservaService historialReservaService;
  @Autowired
  private ObtenerReparticionServices obtenerReparticionService;
  
  private DozerBeanMapper mapper = new DozerBeanMapper();

  public boolean existenDocumentosTipo(TipoDocumentoDTO tipoDocumento) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("existenDocumentosTipo(TipoDocumentoDTO) - start"); //$NON-NLS-1$
    }

    TipoDocumento td = this.mapper.map(tipoDocumento, TipoDocumento.class);
    List<Documento> result = documentoRepo.findByTipo(td);
    boolean returnboolean = !result.isEmpty();
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("existenDocumentosTipo(TipoDocumentoDTO) - end"); //$NON-NLS-1$
    }
    return returnboolean;
  }

  public List<DocumentoDTO> buscarDocumentoPorNumeroConsulta(String numeroSADE, String acronimoDoc) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarDocumentoPorNumeroConsulta(String, String) - start"); //$NON-NLS-1$
    }
    
    List<Documento> documenEntity = this.documentoRepo.buscarDocumentoEstandarByNumeroConsulta(numeroSADE, acronimoDoc);

    List<DocumentoDTO> returnDocumentoDTO = null;
    if (null != documenEntity) {
    	returnDocumentoDTO = ListMapper.mapList(documenEntity, mapper, DocumentoDTO.class);
    } 
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarDocumentoPorNumeroConsulta(String, String) - end"); //$NON-NLS-1$
    }
    return returnDocumentoDTO;
  }

  public DocumentoDTO buscarDocumentoPorNumero(String numeroSADE) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarDocumentoPorNumero(String) - start"); //$NON-NLS-1$
    }
    DocumentoDTO returnDocumentoDTO = null;
    List<Documento> document = this.documentoRepo.findByNumero(numeroSADE);
    if (document != null && !document.isEmpty()) {
      returnDocumentoDTO = this.mapper.map(document.get(0), DocumentoDTO.class);
    }
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarDocumentoPorNumero(String) - end"); //$NON-NLS-1$
    }
    return returnDocumentoDTO;
  }

  public DocumentoDTO buscarDocumentoPorActuacion(String numeroSADE, String acronimoActuacion) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarDocumentoPorActuacion(String) - start"); //$NON-NLS-1$
    }
    DocumentoDTO returnDocumentoDTO = null;
    List<Documento> documentos = this.documentoRepo
    		.buscarDocumentoPorNumeroYactuacion(numeroSADE, acronimoActuacion);
    
    if(!documentos.isEmpty()) {
    	returnDocumentoDTO = this.mapper.map(documentos.get(0),
    			DocumentoDTO.class);
    }
    
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarDocumentoPorActuacion(String) - end"); //$NON-NLS-1$
    }
    return returnDocumentoDTO;
  }

  public List<DocumentoDTO> buscarDocumentosADepurar() {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarDocumentosADepurar() - start"); //$NON-NLS-1$
    }

    List<DocumentoDTO> returnList = new ArrayList();
    		
//    		ListMapper.mapList(
//        this.documentoRepo.findByFechaDepuracionIsNullAndMotivoDepuracionNotNull(), this.mapper,
//        DocumentoDTO.class);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarDocumentosADepurar() - end"); //$NON-NLS-1$
    }
    return returnList;
  }

  public List<DocumentoDTO> buscarDocumentosPorCriterio(Map inputMap) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarDocumentosPorCriterio(Map) - start"); //$NON-NLS-1$
    }

    List<DocumentoDTO> documentosList = null;
    DocumentoQuery persistenceDocumentoQuery = documentoQueryFactory.getInstance();

    try {
      documentosList = ListMapper.mapList(
          persistenceDocumentoQuery.getDocumentosPorCriteriaQuery(inputMap), this.mapper,
          DocumentoDTO.class);
    } catch (DataAccessLayerException dale) {
      LOGGER.error("Hubo un error en buscarDocumentosPorCriterio", dale);
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarDocumentosPorCriterio(Map) - end"); //$NON-NLS-1$
    }
    return documentosList;
  }

  public DocumentoDTO buscarDocumentoPorProceso(String workflowId) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarDocumentoPorProceso(String) - start"); //$NON-NLS-1$
    }
    Documento documentoEntity = this.documentoRepo.findByWorkflowOrigen(workflowId);
    DocumentoDTO returnDocumentoDTO = null;
    if (null !=  documentoEntity) {
    	returnDocumentoDTO = this.mapper
    	        .map(this.documentoRepo.findByWorkflowOrigen(workflowId), DocumentoDTO.class);
    }
    	 	
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarDocumentoPorProceso(String) - end"); //$NON-NLS-1$
    }
    return returnDocumentoDTO;
  }

  public Boolean puedeVerDocumentoConfidencial(DocumentoDTO documento, String usuario,
      String reparticionUsuario) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("puedeVerDocumentoConfidencial(DocumentoDTO, String, String) - start"); //$NON-NLS-1$
    }

    Boolean esReservado = false;
    // Si el usuario forma parte de la lista de usuarios reservados del
    // documento,
    // Este podra consultar el documento sin necesidad de tener ningun tipo
    // de permisos, ni pertenecer a la reparticion firmante.
	List<UsuarioReservadoDTO> listaUsuariosReservados = new ArrayList();
	if (null != documento.getUsuariosReservados()) {
		listaUsuariosReservados = new ArrayList<>(documento.getUsuariosReservados());
	}

    if (!listaUsuariosReservados.isEmpty()) {
      for (UsuarioReservadoDTO usuarioReservado : listaUsuariosReservados) {
        if (usuarioReservado.getUserName().equalsIgnoreCase(usuario)) {
          if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("puedeVerDocumentoConfidencial(DocumentoDTO, String, String) - end"); //$NON-NLS-1$
          }
          return true;
        }
      }
    }

    if (documento.getTipo() != null && documento.getTipo().getEsFirmaConjunta()) {
      Iterator<FirmanteDTO> iterator = this.firmaConjuntaService
          .buscarUsuarioFirmantesPorProceso(documento.getWorkflowOrigen()).iterator();
      while (iterator.hasNext()) {
        FirmanteDTO current = iterator.next();
        if (current.getUsuarioFirmante().equals(usuario)
            || (current.getApoderado() != null && current.getApoderado().equals(usuario))
            || (documento.getApoderado() != null && documento.getApoderado().equals(usuario))) {
          if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("puedeVerDocumentoConfidencial(DocumentoDTO, String, String) - end"); //$NON-NLS-1$
          }
          return true;
        }
      }
    } else {
      // Si la persona firmo el documento puede verlo sin mas
      // restricciones
      if (usuario.equals(documento.getUsuarioGenerador())
          || (documento.getApoderado() != null && documento.getApoderado().equals(usuario))) {
        if (LOGGER.isDebugEnabled()) {
          LOGGER.debug("puedeVerDocumentoConfidencial(DocumentoDTO, String, String) - end"); //$NON-NLS-1$
        }
        return true;
      }
    }
    if (documento.getTipo().getEsFirmaConjunta()) {
      Iterator<FirmanteDTO> iterator = this.firmaConjuntaService
          .buscarUsuarioFirmantesPorProceso(documento.getWorkflowOrigen()).iterator();
      while (iterator.hasNext()) {
        FirmanteDTO current = iterator.next();
        if (current.getUsuarioFirmante().equals(usuario)
            || (current.getApoderado() != null && current.getApoderado().equals(usuario))
            || (documento.getApoderado() != null && documento.getApoderado().equals(usuario))) {
          if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("puedeVerDocumentoConfidencial(DocumentoDTO, String, String) - end"); //$NON-NLS-1$
          }
          return true;
        }
      }
    } else {
      // Si la persona firmo el documento puede verlo sin mas
      // restricciones
      if (usuario.equals(documento.getUsuarioGenerador())
          || (documento.getApoderado() != null && documento.getApoderado().equals(usuario))) {
        if (LOGGER.isDebugEnabled()) {
          LOGGER.debug("puedeVerDocumentoConfidencial(DocumentoDTO, String, String) - end"); //$NON-NLS-1$
        }
        return true;
      }
    }
    if (documento.getTipo().getEsFirmaConjunta()) {
      Iterator<FirmanteDTO> iterator = this.firmaConjuntaService
          .buscarUsuarioFirmantesPorProceso(documento.getWorkflowOrigen()).iterator();
      while (iterator.hasNext()) {
        FirmanteDTO current = iterator.next();
        if (current.getUsuarioFirmante().equals(usuario)
            || (current.getApoderado() != null && current.getApoderado().equals(usuario))
            || (documento.getApoderado() != null && documento.getApoderado().equals(usuario))) {
          if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("puedeVerDocumentoConfidencial(DocumentoDTO, String, String) - end"); //$NON-NLS-1$
          }
          return true;
        }
      }
    } else {
      // Si la persona firmo el documento puede verlo sin mas
      // restricciones
      if (usuario.equals(documento.getUsuarioGenerador())
          || (documento.getApoderado() != null && documento.getApoderado().equals(usuario))) {
        if (LOGGER.isDebugEnabled()) {
          LOGGER.debug("puedeVerDocumentoConfidencial(DocumentoDTO, String, String) - end"); //$NON-NLS-1$
        }
        return true;
      }
    }
    if (documento.getTipo().getEsFirmaConjunta()) {
      Iterator<FirmanteDTO> iterator = this.firmaConjuntaService
          .buscarUsuarioFirmantesPorProceso(documento.getWorkflowOrigen()).iterator();
      while (iterator.hasNext()) {
        FirmanteDTO current = iterator.next();
        if (current.getUsuarioFirmante().equals(usuario)
            || (current.getApoderado() != null && current.getApoderado().equals(usuario))
            || (documento.getApoderado() != null && documento.getApoderado().equals(usuario))) {
          if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("puedeVerDocumentoConfidencial(DocumentoDTO, String, String) - end"); //$NON-NLS-1$
          }
          return true;
        }
      }
    } else {
      // Si la persona firmo el documento puede verlo sin mas
      // restricciones
      if (usuario.equals(documento.getUsuarioGenerador())
          || (documento.getApoderado() != null && documento.getApoderado().equals(usuario))) {
        if (LOGGER.isDebugEnabled()) {
          LOGGER.debug("puedeVerDocumentoConfidencial(DocumentoDTO, String, String) - end"); //$NON-NLS-1$
        }
        return true;
      }
    }
    if (documento.getTipo().getEsFirmaConjunta()) {
      Iterator<FirmanteDTO> iterator = this.firmaConjuntaService
          .buscarUsuarioFirmantesPorProceso(documento.getWorkflowOrigen()).iterator();
      while (iterator.hasNext()) {
        FirmanteDTO current = iterator.next();
        if (current.getUsuarioFirmante().equals(usuario)
            || (current.getApoderado() != null && current.getApoderado().equals(usuario))
            || (documento.getApoderado() != null && documento.getApoderado().equals(usuario))) {
          if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("puedeVerDocumentoConfidencial(DocumentoDTO, String, String) - end"); //$NON-NLS-1$
          }
          return true;
        }
      }
    } else {
      // Si la persona firmo el documento puede verlo sin mas
      // restricciones
      if (usuario.equals(documento.getUsuarioGenerador())
          || (documento.getApoderado() != null && documento.getApoderado().equals(usuario))) {
        if (LOGGER.isDebugEnabled()) {
          LOGGER.debug("puedeVerDocumentoConfidencial(DocumentoDTO, String, String) - end"); //$NON-NLS-1$
        }
        return true;
      }
    }
    if (documento.getTipo().getEsFirmaConjunta()) {
      Iterator<FirmanteDTO> iterator = this.firmaConjuntaService
          .buscarUsuarioFirmantesPorProceso(documento.getWorkflowOrigen()).iterator();
      while (iterator.hasNext()) {
        FirmanteDTO current = iterator.next();
        if (current.getUsuarioFirmante().equals(usuario)
            || (current.getApoderado() != null && current.getApoderado().equals(usuario))
            || (documento.getApoderado() != null && documento.getApoderado().equals(usuario))) {
          if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("puedeVerDocumentoConfidencial(DocumentoDTO, String, String) - end"); //$NON-NLS-1$
          }
          return true;
        }
      }
    } else {
      // Si la persona firmo el documento puede verlo sin mas
      // restricciones
      if (usuario.equals(documento.getUsuarioGenerador())
          || (documento.getApoderado() != null && documento.getApoderado().equals(usuario))) {
        if (LOGGER.isDebugEnabled()) {
          LOGGER.debug("puedeVerDocumentoConfidencial(DocumentoDTO, String, String) - end"); //$NON-NLS-1$
        }
        return true;
      }
    }
    if (documento.getTipo().getEsFirmaConjunta()) {
      Iterator<FirmanteDTO> iterator = this.firmaConjuntaService
          .buscarUsuarioFirmantesPorProceso(documento.getWorkflowOrigen()).iterator();
      while (iterator.hasNext()) {
        FirmanteDTO current = iterator.next();
        if (current.getUsuarioFirmante().equals(usuario)
            || (current.getApoderado() != null && current.getApoderado().equals(usuario))
            || (documento.getApoderado() != null && documento.getApoderado().equals(usuario))) {
          if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("puedeVerDocumentoConfidencial(DocumentoDTO, String, String) - end"); //$NON-NLS-1$
          }
          return true;
        }
      }
    } else {
      // Si la persona firmo el documento puede verlo sin mas
      // restricciones
      if (usuario.equals(documento.getUsuarioGenerador())
          || (documento.getApoderado() != null && documento.getApoderado().equals(usuario))) {
        if (LOGGER.isDebugEnabled()) {
          LOGGER.debug("puedeVerDocumentoConfidencial(DocumentoDTO, String, String) - end"); //$NON-NLS-1$
        }
        return true;
      }
    }
    if (documento.getTipo().getEsFirmaConjunta()) {
      Iterator<FirmanteDTO> iterator = this.firmaConjuntaService
          .buscarUsuarioFirmantesPorProceso(documento.getWorkflowOrigen()).iterator();
      while (iterator.hasNext()) {
        FirmanteDTO current = iterator.next();
        if (current.getUsuarioFirmante().equals(usuario)
            || (current.getApoderado() != null && current.getApoderado().equals(usuario))
            || (documento.getApoderado() != null && documento.getApoderado().equals(usuario))) {
          if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("puedeVerDocumentoConfidencial(DocumentoDTO, String, String) - end"); //$NON-NLS-1$
          }
          return true;
        }
      }
    } else {
      // Si la persona firmo el documento puede verlo sin mas
      // restricciones
      if (usuario.equals(documento.getUsuarioGenerador())
          || (documento.getApoderado() != null && documento.getApoderado().equals(usuario))) {
        if (LOGGER.isDebugEnabled()) {
          LOGGER.debug("puedeVerDocumentoConfidencial(DocumentoDTO, String, String) - end"); //$NON-NLS-1$
        }
        return true;
      }
    }

    switch (documento.getTipoReserva().getId()) {
    case 1: // NO _RESERVADO O RESERVA_POR_TIPO

      if (documento.getTipo().getEsConfidencial()) {
        if (esReparticionFirmante(documento, reparticionUsuario)
            && tienePermisoConfidencial(usuario)) {
          if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("puedeVerDocumentoConfidencial(DocumentoDTO, String, String) - end"); //$NON-NLS-1$
          }
          return true;
        }
      } else {
        if (LOGGER.isDebugEnabled()) {
          LOGGER.debug("puedeVerDocumentoConfidencial(DocumentoDTO, String, String) - end"); //$NON-NLS-1$
        }
        return true;
      }

      break;

    case 2:
    case 3:
    case 4: // RESERVA_TOTAL, RESERVA_PARCIAL Y _RESERVA_TRAMITACION
      if (puedeVerElDocumento(usuario, documento.getNumero())) {
        if (LOGGER.isDebugEnabled()) {
          LOGGER.debug("puedeVerDocumentoConfidencial(DocumentoDTO, String, String) - end"); //$NON-NLS-1$
        }
        return true;
      }

      break;

    default:
      return esReservado;
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("puedeVerDocumentoConfidencial(DocumentoDTO, String, String) - end"); //$NON-NLS-1$
    }
    return esReservado;
  }

  /**
   * Verifica que la reparticion del usuario sea la firmante del documento.
   * 
   * @param documento
   * @param reparticionUsuario
   * @return
   */
  private boolean esReparticionFirmante(DocumentoDTO documento, String reparticionUsuario) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("esReparticionFirmante(DocumentoDTO, String) - start"); //$NON-NLS-1$
    }

    boolean returnboolean = StringUtils.equals(reparticionUsuario.trim(),
        documento.getReparticionActual().trim());
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("esReparticionFirmante(DocumentoDTO, String) - end"); //$NON-NLS-1$
    }
    return returnboolean;
  }

  /**
   * Verifico que el usuario posea permisos gedo.confidencial.
   * 
   * @param usuario
   * @return
   */
  private boolean tienePermisoConfidencial(String usuario) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("tienePermisoConfidencial(String) - start"); //$NON-NLS-1$
    }

    boolean returnboolean = sadeLdapAccessor.puedeVerDocumentosConfidenciales(usuario);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("tienePermisoConfidencial(String) - end"); //$NON-NLS-1$
    }
    return returnboolean;
  }

  @SuppressWarnings("unchecked")
  public List<DocumentoDetalle> buscarDocumentosDetallePorCriterio(Map inputMap) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarDocumentosDetallePorCriterio(Map) - start"); //$NON-NLS-1$
    }

    Collection<DocumentoDetalle> documentosDetalle;
    // Busco el documento en Gedo
    documentosDetalle = CollectionUtils.collect(buscarDocumentosPorCriterio(inputMap),
        new Documento2DocumentoDetalleTransformer(inputMap.get("usuarioConsulta").toString(),
            historialProcesosService, sadeLdapAccessor, firmaConjuntaService, usuarioService,
            archivoDeTrabajoService, reparticionServ, gestionArchivosWebDavService, this,
            obtenerReparticionService));

    List<DocumentoDetalle> returnList = (List<DocumentoDetalle>) documentosDetalle;
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarDocumentosDetallePorCriterio(Map) - end"); //$NON-NLS-1$
    }
    return returnList;
  }

  public DocumentoDetalle buscarDocumentoDetalle(String numeroSade, String loggedUsername) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarDocumentoDetalle(String, String) - start"); //$NON-NLS-1$
    }

    DocumentoDetalle documentoDetalle = new DocumentoDetalle();

    try {
      // Busco el documento en Gedo
      documentoDetalle = (DocumentoDetalle) new Documento2DocumentoDetalleTransformer(
          loggedUsername, historialProcesosService, sadeLdapAccessor, firmaConjuntaService,
          usuarioService, archivoDeTrabajoService, reparticionServ, gestionArchivosWebDavService,
          this, obtenerReparticionService).transform(this.buscarDocumentoPorNumero(numeroSade));
    } catch (Exception e) {
      LOGGER.error("Hubo un error en buscarDocumentoDetalle", e);
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarDocumentoDetalle(String, String) - end"); //$NON-NLS-1$
    }
    return documentoDetalle;
  }

  public Page<DocumentoSolr> buscarDocumentos(ConsultaSolrRequest reqSolr) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarDocumentos(ConsultaSolrRequest) - start"); //$NON-NLS-1$
    }

    Page<DocumentoSolr> returnPage = dynamicSearchRepository.search(reqSolr);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarDocumentos(ConsultaSolrRequest) - end"); //$NON-NLS-1$
    }
    return returnPage;
  }

  public Page<DocumentoSolr> buscarDocPorNumero(String numeroSADE, String acronimoDoc) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarDocPorNumero(String, String) - start"); //$NON-NLS-1$
    }

    List<DocumentoDTO> documentoPorNumero = this.buscarDocumentoPorNumeroConsulta(numeroSADE,
        acronimoDoc);
    List<DocumentoSolr> list = new ArrayList<>();
    if (documentoPorNumero != null) {
    	for (DocumentoDTO documentoSolr : documentoPorNumero) {
    		list.add(docDominioASolr(documentoSolr));
		} 
    }
    Page<DocumentoSolr> returnPage = new PageImpl<>(list);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarDocPorNumero(String, String) - end"); //$NON-NLS-1$
    }
    return returnPage;
  }

  public Page<DocumentoSolr> buscarDocEspecial(String nroDocEspecial) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarDocEspecial(String) - start"); //$NON-NLS-1$
    }

    DocumentoQuery dq = documentoQueryFactory.getInstance();
    dq.numeroEspecial(nroDocEspecial);
    Page<DocumentoSolr> returnPage = listar(dq);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarDocEspecial(String) - end"); //$NON-NLS-1$
    }
    return returnPage;
  }

  public Page<DocumentoSolr> buscarDocSadePapel(String nroDocSadePapel) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarDocSadePapel(String) - start"); //$NON-NLS-1$
    }

    DocumentoQuery dq = documentoQueryFactory.getInstance();
    dq.numeroSadePapel(nroDocSadePapel);
    Page<DocumentoSolr> returnPage = listar(dq);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarDocSadePapel(String) - end"); //$NON-NLS-1$
    }
    return returnPage;
  }

  private Page<DocumentoSolr> listar(DocumentoQuery dq) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("listar(DocumentoQuery) - start"); //$NON-NLS-1$
    }

    List<DocumentoDTO> lista = ListMapper.mapList(dq.listar(), this.mapper, DocumentoDTO.class);
    if (lista != null) {
      Page<DocumentoSolr> returnPage = new PageImpl<>(listaDominioASolr(lista));
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("listar(DocumentoQuery) - end"); //$NON-NLS-1$
      }
      return returnPage;
    } else {
      Page<DocumentoSolr> returnPage = new PageImpl<>(new ArrayList<DocumentoSolr>());
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("listar(DocumentoQuery) - end"); //$NON-NLS-1$
      }
      return returnPage;
    }
  }

  private List<DocumentoSolr> listaDominioASolr(List<DocumentoDTO> listaDoc) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("listaDominioASolr(List<DocumentoDTO>) - start"); //$NON-NLS-1$
    }

    List<DocumentoSolr> result = new ArrayList<>();
    for (DocumentoDTO doc : listaDoc) {
      result.add(docDominioASolr(doc));
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("listaDominioASolr(List<DocumentoDTO>) - end"); //$NON-NLS-1$
    }
    return result;
  }

  private DocumentoSolr docDominioASolr(DocumentoDTO doc) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("docDominioASolr(DocumentoDTO) - start"); //$NON-NLS-1$
    }

    DocumentoSolr docSolr = new DocumentoSolr();
    docSolr.setId(doc.getId() != null ? doc.getId().longValue() : null);
    docSolr.setNroSade(doc.getNumero());
    docSolr.setFechaCreacion(doc.getFechaCreacion());
    docSolr.setNroEspecialSade(doc.getNumeroEspecial());
    docSolr.setUsuarioGenerador(doc.getUsuarioGenerador());
    docSolr.setReferencia(doc.getMotivo());
    docSolr.setTipoDocAcr(doc.getTipo() != null ? doc.getTipo().getAcronimo() : null);
    docSolr.setTipoDocNombre(doc.getTipo() != null ? doc.getTipo().getNombre() : null);

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("docDominioASolr(DocumentoDTO) - end"); //$NON-NLS-1$
    }
    return docSolr;

  }

  public Page<DocumentoSolr> buscarDocPorActuacion(String numeroSADE, 
  		String acronimoActuacion) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarDocPorActuacion(String) - start"); //$NON-NLS-1$
    }

    DocumentoDTO documentoPorNumero = this.buscarDocumentoPorActuacion(numeroSADE, 
    		acronimoActuacion);
    List<DocumentoSolr> list = new ArrayList<>();
    if (documentoPorNumero != null) {
      list.add(docDominioASolr(documentoPorNumero));
    }
    Page<DocumentoSolr> returnPage = new PageImpl<>(list);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarDocPorActuacion(String) - end"); //$NON-NLS-1$
    }
    return returnPage;
  }

  private boolean puedeVerElDocumento(String username, String nroDocumento) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("puedeVerElDocumento(String, String) - start"); //$NON-NLS-1$
    }

    Usuario usuario = null;
    Collection<GrantedAuthority> lstRoles;
    boolean permisoRectora = false;
    boolean permisoReparticion = false;
    boolean permisoSector = false;
    boolean puedeVer = false;

    try {
      usuario = this.usuarioService.obtenerUsuario(username);
    } catch (SecurityNegocioException e) {
      LOGGER.error("Error al obtener los datos del usuario: " + e.getMessage(), e);
    }
    if (usuario != null) {

      lstRoles = usuario.getAuthorities();

      for (GrantedAuthority rol : lstRoles) {
        if (rol.getAuthority().compareTo(Constantes.CONFIDENCIAL_RECTORA) == 0) {
          permisoRectora = true;
        }
        if (rol.getAuthority().compareTo(Constantes.CONFIDENCIAL_REPARTICION) == 0) {
          permisoReparticion = true;
        }
        if (rol.getAuthority().compareTo(Constantes.CONFIDENCIAL_SECTOR) == 0) {
          permisoSector = true;
        }
      }

      if (permisoRectora) {
        if (permisoReparticion) {
          if (permisoSector) {
            // rectora - reparticion - sector
            puedeVer = buscarTodosLosPermisos(usuario.getCodigoReparticion(), nroDocumento,
                username);
          } else {
            // rectora - reparticion
            puedeVer = verRectoraReparticion(usuario.getUsername(), nroDocumento,
                usuario.getCodigoReparticion());
          }
        } else if (permisoSector) {
          // rectora - sector
          puedeVer = verRectoraSector(usuario.getUsername(), nroDocumento,
              usuario.getCodigoReparticion(), usuario.getCodigoSectorInterno());
        } else {
          // rectora
          if (!buscarSoloRectora(usuario.getCodigoReparticion(), nroDocumento)) {
            puedeVer = buscarPorUsuario(username, nroDocumento);
          } else {
            puedeVer = true;
          }
        }
      } else if (permisoReparticion) {
        if (permisoSector) {
          // reparticion - sector
          puedeVer = verReparticionSector(nroDocumento, usuario.getCodigoReparticion(),
              usuario.getUsername());
        } else {
          // reparticion
          if (!buscarSoloReparticion(nroDocumento, usuario.getCodigoReparticion())) {
            puedeVer = buscarPorUsuario(usuario.getUsername(), nroDocumento);
          } else {
            puedeVer = true;
          }
        }
      } else if (permisoSector) {
        // sector
        if (!buscarSoloSector(usuario.getCodigoSectorInterno(), nroDocumento,
            usuario.getCodigoReparticion())) {
          puedeVer = buscarPorUsuario(usuario.getUsername(), nroDocumento);
        } else {
          puedeVer = true;
        }
      } else {
        // sin permisos
        puedeVer = buscarPorUsuario(usuario.getUsername(), nroDocumento);
      }
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("puedeVerElDocumento(String, String) - end"); //$NON-NLS-1$
    }
    return puedeVer;
  }

  private boolean verReparticionSector(String documento, String reparticion, String usuario) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("verReparticionSector(String, String, String) - start"); //$NON-NLS-1$
    }

    boolean puedeVer = false;
    if (!buscarSoloReparticion(documento, reparticion)) {
      if (buscarPorUsuario(usuario, documento)) {
        puedeVer = true;
      }
    } else {
      puedeVer = true;
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("verReparticionSector(String, String, String) - end"); //$NON-NLS-1$
    }
    return puedeVer;
  }

  private boolean verRectoraSector(String usuario, String documento, String reparticion,
      String sector) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("verRectoraSector(String, String, String, String) - start"); //$NON-NLS-1$
    }

    boolean puedeVer = false;
    if (!buscarSoloRectora(reparticion, documento)) {
      if (!buscarSoloSector(sector, documento, reparticion)) {
        if (buscarPorUsuario(usuario, documento)) {
          // lo encontro por usuario
          puedeVer = true;
        }
      } else {
        // lo encontro por sector
        puedeVer = true;
      }
    } else {
      // lo encontro por rectora
      puedeVer = true;
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("verRectoraSector(String, String, String, String) - end"); //$NON-NLS-1$
    }
    return puedeVer;
  }

  private boolean verRectoraReparticion(String usuario, String documento, String reparticion) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("verRectoraReparticion(String, String, String) - start"); //$NON-NLS-1$
    }

    boolean puedeVer = false;
    if (!buscarSoloRectora(reparticion, documento)) {
      if (!buscarSoloReparticion(documento, reparticion)) {
        if (buscarPorUsuario(usuario, documento)) {
          puedeVer = true;
        }
      } else {
        puedeVer = true;
      }
    } else {
      puedeVer = true;
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("verRectoraReparticion(String, String, String) - end"); //$NON-NLS-1$
    }
    return puedeVer;
  }

  private boolean buscarSoloRectora(String reparticion, String documento) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarSoloRectora(String, String) - start"); //$NON-NLS-1$
    }

    if (buscarTodas(documento)) {
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("buscarSoloRectora(String, String) - end"); //$NON-NLS-1$
      }
      return true;
    } else {
      boolean returnboolean = this.historialReservaService
          .buscarDocumentoPorRectora(documento, reparticion).size() != 0;
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("buscarSoloRectora(String, String) - end"); //$NON-NLS-1$
      }
      return returnboolean;
    }
  }

  private boolean buscarSoloReparticion(String documento, String reparticion) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarSoloReparticion(String, String) - start"); //$NON-NLS-1$
    }

    boolean returnboolean = this.historialReservaService
        .buscarDocumentoPorReparticion(reparticion, documento).size() != 0;
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarSoloReparticion(String, String) - end"); //$NON-NLS-1$
    }
    return returnboolean;

  }

  private boolean buscarPorUsuario(String usuario, String documento) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarPorUsuario(String, String) - start"); //$NON-NLS-1$
    }

    boolean returnboolean = this.historialReservaService
        .buscarDocumentoPorUsuario(usuario, documento).size() != 0;
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarPorUsuario(String, String) - end"); //$NON-NLS-1$
    }
    return returnboolean;

  }

  private boolean buscarSoloSector(String sector, String documento, String reparticion) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarSoloSector(String, String, String) - start"); //$NON-NLS-1$
    }

    boolean returnboolean = this.historialReservaService
        .buscarDocumentoPorSector(sector, documento, reparticion).size() != 0;
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarSoloSector(String, String, String) - end"); //$NON-NLS-1$
    }
    return returnboolean;
  }

  private boolean buscarTodosLosPermisos(String reparticion, String documento, String usuario) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarTodosLosPermisos(String, String, String) - start"); //$NON-NLS-1$
    }

    boolean puedeVer = false;
    if (!buscarSoloRectora(reparticion, documento)) {
      // no esta por rectora
      if (!buscarSoloReparticion(documento, reparticion)) {
        // no esta por reparticion - no se podria buscar por sector,
        // porque usa la reparticion, entonces se busca por usuario
        if (buscarPorUsuario(usuario, documento)) {
          puedeVer = true;
        }
      } else {
        puedeVer = true;
      }
    } else {
      puedeVer = true;
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarTodosLosPermisos(String, String, String) - end"); //$NON-NLS-1$
    }
    return puedeVer;
  }

  private boolean buscarTodas(String documento) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarTodas(String) - start"); //$NON-NLS-1$
    }

    boolean returnboolean = historialReservaService.buscarTodas(documento).size() != 0;
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarTodas(String) - end"); //$NON-NLS-1$
    }
    return returnboolean;
  }

  public boolean puedeVerDocumentoGedo(DocumentoDTO documento, String reparticion,
      String usuario) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("puedeVerDocumentoGedo(DocumentoDTO, String, String) - start"); //$NON-NLS-1$
    }

    // Si el usuario forma parte de la lista de usuarios reservados del
    // documento,
    // Este podra consultar el documento sin necesidad de tener ningun tipo
    // de permisos, ni pertenecer a la reparticion firmante.
    List<UsuarioReservadoDTO> listaUsuariosReservados = new ArrayList<UsuarioReservadoDTO>(
        documento.getUsuariosReservados());
    if (!listaUsuariosReservados.isEmpty()) {
      for (UsuarioReservadoDTO usuarioReservado : listaUsuariosReservados) {
        if (usuarioReservado.getUserName().equalsIgnoreCase(usuario)) {
          if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("puedeVerDocumentoGedo(DocumentoDTO, String, String) - end"); //$NON-NLS-1$
          }
          return true;
        }
      }
    }
    // Si la persona firmo el documento puede verlo sin mas restricciones
    if (usuario.compareTo(documento.getUsuarioGenerador()) == 0) {
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("puedeVerDocumentoGedo(DocumentoDTO, String, String) - end"); //$NON-NLS-1$
      }
      return true;
    }
    // Logica de GEDO
    if (documento.getTipo().getEsConfidencial()) {
      if (esReparticionFirmante(documento, reparticion) && tienePermisoConfidencial(usuario)) {
        if (LOGGER.isDebugEnabled()) {
          LOGGER.debug("puedeVerDocumentoGedo(DocumentoDTO, String, String) - end"); //$NON-NLS-1$
        }
        return true;
      }
    } else {
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("puedeVerDocumentoGedo(DocumentoDTO, String, String) - end"); //$NON-NLS-1$
      }
      return true;
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("puedeVerDocumentoGedo(DocumentoDTO, String, String) - end"); //$NON-NLS-1$
    }
    return false;
  }

  public DocumentoDetalle buscarDocumentoDetalle(String numeroSade, String loggedUsername,
      boolean assignee) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarDocumentoDetalle(String, String, boolean) - start"); //$NON-NLS-1$
    }

    DocumentoDetalle documentoDetalle = new DocumentoDetalle();

    try {
      // Busco el documento en Gedo
      documentoDetalle = (DocumentoDetalle) new Documento2DocumentoDetalleTransformer(
          loggedUsername, historialProcesosService, sadeLdapAccessor, firmaConjuntaService,
          usuarioService, archivoDeTrabajoService, reparticionServ, gestionArchivosWebDavService,
          this, obtenerReparticionService, assignee)
              .transform(this.buscarDocumentoPorNumero(numeroSade));
    } catch (Exception e) {
      LOGGER.error("Hubo un error en buscarDocumentoDetalle", e);
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarDocumentoDetalle(String, String, boolean) - end"); //$NON-NLS-1$
    }
    return documentoDetalle;
  }
}
