package com.egoveris.deo.ws.service;

import com.egoveris.deo.model.model.RequestExternalGenerarTarea;
import com.egoveris.deo.model.model.ResponseExternalGenerarTarea;
import com.egoveris.deo.ws.exception.CantidadDatosNoSoportadaException;
import com.egoveris.deo.ws.exception.ClavesFaltantesException;
import com.egoveris.deo.ws.exception.ErrorGeneracionTareaException;
import com.egoveris.deo.ws.exception.ParametroInvalidoException;
import com.egoveris.deo.ws.exception.ParametroInvalidoTareaException;
import com.egoveris.deo.ws.exception.ParametroNoExisteException;
import com.egoveris.deo.ws.exception.UsuarioSinPermisoException;

public interface IExternalGenerarTareaNombramientoService {

  public ResponseExternalGenerarTarea generarTareaGEDO(RequestExternalGenerarTarea request)
      throws ParametroInvalidoException, ParametroInvalidoTareaException,
      ParametroNoExisteException, UsuarioSinPermisoException, ErrorGeneracionTareaException,
      CantidadDatosNoSoportadaException, ClavesFaltantesException;

}
