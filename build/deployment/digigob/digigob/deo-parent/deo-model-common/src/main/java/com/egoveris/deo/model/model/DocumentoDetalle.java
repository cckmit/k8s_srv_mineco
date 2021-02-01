package com.egoveris.deo.model.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class DocumentoDetalle implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7964242618579201936L;
	private boolean puedeVerDocumento;
	private String referencia;
	private String numeroSade;
	private String numeroEspecial;
	private String urlArchivo;
	private List<String> listaFirmantes;
	private Date fechaCreacion;
	private String tipoDocumento;
	private String idWorkFlow;
	private List<DocumentoMetadataDetalle> datosPropiosDetalle;
	private List<ArchivoDeTrabajoDetalle> listaArchivosDeTrabajoDetalle;
	private List<HistorialDetalle> listaHistorialDetalle;
	private String motivoDepuracion;
	private List<UsuarioReservadoDTO> listaUsuariosReservados;
	private String idGuardaDocumental;

	private String tipoDocAcronimo;

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getNumeroSade() {
		return numeroSade;
	}

	public void setNumeroSade(String numeroSade) {
		this.numeroSade = numeroSade;
	}

	public String getNumeroEspecial() {
		return numeroEspecial;
	}

	public void setNumeroEspecial(String numeroEspecial) {
		this.numeroEspecial = numeroEspecial;
	}

	public List<String> getListaFirmantes() {
		return listaFirmantes;
	}

	public void setListaFirmantes(List<String> listaFirmantes) {
		this.listaFirmantes = listaFirmantes;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public void setPuedeVerDocumento(boolean puedeVerDocumento) {
		this.puedeVerDocumento = puedeVerDocumento;
	}

	public boolean getPuedeVerDocumento() {
		return puedeVerDocumento;
	}

	public void setDatosPropiosDetalle(List<DocumentoMetadataDetalle> datosPropiosDetalle) {
		this.datosPropiosDetalle = datosPropiosDetalle;
	}

	public List<DocumentoMetadataDetalle> getDatosPropiosDetalle() {
		return datosPropiosDetalle;
	}

	public void setListaArchivosDeTrabajoDetalle(List<ArchivoDeTrabajoDetalle> listaArchivosDeTrabajoDetalle) {
		this.listaArchivosDeTrabajoDetalle = listaArchivosDeTrabajoDetalle;
	}

	public List<ArchivoDeTrabajoDetalle> getListaArchivosDeTrabajoDetalle() {
		return listaArchivosDeTrabajoDetalle;
	}

	public void setListaHistorialDetalle(List<HistorialDetalle> listaHistorialDetalle) {
		this.listaHistorialDetalle = listaHistorialDetalle;
	}

	public List<HistorialDetalle> getListaHistorialDetalle() {
		return listaHistorialDetalle;
	}

	public String getUrlArchivo() {
		return urlArchivo;
	}

	public void setUrlArchivo(String urlArchivo) {
		this.urlArchivo = urlArchivo;
	}

	public String getTipoDocAcronimo() {
		return tipoDocAcronimo;
	}

	public void setTipoDocAcronimo(String tipoDocAcronimo) {
		this.tipoDocAcronimo = tipoDocAcronimo;
	}

	public String getIdWorkFlow() {
		return idWorkFlow;
	}

	public void setIdWorkFlow(String idWorkFlow) {
		this.idWorkFlow = idWorkFlow;
	}

	public String getMotivoDepuracion() {
		return motivoDepuracion;
	}

	public void setMotivoDepuracion(String motivoDepuracion) {
		this.motivoDepuracion = motivoDepuracion;
	}

	public List<UsuarioReservadoDTO> getListaUsuariosReservados() {
		return listaUsuariosReservados;
	}

	public void setListaUsuariosReservados(List<UsuarioReservadoDTO> listaUsuariosReservados) {
		this.listaUsuariosReservados = listaUsuariosReservados;
	}

	public String getIdGuardaDocumental() {
		return idGuardaDocumental;
	}

	public void setIdGuardaDocumental(String idGuardaDocumental) {
		this.idGuardaDocumental = idGuardaDocumental;
	}

}
