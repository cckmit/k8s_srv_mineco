package com.egoveris.deo.model.model;

import java.io.Serializable;
import java.util.Date;

public class SistemaOrigenDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private Integer id;
  private String nombre;
  private Date fechaCreacion;
  private String mecanismoRespuesta;
  private String direccionRespuesta;
  private String campo;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public Date getFechaCreacion() {
    return fechaCreacion;
  }

  public void setFechaCreacion(Date fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }

  public String getMecanismoRespuesta() {
    return mecanismoRespuesta;
  }

  public void setMecanismoRespuesta(String mecanismoRespuesta) {
    this.mecanismoRespuesta = mecanismoRespuesta;
  }

  public String getDireccionRespuesta() {
    return direccionRespuesta;
  }

  public void setDireccionRespuesta(String direccionRespuesta) {
    this.direccionRespuesta = direccionRespuesta;
  }

  public String getCampo() {
    return campo;
  }

  public void setCampo(String campo) {
    this.campo = campo;
  }

  public String toString() {
    StringBuffer buffer = new StringBuffer();
    buffer.append("ID = ");
    buffer.append(id);
    buffer.append(" nombre = ");
    buffer.append(nombre);
    buffer.append(" fecha creacion = ");
    buffer.append(fechaCreacion);
    buffer.append(" mecanismo respuesta = ");
    buffer.append(mecanismoRespuesta);
    buffer.append(" direccion respuesta = ");
    buffer.append(direccionRespuesta);
    buffer.append(" campo = ");
    buffer.append(campo);
    return buffer.toString();
  }
}
