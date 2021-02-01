package com.egoveris.te.base.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="EE_REPARTICION_PARTICIPANTE")
public class ReparticionParticipante implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID_REPARTICION")
	private Long idReparticion;
	
	@Column(name="REPARTICION")
	private String reparticion;
	
	@Column(name="TIPO_OPERACION")
	private String tipoOperacion;
	
	@Column(name="FECHA_MODIFICACION")
	private Date fechaModificacion;
	
	@Column(name="USUARIO")
	private String usuario;

	public Long getIdReparticion() {
		return idReparticion;
	}

	public void setIdReparticion(Long idReparticion) {
		this.idReparticion = idReparticion;
	}

	public String getReparticion() {
		return reparticion;
	}

	public void setReparticion(String reparticion) {
		this.reparticion = reparticion;
	}

	public String getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public ReparticionParticipante() {
		//
	}
	
	public ReparticionParticipante(ReparticionParticipanteDTO r) {
		this.idReparticion = r.getIdReparticion();
		this.reparticion = r.getReparticion();
		this.tipoOperacion = r.getTipoOperacion();
		this.fechaModificacion = r.getFechaModificacion();
		this.usuario = r.getUsuario();
	}
}
