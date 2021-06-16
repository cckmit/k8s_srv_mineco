package com.egoveris.ffdd.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.egoveris.ffdd.model.model.ArchivoDTO;

@Entity
@Table(name = "DF_FORM_COMP_VALUE")
public class ValorFormComp {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "id_form_component")
	private Integer idFormComp;
	
	@Column(name = "input_name")
	private String inputName;
	
	@Column(name = "value_str")
	private String valorStr;
	
	@Column(name = "value_int")
	private Long valorLong;
	
	@Column(name = "value_date")
	private Date valorDate;
	
	@Column(name = "value_double")
	private Double valorDouble;
	
	@Column(name = "value_boolean")
	private Boolean valorBoolean;
	
	@Column(name = "value_archivo")
	private String valorArchivo;

	public ValorFormComp() {
	  //constructor 
	}

	public ValorFormComp(final Integer idFormComp, final String inputName, final Object value) {
		setIdFormComp(idFormComp);
		setValor(value);
		setInputName(inputName);
	}

	public Integer getId() {
		return id;
	}

	public Integer getIdFormComp() {
		return idFormComp;
	}

	public String getInputName() {
		return inputName;
	}

	public Object getValor() {
		if (valorStr != null) {
			return valorStr;
		} else if (valorLong != null) {
			return valorLong;
		} else if (valorDate != null) {
			return valorDate;
		} else if (valorDouble != null) {
			return valorDouble;
		} else if (valorBoolean != null) {
			return valorBoolean;
		} else {
			return null;
		}
	}

	public String getValorArchivo() {
		return valorArchivo;
	}

	public Boolean getValorBoolean() {
		return valorBoolean;
	}

	public Date getValorDate() {
		return valorDate;
	}

	public Double getValorDouble() {
		return valorDouble;
	}

	public Long getValorLong() {
		return valorLong;
	}

	public String getValorStr() {
		return valorStr;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public void setIdFormComp(final Integer idFormComp) {
		this.idFormComp = idFormComp;
	}

	public void setInputName(final String inputName) {
		this.inputName = inputName;
	}

	public void setValor(final Object obj) {
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
			valorArchivo = ((ArchivoDTO) obj).getNombre();
		}
	}

	public void setValorArchivo(final String valorArchivo) {
		this.valorArchivo = valorArchivo;
	}

	public void setValorBoolean(final Boolean valorBoolean) {
		this.valorBoolean = valorBoolean;
	}

	public void setValorDate(final Date valorDate) {
		this.valorDate = valorDate;
	}

	public void setValorDouble(final Double valorDouble) {
		this.valorDouble = valorDouble;
	}

	public void setValorLong(final Long valorLong) {
		this.valorLong = valorLong;
	}

	public void setValorStr(final String valorStr) {
		this.valorStr = valorStr;
	}

	@Override
	public String toString() {
		return "IdFormComp: " + idFormComp + " valor: " + getValor();
	}
}
