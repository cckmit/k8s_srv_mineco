package com.egoveris.te.base.model;

import java.util.Date;




public class TareaParaleloInbox {

		private String idTask;
		private Long idExp;
		private String tipoDocumentoExp;
		private Integer anioExp;
		private Integer numeroExp;
		private String codigoReparticionActuacion;
		private String codigoReparticionUsuario;
		private Date fechaPase;
		private String motivo;
		private String motivoRespuesta;
		private String estado;
		private String usuarioGrupoDestinoAnterior;
		private String usuarioGrupoDestino;
		private String usuarioOrigen;
		
		//el valor es obtenido con el task service. No se obtiene de la tabla
		private String usuarioGrupoDestinoAsignado;
		
		
		public Date getFechaPase() {
			return fechaPase;
		}
		public void setFechaPase(Date fechaPase) {
			this.fechaPase = fechaPase;
		}
		public String getEstado() {
			return estado;
		}
		public void setEstado(String estado) {
			this.estado = estado;
		}
		
		public String getMotivo() {
			return motivo;
		}
		public void setMotivo(String motivo) {
			this.motivo = motivo;
		}

		public String getUsuarioGrupoDestinoAsignado() {
			return usuarioGrupoDestinoAsignado;
		}
		public void setUsuarioGrupoDestinoAsignado(String usuarioGrupoDestinoAsignado) {
			this.usuarioGrupoDestinoAsignado = usuarioGrupoDestinoAsignado;
		}
	
		public String getTipoDocumentoExp() {
			return tipoDocumentoExp;
		}
		public void setTipoDocumentoExp(String tipoDocumentoExp) {
			this.tipoDocumentoExp = tipoDocumentoExp;
		}
		public String getCodigoReparticionActuacion() {
			return codigoReparticionActuacion;
		}
		public void setCodigoReparticionActuacion(String codigoReparticionActuacion) {
			this.codigoReparticionActuacion = codigoReparticionActuacion;
		}
		public String getCodigoReparticionUsuario() {
			return codigoReparticionUsuario;
		}
		
		public Integer getAnioExp() {
			return anioExp;
		}
		public void setAnioExp(Integer anioExp) {
			this.anioExp = anioExp;
		}
		public Integer getNumeroExp() {
			return numeroExp;
		}
		public void setNumeroExp(Integer numeroExp) {
			this.numeroExp = numeroExp;
		}
		public void setCodigoReparticionUsuario(String codigoReparticionUsuario) {
			this.codigoReparticionUsuario = codigoReparticionUsuario;
		}
		public String getIdTask() {
			return idTask;
		}
		public void setIdTask(String idTask) {
			this.idTask = idTask;
		}
		public Long getIdExp() {
			return idExp;
		}
		public void setIdExp(Long idExp) {
			this.idExp = idExp;
		}
		public String getMotivoRespuesta() {
			return motivoRespuesta;
		}
		public void setMotivoRespuesta(String motivoRespuesta) {
			this.motivoRespuesta = motivoRespuesta;
		}
		public String getUsuarioGrupoDestinoAnterior() {
			return usuarioGrupoDestinoAnterior;
		}
		public void setUsuarioGrupoDestinoAnterior(String usuarioGrupoDestinoAnterior) {
			this.usuarioGrupoDestinoAnterior = usuarioGrupoDestinoAnterior;
		}
		public String getUsuarioGrupoDestino() {
			return usuarioGrupoDestino;
		}
		public void setUsuarioGrupoDestino(String usuarioGrupoDestino) {
			this.usuarioGrupoDestino = usuarioGrupoDestino;
		}
		public String getUsuarioOrigen() {
			return usuarioOrigen;
		}
		public void setUsuarioOrigen(String usuarioOrigen) {
			this.usuarioOrigen = usuarioOrigen;
		}

		
	}
