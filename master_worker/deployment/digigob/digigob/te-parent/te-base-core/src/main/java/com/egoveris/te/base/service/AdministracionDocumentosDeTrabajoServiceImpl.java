/**
 *
 */
package com.egoveris.te.base.service;

import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.shareddocument.base.service.IWebDavService;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.te.base.model.ArchivoDeTrabajoDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.ReparticionParticipanteDTO;
import com.egoveris.te.base.model.TipoArchivoTrabajoDTO;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.service.iface.IAccesoWebDavService;
import com.egoveris.te.base.service.iface.IAdministracionDocumentosDeTrabajoService;
import com.egoveris.te.base.util.BusinessFormatHelper;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.model.exception.ExpedienteNoDisponibleException;
import com.egoveris.te.model.exception.ParametroIncorrectoException;
import com.egoveris.te.model.exception.ProcesoFallidoException;
import com.egoveris.te.model.model.ArchivoDeTrabajoResponse;
//TODO Comentado lo de vuce
//import com.egoveris.vuc.external.service.client.service.external.exception.ExpedienteInexistenteException;
import com.egoveris.te.model.model.DocumentoTrabajo;

@Service
@Transactional
public class AdministracionDocumentosDeTrabajoServiceImpl extends ExternalServiceAbstract
    implements IAdministracionDocumentosDeTrabajoService {
  private static final Logger logger = LoggerFactory
      .getLogger(AdministracionDocumentosDeTrabajoServiceImpl.class);

  @Autowired
  private ArchivoDeTrabajoService archivoDeTrabajoService;
  @Autowired
  private ExpedienteElectronicoService expedienteElectronicoService;
  @Autowired
  private UsuariosSADEService usuarioSADEService;
  @Autowired
  private UsuariosSADEService usuariosSADEService;
  @Autowired
  private ServicioAdministracion servicioAdministracionFactory;
  @Autowired
  private IAccesoWebDavService visualizaDocumentoService;
  @Autowired
  private IWebDavService webDavService;
  @Autowired
  private PermisoVisualizacionService permisoVisualizacionService;

  @Transactional
  public void adjuntarDocumentosTrabajo(String sistemaUsuario, String usuario, String codigoEE,
      List<DocumentoTrabajo> listaDocumentosTrabajo) throws ProcesoFallidoException,
      /* ExpedienteInexistenteException, */ ParametroIncorrectoException,
      ExpedienteNoDisponibleException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "adjuntarDocumentosTrabajo(sistemaUsuario={}, usuario={}, codigoEE={}, listaDocumentosTrabajo={}) - start",
          sistemaUsuario, usuario, codigoEE, listaDocumentosTrabajo);
    }

    List<ArchivoDeTrabajoDTO> archivoTrabajoAux;
    boolean encontrado = false;
    boolean sinArchivosDeTrabajo = false;

    // ************************************************************************
    // ** TODO: REFACTOR REFACTOR (ISSUE) BISADE-2180 Excepciones de negocio
    // se muestran en el log de EE (Martes 15-Marzo-2014) grinberg.
    // ** Desc: Validación Documentacion de EE (Business Service)
    // ** En la clase ExternServiceAbstract en vez verificarAsignación en la
    // ejecución del servicio,
    // ** se arma con la referencia ya validada desde el principio del CU.
    // ** Asi al invocar la llama desde un factory de vínculacion, ya está
    // referenciada
    // ** la asociación y no se permite su ejecución sino antes de a ver
    // llegado a la invocación del servicio.
    // ** Caso de Uso afectado: Documentos de Trabajo
    // ************************************************************************
    ExpedienteElectronicoDTO expedienteElectronico = servicioAdministracionFactory
        .crearExpedienteElectronicoParaDocumentosDeTrabajo(codigoEE, sistemaUsuario, usuario,
            false);

    archivoTrabajoAux = expedienteElectronico.getArchivosDeTrabajo();

    // Verifica si el expediente tiene archivos de Trabajo
    if (archivoTrabajoAux.isEmpty()) {
      sinArchivosDeTrabajo = true;
    }

    // Valido si el usuario esta de licencia

    String usuarioApoderado = null;
    if (usuariosSADEService.licenciaActiva(usuario)) {
      usuarioApoderado = this.validarLicencia(usuario.toUpperCase());
    }
    if (usuarioApoderado != null) {
      throw new ParametroIncorrectoException(
          "El usuario seleccionado no puede vincular archivos de trabajo ya que está de licencia.",
          null);
    }

    // Valida que por servivio (en la lista de entrada) no haya archivos con
    // nombres repetidos.
    for (DocumentoTrabajo documentoTrabajo : listaDocumentosTrabajo) {
      int cont = 0;
      for (DocumentoTrabajo documentoTrabajoAux : listaDocumentosTrabajo) {
        if (documentoTrabajo.getNombreArchivo().equals(documentoTrabajoAux.getNombreArchivo())) {
          cont++;
        }
      }

      if (cont > 1) {
        throw new ProcesoFallidoException(
            "La lista de Archivo de Trabajo posee nombres de archivos repetidos. ", null);
      }
    }

    // Validación para evitar subir archivos de trabajo repetidos
    for (ArchivoDeTrabajoDTO archivoTrabajoEncontrado : archivoTrabajoAux) {
      for (DocumentoTrabajo documentoTrabajo : listaDocumentosTrabajo) {
        if (!documentoTrabajo.getNombreArchivo()
            .equals(archivoTrabajoEncontrado.getNombreArchivo())) {
          encontrado = true;
        } else {
          throw new ProcesoFallidoException("El Archivo de Trabajo "
              + documentoTrabajo.getNombreArchivo() + " ya está adjuntado en el expediente", null);
        }
      }
    }

    String nombreSpace = codigoEE;
    Task workingTask = obtenerWorkingTask(expedienteElectronico);

    if (encontrado || sinArchivosDeTrabajo) {
      for (DocumentoTrabajo documentoTrabajo : listaDocumentosTrabajo) {
        ArchivoDeTrabajoDTO archivoTrabajo = new ArchivoDeTrabajoDTO();
        archivoTrabajo.setDataArchivo(documentoTrabajo.getDataArchivo());
        archivoTrabajo.setNombreArchivo(documentoTrabajo.getNombreArchivo());
        archivoTrabajo.setUsuarioAsociador(usuario);
        archivoTrabajo.setDefinitivo(true);
        if (!sistemaUsuario.equals("ARCH")) {
          archivoTrabajo.setDefinitivo(false);
        }
        archivoTrabajo.setFechaAsociacion(new Date());

        TipoArchivoTrabajoDTO tipoArchivo;

        if (documentoTrabajo.getTipoDocumentoTrabajo() == null
            || "".equals(documentoTrabajo.getTipoDocumentoTrabajo())) {
          tipoArchivo = archivoDeTrabajoService
              .buscarTipoArchivoTrabajoPorNombre(ConstantesWeb.TIPO_ARCHIVO_DEFECTO);
        } else {
          tipoArchivo = archivoDeTrabajoService
              .buscarTipoArchivoTrabajoPorNombre(documentoTrabajo.getTipoDocumentoTrabajo());
        }

        if (tipoArchivo != null && !tipoArchivo.isRepetible()) {
          for (ArchivoDeTrabajoDTO archivo : expedienteElectronico.getArchivosDeTrabajo()) {
            if (archivo.getTipoArchivoTrabajo().equals(tipoArchivo)) {
              throw new ParametroIncorrectoException(
                  "El tipo de archivo " + tipoArchivo.getNombre() + ", no se permite repetir.",
                  null);
            }
          }
        }

        if (tipoArchivo != null) {
          archivoTrabajo.setTipoArchivoTrabajo(tipoArchivo);
        } else {
          throw new ParametroIncorrectoException(
              "No se encontro el Tipo de Documento " + documentoTrabajo.getTipoDocumentoTrabajo()
                  + ", por favor vuelva a ingresar su valor.",
              null);
        }

        Usuario datosUsuario = this.usuariosSADEService.getDatosUsuario(usuario);

        ReparticionParticipanteDTO repPart = new ReparticionParticipanteDTO();

        repPart.setReparticion(datosUsuario.getCodigoReparticion());
        repPart.setTipoOperacion("ALTA");
        repPart.setFechaModificacion(new Date());
        repPart.setUsuario(usuario);

        archivoTrabajo.getReparticionesParticipantes().add(repPart);

        archivoTrabajo.setIdTask(workingTask.getExecutionId());
        if ((expedienteElectronico.getEsCabeceraTC() != null)
            && expedienteElectronico.getEsCabeceraTC()) {
          archivoTrabajo.setIdExpCabeceraTC(expedienteElectronico.getId());

        }

        String idFilenet = this.archivoDeTrabajoService.subirArchivoDeTrabajo(archivoTrabajo,
            nombreSpace);

        archivoTrabajo.setIdGuardaDocumental(idFilenet);
        expedienteElectronico.getArchivosDeTrabajo().add(archivoTrabajo);
      }
    }

    expedienteElectronicoService.modificarExpedienteElectronico(expedienteElectronico);

    if (logger.isDebugEnabled()) {
      logger.debug(
          "adjuntarDocumentosTrabajo(String, String, String, List<DocumentoTrabajo>) - end");
    }
  }

  @Transactional
  public void desadjuntarDocumentosDeTrabajo(String sistemaUsuario, String usuario,
      String codigoEE, List<String> listaDocumentosTrabajo)
      throws ProcesoFallidoException, /* ExpedienteInexistenteException, */
      ParametroIncorrectoException, ExpedienteNoDisponibleException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "desadjuntarDocumentosDeTrabajo(sistemaUsuario={}, usuario={}, codigoEE={}, listaDocumentosTrabajo={}) - start",
          sistemaUsuario, usuario, codigoEE, listaDocumentosTrabajo);
    }

    // Expediente Padre

    // ************************************************************************
    // ** TODO: REFACTOR REFACTOR (ISSUE) BISADE-2180 Excepciones de negocio
    // se muestran en el log de EE (Martes 15-Marzo-2014) grinberg.
    // ** Desc: Validación Documentacion de EE (Business Service)
    // ** En la clase ExternServiceAbstract en vez verificarAsignación en la
    // ejecución del servicio,
    // ** se arma con la referencia ya validada desde el principio del CU.
    // ** Asi al invocar la llama desde un factory de vínculacion, ya está
    // referenciada
    // ** la asociación y no se permite su ejecución sino antes de a ver
    // llegado a la invocación del servicio.
    // ** Caso de Uso afectado: Documentos de Trabajo
    // ************************************************************************
    ExpedienteElectronicoDTO expedienteElectronico = servicioAdministracionFactory
        .crearExpedienteElectronicoParaDocumentosDeTrabajo(codigoEE, sistemaUsuario, usuario,
            false);

    List<ArchivoDeTrabajoDTO> archivosTrabajoAux = new ArrayList<ArchivoDeTrabajoDTO>();

    for (String documentoTrabajo : listaDocumentosTrabajo) {
      for (ArchivoDeTrabajoDTO archivoTrabajo : expedienteElectronico.getArchivosDeTrabajo()) {
        if (archivoTrabajo.getNombreArchivo().equals(documentoTrabajo)
            && !archivoTrabajo.isDefinitivo()) {
          archivosTrabajoAux.add(archivoTrabajo);
        }
      }
    }

    if (archivosTrabajoAux.size() > 0) {
      expedienteElectronico.getArchivosDeTrabajo().removeAll(archivosTrabajoAux);
    }

    expedienteElectronicoService.modificarExpedienteElectronico(expedienteElectronico);

    if (logger.isDebugEnabled()) {
      logger.debug("desadjuntarDocumentosDeTrabajo(String, String, String, List<String>) - end");
    }
  }

  private String validarLicencia(String username) {
    if (logger.isDebugEnabled()) {
      logger.debug("validarLicencia(username={}) - start", username);
    }

    String returnString = this.usuariosSADEService.getDatosUsuario(username).getApoderado();
    if (logger.isDebugEnabled()) {
      logger.debug("validarLicencia(String) - end - return value={}", returnString);
    }
    return returnString;
  }

  @Transactional
  public ArchivoDeTrabajoResponse obtenerArchivoDeTrabajo(String codigoEE, String usuario,
      String nombre) throws ProcesoFallidoException, ParametroIncorrectoException {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerArchivoDeTrabajo(codigoEE={}, usuario={}, nombre={}) - start", codigoEE,
          usuario, nombre);
    }

    ArchivoDeTrabajoResponse response = new ArchivoDeTrabajoResponse();

    Usuario user;
    List<ArchivoDeTrabajoDTO> listArch;

    // Valido si el usuario esta de licencia

    String usuarioApoderado = null;
    if (usuariosSADEService.licenciaActiva(usuario)) {
      usuarioApoderado = this.validarLicencia(usuario.toUpperCase());
    }
    if (usuarioApoderado != null) {
      throw new ParametroIncorrectoException(
          "El usuario seleccionado no puede vincular archivos de trabajo ya que está de licencia.",
          null);
    }

    if ("".equalsIgnoreCase(codigoEE)) {
      throw new ProcesoFallidoException("Debe ingresar un código de expediente electrónico.",
          null);
    }

    if ("".equalsIgnoreCase(usuario)) {
      throw new ProcesoFallidoException("Debe ingresar un usuario.", null);
    }

    if ("".equalsIgnoreCase(nombre)) {
      throw new ProcesoFallidoException("Debe ingresar un nombre de archivo de trabajo.", null);
    }

    user = usuarioSADEService.getDatosUsuario(usuario);
    if (user == null) {
      throw new ProcesoFallidoException("Debe ingresar un usuario válido.", null);
    }
    ExpedienteElectronicoDTO expedienteElectronico = servicioAdministracionFactory
        .obtenerExpedienteElectronico(codigoEE);
    listArch = expedienteElectronico.getArchivosDeTrabajo();
    if (listArch.isEmpty()) {
      throw new ProcesoFallidoException("El expediente no posee archivos de trabajo", null);
    }
    try {
      for (ArchivoDeTrabajoDTO at : listArch) {
        if (at.getNombreArchivo().equals(nombre)) {

          if (expedienteElectronico.getEsReservado()
              || (expedienteElectronico.getTrata().getTipoReserva() != null
                  && expedienteElectronico.getTrata().getTipoReserva().getId() > 1)) {
            if (!permisoVisualizacionService.tienePermisoVisualizacion(user, at)) {
              throw new ProcesoFallidoException(
                  "El usuario no tiene permisos para descargar el archivo de trabajo.", null);
            }
          }

          String nombreSpace = BusinessFormatHelper
              .formarPathWebDavApache(expedienteElectronico.getCodigoCaratula());
          String pathDocumentoDeTrabajo = "Documentos_De_Trabajo";
          String fileName = pathDocumentoDeTrabajo + "/" + nombreSpace + "/"
              + at.getNombreArchivo();

          BufferedInputStream file = this.visualizaDocumentoService.visualizarDocumento(fileName);
          response.setDataArchivo(IOUtils.toByteArray(file));

          if (logger.isDebugEnabled()) {
            logger.debug("obtenerArchivoDeTrabajo(String, String, String) - end - return value={}",
                response);
          }
          return response;
        }
      }
    } catch (ProcesoFallidoException e) {
      throw e;
    } catch (Exception e) {
      logger.error("obtenerArchivoDeTrabajo(String, String, String)", e);

      throw new ProcesoFallidoException("No se ha podido obtener el Archivo de Trabajo", null);
    }
    throw new ProcesoFallidoException("No se ha podido obtener el Archivo de Trabajo", null);
  }

}
