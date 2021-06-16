package com.egoveris.sharedorganismo.base.model;

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
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID_SECTOR_USUARIO")
  private Integer id;

  @Column(name = "NOMBRE_USUARIO")
  private String nombre;

  @Column(name = "ESTADO_REGISTRO")
  private Boolean estado;

  @ManyToOne
  @JoinColumn(name = "ID_SECTOR_INTERNO")
  private SectorInterno sectorInterno;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public Boolean getEstado() {
    return estado;
  }

  public void setEstado(Boolean estado) {
    this.estado = estado;
  }

  public SectorInterno getSectorInterno() {
    return sectorInterno;
  }

  public void setSectorInterno(SectorInterno sectorInterno) {
    this.sectorInterno = sectorInterno;
  }

  
}
