package com.egoveris.ffdd.model.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

public class TransaccionDTO implements Serializable {

	private static final long serialVersionUID = -1617582243652878579L;
	
	private Integer uuid;
	private Date fechaCreacion;
	private String nombreFormulario;
	private String sistOrigen;
	private Set<ValorFormCompDTO> valorFormComps;

	public TransaccionDTO() {
		this.fechaCreacion = Calendar.getInstance().getTime();
	}

	public TransaccionDTO(String nombreFormulario) {
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

	public Set<ValorFormCompDTO> getValorFormComps() {
		return valorFormComps;
	}

	public void setValorFormComps(Set<ValorFormCompDTO> valorFormComps) {
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TransaccionDTO [uuid=").append(uuid).append(", fechaCreacion=").append(fechaCreacion)
				.append(", nombreFormulario=").append(nombreFormulario).append(", sistOrigen=").append(sistOrigen)
				.append(", valorFormComps=").append(valorFormComps).append("]");
		return builder.toString();
	}

}
