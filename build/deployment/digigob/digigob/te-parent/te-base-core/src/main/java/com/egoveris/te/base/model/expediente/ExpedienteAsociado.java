package com.egoveris.te.base.model.expediente;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "EXPEDIENTEASOCIADO")
public class ExpedienteAsociado implements Serializable {

	private static final long serialVersionUID = 8221690496211275093L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "TIPO_DOCUMENTO")
	private String tipoDocumento;

	@Column(name = "ANIO")
	private Integer anio;

	@Column(name = "NUMERO")
	private Integer numero;

	@Column(name = "SECUENCIA")
	private String secuencia;

	@Column(name = "DEFINITIVO")
	private Boolean definitivo = false;

	@Column(name = "CODIGO_REPARTICION_ACTUACION")
	private String codigoReparticionActuacion;

	@Column(name = "CODIGO_REPARTICION_USUARIO")
	private String codigoReparticionUsuario;

	@Column(name = "ES_ELECTRONICO")
	private Boolean esElectronico;

	@Column(name = "ID_CODIGO_CARATULA")
	private Long idCodigoCaratula;

	@Column(name = "USUARIO_ASOCIADOR")
	private String usuarioAsociador;

	// @Column(name = "FECHA_ASOCIACION")
	// private DateTime fechaAsociacion;

	@Column(name = "ID_TASK")
	private String idTask;

	@Column(name = "ES_EXP_ASOC_TC")
	private Boolean esExpedienteAsociadoTC;

	@Column(name = "ES_EXP_ASOC_FUSION")
	private Boolean esExpedienteAsociadoFusion;

	// @Column(name = "FECHA_MODIFICACION")
	// private DateTime fechaModificacion;

	@Column(name = "USUARIO_MODIFICACION")
	private String usuarioModificacion;

	@Column(name = "ID_EXP_CABECERA_TC")
	private Long idExpCabeceraTC;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(final String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Integer getAnio() {
		return anio;
	}

	public void setAnio(final Integer anio) {
		this.anio = anio;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(final Integer numero) {
		this.numero = numero;
	}

	public String getSecuencia() {
		return secuencia;
	}

	public void setSecuencia(final String secuencia) {
		this.secuencia = secuencia;
	}

	public Boolean getDefinitivo() {
		return definitivo;
	}

	public void setDefinitivo(final Boolean definitivo) {
		this.definitivo = definitivo;
	}

	public String getCodigoReparticionActuacion() {
		return codigoReparticionActuacion;
	}

	public void setCodigoReparticionActuacion(final String codigoReparticionActuacion) {
		this.codigoReparticionActuacion = codigoReparticionActuacion;
	}

	public String getCodigoReparticionUsuario() {
		return codigoReparticionUsuario;
	}

	public void setCodigoReparticionUsuario(final String codigoReparticionUsuario) {
		this.codigoReparticionUsuario = codigoReparticionUsuario;
	}

	public Boolean getEsElectronico() {
		return esElectronico;
	}

	public void setEsElectronico(final Boolean esElectronico) {
		this.esElectronico = esElectronico;
	}

	public Long getIdCodigoCaratula() {
		return idCodigoCaratula;
	}

	public void setIdCodigoCaratula(final Long idCodigoCaratula) {
		this.idCodigoCaratula = idCodigoCaratula;
	}

	public String getUsuarioAsociador() {
		return usuarioAsociador;
	}

	public void setUsuarioAsociador(final String usuarioAsociador) {
		this.usuarioAsociador = usuarioAsociador;
	}

	// public DateTime getFechaAsociacion() {
	// return fechaAsociacion;
	// }
	//
	// public void setFechaAsociacion(DateTime fechaAsociacion) {
	// this.fechaAsociacion = fechaAsociacion;
	// }

	public String getIdTask() {
		return idTask;
	}

	public void setIdTask(final String idTask) {
		this.idTask = idTask;
	}

	public Boolean getEsExpedienteAsociadoTC() {
		return esExpedienteAsociadoTC;
	}

	public void setEsExpedienteAsociadoTC(final Boolean esExpedienteAsociadoTC) {
		this.esExpedienteAsociadoTC = esExpedienteAsociadoTC;
	}

	public Boolean getEsExpedienteAsociadoFusion() {
		return esExpedienteAsociadoFusion;
	}

	public void setEsExpedienteAsociadoFusion(final Boolean esExpedienteAsociadoFusion) {
		this.esExpedienteAsociadoFusion = esExpedienteAsociadoFusion;
	}

	// public DateTime getFechaModificacion() {
	// return fechaModificacion;
	// }
	//
	// public void setFechaModificacion(DateTime fechaModificacion) {
	// this.fechaModificacion = fechaModificacion;
	// }

	public String getUsuarioModificacion() {
		return usuarioModificacion;
	}

	public void setUsuarioModificacion(final String usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}

	public Long getIdExpCabeceraTC() {
		return idExpCabeceraTC;
	}

	public void setIdExpCabeceraTC(final Long idExpCabeceraTC) {
		this.idExpCabeceraTC = idExpCabeceraTC;
	}

}
