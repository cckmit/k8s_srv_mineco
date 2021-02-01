package com.egoveris.te.base.model.expediente;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "METADATOS_TRATA")
public class ExpedienteMetadata {

  @EmbeddedId
  private ExpedienteMetadataPK expedienteMetadataPK;

  @Column(name = "NOMBRE_METADATO")
  private String nombre;

  @Column(name = "OBLIGATORIEDAD")
  private Boolean obligatoriedad;

  @Column(name = "TIPO")
  private Integer tipo;

  @Column(name = "VALOR_METADATO")
  private String valorMetadato;

  public ExpedienteMetadataPK getExpedienteMetadataPK() {
    return expedienteMetadataPK;
  }

  public void setExpedienteMetadataPK(ExpedienteMetadataPK expedienteMetadataPK) {
    this.expedienteMetadataPK = expedienteMetadataPK;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public Boolean getObligatoriedad() {
    return obligatoriedad;
  }

  public void setObligatoriedad(Boolean obligatoriedad) {
    this.obligatoriedad = obligatoriedad;
  }

  public Integer getTipo() {
    return tipo;
  }

  public void setTipo(Integer tipo) {
    this.tipo = tipo;
  }

  public String getValorMetadato() {
    return valorMetadato;
  }

  public void setValorMetadato(String valorMetadato) {
    this.valorMetadato = valorMetadato;
  }

}