package com.egoveris.te.base.model.trata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TRATA_REPARTICION")
public class TrataIntegracionReparticion {

  @Id 
  @GeneratedValue(strategy=GenerationType.AUTO)
  @Column(name="ID")
	private Long id;
  
  @Column(name="ID_TRATA")
	private Long idTrata;
	
  @Column(name="CODIGO_REPARTICION")
	private String codigoReparticion;
	
  @Column(name="HABILITADA")
	private boolean habilitada;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getIdTrata() {
    return idTrata;
  }

  public void setIdTrata(Long idTrata) {
    this.idTrata = idTrata;
  }

  public String getCodigoReparticion() {
    return codigoReparticion;
  }

  public void setCodigoReparticion(String codigoReparticion) {
    this.codigoReparticion = codigoReparticion;
  }

  public boolean isHabilitada() {
    return habilitada;
  }

  public void setHabilitada(boolean habilitada) {
    this.habilitada = habilitada;
  }
  
  
	
}
