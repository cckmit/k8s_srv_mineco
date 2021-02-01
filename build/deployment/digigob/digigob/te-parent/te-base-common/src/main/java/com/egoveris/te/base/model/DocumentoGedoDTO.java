package com.egoveris.te.base.model;

import com.egoveris.te.base.util.ConstantesCommon;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Esta es la clase Documento del proyecto Gedo que ha sido adaptada para usarse en EE.
 * la misma es utilizada para mejorar le eficiencia en las consultas a la base de datos de Gedo
 *
 * @author: Jorge Federico Flores (joflores)
 * @date: 07/10/2014
 */
public class DocumentoGedoDTO implements Serializable {
	private static final Logger logger = LoggerFactory.getLogger(DocumentoGedoDTO.class);

	private static final long serialVersionUID = 3779512000403727497L;

	private Long id;
	private String numero;
	private String numeroEspecial;
	private String reparticion;
	private String anio;
	private String usuarioGenerador;
	private String motivo;
	private List<String> firmantes;
	private TipoDocumentoGedoDTO tipo;
	private Date fechaCreacion;
	private Boolean mostrarDescargas = true;
	private Boolean noMostrarDescargas = false;
	private String workflowOrigen;
	private Boolean workflowNoDisponible = false;
	private Boolean workflowDisponible = true;
	private String sistemaOrigen = ConstantesCommon.NOMBRE_APLICACION_GEDO;
	private String sistemaIniciador = ConstantesCommon.NOMBRE_APLICACION_GEDO;
	private String usuarioIniciador;
	private String numeroSadePapel;
	private Integer tipoReserva;
	private String motivoDepuracion;
	
	//private TipoReservaGedo tipoReserva;

//	public TipoReservaGedo getTipoReserva() {
//		return tipoReserva;
//	}
//
//	public void setTipoReserva(TipoReservaGedo tipoReserva) {
//		this.tipoReserva = tipoReserva;
//	}

	private transient Boolean poseeArchivosDeTrabajo = false;
	private transient Boolean noPoseeArchivosDeTrabajo = false;
	private transient String motivoParseado;

	public String getNumeroSadePapel() {
		return numeroSadePapel;
	}

	public void setNumeroSadePapel(String numeroSadePapel) {
		this.numeroSadePapel = numeroSadePapel;
	}

	public DocumentoGedoDTO() {
		this.fechaCreacion = new Date();
	}

	public TipoDocumentoGedoDTO getTipo() {
		return tipo;
	}

	public void setTipo(TipoDocumentoGedoDTO tipo) {
		this.tipo = tipo;
	}

	public Long getId() {
		return id;
	}

	public String getNumeroEspecial() {
		return numeroEspecial;
	}

	public void setNumeroEspecial(String numeroEspecial) {
		this.numeroEspecial = numeroEspecial;
	}

	public void setId(Long id) {
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
	
	public Integer getTipoReserva() {
		return tipoReserva;
	}

	public void setTipoReserva(Integer tipoReserva) {
		this.tipoReserva = tipoReserva;
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

	public String getMotivoDepuracion() {
		return motivoDepuracion;
	}

	public void setMotivoDepuracion(String motivoDepuracion) {
		this.motivoDepuracion = motivoDepuracion;
	}

	public boolean esReservado() {
		if (logger.isDebugEnabled()) {
			logger.debug("esReservado() - start");
		}

		boolean returnboolean = this.getTipoReserva().equals(3) || this.getTipoReserva().equals(4);
		if (logger.isDebugEnabled()) {
			logger.debug("esReservado() - end - return value={}", returnboolean);
		}
		return returnboolean;
	}
}
