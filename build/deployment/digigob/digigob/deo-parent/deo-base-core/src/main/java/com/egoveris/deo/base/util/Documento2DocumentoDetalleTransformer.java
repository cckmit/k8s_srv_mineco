package com.egoveris.deo.base.util;

import com.egoveris.deo.base.service.ArchivoDeTrabajoService;
import com.egoveris.deo.base.service.BuscarDocumentosGedoService;
import com.egoveris.deo.base.service.FirmaConjuntaService;
import com.egoveris.deo.base.service.GestionArchivosWebDavService;
import com.egoveris.deo.base.service.IHistorialProcesosService;
import com.egoveris.deo.base.service.LdapAccessor;
import com.egoveris.deo.base.service.ObtenerReparticionServices;
import com.egoveris.deo.model.model.ArchivoDeTrabajoDTO;
import com.egoveris.deo.model.model.ArchivoDeTrabajoDetalle;
import com.egoveris.deo.model.model.DocumentoDTO;
import com.egoveris.deo.model.model.DocumentoDetalle;
import com.egoveris.deo.model.model.DocumentoMetadataDTO;
import com.egoveris.deo.model.model.DocumentoMetadataDetalle;
import com.egoveris.deo.model.model.HistorialDTO;
import com.egoveris.deo.model.model.HistorialDetalle;
import com.egoveris.deo.model.model.UsuarioReservadoDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.sharedorganismo.base.model.ReparticionBean;
import com.egoveris.sharedorganismo.base.service.ReparticionServ;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.Transformer;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Deprecated
public class Documento2DocumentoDetalleTransformer implements Transformer {
  private IHistorialProcesosService historialProcesosService;
  private FirmaConjuntaService firmaConjuntaService;
  private IUsuarioService usuarioService;
  private ArchivoDeTrabajoService archivoDeTrabajoService;
  private GestionArchivosWebDavService gestionArchivosWebDavService;
  private BuscarDocumentosGedoService buscarDocumentosGedoService;
  private ObtenerReparticionServices obtenerReparticionService;
  private boolean assignee;
  private DozerBeanMapper mapper = new DozerBeanMapper();

  private static transient Logger logger = LoggerFactory
      .getLogger(Documento2DocumentoDetalleTransformer.class);

  /* uuid de usuario consultor del <code>Documento</code> */
  private String uuidUsuario;

  public Documento2DocumentoDetalleTransformer(final String uuidUsuario,
      final IHistorialProcesosService historialProcesosService,
      final LdapAccessor sadeLdapAccessor, final FirmaConjuntaService firmaConjuntaService,
      final IUsuarioService usuarioService, final ArchivoDeTrabajoService archivoDeTrabajoService,
      final ReparticionServ reparticionServ,
      final GestionArchivosWebDavService gestionArchivosWebDavService,
      final BuscarDocumentosGedoService buscarDococumentoGedoService,
      final ObtenerReparticionServices obtenerReparticionService) {
    this.historialProcesosService = historialProcesosService;
    this.firmaConjuntaService = firmaConjuntaService;
    this.usuarioService = usuarioService;
    this.archivoDeTrabajoService = archivoDeTrabajoService;
    this.uuidUsuario = uuidUsuario;
    this.gestionArchivosWebDavService = gestionArchivosWebDavService;
    this.buscarDocumentosGedoService = buscarDococumentoGedoService;
    this.obtenerReparticionService = obtenerReparticionService;
  }

  public Documento2DocumentoDetalleTransformer(final String uuidUsuario,
      final IHistorialProcesosService historialProcesosService,
      final LdapAccessor sadeLdapAccessor, final FirmaConjuntaService firmaConjuntaService,
      final IUsuarioService usuarioService, final ArchivoDeTrabajoService archivoDeTrabajoService,
      final ReparticionServ reparticionServ,
      final GestionArchivosWebDavService gestionArchivosWebDavService,
      final BuscarDocumentosGedoService buscarDococumentoGedoService,
      final ObtenerReparticionServices obtenerReparticionService, boolean assignee) {
    this.historialProcesosService = historialProcesosService;
    this.firmaConjuntaService = firmaConjuntaService;
    this.usuarioService = usuarioService;
    this.archivoDeTrabajoService = archivoDeTrabajoService;
    this.uuidUsuario = uuidUsuario;
    this.gestionArchivosWebDavService = gestionArchivosWebDavService;
    this.buscarDocumentosGedoService = buscarDococumentoGedoService;
    this.obtenerReparticionService = obtenerReparticionService;
    this.assignee = assignee;
  }

  public Object transform(Object input) {
    DocumentoDTO documento = (DocumentoDTO) input;
    List<String> listaFirmantes = new ArrayList<String>();
    DocumentoDetalle documentoDetalle = new DocumentoDetalle();
    if (input != null) {
    try {
      // Agrego los usuarios firmantes
      Iterator<Usuario> iterator = this.firmaConjuntaService
          .buscarFirmantesPorProceso(documento.getWorkflowOrigen()).iterator();

      while (iterator.hasNext()) {
        Usuario current = iterator.next();
        listaFirmantes.add(current.toString());
      }

      documento.setFirmantes(listaFirmantes);
      documentoDetalle.setNumeroSade(documento.getNumero());
      documentoDetalle.setNumeroEspecial(documento.getNumeroEspecial());
      documentoDetalle.setReferencia(documento.getMotivo());
      documentoDetalle.setIdWorkFlow(documento.getWorkflowOrigen());
      documentoDetalle.setIdGuardaDocumental(documento.getIdGuardaDocumental());

      if (documento.getFirmantes().size() > 0) {
        documentoDetalle.setListaFirmantes(documento.getFirmantes());
      } else {
        if (usuarioService.obtenerUsuario(documento.getUsuarioGenerador()) != null
            && usuarioService.obtenerUsuario(documento.getUsuarioGenerador())
                .getNombreApellido() != null) {
          listaFirmantes
              .add(usuarioService.obtenerUsuario(documento.getUsuarioGenerador()).toString());
          documentoDetalle.setListaFirmantes(listaFirmantes);
        } else {
          listaFirmantes.add(Constantes.USUARIO_DADO_DE_BAJA_EN_TRACK_LDAP_O_CCOO + " ( "
              + documento.getUsuarioGenerador() + " )");
          documentoDetalle.setListaFirmantes(listaFirmantes);
        }
      }

      documentoDetalle.setFechaCreacion(documento.getFechaCreacion());
      documentoDetalle.setTipoDocAcronimo(documento.getTipo().getAcronimo());
      documentoDetalle.setTipoDocumento(documento.getTipo().getAcronimo().toString() + " "
          + documento.getTipo().getDescripcion());
      documentoDetalle
          .setDatosPropiosDetalle(parsearDocumentoMetadataDetalle(documento.getListaMetadatos()));
      documentoDetalle.setListaArchivosDeTrabajoDetalle(
          this.parsearArchivosDeTrabajoDetalle(documento.getWorkflowOrigen()));
      documentoDetalle
          .setListaHistorialDetalle(this.parsearHistorial(documento.getWorkflowOrigen()));
      documentoDetalle
          .setPuedeVerDocumento(puedeVerDocumentoConfidencial(documento, this.uuidUsuario));
      documentoDetalle.setMotivoDepuracion(documento.getMotivoDepuracion());
      if (documento.getMotivoDepuracion() != null) {
        documentoDetalle
            .setUrlArchivo(documento.getMotivoDepuracion());
      } else {
        documentoDetalle.setUrlArchivo(
            gestionArchivosWebDavService.obtenerUbicacionDescarga(documento.getNumero()));
      }
      if (documento.getUsuariosReservados() != null) {
        List<UsuarioReservadoDTO> usuariosReservados = new ArrayList<UsuarioReservadoDTO>(
            documento.getUsuariosReservados());
        documentoDetalle.setListaUsuariosReservados(usuariosReservados);
      }

    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    }
    return documentoDetalle;
  }

  private List<DocumentoMetadataDetalle> parsearDocumentoMetadataDetalle(
      List<DocumentoMetadataDTO> listaDocumentoMetadata)
      throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		// Iterator<DocumentoMetadataDTO> iterator;
		// DocumentoMetadataDTO current;
    List<DocumentoMetadataDetalle> listaDocumentoMetadataDetalle = new ArrayList<DocumentoMetadataDetalle>();

		// iterator = listaDocumentoMetadata.iterator();
		//
		// while (iterator.hasNext()) {
		// current = iterator.next();
		//
		// DocumentoMetadataDetalle documentoMetadataDetalle = new
		// DocumentoMetadataDetalle();
		// org.apache.commons.beanutils.PropertyUtils.copyProperties(documentoMetadataDetalle,
		// current);
		// listaDocumentoMetadataDetalle.add(documentoMetadataDetalle);
		// }

    return listaDocumentoMetadataDetalle;
  }

  private List<ArchivoDeTrabajoDetalle> parsearArchivosDeTrabajoDetalle(String workflowId)
      throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
    Iterator<ArchivoDeTrabajoDTO> iterator;
    ArchivoDeTrabajoDTO current;
    List<ArchivoDeTrabajoDetalle> listaArchivoDeTrabajoDetalle = new ArrayList<ArchivoDeTrabajoDetalle>();

    iterator = this.archivoDeTrabajoService.buscarArchivosDeTrabajoPorProceso(workflowId)
        .iterator();

    while (iterator.hasNext()) {
      current = iterator.next();

      ArchivoDeTrabajoDetalle archivoDeTrabajoDetalle = new ArchivoDeTrabajoDetalle();
      org.apache.commons.beanutils.PropertyUtils.copyProperties(archivoDeTrabajoDetalle, current);
      listaArchivoDeTrabajoDetalle.add(archivoDeTrabajoDetalle);
    }

    return listaArchivoDeTrabajoDetalle;
  }

  private List<HistorialDetalle> parsearHistorial(String workflowId)
      throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
    Iterator<HistorialDTO> iterator;
    HistorialDTO current;
    List<HistorialDetalle> listaHistorialDetalle = new ArrayList<HistorialDetalle>();

    iterator = this.historialProcesosService.getHistorial(workflowId).iterator();

    while (iterator.hasNext()) {
    	current = this.mapper.map(iterator.next(), HistorialDTO.class);
      //current = iterator.next();

      HistorialDetalle historialDetalle = new HistorialDetalle();
      org.apache.commons.beanutils.PropertyUtils.copyProperties(historialDetalle, current);
      listaHistorialDetalle.add(historialDetalle);
    }

    return listaHistorialDetalle;
  }

  private Boolean puedeVerDocumentoConfidencial(DocumentoDTO documento, String loggedUsername) {
    ReparticionBean reparticion = obtenerReparticionService
        .buscarReparticionByUsuario(loggedUsername);
    Usuario user = null;
    try {
      user = this.usuarioService.obtenerUsuario(loggedUsername);
    } catch (SecurityNegocioException e) {
      logger.error("Error al obtener el usuario. " + e.getMessage(), e);
    }
    String reparticionSeleccionada;
    if (user != null) {
      reparticionSeleccionada = user.getCodigoReparticion();
    } else {
      reparticionSeleccionada = reparticion.getCodigo();
    }
    if (this.assignee) {
      return buscarDocumentosGedoService.puedeVerDocumentoGedo(documento, reparticionSeleccionada,
          loggedUsername);
    } else {
      return buscarDocumentosGedoService.puedeVerDocumentoConfidencial(documento, loggedUsername,
          reparticionSeleccionada);
    }
  }

}
