package com.egoveris.edt.web.admin.pl.renderers;

public class ApLayOut {

  private String nombreAplicacion;
  private int idAplicacion;
  private boolean esSeleccionado;

  public String getNombreAplicacion() {
    return nombreAplicacion;
  }

  public void setNombreAplicacion(String nombreAplicacion) {
    this.nombreAplicacion = nombreAplicacion;
  }

  public int getIdAplicacion() {
    return idAplicacion;
  }

  public void setIdAplicacion(int idAplicacion) {
    this.idAplicacion = idAplicacion;
  }

  public boolean isEsSeleccionado() {
    return esSeleccionado;
  }

  public void setEsSeleccionado(boolean esSeleccionado) {
    this.esSeleccionado = esSeleccionado;
  }

}