package com.egoveris.ffdd.model.model;

import java.io.Serializable;
import java.util.List;

public class FormularioComponenteWDDTO implements Serializable, Comparable<FormularioComponenteWDDTO> {

	private static final long serialVersionUID = 1657372480346415935L;

	private Integer id;
	private String nombre;
	private String etiqueta;
	private Boolean obligatorio;
	private Integer relevanciaBusqueda;
	private Integer orden;
	private transient FormularioWDDTO formulario;
	private transient ComponenteDTO componente;
	private String textoMultilinea;
	private List<ConstraintDTO> constraintList;
	private Boolean oculto;

	public static final String TEXTBOX_MULTILINEA = "TextBox - Multilinea";

	public FormularioComponenteWDDTO clone() {
		FormularioComponenteWDDTO clonado = new FormularioComponenteWDDTO();
		clonado.setComponente(this.getComponente());
		clonado.setEtiqueta(this.getEtiqueta());
		clonado.setId(this.getId());
		clonado.setNombre(this.getNombre());
		clonado.setObligatorio(this.getObligatorio());
		clonado.setOrden(this.getOrden());
		clonado.setRelevanciaBusqueda(this.relevanciaBusqueda);
		clonado.setConstraintList(this.constraintList);
		return clonado;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ComponenteDTO getComponente() {
		return componente;
	}

	public void setComponente(ComponenteDTO componente) {
		this.componente = componente;
	}

	public FormularioWDDTO getFormulario() {
		return formulario;
	}

	public void setFormulario(FormularioWDDTO formulario) {
		this.formulario = formulario;
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

	public List<ConstraintDTO> getConstraintList() {
		return constraintList;
	}

	public void setConstraintList(List<ConstraintDTO> constraintList) {
		this.constraintList = constraintList;
	}

	@Override
	public int compareTo(FormularioComponenteWDDTO o) {
		return orden.compareTo(o.getOrden());
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((etiqueta == null) ? 0 : etiqueta.hashCode());
		result = prime * result + ((obligatorio == null) ? 0 : obligatorio.hashCode());
		result = prime * result + ((relevanciaBusqueda == null) ? 0 : relevanciaBusqueda.hashCode());
		result = prime * result + ((orden == null) ? 0 : orden.hashCode());
		result = prime * result + ((componente == null) ? 0 : componente.hashCode());
		return result;
	}

	public boolean isMultilinea() {
		return getComponente().getNombre().equals(TEXTBOX_MULTILINEA);
	}

	public boolean isComponenteComun() {
		return !(getComponente().getNombre().equals(TEXTBOX_MULTILINEA));
	}

	public String toString() {
		return this.nombre;
	}

	public Boolean getOculto() {
		return oculto;
	}

	public void setOculto(Boolean oculto) {
		this.oculto = oculto;
	}
}