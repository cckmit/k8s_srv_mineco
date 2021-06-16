package com.egoveris.deo.model.model;

import java.io.Serializable;

public class PrepararFirmaRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  // assigne de la tarea
  private String currentUser;
  private String executionId;
  private byte[] certs;

  private boolean firmaExterna = false;
  private String nombreYApellido;
  private String cargo;
  private String reparticion;

  public PrepararFirmaRequest(String currentUser, String executionId, byte[] certs) {
    super();
    this.currentUser = currentUser;
    this.executionId = executionId;
    this.certs = certs;
  }

  public PrepararFirmaRequest(String currentUser, String executionId, byte[] certs,
      String nombreYApellido, String cargo, String reparticion) {
    super();
    this.currentUser = currentUser;
    this.executionId = executionId;
    this.certs = certs;

    this.firmaExterna = true;
    this.nombreYApellido = nombreYApellido;
    this.cargo = cargo;
    this.reparticion = reparticion;
  }

  public String getCurrentUser() {
    return currentUser;
  }

  public void setCurrentUser(String currentUser) {
    this.currentUser = currentUser;
  }

  public String getExecutionId() {
    return executionId;
  }

  public void setExecutionId(String executionId) {
    this.executionId = executionId;
  }

  public byte[] getCerts() {
    return certs;
  }

  public void setCerts(byte[] certs) {
    this.certs = certs;
  }

  public boolean isFirmaExterna() {
    return firmaExterna;
  }

  public void setFirmaExterna(boolean firmaExterna) {
    this.firmaExterna = firmaExterna;
  }

  public String getNombreYApellido() {
    return nombreYApellido;
  }

  public void setNombreYApellido(String nombreYApellido) {
    this.nombreYApellido = nombreYApellido;
  }

  public String getCargo() {
    return cargo;
  }

  public void setCargo(String cargo) {
    this.cargo = cargo;
  }

  public String getReparticion() {
    return reparticion;
  }

  public void setReparticion(String reparticion) {
    this.reparticion = reparticion;
  }

}