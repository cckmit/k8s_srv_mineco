package com.egoveris.te.base.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SADE_REPARTICION")
public class Reparticion implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 6880948487698470479L;

  @Id
  @Column(name = "ID_REPARTICION")
  private Integer id;

  @Column(name = "CODIGO_REPARTICION")
  private String codigoReparticion;

  @Column(name = "CODIGO_REPAR_INTER")
  private String codigoReparticionInterno;

  @Column(name = "NOMBRE_REPARTICION")
  private String nombreReparticion;

  @Column(name = "VIGENCIA_DESDE")
  private Date vigenciaDesde;

  @Column(name = "VIGENCIA_HASTA")
  private Date vigenciaHasta;

  @Column(name = "NUMERO")
  private String numero;

  @Column(name = "PISO")
  private String piso;

  @Column(name = "OFICINA")
  private String oficina;

  @Column(name = "TELEFONO")
  private String telefono;

  @Column(name = "FAX")
  private String fax;

  @Column(name = "EMAIL")
  private String email;

  @Column(name = "ID_ESTRUCTURA")
  private int idEstructura;

  @Column(name = "EN_RED")
  private String enRed;

  @Column(name = "SECTOR_MESA")
  private String sectorMesa;

  @Column(name = "VERSION")
  private Integer version;

  @Column(name = "FECHA_CREACION")
  private Date fechaCreacion;

  @Column(name = "USUARIO_CREACION")
  private String usuarioCreacion;

  @Column(name = "FECHA_MODIFICACION")
  private Date fechaModificacion;

  @Column(name = "USUARIO_MODIFICACION")
  private String usuarioModificacion;

  @Column(name = "ESTADO_REGISTRO")
  private String estadoRegistro;

  @Column(name = "ES_DGTAL")
  private Integer esDgtal;

  @Column(name = "COD_DGTAL")
  private String codDgtal;

  @Column(name = "REP_PADRE")
  private Integer repPadre;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getCodigoReparticion() {
    return codigoReparticion;
  }

  public void setCodigoReparticion(String codigoReparticion) {
    this.codigoReparticion = codigoReparticion;
  }

  public String getCodigoReparticionInterno() {
    return codigoReparticionInterno;
  }

  public void setCodigoReparticionInterno(String codigoReparticionInterno) {
    this.codigoReparticionInterno = codigoReparticionInterno;
  }

  public String getNombreReparticion() {
    return nombreReparticion;
  }

  public void setNombreReparticion(String nombreReparticion) {
    this.nombreReparticion = nombreReparticion;
  }

  public Date getVigenciaDesde() {
    return vigenciaDesde;
  }

  public void setVigenciaDesde(Date vigenciaDesde) {
    this.vigenciaDesde = vigenciaDesde;
  }

  public Date getVigenciaHasta() {
    return vigenciaHasta;
  }

  public void setVigenciaHasta(Date vigenciaHasta) {
    this.vigenciaHasta = vigenciaHasta;
  }

  public String getNumero() {
    return numero;
  }

  public void setNumero(String numero) {
    this.numero = numero;
  }

  public String getPiso() {
    return piso;
  }

  public void setPiso(String piso) {
    this.piso = piso;
  }

  public String getOficina() {
    return oficina;
  }

  public void setOficina(String oficina) {
    this.oficina = oficina;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    telefono = telefono;
  }

  public String getFax() {
    return fax;
  }

  public void setFax(String fax) {
    this.fax = fax;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public int getIdEstructura() {
    return idEstructura;
  }

  public void setIdEstructura(int idEstructura) {
    this.idEstructura = idEstructura;
  }

  public String getEnRed() {
    return enRed;
  }

  public void setEnRed(String enRed) {
    this.enRed = enRed;
  }

  public String getSectorMesa() {
    return sectorMesa;
  }

  public void setSectorMesa(String sectorMesa) {
    this.sectorMesa = sectorMesa;
  }

  public Integer getVersion() {
    return version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  public Date getFechaCreacion() {
    return fechaCreacion;
  }

  public void setFechaCreacion(Date fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }

  public String getUsuarioCreacion() {
    return usuarioCreacion;
  }

  public void setUsuarioCreacion(String usuarioCreacion) {
    this.usuarioCreacion = usuarioCreacion;
  }

  public Date getFechaModificacion() {
    return fechaModificacion;
  }

  public void setFechaModificacion(Date fechaModificacion) {
    this.fechaModificacion = fechaModificacion;
  }

  public String getUsuarioModificacion() {
    return usuarioModificacion;
  }

  public void setUsuarioModificacion(String usuarioModificacion) {
    this.usuarioModificacion = usuarioModificacion;
  }

  public String getEstadoRegistro() {
    return estadoRegistro;
  }

  public void setEstadoRegistro(String estadoRegistro) {
    this.estadoRegistro = estadoRegistro;
  }

  public Integer getEsDgtal() {
    return esDgtal;
  }

  public void setEsDgtal(Integer esDgtal) {
    this.esDgtal = esDgtal;
  }

  public Integer getRepPadre() {
    return repPadre;
  }

  public void setRepPadre(Integer repPadre) {
    this.repPadre = repPadre;
  }

  public String getCodDgtal() {
    return codDgtal;
  }

  public void setCodDgtal(String codDgtal) {
    this.codDgtal = codDgtal;
  }
}
