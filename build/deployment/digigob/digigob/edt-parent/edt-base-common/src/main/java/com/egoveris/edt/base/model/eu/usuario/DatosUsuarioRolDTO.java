package com.egoveris.edt.base.model.eu.usuario;

import java.io.Serializable;
import java.util.Date;



public class DatosUsuarioRolDTO implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -7160714644298100853L;


	private DatosUsuarioRolIdDTO id;
	
	private String usuarioModificacion;
	
	private Date fechaVinculacion;
	
	private boolean agregar;

	
	public DatosUsuarioRolDTO() {
		super();
		this.id = new DatosUsuarioRolIdDTO();
		this.agregar = false;
	}
	
  public DatosUsuarioRolDTO(DatosUsuarioDTO datosUsuario,RolDTO rol  ,String usuarioModificacion) {
		
  	super();
		this.id = new DatosUsuarioRolIdDTO();
		setDatosUsuario(datosUsuario);
		setRol(rol);
		this.usuarioModificacion = usuarioModificacion;
		this.fechaVinculacion = new Date();
		
  }

  public DatosUsuarioDTO getDatosUsuario() {
      return getId().getDatosUsuario();
  }
  
  public void setDatosUsuario(DatosUsuarioDTO datosUsuario) {
  	getId().setDatosUsuario(datosUsuario);
  }
  
  public void setRol(RolDTO rol) {
  	getId().setRol(rol);
  }
  

  public RolDTO getRol() {
      return getId().getRol();
  }

	public DatosUsuarioRolIdDTO getId() {
		return id;
	}

	public void setId(DatosUsuarioRolIdDTO id) {
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

	public boolean getAgregar() {
		return agregar;
	}

	public void setAgregar(boolean agregar) {
		this.agregar = agregar;
	}

}
