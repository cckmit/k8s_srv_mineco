package com.egoveris.deo.model.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author MAGARCES
 *
 */
@XmlRootElement(name = "RequestExternalBuscarDocumentos")
public class RequestExternalBuscarDocumentos implements Serializable {

  private static final long serialVersionUID = -3732660143113784748L;
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
  private HashMap<String, Object> equalsMap;
  private HashMap<String, String> containsMap;
  private String usuario;
  private String acronimo;
  private String nombre;
  private boolean generadosPorMi;
  private boolean generadosPorMiReparticion;
  private String codigoReparticion;
  private String usuarioFirmante;
  private List<MetaDocumento> listaDocMetadata;

  public RequestExternalBuscarDocumentos() {
    equalsMap = new HashMap<String, Object>();
    containsMap = new HashMap<String, String>();
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  public String getUsuario() {
    return this.usuario;
  }

  public void setAcronimo(String acronimo) {
    this.acronimo = acronimo;
  }

  public String getAcronimo() {
    return this.acronimo;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getNombre() {

    return this.nombre;
  }

  public void setGeneradosPorMi(boolean generadosPorMi) {
    this.generadosPorMi = generadosPorMi;
  }

  public boolean getGeneradosPorMi() {
    return this.generadosPorMi;
  }

  public void setGeneradosPorMiReparticion(boolean generadosPorMiReparticion) {
    this.generadosPorMiReparticion = generadosPorMiReparticion;
  }

  public boolean getGeneradosPorMiReparticion() {
    return this.generadosPorMiReparticion;
  }

  public void setCodigoReparticion(String codigoReparticion) {
    this.codigoReparticion = codigoReparticion;
  }

  public String getCodigoReparticion() {
    return this.codigoReparticion;
  }

  public void setListaDocMetadata(List<MetaDocumento> listaDocMetadata) {
    this.listaDocMetadata = listaDocMetadata;
  }

  public List<MetaDocumento> getListaDocMetadata() {
    if (this.listaDocMetadata == null) {
      this.listaDocMetadata = new ArrayList<MetaDocumento>();
    }
    return this.listaDocMetadata;
  }

  public void addDynamicField(String key, Object value) {
    equalsMap.put(key + sufix(value), value);
  }

  public void addContainsDynamicField(String key, String value) {
    containsMap.put(key + sufix(value), value);
  }

  public void addLegacyField(String key, String value) {
    containsMap.put(key + DYN_FIELD_SUF_LEG, value);
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
    equalsMap.put(FIELD_NRO_SADE, nroSade);
    this.nroSade = nroSade;
  }

  public String getNroSadePapel() {
    return nroSadePapel;
  }

  public void setNroSadePapel(String nroSadePapel) {
    equalsMap.put(FIELD_NRO_SADE_PAPEL, nroSadePapel);
    this.nroSadePapel = nroSadePapel;
  }

  public String getNroEspecialSade() {
    return nroEspecialSade;
  }

  public void setNroEspecialSade(String nroEspecialSade) {
    equalsMap.put(FIELD_ESP_SADE, nroEspecialSade);
    this.nroEspecialSade = nroEspecialSade;
  }

  public String getUsuarioGenerador() {
    return usuarioGenerador;
  }

  public void setUsuarioGenerador(String usuarioGenerador) {
    equalsMap.put(FIELD_USUARIO_GENERADOR, usuarioGenerador);
    this.usuarioGenerador = usuarioGenerador;
  }

  public String getReparticion() {
    return reparticion;
  }

  public void setReparticion(String reparticion) {
    equalsMap.put(FIELD_REPARTICION, reparticion);
    this.reparticion = reparticion;
  }

  public String getReferencia() {
    return referencia;
  }

  public void setReferencia(String referencia) {
    equalsMap.put(FIELD_REFERENCIA, referencia);
    this.referencia = referencia;
  }

  public String getTipoDocAcr() {
    return tipoDocAcr;
  }

  public void setTipoDocAcr(String tipoDocAcr) {
    equalsMap.put(FIELD_TIPO_DOC_ACR, tipoDocAcr);
    this.tipoDocAcr = tipoDocAcr;
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
    equalsMap.put(FIELD_ACTUACION_ACR, actuacionAcr);
    this.actuacionAcr = actuacionAcr;
  }

  public Integer getAnioDoc() {
    return anioDoc;
  }

  public void setAnioDoc(Integer anioDoc) {
    equalsMap.put(FIELD_ANIO_DOC, anioDoc);
    this.anioDoc = anioDoc;
  }

  public Long getNroDoc() {
    return nroDoc;
  }

  public void setNroDoc(Long nroDoc) {
    equalsMap.put(FIELD_NRO_DOC, nroDoc);
    this.nroDoc = nroDoc;
  }

  public void setFechaDesde(Date fechaDesde) {
    this.fechaDesde = fechaDesde;
  }

  public Date getFechaHasta() {
    return this.fechaHasta;
  }

  public Date getFechaDesde() {
    return this.fechaDesde;
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
    this.criteria = ORDER_DESC;
  }

  public void setAscCriteria() {
    this.criteria = ORDER_ASC;
  }

  public String getTipoBusqueda() {
    return tipoBusqueda;
  }

  public void setTipoBusqueda(String tipoBusqueda) {
    this.tipoBusqueda = tipoBusqueda;
  }

  private static String sufix(Object value) {
    if (value instanceof String) {
      return DYN_FIELD_SUF_STR;
    } else if (value instanceof Date) {
      return DYN_FIELD_SUF_DATE;
    } else if (value instanceof Integer || value instanceof Long) {
      return DYN_FIELD_SUF_INT;
    } else if (value instanceof Boolean) {
      return DYN_FIELD_SUF_BOOLEAN;
    } else if (value instanceof Double) {
      return DYN_FIELD_SUF_DOUBLE;
    }
    return null;
  }

}
