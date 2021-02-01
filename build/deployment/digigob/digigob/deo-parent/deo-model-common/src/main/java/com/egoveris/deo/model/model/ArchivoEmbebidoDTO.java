package com.egoveris.deo.model.model;

import java.io.Serializable;
import java.util.Date;

public class ArchivoEmbebidoDTO implements Serializable {

	private static final long serialVersionUID = 5771939315235128341L;
	private Integer id;
	private String nombreArchivo;
	private String usuarioAsociador;
	private String pathRelativo;
	private Date fechaAsociacion;
	private String idTask;
	private byte[] dataArchivo;
	private String mimetype;
	private String idGuardaDocumental;
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
	public byte[] getDataArchivo() {
		return dataArchivo;
	}
	public void setDataArchivo(byte[] dataArchivo) {
		this.dataArchivo = dataArchivo;
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
