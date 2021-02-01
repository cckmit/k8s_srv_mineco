package com.egoveris.deo.base.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "GEDO_PLANTILLA")
public class Plantilla {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "descripcion")
	private String descripcion;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "contenido")
	private String contenido;

	@Column(name = "fecha_modificacion")
	private Date fechaModificacion;

	@OneToMany(mappedBy = "plantilla", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<UsuarioPlantilla> setUsuarioPlantilla;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public void setSetUsuarioPlantilla(Set<UsuarioPlantilla> setUsuarioPlantilla) {
		this.setUsuarioPlantilla = setUsuarioPlantilla;
	}

	public Set<UsuarioPlantilla> getSetUsuarioPlantilla() {
		return setUsuarioPlantilla;
	}

	public void addUsuarioPlantilla(UsuarioPlantilla usuarioPlantilla) {
		usuarioPlantilla.setPlantilla(this);
		setUsuarioPlantilla.add(usuarioPlantilla);
	}
}
