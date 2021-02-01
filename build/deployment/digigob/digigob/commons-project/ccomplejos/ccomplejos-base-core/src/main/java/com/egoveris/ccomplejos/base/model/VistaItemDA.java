package com.egoveris.ccomplejos.base.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "view_itemda")
public class VistaItemDA extends AbstractViewCComplejoJPA {
	
	@Column(name = "PESO_BRUTO")
	protected BigDecimal pesoBrutoItem;
	@Column(name = "COD_TRATADO_ARANCEL")
	protected String codigoArancelProd;
	@Column(name = "UNIDAD_VOLUMEN")
	protected String unidadMedidaArancelProd;
	@Column(name = "ID_ITEM")
	protected Integer secuencialItem;
	@Column(name = "MONTO_FOB")
	protected BigDecimal montoFOBItem;
	@Column(name = "VALOR_MINIMO")
	protected BigDecimal valorMercanciaItem;
	@Column(name = "NUM_RESOLUCION")
	protected Integer numeroDeItem;
	@Column(name = "MONTO_UNITARIO_FOB")
	protected BigDecimal fobUnitario;
	@Column(name = "fobUSD")
	protected BigDecimal fobUSD;
	@Column(name = "PESO_NETO_EMBARQUE")
	protected BigDecimal cantidadKilosNetoProd;
	@Column(name = "UNIDAD_PESO_BRUTO")
	protected BigDecimal cantidadKilosBrutosProd;
	@Column(name = "estadoItem")
	protected String estadoItem;
	@Column(name = "VOLUMEN_TOTAL")
	protected Integer cantidadItemProducto;
	@Column(name = "UNIDAD_PN_EMBARQUE")
	protected String unidadDeMedida;
	@Column(name = "CANT_MERCANCIAS")
	protected Integer cantidadDeMercancia;
	@Column(name = "ID_PRODUCTO")
	Long vistaProducto;
	@OneToOne(mappedBy = "producto")
	private VistaProductDA producto;
	@Column(name = "OPERACION_ID")
	protected String numeroSolicitudAutorizacion;

	@Column(name = "ID_OPERACION", insertable = false, updatable = false)
	protected Integer idOperacion;

	@Column(name = "NUMERO_DOCUMENTO")
	protected Integer numeroDocumento;
	@OneToMany(mappedBy = "vistaDocumento")
	protected List<VistaDocumentoApoyoDA> documentosRelacionados;
	@OneToMany(mappedBy = "vistaOb")
	protected List<VistaObservacionDA> observaciones;

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
	 * @return the numeroDeItem
	 */
	public Integer getNumeroDeItem() {
		return numeroDeItem;
	}

	/**
	 * @param numeroDeItem
	 *            the numeroDeItem to set
	 */
	public void setNumeroDeItem(Integer numeroDeItem) {
		this.numeroDeItem = numeroDeItem;
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
	 * @return the cantidadKilosNetoProd
	 */
	public BigDecimal getCantidadKilosNetoProd() {
		return cantidadKilosNetoProd;
	}

	/**
	 * @param cantidadKilosNetoProd
	 *            the cantidadKilosNetoProd to set
	 */
	public void setCantidadKilosNetoProd(BigDecimal cantidadKilosNetoProd) {
		this.cantidadKilosNetoProd = cantidadKilosNetoProd;
	}

	/**
	 * @return the cantidadKilosBrutosProd
	 */
	public BigDecimal getCantidadKilosBrutosProd() {
		return cantidadKilosBrutosProd;
	}

	/**
	 * @param cantidadKilosBrutosProd
	 *            the cantidadKilosBrutosProd to set
	 */
	public void setCantidadKilosBrutosProd(BigDecimal cantidadKilosBrutosProd) {
		this.cantidadKilosBrutosProd = cantidadKilosBrutosProd;
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
	 * @return the vistaProducto
	 */
	public Long getVistaProducto() {
		return vistaProducto;
	}

	/**
	 * @param vistaProducto
	 *            the vistaProducto to set
	 */
	public void setVistaProducto(Long vistaProducto) {
		this.vistaProducto = vistaProducto;
	}

	/**
	 * @return the producto
	 */
	public VistaProductDA getProducto() {
		return producto;
	}

	/**
	 * @param producto
	 *            the producto to set
	 */
	public void setProducto(VistaProductDA producto) {
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
	 * @return the idOperacion
	 */
	public Integer getIdOperacion() {
		return idOperacion;
	}

	/**
	 * @param idOperacion
	 *            the idOperacion to set
	 */
	public void setIdOperacion(Integer idOperacion) {
		this.idOperacion = idOperacion;
	}

	/**
	 * @return the numeroDocumento
	 */
	public Integer getNumeroDocumento() {
		return numeroDocumento;
	}

	/**
	 * @param numeroDocumento
	 *            the numeroDocumento to set
	 */
	public void setNumeroDocumento(Integer numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	/**
	 * @return the documentosRelacionados
	 */
	public List<VistaDocumentoApoyoDA> getDocumentosRelacionados() {
		return documentosRelacionados;
	}

	/**
	 * @param documentosRelacionados
	 *            the documentosRelacionados to set
	 */
	public void setDocumentosRelacionados(List<VistaDocumentoApoyoDA> documentosRelacionados) {
		this.documentosRelacionados = documentosRelacionados;
	}

	/**
	 * @return the observaciones
	 */
	public List<VistaObservacionDA> getObservaciones() {
		return observaciones;
	}

	/**
	 * @param observaciones
	 *            the observaciones to set
	 */
	public void setObservaciones(List<VistaObservacionDA> observaciones) {
		this.observaciones = observaciones;
	}

}
