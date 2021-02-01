package com.egoveris.edt.base.model.eu.novedad;

import com.egoveris.edt.base.model.eu.AplicacionDTO;
import com.egoveris.edt.base.model.eu.CategoriaDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class NovedadDTO implements Serializable {

  private static final long serialVersionUID = 314824581304632017L;

  private Integer id;
  private String novedad;
  private Set<AplicacionDTO> aplicaciones;
  private CategoriaDTO categoria;
  private Date fechaInicio;
  private Date fechaFin;
  private Date fechaModificacion;
  private String usuario;
  private String estado;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getNovedad() {
    return novedad;
  }

  public void setNovedad(String novedad) {
    this.novedad = novedad;
  }

  public Set<AplicacionDTO> getAplicaciones() {
    return aplicaciones;
  }

  /**
   * Gets the aplicaciones text.
   *
   * @return the aplicaciones text
   */
  public String getAplicacionesText() {
    StringBuilder sb = new StringBuilder();

    List<String> aplicacionesTexto = new ArrayList<>();

    for (AplicacionDTO aplicacion : aplicaciones) {
      aplicacionesTexto.add(aplicacion.getNombreAplicacion());
    }

    SortedSet<String> sortedNames = new TreeSet<>();
    sortedNames.addAll(aplicacionesTexto);

    for (String app : sortedNames) {
      sb.append(app);
      if (sortedNames.size() != 1) {
        sb.append(" - ");
      }
    }

    return sb.toString();
  }

  public void setAplicaciones(Set<AplicacionDTO> aplicaciones) {
    this.aplicaciones = aplicaciones;
  }

  public CategoriaDTO getCategoria() {
    return categoria;
  }

  public void setCategoria(CategoriaDTO categoria) {
    this.categoria = categoria;
  }

  public Date getFechaInicio() {
    return fechaInicio;
  }

  public void setFechaInicio(Date fechaInicio) {
    this.fechaInicio = fechaInicio;
  }

  public Date getFechaFin() {
    return fechaFin;
  }

  public void setFechaFin(Date fechaFin) {
    this.fechaFin = fechaFin;
  }

  public Date getFechaModificacion() {
    return fechaModificacion;
  }

  public void setFechaModificacion(Date fechaModificacion) {
    this.fechaModificacion = fechaModificacion;
  }

  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((novedad == null) ? 0 : novedad.hashCode());
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
    NovedadDTO other = (NovedadDTO) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (novedad == null) {
      if (other.novedad != null)
        return false;
    } else if (!novedad.equals(other.novedad))
      return false;
    return true;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("NovedadDTO [id=").append(id).append(", novedad=").append(novedad)
        .append(", categoria=").append(categoria).append(", fechaInicio=").append(fechaInicio)
        .append(", fechaFin=").append(fechaFin).append(", fechaModificacion=")
        .append(fechaModificacion).append(", usuario=").append(usuario).append(", estado=")
        .append(estado).append("]");
    return builder.toString();
  }

}