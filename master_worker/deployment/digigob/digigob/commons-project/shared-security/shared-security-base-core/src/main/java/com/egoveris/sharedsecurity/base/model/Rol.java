package com.egoveris.sharedsecurity.base.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "EDT_ROL")
public class Rol implements Serializable {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1779817968672572851L;
	
	private Integer id;
  private String rolNombre;
  private String usuarioCreacion;
  private Date fechaCreacion;
  private Boolean esVigente;
  private String usuarioModificacion;
  private Date fechaModificacion;
  private List<Permiso> listaPermisos;


  private Set<DatosUsuarioRol> usuarios;
  
  @Id
  @Column(name = "EDTRL_ID")
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @Column(name = "EDTRL_ROL")
  public String getRolNombre() {
    return rolNombre;
  }

  public void setRolNombre(String rolNombre) {
    this.rolNombre = rolNombre;
  }

  @Column(name = "EDTRL_USUARIO_CREACION")
  public String getUsuarioCreacion() {
    return usuarioCreacion;
  }

  public void setUsuarioCreacion(String usuarioCreacion) {
    this.usuarioCreacion = usuarioCreacion;
  }

  @Column(name = "EDTRL_FECHA_CREACION")
  public Date getFechaCreacion() {
    return fechaCreacion;
  }

  public void setFechaCreacion(Date fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }

  @Column(name = "EDTRL_VIGENTE")
  public Boolean getEsVigente() {
    return esVigente;
  }

  public void setEsVigente(Boolean esVigente) {
    this.esVigente = esVigente;
  }

  @Column(name = "EDTRL_USUARIO_MODIFICACION")
  public String getUsuarioModificacion() {
    return usuarioModificacion;
  }

  public void setUsuarioModificacion(String usuarioModificacion) {
    this.usuarioModificacion = usuarioModificacion;
  }

  @Column(name = "EDTRL_FECHA_MODIFICACION")
  public Date getFechaModificacion() {
    return fechaModificacion;
  }

  public void setFechaModificacion(Date fechaModificacion) {
    this.fechaModificacion = fechaModificacion;
  }

  @ManyToMany
  @JoinTable(name = "EDT_ROL_PERMISOS", joinColumns = @JoinColumn(name = "EDTRL_ID", referencedColumnName = "EDTRL_ID"), inverseJoinColumns = @JoinColumn(name = "SD_PERMISO_ID", referencedColumnName = "ID"))
  public List<Permiso> getListaPermisos() {
    return listaPermisos;
  }

  public void setListaPermisos(List<Permiso> listaPermisos) {
    this.listaPermisos = listaPermisos;
  }

  @OneToMany(mappedBy = "id.rol",
      cascade = CascadeType.ALL)
	public Set<DatosUsuarioRol> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Set<DatosUsuarioRol> usuarios) {
		this.usuarios = usuarios;
	}

	@Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((esVigente == null) ? 0 : esVigente.hashCode());
    result = prime * result + ((fechaCreacion == null) ? 0 : fechaCreacion.hashCode());
    result = prime * result + ((fechaModificacion == null) ? 0 : fechaModificacion.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((listaPermisos == null) ? 0 : listaPermisos.hashCode());
    result = prime * result + ((rolNombre == null) ? 0 : rolNombre.hashCode());
    result = prime * result + ((usuarioCreacion == null) ? 0 : usuarioCreacion.hashCode());
    result = prime * result + ((usuarioModificacion == null) ? 0 : usuarioModificacion.hashCode());
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
    Rol other = (Rol) obj;

    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (rolNombre == null) {
      if (other.rolNombre != null)
        return false;
    } else if (!rolNombre.equals(other.rolNombre))
      return false;
    if (usuarioCreacion == null) {
      if (other.usuarioCreacion != null)
        return false;
    } else if (!usuarioCreacion.equals(other.usuarioCreacion))
      return false;
    if (usuarioModificacion == null) {
      if (other.usuarioModificacion != null)
        return false;
    } else if (!usuarioModificacion.equals(other.usuarioModificacion))
      return false;
    return true;
  }

}