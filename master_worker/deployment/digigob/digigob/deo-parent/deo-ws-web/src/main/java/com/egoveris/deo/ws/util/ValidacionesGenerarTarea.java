package com.egoveris.deo.ws.util;

import com.egoveris.deo.base.exception.CantidadDatosException;
import com.egoveris.deo.base.exception.ParametroInvalidoException;
import com.egoveris.deo.base.exception.ValidacionContenidoException;
import com.egoveris.deo.base.service.ReparticionHabilitadaService;
import com.egoveris.deo.base.util.UtilitariosServicios;
import com.egoveris.deo.model.model.RequestExternalGenerarTarea;
import com.egoveris.deo.model.model.TipoDocumentoDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.deo.ws.exception.ParametroInvalidoTareaException;
import com.egoveris.deo.ws.exception.ParametroNoExisteException;
import com.egoveris.deo.ws.exception.UsuarioSinPermisoException;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Clase utilizada para validar los parametros del Servicio Externo,
 * ExternalGenerarTareaServiceImpl
 *
 */
public abstract class ValidacionesGenerarTarea {

  @Autowired
  private IUsuarioService usuarioService;
  @Autowired
  private ReparticionHabilitadaService reparticionesHabilitadaService;
  @Value("${gedo.maximoArchivos}")
  protected String maximoArchivos;

  private static final Logger logger = LoggerFactory.getLogger(ValidacionesGenerarTarea.class);

  /**
   * Valida que el tamaño del arreglo de bytes que almacena el contenido del
   * documento, no supere el tamaño definido por el sistema.
   * 
   * @param data
   * @throws CantidadDatosException
   */

  public void validarTamanioContenido(byte[] data) throws CantidadDatosException {
    int maxSizeArchivo = Integer.parseInt(this.maximoArchivos);
    int maxSizeArchivoBytesTamanio = maxSizeArchivo * Constantes.FACTOR_CONVERSION;
    if (data.length > maxSizeArchivoBytesTamanio)
      throw new CantidadDatosException(
          "El contenido del documento supera la cantidad soportada de: "
              + maxSizeArchivoBytesTamanio + " Bytes.");
  }

  /**
   * Detecta el tipo de contenido almacenado en el arreglo de bytes.
   * 
   * @param datos
   * @return La extensión del archivo, identificando el tipo de contenido.
   * @throws ValidacionContenidoException
   */

  public String obtenerTipoArchivo(byte[] datos) throws ValidacionContenidoException {
    String tipoContenido = null;
    try {
      tipoContenido = UtilitariosServicios.obtenerTipoContenido(datos);
    } catch (Exception e) {
      throw new ValidacionContenidoException(
          "No ha sido posible obtener el tipo de contenido de la información enviada", e);
    }
    return tipoContenido;
  }

  /**
   * Valida que el Tipo de Documento pueda ser utilizado en la tarea
   * seleccionada, y los usuarios intervinientes, tengan sus permisos
   * correspondientes.
   * 
   * @param tipoDoc:
   *          Tipo de documento a utilizar
   * @param request:
   *          Request que se recibio del WS, de tipo RequestExternalGenerarTarea
   * @throws ParametroInvalidoTareaException
   * @throws UsuarioSinPermisoException
   */
  // MULTIREPARTICION
  public void validarPermisos(RequestExternalGenerarTarea request, TipoDocumentoDTO tipoDoc,
      List<String> usuarioFirmante)
      throws ParametroInvalidoTareaException, UsuarioSinPermisoException {
    // Valido la lista de reparticiones del usuario con la lista de
    // reparticiones del documento para iniciar el doc
    if (!this.reparticionesHabilitadaService.validarPermisosUsuariosDeSuListaReparticiones(tipoDoc,
        request.getUsuarioEmisor(), Constantes.REPARTICION_PERMISO_INICIAR)
        && request.getTarea().equalsIgnoreCase(Constantes.ACT_CONFECCIONAR)) {
      throw new UsuarioSinPermisoException("El Usuario: " + request.getUsuarioEmisor()
          + " no tiene permiso para Iniciar el Tipo de Documento: " + tipoDoc.getAcronimo());
    }
    if (!tipoDoc.getEsFirmaConjunta()
        && request.getTarea().equalsIgnoreCase(Constantes.ACT_FIRMAR)) {
      // valido si la reparticion seleccionada coincide con la lista de
      // reparticiones del documento
      if (!this.reparticionesHabilitadaService.validarPermisoReparticion(tipoDoc,
          usuarioFirmante.get(0), Constantes.REPARTICION_PERMISO_FIRMAR)) {
        throw new UsuarioSinPermisoException("El Usuario: " + usuarioFirmante.get(0)
            + " no tiene permiso para firmar el Tipo de Documento: " + tipoDoc.getAcronimo());
      }
    } else if (tipoDoc.getEsFirmaConjunta() && request.getUsuarioFirmante() != null) {
      for (String firmante : usuarioFirmante) {
        // valido si la reparticion seleccionada coincide con la lista de
        // reparticiones del documento
        if (!this.reparticionesHabilitadaService.validarPermisoReparticion(tipoDoc, firmante,
            Constantes.REPARTICION_PERMISO_FIRMAR)) {
          throw new UsuarioSinPermisoException("El Usuario: " + firmante
              + " no tiene permiso para firmar el Tipo de Documento: " + tipoDoc.getAcronimo());
        }
      }
    }
  }

  /**
   * Transforma la lista de firmantes, en una lista de DatosUsuarioBean, que
   * obtiene con la lista de tipo String dada, si uno de los firmantes enviadas
   * por parametro no existe, se lanza una excepcion de tipo
   * ParametroNoExisteException.
   * 
   * @param firmantes
   *          : Lista de tipo String, con los firmantes a recibir la tarea.
   * @return usuariosFirmantes : Lista de de tipo DatosUsuarioBean con los
   *         firmantes correspondientes.
   * @throws ParametroNoExisteException
   *           : Excepcion que se lanza si el usuario no existe.
   */

  public List<Usuario> obtenerFirmantes(List<String> firmantes) throws ParametroNoExisteException {
    List<Usuario> usuariosFirmantes = new ArrayList<Usuario>();
    for (String firmante : firmantes) {
      Usuario datosUsuario;
      try {
        datosUsuario = this.usuarioService.obtenerUsuario(firmante);
        if (datosUsuario == null)
          throw new ParametroNoExisteException("El usuario firmante " + firmante + " no existe");
        usuariosFirmantes.add(datosUsuario);

      } catch (SecurityNegocioException e) {
        logger.error("Mensaje de error", e);
      }

    }
    return usuariosFirmantes;
  }

  /**
   * Valida los paramétros enviados por el sistema solicitante.
   * 
   * @param request:
   *          Objeto RequestExternalGenerarTarea
   * @throws ParametroInvalidoException
   * @throws ParametroInvalidoTareaException
   * @throws ParametroNoExisteException
   */

  public void validarParametros(RequestExternalGenerarTarea request, TipoDocumentoDTO tipoDocumento,
      List<String> usuarioFirmante) throws ParametroInvalidoException,
      ParametroInvalidoTareaException, ParametroNoExisteException {
    String mensaje = "";
    boolean parametroInvalido = false;
    boolean parametroTareaInvalido = false;
    boolean parametroInexistente = false;
    if (request.getTarea() == null) {
      parametroInvalido = true;
      mensaje = "tarea";
    } else if (!request.getTarea().equalsIgnoreCase(Constantes.ACT_CONFECCIONAR)
        && !request.getTarea().equalsIgnoreCase(Constantes.ACT_FIRMAR)
        && !request.getTarea().equalsIgnoreCase(Constantes.ACT_REVISAR)) {
      parametroInexistente = true;
      mensaje = "tarea: " + request.getTarea();
    } else if (!request.getTarea().equalsIgnoreCase(Constantes.ACT_CONFECCIONAR)
        && (request.getReferencia() == null || request.getReferencia().isEmpty())) {
      parametroTareaInvalido = true;
      mensaje = "referencia";
    } else if ((tipoDocumento.getTipoProduccion() != Constantes.TIPO_PRODUCCION_TEMPLATE)
        && (request.getData() == null || request.getData().length == 0)
        && !request.getTarea().equalsIgnoreCase(Constantes.ACT_CONFECCIONAR)) {
      parametroTareaInvalido = true;
      mensaje = "Data";
    } else if (request.getSistemaIniciador() == null || request.getSistemaIniciador().isEmpty()) {
      parametroInvalido = true;
      mensaje = "sistemaIniciador";
    } else if (request.getSistemaIniciador().equals("GEDO")) {
      mensaje = "sistemaIniciador";
      throw new ParametroInvalidoException("Parámetro " + mensaje + " debe ser distinto de GEDO");
    } else if (request.getUsuarioEmisor() == null || request.getUsuarioEmisor().isEmpty()) {
      parametroInvalido = true;
      mensaje = "usuarioEmisor";
    } else if (((request.getUsuarioReceptor() == null) || request.getUsuarioReceptor().isEmpty())
        && !request.getTarea().equalsIgnoreCase(Constantes.ACT_FIRMAR)) {
      parametroTareaInvalido = true;
      mensaje = "usuarioReceptor";
    } else if (((request.getUsuarioFirmante() == null) || request.getUsuarioFirmante().isEmpty())
        && (request.getTarea().equalsIgnoreCase(Constantes.ACT_FIRMAR))) {
      parametroTareaInvalido = true;
      mensaje = "usuarioFirmante";
    } else {
      try {

        if (this.obtenerUser(request.getUsuarioEmisor()) == null) {
          parametroInexistente = true;
          mensaje = "usuario emisor: " + request.getUsuarioEmisor();
        } else if (!request.getTarea().equalsIgnoreCase(Constantes.ACT_FIRMAR)
            && this.obtenerUser(request.getUsuarioReceptor()) == null) {
          parametroInexistente = true;
          mensaje = "usuario receptor: " + request.getUsuarioReceptor();
        } else if (request.getTarea().equalsIgnoreCase(Constantes.ACT_FIRMAR)
            && !tipoDocumento.getEsFirmaConjunta() && request.getUsuarioFirmante().size() != 1) {
          throw new ParametroInvalidoException("El Tipo de Documento: "
              + tipoDocumento.getAcronimo() + ", solo acepta un Usuario Firmante");
        } else if (request.getTarea().equalsIgnoreCase(Constantes.ACT_FIRMAR)
            && !tipoDocumento.getEsFirmaConjunta()
            && this.obtenerUser(usuarioFirmante.get(0)) == null) {
          parametroInexistente = true;
          mensaje = "usuario firmante: " + usuarioFirmante;
        } else if (request.getTarea().equalsIgnoreCase(Constantes.ACT_FIRMAR)
            && tipoDocumento.getEsFirmaConjunta() && request.getUsuarioFirmante().size() == 1) {
          throw new ParametroInvalidoException(
              "El tipo de documento exige firma conjunta, por favor ingrese al menos dos usuarios firmantes.");
        } else if (tipoDocumento.getEsComunicable()
            && request.getTarea().equalsIgnoreCase(Constantes.ACT_FIRMAR)
            && (request.getListaUsuariosDestinatarios() == null
                || (request.getListaUsuariosDestinatarios() != null
                    && request.getListaUsuariosDestinatarios().isEmpty()))
            && (request.getListaUsuariosDestinatariosExternos() == null
                || (request.getListaUsuariosDestinatariosExternos() != null
                    && request.getListaUsuariosDestinatariosExternos().isEmpty()))) {
          parametroTareaInvalido = true;
          mensaje = "destinatario o destinatarioExterno";
        } else if (tipoDocumento.getEsComunicable()) {

          if (request.getListaUsuariosDestinatarios() != null) {
            validarRepetidosMismaLista(request.getListaUsuariosDestinatarios(),
                request.getListaUsuariosDestinatarios());
            validarUsuarios(request.getListaUsuariosDestinatarios());
          }

          if (request.getListaUsuariosDestinatariosCopia() != null) {
            validarRepetidosMismaLista(request.getListaUsuariosDestinatariosCopia(),
                request.getListaUsuariosDestinatariosCopia());
            validarUsuarios(request.getListaUsuariosDestinatariosCopia());
          }

          if (request.getListaUsuariosDestinatariosCopiaOculta() != null) {
            validarRepetidosMismaLista(request.getListaUsuariosDestinatariosCopiaOculta(),
                request.getListaUsuariosDestinatariosCopiaOculta());
            validarUsuarios(request.getListaUsuariosDestinatariosCopiaOculta());
          }

          if (request.getListaUsuariosDestinatarios() != null
              && request.getListaUsuariosDestinatariosCopia() != null) {
            validarRepetidos(request.getListaUsuariosDestinatarios(),
                request.getListaUsuariosDestinatariosCopia());
          }

          if (request.getListaUsuariosDestinatarios() != null
              && request.getListaUsuariosDestinatariosCopiaOculta() != null) {
            validarRepetidos(request.getListaUsuariosDestinatarios(),
                request.getListaUsuariosDestinatariosCopiaOculta());
          }

          if (request.getListaUsuariosDestinatariosCopia() != null
              && request.getListaUsuariosDestinatariosCopiaOculta() != null) {
            validarRepetidos(request.getListaUsuariosDestinatariosCopiaOculta(),
                request.getListaUsuariosDestinatariosCopia());
          }

        }
      } catch (SecurityNegocioException e) {
        logger.error("Mensaje de error", e);
      }
    }
    if (parametroInexistente)
      throw new ParametroNoExisteException("El parametro " + mensaje + " no existe.");
    if (parametroInvalido)
      throw new ParametroInvalidoException("Parámetro " + mensaje + " es obligatorio");
    if (parametroTareaInvalido)
      throw new ParametroInvalidoTareaException(
          "En la tarea: " + request.getTarea() + ", el parámetro " + mensaje + " es obligatorio.");
  }

  private Usuario obtenerUser(String request) throws SecurityNegocioException {
    try {
      if (request == null) {
        return null;
      }
      Usuario user = this.usuarioService.obtenerUsuario(request);
      return user;
    } catch (UsernameNotFoundException e) {
      logger.error("Error al obtener usuario. " + e.getMessage(), e);
      return null;
    }
  }

  private void validarRepetidos(List<String> listaUno, List<String> listaDos)
      throws ParametroInvalidoException {

    if (!listaUno.isEmpty() && !listaDos.isEmpty()) {
      for (String destinatario : listaUno) {
        for (String destino : listaDos) {
          if (destinatario.equals(destino)) {
            throw new ParametroInvalidoException("No se pueden repetir usuarios entre listas");
          }
        }
      }
    }

  }

  private void validarRepetidosMismaLista(List<String> listaUno, List<String> listaDos)
      throws ParametroInvalidoException {
    boolean mismoNombre;

    for (String destinatario : listaUno) {
      mismoNombre = false;
      for (String destino : listaDos) {
        if (destinatario.equals(destino) && mismoNombre) {
          throw new ParametroInvalidoException("No se pueden repetir usuarios en la misma lista");
        } else {
          if (destinatario.equals(destino)) {
            mismoNombre = true;
          }
        }
      }
    }

  }

  private void validarUsuarios(List<String> listaUsuarios) throws ParametroInvalidoException {
    for (String usuario : listaUsuarios) {
      try {
        if (usuarioService.obtenerUsuario(usuario) == null) {
          throw new ParametroInvalidoException(
              "Parámetro " + usuario + " no es un usuario válido");
        }
      } catch (SecurityNegocioException e) {
        logger.error("Mensaje de error", e);
      }
    }
  }

}
