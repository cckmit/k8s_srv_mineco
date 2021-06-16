package com.egoveris.te.base.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatosDeBusqueda {
	private static final Logger logger = LoggerFactory.getLogger(DatosDeBusqueda.class);

	private String campo;
	private String valor;
	private Date fecha;

	public DatosDeBusqueda(String campo,String valor){
		this.campo=campo;
		this.valor=valor;
	}


	public DatosDeBusqueda() {
	}


	public String getCampo() {
		return campo;
	}


	public void setCampo(String campo) {
		this.campo = campo;
	}


	public String getValor() {
		return valor;
	}


	public void setValor(String valor) {
		this.valor = valor;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((campo == null) ? 0 : campo.hashCode());
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DatosDeBusqueda other = (DatosDeBusqueda) obj;
		if (campo == null) {
			if (other.campo != null)
				return false;
		} else if (!campo.equals(other.campo))
			return false;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}
	
	public Date getFecha() {
		return fecha;
	}


	public void setFecha(Date fecha) {
		if (logger.isDebugEnabled()) {
			logger.debug("setFecha(fecha={}) - start", fecha);
		}

		this.fecha = fecha;
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		this.valor = format.format(fecha);

		if (logger.isDebugEnabled()) {
			logger.debug("setFecha(Date) - end");
		}
	}
	
	
	
}
	
