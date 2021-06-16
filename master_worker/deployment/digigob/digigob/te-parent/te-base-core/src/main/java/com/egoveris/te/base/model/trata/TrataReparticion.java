package com.egoveris.te.base.model.trata;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="TRATA_REPARTICION")
public class TrataReparticion implements Serializable{
  
	private static final long serialVersionUID = -6002997424131983993L;
	
	@EmbeddedId
	private TrataReparticionPK pk;

	@Column(name="ID_TRATA", insertable = false, updatable = false)
	private Long idTrata;
	
	@Column(name="orden", insertable = false, updatable = false)
	private int orden;
	
	@Column(name="codigoReparticion")
	private String codigoReparticion;
	
	@Column(name="habilitacion")
	private Boolean habilitacion;

	@Column(name="reserva")
	private Boolean reserva;

	public TrataReparticionPK getPk() {
		return pk;
	}

	public void setPk(TrataReparticionPK pk) {
		this.pk = pk;
	}

	public Long getIdTrata() {
		return idTrata;
	}

	public void setIdTrata(Long idTrata) {
		this.idTrata = idTrata;
	}

	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	public String getCodigoReparticion() {
		return codigoReparticion;
	}

	public void setCodigoReparticion(String codigoReparticion) {
		this.codigoReparticion = codigoReparticion;
	}

	public Boolean getHabilitacion() {
		return habilitacion;
	}

	public void setHabilitacion(Boolean habilitacion) {
		this.habilitacion = habilitacion;
	}

	public Boolean getReserva() {
		return reserva;
	}

	public void setReserva(Boolean reserva) {
		this.reserva = reserva;
	}
	
}
