package com.egoveris.ffdd.render.zk.comp.cc;

import com.egoveris.ffdd.render.model.InputComponent;
import com.egoveris.ffdd.render.zk.comp.ext.SeparatorComplex;

import org.zkoss.zul.Cell;

public class ProductOperationComposer extends ComplexComponentComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7077581233332324806L;

	Cell productoOperacionIdDiv;
	Cell productVersionDiv;
	Cell commonNameDiv;
	Cell arancelarioProdDiv;
	Cell codigoCochilcoDiv;
	Cell caracteristicaEspecialDiv;
	Cell productIdDiv;
	Cell descripcionProdDiv;
	Cell descripcionAdicDiv;
	Cell cuotaContractualDiv;
	Cell usoPrevistoDiv; 
	Cell listComponenteCochilcoDiv;
	Cell listProductAttributesDiv;
	Cell ssppEnumDiv;
	Cell paisOrigenDiv;
	Cell prodCodeSSPPDiv;
	Cell arancelarioCodDiv;
	Cell productCodeDiv;

	InputComponent productoOperacionId;
	InputComponent productVersion;
	InputComponent commonName;
	InputComponent arancelarioProd;
	InputComponent codigoCochilco;
	InputComponent caracteristicaEspecial;
	InputComponent productId;
	InputComponent descripcionProd;
	InputComponent descripcionAdic;
	InputComponent cuotaContractual;
	InputComponent usoPrevisto; 
	SeparatorComplex listComponenteCochilco;
	SeparatorComplex listProductAttributes;
	InputComponent ssppEnum;
	InputComponent paisOrigen;
	InputComponent prodCodeSSPP;
	InputComponent arancelarioCod;
	InputComponent productCode;

	@Override
	protected String getName() {
		return "product operation";
	}

	@Override
	protected void setDefaultValues(String prefixName) {

	}

}
