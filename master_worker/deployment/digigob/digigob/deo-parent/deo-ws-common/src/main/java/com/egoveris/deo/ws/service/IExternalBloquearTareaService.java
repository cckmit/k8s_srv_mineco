package com.egoveris.deo.ws.service;

import com.egoveris.deo.model.model.RequestExternalBloquearTarea;
import com.egoveris.deo.model.model.ResponseExternalBloquearTarea;
import com.egoveris.deo.ws.exception.ErrorBloquearTareaException;

public interface IExternalBloquearTareaService {

  public ResponseExternalBloquearTarea bloquearTarea(RequestExternalBloquearTarea request)
      throws ErrorBloquearTareaException;
}
