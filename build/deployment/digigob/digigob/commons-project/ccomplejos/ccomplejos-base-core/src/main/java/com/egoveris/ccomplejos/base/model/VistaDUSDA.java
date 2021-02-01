package com.egoveris.ccomplejos.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "view_dusda")
public class VistaDUSDA extends AbstractViewCComplejoJPA {


	@Column(name="TIPO_ENVIO")
	protected String tipoEnvio;
	@OneToOne
	@JoinColumn(name = "ID_PRODUCTO", referencedColumnName = "id", insertable = false, updatable = false)
	protected VistaComTransportadoraDUS comTrasnportadora;
	@Column(name="TOTAL_ITEM")
	protected Double totalItem;
	@Column(name="TOTAL_BULTOS")
	protected Double totalBultos;
	@Column(name = "CODIGO_DESPACHO")
	protected String codigoDespacho;
	@Column(name="INDICA_MERC_NAC")
	protected String indicaMercanciaNacionalizada;
	@Column(name="INDICA_MERC_CONS_ZP")
	protected String indicaMercanciaConsolidadaEnZonaPrimaria;
	@Column(name = "VIA_TRANSPORTE")
	protected String viaTransporte;
	@Column(name = "TIPO_OPERACION_ADUANA")
	protected String tipoOperacionAduanera;
	@Column(name = "CODIGO_FLETE")
	protected String codigoFlete;
	@Column(name = "DOCUMENTO_PARCIAL")
	protected String indicaDocumentoParcialDus;
	@Column(name = "ESTADO_DUS")
	protected String estadoDus;
	@Column(name = "NUMERO_DESPACHO")
	protected String numeroInternoDeDespacho;
	@Column(name = "REGION")
	protected String regionOrigen;

	/**
	 * @return the tipoEnvio
	 */
	public String getTipoEnvio() {
		return tipoEnvio;
	}

	/**
	 * @param tipoEnvio
	 *            the tipoEnvio to set
	 */
	public void setTipoEnvio(String tipoEnvio) {
		this.tipoEnvio = tipoEnvio;
	}

	/**
	 * @return the comTrasnportadora
	 */
	public VistaComTransportadoraDUS getComTrasnportadora() {
		return comTrasnportadora;
	}

	/**
	 * @param comTrasnportadora
	 *            the comTrasnportadora to set
	 */
	public void setComTrasnportadora(VistaComTransportadoraDUS comTrasnportadora) {
		this.comTrasnportadora = comTrasnportadora;
	}

	/**
	 * @return the totalItem
	 */
	public Double getTotalItem() {
		return totalItem;
	}

	/**
	 * @param totalItem
	 *            the totalItem to set
	 */
	public void setTotalItem(Double totalItem) {
		this.totalItem = totalItem;
	}

	/**
	 * @return the totalBultos
	 */
	public Double getTotalBultos() {
		return totalBultos;
	}

	/**
	 * @param totalBultos
	 *            the totalBultos to set
	 */
	public void setTotalBultos(Double totalBultos) {
		this.totalBultos = totalBultos;
	}

	/**
	 * @return the codigoDespacho
	 */
	public String getCodigoDespacho() {
		return codigoDespacho;
	}

	/**
	 * @param codigoDespacho
	 *            the codigoDespacho to set
	 */
	public void setCodigoDespacho(String codigoDespacho) {
		this.codigoDespacho = codigoDespacho;
	}

	/**
	 * @return the indicaMercanciaNacionalizada
	 */
	public String getIndicaMercanciaNacionalizada() {
		return indicaMercanciaNacionalizada;
	}

	/**
	 * @param indicaMercanciaNacionalizada
	 *            the indicaMercanciaNacionalizada to set
	 */
	public void setIndicaMercanciaNacionalizada(String indicaMercanciaNacionalizada) {
		this.indicaMercanciaNacionalizada = indicaMercanciaNacionalizada;
	}

	/**
	 * @return the indicaMercanciaConsolidadaEnZonaPrimaria
	 */
	public String getIndicaMercanciaConsolidadaEnZonaPrimaria() {
		return indicaMercanciaConsolidadaEnZonaPrimaria;
	}

	/**
	 * @param indicaMercanciaConsolidadaEnZonaPrimaria
	 *            the indicaMercanciaConsolidadaEnZonaPrimaria to set
	 */
	public void setIndicaMercanciaConsolidadaEnZonaPrimaria(String indicaMercanciaConsolidadaEnZonaPrimaria) {
		this.indicaMercanciaConsolidadaEnZonaPrimaria = indicaMercanciaConsolidadaEnZonaPrimaria;
	}

	/**
	 * @return the viaTransporte
	 */
	public String getViaTransporte() {
		return viaTransporte;
	}

	/**
	 * @param viaTransporte
	 *            the viaTransporte to set
	 */
	public void setViaTransporte(String viaTransporte) {
		this.viaTransporte = viaTransporte;
	}

	/**
	 * @return the tipoOperacionAduanera
	 */
	public String getTipoOperacionAduanera() {
		return tipoOperacionAduanera;
	}

	/**
	 * @param tipoOperacionAduanera
	 *            the tipoOperacionAduanera to set
	 */
	public void setTipoOperacionAduanera(String tipoOperacionAduanera) {
		this.tipoOperacionAduanera = tipoOperacionAduanera;
	}

	/**
	 * @return the codigoFlete
	 */
	public String getCodigoFlete() {
		return codigoFlete;
	}

	/**
	 * @param codigoFlete
	 *            the codigoFlete to set
	 */
	public void setCodigoFlete(String codigoFlete) {
		this.codigoFlete = codigoFlete;
	}

	/**
	 * @return the indicaDocumentoParcialDus
	 */
	public String getIndicaDocumentoParcialDus() {
		return indicaDocumentoParcialDus;
	}

	/**
	 * @param indicaDocumentoParcialDus
	 *            the indicaDocumentoParcialDus to set
	 */
	public void setIndicaDocumentoParcialDus(String indicaDocumentoParcialDus) {
		this.indicaDocumentoParcialDus = indicaDocumentoParcialDus;
	}

	/**
	 * @return the estadoDus
	 */
	public String getEstadoDus() {
		return estadoDus;
	}

	/**
	 * @param estadoDus
	 *            the estadoDus to set
	 */
	public void setEstadoDus(String estadoDus) {
		this.estadoDus = estadoDus;
	}

	/**
	 * @return the numeroInternoDeDespacho
	 */
	public String getNumeroInternoDeDespacho() {
		return numeroInternoDeDespacho;
	}

	/**
	 * @param numeroInternoDeDespacho
	 *            the numeroInternoDeDespacho to set
	 */
	public void setNumeroInternoDeDespacho(String numeroInternoDeDespacho) {
		this.numeroInternoDeDespacho = numeroInternoDeDespacho;
	}

	/**
	 * @return the regionOrigen
	 */
	public String getRegionOrigen() {
		return regionOrigen;
	}

	/**
	 * @param regionOrigen
	 *            the regionOrigen to set
	 */
	public void setRegionOrigen(String regionOrigen) {
		this.regionOrigen = regionOrigen;
	}


	
}