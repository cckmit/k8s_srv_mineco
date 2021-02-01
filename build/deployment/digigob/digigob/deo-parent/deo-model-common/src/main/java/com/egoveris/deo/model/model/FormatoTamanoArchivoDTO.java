package com.egoveris.deo.model.model;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class FormatoTamanoArchivoDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5655656368633985180L;
	private Integer id;
	private String formato;
	private Integer tamano;
	private String descripcion;
	private boolean obligatoriedad;
	private Set<ExtensionMimetypeDTO> extensionMimetypes;
	
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

	public Set<ExtensionMimetypeDTO> getExtensionMimetypes() {
		return extensionMimetypes;
	}

	public void setExtensionMimetypes(Set<ExtensionMimetypeDTO> extensionMimetypes) {
		this.extensionMimetypes = extensionMimetypes;
	}
	
	
	public boolean existeExtension(List<ArchivoEmbebidoDTO> listaArchivosEmbebidos) {
		
		boolean existeExtension = false;
		for (ArchivoEmbebidoDTO archivoEmbebido : listaArchivosEmbebidos) {
			for(Iterator<ExtensionMimetypeDTO> mimetypesIterator = this.getExtensionMimetypes().iterator(); mimetypesIterator.hasNext();){
				ExtensionMimetypeDTO extMimetype = mimetypesIterator.next();			
				if(extMimetype.getExtensionMimetypePK().getMimetype().equals(archivoEmbebido.getMimetype())){
					existeExtension = true;
					return existeExtension;
				}
			}
		}
		return existeExtension;
	}
	
	
}
