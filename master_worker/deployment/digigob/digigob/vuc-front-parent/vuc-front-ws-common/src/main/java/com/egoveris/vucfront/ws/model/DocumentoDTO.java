package com.egoveris.vucfront.ws.model;

import java.util.Date;
import java.util.List;

import com.egoveris.vucfront.model.model.DocumentoEstadoDTO;
import com.egoveris.vucfront.model.model.PersonaDTO;
import com.egoveris.vucfront.model.model.TipoDocumentoDTO;

public class DocumentoDTO {

	private Long id;
	private Boolean bajaLogica;
	private Date fechaCreacion;
	private PersonaDTO persona;
	private TipoDocumentoDTO tipoDocumento;
	private Long idTransaccion;
	private String nombreOriginal;
	private String referencia;
	private String urlTemporal;
	private String usuarioCreacion;
	private Long version;
	private List<DocumentoEstadoDTO> documentoEstados;
	private String numeroDocumento;

	private byte[] archivo;

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

	public PersonaDTO getPersona() {
		return persona;
	}

	public void setPersona(PersonaDTO persona) {
		this.persona = persona;
	}

	public TipoDocumentoDTO getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumentoDTO tipoDocumento) {
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

	public List<DocumentoEstadoDTO> getDocumentoEstados() {
		return documentoEstados;
	}

	public void setDocumentoEstados(List<DocumentoEstadoDTO> documentoEstados) {
		this.documentoEstados = documentoEstados;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public byte[] getArchivo() {
		return archivo;
	}

	public void setArchivo(byte[] archivo) {
		this.archivo = archivo;
	}

	

}
