package com.egoveris.numerador.base.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="NUMERO_PROCESO_BATCH")
public class NumeroProcesoBatch implements Serializable{

	/**
	 *@author cavazque
	 *Hace referencia a la tabla numero_proceso_batch
	 *contiene la cantidad de registros procesados, el sistema y el anio
	 *que ejecuta el proceso batch
	 * 
	 */
	private static final long serialVersionUID = -8154659509729828688L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_PROCESO_BATCH")
	private Integer id;
	
	@Column(name="ANIO")
	private Integer anio;
	
	@Column(name="SISTEMA")
	private String  sistema;
	
	@Column(name="CANTIDAD_REGISTRO_ACTUALIZADO")
	private Integer cantidadRegistrosActualizados;
	
	@Column(name="ESTADO")
	private String estado;
	
	@Column(name="FECHA_INICIO_PROCESO")
	private Date fechaInicioProceso;
	
	@Column(name="FECHA_FIN_PROCESO")
	private Date fechaFinProceso;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getAnio() {
		return anio;
	}
	public void setAnio(Integer anio) {
		this.anio = anio;
	}
	public String getSistema() {
		return sistema;
	}
	public void setSistema(String sistema) {
		this.sistema = sistema;
	}
	public Integer getcantidadRegistrosActualizados() {
		return cantidadRegistrosActualizados;
	}
	public void setcantidadRegistrosActualizados(Integer cantidadRegistrosActualizados) {
		this.cantidadRegistrosActualizados = cantidadRegistrosActualizados;
	}
	
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Date getFechaInicioProceso() {
		return fechaInicioProceso;
	}
	public void setFechaInicioProceso(Date fechaInicioProceso) {
		this.fechaInicioProceso = fechaInicioProceso;
	}
	public Date getFechaFinProceso() {
		return fechaFinProceso;
	}
	public void setFechaFinProceso(Date fechaFinProceso) {
		this.fechaFinProceso = fechaFinProceso;
	}
	
}
