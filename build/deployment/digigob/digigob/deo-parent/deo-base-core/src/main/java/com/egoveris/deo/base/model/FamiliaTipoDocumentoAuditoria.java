package com.egoveris.deo.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="GEDO_TIPODOCUMENTO_FAMILIA_AUD")
public class FamiliaTipoDocumentoAuditoria {

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id ;
	  
	@Column(name="NOMBREUSUARIO")	
	private String nombreUsuario;
	
	@Column(name="ID_FAMILIA")	
	private Integer idFamilia;
	
	@Column(name="NOMBREFAMILIA")	
	private String nombreFamilia ;
	
	@Column(name="DESCRIPCION")	
	private String descripcion ;
	
	@Column(name="FECHACREACION")	
	private Date fechaCreacion;
	
	@Column(name="ESTADO")	
	private String estado;
	
	
	public FamiliaTipoDocumentoAuditoria(FamiliaTipoDocumento familiaTipoDocumento, String usuario,String estado) {
		this.nombreUsuario= usuario;
		this.idFamilia = familiaTipoDocumento.getId();
		this.nombreFamilia =familiaTipoDocumento.getNombre();
		this.descripcion =familiaTipoDocumento.getDescripcion();
		this.fechaCreacion= new Date();
		this.estado=estado;
		
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public Integer getIdFamilia() {
		return idFamilia;
	}
	public void setIdFamilia(Integer idFamilia) {
		this.idFamilia = idFamilia;
	}
	public String getNombreFamilia() {
		return nombreFamilia;
	}
	public void setNombreFamilia(String nombreFamilia) {
		this.nombreFamilia = nombreFamilia;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	
	
}
