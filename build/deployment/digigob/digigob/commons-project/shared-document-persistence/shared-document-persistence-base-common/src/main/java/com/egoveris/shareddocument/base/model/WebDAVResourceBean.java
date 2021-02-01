/**
 * 
 */
package com.egoveris.shareddocument.base.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author jnorvert
 * 
 */
public class WebDAVResourceBean implements Serializable {

  /**
  * 
  */
  private static final long serialVersionUID = 6670372563200836655L;

  private String nombre;

  private String referencia;

  private byte[] archivo;

  private String mimeType;

  private boolean isDirectorio = false;

  private int size = 0;

  private Date fechaCreacion = null;

  private Date fechaModificacion = null;

  private String href = null;

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public boolean isDirectorio() {
    return isDirectorio;
  }

  public void setDirectorio(boolean directorio) {
    this.isDirectorio = directorio;
  }

  public Date getFechaCreacion() {
    return (Date) fechaCreacion.clone();
  }

  public void setFechaCreacion(Date fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }

  public Date getFechaModificacion() {
    return (Date) fechaModificacion.clone();
  }

  public void setFechaModificacion(Date fechaModificacion) {
    this.fechaModificacion = fechaModificacion;
  }

  public String getMimeType() {
    return mimeType;
  }

  /**
   * Retorna el nombre del recurso, sin codificar (por ejemplo retorna espacios
   * en vez de '%20')
   * 
   * @return
   */
  public byte[] getArchivo() {
    return archivo;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String documento) {
    this.nombre = documento;
  }

  /**
   * Retorna la url relativa al recurso, sin codificar (por ejemplo retorna
   * espacios en vez de '%20')
   * 
   * @return
   */
  public String getReferencia() {
    return referencia;
  }

  public void setReferencia(String referencia) {
    this.referencia = referencia;
  }

  public boolean equals(Object pObject) {
    boolean equals = false;
    if (pObject == null)
      return false;
    if (pObject.getClass().getName().equals(WebDAVResourceBean.class.getName())) {
      WebDAVResourceBean bean = (WebDAVResourceBean) pObject;
      equals = (new EqualsBuilder().append(this.getNombre(), bean.getNombre()).isEquals());
    }
    return equals;
  }

  public int hashCode() {
    return (new HashCodeBuilder().append(this.getNombre())).toHashCode();
  }

  public void setArchivo(byte[] responseBody) {
    this.archivo = responseBody;

  }

  public void setMimeType(String mimeType) {
    this.mimeType = mimeType;
  }

  public void setHref(String href) {
    this.href = href;
  }

  public String getHref() {
    return href;
  }
}
