package com.egoveris.te.model.model;


import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

/**
 * La presente clase da forma a los datos que se requiere ingresar a un servicio
 * externo, para que éste pueda operar.
 * 
 * @author cearagon
 */
public class CaratulacionExpedienteRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1358291145144128010L;

	/**
	 * Nombre del usuario logueado, realizando la operación.
	 */
	private String loggeduser;
	/**
	 * Nombre del sistema que solicita la generación del expediente electrónico.
	 */
	private String sistema;
	/**
	 * Motivo interno por el cual se solicita generar el expediente.
	 */
	private String motivo;
	/**
	 * Motivo Externo por el cual se solicita generar el expediente. Es el que
	 * se vera en la consulta externa.
	 */
	private String motivoExterno;

	/**
	 * Código de la trata para el expediente que se desea generar.
	 */
	private String selectTrataCod;
	/**
	 * Refiere al orígen de la solicitud. True, si quien solicita pertenece a
	 * algún sistema del gobierno. False, en caso contrario.
	 */
	private boolean interno;
	/**
	 * Refiere al orígen de la solicitud. True, si quien solicita no pertenece a
	 * algún sistema del gobierno. False, en caso contrario.
	 */
	private boolean externo;
	/**
	 * Descripción correspondiente a la generación del expediente.
	 */
	private String descripcion;
	/**
	 * Si externo es true y quien solicita es una persona física.
	 */
	private boolean persona;
	/**
	 * Si externo es true y quien solicita es una empresa.
	 */
	private boolean empresa;
	/**
	 * Si externo es true. Corresponde al tipo de documento de la
	 * persona/empresa solicitnate.
	 */
	private String tipoDoc;
	/**
	 * Si externo es true. Corresponde al nro de documento de la persona/empresa
	 * solicitante.
	 */
	private String nroDoc;
	/**
	 * Si externo y persona son true. Corresponde al apellido de la persona
	 * solicitante.
	 */
	private String apellido;

	/**
	 * Corresponde al segundo apellido de la persona solicitante.
	 */
	private String segundoApellido;

	/**
	 * Corresponde al tercer apellido de la persona solicitante.
	 */
	private String tercerApellido;

	/**
	 * Si externo y persona son true. Corresponde al nombre de la persona
	 * solicitante.
	 */
	private String nombre;

	/**
	 * Corresponde al segundo nombre de la persona solicitante.
	 */
	private String segundoNombre;

	/**
	 * Corresponde al tercer nombre de la persona solicitante.
	 */
	private String tercerNombre;
	/**
	 * Si externo y empresa son true. Corresponde a la razón social de la
	 * empresa solicitante.
	 */
	private String razonSocial;
	/**
	 * Si externo es true. Corresponde al e-mail de la persona/empresa
	 * solicitante.
	 */
	private String email;
	/**
	 * Si externo es true. Corresponde al teléfono de la persona/empresa
	 * solicitante.
	 */
	private String telefono;

	/**
	 * Corresponde al cuit/cuil solicitante.
	 */
	private boolean tieneCuitCuil = false;

	/**
	 * Corresponde al cuit/cuil solicitante.
	 */
	private String cuitCuil;

	/**
	 * Corresponde al domicilio solicitante.
	 */
	private String domicilio;

	/**
	 * Corresponde al piso solicitante.
	 */
	private String piso;

	/**
	 * Corresponde al departamento solicitante.
	 */
	private String departamento;

	/**
	 * Corresponde al codigoPostal solicitante.
	 */
	private String codigoPostal;
	
	/**
	 * Boolean para definir el estado de bloqueo de la tarea.
	 */
	private boolean taskApp;
 
	/**
	 * Corresponde al barrio solicitante.
	 */
//	private String barrio;
	
	/**
	 * Corresponde al comuna solicitante.
	 */
//	private String comuna;
	
	/**
	 * Lista conteniendo los metadatos relacionados a la trata, y
	 * correspondientes al expediente que se desea generar.
	 */
	private List<ExpedienteMetadataExternal> metadata;

	/**
	 * Id de transaccion del documento de caratula variable
	 */
	private Integer idTransaccion;

	/**
	 * campos del formulario controlado
	 */
	
	private List<ExpedienteFFccExternalRequest> camposFFCCExternal;
	
	@XmlTransient
	protected HashMap<String, Object> camposFFCC;

	

	// Getters and Setters

	public String getLoggeduser() {
		return loggeduser;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getMotivoExterno() {
		return motivoExterno;
	}

	public void setMotivoExterno(String motivoExterno) {
		this.motivoExterno = motivoExterno;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getSistema() {
		return sistema;
	}

	public void setSistema(String sistema) {
		this.sistema = sistema;
	}

	public String getSelectTrataCod() {
		return selectTrataCod;
	}

	public void setSelectTrataCod(String selectTrataCod) {
		this.selectTrataCod = selectTrataCod;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<ExpedienteMetadataExternal> getMetadata() {
		return metadata;
	}

	public void setMetadata(List<ExpedienteMetadataExternal> metadata) {
		this.metadata = metadata;
	}

	public boolean isInterno() {
		return interno;
	}

	public void setInterno(boolean interno) {
		this.interno = interno;
	}

	public boolean isExterno() {
		return externo;
	}

	public void setExterno(boolean externo) {
		this.externo = externo;
	}

	public String getApellido() {
		return apellido;
	}

	public boolean isPersona() {
		return persona;
	}

	public void setPersona(boolean persona) {
		this.persona = persona;
	}

	public boolean isEmpresa() {
		return empresa;
	}

	public void setEmpresa(boolean empresa) {
		this.empresa = empresa;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getTipoDoc() {
		return tipoDoc;
	}

	public void setTipoDoc(String tipoDoc) {
		this.tipoDoc = tipoDoc;
	}

	public String getNroDoc() {
		return nroDoc;
	}

	public void setNroDoc(String nroDoc) {
		this.nroDoc = nroDoc;
	}

	public void setCuitCuil(String cuitCuil) {
		this.cuitCuil = cuitCuil;
	}

	public String getCuitCuil() {
		return cuitCuil;
	}

	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}

	public String getSegundoNombre() {
		return segundoNombre;
	}

	public void setTercerApellido(String tercerApellido) {
		this.tercerApellido = tercerApellido;
	}

	public String getTercerApellido() {
		return tercerApellido;
	}

	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	public String getSegundoApellido() {
		return segundoApellido;
	}

	public void setTieneCuitCuil(boolean tieneCuitCuil) {
		this.tieneCuitCuil = tieneCuitCuil;
	}

	public boolean isTieneCuitCuil() {
		return tieneCuitCuil;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setPiso(String piso) {
		this.piso = piso;
	}

	public String getPiso() {
		return piso;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	
	
//	public String getBarrio() {
//		return barrio;
//	}

//	public void setBarrio(String barrio) {
//		this.barrio = barrio;
//	}

//	public String getComuna() {
//		return comuna;
//	}

//	public void setComuna(String comuna) {
//		this.comuna = comuna;
//	}

	/**
	 * @return the taskApp
	 */
	public boolean getTaskApp() {
		return taskApp;
	}

	/**
	 * @param taskApp the taskApp to set
	 */
	public void setTaskApp(boolean taskApp) {
		this.taskApp = taskApp;
	}

	public void setTercerNombre(String tercerNombre) {
		this.tercerNombre = tercerNombre;
	}

	public String getTercerNombre() {
		return tercerNombre;
	}

	public Integer getIdTransaccion() {
		return idTransaccion;
	}

	public void setIdTransaccion(Integer idTransaccion) {
		this.idTransaccion = idTransaccion;
	}



	public void setCamposFFCCExternal(List<ExpedienteFFccExternalRequest> camposFFCCExternal) {
		this.camposFFCCExternal = camposFFCCExternal;
	}

	public List<ExpedienteFFccExternalRequest> getCamposFFCCExternal() {
		return camposFFCCExternal;
	}

	public void setCamposFFCC(HashMap<String, Object> camposFFCC) {
		if (camposFFCC!=null&& camposFFCC.size()>0){
			this.camposFFCC = camposFFCC;
		}
		this.camposFFCC = getCamposFFCC();

	}
    @XmlTransient
	public HashMap<String, Object> getCamposFFCC() {
		if (this.camposFFCCExternal!=null && this.camposFFCCExternal.size()>0){
			this.camposFFCC = new HashMap<String, Object>();
			for (ExpedienteFFccExternalRequest e : this.camposFFCCExternal){
				this.camposFFCC.put(e.getClave(), e.getValor());
			}
		} 
		return (HashMap<String, Object>) camposFFCC;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CaratulacionExpedienteRequest [loggeduser=");
		builder.append(loggeduser);
		builder.append(", sistema=");
		builder.append(sistema);
		builder.append(", motivo=");
		builder.append(motivo);
		builder.append(", motivoExterno=");
		builder.append(motivoExterno);
		builder.append(", selectTrataCod=");
		builder.append(selectTrataCod);
		builder.append(", interno=");
		builder.append(interno);
		builder.append(", externo=");
		builder.append(externo);
		builder.append(", descripcion=");
		builder.append(descripcion);
		builder.append(", persona=");
		builder.append(persona);
		builder.append(", empresa=");
		builder.append(empresa);
		builder.append(", tipoDoc=");
		builder.append(tipoDoc);
		builder.append(", nroDoc=");
		builder.append(nroDoc);
		builder.append(", apellido=");
		builder.append(apellido);
		builder.append(", segundoApellido=");
		builder.append(segundoApellido);
		builder.append(", tercerApellido=");
		builder.append(tercerApellido);
		builder.append(", nombre=");
		builder.append(nombre);
		builder.append(", segundoNombre=");
		builder.append(segundoNombre);
		builder.append(", tercerNombre=");
		builder.append(tercerNombre);
		builder.append(", razonSocial=");
		builder.append(razonSocial);
		builder.append(", email=");
		builder.append(email);
		builder.append(", telefono=");
		builder.append(telefono);
		builder.append(", tieneCuitCuil=");
		builder.append(tieneCuitCuil);
		builder.append(", cuitCuil=");
		builder.append(cuitCuil);
		builder.append(", domicilio=");
		builder.append(domicilio);
		builder.append(", piso=");
		builder.append(piso);
		builder.append(", departamento=");
		builder.append(departamento);
		builder.append(", codigoPostal=");
		builder.append(codigoPostal);
		builder.append(", metadata=");
		builder.append(metadata);
		builder.append(", idTransaccion=");
		builder.append(idTransaccion);
		builder.append(", camposFFCCExternal=");
		builder.append(camposFFCCExternal);
		builder.append(", camposFFCC=");
		builder.append(camposFFCC);
		builder.append("]");
		return builder.toString();
	}



}
