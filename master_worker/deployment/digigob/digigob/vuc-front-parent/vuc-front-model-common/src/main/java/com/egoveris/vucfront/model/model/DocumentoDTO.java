package com.egoveris.vucfront.model.model;

import com.egoveris.shared.date.DateUtil;
import com.egoveris.vucfront.model.util.BusinessFormatHelper;

import java.beans.Transient;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DocumentoDTO implements Serializable {

	private static final long serialVersionUID = 3092980340133506397L;

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

	public String getCodigoSade() {
		return this.numeroDocumento;
	}

	public String getFormattedFechaCreacion() {
		if (fechaCreacion != null) {
			return DateUtil.getFormattedDate(fechaCreacion);
		}
		return "";
	}

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

	public byte[] getArchivo() {
		return archivo;
	}

	public void setArchivo(byte[] archivo) {
		this.archivo = archivo;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	@Transient
	public List<DocumentoEstadoDTO> getDocumentoEstados() {
		return documentoEstados;
	}

	@Transient
	public void setDocumentoEstados(List<DocumentoEstadoDTO> documentoEstado) {
		this.documentoEstados = documentoEstado;
	}

	@Override
	public String toString() {
		return "DocumentoDTO [id=" + id + ", bajaLogica=" + bajaLogica + ", fechaCreacion=" + fechaCreacion
				+ ", persona=" + persona + ", tipoDocumento=" + tipoDocumento + ", idTransaccion=" + idTransaccion
				+ ", nombreOriginal=" + nombreOriginal + ", referencia=" + referencia + ", urlTemporal=" + urlTemporal
				+ ", usuarioCreacion=" + usuarioCreacion + ", version=" + version + ", documentoEstados="
				+ documentoEstados + ", numeroDocumento=" + numeroDocumento + ", archivo=" + Arrays.toString(archivo)
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idTransaccion == null) ? 0 : idTransaccion.hashCode());
		result = prime * result + ((nombreOriginal == null) ? 0 : nombreOriginal.hashCode());
		result = prime * result + ((numeroDocumento == null) ? 0 : numeroDocumento.hashCode());
		result = prime * result + ((tipoDocumento == null) ? 0 : tipoDocumento.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

}