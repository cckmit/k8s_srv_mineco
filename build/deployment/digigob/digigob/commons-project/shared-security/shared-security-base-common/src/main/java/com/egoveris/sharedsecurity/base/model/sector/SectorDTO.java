package com.egoveris.sharedsecurity.base.model.sector;

import java.io.Serializable;
import java.util.Date;

import com.egoveris.sharedsecurity.base.model.reparticion.ReparticionDTO;

public class SectorDTO implements Serializable {

  /**
  * 
  */
  private static final long serialVersionUID = 3059200458351987053L;

  private Integer id;
  private String codigo;
  private String nombre;
  private String calle;
  private String numeroCalle;
  private String piso;
  private String oficina;
  private String telefono;
  private String fax;
  private String email;
  private String enRed;
  private String sectorMesa;
  private Date vigenciaDesde;
  private Date vigenciaHasta;
  private Boolean esArchivo;
  private Boolean mesaVirtual;
  private ReparticionDTO reparticion;
  private Boolean estadoRegistro;
  private String usuarioAsignador;
  private String usuarioCreacion;
  private Date fechaCreacion;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getCodigo() {
    return codigo;
  }

  public void setCodigo(String codigoSector) {
    this.codigo= codigoSector;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombreSector) {
    this.nombre = nombreSector;
  }

  public String getCalle() {
    return calle;
  }

  public void setCalle(String calle) {
    this.calle = calle;
  }

  public String getNumeroCalle() {
    return numeroCalle;
  }

  public void setNumeroCalle(String numeroCalle) {
    this.numeroCalle = numeroCalle;
  }

  public String getPiso() {
    return piso;
  }

  public void setPiso(String piso) {
    this.piso = piso;
  }

  public String getOficina() {
    return oficina;
  }

  public void setOficina(String oficina) {
    this.oficina = oficina;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public String getFax() {
    return fax;
  }

  public void setFax(String fax) {
    this.fax = fax;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getEnRed() {
    return enRed;
  }

  public void setEnRed(String enRed) {
    this.enRed = enRed;
  }

  public String getSectorMesa() {
    return sectorMesa;
  }

  public void setSectorMesa(String sectorMesa) {
    this.sectorMesa = sectorMesa;
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

  public Boolean getEsArchivo() {
    return esArchivo;
  }

  public void setEsArchivo(Boolean esArchivo) {
    this.esArchivo = esArchivo;
  }

  public Boolean getMesaVirtual() {
    return mesaVirtual;
  }

  public void setMesaVirtual(Boolean mesaVirtual) {
    this.mesaVirtual = mesaVirtual;
  }

  public ReparticionDTO getReparticion() {
    return reparticion;
  }

  public void setReparticion(ReparticionDTO reparticion) {
    this.reparticion = reparticion;
  }

	public Boolean getEstadoRegistro() {
		return estadoRegistro;
	}
	
	public void setEstadoRegistro(Boolean estado) {
		this.estadoRegistro = estado;
	}

  public String getUsuarioAsignador() {
    return usuarioAsignador;
  }

  public void setUsuarioAsignador(String usuarioAsignador) {
    this.usuarioAsignador = usuarioAsignador;
  }

  public String getUsuarioCreacion() {
    return usuarioCreacion;
  }

  public void setUsuarioCreacion(String usuarioCreacion) {
    this.usuarioCreacion = usuarioCreacion;
  }

  public Date getFechaCreacion() {
    return fechaCreacion;
  }

  public void setFechaCreacion(Date fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }
  
  

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    SectorDTO other = (SectorDTO) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("SectorDTO [id=").append(id).append(", codigoSector=").append(codigo)
        .append(", nombreSector=").append(nombre).append(", calle=").append(calle)
        .append(", numeroCalle=").append(numeroCalle).append(", piso=").append(piso)
        .append(", oficina=").append(oficina).append(", telefono=").append(telefono)
        .append(", fax=").append(fax).append(", email=").append(email).append(", enRed=")
        .append(enRed).append(", sectorMesa=").append(sectorMesa).append(", vigenciaDesde=")
        .append(vigenciaDesde).append(", vigenciaHasta=").append(vigenciaHasta)
        .append(", esArchivo=").append(esArchivo).append(", mesaVirtual=").append(mesaVirtual)
        .append(", reparticion=").append(reparticion).append(", estado=").append(estadoRegistro)
        .append(", usuarioAsignador=").append(usuarioAsignador).append(", usuarioCreacion=")
        .append(usuarioCreacion).append(", fechaCreacion=").append(fechaCreacion).append("]");
    return builder.toString();
  }

}