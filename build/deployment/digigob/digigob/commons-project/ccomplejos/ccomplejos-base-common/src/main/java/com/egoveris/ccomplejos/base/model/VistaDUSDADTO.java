package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;

public class VistaDUSDADTO extends AbstractCComplejoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2194198756982841831L;
	
	protected String tipoEnvio;
	protected String codigoDespacho;
	protected String tipoOperAduanera;
	protected String indicaDocParcialDus;
	protected String estadoDus;
	protected String numeroInternoDespacho;
	protected String regionOrigen;
	protected String viaTransporte;
	protected Double totalItem;
	protected Double totalBultos;
	protected String codigoFlete;
	protected String indicaMercNacionalizada;
	protected String indicaMercConsolidadaZP;
	protected VistaComTransportadoraDUSDTO comTrasnportadora;

	
	public String getTipoEnvio() {
		return tipoEnvio;
	}
	public void setTipoEnvio(String tipoEnvio) {
		this.tipoEnvio = tipoEnvio;
	}
	public String getCodigoDespacho() {
		return codigoDespacho;
	}
	public void setCodigoDespacho(String codigoDespacho) {
		this.codigoDespacho = codigoDespacho;
	}
	public String getTipoOperAduanera() {
		return tipoOperAduanera;
	}
	public void setTipoOperAduanera(String tipoOperAduanera) {
		this.tipoOperAduanera = tipoOperAduanera;
	}
	public String getIndicaDocParcialDus() {
		return indicaDocParcialDus;
	}
	public void setIndicaDocParcialDus(String indicaDocParcialDus) {
		this.indicaDocParcialDus = indicaDocParcialDus;
	}
	public String getEstadoDus() {
		return estadoDus;
	}
	public void setEstadoDus(String estadoDus) {
		this.estadoDus = estadoDus;
	}
	public String getNumeroInternoDespacho() {
		return numeroInternoDespacho;
	}
	public void setNumeroInternoDespacho(String numeroInternoDespacho) {
		this.numeroInternoDespacho = numeroInternoDespacho;
	}
	public String getRegionOrigen() {
		return regionOrigen;
	}
	public void setRegionOrigen(String regionOrigen) {
		this.regionOrigen = regionOrigen;
	}
	public String getViaTransporte() {
		return viaTransporte;
	}
	public void setViaTransporte(String viaTransporte) {
		this.viaTransporte = viaTransporte;
	}
	public Double getTotalItem() {
		return totalItem;
	}
	public void setTotalItem(Double totalItem) {
		this.totalItem = totalItem;
	}
	public Double getTotalBultos() {
		return totalBultos;
	}
	public void setTotalBultos(Double totalBultos) {
		this.totalBultos = totalBultos;
	}
	public String getCodigoFlete() {
		return codigoFlete;
	}
	public void setCodigoFlete(String codigoFlete) {
		this.codigoFlete = codigoFlete;
	}
	public String getIndicaMercNacionalizada() {
		return indicaMercNacionalizada;
	}
	public void setIndicaMercNacionalizada(String indicaMercNacionalizada) {
		this.indicaMercNacionalizada = indicaMercNacionalizada;
	}
	public String getIndicaMercConsolidadaZP() {
		return indicaMercConsolidadaZP;
	}
	public void setIndicaMercConsolidadaZP(String indicaMercConsolidadaZP) {
		this.indicaMercConsolidadaZP = indicaMercConsolidadaZP;
	}

	public VistaComTransportadoraDUSDTO getComTrasnportadora() {
		return comTrasnportadora;
	}

	public void setComTrasnportadora(VistaComTransportadoraDUSDTO comTransportadora) {
		this.comTrasnportadora = comTransportadora;
	}

}
