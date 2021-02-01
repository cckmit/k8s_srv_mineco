package com.egoveris.ccomplejos.base.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CC_ITEM")
public class ItemJPA extends AbstractCComplejoJPA {

	@Column(name = "ID_ITEM")
	Long itemId;
	
	@ManyToOne
	@JoinColumn(name="ID_BULTO", referencedColumnName = "id")
	private Bulto bulto;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_PRODUCTO")
	ProductOperation producto;

	@Column(name = "VALOR_MINIMO")
	Long valorMinimo;

	@Column(name = "ACUERDO_COMERCIAL")
	String acuerdoComercial;

	@Column(name = "MONTO_UNITARIO_FOB")
	Long montoUnitarioFOB;

	@Column(name = "MONTO_FOB")
	Long montoFOB;

	@Column(name = "PESO_BRUTO")
	Long pesoBrutoItem;

	@Column(name = "UNIDAD_PESO_BRUTO")
	String unidadPesoBrutoItem;

	@Column(name = "PESO_NETO_EMBARQUE")
	Long pesoNetoEmbarque;

	@Column(name = "UNIDAD_PN_EMBARQUE")
	String unidadPesoNetoEmbarque;

	@Column(name = "VOLUMEN_TOTAL")
	Long volumenTotal;

	@Column(name = "UNIDAD_VOLUMEN")
	String unidadVolumen;

	@Column(name = "CANT_MERCANCIAS")
	Long cantidadMercancias;

	@Column(name = "UNIDAD_CANT_MERCANCIAS")
	String unidadCantidadMercancia;

	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "item")
	List<Observacion> listObservacionDTOs;

	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "item")
	List<DocumentoApoyo> listDocumentoApoyo;

	@Column(name = "MONTO_AJUSTE")
	String montoAjusteItem;

	@Column(name = "SIGNO_AJUSTE")
	String signoAjuste;

	@Column(name = "SUJETO_CUPO")
	String sujetoCupo;

	@Column(name = "COD_TRATADO_ARANCEL")
	String codigoTratadoArancel;

	@Column(name = "NUM_CORR_ARANCEL")
	String numeroCorrelativoArancel;

	@Column(name = "VALOR_CIF")
	Double valorCIF;

	@Column(name = "PORC_ADVALOREM")
	Long porcentajeAdvalorem;

	@Column(name = "COD_CUENTA_ADVALOREM")
	String codigoCuentaAdvalorem;

	@Column(name = "MONTO_CUENTA_ADVALOREM")
	Double montoCuentaAdvalorem;

	@Column(name = "PAIS_PRODUCCION")
	String paisProduccion;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_PRODUCTOR")
	ParticipanteSecundario productor;

	@Column(name = "CUARENTENA_PF")
	String cuarentenaPostFrontera;

	@Column(name = "NUM_RESOLUCION")
	String numeroResolucion;

	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "item")
	List<Lote> listaLote;

	@Column(name = "MERC_DESTINO")
	String mercadoDestino;

	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "item")
	List<Instalacion> listaInstalacion;

	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "item")
	List<Participantes> listaTransportista;

	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "item")
	List<Document> listaDocumentoComercial;
	
	@ManyToOne
	@JoinColumn(name = "ID_OPERATION", referencedColumnName = "id")
	Operation operation;

//	@ManyToOne
//	@JoinColumn(name = "ID_PRODUCTO", referencedColumnName = "id", insertable = false, updatable = false)
//	VistaMercanciaDA vistaMercanciaDA;
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
	public ProductOperation getProducto() {
		return producto;
	}

	/**
	 * @param producto
	 *            the producto to set
	 */
	public void setProducto(ProductOperation producto) {
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
	public List<Observacion> getListObservacionDTOs() {
		return listObservacionDTOs;
	}

	/**
	 * @param listObservacionDTOs
	 *            the listObservacionDTOs to set
	 */
	public void setListObservacionDTOs(List<Observacion> listObservacionDTOs) {
		this.listObservacionDTOs = listObservacionDTOs;
	}

	/**
	 * @return the listDocumentoApoyo
	 */
	public List<DocumentoApoyo> getListDocumentoApoyo() {
		return listDocumentoApoyo;
	}

	/**
	 * @param listDocumentoApoyo
	 *            the listDocumentoApoyo to set
	 */
	public void setListDocumentoApoyo(List<DocumentoApoyo> listDocumentoApoyo) {
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
	public ParticipanteSecundario getProductor() {
		return productor;
	}

	/**
	 * @param productor
	 *            the productor to set
	 */
	public void setProductor(ParticipanteSecundario productor) {
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
	public List<Lote> getListaLote() {
		return listaLote;
	}

	/**
	 * @param listaLote
	 *            the listaLote to set
	 */
	public void setListaLote(List<Lote> listaLote) {
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
	public List<Instalacion> getListaInstalacion() {
		return listaInstalacion;
	}

	/**
	 * @param listaInstalacion
	 *            the listaInstalacion to set
	 */
	public void setListaInstalacion(List<Instalacion> listaInstalacion) {
		this.listaInstalacion = listaInstalacion;
	}

	/**
	 * @return the listaTransportista
	 */
	public List<Participantes> getListaTransportista() {
		return listaTransportista;
	}

	/**
	 * @param listaTransportista
	 *            the listaTransportista to set
	 */
	public void setListaTransportista(List<Participantes> listaTransportista) {
		this.listaTransportista = listaTransportista;
	}

	/**
	 * @return the listaDocumentoComercial
	 */
	public List<Document> getListaDocumentoComercial() {
		return listaDocumentoComercial;
	}

	/**
	 * @param listaDocumentoComercial
	 *            the listaDocumentoComercial to set
	 */
	public void setListaDocumentoComercial(List<Document> listaDocumentoComercial) {
		this.listaDocumentoComercial = listaDocumentoComercial;
	}

}
