package com.egoveris.vucfront.base.model;

import java.util.Date;
import java.util.List;

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
@Table(name = "TAD_TIPO_TRAMITE")
public class TipoTramite {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "ANEXO1")
  private String anexo1;

  @Column(name = "ANEXO3")
  private String anexo3;

  @Column(name = "APODERABLE")
  private Long apoderable;

  @Column(name = "ARCHIVO_TRABAJO")
  private Long archivoTrabajo;

  @Column(name = "DIAS_GUARDADO")
  private Long diasGuardado;

  @Column(name = "ESTADO")
  private String estado;

  @Column(name = "FECHA_CREACION")
  private Date fechaCreacion;

  @Column(name = "FECHA_MODIFICACION")
  private Date fechaModificacion;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ID_GRUPO")
  private Grupo grupo;

  @ManyToOne
  @JoinColumn(name = "ID_TIPO_DOC_FORMULARIO")
  private TipoDocumento tipoDocumentoFormulario;

  @OneToMany(mappedBy = "tipoTramite", fetch = FetchType.LAZY)
  private List<TipoTramiteTipoDoc> tipoTramiteTipoDoc;

  @Column(name = "ID_TRAMITE_TURNO")
  private Long idTramiteTurno;

  @Column(name = "INTERVINIENTE")
  private Boolean interviniente;

  @Column(name = "MENSAJE_EXITO")
  private String mensajeExito;

  @Column(name = "MOTIVO")
  private String motivo;

  @Column(name = "NOMBRE")
  private String nombre;

  @Column(name = "NOMBRE_FORMULARIO")
  private String nombreFormulario;

  @Column(name = "PAGO")
  private Boolean pago;

  @Column(name = "path_inicial")
  private String pathInicial;

  @Column(name = "PREVALIDACION")
  private Boolean prevalidacion;

  @Column(name = "REPARTICION_INICIADORA")
  private String reparticionIniciadora;

  @Column(name = "REQUISITOS_COD_CONTENIDO")
  private String requisitosCodContenido;

  @Column(name = "SECTOR_INICIADOR")
  private String sectorIniciador;

  @Column(name = "SUPER_TRATA")
  private Boolean superTrata;

  @Column(name = "TRATA")
  private String trata;

  @Column(name = "TURNO")
  private Boolean turno;

  @Column(name = "USUARIO_INICIADOR")
  private String usuarioIniciador;

  @Column(name = "VERSION")
  private Long version;
  
  @Column(name = "MONTO")
  private String monto;
  
  @Column(name = "APIKEY")
  private String apiKey;
  
  @Column(name = "REPARTICION_RECTORA")
  private String organismoRector;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getAnexo1() {
    return anexo1;
  }

  public void setAnexo1(String anexo1) {
    this.anexo1 = anexo1;
  }

  public String getAnexo3() {
    return anexo3;
  }

  public void setAnexo3(String anexo3) {
    this.anexo3 = anexo3;
  }

  public Long getApoderable() {
    return apoderable;
  }

  public void setApoderable(Long apoderable) {
    this.apoderable = apoderable;
  }

  public Long getArchivoTrabajo() {
    return archivoTrabajo;
  }

  public void setArchivoTrabajo(Long archivoTrabajo) {
    this.archivoTrabajo = archivoTrabajo;
  }

  public Long getDiasGuardado() {
    return diasGuardado;
  }

  public void setDiasGuardado(Long diasGuardado) {
    this.diasGuardado = diasGuardado;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public Date getFechaCreacion() {
    return fechaCreacion;
  }

  public void setFechaCreacion(Date fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }

  public Date getFechaModificacion() {
    return fechaModificacion;
  }

  public void setFechaModificacion(Date fechaModificacion) {
    this.fechaModificacion = fechaModificacion;
  }

  public Grupo getGrupo() {
    return grupo;
  }

  public void setGrupo(Grupo grupo) {
    this.grupo = grupo;
  }

  public TipoDocumento getTipoDocumentoFormulario() {
    return tipoDocumentoFormulario;
  }

  public void setTipoDocumentoFormulario(TipoDocumento tipoDocumentoFormulario) {
    this.tipoDocumentoFormulario = tipoDocumentoFormulario;
  }

  public List<TipoTramiteTipoDoc> getTipoTramiteTipoDoc() {
    return tipoTramiteTipoDoc;
  }

  public void setTipoTramiteTipoDoc(List<TipoTramiteTipoDoc> tipoTramiteTipoDoc) {
    this.tipoTramiteTipoDoc = tipoTramiteTipoDoc;
  }

  public Long getIdTramiteTurno() {
    return idTramiteTurno;
  }

  public void setIdTramiteTurno(Long idTramiteTurno) {
    this.idTramiteTurno = idTramiteTurno;
  }

  public Boolean getInterviniente() {
    return interviniente;
  }

  public void setInterviniente(Boolean interviniente) {
    this.interviniente = interviniente;
  }

  public String getMensajeExito() {
    return mensajeExito;
  }

  public void setMensajeExito(String mensajeExito) {
    this.mensajeExito = mensajeExito;
  }

  public String getMotivo() {
    return motivo;
  }

  public void setMotivo(String motivo) {
    this.motivo = motivo;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getNombreFormulario() {
    return nombreFormulario;
  }

  public void setNombreFormulario(String nombreFormulario) {
    this.nombreFormulario = nombreFormulario;
  }

  public Boolean getPago() {
    return pago;
  }

  public void setPago(Boolean pago) {
    this.pago = pago;
  }

  public String getPathInicial() {
    return pathInicial;
  }

  public void setPathInicial(String pathInicial) {
    this.pathInicial = pathInicial;
  }

  public Boolean getPrevalidacion() {
    return prevalidacion;
  }

  public void setPrevalidacion(Boolean prevalidacion) {
    this.prevalidacion = prevalidacion;
  }

  public String getReparticionIniciadora() {
    return reparticionIniciadora;
  }

  public void setReparticionIniciadora(String reparticionIniciadora) {
    this.reparticionIniciadora = reparticionIniciadora;
  }

  public String getRequisitosCodContenido() {
    return requisitosCodContenido;
  }

  public void setRequisitosCodContenido(String requisitosCodContenido) {
    this.requisitosCodContenido = requisitosCodContenido;
  }

  public String getSectorIniciador() {
    return sectorIniciador;
  }

  public void setSectorIniciador(String sectorIniciador) {
    this.sectorIniciador = sectorIniciador;
  }

  public Boolean getSuperTrata() {
    return superTrata;
  }

  public void setSuperTrata(Boolean superTrata) {
    this.superTrata = superTrata;
  }

  public String getTrata() {
    return trata;
  }

  public void setTrata(String trata) {
    this.trata = trata;
  }

  public Boolean getTurno() {
    return turno;
  }

  public void setTurno(Boolean turno) {
    this.turno = turno;
  }

  public String getUsuarioIniciador() {
    return usuarioIniciador;
  }

  public void setUsuarioIniciador(String usuarioIniciador) {
    this.usuarioIniciador = usuarioIniciador;
  }

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

	public String getMonto() {
		return monto;
	}

	public void setMonto(String monto) {
		this.monto = monto;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getOrganismoRector() {
		return organismoRector;
	}

	public void setOrganismoRector(String organismoRector) {
		this.organismoRector = organismoRector;
	}

}