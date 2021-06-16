package com.egoveris.ccomplejos.base.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "cc_product_operation")
public class ProductOperation extends AbstractCComplejoJPA {

	@Column(name = "PRODUCT_OPERACION_ID")
	protected Long productoOperacionId;
	@Column(name = "PRODUCT_VERSION")
	protected String productVersion;
	@Column(name = "COMMON_NAME")
	protected String commonName;
	@Column(name = "ARANCELARIO_PROD")
	protected String arancelarioProd;
	@Column(name = "CODIGO_COCHILCO")
	protected String codigoCochilco;
	@Column(name = "CARACTERISTICA_ESPECIAL")
	protected String caracteristicaEspecial;
	@Column(name = "PRODUCT_ID")
	protected Long productId;
	@Column(name = "DESCRIPCION_PROD")
	protected String descripcionProd;
	@Column(name = "DESCRIPCION_ADIC")
	protected String descripcionAdic;
	@Column(name = "CUOTA_CONTRACTUAL")
	protected String cuotaContractual;
	@Column(name = "USO_PREVISTO")
	protected String usoPrevisto;
	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "productOperation")
	protected List<ProductAttributesOperation> listProductAttributes;
	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "productOperation")
	protected List<ProductComponent> listComponenteCochilco;
	@Column(name = "SSPP_ENUM")
	protected String ssppEnum;
	@Column(name = "PAIS_ORIGEN")
	protected String paisOrigen;
	@Column(name = "PROD_CODE_SSPP")
	protected String prodCodeSSPP;
	@Column(name = "ARANCELARIO_COD")
	protected String arancelarioCod;
	@Column(name = "PRODUCT_CODE")
	protected String productCode;
	
	@ManyToOne
	@JoinColumn(name = "ID_OPERATION", referencedColumnName = "id")
	Operation operation;
	
	
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
	public List<ProductAttributesOperation> getListProductAttributes() {
		return listProductAttributes;
	}
	public void setListProductAttributes(List<ProductAttributesOperation> listProductAttributes) {
		this.listProductAttributes = listProductAttributes;
	}
	public List<ProductComponent> getListComponenteCochilco() {
		return listComponenteCochilco;
	}
	public void setListComponenteCochilco(List<ProductComponent> listComponenteCochilco) {
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
