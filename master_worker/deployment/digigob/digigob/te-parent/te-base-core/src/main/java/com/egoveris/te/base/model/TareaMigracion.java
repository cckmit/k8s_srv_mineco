package com.egoveris.te.base.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="EE_AUD_TAREA_MIGRACION")
public class TareaMigracion implements Serializable {
	private static final long serialVersionUID = 7906721943937251301L;

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="TAREA")
	private String tarea;
	
	@Column(name="CODIGO_REPARTICION_ORIGEN")
	private String codigoReparticionOrigen;
	
	@Column(name="CODIGO_REPARTICION_DESTINO")
	private String codigoReparticionDestino;
	
	@Column(name="CODIGO_SECTOR_ORIGEN")
	private String codigoSectorOrigen;
	
	@Column(name="CODIGO_SECTOR_DESTINO")
	private String codigoSectorDestino;
	
	@Column(name="FECHA")
	private Date fecha;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the tarea
	 */
	public String getTarea() {
		return tarea;
	}

	/**
	 * @param tarea the tarea to set
	 */
	public void setTarea(String tarea) {
		this.tarea = tarea;
	}

	/**
	 * @return the codigoReparticionOrigen
	 */
	public String getCodigoReparticionOrigen() {
		return codigoReparticionOrigen;
	}

	/**
	 * @param codigoReparticionOrigen the codigoReparticionOrigen to set
	 */
	public void setCodigoReparticionOrigen(String codigoReparticionOrigen) {
		this.codigoReparticionOrigen = codigoReparticionOrigen;
	}

	/**
	 * @return the codigoReparticionDestino
	 */
	public String getCodigoReparticionDestino() {
		return codigoReparticionDestino;
	}

	/**
	 * @param codigoReparticionDestino the codigoReparticionDestino to set
	 */
	public void setCodigoReparticionDestino(String codigoReparticionDestino) {
		this.codigoReparticionDestino = codigoReparticionDestino;
	}

	/**
	 * @return the codigoSectorOrigen
	 */
	public String getCodigoSectorOrigen() {
		return codigoSectorOrigen;
	}

	/**
	 * @param codigoSectorOrigen the codigoSectorOrigen to set
	 */
	public void setCodigoSectorOrigen(String codigoSectorOrigen) {
		this.codigoSectorOrigen = codigoSectorOrigen;
	}

	/**
	 * @return the codigoSectorDestino
	 */
	public String getCodigoSectorDestino() {
		return codigoSectorDestino;
	}

	/**
	 * @param codigoSectorDestino the codigoSectorDestino to set
	 */
	public void setCodigoSectorDestino(String codigoSectorDestino) {
		this.codigoSectorDestino = codigoSectorDestino;
	}

	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

}
