package com.egoveris.te.base.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TipoOperacionDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private String codigo;
  private String codigoAux;
  private String nombre;
  private String descripcion;
  private boolean estado;
  private Long id;
  private Long workflow;
  private List<TipoOperacionDocDTO> tiposOpDocumento;
  private List<TipoOperacionOrganismoDTO> organismos;

  public String getCodigo() {
    return codigo;
  }

  public void setCodigo(String codigo) {
    this.codigo = codigo;
  }

  public String getCodigoAux() {
    return codigoAux;
  }

  public void setCodigoAux(String codigoAux) {
    this.codigoAux = codigoAux;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public boolean getEstado() {
    return estado;
  }

  public void setEstado(boolean estado) {
    this.estado = estado;
  }

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @param id
   *          the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @return the workflow
   */
  public Long getWorkflow() {
    return workflow;
  }

  /**
   * @param workflow
   *          the workflow to set
   */
  public void setWorkflow(Long workflow) {
    this.workflow = workflow;
  }

  public List<TipoOperacionDocDTO> getTiposOpDocumento() {
    if (tiposOpDocumento == null) {
      tiposOpDocumento = new ArrayList<>();
    }
    
    return tiposOpDocumento;
  }

  public void setTiposOpDocumento(List<TipoOperacionDocDTO> tiposOpDocumento) {
    this.tiposOpDocumento = tiposOpDocumento;
  }

  public List<TipoOperacionOrganismoDTO> getOrganismos() {
    if (organismos == null) {
      organismos = new ArrayList<>();
    }

    return organismos;
  }

  public void setOrganismos(List<TipoOperacionOrganismoDTO> organismos) {
    this.organismos = organismos;
  }

}
