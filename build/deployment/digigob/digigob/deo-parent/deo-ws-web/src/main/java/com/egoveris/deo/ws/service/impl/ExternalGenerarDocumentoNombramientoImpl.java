package com.egoveris.deo.ws.service.impl;

import com.egoveris.deo.base.exception.ArchivoDuplicadoException;
import com.egoveris.deo.base.exception.ArchivoFirmadoException;
import com.egoveris.deo.base.exception.CantidadDatosException;
import com.egoveris.deo.base.exception.ExtensionesFaltantesException;
import com.egoveris.deo.base.exception.FormatoInvalidoException;
import com.egoveris.deo.base.exception.ParametroInvalidoException;
import com.egoveris.deo.base.exception.SADERollbackErrorException;
import com.egoveris.deo.base.exception.TamanoInvalidoException;
import com.egoveris.deo.base.exception.ValidacionCampoFirmaException;
import com.egoveris.deo.base.exception.ValidacionContenidoException;
import com.egoveris.deo.base.service.ArchivosEmbebidosService;
import com.egoveris.deo.base.service.GenerarDocumentoService;
import com.egoveris.deo.base.service.ProcesamientoTemplate;
import com.egoveris.deo.base.service.TipoDocumentoService;
import com.egoveris.deo.base.util.GeneradorDocumentoFactory;
import com.egoveris.deo.model.model.ArchivoEmbebidoDTO;
import com.egoveris.deo.model.model.DocumentoMetadataDTO;
import com.egoveris.deo.model.model.MetadataDTO;
import com.egoveris.deo.model.model.RequestExternalGenerarDocumento;
import com.egoveris.deo.model.model.RequestGenerarDocumento;
import com.egoveris.deo.model.model.ResponseExternalGenerarDocumento;
import com.egoveris.deo.model.model.ResponseGenerarDocumento;
import com.egoveris.deo.model.model.TipoDocumentoDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.deo.ws.exception.CantidadDatosNoSoportadaException;
import com.egoveris.deo.ws.exception.ClavesFaltantesException;
import com.egoveris.deo.ws.exception.ErrorGeneracionDocumentoException;
import com.egoveris.deo.ws.service.IExternalGenerarDocumentoNombramientoService;
import com.egoveris.ffdd.model.exception.DynFormException;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExternalGenerarDocumentoNombramientoImpl
    implements IExternalGenerarDocumentoNombramientoService {

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

  private static final Logger logger = LoggerFactory
      .getLogger(ExternalGenerarDocumentoServiceImpl.class);

  public ResponseExternalGenerarDocumento generarDocumentoGEDO(
      RequestExternalGenerarDocumento request)
      throws ParametroInvalidoException, CantidadDatosNoSoportadaException,
      ErrorGeneracionDocumentoException, ClavesFaltantesException {
    if (logger.isDebugEnabled()) {
      logger.debug("generarDocumentoGEDO(RequestExternalGenerarDocumento) - start"); //$NON-NLS-1$
    }

    int contadorEmbebidos = 0;
    ResponseGenerarDocumento response = null;
    TipoDocumentoDTO tipoDocumento = null;
    Map<String, Object> camposTemplate = new HashMap<String, Object>();
    RequestGenerarDocumento requestInternal = new RequestGenerarDocumento();
    ResponseExternalGenerarDocumento responseExternal = new ResponseExternalGenerarDocumento();
    try {
      validarParametros(request);
      tipoDocumento = obtenerTipoDocumento(request.getAcronimoTipoDocumento());
      if (tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_TEMPLATE
          || tipoDocumento.getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO_TEMPLATE) {
        validarParametrosTemplate(request);
        camposTemplate = obtenerCamposTemplate(tipoDocumento, request);
        validarCamposTemplate(camposTemplate, tipoDocumento);
      } else {
        if (request.getData() == null)
          throw new ParametroInvalidoException("Parámetro data es obligatorio");
      }
      requestInternal.setTipoDocumentoGedo(tipoDocumento);
      Usuario datosUsuario = obtenerDatosUsuario(request.getUsuario());
      requestInternal.setCodigoReparticion(datosUsuario.getCodigoReparticion());
      requestInternal.setUsuario(request.getUsuario());
      requestInternal.setMotivo(request.getReferencia());
      requestInternal.setData(request.getData());
      requestInternal.setNombreAplicacion(request.getSistemaOrigen());
      requestInternal.setNombreAplicacionIniciadora(request.getSistemaOrigen());
      requestInternal.setUsuarioIniciador(request.getUsuario());
      List<DocumentoMetadataDTO> listaDatosPropiosDocumento = null;
      listaDatosPropiosDocumento = cargarDatosPropios(request.getMetaDatos(), tipoDocumento,
          request.getReferencia());
      if (listaDatosPropiosDocumento.size() > 0)
        requestInternal.setDocumentoMetadata(listaDatosPropiosDocumento);
      requestInternal.setTipoArchivo(request.getTipoArchivo());
      requestInternal.setCamposTemplate((HashMap<String, Object>) camposTemplate);
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
      archivosEmbebidosService.verificarObligatoriedadExtensiones(
          requestInternal.getListaArchivosEmbebidos(), tipoDocumento);
      GenerarDocumentoService generarDocumentoService = generadorDocumentoFactory
          .obtenerGeneradorDocumento(tipoDocumento);
      response = generarDocumentoService.generarDocumentoExterno(requestInternal);
      responseExternal.setNumero(response.getNumero());
      if (tipoDocumento.getEsEspecial())
        responseExternal.setNumeroEspecial(responseExternal.getNumeroEspecial());
      responseExternal.setUrlArchivoGenerado(response.getUrlArchivoGenerado());
      logger.debug("Documento pedido por usuario " + request.getUsuario() + "generado OK: "
          + response.getNumero());
      return responseExternal;
    } catch (ParametroInvalidoException pie) {
      logger.error("Parámetros inválidos para documento con referencia: " + request.getReferencia()
          + " . Sistema Origen " + request.getSistemaOrigen(), pie);
      throw pie;
    } catch (IOException ioe) {
      logger.error(ioe.getMessage());
    } catch (ArchivoFirmadoException afe) {
      logger.error("Error en validación de data para generacion de documento externo: "
          + request.getReferencia() + " . Sistema Origen " + request.getSistemaOrigen(), afe);
      throw new ParametroInvalidoException(
          "Error en validación de data para generacion de documento externo: "
              + request.getReferencia() + ", " + afe.getMessage());
      // TODO ver quien lanza esta exception
    } catch (SADERollbackErrorException sre) {
      logger.error(sre.getDefaultMessage());
      throw new ErrorGeneracionDocumentoException(
          "No se ha podido generar el documento. Error interno de GEDO." + sre.getMessage());
    } catch (ClavesFaltantesException cfe) {
      logger.error("Error en validaciones para Tipo De Documento Template. ", cfe);
      throw cfe;
    } catch (ValidacionContenidoException vce) {
      logger.error("Error en validación del contenido del documento con referencia: "
          + request.getReferencia() + " . Sistema Origen " + request.getSistemaOrigen(), vce);
      throw new ParametroInvalidoException(
          "Error en validación del contenido del documento con referencia: "
              + request.getReferencia() + ", " + vce.getMessage());
    } catch (ValidacionCampoFirmaException vcfe) {
      logger.error("Error en validación de campos de firma: " + request.getReferencia()
          + " . Sistema Origen " + request.getSistemaOrigen(), vcfe);
      throw new ParametroInvalidoException("Error en validación de campos de firma: "
          + request.getReferencia() + ", " + vcfe.getMessage());
    } catch (CantidadDatosException cde) {
      logger.error("Error en validación del documento con referencia: " + request.getReferencia()
          + " . Sistema Origen " + request.getSistemaOrigen(), cde);
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
      logger.debug("generarDocumentoGEDO(RequestExternalGenerarDocumento) - end"); //$NON-NLS-1$
    }
    return null;
  }

  private Map<String, Object> obtenerCamposTemplate(TipoDocumentoDTO tipoDocumento,
      RequestExternalGenerarDocumento request) throws DynFormException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "obtenerCamposTemplate(TipoDocumentoDTO, RequestExternalGenerarDocumento) - start"); //$NON-NLS-1$
    }

    if (request.getIdTransaccion() != null) {
      Map<String, Object> returnMap = procesamientoTemplate
          .obtenerCamposTemplate(request.getIdTransaccion());
      if (logger.isDebugEnabled()) {
        logger.debug(
            "obtenerCamposTemplate(TipoDocumentoDTO, RequestExternalGenerarDocumento) - end"); //$NON-NLS-1$
      }
      return returnMap;
    } else {
      Map<String, Object> returnMap = this
          .transformMapStringStringToStringObject(request.getCamposTemplate());
      if (logger.isDebugEnabled()) {
        logger.debug(
            "obtenerCamposTemplate(TipoDocumentoDTO, RequestExternalGenerarDocumento) - end"); //$NON-NLS-1$
      }
      return returnMap;
    }
  }

  private Map<String, Object> transformMapStringStringToStringObject(Map<String, String> mapss) {
    if (logger.isDebugEnabled()) {
      logger.debug("transformMapStringStringToStringObject(Map<String,String>) - start"); //$NON-NLS-1$
    }

    Map<String, Object> mapso = new HashMap<String, Object>();

    for (Map.Entry<String, String> entry : mapss.entrySet()) {
      mapso.put(entry.getKey(), entry.getValue());
    }

    if (logger.isDebugEnabled()) {
      logger.debug("transformMapStringStringToStringObject(Map<String,String>) - end"); //$NON-NLS-1$
    }
    return mapso;
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
      logger.error("Error al validar campos de template. " + vie.getMessage(), vie);
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
   */
  private void validarParametros(RequestExternalGenerarDocumento request)
      throws ParametroInvalidoException {
    if (logger.isDebugEnabled()) {
      logger.debug("validarParametros(RequestExternalGenerarDocumento) - start"); //$NON-NLS-1$
    }

    String mensaje = "";
    boolean parametroInvalido = false;
    if (request.getAcronimoTipoDocumento() == null) {
      parametroInvalido = true;
      mensaje = "acronimoTipoDocumento";
    }

    else if (request.getReferencia() == null) {
      parametroInvalido = true;
      mensaje = "referencia";
    } else if (request.getSistemaOrigen() == null) {
      parametroInvalido = true;
      mensaje = "sistemaOrigen";
    } else if (request.getUsuario() == null) {
      parametroInvalido = true;
      mensaje = "usuario";
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

    // Si envian ambos campos completos
    if (request.getIdTransaccion() != null && !request.getIdTransaccion().equals(0)
        && request.getCamposTemplate() != null && !request.getCamposTemplate().isEmpty()) {
      throw new ValidacionContenidoException("Debe ingresar o IdTransaccion o CamposTemplate, "
          + "no puede procesar ambos a la vez. Para mas informacion, contacte a su administrador.");
    }

    // Si envian ambos campos nulos o vacios
    if ((request.getIdTransaccion() != null && request.getIdTransaccion().equals(0)
        && request.getCamposTemplate() != null && request.getCamposTemplate().isEmpty())
        || (request.getIdTransaccion() == null && request.getCamposTemplate() == null)) {
      throw new ValidacionContenidoException("Debe ingresar o IdTransaccion o CamposTemplate, "
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
      logger.debug("cargarDatosPropios(Map<String,String>, TipoDocumento, String) - start"); //$NON-NLS-1$
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
      logger.debug("cargarDatosPropios(Map<String,String>, TipoDocumento, String) - end"); //$NON-NLS-1$
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
      if (datosUsuario == null || datosUsuario.getCodigoReparticion() == null) {
        String mensajeError = "El usuario: " + nombreUsuario
            + " no puede generar documentos en GEDO."
            + " Por favor ingrese a Escritorio Único o contacte a su Administrador";
        throw new ParametroInvalidoException(mensajeError);
      }
    } catch (SecurityNegocioException e) {
      logger.error("Mensaje de error", e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("obtenerDatosUsuario(String) - end"); //$NON-NLS-1$
    }
    return datosUsuario;
  }
}
