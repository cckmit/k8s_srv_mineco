package com.egoveris.te.base.model.trata;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "TRATA_REPARTICION_AUDITORIA")
public class TrataReparticionAuditoria implements Serializable {
	private static final long serialVersionUID = -2032497240542598591L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@Column(name = "FECHAOPERACION")
	private Date fechaOperacion;

	@Column(name = "ID_TRATA")
	private Long id_trata;

	@Column(name = "USUARIO")
	private String usuario;

	@Column(name = "TIPOOPERACION")
	private String tipoOperacion;

	@Column(name = "HABILITACION")
	private boolean habilitacion;

	@Column(name = "CODIGOREPARTICION")
	private String codigoReparticion;

	@Column(name = "RESERVA")
	private boolean reserva;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the fechaOperacion
	 */
	public Date getFechaOperacion() {
		return fechaOperacion;
	}

	/**
	 * @param fechaOperacion
	 *            the fechaOperacion to set
	 */
	public void setFechaOperacion(Date fechaOperacion) {
		this.fechaOperacion = fechaOperacion;
	}

	/**
	 * @return the id_trata
	 */
	public Long getId_trata() {
		return id_trata;
	}

	/**
	 * @param id_trata
	 *            the id_trata to set
	 */
	public void setId_trata(Long id_trata) {
		this.id_trata = id_trata;
	}

	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario
	 *            the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the tipoOperacion
	 */
	public String getTipoOperacion() {
		return tipoOperacion;
	}

	/**
	 * @param tipoOperacion
	 *            the tipoOperacion to set
	 */
	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	/**
	 * @return the habilitacion
	 */
	public boolean isHabilitacion() {
		return habilitacion;
	}

	/**
	 * @param habilitacion
	 *            the habilitacion to set
	 */
	public void setHabilitacion(boolean habilitacion) {
		this.habilitacion = habilitacion;
	}

	/**
	 * @return the codigoReparticion
	 */
	public String getCodigoReparticion() {
		return codigoReparticion;
	}

	/**
	 * @param codigoReparticion
	 *            the codigoReparticion to set
	 */
	public void setCodigoReparticion(String codigoReparticion) {
		this.codigoReparticion = codigoReparticion;
	}

	/**
	 * @return the reserva
	 */
	public boolean isReserva() {
		return reserva;
	}

	/**
	 * @param reserva
	 *            the reserva to set
	 */
	public void setReserva(boolean reserva) {
		this.reserva = reserva;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
