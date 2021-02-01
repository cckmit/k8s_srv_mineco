package com.egoveris.deo.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "GEDO_NUMEROSUSADOS")
public class NumerosUsados {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "tipodocumento")
  private TipoDocumento tipoDocumento;

  @Column(name = "codigoReparticion")
  private String codigoReparticion;

  @Column(name = "anio")
  private String anio;

  @Column(name = "numeroSADE")
  private String numeroSADE;

  @Column(name = "numeroEspecial")
  private String numeroEspecial;

  @Column(name = "estado")
  private String estado = "INVALIDO";

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public TipoDocumento getTipoDocumento() {
    return tipoDocumento;
  }

  public void setTipoDocumento(TipoDocumento tipoDocumento) {
    this.tipoDocumento = tipoDocumento;
  }

  public String getCodigoReparticion() {
    return codigoReparticion;
  }

  public void setCodigoReparticion(String codigoReparticion) {
    this.codigoReparticion = codigoReparticion;
  }

  public String getAnio() {
    return anio;
  }

  public void setAnio(String anio) {
    this.anio = anio;
  }

  public String getNumeroSADE() {
    return numeroSADE;
  }

  public void setNumeroSADE(String numeroSADE) {
    this.numeroSADE = numeroSADE;
  }

  public String getNumeroEspecial() {
    return numeroEspecial;
  }

  public void setNumeroEspecial(String numeroEspecial) {
    this.numeroEspecial = numeroEspecial;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  @Override
  public String toString() {
    return tipoDocumento + "-" + anio + "-" + numeroSADE + "-" + numeroEspecial + "-"
        + codigoReparticion;
  }

}
