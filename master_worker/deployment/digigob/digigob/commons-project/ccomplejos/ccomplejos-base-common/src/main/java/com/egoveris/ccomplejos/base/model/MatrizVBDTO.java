package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;
import java.util.Date;

public class MatrizVBDTO extends AbstractCComplejoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3485625280494835354L;
	
	
	protected Long idMatrizVB;
	protected String codigoMatrizVB;
	protected String regimeMatrizVB;
	protected String nombreMatrizVB;
	protected String nombreInglesMatrizVB;
	protected String descripcionMatrizVB;
	protected String descripcionInglesmatrizVB;
	protected Date validaDesde;
	protected Date validaHasta;
	protected String todosHSCode;
	protected String todosPaises;
	protected String todosCaracteresEspeciales;
	protected String controlAnticipado;
	protected String premIns;
	protected String estadoMatrizVB;
	protected Long idUsoPrevisto;
	protected Long idCaracteristicaEspecial;
	
	public Long getIdMatrizVB() {
		return idMatrizVB;
	}
	public void setIdMatrizVB(Long idMatrizVB) {
		this.idMatrizVB = idMatrizVB;
	}
	public String getCodigoMatrizVB() {
		return codigoMatrizVB;
	}
	public void setCodigoMatrizVB(String codigoMatrizVB) {
		this.codigoMatrizVB = codigoMatrizVB;
	}
	public String getRegimeMatrizVB() {
		return regimeMatrizVB;
	}
	public void setRegimeMatrizVB(String regimeMatrizVB) {
		this.regimeMatrizVB = regimeMatrizVB;
	}
	public String getNombreMatrizVB() {
		return nombreMatrizVB;
	}
	public void setNombreMatrizVB(String nombreMatrizVB) {
		this.nombreMatrizVB = nombreMatrizVB;
	}
	public String getNombreInglesMatrizVB() {
		return nombreInglesMatrizVB;
	}
	public void setNombreInglesMatrizVB(String nombreInglesMatrizVB) {
		this.nombreInglesMatrizVB = nombreInglesMatrizVB;
	}
	public String getDescripcionMatrizVB() {
		return descripcionMatrizVB;
	}
	public void setDescripcionMatrizVB(String descripcionMatrizVB) {
		this.descripcionMatrizVB = descripcionMatrizVB;
	}
	public String getDescripcionInglesmatrizVB() {
		return descripcionInglesmatrizVB;
	}
	public void setDescripcionInglesmatrizVB(String descripcionInglesmatrizVB) {
		this.descripcionInglesmatrizVB = descripcionInglesmatrizVB;
	}
	public Date getValidaDesde() {
		return validaDesde;
	}
	public void setValidaDesde(Date validaDesde) {
		this.validaDesde = validaDesde;
	}
	public Date getValidaHasta() {
		return validaHasta;
	}
	public void setValidaHasta(Date validaHasta) {
		this.validaHasta = validaHasta;
	}
	public String getTodosHSCode() {
		return todosHSCode;
	}
	public void setTodosHSCode(String todosHSCode) {
		this.todosHSCode = todosHSCode;
	}
	public String getTodosPaises() {
		return todosPaises;
	}
	public void setTodosPaises(String todosPaises) {
		this.todosPaises = todosPaises;
	}
	public String getTodosCaracteresEspeciales() {
		return todosCaracteresEspeciales;
	}
	public void setTodosCaracteresEspeciales(String todosCaracteresEspeciales) {
		this.todosCaracteresEspeciales = todosCaracteresEspeciales;
	}
	public String getControlAnticipado() {
		return controlAnticipado;
	}
	public void setControlAnticipado(String controlAnticipado) {
		this.controlAnticipado = controlAnticipado;
	}
	public String getPremIns() {
		return premIns;
	}
	public void setPremIns(String premIns) {
		this.premIns = premIns;
	}
	public String getEstadoMatrizVB() {
		return estadoMatrizVB;
	}
	public void setEstadoMatrizVB(String estadoMatrizVB) {
		this.estadoMatrizVB = estadoMatrizVB;
	}
	public Long getIdUsoPrevisto() {
		return idUsoPrevisto;
	}
	public void setIdUsoPrevisto(Long idUsoPrevisto) {
		this.idUsoPrevisto = idUsoPrevisto;
	}
	public Long getIdCaracteristicaEspecial() {
		return idCaracteristicaEspecial;
	}
	public void setIdCaracteristicaEspecial(Long idCaracteristicaEspecial) {
		this.idCaracteristicaEspecial = idCaracteristicaEspecial;
	}

	

}
