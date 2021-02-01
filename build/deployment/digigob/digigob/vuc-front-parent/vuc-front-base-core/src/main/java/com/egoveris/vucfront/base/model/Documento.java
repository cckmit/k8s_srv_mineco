package com.egoveris.vucfront.base.model;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TAD_DOCUMENTO")
public class Documento {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;


	@Column(name = "BAJA_LOGICA")
	private Boolean bajaLogica;

	@Column(name = "FECHA_CREACION")
	private Date fechaCreacion;

	@ManyToOne
	@JoinColumn(name = "ID_PERSONA")
	private Persona persona;

	@OneToOne
	@JoinColumn(name = "ID_TIPO_DOCUMENTO")
	private TipoDocumento tipoDocumento;

	@Column(name = "ID_TRANSACCION")
	private Long idTransaccion;

	@Column(name = "NOMBRE_ORIGINAL")
	private String nombreOriginal;

	@Column(name = "REFERENCIA")
	private String referencia;

	@Column(name = "URL_TEMPORAL")
	private String urlTemporal;

	@Column(name = "USUARIO_CREACION")
	private String usuarioCreacion;

	@Column(name = "VERSION")
	private Long version;

	@OneToMany(mappedBy = "documento", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<DocumentoEstados> documentoEstados;

	@Column(name = "NUMERO_DOCUMENTO")
	private String numeroDocumento;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public Boolean getBajaLogica() {
		return bajaLogica;
	}

	public void setBajaLogica(Boolean bajaLogica) {
		this.bajaLogica = bajaLogica;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Long getIdTransaccion() {
		return idTransaccion;
	}

	public void setIdTransaccion(Long idTransaccion) {
		this.idTransaccion = idTransaccion;
	}

	public String getNombreOriginal() {
		return nombreOriginal;
	}

	public void setNombreOriginal(String nombreOriginal) {
		this.nombreOriginal = nombreOriginal;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getUrlTemporal() {
		return urlTemporal;
	}

	public void setUrlTemporal(String urlTemporal) {
		this.urlTemporal = urlTemporal;
	}

	public String getUsuarioCreacion() {
		return usuarioCreacion;
	}

	public void setUsuarioCreacion(String usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public List<DocumentoEstados> getDocumentoEstados() {
		return documentoEstados;
	}

	public void setDocumentoEstados(List<DocumentoEstados> documentoEstados) {
		this.documentoEstados = documentoEstados;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

}