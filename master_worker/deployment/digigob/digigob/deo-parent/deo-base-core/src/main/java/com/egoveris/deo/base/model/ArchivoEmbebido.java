package com.egoveris.deo.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "GEDO_ARCHIVO_EMBEBIDO")
public class ArchivoEmbebido {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "NOMBRE_ARCHIVO")
	private String nombreArchivo;
	
	@Column(name = "USUARIOASOCIADOR")
	private String usuarioAsociador;
	
	@Column(name = "PATHRELATIVO")
	private String pathRelativo;
	
	@Column(name = "FECHAASOCIACION")
	private Date fechaAsociacion;
	
	@Column(name = "IDTASK")
	private String idTask;
	
	@Column(name = "MIMETYPE")
	private String mimetype;
	
	@Column(name = "ID_GUARDA_DOCUMENTAL")
	private String idGuardaDocumental;
	
	@Column(name = "PESO")
	private Integer peso;
	
	public final static String ARCHIVOS_EMBEBIDOS = "archivosEmbebidos";

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombreArchivo() {
		return nombreArchivo;
	}
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}
	public String getUsuarioAsociador() {
		return usuarioAsociador;
	}
	public void setUsuarioAsociador(String usuarioAsociador) {
		this.usuarioAsociador = usuarioAsociador;
	}
	public String getPathRelativo() {
		return pathRelativo;
	}
	public void setPathRelativo(String pathRelativo) {
		this.pathRelativo = pathRelativo;
	}
	public Date getFechaAsociacion() {
		return fechaAsociacion;
	}
	public void setFechaAsociacion(Date fechaAsociacion) {
		this.fechaAsociacion = fechaAsociacion;
	}
	public String getIdTask() {
		return idTask;
	}
	public void setIdTask(String idTask) {
		this.idTask = idTask;
	}	
	public String getMimetype() {
		return mimetype;
	}
	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}
	
	public String getIdGuardaDocumental() {
		return idGuardaDocumental;
	}
	public void setIdGuardaDocumental(String idGuardaDocumental) {
		this.idGuardaDocumental = idGuardaDocumental;
	}
	
	public Integer getPeso() {
		return peso;
	}
	public void setPeso(Integer peso) {
		this.peso = peso;
	}
	public static String getARCHIVOS_EMBEBIDOS() {
		return ARCHIVOS_EMBEBIDOS;
	}
}
