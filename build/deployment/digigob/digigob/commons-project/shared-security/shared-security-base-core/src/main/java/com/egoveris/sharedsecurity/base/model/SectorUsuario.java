package com.egoveris.sharedsecurity.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "edt_sade_sector_usuario")
public class SectorUsuario {

	 @Id
	  @Column(name = "id_sector_usuario")
	  @GeneratedValue(strategy = GenerationType.AUTO)
	  private Integer id;

	  @ManyToOne
	  @JoinColumn(name = "id_sector_interno")
	  private Sector sector;

	  @Column(name = "cargo_id")
	  private Integer cargoId;

	  @Column(name = "nombre_usuario")
	  private String nombreUsuario;

	  @Column(name = "proceso")
	  private String proceso;

	  @Column(name = "estado_registro")
	  private Boolean estado;

	  @Column(name = "fecha_Creacion")
	  private Date fechaCreacion;

	  @Column(name = "fecha_modificacion")
	  private Date fechaModificacion;

	  public SectorUsuario() {

	  }

	  /**
	   * Instantiates a new sector usuario.
	   *
	   * @param sector the sector
	   * @param usuario the usuario
	   * @param proceso the proceso
	   */
	  public SectorUsuario(Sector sector, String usuario, String proceso) {
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

	  public Sector getSector() {
	    return sector;
	  }

	  public void setSector(Sector sector) {
	    this.sector = sector;
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

	  public Integer getCargoId() {
	    return cargoId;
	  }

	  public void setCargoId(Integer cargoId) {
	    this.cargoId = cargoId;
	  }
}
