package com.egoveris.deo.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CCOO_COMUNICACION")
public class Comunicacion {	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "ID_DOCUMENTO")
	private Documento documento;					
	
	@Column(name = "MENSAJE")
	private String mensaje;
	
	@Column(name = "FECHA_CREACION")
	private Date fechaCreacion;			

	@Column(name = "ID_COMUNICACION")
	private Integer idComunicacion;
	
	@Column(name = "USUARIO_CREADOR")
	private String usuarioCreador;
	
	@Column(name = "NOMBRE_COMPLETO_USUARIO")
	private String nombreApellidoUsuario;
	
	@Column(name = "FECHA_ELIMINACION_BANDEJA")
	private Date fechaEliminacion;
	
	@Column(name = "NRO_COMUNICACION_RESPONDIDA")
	private String nroComunicacionRespondida;
	
	@Column(name = "TIENE_ADJUNTOS")
	private Boolean tieneAdjuntos;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}	
	
	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}		

	public Integer getIdComunicacion() {
		return idComunicacion;
	}

	public void setIdComunicacion(Integer idComunicacion) {
		this.idComunicacion = idComunicacion;
	}

	public String getUsuarioCreador() {
		return usuarioCreador;
	}

	public void setUsuarioCreador(String usuarioCreador) {
		this.usuarioCreador = usuarioCreador;
	}

	public String getNombreApellidoUsuario() {
		return nombreApellidoUsuario;
	}

	public void setNombreApellidoUsuario(String nombreApellidoUsuario) {
		this.nombreApellidoUsuario = nombreApellidoUsuario;
	}

	public Date getFechaEliminacion() {
		return fechaEliminacion;
	}

	public void setFechaEliminacion(Date fechaEliminacion) {
		this.fechaEliminacion = fechaEliminacion;
	}

	public String getNroComunicacionRespondida() {
		return nroComunicacionRespondida;
	}

	public void setNroComunicacionRespondida(String nroComunicacionRespondida) {
		this.nroComunicacionRespondida = nroComunicacionRespondida;
	}

	public Boolean getTieneAdjuntos() {
		return tieneAdjuntos;
	}

	public void setTieneAdjuntos(Boolean tieneAdjuntos) {
		this.tieneAdjuntos = tieneAdjuntos;
	}
		
}
