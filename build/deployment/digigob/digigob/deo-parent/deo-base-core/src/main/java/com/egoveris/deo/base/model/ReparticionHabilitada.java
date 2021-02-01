package com.egoveris.deo.base.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "GEDO_TIPODOCUMENTO_REPARTICION")
public class ReparticionHabilitada implements Comparable<ReparticionHabilitada> {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
  @JoinColumn(name = "tipoDocumento")
  private TipoDocumento tipoDocumento;

  @Column(name = "codigoReparticion")
  private String codigoReparticion;

  @Column(name = "permisoIniciar")
  private Boolean permisoIniciar;

  @Column(name = "permisoFirmar")
  private Boolean permisoFirmar;

  @Column(name = "estado")
  private Boolean estado;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Boolean getPermisoIniciar() {
    return permisoIniciar;
  }

  public void setPermisoIniciar(Boolean permisoIniciar) {
    this.permisoIniciar = permisoIniciar;
  }

  public Boolean getPermisoFirmar() {
    return permisoFirmar;
  }

  public void setPermisoFirmar(Boolean permisoFirmar) {
    this.permisoFirmar = permisoFirmar;
  }

  public Boolean getEstado() {
    return estado;
  }

  public void setEstado(Boolean estado) {
    this.estado = estado;
  }

  public String getCodigoReparticion() {
    return codigoReparticion;
  }

  public void setCodigoReparticion(String codigoReparticion) {
    this.codigoReparticion = codigoReparticion;
  }

  public void setTipoDocumento(TipoDocumento tipoDocumento) {
    this.tipoDocumento = tipoDocumento;
  }

  public TipoDocumento getTipoDocumento() {
    return tipoDocumento;
  }

  public int compareTo(ReparticionHabilitada reparticion) {
    int resultadoComparacion;
    if ((id == null)) {
      if (reparticion.getId() == null) {
        resultadoComparacion = codigoReparticion.compareTo(reparticion.getCodigoReparticion());
      } else {
        resultadoComparacion = 1;
      }
    } else {
      if (reparticion.getId() != null) {
        resultadoComparacion = id.compareTo(reparticion.getId());
      } else {
        resultadoComparacion = -1;
      }
    }
    return resultadoComparacion;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof ReparticionHabilitada) {
      return this.compareTo((ReparticionHabilitada) obj) == 0;
    }
    return false;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((codigoReparticion == null) ? 0 : codigoReparticion.hashCode());
    result = prime * result + ((estado == null) ? 0 : estado.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((permisoFirmar == null) ? 0 : permisoFirmar.hashCode());
    result = prime * result + ((permisoIniciar == null) ? 0 : permisoIniciar.hashCode());
    result = prime * result + ((tipoDocumento == null) ? 0 : tipoDocumento.hashCode());
    return result;
  }
}