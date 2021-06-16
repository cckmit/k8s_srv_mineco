package com.egoveris.deo.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "GEDO_SISTEMA_ORIGEN")

public class SistemaOrigen {

  @Id
  @Column(name = "ID")
  private Integer id;

  @Column(name = "NOMBRE")
  private String nombre;

  @Column(name = "FECHA_CREACION")
  private Date fechaCreacion;

  @Column(name = "MECANISMO_RESPUESTA")
  private String mecanismoRespuesta;

  @Column(name = "DIRECCION_RESPUESTA")
  private String direccionRespuesta;

  @Column(name = "CAMPO")
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
  
  @Override
  public String toString() {
    StringBuilder buffer = new StringBuilder();
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
