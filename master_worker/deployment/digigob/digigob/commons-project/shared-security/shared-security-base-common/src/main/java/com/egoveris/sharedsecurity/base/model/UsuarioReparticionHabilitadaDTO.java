package com.egoveris.sharedsecurity.base.model;

import java.io.Serializable;
import java.util.Date;

import com.egoveris.sharedsecurity.base.model.cargo.CargoDTO;
import com.egoveris.sharedsecurity.base.model.reparticion.ReparticionDTO;
import com.egoveris.sharedsecurity.base.model.sector.SectorDTO;

public class UsuarioReparticionHabilitadaDTO implements Serializable {

  private static final long serialVersionUID = 4671892224732638948L;

  private Integer id;
  private ReparticionDTO reparticion;
  private String nombreUsuario;
  private SectorDTO sector;
  private Date fechaRevision;
  private String tipoRevision;
  private Integer cargoId;
  private CargoDTO cargo;

 
  
  public UsuarioReparticionHabilitadaDTO() {
    //Constructor
  }

  public UsuarioReparticionHabilitadaDTO(String username, ReparticionDTO reparticion,
      SectorDTO sector, CargoDTO cargo) {
    this.nombreUsuario = username;
    this.reparticion = reparticion;
    this.sector = sector;
    this.cargo = cargo;
  }

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

  public String getNombreUsuario() {
    return nombreUsuario;
  }

  public void setNombreUsuario(String nombreUsuario) {
    this.nombreUsuario = nombreUsuario;
  }

  public SectorDTO getSector() {
    return sector;
  }

  public void setSector(SectorDTO sector) {
    this.sector = sector;
  }

  public Date getFechaRevision() {
    return fechaRevision;
  }

  public void setFechaRevision(Date fechaRevision) {
    this.fechaRevision = fechaRevision;
  }

  /**
   * Gets the tipo revision.
   *
   * @return the tipo revision
   */
  public String getTipoRevision() {
    if ("ADD".equals(tipoRevision)) {
      this.tipoRevision = TipoRevisionEnum.AGREGADO.name();
    }
    if ("MOD".equals(tipoRevision)) {
      this.tipoRevision = TipoRevisionEnum.ELIMINADO.name();
    }
    return tipoRevision;
  }

  public void setTipoRevision(String tipoRevision) {
    this.tipoRevision = tipoRevision;
  }

  public Integer getCargoId() {
    return cargoId;
  }

  public void setCargoId(Integer cargoId) {
    this.cargoId = cargoId;
  }

  public CargoDTO getCargo() {
    return cargo;
  }

  public void setCargo(CargoDTO cargo) {
    this.cargo = cargo;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof UsuarioReparticionHabilitadaDTO)) {
      return false;
    }
    if (getId()==(((UsuarioReparticionHabilitadaDTO)obj).getId())) {
      return true;
    } else if ((getNombreUsuario()
        .equals(((UsuarioReparticionHabilitadaDTO) obj).getNombreUsuario()))
        && (getReparticion().equals(((UsuarioReparticionHabilitadaDTO) obj).getReparticion()))
        && (getSector().equals(((UsuarioReparticionHabilitadaDTO) obj).getSector()))) {
      return true;
    } else {
      return super.equals(obj);
    }
  }

  @Override
  public int hashCode() {
    if ((getReparticion() != null) && (getNombreUsuario() != null)) {
      return getReparticion().hashCode() + getNombreUsuario().hashCode()
          + +getSector().hashCode();
    }
    return super.hashCode();
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("UsuarioReparticionHabilitadaDTO [id=").append(id).append(", reparticion=")
        .append(reparticion).append(", nombreUsuario=").append(nombreUsuario).append(", sector=")
        .append(sector).append(", fechaRevision=").append(fechaRevision).append(", tipoRevision=")
        .append(tipoRevision).append(", cargoId=").append(cargoId).append(", cargo=").append(cargo)
        .append("]");
    return builder.toString();
  }

}