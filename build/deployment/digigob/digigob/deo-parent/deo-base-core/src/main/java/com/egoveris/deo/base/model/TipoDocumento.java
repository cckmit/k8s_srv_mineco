package com.egoveris.deo.base.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "GEDO_TIPODOCUMENTO")
public class TipoDocumento {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "ACRONIMO")
  private String acronimo;

  @Column(name = "CODIGOTIPODOCUMENTOSADE")
  private String codigoTipoDocumentoSade;

  @Column(name = "DESCRIPCION")
  private String descripcion;

  @Column(name = "ES_COMUNICABLE")
  private Boolean esComunicable;

  @Column(name = "ES_NOTIFICABLE")
  private Boolean esNotificable;

  @Column(name = "ESAUTOMATICA")
  private Boolean esAutomatica;

  @Column(name = "ESCONFIDENCIAL")
  private Boolean esConfidencial;

  @Column(name = "ESESPECIAL")
  private Boolean esEspecial;

  @Column(name = "ESFIRMACONJUNTA")
  private Boolean esFirmaConjunta;

  @Column(name = "ESFIRMAEXTERNA")
  private Boolean esFirmaExterna;

  @Column(name = "ESFIRMAEXTERNACONENCABEZADO")
  private Boolean esFirmaExternaConEncabezado;

  @Column(name = "ESMANUAL")
  private Boolean esManual;

  @Column(name = "ESOCULTO")
  private Boolean esOculto;

  @Column(name = "ESTADO")
  private String estado;

  @ManyToOne
  @JoinColumn(name = "FAMILIA")
  private FamiliaTipoDocumento familia;

  @Column(name = "IDTIPODOCUMENTOSADE")
  private Integer idTipoDocumentoSade;

  @Column(name = "NOMBRE")
  private String nombre;

  @Column(name = "PERMITE_EMBEBIDOS")
  private Boolean permiteEmbebidos;

  @Column(name = "TAMANO")
  private Integer sizeImportado;

  @Column(name = "TIENEAVISO")
  private Boolean tieneAviso;

  @Column(name = "TIENETEMPLATE")
  private Boolean tieneTemplate;

  @Column(name = "TIENETOKEN")
  private Boolean tieneToken;
  
  @Column(name = "ES_DOBLEFACTOR")
  private Boolean esDobleFactor;

  @Column(name = "TIPOPRODUCCION")
  private Integer tipoProduccion;

  @Column(name = "USUARIO_CREADOR")
  private String usuarioCreador;

  @Column(name = "VERSION")
  private String version;

  @OneToMany(mappedBy = "tipo")
  private List<Documento> documento;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "ID_TIPODOCUMENTO")
  private Set<TipoDocumentoEmbebidos> tipoDocumentoEmbebidos;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoDocumentoTemplatePK.idTipoDocumento")
  private Set<TipoDocumentoTemplate> listaTemplates;

  @OneToMany(mappedBy = "tipoDocumento", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Set<ReparticionHabilitada> listaReparticiones;

  @Column(name = "RESULTADO")
  private Boolean resultado;
  
  @Column(name = "ES_PUBLICABLE")
  private Boolean esPublicable;
  
  @Column(name = "FECHA_CREACION")
  private Date fechaCreacion;
  
  public Date getFechaCreacion() {
	return fechaCreacion;
  }

  public void setFechaCreacion(Date fechaCreacion) {
	this.fechaCreacion = fechaCreacion;
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

  public FamiliaTipoDocumento getFamilia() {
    return familia;
  }

  public void setFamilia(FamiliaTipoDocumento familia) {
    this.familia = familia;
  }

  public Boolean getTieneAviso() {
    return tieneAviso;
  }

  public void setTieneAviso(Boolean tieneAviso) {
    this.tieneAviso = tieneAviso;
  }

  public String getUsuarioCreador() {
    return usuarioCreador;
  }

  public void setUsuarioCreador(String usuarioCreador) {
    this.usuarioCreador = usuarioCreador;
  }

  public Set<TipoDocumentoEmbebidos> getTipoDocumentoEmbebidos() {
    return tipoDocumentoEmbebidos;
  }

  public void setTipoDocumentoEmbebidos(Set<TipoDocumentoEmbebidos> tipoDocumentoEmbebidos) {
    this.tipoDocumentoEmbebidos = tipoDocumentoEmbebidos;
  }

  public Integer getTipoProduccion() {
    return tipoProduccion;
  }

  public void setTipoProduccion(Integer tipoProduccion) {
    this.tipoProduccion = tipoProduccion;
  }

  public Set<TipoDocumentoTemplate> getListaTemplates() {
    return listaTemplates;
  }

  public void setListaTemplates(Set<TipoDocumentoTemplate> listaTemplates) {
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

  public Set<ReparticionHabilitada> getListaReparticiones() {
    return listaReparticiones;
  }
  
  public void setListaReparticiones(Set<ReparticionHabilitada> listaReparticiones) {
    this.listaReparticiones = listaReparticiones;
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
