package com.egoveris.sharedsecurity.base.model.sector;

import java.io.Serializable;
import java.util.Date;

public class SectorUsuarioDTO implements Serializable {

  private static final long serialVersionUID = -6649398115978994122L;

  private Integer id;
  private SectorDTO sector;
  private Integer cargoId;
  private String nombreUsuario;
  private String proceso;
  private Boolean estado;
  private Date fechaCreacion;
  private Date fechaModificacion;

  public SectorUsuarioDTO() {

  }

  public SectorUsuarioDTO(SectorDTO sector, String usuario, String proceso) {
    this.sector = sector;
    this.nombreUsuario = usuario;
    this.estado = true;
    this.proceso = proceso;
    this.fechaCreacion = new Date();

  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public SectorDTO getSector() {
    return sector;
  }

  public void setSector(SectorDTO sector) {
    this.sector = sector;
  }

  public Integer getCargoId() {
    return cargoId;
  }

  public void setCargoId(Integer cargoId) {
    this.cargoId = cargoId;
  }

  public String getNombreUsuario() {
    return nombreUsuario;
  }

  public void setNombreUsuario(String nombreUsuario) {
    this.nombreUsuario = nombreUsuario;
  }

  public String getProceso() {
    return proceso;
  }

  public void setProceso(String proceso) {
    this.proceso = proceso;
  }

  public Boolean getEstado() {
    return estado;
  }

  public void setEstado(Boolean estado) {
    this.estado = estado;
  }

  public Date getFechaCreacion() {
    return fechaCreacion;
  }

  public void setFechaCreacion(Date fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }

  public Date getFechaModificacion() {
    return fechaModificacion;
  }

  public void setFechaModificacion(Date fechaModificacion) {
    this.fechaModificacion = fechaModificacion;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("SectorUsuarioDTO [id=").append(id).append(", sector=").append(sector)
        .append(", cargoId=").append(cargoId).append(", nombreUsuario=").append(nombreUsuario)
        .append(", proceso=").append(proceso).append(", estado=").append(estado)
        .append(", fechaCreacion=").append(fechaCreacion).append(", fechaModificacion=")
        .append(fechaModificacion).append("]");
    return builder.toString();
  }

}