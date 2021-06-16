package com.egoveris.te.base.model.expediente;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import org.hibernate.annotations.WhereJoinTable;

import com.egoveris.te.base.model.ArchivoDeTrabajo;
import com.egoveris.te.base.model.Documento;
import com.egoveris.te.base.model.Operacion;
import com.egoveris.te.base.model.PropertyConfiguration;
import com.egoveris.te.base.model.SolicitudExpediente;
import com.egoveris.te.base.model.trata.Trata;

@Entity
@Table(name = "EE_EXPEDIENTE_ELECTRONICO")
public class ExpedienteElectronico {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "ANIO")
	private Integer anio;

	@Column(name = "BLOQUEADO")
	private Boolean bloqueado;

	@Column(name = "CODIGO_REPARTICION_ACTUACION")
	private String codigoReparticionActuacion;

	@Column(name = "CODIGO_REPARTICION_USUARIO")
	private String codigoReparticionUsuario;

	@Column(name = "DEFINITIVO")
	private Boolean definitivo;

	@Column(name = "DESCRIPCION")
	private String descripcion;

	@Column(name = "ES_CABECERA_TC")
	private Boolean esCabeceraTC;

	@Column(name = "ES_ELECTRONICO")
	private Boolean esElectronico;

	@Column(name = "ES_RESERVADO")
	private Integer esReservado;

	@Column(name = "ESTADO")
	private String estado;

	@Column(name = "FECHA_ARCHIVO")
	private Date fechaArchivo;

	@Column(name = "FECHA_CREACION")
	private Date fechaCreacion;

	@Column(name = "FECHA_ENVIO_DEPURACION")
	private Date fechaDepuracion;

	@Column(name = "FECHA_MODIFICACION")
	private Date fechaModificacion;

	@Column(name = "FECHA_RESERVA")
	private Date fechaReserva;

	@Column(name = "FECHA_SOLICITUD_ARCHIVO")
	private Date fechaSolicitudArchivo;

	@Column(name = "ID_OPERACION")
	private Integer idOperacion;

	@ManyToOne
	@JoinColumn(name = "ID_TRATA")
	private Trata trata;

	@Column(name = "ID_WORKFLOW")
	private String idWorkflow;

	@Column(name = "NUMERO")
	private Integer numero;

	@Column(name = "SECUENCIA")
	private String secuencia;

	@Column(name = "SISTEMA_APODERADO")
	private String sistemaApoderado;

	@Column(name = "SISTEMA_CREADOR")
	private String sistemaCreador;

	@OneToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "SOLICITUD_INICIADORA")
	private SolicitudExpediente solicitudIniciadora;

	@Column(name = "TIPO_DOCUMENTO")
	private String tipoDocumento;

	@Column(name = "TRAMITACION_LIBRE")
	private Boolean tramitacionLibre;

	@Column(name = "USUARIO_CREADOR")
	private String usuarioCreador;

	@Column(name = "USUARIO_MODIFICACION")
	private String usuarioModificacion;

	@Column(name = "USUARIO_RESERVA")
	private String usuarioReserva;

	@OneToMany(mappedBy = "expedienteMetadataPK.idExpediente", fetch = FetchType.LAZY)
	private List<ExpedienteMetadata> metadatosDeTrata;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "EE_EXPEDIENTE_ARCHIVOSTRABAJO", joinColumns = {
			@JoinColumn(name = "ID") }, inverseJoinColumns = { @JoinColumn(name = "ID_ARCHIVOTRABAJO") })
	@OrderColumn(name = "posicion")
	private List<ArchivoDeTrabajo> archivosDeTrabajo;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "EE_EXPEDIENTE_ASOCIADO", joinColumns = { @JoinColumn(name = "ID") }, inverseJoinColumns = {
			@JoinColumn(name = "ID_EXPEDIENTEASOCIADO") })
	@OrderColumn(name = "posicion")
	private List<ExpedienteAsociado> listaExpedientesAsociados;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "EE_EXPEDIENTE_DOCUMENTOS", joinColumns = { @JoinColumn(name = "ID") }, inverseJoinColumns = {
			@JoinColumn(name = "ID_DOCUMENTO") })
	@OrderColumn(name = "posicion")
	private List<Documento> documentos;

	@Column(name = "RESULTADO")
	private String resultado;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "RESULTADO", referencedColumnName = "clave", insertable = false, updatable = false)
	@WhereJoinTable(clause = "CONFIGURACION = 'SISTEMA'")
	private PropertyConfiguration propertyResultado;

	@Column(name = "BLOCKED")
	private boolean blocked;

	@Column(name = "CANTIDAD_SUBSANAR")
	private Integer cantidadSubsanar;

	@ManyToOne
	@JoinColumn(name = "ID_OPERACION", insertable = false, updatable = false)
	private Operacion operacion;
	
	/**
	 * @return the cantidadSubsanar
	 */
	public Integer getCantidadSubsanar() {
		return cantidadSubsanar;
	}

	/**
	 * @param cantidadSubsanar the cantidadSubsanar to set
	 */
	public void setCantidadSubsanar(Integer cantidadSubsanar) {
		this.cantidadSubsanar = cantidadSubsanar;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getAnio() {
		return anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	public Boolean getBloqueado() {
		return bloqueado;
	}

	public void setBloqueado(Boolean bloqueado) {
		this.bloqueado = bloqueado;
	}

	public String getCodigoReparticionActuacion() {
		return codigoReparticionActuacion;
	}

	public void setCodigoReparticionActuacion(String codigoReparticionActuacion) {
		this.codigoReparticionActuacion = codigoReparticionActuacion;
	}

	public String getCodigoReparticionUsuario() {
		return codigoReparticionUsuario;
	}

	public void setCodigoReparticionUsuario(String codigoReparticionUsuario) {
		this.codigoReparticionUsuario = codigoReparticionUsuario;
	}

	public Boolean getDefinitivo() {
		return definitivo;
	}

	public void setDefinitivo(Boolean definitivo) {
		this.definitivo = definitivo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Boolean getEsCabeceraTC() {
		return esCabeceraTC;
	}

	public void setEsCabeceraTC(Boolean esCabeceraTC) {
		this.esCabeceraTC = esCabeceraTC;
	}

	public Boolean getEsElectronico() {
		return esElectronico;
	}

	public void setEsElectronico(Boolean esElectronico) {
		this.esElectronico = esElectronico;
	}

	public Integer getEsReservado() {
		return esReservado;
	}

	public void setEsReservado(Integer esReservado) {
		this.esReservado = esReservado;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaArchivo() {
		return fechaArchivo;
	}

	public void setFechaArchivo(Date fechaArchivo) {
		this.fechaArchivo = fechaArchivo;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaDepuracion() {
		return fechaDepuracion;
	}

	public void setFechaDepuracion(Date fechaDepuracion) {
		this.fechaDepuracion = fechaDepuracion;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public Date getFechaReserva() {
		return fechaReserva;
	}

	public void setFechaReserva(Date fechaReserva) {
		this.fechaReserva = fechaReserva;
	}

	public Date getFechaSolicitudArchivo() {
		return fechaSolicitudArchivo;
	}

	public void setFechaSolicitudArchivo(Date fechaSolicitudArchivo) {
		this.fechaSolicitudArchivo = fechaSolicitudArchivo;
	}

	public Integer getIdOperacion() {
		return idOperacion;
	}

	public void setIdOperacion(Integer idOperacion) {
		this.idOperacion = idOperacion;
	}

	public Trata getTrata() {
		return trata;
	}

	public void setTrata(Trata trata) {
		this.trata = trata;
	}

	public String getIdWorkflow() {
		return idWorkflow;
	}

	public void setIdWorkflow(String idWorkflow) {
		this.idWorkflow = idWorkflow;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getSecuencia() {
		return secuencia;
	}

	public void setSecuencia(String secuencia) {
		this.secuencia = secuencia;
	}

	public String getSistemaApoderado() {
		return sistemaApoderado;
	}

	public void setSistemaApoderado(String sistemaApoderado) {
		this.sistemaApoderado = sistemaApoderado;
	}

	public String getSistemaCreador() {
		return sistemaCreador;
	}

	public void setSistemaCreador(String sistemaCreador) {
		this.sistemaCreador = sistemaCreador;
	}

	public SolicitudExpediente getSolicitudIniciadora() {
		return solicitudIniciadora;
	}

	public void setSolicitudIniciadora(SolicitudExpediente solicitudIniciadora) {
		this.solicitudIniciadora = solicitudIniciadora;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Boolean getTramitacionLibre() {
		return tramitacionLibre;
	}

	public void setTramitacionLibre(Boolean tramitacionLibre) {
		this.tramitacionLibre = tramitacionLibre;
	}

	public String getUsuarioCreador() {
		return usuarioCreador;
	}

	public void setUsuarioCreador(String usuarioCreador) {
		this.usuarioCreador = usuarioCreador;
	}

	public String getUsuarioModificacion() {
		return usuarioModificacion;
	}

	public void setUsuarioModificacion(String usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}

	public String getUsuarioReserva() {
		return usuarioReserva;
	}

	public void setUsuarioReserva(String usuarioReserva) {
		this.usuarioReserva = usuarioReserva;
	}

	public List<ExpedienteMetadata> getMetadatosDeTrata() {
		return metadatosDeTrata;
	}

	public void setMetadatosDeTrata(List<ExpedienteMetadata> metadatosDeTrata) {
		this.metadatosDeTrata = metadatosDeTrata;
	}

	public List<ArchivoDeTrabajo> getArchivosDeTrabajo() {
		return archivosDeTrabajo;
	}

	public void setArchivosDeTrabajo(List<ArchivoDeTrabajo> archivosDeTrabajo) {
		this.archivosDeTrabajo = archivosDeTrabajo;
	}

	public List<ExpedienteAsociado> getListaExpedientesAsociados() {
		return listaExpedientesAsociados;
	}

	public void setListaExpedientesAsociados(List<ExpedienteAsociado> listaExpedientesAsociados) {
		this.listaExpedientesAsociados = listaExpedientesAsociados;
	}

	public List<Documento> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<Documento> documentos) {
		this.documentos = documentos;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public PropertyConfiguration getPropertyResultado() {
		return propertyResultado;
	}

	public void setPropertyResultado(PropertyConfiguration propertyResultado) {
		this.propertyResultado = propertyResultado;
	}

	public boolean getBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	public Operacion getOperacion() {
		return operacion;
	}

	public void setOperacion(Operacion operacion) {
		this.operacion = operacion;
	}


}