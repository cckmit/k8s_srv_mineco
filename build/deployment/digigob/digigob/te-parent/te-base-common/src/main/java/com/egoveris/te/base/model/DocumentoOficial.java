package com.egoveris.te.base.model;

import java.io.Serializable;
import java.util.Date;

public class DocumentoOficial implements Serializable{

	private static final long serialVersionUID = 7299181072187138378L;
		Integer id;
		String numeroSade;
		String numeroEspecial;
		String tipoDocumento;
		String nombreUsuarioGenerador;
		String motivo;
		transient byte[] data;
		String nombreArchivo;
		Integer numeroFoliado;
		Boolean definitivo=false;
		Date fechaCreacion;
		Date fechaAsociacion;
		String usuarioAsociador;
		Integer posicion;
		String idTask; 
		Integer tipoDocGedo;
		
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getNumeroSade() {
			return numeroSade;
		}
		public void setNumeroSade(String numeroSade) {
			this.numeroSade = numeroSade;
		}
		public String getNumeroEspecial() {
			return numeroEspecial;
		}
		public void setNumeroEspecial(String numeroEspecial) {
			this.numeroEspecial = numeroEspecial;
		}
		public String getTipoDocumento() {
			return tipoDocumento;
		}
		public void setTipoDocumento(String tipoDocumento) {
			this.tipoDocumento = tipoDocumento;
		}
		public String getNombreUsuarioGenerador() {
			return nombreUsuarioGenerador;
		}
		public void setNombreUsuarioGenerador(String nombreUsuarioGenerador) {
			this.nombreUsuarioGenerador = nombreUsuarioGenerador;
		}
		public String getMotivo() {
			return motivo;
		}
		public void setMotivo(String motivo) {
			this.motivo = motivo;
		}
		public byte[] getData() {
			return data;
		}
		public void setData(byte[] data) {
			this.data = data;
		}
		public String getNombreArchivo() {
			return nombreArchivo;
		}
		public void setNombreArchivo(String nombreArchivo) {
			this.nombreArchivo = nombreArchivo;
		}
		public Integer getNumeroFoliado() {
			return numeroFoliado;
		}
		public void setNumeroFoliado(Integer numeroFoliado) {
			this.numeroFoliado = numeroFoliado;
		}
		public Boolean getDefinitivo() {
			return definitivo;
		}
		public void setDefinitivo(Boolean definitivo) {
			this.definitivo = definitivo;
		}
		public Date getFechaCreacion() {
			return fechaCreacion;
		}
		public void setFechaCreacion(Date fechaCreacion) {
			this.fechaCreacion = fechaCreacion;
		}
		public Date getFechaAsociacion() {
			return fechaAsociacion;
		}
		public void setFechaAsociacion(Date fechaAsociacion) {
			this.fechaAsociacion = fechaAsociacion;
		}
		public String getUsuarioAsociador() {
			return usuarioAsociador;
		}
		public void setUsuarioAsociador(String usuarioAsociador) {
			this.usuarioAsociador = usuarioAsociador;
		}
		public Integer getPosicion() {
			return posicion;
		}
		public void setPosicion(Integer posicion) {
			this.posicion = posicion;
		}
		public String getIdTask() {
			return idTask;
		}
		public void setIdTask(String idTask) {
			this.idTask = idTask;
		}
		public Integer getTipoDocGedo() {
			return tipoDocGedo;
		}
		public void setTipoDocGedo(Integer tipoDocGedo) {
			this.tipoDocGedo = tipoDocGedo;
		}
		
}
