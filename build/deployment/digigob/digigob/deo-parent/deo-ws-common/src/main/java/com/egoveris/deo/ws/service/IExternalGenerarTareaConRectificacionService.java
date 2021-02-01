package com.egoveris.deo.ws.service;

import com.egoveris.deo.model.model.RequestExternalGenerarTareaConRectificacion;
import com.egoveris.deo.model.model.ResponseExternalGenerarTarea;
import com.egoveris.deo.ws.exception.CantidadDatosNoSoportadaException;
import com.egoveris.deo.ws.exception.ClavesFaltantesException;
import com.egoveris.deo.ws.exception.ErrorGeneracionTareaException;
import com.egoveris.deo.ws.exception.ParametroInvalidoException;
import com.egoveris.deo.ws.exception.ParametroInvalidoTareaException;
import com.egoveris.deo.ws.exception.ParametroNoExisteException;
import com.egoveris.deo.ws.exception.UsuarioSinPermisoException;

/**
 * Datos obligatorios del request - Data: Documento que se quiere firmar pdf en
 * bytes [] - AcronimoTipoDocumento - Referencia - Tarea - TipoArchivo -
 * UsuarioEmisor - nroSadeRectificacion: Número de Sade que hace referencia al
 * documento 1. - textoRectificacion: Texto de rectificación que se va agregar
 * al documento 1. - pagContexto: Establece en que página se va agregar el texto
 * de rectificación. "primera" / "ultima" / "todas" - Opcional: - marcaAgua:
 * Establece si se agrega la marca de agua en el documento 1. Boolean
 */

public interface IExternalGenerarTareaConRectificacionService {

  public ResponseExternalGenerarTarea generarTareaConRectificacion(
      RequestExternalGenerarTareaConRectificacion request) throws ParametroInvalidoException,
      ParametroInvalidoTareaException, ParametroNoExisteException, UsuarioSinPermisoException,
      ErrorGeneracionTareaException, CantidadDatosNoSoportadaException, ClavesFaltantesException;

}
