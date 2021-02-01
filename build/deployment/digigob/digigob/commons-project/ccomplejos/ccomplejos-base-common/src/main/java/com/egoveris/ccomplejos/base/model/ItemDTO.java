package com.egoveris.ccomplejos.base.model;

import java.util.List;

public class ItemDTO extends AbstractCComplejoDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6626160867764180909L;

	Long itemId;
	ProductOperationDTO producto;
	Long valorMinimo;
	String acuerdoComercial;
	Long montoUnitarioFOB;
	Long montoFOB;
	Long pesoBrutoItem;
	String unidadPesoBrutoItem;
	Long pesoNetoEmbarque;
	String unidadPesoNetoEmbarque;
	Long volumenTotal;
	String unidadVolumen;
	Long cantidadMercancias;
	String unidadCantidadMercancia;
	List<ObservacionDTO> listObservacionDTOs;
	List<DocumentoApoyoDTO> listDocumentoApoyo;
	String montoAjusteItem;
	String signoAjuste;
	String sujetoCupo;
	String codigoTratadoArancel;
	String numeroCorrelativoArancel;
	Double valorCIF;
	Long porcentajeAdvalorem;
	String codigoCuentaAdvalorem;
	Double montoCuentaAdvalorem;
	String paisProduccion;
	ParticipanteSecundarioDTO productor;
	String cuarentenaPostFrontera;
	String numeroResolucion;
	List<LoteDTO> listaLote;
	String mercadoDestino;
	List<InstalacionDTO> listaInstalacion;
	List<ParticipantesDTO> listaTransportista;
	List<DocumentDTO> listaDocumentoComercial;

	/**
	 * @return the itemId
	 */
	public Long getItemId() {
		return itemId;
	}

	/**
	 * @param itemId
	 *            the itemId to set
	 */
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	/**
	 * @return the producto
	 */
	public ProductOperationDTO getProducto() {
		return producto;
	}

	/**
	 * @param producto
	 *            the producto to set
	 */
	public void setProducto(ProductOperationDTO producto) {
		this.producto = producto;
	}

	/**
	 * @return the valorMinimo
	 */
	public Long getValorMinimo() {
		return valorMinimo;
	}

	/**
	 * @param valorMinimo
	 *            the valorMinimo to set
	 */
	public void setValorMinimo(Long valorMinimo) {
		this.valorMinimo = valorMinimo;
	}

	/**
	 * @return the acuerdoComercial
	 */
	public String getAcuerdoComercial() {
		return acuerdoComercial;
	}

	/**
	 * @param acuerdoComercial
	 *            the acuerdoComercial to set
	 */
	public void setAcuerdoComercial(String acuerdoComercial) {
		this.acuerdoComercial = acuerdoComercial;
	}

	/**
	 * @return the montoUnitarioFOB
	 */
	public Long getMontoUnitarioFOB() {
		return montoUnitarioFOB;
	}

	/**
	 * @param montoUnitarioFOB
	 *            the montoUnitarioFOB to set
	 */
	public void setMontoUnitarioFOB(Long montoUnitarioFOB) {
		this.montoUnitarioFOB = montoUnitarioFOB;
	}

	/**
	 * @return the montoFOB
	 */
	public Long getMontoFOB() {
		return montoFOB;
	}

	/**
	 * @param montoFOB
	 *            the montoFOB to set
	 */
	public void setMontoFOB(Long montoFOB) {
		this.montoFOB = montoFOB;
	}

	/**
	 * @return the pesoBrutoItem
	 */
	public Long getPesoBrutoItem() {
		return pesoBrutoItem;
	}

	/**
	 * @param pesoBrutoItem
	 *            the pesoBrutoItem to set
	 */
	public void setPesoBrutoItem(Long pesoBrutoItem) {
		this.pesoBrutoItem = pesoBrutoItem;
	}

	/**
	 * @return the unidadPesoBrutoItem
	 */
	public String getUnidadPesoBrutoItem() {
		return unidadPesoBrutoItem;
	}

	/**
	 * @param unidadPesoBrutoItem
	 *            the unidadPesoBrutoItem to set
	 */
	public void setUnidadPesoBrutoItem(String unidadPesoBrutoItem) {
		this.unidadPesoBrutoItem = unidadPesoBrutoItem;
	}

	/**
	 * @return the pesoNetoEmbarque
	 */
	public Long getPesoNetoEmbarque() {
		return pesoNetoEmbarque;
	}

	/**
	 * @param pesoNetoEmbarque
	 *            the pesoNetoEmbarque to set
	 */
	public void setPesoNetoEmbarque(Long pesoNetoEmbarque) {
		this.pesoNetoEmbarque = pesoNetoEmbarque;
	}

	/**
	 * @return the unidadPesoNetoEmbarque
	 */
	public String getUnidadPesoNetoEmbarque() {
		return unidadPesoNetoEmbarque;
	}

	/**
	 * @param unidadPesoNetoEmbarque
	 *            the unidadPesoNetoEmbarque to set
	 */
	public void setUnidadPesoNetoEmbarque(String unidadPesoNetoEmbarque) {
		this.unidadPesoNetoEmbarque = unidadPesoNetoEmbarque;
	}

	/**
	 * @return the volumenTotal
	 */
	public Long getVolumenTotal() {
		return volumenTotal;
	}

	/**
	 * @param volumenTotal
	 *            the volumenTotal to set
	 */
	public void setVolumenTotal(Long volumenTotal) {
		this.volumenTotal = volumenTotal;
	}

	/**
	 * @return the unidadVolumen
	 */
	public String getUnidadVolumen() {
		return unidadVolumen;
	}

	/**
	 * @param unidadVolumen
	 *            the unidadVolumen to set
	 */
	public void setUnidadVolumen(String unidadVolumen) {
		this.unidadVolumen = unidadVolumen;
	}

	/**
	 * @return the cantidadMercancias
	 */
	public Long getCantidadMercancias() {
		return cantidadMercancias;
	}

	/**
	 * @param cantidadMercancias
	 *            the cantidadMercancias to set
	 */
	public void setCantidadMercancias(Long cantidadMercancias) {
		this.cantidadMercancias = cantidadMercancias;
	}

	/**
	 * @return the unidadCantidadMercancia
	 */
	public String getUnidadCantidadMercancia() {
		return unidadCantidadMercancia;
	}

	/**
	 * @param unidadCantidadMercancia
	 *            the unidadCantidadMercancia to set
	 */
	public void setUnidadCantidadMercancia(String unidadCantidadMercancia) {
		this.unidadCantidadMercancia = unidadCantidadMercancia;
	}

	/**
	 * @return the listObservacionDTOs
	 */
	public List<ObservacionDTO> getListObservacionDTOs() {
		return listObservacionDTOs;
	}

	/**
	 * @param listObservacionDTOs
	 *            the listObservacionDTOs to set
	 */
	public void setListObservacionDTOs(List<ObservacionDTO> listObservacionDTOs) {
		this.listObservacionDTOs = listObservacionDTOs;
	}

	/**
	 * @return the listDocumentoApoyo
	 */
	public List<DocumentoApoyoDTO> getListDocumentoApoyo() {
		return listDocumentoApoyo;
	}

	/**
	 * @param listDocumentoApoyo
	 *            the listDocumentoApoyo to set
	 */
	public void setListDocumentoApoyo(List<DocumentoApoyoDTO> listDocumentoApoyo) {
		this.listDocumentoApoyo = listDocumentoApoyo;
	}

	/**
	 * @return the montoAjusteItem
	 */
	public String getMontoAjusteItem() {
		return montoAjusteItem;
	}

	/**
	 * @param montoAjusteItem
	 *            the montoAjusteItem to set
	 */
	public void setMontoAjusteItem(String montoAjusteItem) {
		this.montoAjusteItem = montoAjusteItem;
	}

	/**
	 * @return the signoAjuste
	 */
	public String getSignoAjuste() {
		return signoAjuste;
	}

	/**
	 * @param signoAjuste
	 *            the signoAjuste to set
	 */
	public void setSignoAjuste(String signoAjuste) {
		this.signoAjuste = signoAjuste;
	}

	/**
	 * @return the sujetoCupo
	 */
	public String getSujetoCupo() {
		return sujetoCupo;
	}

	/**
	 * @param sujetoCupo
	 *            the sujetoCupo to set
	 */
	public void setSujetoCupo(String sujetoCupo) {
		this.sujetoCupo = sujetoCupo;
	}

	/**
	 * @return the codigoTratadoArancel
	 */
	public String getCodigoTratadoArancel() {
		return codigoTratadoArancel;
	}

	/**
	 * @param codigoTratadoArancel
	 *            the codigoTratadoArancel to set
	 */
	public void setCodigoTratadoArancel(String codigoTratadoArancel) {
		this.codigoTratadoArancel = codigoTratadoArancel;
	}

	/**
	 * @return the numeroCorrelativoArancel
	 */
	public String getNumeroCorrelativoArancel() {
		return numeroCorrelativoArancel;
	}

	/**
	 * @param numeroCorrelativoArancel
	 *            the numeroCorrelativoArancel to set
	 */
	public void setNumeroCorrelativoArancel(String numeroCorrelativoArancel) {
		this.numeroCorrelativoArancel = numeroCorrelativoArancel;
	}

	/**
	 * @return the valorCIF
	 */
	public Double getValorCIF() {
		return valorCIF;
	}

	/**
	 * @param valorCIF
	 *            the valorCIF to set
	 */
	public void setValorCIF(Double valorCIF) {
		this.valorCIF = valorCIF;
	}

	/**
	 * @return the porcentajeAdvalorem
	 */
	public Long getPorcentajeAdvalorem() {
		return porcentajeAdvalorem;
	}

	/**
	 * @param porcentajeAdvalorem
	 *            the porcentajeAdvalorem to set
	 */
	public void setPorcentajeAdvalorem(Long porcentajeAdvalorem) {
		this.porcentajeAdvalorem = porcentajeAdvalorem;
	}

	/**
	 * @return the codigoCuentaAdvalorem
	 */
	public String getCodigoCuentaAdvalorem() {
		return codigoCuentaAdvalorem;
	}

	/**
	 * @param codigoCuentaAdvalorem
	 *            the codigoCuentaAdvalorem to set
	 */
	public void setCodigoCuentaAdvalorem(String codigoCuentaAdvalorem) {
		this.codigoCuentaAdvalorem = codigoCuentaAdvalorem;
	}

	/**
	 * @return the montoCuentaAdvalorem
	 */
	public Double getMontoCuentaAdvalorem() {
		return montoCuentaAdvalorem;
	}

	/**
	 * @param montoCuentaAdvalorem
	 *            the montoCuentaAdvalorem to set
	 */
	public void setMontoCuentaAdvalorem(Double montoCuentaAdvalorem) {
		this.montoCuentaAdvalorem = montoCuentaAdvalorem;
	}

	/**
	 * @return the paisProduccion
	 */
	public String getPaisProduccion() {
		return paisProduccion;
	}

	/**
	 * @param paisProduccion
	 *            the paisProduccion to set
	 */
	public void setPaisProduccion(String paisProduccion) {
		this.paisProduccion = paisProduccion;
	}

	/**
	 * @return the productor
	 */
	public ParticipanteSecundarioDTO getProductor() {
		return productor;
	}

	/**
	 * @param productor
	 *            the productor to set
	 */
	public void setProductor(ParticipanteSecundarioDTO productor) {
		this.productor = productor;
	}

	/**
	 * @return the cuarentenaPostFrontera
	 */
	public String getCuarentenaPostFrontera() {
		return cuarentenaPostFrontera;
	}

	/**
	 * @param cuarentenaPostFrontera
	 *            the cuarentenaPostFrontera to set
	 */
	public void setCuarentenaPostFrontera(String cuarentenaPostFrontera) {
		this.cuarentenaPostFrontera = cuarentenaPostFrontera;
	}

	/**
	 * @return the numeroResolucion
	 */
	public String getNumeroResolucion() {
		return numeroResolucion;
	}

	/**
	 * @param numeroResolucion
	 *            the numeroResolucion to set
	 */
	public void setNumeroResolucion(String numeroResolucion) {
		this.numeroResolucion = numeroResolucion;
	}

	/**
	 * @return the listaLote
	 */
	public List<LoteDTO> getListaLote() {
		return listaLote;
	}

	/**
	 * @param listaLote
	 *            the listaLote to set
	 */
	public void setListaLote(List<LoteDTO> listaLote) {
		this.listaLote = listaLote;
	}

	/**
	 * @return the mercadoDestino
	 */
	public String getMercadoDestino() {
		return mercadoDestino;
	}

	/**
	 * @param mercadoDestino
	 *            the mercadoDestino to set
	 */
	public void setMercadoDestino(String mercadoDestino) {
		this.mercadoDestino = mercadoDestino;
	}

	/**
	 * @return the listaInstalacion
	 */
	public List<InstalacionDTO> getListaInstalacion() {
		return listaInstalacion;
	}

	/**
	 * @param listaInstalacion
	 *            the listaInstalacion to set
	 */
	public void setListaInstalacion(List<InstalacionDTO> listaInstalacion) {
		this.listaInstalacion = listaInstalacion;
	}

	/**
	 * @return the listaTransportista
	 */
	public List<ParticipantesDTO> getListaTransportista() {
		return listaTransportista;
	}

	/**
	 * @param listaTransportista
	 *            the listaTransportista to set
	 */
	public void setListaTransportista(List<ParticipantesDTO> listaTransportista) {
		this.listaTransportista = listaTransportista;
	}

	/**
	 * @return the listaDocumentoComercial
	 */
	public List<DocumentDTO> getListaDocumentoComercial() {
		return listaDocumentoComercial;
	}

	/**
	 * @param listaDocumentoComercial
	 *            the listaDocumentoComercial to set
	 */
	public void setListaDocumentoComercial(List<DocumentDTO> listaDocumentoComercial) {
		this.listaDocumentoComercial = listaDocumentoComercial;
	}

}
