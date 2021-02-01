package com.egoveris.deo.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="GEDO_DOC_PUBLICABLE")
public class DocumentoPublicable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="NOMBRE_ARCH_TEMP")
	private String nombreArchivoTemporal;
	
	@Column(name="ID_PUBLICABLE")
	private String idPublicable;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombreArchivoTemporal() {
		return nombreArchivoTemporal;
	}

	public void setNombreArchivoTemporal(String nombreArchivoTemporal) {
		this.nombreArchivoTemporal = nombreArchivoTemporal;
	}

	public String getIdPublicable() {
		return idPublicable;
	}

	public void setIdPublicable(String idPublicable) {
		this.idPublicable = idPublicable;
	}
	
}
