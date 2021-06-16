package com.egoveris.deo.model.model;

import java.io.Serializable;

public class TipoDocumentoReducidoDTO implements Serializable {

  private static final long serialVersionUID = 5415321549098766495L;
  private Integer id;
  private String nombre;
  private String version;
  private String acronimo;
  private String descripcion;
  private Boolean esEspecial;
  private Boolean esManual;
  private Boolean esAutomatica;
  private Boolean tieneToken;
  private Boolean tieneTemplate;
  private Boolean permiteEmbebidos;
  private Boolean esNotificable;
  private Boolean esComunicable;
  private Integer tipoProduccion;
  private String estado;
  private Integer idTipoDocumentoSade;
  private String codigoTipoDocumentoSade;
  private Boolean esFirmaConjunta;
  private Boolean esConfidencial;
  private Boolean esOculto;
  private Boolean estaHabilitado;
  private Boolean esFirmaExterna;
  private Boolean tieneAviso;
  private Integer sizeImportado;
  private Boolean resultado;

  /**
   * Indica que pueden seguirse creando instancias de este tipo de documento.
   */
  public static final String ESTADO_ACTIVO = "ALTA";
  public static final String ESTADO_INACTIVO = "BAJA";

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

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

  public String getAcronimo() {
    return acronimo;
  }

  public void setAcronimo(String acronimo) {
    this.acronimo = acronimo;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public Boolean getEsEspecial() {
    return esEspecial;
  }

  public void setEsEspecial(Boolean esEspecial) {
    this.esEspecial = esEspecial;
  }

  public Boolean getTieneToken() {
    return tieneToken;
  }

  public void setTieneToken(Boolean tieneToken) {
    this.tieneToken = tieneToken;
  }

  public Boolean getTieneTemplate() {
    return tieneTemplate;
  }

  public void setTieneTemplate(Boolean tieneTemplate) {
    this.tieneTemplate = tieneTemplate;
  }

  public Boolean getPermiteEmbebidos() {
    return permiteEmbebidos;
  }

  public void setPermiteEmbebidos(Boolean permiteEmbebidos) {
    this.permiteEmbebidos = permiteEmbebidos;
  }

  public Boolean getEsNotificable() {
    return esNotificable;
  }

  public void setEsNotificable(Boolean esNotificable) {
    this.esNotificable = esNotificable;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public String getEstado() {
    return estado;
  }

  public Integer getIdTipoDocumentoSade() {
    return idTipoDocumentoSade;
  }

  public void setIdTipoDocumentoSade(Integer idTipoDocumentoSade) {
    this.idTipoDocumentoSade = idTipoDocumentoSade;
  }

  public String getCodigoTipoDocumentoSade() {
    return codigoTipoDocumentoSade;
  }

  public void setCodigoTipoDocumentoSade(String codigoTipoDocumentoSade) {
    this.codigoTipoDocumentoSade = codigoTipoDocumentoSade;
  }

  public Boolean getEsFirmaConjunta() {
    return esFirmaConjunta;
  }

  public void setEsFirmaConjunta(Boolean esFirmaConjunta) {
    this.esFirmaConjunta = esFirmaConjunta;
  }

  public Boolean getEsConfidencial() {
    return esConfidencial;
  }

  public void setEsConfidencial(Boolean esConfidencial) {
    this.esConfidencial = esConfidencial;
  }

  public Boolean getEstaHabilitado() {
    setEstaHabilitado(getEstado().compareTo(ESTADO_ACTIVO) == 0);
    return estaHabilitado;
  }

  public void setEstaHabilitado(Boolean estaHabilitado) {
    this.estaHabilitado = estaHabilitado;
  }

  public Boolean getEsManual() {
    return esManual;
  }

  public void setEsManual(Boolean esManual) {
    this.esManual = esManual;
  }

  public Boolean getEsAutomatica() {
    return esAutomatica;
  }

  public void setEsAutomatica(Boolean esAutomatica) {
    this.esAutomatica = esAutomatica;
  }

  public void setEsFirmaExterna(Boolean esFirmaExterna) {
    this.esFirmaExterna = esFirmaExterna;
  }

  public Boolean getEsFirmaExterna() {
    return esFirmaExterna;
  }

  public Boolean getTieneAviso() {
    return tieneAviso;
  }

  public void setTieneAviso(Boolean tieneAviso) {
    this.tieneAviso = tieneAviso;
  }

  public Integer getTipoProduccion() {
    return tipoProduccion;
  }

  public void setTipoProduccion(Integer tipoProduccion) {
    this.tipoProduccion = tipoProduccion;
  }

  public void setSizeImportado(Integer sizeImportado) {
    this.sizeImportado = sizeImportado;
  }

  public Integer getSizeImportado() {
    return sizeImportado;
  }

  public void setEsOculto(Boolean esOculto) {
    this.esOculto = esOculto;
  }

  public Boolean getEsOculto() {
    return esOculto;
  }

  public Boolean getEsComunicable() {
    return esComunicable;
  }

  public void setEsComunicable(Boolean esComunicable) {
    this.esComunicable = esComunicable;
  }

  public Boolean getResultado() {
    return resultado;
  }

  public void setResultado(Boolean resultado) {
    this.resultado = resultado;
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((acronimo == null) ? 0 : acronimo.hashCode());
    result = prime * result
        + ((codigoTipoDocumentoSade == null) ? 0 : codigoTipoDocumentoSade.hashCode());
    result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
    result = prime * result + ((esAutomatica == null) ? 0 : esAutomatica.hashCode());
    result = prime * result + ((esComunicable == null) ? 0 : esComunicable.hashCode());
    result = prime * result + ((esConfidencial == null) ? 0 : esConfidencial.hashCode());
    result = prime * result + ((esEspecial == null) ? 0 : esEspecial.hashCode());
    result = prime * result + ((esFirmaConjunta == null) ? 0 : esFirmaConjunta.hashCode());
    result = prime * result + ((esFirmaExterna == null) ? 0 : esFirmaExterna.hashCode());
    result = prime * result + ((esManual == null) ? 0 : esManual.hashCode());
    result = prime * result + ((esNotificable == null) ? 0 : esNotificable.hashCode());
    result = prime * result + ((esOculto == null) ? 0 : esOculto.hashCode());
    result = prime * result + ((estaHabilitado == null) ? 0 : estaHabilitado.hashCode());
    result = prime * result + ((estado == null) ? 0 : estado.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((idTipoDocumentoSade == null) ? 0 : idTipoDocumentoSade.hashCode());
    result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
    result = prime * result + ((permiteEmbebidos == null) ? 0 : permiteEmbebidos.hashCode());
    result = prime * result + ((sizeImportado == null) ? 0 : sizeImportado.hashCode());
    result = prime * result + ((tieneAviso == null) ? 0 : tieneAviso.hashCode());
    result = prime * result + ((tieneTemplate == null) ? 0 : tieneTemplate.hashCode());
    result = prime * result + ((tieneToken == null) ? 0 : tieneToken.hashCode());
    result = prime * result + ((tipoProduccion == null) ? 0 : tipoProduccion.hashCode());
    result = prime * result + ((version == null) ? 0 : version.hashCode());
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
    TipoDocumentoReducidoDTO other = (TipoDocumentoReducidoDTO) obj;
    if (acronimo == null) {
      if (other.acronimo != null)
        return false;
    } else if (!acronimo.equals(other.acronimo))
      return false;
    if (codigoTipoDocumentoSade == null) {
      if (other.codigoTipoDocumentoSade != null)
        return false;
    } else if (!codigoTipoDocumentoSade.equals(other.codigoTipoDocumentoSade))
      return false;
    if (descripcion == null) {
      if (other.descripcion != null)
        return false;
    } else if (!descripcion.equals(other.descripcion))
      return false;
    if (esAutomatica == null) {
      if (other.esAutomatica != null)
        return false;
    } else if (!esAutomatica.equals(other.esAutomatica))
      return false;
    if (esComunicable == null) {
      if (other.esComunicable != null)
        return false;
    } else if (!esComunicable.equals(other.esComunicable))
      return false;
    if (esConfidencial == null) {
      if (other.esConfidencial != null)
        return false;
    } else if (!esConfidencial.equals(other.esConfidencial))
      return false;
    if (esEspecial == null) {
      if (other.esEspecial != null)
        return false;
    } else if (!esEspecial.equals(other.esEspecial))
      return false;
    if (esFirmaConjunta == null) {
      if (other.esFirmaConjunta != null)
        return false;
    } else if (!esFirmaConjunta.equals(other.esFirmaConjunta))
      return false;
    if (esFirmaExterna == null) {
      if (other.esFirmaExterna != null)
        return false;
    } else if (!esFirmaExterna.equals(other.esFirmaExterna))
      return false;
    if (esManual == null) {
      if (other.esManual != null)
        return false;
    } else if (!esManual.equals(other.esManual))
      return false;
    if (esNotificable == null) {
      if (other.esNotificable != null)
        return false;
    } else if (!esNotificable.equals(other.esNotificable))
      return false;
    if (esOculto == null) {
      if (other.esOculto != null)
        return false;
    } else if (!esOculto.equals(other.esOculto))
      return false;
    if (estaHabilitado == null) {
      if (other.estaHabilitado != null)
        return false;
    } else if (!estaHabilitado.equals(other.estaHabilitado))
      return false;
    if (estado == null) {
      if (other.estado != null)
        return false;
    } else if (!estado.equals(other.estado))
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (idTipoDocumentoSade == null) {
      if (other.idTipoDocumentoSade != null)
        return false;
    } else if (!idTipoDocumentoSade.equals(other.idTipoDocumentoSade))
      return false;
    if (nombre == null) {
      if (other.nombre != null)
        return false;
    } else if (!nombre.equals(other.nombre))
      return false;
    if (permiteEmbebidos == null) {
      if (other.permiteEmbebidos != null)
        return false;
    } else if (!permiteEmbebidos.equals(other.permiteEmbebidos))
      return false;
    if (sizeImportado == null) {
      if (other.sizeImportado != null)
        return false;
    } else if (!sizeImportado.equals(other.sizeImportado))
      return false;
    if (tieneAviso == null) {
      if (other.tieneAviso != null)
        return false;
    } else if (!tieneAviso.equals(other.tieneAviso))
      return false;
    if (tieneTemplate == null) {
      if (other.tieneTemplate != null)
        return false;
    } else if (!tieneTemplate.equals(other.tieneTemplate))
      return false;
    if (tieneToken == null) {
      if (other.tieneToken != null)
        return false;
    } else if (!tieneToken.equals(other.tieneToken))
      return false;
    if (tipoProduccion == null) {
      if (other.tipoProduccion != null)
        return false;
    } else if (!tipoProduccion.equals(other.tipoProduccion))
      return false;
    if (version == null) {
      if (other.version != null)
        return false;
    } else if (!version.equals(other.version))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "TipoDocumentoReducidoDTO [id=" + id + ", nombre=" + nombre + ", version=" + version
        + ", acronimo=" + acronimo + ", descripcion=" + descripcion + ", esEspecial=" + esEspecial
        + ", esManual=" + esManual + ", esAutomatica=" + esAutomatica + ", tieneToken="
        + tieneToken + ", tieneTemplate=" + tieneTemplate + ", permiteEmbebidos="
        + permiteEmbebidos + ", esNotificable=" + esNotificable + ", esComunicable="
        + esComunicable + ", tipoProduccion=" + tipoProduccion + ", estado=" + estado
        + ", idTipoDocumentoSade=" + idTipoDocumentoSade + ", codigoTipoDocumentoSade="
        + codigoTipoDocumentoSade + ", esFirmaConjunta=" + esFirmaConjunta + ", esConfidencial="
        + esConfidencial + ", esOculto=" + esOculto + ", estaHabilitado=" + estaHabilitado
        + ", esFirmaExterna=" + esFirmaExterna + ", tieneAviso=" + tieneAviso + ", sizeImportado="
        + sizeImportado + "]";
  }

}
