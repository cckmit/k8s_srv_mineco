package com.egoveris.te.base.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.egoveris.te.base.model.expediente.ExpedienteElectronico;

@Entity
@Table(name = "TE_OPERACION_EE")
public class SubProcesoOperacion implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 2101890966511826083L;
	@EmbeddedId
	private SubProcesoOperacionPk pk;

	@OneToOne
	@JoinColumn(name = "ID_OPERACION", insertable = false, updatable = false)
	private Operacion operacion;

	@OneToOne
	@JoinColumn(name = "ID_SUBPROCESO", insertable = false, updatable = false)
	private SubProceso subproceso;

	@OneToOne
	@JoinColumn(name = "ID_EXPEDIENTE_ELECTRONICO", insertable = false, updatable = false)
	private ExpedienteElectronico expediente;

	public SubProcesoOperacionPk getPk() {
		return pk;
	}

	public void setPk(final SubProcesoOperacionPk pk) {
		this.pk = pk;
	}

	public Operacion getOperacion() {
		return operacion;
	}

	public void setOperacion(final Operacion operacion) {
		this.operacion = operacion;
	}

	public SubProceso getSubproceso() {
		return subproceso;
	}

	public void setSubproceso(final SubProceso subproceso) {
		this.subproceso = subproceso;
	}

	public ExpedienteElectronico getExpediente() {
		return expediente;
	}

	public void setExpediente(final ExpedienteElectronico expediente) {
		this.expediente = expediente;
	}

}
