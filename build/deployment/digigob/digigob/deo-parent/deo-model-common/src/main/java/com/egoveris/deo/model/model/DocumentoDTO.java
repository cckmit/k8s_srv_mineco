package com.egoveris.deo.model.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class DocumentoDTO implements Serializable {

	private static final long serialVersionUID = 3779512000403727497L;
	
	private Integer id;
	private String numero;
	private String numeroEspecial;
	private String reparticion;
	private String reparticionActual;
	private String anio;
	private String usuarioGenerador;
	private String motivo;
	private List<String> firmantes;
	private TipoDocumentoDTO tipo;
	private Date fechaCreacion;
	private Date fechaModificacion;
	private Boolean mostrarDescargas = true; 
	private Boolean noMostrarDescargas = false; 
	private List<DocumentoMetadataDTO> listaMetadatos;
	private String workflowOrigen;
	private Boolean workflowNoDisponible = false;
	private Boolean workflowDisponible = true;
	private String sistemaOrigen;
	private String sistemaIniciador;
	private String usuarioIniciador;
	private String numeroSadePapel;
	private TipoReservaDTO tipoReserva;
	private Set<ReparticionAcumuladaDTO> reparticionesAcumuladas;
	private Set<UsuarioReservadoDTO> usuariosReservados;
	private String apoderado;
	private String motivoDepuracion;
	private Date fechaDepuracion;
	private String idGuardaDocumental;
	private Integer peso;
	private String idPublicable;
	
	private transient Boolean poseeArchivosDeTrabajo = false;
	private transient Boolean noPoseeArchivosDeTrabajo = false;
	private transient String motivoParseado; 
		
	public DocumentoDTO() {
		this.fechaCreacion=new Date();
	}
	
	public String getNumeroSadePapel() {
		return numeroSadePapel;
	}

	public void setNumeroSadePapel(String numeroSadePapel) {
		this.numeroSadePapel = numeroSadePapel;
	}
	public List<DocumentoMetadataDTO> getListaMetadatos() {
		return listaMetadatos;
	}

	public void setListaMetadatos(List<DocumentoMetadataDTO> listaMetadatos) {
		this.listaMetadatos = listaMetadatos;
	}

	public TipoDocumentoDTO getTipo() {
		return tipo;
	}

	public void setTipo(TipoDocumentoDTO tipo) {
		this.tipo = tipo;
	}

	public Integer getId() {
		return id;
	}

	public String getNumeroEspecial() {
		return numeroEspecial;
	}

	public void setNumeroEspecial(String numeroEspecial) {
		this.numeroEspecial = numeroEspecial;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getReparticion() {
		return reparticion;
	}
	
	public String getReparticionActual() {
		return reparticionActual;
	}

	public void setReparticionActual(String reparticionActual) {
		this.reparticionActual = reparticionActual;
	}

	public void setReparticion(String reparticion) {
		this.reparticion = reparticion;
	}

	public String getAnio() {
		return anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public List<String> getFirmantes() {
		return firmantes;
	}

	public void setFirmantes(List<String> firmantes) {
		this.firmantes = firmantes;
	}


	public void setUsuarioGenerador(String usuarioGenerador) {
		this.usuarioGenerador = usuarioGenerador;
	}

	public String getUsuarioGenerador() {
		return usuarioGenerador;
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

	public Boolean getMostrarDescargas() {
		return mostrarDescargas;
	}

	public void setMostrarDescargas(Boolean mostrarDescargas) {
		this.mostrarDescargas = mostrarDescargas;
	}

	public Boolean getNoMostrarDescargas() {
		return noMostrarDescargas;
	}

	public void setNoMostrarDescargas(Boolean noMostrarDescargas) {
		this.noMostrarDescargas = noMostrarDescargas;
	}

	public String getWorkflowOrigen() {
		return workflowOrigen;
	}

	public void setWorkflowOrigen(String workflowOrigen) {
		this.workflowOrigen = workflowOrigen;
		
	}

	public Boolean getWorkflowNoDisponible() {
		return workflowNoDisponible;
	}

	public void setWorkflowNoDisponible(Boolean workflowNoDisponible) {
		this.workflowNoDisponible = workflowNoDisponible;
	}

	public Boolean getWorkflowDisponible() {
		return workflowDisponible;
	}

	public void setWorkflowDisponible(Boolean workflowDisponible) {
		this.workflowDisponible = workflowDisponible;
	}

	public Boolean getPoseeArchivosDeTrabajo() {
		return poseeArchivosDeTrabajo;
	}

	public void setPoseeArchivosDeTrabajo(Boolean poseeArchivosDeTrabajo) {
		this.poseeArchivosDeTrabajo = poseeArchivosDeTrabajo;
	}

	public Boolean getNoPoseeArchivosDeTrabajo() {
		return noPoseeArchivosDeTrabajo;
	}

	public void setNoPoseeArchivosDeTrabajo(Boolean noPoseeArchivosDeTrabajo) {
		this.noPoseeArchivosDeTrabajo = noPoseeArchivosDeTrabajo;
	}

	public String getMotivoParseado() {
		return motivoParseado;
	}

	public void setMotivoParseado(String motivoParseado) {
		this.motivoParseado = motivoParseado;
	}

	public void setSistemaOrigen(String sistemaOrigen) {
		this.sistemaOrigen = sistemaOrigen;
	}

	public String getSistemaOrigen() {
		return sistemaOrigen;
	}
	
	public String getUsuarioIniciador() {
		return usuarioIniciador;
	}

	public void setUsuarioIniciador(String usuarioIniciador) {
		this.usuarioIniciador = usuarioIniciador;
	}

	public String getSistemaIniciador() {
		return sistemaIniciador;
	}

	public void setSistemaIniciador(String sistemaIniciador) {
		this.sistemaIniciador = sistemaIniciador;
	}

	public TipoReservaDTO getTipoReserva() {
		return tipoReserva;
	}

	public void setTipoReserva(TipoReservaDTO tipoReserva) {
		this.tipoReserva = tipoReserva;
	}

	public Set<ReparticionAcumuladaDTO> getReparticionesAcumuladas() {
		return reparticionesAcumuladas;
	}
	
	public List<ReparticionAcumuladaDTO> getReparticionesAcumuladasList(){
		List<ReparticionAcumuladaDTO> listReparticionesAcumuladas = 
				new ArrayList<ReparticionAcumuladaDTO>(getReparticionesAcumuladas());
		return listReparticionesAcumuladas;
	}

	public void setReparticionesAcumuladas(Set<ReparticionAcumuladaDTO> reparticionesAcumuladas) {
		this.reparticionesAcumuladas = reparticionesAcumuladas;
	}

	public Set<UsuarioReservadoDTO> getUsuariosReservados() {
		return usuariosReservados;
	}

	public void setUsuariosReservados(Set<UsuarioReservadoDTO> usuariosReservados) {
		this.usuariosReservados = usuariosReservados;
	}

	public String getApoderado() {
		return apoderado;
	}

	public void setApoderado(String apoderado) {
		this.apoderado = apoderado;
	}

	public String getMotivoDepuracion() {
		return motivoDepuracion;
	}

	public void setMotivoDepuracion(String motivoDepuracion) {
		this.motivoDepuracion = motivoDepuracion;
	}

	public Date getFechaDepuracion() {
		return fechaDepuracion;
	}

	public void setFechaDepuracion(Date fechaDepuracion) {
		this.fechaDepuracion = fechaDepuracion;
	}	

	public String getIdGuardaDocumental() {
		return idGuardaDocumental;
	}

	public void setIdGuardaDocumental(String idGuardaDocumental) {
		this.idGuardaDocumental = idGuardaDocumental;
	}

	public Integer getPeso() {
		return peso;
	}

	public void setPeso(Integer peso) {
		this.peso = peso;
	}

	public String getIdPublicable() {
		return idPublicable;
	}

	public void setIdPublicable(String idPublicable) {
		this.idPublicable = idPublicable;
	}	

}