package com.egoveris.te.base.model;

import java.io.Serializable;

/**
 * Documento para la identificacion de una persona fisica
 * o juridica.
 * 
 * @author rgalloci
 *
 */

public class DocumentoDeIdentidadDTO implements Serializable{
	private static final long serialVersionUID = -3216817270662213059L;
	private Long id;
	private String tipoDocumento;
	private String numeroDocumento;
	private TipoDocumentoPosibleDTO tipoDocumentoPosible;
		
	public DocumentoDeIdentidadDTO(){
		
	}
		
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getTipoDocumento() {		
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	
	public void setNumeroDocumento(String numeroDocumento) {
//		if (validarCuitCuil(numeroDocumento)){
//			this.numeroDocumento = numeroDocumento;
//		}	
		this.numeroDocumento = numeroDocumento;
	}
	
	public TipoDocumentoPosibleDTO getTipoDocumentoPosible() {
		return tipoDocumentoPosible;
	}
	public void setTipoDocumentoPosible(TipoDocumentoPosibleDTO tipoDocumentoPosible) {
		this.tipoDocumentoPosible = tipoDocumentoPosible;
	}
	
	/**
	 * Verifica que si el tipo de documento cuit o cuil, el formato
	 * sea el indicado. Se valida esta condicion solamente si 
	 * fue seteado previamente el documento como cuit o cuil.  
	 * 
	 * @param numeroDocumento
	 * @return true si el quit o cuil es valido.
	 */
//	private boolean validarCuitCuil(String numero){
//		if (!TipoDocumentoPosible.CUIL.toString().equals(this.tipoDocumento) ||
//			!TipoDocumentoPosible.CU.toString().equals(this.tipoDocumento)){
//			return true; 
//		}
//		if (!TipoDocumentoPosible.CU.toString().equals(this.tipoDocumento)){
//				return true; 
//			}
//		try{
//			new Integer(numero.replace("-", ""));
//		}catch(NumberFormatException nfe){
//			return false;
//		}
//		return true;
//	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((numeroDocumento == null) ? 0 : numeroDocumento.hashCode());
		result = prime * result
				+ ((tipoDocumento == null) ? 0 : tipoDocumento.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DocumentoDeIdentidadDTO other = (DocumentoDeIdentidadDTO) obj;
		if (numeroDocumento == null) {
			if (other.numeroDocumento != null)
				return false;
		} else if (!numeroDocumento.equals(other.numeroDocumento))
			return false;
		if (tipoDocumento == null) {
			if (other.tipoDocumento != null)
				return false;
		} else if (!tipoDocumento.equals(other.tipoDocumento))
			return false;
		return true;
	}
	
	
	
	
}
