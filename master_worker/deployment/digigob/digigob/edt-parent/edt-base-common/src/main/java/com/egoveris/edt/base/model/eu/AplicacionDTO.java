package com.egoveris.edt.base.model.eu;

import com.egoveris.edt.base.model.eu.novedad.NovedadDTO;

import java.io.Serializable;
import java.util.Set;

public class AplicacionDTO implements Serializable {

  private static final long serialVersionUID = -8602650171133752607L;

  private Integer idAplicacion;
  private String nombreAplicacion;
  private String descripcionAplicacion;
  private String urlAplicacion;
  private String urlAplicacionValidacion;
  private String urlAplicacionInbox;
  private String urlAplicacionInboxSupervisado;
  private String urlAplicacionBuzon;
  private boolean visibleMisTareas;
  private boolean visibleMisSistemas;
  private boolean visibleMisSupervisados;
  private boolean visibleBuzonGrupal;
  private boolean visible;
  private Set<NovedadDTO> novedades;

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

  public Set<NovedadDTO> getNovedades() {
    return novedades;
  }

  public void setNovedades(Set<NovedadDTO> novedades) {
    this.novedades = novedades;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("AplicacionDTO [idAplicacion=").append(idAplicacion)
        .append(", nombreAplicacion=").append(nombreAplicacion).append(", descripcionAplicacion=")
        .append(descripcionAplicacion).append(", urlAplicacion=").append(urlAplicacion)
        .append(", urlAplicacionValidacion=").append(urlAplicacionValidacion)
        .append(", urlAplicacionInbox=").append(urlAplicacionInbox)
        .append(", urlAplicacionInboxSupervisado=").append(urlAplicacionInboxSupervisado)
        .append(", urlAplicacionBuzon=").append(urlAplicacionBuzon).append(", visibleMisTareas=")
        .append(visibleMisTareas).append(", visibleMisSistemas=").append(visibleMisSistemas)
        .append(", visibleMisSupervisados=").append(visibleMisSupervisados)
        .append(", visibleBuzonGrupal=").append(visibleBuzonGrupal).append(", visible=")
        .append(visible).append(", novedades=").append(novedades).append("]");
    return builder.toString();
  }

}