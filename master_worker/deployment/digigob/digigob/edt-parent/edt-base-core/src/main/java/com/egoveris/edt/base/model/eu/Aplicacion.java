/**
 * 
 */
package com.egoveris.edt.base.model.eu;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.egoveris.edt.base.model.eu.novedad.Novedad;

/**
 * @author pfolgar
 * 
 */

@Entity
@Table(name = "EU_APLICACION")
public class Aplicacion {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer idAplicacion;

  @Column(name = "nombre", nullable = false)
  private String nombreAplicacion;

  @Column(name = "descripcion", nullable = false, unique = true)
  private String descripcionAplicacion;

  @Column(name = "urlAplicacion", nullable = false)
  private String urlAplicacion;

  @Column(name = "urlAplicacionValidacion", nullable = false)
  private String urlAplicacionValidacion;

  @Column(name = "urlAplicacionInbox")
  private String urlAplicacionInbox;

  @Column(name = "urlAplicacionInboxSupervisado")
  private String urlAplicacionInboxSupervisado;

  @Column(name = "urlAplicacionBuzon")
  private String urlAplicacionBuzon;

  @Column(name = "VISIBLEMISTAREAS", nullable = false)
  private boolean visibleMisTareas;

  @Column(name = "VISIBLEMISSISTEMAS", nullable = false)
  private boolean visibleMisSistemas;

  @Column(name = "VISIBLEMISSUPERVISADOS", nullable = false)
  private boolean visibleMisSupervisados;

  @Column(name = "VISIBLEBUZONGRUPAL", nullable = false)
  private boolean visibleBuzonGrupal;

  @Column(name = "VISIBLE", nullable = false)
  private boolean visible;

  @ManyToMany
  @JoinTable(name = "EU_NOVEDAD_APLICACION", joinColumns = {
      @JoinColumn(name = "APLICACION_ID", nullable = false) }, inverseJoinColumns = {
          @JoinColumn(name = "NOVEDAD_ID", nullable = false) })
  private Set<Novedad> novedades;

  public Integer getIdAplicacion() {
    return idAplicacion;
  }

  public void setIdAplicacion(Integer idAplicacion) {
    this.idAplicacion = idAplicacion;
  }

  public String getNombreAplicacion() {
    return nombreAplicacion;
  }

  public void setNombreAplicacion(String nombreAplicacion) {
    this.nombreAplicacion = nombreAplicacion;
  }

  public String getDescripcionAplicacion() {
    return descripcionAplicacion;
  }

  public void setDescripcionAplicacion(String descripcionAplicacion) {
    this.descripcionAplicacion = descripcionAplicacion;
  }

  public String getUrlAplicacion() {
    return urlAplicacion;
  }

  public void setUrlAplicacion(String urlAplicacion) {
    this.urlAplicacion = urlAplicacion;
  }

  public String getUrlAplicacionValidacion() {
    return urlAplicacionValidacion;
  }

  public void setUrlAplicacionValidacion(String urlAplicacionValidacion) {
    this.urlAplicacionValidacion = urlAplicacionValidacion;
  }

  public String getUrlAplicacionInbox() {
    return urlAplicacionInbox;
  }

  public void setUrlAplicacionInbox(String urlAplicacionInbox) {
    this.urlAplicacionInbox = urlAplicacionInbox;
  }

  public String getUrlAplicacionInboxSupervisado() {
    return urlAplicacionInboxSupervisado;
  }

  public void setUrlAplicacionInboxSupervisado(String urlAplicacionInboxSupervisado) {
    this.urlAplicacionInboxSupervisado = urlAplicacionInboxSupervisado;
  }

  public String getUrlAplicacionBuzon() {
    return urlAplicacionBuzon;
  }

  public void setUrlAplicacionBuzon(String urlAplicacionBuzon) {
    this.urlAplicacionBuzon = urlAplicacionBuzon;
  }

  public boolean isVisibleMisTareas() {
    return visibleMisTareas;
  }

  public void setVisibleMisTareas(boolean visibleMisTareas) {
    this.visibleMisTareas = visibleMisTareas;
  }

  public boolean isVisibleMisSistemas() {
    return visibleMisSistemas;
  }

  public void setVisibleMisSistemas(boolean visibleMisSistemas) {
    this.visibleMisSistemas = visibleMisSistemas;
  }

  public boolean isVisibleMisSupervisados() {
    return visibleMisSupervisados;
  }

  public void setVisibleMisSupervisados(boolean visibleMisSupervisados) {
    this.visibleMisSupervisados = visibleMisSupervisados;
  }

  public boolean isVisibleBuzonGrupal() {
    return visibleBuzonGrupal;
  }

  public void setVisibleBuzonGrupal(boolean visibleBuzonGrupal) {
    this.visibleBuzonGrupal = visibleBuzonGrupal;
  }

  public boolean isVisible() {
    return visible;
  }

  public void setVisible(boolean visible) {
    this.visible = visible;
  }

  public Set<Novedad> getNovedades() {
    return novedades;
  }

  public void setNovedades(Set<Novedad> novedades) {
    this.novedades = novedades;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof AplicacionDTO)) {
      return false;
    }

    Aplicacion other = (Aplicacion) o;

    if (idAplicacion == other.getIdAplicacion()) {
      return true;
    }
    if (idAplicacion == null) {
      return false;
    }

    return idAplicacion.equals(other.getIdAplicacion());
  }

  @Override
  public int hashCode() {
    if (idAplicacion != null) {
      return idAplicacion.hashCode();
    } else {
      return super.hashCode();
    }
  }

}