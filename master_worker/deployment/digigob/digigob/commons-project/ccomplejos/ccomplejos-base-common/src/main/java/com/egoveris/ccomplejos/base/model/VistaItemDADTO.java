package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class VistaItemDADTO extends AbstractCComplejoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8830521665908317270L;
	
	
	protected BigDecimal pesoBrutoItem;
	protected String codigoArancelProd;
	protected String unidadMedidaArancelProd;
	protected Integer secuencialItem;
	protected BigDecimal montoFOBItem;
	protected BigDecimal valorMercanciaItem;
	protected Integer numeroItem;
	protected BigDecimal fobUnitario;
	protected BigDecimal fobUSD;
	protected BigDecimal cantidadKilosNetoProd;
	protected BigDecimal cantidadKilosBrutosProd;
	protected String estadoItem;
	protected Integer cantidadItemProducto;
	protected String unidadDeMedida;
	protected Integer cantidadDeMercancia;
	protected VistaProductDADTO producto;
	protected String numeroSolicitudAutorizacion;
	private List<LoteDTO> listLote;
	private List<VistaDocumentoApoyoDADTO> documentosRelacionados;
	private List<VistaObservacionDADTO> observaciones;
	// private List<String> codigoLote;

	/**
	 * @return the pesoBrutoItem
	 */
	public BigDecimal getPesoBrutoItem() {
		return pesoBrutoItem;
	}

	/**
	 * @param pesoBrutoItem
	 *            the pesoBrutoItem to set
	 */
	public void setPesoBrutoItem(BigDecimal pesoBrutoItem) {
		this.pesoBrutoItem = pesoBrutoItem;
	}

	/**
	 * @return the codigoArancelProd
	 */
	public String getCodigoArancelProd() {
		return codigoArancelProd;
	}

	/**
	 * @param codigoArancelProd
	 *            the codigoArancelProd to set
	 */
	public void setCodigoArancelProd(String codigoArancelProd) {
		this.codigoArancelProd = codigoArancelProd;
	}

	/**
	 * @return the unidadMedidaArancelProd
	 */
	public String getUnidadMedidaArancelProd() {
		return unidadMedidaArancelProd;
	}

	/**
	 * @param unidadMedidaArancelProd
	 *            the unidadMedidaArancelProd to set
	 */
	public void setUnidadMedidaArancelProd(String unidadMedidaArancelProd) {
		this.unidadMedidaArancelProd = unidadMedidaArancelProd;
	}

	/**
	 * @return the secuencialItem
	 */
	public Integer getSecuencialItem() {
		return secuencialItem;
	}

	/**
	 * @param secuencialItem
	 *            the secuencialItem to set
	 */
	public void setSecuencialItem(Integer secuencialItem) {
		this.secuencialItem = secuencialItem;
	}

	/**
	 * @return the montoFOBItem
	 */
	public BigDecimal getMontoFOBItem() {
		return montoFOBItem;
	}

	/**
	 * @param montoFOBItem
	 *            the montoFOBItem to set
	 */
	public void setMontoFOBItem(BigDecimal montoFOBItem) {
		this.montoFOBItem = montoFOBItem;
	}

	/**
	 * @return the valorMercanciaItem
	 */
	public BigDecimal getValorMercanciaItem() {
		return valorMercanciaItem;
	}

	/**
	 * @param valorMercanciaItem
	 *            the valorMercanciaItem to set
	 */
	public void setValorMercanciaItem(BigDecimal valorMercanciaItem) {
		this.valorMercanciaItem = valorMercanciaItem;
	}

	/**
	 * @return the numeroItem
	 */
	public Integer getNumeroItem() {
		return numeroItem;
	}

	/**
	 * @param numeroItem
	 *            the numeroItem to set
	 */
	public void setNumeroItem(Integer numeroItem) {
		this.numeroItem = numeroItem;
	}

	/**
	 * @return the fobUnitario
	 */
	public BigDecimal getFobUnitario() {
		return fobUnitario;
	}

	/**
	 * @param fobUnitario
	 *            the fobUnitario to set
	 */
	public void setFobUnitario(BigDecimal fobUnitario) {
		this.fobUnitario = fobUnitario;
	}

	/**
	 * @return the fobUSD
	 */
	public BigDecimal getFobUSD() {
		return fobUSD;
	}

	/**
	 * @param fobUSD
	 *            the fobUSD to set
	 */
	public void setFobUSD(BigDecimal fobUSD) {
		this.fobUSD = fobUSD;
	}

	/**
	 * @return the cantKilosNetoProd
	 */
	public BigDecimal getCantKilosNetoProd() {
		return cantidadKilosNetoProd;
	}

	/**
	 * @param cantKilosNetoProd
	 *            the cantKilosNetoProd to set
	 */
	public void setCantKilosNetoProd(BigDecimal cantKilosNetoProd) {
		this.cantidadKilosNetoProd = cantKilosNetoProd;
	}

	/**
	 * @return the cantKilosBrutosProd
	 */
	public BigDecimal getCantKilosBrutosProd() {
		return cantidadKilosBrutosProd;
	}

	/**
	 * @param cantKilosBrutosProd
	 *            the cantKilosBrutosProd to set
	 */
	public void setCantKilosBrutosProd(BigDecimal cantKilosBrutosProd) {
		this.cantidadKilosBrutosProd = cantKilosBrutosProd;
	}

	/**
	 * @return the estadoItem
	 */
	public String getEstadoItem() {
		return estadoItem;
	}

	/**
	 * @param estadoItem
	 *            the estadoItem to set
	 */
	public void setEstadoItem(String estadoItem) {
		this.estadoItem = estadoItem;
	}

	/**
	 * @return the cantidadItemProducto
	 */
	public Integer getCantidadItemProducto() {
		return cantidadItemProducto;
	}

	/**
	 * @param cantidadItemProducto
	 *            the cantidadItemProducto to set
	 */
	public void setCantidadItemProducto(Integer cantidadItemProducto) {
		this.cantidadItemProducto = cantidadItemProducto;
	}

	/**
	 * @return the unidadDeMedida
	 */
	public String getUnidadDeMedida() {
		return unidadDeMedida;
	}

	/**
	 * @param unidadDeMedida
	 *            the unidadDeMedida to set
	 */
	public void setUnidadDeMedida(String unidadDeMedida) {
		this.unidadDeMedida = unidadDeMedida;
	}

	/**
	 * @return the cantidadDeMercancia
	 */
	public Integer getCantidadDeMercancia() {
		return cantidadDeMercancia;
	}

	/**
	 * @param cantidadDeMercancia
	 *            the cantidadDeMercancia to set
	 */
	public void setCantidadDeMercancia(Integer cantidadDeMercancia) {
		this.cantidadDeMercancia = cantidadDeMercancia;
	}

	/**
	 * @return the producto
	 */
	public VistaProductDADTO getProducto() {
		return producto;
	}

	/**
	 * @param producto
	 *            the producto to set
	 */
	public void setProducto(VistaProductDADTO producto) {
		this.producto = producto;
	}

	/**
	 * @return the numeroSolicitudAutorizacion
	 */
	public String getNumeroSolicitudAutorizacion() {
		return numeroSolicitudAutorizacion;
	}

	/**
	 * @param numeroSolicitudAutorizacion
	 *            the numeroSolicitudAutorizacion to set
	 */
	public void setNumeroSolicitudAutorizacion(String numeroSolicitudAutorizacion) {
		this.numeroSolicitudAutorizacion = numeroSolicitudAutorizacion;
	}

	/**
	 * @return the listLote
	 */
	public List<LoteDTO> getListLote() {
		return listLote;
	}

	/**
	 * @param listLote
	 *            the listLote to set
	 */
	public void setListLote(List<LoteDTO> listLote) {
		this.listLote = listLote;
	}

	/**
	 * @return the documentosRelacionados
	 */
	public List<VistaDocumentoApoyoDADTO> getDocumentosRelacionados() {
		return documentosRelacionados;
	}

	/**
	 * @param documentosRelacionados
	 *            the documentosRelacionados to set
	 */
	public void setDocumentosRelacionados(List<VistaDocumentoApoyoDADTO> documentosRelacionados) {
		this.documentosRelacionados = documentosRelacionados;
	}

	/**
	 * @return the observaciones
	 */
	public List<VistaObservacionDADTO> getObservaciones() {
		return observaciones;
	}

	/**
	 * @param observaciones
	 *            the observaciones to set
	 */
	public void setObservaciones(List<VistaObservacionDADTO> observaciones) {
		this.observaciones = observaciones;
	}

	/**
	 * // * @return the codigoLote //
	 */
	// public List<String> getCodigoLote() {
	// List<LoteDTO> listLote = getListLote();
	// List<String> out = new ArrayList();
	// if (null != listLote && !listLote.isEmpty()) {
	// for (LoteDTO loteDTO : listLote) {
	// String numeroLote = loteDTO.getValorLote();
	// out.add(numeroLote);
	// }
	// }
	// return out;
	// }




}
