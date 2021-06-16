package com.egoveris.te.base.model;

import java.io.Serializable;
import java.util.Date;

public class ExpedienteElectronicoConSuspension implements Serializable {

	private static final long serialVersionUID = -4099335598695501341L;
	private Long id;
	private Long eeId;
	private ExpedienteElectronicoDTO ee;
	private String usuarioSuspension;
	private Date fechaSuspension;
	private String codigoCaratula;
	
	public ExpedienteElectronicoConSuspension() {
		super();
	}
	
	public ExpedienteElectronicoConSuspension(ExpedienteElectronicoDTO ee) {
		super();
		this.id = ee.getId();
		this.eeId = ee.getId();
		this.ee = ee;
	}

	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getEeId() {
		return this.eeId;
	}

	public void setEeId(Long eeId) {
		this.eeId = eeId;
	}

	public ExpedienteElectronicoDTO getEe() {
		return this.ee;
	}

	public void setEe(ExpedienteElectronicoDTO ee) {
		this.ee = ee;
	}

	public String getUsuarioSuspension() {
		return this.usuarioSuspension;
	}
	
	public void setUsuarioSuspension(String usuarioSuspension) {
		this.usuarioSuspension = usuarioSuspension;
	}
	
	public Date getFechaSuspension() {
		return this.fechaSuspension;
	}
	
	public void setFechaSuspension(Date fechaSuspension) {
		this.fechaSuspension = fechaSuspension;
	}

	public String getCodigoCaratula() {
		return this.codigoCaratula;
	}

	public void setCodigoCaratula(String codigoCaratula) {
		this.codigoCaratula = codigoCaratula;
	}

	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder("");
		sb
		.append("ExpedienteElectronicoConSuspension [")
		.append((this.id != null ? "id=" + this.id + ", " : ""))
		.append((this.eeId != null ? "eeId=" + this.eeId + ", " : ""))
		.append((this.ee != null ? "ee=" + this.ee + ", " : ""))
		.append((this.usuarioSuspension != null ? "usuarioSuspension=" + this.usuarioSuspension + ", ": ""))
		.append((this.fechaSuspension != null ? "fechaSuspension=" + this.fechaSuspension : ""))
		.append("]");
		
		return sb.toString();
	}

	@Override
	public int hashCode() {
		return this.ee.getId().intValue();
	}

	@Override
	public boolean equals(Object obj) {
		boolean esIgual = false;
		
		if(obj != null && obj instanceof ExpedienteElectronicoConSuspension) {
			esIgual = (this.ee.getId().equals(((ExpedienteElectronicoConSuspension) obj).ee.getId()));
		}
		
		return esIgual;
	}
	
}
