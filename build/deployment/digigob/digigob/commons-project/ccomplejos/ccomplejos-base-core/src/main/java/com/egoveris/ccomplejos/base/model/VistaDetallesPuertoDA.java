package com.egoveris.ccomplejos.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "view_detallespuertoda")
public class VistaDetallesPuertoDA extends AbstractViewCComplejoJPA {

	@Column(name = "REGION")
	protected String region;
	
	@Column(name = "COD_PAIS")
	protected String codigoPais;
	
	@Column(name = "FECHA")
	protected Date fecha;
	
	@Column(name = "SECUENCIAL")
	protected Integer secuencial;
	
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getCodigoPais() {
		return codigoPais;
	}
	public void setCodigoPais(String codigoPais) {
		this.codigoPais = codigoPais;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Integer getSecuencial() {
		return secuencial;
	}
	public void setSecuencial(Integer secuencial) {
		this.secuencial = secuencial;
	}


}