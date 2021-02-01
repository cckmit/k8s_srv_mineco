package com.egoveris.te.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TRANSACTION_ESB")
public class TransactionESB {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name= "ID")
	private Long id;
	
	@Column(name= "DATE_CREATION")
	private Date dateCreation;
	
	@Column(name= "TRANSACTION")
	private String transaction;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public String getTransaction() {
		return transaction;
	}

	public void setTransaction(String transaction) {
		this.transaction = transaction;
	}
	
	
	
	
}
