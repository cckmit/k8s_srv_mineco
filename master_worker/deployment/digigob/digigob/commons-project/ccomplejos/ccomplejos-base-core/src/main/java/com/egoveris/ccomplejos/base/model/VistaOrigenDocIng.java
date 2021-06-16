package com.egoveris.ccomplejos.base.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "view_origendocing")
public class VistaOrigenDocIng extends AbstractViewCComplejoJPA {

	@Column(name = "VIA_TRANSPORTE")
	protected String viaDeTransporte;
	@Column(name = "CODIGO_PUERTO")
	protected String codigoPuertoDeEmbarque;
	@Column(name = "CODIGO_PAIS")
	protected String codigoPaisProcedencia;
	@Column(name = "CODIGO_DES")
	protected String codigoPuertoDesembarque;
	@Column(name = "FECHA_EMBARQUE")
	protected Date fechaEmbarque;
	@Column(name = "FECHA_DESEMBARQUE")
	protected Date fechaDesembarque;
	@OneToOne
	@JoinColumn(name = "NUMERO_MANIFIESTO")
	protected Manifiesto manifiesto;
	@Column(name = "NOMBRE_NAVE")
	protected String nombreNave;
	@Column(name = "CODIGO_PAIS_ORIGEN")
	protected String codigoPaisOrigen;
	@Column(name = "NOMBRE_COMPANIA")
	protected String nombreCompaniaTransporte;
	@Column(name = "NUMERO_DOC_TRASPORTE")
	protected String numeroDocumentoTransporte;
	@Column(name = "FECHA_DOC_TRASPORTE")
	protected String fechaDocumentoTransporte;
	@OneToMany
	@JoinColumn(name = "ID_PRODUCTO", referencedColumnName = "id", insertable = false, updatable = false)
	protected List<VistaDetallesPuertoDA> transbordo;
	@OneToMany
	@JoinColumn(name = "ID_PRODUCTO", referencedColumnName = "id", insertable = false, updatable = false)
	protected List<VistaParticipantesDA> emisor;

	/**
	 * @return the viaDeTransporte
	 */
	public String getViaDeTransporte() {
		return viaDeTransporte;
	}

	/**
	 * @param viaDeTransporte
	 *            the viaDeTransporte to set
	 */
	public void setViaDeTransporte(String viaDeTransporte) {
		this.viaDeTransporte = viaDeTransporte;
	}

	/**
	 * @return the codigoPuertoDeEmbarque
	 */
	public String getCodigoPuertoDeEmbarque() {
		return codigoPuertoDeEmbarque;
	}

	/**
	 * @param codigoPuertoDeEmbarque
	 *            the codigoPuertoDeEmbarque to set
	 */
	public void setCodigoPuertoDeEmbarque(String codigoPuertoDeEmbarque) {
		this.codigoPuertoDeEmbarque = codigoPuertoDeEmbarque;
	}

	/**
	 * @return the codigoPaisProcedencia
	 */
	public String getCodigoPaisProcedencia() {
		return codigoPaisProcedencia;
	}

	/**
	 * @param codigoPaisProcedencia
	 *            the codigoPaisProcedencia to set
	 */
	public void setCodigoPaisProcedencia(String codigoPaisProcedencia) {
		this.codigoPaisProcedencia = codigoPaisProcedencia;
	}

	/**
	 * @return the codigoPuertoDesembarque
	 */
	public String getCodigoPuertoDesembarque() {
		return codigoPuertoDesembarque;
	}

	/**
	 * @param codigoPuertoDesembarque
	 *            the codigoPuertoDesembarque to set
	 */
	public void setCodigoPuertoDesembarque(String codigoPuertoDesembarque) {
		this.codigoPuertoDesembarque = codigoPuertoDesembarque;
	}

	/**
	 * @return the fechaEmbarque
	 */
	public Date getFechaEmbarque() {
		return fechaEmbarque;
	}

	/**
	 * @param fechaEmbarque
	 *            the fechaEmbarque to set
	 */
	public void setFechaEmbarque(Date fechaEmbarque) {
		this.fechaEmbarque = fechaEmbarque;
	}

	/**
	 * @return the fechaDesembarque
	 */
	public Date getFechaDesembarque() {
		return fechaDesembarque;
	}

	/**
	 * @param fechaDesembarque
	 *            the fechaDesembarque to set
	 */
	public void setFechaDesembarque(Date fechaDesembarque) {
		this.fechaDesembarque = fechaDesembarque;
	}

	/**
	 * @return the manifiesto
	 */
	public Manifiesto getManifiesto() {
		return manifiesto;
	}

	/**
	 * @param manifiesto
	 *            the manifiesto to set
	 */
	public void setManifiesto(Manifiesto manifiesto) {
		this.manifiesto = manifiesto;
	}

	/**
	 * @return the nombreNave
	 */
	public String getNombreNave() {
		return nombreNave;
	}

	/**
	 * @param nombreNave
	 *            the nombreNave to set
	 */
	public void setNombreNave(String nombreNave) {
		this.nombreNave = nombreNave;
	}

	/**
	 * @return the codigoPaisOrigen
	 */
	public String getCodigoPaisOrigen() {
		return codigoPaisOrigen;
	}

	/**
	 * @param codigoPaisOrigen
	 *            the codigoPaisOrigen to set
	 */
	public void setCodigoPaisOrigen(String codigoPaisOrigen) {
		this.codigoPaisOrigen = codigoPaisOrigen;
	}

	/**
	 * @return the nombreCompaniaTransporte
	 */
	public String getNombreCompaniaTransporte() {
		return nombreCompaniaTransporte;
	}

	/**
	 * @param nombreCompaniaTransporte
	 *            the nombreCompaniaTransporte to set
	 */
	public void setNombreCompaniaTransporte(String nombreCompaniaTransporte) {
		this.nombreCompaniaTransporte = nombreCompaniaTransporte;
	}

	/**
	 * @return the numeroDocumentoTransporte
	 */
	public String getNumeroDocumentoTransporte() {
		return numeroDocumentoTransporte;
	}

	/**
	 * @param numeroDocumentoTransporte
	 *            the numeroDocumentoTransporte to set
	 */
	public void setNumeroDocumentoTransporte(String numeroDocumentoTransporte) {
		this.numeroDocumentoTransporte = numeroDocumentoTransporte;
	}

	/**
	 * @return the fechaDocumentoTransporte
	 */
	public String getFechaDocumentoTransporte() {
		return fechaDocumentoTransporte;
	}

	/**
	 * @param fechaDocumentoTransporte
	 *            the fechaDocumentoTransporte to set
	 */
	public void setFechaDocumentoTransporte(String fechaDocumentoTransporte) {
		this.fechaDocumentoTransporte = fechaDocumentoTransporte;
	}

	/**
	 * @return the transbordo
	 */
	public List<VistaDetallesPuertoDA> getTransbordo() {
		return transbordo;
	}

	/**
	 * @param transbordo
	 *            the transbordo to set
	 */
	public void setTransbordo(List<VistaDetallesPuertoDA> transbordo) {
		this.transbordo = transbordo;
	}

	/**
	 * @return the emisor
	 */
	public List<VistaParticipantesDA> getEmisor() {
		return emisor;
	}

	/**
	 * @param emisor
	 *            the emisor to set
	 */
	public void setEmisor(List<VistaParticipantesDA> emisor) {
		this.emisor = emisor;
	}

}
