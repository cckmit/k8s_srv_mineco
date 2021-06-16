package com.egoveris.te.base.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.egoveris.deo.model.model.ResponseTipoDocumento;

/*
 * Esta clase es un DTO ampliado de @See TipoDocumento
 * Se utiliza en al menos @See TipoDocumentoServiceImpl.obtenerTipoDocumento
 * @See DocumentoDTO tiene un TipoDocumentoDTO
 * 
 */
public class TipoDocumentoDTO implements Serializable {
  private static final Logger logger = LoggerFactory.getLogger(TipoDocumentoDTO.class);

  private static final long serialVersionUID = -213335252860599444L;
  private Long id;
  private String nombre;
  private String acronimo;
  private String descripcion;
  private Boolean esEspecial = false;
  private Boolean esManual;

  private Boolean tieneToken;
  private Boolean tieneTemplate;

  private String tipoProduccion;
  private String codigoTipoDocumentoSade;
  private Boolean esFirmaConjunta;
  private Boolean esConfidencial;
  private List<MetadataDTO> listaDatosVariables = new ArrayList<MetadataDTO>();
  private String estado;
  private Boolean esFirmaExterna;
  private Boolean esNotificable = false;

  private Integer idTipoDocumentoSade;

  private String descripcionTipoDocumentoSade;
  private String usoEnEE;
  private transient Integer idTipoDocumentoGedo;
  private String idFormulario;
  private String descripcionGedo;
  private FamiliaTipoDocumentoDTO familia;
  private Set<TipoDocumentoTemplateDTO> listaTemplates = new HashSet<TipoDocumentoTemplateDTO>();
  private Boolean resultado;
  
  public static final String TEMPLATE = "TEMPLATE";

  public TipoDocumentoDTO() {

  }

  public TipoDocumentoDTO(String seleccionarTodos) {
    this.nombre = "";
    this.acronimo = seleccionarTodos;
    this.codigoTipoDocumentoSade = "";
  }

	public TipoDocumentoDTO(ResponseTipoDocumento tipoDocumento) {

		if (null != tipoDocumento.getId()) {
			this.id = Long.valueOf(tipoDocumento.getId());
		}
		this.nombre = tipoDocumento.getNombre();
		this.acronimo = tipoDocumento.getAcronimo();
		this.descripcion = tipoDocumento.getDescripcion();
		this.esEspecial = tipoDocumento.getEsEspecial();
		this.esManual = tipoDocumento.getEsManual();
		this.tieneToken = tipoDocumento.getTieneToken();
		this.tieneTemplate = tipoDocumento.getTieneTemplate();
		this.tipoProduccion = tipoDocumento.getTipoProduccion();
		this.codigoTipoDocumentoSade = tipoDocumento
				.getCodigoTipoDocumentoSade();
		this.esFirmaConjunta = tipoDocumento.getEsFirmaConjunta();
		this.esConfidencial = tipoDocumento.getEsConfidencial();
		this.listaDatosVariables = new ArrayList<MetadataDTO>();
		this.estado = tipoDocumento.getEstado();
		this.esFirmaExterna = tipoDocumento.getEsFirmaExterna();
		this.esNotificable = tipoDocumento.getEsNotificable();
		this.idTipoDocumentoSade = tipoDocumento.getIdTipoDocumentoSade();
		if (null != tipoDocumento.getIdTipoDocumentoSade()) {
			this.descripcionTipoDocumentoSade = tipoDocumento
					.getIdTipoDocumentoSade().toString();
		}
		this.idTipoDocumentoGedo = tipoDocumento.getIdTipoDocumentoSade();
		this.idFormulario = tipoDocumento.getIdFormulario();
		this.descripcionGedo = tipoDocumento.getDescripcion();
		if (StringUtils.isBlank(tipoDocumento.getFamilia())) {
			FamiliaTipoDocumentoDTO familia = new FamiliaTipoDocumentoDTO(
					tipoDocumento.getFamilia());
			this.familia = familia;
		}
		FamiliaTipoDocumentoDTO familia = new FamiliaTipoDocumentoDTO(
				tipoDocumento.getFamilia());
		this.familia = familia;
		this.listaTemplates = new HashSet<TipoDocumentoTemplateDTO>();
		this.resultado = tipoDocumento.getResultado();

	}

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

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

  public Boolean getEsEspecial() {
    return esEspecial;
  }

  public void setEsEspecial(Boolean esEspecial) {
    this.esEspecial = esEspecial;
  }

  public Integer getIdTipoDocumentoSade() {
    return idTipoDocumentoSade;
  }

  public void setIdTipoDocumentoSade(Integer idTipoDocumentoSade) {
    this.idTipoDocumentoSade = idTipoDocumentoSade;
  }

  public void setCodigoTipoDocumentoSade(String codigoTipoDocumentoSade) {
    this.codigoTipoDocumentoSade = codigoTipoDocumentoSade;
  }

  public String getCodigoTipoDocumentoSade() {
    return codigoTipoDocumentoSade;
  }

  public String toStringGedo() {
    if (logger.isDebugEnabled()) {
      logger.debug("toStringGedo() - start");
    }

    if (this.nombre == " ") {
      if (logger.isDebugEnabled()) {
        logger.debug("toStringGedo() - end - return value={}", this.nombre);
      }
      return this.nombre;
    } else {
      String returnString = this.acronimo + " - " + this.nombre;
      if (logger.isDebugEnabled()) {
        logger.debug("toStringGedo() - end - return value={}", returnString);
      }
      return returnString;
    }
  }

  public String toString() {

    if (this.codigoTipoDocumentoSade == " " && this.descripcionTipoDocumentoSade == " ") {
      return this.codigoTipoDocumentoSade;
    } else {
      return this.codigoTipoDocumentoSade + " - " + this.descripcionTipoDocumentoSade;
    }
  }

  public boolean esIgual(String nombre) {
    if (logger.isDebugEnabled()) {
      logger.debug("esIgual(nombre={}) - start", nombre);
    }

    String tipo = this.getCodigoTipoDocumentoSade() + " - "
        + this.getDescripcionTipoDocumentoSade();
    boolean returnboolean = tipo.equals(nombre);
    if (logger.isDebugEnabled()) {
      logger.debug("esIgual(String) - end - return value={}", returnboolean);
    }
    return returnboolean;
  }

  public String getUsoEnEE() {
    return usoEnEE;
  }

  public void setUsoEnEE(String usoEnEE) {
    this.usoEnEE = usoEnEE;
  }

  public String getDescripcionTipoDocumentoSade() {
    return descripcionTipoDocumentoSade;
  }

  public void setDescripcionTipoDocumentoSade(String descripcionTipoDocumentoSade) {
    this.descripcionTipoDocumentoSade = descripcionTipoDocumentoSade;
  }

  public Boolean getEsConfidencial() {
    return esConfidencial;
  }

  public void setEsConfidencial(Boolean esConfidencial) {
    this.esConfidencial = esConfidencial;
  }

  public Integer getIdTipoDocumentoGedo() {
    return idTipoDocumentoGedo;
  }

  public void setIdTipoDocumentoGedo(Integer idTipoDocumentoGedo) {
    this.idTipoDocumentoGedo = idTipoDocumentoGedo;
  }

  public String getIdFormulario() {
    return this.idFormulario;
  }

  public void setIdFormulario(String idFormulario) {
    this.idFormulario = idFormulario;
  }

  public void setEsManual(Boolean esManual) {
    this.esManual = esManual;
  }

  public Boolean getEsManual() {
    return esManual;
  }

  public void setDescripcionGedo(String descripcionGedo) {
    this.descripcionGedo = descripcionGedo;
  }

  public String getDescripcionGedo() {
    return descripcionGedo;
  }

  public void setTieneToken(Boolean tieneToken) {
    this.tieneToken = tieneToken;
  }

  public Boolean getTieneToken() {
    return tieneToken;
  }

  public void setTieneTemplate(Boolean tieneTemplate) {
    this.tieneTemplate = tieneTemplate;
  }

  public Boolean getTieneTemplate() {
    return tieneTemplate;
  }

  public void setEsFirmaExterna(Boolean esFirmaExterna) {
    this.esFirmaExterna = esFirmaExterna;
  }

  public Boolean getEsFirmaExterna() {
    return esFirmaExterna;
  }

  public void setEsFirmaConjunta(Boolean esFirmaConjunta) {
    this.esFirmaConjunta = esFirmaConjunta;
  }

  public Boolean getEsFirmaConjunta() {
    return esFirmaConjunta;
  }

  public void setFamilia(FamiliaTipoDocumentoDTO familia) {
    this.familia = familia;
  }

  public FamiliaTipoDocumentoDTO getFamilia() {
    return familia;
  }

  public void setEsNotificable(Boolean esNotificable) {
    this.esNotificable = esNotificable;
  }

  public Boolean getEsNotificable() {
    return esNotificable;
  }

  public void setTipoProduccion(String tipoProduccion) {
    this.tipoProduccion = tipoProduccion;
  }

  public String getTipoProduccion() {
    return tipoProduccion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public List<MetadataDTO> getListaDatosVariables() {
    return listaDatosVariables;
  }

  public void setListaDatosVariables(List<MetadataDTO> listaDatosVariables) {
    this.listaDatosVariables = listaDatosVariables;
  }

  public Set<TipoDocumentoTemplateDTO> getListaTemplates() {
    return listaTemplates;
  }

  public void setListaTemplates(Set<TipoDocumentoTemplateDTO> listaTemplates) {
    this.listaTemplates = listaTemplates;
  }

  public Boolean getResultado() {
    return resultado;
  }

  public void setResultado(Boolean resultado) {
    this.resultado = resultado;
  }
}
