package com.egoveris.deo.ws.service.impl;

import com.egoveris.deo.base.service.TareaJBPMService;
import com.egoveris.deo.model.model.RequestExternalBloquearTarea;
import com.egoveris.deo.model.model.ResponseExternalBloquearTarea;
import com.egoveris.deo.ws.exception.ErrorBloquearTareaException;
import com.egoveris.deo.ws.service.IExternalBloquearTareaService;

import javax.jws.WebParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExternalBloquearTareaServiceImpl implements IExternalBloquearTareaService {

  @Autowired
  TareaJBPMService tareaJBPMService;

  @Override
  public ResponseExternalBloquearTarea bloquearTarea(
      RequestExternalBloquearTarea request)
      throws ErrorBloquearTareaException {

    if (request.getWorkflowID() != null && request.getOperacion() != null) {
      tareaJBPMService.bloquearTarea(request.getOperacion().name(), request.getWorkflowID());
    } else {
      throw new ErrorBloquearTareaException("Error al bloquear/desbloquear la tarea:");
    }
    ResponseExternalBloquearTarea response = new ResponseExternalBloquearTarea();
    response.setEstado("Ok");

    return response;
  }

}
