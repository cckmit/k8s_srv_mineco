package com.egoveris.te.base.model;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Esta es la clase TipoDocumento del proyecto Gedo que ha sido adaptada para usarse en EE.
 * la misma es utilizada para mejorar le eficiencia en las consultas a la base de datos de Gedo
 * 
 * @author Jorge Federico Flores (joflores)
 * @date: 07/10/2014
 */
public class TipoDocumentoGedoDTO implements Serializable {
	private static final Logger logger = LoggerFactory.getLogger(TipoDocumentoGedoDTO.class);

	private static final long serialVersionUID = -1339224212211100561L;
	private Long id;
	private String nombre;
	private String acronimo;
	private String descripcion;
	private Boolean esEspecial = false;
	private Boolean esManual = false;
	private Boolean esAutomatica = false;
	private Boolean tieneToken = false;
	private Boolean tieneTemplate = false;
	private Integer tipoProduccion;
	private Boolean permiteEmbebidos = false;
	private Boolean esNotificable = false;
	private Boolean esFirmaExterna = false;
	private Integer idTipoDocumentoSade;
	private String version;
	private String codigoTipoDocumentoSade;
	private Boolean esFirmaConjunta = false; 
	private Boolean esConfidencial = false; 
	private Boolean tieneAviso = true;
	private String estado;
	private Boolean estaHabilitado = true; 
	private Boolean estaDeshabilitado = true; 
	
	/**
	 * Indica que pueden seguirse creando instancias de este tipo de documento.
	 */
	public static final String ESTADO_ACTIVO = "ALTA";
	public static final String ESTADO_INACTIVO = "BAJA";	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getAcronimo() {
		return acronimo;
	}

	public void setAcronimo(String acronimo) {
		this.acronimo = acronimo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Boolean getEsEspecial() {
		return esEspecial;
	}

	public void setEsEspecial(Boolean esEspecial) {
		this.esEspecial = esEspecial;
	}

	public Boolean getTieneToken() {
		return tieneToken;
	}

	public void setTieneToken(Boolean tieneToken) {
		this.tieneToken = tieneToken;
	}

	public Boolean getTieneTemplate() {
		return tieneTemplate;
	}

	public void setTieneTemplate(Boolean tieneTemplate) {
		this.tieneTemplate = tieneTemplate;
	}

	public Boolean getPermiteEmbebidos() {
		return permiteEmbebidos;
	}

	public void setPermiteEmbebidos(Boolean permiteEmbebidos) {
		this.permiteEmbebidos = permiteEmbebidos;
	}

	public Boolean getEsNotificable() {
		return esNotificable;
	}

	public void setEsNotificable(Boolean esNotificable) {
		this.esNotificable = esNotificable;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getEstado() {
		return estado;
	}
	
	public String toString(){
		return this.acronimo+" - "+this.nombre;
	}

	public Integer getIdTipoDocumentoSade() {
		return idTipoDocumentoSade;
	}

	public void setIdTipoDocumentoSade(Integer idTipoDocumentoSade) {
		this.idTipoDocumentoSade = idTipoDocumentoSade;
	}

	public String getCodigoTipoDocumentoSade() {
		return codigoTipoDocumentoSade;
	}

	public void setCodigoTipoDocumentoSade(String codigoTipoDocumentoSade) {
		this.codigoTipoDocumentoSade = codigoTipoDocumentoSade;
	}

	public Boolean getEsFirmaConjunta() {
		return esFirmaConjunta;
	}	

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void setEsFirmaConjunta(Boolean esFirmaConjunta) {
		this.esFirmaConjunta = esFirmaConjunta;
	}

	public Boolean getEsConfidencial() {
		return esConfidencial;
	}

	public void setEsConfidencial(Boolean esConfidencial) {
		this.esConfidencial = esConfidencial;
	}

	public Boolean getEstaHabilitado() {
		if (logger.isDebugEnabled()) {
			logger.debug("getEstaHabilitado() - start");
		}

		setEstaHabilitado(getEstado().compareTo(ESTADO_ACTIVO) == 0);

		if (logger.isDebugEnabled()) {
			logger.debug("getEstaHabilitado() - end - return value={}", estaHabilitado);
		}
		return estaHabilitado;
	}
	
	public void setEstaHabilitado(Boolean estaHabilitado) {
		this.estaHabilitado = estaHabilitado;
	}
	public Boolean getEstaDeshabilitado() {
		if (logger.isDebugEnabled()) {
			logger.debug("getEstaDeshabilitado() - start");
		}

		setEstaDeshabilitado(getEstado().compareTo(ESTADO_INACTIVO) == 0);

		if (logger.isDebugEnabled()) {
			logger.debug("getEstaDeshabilitado() - end - return value={}", estaDeshabilitado);
		}
		return estaDeshabilitado;
	}

	public void setEstaDeshabilitado(Boolean estaDeshabilitado) {
		this.estaDeshabilitado = estaDeshabilitado;
	}

	public Boolean getEsManual() {
		return esManual;
	}

	public void setEsManual(Boolean esManual) {
		this.esManual = esManual;
	}

	public Boolean getEsAutomatica() {
		return esAutomatica;
	}

	public void setEsAutomatica(Boolean esAutomatica) {
		this.esAutomatica = esAutomatica;
	}

	public void setEsFirmaExterna(Boolean esFirmaExterna) {
		this.esFirmaExterna = esFirmaExterna;
	}

	public Boolean getEsFirmaExterna() {
		return esFirmaExterna;
	}

//	public FamiliaTipoDocumentoGedo getFamilia() {
//		return familia;
//	}
//
//	public void setFamilia(FamiliaTipoDocumentoGedo familia) {
//		this.familia = familia;
//	}
	
	public Boolean getTieneAviso() {
		return tieneAviso;
	}

	public void setTieneAviso(Boolean tieneAviso) {
		this.tieneAviso = tieneAviso;
	}
	
	public Integer getTipoProduccion() {
		return tipoProduccion;
	}

	public void setTipoProduccion(Integer tipoProduccion) {
		this.tipoProduccion = tipoProduccion;
	}
}
