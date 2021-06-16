package com.egoveris.deo.ws.service.impl;

import com.egoveris.deo.base.exception.ArchivoDuplicadoException;
import com.egoveris.deo.base.exception.ArchivoFirmadoException;
import com.egoveris.deo.base.exception.CantidadDatosException;
import com.egoveris.deo.base.exception.ExtensionesFaltantesException;
import com.egoveris.deo.base.exception.FormatoInvalidoException;

import com.egoveris.deo.base.exception.SADERollbackErrorException;
import com.egoveris.deo.base.exception.TamanoInvalidoException;
import com.egoveris.deo.base.exception.ValidacionCampoFirmaException;
import com.egoveris.deo.base.exception.ValidacionContenidoException;
import com.egoveris.deo.base.service.ArchivosEmbebidosService;
import com.egoveris.deo.base.service.GenerarDocumentoService;
import com.egoveris.deo.base.service.ProcesamientoTemplate;
import com.egoveris.deo.base.service.ReparticionHabilitadaService;
import com.egoveris.deo.base.service.TipoDocumentoService;
import com.egoveris.deo.base.util.GeneradorDocumentoFactory;
import com.egoveris.deo.model.model.ArchivoEmbebidoDTO;
import com.egoveris.deo.model.model.DocumentoMetadataDTO;
import com.egoveris.deo.model.model.MetadataDTO;
import com.egoveris.deo.model.model.ReparticionHabilitadaDTO;
import com.egoveris.deo.model.model.RequestExternalGenerarDocumento;
import com.egoveris.deo.model.model.RequestExternalGenerarDocumentoUsuarioExterno;
import com.egoveris.deo.model.model.RequestGenerarDocumento;
import com.egoveris.deo.model.model.ResponseExternalGenerarDocumento;
import com.egoveris.deo.model.model.ResponseGenerarDocumento;
import com.egoveris.deo.model.model.TipoDocumentoDTO;
import com.egoveris.deo.model.model.UsuarioExternoDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.deo.ws.exception.CantidadDatosNoSoportadaException;
import com.egoveris.deo.ws.exception.ClavesFaltantesException;
import com.egoveris.deo.ws.exception.ErrorGeneracionDocumentoException;
import com.egoveris.deo.ws.exception.ParametroInvalidoException;
import com.egoveris.deo.ws.service.IExternalGenerarDocumentoService;
import com.egoveris.shared.map.ListMapper;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jws.WebParam;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ExternalGenerarDocumentoServiceImpl implements IExternalGenerarDocumentoService {
  /**
   * Logger for this class
   */
  private static final Logger logger = LoggerFactory
      .getLogger(ExternalGenerarDocumentoServiceImpl.class);

  @Autowired
  private TipoDocumentoService tipoDocumentoService;
  @Autowired
  private GeneradorDocumentoFactory generadorDocumentoFactory;
  @Autowired
  private IUsuarioService usuarioService;
  @Autowired
  private ProcesamientoTemplate procesamientoTemplate;
  @Autowired
  private ArchivosEmbebidosService archivosEmbebidosService;
  @Autowired
  protected ReparticionHabilitadaService reparticionHabilitadaService;

  private DozerBeanMapper mapper = new DozerBeanMapper();

  public ResponseExternalGenerarDocumento generarDocumentoGEDO(
      RequestExternalGenerarDocumento request)
      throws ParametroInvalidoException, CantidadDatosNoSoportadaException,
      ErrorGeneracionDocumentoException, ClavesFaltantesException {
    if (logger.isDebugEnabled()) {
      logger.debug("generarDocumentoGEDO(RequestExternalGenerarDocumento) - start"); //$NON-NLS-1$
    }

    RequestGenerarDocumento requestInternal = new RequestGenerarDocumento();
    ResponseExternalGenerarDocumento returnResponseExternalGenerarDocumento = generarDocumentoGEDO(
        request, requestInternal);
    if (logger.isDebugEnabled()) {
      logger.debug("generarDocumentoGEDO(RequestExternalGenerarDocumento) - end"); //$NON-NLS-1$
    }
    return returnResponseExternalGenerarDocumento;
  }

  private ResponseExternalGenerarDocumento generarDocumentoGEDO(
      RequestExternalGenerarDocumento request, RequestGenerarDocumento requestInternal)
      throws ParametroInvalidoException, CantidadDatosNoSoportadaException,
      ErrorGeneracionDocumentoException, ClavesFaltantesException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "generarDocumentoGEDO(RequestExternalGenerarDocumento, RequestGenerarDocumento) - start"); //$NON-NLS-1$
    }

    int contadorEmbebidos = 0;
    ResponseGenerarDocumento response = null;
    TipoDocumentoDTO tipoDocumento = null;
    Map<String, Object> camposTemplate = new HashMap<String, Object>();

    Map<String, String> mapLicenciadoApoderado = new HashMap<String, String>();

    ResponseExternalGenerarDocumento responseExternal = new ResponseExternalGenerarDocumento();
    try {
      validarParametros(request);
      tipoDocumento = obtenerTipoDocumento(request.getAcronimoTipoDocumento());
      if (tipoDocumento.getTieneToken()) {
        throw new ParametroInvalidoException("El tipo de documento " + tipoDocumento.getAcronimo()
            + " solicita firma con token. Debe utilizar un tipo de documento que permita firma con certificado.");
      }
      if (tipoDocumento.getTieneToken()) {
        throw new ParametroInvalidoException("El tipo de documento " + tipoDocumento.getAcronimo()
            + " solicita firma con token. Debe utilizar un tipo de documento que permita firma con certificado.");
      }
      if (tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_TEMPLATE
          || tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO_TEMPLATE) {
        validarParametrosTemplate(request);
        camposTemplate = procesamientoTemplate.obtenerCamposTemplate(request.getIdTransaccion());
        validarCamposTemplate(new HashMap<String, Object>(camposTemplate), tipoDocumento);
      } else {
        if (request.getData() == null)
          throw new ParametroInvalidoException("Parámetro data es obligatorio");
      }
      requestInternal.setTipoDocumentoGedo(tipoDocumento);
      Usuario datosUsuario = obtenerDatosUsuario(request.getUsuario());
      requestInternal.setCodigoReparticion(datosUsuario.getCodigoReparticion());

      validarReparticiones(tipoDocumento, request, datosUsuario);

      String apoderado = obtenerApoderado(datosUsuario.getUsername(), new Date(),
          mapLicenciadoApoderado);
      if (apoderado != null) {
        datosUsuario = usuarioService.obtenerUsuario(apoderado);
        request.setUsuario(apoderado);
        requestInternal.setUsuarioApoderador(apoderado);
      }
      requestInternal.setUsuario(request.getUsuario());
      requestInternal.setMotivo(request.getReferencia());
      requestInternal.setData(request.getData());
      requestInternal.setNombreAplicacion(request.getSistemaOrigen());
      requestInternal.setNombreAplicacionIniciadora(request.getSistemaOrigen());
      requestInternal.setUsuarioIniciador(request.getUsuario());
      List<DocumentoMetadataDTO> listaDatosPropiosDocumento = null;
      listaDatosPropiosDocumento = cargarDatosPropios(request.getMetaDatos(), tipoDocumento,
          request.getReferencia());
      if (listaDatosPropiosDocumento.size() > 0) {
        requestInternal.setDocumentoMetadata(listaDatosPropiosDocumento);
      }
      requestInternal.setTipoArchivo(request.getTipoArchivo());
      requestInternal.setCamposTemplate((HashMap<String, Object>) camposTemplate);
      requestInternal.setIdTransaccion(request.getIdTransaccion());
      if (tipoDocumento.getPermiteEmbebidos() && request.getListaArchivosEmbebidos() != null) {
        List<ArchivoEmbebidoDTO> listaArchivosEmbebidos = new ArrayList<>();
        for (ArchivoEmbebidoDTO archivoEmbebidoDTO : request.getListaArchivosEmbebidos()) {
          if (archivoEmbebidoDTO.getNombreArchivo().trim() == null
              || archivoEmbebidoDTO.getNombreArchivo().trim().equals("")) {
            throw new ParametroInvalidoException("El nombre de un archivo embebido está vacío.");
          }
          archivosEmbebidosService.verificarArchivo(archivoEmbebidoDTO.getDataArchivo(),
              tipoDocumento);
          ArchivoEmbebidoDTO archivoEmbebido = new ArchivoEmbebidoDTO();
          archivoEmbebido.setNombreArchivo(archivoEmbebidoDTO.getNombreArchivo());
          archivoEmbebido.setDataArchivo(archivoEmbebidoDTO.getDataArchivo());
          archivoEmbebido.setFechaAsociacion(new Date());
          archivoEmbebido.setUsuarioAsociador(request.getUsuario());
          archivoEmbebido
              .setMimetype(archivosEmbebidosService.getMimetype(archivoEmbebido.getDataArchivo()));
          archivosEmbebidosService.validarNombre(listaArchivosEmbebidos, archivoEmbebido);
          listaArchivosEmbebidos.add(archivoEmbebido);
          contadorEmbebidos++;
        }
        requestInternal.setListaArchivosEmbebidos(listaArchivosEmbebidos);
      }

      if (tipoDocumento.getEsComunicable()) {
        requestInternal = prepararComunicacion(request, requestInternal, mapLicenciadoApoderado);
      }

      archivosEmbebidosService.verificarObligatoriedadExtensiones(
          requestInternal.getListaArchivosEmbebidos(), tipoDocumento);
      GenerarDocumentoService generarDocumentoService = generadorDocumentoFactory
          .obtenerGeneradorDocumento(tipoDocumento);
      response = generarDocumentoService.generarDocumentoExterno(requestInternal);
      responseExternal.setNumero(response.getNumero());
      if (tipoDocumento.getEsEspecial()) {
        responseExternal.setNumeroEspecial(responseExternal.getNumeroEspecial());
      }
      responseExternal.setUrlArchivoGenerado(response.getUrlArchivoGenerado());
      String licencia = "";
      for (Map.Entry<String, String> entry : mapLicenciadoApoderado.entrySet()) {
        licencia = licencia.concat("El usuario " + entry.getKey()
            + " se encuentra de licencia. Se redigirá a " + entry.getValue() + "\n ");
      }
      if (!licencia.isEmpty()) {
        responseExternal.setLicencia(licencia);
      }
      logger.debug("Documento pedido por usuario " + request.getUsuario() + "generado OK: "
          + response.getNumero());
      return responseExternal;
    } catch (ParametroInvalidoException pie) {
      logger.error("Parámetros inválidos para documento con referencia: " + request.getReferencia()
          + " . Sistema Origen " + request.getSistemaOrigen() + pie.getMessage(), pie);
      throw pie;
    } catch (IOException ioe) {
      logger.error(ioe.getMessage(), ioe);
    } catch (ArchivoFirmadoException afe) {
      logger.error("Error en validación de data para generacion de documento externo: "
          + request.getReferencia() + " . Sistema Origen " + request.getSistemaOrigen());
      throw new ParametroInvalidoException(
          "Error en validación de data para generacion de documento externo: "
              + request.getReferencia() + ", " + afe.getMessage());
      // TODO ver quien lanza esta exception
    } catch (SADERollbackErrorException sre) {
      logger.error("Error al realizar rollback" + sre.getMessage(), sre);
      throw new ErrorGeneracionDocumentoException(
          "No se ha podido generar el documento. Error interno de GEDO." + sre.getMessage());
    } catch (ClavesFaltantesException cfe) {
      logger.error("Error en validaciones para Tipo De Documento Template. " + cfe.getMessage(),
          cfe);
      throw cfe;
    } catch (ValidacionContenidoException vce) {
      logger.error("Error en validación del contenido del documento con referencia: "
          + request.getReferencia() + " . Sistema Origen " + request.getSistemaOrigen()
          + vce.getMessage(), vce);
      throw new ParametroInvalidoException(
          "Error en validación del contenido del documento con referencia: "
              + request.getReferencia() + ", " + vce.getMessage());
    } catch (ValidacionCampoFirmaException vcfe) {
      logger.error("Error en validación de campos de firma: " + request.getReferencia()
          + " . Sistema Origen " + request.getSistemaOrigen() + vcfe.getMessage(), vcfe);
      throw new ParametroInvalidoException("Error en validación de campos de firma: "
          + request.getReferencia() + ", " + vcfe.getMessage());
    } catch (CantidadDatosException cde) {
      logger.error("Error en validación del documento con referencia: " + request.getReferencia()
          + " . Sistema Origen " + request.getSistemaOrigen() + cde.getMessage(), cde);
      throw new CantidadDatosNoSoportadaException(
          "Error en validación del documento con referencia: " + request.getReferencia() + ", "
              + cde.getMessage());
    } catch (TamanoInvalidoException tie) {
      logger.error("El archivo \""
          + request.getListaArchivosEmbebidos().get(contadorEmbebidos).getNombreArchivo()
          + "\" supera el tamaño permitido por el tipo de documento seleccionado", tie);
      throw new CantidadDatosNoSoportadaException(
          "Error en validación de archivos embebidos con referencia: " + request.getReferencia()
              + ", El archivo \""
              + request.getListaArchivosEmbebidos().get(contadorEmbebidos).getNombreArchivo()
              + "\" supera el tamaño permitido por el tipo de documento seleccionado");
    } catch (FormatoInvalidoException fie) {
      logger.error("El archivo \""
          + request.getListaArchivosEmbebidos().get(contadorEmbebidos).getNombreArchivo()
          + "\" es de un formato no permitido por el tipo de documento", fie);
      throw new ParametroInvalidoException(
          "Error en validacion de archivos embebidos con referencia: " + request.getReferencia()
              + ", El archivo \""
              + request.getListaArchivosEmbebidos().get(contadorEmbebidos).getNombreArchivo()
              + "\" tiene un formato no permitido para el tipo de documento seleccionado.");
    } catch (ArchivoDuplicadoException ade) {
      logger.error("El archivo \"" + request.getListaArchivosEmbebidos().get(contadorEmbebidos)
          + "\" se encuentra duplicado en la lista de archivos embebidos", ade);
      throw new ParametroInvalidoException(
          "Error en validacion de archivos embebidos con referencia: " + request.getReferencia()
              + ", " + ade.getMessage());
    } catch (ExtensionesFaltantesException efe) {
      logger.error("No se subieron archivos de con el formato de las extensiones obligatorias."
          + efe.getMessage(), efe);
      throw new ParametroInvalidoException(
          "Error en validacion de archivos embebidos con referencia: " + request.getReferencia()
              + ", no se subieron archivos con el formato de las extensiones obligatorias.");
    } catch (Exception e) {
      logger.error("Error en generación de documento con referencia: " + request.getReferencia()
          + " . Sistema Origen " + request.getSistemaOrigen(), e);
      throw new ErrorGeneracionDocumentoException(
          "Error en generación de documento con referencia: " + request.getReferencia() + ", "
              + e.getMessage());
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "generarDocumentoGEDO(RequestExternalGenerarDocumento, RequestGenerarDocumento) - end"); //$NON-NLS-1$
    }
    return null;
  }

  private String obtenerApoderado(String usuario, Date fecha,
      Map<String, String> mapLicenciadoApoderado) throws SecurityNegocioException {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerApoderado(String, Date, Map<String,String>) - start"); //$NON-NLS-1$
    }

    String apoderado = (this.usuarioService.licenciaActiva(usuario, fecha)
        ? this.usuarioService.obtenerUsuario(usuario).getApoderado() : null);
    if (apoderado != null) {
      while (this.usuarioService.licenciaActiva(apoderado, fecha)) {
        apoderado = this.usuarioService.obtenerUsuario(apoderado).getApoderado();
      }
      mapLicenciadoApoderado.put(usuario, apoderado);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("obtenerApoderado(String, Date, Map<String,String>) - end"); //$NON-NLS-1$
    }
    return apoderado;
  }

  private void validarDatosComunicables(RequestExternalGenerarDocumento request) {
    if (logger.isDebugEnabled()) {
      logger.debug("validarDatosComunicables(RequestExternalGenerarDocumento) - start"); //$NON-NLS-1$
    }

    if (request.getListaUsuariosDestinatarios() != null
        && request.getListaUsuariosDestinatarios().size() == 1
        && request.getListaUsuariosDestinatarios().get(0).equals("")) {
      request.setListaUsuariosDestinatarios(null);
    }
    if (request.getListaUsuariosDestinatariosCopia() != null
        && request.getListaUsuariosDestinatariosCopia().size() == 1
        && request.getListaUsuariosDestinatariosCopia().get(0).equals("")) {
      request.setListaUsuariosDestinatariosCopia(null);
    }
    if (request.getListaUsuariosDestinatariosCopiaOculta() != null
        && request.getListaUsuariosDestinatariosCopiaOculta().size() == 1
        && request.getListaUsuariosDestinatariosCopiaOculta().get(0).equals("")) {
      request.setListaUsuariosDestinatariosCopiaOculta(null);
    }
    if (request.getListaUsuariosDestinatariosExternos() != null
        && request.getListaUsuariosDestinatariosExternos().size() == 1) {
      Set<String> keySet = request.getListaUsuariosDestinatariosExternos().keySet();
      for (String key : keySet) {
        if (request.getListaUsuariosDestinatariosExternos().get(key).equals("")
            || request.getListaUsuariosDestinatariosExternos().get(key).equals("?")) {
          request.setListaUsuariosDestinatariosExternos(null);
        }
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("validarDatosComunicables(RequestExternalGenerarDocumento) - end"); //$NON-NLS-1$
    }
  }

  private RequestGenerarDocumento prepararComunicacion(RequestExternalGenerarDocumento request,
      RequestGenerarDocumento requestInternal, Map<String, String> mapLicenciadoApoderado)
      throws ParametroInvalidoException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "prepararComunicacion(RequestExternalGenerarDocumento, RequestGenerarDocumento, Map<String,String>) - start"); //$NON-NLS-1$
    }

    requestInternal.setMensajeDestinatario(
        request.getMensajeDestinatario() != null ? request.getMensajeDestinatario() : " ");
    requestInternal.setUsuarioFirmante(request.getUsuario());
    if (request.getListaUsuariosDestinatarios() != null) {
      for (String usuario : request.getListaUsuariosDestinatarios()) {

        if (validarRepetidosMismaLista(request.getListaUsuariosDestinatarios(),
            request.getListaUsuariosDestinatarios())) {
          throw new ParametroInvalidoException(
              "No esta permitido repetir usuarios en la misma lista");
        }
        try {
          if (usuarioService.obtenerUsuario(usuario) == null) {
            throw new ParametroInvalidoException(
                "Parámetro " + usuario + " no es un usuario válido");
          } else {
            obtenerApoderado(usuario, new Date(), mapLicenciadoApoderado);
          }
        } catch (SecurityNegocioException e) {
          logger.error("Mensaje de error", e);
        }
      }
      requestInternal.setListaUsuariosDestinatarios(request.getListaUsuariosDestinatarios());
    } else {
      requestInternal.setListaUsuariosDestinatarios(new ArrayList<String>());
      request.setListaUsuariosDestinatarios(new ArrayList<String>());
    }

    if (request.getListaUsuariosDestinatariosCopia() != null) {
      for (String usuario : request.getListaUsuariosDestinatariosCopia()) {

        if (validarRepetidos(request.getListaUsuariosDestinatarios(),
            request.getListaUsuariosDestinatariosCopia())) {
          throw new ParametroInvalidoException(
              "No esta permitido repetir usuarios entre distintas listas");
        }
        if (validarRepetidosMismaLista(request.getListaUsuariosDestinatariosCopia(),
            request.getListaUsuariosDestinatariosCopia())) {
          throw new ParametroInvalidoException(
              "No esta permitido repetir usuarios en la misma lista");
        }
        try {
          if (usuarioService.obtenerUsuario(usuario) == null) {
            throw new ParametroInvalidoException(
                "Parámetro " + usuario + " no es un usuario válido");
          } else {
            obtenerApoderado(usuario, new Date(), mapLicenciadoApoderado);
          }
        } catch (SecurityNegocioException e) {
          logger.error("Mensaje de error", e);
        }

      }

      requestInternal
          .setListaUsuariosDestinatariosCopia(request.getListaUsuariosDestinatariosCopia());
    } else {
      requestInternal.setListaUsuariosDestinatariosCopia(new ArrayList<String>());
    }

    if (request.getListaUsuariosDestinatariosCopiaOculta() != null) {
      for (String usuario : request.getListaUsuariosDestinatariosCopiaOculta()) {

        if (validarRepetidosMismaLista(request.getListaUsuariosDestinatariosCopiaOculta(),
            request.getListaUsuariosDestinatariosCopiaOculta())) {
          throw new ParametroInvalidoException(
              "No esta permitido repetir usuarios en la misma lista");
        }
        if (validarRepetidos(request.getListaUsuariosDestinatarios(),
            request.getListaUsuariosDestinatariosCopiaOculta())) {
          throw new ParametroInvalidoException(
              "No esta permitido repetir usuarios entre distintas listas");
        }
        if (request.getListaUsuariosDestinatariosCopia() != null) {
          if (validarRepetidos(request.getListaUsuariosDestinatariosCopiaOculta(),
              request.getListaUsuariosDestinatariosCopia())) {
            throw new ParametroInvalidoException(
                "No esta permitido repetir usuarios entre distintas listas");
          }
        }
        try {
          if (usuarioService.obtenerUsuario(usuario) == null) {
            throw new ParametroInvalidoException(
                "Parámetro " + usuario + " no es un usuario válido");
          } else {
            obtenerApoderado(usuario, new Date(), mapLicenciadoApoderado);
          }
        } catch (SecurityNegocioException e) {
          logger.error("Mensaje de error", e);
        }
      }
      requestInternal.setListaUsuariosDestinatariosCopiaOculta(
          request.getListaUsuariosDestinatariosCopiaOculta());
    } else {
      requestInternal.setListaUsuariosDestinatariosCopiaOculta(new ArrayList<String>());
    }

    if (request.getListaUsuariosDestinatariosExternos() != null) {
      List<UsuarioExternoDTO> listaExternos = new ArrayList<>();
      Set<String> setKeys = request.getListaUsuariosDestinatariosExternos().keySet();
      for (String usuario : setKeys) {
        UsuarioExternoDTO externo = new UsuarioExternoDTO();
        externo.setNombre(usuario);
        externo.setDestino(request.getListaUsuariosDestinatariosExternos().get(usuario));
        listaExternos.add(externo);
      }
      requestInternal.setListaUsuariosDestinatariosExternos(listaExternos);
    } else {
      requestInternal.setListaUsuariosDestinatariosExternos(new ArrayList<UsuarioExternoDTO>());
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "prepararComunicacion(RequestExternalGenerarDocumento, RequestGenerarDocumento, Map<String,String>) - end"); //$NON-NLS-1$
    }
    return requestInternal;
  }

  private boolean validarRepetidos(List<String> listaUno, List<String> listaDos) {
    if (logger.isDebugEnabled()) {
      logger.debug("validarRepetidos(List<String>, List<String>) - start"); //$NON-NLS-1$
    }

    for (String destinatario : listaUno) {
      for (String destino : listaDos) {
        if (destinatario.equals(destino)) {
          if (logger.isDebugEnabled()) {
            logger.debug("validarRepetidos(List<String>, List<String>) - end"); //$NON-NLS-1$
          }
          return true;
        }
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("validarRepetidos(List<String>, List<String>) - end"); //$NON-NLS-1$
    }
    return false;
  }

  private boolean validarRepetidosMismaLista(List<String> listaUno, List<String> listaDos) {
    if (logger.isDebugEnabled()) {
      logger.debug("validarRepetidosMismaLista(List<String>, List<String>) - start"); //$NON-NLS-1$
    }

    boolean mismoNombre = false;

    for (String destinatario : listaUno) {
      mismoNombre = false;
      for (String destino : listaDos) {
        if (destinatario.equals(destino) && mismoNombre) {
          if (logger.isDebugEnabled()) {
            logger.debug("validarRepetidosMismaLista(List<String>, List<String>) - end"); //$NON-NLS-1$
          }
          return true;
        } else {
          if (destinatario.equals(destino)) {
            mismoNombre = true;
          }
        }
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("validarRepetidosMismaLista(List<String>, List<String>) - end"); //$NON-NLS-1$
    }
    return false;
  }

  /**
   * Valida que los campos pasados para el tipo de documentos Template, no sean
   * menos que los que realmente necesita para su confeccion.
   * 
   * @param request
   * @param tipoDocumento
   * @throws Exception
   * @throws ClavesFaltantesException
   */
  private void validarCamposTemplate(Map<String, Object> campoTemplate,
      TipoDocumentoDTO tipoDocumento) throws Exception, ClavesFaltantesException {
    if (logger.isDebugEnabled()) {
      logger.debug("validarCamposTemplate(Map<String,Object>, TipoDocumentoDTO) - start"); //$NON-NLS-1$
    }

    try {
      procesamientoTemplate.validarCamposTemplatePorTipoDocumentoYMap(tipoDocumento,
          campoTemplate);
    } catch (ClavesFaltantesException vie) {
      logger.error("Error al validar los campos del template" + vie.getMessage(), vie);
      throw new ClavesFaltantesException(
          "Los campos ingresados para el Tipo de Documento Template: "
              + tipoDocumento.getAcronimo() + " son insuficientes");
    }

    if (logger.isDebugEnabled()) {
      logger.debug("validarCamposTemplate(Map<String,Object>, TipoDocumentoDTO) - end"); //$NON-NLS-1$
    }
  }

  /**
   * Valida que los paramétros obligatorios enviados por el sistema solicitante
   * sean diferentes de nulo.
   * 
   * @param request:
   *          Objeto RequestExternalGenerarDocumento
   * @throws ParametroInvalidoException
   * @throws SecurityNegocioException
   */
  private void validarParametros(RequestExternalGenerarDocumento request)
      throws ParametroInvalidoException, SecurityNegocioException {
    if (logger.isDebugEnabled()) {
      logger.debug("validarParametros(RequestExternalGenerarDocumento) - start"); //$NON-NLS-1$
    }

    String mensaje = "";
    boolean parametroInvalido = false;
    if (request.getAcronimoTipoDocumento() == null) {
      parametroInvalido = true;
      mensaje = "acronimoTipoDocumento";
    } else if (request.getReferencia() == null) {
      parametroInvalido = true;
      mensaje = "referencia";
    } else if (request.getSistemaOrigen() == null) {
      parametroInvalido = true;
      mensaje = "sistemaOrigen";
    } else if (request.getSistemaOrigen().equals("GEDO")) {
      mensaje = "sistemaOrigen";
      throw new ParametroInvalidoException("Parámetro " + mensaje + " debe ser distinto de GEDO");
    } else if (request.getUsuario() == null) {
      parametroInvalido = true;
      mensaje = "usuario";
    } else if (tipoDocumentoService
        .buscarTipoDocumentoByAcronimo(request.getAcronimoTipoDocumento()).getEsComunicable()) {

      validarDatosComunicables(request);
      if ((request.getListaUsuariosDestinatarios() == null
          || request.getListaUsuariosDestinatarios().isEmpty())
          && (request.getListaUsuariosDestinatariosExternos() == null
              || request.getListaUsuariosDestinatariosExternos().isEmpty())) {
        parametroInvalido = true;
        mensaje = "destinatario o destinatarioExterno";
      }
    }

    Usuario usuario = this.usuarioService.obtenerUsuario(request.getUsuario());
    if (usuario.getCodigoReparticionOriginal() == null || usuario.getCodigoReparticion() == null
        || usuario.getNombreReparticionOriginal() == null) {

      throw new ParametroInvalidoException("El usuario " + request.getUsuario()
          + " se encuentra inconsistente" + " y no puede firmar el documento.");
    }

    if (parametroInvalido)
      throw new ParametroInvalidoException("Parámetro " + mensaje + " es obligatorio");

    if (logger.isDebugEnabled()) {
      logger.debug("validarParametros(RequestExternalGenerarDocumento) - end"); //$NON-NLS-1$
    }
  }

  private void validarParametrosTemplate(RequestExternalGenerarDocumento request)
      throws ValidacionContenidoException {
    if (logger.isDebugEnabled()) {
      logger.debug("validarParametrosTemplate(RequestExternalGenerarDocumento) - start"); //$NON-NLS-1$
    }

    // Si envian idTransaccion nulo o vacios
    if ((request.getIdTransaccion() != null && request.getIdTransaccion().equals(0))) {
      throw new ValidacionContenidoException("Debe ingresar un IdTransaccion, "
          + "no puede no ingresar ningun valor. Para mas informacion, contacte a su administrador.");
    }

    if (logger.isDebugEnabled()) {
      logger.debug("validarParametrosTemplate(RequestExternalGenerarDocumento) - end"); //$NON-NLS-1$
    }
  }

  /**
   * Obtiene el objeto Tipo de documento, identificado por el acrónimo dado,
   * siempre que supere las validaciones necesarias. Validaciones: - Existencia
   * del tipo de documento identificado por el acrónimo. - Que el tipo de
   * documento tenga el atributo "esAutomatica" en true. - Que el tipo de
   * documento este en estado "ALTA".
   * 
   * @param acronimoTipoDocumento:
   *          Cadena que almacena el acrónimo del tipo de documento.
   * @return TipoDocumento.
   * @throws ParametroInvalidoException
   */
  private TipoDocumentoDTO obtenerTipoDocumento(String acronimoTipoDocumento)
      throws ParametroInvalidoException {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerTipoDocumento(String) - start"); //$NON-NLS-1$
    }

    TipoDocumentoDTO tipoDocumento = null;
    tipoDocumento = tipoDocumentoService.buscarTipoDocumentoByAcronimo(acronimoTipoDocumento);
    if (tipoDocumento == null)
      throw new ParametroInvalidoException(
          "Tipo de documento: " + acronimoTipoDocumento + " inexistente");
    if (!tipoDocumento.getEsAutomatica())
      throw new ParametroInvalidoException(
          "El tipo de documento: " + acronimoTipoDocumento + " debe ser de generación automática");
    if (tipoDocumento.getEstado().compareTo(TipoDocumentoDTO.ESTADO_ACTIVO) != 0)
      throw new ParametroInvalidoException(
          "El tipo de documento: " + acronimoTipoDocumento + " debe estar habilitado");

    if (logger.isDebugEnabled()) {
      logger.debug("obtenerTipoDocumento(String) - end"); //$NON-NLS-1$
    }
    return tipoDocumento;
  }

  /**
   * Convierte el Map recibido en una lista de metadatos, basándose en los datos
   * propios configurados para el tipo de documento. Valida la obligatoriedad de
   * los datos propios del documento.
   * 
   * @param datosPropios
   */
  private List<DocumentoMetadataDTO> cargarDatosPropios(Map<String, String> datosPropiosDocumento,
      TipoDocumentoDTO tipoDocumento, String referencia) throws ParametroInvalidoException {
    if (logger.isDebugEnabled()) {
      logger.debug("cargarDatosPropios(Map<String,String>, TipoDocumentoDTO, String) - start"); //$NON-NLS-1$
    }

    List<MetadataDTO> listaDatosPropiosTipoDocumento = tipoDocumento.getListaDatosVariables();
    List<DocumentoMetadataDTO> listaDatosPropiosDocumento = new ArrayList<>();
    boolean datoPropioObligatorio = false;
    for (MetadataDTO datoPropio : listaDatosPropiosTipoDocumento) {
      String valorDatoPropio = null;
      if (datosPropiosDocumento == null && datoPropio.isObligatoriedad())
        datoPropioObligatorio = true;
      if (datosPropiosDocumento != null) {
        valorDatoPropio = (String) datosPropiosDocumento.get(datoPropio.getNombre());
        if (valorDatoPropio == null && datoPropio.isObligatoriedad())
          datoPropioObligatorio = true;
        if (valorDatoPropio != null) {
          DocumentoMetadataDTO documentoMetadata = new DocumentoMetadataDTO();
          documentoMetadata.setNombre(datoPropio.getNombre());
          documentoMetadata.setValor(valorDatoPropio);
          documentoMetadata.setTipo(datoPropio.getTipo());
          documentoMetadata.setObligatoriedad(datoPropio.isObligatoriedad());
          documentoMetadata.setOrden(datoPropio.getOrden());
          listaDatosPropiosDocumento.add(documentoMetadata);
        }
      }
      if (datoPropioObligatorio) {
        String mensajeError = "Dato propio: " + datoPropio.getNombre()
            + " es obligatorio para el tipo de documento: " + tipoDocumento.getNombre()
            + " con referencia: " + referencia;
        throw new ParametroInvalidoException(mensajeError);
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("cargarDatosPropios(Map<String,String>, TipoDocumentoDTO, String) - end"); //$NON-NLS-1$
    }
    return listaDatosPropiosDocumento;
  }

  /**
   * Obtiene la información del usuario, valida que el usuario pertenezca a
   * CCOO.
   * 
   * @param nombreUsuario
   * @return DatosUsuarioBean, bean con los datos del usuario, repartición,
   *         nombre, apellido, cargo,etc.
   * @throws ParametroInvalidoException
   */
  private Usuario obtenerDatosUsuario(String nombreUsuario) throws ParametroInvalidoException {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerDatosUsuario(String) - start"); //$NON-NLS-1$
    }

    Usuario datosUsuario = null;
    try {
      datosUsuario = this.usuarioService.obtenerUsuario(nombreUsuario);
    } catch (SecurityNegocioException e) {
      logger.error("Mensaje de error", e);
    }
    if (datosUsuario == null || datosUsuario.getCodigoReparticion() == null) {
      String mensajeError = "El usuario: " + nombreUsuario
          + " no puede generar documentos en GEDO."
          + " Por favor ingrese a Escritorio Único o contacte a su Administrador";
      throw new ParametroInvalidoException(mensajeError);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("obtenerDatosUsuario(String) - end"); //$NON-NLS-1$
    }
    return datosUsuario;
  }

  private void validarReparticiones(TipoDocumentoDTO tipoDocumento,
      RequestExternalGenerarDocumento request, Usuario datosUsuario)
      throws ValidacionContenidoException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "validarReparticiones(TipoDocumento, RequestExternalGenerarDocumento, Usuario) - start"); //$NON-NLS-1$
    }

    List<ReparticionHabilitadaDTO> listaReparticiones;
    listaReparticiones = ListMapper.mapList(
        reparticionHabilitadaService
            .findByTipoDocumento(tipoDocumento),
        this.mapper, ReparticionHabilitadaDTO.class);

    // Multireparticion
    List<String> reparticiones = new ArrayList<>();
    try {
      if (datosUsuario.getIsMultireparticion() != null && datosUsuario.getIsMultireparticion()) {
        reparticiones = this.usuarioService
            .obtenerReparticionesHabilitadasPorUsuario(datosUsuario.getUsername());
      }
      reparticiones.add(datosUsuario.getCodigoReparticion());
      boolean encontro = false;
      for (ReparticionHabilitadaDTO reparticionHabilitada : listaReparticiones) {
        if (encontro) {
          break;
        }
        if (reparticionHabilitada.getPermisoFirmar() && reparticionHabilitada.getPermisoIniciar()
            && reparticionHabilitada.getCodigoReparticion()
                .equals(Constantes.TODAS_REPARTICIONES_HABILITADAS)) {
          encontro = true;
          break;
        }
        for (String reparticion : reparticiones) {
          if (reparticionHabilitada.getPermisoFirmar() && reparticionHabilitada.getPermisoIniciar()
              && reparticionHabilitada.getCodigoReparticion().equals(reparticion)) {
            encontro = true;
            break;
          }
        }
      }

      if (!encontro) {
        throw new ValidacionContenidoException(
            "el tipo de documento no esta habilitado para la reparticion "
                + datosUsuario.getCodigoReparticion());
      }

    } catch (SecurityNegocioException e) {
      logger.error("Mensaje de error", e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "validarReparticiones(TipoDocumento, RequestExternalGenerarDocumento, Usuario) - end"); //$NON-NLS-1$
    }
  }

  public ResponseExternalGenerarDocumento generarDocumentoUsuarioExterno(
      @WebParam(name = "request") RequestExternalGenerarDocumentoUsuarioExterno request)
      throws ParametroInvalidoException, CantidadDatosNoSoportadaException,
      ErrorGeneracionDocumentoException, ClavesFaltantesException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "generarDocumentoUsuarioExterno(RequestExternalGenerarDocumentoUsuarioExterno) - start"); //$NON-NLS-1$
    }

    validarParametrosUsuarioExterno(request);
    RequestGenerarDocumento requestInternal = new RequestGenerarDocumento();
    requestInternal.setReparticion(request.getReparticion());
    requestInternal.setNombreYApellido(request.getNombreYApellido());
    requestInternal.setCargo(request.getCargo());
    requestInternal.setSector(request.getSector());
    ResponseExternalGenerarDocumento returnResponseExternalGenerarDocumento = this
        .generarDocumentoGEDO(request, requestInternal);
    if (logger.isDebugEnabled()) {
      logger.debug(
          "generarDocumentoUsuarioExterno(RequestExternalGenerarDocumentoUsuarioExterno) - end"); //$NON-NLS-1$
    }
    return returnResponseExternalGenerarDocumento;
  }

  private void validarParametrosUsuarioExterno(
      RequestExternalGenerarDocumentoUsuarioExterno request) throws ParametroInvalidoException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "validarParametrosUsuarioExterno(RequestExternalGenerarDocumentoUsuarioExterno) - start"); //$NON-NLS-1$
    }

    String mensaje = "";
    boolean parametroInvalido = false;

    if (request.getUsuario() == null || request.getUsuario().trim().isEmpty()) {
      parametroInvalido = true;
      mensaje = "usuario";
    } else if (request.getNombreYApellido() == null
        || request.getNombreYApellido().trim().isEmpty()) {
      parametroInvalido = true;
      mensaje = "nombre y apellido";
    } else if (request.getCargo() == null || request.getCargo().trim().isEmpty()) {
      parametroInvalido = true;
      mensaje = "cargo";
    } else if (request.getReparticion() == null || request.getReparticion().trim().isEmpty()) {
      parametroInvalido = true;
      mensaje = "código de repartición";
    }
    if (parametroInvalido)
      throw new ParametroInvalidoException("Parámetro " + mensaje + " es obligatorio");

    if (logger.isDebugEnabled()) {
      logger.debug(
          "validarParametrosUsuarioExterno(RequestExternalGenerarDocumentoUsuarioExterno) - end"); //$NON-NLS-1$
    }
  }

}
