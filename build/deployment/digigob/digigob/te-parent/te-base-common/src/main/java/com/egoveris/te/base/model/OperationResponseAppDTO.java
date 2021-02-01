package com.egoveris.te.base.model;

import java.util.List;

import com.egoveris.te.model.model.Status;

public class OperationResponseAppDTO {

	/**
	 * OperationAppDTO.
	 */
	private List<OperationAppDTO> operacion;
	
	/**
	 * Status
	 */
	private Status status;

	/**
	 * @return the operacion
	 */
	public List<OperationAppDTO> getOperacion() {
		return operacion;
	}

	/**
	 * @param operacion the operacion to set
	 */
	public void setOperacion(List<OperationAppDTO> operacion) {
		this.operacion = operacion;
	}

	/**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

}