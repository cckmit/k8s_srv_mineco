package com.egoveris.deo.model.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.util.CollectionUtils;

public class TipoDocumentoAuditoriaDTO implements Serializable {

  private static final long serialVersionUID = 4784894091508384750L;

  private String tipoOperacion;
  private Date fechaModificacion;
  private String userName;

  private Integer id;
  private String nombre;
  private String acronimo;
  private String version;
  private String descripcion;
  private Boolean esEspecial = false;
  private Boolean esManual = false;
  private Boolean esAutomatica = false;
  private Boolean tieneToken = false;
  private Boolean tieneTemplate = false;
  private Integer tipoProduccion;
  private String estado;
  private Integer idTipoDocumentoSade;
  private String codigoTipoDocumentoSade;
  private Boolean esFirmaConjunta = false;
  private Boolean esConfidencial = false;
  private Set<ReparticionHabilitadaDTO> listaReparticiones = new HashSet<>();
  private List<MetadataDTO> listaDatosVariables = new ArrayList<>();
  private Boolean estaHabilitado = true;
  private Boolean esFirmaExterna = false;
  private FamiliaTipoDocumentoDTO familiaTipoDocumento;

  public TipoDocumentoAuditoriaDTO(TipoDocumentoDTO tipoDocumento, String username) {

    this.setFechaModificacion(new Date());
    this.setUserName(username);
    this.setAcronimo(tipoDocumento.getAcronimo());
    this.setVersion(tipoDocumento.getVersion());
    this.setCodigoTipoDocumentoSade(tipoDocumento.getCodigoTipoDocumentoSade());
    this.setDescripcion(tipoDocumento.getDescripcion());
    this.setEsAutomatica(tipoDocumento.getEsAutomatica());
    this.setEsConfidencial(tipoDocumento.getEsConfidencial());
    this.setEsEspecial(tipoDocumento.getEsEspecial());
    this.setEsFirmaConjunta(tipoDocumento.getEsFirmaConjunta());
    this.setEsFirmaExterna(tipoDocumento.getEsFirmaExterna());
    this.setEsManual(tipoDocumento.getEsManual());
    this.setEstado(tipoDocumento.getEstado());
    this.setEstaHabilitado(tipoDocumento.getEstaHabilitado());
    this.setIdTipoDocumentoSade(tipoDocumento.getIdTipoDocumentoSade());
    this.setFamiliaTipoDocumento(tipoDocumento.getFamilia());
    if (!CollectionUtils.isEmpty(tipoDocumento.getListaDatosVariables())) {
      this.listaDatosVariables.addAll(tipoDocumento.getListaDatosVariables());
    }
    if (!CollectionUtils.isEmpty(tipoDocumento.getListaReparticiones())) {
      this.listaReparticiones.addAll(tipoDocumento.getListaReparticiones());
    }
    this.setNombre(tipoDocumento.getNombre());
    this.setTipoProduccion(tipoDocumento.getTipoProduccion());
    this.setTieneToken(tipoDocumento.getTieneToken());
    this.setTieneTemplate(tipoDocumento.getTieneTemplate());
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

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
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

  public List<MetadataDTO> getListaDatosVariables() {
    return listaDatosVariables;
  }

  public void setListaDatosVariables(List<MetadataDTO> listaDatosVariables) {
    this.listaDatosVariables = listaDatosVariables;
  }

  public Boolean getEstaHabilitado() {
    return estaHabilitado;
  }

  public void setEstaHabilitado(Boolean estaHabilitado) {
    this.estaHabilitado = estaHabilitado;
  }

  public Date getFechaModificacion() {
    return fechaModificacion;
  }

  public void setFechaModificacion(Date fechaModificacion) {
    this.fechaModificacion = fechaModificacion;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getTipoOperacion() {
    return tipoOperacion;
  }

  public void setTipoOperacion(String tipoOperacion) {
    this.tipoOperacion = tipoOperacion;
  }

  public Set<ReparticionHabilitadaDTO> getListaReparticiones() {
    return listaReparticiones;
  }

  public void setListaReparticiones(Set<ReparticionHabilitadaDTO> listaReparticiones) {
    this.listaReparticiones = listaReparticiones;
  }

  public Boolean getEsFirmaExterna() {
    return esFirmaExterna;
  }

  public void setEsFirmaExterna(Boolean esFirmaExterna) {
    this.esFirmaExterna = esFirmaExterna;
  }

  public FamiliaTipoDocumentoDTO getFamiliaTipoDocumento() {
    return familiaTipoDocumento;
  }

  public void setFamiliaTipoDocumento(FamiliaTipoDocumentoDTO familiaTipoDocumento) {
    this.familiaTipoDocumento = familiaTipoDocumento;
  }

  public Integer getTipoProduccion() {
    return tipoProduccion;
  }

  public void setTipoProduccion(Integer tipoProduccion) {
    this.tipoProduccion = tipoProduccion;
  }

}
