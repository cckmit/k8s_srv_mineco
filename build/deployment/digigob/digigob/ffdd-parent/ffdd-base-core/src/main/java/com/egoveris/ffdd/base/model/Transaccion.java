package com.egoveris.ffdd.base.model;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "DF_TRANSACTION")
public class Transaccion {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer uuid;
	
	@Column(name = "creation_date")
	private Date fechaCreacion;
	
	@Column(name = "form_name")
	private String nombreFormulario;
	
	@Column(name = "sys_source")
	private String sistOrigen;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_transaction", nullable = false)
	private Set<ValorFormComp> valorFormComps;

	public Transaccion() {
		this.fechaCreacion = Calendar.getInstance().getTime();
	}

	public Transaccion(String nombreFormulario) {
		this();
		this.nombreFormulario = nombreFormulario;
	}

	public Integer getUuid() {
		return uuid;
	}

	public void setUuid(Integer uuid) {
		this.uuid = uuid;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Set<ValorFormComp> getValorFormComps() {
		return valorFormComps;
	}

	public void setValorFormComps(Set<ValorFormComp> valorFormComps) {
		this.valorFormComps = valorFormComps;
	}

	public String getNombreFormulario() {
		return nombreFormulario;
	}

	public void setNombreFormulario(String nombreFormulario) {
		this.nombreFormulario = nombreFormulario;
	}

	public String getSistOrigen() {
		return sistOrigen;
	}

	public void setSistOrigen(String sistemaOrigen) {
		this.sistOrigen = sistemaOrigen;
	}

}
