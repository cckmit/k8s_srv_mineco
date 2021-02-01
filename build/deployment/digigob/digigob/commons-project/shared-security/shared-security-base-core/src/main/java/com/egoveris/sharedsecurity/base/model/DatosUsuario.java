package com.egoveris.sharedsecurity.base.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "edt_datos_usuario")
public class DatosUsuario implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 8977414489512461125L;

	@Id
  @Column(name = "id_dato_usuario")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "mail")
  private String mail;

  @Column(name = "ocupacion")
  private String ocupacion;

  @Column(name = "usuario_asesor")
  private String usuarioAsesor;

  @Column(name = "usuario", nullable = false)
  private String usuario;

  @Column(name = "user_superior")
  private String userSuperior;

  @Column(name = "apellido_nombre")
  private String apellidoYNombre;
  
  @Column(name = "primer_nombre")
  private String primerNombre;
  
  @Column(name = "segundo_nombre")
  private String segundoNombre;

  @Column(name = "tercer_nombre")
  private String tercerNombre;
  
  @Column(name = "primer_apellido")
  private String primerApellido;
  
  @Column(name = "segundo_apellido")
  private String segundoApellido;
  
  @Column(name = "tercer_apellido")
  private String tercerApellido;
  
  @Column(name = "numero_cuit")
  private String numeroCuit;

  @Column(name = "aceptacion_tyc")
  private Boolean aceptacionTyC;

  @Column(name = "codigo_sector_interno")
  private String codigoSectorInterno;

  @Column(name = "fecha_caducidad_sector_interno")
  private Date fechaCaducidadSectorInterno;

  @Column(name = "secretario")
  private String secretario;

  @Column(name = "Es_Secretario")
  private String esSecretario;

  @Column(name = "ID_SECTOR_INTERNO")
  private Integer idSectorInterno;

  @Column(name = "CAMBIAR_MESA")
  private Boolean cambiarMesa;

  @ManyToOne(targetEntity = Cargo.class)
  @JoinColumn(name = "CARGO")
  private Cargo cargoAsignado;

  @OneToMany(mappedBy = "id.datosUsuario", cascade = CascadeType.ALL)
  private Set<DatosUsuarioRol> roles = new HashSet<>();
  
  @Transient
  private Date fechaRevision;

  @Transient
  private String tipoRevision;

  @Transient
  @ManyToOne
  @JoinColumn(name = "ID_SECTOR_INTERNO")
  private Sector sector;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUser() {
    return usuario;
  }

  public void setUser(String user) {
    this.usuario = user;
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

  public String getApellidoYNombre() {
    return apellidoYNombre;
  }

  public void setApellidoYNombre(String apellidoYNombre) {
    this.apellidoYNombre = apellidoYNombre;
  }

  public String getUserSuperior() {
    return userSuperior;
  }

  public void setUserSuperior(String userSuperior) {
    this.userSuperior = userSuperior;
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

  public String getEsSecretario() {
    return esSecretario;
  }

  public void setEsSecretario(String esSecretario) {
    this.esSecretario = esSecretario;
  }

  public String getSecretario() {
    return secretario;
  }

  public void setSecretario(String secretario) {
    this.secretario = secretario;
  }

  public Boolean getAceptacionTyC() {
    return aceptacionTyC;
  }

  public void setAceptacionTyC(Boolean aceptacionTyC) {
    this.aceptacionTyC = aceptacionTyC;
  }

  public void setNumeroCuit(String numeroCuit) {
    this.numeroCuit = numeroCuit;
  }

  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  public String getNumeroCuit() {
    return numeroCuit;
  }

  public String getUsuarioAsesor() {
    return this.usuarioAsesor;
  }

  public void setUsuarioAsesor(String usuarioAsesor) {
    this.usuarioAsesor = usuarioAsesor;
  }

  public Integer getIdSectorInterno() {
    return idSectorInterno;
  }

  public void setIdSectorInterno(Integer idSectorInterno) {
    this.idSectorInterno = idSectorInterno;
  }

  public Cargo getCargoAsignado() {
    return cargoAsignado;
  }

  public void setCargoAsignado(Cargo cargoAsignado) {
    this.cargoAsignado = cargoAsignado;
  }

  public Boolean getCambiarMesa() {
    return cambiarMesa;
  }

  public void setCambiarMesa(Boolean cambiarMesa) {
    this.cambiarMesa = cambiarMesa;
  }

  public String getTipoRevision() {
    return tipoRevision;
  }

  public void setTipoRevision(String tipoRevision) {
    this.tipoRevision = tipoRevision;
  }

  public Date getFechaRevision() {
    return fechaRevision;
  }

  public void setFechaRevision(Date fechaRevision) {
    this.fechaRevision = fechaRevision;
  }

  public Sector getSector() {
    return sector;
  }

  public void setSector(Sector sector) {
    this.sector = sector;
  }
	
  public Set<DatosUsuarioRol> getRoles() {
		return roles;
	}

	public void setRoles(Set<DatosUsuarioRol> roles) {
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
}