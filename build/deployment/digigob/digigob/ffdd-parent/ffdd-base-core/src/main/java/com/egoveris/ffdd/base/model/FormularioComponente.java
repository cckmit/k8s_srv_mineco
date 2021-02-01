package com.egoveris.ffdd.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "DF_FORM_COMPONENT")
public class FormularioComponente {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@Column(name = "name")
	private String nombre;

	@Column(name = "label")
	private String etiqueta;

	@Column(name = "obligatory")
	private Boolean obligatorio;

	@Column(name = "search_relevancy")
	private Integer relevanciaBusqueda;

	@Column(name = "comp_order")
	private Integer orden;

	@ManyToOne
	@JoinColumn(name = "id_form")
	private Formulario formulario;

	@ManyToOne
	@JoinColumn(name = "id_component")
	private Componente componente;

	@Transient
	private String textoMultilinea;

	@Column(name = "hidden")
	private Boolean oculto;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Formulario getFormulario() {
		return formulario;
	}

	public void setFormulario(Formulario formulario) {
		this.formulario = formulario;
	}

	public Componente getComponente() {
		return componente;
	}

	public void setComponente(Componente componente) {
		this.componente = componente;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEtiqueta() {
		return etiqueta;
	}

	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public Boolean getObligatorio() {
		return obligatorio;
	}

	public void setObligatorio(Boolean obligatorio) {
		this.obligatorio = obligatorio;
	}

	public Integer getRelevanciaBusqueda() {
		return relevanciaBusqueda;
	}

	public void setRelevanciaBusqueda(Integer relevanciaBusqueda) {
		this.relevanciaBusqueda = relevanciaBusqueda;
	}

	public String getTextoMultilinea() {
		return textoMultilinea;
	}

	public void setTextoMultilinea(String textoMultilinea) {
		this.textoMultilinea = textoMultilinea;
	}

	public boolean isMultilinea() {
		return "TextBox - Multilinea".equals(getComponente().getNombre());
	}

	public Boolean getOculto() {
		return oculto;
	}

	public void setOculto(Boolean oculto) {
		this.oculto = oculto;
	}

}
