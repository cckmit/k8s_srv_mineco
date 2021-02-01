package com.egoveris.te.model.model;

import java.io.Serializable;

/**
 * @author cearagon La presente clase da forma a los datos que se requiere
 *         ingresar para la realización de un pase de expediente.
 * 
 *         NOTAS: - Cuando se realiza un pase con bloqueo, significa que el
 *         expediente, de este punto en adelante, estará en poder de un sistema
 *         externo (de ahora en más sistX). Este sistX será el parámetro
 *         sistemaOrígen, que es el sistema al cual pertenece el usuario
 *         generador del pase (usuarioOrigen). Es decir, el usuarioOrigen se
 *         apodera del expediente para poder trabajarlo en su sistX y
 *         bloquearlo, tanto para EE como para cualquier otro sistema externo.
 * 
 *         - Cuando se realiza un pase sin bloqueo, el expediente puede cambiar
 *         de estado, asignación de usuario, sector y/o repartición; pero
 *         conserva su sistemaApoderado y su estado de bloqueo.
 * 
 *         - Cuando se realiza un pase con desbloqueo, el expediente cambia su
 *         sistemaApoderado a EE y su estado a desbloqueado (bloqueado=false).
 * 
 */

public class PaseExpedienteRequest implements Serializable {

	private static final long serialVersionUID = -177078925517489174L;

	/**
	 * Código SADE del expediente electrónico, el cual sufre el pase.
	 * Ej."EX-2012-00001234-   -MGEYA-MGEYA"
	 */
	String codigoEE;
	/**
	 * Usuario que formula el pase.
	 */
	String usuarioOrigen;
	/**
	 * Estado al cual se realiza el pase del expediente electrónico. Un valor
	 * null en este parámetro, conservará el estado actual del expediente
	 * electrónico.
	 */
	String estadoSeleccionado;
	/**
	 * Motivo que causa el pase del expediente electrónico.
	 */
	String motivoPase;
	/**
	 * true: el pase se realiza a un usuario determinado. Se debará completar
	 * usuarioDestino. false: caso contrario.
	 */
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
	 * true: el pase se realiza a una sector determinado en base a la reparticionDestino
	 * false: caso contrario.
	 */
	private boolean esMesaDestino = false;
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
	 * Nombre del sistema orígen solicitante del pase. Corresponde al sistema al
	 * cual pertenece usuarioOrigen.
	 */
	String sistemaOrigen;

	// Getters and Setters
	public String getSistemaOrigen() {
		return sistemaOrigen;
	}

	public void setSistemaOrigen(String sistemaOrigen) {
		this.sistemaOrigen = sistemaOrigen;
	}

	public String getCodigoEE() {
		return codigoEE;
	}

	public void setCodigoEE(String codigoEE) {
		this.codigoEE = codigoEE;
	}

	public String getUsuarioOrigen() {
		return usuarioOrigen;
	}

	public void setUsuarioOrigen(String usuarioOrigen) {
		this.usuarioOrigen = usuarioOrigen;
	}

	public String getEstadoSeleccionado() {
		return estadoSeleccionado;
	}

	public void setEstadoSeleccionado(String estadoSeleccionado) {
		this.estadoSeleccionado = estadoSeleccionado;
	}

	public String getMotivoPase() {
		return motivoPase;
	}

	public void setMotivoPase(String motivoPase) {
		this.motivoPase = motivoPase;
	}

	public String getUsuarioDestino() {
		return usuarioDestino;
	}

	public void setUsuarioDestino(String usuarioDestino) {
		this.usuarioDestino = usuarioDestino;
	}

	public boolean getEsUsuarioDestino() {
		return esUsuarioDestino;
	}

	public void setEsUsuarioDestino(boolean esUsuarioDestino) {
		this.esUsuarioDestino = esUsuarioDestino;
	}

	public boolean getEsSectorDestino() {
		return esSectorDestino;
	}

	public void setEsSectorDestino(boolean esSectorDestino) {
		this.esSectorDestino = esSectorDestino;
	}

	public boolean getEsReparticionDestino() {
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

	public boolean getEsMesaDestino() {
		return esMesaDestino;
	}

	public void setEsMesaDestino(boolean esMesaDestino) {
		this.esMesaDestino = esMesaDestino;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("codigoEE: ");
		sb.append(codigoEE);
		sb.append(" - usuarioOrigen: ");
		sb.append(usuarioOrigen);
		sb.append(" - estadoSeleccionado: ");
		sb.append(estadoSeleccionado);
		sb.append(" - motivoPase: ");
		sb.append(motivoPase);
		sb.append(" - esUsuarioDestino: ");
		sb.append(esUsuarioDestino);
		sb.append(" - esSectorDestino: ");
		sb.append(esSectorDestino);
		sb.append(" - esReparticionDestino: ");
		sb.append(esReparticionDestino);
		sb.append(" - esMesaDestino: ");
		sb.append(esMesaDestino);
		sb.append(" - usuarioDestino: ");
		sb.append(usuarioDestino);
		sb.append(" - reparticionDestino: ");
		sb.append(reparticionDestino);
		sb.append(" - sectorDestino: ");
		sb.append(sectorDestino);
		sb.append(" - sistemaOrigen: ");
		sb.append(sistemaOrigen);
		return sb.toString();
	}
}
