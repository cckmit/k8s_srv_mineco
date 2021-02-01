package com.egoveris.te.base.model;

import java.io.Serializable;
import java.util.Date;

public class ReparticionDTO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 6880948487698470479L;

  private int id;
  private String codigoReparticion;
  private String codigoReparticionInterno;
  private String nombreReparticion;
  private Date vigenciaDesde;
  private Date vigenciaHasta;
  private String numero;
  private String piso;
  private String oficina;
  private String Telefono;
  private String fax;
  private String email;
  private int idEstructura;
  private String enRed;
  private String sectorMesa;
  private Integer version;
  private Date fechaCreacion;
  private String usuarioCreacion;
  private Date fechaModificacion;
  private String usuarioModificacion;
  private String estadoRegistro;
  private Integer esDgtal;
  private String codDgtal;
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
    return Telefono;
  }

  public void setTelefono(String telefono) {
    Telefono = telefono;
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
