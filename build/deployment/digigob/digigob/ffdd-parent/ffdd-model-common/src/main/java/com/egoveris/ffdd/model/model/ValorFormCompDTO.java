package com.egoveris.ffdd.model.model;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class ValorFormCompDTO implements Serializable {

	private static final long serialVersionUID = 7793712048862707458L;

	private Integer id;
	private Integer idFormComp;
	private String inputName;
	private String etiqueta;
	private Integer relevanciaBusqueda;
	private String valorStr;
	private Long valorLong;
	private Date valorDate;
	private Double valorDouble;
	private Boolean valorBoolean;

	private String valorArchivo;
	private ArchivoDTO archivo;

	
	public ValorFormCompDTO() {

	}

	public ValorFormCompDTO(Integer idFormComp, String inputName,Object value) {
		setIdFormComp(idFormComp);
		if (value instanceof ArchivoDTO){
			valorArchivo = (((ArchivoDTO)value).getNombre());
			setArchivo((ArchivoDTO)value);
		}else
			setValor(value);
		setInputName(inputName);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getValorStr() {
		return valorStr;
	}

	public void setValorStr(String valorStr) {
		this.valorStr = valorStr;
	}

	public Long getValorLong() {
		return valorLong;
	}

	public void setValorLong(Long valorLong) {
		this.valorLong = valorLong;
	}

	public Date getValorDate() {
		return valorDate;
	}

	public void setValorDate(Date valorDate) {
		this.valorDate = valorDate;
	}

	public Integer getIdFormComp() {
		return idFormComp;
	}

	public void setIdFormComp(Integer idFormComp) {
		this.idFormComp = idFormComp;
	}

	public Double getValorDouble() {
		return valorDouble;
	}

	public void setValorDouble(Double valorDouble) {
		this.valorDouble = valorDouble;
	}
	
	public Boolean getValorBoolean() {
		return valorBoolean;
	}

	public void setValorBoolean(Boolean valorBoolean) {
		this.valorBoolean = valorBoolean;
	}

	public void setValor(Object obj) {
		if (obj instanceof String) {
			valorStr = (String) obj;
		} else if (obj instanceof Integer) {
			valorLong = ((Integer) obj).longValue();
		} else if (obj instanceof Long) {
			valorLong = (Long) obj;
		} else if (obj instanceof Date) {
			valorDate = (Date) obj;
		} else if (obj instanceof Double) {
			valorDouble = (Double) obj;
		} else if (obj instanceof Boolean) {
			valorBoolean = (Boolean) obj;
		} else if (obj instanceof ArchivoDTO) {
			archivo = (ArchivoDTO) obj;	
			valorArchivo = archivo.getNombre();
		
		}
	}

	@XmlTransient
	public Object getValor() {
		if (valorStr != null) {
			return valorStr;
		} else if (valorLong != null) {
			return valorLong;
		} else if (valorDate != null) {
			return valorDate;
		} else if (valorDouble != null) {
			return valorDouble;
		} else if (valorBoolean!= null) {
			return valorBoolean;
		} else if (archivo!= null) {
			return archivo;
		} else {
			return null;
		}
	}
	
	public String getInputName() {
		return inputName;
	}

	public void setInputName(String inputName) {
		this.inputName = inputName;
	}
	

	public String getEtiqueta() {
		return etiqueta;
	}

	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	public Integer getRelevanciaBusqueda() {
		return relevanciaBusqueda;
	}

	public void setRelevanciaBusqueda(Integer relevanciaBusqueda) {
		this.relevanciaBusqueda = relevanciaBusqueda;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public String getValorArchivo() {
		return valorArchivo;
	}

	public void setValorArchivo(String valorArchivo) {
		this.valorArchivo = valorArchivo;
	}

	public ArchivoDTO getArchivo() {
		return archivo;
	}

	public void setArchivo(ArchivoDTO archivo) {
		this.archivo = archivo;
	}
}
