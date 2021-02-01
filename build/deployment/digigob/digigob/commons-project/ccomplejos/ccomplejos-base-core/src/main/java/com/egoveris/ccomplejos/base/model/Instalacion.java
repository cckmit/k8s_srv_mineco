package com.egoveris.ccomplejos.base.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CC_INSTALACION")
public class Instalacion extends AbstractCComplejoJPA {

	@Column(name = "OIG_PROPIE")
	String oigPropietaria;

	@Column(name = "NOMBRE_INST")
	String nombreInstalacion;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "DIRECCION_INST")
	Address direccionInstalacion;

	@Column(name = "NUM_RESOL_AUT")
	String numeroResolucionAut;

	@Column(name = "FECHA_EMI_RESOL")
	Date fechaEmisionResolucion;

	@Column(name = "ENTIDAD_EMI")
	String entidadEmisora;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "DIRECTOR_TECNICO")
	Participantes directorTecnico;

	@Column(name = "TIPO_INST_DEST")
	String tipoInstalacionDestino;

	@Column(name = "COD_INST_DEST")
	String codigoInstalacionDestino;

	@Column(name = "TELEF_INST_DEST")
	String telefonoInstalacionDestino;

	@Column(name = "CONT_INST_DEST")
	String contactoInstalacionDestino;

	@Column(name = "RAZON_SOCIAL")
	String razonSocial;

	@ManyToOne
	@JoinColumn(name = "ID_ITEM", referencedColumnName = "id")
	ItemJPA item;

	/**
	 * @return the oigPropietaria
	 */
	public String getOigPropietaria() {
		return oigPropietaria;
	}

	/**
	 * @param oigPropietaria
	 *            the oigPropietaria to set
	 */
	public void setOigPropietaria(String oigPropietaria) {
		this.oigPropietaria = oigPropietaria;
	}

	/**
	 * @return the nombreInstalacion
	 */
	public String getNombreInstalacion() {
		return nombreInstalacion;
	}

	/**
	 * @param nombreInstalacion
	 *            the nombreInstalacion to set
	 */
	public void setNombreInstalacion(String nombreInstalacion) {
		this.nombreInstalacion = nombreInstalacion;
	}

	/**
	 * @return the direccionInstalacion
	 */
	public Address getDireccionInstalacion() {
		return direccionInstalacion;
	}

	/**
	 * @param direccionInstalacion
	 *            the direccionInstalacion to set
	 */
	public void setDireccionInstalacion(Address direccionInstalacion) {
		this.direccionInstalacion = direccionInstalacion;
	}

	/**
	 * @return the numeroResolucionAut
	 */
	public String getNumeroResolucionAut() {
		return numeroResolucionAut;
	}

	/**
	 * @param numeroResolucionAut
	 *            the numeroResolucionAut to set
	 */
	public void setNumeroResolucionAut(String numeroResolucionAut) {
		this.numeroResolucionAut = numeroResolucionAut;
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
	 * @return the directorTecnico
	 */
	public Participantes getDirectorTecnico() {
		return directorTecnico;
	}

	/**
	 * @param directorTecnico
	 *            the directorTecnico to set
	 */
	public void setDirectorTecnico(Participantes directorTecnico) {
		this.directorTecnico = directorTecnico;
	}

	/**
	 * @return the tipoInstalacionDestino
	 */
	public String getTipoInstalacionDestino() {
		return tipoInstalacionDestino;
	}

	/**
	 * @param tipoInstalacionDestino
	 *            the tipoInstalacionDestino to set
	 */
	public void setTipoInstalacionDestino(String tipoInstalacionDestino) {
		this.tipoInstalacionDestino = tipoInstalacionDestino;
	}

	/**
	 * @return the codigoInstalacionDestino
	 */
	public String getCodigoInstalacionDestino() {
		return codigoInstalacionDestino;
	}

	/**
	 * @param codigoInstalacionDestino
	 *            the codigoInstalacionDestino to set
	 */
	public void setCodigoInstalacionDestino(String codigoInstalacionDestino) {
		this.codigoInstalacionDestino = codigoInstalacionDestino;
	}

	/**
	 * @return the telefonoInstalacionDestino
	 */
	public String getTelefonoInstalacionDestino() {
		return telefonoInstalacionDestino;
	}

	/**
	 * @param telefonoInstalacionDestino
	 *            the telefonoInstalacionDestino to set
	 */
	public void setTelefonoInstalacionDestino(String telefonoInstalacionDestino) {
		this.telefonoInstalacionDestino = telefonoInstalacionDestino;
	}

	/**
	 * @return the contactoInstalacionDestino
	 */
	public String getContactoInstalacionDestino() {
		return contactoInstalacionDestino;
	}

	/**
	 * @param contactoInstalacionDestino
	 *            the contactoInstalacionDestino to set
	 */
	public void setContactoInstalacionDestino(String contactoInstalacionDestino) {
		this.contactoInstalacionDestino = contactoInstalacionDestino;
	}

	/**
	 * @return the razonSocial
	 */
	public String getRazonSocial() {
		return razonSocial;
	}

	/**
	 * @param razonSocial
	 *            the razonSocial to set
	 */
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	/**
	 * @return the item
	 */
	public ItemJPA getItem() {
		return item;
	}

	/**
	 * @param item
	 *            the item to set
	 */
	public void setItem(ItemJPA item) {
		this.item = item;
	}

}
