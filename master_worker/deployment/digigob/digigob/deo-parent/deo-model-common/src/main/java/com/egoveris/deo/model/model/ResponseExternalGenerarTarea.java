package com.egoveris.deo.model.model;

import java.io.Serializable;

/**
 * Define el formato de respuesta después de procesar la solicitud de generación
 * de una tarea.
 * 
 * - processId: ProcessId de JBPM, del proceso GEDO. - usuarioApoderado: Usuario
 * Apoderado que tiene la tarea, si es el mismo usuario al cual se le asigno la
 * tarea, este es null.
 */

public class ResponseExternalGenerarTarea implements Serializable {

  private static final long serialVersionUID = -419119953151411763L;

  private String processId;
  private String usuarioApoderado;
  private String licencia;

  public String getProcessId() {
    return processId;
  }

  public void setProcessId(String processId) {
    this.processId = processId;
  }

  public String getUsuarioApoderado() {
    return usuarioApoderado;
  }

  public void setUsuarioApoderado(String usuarioApoderado) {
    this.usuarioApoderado = usuarioApoderado;
  }

  public String getLicencia() {
    return licencia;
  }

  public void setLicencia(String licencia) {
    this.licencia = licencia;
  }

}
