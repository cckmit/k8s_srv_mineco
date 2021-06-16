package com.egoveris.sharedsecurity.base.model.reparticion;

import java.io.Serializable;

import com.egoveris.sharedsecurity.base.model.sector.SectorDTO;

public class ReparticionSeleccionadaDTO implements Serializable {

  private static final long serialVersionUID = -4178371707226376063L;

  private Integer id;
  private ReparticionDTO reparticion;
  private String usuario;
  private SectorDTO sector;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public ReparticionDTO getReparticion() {
    return reparticion;
  }

  public void setReparticion(ReparticionDTO reparticion) {
    this.reparticion = reparticion;
  }

  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  public SectorDTO getSector() {
    return sector;
  }

  public void setSector(SectorDTO sector) {
    this.sector = sector;
  }

  @Override
  public int hashCode() {
    return getId().hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null)
      return false;
    if(!(obj instanceof ReparticionSeleccionadaDTO))
      return false;
    ReparticionSeleccionadaDTO rep = (ReparticionSeleccionadaDTO) obj;
    return rep.getId().equals(this.getId());
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ReparticionSeleccionadaDTO [id=").append(id).append(", reparticion=")
        .append(reparticion).append(", usuario=").append(usuario).append(", sector=")
        .append(sector).append("]");
    return builder.toString();
  }

}