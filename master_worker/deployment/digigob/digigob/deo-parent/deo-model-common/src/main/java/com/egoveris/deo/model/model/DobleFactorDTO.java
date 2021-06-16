package com.egoveris.deo.model.model;

import java.io.Serializable;
import java.util.Date;

public class DobleFactorDTO implements Serializable{

	private static final long serialVersionUID = 6090846329326866L;

	private Integer id;
	private String documento;
	private Date fecha;
	private String codigo;
	private String estado;
	
	public DobleFactorDTO() {}
	public DobleFactorDTO(String documento, Date fecha, String codigo, String estado) {		
		this.documento = documento;
		this.fecha = fecha;
		this.codigo = codigo;
		this.estado = estado;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
}
