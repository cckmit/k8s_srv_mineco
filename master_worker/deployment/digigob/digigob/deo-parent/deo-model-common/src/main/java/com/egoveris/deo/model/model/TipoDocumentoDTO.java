package com.egoveris.deo.model.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TipoDocumentoDTO implements Serializable {

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
	private Boolean esDobleFactor = false;
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
	private Set<ReparticionHabilitadaDTO> listaReparticiones;
	private List<MetadataDTO> listaDatosVariables = new ArrayList<>();
	private Boolean estaHabilitado = true;
	private Boolean esFirmaExterna = false;
	private Boolean esFirmaExternaConEncabezado = false;
	private FamiliaTipoDocumentoDTO familia;
	private Boolean tieneAviso = true;
	private Date fechaCreacion;
	private String usuarioCreador;
	private Set<TipoDocumentoEmbebidosDTO> tipoDocumentoEmbebidos = new HashSet<>();
	private Set<TipoDocumentoTemplateDTO> listaTemplates = new HashSet<>();
	private Integer sizeImportado = null;
	private Boolean resultado = false;
	private Boolean esPublicable = false;

	/**
	 * Indica que pueden seguirse creando instancias de este tipo de documento.
	 */
	public static final String ESTADO_ACTIVO = "ALTA";
	public static final String ESTADO_INACTIVO = "BAJA";

	public TipoDocumentoDTO() {
		super();
	}

	public TipoDocumentoDTO(Integer id, String nombre, String version, String acronimo, String descripcion,
			Boolean esEspecial, Boolean esManual, Boolean esAutomatica, Boolean tieneToken, Boolean esDobleFactor, Boolean tieneTemplate,
			Boolean permiteEmbebidos, Boolean esNotificable, Integer tipoProduccion, String estado,
			Integer idTipoDocumentoSade, String codigoTipoDocumentoSade, Boolean esFirmaConjunta,
			Boolean esConfidencial, Set<ReparticionHabilitadaDTO> listaReparticiones,
			List<MetadataDTO> listaDatosVariables, Boolean estaHabilitado, Boolean esFirmaExterna,
			Boolean esFirmaExternaConEncabezado, FamiliaTipoDocumentoDTO familia, Boolean tieneAviso,
			Set<TipoDocumentoEmbebidosDTO> tipoDocumentoEmbebidos, Set<TipoDocumentoTemplateDTO> listaTemplates,
			Integer sizeImportado, Boolean esOculto, Boolean esComunicable, Boolean resultado,
			Boolean esPublicable) {
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
		this.esDobleFactor = esDobleFactor;
		this.tieneTemplate = tieneTemplate;
		this.permiteEmbebidos = permiteEmbebidos;
		this.esNotificable = esNotificable;
		this.tipoProduccion = tipoProduccion;
		this.estado = estado;
		this.idTipoDocumentoSade = idTipoDocumentoSade;
		this.codigoTipoDocumentoSade = codigoTipoDocumentoSade;
		this.esFirmaConjunta = esFirmaConjunta;
		this.esConfidencial = esConfidencial;
		this.listaReparticiones = listaReparticiones;
		this.listaDatosVariables = listaDatosVariables;
		this.estaHabilitado = estaHabilitado;
		this.esFirmaExterna = esFirmaExterna;
		this.esFirmaExternaConEncabezado = esFirmaExternaConEncabezado;
		this.familia = familia;
		this.tieneAviso = tieneAviso;
		this.tipoDocumentoEmbebidos = tipoDocumentoEmbebidos;
		this.listaTemplates = listaTemplates;
		this.sizeImportado = sizeImportado;
		this.esOculto = esOculto;
		this.esComunicable = esComunicable;
		this.resultado = resultado;
		this.esPublicable = esPublicable;
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
	
	public Boolean getEsDobleFactor() {
		return esDobleFactor;
	}

	public void setEsDobleFactor(Boolean esDobleFactor) {
		this.esDobleFactor = esDobleFactor;
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

	@Override
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

	public Set<ReparticionHabilitadaDTO> getListaReparticiones() {
		return listaReparticiones;
	}

	public void setListaReparticiones(Set<ReparticionHabilitadaDTO> listaReparticiones) {
		this.listaReparticiones = listaReparticiones;
	}

	public List<MetadataDTO> getListaDatosVariables() {
		return listaDatosVariables;
	}

	public void setListaDatosVariables(List<MetadataDTO> listaDatosVariables) {
		this.listaDatosVariables = listaDatosVariables;
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

	public FamiliaTipoDocumentoDTO getFamilia() {
		return familia;
	}

	public void setFamilia(FamiliaTipoDocumentoDTO familia) {
		this.familia = familia;
	}

	public Boolean getTieneAviso() {
		return tieneAviso;
	}

	public void setTieneAviso(Boolean tieneAviso) {
		this.tieneAviso = tieneAviso;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getUsuarioCreador() {
		return usuarioCreador;
	}

	public void setUsuarioCreador(String usuarioCreador) {
		this.usuarioCreador = usuarioCreador;
	}

	public Set<TipoDocumentoEmbebidosDTO> getTipoDocumentoEmbebidos() {
		return tipoDocumentoEmbebidos;
	}

	public void setTipoDocumentoEmbebidos(Set<TipoDocumentoEmbebidosDTO> tipoDocumentoEmbebidos) {
		this.tipoDocumentoEmbebidos = tipoDocumentoEmbebidos;
	}

	public Integer getTipoProduccion() {
		return tipoProduccion;
	}

	public void setTipoProduccion(Integer tipoProduccion) {
		this.tipoProduccion = tipoProduccion;
	}

	public Set<TipoDocumentoTemplateDTO> getListaTemplates() {
		return listaTemplates;
	}

	public void setListaTemplates(Set<TipoDocumentoTemplateDTO> listaTemplates) {
		this.listaTemplates = listaTemplates;
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

	public Boolean getEsFirmaExternaConEncabezado() {
		return esFirmaExternaConEncabezado;
	}

	public void setEsFirmaExternaConEncabezado(Boolean esFirmaExternaConEncabezado) {
		this.esFirmaExternaConEncabezado = esFirmaExternaConEncabezado;
	}

	public Boolean getResultado() {
		return resultado;
	}

	public void setResultado(Boolean resultado) {
		this.resultado = resultado;
	}

	public Boolean getEsPublicable() {
		return esPublicable;
	}

	public void setEsPublicable(Boolean esPublicable) {
		this.esPublicable = esPublicable;
	}

}
