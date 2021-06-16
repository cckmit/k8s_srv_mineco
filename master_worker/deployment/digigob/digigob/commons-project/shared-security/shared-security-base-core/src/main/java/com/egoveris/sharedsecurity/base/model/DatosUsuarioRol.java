package com.egoveris.sharedsecurity.base.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "edt_usuario_rol")

@AssociationOverrides({
  @AssociationOverride(name = "id.datosUsuario",
      joinColumns = @JoinColumn(name = "datos_usuario_id")),
  @AssociationOverride(name = "id.rol",
      joinColumns = @JoinColumn(name = "rol_id")) })
public class DatosUsuarioRol implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2041293564584200480L;


	@EmbeddedId
	private DatosUsuarioRolId id;
	
	
	@Column(name = "usuario_modificador")
	private String usuarioModificacion;
	
	@Column(name = "fecha_vinculacion")
	private Date fechaVinculacion;

	
	public DatosUsuarioRol() {
		super();
		this.id = new DatosUsuarioRolId();
	}
	
  public DatosUsuarioRol(DatosUsuario datosUsuario,Rol rol  ,String usuarioModificacion) {
		
  	super();
		this.id = new DatosUsuarioRolId();
		setDatosUsuario(datosUsuario);
		setRol(rol);
		this.usuarioModificacion = usuarioModificacion;
		this.fechaVinculacion = new Date();
		
  }

	@Transient
  public DatosUsuario getDatosUsuario() {
      return getId().getDatosUsuario();
  }
  
  public void setDatosUsuario(DatosUsuario datosUsuario) {
  	getId().setDatosUsuario(datosUsuario);
  }
  
  public void setRol(Rol rol) {
  	getId().setRol(rol);
  }
  
  @Transient
  public Rol getRol() {
      return getId().getRol();
  }

	public DatosUsuarioRolId getId() {
		return id;
	}

	public void setId(DatosUsuarioRolId id) {
		this.id = id;
	}

	public String getUsuarioModificacion() {
		return usuarioModificacion;
	}

	public void setUsuarioModificacion(String usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}

	public Date getFechaVinculacion() {
		return fechaVinculacion;
	}

	public void setFechaVinculacion(Date fechaVinculacion) {
		this.fechaVinculacion = fechaVinculacion;
	}
  
  
}
