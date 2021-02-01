package com.egoveris.edt.base.model.eu.usuario;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.egoveris.sharedsecurity.base.model.PermisoDTO;

public class RolDTO implements Serializable{

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
  private String rolNombre;
  private String usuarioCreacion;
  private Date fechaCreacion;
  private Boolean esVigente;
  private String usuarioModificacion;
  private Date fechaModificacion;
  private List<PermisoDTO> listaPermisos;
  
  private Set<DatosUsuarioRolDTO> usuarios = new HashSet<>();

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getRolNombre() {
    return rolNombre;
  }

  public void setRolNombre(String rolNombre) {
    this.rolNombre = rolNombre;
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

  public Boolean getEsVigente() {
    return esVigente;
  }

  public void setEsVigente(Boolean esVigente) {
    this.esVigente = esVigente;
  }

  public String getUsuarioModificacion() {
    return usuarioModificacion;
  }

  public void setUsuarioModificacion(String usuarioModificacion) {
    this.usuarioModificacion = usuarioModificacion;
  }

  public Date getFechaModificacion() {
    return fechaModificacion;
  }

  public void setFechaModificacion(Date fechaModificacion) {
    this.fechaModificacion = fechaModificacion;
  }

  public List<PermisoDTO> getListaPermisos() {
    return listaPermisos;
  }

  public void setListaPermisos(List<PermisoDTO> listaPermisos) {
    this.listaPermisos = listaPermisos;
  }


	public Set<DatosUsuarioRolDTO> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Set<DatosUsuarioRolDTO> usuarios) {
		this.usuarios = usuarios;
	}

	@Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("RolDTO [id=").append(id).append(", rolNombre=").append(rolNombre)
        .append(", usuarioCreacion=").append(usuarioCreacion).append(", fechaCreacion=")
        .append(fechaCreacion).append(", esVigente=").append(esVigente)
        .append(", usuarioModificacion=").append(usuarioModificacion)
        .append(", fechaModificacion=").append(fechaModificacion).append(", listaPermisos=")
        .append(listaPermisos).append("]");
    return builder.toString();
  }

}
