package com.egoveris.edt.base.model.eu;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Clase de mapeo para la tabla: PERIODO_LICENCIA.
 * 
 * @author sabianco
 */

@Entity
@Table(name = "edt_periodo_licencia")
public class PeriodoLicencia {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID_PERIODO_LICENCIA")
  private Integer id;

  @Column(name = "FECHA_HORA_DESDE")
  private Date fechaHoraDesde;

  @Column(name = "FECHA_HORA_HASTA")
  private Date fechaHoraHasta;

  @Column(name = "USUARIO")
  private String usuario;

  @Column(name = "APODERADO")
  private String apoderado;

  @Column(name = "CONDICION_PERIODO")
  private String condicionPeriodo;

  @Column(name = "FECHA_CANCELACION")
  private Date fechaCancelacion;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Date getFechaHoraDesde() {
    return fechaHoraDesde;
  }

  public void setFechaHoraDesde(Date fechaHoraDesde) {
    this.fechaHoraDesde = fechaHoraDesde;
  }

  public Date getFechaHoraHasta() {
    return fechaHoraHasta;
  }

  public void setFechaHoraHasta(Date fechaHoraHasta) {
    this.fechaHoraHasta = fechaHoraHasta;
  }

  public String getApoderado() {
    return apoderado;
  }

  public void setApoderado(String apoderado) {
    this.apoderado = apoderado;
  }

  public String getCondicionPeriodo() {
    return condicionPeriodo;
  }

  public void setCondicionPeriodo(String condicionPeriodo) {
    this.condicionPeriodo = condicionPeriodo;
  }

  public Date getFechaCancelacion() {
    return fechaCancelacion;
  }

  public void setFechaCancelacion(Date fechaCancelacion) {
    this.fechaCancelacion = fechaCancelacion;
  }

  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

}
