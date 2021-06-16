package com.egoveris.ccomplejos.base.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "view_docingresoenvio")
public class VistaItemsDocIngresoEnvio extends AbstractViewCComplejoJPA {

	@Column(name = "NUMERO_ITEM")
	protected Integer nroItem;
	@Column(name = "CODIGO_TRATA_ARAN")
	protected String codigoTratadoArancel;
	@Column(name = "USE_PREVISTO")
	protected String usoPrevisto;
	@Column(name = "CODIGO_PAIS_PROD")
	protected String codigoPaisProduccion;
	@Column(name = "VALOR_CIF")
	protected BigDecimal valorCIF;
	@Column(name = "MONTO_AJUSTE")
	protected BigDecimal montoAjuste;
	@Column(name = "SIGNO_AJUSTE")
	protected String signoAjuste;
	@Column(name = "CANTIDAD")
	protected BigDecimal cantidad;
	@Column(name = "CODIGO_UNIDAD_MED")
	protected String codigoUnidadMedida;
	@Column(name = "CANTIDAD_DE_MERCANCIA")
	protected BigDecimal cantidadDeMercanciasEstimada;
	@Column(name = "PRECIO_FOB")
	protected BigDecimal precioUnitarioFOB;
	@Column(name = "CODIGO_ARANCEL")
	protected String codigoArancelDelTratado;
	@Column(name = "NUMERO_CORRELATIVO")
	protected Integer numeroCorrelativoArancel;
	@Column(name = "CODIGO_ACUERDO_COMERCIAL")
	protected String codigoAcuerdoComercial;
	@Column(name = "SUJETO_CUPO")
	protected String sujetoACupo;
	@Column(name = "PORCENTAJE_VALOREM")
	protected Integer porcentajeAdvalorem;
	@Column(name = "CODIGO_CUENTALOREM")
	protected String codigoCuentaAdvalorem;
	@Column(name = "NOMTO_CUENTALOREM")
	protected BigDecimal montoCuentaAdvalorem;
	@Column(name = "CARAC_ESP")
	protected String caracteristicaEspecial;
	@Column(name = "PESO_UNITARIO")
	protected Integer pesoUnitario;
	@Column(name = "OTRA_DESC")
	protected String otraDescripcion;
	@Column(name = "OB_OIG")
	protected String observacionOIG;
	@OneToMany
	@JoinColumn(name = "ID_PRODUCTO", referencedColumnName = "id", insertable = false, updatable = false)
	protected List<VistaLoteDocIngresoEnvio> lote;
	@Column(name = "NOMBRE_PRODUCTOR")
	protected String nombreProductor;
	@Column(name = "CODIGO_ARANCEL_FINAL")
	protected String codigoArancelarioFinal;
	@Column(name = "MERCADO_DESTINO")
	protected String mercadoDestino;
	@Column(name = "PESO_NETO")
	protected BigDecimal pesoNeto;
	@Column(name = "PESO_BRUTO")
	protected BigDecimal pesoBruto;
	@Column(name = "DIR_PARTICIPANTES")
	protected String direccionParticipante;
	@Column(name = "CUARENTENA_POST_FRONT")
	protected String cuarentenaPostFrontera;
	@Column(name = "NOMBRE_PRODC")
	protected String nombreProducto;
	@OneToMany
	@JoinColumn(name = "ID_PRODUCTO", referencedColumnName = "id", insertable = false, updatable = false)
	protected List<VistaProdAttrDocIngEnv> producto;
	@OneToMany
	@JoinColumn(name = "ID_PRODUCTO", referencedColumnName = "id", insertable = false, updatable = false)
	protected List<VistaObservacionDocIngresoEnvio> observacion;
	@OneToOne
	@JoinColumn(name = "ID_PRODUCTO", referencedColumnName = "id", insertable = false, updatable = false)
	protected VistaIntalacionDocIngEnvio establecimientoOrigen;
	@OneToMany
	@JoinColumn(name = "ID_PRODUCTO", referencedColumnName = "id", insertable = false, updatable = false)
	protected List<VistaDocumentoApoyoDA> documentoAdjunto;
	@Column(name = "CODIGO_PRODUCTO")
	protected String codigoProducto;
	@OneToMany
	@JoinColumn(name = "ID_PRODUCTO", referencedColumnName = "id", insertable = false, updatable = false)
	protected List<VistaOtroImpuestoDin> otroImpuesto;

	/**
	 * @return the nroItem
	 */
	public Integer getNroItem() {
		return nroItem;
	}

	/**
	 * @param nroItem
	 *            the nroItem to set
	 */
	public void setNroItem(Integer nroItem) {
		this.nroItem = nroItem;
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
	 * @return the usoPrevisto
	 */
	public String getUsoPrevisto() {
		return usoPrevisto;
	}

	/**
	 * @param usoPrevisto
	 *            the usoPrevisto to set
	 */
	public void setUsoPrevisto(String usoPrevisto) {
		this.usoPrevisto = usoPrevisto;
	}

	/**
	 * @return the codigoPaisProduccion
	 */
	public String getCodigoPaisProduccion() {
		return codigoPaisProduccion;
	}

	/**
	 * @param codigoPaisProduccion
	 *            the codigoPaisProduccion to set
	 */
	public void setCodigoPaisProduccion(String codigoPaisProduccion) {
		this.codigoPaisProduccion = codigoPaisProduccion;
	}

	/**
	 * @return the valorCIF
	 */
	public BigDecimal getValorCIF() {
		return valorCIF;
	}

	/**
	 * @param valorCIF
	 *            the valorCIF to set
	 */
	public void setValorCIF(BigDecimal valorCIF) {
		this.valorCIF = valorCIF;
	}

	/**
	 * @return the montoAjuste
	 */
	public BigDecimal getMontoAjuste() {
		return montoAjuste;
	}

	/**
	 * @param montoAjuste
	 *            the montoAjuste to set
	 */
	public void setMontoAjuste(BigDecimal montoAjuste) {
		this.montoAjuste = montoAjuste;
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
	 * @return the cantidad
	 */
	public BigDecimal getCantidad() {
		return cantidad;
	}

	/**
	 * @param cantidad
	 *            the cantidad to set
	 */
	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * @return the codigoUnidadMedida
	 */
	public String getCodigoUnidadMedida() {
		return codigoUnidadMedida;
	}

	/**
	 * @param codigoUnidadMedida
	 *            the codigoUnidadMedida to set
	 */
	public void setCodigoUnidadMedida(String codigoUnidadMedida) {
		this.codigoUnidadMedida = codigoUnidadMedida;
	}

	/**
	 * @return the cantidadDeMercanciasEstimada
	 */
	public BigDecimal getCantidadDeMercanciasEstimada() {
		return cantidadDeMercanciasEstimada;
	}

	/**
	 * @param cantidadDeMercanciasEstimada
	 *            the cantidadDeMercanciasEstimada to set
	 */
	public void setCantidadDeMercanciasEstimada(BigDecimal cantidadDeMercanciasEstimada) {
		this.cantidadDeMercanciasEstimada = cantidadDeMercanciasEstimada;
	}

	/**
	 * @return the precioUnitarioFOB
	 */
	public BigDecimal getPrecioUnitarioFOB() {
		return precioUnitarioFOB;
	}

	/**
	 * @param precioUnitarioFOB
	 *            the precioUnitarioFOB to set
	 */
	public void setPrecioUnitarioFOB(BigDecimal precioUnitarioFOB) {
		this.precioUnitarioFOB = precioUnitarioFOB;
	}

	/**
	 * @return the codigoArancelDelTratado
	 */
	public String getCodigoArancelDelTratado() {
		return codigoArancelDelTratado;
	}

	/**
	 * @param codigoArancelDelTratado
	 *            the codigoArancelDelTratado to set
	 */
	public void setCodigoArancelDelTratado(String codigoArancelDelTratado) {
		this.codigoArancelDelTratado = codigoArancelDelTratado;
	}

	/**
	 * @return the numeroCorrelativoArancel
	 */
	public Integer getNumeroCorrelativoArancel() {
		return numeroCorrelativoArancel;
	}

	/**
	 * @param numeroCorrelativoArancel
	 *            the numeroCorrelativoArancel to set
	 */
	public void setNumeroCorrelativoArancel(Integer numeroCorrelativoArancel) {
		this.numeroCorrelativoArancel = numeroCorrelativoArancel;
	}

	/**
	 * @return the codigoAcuerdoComercial
	 */
	public String getCodigoAcuerdoComercial() {
		return codigoAcuerdoComercial;
	}

	/**
	 * @param codigoAcuerdoComercial
	 *            the codigoAcuerdoComercial to set
	 */
	public void setCodigoAcuerdoComercial(String codigoAcuerdoComercial) {
		this.codigoAcuerdoComercial = codigoAcuerdoComercial;
	}

	/**
	 * @return the sujetoACupo
	 */
	public String isSujetoACupo() {
		return sujetoACupo;
	}

	/**
	 * @param sujetoACupo
	 *            the sujetoACupo to set
	 */
	public void setSujetoACupo(String sujetoACupo) {
		this.sujetoACupo = sujetoACupo;
	}

	/**
	 * @return the porcentajeAdvalorem
	 */
	public Integer getPorcentajeAdvalorem() {
		return porcentajeAdvalorem;
	}

	/**
	 * @param porcentajeAdvalorem
	 *            the porcentajeAdvalorem to set
	 */
	public void setPorcentajeAdvalorem(Integer porcentajeAdvalorem) {
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
	public BigDecimal getMontoCuentaAdvalorem() {
		return montoCuentaAdvalorem;
	}

	/**
	 * @param montoCuentaAdvalorem
	 *            the montoCuentaAdvalorem to set
	 */
	public void setMontoCuentaAdvalorem(BigDecimal montoCuentaAdvalorem) {
		this.montoCuentaAdvalorem = montoCuentaAdvalorem;
	}

	/**
	 * @return the caracteristicaEspecial
	 */
	public String getCaracteristicaEspecial() {
		return caracteristicaEspecial;
	}

	/**
	 * @param caracteristicaEspecial
	 *            the caracteristicaEspecial to set
	 */
	public void setCaracteristicaEspecial(String caracteristicaEspecial) {
		this.caracteristicaEspecial = caracteristicaEspecial;
	}

	/**
	 * @return the pesoUnitario
	 */
	public Integer getPesoUnitario() {
		return pesoUnitario;
	}

	/**
	 * @param pesoUnitario
	 *            the pesoUnitario to set
	 */
	public void setPesoUnitario(Integer pesoUnitario) {
		this.pesoUnitario = pesoUnitario;
	}

	/**
	 * @return the otraDescripcion
	 */
	public String getOtraDescripcion() {
		return otraDescripcion;
	}

	/**
	 * @param otraDescripcion
	 *            the otraDescripcion to set
	 */
	public void setOtraDescripcion(String otraDescripcion) {
		this.otraDescripcion = otraDescripcion;
	}

	/**
	 * @return the observacionOIG
	 */
	public String getObservacionOIG() {
		return observacionOIG;
	}

	/**
	 * @param observacionOIG
	 *            the observacionOIG to set
	 */
	public void setObservacionOIG(String observacionOIG) {
		this.observacionOIG = observacionOIG;
	}

	/**
	 * @return the lote
	 */
	public List<VistaLoteDocIngresoEnvio> getLote() {
		return lote;
	}

	/**
	 * @param lote
	 *            the lote to set
	 */
	public void setLote(List<VistaLoteDocIngresoEnvio> lote) {
		this.lote = lote;
	}

	/**
	 * @return the nombreProductor
	 */
	public String getNombreProductor() {
		return nombreProductor;
	}

	/**
	 * @param nombreProductor
	 *            the nombreProductor to set
	 */
	public void setNombreProductor(String nombreProductor) {
		this.nombreProductor = nombreProductor;
	}

	/**
	 * @return the codigoArancelarioFinal
	 */
	public String getCodigoArancelarioFinal() {
		return codigoArancelarioFinal;
	}

	/**
	 * @param codigoArancelarioFinal
	 *            the codigoArancelarioFinal to set
	 */
	public void setCodigoArancelarioFinal(String codigoArancelarioFinal) {
		this.codigoArancelarioFinal = codigoArancelarioFinal;
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
	 * @return the pesoNeto
	 */
	public BigDecimal getPesoNeto() {
		return pesoNeto;
	}

	/**
	 * @param pesoNeto
	 *            the pesoNeto to set
	 */
	public void setPesoNeto(BigDecimal pesoNeto) {
		this.pesoNeto = pesoNeto;
	}

	/**
	 * @return the pesoBruto
	 */
	public BigDecimal getPesoBruto() {
		return pesoBruto;
	}

	/**
	 * @param pesoBruto
	 *            the pesoBruto to set
	 */
	public void setPesoBruto(BigDecimal pesoBruto) {
		this.pesoBruto = pesoBruto;
	}

	/**
	 * @return the direccionParticipante
	 */
	public String getDireccionParticipante() {
		return direccionParticipante;
	}

	/**
	 * @param direccionParticipante
	 *            the direccionParticipante to set
	 */
	public void setDireccionParticipante(String direccionParticipante) {
		this.direccionParticipante = direccionParticipante;
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
	 * @return the nombreProducto
	 */
	public String getNombreProducto() {
		return nombreProducto;
	}

	/**
	 * @param nombreProducto
	 *            the nombreProducto to set
	 */
	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	/**
	 * @return the producto
	 */
	public List<VistaProdAttrDocIngEnv> getProducto() {
		return producto;
	}

	/**
	 * @param producto
	 *            the producto to set
	 */
	public void setProducto(List<VistaProdAttrDocIngEnv> producto) {
		this.producto = producto;
	}

	/**
	 * @return the observacion
	 */
	public List<VistaObservacionDocIngresoEnvio> getObservacion() {
		return observacion;
	}

	/**
	 * @param observacion
	 *            the observacion to set
	 */
	public void setObservacion(List<VistaObservacionDocIngresoEnvio> observacion) {
		this.observacion = observacion;
	}

	/**
	 * @return the establecimientoOrigen
	 */
	public VistaIntalacionDocIngEnvio getEstablecimientoOrigen() {
		return establecimientoOrigen;
	}

	/**
	 * @param establecimientoOrigen
	 *            the establecimientoOrigen to set
	 */
	public void setEstablecimientoOrigen(VistaIntalacionDocIngEnvio establecimientoOrigen) {
		this.establecimientoOrigen = establecimientoOrigen;
	}

	/**
	 * @return the documentoAdjunto
	 */
	public List<VistaDocumentoApoyoDA> getDocumentoAdjunto() {
		return documentoAdjunto;
	}

	/**
	 * @param documentoAdjunto
	 *            the documentoAdjunto to set
	 */
	public void setDocumentoAdjunto(List<VistaDocumentoApoyoDA> documentoAdjunto) {
		this.documentoAdjunto = documentoAdjunto;
	}

	/**
	 * @return the codigoProducto
	 */
	public String getCodigoProducto() {
		return codigoProducto;
	}

	/**
	 * @param codigoProducto
	 *            the codigoProducto to set
	 */
	public void setCodigoProducto(String codigoProducto) {
		this.codigoProducto = codigoProducto;
	}

	/**
	 * @return the otroImpuesto
	 */
	public List<VistaOtroImpuestoDin> getOtroImpuesto() {
		return otroImpuesto;
	}

	/**
	 * @param otroImpuesto
	 *            the otroImpuesto to set
	 */
	public void setOtroImpuesto(List<VistaOtroImpuestoDin> otroImpuesto) {
		this.otroImpuesto = otroImpuesto;
	}

}