package com.egoveris.te.base.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ACTIVIDAD")
public class Actividad implements Serializable {

  private static final long serialVersionUID = -5136957421951116190L;

  @GeneratedValue(strategy = GenerationType.AUTO)
  @Id
  @Column(name = "ID")
  private Long id;

  @Column(name = "ESTADO")
  private String estado;

  @Column(name = "USUARIO_ALTA")
  private String usuarioAlta;

  @Column(name = "USUARIO_CIERRE")
  private String usuarioCierre;

  @Column(name = "ID_OBJETIVO")
  private String idObjetivo;

  @Column(name = "PARENT_ID")
  private Long parentId;

  @Column(name = "FECHA_ALTA")
  private Date fechaAlta;

  @Column(name = "FECHA_CIERRE")
  private Date fechaCierre;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "id_tipo_actividad")
  private TipoActividad tipoActividad;

  @OneToMany(mappedBy = "idActividad", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @MapKeyColumn(name = "CAMPO")
  private Map<String, ParametroActividad> parametros;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public String getUsuarioAlta() {
    return usuarioAlta;
  }

  public void setUsuarioAlta(String usuarioAlta) {
    this.usuarioAlta = usuarioAlta;
  }

  public String getIdObjetivo() {
    return idObjetivo;
  }

  public void setIdObjetivo(String idObjetivo) {
    this.idObjetivo = idObjetivo;
  }

  public Date getFechaAlta() {
    return fechaAlta;
  }

  public void setFechaAlta(Date fechaAlta) {
    this.fechaAlta = fechaAlta;
  }

  public TipoActividad getTipoActividad() {
    return tipoActividad;
  }

  public void setTipoActividad(TipoActividad tipoActividad) {
    this.tipoActividad = tipoActividad;
  }

  public Long getParentId() {
    return parentId;
  }

  public void setParentId(Long parentId) {
    this.parentId = parentId;
  }

  public Map<String, ParametroActividad> getParametros() {
    return parametros;
  }

  public void setParametros(Map<String, ParametroActividad> parametros) {
    this.parametros = parametros;
  }

  public Date getFechaCierre() {
    return fechaCierre;
  }

  public void setFechaCierre(Date fechaCierre) {
    this.fechaCierre = fechaCierre;
  }

  public String getUsuarioCierre() {
    return usuarioCierre;
  }

  public void setUsuarioCierre(String usuarioCierre) {
    this.usuarioCierre = usuarioCierre;
  }
}