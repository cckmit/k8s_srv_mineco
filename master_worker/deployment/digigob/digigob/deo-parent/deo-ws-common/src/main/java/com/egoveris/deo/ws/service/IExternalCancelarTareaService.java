package com.egoveris.deo.ws.service;

import com.egoveris.deo.model.model.RequestExternalCancelarTarea;
import com.egoveris.deo.model.model.ResponseExternalCancelarTarea;
import com.egoveris.deo.ws.exception.ErrorCancelarTareaException;

public interface IExternalCancelarTareaService {

  public ResponseExternalCancelarTarea cancelarTarea(RequestExternalCancelarTarea request)
      throws ErrorCancelarTareaException;
}
