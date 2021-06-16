package com.egoveris.sharedorganismo.base.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "edt_sade_sector_interno")
public class SectorInterno {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID_SECTOR_INTERNO")
  private Integer id;

  @Column(name = "NOMBRE_SECTOR_INTERNO")
  private String nombre;

  @Column(name = "CODIGO_SECTOR_INTERNO")
  private String codigo;

  @Column(name = "SECTOR_MESA")
  private Boolean sectorMesa;

  @Column(name = "MESA_VIRTUAL")
  private Boolean mesaVirtual;

  @Column(name = "CODIGO_REPARTICION")
  private Long codigoReparticion;

  @Column(name = "VIGENCIA_DESDE")
  private Date vigenciaDesde;

  @Column(name = "VIGENCIA_HASTA")
  private Date vigenciaHasta;
  
  @Column(name = "ESTADO_REGISTRO")
  private String estadoRegistro;
  
  @OneToMany(mappedBy = "sectorInterno", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Set<SectorUsuario> sectoresUsuario;

  public Long getCodigoReparticion() {
    return codigoReparticion;
  }

  public void setCodigoReparticion(Long codigoReparticion) {
    this.codigoReparticion = codigoReparticion;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getCodigo() {
    return codigo;
  }

  public void setCodigo(String codigo) {
    this.codigo = codigo;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setSectorMesa(boolean sectorMesa) {
    this.sectorMesa = sectorMesa;
  }

  public boolean isSectorMesa() {
    return sectorMesa;
  }

  public boolean isMesaVirtual() {
    return mesaVirtual;
  }

  public void setMesaVirtual(boolean mesaVirtual) {
    this.mesaVirtual = mesaVirtual;
  }

  public Boolean getSectorMesa() {
    return sectorMesa;
  }

  public void setSectorMesa(Boolean sectorMesa) {
    this.sectorMesa = sectorMesa;
  }

  public Boolean getMesaVirtual() {
    return mesaVirtual;
  }

  public void setMesaVirtual(Boolean mesaVirtual) {
    this.mesaVirtual = mesaVirtual;
  }

  public Date getVigenciaDesde() {
    return vigenciaDesde;
  }

  public void setVigenciaDesde(Date vigenciaDesde) {
    this.vigenciaDesde = vigenciaDesde;
  }

  public Date getVigenciaHasta() {
    return vigenciaHasta;
  }

  public void setVigenciaHasta(Date vigenciaHasta) {
    this.vigenciaHasta = vigenciaHasta;
  }

  public String getEstadoRegistro() {
	return estadoRegistro;
  }

  public void setEstadoRegistro(String estadoRegistro) {
	this.estadoRegistro = estadoRegistro;
  }
  
  public Set<SectorUsuario> getSectoresUsuario() {
    return sectoresUsuario;
  }

  public void setSectoresUsuario(Set<SectorUsuario> sectoresUsuario) {
    this.sectoresUsuario = sectoresUsuario;
  }


}
