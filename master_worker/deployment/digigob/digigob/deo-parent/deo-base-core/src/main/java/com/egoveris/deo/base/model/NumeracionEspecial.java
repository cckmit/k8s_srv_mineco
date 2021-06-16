package com.egoveris.deo.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "GEDO_NUMERACIONESPECIAL")
public class NumeracionEspecial {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private Integer id;

  @Column(name = "codigoReparticion")
  private String codigoReparticion;

  @Column(name = "tipoDocumento")
  private Integer idTipoDocumento;

  @Column(name = "anio")
  private String anio;

  @Column(name = "numero")
  private Integer numero;

  @Column(name = "numeroInicial")
  private Integer numeroInicial;

  @Column(name = "codigoEcosistema")
  private String codigoEcosistema;

  @Column(name = "locked")
  private Long locked;

  public Long getLocked() {
    return locked;
  }

  public void setLocked(Long locked) {
    this.locked = locked;
  }

  public Integer getNumeroInicial() {
    return numeroInicial;
  }

  public void setNumeroInicial(Integer numeroInicial) {
    this.numeroInicial = numeroInicial;
  }

  public String getAnio() {
    return anio;
  }

  public void setAnio(String anio) {
    this.anio = anio;
  }

  public Integer getNumero() {
    return numero;
  }

  public void setNumero(Integer numero) {
    this.numero = numero;
  }

  public String getCodigoReparticion() {
    return codigoReparticion;
  }

  public void setCodigoReparticion(String codigoReparticion) {
    this.codigoReparticion = codigoReparticion;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setIdTipoDocumento(Integer idTipoDocumento) {
    this.idTipoDocumento = idTipoDocumento;
  }

  public Integer getIdTipoDocumento() {
    return idTipoDocumento;
  }

  public void setCodigoEcosistema(String codigoEcosistema) {
    this.codigoEcosistema = codigoEcosistema;
  }

  public String getCodigoEcosistema() {
    return codigoEcosistema;
  }
}