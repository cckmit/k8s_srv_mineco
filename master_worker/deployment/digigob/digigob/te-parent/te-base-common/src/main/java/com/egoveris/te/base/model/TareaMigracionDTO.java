package com.egoveris.te.base.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TareaMigracionDTO implements Serializable {
	private static final Logger logger = LoggerFactory.getLogger(TareaMigracionDTO.class);

	private static final long serialVersionUID = 7906721943937251301L;

	private Long id;
	
	private String tarea;
	
	private String codigoReparticionOrigen;
	
	private String codigoReparticionDestino;
	
	private String codigoSectorOrigen;
	
	private String codigoSectorDestino;
	
	private Date fecha;
	
	private Set<TareaMigracionErrorDTO> tareasError;

	
	
	public void crearTareaMigracionError(String motivo){
		if (logger.isDebugEnabled()) {
			logger.debug("crearTareaMigracionError(motivo={}) - start", motivo);
		}

		TareaMigracionErrorDTO error = new TareaMigracionErrorDTO();
		error.setFecha(new Date());
		error.setError(motivo);
		this.agregarError(error);

		if (logger.isDebugEnabled()) {
			logger.debug("crearTareaMigracionError(String) - end");
		}
	}
	
	
	
	
	public void agregarError(TareaMigracionErrorDTO error){
		if (logger.isDebugEnabled()) {
			logger.debug("agregarError(error={}) - start", error);
		}

		tareasError.add(error);

		if (logger.isDebugEnabled()) {
			logger.debug("agregarError(TareaMigracionError) - end");
		}
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTarea() {
		return tarea;
	}

	public void setTarea(String tarea) {
		this.tarea = tarea;
	}

	public String getCodigoReparticionOrigen() {
		return codigoReparticionOrigen;
	}

	public void setCodigoReparticionOrigen(String codigReparticionOrigen) {
		this.codigoReparticionOrigen = codigReparticionOrigen;
	}

	public String getCodigoReparticionDestino() {
		return codigoReparticionDestino;
	}

	public void setCodigoReparticionDestino(String codigoReparticionDestino) {
		this.codigoReparticionDestino = codigoReparticionDestino;
	}

	public String getCodigoSectorOrigen() {
		return codigoSectorOrigen;
	}

	public void setCodigoSectorOrigen(String codigoSectorOrigen) {
		this.codigoSectorOrigen = codigoSectorOrigen;
	}

	public String getCodigoSectorDestino() {
		return codigoSectorDestino;
	}

	public void setCodigoSectorDestino(String codigoSectorDestino) {
		this.codigoSectorDestino = codigoSectorDestino;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Set<TareaMigracionErrorDTO> getTareasError() {
		return tareasError;
	}

	public void setTareasError(Set<TareaMigracionErrorDTO> tareasError) {
		this.tareasError = tareasError;
	}
	
	
	
	
}
