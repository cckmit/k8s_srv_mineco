package com.egoveris.sharedorganismo.base.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author agambina
 * 
 */

public class ReparticionBean implements Serializable {

  private static final long serialVersionUID = -528665397204039343L;

  private String nombre;
  private String codigo;
  private Long id;
  private Integer idEstructura;
  private String codDgtal;
  private String estadoRegistro;
  private Date vigenciaDesde;
  private Date vigenciaHasta;
  private Integer esDigital;
  private Integer idPadre;

  @Deprecated
  private String usuario;
  @Deprecated
  private String proceso;
  @Deprecated
  private boolean vigente;

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getNombre() {
    return nombre;
  }

  public void setCodigo(String codigo) {
    this.codigo = codigo;
  }

  public String getCodigo() {
    return codigo;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String toString() {
    return this.codigo;
  }

  /**
   * @return the usuario
   */
  @Deprecated
  public String getUsuario() {
    return usuario;
  }

  public String getCodDgtal() {
    return codDgtal;
  }

  public void setCodDgtal(String codDgtal) {
    this.codDgtal = codDgtal;
  }

  /**
   * @param usuario
   *          the usuario to set
   */
  @Deprecated
  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  /**
   * @return the proceso
   */
  @Deprecated
  public String getProceso() {
    return proceso;
  }

  /**
   * @param proceso
   *          the proceso to set
   */
  @Deprecated
  public void setProceso(String proceso) {
    this.proceso = proceso;
  }

  /**
   * @return the vigente
   */
  @Deprecated
  public boolean isVigente() {
    return vigente;
  }

  /**
   * @param vigente
   *          the vigente to set
   */
  @Deprecated
  public void setVigente(boolean vigente) {
    this.vigente = vigente;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
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
    ReparticionBean other = (ReparticionBean) obj;
    if (codigo == null) {
      if (other.codigo != null)
        return false;
    } else if (!codigo.trim().equals(other.codigo.trim()))
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

  public void setEstadoRegistro(String estadoRegistro) {
    this.estadoRegistro = estadoRegistro;
  }

  public String getEstadoRegistro() {
    return estadoRegistro;
  }

  public void setVigenciaDesde(Date vigenciaDesde) {
    this.vigenciaDesde = vigenciaDesde;
  }

  public Date getVigenciaDesde() {
    return vigenciaDesde;
  }

  public void setVigenciaHasta(Date vigenciaHasta) {
    this.vigenciaHasta = vigenciaHasta;
  }

  public Date getVigenciaHasta() {
    return vigenciaHasta;
  }

  /**
   * @return the esDigital
   */
  public Integer getEsDigital() {
    return esDigital;
  }

  /**
   * @param esDigital
   *          the esDigital to set
   */
  public void setEsDigital(Integer esDigital) {
    this.esDigital = esDigital;
  }

  public boolean isVigenteActiva() {
    Date now = new Date();
    if ((now.after(this.getVigenciaDesde())) && (now.before(this.getVigenciaHasta()))) {
      if (this.getEstadoRegistro().trim().equals("1")) {
        return true;
      }
    }
    return false;

  }

  public void setIdEstructura(Integer idEstructura) {
    this.idEstructura = idEstructura;
  }

  public Integer getIdEstructura() {
    return idEstructura;
  }

  public void setIdPadre(Integer idPadre) {
    this.idPadre = idPadre;
  }

  public Integer getIdPadre() {
    return idPadre;
  }

}
