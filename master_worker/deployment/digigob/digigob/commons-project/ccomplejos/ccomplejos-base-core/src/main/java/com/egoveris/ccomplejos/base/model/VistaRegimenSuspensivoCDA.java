package com.egoveris.ccomplejos.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "view_regimensuspensivocda")
public class VistaRegimenSuspensivoCDA extends AbstractViewCComplejoJPA {
	
	@Column(name = "TIPO_REINGRESO")
	protected String tipoReingreso;
	
	@Column(name = "RAZON_REINGRESO")
	protected String razonReingreso;
	
	public String getTipoReingreso() {
		return tipoReingreso;
	}
	public void setTipoReingreso(String tipoReingreso) {
		this.tipoReingreso = tipoReingreso;
	}
	public String getRazonReingreso() {
		return razonReingreso;
	}
	public void setRazonReingreso(String razonReingreso) {
		this.razonReingreso = razonReingreso;
	}
	
}
