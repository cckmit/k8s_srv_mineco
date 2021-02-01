package com.egoveris.deo.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "GEDO_DOCUMENTO_SOLICITUD")
public class DocumentoSolicitud {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "NUMERO_SADE")
  private String numeroSade;

  @Column(name = "WORKFLOWID")
  private String workflowId;

  @Column(name = "ID_TRANSACCION")
	private Double idTransaccion;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getNumeroSade() {
    return numeroSade;
  }

  public void setNumeroSade(String numeroSade) {
    this.numeroSade = numeroSade;
  }

  public String getWorkflowId() {
    return workflowId;
  }

  public void setWorkflowId(String workflowId) {
    this.workflowId = workflowId;
  }

	public Double getIdTransaccion() {
    return idTransaccion;
  }

	public void setIdTransaccion(Double idTransaccion) {
    this.idTransaccion = idTransaccion;
  }

}
