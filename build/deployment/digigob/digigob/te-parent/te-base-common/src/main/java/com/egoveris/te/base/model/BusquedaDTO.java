package com.egoveris.te.base.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BusquedaDTO implements Serializable {
	private static final Logger logger = LoggerFactory.getLogger(BusquedaDTO.class);


	private static final long serialVersionUID = 2370735991049262092L;
	
	public static final String ORDER_DESC = "DESC";
	public static final String ORDER_ASC = "ASC";
	public static final String FIELD_FECHA_CREACION = "fecha_creacion";
	public static final String FIELD_USUARIO_GENERADOR = "usuario_generador";
	public static final String FIELD_USUARIO_FIRMANTE = "usuario_firmante";
	
	private static final String FIELD_NRO_SADE = "nro_sade";
	private static final String FIELD_NRO_SADE_PAPEL = "nro_sade_papel";
	private static final String FIELD_ESP_SADE = "nro_especial_sade";
	private static final String FIELD_REPARTICION = "reparticion_generador";
	private static final String FIELD_REFERENCIA = "referencia";
	private static final String FIELD_TIPO_DOC_ACR = "tipo_doc_acr";
	private static final String FIELD_TIPO_DOC_NOMBRE = "tipo_doc_nombre";
	private static final String FIELD_ANIO_DOC = "anio_doc";
	private static final String FIELD_NRO_DOC = "nro_doc";
	private static final String FIELD_ACTUACION_ACR = "actuacion_acr";

	public static final String DYN_FIELD_SUF_STR = "_df_str";
	public static final String DYN_FIELD_SUF_LEG = "_df_leg";
	public static final String DYN_FIELD_SUF_INT = "_df_int";
	public static final String DYN_FIELD_SUF_DATE = "_df_date";
	public static final String DYN_FIELD_SUF_BOOLEAN = "_df_boolean";
	public static final String DYN_FIELD_SUF_DOUBLE = "_df_double";

	
	
	private String nroSade;
	private String nroSadePapel;
	private String nroEspecialSade;
	private Date fechaDesde;
	private Date fechaHasta;
	private String usuarioGenerador;
	private String usuarioFirmante;
	private String reparticion;
	private String referencia;
	private String tipoDocAcr;
	private String tipoDocDescr;
	private String actuacionAcr;
	private Integer anioDoc;
	private Long nroDoc;
	private int pageIndex = 0;
	private int pageSize = 10;
	private String criteria = ORDER_DESC;
	private int indexSortColumn = 2;
	private String tipoBusqueda;

	private transient Map<String, Object> equalsMap;
	private Map<String, String> containsMap;

	public BusquedaDTO() {
		equalsMap = new HashMap<>();
		containsMap = new HashMap<>();
	}

	public void addDynamicField(String key, Object value) {
		if (logger.isDebugEnabled()) {
			logger.debug("addDynamicField(key={}, value={}) - start", key, value);
		}

		equalsMap.put(key + sufix(value), value);

		if (logger.isDebugEnabled()) {
			logger.debug("addDynamicField(String, Object) - end");
		}
	}
	
	public void addContainsDynamicField(String key, String value) {
		if (logger.isDebugEnabled()) {
			logger.debug("addContainsDynamicField(key={}, value={}) - start", key, value);
		}

		containsMap.put(key + sufix(value), value);

		if (logger.isDebugEnabled()) {
			logger.debug("addContainsDynamicField(String, String) - end");
		}
	}

	public void addLegacyField(String key, String value) {
		if (logger.isDebugEnabled()) {
			logger.debug("addLegacyField(key={}, value={}) - start", key, value);
		}

		containsMap.put(key + DYN_FIELD_SUF_LEG, value);

		if (logger.isDebugEnabled()) {
			logger.debug("addLegacyField(String, String) - end");
		}
	}

	public Map<String, Object> getEqualsMap() {
		return equalsMap;
	}
	
	public Map<String, String> getContainsMap() {
		return containsMap;
	}

	public String getNroSade() {
		return nroSade;
	}

	public void setNroSade(String nroSade) {
		if (logger.isDebugEnabled()) {
			logger.debug("setNroSade(nroSade={}) - start", nroSade);
		}

		equalsMap.put(FIELD_NRO_SADE, nroSade);
		this.nroSade = nroSade;

		if (logger.isDebugEnabled()) {
			logger.debug("setNroSade(String) - end");
		}
	}

	public String getNroSadePapel() {
		return nroSadePapel;
	}

	public void setNroSadePapel(String nroSadePapel) {
		if (logger.isDebugEnabled()) {
			logger.debug("setNroSadePapel(nroSadePapel={}) - start", nroSadePapel);
		}

		equalsMap.put(FIELD_NRO_SADE_PAPEL, nroSadePapel);
		this.nroSadePapel = nroSadePapel;

		if (logger.isDebugEnabled()) {
			logger.debug("setNroSadePapel(String) - end");
		}
	}

	public String getNroEspecialSade() {
		return nroEspecialSade;
	}

	public void setNroEspecialSade(String nroEspecialSade) {
		if (logger.isDebugEnabled()) {
			logger.debug("setNroEspecialSade(nroEspecialSade={}) - start", nroEspecialSade);
		}

		equalsMap.put(FIELD_ESP_SADE, nroEspecialSade);
		this.nroEspecialSade = nroEspecialSade;

		if (logger.isDebugEnabled()) {
			logger.debug("setNroEspecialSade(String) - end");
		}
	}

	public String getUsuarioGenerador() {
		return usuarioGenerador;
	}

	public void setUsuarioGenerador(String usuarioGenerador) {
		if (logger.isDebugEnabled()) {
			logger.debug("setUsuarioGenerador(usuarioGenerador={}) - start", usuarioGenerador);
		}

		equalsMap.put(FIELD_USUARIO_GENERADOR, usuarioGenerador);
		this.usuarioGenerador = usuarioGenerador;

		if (logger.isDebugEnabled()) {
			logger.debug("setUsuarioGenerador(String) - end");
		}
	}

	public String getReparticion() {
		return reparticion;
	}

	public void setReparticion(String reparticion) {
		if (logger.isDebugEnabled()) {
			logger.debug("setReparticion(reparticion={}) - start", reparticion);
		}

		equalsMap.put(FIELD_REPARTICION, reparticion);
		this.reparticion = reparticion;

		if (logger.isDebugEnabled()) {
			logger.debug("setReparticion(String) - end");
		}
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		if (logger.isDebugEnabled()) {
			logger.debug("setReferencia(referencia={}) - start", referencia);
		}

		equalsMap.put(FIELD_REFERENCIA, referencia);
		this.referencia = referencia;

		if (logger.isDebugEnabled()) {
			logger.debug("setReferencia(String) - end");
		}
	}

	public String getTipoDocAcr() {
		return tipoDocAcr;
	}

	public void setTipoDocAcr(String tipoDocAcr) {
		if (logger.isDebugEnabled()) {
			logger.debug("setTipoDocAcr(tipoDocAcr={}) - start", tipoDocAcr);
		}

		equalsMap.put(FIELD_TIPO_DOC_ACR, tipoDocAcr);
		this.tipoDocAcr = tipoDocAcr;

		if (logger.isDebugEnabled()) {
			logger.debug("setTipoDocAcr(String) - end");
		}
	}

	public String getTipoDocDescr() {
		return tipoDocDescr;
	}

	public void setTipoDocDescr(String tipoDocDescr) {
		this.tipoDocDescr = tipoDocDescr;
	}

	public String getActuacionAcr() {
		return actuacionAcr;
	}

	public void setActuacionAcr(String actuacionAcr) {
		if (logger.isDebugEnabled()) {
			logger.debug("setActuacionAcr(actuacionAcr={}) - start", actuacionAcr);
		}

		equalsMap.put(FIELD_ACTUACION_ACR, actuacionAcr);
		this.actuacionAcr = actuacionAcr;

		if (logger.isDebugEnabled()) {
			logger.debug("setActuacionAcr(String) - end");
		}
	}

	public Integer getAnioDoc() {
		return anioDoc;
	}

	public void setAnioDoc(Integer anioDoc) {
		if (logger.isDebugEnabled()) {
			logger.debug("setAnioDoc(anioDoc={}) - start", anioDoc);
		}

		equalsMap.put(FIELD_ANIO_DOC, anioDoc);
		this.anioDoc = anioDoc;

		if (logger.isDebugEnabled()) {
			logger.debug("setAnioDoc(Integer) - end");
		}
	}

	public Long getNroDoc() {
		return nroDoc;
	}

	public void setNroDoc(Long nroDoc) {
		if (logger.isDebugEnabled()) {
			logger.debug("setNroDoc(nroDoc={}) - start", nroDoc);
		}

		equalsMap.put(FIELD_NRO_DOC, nroDoc);
		this.nroDoc = nroDoc;

		if (logger.isDebugEnabled()) {
			logger.debug("setNroDoc(Long) - end");
		}
	}

	public Date getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public Date getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public String getUsuarioFirmante() {
		return usuarioFirmante;
	}

	public void setUsuarioFirmante(String usuarioFirmante) {
		this.usuarioFirmante = usuarioFirmante;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getSortColumn() {
		if (logger.isDebugEnabled()) {
			logger.debug("getSortColumn() - start");
		}

		switch (indexSortColumn) {
		case 0:
			return FIELD_NRO_SADE;
		case 1:
			return FIELD_ESP_SADE;
		case 2:
			return FIELD_FECHA_CREACION;
		case 3:
			return FIELD_USUARIO_GENERADOR;
		case 4:
			return FIELD_REFERENCIA;
		case 5:
			return FIELD_TIPO_DOC_NOMBRE;
		default:
			return FIELD_FECHA_CREACION;
		}
	}

	public void setSortColumn(int indexSortColumn) {
		this.indexSortColumn = indexSortColumn;
	}

	public int getIndexSortColumn() {
		return indexSortColumn;
	}

	public String getCriteria() {
		return this.criteria;
	}

	public void setDescCriteria() {
		if (logger.isDebugEnabled()) {
			logger.debug("setDescCriteria() - start");
		}

		this.criteria = ORDER_DESC;

		if (logger.isDebugEnabled()) {
			logger.debug("setDescCriteria() - end");
		}
	}

	public void setAscCriteria() {
		if (logger.isDebugEnabled()) {
			logger.debug("setAscCriteria() - start");
		}

		this.criteria = ORDER_ASC;

		if (logger.isDebugEnabled()) {
			logger.debug("setAscCriteria() - end");
		}
	}

	public String getTipoBusqueda() {
		return tipoBusqueda;
	}

	public void setTipoBusqueda(String tipoBusqueda) {
		this.tipoBusqueda = tipoBusqueda;
	}
	
	private String sufix(Object value) {
		if (logger.isDebugEnabled()) {
			logger.debug("sufix(value={}) - start", value);
		}

		if (value instanceof String) {
			if (logger.isDebugEnabled()) {
				logger.debug("sufix(Object) - end - return value={}", DYN_FIELD_SUF_STR);
			}
			return DYN_FIELD_SUF_STR;
		} else if (value instanceof Date) {
			if (logger.isDebugEnabled()) {
				logger.debug("sufix(Object) - end - return value={}", DYN_FIELD_SUF_DATE);
			}
			return DYN_FIELD_SUF_DATE;
		} else if (value instanceof Integer || value instanceof Long) {
			if (logger.isDebugEnabled()) {
				logger.debug("sufix(Object) - end - return value={}", DYN_FIELD_SUF_INT);
			}
			return DYN_FIELD_SUF_INT;
		} else if (value instanceof Boolean) {
			if (logger.isDebugEnabled()) {
				logger.debug("sufix(Object) - end - return value={}", DYN_FIELD_SUF_BOOLEAN);
			}
			return DYN_FIELD_SUF_BOOLEAN;
		} else if (value instanceof Double) {
			if (logger.isDebugEnabled()) {
				logger.debug("sufix(Object) - end - return value={}", DYN_FIELD_SUF_DOUBLE);
			}
			return DYN_FIELD_SUF_DOUBLE;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("sufix(Object) - end - return value={null}");
		}
		return null;
	}
}
