package com.egoveris.te.base.model;

import java.io.Serializable;

public class SubProcesoOperacionDTO implements Serializable{

	 /**
	 * 
	 */
	private static final long serialVersionUID = 536785093054207301L;
	private OperacionDTO operacion;
	private SubProcesoDTO subproceso;
	private ExpedienteElectronicoDTO expediente;
	
	public OperacionDTO getOperacion() {
		return operacion;
	}

	public void setOperacion(OperacionDTO operacion) {
		this.operacion = operacion;
	}

	public SubProcesoDTO getSubproceso() {
		return subproceso;
	}

	public void setSubproceso(SubProcesoDTO subproceso) {
		this.subproceso = subproceso;
	}

	public ExpedienteElectronicoDTO getExpediente() {
		return expediente;
	}

	public void setExpediente(ExpedienteElectronicoDTO expediente) {
		this.expediente = expediente;
	}
	 
}
