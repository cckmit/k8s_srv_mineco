package com.egoveris.te.model.model;

import com.egoveris.te.model.model.DatosTareaBean;

import java.io.Serializable;
import java.util.List;

public class ConsultaExpedienteResponse implements Serializable {

	private static final long serialVersionUID = -5939279406356891106L;

	/**
	 * Código SADE del expediente electrónico.
	 */
	String codigoEE;
	/**
	 * Estado actual del expediente electrónico. Corresponde a un estado dentro
	 * del workflow por el cual puede transitar un expediente electrónico.
	 */
	String estado;
	/**
	 * Motivo que generó la creación o último movimiento (pase) del expediente
	 * electrónico.
	 */
	String motivo;
	/**
	 * Descripción de la trata con la cual fue generado el expediente
	 * electrónico.
	 */
	String descripcionTrata;
	/**
	 * Código de la trata con la cual fue generado el expediente electrónico.
	 */
	String codigotrata;
	/**
	 * Lista conteniendo los códigos SADE de los documentos oficiales vinculados
	 * al expediente electrónico.
	 */
	private List<String> listDocumentosOficiales;
	/**
	 * Lista conteniendo los nombres de los documentos de trabajo adjuntos al expediente electrónico.
	 */
	private List<String> listDocumentosDeTrabajo;
	/**
	 * Lista conteniendo los códigos SADE de los expedientes asociados al expediente electrónico.
	 */
	private List<String> listExpedientesAsociados;

	private List<DatosTareaBean> listaDatosTarea;
	
	private String codigoDocCaratula;
	
	private String sistemaOrigen;
	
	public ConsultaExpedienteResponse() {
		super();
	}

	public String getCodigoEE() {
		return codigoEE;
	}

	public void setCodigoEE(String codigoEE) {
		this.codigoEE = codigoEE;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getDescripcionTrata() {
		return descripcionTrata;
	}

	public void setDescripcionTrata(String descripcionTrata) {
		this.descripcionTrata = descripcionTrata;
	}

	public String getCodigotrata() {
		return codigotrata;
	}

	public void setCodigotrata(String codigotrata) {
		this.codigotrata = codigotrata;
	}

	public List<String> getListDocumentosOficiales() {
		return listDocumentosOficiales;
	}

	public void setListDocumentosOficiales(List<String> listDocumentosOficiales) {
		this.listDocumentosOficiales = listDocumentosOficiales;
	}

	public List<String> getListArchivosAdjuntos() {
		return listDocumentosDeTrabajo;
	}

	public void setListArchivosAdjuntos(List<String> listArchivosAdjuntos) {
		this.listDocumentosDeTrabajo = listArchivosAdjuntos;
	}

	public List<String> getListExpedientesAsociados() {
		return listExpedientesAsociados;
	}

	public void setListExpedientesAsociados(
			List<String> listExpedientesAsociados) {
		this.listExpedientesAsociados = listExpedientesAsociados;
	}

	public List<DatosTareaBean> getListaDatosTarea() {
		return listaDatosTarea;
	}

	public void setListaDatosTarea(List<DatosTareaBean> listaDatosTarea) {
		this.listaDatosTarea = listaDatosTarea;
	}

	public String getCodigoDocCaratula() {
		return codigoDocCaratula;
	}

	public void setCodigoDocCaratula(String codigoDocCaratula) {
		this.codigoDocCaratula = codigoDocCaratula;
	}

	public void setSistemaOrigen(String sistemaOrigen) {
		this.sistemaOrigen = sistemaOrigen;
	}

	public String getSistemaOrigen() {
		return sistemaOrigen;
	}
}