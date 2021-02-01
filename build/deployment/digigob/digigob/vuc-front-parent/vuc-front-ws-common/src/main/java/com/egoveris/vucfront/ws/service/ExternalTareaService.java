package com.egoveris.vucfront.ws.service;

import com.egoveris.vucfront.ws.model.NuevaTareaRequest;

public interface ExternalTareaService {

  /**
   * Generates a new Tarea Subsanación.
   * 
   * @param request
   */
  void nuevaTareaSubsanacionRequest(NuevaTareaRequest request);
}
