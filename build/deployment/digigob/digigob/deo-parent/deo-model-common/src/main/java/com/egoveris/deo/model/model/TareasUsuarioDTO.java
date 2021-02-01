package com.egoveris.deo.model.model;

import com.egoveris.sharedsecurity.base.model.Usuario;

public class TareasUsuarioDTO {
  private Usuario datosUsuario;
  private int tareasPendientes;

  public TareasUsuarioDTO(Usuario datosUsuario, int cantTareas) {
    this.datosUsuario = datosUsuario;
    this.tareasPendientes = cantTareas;
  }

  public Usuario getDatosUsuario() {
    return datosUsuario;
  }

  public void setDatosUsuario(Usuario datosUsuario) {
    this.datosUsuario = datosUsuario;
  }

  public int getTareasPendientes() {
    return tareasPendientes;
  }

  public void setTareasPendientes(int tareasPendientes) {
    this.tareasPendientes = tareasPendientes;
  }

}
