package com.egoveris.te.base.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OperacionDTO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -4363701929069497138L;
  private Date fechaInicio;
  private Date fechaFin;
  private String numOficial;
  private String estadoBloq;
  private List<String> transiciones;
  private TipoOperacionDTO tipoOperacionOb;
  private String estadoOperacion;
  private Integer idSectorInterno;
  private String jbpmExecutionId;
  private Long id;
  private Long tipoOperacion;
  private Integer versionProcedure;
  private String usuarioCreador;
  private Integer idReparticion;

  public TipoOperacionDTO getTipoOperacionOb() {
    return tipoOperacionOb;
  }

  public void setTipoOperacionOb(TipoOperacionDTO tipoOperacionOb) {
    this.tipoOperacionOb = tipoOperacionOb;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getTipoOperacion() {
    return tipoOperacion;
  }

  public void setTipoOperacion(Long tipoOperacion) {
    this.tipoOperacion = tipoOperacion;
  }

  public Date getFechaInicio() {
    return fechaInicio;
  }

  public void setFechaInicio(Date fechaInicio) {
    this.fechaInicio = fechaInicio;
  }

  public Date getFechaFin() {
    return fechaFin;
  }

  public void setFechaFin(Date fechaFin) {
    this.fechaFin = fechaFin;
  }

  public String getNumOficial() {
    return numOficial;
  }

  public void setNumOficial(String numOficial) {
    this.numOficial = numOficial;
  }

  public String getEstadoBloq() {
    return estadoBloq;
  }

  public void setEstadoBloq(String estadoBloq) {
    this.estadoBloq = estadoBloq;
  }

  public List<String> getTransiciones() {
    return transiciones;
  }

  public void setTransiciones(List<String> transiciones) {
    this.transiciones = transiciones;
  }

  public String getEstadoOperacion() {
    return estadoOperacion;
  }

  public void setEstadoOperacion(String estadoOperacion) {
    this.estadoOperacion = estadoOperacion;
  }

  public String getCodigoCaratula() {
    final StringBuilder codigoCaratulaBuilder = new StringBuilder();
    codigoCaratulaBuilder.append("OP");
    codigoCaratulaBuilder.append("-");
    codigoCaratulaBuilder.append("EX");
    codigoCaratulaBuilder.append("-");
    codigoCaratulaBuilder.append(new SimpleDateFormat("yyyyMMdd").format(new Date()));
    codigoCaratulaBuilder.append("-");
    codigoCaratulaBuilder
        .append(getJbpmExecutionId().subSequence(getJbpmExecutionId().indexOf('.') + 1, getJbpmExecutionId().length()));
    return codigoCaratulaBuilder.toString();
  }

  public Integer getIdSectorInterno() {
    return idSectorInterno;
  }

  public void setIdSectorInterno(Integer idSectorInterno) {
    this.idSectorInterno = idSectorInterno;
  }

  public String getJbpmExecutionId() {
    return jbpmExecutionId;
  }

  public void setJbpmExecutionId(String jbpmExecutionId) {
    this.jbpmExecutionId = jbpmExecutionId;
  }

  public Integer getVersionProcedure() {
    return versionProcedure;
  }

  public void setVersionProcedure(Integer versionProcedure) {
    this.versionProcedure = versionProcedure;
  }

  public String getUsuarioCreador() {
    return usuarioCreador;
  }

  public void setUsuarioCreador(String usuarioCreador) {
    this.usuarioCreador = usuarioCreador;
  }

  public Integer getIdReparticion() {
    return idReparticion;
  }

  public void setIdReparticion(Integer idReparticion) {
    this.idReparticion = idReparticion;
  }

}
