package com.egoveris.vucfront.model.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TipoTramiteDTO implements Serializable {

  private static final long serialVersionUID = 6237670528627496653L;

  private Long id;
  private String anexo1;
  private String anexo3;
  private Long apoderable;
  private Long archivoTrabajo;
  private Long diasGuardado;
  private String estado;
  private Date fechaCreacion;
  private Date fechaModificacion;
  private GrupoDTO grupo;
  private TipoDocumentoDTO tipoDocumentoFormulario;
  private List<TipoTramiteTipoDocDTO> tipoTramiteTipoDoc;
  private Long idTramiteTurno;
  private Boolean interviniente;
  private String mensajeExito;
  private String motivo;
  private String nombre;
  private String nombreFormulario;
  private Boolean pago;
  private String pathInicial;
  private Boolean prevalidacion;
  private String reparticionIniciadora;
  private String requisitosCodContenido;
  private String sectorIniciador;
  private Boolean superTrata;
  private String trata;
  private Boolean turno;
  private String usuarioIniciador;
  private Long version;
  private String monto;
  private String apiKey;
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

  public GrupoDTO getGrupo() {
    return grupo;
  }

  public void setGrupo(GrupoDTO grupo) {
    this.grupo = grupo;
  }

  public TipoDocumentoDTO getTipoDocumentoFormulario() {
    return tipoDocumentoFormulario;
  }

  public void setTipoDocumentoFormulario(TipoDocumentoDTO tipoDocumentoFormulario) {
    this.tipoDocumentoFormulario = tipoDocumentoFormulario;
  }

  public List<TipoTramiteTipoDocDTO> getTipoTramiteTipoDoc() {
    return tipoTramiteTipoDoc;
  }

  public void setTipoTramiteTipoDoc(List<TipoTramiteTipoDocDTO> tipoTramiteTipoDoc) {
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

	@Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("TipoTramiteDTO [id=").append(id).append(", anexo1=").append(anexo1)
        .append(", anexo3=").append(anexo3).append(", apoderable=").append(apoderable)
        .append(", archivoTrabajo=").append(archivoTrabajo).append(", diasGuardado=")
        .append(diasGuardado).append(", estado=").append(estado).append(", fechaCreacion=")
        .append(fechaCreacion).append(", fechaModificacion=").append(fechaModificacion)
        .append(", grupo=").append(grupo).append(", tipoDocumentoFormulario=")
        .append(tipoDocumentoFormulario).append(", tipoTramiteTipoDoc=").append(tipoTramiteTipoDoc)
        .append(", idTramiteTurno=").append(idTramiteTurno).append(", interviniente=")
        .append(interviniente).append(", mensajeExito=").append(mensajeExito).append(", motivo=")
        .append(motivo).append(", nombre=").append(nombre).append(", nombreFormulario=")
        .append(nombreFormulario).append(", pago=").append(pago).append(", pathInicial=")
        .append(pathInicial).append(", prevalidacion=").append(prevalidacion)
        .append(", reparticionIniciadora=").append(reparticionIniciadora)
        .append(", requisitosCodContenido=").append(requisitosCodContenido)
        .append(", sectorIniciador=").append(sectorIniciador).append(", superTrata=")
        .append(superTrata).append(", trata=").append(trata).append(", turno=").append(turno)
        .append(", usuarioIniciador=").append(usuarioIniciador).append(", version=").append(version)
        .append(", monto=").append(monto).append(", apiKey=").append(apiKey)
        .append("]");
    return builder.toString();
  }

}