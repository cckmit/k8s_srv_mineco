package com.egoveris.te.model.model;


import com.egoveris.te.model.model.ExpedienteAsociadoDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConsultaExpedienteResponseDetallado extends ConsultaExpedienteResponse implements Serializable{

	
	/**
	 *  Descripción adicional del trámite (dato de la carátula que se completa al  caratular)
	 */
	String descripcionTramite;
	/**
	 * Fecha de caratulación
	 */
	Date f_caratulacion;

	boolean esUsuarioDestino = false;
	/**
	 * true: el pase se realiza a un sector determinado. Se deberá completar
	 * sectorDestino. false: caso contrario.
	 */
	boolean esSectorDestino = false;
	/**
	 * true: el pase se realiza a una repartición determinada. Deberán
	 * completarse reparticionDestino y sectorDestino. false: caso contrario.
	 */
	boolean esReparticionDestino = false;
	/**
	 * Si esUsuarioDestino es true. Corresponde al usuario destino del
	 * expediente en el pase.
	 */
	String usuarioDestino;
	/**
	 * Si esReparticionDestino es true. Corresponde a la repartición destino del
	 * expediente en el pase.
	 */
	String reparticionDestino;
	/**
	 * Si esSectorDestino es true. Corresponde al sector destino del expediente
	 * en el pase.
	 */
	String sectorDestino;
	
	/**
	 * Usuario caratulador del EE
	 */
	String usuarioCaratulador;
	
	/**
	 * Lista conteniendo los expedientesAsociadosDTO con los expedientes asociados al expediente electrónico.
	 */
	private List<ExpedienteAsociadoDTO> listaExpedientesAsociados;
	/**
	 * Lista conteniendo la  listExpedientesAsociadosTC  de expedientesAsociadosDTO con los expedientes asociados al expediente electrónico.
	 */
	private List<ExpedienteAsociadoDTO> listaExpedientesAsociadosTC;
	
	/**
	 * Lista conteniendo la  listExpedientesAsociadosFusion  de expedientesAsociadosDTO con los expedientes asociados al expediente electrónico.
	 */
	private List<ExpedienteAsociadoDTO> listaExpedientesAsociadosFusion;
	
	private List<ExpedienteMetadataExternal> datosPropios;
	

	public ConsultaExpedienteResponseDetallado() {
		super();
			this.datosPropios =  new ArrayList<>();
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = -2249761954395233580L;
	
	
	



	public String getDescripcionTramite() {
		return descripcionTramite;
	}


	public void setDescripcionTramite(String descripcionTramite) {
		this.descripcionTramite = descripcionTramite;
	}


	public Date getF_caratulacion() {
		return f_caratulacion;
	}


	public void setF_caratulacion(Date f_caratulacion) {
		this.f_caratulacion = f_caratulacion;
	}




	public String getUsuarioCaratulador() {
		return usuarioCaratulador;
	}


	public void setUsuarioCaratulador(String usuarioCaratulador) {
		this.usuarioCaratulador = usuarioCaratulador;
	}


	public List<ExpedienteMetadataExternal> getDatoVariable() {
		return datosPropios;
	}


	public void setDatoVariable(List<ExpedienteMetadataExternal> datosPropios) {
		this.datosPropios = datosPropios;
	}


	public boolean isUsuarioDestino() {
		return esUsuarioDestino;
	}


	public void setEsUsuarioDestino(boolean esUsuarioDestino) {
		this.esUsuarioDestino = esUsuarioDestino;
	}


	public boolean isSectorDestino() {
		return esSectorDestino;
	}


	public void setEsSectorDestino(boolean esSectorDestino) {
		this.esSectorDestino = esSectorDestino;
	}


	public boolean isReparticionDestino() {
		return esReparticionDestino;
	}


	public void setEsReparticionDestino(boolean esReparticionDestino) {
		this.esReparticionDestino = esReparticionDestino;
	}


	public String getReparticionDestino() {
		return reparticionDestino;
	}


	public void setReparticionDestino(String reparticionDestino) {
		this.reparticionDestino = reparticionDestino;
	}


	public String getSectorDestino() {
		return sectorDestino;
	}


	public void setSectorDestino(String sectorDestino) {
		this.sectorDestino = sectorDestino;
	}


	public String getUsuarioDestino() {
		return usuarioDestino;
	}


	public void setUsuarioDestino(String usuarioDestino) {
		this.usuarioDestino = usuarioDestino;
	}

	public List<ExpedienteAsociadoDTO> getListaExpedientesAsociados() {
		return listaExpedientesAsociados;
	}


	public void setListaExpedientesAsociados(
			List<ExpedienteAsociadoDTO> listaExpedientesAsociados) {
		this.listaExpedientesAsociados = listaExpedientesAsociados;
	}


	public List<ExpedienteAsociadoDTO> getListaExpedientesAsociadosTC() {
		return listaExpedientesAsociadosTC;
	}


	public void setListaExpedientesAsociadosTC(
			List<ExpedienteAsociadoDTO> listaExpedientesAsociadosTC) {
		this.listaExpedientesAsociadosTC = listaExpedientesAsociadosTC;
	}


	public List<ExpedienteAsociadoDTO> getListaExpedientesAsociadosFusion() {
		return listaExpedientesAsociadosFusion;
	}


	public void setListaExpedientesAsociadosFusion(
			List<ExpedienteAsociadoDTO> listaExpedientesAsociadosFusion) {
		this.listaExpedientesAsociadosFusion = listaExpedientesAsociadosFusion;
	}
}
