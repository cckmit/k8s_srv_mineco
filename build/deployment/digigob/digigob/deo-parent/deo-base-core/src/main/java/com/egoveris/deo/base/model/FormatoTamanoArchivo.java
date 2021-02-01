package com.egoveris.deo.base.model;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "GEDO_FORMATO_TAMANO_ARCHIVO")
public class FormatoTamanoArchivo {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "FORMATO")
	private String formato;

	@Column(name = "TAMANO")
	private Integer tamano;

	@Column(name = "DESCRIPCION")
	private String descripcion;

	@Column(name = "obligatoriedad")
	private boolean obligatoriedad;

	@OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
	@JoinColumn(name = "ID_FORMATO_TAMANO_ARCHIVO")
	private Set<ExtensionMimetype> extensionMimetypes;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public Integer getTamano() {
		return tamano;
	}

	public void setTamano(Integer tamano) {
		this.tamano = tamano;

	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean isObligatoriedad() {
		return obligatoriedad;
	}

	public void setObligatoriedad(boolean obligatoriedad) {
		this.obligatoriedad = obligatoriedad;
	}

	public Set<ExtensionMimetype> getExtensionMimetypes() {
		return extensionMimetypes;
	}

	public void setExtensionMimetypes(Set<ExtensionMimetype> extensionMimetypes) {
		this.extensionMimetypes = extensionMimetypes;
	}

	public boolean existeExtension(List<ArchivoEmbebido> listaArchivosEmbebidos) {

		boolean existeExtension = false;
		for (ArchivoEmbebido archivoEmbebido : listaArchivosEmbebidos) {
			for (Iterator<ExtensionMimetype> mimetypesIterator = this.getExtensionMimetypes()
					.iterator(); mimetypesIterator.hasNext();) {
				ExtensionMimetype extMimetype = mimetypesIterator.next();
				if (extMimetype.getExtensionMimetypePK().getMimetype().equals(archivoEmbebido.getMimetype())) {
					existeExtension = true;
					return existeExtension;
				}
			}
		}
		return existeExtension;
	}

}
