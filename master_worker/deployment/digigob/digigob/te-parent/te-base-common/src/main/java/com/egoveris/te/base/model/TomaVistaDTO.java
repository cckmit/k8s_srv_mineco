package com.egoveris.te.base.model;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TomaVistaDTO {
	private static final Logger logger = LoggerFactory.getLogger(TomaVistaDTO.class);

	
	private String motivo;
	private String usuario;
	private Date fechaFinSusp;
	private Long id;
//	private List<DocumentoRequest> numDocPermitidosList;
	
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	
	public String getMotivo() {
		
		return motivo;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public String getUsuario() {
		
		return usuario;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return this.id;
	}

//	public List<DocumentoRequest> getNumDocPermitidosList() {
//		if (logger.isDebugEnabled()) {
//			logger.debug("getNumDocPermitidosList() - start");
//		}
//		
//		if(numDocPermitidosList == null){
//			numDocPermitidosList = new ArrayList<DocumentoRequest>();
//		}
//
//		if (logger.isDebugEnabled()) {
//			logger.debug("getNumDocPermitidosList() - end - return value={}", numDocPermitidosList);
//		}
//		return numDocPermitidosList;
//	}
//
//	public void setNumDocPermitidosList(List<DocumentoRequest> numDocPermitidosList) {
//		this.numDocPermitidosList = numDocPermitidosList;
//	}

	public Date getFechaFinSusp() {
		return this.fechaFinSusp;
	}

	public void setFechaFinSusp(Date fechaFinSusp) {
		this.fechaFinSusp = fechaFinSusp;
	}
	
}
