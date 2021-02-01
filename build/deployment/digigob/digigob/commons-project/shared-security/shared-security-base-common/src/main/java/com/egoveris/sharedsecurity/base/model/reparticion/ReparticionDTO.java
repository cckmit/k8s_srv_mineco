package com.egoveris.sharedsecurity.base.model.reparticion;

import java.io.Serializable;
import java.util.Date;

import com.egoveris.sharedsecurity.base.model.EstructuraDTO;

public class ReparticionDTO implements Serializable {

  private static final long serialVersionUID = 2515824296595049149L;

  private Integer id;
  private String codigo;
  private String codigoReparticionInterna;
  private String nombre;
  private Date vigenciaDesde;
  private Date vigenciaHasta;
  private String calleReparticion;
  private String numero;
  private String piso;
  private String oficina;
  private String telefono;
  private String fax;
  private String email;
  private EstructuraDTO estructura;
  private String enRed;
  private String sectorMesa;
  private Integer version;
  private Date fechaCreacion;
  private String usuarioCreacion;
  private Date fechaModificacion;
  private String usuarioModificacion;
  private Boolean estadoRegistro;
  private Boolean esDgtal;
  private ReparticionDTO repPadre;
  private Boolean esPadre = false;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getCodigo() {
    return codigo;
  }

  public void setCodigo(String codigoReparticion) {
    this.codigo = codigoReparticion;
  }

  public String getCodigoReparticionInterna() {
    return codigoReparticionInterna;
  }

  public void setCodigoReparticionInterna(String codigoReparticionInterna) {
    this.codigoReparticionInterna = codigoReparticionInterna;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
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

  public String getCalleReparticion() {
    return calleReparticion;
  }

  public void setCalleReparticion(String calleReparticion) {
    this.calleReparticion = calleReparticion;
  }

  public String getNumero() {
    return numero;
  }

  public void setNumero(String numero) {
    this.numero = numero;
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

  public EstructuraDTO getEstructura() {
    return estructura;
  }

  public void setEstructura(EstructuraDTO estructura) {
    this.estructura = estructura;
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

  public Integer getVersion() {
    return version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  public Date getFechaCreacion() {
    return fechaCreacion;
  }

  public void setFechaCreacion(Date fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }

  public String getUsuarioCreacion() {
    return usuarioCreacion;
  }

  public void setUsuarioCreacion(String usuarioCreacion) {
    this.usuarioCreacion = usuarioCreacion;
  }

  public Date getFechaModificacion() {
    return fechaModificacion;
  }

  public void setFechaModificacion(Date fechaModificacion) {
    this.fechaModificacion = fechaModificacion;
  }

  public String getUsuarioModificacion() {
    return usuarioModificacion;
  }

  public void setUsuarioModificacion(String usuarioModificacion) {
    this.usuarioModificacion = usuarioModificacion;
  }

  public Boolean getEstadoRegistro() {
    return estadoRegistro;
  }

  public void setEstadoRegistro(Boolean estadoRegistro) {
    this.estadoRegistro = estadoRegistro;
  }

  public Boolean getEsDgtal() {
    return esDgtal;
  }

  public void setEsDgtal(Boolean esDgtal) {
    this.esDgtal = esDgtal;
  }

  public ReparticionDTO getRepPadre() {
    return repPadre;
  }

  public void setRepPadre(ReparticionDTO repPadre) {
    this.repPadre = repPadre;
  }

  public Boolean getEsPadre() {
		return esPadre;
	}

	public void setEsPadre(Boolean esPadre) {
		this.esPadre = esPadre;
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
    ReparticionDTO other = (ReparticionDTO) obj;
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
    builder.append("ReparticionDTO [id=").append(id).append(", codigoReparticion=")
        .append(codigo).append(", codigoReparticionInterna=").append(", vigenciaDesde=")
        .append(vigenciaDesde).append(", vigenciaHasta=").append(vigenciaHasta)
        .append(", calleReparticion=").append(calleReparticion).append(", numero=").append(numero)
        .append(", piso=").append(piso).append(", oficina=").append(oficina).append(", telefono=")
        .append(telefono).append(", fax=").append(fax).append(", email=").append(email)
        .append(", estructura=").append(estructura).append(", enRed=").append(enRed)
        .append(", sectorMesa=").append(sectorMesa).append(", version=").append(version)
        .append(", fechaCreacion=").append(fechaCreacion).append(", usuarioCreacion=")
        .append(usuarioCreacion).append(", fechaModificacion=").append(fechaModificacion)
        .append(", usuarioModificacion=").append(usuarioModificacion).append(", estadoRegistro=")
        .append(estadoRegistro).append(", esDgtal=").append(esDgtal).append("]");
    return builder.toString();
  }

}
