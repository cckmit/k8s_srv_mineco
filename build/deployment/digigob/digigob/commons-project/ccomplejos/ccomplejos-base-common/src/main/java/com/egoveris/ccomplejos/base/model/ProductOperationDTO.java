package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;
import java.util.List;

public class ProductOperationDTO extends AbstractCComplejoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	protected Long productoOperacionId;
	protected String productVersion;
	protected String commonName;
	protected String arancelarioProd;
	protected String codigoCochilco;
	protected String caracteristicaEspecial;
	protected Long productId;
	protected String descripcionProd;
	protected String descripcionAdic;
	protected String cuotaContractual;
	protected String usoPrevisto;
	protected List<ProductAttributesOperationDTO> listProductAttributes;
	protected List<ProductComponentDTO> listComponenteCochilco;
	protected String ssppEnum;
	protected String paisOrigen;
	protected String prodCodeSSPP;
	protected String arancelarioCod;
	protected String productCode;
	
	public Long getProductoOperacionId() {
		return productoOperacionId;
	}
	public void setProductoOperacionId(Long productoOperacionId) {
		this.productoOperacionId = productoOperacionId;
	}
	public String getProductVersion() {
		return productVersion;
	}
	public void setProductVersion(String productVersion) {
		this.productVersion = productVersion;
	}
	public String getCommonName() {
		return commonName;
	}
	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}
	public String getArancelarioProd() {
		return arancelarioProd;
	}
	public void setArancelarioProd(String arancelarioProd) {
		this.arancelarioProd = arancelarioProd;
	}
	public String getCodigoCochilco() {
		return codigoCochilco;
	}
	public void setCodigoCochilco(String codigoCochilco) {
		this.codigoCochilco = codigoCochilco;
	}
	public String getCaracteristicaEspecial() {
		return caracteristicaEspecial;
	}
	public void setCaracteristicaEspecial(String caracteristicaEspecial) {
		this.caracteristicaEspecial = caracteristicaEspecial;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getDescripcionProd() {
		return descripcionProd;
	}
	public void setDescripcionProd(String descripcionProd) {
		this.descripcionProd = descripcionProd;
	}
	public String getDescripcionAdic() {
		return descripcionAdic;
	}
	public void setDescripcionAdic(String descripcionAdic) {
		this.descripcionAdic = descripcionAdic;
	}
	public String getCuotaContractual() {
		return cuotaContractual;
	}
	public void setCuotaContractual(String cuotaContractual) {
		this.cuotaContractual = cuotaContractual;
	}
	public String getUsoPrevisto() {
		return usoPrevisto;
	}
	public void setUsoPrevisto(String usoPrevisto) {
		this.usoPrevisto = usoPrevisto;
	}
	public List<ProductAttributesOperationDTO> getListProductAttributes() {
		return listProductAttributes;
	}
	public void setListProductAttributes(List<ProductAttributesOperationDTO> listProductAttributesDTO) {
		this.listProductAttributes = listProductAttributesDTO;
	}
	public List<ProductComponentDTO> getListComponenteCochilco() {
		return listComponenteCochilco;
	}
	public void setListComponenteCochilco(List<ProductComponentDTO> listComponenteCochilco) {
		this.listComponenteCochilco = listComponenteCochilco;
	}
	public String getSsppEnum() {
		return ssppEnum;
	}
	public void setSsppEnum(String ssppEnum) {
		this.ssppEnum = ssppEnum;
	}
	public String getPaisOrigen() {
		return paisOrigen;
	}
	public void setPaisOrigen(String paisOrigen) {
		this.paisOrigen = paisOrigen;
	}
	public String getProdCodeSSPP() {
		return prodCodeSSPP;
	}
	public void setProdCodeSSPP(String prodCodeSSPP) {
		this.prodCodeSSPP = prodCodeSSPP;
	}
	public String getArancelarioCod() {
		return arancelarioCod;
	}
	public void setArancelarioCod(String arancelarioCod) {
		this.arancelarioCod = arancelarioCod;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	
}
