package com.egoveris.ccomplejos.base.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "view_intalaciondocingenvio")
public class VistaIntalacionDocIngEnvio extends AbstractViewCComplejoJPA {

	@Column(name = "NOMBRE_CONTACTO_ESTABLECIMIENTO")
	protected String nombreContactoEstablecimiento;
	@Column(name = "NOMBRE_DIRECTOR")
	protected String nombreDirectorTecnico;
	@Column(name = "TIPO_ESTABLECIMIENTO")
	protected String tipoEstablecimiento;
	@Column(name = "CODIGO_DESTINO")
	protected String codigoEstablecimiento;
	@Column(name = "NOMBRE_ESTABLECIMIENTO")
	protected String nombreEstablecimiento;
	@Column(name = "AUTORIZADO")
	protected String establecimientoAutorizado;
	@Column(name = "RAZON_SOCIAL")
	protected String razonSocialEstablecimiento;
	@Column(name = "NUMERO_RESOLUCION")
	protected String numeroResolucionAutorizacion;
	@Column(name = "FECHA_EMISION")
	protected Date fechaEmisionResolucion;
	@Column(name = "ENTIDAD_EMISORA")
	protected String entidadEmisora;
	@Column(name = "TELEFONO_INSTALACION")
	protected String telefonoInstalacion;
	@Column(name = "DIRECCION")
	protected String direccionEstablecimiento;
	@Column(name = "REGION")
	protected String regionInstalacion;
	@Column(name = "COMUNA")
	protected String comunaInstalacion;

	/**
	 * @return the nombreContactoEstablecimiento
	 */
	public String getNombreContactoEstablecimiento() {
		return nombreContactoEstablecimiento;
	}

	/**
	 * @param nombreContactoEstablecimiento
	 *            the nombreContactoEstablecimiento to set
	 */
	public void setNombreContactoEstablecimiento(String nombreContactoEstablecimiento) {
		this.nombreContactoEstablecimiento = nombreContactoEstablecimiento;
	}

	/**
	 * @return the nombreDirectorTecnico
	 */
	public String getNombreDirectorTecnico() {
		return nombreDirectorTecnico;
	}

	/**
	 * @param nombreDirectorTecnico
	 *            the nombreDirectorTecnico to set
	 */
	public void setNombreDirectorTecnico(String nombreDirectorTecnico) {
		this.nombreDirectorTecnico = nombreDirectorTecnico;
	}

	/**
	 * @return the tipoEstablecimiento
	 */
	public String getTipoEstablecimiento() {
		return tipoEstablecimiento;
	}

	/**
	 * @param tipoEstablecimiento
	 *            the tipoEstablecimiento to set
	 */
	public void setTipoEstablecimiento(String tipoEstablecimiento) {
		this.tipoEstablecimiento = tipoEstablecimiento;
	}

	/**
	 * @return the codigoEstablecimiento
	 */
	public String getCodigoEstablecimiento() {
		return codigoEstablecimiento;
	}

	/**
	 * @param codigoEstablecimiento
	 *            the codigoEstablecimiento to set
	 */
	public void setCodigoEstablecimiento(String codigoEstablecimiento) {
		this.codigoEstablecimiento = codigoEstablecimiento;
	}

	/**
	 * @return the nombreEstablecimiento
	 */
	public String getNombreEstablecimiento() {
		return nombreEstablecimiento;
	}

	/**
	 * @param nombreEstablecimiento
	 *            the nombreEstablecimiento to set
	 */
	public void setNombreEstablecimiento(String nombreEstablecimiento) {
		this.nombreEstablecimiento = nombreEstablecimiento;
	}

	/**
	 * @return the establecimientoAutorizado
	 */
	public String isEstablecimientoAutorizado() {
		return establecimientoAutorizado;
	}

	/**
	 * @param establecimientoAutorizado
	 *            the establecimientoAutorizado to set
	 */
	public void setEstablecimientoAutorizado(String establecimientoAutorizado) {
		this.establecimientoAutorizado = establecimientoAutorizado;
	}

	/**
	 * @return the razonSocialEstablecimiento
	 */
	public String getRazonSocialEstablecimiento() {
		return razonSocialEstablecimiento;
	}

	/**
	 * @param razonSocialEstablecimiento
	 *            the razonSocialEstablecimiento to set
	 */
	public void setRazonSocialEstablecimiento(String razonSocialEstablecimiento) {
		this.razonSocialEstablecimiento = razonSocialEstablecimiento;
	}

	/**
	 * @return the numeroResolucionAutorizacion
	 */
	public String getNumeroResolucionAutorizacion() {
		return numeroResolucionAutorizacion;
	}

	/**
	 * @param numeroResolucionAutorizacion
	 *            the numeroResolucionAutorizacion to set
	 */
	public void setNumeroResolucionAutorizacion(String numeroResolucionAutorizacion) {
		this.numeroResolucionAutorizacion = numeroResolucionAutorizacion;
	}

	/**
	 * @return the fechaEmisionResolucion
	 */
	public Date getFechaEmisionResolucion() {
		return fechaEmisionResolucion;
	}

	/**
	 * @param fechaEmisionResolucion
	 *            the fechaEmisionResolucion to set
	 */
	public void setFechaEmisionResolucion(Date fechaEmisionResolucion) {
		this.fechaEmisionResolucion = fechaEmisionResolucion;
	}

	/**
	 * @return the entidadEmisora
	 */
	public String getEntidadEmisora() {
		return entidadEmisora;
	}

	/**
	 * @param entidadEmisora
	 *            the entidadEmisora to set
	 */
	public void setEntidadEmisora(String entidadEmisora) {
		this.entidadEmisora = entidadEmisora;
	}

	/**
	 * @return the telefonoInstalacion
	 */
	public String getTelefonoInstalacion() {
		return telefonoInstalacion;
	}

	/**
	 * @param telefonoInstalacion
	 *            the telefonoInstalacion to set
	 */
	public void setTelefonoInstalacion(String telefonoInstalacion) {
		this.telefonoInstalacion = telefonoInstalacion;
	}

	/**
	 * @return the direccionEstablecimiento
	 */
	public String getDireccionEstablecimiento() {
		return direccionEstablecimiento;
	}

	/**
	 * @param direccionEstablecimiento
	 *            the direccionEstablecimiento to set
	 */
	public void setDireccionEstablecimiento(String direccionEstablecimiento) {
		this.direccionEstablecimiento = direccionEstablecimiento;
	}

	/**
	 * @return the regionInstalacion
	 */
	public String getRegionInstalacion() {
		return regionInstalacion;
	}

	/**
	 * @param regionInstalacion
	 *            the regionInstalacion to set
	 */
	public void setRegionInstalacion(String regionInstalacion) {
		this.regionInstalacion = regionInstalacion;
	}

	/**
	 * @return the comunaInstalacion
	 */
	public String getComunaInstalacion() {
		return comunaInstalacion;
	}

	/**
	 * @param comunaInstalacion
	 *            the comunaInstalacion to set
	 */
	public void setComunaInstalacion(String comunaInstalacion) {
		this.comunaInstalacion = comunaInstalacion;
	}

}