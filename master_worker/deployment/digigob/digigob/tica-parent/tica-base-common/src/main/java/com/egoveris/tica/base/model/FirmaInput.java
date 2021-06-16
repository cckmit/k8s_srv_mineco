package com.egoveris.tica.base.model;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * The Class FirmaInput.
 */
public class FirmaInput implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7068765183767084213L;

	/** The path file PDF. */
	private String pathFilePDF;
	
	/** The path file DOC. */
	private String pathFileDOC;
	
	/** The signature field name. */
	private String signatureFieldName;
	
	/** The signature field names. */
	private List<String> signatureFieldNames;
	
	/** The path file PDF output. */
	private String pathFilePDFOutput;
	
	/** The data. */
	private byte[] data;
	
	/** The id alfresco. */
	private String idAlfresco;
	
	/** The cert cadena. */
	private String certCadena;
	
	/** The obtener info. */
	private boolean obtenerInfo;
	
	/** The ticket. */
	private String ticket;
	
	/** The pos X. */
	private Integer posX;
	
	/** The pos Y. */
	private Integer posY;
	
	/** The key store. */
	private File keyStore;
	
	/** The password. */
	private String password;
	
	/** The location. */
	private String location;
	
	/** The sello. */
	private File sello;
	
	/** The alias cert. */
	private String aliasCert;
	
	/** The algoritmo hash. */
	private String algoritmoHash;
	
	/** The cargo. */
	private String cargo;
	
	/** The organismo. */
	private String organismo;
	
	/** The sector. */
	private String sector;
	
	/** The usuario. */
	private String usuario;
	
	private Map<String, String> labelsFirma;
	
	/**
	 * Gets the pos X.
	 *
	 * @return the pos X
	 */
	public Integer getPosX() {
		return posX;
	}

	/**
	 * Sets the pos X.
	 *
	 * @param posX the new pos X
	 */
	public void setPosX(Integer posX) {
		this.posX = posX;
	}

	/**
	 * Gets the pos Y.
	 *
	 * @return the pos Y
	 */
	public Integer getPosY() {
		return posY;
	}

	/**
	 * Sets the pos Y.
	 *
	 * @param posY the new pos Y
	 */
	public void setPosY(Integer posY) {
		this.posY = posY;
	}

	/**
	 * Gets the signature field names.
	 *
	 * @return the signature field names
	 */
	public List<String> getSignatureFieldNames() {
		return signatureFieldNames;
	}

	/**
	 * Sets the signature field names.
	 *
	 * @param signatureFieldNames the new signature field names
	 */
	public void setSignatureFieldNames(List<String> signatureFieldNames) {
		this.signatureFieldNames = signatureFieldNames;
	}

	/**
	 * Gets the cert cadena.
	 *
	 * @return the cert cadena
	 */
	public String getCertCadena() {
		return certCadena;
	}

	/**
	 * Sets the cert cadena.
	 *
	 * @param certCadena the new cert cadena
	 */
	public void setCertCadena(String certCadena) {
		this.certCadena = certCadena;
	}

	/**
	 * Checks if is obtener info.
	 *
	 * @return true, if is obtener info
	 */
	public boolean isObtenerInfo() {
		return obtenerInfo;
	}

	/**
	 * Sets the obtener info.
	 *
	 * @param obtenerInfo the new obtener info
	 */
	public void setObtenerInfo(boolean obtenerInfo) {
		this.obtenerInfo = obtenerInfo;
	}

	/**
	 * Gets the ticket.
	 *
	 * @return the ticket
	 */
	public String getTicket() {
		return ticket;
	}

	/**
	 * Sets the ticket.
	 *
	 * @param ticket the new ticket
	 */
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	/**
	 * Gets the id alfresco.
	 *
	 * @return the id alfresco
	 */
	public String getIdAlfresco() {
		return idAlfresco;
	}

	/**
	 * Sets the id alfresco.
	 *
	 * @param idAlfresco the new id alfresco
	 */
	public void setIdAlfresco(String idAlfresco) {
		this.idAlfresco = idAlfresco;
	}

	/**
	 * Gets the path file PDF output.
	 *
	 * @return the path file PDF output
	 */
	public String getPathFilePDFOutput() {
		return pathFilePDFOutput;
	}

	/**
	 * Sets the path file PDF output.
	 *
	 * @param pathFilePDFOutput the new path file PDF output
	 */
	public void setPathFilePDFOutput(String pathFilePDFOutput) {
		this.pathFilePDFOutput = pathFilePDFOutput;
	}

	/**
	 * Sets the path file PDF.
	 *
	 * @param pathFilePDF the new path file PDF
	 */
	public void setPathFilePDF(String pathFilePDF) {
		this.pathFilePDF = pathFilePDF;
	}

	/**
	 * Sets the signature field name.
	 *
	 * @param signatureFieldName the new signature field name
	 */
	public void setSignatureFieldName(String signatureFieldName) {
		this.signatureFieldName = signatureFieldName;
	}

	/**
	 * Gets the path file PDF.
	 *
	 * @return the path file PDF
	 */
	public String getPathFilePDF() {
		return pathFilePDF;
	}

	/**
	 * Gets the path file DOC.
	 *
	 * @return the path file DOC
	 */
	public String getPathFileDOC() {
		return pathFileDOC;
	}

	/**
	 * Sets the path file DOC.
	 *
	 * @param pathFileDOC the new path file DOC
	 */
	public void setPathFileDOC(String pathFileDOC) {
		this.pathFileDOC = pathFileDOC;
	}

	/**
	 * Gets the signature field name.
	 *
	 * @return the signature field name
	 */
	public String getSignatureFieldName() {
		return signatureFieldName;
	}

	/**
	 * Sets the data.
	 *
	 * @param data the new data
	 */
	public void setData(byte[] data) {
		this.data = data;
	}

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public byte[] getData() {
		return data;
	}

	/**
	 * Gets the key store.
	 *
	 * @return the key store
	 */
	public File getKeyStore() {
		return keyStore;
	}

	/**
	 * Sets the key store.
	 *
	 * @param keyStore the new key store
	 */
	public void setKeyStore(File keyStore) {
		this.keyStore = keyStore;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the location.
	 *
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Sets the location.
	 *
	 * @param location the new location
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * Gets the sello.
	 *
	 * @return the sello
	 */
	public File getSello() {
		return sello;
	}

	/**
	 * Sets the sello.
	 *
	 * @param sello the new sello
	 */
	public void setSello(File sello) {
		this.sello = sello;
	}

	/**
	 * Gets the alias cert.
	 *
	 * @return the alias cert
	 */
	public String getAliasCert() {
		return aliasCert;
	}

	/**
	 * Sets the alias cert.
	 *
	 * @param aliasCert the new alias cert
	 */
	public void setAliasCert(String aliasCert) {
		this.aliasCert = aliasCert;
	}

	/**
	 * Gets the algoritmo hash.
	 *
	 * @return the algoritmo hash
	 */
	public String getAlgoritmoHash() {
		return algoritmoHash;
	}

	/**
	 * Sets the algoritmo hash.
	 *
	 * @param algoritmoHash the new algoritmo hash
	 */
	public void setAlgoritmoHash(String algoritmoHash) {
		this.algoritmoHash = algoritmoHash;
	}

	/**
	 * Gets the cargo.
	 *
	 * @return the cargo
	 */
	public String getCargo() {
		return cargo;
	}

	/**
	 * Sets the cargo.
	 *
	 * @param cargo the new cargo
	 */
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	/**
	 * Gets the organismo.
	 *
	 * @return the organismo
	 */
	public String getOrganismo() {
		return organismo;
	}

	/**
	 * Sets the organismo.
	 *
	 * @param organismo the new organismo
	 */
	public void setOrganismo(String organismo) {
		this.organismo = organismo;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	/**
	 * Gets the usuario.
	 *
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * Sets the usuario.
	 *
	 * @param usuario the new usuario
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the labelsFirma
	 */
	public Map<String, String> getLabelsFirma() {
		return labelsFirma;
	}

	/**
	 * @param labelsFirma the labelsFirma to set
	 */
	public void setLabelsFirma(Map<String, String> labelsFirma) {
		this.labelsFirma = labelsFirma;
	}

	
}
