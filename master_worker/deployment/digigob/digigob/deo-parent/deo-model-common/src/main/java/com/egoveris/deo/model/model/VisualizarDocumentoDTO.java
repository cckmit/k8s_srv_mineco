package com.egoveris.deo.model.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class VisualizarDocumentoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2537607045247555112L;

	private boolean puedeVerDocumento;

	private String referencia;
	private String numeroSade;
	private String numeroEspecial;
	private List<String> listaFirmantes;
	private Date fechaCreacion;
	private String tipoDocumento;
	private List<DocumentoMetadataDTO> datosPropios;
	private List<ArchivoDeTrabajoDTO> listaArchivosDeTrabajo;
	private List<HistorialDTO> listaHistorialDTO;
	private String url;
	private String idGuardaDocumental;
	//Formulario
	private String idFormulario;
	private Integer idTransaccion;
	private String motivoDepuracion;
	private List<UsuarioReservadoDTO> listaUsuariosReservados;
	
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	// Getters y Setters
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

	public List<DocumentoMetadataDTO> getDatosPropios() {
		return datosPropios;
	}

	public void setDatosPropios(List<DocumentoMetadataDTO> datosPropios) {
		this.datosPropios = datosPropios;
	}

	public void setListaArchivosDeTrabajo(List<ArchivoDeTrabajoDTO> listaArchivosDeTrabajo) {
		this.listaArchivosDeTrabajo = listaArchivosDeTrabajo;
	}

	public List<ArchivoDeTrabajoDTO> getListaArchivosDeTrabajo() {
		return listaArchivosDeTrabajo;
	}

	public void setListaHistorialDTOs(List<HistorialDTO> listaHistorialDTO) {
		this.listaHistorialDTO = listaHistorialDTO;
	}

	public List<HistorialDTO> getListaHistorialDTO() {
		return listaHistorialDTO;
	}

	public void setPuedeVerDocumento(boolean puedeVerDocumento) {
		this.puedeVerDocumento = puedeVerDocumento;
	}

	public boolean getPuedeVerDocumento() {
		return puedeVerDocumento;
	}

	public String getIdFormulario() {
		return idFormulario;
	}

	public void setIdFormulario(String idFormulario) {
		this.idFormulario = idFormulario;
	}

	public Integer getIdTransaccion() {
		return idTransaccion;
	}

	public void setIdTransaccion(Integer idTransaccion) {
		this.idTransaccion = idTransaccion;
	}

	public String getMotivoDepuracion() {
		return motivoDepuracion;
	}

	public String getIdGuardaDocumental() {
		return idGuardaDocumental;
	}

	public void setIdGuardaDocumental(String idGuardaDocumental) {
		this.idGuardaDocumental = idGuardaDocumental;
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

	
}
