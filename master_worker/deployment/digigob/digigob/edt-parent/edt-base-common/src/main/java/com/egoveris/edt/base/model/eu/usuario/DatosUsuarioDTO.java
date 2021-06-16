package com.egoveris.edt.base.model.eu.usuario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.egoveris.sharedsecurity.base.model.TipoRevisionEnum;
import com.egoveris.sharedsecurity.base.model.cargo.CargoDTO;
import com.egoveris.sharedsecurity.base.model.sector.SectorDTO;

public class DatosUsuarioDTO implements Serializable {

  private static final long serialVersionUID = -2589562244224528675L;
  public static final String ESSECRETARIO = "S";
  public static final String NOESSECRETARIO = "N";

  private Integer id;
  private String mail;
  private String ocupacion;
  private String usuarioAsesor;
  private String usuario;
  private String userSuperior;
  private String apellidoYNombre;
  
  private String primerNombre;
  
  private String segundoNombre;
  
  private String tercerNombre;
  
  private String primerApellido;
  
  private String segundoApellido;
  
  private String tercerApellido;
  
  private String numeroCuit;
  private Boolean aceptacionTyC;
  private String codigoSectorInterno;
  private Date fechaCaducidadSectorInterno;
  private String secretario;
  private String esSecretario;
  private Integer idSectorInterno;
  private Boolean cambiarMesa;
  private CargoDTO cargoAsignado;
  private Date fechaRevision;
  private String tipoRevision;
  private SectorDTO sector;
  
  private Set<DatosUsuarioRolDTO> roles = new HashSet<>();

  public DatosUsuarioDTO() {
  }

  public DatosUsuarioDTO(String username) {
    this.usuario = username;
  }

  public void removeRol(RolDTO rol) {
    for (Iterator<DatosUsuarioRolDTO> iterator = roles.iterator();
         iterator.hasNext(); ) {
    		DatosUsuarioRolDTO usuarioRol = iterator.next();

        if (usuarioRol.getDatosUsuario().equals(this) &&
                usuarioRol.getRol().equals(rol)) {
            iterator.remove();
            usuarioRol.setDatosUsuario(null);
            usuarioRol.setRol(null);
        }
    }
  }
  
  public List<String> obtenerLosPermisos(){
  	Set<String> permisos = new HashSet<>();
  	
   this.roles.forEach(rolUser -> 
  	 rolUser.getRol().getListaPermisos()
  	 .forEach(per -> 
  		 	permisos.add(per.getPermiso())
  	 )
   );
  	
  	return new ArrayList<>(permisos);
  }
  
  
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getMail() {
    return mail;
  }

  public void setMail(String mail) {
    this.mail = mail;
  }

  public String getOcupacion() {
    return ocupacion;
  }

  public void setOcupacion(String ocupacion) {
    this.ocupacion = ocupacion;
  }

  /**
   * Gets the usuario asesor.
   *
   * @return the usuario asesor
   */
  public String getUsuarioAsesor() {
    if (this.usuarioAsesor == null) {
      return "";
    }
    return usuarioAsesor;
  }

  public void setUsuarioAsesor(String usuarioAsesor) {
    this.usuarioAsesor = usuarioAsesor;
  }

  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  public String getUserSuperior() {
    return userSuperior;
  }

  public void setUserSuperior(String userSuperior) {
    this.userSuperior = userSuperior;
  }

  public String getApellidoYNombre() {
  	
  	StringBuilder strBuilder = new StringBuilder();
  		
  			strBuilder.append(primerNombre)
  			.append(StringUtils.isNotBlank(segundoNombre) ?  " " + segundoNombre : StringUtils.EMPTY)
  			.append(StringUtils.isNotBlank(tercerNombre) ?  " " +  tercerNombre: StringUtils.EMPTY)
  			.append(StringUtils.isNotBlank(primerApellido) ?  " " + primerApellido: StringUtils.EMPTY)
  			.append(StringUtils.isNotBlank(segundoApellido) ?  " " + segundoApellido : StringUtils.EMPTY)
  			.append(StringUtils.isNotBlank(tercerApellido) ? " " + tercerApellido  : StringUtils.EMPTY);
  			
  	this.apellidoYNombre = strBuilder.toString();
  	
  	
    return apellidoYNombre;
  }

  public void setApellidoYNombre(String apellidoYNombre) {
    this.apellidoYNombre = apellidoYNombre;
  }

  public String getNumeroCuit() {
    return numeroCuit;
  }

  public void setNumeroCuit(String numeroCuit) {
    this.numeroCuit = numeroCuit;
  }

  public Boolean getAceptacionTyC() {
    return aceptacionTyC;
  }

  public void setAceptacionTyC(Boolean aceptacionTyC) {
    this.aceptacionTyC = aceptacionTyC;
  }

  public String getCodigoSectorInterno() {
    return codigoSectorInterno;
  }

  public void setCodigoSectorInterno(String codigoSectorInterno) {
    this.codigoSectorInterno = codigoSectorInterno;
  }

  public Date getFechaCaducidadSectorInterno() {
    return fechaCaducidadSectorInterno;
  }

  public void setFechaCaducidadSectorInterno(Date fechaCaducidadSectorInterno) {
    this.fechaCaducidadSectorInterno = fechaCaducidadSectorInterno;
  }

  public String getSecretario() {
    return secretario;
  }

  public void setSecretario(String secretario) {
    this.secretario = secretario;
  }

  public String getEsSecretario() {
    return esSecretario;
  }

  public void setEsSecretario(String esSecretario) {
    this.esSecretario = esSecretario;
  }

  public Integer getIdSectorInterno() {
    return idSectorInterno;
  }

  public void setIdSectorInterno(Integer idSectorInterno) {
    this.idSectorInterno = idSectorInterno;
  }

  public Boolean getCambiarMesa() {
    return cambiarMesa;
  }

  public void setCambiarMesa(Boolean cambiarMesa) {
    this.cambiarMesa = cambiarMesa;
  }

  public CargoDTO getCargoAsignado() {
    return cargoAsignado;
  }

  public void setCargoAsignado(CargoDTO cargoAsignado) {
    this.cargoAsignado = cargoAsignado;
  }

  public Date getFechaRevision() {
    return fechaRevision;
  }

  public void setFechaRevision(Date fechaRevision) {
    this.fechaRevision = fechaRevision;
  }

  /**
   * Gets the tipo revision.
   *
   * @return the tipo revision
   */
  public String getTipoRevision() {
    if ("ADD".equals(tipoRevision)) {
      this.tipoRevision = TipoRevisionEnum.AGREGADO.name();
    }
    if ("MOD".equals(tipoRevision)) {
      this.tipoRevision = TipoRevisionEnum.MODIFICADO.name();
    }
    if ("DEL".equals(tipoRevision)) {
      this.tipoRevision = TipoRevisionEnum.ELIMINADO.name();
    }
    return tipoRevision;
  }

  public void setTipoRevision(String tipoRevision) {
    this.tipoRevision = tipoRevision;
  }

  public SectorDTO getSector() {
    return sector;
  }

  public void setSector(SectorDTO sector) {
    this.sector = sector;
  }

  public Set<DatosUsuarioRolDTO> getRoles() {
		return roles;
	}
  
  public Set<RolDTO> getSoloRoles(){
  	return roles.stream().map(DatosUsuarioRolDTO::getRol).collect(Collectors.toSet());
  }
  
 
	public void setRoles(Set<DatosUsuarioRolDTO> roles) {
		this.roles = roles;
	}
	
  public String getPrimerNombre() {
		return primerNombre;
	}

	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}

	public String getSegundoNombre() {
		return segundoNombre;
	}

	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}

	public String getTercerNombre() {
		return tercerNombre;
	}

	public void setTercerNombre(String tercerNombre) {
		this.tercerNombre = tercerNombre;
	}

	public String getPrimerApellido() {
		return primerApellido;
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	public String getSegundoApellido() {
		return segundoApellido;
	}

	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	public String getTercerApellido() {
		return tercerApellido;
	}

	public void setTercerApellido(String tercerApellido) {
		this.tercerApellido = tercerApellido;
	}

	public void addRol(RolDTO rol, String usuarioModificador) {	  		
  		DatosUsuarioRolDTO rolUsuario = new DatosUsuarioRolDTO(this, rol, usuarioModificador);
  		for(DatosUsuarioRolDTO r : roles) {
  			if(existeDatosUsuarioRol(r, rolUsuario)) {
  				return;
  			}
  		}
  		roles.add(rolUsuario);
  		
  }
  
	private boolean existeDatosUsuarioRol(DatosUsuarioRolDTO rolUsuario, DatosUsuarioRolDTO rolUsuarioAsignado) {
		return rolUsuarioAsignado.getId().getDatosUsuario() != null 
				 && rolUsuario.getId().getRol()!=null 
				 && rolUsuarioAsignado.getDatosUsuario().getUsuario()
				 .equals(rolUsuario.getDatosUsuario().getUsuario())
				 && rolUsuarioAsignado.getRol().getId().equals(rolUsuario.getRol().getId());
	}

  
	@Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("DatosUsuarioDTO [id=").append(id).append(", mail=").append(mail)
        .append(", ocupacion=").append(ocupacion).append(", usuarioAsesor=").append(usuarioAsesor)
        .append(", usuario=").append(usuario).append(", userSuperior=").append(userSuperior)
        .append(", apellidoYNombre=").append(apellidoYNombre).append(", numeroCuit=")
        .append(numeroCuit).append(", aceptacionTyC=").append(aceptacionTyC)
        .append(", codigoSectorInterno=").append(codigoSectorInterno)
        .append(", fechaCaducidadSectorInterno=").append(fechaCaducidadSectorInterno)
        .append(", secretario=").append(secretario).append(", esSecretario=").append(esSecretario)
        .append(", idSectorInterno=").append(idSectorInterno).append(", cambiarMesa=")
        .append(cambiarMesa).append(", cargoAsignado=").append(cargoAsignado)
        .append(", fechaRevision=").append(fechaRevision).append(", tipoRevision=")
        .append(tipoRevision).append(", sector=").append(sector).append("]");
    return builder.toString();
  }

}