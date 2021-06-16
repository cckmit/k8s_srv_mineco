package com.egoveris.deo.model.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class TipoDocumentoIDDTO implements Serializable {

	private static final long serialVersionUID = -1339224212211100561L;
	private Integer id;
	private String nombre;
	private String version;
	private String acronimo;
	private String descripcion;
	private Boolean esEspecial = false;
	private Boolean esManual = false;
	private Boolean esAutomatica = false;
	private Boolean tieneToken = false;
	private Boolean tieneTemplate = false;
	private Boolean permiteEmbebidos = false;
	private Boolean esNotificable = false;
	private Boolean esComunicable = false;
	private Integer tipoProduccion;
	private String estado;
	private Integer idTipoDocumentoSade;
	private String codigoTipoDocumentoSade;
	private Boolean esFirmaConjunta = false;
	private Boolean esConfidencial = false;
	private Boolean esOculto = false;		
	private Boolean estaHabilitado = true;
	private Boolean esFirmaExterna = false;
	private FamiliaTipoDocumentoIDDTO familia;
	private Boolean tieneAviso = true;	
	private Integer sizeImportado = null;
	

	/**
	 * Indica que pueden seguirse creando instancias de este tipo de documento.
	 */
	public static final String ESTADO_ACTIVO = "ALTA";
	public static final String ESTADO_INACTIVO = "BAJA";

	public TipoDocumentoIDDTO() {
		super();
	}

	public TipoDocumentoIDDTO(Integer id, String nombre, String version,
			String acronimo, String descripcion, Boolean esEspecial,
			Boolean esManual, Boolean esAutomatica, Boolean tieneToken,
			Boolean tieneTemplate, Boolean permiteEmbebidos,
			Boolean esNotificable, Integer tipoProduccion, String estado,
			Integer idTipoDocumentoSade, String codigoTipoDocumentoSade,
			Boolean esFirmaConjunta, Boolean esConfidencial,
			Set<ReparticionHabilitadaDTO> listaReparticiones,
			List<MetadataDTO> listaDatosVariables, Boolean estaHabilitado,
			Boolean esFirmaExterna, FamiliaTipoDocumentoIDDTO familia,
			Boolean tieneAviso,
			Set<TipoDocumentoEmbebidosDTO> tipoDocumentoEmbebidos,
			Set<TipoDocumentoTemplateDTO> listaTemplates, Integer sizeImportado, Boolean esOculto, Boolean esComunicable) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.version = version;
		this.acronimo = acronimo;
		this.descripcion = descripcion;
		this.esEspecial = esEspecial;
		this.esManual = esManual;
		this.esAutomatica = esAutomatica;
		this.tieneToken = tieneToken;
		this.tieneTemplate = tieneTemplate;
		this.permiteEmbebidos = permiteEmbebidos;
		this.esNotificable = esNotificable;
		this.tipoProduccion = tipoProduccion;
		this.estado = estado;
		this.idTipoDocumentoSade = idTipoDocumentoSade;
		this.codigoTipoDocumentoSade = codigoTipoDocumentoSade;
		this.esFirmaConjunta = esFirmaConjunta;
		this.esConfidencial = esConfidencial;
	
		this.estaHabilitado = estaHabilitado;
		this.esFirmaExterna = esFirmaExterna;
		this.familia = familia;
		this.tieneAviso = tieneAviso;
		
		this.sizeImportado = sizeImportado;
		this.esOculto = esOculto;
		this.esComunicable = esComunicable;
	}



	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public String toString() {
		return this.acronimo + " - " + this.version;
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
		setEstaHabilitado(getEstado().compareTo(ESTADO_ACTIVO) == 0);
		return estaHabilitado;
	}

	public void setEstaHabilitado(Boolean estaHabilitado) {
		this.estaHabilitado = estaHabilitado;
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

	

	public FamiliaTipoDocumentoIDDTO getFamilia() {
		return familia;
	}

	public void setFamilia(FamiliaTipoDocumentoIDDTO familia) {
		this.familia = familia;
	}

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

	

	public void setSizeImportado(Integer sizeImportado) {
		this.sizeImportado = sizeImportado;
	}

	public Integer getSizeImportado() {
		return sizeImportado;
	}

	public void setEsOculto(Boolean esOculto) {
		this.esOculto = esOculto;
	}

	public Boolean getEsOculto() {
		return esOculto;
	}

	public Boolean getEsComunicable() {
		return esComunicable;
	}

	public void setEsComunicable(Boolean esComunicable) {
		this.esComunicable = esComunicable;
	}

}

