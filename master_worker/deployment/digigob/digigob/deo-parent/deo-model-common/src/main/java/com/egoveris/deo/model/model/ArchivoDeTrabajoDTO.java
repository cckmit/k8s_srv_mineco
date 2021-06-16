package com.egoveris.deo.model.model;

import java.io.Serializable;
import java.util.Date;


public class ArchivoDeTrabajoDTO implements Serializable {

	private static final long serialVersionUID = 3005946162657159558L;
	private Integer id;
	private String nombreArchivo;
	private transient byte[] dataArchivo;
	private boolean definitivo = false;
	private String usuarioAsociador;
	private Date fechaAsociacion;
	private String idTask;
	private String pathRelativo;
	private String idGuardaDocumental;
	private Integer peso;
	
	public static final String PATH_RELATIVO = "pathRelativo";
	
	public static final String ARCHIVOS_DE_TRABAJO = "archivosDeTrabajo";
	
	public String getPathRelativo() {
		return pathRelativo;
	}
	public void setPathRelativo(String pathRelativo) {
		this.pathRelativo = pathRelativo;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}
	public String getNombreArchivo() {
		return nombreArchivo;
	}
	public byte[] getDataArchivo() {
		return dataArchivo;
	}
	public void setDataArchivo(byte[] dataArchivo) {
		this.dataArchivo = dataArchivo;
	}
	public boolean isDefinitivo() {
		return definitivo;
	}
	public void setDefinitivo(boolean definitivo) {
		this.definitivo = definitivo;
	}
	public String getUsuarioAsociador() {
		return usuarioAsociador;
	}
	public void setUsuarioAsociador(String usuarioAsociador) {
		this.usuarioAsociador = usuarioAsociador;
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
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ArchivoDeTrabajoDTO other = (ArchivoDeTrabajoDTO) obj;
		if (nombreArchivo == null) {
			if (other.nombreArchivo != null)
				return false;
		} else if (!nombreArchivo.equals(other.nombreArchivo))
			return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (definitivo ? 1231 : 1237);
		result = prime * result + ((fechaAsociacion == null) ? 0 : fechaAsociacion.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idTask == null) ? 0 : idTask.hashCode());
		result = prime * result + ((nombreArchivo == null) ? 0 : nombreArchivo.hashCode());
		result = prime * result + ((pathRelativo == null) ? 0 : pathRelativo.hashCode());
		result = prime * result + ((usuarioAsociador == null) ? 0 : usuarioAsociador.hashCode());
		return result;
	}
	
	@Override
	public String toString() {
		return nombreArchivo;
	}
}
