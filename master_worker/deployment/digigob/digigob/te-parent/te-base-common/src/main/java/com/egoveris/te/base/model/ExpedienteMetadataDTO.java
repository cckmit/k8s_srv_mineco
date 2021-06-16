package com.egoveris.te.base.model;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

	
public class ExpedienteMetadataDTO implements Serializable{
	private static final Logger logger = LoggerFactory.getLogger(ExpedienteMetadataDTO.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 8221690496211275093L;
	private String nombre;
	private boolean obligatoriedad;
	private Integer tipo;
	private int orden;
	private String valor;
	 
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public boolean isObligatoriedad() {
		return obligatoriedad;
	}
	public void setObligatoriedad(boolean obligatoriedad) {
		this.obligatoriedad = obligatoriedad;
	}
	public Integer getTipo() {
		return tipo;
	}
	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}
	public int getOrden() {
		return orden;
	}
	public void setOrden(int orden) {
		this.orden = orden;
	}
	public void copiar(ExpedienteMetadataDTO mt) {
		if (logger.isDebugEnabled()) {
			logger.debug("copiar(mt={}) - start", mt);
		}

		this.nombre = mt.getNombre();
		this.valor = mt.getNombre() + "/" + mt.getValor();
		this.tipo = mt.getTipo();
		this.obligatoriedad = this.isObligatoriedad();
		this.orden = mt.getOrden();

		if (logger.isDebugEnabled()) {
			logger.debug("copiar(ExpedienteMetadata) - end");
		}
	}
	
	

}
