package com.egoveris.edt.base.model;

import com.egoveris.edt.base.model.eu.AplicacionDTO;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class TareasPorSistemaBean implements Serializable {

  private static final long serialVersionUID = -6729651141723963173L;
  private Integer tareasPendientes;
  private Integer porcentajeFrecuenciaMenor;
  private Integer porcentajeFrecuenciaMayor;
  private Integer frecuencia1;
  private Integer frecuencia2;
  private Integer frecuencia3;
  private Integer frecuencia4;
  private AplicacionDTO aplicacion;

  /**
   * Instantiates a new tareas por sistema bean.
   *
   * @param i
   *          the i
   * @param aplicacion
   *          the aplicacion
   */
  public TareasPorSistemaBean(int i, AplicacionDTO aplicacion) {
    this.tareasPendientes = i;
    this.frecuencia1 = i;
    this.porcentajeFrecuenciaMenor = i;
    this.porcentajeFrecuenciaMayor = i;
    this.frecuencia2 = i;
    this.frecuencia3 = i;
    this.frecuencia4 = i;
    this.aplicacion = aplicacion;
  }

  public TareasPorSistemaBean() {
    // Constructor
  }

  public Integer getTareasPendientes() {
    return tareasPendientes;
  }

  public void setTareasPendientes(Integer tareasPendientes) {
    this.tareasPendientes = tareasPendientes;
  }

  public Integer getFrecuencia1() {
    return frecuencia1;
  }

  public void setFrecuencia1(Integer frecuencia1) {
    this.frecuencia1 = frecuencia1;
  }

  public Integer getFrecuencia2() {
    return frecuencia2;
  }

  public void setFrecuencia2(Integer frecuencia2) {
    this.frecuencia2 = frecuencia2;
  }

  public Integer getFrecuencia3() {
    return frecuencia3;
  }

  public void setFrecuencia3(Integer frecuencia3) {
    this.frecuencia3 = frecuencia3;
  }

  public Integer getFrecuencia4() {
    return frecuencia4;
  }

  public void setFrecuencia4(Integer frecuencia4) {
    this.frecuencia4 = frecuencia4;
  }

  public AplicacionDTO getAplicacion() {
    return aplicacion;
  }

  public void setAplicacion(AplicacionDTO aplicacion) {
    this.aplicacion = aplicacion;
  }

  public Integer getPorcentajeFrecuenciaMenor() {
    return porcentajeFrecuenciaMenor;
  }

  public void setPorcentajeFrecuenciaMenor(Integer porcentajeFrecuenciaMenor) {
    this.porcentajeFrecuenciaMenor = porcentajeFrecuenciaMenor;
  }

  public Integer getPorcentajeFrecuenciaMayor() {
    return porcentajeFrecuenciaMayor;
  }

  public void setPorcentajeFrecuenciaMayor(Integer porcentajeFrecuenciaMayor) {
    this.porcentajeFrecuenciaMayor = porcentajeFrecuenciaMayor;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder(StringUtils.EMPTY);
    sb.append("[ tareasPendientes: ");
    sb.append(this.getTareasPendientes());
    sb.append(" - porcentajeFrecuenciaMenor: ");
    sb.append(porcentajeFrecuenciaMenor);
    sb.append(" - porcentajeFrecuenciaMayor: ");
    sb.append(porcentajeFrecuenciaMayor);
    sb.append(" - frecuencia1: ");
    sb.append(frecuencia1);
    sb.append(" - frecuencia2: ");
    sb.append(frecuencia2);
    sb.append(" - frecuencia3: ");
    sb.append(frecuencia3);
    sb.append(" - frecuencia4: ");
    sb.append(frecuencia4);
    sb.append(" - aplicacion: ");
    sb.append((aplicacion != null) ? aplicacion.getNombreAplicacion() : "no definida!");
    sb.append(" ]");
    return sb.toString();
  }
}