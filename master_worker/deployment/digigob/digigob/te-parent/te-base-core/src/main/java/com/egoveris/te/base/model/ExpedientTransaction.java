package com.egoveris.te.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.egoveris.te.base.model.expediente.ExpedienteElectronico;

@Entity
@Table(name="TE_EXPEDIENT_TRANSACTION")
public class ExpedientTransaction {

	@Id
	@Column(name="ID")
	@GeneratedValue
	private Long id;
	
	@Column(name="ID_TRANSACTION")
	private String idTransaction;
	
	@Column(name="ID_EXPEDIENT")
	private Long idExpedient;
	
	@Column(name="DATE_CREATION")
	private Date dateCreation;
	
	@Column(name="CODE")
	private String code;
	
	@Column(name="MESSAGE")
	private String message;
	
	@Column(name="STATUS")
	private int status;
	
	@Column(name="ID_OPERATION")
	private Long idOperation;
	
	@OneToOne
	@JoinColumn(name="ID_OPERATION" , insertable = false, updatable = false)
	private Operacion operation;
	
	
	@OneToOne
	@JoinColumn(name="ID_EXPEDIENT" , insertable = false, updatable = false)
	private ExpedienteElectronico expedient;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIdTransaction() {
		return idTransaction;
	}
	public void setIdTransaction(String idTransaction) {
		this.idTransaction = idTransaction;
	}
	public Long getIdExpedient() {
		return idExpedient;
	}
	public void setIdExpedient(Long idExpedient) {
		this.idExpedient = idExpedient;
	}
	public Date getDateCreation() {
		return dateCreation;
	}
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Long getIdOperation() {
		return idOperation;
	}
	public void setIdOperation(Long idOperation) {
		this.idOperation = idOperation;
	}
	public Operacion getOperation() {
		return operation;
	}
	public void setOperation(Operacion operation) {
		this.operation = operation;
	}
	public ExpedienteElectronico getExpedient() {
		return expedient;
	}
	public void setExpedient(ExpedienteElectronico expedient) {
		this.expedient = expedient;
	}
	
}
